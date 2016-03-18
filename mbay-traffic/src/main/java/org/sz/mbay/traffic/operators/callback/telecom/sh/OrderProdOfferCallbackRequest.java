package org.sz.mbay.traffic.operators.callback.telecom.sh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for OrderProdOfferCallbackRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="OrderProdOfferCallbackRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MSGID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORDER_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERROR_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERROR_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderProdOfferCallbackRequest", propOrder = { "msgid",
		"ordernum", "errorcode", "errormessage" })
public class OrderProdOfferCallbackRequest {

	@XmlElement(name = "MSGID", required = true)
	protected String msgid;
	@XmlElement(name = "ORDER_NUM", required = true)
	protected String ordernum;
	@XmlElement(name = "ERROR_CODE", required = true)
	protected String errorcode;
	@XmlElement(name = "ERROR_MESSAGE", required = true)
	protected String errormessage;

	/**
	 * Gets the value of the msgid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMSGID() {
		return msgid;
	}

	/**
	 * Sets the value of the msgid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMSGID(String value) {
		this.msgid = value;
	}

	/**
	 * Gets the value of the ordernum property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getORDERNUM() {
		return ordernum;
	}

	/**
	 * Sets the value of the ordernum property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setORDERNUM(String value) {
		this.ordernum = value;
	}

	/**
	 * Gets the value of the errorcode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getERRORCODE() {
		return errorcode;
	}

	/**
	 * Sets the value of the errorcode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setERRORCODE(String value) {
		this.errorcode = value;
	}

	/**
	 * Gets the value of the errormessage property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getERRORMESSAGE() {
		return errormessage;
	}

	/**
	 * Sets the value of the errormessage property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setERRORMESSAGE(String value) {
		this.errormessage = value;
	}

}
