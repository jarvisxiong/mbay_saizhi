package org.sz.mbay.paymb.pay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.sz.mbay.paymb.pay.response.TradeResponse;

@ControllerAdvice
public class TradeExceptionHandler {

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(TradeController.class);

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "IOException occured")
	@ExceptionHandler(Exception.class)
	public void handleInternalException(Exception e) {

	}

	@ExceptionHandler(MBTradeException.class)
	@ResponseBody
	public TradeResponse handleServiceException(MBTradeException exception) {
		return exception.getTradeResponse();
	}

}
