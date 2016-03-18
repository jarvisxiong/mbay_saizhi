package org.sz.mbay.channel.web.servelt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.channel.bean.AppDeployInfo;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.common.util.HttpSupport.ReqMethod;

/**
 * 
 * @ClassName: AppDeployInfoInitServlet
 * 
 * @Description:
 *               当应用启动时候会将配置在web.xml中的有关该应用的部署信息，发送给onembay(
 *               发送本机IP以及端口信息至onembay平台
 *               ，以便onembay通知变更数据)
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年11月27日 下午3:23:21
 * 
 */

@WebServlet("/AppDeployInfoInitServlet")
public class AppDeployInfoInitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String CREATE_ACTION = "deploy_info/create.mbay";
	private static final String DESTORY_ACTION = "deploy_info/delete.mbay";
	private String mainhost;
	private String destoryUrl = null;
	private String createUrl = null;
	private AppDeployInfo deployInfo = null;
	
	private static Logger logger = LoggerFactory
			.getLogger(AppDeployInfoInitServlet.class);
	
	public AppDeployInfoInitServlet() {
		super();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		try {
			// 拼接get请求参数
			String param = "?name=" + this.deployInfo.getName() + "&text="
					+ this.deployInfo.getText() + "&ip="
					+ this.deployInfo.getIp() + "&port="
					+ this.deployInfo.getPort();
			destoryUrl = destoryUrl + param;
			// 发起请求
			int connTimeout = 3000;
			int socketTimeout = 5000;
			String result = HttpSupport.build(destoryUrl, ReqMethod.GET,
					connTimeout, socketTimeout, null).connect();
			System.out.println("result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("子系统向onembay通信，子系统边servlet的destory方法出错了",
					e.getMessage());
		}
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			
			// 从配置文件中获取配置信息
			this.mainhost = config.getInitParameter("mainhost");
			this.createUrl = this.mainhost
					+ AppDeployInfoInitServlet.CREATE_ACTION;
			this.destoryUrl = this.mainhost
					+ AppDeployInfoInitServlet.DESTORY_ACTION;
			// use init value construct object AppDeployInfo
			this.deployInfo = new AppDeployInfo();
			this.deployInfo.setIp(config.getInitParameter("ip"));
			this.deployInfo.setName(config.getInitParameter("name"));
			this.deployInfo.setText(config.getInitParameter("text"));
			this.deployInfo.setPort(Integer.valueOf(config
					.getInitParameter("port")));
			
			// build the param for the url(whose request method is GET)
			String param = "?name=" + this.deployInfo.getName() + "&text="
					+ this.deployInfo.getText() + "&ip="
					+ this.deployInfo.getIp() + "&port="
					+ this.deployInfo.getPort();
			createUrl = createUrl + param;
			/* 下面的对线程的处理是javaSE5/6以后推出来的新方式 */
			ExecutorService executorService = Executors
					.newSingleThreadExecutor();
			executorService.execute(new DeployNotify(createUrl));
			executorService.shutdown();
			
			// new Thread(new DeployNotify(createUrl)).start();//老方法，无法保证线程呗关闭
			
		} catch (Exception e) {
			logger.error("子系统向onembay通信，子系统边servlet的init方法出错了", e.getMessage());
			e.printStackTrace();
		}
	}
	
	static class DeployNotify implements Runnable {
		
		public String url = null;
		int connTimeout = 3000;
		int socketTimeout = 5000;
		
		public DeployNotify(String url) {
			this.url = url;
		}
		
		int times = 0;// 次数
		private int[] time = { 5, 10, 15, 20, 25 };// 间隔时间
		
		@Override
		public void run() {
			try {
				if (times < 5) {
					String result = HttpSupport.build(url,  ReqMethod.GET,connTimeout,
							socketTimeout, null).connect();
					if ("connect refused".equals(result)) {
						// Thread.sleep(time[times] * 1000);//old style
						TimeUnit.SECONDS.sleep(time[times]);// Java SE5/6-style
						times++;
						this.run();
					}
				}
			} catch (Exception e) {
				AppDeployInfoInitServlet.logger.error("DeployNotify",
						e.fillInStackTrace());
			}
		}
	}
	
}
