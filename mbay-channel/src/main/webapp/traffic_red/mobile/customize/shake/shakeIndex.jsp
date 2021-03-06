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
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/customize/shake/css/shakeIndex.css?v=${version}" />
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/customize/shake/js/shakeIndex.js?v=${version}"></script>
</head>
<body>
<section class="shake_import_bg">
	<article id="Import_ms">
		<div id="Import_Form">
			<input type="text" maxlength="11" placeholder="请输入手机号" id="mobile" autocomplete="off" />
			<p class="shake_message"></p>
			<button class="shake_start">马上开摇</button>
		</div>
	</article>
	<!--底层按钮-->
	<article class="footer">
		<button class="experience">参加活动</button>
	</article>
</section>
<input type="hidden" id="bgImgUrl" value="<fs:fdfs value="${c.template.shakeIndexImg }" />" /> 
<input type="hidden" id="cnumber" value="${cnumber }" />
</body>
</html>
