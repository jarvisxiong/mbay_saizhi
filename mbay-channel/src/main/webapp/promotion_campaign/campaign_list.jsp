<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>促销神器-活动列表</title>
<link href="${actx}/wro/${version}/table_list.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/event/activitylist.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("[name='eventstate']").val("${event.eventstate}");
		$("#promotion_add_button").bind("click",function(){
			window.location.href="<c:url value='/promotionCampaign/to_campaign_add.mbay'/>";
		});
	});
</script>
<style>
/* table{table-layout:fixed;} */
.activity_name{ height:38px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow:hidden; line-height:38px;white-space:nowrap;width:140px;}
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
						action="<c:url value="/promotionCampaign/campaign_list.mbay"/>"
						method="post">
						<div class='hdlb'>促销神器-活动列表</div>
						<%@ include file="/common/leftcon.jsp"%>
						<%-- <jsp:include page="/common/leftcon.jsp"></jsp:include> --%>
						<!--内容右部-->
						<div class='hd_con_r fr'>
							<div class='lb'>
								<div class='hd'>
									<span class='hdbh'>活动编号：</span> <input type='text'
										name="eventnumber" value="${event.eventnumber}" class='bh' />
									<span class='hdbh'>活动状态：</span> <select class='sel'
										name="eventstate">
										<option value="">请选择</option>
										<option value="NONE_FINISH">未完善</option>
										<option value="IN_ACTIVE">活动中</option>
										<option value="NOT_STARTED">未开始</option>
										<option value="OVER">已结束</option>
										<option value="CANCLED">已取消</option>
									</select> <br />
									<div style="height: 20px;"></div>
									<span class='hdmc'>查询日期：</span> <img
										src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq'
										onclick="WdatePicker()" /> <input id="rq_1"
										name="eventstarttime" class="Wdate"
										value='<fmt:formatDate pattern="yyyy-MM-dd" value="${event.starttime.toDate()}"/>'
										type="text"
										onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq' onclick="WdatePicker()" /> <input id="rq_2"
										name="eventendtime"
										value='<fmt:formatDate pattern="yyyy-MM-dd" value="${event.endtime.toDate()}"/>'
										class="Wdate" type="text"
										onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})" /> <input
										type="submit" style="margin-left: 30px;"
										class='hd_btn btn_confirm' value='查询' /> <input
										id="promotion_add_button" type="button"
										style="margin-left: 30px;" class='hd_btn btn_confirm'
										value="新增" />
								</div>
							</div>
							<!--表格-->
							<div class='table'>
								<table>
									<tr>
										<td>活动编号|创建日期</td>
										<td>活动名称</td>
										<td>活动状态</td>
										<td>活动日期</td>
										<!-- <td>类别</td> -->
										<td>已送出</td>
										<td>操作</td>
									</tr>
									<c:forEach items="${listevent}" var="event">
										<tr id="strategytr${event.eventnumber}">
											<td>${event.eventnumber}<br><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${event.createtime.toDate()}" /></td>
											<td style="/* word-break: break-all; */" title="${event.eventname}"><p class="activity_name" >${event.eventname}</p></td>
											<td>${event.state.value}</td>
											<td><fmt:formatDate pattern="yyyy-MM-dd"
													value="${event.starttime.toDate()}" /><br /> <fmt:formatDate
													pattern="yyyy-MM-dd" value="${event.endingtime.toDate()}" /></td>
			  								<%-- <td>送${event.sendway.getValue()}</td> --%>
											<td>${event.sendednum}<br /></td>
											<td><a
												href="<c:url value="/promotionCampaign/campaign_info.mbay?eventnumber=${event.eventnumber}"/>">查看</a>
											</td>
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