<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="美贝直通车" />
<meta name="description" content="美贝直通车" />
<link rel="icon" href="<c:url value="/images/favicon.png"/>" />
<title>兑换流量</title>
<t:mobile-assets />
<script type="text/javascript"
	src="${ctx}/js/promotion_campaign/redeemPromotion.js?v=14423"></script>
<style type="text/css">
html,body,.mb-wrap {
	height: 100%;
	width: 100%;
}
.mb-wrap {
	background: url(${ctx}/images/campaign_common/mobile/redeem/content-bg.jpg) 50% 50% no-repeat;
	background-size: cover;
}
.head-big {
	display: block;
	margin: 0 auto 1.6rem auto;
}
.content-wrap {
	width: 10rem;
	margin: 0 auto;
}
.content-wrap input {
	border: 1px solid #D8D8D8;
	color: #9B9B9B;
	text-indent: 0.5em;
	height: 1.4rem;
	width: 100%;
	line-height: 100%;
	font-size: .6rem;
	border-radius: .2rem;
	margin-top: 0;
}
.ct-item {
	height: 1.2rem;
	position: relative;
}
.ct-item a {
	display: block;
	height: 1.2rem;
	width: 3.6rem;
	position: absolute;
	top: 0;
	font-size: .6rem;
	text-align:center;
	line-height:1.26rem;
    font-weight: bold;
    color:#FFF;
	/* text-indent: -9999px; */
}
.ct-item .duihuan-btn {
	left: 0;
	background: url(${ctx}/images/campaign_common/mobile/redeem/btn2.png) 50% 50% no-repeat;
	background-size: cover;
}
.mb-btn {
	width:3.6rem;
	height:1.2rem;
}
.content-wrap p {
	display: block;
	color: red;
	left: 0;
	font-size: .48rem;
	text-indent: 0.5em;
	height: 1.2rem;
	line-height: 1.2rem;
	font-family: "微软雅黑"
}
.ct-item .shuom-btn {
	right: 0;
	background: url(${ctx}/images/campaign_common/mobile/redeem/btn1.png) 50% 50% no-repeat;
	background-size: cover;
	color:#000;
}
.m-copy {
	width: 100%;
	text-align: center;
	margin-bottom: .4rem;
	margin-top: .4rem;
}
.m-copy .duihuan_code {
	border: 1px solid #ccc;
	border-radius: .4rem;
	-webkit-user-select: initial;
	-webkit-tap-highlight-color: #ccc;
	font-size: .88rem;
	padding: .28rem 3.04rem;
}
</style>
<script type="text/javascript">
	/**
	 * 验证码刷新
	 */
	var refreshUrl = '<c:url value="/authcode/authImg.mbay"/>';
	function authImgRefresh() {
		$("#authImg").attr("src", refreshUrl + "?" + Math.random());
	}
</script>
</head>
<body>
	<div class="mb-wrap">
		<c:choose>
			<c:when test="${redeemBackphoto != ''}">
				<img class="head-big" src="${redeemBackphoto}">
			</c:when>
			<c:otherwise>
				<img class="head-big"
					src="${ctx}/images/campaign_common/tou-big.png">
			</c:otherwise>
		</c:choose>
		<div class="content-wrap">
			<form id="form">
				<input type="hidden" id="campaignNumber" value="${campaignNumber}">
				<input type="hidden" id="captcha" value="false">
				<input type="text" maxlength="16" id="dhm" placeholder="请输入兑换码">
				<p id="dhm_error"></p>
				<input type="tel" maxlength="11" id="sjh" placeholder="请输入手机号码">
				<p id="sjh_error"></p>
				<div id="yzm_div" style="display:none;">
					<input type="text" maxlength="6" id="yzm" placeholder="请输入验证码" style="width:50%">
					<img border=0 src="<c:url value="/authcode/authImg.mbay"/>" id="authImg" 
						onclick="authImgRefresh()" style="width:48%"/>
					<p id="yzm_error"></p>
				</div>
				<div id="hxm_div" style="display:none;" class="m-copy">
					<p id="hxm" class="duihuan_code"></p>
					<p></p>
				</div>
				<div class="ct-item">
					<a href="#" class="duihuan-btn">${redeemText}</a> 
					<a href="${ctx}/promotionCampaign/redeem_code/redeem_instruction.mbay"
						target="_blank" class="shuom-btn">${introductionText}</a>
					<%-- <a href="${ctx}/promotion_campaign/mobile/mbay_redeem_instruction.jsp"
						target="_blank" class="shuom-btn">${introductionText}</a> --%>
				</div>
			</form>
		</div>
	</div>
</body>
</html>