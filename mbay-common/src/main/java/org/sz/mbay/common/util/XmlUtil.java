package org.sz.mbay.common.util;

import org.apache.commons.lang.StringUtils;
import org.jdom.Element;

/**
 * xml工具
 * 
 * @author jerry
 */
public class XmlUtil {
	
	/**
	 * 获取元素文本
	 * 
	 * @param ele
	 * @param defTxt
	 *            默认值
	 * @return
	 */
	public static String getChildTextTrim(Element ele, String name,
			String defTxt) {
		String txt = ele.getChildTextTrim(name);
		return StringUtils.isEmpty(txt) ? defTxt : txt;
	}
}
