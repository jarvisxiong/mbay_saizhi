package org.sz.mbay.channel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.Emailretrievepwdrecord;
import org.sz.mbay.channel.dao.EmailretrievepwdrecordDao;
import org.sz.mbay.channel.service.EmailretRievepwdRecordService;

/**
 * @author Tom
 * @version 创建时间：2014-8-7下午1:52:27
 * @type 类型描述 public class EmailretrievepwdrecordServiceImpl{ }
 */
@Service
public class EmailretrievepwdrecordServiceImpl extends BaseServiceImpl implements EmailretRievepwdRecordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailretrievepwdrecordServiceImpl.class);

	@Autowired
	EmailretrievepwdrecordDao emailretrievepwdrecordDao;

	@Override
	public Emailretrievepwdrecord insertEmailretrievepwdrecord(
			Emailretrievepwdrecord emailretrievepwdrecord) throws Exception {

		return this.emailretrievepwdrecordDao
				.createBean(emailretrievepwdrecord);
	}

	@Override
	public Emailretrievepwdrecord findEmailRetrievepwdRecord(String userNumber) {
		try {
			return this.emailretrievepwdrecordDao.getBean(userNumber);
		} catch (Exception e) {
			LOGGER.error("findEmailretrievepwdrecord",e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public int deleteEmailRetrievepwdRecord(String userNumber) throws Exception {
		return this.emailretrievepwdrecordDao.deleteBean(userNumber);
	}

}
