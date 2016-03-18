<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>确认转账信息</title>
<link href="${actx}/wro/${version}/confirm.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
    	$("#subForm").click(function(){
    		$("#myForm").submit();
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
             <div class='ddqr'>确认转账信息</div>
              <%@ include file="/common/leftcon.jsp" %>  
                <div class='dd fr'>
                 <form id='llcz' action="<c:url value="/account/transfer/payment/cashier.mbay"/>" method="post">     
                 <input type="hidden" name="orderid" value="${transferrecord.orderid}">              
                   <div class='yfje'>
                   <span class='czll'>转入账户：</span><span > ${transfer_account}</span>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>转入数量：</span><span >${transferrecord.payAmount}</span><span class='je'>美贝</span>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>赠送数量：</span><span >${transferrecord.sendAmount}</span><span class='je'>美贝</span>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>共计：</span><span >${transferrecord.sendAmount+transferrecord.payAmount}</span><span class='je'>美贝</span>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>转账说明：</span><span>${transferrecord.transfernote}</span>
                   </div>
                   <div class='an'>
                   <input type='submit' class='an_btn' value='确认并转账'/>
                   </div>
                 </form>
                </div>               
           </div>         
             
  <!--尾部--> 
</div>
</div>
 </div>
</body>
</html>
