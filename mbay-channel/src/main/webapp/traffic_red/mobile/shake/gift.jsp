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
<title>抢流量</title>
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/gift-public.css?v=${version}" /> 
<link rel="stylesheet" href="${ctx }/traffic_red/mobile/shake/css/gift.css?v=${version}" /> 
<script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/js/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/gift.js?v=${version}"></script>
</head>
<body>
<c:choose>
	<c:when test="${not empty bgImg }">
		<c:set var="bg" value="${bgImg }" />
	</c:when>
	<c:otherwise>
		<c:set var="bg" value="${ctx }/traffic_red/mobile/shake/image/gift/bg1.jpg" />
	</c:otherwise>
</c:choose>
<section class="give-wrap" style="background: url(${bg}) 50% 50% no-repeat;background-size: 100% 100%;">
    <div class="form">
        <input type="text" placeholder="请输入手机号" class="form-tel" id="mobile" />
        <button class="sub-btn" id="chain">立即领取</button>
    </div>
</section>	

<input type="hidden" id="cnumber" value="${cnumber }" />
<input type="hidden" id="serialNumber" value="${serialNumber }" />
</body>
</html>

