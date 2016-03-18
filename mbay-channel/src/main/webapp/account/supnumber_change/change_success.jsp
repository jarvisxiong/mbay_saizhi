<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>变更结果</title>
<t:assets/>
<link href="${actx}/wro/${version}/message_notice.css"	rel="stylesheet" type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"	src="${actx}/js/widget/jquery.Sonline.js"></script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->     
             <div class='left_con '>
                <div class='ddqr'>变更结果</div>
                 <%@ include file="/common/leftcon.jsp" %>
                <div class='dd fl' style="width: 740px; margin-left: 30px;">
                   <div class='dd_con'>
                 		  您的上级代理变更成功!
                   </div>
                </div>                   
             </div>
          <!--右边-->
         
             </div>
  <!--尾部--> 
</div>
</div>
</body>
</html>
