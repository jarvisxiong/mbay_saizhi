package org.sz.mbay.fs.fastdfs.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSService;

/**
 * fastdfs分布式文件系统附加ip地址
 * 
 * @author jerry
 */
public class FDFSTag extends TagSupport {
	
	private static final long serialVersionUID = -2011674371912656185L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FDFSTag.class);
	
	private static final FSClient fsClient = FSClientFactory
			.getClient(ClientType.FDFS);
			
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value.trim();
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.write(((FDFSService) fsClient.getFsSerice())
					.getFullPath(value));
		} catch (Exception e) {
			LOGGER.error("fastdfs标签输出异常");
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
