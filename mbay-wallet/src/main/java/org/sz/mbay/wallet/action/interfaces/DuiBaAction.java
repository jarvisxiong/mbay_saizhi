package org.sz.mbay.wallet.action.interfaces;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.wallet.enums.DuiBaState;
import org.sz.mbay.wallet.service.DuiBaService;

/**
 * 交易记录
 * 
 * @author jerry
 */
@RequestMapping("interface/duiba")
@Controller("Interface_DuiBaAction")
public class DuiBaAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DuiBaAction.class);
	
	@Autowired
	private DuiBaService duiBaService;
	
	/**
	 * 获取状态
	 * 
	 * @param snumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("state/get")
	public Object stateGet(@RequestParam("orderNumber") String orderNumber) {
		if (duiBaService.getState(orderNumber) == DuiBaState.OPERATED) {
			return Response.SUCCESS;
		}
		LOGGER.info("traffic red duiba get state fail: " + orderNumber);
		return Response.FAIL;
	}
	
	/**
	 * 更新状态
	 * 
	 * @param orderNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("state/update")
	public Object stateUpdate(
			@RequestParam("orderNumber") String orderNumber,
			@RequestParam("state") Integer state) {
		if (duiBaService.updateState(orderNumber, DuiBaState.valueOf(state))) {
			return Response.SUCCESS;
		}
		LOGGER.error("traffic red duiba update state fail: " + orderNumber);
		return Response.FAIL;
	}
	
	/**
	 * 回滚加款
	 * 
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/balance/rollback")
	public Object addBalance(@RequestParam("orderNumber") String orderNumber) {
		return duiBaService.rollback(orderNumber);
	}
	
	/**
	 * 扣款
	 * 
	 * @param mobile
	 * @param amount
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/balance/reduce")
	public Object reduceBalance(
			@RequestParam("mobile") String mobile,
			@RequestParam("amount") double amount,
			@RequestParam("number") String number,
			@RequestParam("relatedNumber") String relatedNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("amount", amount);
		params.put("number", number);
		params.put("relatedNumber", relatedNumber);
		return duiBaService.reduceBalance(params);
	}
	
}
