package org.sz.mbay.base.web.servlet.tags.token;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.constant.Globals;

/**
 * @author ONECITY Token标签
 * 
 */
@SuppressWarnings("serial")
public class TokenTag extends TagSupport {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TokenTag.class);
	
	@Override
	public int doStartTag() {
		JspWriter out = this.pageContext.getOut();
		HttpSession session = pageContext.getSession();
		String tokenhtml = "<input type=\"hidden\" name=\"" + Globals.TOKEN_KEY
				+ "\" value=\""
				+ session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)
				+ "\"  />";
		try {
			out.write(tokenhtml);
		} catch (IOException e) {
			LOGGER.error("Token标签输出异常", e.fillInStackTrace());
		}
		
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() {
		return EVAL_PAGE;
	}
	
}
