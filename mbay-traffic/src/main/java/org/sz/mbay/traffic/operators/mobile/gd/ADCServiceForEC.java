/**
 * ADCServiceForEC.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package org.sz.mbay.traffic.operators.mobile.gd;

/*
 *  ADCServiceForEC java interface
 */

public interface ADCServiceForEC {

	/**
	 * Auto generated method signature
	 * 
	 * @param adcServices0
	 */

	public AdcServicesResponse adcServices(

	AdcServices adcServices0) throws java.rmi.RemoteException;

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @param adcServices0
	 */
	public void startadcServices(

	AdcServices adcServices0,

	final ADCServiceForECCallbackHandler callback)

	throws java.rmi.RemoteException;

	//
}
