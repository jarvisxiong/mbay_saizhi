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
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/customize/kule/shake/css/shakeIndex.css?v=${version}" />
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/customize/kule/shake/js/shakeIndex.js?v=${version}"></script>
</head>
<body>
<section class="import_bg">
	<article class="import_form">
		<input type="tel" name="tel" class="import_number" placeholder="请输入手机号" maxlength="11"/>
		<button class="shake_start">马上开摇</button>
	</article>
</section>
<input type="hidden" id="cnumber" value="${cnumber }" />
</body>
</html>
