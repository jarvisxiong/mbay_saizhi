<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<link type="text/css" rel="stylesheet" href="${ctx }/page/main/css/login.css?v=${version}" />
</head>
<body>
<article class="mb-form">
	<nav class="mb-tab clearfix">
		<a href="javascript:void(0)" class="active">MB账号登录</a> 
	</nav>
	<form class="block">
		<section class="mb-form-a clearfix">
			<div class="form-item clearfix">
				<input type="tel" id="mobile" placeholder="手机号/账户名" value="${mobile }" autocomplete="off" maxlength="11" />
				<img src="${ctx }/page/main/img/loading.gif" class="loading hide" id="loading" />
				<a href="javascript:void(0)" class="form-code hide" id="checkcode">获取验证码</a>
			</div>
			<div class="form-item clearfix hide form-item-border" id="pwd-wrap">
				<input type="password" id="pwd" placeholder="密码">
			</div>
			<div class="form-item clearfix hide form-item-border" id="smscode-wrap">
				<input type="tel" id="smscode" placeholder="请输入手机收到的验证码">
			</div>
		</section>
		<a href="javascript:void(0)" class="forget forg">找回密码？</a>
		<p class="voice" id="voiceCode">
			<span>收不到验证码？使用</span> 
			<a href="javascript:void(0)" class="voiceyzm">语音验证码</a>
		<p>
		<section class="mb-form-b clearfix">
			<input type="button" value="登录" id="pwdLogin" /> 
		</section>
	</form>
	
	<div class="code">
	    <p class="p1">登陆MB钱包参与更多活动!</p>
	    <img src="${ctx}/common/img/login_qrcode.png" alt="">
	    <p class="p2">长按二维码，记住你的MB钱包</p>
	</div>
</article>
<script type="text/javascript" src="${ctx }/page/main/js/login.js?v=${version}"></script>
</body>
</html>