<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人主页-兑换</title>
<link rel="stylesheet" href="${ctx }/page/traffic/package/css/redeem.css?v=${version}">
</head>
<body> 
<header class="shake-list-head">
	<a href="javascript:void(0)" id="back" statistics-text="返回"></a> 
	<img src="${ctx }/common/img/cloud.png" alt=""> 
	<span class="cloud-mb">${mbayBalance}MB</span>
</header>
<section class="flow-number"> 
	<p>今天已有<span>${attendNums }人</span>成功领取</p>
</section>
<section class="shake-inner-list">
	<a href="javascript:void(0)" class="inner-list-dx unicom">
		<img src="${ctx }/common/img/unicom.png">
		<span class="dx-num">500M</span>
	</a> 
	<a href="javascript:void(0)" class="inner-list-yd mobile">
		<img src="${ctx }/common/img/mobile.png">
		<span class="yd-num">500M</span>
	</a> 
	<a href="javascript:void(0)" class="inner-list-lt telecom">
		<img src="${ctx }/common/img/telecom.png">
		<span class="lt-num">500M</span>
	</a>
</section>
<section>
	<p style="text-align: center;">
		<img src="${ctx }/common/img/line.png" alt="" style="margin: 0 auto;">
	</p>
</section>
<section>
	<p class="btn-wpp">
		<a href="javascript:void(0)" class="zhuan-mb" id="go-on-zhuan-mb">我要继续赚MB</a>
	</p>
</section>
<div class="sus-pop" id="recharging">
	<article class="absolute-center">
		<h2>您正在兑换</h2>
		<p class="sus-pop-p1"></p>
		<p class="sus-pop-p2">兑换说明<span class="sus-pop-p3"></span></p>
		<p class="btn-wpp">
			<a href="javascript:void(0)" class="zhuan-mb redeem">确认兑换</a>
		</p>
	</article>
	<img src="${ctx }/common/img/close2.png" class="close" /> 
	<input type="hidden" id="rechargingId" />
</div>
<div class="sus-pop" id="rechargeSuc">
	<article class="absolute-center">
		<h2>兑换成功</h2>
		<p class="sus-pop-p2">
			您兑换的流量已经成功下发<br>
			<a href="javascript:void(0)" class="sus-pop-a1">关注美贝钱包</a>微信进行查询
		</p>
		<p class="btn-wpp">
			<a href="javascript:void(0)" class="zhuan-mb sure">确认</a>
		</p>
	</article>
	<img src="${ctx }/common/img/close2.png" class="close" />
</div>
<div class="sus-pop" id="accountInsufficient">
	<article class="absolute-center">
		<h2>您的余额不足</h2>
		<div class="zhang-xiaoxiao">
			<img src="${ctx }/common/img/zhang_15.png">
			<div class="zhang-dada">
				<p>今天已经有${attendNums }个小伙伴<br>成功获得500M流量包</p>
			</div>
		</div>
		<p class="btn-wpp">
			<a href="javascript:void(0)" class="zhuan-mb">我要继续赚MB</a>
		</p>
	</article>
	<img src="${ctx }/common/img/close2.png" class="close" />
</div>
<input type="hidden" value="${UNICOM_500 }" id="unicom-code" />
<input type="hidden" value="${MOBILE_500 }" id="mobile-code" />
<input type="hidden" value="${TELECOM_500 }" id="telecom-code" />

<script type="text/javascript" src="${ctx }/page/traffic/package/js/redeem.js?v=${version}"></script>
</body>
</html>