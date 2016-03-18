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
<title>抽奖</title>
<link rel="stylesheet" href="${actx}/traffic_red/mobile/shake/css/raffle.css?v=${version}">
<script type="text/javascript" src="${actx}/traffic_red/mobile/shake/js/raffle.js?v=${version}"></script>
</head>
<body>
<article class="mb-form">
 <form id="raffleForm" class="block forget-psd-1" action="<c:url value='/'/>" method="get">
		<input type="hidden" id="cNumber" value="${cNumber}"/>
        <section class="mb-form-a clearfix">
            <div class="form-item clearfix">
            	<input type="tel" placeholder="${mobile}" readonly="readonly"/>
            </div>
            <div class="form-item clearfix">
            	<input id="yzm" type="text" value="" placeholder="请输入手机收到的验证码" maxlength="6"/>
				<a href="javascript:getYzm();" id='hqyzma' class="form-code">发送验证码</a>
			</div>
        </section>
        <p class="voice" id="voicep">
			<span>收不到验证码？使用</span> 
			<a href="javascript:voiceYzm();" id="voiceyzm">语音验证码</a>
		<p>
        <section class="mb-form-b clearfix">
            <button type="button" evt-touch="submit_before()">立即抽奖</button>
        </section>
    </form>
</article>
</body>
</html>