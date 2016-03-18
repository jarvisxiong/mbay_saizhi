<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	领取失败啦
	<div>
		<label>失败代码：</label>&nbsp;${result.error_code }
	</div>
	<div>
		<label>失败消息：</label>&nbsp;${result.message }
	</div>
</body>
</html>