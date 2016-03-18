package org.sz.mbay.common.interfaces.def;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.interfaces.Interface;
import org.sz.mbay.common.interfaces.ParamsPreprocessException;

/**
 * 接口信息简单实现
 * 
 * 从配置文件加载
 * 
 * 1.可以在配置文件中使用server=http://xx.xx:8080/project...的形式定义统一服务器路径，
 * 接口定义形如如api=xx.xx.json，加载配置时会将server值合并到接口前面，即url=server + api，
 * 
 * 2.如果不定义server属性，则接口需定义完整格式，如api=http://xx.xx:8080/xx/xx.json，
 * 即url=api
 * 
 * PS：如果定义了server，接口不能定义完整格式，否则请求将出错
 * 
 * @author jerry
 */
public class DefaultInterface extends Interface implements Cloneable {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultInterface.class);
			
	private static final Pattern HEADER_PARAM_INDEX = Pattern
			.compile("^\\{\\s*(?<index>\\d)\\s*\\}$");
			
	@SuppressWarnings("unchecked")
	@Override
	public Interface preproccess(Object paramsList)
			throws ParamsPreprocessException {
		// 1.url参数填补
		// 2.请求头参数填补
		try {
			DefaultInterface data = (DefaultInterface) clone();
			
			if (paramsList instanceof Object[]
					&& ((Object[]) paramsList).length == 1 &&
					((Object[]) paramsList)[0] instanceof Map) {
				paramsList = ((Object[]) paramsList)[0];
			}
			
			if (paramsList instanceof Object[]) {
				final Object[] pms = (Object[]) paramsList;
				for (int i = 0; i < pms.length; i++) {
					data.setUrl(data.url.replaceAll(
							getReplaceReg(String.valueOf(i)),
							String.valueOf(pms[i])));
				}
				
				data.headers.forEach((name, value) -> {
					data.headers.put(name, String.valueOf(
							pms[Integer.parseInt(getHeaderParamIndex(value))]));
				});
			} else if (paramsList instanceof Map) {
				final Map<String, String> pms = (Map<String, String>) paramsList;
				pms.forEach((key, value) -> {
					data.setUrl(data.url.replaceAll(getReplaceReg(key), value));
				});
				
				data.headers.forEach((name, value) -> {
					data.headers.put(name, String.valueOf(
							pms.get(getHeaderParamIndex(value))));
				});
			} else {
				final String pm = (String) paramsList;
				data.setUrl(data.url.replaceAll(getReplaceReg("0"), pm));
				
				data.headers.forEach((name, value) -> {
					if ("0".equals(getHeaderParamIndex(value))) {
						data.headers.put(name, pm);
					}
				});
			}
			
			return data;
		} catch (Exception e) {
			LOGGER.error("params preproccess error:{}", e.getMessage());
			throw new ParamsPreprocessException(e.getMessage());
		}
	}
	
	private String getReplaceReg(String index) {
		return "\\{[^\\{\\}]*" + index + "[^\\{\\}]*\\}";
	}
	
	private String getHeaderParamIndex(String value) {
		Matcher mc = HEADER_PARAM_INDEX.matcher(value);
		assert mc.matches();
		return mc.group("index");
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		final DefaultInterface i = (DefaultInterface) super.clone();
		i.headers = new HashMap<>();
		super.headers.forEach((name, value) -> {
			i.headers.put(name, value);
		});
		return i;
	}
}
