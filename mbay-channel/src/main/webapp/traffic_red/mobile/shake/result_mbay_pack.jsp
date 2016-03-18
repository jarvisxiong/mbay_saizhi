<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:mobile-assets/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>摇一摇抢流量红包</title>
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/swing.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/share-pump.css?v=${version}" /> 
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/share.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift-share.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/result_mbay_pack.js?v=${version}"></script>
</head>
<body delegate="share-close gift-close">	
<!--获得红包内容-start-->
<section class="obtain-packet-content bg-style">
	<!--获得的红包-start-->
	<article class="obtain-packet bg-style">
		<!--获得红包流量数量-start-->
		<ul class="obtain-packet-number">
			<c:forEach var="s" items="${size }">
				<li><img src="${ctx }/traffic_red/mobile/shake/image/flow/${s}.png"></li>
			</c:forEach>
			<li class="unit"><img src="${ctx }/traffic_red/mobile/shake/image/flow/MB.png"></li>
		</ul>
		<!--获得红包流量数量-end-->
	
		<!--再摇一次-start-->
		<c:if test="${continuable }">
			<aside class="continue">
				<a href="javascript:void(0)" class="two-shake-btn"
					evt-touch="shakeAgain('<c:url value='/tr_mobile/shakeIndex.mbay?number=${cnumber }'/>')">
					<span class="statistics-text">再摇一次</span>
				</a>
			</aside>
		</c:if>
		<!--再摇一次-end-->
	</article>
	<!--获得的红包-end-->
	
	<!--按钮层-->
	<article class="button-imgbtn">
		<a class="deposit" statistics-text="存钱">
			<img src="${ctx }/traffic_red/mobile/shake/image/gift/store.png" evt-touch="gotoUrl('${walletIndex }')" />
		</a>
		<a class="conversion" statistics-text="兑换">
			<img src="${ctx }/traffic_red/mobile/shake/image/gift/convertibility.png" evt-touch="gotoUrl('${duiba }')" />
		</a>
		<a class="lottery" statistics-text="游戏">
			<img src="${ctx }/traffic_red/mobile/shake/image/lottery.png" evt-touch="gotoUrl('${youxi }')">
		</a>
		<a class="give" statistics-text="送人">
			<img src="${ctx }/traffic_red/mobile/shake/image/gift/give.png" evt-touch="gift()" />
		</a>
	</article>
	
	<!--立即分享-start-->
	<a href="javascript:void(0)" id="surprised" class="surprised gain-packet-surprised" evt-touch="gotoUrl('${surprise }')">
	    <b class="surprised-content">
	        <i class="surprised-ico"><img src="${ctx }/traffic_red/mobile/shake/image/surprised.png" /></i>
	        <small class="surprised-txt statistics-text">有惊喜</small>
	    </b>
	</a>
	<article class="activity-page">
		<p class="activity-tit"><label class="feilipu">${campaignName}</label>活动</p>
		<p>赠送您<label class="flow">${mbayTraffic}MB</label>流量已存入您的MB钱包</p>
	</article> 
</section>	

<div style="display:none;" id="share-popup">
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
		            <p>操作完成后可点击<span class="spe">"再摇一次"</span></p>
	            </div>
	           	<a href="javascript:void(0)" evt-touch="$.messager.closeDialog()" delegate-id="share-close">关闭</a>
	        </div>
	    </div>
	</section>
</div> 

<!-- 送人功能说明  -->
<div style="display:none;" id="gift-popup">
	<section class="modal-succeed">
	    <div class="modal-succeed-content">
	        <header>
	            <img src="${ctx }/traffic_red/mobile/shake/image/gift/uncle-3.png">
	        </header>
	        <div class="modal-content">
	        	<h2>"送人"功能说明</h2>
	        	<h3>摇中"MB"可以送人</h3>
	        	<div class="cont">
	         		<p>送给闺蜜好基友</p>
	         		<p class="spe">请选择右上角"发送给朋友"</p>
		            <p>发给大家一起玩</p>
		            <p class="spe">请选择右上角"分享朋友圈"</p>
	            </div>
	           	<a href="javascript:void(0)" evt-touch="$.messager.closeDialog()" delegate-id="gift-close">关闭</a> 
	        </div> 
	    </div> 
	</section>
</div>  

<input type="hidden" id="serialNumber" value="${serialNumber }" />
<input type="hidden" id="cnumber" value="${cnumber }" />
<input type="hidden" id="remindShare" value="${remindShare }" />
</body>
</html>
