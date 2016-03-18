package org.sz.mbay.channel.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.LiuMiRecord;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.LiuMiService;

/**
 * @Description: 流米查询记录和设置可用余额
 * @author frank.zong
 * @date 2015-2-13 下午13:37:51
 * 
 */
@Controller
@RequestMapping("liumi")
public class LiuMiAction extends BaseAction {
	
	/**
	 * 列表
	 */
	public static final String LIST = "liumi/list";
	
	/**
	 * 重定向到列表
	 */
	public static final String REDIRECT_LIST = "redirect:/liumi/list.mbay";
	
	@Autowired
	LiuMiService service;
	
	/**
	 * 列表
	 */
	@RequestMapping("list")
	public String list(Model model, PageInfo pageinfo, HttpServletRequest request) {
		List<LiuMiRecord> list = this.service.findList(pageinfo);
		model.addAttribute("list", list);
		model.addAttribute("price", service.findLiuMiTotalPrice());
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return LIST;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("setTotalPrice")
	public String setTotalPrice(HttpServletRequest request, RedirectAttributes redAttr) {
		String price = request.getParameter("price");
		service.updateTotalPrice(Double.parseDouble(price));
		return REDIRECT_LIST;
	}
}
