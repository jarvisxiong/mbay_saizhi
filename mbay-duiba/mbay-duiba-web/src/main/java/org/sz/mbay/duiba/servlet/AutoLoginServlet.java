package org.sz.mbay.duiba.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.duiba.CreditTool;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.config.DuibaConfig;
import org.sz.mbay.duiba.http.HttpSupport;
import org.sz.mbay.duiba.service.DuiBaMallService;

/**
 * 将兑吧积分商城嵌入App,自动登录地址生成
 * 
 * @author frank.zong
 * 
 */
public class AutoLoginServlet extends HttpServlet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoLoginServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	public AutoLoginServlet() {
		super();
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String mobile_encode = request.getParameter("mobile");
		String mallId = request.getParameter("mallId");
		if (StringUtils.isEmpty(mobile_encode)) {
			LOGGER.error("请求地址出错,原因:mobile加密参数为空！");
			out.println("请求地址出错,请确认后再重试！");
			return;
		}
		String mobile = DigestUtils.pbeDecrypt(mobile_encode);
		if(StringUtils.isEmpty(mobile)){
			LOGGER.error("请求地址出错,原因:mobile参数解密出错");
			out.println("请求地址出错,请确认后再重试！");
			return;
		}
		if (StringUtils.isEmpty(mallId)) {
			LOGGER.error("请求地址出错,原因:mallId参数为空！");
			out.println("请求地址出错,请确认后再重试！");
			return;
		}
		
		//根据mallId查询相应的兑吧应用,获取appkey跟appsecret
		int id = Integer.valueOf(mallId);
		DuiBaMallService service = (DuiBaMallService) SpringApplicationContext.getBean(DuiBaMallService.class.getSimpleName());
		DuiBaMall mall = service.findOne(id);
		if(mall == null){
			LOGGER.error("获取商城信息出错,原因:商城为空,可能是后台onembay没有维护相关信息");
			out.println("获取商城信息出错,请确认后再重试！");
			return;
		}
		if(EnableState.DISENABLE.equals(mall.getStatus())){
			LOGGER.error("获取商城信息出错,原因:当前商城已禁用");
			out.println("获取商城信息出错,请确认后再重试！");
			return;
		}
		
		CreditTool tool = new CreditTool(mall.getAppkey(), mall.getAppsecret());
		
		Map<String, String> params = new HashMap<String, String>();
		String credits = "0";
		try {
			String value = HttpSupport.httpGet(DuibaConfig.getAccount() + mobile);
			LOGGER.info("调用getAccount接口返回值:" + value);
			JSONObject json = JSONObject.fromObject(value);
			//状态
			Boolean status = json.getBoolean("status");
			//具体数据
			if (status) {
				//剩余积分
				double d = json.getDouble("data");
				long l = Math.round(d);
				credits = String.valueOf(l);
			} else {
				LOGGER.error("获取对应的账户信息出错,原因:返回的status->false");
				throw new Exception();
			}
		} catch (Exception e) {
			out.println("获取对应的账户信息出错,请确认后再重试！");
			return;
		}
		// uid由'手机号'+'-'+'商城id'组成
		params.put("uid", mobile + "-" + mallId);
		params.put("credits", credits);
		String url = tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?", params);
		// 此url即为免登录url
		response.sendRedirect(url);
	}
	
	public void init(ServletConfig config) throws ServletException {
		
	}
}
