<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<title>转账成功</title>
<head>
<link href="${actx}/wro/${version}/transfer_success.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->        
             <div class='b_con com_width clearfix'>
        <!--左边-->     
          
             <div class='left_con '>
             <div class='ddqr'>转账</div>
                 <%@ include file="/common/leftcon.jsp" %>  
                <div class='dd fr'>
                   <div class='dd_con'>
                 		转账成功!
                   </div>
                </div>                   
             </div>
          <!--右边-->
          
             </div>
  <!--尾部--> 
</div>
</div>
 </div>
 </body>
</html>
