<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>门店红包墙页面</title>
<t:mobile-assets />
<meta name="viewport"
	content="user-scalable=no, width=device-width, initial-scale=1.0" />
<link type="text/css" rel="stylesheet"
	href="${actx }/css/bootstrap3/bootstrap.min.css">
</head>
<body>
	<div class="container-fluid">
		<c:forEach var="c" varStatus="s" items="${campaigns }">
			<div class="panel panel-primary">
				<div class="panel-heading">${c.name }</div>
				<a href='${ctx }/campaign/join/${store.id}/${c.id}/detail.mbay'>
					<div class="row">
						<div class="col-xs-6">
							<div class="content-money" style="text-align: center">
								<span class="glyphicon glyphicon-yen" aria-hidden="true"
									style="font-size: 50px;"></span> <span
									style="font-size: 100px;">${c.price }</span>
							</div>
						</div>
						<div class="col-xs-6">
							<div class="list-group">
								<div class="list-group-item">活动名称: ${c.name }</div>
								<div class="list-group-item">
									活动有效期至:
									<joda:format value="${c.endTime }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</div>
								<div class="list-group-item">
									活动官方链接: <a href="http://${c.link }">${c.link }</a>
								</div>
								<div class="list-group-item">${c.describtion }</div>
							</div>
						</div>
					</div>
				</a>
			</div>
		</c:forEach>
	</div>
</body>
</html>