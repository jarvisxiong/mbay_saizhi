//package org.sz.mbay.wallet.user.error;
//
//import org.sz.mbay.base.wrap.Response;
//import org.sz.mbay.base.wrap.ResponseFail;
//
///**
// * UserAction 相关错误信息
// * 
// * @author jerry
// */
//public class UserError {
//	
//	public static Response UNAME_EMPTY_REJECT = ResponseFail.create(
//			"UNAME_EMPTY_REJECT", "手机号不能为空");
//	
//	public static Response UNAME_FORMAT_ERROR = ResponseFail.create(
//			"UNAME_FORMAT_ERROR", "手机号格式不正确");
//	
//	public static Response PASSWD_EMPTY_REJECT = ResponseFail.create(
//			"PASSWD_EMPTY_REJECT", "密码不能为空");
//	
//	public static Response PASSWD_INCORRECT = ResponseFail.create(
//			"PASSWD_INCORRECT", "密码不正确");
//	
//	public static Response UNAME_PASSWD_INCORRECT = ResponseFail
//			.create("UNAME_PASSWD_INCORRECT", "手机号或密码错误");
//	
//	public static Response USER_NOT_ACTIVE = ResponseFail.create(
//			"USER_NOT_ACTIVE", "用户尚未激活");
//	
//	public static Response UNAME_NOT_EXIST = ResponseFail.create(
//			"UNAME_NOT_EXIST", "手机号尚未注册");
//	
//	public static Response RESET_PASSWD_FAIL = ResponseFail.create(
//			"RESET_PASSWD_FAIL", "密码修改失败，请稍后再试");
//	
//	public static Response UNAME_HAS_EXIST = ResponseFail.create(
//			"UNAME_HAS_EXIST", "手机号已注册");
//	
//	public static Response REGISTER_FAILED = ResponseFail.create(
//			"REGISTER_FAILED", "注册失败，请稍后再试");
//	
//	public static Response OLD_PASSWD_INCORRECT = ResponseFail.create(
//			"OLD_PASSWD_INCORRECT", "原密码不正确");
//	
//	public static Response NICK_NAME_EMPTY_REJECT = ResponseFail
//			.create("NICK_NAME_EMPTY_REJECT", "昵称不能为空");
//	
//	public static Response IMAGE_TYPE_MISMATCH = ResponseFail.create(
//			"IMAGE_TYPE_MISMATCH", "图片格式不正确");
//	
//	public static Response PORTRAIT_SIZE_EXCEED = ResponseFail.create(
//			"PORTRAIT_SIZE_EXCEED", "图片大小不能超过5M");
//	
//	public static Response PORTRAIT_EMPTY_REJECT = ResponseFail.create(
//			"PORTRAIT_EMPTY_REJECT", "请选择上传头像");
//	
//	public static Response PORTRAIT_UPLOAD_FAILED = ResponseFail
//			.create("PORTRAIT_UPLOAD_FAILED", "图片上传失败");
//	
//	public static Response OLD_PAY_PASSWD_INCORRECT = ResponseFail
//			.create("OLD_PAY_PASSWD_INCORRECT", "原支付密码不正确");
//	
//	public static Response PAY_PASSWD_NOT_SETTED = ResponseFail.create(
//			"PAY_PASSWD_NOT_SETTED", "支付密码尚未设置");
//	
//	public static Response PAY_PASSWD_INCORRECT = ResponseFail.create(
//			"PAY_PASSWD_INCORRECT", "支付密码不正确");
//}
