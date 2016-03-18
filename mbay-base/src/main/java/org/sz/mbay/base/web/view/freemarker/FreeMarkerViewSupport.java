package org.sz.mbay.base.web.view.freemarker;

import java.io.StringWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.sz.mbay.base.context.SpringApplicationContext;
import freemarker.template.Configuration;
import freemarker.template.Template;

/** 
* @Description: 
* @author han.han 
* @date 2015-6-12 上午9:05:58 
*  
*/
public class FreeMarkerViewSupport {
	
	private static Logger logger = LoggerFactory.getLogger(FreeMarkerViewSupport.class);

	public static String getTemplate(String name, Model model) {
		FreeMarkerConfig freeMarkerConfig = SpringApplicationContext.getBean(FreeMarkerConfig.class);
		Configuration configuration = freeMarkerConfig.getConfiguration();
		try {
			Template template = configuration.getTemplate(name);
			StringWriter consoleWriter = new StringWriter();
			Map<String, Object> dataModel = model.asMap();
			template.process(dataModel, consoleWriter);
			return consoleWriter.getBuffer().toString();
		} catch (Exception e) {
			logger.error("FreeMarkerViewSupport getTemplate Error", e.fillInStackTrace());
			return null;
		} 
	}
}
