package org.sz.mbay.wallet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.wallet.context.DecryptRequestWrapper;
import org.sz.mbay.wallet.context.EncryptResponseWrapper;

/**
 * 数据加密解密过滤器
 * 
 * @author jerry
 */
public class DataEnAndDecryptFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		EncryptResponseWrapper resp = new EncryptResponseWrapper((HttpServletResponse) response);
		
		chain.doFilter(new DecryptRequestWrapper((HttpServletRequest) request), resp); 
		
		// 加密数据流
		byte[] result = resp.getData(); 
		String data = new String(result, response.getCharacterEncoding());
		PrintWriter writer = response.getWriter();
		writer.write(DigestUtils.pbeEncrypt(data));
		writer.flush();
		writer.close();
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
}
