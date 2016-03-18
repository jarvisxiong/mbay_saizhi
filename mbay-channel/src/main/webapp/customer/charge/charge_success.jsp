<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量充值成功</title>
<link href="${actx}/wro/${version}/message_notice.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
 <script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->        
             <div class='b_con com_width clearfix'>
          
             <div class='left_con'>
                
                <div class='ddqr'>批量流量充值</div>
                <%@ include file="/common/leftcon.jsp" %>                  
                <div class='dd fr'>
                   <div class='dd_con'>
                 		批量充值提交成功！系统将为您自动完成充值，如有任何疑问，请及时联系客服!
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
