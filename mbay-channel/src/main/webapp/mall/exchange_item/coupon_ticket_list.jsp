<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看券码</title>
<link href="${actx}/wro/${version}/mall_add.css" rel="stylesheet" type="text/css" />
<link href="${actx}/css/mall/coupon_ticket_list.css" rel="stylesheet" type="text/css" />
<script>
function delTicket(id){
	$.get(ctx+"/mall/exchangeItem/delTicket.mbay",{id:id},function(result){	
		if(result.success){
			$.messager.alert({ 
				content: "删除成功",
				button: {
					ok: {
						callback: function() {
							window.location.href = ctx + "/mall/exchangeItem/couponTicketList.mbay";
						}
					}
				}
			});
		}else{
			$.messager.alert({ content: result.message });
		}
	});
}

//之前审核过的记录无法进行操作(只有对第一次添加的数据提供删除)
function showMessage(){
	$.messager.alert({ content: "之前审核过的记录无法进行操作!" });
}
</script>
</head>
<body>
<section id="main-content" class="clearfix">
	<div class="breadcrumb">
		<a href="<c:url value='/mall/exchangeItem/list.mbay'/>">我的兑换项</a><i class="dbIcon dbIcon-angle-right"></i><span>查看券码</span>
	</div>
	<form class="form-horizontal" action="<c:url value='/mall/exchangeItem/couponTicketList.mbay'/>" method="get">
		<div class="content">
			<p class="mb-20"><span class="tip">注：</span>您可以按导入时间区域或者输入券码进行筛选。</p>
			<input type="hidden" name="itemNumber" value="${itemNumber}" />
			<div class="form-group">
				<input type="text" class="form-control" id="coupon-code" name="ticket" value="${ticket}" placeholder="请输入券码">
				<button class="btn btn-save" type="submit">筛选</button>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>ID</th>
						<th>券码</th>
						<th>密码</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="bean">
						<tr>
							<td>${bean.id}</td>
							<td class="code">${bean.ticket}</td>
							<td class="password">${bean.password}</td>
							<td>
								<i class="dbIcon dbIcon-delete delete-one">
									<c:if test="${audit eq 'NON'}">
										<a href="javascript:delTicket('${bean.id}')"></a>
									</c:if>
									<c:if test="${audit ne 'NON'}">
										<a href="javascript:showMessage()"></a>
									</c:if>
								</i>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="pagination">
			<m:page pageinfo="${pageinfo}" />
		</div>
	</form>
</section>
</body>
</html>