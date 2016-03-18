<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="keywords" content="美贝官网">
<meta name="description" content="美贝官网">
<meta name="csrf-param" content="token">
<title>个人信息-美贝直通车</title>
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
<body class="navbar-padding-top">
	<!--  内容     -->

	<div class="container">
		<div class="panel-segment tiny center-block">
			<div class="form-title">找回密码</div>

			<div style="height: 50px;"></div>

			<div class="center-block clearfix">
				<!-- <div class="col-sm-2"><img src="https://asset.sobug.com/themes/default/images/right.png"></div> -->
				<div class="col-sm-10">
					<div>
						<strong style="margin: auto;">联系电话：4006-210-166</strong>
					</div>
					<div>
						<strong></strong>
					</div>
				</div>

			</div>
			<!--    <div class="an"> <a href="http://mail.sina.com" target="_blank" class="btne btn_confirm" style="opacity: 1; cursor: pointer;">立即查收邮箱</a> </div> -->

			<div style="height: 30px;"></div>

			<div class="col-sm-8 clearfix">
				<div class="form-group">
					<a href="<c:url value='/channel/toLogin.mbay'/>"><small>登录</small></a>
					| <a href="<c:url value='/signup/enterprise.mbay'/>"><small>注册新账号</small></a>
				</div>
			</div>

			<div style="height: 80px;"></div>
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