package org.sz.mbay.channel.service;

import org.sz.mbay.channel.bean.Emailretrievepwdrecord;

/**
 * @author Tom
 * @version 创建时间：2014-8-7下午1:50:13
 * @type 类型描述 public class EmailretrievepwdrecordService{ }
 */
public interface EmailretRievepwdRecordService {

	public Emailretrievepwdrecord insertEmailretrievepwdrecord(
			Emailretrievepwdrecord emailretrievepwdrecord) throws Exception;

	public Emailretrievepwdrecord findEmailRetrievepwdRecord(String userNumber);


	public int deleteEmailRetrievepwdRecord(String userNumber) throws Exception;

}
