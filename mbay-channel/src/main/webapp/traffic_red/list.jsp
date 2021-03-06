<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动管理</title>
<link href="${actx}/wro/${version}/table_list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function gotoCreateCampaign() {
	window.location.href = ctx + "/traffic_red/createUI.mbay";
}
function gotoStoreManage() {
	window.location.href = ctx + "/store/list.mbay";
}

function wansan() {
	alert("还在完善!");
}

function exportCampaign() {
	window.location.href = ctx + "/campaign/campaign.xls";
}
</script>
<style type="text/css">
#store-ope {
	margin-bottom: 10px
}
</style>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<div class='hdlb'>活动管理</div>
				<%@ include file="/common/leftcon.jsp"%>
				<!--左边-->
				<div class='fr hd_con_r'>
					<form id='listCampaign' method="post"
						action="<c:url value="/traffic_red/list.mbay"/>" class='clearfix'>
						<div id="store-ope">
							<!--<input type="text" name="number" class='input_txt' />
							 <input type="button" value="搜索" onclick="query(this)"
								class='btn_confirm'> -->
							<input type="button" value="  新   增  " class='btn_confirm'
								onclick="gotoCreateCampaign()">&nbsp;&nbsp;&nbsp;
							<!-- 	<input
								type="button" value="导出明细" onclick="exportCampaign()"
								class='btn_confirm'> -->
						</div>
						<div class='table'>
							<table class="tb-stores" cellspacing="2" cellpadding="2">
								<thead>
									<tr>
										<th>活动编号|创建日期</th>
										<th>活动名称</th>
										<th>活动状态</th>
										<th>操作</th>
									</tr>

								</thead>
								<tbody class="store-single">
									<c:forEach var="c" items="${campaigns }">
										<tr>
											<td>${c.number }<br><joda:format value="${c.dateCreated }" pattern="yyyy-MM-dd HH:MM:ss" /></td>
											<td>${c.name }</td>
											<td>${c.status.value }</td>
											<td><a href="${ctx }/traffic_red/detail.mbay?id=${c.id}">详细</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</form>
					<m:page pageinfo="${pageinfo}" formid="listCampaign" />
				</div>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>
