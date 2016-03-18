<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动初始化门店</title>
<link href="${actx}/wro/${version}/workbenches.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript"
	src="${actx}/js/o2o_campaign/campaign/chooseStore.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var campaignId = '${campaignId}';
	function gotoCampaignManage() {
		window.location.href = ctx + "/campaign/list.mbay";
	}
	function gotoStoreManage() {
		window.location.href = ctx + "/store/list.mbay";
	}
	function initStoreUI() {
		window.location.href = ctx + "/store/initui.mbay";
	}
</script>
<style>
  #store{margin-bottom:10px}
  #store input{margin-right:10px}
</style>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div>
					<div class='hdlb'>O2O门店活动平台</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='lb fr' style='width: 830px'>
						<div class='hd'>
							<div class='r_box'>
								<c:choose>
									<c:when test="${empty storeslist }">
										<div>没有可选门店</div>
										<div>
											<a href="${ctx }/store/initui.mbay">添加门店</a>
										</div>
										<div>
											<a href="${ctx }/store/list.mbay">门店列表</a>
										</div>
									</c:when>
									<c:otherwise>
										<form id='liststore' method="post"
											action="${ctx }/campaign/chooseStore.mbay" class='clearfix'>

											<input type="hidden" name="campaignId" value="${ campaignId}">
											<div id='store'>
												<input type="button" id="select-Store" value="选择"
													class='btn_confirm'><input
													type="button" value="活动列表" class='btn_confirm'
													onclick="gotoCampaignManage()"><input
													type="button" value="门店列表" class='btn_confirm'
													onclick="gotoStoreManage()"><input
													type="button" value="初始化门店" class='btn_confirm'
													onclick="initStoreUI()">
											</div>
											<div class='table'>
												<table class="tb-stores" cellspacing="2" cellpadding="2">
													<tr>
														<th><input type="checkbox" id="chooseAll"> <label>全选</label></th>
														<th>门店编号</th>
														<th>门店名称</th>
														<th>授权码</th>
														<th>激活</th>
														<th>门店状态</th>
													</tr>
													<tbody class="store-single">
														<c:forEach var="store" items="${storeslist }">
															<tr>
																<td><input type="checkbox" name="store"
																	alt="${store.id }" class="checkStore"
																	value="${store.id }"></td>
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
																<td>${store.enable }</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</form>
										<m:page pageinfo="${pageinfo}" formid="liststore" />
									</c:otherwise>
								</c:choose>
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