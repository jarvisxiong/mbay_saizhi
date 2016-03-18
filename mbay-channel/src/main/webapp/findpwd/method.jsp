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
<title>找回密码-美贝直通车</title>
<t:assets />
<script src="${actx}/js/index/head.js"></script>
<!--[if lte IE 8]>
    <script src="https://asset.sobug.com/themes/default/js/html5shiv.min.js"></script>
    <script src="https://asset.sobug.com/themes/default/js/respond.min.js"></script>

    <link href="https://asset.sobug.com/themes/default/respond-proxy.html" id="respond-proxy" rel="respond-proxy" />
    <link href="/respond.proxy.gif" id="respond-redirect" rel="respond-redirect" />
    <script src="/respond.proxy.js"></script>
<![endif]-->
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
</style>
<style>
.fr {
	float: left;
}

.zj_con {
	height: 90px;
}

.jc {
	padding-left: 40px;
	height: 70px;
	background: #EDFED0;
	border: 1px solid #E1E1CA;
	margin-bottom: 40px;
}

.jc_con {
	padding-left: 45px;
	height: 70px;
	background: url(../images/index_new/yes.png) no-repeat left;
	line-height: 70px;
	letter-spacing: 1px;
	font-size: 16px;
}

.zj_con {
	border: 1px solid #E8E8E8;
	height: 90px;
	background: #FFF;
	margin-bottom: 30px;
	padding-left: 40px;
}

.zj {
	border: 1px solid #E8E8E8;
	height: 90px;
	background: #FFF;
	margin-bottom: 30px;
}

.zj_left {
	width: 360px;
	height: 90px;
}

.zj_right {
	height: 90px;
	width: 180px;
}

.zj_right input {
	width: 100px;
	cursor: pointer;
}
</style>
</head>
<body class="navbar-padding-top">
	<div class="container">
		<div class="panel-segment tiny center-block" style="width: 450px;">
			<div class="form-title">找回密码</div>

			<div class="jc">
				<div class="jc_con">我们提供以下方式供您选择</div>
			</div>
			<div class="zj_con phone">
				<div class="zj_left fl">
					<h4>通过“手机验证码”</h4>
					<div class="zj_l_con">如果您的${phonenum}手机还正常使用，请选择此方式</div>
					<div class="zj_right fr">
						<button type="button" style="width: 50%; padding: 2px 5px;"
							class="btn btn-submit btn-block"
							onclick="window.location.href='<c:url value="/resetpwd/findpwd/sms_strategy.mbay"/>'">立即找回</button>
					</div>
				</div>
			</div>
			<div class="zj">
				<div class="zj_con email">
					<div class="zj_left fl">
						<h4>通过“电子邮箱”</h4>
						<div class="zj_l_con">如果您的${email}邮箱还在使用，请选择此方式</div>
						<div class="zj_right fr">
							<button type="button" style="width: 50%; padding: 2px 5px;"
								class="btn btn-submit btn-block"
								onclick="window.location.href='<c:url value="/resetpwd/findpwd/email_strategy.mbay"/>'">立即找回</button>
						</div>
					</div>

				</div>
			</div>
			<div class="zj">
				<div class="zj_con rz">
					<div class="zj_left fl">
						<h4>通过人工服务</h4>
						<div class="zj_l_con">可以联系客服，及时处理</div>
						<div class="zj_right fr">
							<button type="button" style="width: 50%; padding: 2px 5px;"
								class="btn btn-submit btn-block"
								onclick="window.location.href='<c:url value="/resetpwd/findpwd/artificia_retrieve.mbay"/>'">立即找回</button>
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>
	<!--body_end-->

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
	    jQuery(document).ready(function(){ 
	    	function showBar(){
	    		if (jQuery(this).scrollTop() > 100) {
	                jQuery('.side-bar').fadeIn();
	            } else {
	                jQuery('.side-bar').fadeOut();
	            }
	    	}

	    	showBar();
	        jQuery(window).scroll(showBar); 
	        jQuery('.gotop').click(function(){
	            jQuery("html, body").animate({ scrollTop: 0 }, 200);
	            return false;
	        });
	    });

    </script>
	</div>
	<!--bottom_end-->
</body>
</html>