<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>密码修改成功</title>
<t:assets/>
<link href="${actx}/wro/${version}/base.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
	<div class='b_con com_width clearfix' >
        <!--左边-->               
          <div class='left_con'>         
              <div class='ddqr'>密码修改成功</div>
               <%@ include file="/common/leftcon.jsp" %> 
                <div class='dd fr'>
                   <div class='left_con fl'>
                <div class='dd_con'>密码更新成功, 请牢记新密码!</div>                               
             </div>
                </div>
           </div> 
	</div>
</div>
</div>
</body>
</html>
