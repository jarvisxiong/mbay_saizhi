package org.sz.mbay.base.service.impl;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sz.mbay.base.dao.UtilDao;
import org.sz.mbay.base.service.UtilService;

@Service
public class UtilServiceImpl implements UtilService {

	@Autowired
	UtilDao utildao;

	public static final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyyMMdd");

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int getNextIndex(Class<?> tclass) {
		int next= utildao.getNextIndex(tclass.getSimpleName());
		this.utildao.incrementIndex(tclass.getSimpleName());
		return next;
	}
	

	@Override
	public String getNextSerialNumber(Class<?> tclass) {
		String number = DateTime.now().toString(dateFormat);
		int nextNo = this.getNextIndex(tclass);
		number += String.valueOf(100000 + nextNo);
		return number;
	}

}