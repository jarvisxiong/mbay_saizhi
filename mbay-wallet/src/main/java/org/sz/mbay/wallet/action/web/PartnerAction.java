package org.sz.mbay.wallet.action.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.wallet.context.WalletConfig;
import org.sz.mbay.wallet.context.WalletContext;

/**
 * 合作方
 * 
 * @author jerry
 */
@Controller
@RequestMapping("web/partner")
public class PartnerAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PartnerAction.class);
			
	/**
	 * 游戏接口
	 * 
	 * @return
	 */
	@RequestMapping("izhangxin")
	public String izhangxin(Model model, HttpServletResponse response) {
		String mobile = WalletContext.getSessionUser().getMobile();
		String code = DigestUtils.pbeEncrypt(mobile);
		
		String url = WalletConfig.IZHANGXIN.replace("{mb_uid}", mobile)
				.replace("{mb_ucode}", code);
				
		try {
			response.sendRedirect(url);
			return null;
		} catch (IOException e) {
			LOGGER.error("redirect to izhangxin error:{}", e.getMessage());
			model.addAttribute("errorMsg", e.getMessage());
			return "error";
		}
	}
}
