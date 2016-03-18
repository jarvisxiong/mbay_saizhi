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

.sjhm,.jym,.sfzhm,.an {
	margin-bottom: 30px;
}

.sj,.jym_wz,.sfz_wz {
	width: 52px;
	text-align: right;
	display: inline-block;
	margin-right: 30px;
}

.btn_confirm,.btn_reset {
	padding: 0 5px;
	height: 26px;
	letter-spacing: 1px;
	border-radius: 4px;
	color: #FFF;
	font-family: 'Microsoft YaHei';
	cursor: pointer;
	border: 0;
	outline: medium;
}

.btn_confirm {
	background: #53b92f;
}

.input_txt {
	border: 1px solid #94abc4;
	line-height: 20px;
	padding: 5px 5px;
	border-radius: 4px;
}

.an {
	margin: 50px 0 0 50px;
}

.an .btn_confirm {
	margin-right: 10px;
	width: 100px;
}

.an a {
	color: #53b92f;
}
</style>
<script type="text/javascript">
$(function(){ 
		$("#hqyzma").click(function(){	
			      $(this).hide();
			      $("#yzm").show();
			  	  $("#error").html('').hide();			      
				  $.ajax({				  
			          type:"POST",  
			         url:'/mbaychannel/resetpwd/findpwd/sms_strategy/send_authcode.mbay',
			         async:true
			      });  
		});  
	});

function validAutoCode() {
    var value=$('#yzm').val();
	
	if ($("#yzm").is(":hidden")) {
		return false;
	}	
	// 验证非空
	if (!value) {
		$("#error").text("请输入获取的验证码！").show();
		return false;
	}
	// 验证非空
	if (value.length !=6) {
		$("#error").text("验证码长度为6位！").show();
		return false;
	}
	
	 //验证手机发送的验证码
    //用jquery  ajax验证验证码
	var result = false;
	$.ajax({  
        type:"POST",  
        url: '/mbaychannel/resetpwd/findpwd/validate_authcode.mbay',  
        async:false,   //同步方法，如果用异步的话，flag永远为1  
        data:{'AUTHCODE':value},  
        success: function(msg){
        	result = msg.success;
      		if(!result) {
      			$("#error").html(msg.message).css("display", "inline-block");
      			if (msg.error_code == "RE_OBTAIN") {
      				$("#hqyzma").show();
      				$("#yzm").hide();
      				
      			}
      			$("#yzm").val('');
	  		}
        }
	}); 
	return result;
}
</script>
</head>
<body class="navbar-padding-top">
	<!-- 内容 -->
	<div class="container">
		<div class="panel-segment tiny center-block">
			<div class="form-title">找回密码</div>
			<form id="phoneForm"
				action="<c:url value="/resetpwd/findpwd/sms_strategy/toresetpwd.mbay"/>"
				method="post">
				<div>
					<div class="sjhm">
						<span class="sj">手机号码:</span><span class="hm">${phoneNumber}</span>
					</div>
					<div class="jym">
						<span class="jym_wz">校验码:</span><input type="button" id="hqyzma"
							class="btn_confirm onlynum" name="hqyma" value="获取验证码"
							style="opacity: 1; cursor: pointer;"> <input type="text"
							id="yzm" class="onlynum input_txt" style='display: none;'
							name="AUTHCODE" maxlength="6" autocomplete="off"> <label
							for="yzm" id="error" class="error" style="display: none;"></label>
					</div>

					<!-- <div id="codeinput">
                  <span class='sfz_wz'>输入验证码:</span>
                  <input type='text' id='yzm' name='AUTHCODE'  maxlength="6" onkeydown="onlyNum(event);" style="ime-mode:Disabled"/>  
                  <label for="yzm" id="error" class="error" style="display:none;"></label>  
               </div>   -->
					<div class="an">
						<input type="submit" class="btn_confirm" id="btnn" value="下一步"
							onclick="return validAutoCode();"
							style="opacity: 1; cursor: pointer;"><span><a
							href="javascript:history.go(-1);"
							style="opacity: 1; cursor: pointer;">重新选择验证方式</a></span>
					</div>
				</div>
			</form>
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