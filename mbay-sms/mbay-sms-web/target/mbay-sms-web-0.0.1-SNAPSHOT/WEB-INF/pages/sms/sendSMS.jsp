<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>短信发送平台</title>
</head>
<body>
	<form action="${pageContext.request.contextPath	}/sms/sendsms.mbay"
		method="post">
		<table align="center">
			<tr>
				<td>手机号：</td>
				<td><input name="mobiles" size="50" type="text" />多个手机号以英文","隔开</td>
			</tr>
			<tr>
				<td>短信模版Id：</td>
				<td><input name="smsId" type="text" /></td>
			</tr>
			<tr>
				<td>内&nbsp;&nbsp;容：</td>
				<td><textarea name="param" rows="3" cols="58"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="发送" /></td>
			</tr>
		</table>
	</form>
</body>
</html>