<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta content="telephone=no" name="format-detection">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<title>平安好房输入手机号</title>
<t:mobile-assets/>
<link rel="icon" href="${actx}/images/favicon.png" />
<link rel="stylesheet" href="${ctx}/particularcase/pingan/trafficred/css/import.css" />
<script type="text/javascript" src="${ctx}/particularcase/pingan/trafficred/js/new_file.js" ></script>
<script>
$(function(){
	if("${errorMsg}" != ""){
		$.messager.remind({content : "${errorMsg}"});
	}
});
</script>
</head>
<body>
	<section class="import_bg">
		<p class="bg_logo"><img src="${ctx}/particularcase/pingan/trafficred/img/bg_logo.png"></p>
		<!--上部分文字说明-->
		<article class="text1">
			<p class="txt1">平安好房是平安集团旗下领先的</p>
			<p class="txt1">“互联网+房地产+金融”电商平台</p>
			<p class="imgtxt txt1"><img src="${ctx}/particularcase/pingan/trafficred/img/enroll.png"></p>
		</article>			
		<!--输入表单-->
		<article class="import_form">
			<form action="<c:url value='/pingan/trafficred/otp.mbay'/>" method="GET" id="pinganForm">
				<input type="tel" name="mobile" class="cell_phone_number" placeholder="在此输入手机号" maxlength="11" autocomplete="off"/>
				<input type="button" class="next_step" onClick="submit_before()" value="立即参与"/>
			</form>
		</article>
		<!--下部分文字说明-->
		<article class="text2">
			<p class="txt_tit">活动注意事项:</p>
			<p class="txt2">1.点击立即参与后请在验证页面输入”平安好房”官方发送的短信验证码。</p>
			<p class="txt2">2.活动期间同一个手机号限领取一次。</p>
			<p class="txt2">3.已经注册过”平安好房”的用户本次活动不能获赠MB大礼包。</p>
			<p class="txt2">4.注册成功后用户会随机获赠10MB~100MB不等的MB流量，您可以在您的MB钱包进行查看。</p>
		</article>
	</section>
</body>
</html>