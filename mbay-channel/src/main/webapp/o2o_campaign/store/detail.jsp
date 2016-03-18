<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>门店详细</title>
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
					<div class='ddqr'>门店详细</div>
					<%@ include file="/common/leftcon.jsp"%>
				</c:if>
				<div class="hd fr">
					<div class="hd_con">
						<!--门店详细-->
						<div>
							<h2 class='sp_mar_top'>门店详细</h2>
							<div class='table'>
								<table>
									<tr>
										<th>门店编码</th>
										<th>门店名称</th>
										<th>授权码</th>
										<th>状态</th>
										<th>操作员数</th>
										<th>二维码</th>
									</tr>
									<tr>
										<td>${store.number}</td>
										<td>${store.name}</td>
										<td>${store.authCode}</td>
										<td><c:choose>
												<c:when test="${store.enable }">
													<span>启用</span>
												</c:when>
												<c:otherwise>
													<span>禁用</span>
												</c:otherwise>
											</c:choose></td>
										<td>${store.operatorNum}</td>
										<td>
											<div>
												<img id="qrcode" />
											</div>
											<div>
												<span><a href="#"
													onclick="showQRCode('${store.id}',this)">查看</a></span>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<!--操作员详细-->
						<div>
							<h2>操作员详细</h2>
							<div class='table'>
								<table>
									<tr>
										<th>操作员</th>
										<th>状态</th>
										<th>手机号码</th>
									</tr>
									<c:forEach var="index" begin="1" step="1"
										end="${store.operatorNum }">
										<tr>
											<td>操作员${index }</td>
											<td>${storeOperators[index-1].status.value}</td>
											<td>${storeOperators[index-1].cellphone}</td>
											<%--<td>
											 <a href="#"
												onclick="deleteOperator('${storeOperators[index-1].id}','${store.id }','${storeOperators[index-1].cellphone }')">删除</a> 
											</td>--%>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						<!--活动详细-->
						<div>
							<div class='table'>
								<div>
									<h2>已经参与的活动</h2>
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
											<c:forEach var="a" items="${joined}">
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
													<td><input class='btn_reset' type="button"
														value="退出活动"
														onclick="exitActivity('${store.id}','${a.id}')"></td>
												</tr>
											</c:forEach>
										</table>
										<c:if test="${ joined.size()>5}">
											<div class='detail'>
												<span> <a
													href="${ctx }/store/queryJoined.mbay?storeId=${store.id}&flag=joined"
													target="_blank">more</a>
												</span>
											</div>
										</c:if>
									</div>
								</div>
								<div>
									<h2>没有参与的活动</h2>
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
											<c:forEach var="a" items="${notJoined}">
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
													<td>${a.status.value}</td>
													<td>${a.deliverNum }</td>
													<td>${a.redeemNum }</td>
													<td><input type="button" class='btn_reset'
														value="参与活动"
														onclick="joinActivity('${store.id}','${a.id}')"></td>
												</tr>
											</c:forEach>
										</table>
										<c:if test="${ notJoined.size()>5}">
											<div class='detail'>
												<span> <a
													href="${ctx }/store/queryJoined.mbay?storeId=${store.id}&flag=not"
													target="_blank">more</a>
												</span>
											</div>
										</c:if>
									</div>
								</div>
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
