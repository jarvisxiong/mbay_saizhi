<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="美贝直通车" />
<meta name="description" content="美贝直通车" />
<link rel="icon" href="<c:url value="/images/favicon.png"/>" />
<title>兑换码领取</title>
<t:mobile-assets />
<style type="text/css">
html,body,.mb-wrap {
	height: 100%;
	width: 100%;
}    
.mb-wrap {
	background: url(../../../images/campaign_common/mobile/redeem/content-bg.jpg) 50% 50% no-repeat;
	background-size: cover;
}
.head-big {
	display: block;
	margin: 0 auto 1.6rem auto;
}
.content-wrap {
	width: 10.68rem;
	position: relative;
	margin: 0 auto;
	bottom: .8rem;
}

.page-content {
	overflow: hidden;
	-webkit-box-flex: 100;
}

.m-contactUs {
	width: 100%;
	text-align: center;
	margin-bottom: .8rem;
	font-size: .7rem;
}

.m-link .textLink {
	width: 100%;
	height: 1.8rem;
	line-height: 1.8rem;
	color: #000;
	text-align: center;
	text-decoration: none;
	background: #ffb504;
	border-radius: 1px;
	display: inline-block;
}

.m-link .textLink2 {
	background: gray;
	color: #FFF;
}

.m-copy {
	width: 100%;
	text-align: center;
	margin-bottom: .4rem;
	margin-top: .4rem;
}

.m-copy .duihuan_code {
	border: 1px solid #ccc;
	border-radius: 1px;
	-webkit-user-select: initial;
	-webkit-tap-highlight-color: #ccc;
	font-size: 22px;
	/* 	padding: 7px 78px; */
 	display: block;
  	width: 99%;
 	height: 1.8rem;
  	line-height: 1.8rem; 
}

.zi {;
	margin: 0 auto;
	text-align: center;
}

.zi p {
	font-size: .56rem;
	color: red;
}

.redeem_code {
	width:100%;
	display: none;
}
.redeem_code_message>p{padding-left:.6rem; text-indent:-0.6rem;font-size:.48rem;line-height:.8rem;}
.redeem_code_message>p:nth-of-type(1){font-size:.52rem;font-weight: bold;margin-top:.8rem;}
.text_area_content{
	border: 0;
	-webkit-user-select: initial;
	-webkit-tap-highlight-color: #ccc;
	font-size: .4rem;
	padding: 0;
	padding-top: .2rem;
	width: 100%;
	border-radius: 1px;
	height: 4.56rem;
	resize:none;
}
.content-wrap .thks {
	font-size: 1rem;
}
</style>
<c:if test="${!partaked}">
	<script type="text/javascript">
	$(function() {
		$(".textLink").one("click",function(){
            $(this).addClass("textLink2");
            $.post("${ctx}/promotionCampaign/redeem_code/generate_code.mbay", {
				'campaignNumber' : '${campaignNumber}',
				'method' : '${method}'
			}, function(json) {
				if (json.success) {
					$(".duihuan_code").text(json.code);
					//$(".m-link").hide();
					$(".redeem_code").show();
				} else {
					$.messager.remind({content:json.message});
				}
			});
        });
		
		$(".redeem_code").hide();
	});
	</script>
</c:if>
</head>
<body>
	<div class="mb-wrap">
		<c:choose>
			<c:when test="${backphoto != ''}">
				<img class="head-big" src="${backphoto}">
			</c:when>
			<c:otherwise>
				<img class="head-big" src="${ctx}/images/campaign_common/tou-big.png">
			</c:otherwise>
		</c:choose>

		<div class="content-wrap">
			<c:choose>
				<c:when test="${partaked}">
					<h2 class="thks">您已参与过此活动，感谢您的参与！</h2>
					<p></p>
				</c:when>
				<c:otherwise>
					<section class="page-content">
						<div class="m-contactUs m-link">
							<a href="javascript:void(0)" class="textLink">${gotText}</a>
						</div>
						<div class="m-copy redeem_code">
							<span class="duihuan_code"></span>
						</div>
						<div class="zi redeem_code">
							<p>点击长按复制兑换码</p>
						</div>
						<div class="redeem_code_message">
							<!-- <p>玩转流量口令:</p>
							<p>1、点击“获取免费流量兑换码”按钮。</p>
							<p>2、长按复制兑换码（要牢牢记住呦！）。</p>
							<p>3、长按识别上图中的二维码。</p>
							<p>4、在二维码对应的公众号中找到“免费流量”——“流量口令”。</p>
							<p>5、在兑换页面粘贴兑换码，输入手机号，立刻获取流量。</p>	 -->	
							<textarea class="text_area_content" readonly="readonly">${introduction}</textarea>			
						</div>
					</section>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>