<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><c:choose>
		<c:when test="${flag==0 }">
			查询未参与的活动
		</c:when>
		<c:otherwise>
			查询参与的活动
		</c:otherwise>
	</c:choose></title>
<t:assets />
<link href="${actx}/wro/${version}/message_notice.css" rel="stylesheet"
	type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"
	src="${actx}/js/o2o_campaign/store/detail.js"></script>
<style type="text/css">
/*独立*/
.hd {
	width: 828px;
}

.hd_con h2 {
	color: #35618F;
	font-size: 18px;
	margin: 30px 0px;
	padding-bottom: 10px;
	border-bottom: 2px dashed #35618F;
}

div.table table {
	width: 100%
}

.hd_con .sp_mar_top {
	margin-top: 0px
}

#qrcode {
	width: 100px;
	height: 100px display: none;
}

.detail {
	text-align: right;
	font-size: small;
}
</style>

</head>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div class='left_con'>
				<c:if test="${sessionScope.channel!=null}">
					<div class='ddqr'>门店活动</div>
					<%@ include file="/common/leftcon.jsp"%>
				</c:if>
				<div class="hd fr">
					<div class="hd_con">
						<!--活动详细-->
						<div>
							<div class='table'>
								<table>
									<tr>
										<th>活动编号</th>
										<th>活动名称</th>
										<th>活动创建日期</th>
										<th>活动开始日期</th>
										<th>活动结束日期</th>
										<th>活动有效期</th>
										<th>活动状态</th>
										<th>发放红包数量</th>
										<th>兑换红包数量</th>
										<th>操作</th>
									</tr>
									<c:forEach var="a" items="${campaigns}">
										<tr>
											<td>${a.number}</td>
											<td>${a.name}</td>
											<td><joda:format value="${a.dateCreated}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td><joda:format value="${a.startTime}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td><joda:format value="${a.endTime}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${a.validity}天</td>
											<td>${a.status.value }</td>
											<td>${a.deliverNum}</td>
											<td>${a.redeemNum}</td>
											<td><c:choose>
													<c:when test="${flag==0}">
														<input type="button" value="参与活动"
															onclick="joinActivity('${storeId}','${a.id}')">
													</c:when>
													<c:otherwise>
														<input type="button" value="退出活动"
															onclick="exitActivity('${storeId}','${a.id}')">
													</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--右边-->
		</div>
		<!--尾部-->
	</div>
	</body>
</html>
