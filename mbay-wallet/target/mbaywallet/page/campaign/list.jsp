<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>活动</title>
<link rel="stylesheet" href="${ctx}/page/campaign/css/list.css?v=${version}">  
</head>
<body delegate="pop-close">
<header class="shake-list-head">
	<a href="javascript:void(0)" evt-touch="gotoUrl('<c:url value="/web/main/index/ui.mbay?mobile=${mobile }" />')" statistics-text="返回"></a> 
	<span class="shake-login-i">
		<c:if test="${not empty mobile}">
			<c:choose>
				<c:when test="${empty session_user }">
					${fn:substring(mobile, 0, 3) }xxxx${fn:substring(mobile, 8, 11) }
				</c:when>
				<c:otherwise>
					${mobile }
				</c:otherwise>
			</c:choose>
		</c:if>
		<a href="javascript:void(0)" class="shake-login-i0" style="color: #FFF;">活动注意事项</a>
	</span>
</header>
<section id="campaign-list" class="mb-detail-list"></section>
<div id="cmp-pop" style="display: none;"> 
	<div class="mx-popup">
		<span class="mx-xx" evt-touch="$.messager.closeDialog(this);" delegate-id="pop-close">X</span>
		<p class="tp">活动注意事项</p>
		<p>1、活动区内的所有活动，相关参与方式以商家给出的活动规范为准；</p>
		<p>2、用户若原先已参与过活动区内所推荐的活动，则无法重复或多次参与；</p>
		<p>3、用户参与活动区内的活动，所获赠的MB将存储在以手机号码为账号的MB钱包内；</p>
		<p>4、用户参与多个活动，所获赠的MB可累计，不清零；</p>
		<p>5、活动区内的所有活动，由MB钱包推荐用户参与，MB钱包团队拥有活动参与的最终解释权。</p>
		<p class="bom">赛志科技拥有最终解释权.</p>
	</div>
</div>
<script type="text/javascript" src="${ctx }/page/campaign/js/list.js?v=${version}"></script>
</body>
</html>