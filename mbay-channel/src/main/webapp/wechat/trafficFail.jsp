<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=0" />
<meta http-equiv="Access-Control-Allow-Origin" content="*"/>
<title>兑换失败</title>
<style>
.con{max-width: 400px;margin: 0px auto;}
.con,.con img{width:100%;height:100%;background:url(../images/traffic_fail.jpg) no-repeat center}
</style>
</head>

<body>
  <div class='con'>
      <img src="<c:url value='/images/traffic_fail.jpg'/>" />
  </div>
</body>
</html>
