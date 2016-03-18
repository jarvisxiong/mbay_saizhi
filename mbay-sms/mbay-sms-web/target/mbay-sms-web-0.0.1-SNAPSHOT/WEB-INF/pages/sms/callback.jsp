<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>短信发送平台</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/sms/callback.mbay"
		method="post">
		<table align="center">
			<tr>
				<td>主叫号码：</td>
				<td><input size="40" name="mainMobile" type="text" /></td>
			</tr>
			<tr>
				<td>被叫号码：</td>
				<td><input size="40" type="text" name="toMobile" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="发送" /></td>
			</tr>
		</table>
	</form>
</body>
</html>