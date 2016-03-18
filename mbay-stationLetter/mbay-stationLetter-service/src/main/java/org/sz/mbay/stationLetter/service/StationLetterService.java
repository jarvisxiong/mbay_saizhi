package org.sz.mbay.stationLetter.service;

import org.sz.mbay.stationLetter.bean.StationLetter;

/**
 * 站内信Service
 * 
 * @author frank.zong
 * 
 */
public interface StationLetterService {
	
	public boolean sendWebSiteEmail(StationLetter message, String usernumber, String currentUserNumber);
	
	public StationLetter findMessage(String msgid);
	
}
