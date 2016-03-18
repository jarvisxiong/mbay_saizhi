<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<link rel="shortcut icon" href="<c:url value="/images/favicon.png"/>" /> 
<t:assets/>
<link href="<c:url value="/css/person/2.css"/> " rel="stylesheet"	type="text/css" />
<script type="text/javascript"	src="<c:url value="/js/lib/banbackbrowser.js" />"></script>
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"	src="${actx}/js/lib/jquery.validate.js"></script>
<script type="text/javascript"	src="<c:url value="/css/person/v_1.js" />"></script>
<style type="text/css">
.body {
	z-index: 99;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #000;
	opacity: 0.2;
	filter: alpha(opacity = 20);
	display: none
}

.zorro {
	z-index: 100;
	position: fixed;
	top: 50%;
	left: 50%;
	width: 660px;
	height: 380px;
	padding: 20px;
	margin: -180px 0 0 -330px;
	border-radius: 8px;
	border: solid 2px #666;
	background-color: #fff;
	display: none;
	box-shadow: 0 0 10px #666;
}

.close {
	float: right;
	color: #999;
	text-shadow: 0 1px 0 #ddd;
	text-decoration: none;
	font-size：18px;
}

.close:hover {
	color: #444;
}
</style>
</head>
<body>
<div class='con'>
    <!--注册栏目-->
     <div class='reg_s'>
      <!--进度栏-->
        <div class='jindu'>
           <div class='jd com_width'>
             <div class='jd_box fl jd_1'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_box fl jd_2'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_3'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_4'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_5'></div>             
             <div class='wenzi_1'>阅读提示</div>
             <div class='wenzi_2'>注册</div>
             <div class='wenzi_3'>注册已完成</div>
             <div class='wenzi_4'>个人信息</div>
             <div class='wenzi_5'>完成</div>
             
           </div>
        </div>
      <!--个人注册-->
        <div class='zhuce'>
           <form id='regForm' action='<c:url value="/channel/signup/personal_signup.mbay" />' method="post" autocomplete="off">
            <mbay:token/>
              <h2 class='zc_tit'>个人直通车注册</h2>
              <div class='use'>
              <label for='uname'><b>* </b>用户名：</label>
              <input type='text' id='uname' autocomplete="off" name='username' placeholder="手机号/邮箱/用户名"  title='请输入数字/字母或下划线'/>            
              </div>
              <div class='mima'>
              <label for='password'><b>* </b>密码：</label>
              <input type='password' id='password' name='password' maxlength="20" placeholder='请输入密码' title='试试字母、数字、标点的组合吧'/>
              </div>
              <div id="div2"></div>
              <div class='chongfu'>
              <label for='repassword'><b>* </b>确认密码：</label>
              <input type='password' id='repassword' maxlength="20" name='repassword' placeholder='请重复输入密码'/>
              </div>
              <div class='yanzhen clearfix'>
              <div class='fl yzm'>
              <input type='text' class="verifycode" id='authcode' name='authcode' placeholder='验证码'/>
              </div><div class='ma fl'><img border=0 src="<c:url value="/authcode/authImg.mbay"/>" id="authImg"	onclick="authImgRefresh()" /></div>
              </div> 
              <div class="yd">
                   <input  id="xuan" type='checkbox'  checked="checked"/><label  for='xuan'>同意</label><a class="open" href="javascript:">《美贝直通车服务协议》</a>
                 </div>
              <c:if test="${message != ''}"><div style="color:red;text-align:center;margin-top:10px;">${message}</div></c:if>
              <div class='anniu'>
               <a href='<c:url value="/channel/signup/index.mbay"/>'><input id='fanhui' type='button' name='btn_1' value='上一步'/> </a>
              <input id='tijiao' type='submit' name='sub' value='提交' />
              </div>
           </form>
        </div>        
     </div>
    <!--尾部-->
        <%@ include file="/common/footer.jsp" %>        
    
 </div>
     <!--点击协议弹出  -->
	<div class="zorro">
		<a href="javascript:;" title="关闭" class="close">x</a>
		<h2>协议条款</h2>
		<textarea rows="40" cols="75" style="font-size:12px;height: 286px; margin-top: 26px; margin-left:25px; margin-bottom:10px; border: 1px solid;width:600px;font-family:'Microsoft YaHei';line-height:22px;background:#FFF" disabled="disabled">
声明与承诺
（一）本协议已对与您的权益有或可能具有重大关系的条款,及对本公司具有或可能具有免责或限制责任的条款用粗体字予以标注,请您注意.您确认,在您注册成为美贝直通车用户以接受本服务,或您以其他本公司允许的方式实际使用本服务前,您已充分阅读理解并接受本协议的全部内容,一旦您使用本服务,即表示您同意遵循本协议之所有约定.
（二）您同意,本公司有权随时对本协议内容进行单方面的变更,并以在本网站公告的方式予以公布,无需另行单独通知您;若您在本协议内容公告变更后继续使用本服务的,表示您已充分 阅读、理解并接受修改后的协议内容,也将遵循修改后的协议内容使用本服务;若您不同意修改后的协议内容,您应停止使用本服务.
（三）您保证,在您同意接受本协议并注册成为美贝直通车用户时,您已经年满16周岁,或者您是在中国大陆地区合法开展经营活动或其他业务的个人,法人或其他组织;本协议内容不受您所属国家或地区法律的排斥.不具备前述条件的,您应立即终止注册或停止使用本服务.您在使用美贝直通车服务时,应自行判断对方是否是完全民事行为能力人并自行决定是否与对方进行交易或转账给对方等,且您应自行承担与此相关的所有风险.
		</textarea>
		<center>
			<button id="agree"  class="closebtn" >同意协议</button>
		</center>
	</div>
	<div class="body"></div>
 
 <script type="text/javascript"> 
 
	/**
	 * 验证码刷新
	 */
	var refreshUrl = '<c:url value="/authcode/authImg.mbay"/>';
	function authImgRefresh() {
		$("#authImg").attr("src", refreshUrl + "?" + Math.random());
	}	
	
	$("#xuan").click(function(){		
		  if($(this).attr("checked")){
			  $("#tijiao").removeAttr("disabled");
			  $("#tijiao").css("background-image","url(../../images/person/images/icons/btn.jpg)");
			  $('#xuan').attr('checked',true);
		  }else{		
			 $("#tijiao").css("background-image","url(../../images/disabled.jpg)");
			   $("#tijiao").attr("disabled","disabled"); 
		  }	
	});

 </script>
 
 <script type="text/javascript">
 /**
	 *弹出层使用
	 */
	$(document).ready(function() {
		$('.open').click(function() {
			$('.body').fadeIn(100);
			$('.zorro').slideDown(500);
		});
		$('.close').click(function() {
			$('.body').fadeOut(100);
			$('.zorro').slideUp(500);
			 $('#xuan').attr('checked',false);
			 $("#tijiao").attr("disabled","disabled"); 
			 $("#tijiao").css("background-image","url(../../images/disabled.jpg)");
		});
		$('.closebtn').click(function() {
			$('.body').fadeOut(100);
			$('.zorro').slideUp(500);	
			$("#tijiao").removeAttr("disabled");
			 $("#tijiao").css("background-image","url(../../images/person/images/icons/btn.jpg)");
		    $('#xuan').attr('checked',true);
		});

	});
	
 </script>
 
 
</body>
</html>
