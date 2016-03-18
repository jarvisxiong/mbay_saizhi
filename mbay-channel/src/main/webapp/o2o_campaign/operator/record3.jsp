<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>


<!DOCTYPE html>
<html>
<head>
<t:mobile-assets />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>登录</title>
<link rel="stylesheet" href="${ctx }/js/o2o_campaign/operator/share.css">
<script type="text/javascript"
	src="${ctx }/js/o2o_campaign/operator/login.js"></script>
<style>
.wrapper {
	max-width: 640px;
	margin: 0 auto;
	position: relative;
}

._form-alert {
	position: fixed;
	width: 100%;
	color: #FFF;
	font-size: 10px;
	text-align: center;
	background: rgba(255, 0, 0, 0.8);
	top: 0;
	left: 0;
	line-height: 20px;
	z-index: 7000;
	display: none;
}

._form-login {
	padding: 20px 15px 10px 15px;
}

._form-item {
	height: 40px;
	border: 1px solid #454444;
	border-radius: 4px;
	margin-bottom: 15px;
	position: relative;
}

._form-item>label {
	display: none;
}

._form-item>input {
	outline: medium;
	display: block;
	float: left;
	height: 20px;
	width: 100%;
	z-index: 4;
	margin-top: 7px;
	padding-left: 10px;
	color: #888888
}

._form-sub {
	border: none;
	height: 40px;
	width: 100%;
	color: #454444;
	font-size: 16px;
	border-radius: 4px;
	background: #F39C11;
}

._form-msg {
	padding: 0 15px;
	position: relative;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="_form-alert">错误</div>
		<form class="_form-login" id="loginform">
			<div class="_form-item">
				<label for="form-tel">授权码</label> <input type="text"
					value="${authCode }" id="form-tel" name='authCode'
					placeholder="授权码">
			</div>
			<div class="_form-item">
				<label for="form-tel">手机号码</label> <input type="tel" id="form-tel"
					value="${cellphone }" name='cellphone' placeholder="手机号码">
			</div>
			<div class="_form-item">
				<label for="form-name">密码</label> <input type="password"
					id="form-name" name='password' placeholder="密码">
			</div>
			<button type="button" class="_form-sub" id="btn_ope_login">确认</button>
		</form>
		<div class="_form-msg">
			<input type="checkbox" id="pwd2"
				style="position: relative; top: 1px;"> <label for="pwd2">记住密码</label>------->>>
			<a style="color: green;" href="${ctx }/store_ope/initUI.mbay">首次登陆初始化</a>
		</div>
	</div>


	<%-- <form id="loginform">
		<div>
			<label>授权码：</label><input type="text" value="${authCode }"
				name="authCode">
		</div>
		<div>
			<label>手机号：</label><input type="text" value="${cellphone }"
				name="cellphone">
		</div>
		<div>
			<label>密码：</label><input type="text" name="password">
		</div>
		<!-- <div>
			<label>验证码：</label><input type="text" name="checkCode">
		</div> -->
		<div>
			<input type="reset" value="重置"> <input type="button"
				value="登录">
		</div>
		<div>
			<a href="${ctx }/store_ope/initUI.mbay">首次登陆初始化</a>
		</div>
	</form> --%>
</body>
</html>