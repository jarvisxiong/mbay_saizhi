<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销明细</title>
<link href="${actx}/wro/${version}/table_list.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<style type="text/css">
.hdrq {
	display: inline-block;
	vertical-align: middle;
	margin-right: 2px;
	vertical-align: text-top;
}

#rq_1,#rq_2 {
	border: 1px solid #94ABC4;
	width: 68px;
	height: 26px;
	background: #fff;
	border-radius: 0px;
	color: #000;
	padding-left: 5px;
	vertical-align: baseline;
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
					<form id="pagerForm"
						action="<c:url value="/campaign/redeem_detail_with_con.mbay"/>"
						method="post">
						<div class='hdlb'>营销明细</div>
						<%@ include file="/common/leftcon.jsp"%>
						<div class='fr' style='width: 828px'>
							<div class='lb'>
								<div class='hd'>
									<div style='margin-bottom: 20px'>
										<input type="hidden" name="campaignId" value="${campaign.id }">
										<span class='hdbh'>活动编号：</span><input type='text'
											name="eventnumber" value="${campaign.number}"
											disabled="disabled" class='bh' /> <span class='hdmc'>查询日期：</span>
										<img src="<c:url value='/images/workimages/rq.jpg'/>"
											class='hdrq' onclick="WdatePicker()" /> <input id="rq_1"
											name="startTime" class="Wdate"
											value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${campaign.startTime.toDate()}"/>'
											type="text"
											onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
										<img src="<c:url value='/images/workimages/rq.jpg'/>"
											class='hdrq' onclick="WdatePicker()" /> <input id="rq_2"
											name="endTime"
											value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${campaign.endTime.toDate()}"/>'
											class="Wdate" type="text"
											onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})" />
									</div>
									<div>
										<%-- <span class='hdmc'>订单编号：</span><input type='text'
											name="number" value='${orderform.number}' class='mc' /> --%>
										<span class='hdmc'>手机号码：</span><input type='text'
											name="cellPhone" class='mc' /> <input type="submit"
											class='hd_btn' value='查询' />
									</div>
								</div>
							</div>
							<!--表格-->
							<div class='table'>
								<table>
									<tr>
										<td>兑换码</td>
										<td>核销码</td>
										<td>兑换状态</td>
										<td>用户手机号</td>
										<td>发放时间</td>
										<td>发放门店编号</td>
										<td>操作员手机号</td>
										<td>兑换门店编号</td>
										<td>兑换日期</td>
										<td>操作</td>
									</tr>
									<c:forEach items="${redeemDetail}" var="c">
										<tr>
											<td>${c.redeemCode}</td>
											<td style="text-align: left; padding-left: 3px;">${c.checkCode}</td>
											<td>${c.status.value}</td>
											<td>${c.userCellPhone}</td>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${c.deliverDate.toDate()}" /></td>
											<td>${c.deliverStoreNumber}</td>
											<td>${c.operatorCellPhone }</td>
											<td>${c.redeemStoreNumber}<br /></td>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${c.redeemDate.toDate() }" /></td>
											<td><a href="#">查看</a></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<m:page pageinfo="${pageinfo}" />
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>
