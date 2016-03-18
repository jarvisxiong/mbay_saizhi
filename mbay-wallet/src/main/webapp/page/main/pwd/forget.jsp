<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>忘记密码</title>
<link type="text/css" rel="stylesheet" href="${ctx }/page/main/pwd/css/forget.css?v=${version}" />
</head>
<body>
<article class="mb-form">
	<form class="block forget-psd-1">
		<section class="mb-form-a clearfix">
			<div class="form-item clearfix">
				<input type="tel" id="mobile" value="${mobile }" placeholder="请输入手机号码" />
				<a href="#" class="form-code" id="checkcode">发送验证码</a>
			</div>
			<div class="form-item clearfix">
				<input type="text" id="codeText" placeholder="请输入手机收到的验证码">
			</div>
		</section>
		<p class="voice" id="voiceCode">
			<span>收不到验证码？使用</span> 
			<a href="javascript:void(0)">语音验证码</a>
		<p>
		<section class="mb-form-b clearfix">
			<input type="button" id="nextStep" value="下一步" />
		</section>
	</form>
</article>
<script type="text/javascript" src="${ctx }/page/main/pwd/js/forget.js?v=${version}"></script>
</body>
</html>