<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>XMPP消息推送</title>
<meta name="menu" content="session" />
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/tablesorter/style.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

<div style="margin:20px 0px;">
	<div class="search">
		<form action="<c:url value="/session/list.mbay" />" method="post">
			<input type="text" class="input-txt" placeholder="应用" name="application" value="${applicationQO }" autocomplete="off" />
			<input type="submit" value="查询" class="btn-primary" />
		</form>
	</div>
	<table id="tableList" class="tablesorter" cellspacing="1">
		<thead>
			<tr>
				<th>应用</th>
				<th>用户名</th>
				<th>通信状态</th>
				<th>用户状态</th>
				<th>IP</th>
				<th>更新日期</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sess" items="${sessionList}">
				<tr>
					<td><c:out value="${sess.application}" /></td>
					<td><c:out value="${sess.userId}" /></td>
					<td align="center"><c:out value="${sess.status}" /></td>
					<td>
						<c:choose>
							<c:when test="${sess.presence eq 'Online'}">
								<img src="<c:url value='/images/user-online.png'/>" class="state-img" /><span>在线</span>
							</c:when>
							<c:when test="${sess.presence eq 'Offline'}">
								<img src="<c:url value='/images/user-offline.png' />" class="state-img" /><span>离线</span>
							</c:when>
							<c:otherwise>
								<img src="<c:url value='/images/user-away.png' />" class="state-img" /><span>离开</span>
							</c:otherwise>
						</c:choose>
					</td>
					<td><c:out value="${sess.clientIP}" /></td>
					<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sess.createdDate}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
//<![CDATA[
$(function() {
	$('#tableList').tablesorter();
	$('table tr:nth-child(even)').addClass('even');	 
});
//]]>
</script>

</body>
</html>
