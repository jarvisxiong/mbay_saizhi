<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MB收支明细</title>
<link href="${actx}/wro/${version}/account_detail.css" rel="stylesheet"
	type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<style>
.sub_string {
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
	margin-bottom: 0px;
	width: 192px
}
</style>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con fl'>
					<form id="pagerForm"
						action="<c:url value="/account/mbaytraffic/tradedetail.mbay"/>"
						method="post">
						<div class='hdlb'>MB收支明细</div>
						<!--身体中部左侧-->
						<%@ include file="/common/leftcon.jsp"%>
						<!--身体中部右侧-->
						<div class='hd_con_r fr'>
							<!--表格-->
							<div class='lb'>
								<div class='hd'>
									<span class='hdbh'>查询流水号：</span> <input type='text'
										name="number" value="${detailQO.number}" class='bh' /> <span
										class='hdmc'>查询日期：</span> <img
										src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq'
										onclick="WdatePicker()" /> <input id="rq_1" name="starttime"
										class="Wdate"
										value='<fmt:formatDate pattern="yyyy-MM-dd" value="${detailQO.startTime.toDate()}"/>'
										type="text"
										onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq' onclick="WdatePicker()" /> <input id="rq_2"
										name="endtime"
										value='<fmt:formatDate pattern="yyyy-MM-dd" value="${detailQO.endTime.toDate()}"/>'
										class="Wdate" type="text"
										onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})" /> <input
										value='查询' type='submit' class='cx' />
								</div>
							</div>
							<div class='table'>
								<table>
									<tr>
										<td>流水号|日期</td>
										<td>名称|备注</td>
										<td>收入( MB)</td>
										<td>支出(MB)</td>
										<td>账户余额(MB)</td>
									</tr>
									<c:forEach items="${trafficDetail}" var="detail">
										<tr>
											<td>${detail.tradeRecord.tradeNumber }<br>
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${detail.tradeRecord.createTime.toDate() }" /></td>
											<td width="180px">
												<div class="sub_string">
													<span title="">${detail.tradeRecord.tradeName }</span>
												</div>
											</td>

											<td><c:if test="${detail.paymentType=='INCOME' }">

													<fmt:formatNumber value=" ${detail.tradeRecord.traffic }"
														maxFractionDigits="2" minFractionDigits="2" />
												</c:if></td>
											<td><c:if test="${detail.paymentType=='EXPENSE' }">

													<fmt:formatNumber value=" ${detail.tradeRecord.traffic }"
														maxFractionDigits="2" minFractionDigits="2" />
												</c:if></td>
											<td><fmt:formatNumber value="${detail.balance}"
													maxFractionDigits="2" minFractionDigits="2" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<m:page pageinfo="${pageinfo}" formid="pagerForm" />
						</div>
					</form>
				</div>
			</div>
			<!--尾部-->
		</div>
	</div>
</body>
</html>