package org.sz.mbay.wallet.context;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.sz.mbay.common.util.DigestUtils;


/**
 * 请求进入时解密参数
 * 
 * @author jerry
 */
public class DecryptRequestWrapper extends HttpServletRequestWrapper {
	
	private Map<String, String[]> paramMap = new HashMap<String, String[]>();
	private HttpServletRequest request;
	
	public DecryptRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
		this.paramMap = request.getParameterMap();
		
		// 参数值解密
		String[] vals = null;
		String[] temp = null;
		for (Entry<String, String[]> entry : paramMap.entrySet()) {
			vals = entry.getValue();
			temp = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				temp[i] = DigestUtils.des3Decrypt(vals[i]);
			}
			entry.setValue(temp);
		}
	}
	
	@Override
	public String getParameter(String name) {
		String result = null;
		Object v = paramMap.get(name);
		
		if (v == null) {
			result = null;
		} else if (v instanceof String[]) {
			String[] strArr = (String[]) v;
			if (strArr.length > 0) {
				result = strArr[0];
			} else {
				result = null;
			}
		} else if (v instanceof String) {
			result = (String) v;
		} else {
			result = v.toString();
		}
		
		return result;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		return paramMap;
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		return new Vector<String>(paramMap.keySet()).elements();
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String[] result = null;
		Object v = paramMap.get(name);
		
		if (v == null) {
			result = null;
		} else if (v instanceof String[]) {
			result = (String[]) v;
		} else if (v instanceof String) {
			result = new String[] { (String) v };
		} else {
			result = new String[] { v.toString() };
		}
		
		return result;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
