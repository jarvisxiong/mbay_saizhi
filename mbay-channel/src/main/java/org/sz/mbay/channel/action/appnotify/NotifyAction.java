package org.sz.mbay.channel.action.appnotify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.channel.context.EmailTemplateSupport;
import org.sz.mbay.channel.enums.EmailTemplateType;

/**
 * 
 * @ClassName: NotifyAction
 * 
 * @Description: 主要是处理onembay到该系统的系统通知
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月1日 上午10:03:39
 * 
 */
@RequestMapping("notify")
@Controller
public class NotifyAction {
	
	/**
	 * 处理邮件模版跟新 通知
	 * 
	 * @param type
	 *            邮件模版类型
	 * @return 返回执行结果
	 */
	@RequestMapping(value = "update-emailTemplate", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateMailtemplate(EmailTemplateType type) {
		return EmailTemplateSupport.resetEmailTemplateByType(type);
	}
}
