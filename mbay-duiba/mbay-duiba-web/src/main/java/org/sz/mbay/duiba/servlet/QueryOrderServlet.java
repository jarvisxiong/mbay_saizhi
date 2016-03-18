package org.sz.mbay.duiba.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sz.mbay.duiba.CreditTool;
import org.sz.mbay.duiba.config.DuibaConfig;

/**
 * 兑吧订单查询接口
 * @author frank.zong
 *
 */
public class QueryOrderServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	
	public QueryOrderServlet() {
		super();
	}
	
	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");  
		response.setHeader("content-type","text/html;charset=utf-8");
		
		CreditTool tool=new CreditTool(DuibaConfig.getAppKey(), DuibaConfig.getAppSecret());
		
		//兑吧订单号
		String orderNum = "2015052014260155600795895";
		//美贝订单号
		String bizId = "201505201426100000600839154";
		String urlstr=tool.buildCreditOrderStatusRequest(orderNum,bizId);

		//将此url请求发出，并解析响应的json字符串，获取订单状态
		//具体输入输出参数见HTTP接口说明
		try {
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String lines;
            while ((lines = reader.readLine()) != null){
                System.out.println(lines);
            }
            reader.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
  
	public void init(ServletConfig config) throws ServletException {
		
	}
}