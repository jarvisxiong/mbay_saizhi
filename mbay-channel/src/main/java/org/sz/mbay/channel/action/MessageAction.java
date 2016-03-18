package org.sz.mbay.channel.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.Notice;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.NoticeService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.stationLetter.bean.StationLetter;
import org.sz.mbay.stationLetter.bean.UserStationLetter;
import org.sz.mbay.stationLetter.service.StationLetterService;
import org.sz.mbay.stationLetter.service.UserStationLetterService;

/**
 * @author Tom
 * @version 创建时间：2014-8-18上午10:20:45
 * @type 类型描述
 */

@Controller
@RequestMapping("message")
public class MessageAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageAction.class);
	
	public static final String RECEIVE_MESSAGE = "/emailMessage/receivemessage";
	public static final String WRITE_MESSAGE = "/emailMessage/writemessage";
	public static final String SEND_OUT_MESSAGE = "/emailMessage/outboxmessage";
	public static final String NOTICE_LIST = "/emailMessage/notice_list";// 公告列表
	public static final String REDIRECT_NOTICE_LIST = "redirect:/message/notice_list.mbay";// 重定向到列表页面
	public static final String NOTICE_INFO = "/emailMessage/notice_info";// 公告信息
	
	@Autowired
	StationLetterService messageService;
	
	@Autowired
	UserStationLetterService userHasMsgService;
	
	@Autowired
	NoticeService noticeService;
	@Autowired
	UserService userService;
	
	/**
	 * @return 公告列表
	 * @throws Exception
	 */
	@RequestMapping("notice_list")
	public String noticelist(Model model, PageInfo pageinfo,
			HttpServletRequest request) throws Exception {
		List<Notice> noticelist = noticeService.findAllNotice(pageinfo);
		
		if (noticelist.size() > 0) {
			model.addAttribute("noticelist", noticelist);
			model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		}
		return NOTICE_LIST;
	}
	
	@RequestMapping("delete_notice")
	@ResponseBody
	public JSONObject dleteStrategy(@RequestParam("id") int id) {
		ExecuteResult result = this.noticeService.deleteNotice(id);
		return JSONObject.fromObject(result);
	}
	
	@RequestMapping("notice_info")
	@ResponseBody
	public JSONObject noticeinfo(
			@RequestParam("id") int id,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		Notice notice = this.noticeService.findNoticeById(id);
		if (notice != null) {
			// 将记录id保存到cookie
			Cookie noticeCookie = null;
			String cookieName = Globals.READED_NOTICE_COOKIE + "_" +
					ChannelContext.getSessionChannel().getUserNumber();
			
			for (Cookie cookie : request.getCookies()) {
				if (cookieName.equals(cookie.getName())) {
					noticeCookie = cookie;
					break;
				}
			}
			
			boolean newId = false;
			if (noticeCookie == null) {
				noticeCookie = new Cookie(cookieName, String.valueOf(id));
				newId = true;
			} else {
				String value = noticeCookie.getValue();
				if (value != null && !"".equals(value)) {
					String[] ids = noticeCookie.getValue().split(",");
					List<String> idList = Arrays.asList(ids);
					if (!idList.contains(String.valueOf(id))) {
						noticeCookie.setValue(noticeCookie.getValue() + "," + id);
						newId = true;
					}
				}
			}
			
			// 过期时间1年
			noticeCookie.setMaxAge(365 * 24 * 60 * 60);
			noticeCookie.setPath("/");
			response.addCookie(noticeCookie);
			
			// session中未读公告数量减一
			if (newId) {
				int num = (int) session.getAttribute(Globals.UNREAD_NOTICE_NUM);
				session.setAttribute(Globals.UNREAD_NOTICE_NUM, num - 1 >= 0 ? num - 1 : 0);
			}
			
			JSONObject obj = new JSONObject();
			obj.put("state", true);
			obj.put("title", notice.getTitle());
			obj.put("noticedate", MbayDateFormat.formatDateTime(notice.getNoticedate(),
					MbayDateFormat.DatePattern.isoTime));
			obj.put("content", notice.getContent());
			obj.put("unreadNoticeNum", (int) session.getAttribute(Globals.UNREAD_NOTICE_NUM));
			return obj;
		} else {
			JSONObject obj = new JSONObject();
			obj.put("state", false);
			obj.put("msg", "公告不存在或已删除！");
			return obj;
		}
	}
	
	/**
	 * @return 进入信箱(收件箱)
	 * @throws Exception
	 */
	@RequestMapping("msg_list")
	public String selectmsg(Model model, PageInfo pageinfo, HttpServletRequest request) throws Exception {
		// 查询信息列表
		List<UserStationLetter> hmsglist = this.userHasMsgService.findAllUserStationLetter(ChannelContext.getSessionChannel().getUserNumber(), pageinfo);
		if (hmsglist.size() > 0) {
			int id = hmsglist.get(0).getMessage().getId();
			
			StationLetter msg = this.messageService.findMessage(id + "");
			model.addAttribute("hmsglist", hmsglist);
			model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
			model.addAttribute("msg", msg);
		} else {
			model.addAttribute("unreadnum", 0);
		}
		// 查询未读信息条数
		return RECEIVE_MESSAGE;
	}
	
	/**
	 * @return 进入信箱（发件箱）
	 * @throws Exception
	 */
	@RequestMapping("out_msg_list")
	public String selectoutmsg(Model model, PageInfo pageinfo) throws Exception {
		// 查询信息列表
		List<UserStationLetter> hmsglist = this.userHasMsgService.findAllUserHasOutMsg(ChannelContext.getSessionChannel().getUserNumber(), pageinfo);
		
		if (hmsglist.size() > 0) {
			int id = hmsglist.get(0).getMessage().getId();// 获取列表中的第一条消息
			StationLetter msg = this.messageService.findMessage(id + "");
			model.addAttribute("hmsglist", hmsglist);
			model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
			model.addAttribute("msg", msg);
		} else {
			model.addAttribute("unreadnum", 0);
		}
		
		return SEND_OUT_MESSAGE;
	}
	
	/**
	 * @return 写信
	 */
	@RequestMapping("writemessage")
	public String writemessage(@RequestParam(value = "id", required = false) Integer id, Model model) throws Exception {
		/** 未读信息条数 */
		int unreadnum = this.userHasMsgService.countUnMsg(ChannelContext.getSessionChannel().getUserNumber());
		if (id != null) {
			// 回复信息
			UserStationLetter message = this.userHasMsgService.findHasMsgById(id);
			model.addAttribute("message", message);
		}
		model.addAttribute("unreadnum", unreadnum);
		return WRITE_MESSAGE;
	}
	
	/**
	 * @return 写信
	 * @throws Exception
	 */
	@RequestMapping("createMsg")
	public String sendmsg(@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("usernumber") String usernumber, HttpServletRequest request, Model model) throws Exception {
		/** 未读信息条数 */
		int unreadnum = this.userHasMsgService.countUnMsg(ChannelContext.getSessionChannel().getUserNumber());
		// 验证账号是否存在usernumber
		model.addAttribute("unreadnum", unreadnum);
		if (usernumber.length() > 8 && usernumber.indexOf("(") != -1) {
			int begin = usernumber.indexOf("(") + 1;
			int end = usernumber.indexOf(")");
			usernumber = usernumber.substring(begin, end);
		}
		
		// 对回复标题中的字符串处理
		String contitle = "";
		if (title.indexOf(":") != -1) {
			contitle = title.substring(title.indexOf(":") + 1, title.length());
		} else {
			contitle = title;
		}
		StationLetter message = new StationLetter();
		message.setTitle(contitle);
		message.setContent(content);
		if (!this.messageService.sendWebSiteEmail(message, usernumber, ChannelContext.getSessionChannel().getUserNumber())) {
			LOGGER.error("消息创建失败!");
		} else {
			LOGGER.debug("消息创建成功!");
		}
		return WRITE_MESSAGE;
	}
	
	/**
	 * @return 查看未读信息
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("countUnMsg")
	public JSONObject countUserHasMsg() throws Exception {
		int num = this.userHasMsgService.countUnMsg(ChannelContext.getSessionChannel().getUserNumber());
		return JSONObject.fromObject(num);
	}
	
	/**
	 * @return 查看收件箱信息内容
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("selectMsgInfo")
	public JSONObject selectMsgInfo(@RequestParam("id") String id, HttpServletRequest request) throws Exception {
		// 未读站内信数量减一
		int readStatus = userHasMsgService.getHasMsgReadStatusById(Integer.parseInt(id));
		if (readStatus == 0) {
			HttpSession session = request.getSession();
			int unreadMsgNum = (int) session.getAttribute(Globals.UNREAD_MSG_NUM);
			session.setAttribute(Globals.UNREAD_MSG_NUM, unreadMsgNum - 1);
		}
		
		StationLetter message = this.messageService.findMessage(id);
		Map<String, Object> msgmap = new HashMap<>();
		msgmap.put("title", message.getTitle());
		msgmap.put("content", message.getContent());
		msgmap.put("sendTime", MbayDateFormat.formatDateTime(message.getSendTime(),
				MbayDateFormat.DatePattern.isoTime));
		
		// 更改消息的阅读状态
		userHasMsgService.updatereadStatus(id);
		
		return JSONObject.fromObject(msgmap);
	}
	
	/**
	 * @return 查看发件箱信息内容
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("selectSelfMsgInfo")
	public JSONObject selectselfMsgInfo(@RequestParam("id") String id) throws Exception {
		StationLetter message = this.messageService.findMessage(id);
		Map<String, String> msgmap = new HashMap<>();
		msgmap.put("title", message.getTitle());
		msgmap.put("content", message.getContent());
		msgmap.put("sendTime", MbayDateFormat.formatDateTime(message.getSendTime(), DatePattern.isoTime));
		return JSONObject.fromObject(msgmap);
	}
	
	@ResponseBody
	@RequestMapping("deleteMsg")
	public JSONObject deleteMsg(@RequestParam("id") int id) throws Exception {
		ExecuteResult result = this.userHasMsgService.deleteMsg(id);
		return JSONObject.fromObject(result);
	}
	
	@ResponseBody
	@RequestMapping("deleteSendMsg")
	public JSONObject deleteSendMsg(@RequestParam("id") int id) throws Exception {
		ExecuteResult result = this.userHasMsgService.deleteSendMsg(id);
		return JSONObject.fromObject(result);
	}
	
	/**
	 * 获取站内信未读信息数量 - setInterval延迟加载
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getMsgnum")
	public int getmessagenum(HttpServletRequest request, HttpSession session) {
		// 获取上次请求时间
		Object obj = session.getAttribute(Globals.UNREAD_MSG_LAST_TIME);
		DateTime lastTime = null;
		boolean needQueryDb = false;
		
		if (obj == null) {
			// 首次请求
			needQueryDb = true;
		} else {
			// 上次请求时间
			lastTime = (DateTime) obj;
			
			// 当前时间
			DateTime now = DateTime.now();
			
			// 时间差超过15分钟执行数据库查询，否则不做操作
			if (Minutes.minutesBetween(lastTime, now).getMinutes() > 15) {
				needQueryDb = true;
			}
		}
		
		int unreadnum;
		if (needQueryDb) {
			unreadnum = userHasMsgService.countUnMsg(ChannelContext.getSessionChannel().getUserNumber());
			session.setAttribute(Globals.UNREAD_MSG_LAST_TIME, DateTime.now());
			session.setAttribute(Globals.UNREAD_MSG_NUM, unreadnum);
		} else {
			unreadnum = (int) session.getAttribute(Globals.UNREAD_MSG_NUM);
		}
		
		return unreadnum;
	}
	
	/**
	 * 验证收件人是否合法
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("authReceverUnumber")
	public String authReceverUnumber(HttpServletRequest request) {
		String usernumber = request.getParameter("param");
		// 判断输入内容如果包含（）就截取,反之直接进入下一步
		if (usernumber.length() > 8 && usernumber.indexOf("(") != -1) {
			int begin = usernumber.indexOf("(") + 1;
			int end = usernumber.indexOf(")");
			usernumber = usernumber.substring(begin, end);
		}
		boolean success = true;
		String message = "";
		if (usernumber == null || ChannelContext.getSessionChannel().getUserNumber().equals(usernumber)) {
			success = false;
			message = "收件账户不能为自己账户！";
		}
		boolean exist = userService.authExistUserByUserNumber(usernumber);
		if (!exist) {
			success = false;
			message = "收件账户不存在！";
		}
		Map<String, String> ret = new HashMap<>();
		ret.put("info", message);
		ret.put("status", success ? "y" : "n");
		JSONObject obj = JSONObject.fromObject(ret);
		return obj.toString();
	}
	
}
