<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>摇一摇抢流量红包</title>
<t:mobile-assets />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/swing.css?v=${version}" />
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/shakeIndex.js?v=${version}"></script>
</head>
<body>
<!--输入手机号背景+state-->
<section class="shake-content">
	<article class="shake-bg-top"></article>
	<!--输入提交表单+start-->
	<form action="" class="shake-forms relative-center">
		<input type="tel" id="mobile_tr" class="shake-forms-tel tel-num"
			placeholder="请输入11位手机号" maxlength="11" pattern="[0-9]{11}" required="required" /> 
		<label class="tishi"></label> 
		<input type="button" class="shake-forms-btn" value="马上开摇" />
	</form>
	<!--输入提交表单--->
</section>
<!--有惊喜按钮-start-->
<a id="surprised" class="surprised" evt-touch="gotoUrl('${c.template.logoCycleLink }')"> 
	<b class="surprised-content"> 
		<i class="surprised-ico"><img src="${actx}/traffic_red/mobile/shake/image/surprised.png" /></i> 
		<small class="surprised-txt statistics-text">有惊喜</small>
	</b>
</a>
<!--输入手机号背景-end-->
<input type="hidden" id="cnumber" value="${cnumber }" />
</body>
</html>
