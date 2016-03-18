<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>密码修改</title>
<t:assets/>
<link href="${actx}/wro/${version}/base.css"	rel="stylesheet" type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
    $(document).ready(function() { 
		$('#subForm').click(function() {	
			 $("#mbzz").Validform({tiptype:3});
			$("#mbzz").submit();			
		});
	});   
 </script> 
    
</head>
<body>
<div class='con'>
<div class='body clearfix'>
   <div class='b_con com_width clearfix' >
	 <!--左边-->               
	   <div class='left_con'>
	                 
	       <div class='ddqr'>密码修改</div>
	        <%@ include file="/common/leftcon.jsp" %> 
	         <div class='dd fr'>
	          <form id='mbzz' action="<c:url value="/channel/changepwd.mbay"/>" method="post">   
	          <m:token/>               
	             <div class='czsl'>
	            <span class='czll'><b>*</b>原始密码：</span>
	            <input type="password" id="originalpwd" value=""  ajaxurl="<c:url value='/channel/authOriginalpwd.mbay'/>"   class='sl_1' placeholder='请输入原始密码'   nullmsg="原始密码不能为空!" datatype="*6-20"     />
	           <span class="Validform_checktip"></span>
	            </div>                   
	             <div class='czsl'>
	            <span class='czll'><b>*</b>新密码：</span>
	            <input type="password" class='sl_1'  ajaxurl="<c:url value='/channel/authNewpwd.mbay'/>"   placeholder='请输入新密码' name="password" class='hdmc' datatype="*6-20"   nullmsg="请输入新密码!" />
	            <span class="Validform_checktip"></span>
	            </div>
	            <div class='czsl'>
	            <span class='czll'><b>*</b>确认新密码：</span>
	            <input type="password" recheck='password' datatype="*6-20"   name="repassword" class='sl_1' placeholder='请确认新密码!' class='hdmc' nullmsg="密码不能为空!" errormsg="您两次输入密码不一致！"/>
	            <span class="Validform_checktip"></span>
	            </div>                                     
	            <div class='an' style="margin-left:170px">
	            <input type='button' id="subForm" class='btn_confirm' value='确认修改'/>
	            </div>
	          </form>
	         </div>               
   </div>         
</div>
</div>
 </div>
</body>
</html>
