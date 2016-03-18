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
import org.springframework.util.StringUtils;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.duiba.CreditNotifyParams;
import org.sz.mbay.duiba.CreditTool;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.config.DuibaConfig;
import org.sz.mbay.duiba.http.HttpSupport;
import org.sz.mbay.duiba.service.DuiBaMallService;

/**
 * 兑换成功/失败消息的接收接口
 * 
 * @author frank.zong
 * 
 */
public class NotifyServlet extends HttpServlet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotifyServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	public NotifyServlet() {
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
		
		//流水号,由'美贝订单号'+'-'+'商城id'组成
		String request_bizId = request.getParameter("bizId");
		if(StringUtils.isEmpty(request_bizId)){
			LOGGER.error("兑换消息接收出错,原因:扣除积分时出错,没有生成对应的美贝订单号");
			response.getWriter().write("ok");
			return;
		}
		String request_mallId = request_bizId.split("-")[1];
		int id = Integer.valueOf(request_mallId);
		//根据商城id获取appkey跟appsecret
		DuiBaMallService service = (DuiBaMallService) SpringApplicationContext.getBean(DuiBaMallService.class.getSimpleName());
		DuiBaMall mall = service.findOne(id);
		
		CreditTool tool = new CreditTool(mall.getAppkey(), mall.getAppsecret());
		
		try {
			LOGGER.info("notify request -> " + request.getQueryString());
			CreditNotifyParams params = tool.parseCreditNotify(request);// 利用tool来解析这个请求
			String orderNum = params.getOrderNum();
			// 处理重复通知
			String state_url = DuibaConfig.getState() + orderNum;
			String state_str = HttpSupport.httpGet(state_url);
			LOGGER.info("调用getState接口返回值:" + state_str);
			JSONObject state_json = JSONObject.fromObject(state_str);
			Boolean state_status = state_json.getBoolean("status");
			if (state_status) {
				response.getWriter().write("ok");
				return;
			}
			if (!params.isSuccess()) {
				// 兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
				String url = DuibaConfig.rollback() + orderNum;
				String value = HttpSupport.httpGet(url);
				LOGGER.info("调用rollback接口返回值:" + value);
				JSONObject json = JSONObject.fromObject(value);
				// 状态
				Boolean status = json.getBoolean("status");
				if (!status) {
					LOGGER.error("回滚积分操作失败orderNum->" + orderNum);
				}
			}
			// 更改状态为已操作
			String update_url = DuibaConfig.updateState();
			update_url = update_url.replace("ORDERNUMBER", orderNum);
			update_url = update_url.replace("STATE", "1");
			String update_value = HttpSupport.httpGet(update_url);
			LOGGER.info("调用updateState接口返回值:" + update_value);
			JSONObject update_json = JSONObject.fromObject(update_value);
			boolean update_status = update_json.getBoolean("status");
			if (!update_status) {
				LOGGER.error("更改状态为已操作失败orderNumber:" + orderNum);
			}
			LOGGER.info("兑换消息接收成功,orderNumber:" + orderNum);
			response.getWriter().write("ok");
		} catch (Exception e) {
			LOGGER.error("兑换消息接收出错,原因:" + e.getMessage() + ",美贝订单号:" + request_bizId.split("-")[0]);
			response.getWriter().write("ok");
		}
	}
	
	public void init(ServletConfig config) throws ServletException {
		
	}
}
