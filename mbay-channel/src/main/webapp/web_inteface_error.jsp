<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>
<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<t:assets/>
<title>调试错误-美贝直通车</title>
<link rel="icon" href="${actx}/images/favicon.png" />

<script src="${actx}/js/index/head.js"></script>
<style type="text/css">
*:root{margin:0;padding:0;}
.cc{border:1px solid #C1C1C1;border-radius:5px;width:500px; margin: 0 auto;background-color: #fff;height:150px;}
.cc>p{height:20px; line-height:15px;padding:5px;}
.cc_text{margin-top:10px;}
</style>
<script src="${actx}/js/index/head.js"></script>
</head>
<body>
<div class="cc">
	<p class="cc_text">调试错误，请检查请求信息，重新发起请求！</p>
	<p class="cc_msg">错误代码：${exception.code }</p>
</div>
 <%@ include file="/common/footer.jsp"%>
</body>
</html>