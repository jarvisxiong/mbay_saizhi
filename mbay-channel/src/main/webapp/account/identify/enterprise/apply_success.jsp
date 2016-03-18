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
<title>完成</title>
<t:assets/>
<script src="${actx}/js/index/head.js"></script>
<style>[touch-action="none"]{ -ms-touch-action: none; touch-action: none; }[touch-action="pan-x"]{ -ms-touch-action: pan-x; touch-action: pan-x; }[touch-action="pan-y"]{ -ms-touch-action: pan-y; touch-action: pan-y; }[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]{ -ms-touch-action: pan-x pan-y; touch-action: pan-x pan-y; }</style>
</head>
<body class="navbar-padding-top">
	<!--body_begin-->
		
<div class="container">
  <div class="col-sm-7 panel-segment center-block">
    <div class="form-title">欢迎注册账号</div>
    <div class="form-steps clearfix">
      <div class="form-step-3 form-step-finished">
        填写资料
      </div>
      <div class="form-step-seperator form-step-seperator-finished">
      </div>
      <div class="form-step-3 form-step-finished">
        邮箱认证
      </div>
      <div class="form-step-seperator form-step-seperator-finished">
      </div>
      <div class="form-step-3 form-step-finished form-step-final">
        注册成功
      </div>
    </div>
    
    <div style="height:50px;"></div>
    
    <div class="center-block clearfix">
      <div class="col-sm-2"><img src="${actx}/images/index_new/yes.png"></div>
      <div class="col-sm-10">
        <div><h4>您的认证信息已经成功提交，审核结果将于1-2个工作日内给您回复 请注意查看结果，谢谢！
</h4></div>
                <div>
<a href='<c:url value="/channel/index.mbay"/>'><button type="submit" style="width:50%" class="btn btn-submit btn-block">返回首页并登录</button></a></div>
              </div>
    </div>
    
    <div style="height:80px;"></div>
  </div>
  
</div>		<!--body_end-->

	<!--footer_begin-->
	<div class="footer-margin"></div>
	<footer class="footer">
         <p>
        Copyright © 2014 www.mbqianbao.com All Right Reseverd 赛志科技（上海）有限公司   </p>
         <p>
           沪ICP备13001400号-4号   </p>
</footer>
	<!--footer_end-->

	<!--bottom_begin-->
	<div class="side-bar" style="display:none;"><ul><li><a target="_blank" href="https://account.sobug.com/contact"><span class="bg-side s-ic fb"></span>反馈</a></li><li><a href="javascript:void(0);" class="gotop"><span class="bg-side s-ic top"></span></a></li></ul></div>
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
		
    </script>
	</div>
	<!--bottom_end-->
</body>
</html>