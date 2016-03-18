<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="<c:url value="/images/favicon.png"/>" /> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>活动策略</title>
    <meta name="description" content="">
    <%-- <link href="<c:url value="/style/global-min.css"/> " type="text/css" rel="stylesheet">
    <link href="<c:url value="/style/account-settings.css"/> " type="text/css" rel="stylesheet"> --%>
    <script type="text/javascript" src="<c:url value="/requiered/jquery.js" />"></script>
</head>
<body>
<div id="wrapper">
    <form id="pagerForm" action="<c:url value="/strategy/strategymanage.mbay"/>" method="post">
        <div style="margin:0 20px;min-height: 500px;">
        <div class="title-items">
            <h2>手机流量充值记录</h2>
            <b class="line"></b></div>
        <div class="leditBtn"></div>
        <div class="biaoge">
            <table>
                <tr>
                    <td>流水号</td>
                    <td>日期</td>
                    <td>号码</td>
                    <td>流量</td>
                    <td>美贝价格</td>
                    <td>状态</td> 
                </tr>
                <c:forEach items="${trafficcharges}" var="chargerecord">
                    <tr>
                        <td>${chargerecord.number}</td>
                        <td>${chargerecord.createtime}</td>
                        <td>${chargerecord.mobile}</td>          
                        <td>${chargerecord.traffic}</td>          
                        <td>${chargerecord.mbay}</td>          
                        <td>${chargerecord.status}</td>          
                        <td>
                           <a href="<c:url value="/strategy/strategyinfo.mbay?strategyid=${strategy.id}"/>" >兑换详情</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>           
           <m:page pageinfo="${pageinfo}"/>
        </div>
        </div>
    </form>
    
  
</div>
</body>
</html>
