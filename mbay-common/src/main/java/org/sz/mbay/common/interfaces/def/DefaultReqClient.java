package org.sz.mbay.common.interfaces.def;

import java.io.IOException;
import java.util.HashMap;
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
import org.sz.mbay.common.interfaces.Interface;
import org.sz.mbay.common.interfaces.ParamsPreprocessException;
import org.sz.mbay.common.interfaces.ProcessResultException;
import org.sz.mbay.common.interfaces.ReqClient;
import org.sz.mbay.common.interfaces.Response;
import org.sz.mbay.common.util.HttpException;
import org.sz.mbay.common.util.HttpSupport.ReqMethod;
import org.sz.mbay.common.util.XmlUtil;

/**
 * 默认请求上下文
 * 
 * 从配置文件读取信息
 * 
 * @author jerry
 */
public class DefaultReqClient extends ReqClient {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultReqClient.class);
			
	// 实例缓存，格式：module=DefaultReqClient
	private static final Map<String, DefaultReqClient> CLAZZ_MAP = new ConcurrentHashMap<>();
	
	// 当前对象对应的模块名称
	private String module;
	
	// 默认文件名称
	private static final String BASE_NAME = "interfaces.xml";
	
	// 接口缓存，格式：module|name=interface
	private static final Map<String, Interface> IFE_MAP = new ConcurrentHashMap<>();
	
	static {
		ResourcePatternResolver ros = new PathMatchingResourcePatternResolver();
		Resource[] rsc = null;
		try {
			rsc = ros.getResources(
					ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ "**/*" + BASE_NAME);
							
			// 调整资源顺序，保证默认文件interfaces.xml第一个被处理
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
	
	private DefaultReqClient(String module) {
		this.module = module;
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
		if (!"interfaces".equals(root.getName())) {
			LOGGER.warn("parse interfaces.xml error:"
					+ "root element is not interfaces");
			return;
		}
		
		// 全局属性module
		String gModule = root.getChildTextTrim("module");
		if (StringUtils.isEmpty(gModule)) {
			LOGGER.warn("parse interfaces.xml error:"
					+ "module attribute is not provided");
			return;
		}
		
		// 全局属性server
		String gServer = XmlUtil.getChildTextTrim(root, "server",
				StringUtils.EMPTY);
				
		// interfaces
		List<Element> interfaces = root.getChildren("interface");
		String name = null;
		String url = null;
		String type = null;
		Map<String, String> headers = null;
		Element header = null;
		for (Element ife : interfaces) {
			name = ife.getChildTextTrim("name");
			if (StringUtils.isEmpty(name)) {
				continue;
			}
			
			url = ife.getChildTextTrim("url");
			type = XmlUtil.getChildTextTrim(ife, "type", "GET");
			
			headers = new HashMap<>(0);
			header = ife.getChild("headers");
			if (header != null) {
				for (Element hd : (List<Element>) header
						.getChildren("header")) {
					headers.put(hd.getChildTextTrim("name"),
							hd.getChildTextTrim("value"));
				}
			}
			
			DefaultInterface df = new DefaultInterface();
			df.setName(name);
			df.setUrl(gServer + url);
			df.setReqMethod(ReqMethod.valueOf(type.toUpperCase()));
			df.setHeaders(headers);
			
			IFE_MAP.put(gModule + "|" + name, df);
		}
	}
	
	/**
	 * 初始化接口配置
	 * 
	 * @param filePackage
	 *            接口、状态码、错误码配置文件所在包名
	 * @return
	 */
	public static DefaultReqClient build(String module) {
		if (StringUtils.isEmpty(module)) {
			return null;
		}
		if (!CLAZZ_MAP.containsKey(module)) {
			synchronized (CLAZZ_MAP) {
				if (!CLAZZ_MAP.containsKey(module)) {
					CLAZZ_MAP.put(module,
							new DefaultReqClient(module));
				}
			}
		}
		return CLAZZ_MAP.get(module);
	}
	
	/**
	 * http请求
	 * 
	 * @param name
	 *            接口名称
	 * @param paramsList
	 *            参数列表
	 * @return
	 */
	public Response connect(String name, Object... paramsList) {
		try {
			return super.connect(name, paramsList);
		} catch (HttpException e) {
			LOGGER.error("http connect error:{}", e.getMessage());
			return DefaultResponse.fromResultCode(e.getStatusCode());
		} catch (IOException e) {
			LOGGER.error("http connect error:{}", e.getMessage());
			return DefaultResponse.fromResultCode("701");
		} catch (ParamsPreprocessException e) {
			LOGGER.error("http connect error:{}", e.getMessage());
			return DefaultResponse.fromResultCode("702");
		} catch (ProcessResultException e) {
			LOGGER.error("http connect error:{}", e.getMessage());
			return DefaultResponse.fromResultCode("703");
		}
	}
	
	public String getModule() {
		return module;
	}
	
	@Override
	public Interface getInterface(String name) {
		return IFE_MAP.get(module + "|" + name);
	}
	
}
