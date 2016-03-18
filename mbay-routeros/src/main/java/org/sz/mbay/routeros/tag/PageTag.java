package org.sz.mbay.routeros.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.routeros.entity.PageInfo;

/**
 * @author ONECITY
 * 
 */
@SuppressWarnings("serial")
public class PageTag extends TagSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageTag.class);

	private PageInfo pageinfo;

	private String formid;

	public PageInfo getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	@Override
	public int doStartTag() {
		if (pageinfo == null) {
			Object objct = pageContext.getRequest().getAttribute("pageinfo");
			try {
				pageinfo = (PageInfo) objct;
			} catch (Exception e) {

			}
		}
		if (pageinfo != null && pageinfo.getTotalpage() > 0) {
			JspWriter out = this.pageContext.getOut();
			StringBuilder sb = this.getPageHTML();
			sb.append(getPageScipt());
			try {
				out.write(sb.toString());
			} catch (IOException e) {
				LOGGER.error("分页输出异常", e.fillInStackTrace());
			}
		}

		return SKIP_BODY;
	}

	private StringBuilder getPageHTML() {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("<div class=\"meneame\">");
		sb.append("<div class=\"inner\">");
		if (pageinfo.getPagenum() == 1) {// 第一页 首页和 < 都不可点击
			sb.append("<span class=\"disabled\">首页</span><span class=\"disabled\">&lt;</span>");
		} else {
			sb.append("<a href=\"javascript:searchPageSumit(1);\">首页</a>");
			sb.append("<a href=\"javascript:searchPageSumit("
					+ (pageinfo.getPagenum() - 1) + ");\">&lt;</a>");
		}
		int pagestrat = pageinfo.getPagenum() > 5 ? (pageinfo.getPagenum() - 5)
				: 1;
		int pageend = (pagestrat + 9) > pageinfo.getTotalpage() ? pageinfo
				.getTotalpage() : (pagestrat + 9);
		// /(pageinfo.getPagenum() + 4) > pageinfo.getTotalpage() ? pageinfo
		// /.getTotalpage() : pageinfo.getPagenum() + 4;
		if (pageend - pagestrat != 9) {
			pagestrat = (pageend - 9 <= 0) ? 1 : (pageend - 9);
		}
		for (; pagestrat <= pageend; pagestrat++) {
			if (pagestrat == pageinfo.getPagenum()) {
				sb.append("<span class=\"current\">" + pagestrat + "</span>");
			} else {
				sb.append("<a href=\"javascript:searchPageSumit(" + pagestrat
						+ ");\">" + pagestrat + "</a>");
			}
		}
		if (pageinfo.getPagenum() == pageinfo.getTotalpage()) {
			sb.append("<span class=\"disabled\">&gt;</span>");
			sb.append("<span class=\"disabled\">尾页</span>");
		} else {
			sb.append("<a href=\"javascript:searchPageSumit("
					+ (pageinfo.getPagenum() + 1) + ");\">&gt;</a>");
			sb.append("<a href=\"javascript:searchPageSumit("
					+ pageinfo.getTotalpage() + ");\">尾页</a>");
		}
		// /sb.append(" <input type='text' class='zd'/><span>页</span><input type='button'class='qr' value='确认'/>");
		sb.append("<span class=\"disabled\">共" + pageinfo.getTotalpage()
				+ "页</span>");
		sb.append("<span class=\"disabled\">共" + pageinfo.getTotalrow()
				+ "条</span>");
		sb.append("</div></div>");
		return sb;
	}

	private StringBuilder getPageScipt() {
		StringBuilder sb = new StringBuilder(512);
		sb.append("\n<script type=\"text/javascript\">\nfunction searchPageSumit(pnum){\n");
		if (formid == null || formid.length() == 0) {
			sb.append("var subform=document.forms[0];\n");
		} else {
			sb.append("var subform=$(\"#" + formid + "\");\n");
		}
		sb.append("var action=$(subform).attr(\"action\");\n");
		sb.append("var orgaction=action;\n");
		sb.append("var index=action.indexOf(\"?\");\n");
		sb.append("if(index>0){\n");
		sb.append("action=action+\"&pagenum=\"+pnum+\"&totalrow="
				+ pageinfo.getTotalrow() + "\"");
		sb.append("\n}else{\n");
		sb.append("action=action+\"?pagenum=\"+pnum+\"&totalrow="
				+ pageinfo.getTotalrow() + "\"");
		sb.append("\n}\n");
		sb.append("$(subform).attr(\"action\",action);\n");
		sb.append("$(subform).submit();\n");

		sb.append("$(subform).attr(\"action\",orgaction);\n");

		sb.append("}</script>");
		return sb;
	}

	@Override
	public int doEndTag() {
		return EVAL_PAGE;
	}

}
