package org.sz.mbay.traffic.service.impl;

import org.dom4j.Document;

public class RedeemResult {
	
	public RedeemResult(boolean success,Document document){
		this.success=success;
		this.document=document;
	}
	
	private boolean success;
	
	private Document document;

	public boolean isSuccess() {
		return success;
	}

	public Document getDocument() {
		return document;
	}
	


}
