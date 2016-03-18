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
<title>领取失败</title>
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/gift-public.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/gift-fail.css?v=${version}" />
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift-fail-share.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift-fail.js?v=${version}"></script>
</head>
<body>
<section class="modal-succeed">
	<c:if test="${share }">
		<section class="modal-succeed-head">
			<img src="${ctx }/traffic_red/mobile/shake/image/gift/share.png" alt="">
		</section>
	</c:if>
    <div class="modal-succeed-content">
        <header>
            <img src="${ctx }/traffic_red/mobile/shake/image/gift/uncle-1.png">
        </header>
        <div class="modal-content">
            <!-- <h2><i>领取上限</i></h2> -->
            <p><i>${msg }</i></p>
            <a href="javascript:void(0)" evt-touch="gotoUrl('${partUrl }')">我也要参加活动</a>
        </div>
        <footer>
            <a href="javascript:void(0)" statistics-text="存钱">
            	<img src="${ctx }/traffic_red/mobile/shake/image/gift/store.png" evt-touch="gotoUrl('${walletIndex }')">
            </a>
            <a href="javascript:void(0)" statistics-text="兑换">
            	<img src="${ctx }/traffic_red/mobile/shake/image/gift/convertibility.png" evt-touch="gotoUrl('${duiba }')">
            </a>
        </footer>
    </div>
</section>
<input type="hidden" id="serialNumber" value="${serialNumber }" />
<input type="hidden" id="cnumber" value="${cnumber }" />
<input type="hidden" id="share" value="${share }" />
</body>
</html>

