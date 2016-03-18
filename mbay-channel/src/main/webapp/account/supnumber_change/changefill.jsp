<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>变更上级代理</title>
<link href="${actx}/wro/${version}/depositPreprocess.css"	rel="stylesheet" type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript"	src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript">
	$(document).ready(function() {			
		$("#myForm").Validform({
		    showAllError:true,
			tiptype:3,
			datatype:{
				"sz" : /^[0-9]+$/
			}
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
                <div class='ddqr'>变更上级代理</div>

                 <%@ include file="/common/leftcon.jsp" %>
                <div class='dd fr'>
                 <form id='myForm'  action="<c:url value="/channel/changeSupnumber.mbay"/>" method="post">
                  <m:token/>
                   <div class='czsl'><span class='czll'><b>*</b>上级代理编号：</span>
                   <input type='text' id='supnumber' nullmsg="请输入账号" datatype="s6-16" ajaxurl="<c:url value='/channel/getSupUserStatus.mbay'/>" class='sl_1' placeholder='请输入上级代理编号' class='hdmc'  name="supnumber" />
                   </div>                 
                   <div class='an'><input id="subForm" type="submit" class='an_btn' value='立即修改'/>
                   </div>
                 </form>
                </div>
              </div>
              <!--右边-->
              
  <!--尾部--> 
</div>
</div>
 </div>
</body>
</html>
