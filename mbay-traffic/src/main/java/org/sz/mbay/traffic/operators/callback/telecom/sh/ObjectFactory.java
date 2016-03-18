package org.sz.mbay.traffic.operators.callback.telecom.sh;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * org.sz.mbay.traffic.operator.handle.shtelecom package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _OrderProdOfferCallbackResponse_QNAME = new QName(
			"http://www.shtel.com.cn/csb/v2/", "OrderProdOfferCallbackResponse");
	private final static QName _OrderProdOfferCallbackRequest_QNAME = new QName(
			"http://www.shtel.com.cn/csb/v2/", "OrderProdOfferCallbackRequest");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * org.sz.mbay.traffic.operator.handle.shtelecom
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link OrderProdOfferCallbackResponse }
	 * 
	 */
	public OrderProdOfferCallbackResponse createOrderProdOfferCallbackResponse() {
		return new OrderProdOfferCallbackResponse();
	}

	/**
	 * Create an instance of {@link OrderProdOfferCallbackRequest }
	 * 
	 */
	public OrderProdOfferCallbackRequest createOrderProdOfferCallbackRequest() {
		return new OrderProdOfferCallbackRequest();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link OrderProdOfferCallbackResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.shtel.com.cn/csb/v2/", name = "OrderProdOfferCallbackResponse")
	public JAXBElement<OrderProdOfferCallbackResponse> createOrderProdOfferCallbackResponse(
			OrderProdOfferCallbackResponse value) {
		return new JAXBElement<OrderProdOfferCallbackResponse>(
				_OrderProdOfferCallbackResponse_QNAME,
				OrderProdOfferCallbackResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link OrderProdOfferCallbackRequest }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.shtel.com.cn/csb/v2/", name = "OrderProdOfferCallbackRequest")
	public JAXBElement<OrderProdOfferCallbackRequest> createOrderProdOfferCallbackRequest(
			OrderProdOfferCallbackRequest value) {
		return new JAXBElement<OrderProdOfferCallbackRequest>(
				_OrderProdOfferCallbackRequest_QNAME,
				OrderProdOfferCallbackRequest.class, null, value);
	}

}
