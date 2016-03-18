<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="keywords" content="美贝官网">
<meta name="description" content="美贝官网">
<meta name="csrf-param" content="token">
<title>美贝直通车首页</title>
<t:assets />
<link rel="icon" href="${actx}/images/favicon.png" />
<script src="${actx}/js/index/head.js"></script>
<script src="${actx}/js/lib/secure.js"></script>
<script src="${actx}/js/login/login.js"></script>
<link rel="stylesheet" href="${actx}/css/share.css">
<link rel="stylesheet" href="${actx}/css/form.css">
<script src="jquery-1.10.1.min.js"></script>
<style>
.inner-form-item {
	width: 100%;
	position: relative;
	height: 35px;
	margin-bottom: 20px;
}

.glyphicon {
	top: 29px;
	left: 8px;
	opacity: 0.6;
}

.inner-form-item input {
	padding: 6px 39px 3px 25px;
}

.f-yanz {
	top: 18px;
}

#showMsg {
	top: -8px;
}

.f-btn {
	margin-top: 20px;
}

element.style {
	display: block;
	height: 3px;
	margin-top: -5px;
}

.center-block {
	margin-top: -30px;
}

.help-block {
	margin-top: -4px;
	color: red;
	font-size: 12px;
}

label {
	color: #9d9d9d;
	font-size: 12px;
}

.small,small {
	font-size: 100%;
}

#subbtn:hover {
	color: #FFF;
}

.f-yanz img {
	background: #C8C6C7;
	border-radius: 4px;
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
	$(function() {
			var errormsg = '${message}';
			if (errormsg != "") {
			$("#showMsg").text(errormsg);//显示提示信息
	}
	});
</script>
</head>
<body>
	<div id="head"></div>
	<div class="mb-form">
		<div class="mb-form-inner">
			<h2 class="f-title">欢迎登录账号</h2>
			<form id="loginForm" class="center-block"
				action="<c:url value="/channel/login.mbay" />" method="post"
				role="form">
				<div class="inner-form-item">
					<i class="glyphicon glyphicon-user"></i> <input type="text"
						id="username" class="form-control" name="username"
						value="${username}" placeholder="请输入手机/邮箱/用户名" maxlength="30">
					<div class="error-message-box"></div>
				</div>
				<div class="inner-form-item">
					<i class="glyphicon glyphicon-lock"></i> <input type="password"
						id='encryptionPassword' class="form-control" placeholder="请输入密码"
						maxlength="20"> <input name='password' type="hidden" />
					<div class="error-message-box"></div>
				</div>
				<div class="inner-form-item f-yanz">
					<input type="text" placeholder="验证码" class='yanzhenma' id="captcha"
						maxlength="6" name='authcode' onkeydown="onlyNum(event);"
						style="ime-mode: Disabled"> <span><img border=0
						class="form-yanz" src="<c:url value="/authcode/authImg.mbay"/>"
						id="authImg" onclick="authImgRefresh()" /></span>
				</div>
				<div class="innet-f-chk clearfix">
					<label style='margin-top: 10px'> <c:if
							test="${cookie.username.value ne '' and cookie.username.value ne null}">
							<input type="checkbox" id="loginform-rememberme" name="remeber"
								checked="checked"> 记住用户名
			   </c:if> <c:if
							test="${cookie.username.value eq '' or cookie.username.value eq null }">
							<input type="checkbox" id="loginform-rememberme" name="remeber"
								id="f-chk" style="width: auto;"> 记住用户名
			   </c:if>
					</label>
					<div class="help-block" style='height: 3px;'>
						<span id="showMsg"></span>
					</div>
				</div>
				<button id="subbtn" type="button"
					class="btn btn-submit btn-lg btn-block f-btn">登录</button>
			</form>
			<div class="f-msg">
				<a href="<c:url value='/resetpwd/findpwd/tofind.mbay'/>"><small>忘记密码</small></a>
				| <a href="<c:url value='/signup/enterprise.mbay'/>"><small>注册新账号</small></a>
			</div>
		</div>
	</div>
	<!-- footer begin -->
	<%@include file="/common/footer.jsp"%>
	<!-- footer end -->
</body>
</html>


