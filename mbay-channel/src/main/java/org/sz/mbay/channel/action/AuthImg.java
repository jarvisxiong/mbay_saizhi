package org.sz.mbay.channel.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.patchca.service.CaptchaService;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ONECITY 验证码图片生成,验证码正确性验证
 */
@Controller
@RequestMapping("authcode")
public class AuthImg {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthImg.class);

	public static void removeAuthcode(HttpSession session) {
		session.removeAttribute("authcode");
		
		
	}

	/**
	 * 产生一个随机的数字组成的验证图片
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("authImg")
	public void authImg(HttpServletRequest request, HttpServletResponse response) {
		CaptchaService cs = new ConfigurableCaptchaService();
		try {
			String patchca = EncoderHelper.getChallangeAndWriteImage(cs, "png",
					response.getOutputStream());
			request.getSession().setAttribute("authcode", patchca);
		} catch (IOException e) {
			LOGGER.error("生成随机验证码异常：", e.fillInStackTrace());
		}

	}

	/**
	 * 验证验证码是否正确
	 * 此验证绑定了validateform的ajax验证要求。返回Y。
	 * @param authcode
	 * @param request
	 * @return
	 */
	@RequestMapping("valAuthCode")
	@ResponseBody
	public String validateAuthCode(HttpServletRequest request) {
		String authcode = request.getParameter("param");
		if (authcode.equals(request.getSession().getAttribute("authcode"))) {
			return "y";
		}
		return "验证码不正确!";
	}

}
