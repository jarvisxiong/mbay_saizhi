package org.sz.mbay.remote.interfaces.wallet.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.remote.interfaces.wallet.config.Config;

public class RIUtil {
	
	/** 接口代码 */
	protected static final String MODULE_CODE = "moduleCode";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RIUtil.class);
	
	/**
	 * 请求接口
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected static RIResponse connect(String url, Map<String, String> params)
			throws Exception {
		Iterator<String> itor = params.keySet().iterator();
		List<String> strs = new ArrayList<>();
		String key = null;
		while (itor.hasNext()) {
			key = itor.next();
			
			if (StringUtils.isNotEmpty(params.get(key))) {
				// 拼接参数，签名用
				strs.add(key + "=" + params.get(key));
				
				// 加密参数值，请求接口用
				params.put(key, DigestUtils.pbeEncrypt(params.get(key)));
			}
		}
		
		// 排序参数，计算签名
		String[] arr = strs.toArray(new String[strs.size()]);
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		for (String str : arr) {
			sb.append(str).append("&");
		}
		String sig = DigestUtils.md5Encrypt(sb.toString());
		
		// 设置签名
		params.put("signature", sig);
		
		// 移除接口代码
		params.remove(MODULE_CODE);
		
		RIResponse resp = RIResponse.from(HttpSupport.build(
				Config.OUTTER_INTERFACE + url, params).connect());
		if (!resp.isStatus()) {
			LOGGER.error("remote interface error:{}", resp.getErrorMsg());
			throw new Exception(resp.getErrorMsg());
		}
		return resp;
	}
	
}
