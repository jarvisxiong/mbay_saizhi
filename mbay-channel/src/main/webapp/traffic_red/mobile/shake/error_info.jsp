<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:mobile-assets />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>摇一摇抢流量红包</title>
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/swing.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/error_info.css?v=${version}" />
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
</head>
<body>
<!--摇之前提示+state-->
<section class="shake-up-content">
	<!--红包内容-start-->
	<article class="packet-bg relative-center">
		<h3 class="packet-up-tishi">${errorMsg}</h3>
		<!--进入钱包按钮-->
		<a href="javascript:void(0)" class="burse-btn" ontouchend="gotoUrl('${walletIndex}')" statistics-text="进入钱包">
			<img src="${ctx}/traffic_red/mobile/shake/image/up.png" />
		</a>
	</article>
	<!--活动结束内容-end-->
	<!--有惊喜按钮-start-->
	<a id="surprised" class="surprised" href="${surprise}"> 
		<b class="surprised-content"> 
			<i class="surprised-ico"><img src="${ctx}/traffic_red/mobile/shake/image/surprised.png" /></i> 
			<small class="surprised-txt statistics-text">有惊喜</small>
		</b>
	</a>

	<!--有惊喜按钮-end-->
</section>
</body>
</html>