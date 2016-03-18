<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=0" />
<meta http-equiv="x-dns-prefetch-control" content="on"/>
<meta name="baidu-tc-cerfication" content="c3443c3c5dd359363e2d788ff201ddb4" />
<script type="text/javascript" src="<c:url value="/js/json2.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechat/traffic.css"/>" />
<title>领取流量</title>
<t:assets/>
<script type="text/javascript">
	$(document).ready(function(){
			function validateMobile(){
				if($("#mobile").val()==""){
					$("#errormsg").html("请输入手机号");				
					return false;
				}else{
					var reg=/^1[3|4|5|8][0-9]\d{8}$/;
					if(!reg.test($("#mobile").val())){
						$("#errormsg").html("手机号输入不正确！");
						return false;
					}
					$("#errormsg").html("");
					return true;
				}
			}
			$("#gettraffic").bind("click",function(){
				$("#errormsg").html("");
				if(validateMobile()){
				$.getJSON("<c:url value='/eventtraffic/get.mbay'/>?eventnumber=${eventnumber}&mobile="+ $('#mobile').val(),
						function(json) {
					        if(json.success){
					        	window.location.href="<c:url value='/wechat/trafficsuccess.jsp?ordernumber='/>"+json.content;
					        }else{
								$("#errormsg").html(json.content);					        	
					        }
						});
				}
			
		});
		
		});		
</script>
</head>

<body>  
   <div class='con'>
      <div class='head'>
         <div class='pic' id="trfficbody">
          <input type='text' id="mobile" placeholder='请输入手机号' name='txt' class='txt' />
          <input type='button' name='btn'  id="gettraffic"  class='btn' value='立即获取'/>
         </div>
         <div class='tip'>
          <span id="errormsg"></span>
         </div>
         <div class='suc' id='success'>
          <span>恭喜您！流量获取成功，更多免费流量请关注美贝官方微信！</span>
         </div>
      </div>
      <div class='foot'>       
         <div class='footer_2'>
         	<div class='foot_txt'>
            <p style="height: 10px;"></p>
            <span>1、本次活动可以获得免费手机流量的用户有：<br/>&nbsp;&nbsp;&nbsp;&nbsp;浙江联通手机用户。</span>
            <span>2、上网卡号码、3G、4G融合套餐手机号码,<br/>&nbsp;&nbsp;&nbsp;&nbsp;无法获得免费流量，请谅解。</span>
            <span>3、手机号码欠费用户请先恢复开通状态再申领免费流量。</span>
            <span id='s_3'>4、此次活动最终解释权归“智德仁3G服务中心”。</span>
            </div>
         </div>
       <div class='footer_3'></div>
      </div>
   </div>
</body>


</html>
