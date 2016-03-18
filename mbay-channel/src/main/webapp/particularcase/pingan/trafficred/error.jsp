<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta content="telephone=no" name="format-detection">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
	<title>提示</title>
	<t:mobile-assets/>
	<link rel="stylesheet" href="${ctx}/particularcase/pingan/trafficred/css/public.css" />
	<link rel="stylesheet" href="${ctx}/particularcase/pingan/trafficred/css/new_file.css" />
	<script type="text/javascript" src="${ctx}/particularcase/pingan/trafficred/js/new_file.js" ></script>
</head>
<body>
<section  class="mistake_page">
    <div class="mistake_page_content">
        <header class="img">
            <img src="${ctx}/particularcase/pingan/trafficred/img/404.png">
        </header>
        <div class="mistake_message">
            <p>${errorMsg}</p>
            <p>请输入新的手机号或进入MB钱包查看已领取的MB</p><br/>
        </div>
        <footer>
            <a class="two_import" href="<c:url value='/pingan/trafficred/index.mbay'/>">重新输入</a>  
            <a class="MB_wallet" href="${walletIndex}">MB钱包</a>
        </footer>
    </div>
</section>
</body>
</html>