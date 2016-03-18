package org.sz.mbay.paymb.pay.parameter;

import java.util.List;

/**
 * The interface that every trade parameter class must implement.
 * <P>When a ParameterRequir class is loaded, it should create an instance of
 * itself and register it with the ParameterManger. 
 * <p>
 * @author han.han
 *
 */
public interface ParameterRequir {
	
	public List<Parameter> getRequiredParameters();

}
