<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:mobile-assets />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>摇一摇抢流量红包</title>
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/swing.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/share-pump.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/shakeUI.css?v=${version}" />
<script type="text/javascript" src="${ctx }/js/jquery/jquery.ios-shake.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/js/jquery/jquery.ui.shake.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/traffic_red/mobile/shake/js/acceleration.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/traffic_red/mobile/shake/js/share.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/traffic_red/mobile/shake/js/shakeUI.js?v=${version}"></script>
</head>
<body delegate="gift-close wallet-index shake-again">
	<!--摇一摇内容-start-->
	<section class="shake-content-bg">
		<!--欢迎回来-start-->
		<article class="welcome-return">
			<p>欢迎您 <label>${mobile }</label></p>
		</article>

		<article id="music">
			<!--摇一摇图片-start-->
			<div class="liuliang">
				<p>
					<img src="${ctx }/traffic_red/mobile/shake/image/shake-5.png"
						id="headads" style="margin-bottom:0;">
				</p>
				<p>
					<img src="${ctx }/traffic_red/mobile/shake/image/shake-6.png"
						id="headads2" style="margin-top:0;">
				</p>
			</div>
			<!--摇一摇图片-end-->
		</article>
	</section>

	<!--摇一摇失敗提示内容-end-->
	<div id="shakeTipDiv" style="display: none">
		<p class="tip-text"></p>
		<p class="access-burse">
			<!--进入MB钱包-->
			<a href="javascript:void(0)" evt-touch="gotoUrl('${walletIndex}')"
				delegate-id="wallet-index">进入MB钱包</a>
			<!--再摇一次-->
			<a href="javascript:void(0)" evt-touch="shakeAgain()" id="shakeagain"
				style="display: none;" delegate-id="shake-again">再摇一次</a>
		</p>
	</div>

	<div style="display: none;" id="share-popup">
		<section class="modal-succeed">
			<div class="modal-succeed-content">
				<header>
					<img src="${ctx }/traffic_red/mobile/shake/image/gift/uncle-3.png">
				</header>
				<div class="modal-content">
					<h2 class="sp-h2">"再摇一次"说明</h2>
					<div class="cont">
						<p>告诉给专属小伙伴</p>
						<p class="spe">请选择右上角"发送给朋友"</p>
						<p>告诉大家一起参与</p>
						<p class="spe">请选择右上角"分享朋友圈"</p>
						<p>
							操作完成后可点击<span class="spe">"再摇一次"</span>
						</p>
					</div>
					<a href="javascript:void(0)"
						evt-touch="$.messager.closeDialog(this)" delegate-id="gift-close">关闭</a>
				</div>
			</div>
		</section>
	</div>

	<input type="hidden" id="cnumber" value="${cnumber }" />
	<input type="hidden" id="mobile" value="${mobile }" />
</body>
</html>

