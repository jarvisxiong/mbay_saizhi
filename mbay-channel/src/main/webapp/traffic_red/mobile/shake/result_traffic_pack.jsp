<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/result_traffic_pack.js?v=${version}"></script>
</head>
<body>
<section class="get-packedt-content">

	<div class="traffic-wrapper absolute-vert-center">
		<!--流量红包-start-->
		<article class="flow-packedt">
			<div class="gain-packet-number">
				<c:forEach var="s" items="${size }">
					<span><img src="${ctx }/traffic_red/mobile/shake/image/flow/${s}.png"></span>
				</c:forEach>
				<span class="unit"><img src="${ctx }/traffic_red/mobile/shake/image/flow/MB.png"></span>
			</div>
		</article>
		<!--流量红包-end-->
	
		<!--立即兑换-start-->
		<article class="promptly-convert" id="redeemLink"></article>
		<!--立即兑换-end-->
	</div>

	<a href="javascript:void(0)" id="surprised" class="surprised convert-surprised" ontouchend="gotoUrl('${surprise }')"> 
		<b class="surprised-content"> 
			<i class="surprised-ico">
				<img src="${ctx }/traffic_red/mobile/shake/image/surprised.png" />
			</i> 
			<small class="surprised-txt statistics-text">有惊喜</small>
		</b>
	</a>
</section>
<m:token />
<input type="hidden" id="cnumber" value="${cnumber }" />
<input type="hidden" id="recordId" value="${recordId }" />
<input type="hidden" id="traddicSize" value="${traddicSize }" />
</body>
</html>