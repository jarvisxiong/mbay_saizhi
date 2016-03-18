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
<title>促销神器送MB</title>
<link rel="stylesheet" href="${ctx }/promotion_campaign/mobile/css/swing.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/share-pump.css?v=${version}" /> 
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/promotion_campaign/mobile/js/share.js?v=${version}"></script>
<%-- <script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift-share.js?v=${version}"></script> --%>
<script type="text/javascript" src="${actx}/promotion_campaign/mobile/js/result_mbay_pack.js?v=${version}"></script>
<style>
.button-imgbtn{margin-top:36%;margin-bottom:15%;}
.packet-conversion{color:#fff;position:absolute;top:15.5rem;z-index:100;width:100%;text-align:center;font-size:14px;}
.packet-verification{color:#fff;position:absolute;top:16.5rem;z-index:100;text-align:center;width:100%;font-size:14px;}
@media screen and (max-width: 320px) and (max-height:480px){
.obtain-packet {height:9.2rem; }
.continue{padding-top:6.4rem;}
.packet-conversion{top:11.5rem;}
.packet-verification{top:12.5rem;}
.button-imgbtn{margin-top:30%;margin-bottom:12%;}
}
</style>
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
	
		<!--再玩一次/再领一次-start-->
		<c:if test="${continuable }">
			<aside class="continue">
				<a href="javascript:void(0)" class="two-shake-btn"
					evt-touch="shakeAgain('${model }')">
					<span>再玩一次</span>
				</a>
			</aside>
		</c:if>
		<!--再玩一次/再领一次-end-->
	</article>
	<!--获得的红包-end-->
	 <!-- 兑换码 -->
	 <c:if test="${not empty code }">
	  	 <article class="packet-conversion">
			<span>兑换码:${code}</span>    
	     </article>
     </c:if>
     <!-- 核销码 -->
     <c:if test="${not empty verificate }">
	     <article class="packet-verification">
	        <p>核销码:${verificate}</p>
	     </article>
     </c:if>
	<!--按钮层-->
	<article class="button-imgbtn">
		<!-- 钱包 -->
		<a class="give"><img src="${ctx }/promotion_campaign/mobile/image/give.png" evt-touch="gotoUrl('${walletIndex }')"></a>
		<!-- 兑换 -->
		<a class="conversion"><img src="${ctx }/promotion_campaign/mobile/image/convertibility.png" evt-touch="gotoUrl('${duiba }')"></a>
		<!-- 游戏 -->
		<a class="lottery"><img src="${ctx }/promotion_campaign/mobile/image/lottery.png" evt-touch="gotoUrl('${youxi }')"></a>
	</article>
	
	<article class="activity-page" style="width:80%;">
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
         		<h2 class="sp-h2">"再玩一次"说明</h2>
	        	<div class="cont">
	         		<p>告诉给专属小伙伴</p>
	         		<p class="spe">请选择右上角"发送给朋友"</p>
		            <p>告诉大家一起参与</p>
		            <p class="spe">请选择右上角"分享朋友圈"</p>
		            <p>操作完成后可点击<span class="spe">"再玩一次"</span></p>
	            </div>
	           	<a href="javascript:void(0)" evt-touch="$.messager.closeDialog()" delegate-id="share-close">关闭</a>
	        </div>
	    </div>
	</section>
</div> 

<input type="hidden" id="cnumber" value="${cnumber }" />
<input type="hidden" id="enumber" value="${enumber }" />
<input type="hidden" id="code" value="${code }" />
<input type="hidden" id="remindShare" value="${remindShare }" />
<input type="hidden" id="playAgain" value="${playAgain }" />
</body>
</html>
