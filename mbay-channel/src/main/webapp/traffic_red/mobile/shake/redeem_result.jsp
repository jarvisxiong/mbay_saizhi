<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fs" uri="http://www.mbpartner.cn/jsp/fastdfs/tags"%>
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
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/share.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/redeem_result.js?v=${version}"></script>
</head>
<body>
<!--内容模块-start-->
<section class="flow-gain">
	<!--流量到手-start-->
	<article class="flow-red-packet place" evt-touch="gotoUrl('${template.sharkResultLink }')">
		<div class="flow-num place">
			<c:forEach var="s" items="${size }">
				<span><img src="${ctx }/traffic_red/mobile/shake/image/flow/${s}.png"></span>
			</c:forEach>
			<span class="unit"><img src="${ctx }/traffic_red/mobile/shake/image/flow/MB.png"></span>
		</div>
		<p class="flow-red-packet-tishi">流量到手</p>
		<c:if test="${sharable}">
			<p class="flow-red-packet-tishi2">分享给小伙伴<label>再抽一次</label></p>      
		</c:if>
	</article>
	<article class="go-packet-btn">
		<a href="javascript:void(0)" class="burse-btn" evt-touch="gotoUrl('${walletIndex }')" statistics-text="进入钱包">
			<img src="${ctx }/traffic_red/mobile/shake/image/up.png" /><!--进入MB钱包-->
		</a>
	</article>
	<!--更多优惠-start-->
	<article class="more-privilege place" style="position:relative; z-index:999;">
	    <div class="surprise place">
	    	<img src="${ctx }/traffic_red/mobile/shake/image/move-jinxi.png" class="surprise-tit">
	    </div>
	    <!--分享惊喜-->
	    <a id="surprised" class="surprised acquire-surprised" href="javascript:void(0)" evt-touch="gotoUrl('${surprise}')">
	        <b class="surprised-content">
	            <i class="surprised-ico"><img src="${ctx }/traffic_red/mobile/shake/image/surprised.png" /></i>
	            <small class="surprised-txt statistics-text">有惊喜</small>
	        </b>
		</a>
	</article>
	<!--分割线-start-->
	<article class="solid place"><img src="${ctx }/traffic_red/mobile/shake/image/solid.png"/></article>
	<!--分割线-end-->
	<!--更多优惠-end-->
	<!--广告链接-start-->
	<article class="advertisement place">
	    <c:if test="${not empty template.adImg1 }">
			<a href="javascript:void(0)" class="ad-img1 ad-img" evt-touch="gotoUrl('${template.adLink1 }')" statistics-text="广告1">
				<img src="<fs:fdfs value="${template.adImg1 }"/>">
			</a>
		</c:if>
	    <c:if test="${not empty template.adImg2 }">
	    	<a href="javascript:void(0)" class="ad-img2 ad-img" evt-touch="gotoUrl('${template.adLink2 }')" statistics-text="广告2">
	    		<img src="<fs:fdfs value="${template.adImg2 }"/>">
	    	</a>
	    </c:if>
	</article>
	<!--广告链接-end-->
	
	<!--活动规则-start-->
	<article class="activity-rule place">
		<p>活动规则:</p> 
		<p>1、流量包将于24小时内充值到登记手机号中，流量包当月使用有效。</p>
		<p>2、如有以下情况将无法正常领取流量：停机、欠费、未激活、所属套餐不支持订购流量业务、携号转网用户、移动多号通业务的副号用户及手机号码有误。详情可咨询手机号码所属运营商。</p>
	</article>
</section>

<input type="hidden" id="cnumber" value="${cnumber }" />
</body>
</html>