<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=0" />
<meta http-equiv="Access-Control-Allow-Origin" content="*"/>
<title>流量下发成功</title>
<t:assets/>
<link href="${actx}/wro/${version}/trafficsuccess.css"	rel="stylesheet" type="text/css" />
</head>
<body>
  <div class='con'>
    <div class='wx_con'>
      <div class='wz_empty'></div>
      <div class='wz_txt'>
        <p>免费流量已经成功下发,因不同</p>
        <p>运营商流量生效时间有所差异,</p>
        <p>请在1-2个工作日后查询,详情见  <a  href='<c:url value="/wechat/trafficQuery.jsp" />' class='bold'>兑换说明</a></p>
        <p class='sp_wz'>Copyright©2014赛志科技保留所有权利</p>
      </div>
    </div>
  </div>
</body>
</html>