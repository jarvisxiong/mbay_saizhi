<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>写信息</title>
<link href="${actx}/wro/${version}/writemessage.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/writemessage.js"></script>
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
$("#mbayaccount").bind("blur", function() {
	//验证输入值是否存在下级代理列表中如果存在就显示	
	var mbayaccount = $("#mbayaccount").val().trim();
	if (mbayaccount != "") {
		$("#accountvalue").val(mbayaccount);		
		var url = absurl + "/channel/checkSendNumber.mbay";
		$.get(url, {usernumber : mbayaccount
		}, function(data) {				
			$("#mbayaccount").val(data.channel_name);
		});
	}
});

$("#mbayaccount").focus(function() {
	if($("#mbayaccount").val().trim()!=""){		
		var accountvalue = $("#accountvalue").val();
		alert(accountvalue);
			$("#mbayaccount").val(accountvalue);				
	}	
});

$('#subForm').click(function(){	
	$("#msgForm").submit();	
	
});
$("#msgForm").Validform();
}); 
</script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
          <!--邮箱左边索引-->
             <%@ include file="/emailMessage/msgleft.jsp" %>
          <!--邮箱右边-->
          <form id="msgForm" action="<c:url value='/message/createMsg.mbay'/>" method="post"> 
                <div class='email_con fl'>
                <div class='xx_box'>
                   <span class='xx_sec_1'>收件人:</span>
                    <input  name="usernumber"	type="hidden" id="accountvalue" />
                   <input type='text' id="mbayaccount"  ajaxurl="<c:url value='/message/authReceverUnumber.mbay'/>"  value="${message.senderUnumber}" nullmsg="请输入收件人账号！"  datatype="*" errormsg="收件人账号不正确!"  class='xx_text'/>
                 <div class='email_an'>                  
                  <input type='button' id="usernumber" value="下级联系人" class='email_txl' />                
                  <div class='an_tc'>
                     <i></i>
                  </div>
                </div>                
                </div>
                <div class='xx_box'>
                   <span class='xx_sec_1'>主题:</span>
                   <c:if test="${message.message.title ne null }"><input type='text' datatype="*1-30" nullmsg="请输入主题!"   errormsg="主题不能超过30字!" value="回复:${message.message.title }" name="title" class='title_text'/></c:if>
                   <c:if test="${message.message.title eq null }"><input type='text' datatype="*1-30"  nullmsg="请输入主题!"  errormsg="主题不能超过30字!" name="title" class='title_text'/></c:if>          
                </div>
                <div class='xx_box_1'>
                   <span class='xx_sec_1'>正文:</span>
                   <c:if test="${message.message.content ne null }">
                   <textarea tip="请输入内容!。" errormsg="内容不能超过300字!" nullmsg="请输入正文内容!"  datatype="*1-300"  name="content" class='zw_text'>
                   
                   
                   
 ------------------ 原始邮件 ------------------
 
 
 
 ${message.message.content }
                   </textarea>
                   </c:if>
                   <c:if test="${message.message.content eq null}">
                    <textarea  tip="请输入内容!。" errormsg="内容不能超过300字!" nullmsg="请输入正文内容!"  datatype="*1-300" name="content" class='zw_text'>          </textarea>
                   </c:if>
                </div>
                 <input type="button" id="subForm"  class='email_fs'/>
             </div>               
             </form>
          </div>
 </div> 
 </div>
</body>
</html>
