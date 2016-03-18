<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>设置密码</title>
</head>
<body>
<article class="mb-form">
	<form class="block forget-psd-1">
		<section class="mb-form-a clearfix">
			<div class="form-item clearfix">
				<input type="password" id="passwd" placeholder="新密码">
			</div>
			<div class="form-item clearfix">
				<input type="password" id="passwd2" placeholder="确定新密码">
			</div>
		</section>
		<section class="mb-form-b clearfix">
			<input type="button" evt-touch="subform()" class="sub-btn" id="sub" value="完成">
		</section>
	</form>
</article>
<input type="hidden" value="${checkcode }" id="checkcode" />
<input type="hidden" value="${type }" id="type" />

<script type="text/javascript" src="${ctx }/page/main/pwd/forget/js/set.js?v=${version}"></script>
</body>
</html>