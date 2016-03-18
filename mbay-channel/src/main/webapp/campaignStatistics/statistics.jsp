<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ page import="org.sz.mbay.channel.enums.EventType"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动统计</title>
<link href="${actx}/wro/${version}/campaign_statistics.css"
	rel="stylesheet" type="text/css" />
<script>
	$(function() {
		$('.lb').hover(function() {
			$(this).addClass('hover_border');
		}, function() {
			$(this).removeClass('hover_border')
		});
	});
</script>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div>
					<div class='hdlb'>活动统计</div>
					<%@ include file="/common/leftcon.jsp"%>
					<!--内容右部-->
					<div class='hd_con_r fr'>
					
					<!-- O2O门店活动 -->
						<div class='lb'>
							<div class='hd'>
								<a href="<c:url value='/traffic_red/list.mbay'/>">
									<div class='dpa'>
										<table>
											<tr>
												<td rowspan="2" width='100px'>
													<div class='tbd'>
														<img src="<c:url value='/images/workimages/llhongb.jpg'/>"><span>流量红包</span>
													</div>
												</td>
												<th>活动中</th>
												<th>未开始</th>
												<th>未完善</th>
												<!-- <th>已结束</th>
										<th>已取消</th> -->
												<td width="30px" rowspan="2" class="tdi"><i></i></td>
											</tr>
											<tr>
												<td><span>${trafficRedStatistics.inActive}</span></td>
												<td><span>${trafficRedStatistics.notStarted}</span></td>
												<td><span>${trafficRedStatistics.noneFinish}</span></td>
												<%-- <td><span>${eventCount.over}</span></td>
										<td><span>${eventCount.cancled}</span></td> --%>
											</tr>
										</table>
									</div>
								</a>
							</div>
						</div>
						<!-- 客户关怀 -->
					
					
						<!-- 微信伴侣 
						<div class='lb'>
							<div class='hd'>
								<a href="<c:url value='/wechatCampaign/campaign_list.mbay'/>">
									<div class='dpa'>
										<table>
											<tr>
												<td rowspan="2" width='100px'>
													<div class='tbd'>
														<img src="<c:url value='/images/workimages/wxbanlv.jpg'/>"><span>微信伴侣</span>
													</div>
												</td>
												<th>活动中</th>
												<th>未开始</th>
												<th>未完善</th>
												<!-- <th>已结束</th>
										<th>已取消</th> 
												<td width="30px" rowspan="2" class="tdi"><i></i></td>
											</tr>
											<tr>
												<td><span>${weChatStatistics.inActive}</span></td>
												<td><span>${weChatStatistics.notStarted}</span></td>
												<td><span>${weChatStatistics.noneFinish}</span></td>
												<%-- <td><span>${eventCount.over}</span></td>
										<td><span>${eventCount.cancled}</span></td> --%>
											</tr>
										</table>
									</div>
								</a>
							</div>
						</div>-->

						<!-- 促销神器 -->
						<div class='lb'>
							<div class='hd'>
								<a href="${ctx}/promotionCampaign/campaign_list.mbay">
									<div class='dpa'>
										<table>
											<tr>
												<td rowspan="2" width='100px'>
													<div class='tbd'>
														<img src="<c:url value='/images/workimages/cxshenq.jpg'/>"><span>促销神器</span>
													</div>
												</td>
												<th>活动中</th>
												<th>未开始</th>
												<th>未完善</th>
												<!-- <th>已结束</th>
										<th>已取消</th> -->
												<td width="30px" rowspan="2" class="tdi"><i></i></td>
											</tr>
											<tr>
												<td><span>${promotionStatistics.inActive }</span></td>
												<td><span>${promotionStatistics.notStarted }</span></td>
												<td><span>${promotionStatistics.noneFinish }</span></td>
												<!-- <td><span>33</span></td>
										<td><span>33</span></td> -->
											</tr>
										</table>
									</div>
								</a>
							</div>
						</div>

						<!-- app诱惑 -->
						<div class='lb'>
							<div class='hd'>
								<a href="<c:url value='/app_temptation/campaign_list.mbay'/>">
									<div class='dpa'>
										<table>
											<tr>
												<td rowspan="2" width='100px'>
													<div class='tbd'>
														<img src="<c:url value='/images/workimages/jfgongj.jpg'/>"><span>app诱惑</span>
													</div>
												</td>
												<th>活动中</th>
												<th>未开始</th>
												<th>未完善</th>
												<!-- <th>已结束</th>
										<th>已取消</th> -->
												<td width="30px" rowspan="2" class="tdi"><i></i></td>
											</tr>
											<tr>
												<td><span>${appTemptationStatistics.inActive}</span></td>
												<td><span>${appTemptationStatistics.notStarted}</span></td>
												<td><span>${appTemptationStatistics.noneFinish}</span></td>
												<%-- <td><span>${eventCount.over}</span></td>
										<td><span>${eventCount.cancled}</span></td> --%>
											</tr>
										</table>
									</div>
								</a>
							</div>
						</div>
					
						<!-- 客户关怀 -->
						<%-- <div class='lb'>
							<div class='hd'>
								<a href="<c:url value='/campaign/list.mbay'/>">
									<div class='dpa'>
										<table>
											<tr>
												<td rowspan="2" width='100px'>
													<div class='tbd'>
														<img src="<c:url value='/images/workimages/khguanh.jpg'/>"><span>O2O活动</span>
													</div>
												</td>
												<th>活动中</th>
												<th>未开始</th>
												<th>未完善</th>
												<!-- <th>已结束</th>
										<th>已取消</th> -->
												<td width="30px" rowspan="2" class="tdi"><i></i></td>
											</tr>
											<tr>
												<td><span>${storeCampaignStatistics.inActive}</span></td>
												<td><span>${storeCampaignStatistics.notStarted}</span></td>
												<td><span>${storeCampaignStatistics.noneFinish}</span></td>
												<td><span>${eventCount.over}</span></td>
										<td><span>${eventCount.cancled}</span></td>
											</tr>
										</table>
									</div>
								</a>
							</div>
						</div> --%>
					</div>
				</div>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>