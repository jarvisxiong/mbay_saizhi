<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接口管理</title>
<style>
   .body {margin-bottom: 30px;}
   .dd{width: 820px;font-size:14px}
   body{font-family: 'Microsoft YaHei'}
   .ddqr{padding-left: 40px;background: url(../images/workimages/llcz.jpg) no-repeat left;color: #35618F;font-size: 18px;height: 36px;line-height:36px;margin:4px 0 10px 0;font-family: 'Microsoft YaHei';}
   h3{background: url(../images/phone.png) no-repeat left;padding-left:60px;font-size: 16px;height: 60px;line-height: 60px;color: #69737b;border-bottom: 1px dashed #e7ecee;margin-bottom: 20px;}
   button{margin-right:30px;width: 120px;height: 35px;line-height: 35px;letter-spacing: 1px;border-radius: 4px;color: #FFF;font-family: 'Microsoft YaHei';background: #FDAC1C;}
   .dd_con{font-size:14px;margin-bottom:20px}
   .pk{margin-top:40px;letter-spacing:1px}
   .pk div{margin-bottom:5px}
   .pk_0{color: #FC8301;margin-right: 10px;}
</style>
<script type="text/javascript" src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".js_sign").bind("click",function(){		
		window.location.href='<c:url value="/trafficSign/productSign.mbay"/>';
	});
	$(".pid_button").bind("click",function(){		
		$(".pk").show();
	});
	$("#download").bind("click",function(){		
		$.fileDownload(actx+'/filedownload/files/traffic_sign_interface_doc.mbay', {
		    failCallback: function (html, url) {
		    	$.messager.alert({ content: "下载失败！" });
		    }
		});	
	});
});
</script>
<script type="text/javascript"  src="${actx}/js/widget/jquery.Sonline.js"></script>
 </head>
 <body>
 <div class='con'>
 <div class='body clearfix'>
    <div class='b_con com_width clearfix' >       
       <div class='left_con'>                
          <div class='ddqr'>接口管理</div>
        <%@ include file="/common/leftcon.jsp" %>
           <div class='dd fr'>
            <h3>流量产品实时订购接口</h3>
            <div class='dd_con'><p>主要功能: 签约通过后,通过API接口对接实现在您的平台上流量营销.</p><p>申请条件: 1.您必须有已建完成的网站,确保提交的资料真实合法.您已签约该产品.</p></div>
            <div>
            	<button id="download">接口说明文档</button>
            	<c:if test="${isExist eq 0 || status eq '2'}">
            		<button class="js_sign">立即签约</button><br/><br/>
            	</c:if>
            	<c:if test="${isExist eq 1}">
            		<c:if test="${status eq '0'}">
            			<br/><br/><span class="pk_0">审核中,请耐心等待!</span>
            		</c:if>
            		<c:if test="${status eq '1'}">
            			<button class="pid_button">查询PID/KEY</button>
            		</c:if>
            		<c:if test="${status eq '2'}">
            			<span class="pk_0">审核未通过,请重新签约!</span><br/><br/>
            			<span class="pk_0">不通过理由：${reason}</span>
            		</c:if>
            	</c:if>
            </div>
            <div class='pk' style="display: none">
               <div><span class='pk_0'>PID:</span><span class='pk_1'>${pid}</span></div>
               <div><span class='pk_0'>KEY:</span><span class='pk_1'>${key}</span></div>
            </div>
          </div>
       </div>
	</div>
</div>
 </div>
</body>
</html>