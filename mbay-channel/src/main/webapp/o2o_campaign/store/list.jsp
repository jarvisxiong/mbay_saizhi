<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>门店管理</title>
<link href="${actx}/wro/${version}/table_list.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<c:url value="/js/lib/Validform_v5.3.2_min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/js/o2o_campaign/store/store.js" />"></script>
<style type="text/css">
#edit_template {
	display: none;
}

#add_template {
	display: none;
}

#store-ope {
	margin-bottom: 10px
}

#store-ope input {
	margin-right: 10px
}

.qrcode_600 {
	width: 600px;
	height: 600px;
	border: 1px solid red;
}
</style>

<script type="text/javascript">

function wansan() {
	alert("还在完善!");
}
function exportStore() {
	window.location.href = ctx + "/store/store.xls";
}
function showQRCode(storeId) {
	var link="${ctx }/qrcode/"+storeId+"/get.mbay?size=600";
}
</script>

</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<div class='hdlb'>门店管理</div>
				<!--左边-->
				<%@ include file="/common/leftcon.jsp"%>
				<div class='fr hd_con_r'>
					<form id='liststore' method="post"
						action="<c:url value="/store/list.mbay"/>" class='clearfix'>
						<div id="store-ope">
							<!-- <input type="text" name="number" class='input_txt' />&nbsp;&nbsp;&nbsp;
							<input type="button" value="搜索" onclick="query(this)"
								class='btn_confirm'> -->
							<input type="button" value="新增" onclick="addStore(this)"
								class='btn_confirm'><input type="button" value="批量删除"
								onclick="batchDeleteStore(this)" class='btn_confirm'><input
								type="button" value="导出明细" onclick="exportStore()"
								class='btn_confirm'><input type="button" value="批量下载二维码"
								onclick="wansan()" class='btn_confirm'>
						</div>
						<div class='table'>
							<table class="tb-stores" cellspacing="2" cellpadding="2">
								<tr>
									<th><input type="checkbox" id="all-store"> <label>全选</label></th>
									<th>门店编号</th>
									<th>门店名称</th>
									<th>授权码</th>
									<th>激活状态</th>
									<th>门店状态</th>
									<th>二维码</th>
									<th>操作</th>
								</tr>
								<tbody class="store-single">
									<c:forEach var="store" items="${storelist }">
										<tr>
											<td><input type="checkbox" name="store"
												value="${store.id }" class="choose-store"></td>
											<td>${store.number}</td>
											<td>${store.name}</td>
											<td>${store.authCode}</td>
											<td><c:choose>
													<c:when test="${store.active}">
								激活
							</c:when>
													<c:otherwise>
								未激活
							</c:otherwise>
												</c:choose></td>
											<td>${store.enable==true?"启用":"禁用" }</td>
											<td><a
												<%-- href="${ctx }/qrcode/${store.id}/get.mbay?size=600" --%>
												onclick="showQRCode('${store.id}')">查看</a>&nbsp;&nbsp;&nbsp;<a
												href="${ctx }/qrcode/${store.id}/download.mbay">下载</a></td>
											<td><a
												href="${pageContext.request.contextPath }/store/detail.mbay?id=${store.id }">详细</a>&nbsp;&nbsp;&nbsp;<a
												href="#${store.id }"
												onclick="editStore('${store.id}','${store.number}',${store.enable },'${store.name }')">编辑</a>&nbsp;&nbsp;&nbsp;<a
												href="#${store.id }" onclick="deleteStore(this)">删除</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</form>
					<m:page pageinfo="${pageinfo}" formid="liststore" />

					<div id="add_template" class="add_template">
						<form class="add_store_form">
							<div style='padding: 20px'>
								<div style='margin-bottom: 20px'>
									<label
										style='width: 190px; text-align: right; display: inline-block; margin-right: 5px'>新增参与门店数目:</label><input
										type="text" class='input_txt storeNum' name="storeNum"
										datatype="n" errormsg="格式不正确！"><span
										class="Validform_checktip" style='width: 200px'> 数字</span>
								</div>
								<div style='margin-bottom: 20px'>
									<label
										style='width: 190px; text-align: right; display: inline-block; margin-right: 5px'>门店操作员数目:</label><input
										type="text" class='input_txt operatorNum' name="operatorNum"
										datatype="n" errormsg="格式不正确！"><span
										class="Validform_checktip" style='width: 200px'> 数字</span>
								</div>
							</div>
						</form>
					</div>
					<div id="edit_template">
						<div style='padding: 20px'>
							<div style='margin-bottom: 20px'>
								<label
									style='width: 190px; text-align: right; display: inline-block; margin-right: 5px'></label><input
									type="hidden" class='input_txt s_id' name="storeNum" value="">
							</div>
							<div style='margin-bottom: 20px'>
								<label
									style='width: 190px; text-align: right; display: inline-block; margin-right: 5px'>门店编号:</label><input
									type="text" disabled="disabled" class='input_txt s_number'
									name="storeNum">
							</div>
							<div style='margin-bottom: 20px'>
								<label
									style='width: 190px; text-align: right; display: inline-block; margin-right: 5px'>门店名称:</label><input
									type="text" class='input_txt s_name' name="operatorNum">
							</div>
							<div style='margin-bottom: 20px'>
								<label
									style='width: 190px; text-align: right; display: inline-block; margin-right: 30px;'>门店状态:</label><input
									type="radio" class='s_enable' name="enable" value="0"><label
									style="margin-right: 10px">禁用</label><input type="radio"
									class='s_enable' name="enable" value="1"><label>启用</label>
							</div>
						</div>
					</div>
				</div>

				<!-- <div class="qrcode_600"></div> -->
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>
