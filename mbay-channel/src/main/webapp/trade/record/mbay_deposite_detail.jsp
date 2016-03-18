<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>O2O红包</title>
<link href="${actx}/wro/${version}/workbenches.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<%-- <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" /> --%>
<link href="${actx}/css/bootstrap3/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
table {
	border: 0px;
}

table td {
	border: 1px solid #BCE8F1;
}

#order_title {
	font-size: larger;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div>
					<div class='hdlb'>交易详情</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='lb fr' style='width: 830px'>
						<div class='hd'>
							<div class="panel panel-info">
								<!-- Default panel contents -->
								<div class="panel-heading">
									<table id="order_title">
										<tr>
											<td><span>订单号:</span> <span>${accountDetail.ordernumber }</span>
											</td>
											<td><span>交易类型:</span><span>${accountDetail.tradetype.value }</span></td>
											<td><span>交易金额:</span> <span>${accountDetail.type.value }(美贝):</span>
												<span>${accountDetail.amount }</span></td>
										</tr>
									</table>
								</div>

								<!-- Table -->
								<table class="table">
									<tr>
										<td>流水号</td>
										<td>交易数额(美贝)</td>
										<td>交易时间</td>
										<td>交易状态</td>
									</tr>
									<tr>
										<td><span>${accountDetail.number }</span></td>
										<td>${tradeDetail.mbayQuantity}</td>
										<td><span><fmt:formatDate
													value="${accountDetail.time.toDate() }"
													pattern="yyyy-MM-dd HH:mm:ss" /></span></td>
										<td>${tradeDetail.state.value }</td>
									</tr>
									<tr>
										<td>交易备注</td>
										<td colspan="3"><span>${accountDetail.info }</span></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>