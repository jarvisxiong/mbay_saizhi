package org.sz.mbay.sms.util;

public class SDKSMSUtil {
    
    public  static String aa="";

    public static String getErrorDescByCode(int code) {
	switch (code) {
	case -2:
	    return "帐号/密码不正确";
	case -4:
	    return "余额不足支持本次发送";
	case -5:
	    return "数据格式错误";
	case -6:
	    return "参数有误";
	case -7:
	    return "权限受限";
	case -8:
	    return "流量控制错误";
	case -9:
	    return "扩展码权限错误";
	case -10:
	    return "内容长度长";
	case -11:
	    return "内部数据库错误";
	case -12:
	    return "序列号状态错误";
	case -14:
	    return "服务器写文件失败";
	case -17:
	    return "没有权限";
	case -19:
	    return "禁止同时使用多个接口地址";
	case -20:
	    return "相同手机号，相同内容重复提交";
	case -22:
	    return "Ip鉴权失败";
	case -23:
	    return "缓存无此序列号信息";
	case -601:
	    return "序列号为空，参数错误";
	case -602:
	    return "序列号格式错误，参数错误";
	case -603:
	    return "密码为空，参数错误";
	case -604:
	    return "手机号码为空，参数错误";
	case -605:
	    return "内容为空，参数错误";
	case -606:
	    return "ext长度大于9，参数错误";
	case -607:
	    return "参数错误 扩展码非数字 ";
	case -608:
	    return "参数错误 定时时间非日期格式";
	case -609:
	    return "rrid长度大于18,参数错误 ";
	case -610:
	    return "参数错误 rrid非数字";
	case -611:
	    return "参数错误 内容编码不符合规范";
	case -623:
	    return "手机个数与内容个数不匹配";
	case -624:
	    return "扩展个数与手机个数数";
	}

	return "未知异常";
    }

}
