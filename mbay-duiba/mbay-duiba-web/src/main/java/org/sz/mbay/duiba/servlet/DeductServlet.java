package org.sz.mbay.duiba.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.duiba.CreditConsumeParams;
import org.sz.mbay.duiba.CreditConsumeResult;
import org.sz.mbay.duiba.CreditTool;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.config.DuibaConfig;
import org.sz.mbay.duiba.http.HttpSupport;
import org.sz.mbay.duiba.service.DuiBaMallService;

/**
 * 用户积分扣除接口
 * 
 * @author frank.zong
 * 
 */
public class DeductServlet extends HttpServlet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeductServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	public DeductServlet() {
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
		
		// uid由'手机号'+'-'+'商城id'组成
		String request_uid = request.getParameter("uid");
		String request_mallId = request_uid.split("-")[1];
		int id = Integer.valueOf(request_mallId);
		//根据商城id获取appkey跟appsecret
		DuiBaMallService service = (DuiBaMallService) SpringApplicationContext.getBean(DuiBaMallService.class.getSimpleName());
		DuiBaMall mall = service.findOne(id);
		
		CreditTool tool = new CreditTool(mall.getAppkey(), mall.getAppsecret());
		
		try {
			LOGGER.info("deduct request -> " + request.getQueryString());
			CreditConsumeParams params = tool.parseCreditConsume(request);// 利用tool来解析这个请求
			String uid = params.getUid();// uid由'手机号'+'-'+'商城id'组成
			String mobile = uid.split("-")[0];
			String mallId = uid.split("-")[1];
			Long credits = params.getCredits();// 消耗积分数
			String orderNum = params.getOrderNum();// 兑吧订单号
			LOGGER.info("兑吧订单号:" + orderNum);
			// boolean waitAudit = params.isWaitAudit();//是否需要审核
			// 订单需要审核
			/*
			 * if(waitAudit){
			 * Audit.audit(orderNum);
			 * }
			 */
			String url = DuibaConfig.reduce();
			url = url.replace("MOBILE", mobile);
			url = url.replace("AMOUNT", String.valueOf(credits));
			url = url.replace("NUMBER", orderNum);
			url = url.replace("RELATED", mallId);
			String value = HttpSupport.httpGet(url);
			LOGGER.info("调用reduce接口返回值:" + value);
			JSONObject json = JSONObject.fromObject(value);
			//状态
			Boolean status = json.getBoolean("status");
			CreditConsumeResult result = null;
			if (status) {
				//具体数据
				String data = json.getString("data");
				JSONObject data_json = JSONObject.fromObject(data);
				result = new CreditConsumeResult(true);
				//流水号,由'美贝订单号'+'-'+'商城id'组成
				result.setBizId(data_json.getString("snumber") + "-" + mallId);
				LOGGER.info("美贝订单号:" +  data_json.getString("snumber"));
				//剩余积分
				result.setCredits(Long.parseLong(data_json.getString("amount")));
			} else {
				//扣除积分失败，向兑吧传输错误信息
				result = new CreditConsumeResult(false);
				String user_value = HttpSupport.httpGet(DuibaConfig.getAccount() + mobile);
				LOGGER.info("调用getAccount接口返回值:" + user_value);
				JSONObject user_json = JSONObject.fromObject(user_value);
				//状态
				Boolean user_status = user_json.getBoolean("status");
				if(user_status){
					double d = user_json.getDouble("data");
					long l = Math.round(d);
					result.setCredits(l);
				}else{
					LOGGER.error("获取用户剩余积分出错,原因:返回staus->false");
					throw new Exception("获取用户剩余积分出错");
				}
				result.setErrorMessage("扣除积分失败");
			}
			LOGGER.info("积分消耗请求成功,输出结果:" + result.toString());
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			LOGGER.error("积分消耗请求出错,原因:" + e.getMessage());
		}
	}
	
	public void init(ServletConfig config) throws ServletException {
		
	}
}
