<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易详情</title>
<link href="${actx}/wro/${version}/mbayDepositOrderRecord.css"	rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/smoothness/jquery-ui-1.10.4.custom.min.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix' >
	        <!--左边-->  
	        <div class='left_con fl'>                  	   
	        	<div class='hdlb'>${accountDetail.tradetype.value }交易详情</div>
	                <!--身体中部左侧-->
	                <%@ include file="/common/leftcon.jsp" %>      
	                          
	                <!--身体中部右侧-->
	                <div class='hd_con_r fr'>
		            	<!-- 流水信息 -->
		            	<div>
		            		<div>
		            			<span>流水号:</span>
		            			<span>${accountDetail.number }</span>
		            		</div>
		            		<div>
		            			<span>订单号:</span>
		            			<span>${accountDetail.ordernumber }</span>
		            		</div>
		            		<div>
		            			<span>创建日期:</span>
		            			<span><fmt:formatDate value="${accountDetail.time.toDate() }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
		            		</div>
		            		<div>
		            			<span>名称/备注:</span>
		            			<span>${accountDetail.info }</span>
		            		</div>
		            		<div>
		            			<span>${accountDetail.type.value }(美贝):</span>
		            			<span>${accountDetail.amount }</span>
		            		</div>		            		
		            		<div>
		            			<span>交易类型:</span>
		            			<span>${accountDetail.tradetype.value }</span>
		            		</div>
		            		<div>
		            			<span>账户余额(美贝):</span>
		            			<span>${accountDetail.balance }</span>
		            		</div>
		            	</div>
		            	
		            	<hr />
		            	
		            	<!-- 订单详情 -->
		            	<div>
		            		<div>
		            			<span>订单号:</span>
		            			<span>${tradeDetail.orderId }</span>
		            		</div>
		            		<div>
		            			<span>短信购买数量:</span>
		            			<span>${tradeDetail.smsAmount }</span>
		            		</div>
		            		<div>
		            			<span>美贝数量:</span>
		            			<span>${tradeDetail.mbayAmount }</span>
		            		</div>
		            		<div>
		            			<span>创建日期:</span>
		            			<span><fmt:formatDate value="${tradeDetail.createTime.toDate() }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
		            		</div>
		            		<div>
		            			<span>交易状态:</span>
		            			<span>${tradeDetail.state.value }</span>
		            		</div>
		            	</div>
	            	</div>            
	            </div>           
			</div>
		</div>
	</div>

</body>
</html>