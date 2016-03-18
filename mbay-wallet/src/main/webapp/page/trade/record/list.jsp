<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>MB明细</title>
<link rel="stylesheet" href="${ctx }/page/trade/record/css/list.css?v=${version}">
</head>
<body>
<header class="shake-list-head">
	<p>
		<a href="javascript:void(0)" evt-touch="gotoUrl('<c:url value="/web/main/index/ui.mbay"/>')" statistics-text="返回"></a>
		<span class="shake-login-i">${mobile }</span>
	</p>
	<a href="javascript:void(0)" id="share-mb" statistics-text="我是MB"><img src="${ctx }/common/img/icon13.png" /></a>
</header>
<section class="mb-detail-list">
	<c:forEach var="r" items="${records }">
		<article class="mb-detail-item" evt-touch="getDetail('${r.serialNumber }')" statistics-text="交易详情-${r.relatedNumber }">
			<div class="detail-item-left ${r.paymentType == 'INCOME' ? 'left1' : r.paymentType == 'EXPENSE' ? 'left2' : 'left3'  }">
				<span>&lt;${r.paymentType == "INCOME" ? "收入" : r.paymentType == "EXPENSE" ? "支出" : "流量" }&gt;</span>
				<p>${r.paymentType == "INCOME" ? "+" : r.paymentType == "EXPENSE" ? "-" : ""  }<c:if test="${not empty r.amount }">${r.amount }MB</c:if></p>
			</div>
			<div class="detail-item-right ${r.paymentType == 'INCOME' ? 'right1' : r.paymentType == 'EXPENSE' ? 'right2' : 'right3'  }">
				<span>${r.createTime }</span>
				<h2>${fn:substring(r.description, 0, fn:indexOf(r.description, "：")) }</h2>
				<p>${fn:substring(r.description, fn:indexOf(r.description, "：") + 1, fn:length(r.description)) }</p>
				<c:if test="${not empty r.relatedNumber }"><p>${r.relatedNumber }</p></c:if> 
			</div>
		</article>
	</c:forEach>
</section>
<section style="text-align: center;">
	<button class="gotoPage" ${ pageInfo.pagenum==1?"disabled='disabled'":''} 
		name="${pageInfo.pagenum==1?1:pageInfo.pagenum-1 }">上一页</button>
	<button>第${pageInfo.pagenum }页</button>
	<button class="gotoPage" ${ pageInfo.pagenum==totalPage?"disabled='disabled'":''}
		name="${pageInfo.pagenum==totalPage?pageInfo.pagenum:pageInfo.pagenum+1 }">下一页</button>
</section>
<div id="share-notice" style="display:none;">
	<div class="mx-popup">
		<span class="close" evt-win-touch="$.messager.closeDialog()">X</span>
		<div class="cont">
			<h2>It's "MB"</h2>
			<h2>互联网+时代的手机流量新用法</h2>
			<h2>参与MB活动，就可立即获取！</h2>
			<h2>MB长期有效，可累积不清零！</h2>
			<h2>手机号码认证，MB安全无忧！</h2>
			<h2>MB兑换流量，每月流量无忧！</h2>
			<h2>MB兑换礼品，生活多姿多彩！</h2>
		</div>
		<!-- <a href="javascript:void(0)">首次分享，立即送你20MB</a> by Richard 2015-0729 -->
	</div>
</div>
<input type="hidden" id="shareTitle" value="${shareTitle }" />
<input type="hidden" id="content" value="${content }" />
<input type="hidden" id="shareLink" value="${shareLink }" />
<input type="hidden" id="shareImage" value="${shareImage }" />
<script type="text/javascript" src="${ctx }/page/trade/record/js/list.js?v=${version}"></script>
</body>
</html>