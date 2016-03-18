package org.sz.mbay.common.interfaces.def;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.sz.mbay.common.util.XmlUtil;

/**
 * 状态码及其描述
 * 
 * @author jerry
 */
public class ResultCode {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ResultCode.class);
			
	// 状态码缓存
	private static final Map<String, Code> MAP = new ConcurrentHashMap<>();
	
	// 默认文件名称
	private static final String BASE_NAME = "result-codes.xml";
	
	/** 唯一成功状态码 */
	public static Code SUCCESS_CODE;
	
	static {
		ResourcePatternResolver ros = new PathMatchingResourcePatternResolver();
		Resource[] rsc = null;
		try {
			rsc = ros.getResources(
					ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ "**/*" + BASE_NAME);
							
			// 调整资源顺序，保证默认文件result-codes.xml第一个被处理
			// 防止后处理时默认文件覆盖用户定义的配置
			for (int i = 0; i < rsc.length; i++) {
				if (BASE_NAME.equals(rsc[i].getFilename()) && i != 0) {
					Resource tmp = rsc[i];
					rsc[i] = rsc[0];
					rsc[0] = tmp;
					break;
				}
			}
			
			SAXBuilder sb = new SAXBuilder();
			for (Resource rc : rsc) {
				LOGGER.info("try load file:{}", rc.getURL().getPath());
				try {
					Document doc = sb.build(rc.getInputStream());
					parseXml(doc);
				} catch (Exception e) {
					LOGGER.error("init result-codes error:{}", e.getMessage());
				}
			}
		} catch (IOException e) {
			LOGGER.error("load result-codes.xml files error:{}",
					e.getMessage());
		}
	}
	
	private ResultCode() {
	}
	
	/**
	 * 获取错误描述信息
	 * 
	 * @param code
	 * @return
	 */
	public static String getDesc(String code) {
		return getCode(code).getDesc();
	}
	
	/**
	 * 获取错误码对象
	 * 
	 * @param code
	 * @return
	 */
	public static Code getCode(String code) {
		Code c = MAP.get(code);
		return c == null ? new Code() : c;
	}
	
	/**
	 * xml解析
	 * 
	 * @param doc
	 */
	@SuppressWarnings("unchecked")
	private static void parseXml(Document doc) {
		if (doc == null || !doc.hasRootElement()) {
			return;
		}
		
		Element root = doc.getRootElement();
		if (!"result-codes".equals(root.getName())) {
			LOGGER.warn("parse result-codes.xml error:"
					+ "root element is not result-codes");
			return;
		}
		
		// codes
		List<Element> codes = root.getChildren("code");
		String value = null;
		String desc = null;
		boolean success = false;
		for (Element code : codes) {
			value = code.getChildTextTrim("value");
			if (StringUtils.isEmpty(value)) {
				continue;
			}
			
			desc = code.getChildTextTrim("desc");
			success = Boolean.parseBoolean(XmlUtil.getChildTextTrim(
					code, "success", "false"));
					
			Code c = new Code();
			c.setCode(value);
			c.setDesc(desc);
			c.setSuccess(success);
			
			// 缓存唯一成功状态码
			if (success) {
				SUCCESS_CODE = c;
			}
			
			MAP.put(value, c);
		}
	}
	
	/**
	 * 状态码数据接口
	 */
	public static class Code {
		
		// 状态码
		private String code;
		
		// 描述
		private String desc;
		
		// 码对应的状态是否是成功的
		private boolean success;
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public boolean isSuccess() {
			return success;
		}
		
		public void setSuccess(boolean success) {
			this.success = success;
		}
	}
}
