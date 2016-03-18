<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>app诱惑-活动详情-开发者中心</title>
<link href="<c:url value="/css/manage/strategyinfo.css"/> " rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<c:url value="/js/zclip/jquery.zclip.min.js" />"></script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
    <div class='b_con com_width'>
  	<!--左边-->    
       <div class='ckxq'>活动详情</div>
       <%@ include file="/common/leftcon.jsp" %>
       <!--右部内容-->
        <div class='fr xq_con'> 
        <div class='tab_tit clearfix'>
		 <ul>
		 <li><a href="<c:url value='/app_temptation/campaign_info.mbay?eventnumber=${eventNumber}'/>">基本信息</a></li>
		 <li class="nav_list_now"><a href="javascript:void(0)">开发者中心</a></li>
		 </ul>
		</div> 
		<%@ include file="advanced_info.jsp" %>
        </div>    
	</div>
 </div>
 </div>  
</body>
</html>