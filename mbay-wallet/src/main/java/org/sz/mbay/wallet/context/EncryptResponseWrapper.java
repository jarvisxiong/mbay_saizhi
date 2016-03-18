package org.sz.mbay.wallet.context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 请求返回时加密数据
 * 
 * @author jerry
 */
public class EncryptResponseWrapper extends HttpServletResponseWrapper {
	
	private PrintWriter printWriter;
	private ByteArrayOutputStream byteStream;
	private ServletOutputStream servletStream;
	private HttpServletResponse response;
	
	public EncryptResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		this.response = response;
		
		byteStream = new ByteArrayOutputStream();
		servletStream = new WapperedOutputStream(byteStream);
		printWriter = new PrintWriter(new OutputStreamWriter(byteStream, this.getCharacterEncoding()));
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		return printWriter;
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return servletStream;
	}
	
	@Override
	public void flushBuffer() throws IOException {
		if (servletStream != null) {
			servletStream.flush();
		}
		if (printWriter != null) {
			printWriter.flush();
		}
	}
	
	@Override
	public void reset() {
		byteStream.reset();
	}
	
	/**
	 * 获取流中数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getData() throws IOException {
		flushBuffer();
		return byteStream.toByteArray();
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 内部类，对ServletOutputStream进行包装
	 * 
	 * @author jerry
	 */
	private class WapperedOutputStream extends ServletOutputStream {
		
		private ByteArrayOutputStream bos = null;
		
		public WapperedOutputStream(ByteArrayOutputStream stream) {
			bos = stream;
		}
		
		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}
		
		@Override
		public boolean isReady() {
			return false;
		}
		
		@Override
		public void setWriteListener(WriteListener arg0) {
		}
	}
}
