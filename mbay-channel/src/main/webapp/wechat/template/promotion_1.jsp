<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>兑换流量</title>
<link href="${actx}/wro/${version}/promotion_1.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/promotion_1.js"></script>
<script type="text/javascript"	src="${ctx}/webjars/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>
<div class="mb-wrap">
    <img class="head-big" src="<c:url value='/images/promotionCampaign/tou-big.png'/>">
    <div class="content-wrap">
        <form id="form">
           <input type="text" maxlength="16" id="dhm" placeholder="请输入兑换码">
                <p id="dhm_error"></p>
           <input type="tel" maxlength="11" id="sjh" placeholder="请输入手机号码">
                <p id="sjh_error"></p>
            <div class="ct-item">
                 <a href="#" class="duihuan-btn">立即兑换</a>
                 <a href="#" class="shuom-btn">兑换说明</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>