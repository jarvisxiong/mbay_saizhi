<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta http-equiv="Access-Control-Allow-Origin" content="*"/>

<meta http-equiv="x-dns-prefetch-control" content="on"/>
<meta name="baidu-tc-cerfication" content="c3443c3c5dd359363e2d788ff201ddb4" />
<script type="text/javascript" src="<c:url value="/js/json2.js" />"></script>
<t:assets/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechat/template.css"/>" />
<title>领取流量</title>
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
		$("#gettraffic").attr("disabled","disabled");
		$.getJSON("<c:url value='/eventtraffic/get.mbay'/>?eventnumber=${eventnumber}&mobile="+ $('#mobile').val(),
				function(json) {
			        if(json.success){
			        	window.location.href="<c:url value='/wechat/trafficsuccess.jsp?ordernumber='/>"+json.content;
			        }else{
						$("#errormsg").html(json.content);					        	
			        }
			        $("#gettraffic").removeAttr("disabled");
				});
		}		
	});
});		
</script>
</head>

<body>
<div class='con'>
    <div class='body3'>
       <div class='logo'>
        <img src='<c:url value="/images/wechat/logo.png"/>'/>
       </div>
       <div class='wenzi'>
        <img src='<c:url value="/images/wechat/img_1_2.png"/>'/>
       </div>
       <div class='inp_text' id="trfficbody">
         <input type='text' id="mobile" class='txt' placeholder="请输入手机号"/>
       </div>
       <div class='tip'>
          <span id="errormsg"></span>
       </div>
       <div class='suc' id='success'>
          <span>恭喜您！流量获取成功，更多免费流量请关注美贝官方微信！</span>
       </div>  
       <div class='ser'>
         <div class='service3'>
            <a href='#'><div class='ser_box fl' id="gettraffic"><div class='box_ico'><img src='<c:url value="/images/wechat/ljdh.png"/>'/></div><div class='box_wz_1'>立即兑换</div></div></a>
            <a href='<c:url value="/wechat/trafficQuery.jsp" />'><div class='ser_box fl'><div class='box_ico'><img src='<c:url value="/images/wechat/dhsm.png"/>'/></div><div class='box_wz_2'>兑换说明</div></div></a>
         </div>
       </div>
    </div>
 </div>
</body>


</html>
