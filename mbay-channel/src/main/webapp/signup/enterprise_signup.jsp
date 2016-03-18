<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="keywords" content="美贝官网">
<meta name="description" content="美贝官网">
<meta name="csrf-param" content="token">
<title>注册</title>
<t:assets/>
<link rel="shortcut icon" href="<c:url value="/images/favicon.png"/>" />
<script src="${actx}/js/index/head.js"></script>
<script src="${actx}/js/layer/layer.min.js"></script>
<script src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
	$(function() {
		if("${message}" != ""){
			layer.msg('${message}');
		}
		
		$("#regForm").Validform({
			tiptype:3,
			datatype:{
				"username":function(gets,obj,curform,regxp){
					//参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用;
					var reg1 = /^([a-zA-Z0-9]|[_]){6,30}$/;
      				var reg2  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 
					if(reg1.test(gets)){return true;}
					if(reg2.test(gets)){return true;}
					return false;
		 
					//注意return可以返回true 或 false 或 字符串文字，true表示验证通过，返回字符串表示验证失败，字符串作为错误提示显示，返回false则用errmsg或默认的错误提示;
				},
			}
		});
		
		$('.mbtx').hover(function() {
			layer.tips($(".mb_tip").html(), this, {
		        style: ['background-color:#0FA6D8; color:#fff', '#0FA6D8'],
		        maxWidth:240,
		        time: 3,
		        closeBtn:[0, true]
		    });
		}, function() {
			$(this).find('.mb_tip').hide();
		});
	});
</script>

<style>[touch-action="none"]{ -ms-touch-action: none; touch-action: none; }[touch-action="pan-x"]{ -ms-touch-action: pan-x; touch-action: pan-x; }[touch-action="pan-y"]{ -ms-touch-action: pan-y; touch-action: pan-y; }[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]{ -ms-touch-action: pan-x pan-y; touch-action: pan-x pan-y; }</style>
<style>
.zorro {
	z-index: 100;
	position: fixed;
	top: 50%;
	left: 50%;
	width: 744px;
	height: 444px;
	padding: 40px;
	margin: -180px 0 0 -330px;
	border-radius: 8px;
	border: solid 2px #666;
	background-color: #fff;
	display: none;
	box-shadow: 0 0 10px #666;
}
.form-group {margin-bottom:20px;}
</style>
</head>
<body class="navbar-padding-top">
<script type="text/javascript">
/**
 * 验证码刷新
 */
var refreshUrl = '<c:url value="/authcode/authImg.mbay"/>';
function authImgRefresh() {
	$("#yz")[0].validform_valid = "false";
	$("#authImg").attr("src", refreshUrl + "?" + Math.random());
}

function showXy(){
	$.layer({
	    type: 1,
	    shade: [0],
	    area: ['auto', 'auto'],
	    title: false,
	    closeBtn: [0, false],
	    shade: [0.5, '#000'],
	    shadeClose: true,
	    fadeIn: 300,
	    border: [0],
	    page: {dom : '.zorro'}
	});
}

function closeLayer(){
	layer.closeAll();
}
</script>
<div class="container">
    <div class="col-sm-7 panel-segment center-block">
        <div class="form-title">欢迎注册账号</div>
        <div class="form-steps clearfix">
            <div class="form-step-3 form-step-finished">
                填写资料
            </div>
            <div class="form-step-seperator form-step-seperator-finished">
            </div>
            <div class="form-step-3 form-step-unfinished">
                 认证
            </div>
            <div class="form-step-seperator form-step-seperator-unfinished">
            </div>
            <div class="form-step-3 form-step-unfinished  form-step-final">
                注册成功
            </div>
        </div>
        <form id="regForm" class="col-sm-9 center-block clearfix" action="<c:url value="/signup/enterprise_signup.mbay" />" method="post" role="form">
            <m:token />
            <div class="form-group field-registerform-type required" style="position:relative;;overflow:auto"></div>
            <input type="hidden" name="RegisterForm[type]" value="">
            <div class="form-group field-registerform-nickname required" style="position:relative">
                <label class="control-label" for="usernumber">邀请码</label>
                <span class='mbtx' style='cursor:pointer;color:blue'> 
                	如何获取邀请码？
					<div class='mb_tip' style='display: none'>
						请联系您的上级代理或者咨询市场部热线021-64186988获取邀请码!
					</div>
				</span>
                <input type='text' maxlength="8" id='usernumber' name="supnumber" class="form-control" placeholder="填写邀请码"  maxlength="8"  datatype="s8-8" errormsg="邀请码不正确！" ajaxurl="${ctx}/signup/authInvitationCode.mbay"/>
            </div>
            <div class="form-group field-registerform-email required" style="position:relative">
                <label class="control-label" for="username">用户名</label>
				<input type='text' id='username' name='username' class="form-control" placeholder="手机号/邮箱/用户名" maxlength="30" datatype="username" errormsg="格式不正确!" ajaxurl="${ctx}/signup/authRegUserName.mbay"/>
            </div>

            <div class="form-group field-registerform-password required" style="position:relative">
                <label class="control-label" for="password">密码</label>
				<input type='password' id='password' name='password' class="form-control" maxlength="20" placeholder='请输入密码' datatype="*6-20"/>
            </div>
            <div class="form-group field-registerform-password2 required" style="position:relative">
                <label class="control-label" for="repassword">确认密码</label>
				<input type='password' id='repassword' maxlength="20" class="form-control" name='repassword' placeholder='请重复输入密码' datatype="*" recheck="password"/>
            </div>
            <div class="form-group clearfix field-registerform-captcha" style="position:relative">
				<label class="control-label" for="yz">验证码</label>
				<div class="clearfix">
				<input type='text' id='yz' name='authcode' class="form-control horizontal col-sm-5" placeholder='验证码' maxlength="6" style="ime-mode:Disabled" datatype="n6-6" errormsg="验证码不正确!" ajaxurl="<c:url value='/authcode/valAuthCode.mbay'/>"/>
				<img border=0 class="captcha pull-right" style='width:135px;height:50px;' src="<c:url value="/authcode/authImg.mbay"/>"id="authImg" onclick="authImgRefresh()" /></div>
			</div>
            <div class="form-group">
                <button type="submit" class="btn btn-submit btn-block">同意协议并注册</button>
                <!-- a标签注释用，用完删掉 -->
            </div>

            <div class="form-group">
                <a href="javascript:showXy();"><small>《美贝直通车协议》</small></a>
            </div>
            
            <!--点击协议弹出  -->
			<div class="zorro" style='display:none;'>
				<a href="javascript:closeLayer();" title="关闭" class="close">x</a>
				<center style='font-size:20px'>协议条款</center>
				<textarea rows="40" cols="75" disabled="disabled"
					style="height: 286px; margin-top: 26px; margin-left: 25px; margin-bottom: 10px; border: 1px solid; width: 600px; font-family: 'Microsoft YaHei'; line-height: 22px; background: #FFF">
					一、声明与承诺
	（一）本协议已对与您的权益有或可能具有重大关系的条款，及对本公司具有或可能具有免责或限制责任的条款用粗体字予以标注，请您注意。您确认，在您注册成为美贝直通车用户以接受本服 务，或您以其他本公司允许的方式实际使用本服务前，您已充分阅读、理解并接受本协议的全部内容，一旦您使用本服务，即表示您同意遵循本协议之所有约定。
	（二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在本网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。
	（三）您保证，在您同意接受本协议并注册成为美贝直通车用户时，您已经年满16周岁，或者您是在中国大陆地区合法开展经营活动或其他业务的法人或其他组织；本协议内容 不受您所属国家或地区法律的排斥。不具备前述条件的，您应立即终止注册或停止使用本服务。您在使用美贝直通车服务时，应自行判断对方是否是完全民事行为能力人并自行决定是否 与对方进行交易或转账给对方等，且您应自行承担与此相关的所有风险</textarea>
			</div>
        </form>
    </div>
</div>

<!--footer_begin-->
<div class="footer-margin"></div>
  <%@ include file="/common/footer.jsp" %>        
<!--footer_end-->

<!--bottom_begin-->
<div class="side-bar" style="display:none;"><ul><li><a target="_blank" href="/contact"><span class="bg-side s-ic fb"></span>反馈</a></li><li><a href="javascript:void(0);" class="gotop"><span class="bg-side s-ic top"></span></a></li></ul></div>
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
   // document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F44c964f56df9dd6b31ec66a50a3b29e4' type='text/javascript'%3E%3C/script%3E"));
</script>
<!--bottom_end-->
</body>
</html>