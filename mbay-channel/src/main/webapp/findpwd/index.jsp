<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="keywords" content="美贝官网">
<meta name="description" content="美贝官网">
<meta name="csrf-param" content="token">
<title>找回密码-美贝直通车</title>
<t:assets/>
<script src="${actx}/js/index/head.js"></script>
<script src="${actx}/js/layer/layer.min.js"></script>
<script src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
	$(function() {
		if("${message}" != ""){
			layer.msg('${message}');
		}
		
		$("#findForm").Validform({
			tiptype:3
		});
	});
</script>
<script>
/**
 * 验证码刷新
 */
var refreshUrl = '<c:url value="/authcode/authImg.mbay"/>';
function authImgRefresh() {
	$("#authcode")[0].validform_valid = "false";
	$("#authImg").attr("src", refreshUrl + "?" + Math.random());
}

function onlyNum(event) {
    event = event || window.event;
	if (event.ctrlKey || event.shiftKey || event.altKey) {
		event.returnValue=false;
		event.preventDefault();
	}
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) {
	if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) {
		event.returnValue=false;
		event.preventDefault(); 
		}
	}
}
</script>
<!--[if lte IE 8]>
    <script src="https://asset.sobug.com/themes/default/js/html5shiv.min.js"></script>
    <script src="https://asset.sobug.com/themes/default/js/respond.min.js"></script>

    <link href="https://asset.sobug.com/themes/default/respond-proxy.html" id="respond-proxy" rel="respond-proxy" />
    <link href="/respond.proxy.gif" id="respond-redirect" rel="respond-redirect" />
    <script src="/respond.proxy.js"></script>
<![endif]-->
<style>[touch-action="none"]{ -ms-touch-action: none; touch-action: none; }[touch-action="pan-x"]{ -ms-touch-action: pan-x; touch-action: pan-x; }[touch-action="pan-y"]{ -ms-touch-action: pan-y; touch-action: pan-y; }[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]{ -ms-touch-action: pan-x pan-y; touch-action: pan-x pan-y; }</style>
</head>
<body class="navbar-padding-top">
	<!--body_begin-->
		
<script type="text/javascript">
</script>

<div class="container">
  <div class="panel-segment tiny center-block">
    <div class="form-title">找回密码</div>
    
          <form id="findForm" class="center-block clearfix" action="<c:url value="/resetpwd/findpwd/selectStrategy.mbay"/>" method="post" role="form">
<input type="hidden" name="token" value="VmFaNlI5LUM8WClbDXtHJC4YDF87WlkuPzE9BDtOZiUlOxgAFgxEJA==">      
      <div class="form-group left-inner-addon field-passwordresetrequestform-id required" style="position:relative">
<i class="glyphicon glyphicon-user"></i>
<input type='text' id='uname' name='username' placeholder="请输入用户名"  title='请输入英文字母，数字或下划线' maxlength="30" class="form-control" ajaxurl="${ctx}/signup/forgetPwdFindUserName.mbay" datatype="*6-30"/>
<div class="error-message-box"></div>
</div>       
            <div class="form-group field-passwordresetrequestform-captcha required" style="position:relative">
<div id="captchaArea" class="clearfix">
	 <input type='text' id='authcode' name='authcode' class="form-control horizontal col-sm-5" placeholder='验证码' maxlength="6" onkeydown="onlyNum(event);" style="ime-mode:Disabled" class="form-control" datatype="n6-6" errormsg="验证码不正确!" ajaxurl="<c:url value='/authcode/valAuthCode.mbay'/>"/>
	<img border=0 style='width:135px;height:50px;' class="captcha pull-right" src="<c:url value="/authcode/authImg.mbay"/>" id="authImg"	onclick="authImgRefresh()" />
</div>

<div class="error-message-box"></div>
</div>      
      <div style="height:20px;"></div>
      <div class="form-group">
       <button type="submit" class="btn btn-submit btn-lg btn-block">找回密码</button>
      </div>

      <div class="form-group">
        <a href="<c:url value='/channel/toLogin.mbay'/>"><small>登录</small></a> | 
        <a href="<c:url value='/signup/enterprise.mbay'/>"><small>注册新账号</small></a>      </div>
      
      </form>      </div>
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
	<div class="side-bar" style="display:none;"><ul><li><a target="_blank" href="/contact"><span class="bg-side s-ic fb"></span>反馈</a></li><li><a href="javascript:void(0);" class="gotop"><span class="bg-side s-ic top"></span></a></li></ul></div>
	<div style="display:none;">
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
		//var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		//document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F44c964f56df9dd6b31ec66a50a3b29e4' type='text/javascript'%3E%3C/script%3E"));
    </script>
	</div>
	<!--bottom_end-->
</body>
</html>