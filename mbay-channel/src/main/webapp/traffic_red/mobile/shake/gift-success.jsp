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
<title>领取成功</title>
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/gift-public.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/share-pump.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/gift-success.css?v=${version}" />
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift-share.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift-success.js?v=${version}"></script>
</head>
<body delegate="gift-pop-close">
<section class="modal-succeed-x">
    <div class="modal-succeed-content-x">
        <header>
            <img src="${ctx }/traffic_red/mobile/shake/image/gift/congratulation.png">
        </header>
        <div class="modal-content-x">
            <h2>${size }<i>MB流量</i></h2>
            <p>已放入手机账号：<i>${mobile }</i></p>
            <a href="javascript:void(0)" class="part" evt-touch="gotoUrl('${partUrl }')">我也要参加活动</a>
        </div>
        <footer>
            <a href="javascript:void(0)" statistics-text="存钱">
            	<img src="${ctx }/traffic_red/mobile/shake/image/gift/store.png" evt-touch="gotoUrl('${walletIndex }')">
            </a>
            <a href="javascript:void(0)" statistics-text="兑换">
            	<img src="${ctx }/traffic_red/mobile/shake/image/gift/convertibility.png" evt-touch="gotoUrl('${duiba }')">
            </a>
            <a href="javascript:void(0)" statistics-text="送人">
            	<img src="${ctx }/traffic_red/mobile/shake/image/gift/give.png" evt-touch="gift()">
            </a>
        </footer>
    </div>
</section>

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
	           	<a href="javascript:void(0)" evt-touch="$.messager.closeDialog()" delegate-id="gift-pop-close">关闭</a> 
	        </div> 
	    </div> 
	</section>
</div>

<input type="hidden" id="serialNumber" value="${serialNumber }" />
<input type="hidden" id="cnumber" value="${cnumber }" />
</body>
</html>

