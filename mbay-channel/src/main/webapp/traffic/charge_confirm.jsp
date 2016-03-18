<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流量充值确认</title>
<link href="<c:url value="/css/traffic/chargeConfirm.css"/> " rel="stylesheet"	type="text/css" />
<script type="text/javascript">
$(function(){
	$("#confirmsub").bind("click",function(){
		if(!$("#chargeenable").val()){
			alert("您的账户余额不足，请充值！");
			return ;
		}
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
             <div class='b_con com_width clearfix'>
        <!--左边-->  
              <div class='left_con'>
                
                <div class='ddqr'>订单确认</div>
                  <%@ include file="/common/leftcon.jsp" %>
                <form id="myForm" action="<c:url value="/traffic/trafficCharge.mbay"/>" class='fr' style='width:828px' method="post">
				<m:token/>
				<input type="hidden" id="chargeenable" value="${chargeenable}">
					<input type="hidden" name="chargeordernumber" value="${order.number}" >
                <div class='dd'>
                  <div><span class='mc'>订单编号：</span><span class='shu'>${order.number}</span></div>
                  <div><span class='mc'>名称：</span><span class='shu'><span class='sp_1'>${order.orderName}</span></span></div>
                  <div><span class='mc'>号码：</span><span class='shu'>${order.mobile}</span></div>
                  <div><span class='mc'>充值金额：</span><span class='shu'>${order.mbayPrice}&nbsp;美贝</span></div>
                  <div class='an'><input type='button'  id="confirmsub" class='an_btn' value='确认支付'/></div>
                </div>
                </form>
             </div>
          <!--右边-->
             
             </div>
  <!--尾部--> 
</div>
</div>
 </div>
</body>
</html>
