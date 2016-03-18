package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

public class TaskError {
	
	public static Response TASK_REPEAT = ResponseFail.create("TASK_REPEAT", "任务已完成，不能重复参与");
}
