package org.sz.mbay.traffic.operators.callback.telecom.sh;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "CSB_Through_Service", targetNamespace = "http://www.shtel.com.cn/csb/v2/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CSBThroughService {

	/**
	 * 
	 * @param orderProdOfferCallbackRequest
	 * @return returns org.sz.mbay.traffic.operator.handle.shtelecom.
	 *         OrderProdOfferCallbackResponse
	 */
	@WebMethod(operationName = "OrderProdOfferCallback", action = "http://www.shtel.com.cn/csb/v2/OrderProdOfferCallback")
	@WebResult(name = "OrderProdOfferCallbackResponse", targetNamespace = "http://www.shtel.com.cn/csb/v2/", partName = "OrderProdOfferCallbackResponse")
	public OrderProdOfferCallbackResponse orderProdOfferCallback(
			@WebParam(name = "OrderProdOfferCallbackRequest", targetNamespace = "http://www.shtel.com.cn/csb/v2/", partName = "OrderProdOfferCallbackRequest") OrderProdOfferCallbackRequest orderProdOfferCallbackRequest);

}
