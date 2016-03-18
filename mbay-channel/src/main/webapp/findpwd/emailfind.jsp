<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人信息-美贝直通车</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<t:assets />
<script src="${actx}/js/index/head.js"></script>
<script type="text/javascript" src="${actx}/js/findpwd/send.js"></script>
<script type="text/javascript" src="${actx}/wro/emailfind.js"></script>
<script type="text/javascript">
	function resend() {
		$.post("<c:url value='/resetpwd/findpwd/resend_email.mbay' />", null,
				function(data) {
					if (data == "success") {
						$.messager.alert({ content: "发送成功请注意查收！" });
					}
				});
	}
</script>
<style>
[touch-action="none"] {
	-ms-touch-action: none;
	touch-action: none;
}

[touch-action="pan-x"] {
	-ms-touch-action: pan-x;
	touch-action: pan-x;
}

[touch-action="pan-y"] {
	-ms-touch-action: pan-y;
	touch-action: pan-y;
}

[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]
	{
	-ms-touch-action: pan-x pan-y;
	touch-action: pan-x pan-y;
}

.an {
	margin-top: 20px;
	text-align: center;
}

.btn_confirm,.btn_reset {
	padding: 3px 5px;
	height: 26px;
	letter-spacing: 1px;
	border-radius: 4px;
	color: #FFF;
	font-family: 'Microsoft YaHei';
	cursor: pointer;
}

.btn_confirm {
	background: #53b92f;
}
</style>
</head>
<body>
	<div class="container">
		<div class="panel-segment tiny center-block">
			<div class="form-title">找回密码</div>

			<div style="height: 50px;"></div>

			<div class="center-block clearfix">
				<div class="col-sm-2">
					<img src="${actx}/images/index_new/yes.png">
				</div>
				<div class="col-sm-10">
					<div>
						<strong>邮件已发送到您的邮箱 <span>${showemail}</span>。请按邮箱中的提示操作，完成身份验证。
						</strong>
					</div>
					<div>
						<strong>请尽快登陆账号并修改您的密码</strong>
					</div>
				</div>

			</div>
			<div class="an">
				<a href="http://mail.sina.com" target="_blank"
					class="btne btn_confirm" style="opacity: 1; cursor: pointer;">立即查收邮箱</a>
			</div>

			<div style="height: 30px;"></div>

			<div class="col-sm-8 clearfix">
				<div class="form-group">
					<a href="login1.html"><small>登录</small></a> | <a href="zhuce1.html"><small>注册新账号</small></a>
				</div>
			</div>

			<div style="height: 80px;"></div>
		</div>
	</div>

	<!--   内容 -->

	<!--footer_begin-->
	<div class="footer-margin"></div>
	<footer class="footer">
		<p>Copyright © 2014 www.mbqianbao.com All Right Reseverd
			赛志科技（上海）有限公司</p>
		<p>沪ICP备13001400号-4号</p>
	</footer>
	<!--footer_end-->

	<!--bottom_begin-->
	<div class="side-bar" style="display: none;">
		<ul>
			<li><a target="_blank" href="/contact"><span
					class="bg-side s-ic fb"></span>反馈</a></li>
			<li><a href="javascript:void(0);" class="gotop"><span
					class="bg-side s-ic top"></span></a></li>
		</ul>
	</div>
	<div style="display: none;">
		<script type="text/javascript">
			jQuery(document).ready(function() {
				function showBar() {
					if (jQuery(this).scrollTop() > 100) {
						jQuery('.side-bar').fadeIn();
					} else {
						jQuery('.side-bar').fadeOut();
					}
				}

				showBar();
				jQuery(window).scroll(showBar);
				jQuery('.gotop').click(function() {
					jQuery("html, body").animate({
						scrollTop : 0
					}, 200);
					return false;
				});
			});
		</script>
	</div>
	<!--bottom_end-->
</body>
</html>
