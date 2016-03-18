package org.sz.mbay.wallet.action.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;

@Controller("Interface_AccountAction")
@RequestMapping("interface/account")
public class AccountAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountAction.class);
			
	/**
	 * 获取账户余额
	 * 
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/balance/get")
	public Object getBalance(@RequestParam("mobile") String mobile) {
		try {
			RIResponse resp = RIMBAccountUtil.requestUserGetMBQty(mobile);
			if (resp.isStatus()) {
				return ResponseSuccess
						.create(resp.getData().getDouble("balance"));
			} else {
				return ResponseFail.create(resp.getErrorMsg());
			}
		} catch (Exception e) {
			LOGGER.error("get balance error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
	}
}
