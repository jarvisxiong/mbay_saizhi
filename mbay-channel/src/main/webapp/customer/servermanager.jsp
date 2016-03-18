<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户关怀</title>
<link href="${actx}/wro/${version}/servermanager.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/servermanager.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/widget/jquery.Sonline.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<style>
table{table-layout:fixed;}
table td{word-break:break-word;}
</style>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--中间-->
				<div>
					<form id="pagerForm"
						action="<c:url value="/customerserver/servermanager.mbay"/>"
						method="post">
						<div class='hdlb'>客户关怀-流量批充列表</div>
						<%@ include file="/common/leftcon.jsp"%>
						<div class='fr' style='width: 830px;'>
							<div class='lb'>
								<div class='hd'>
									<span class='hdbh'>批充名称：</span><input type='text' name="name"
										value="${info.name}" class='bh' />
									<!--  <span class='hdmc'>活动名称：</span><input type='text' name="eventname"class='mc'/>--->
									<input type="submit" class='hd_btn btn_confirm' value='查询'
										style='vertical-align: middle;' /> <input type='button'
										class='hd_btn btn_confirm' style='vertical-align: middle;'
										onclick="window.location.href='<c:url value="/customerserver/batchcharge/fill.mbay"/>'"
										value='新增' />
								</div>
							</div>
							<!--表格-->
							<div class='table'>
								<table>
									<tr>
										<td style='width:25%'>名称|创建日期</td>
										<td style="width:12%">批充方式</td>
										<td style="width:15%">流量大小</td>
										<td style="width:12%">美贝总额</td>
										<td style="width:12%">号码数量</td>
										<td style="width:12%">批充次数</td>
										<td style="width:12%">操作</td>
									</tr>
									<c:forEach items="${chargeinfoitems}" var="batchinfo">
										<tr>
											<td>${batchinfo.name}<br><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${batchinfo.createtime}" /></td>
											<td>${batchinfo.chargemethod.value} <c:if
													test="${batchinfo.chargemethod == 'PERIOD_CHARGE'}">
													<br />每月/${batchinfo.regulartime}号
		                          </c:if>
											</td>
											<td><c:forEach items="${batchinfo.strategys}"
													var="strategy">
		                        		${strategy.operator.value}全国${strategy.trafficpackage.traffic}M<br />
												</c:forEach></td>
											<td>${batchinfo.costmbay}</td>
											<td>${batchinfo.mobilenum}</td>
											<td>${batchinfo.chargetimes}</td>
										
											<td><a
												href='<c:url value="/customerserver/batch_mobileinfo.mbay?batchid=${batchinfo.id }"/>'>立即充值</a>
												<a
												href="<c:url value="/customerserver/batch_mobileinfo.mbay?batchid=${batchinfo.id }"/>">号码信息</a><br />
												<a
												href="<c:url value="/customerserver/batchcharge/record/charge_items.mbay?id=${batchinfo.id}"/>">充值记录</a><br />
												<!-- 
		                            <a href='#' class='ty'>停用</a>
		                            <a href='#' class='qy'>启用</a>
		                             --></td>
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
