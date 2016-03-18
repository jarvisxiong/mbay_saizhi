package org.sz.mbay.common.interfaces;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.HttpException;
import org.sz.mbay.common.util.HttpSupport;

import net.sf.json.JSONObject;

/**
 * 请求上下文，一种类型的接口对应一个该实例
 * 
 * @author jerry
 */
public abstract class ReqClient {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReqClient.class);
			
	/**
	 * 请求
	 * 
	 * @param name
	 *            接口名称
	 * @param paramsList
	 *            参数列表
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws InterfaceNotExistException
	 * @throws ParamsPreprocessException
	 * @throws ProcessResultException
	 */
	protected Response connect(String name, Object paramsList)
			throws HttpException, IOException,
			ParamsPreprocessException, ProcessResultException {
		// 查找原始接口对象
		Interface rfo = getInterface(name);
		assert rfo != null;
		
		// 参数预处理
		rfo = rfo.preproccess(paramsList);
		assert rfo != null;
		
		// 请求接口，获取响应字符串
		String res = null;
		switch (rfo.getReqMethod()) {
			default:
			case GET:
				res = HttpSupport.build(rfo.getUrl())
						.addHeaders(rfo.getHeaders()).connect();
				break;
			case POST:
				// 组织POST参数
				Map<String, String> params = new HashMap<>();
				String url = rfo.getUrl();
				int ci = url.indexOf("?");
				if (ci > 0) {
					String paramStr = url.substring(ci + 1);
					url = url.substring(0, ci - 1);
					String[] arr = paramStr.split("&");
					String[] kv = null;
					for (String val : arr) {
						kv = val.split("=");
						params.put(kv[0],
								kv.length > 1 ? kv[1] : StringUtils.EMPTY);
					}
				}
				res = HttpSupport.build(url, params)
						.addHeaders(rfo.getHeaders()).connect();
		}
		
		// 返回处理后的数据结构
		LOGGER.info("request【{}】:{}", name, res);
		
		// 返回文本转对象
		try {
			return (Response) JSONObject.toBean(JSONObject.fromObject(res),
					Response.class);
		} catch (Exception e) {
			LOGGER.error("process result error:{}", e.getMessage());
			throw new ProcessResultException(e.getMessage());
		}
	}
	
	/**
	 * 根据标识找到接口对象
	 * 
	 * @param name
	 * @return
	 */
	public abstract Interface getInterface(String name);
}
