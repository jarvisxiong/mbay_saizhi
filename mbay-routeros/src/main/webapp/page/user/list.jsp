<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="/WEB-INF/tags/mbaytags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>routeros</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table_list.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/mbayui.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/web_common.css" />" />
<style type="text/css">
.table {
	width: 600px;
	margin: 0 auto;
	margin-top: 60px;
}
.search {
	padding: 15px 0;
}
</style>
<script type="text/javascript" src="<c:url value="/js/jquery-1.7.2.min.js" />"></script>
<script type="text/javascript">

</script>
</head>
<body>
<form action="<c:url value="/user/list.mbay"/>" method="post">
	<div class='table'>
		<div class="search">
			<label>comments：</label>
			<input type="text" name="comments" class="bh" value="${userQO.comments }" autocomplete="off" />
			<input type="submit" value="查询" class="hd_btn" />
		</div>
		<table cellpadding="0" cellspacing="0" class="list-table">
			<thead>
				<tr>
					<th>userid</th>
					<th>username</th>
					<th>comments</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${userList }">
					<tr>
						<td>${user.userId }</td>
						<td>${user.userName }</td>
						<td>${user.comments }</td> 
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<m:page pageinfo="${pageinfo}"/>
	</div>
</form>
</body>
</html>