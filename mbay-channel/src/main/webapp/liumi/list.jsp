<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流米</title>
<script type="text/javascript"
	src="${actx}/webjars/jquery/2.1.3/jquery.min.js"></script>
<style>
/*通用表格，只需引入class叫table的div*/
div.table table {border: 1px solid #C2C2C2;border-collapse: collapse;}
div.table table td{text-align: center;padding:15px 20px }
div.table table td,div.table table th{border: 1px solid #C2C2C2;}
div.table table th {font-size: 12px;color: #5D5C5C;padding:15px 20px;background: #f3f3f3;}

</style>
</head>
<body>
 <div class='table'>
 	<form action="<c:url value="/liumi/setTotalPrice.mbay"/>" method="post">
 		现流米总额:<c:if test="${price != null && price != ''}">${price}</c:if><br/>
 		设置流米增加额:<input name="price" value=""/><input type="submit" value="设置"/>
 	</form>
 	<form action="<c:url value="/liumi/list.mbay"/>" method="post">
 	<table>
		  	<tr>
		  		<th>流米订单号</th>
		  		<th>美贝订单号</th>
		  		<th>创建时间</th>
		  		<th>手机号</th>
		  		<th>流量大小</th>
		  		<th>折扣价格</th>
		  	</tr>
		  	<c:forEach items="${list}" var="record">
		  		<tr>
		  			<td>${record.orderNo}</td>
		  			<td>${record.extNo}</td>
		  			<td>${record.createtime}</td>
		  			<td>${record.mobile}</td>
		  			<td>${record.traffic}</td>
		  			<td>${record.price}</td>
		  		</tr>
		  	</c:forEach>
		  </table>
		  <m:page pageinfo="${pageinfo}"/>
	</form>
 </div>
</body>
</html>