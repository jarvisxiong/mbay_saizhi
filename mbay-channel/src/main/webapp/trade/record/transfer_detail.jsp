<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易详情</title>
<link href="${actx}/wro/${version}/transfer_detail.css"	rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/smoothness/jquery-ui-1.10.4.custom.min.css"/>" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!-- 左边 -->
				<div class='left_con'>
					<div class='hdlb'>${accountDetail.tradetype.value }交易详情</div>
					<!-- 身体中部左侧 -->
					<%@ include file="/common/leftcon.jsp"%>

					<!-- 身体中部右侧 -->
					<!--右侧设置-->
					<div class='tip_setting fr'>
						<div class="tip_setting_con">

							<div class='tab_block'>
								<div class='tab_0'>
									<!--表一-->
									<table>
										<thead>
											<tr>
												<th class="type">类型</th>
												<th class="name">消费名称</th>
												<th class="amount">消费总额</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="type">${accountDetail.tradetype.value }</td>
												<td class="name">
													流水号 ${accountDetail.number }
												</td>
												<td class="amount">${accountDetail.amount }</td>
											</tr>
										</tbody>
										<tfoot>
											<tr>
												<td colspan="5">单位（美贝）</td>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
							<div class='tab_block'>
								<div class='tab_1'>
									<!--表二-->
									<table>
										<tbody>
											<tr>
												<th class="first">描述：</th>
												<td colspan="5">${tradeDetail.transfernote }</td>
											</tr>
											<tr>
												<th class="first">对方信息：</th>
												<td>${realName } ${tradeDetail.optuser.username }</td>
											</tr>
										</tbody>
										<tfoot>
											<tr>
												<th class="first">时间报告：</th>
												<td class="tb-reset tb-thead-bg p-tb-delivertime"
													colspan="5">
													<table>
														<tbody>
															<tr>
																<th class="time">创建时间</th>
																<th class="time">付款时间</th>
																<th class="time">结束时间</th>
															</tr>
															<tr>
																<td class="time"><fmt:formatDate value="${tradeDetail.createtime.toDate() }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
																<td class="time"><fmt:formatDate value="${accountDetail.time.toDate() }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
																<td class="time"><fmt:formatDate value="${accountDetail.time.toDate() }" pattern="yyyy-MM-dd" /></td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
							<div class='tab_block an'>
								<button class='an_btn' onclick="history.back()">返回</button>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>