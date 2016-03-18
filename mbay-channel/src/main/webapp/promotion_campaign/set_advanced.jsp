<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开发者模式</title>
<t:assets/>
<link href="<c:url value="/css/manage/developmodel.css"/> " rel="stylesheet"	type="text/css" />
<script type="text/javascript"	src="${actx}/js/inside_right_part/common_js.js"></script>	
</head>
 <div class='con'>
 <div class='body clearfix'>
     <div class='b_con com_width clearfix'>
        <!--左边-->               
             <div class='left_con fl'>                
                <div class='ddqr'>促销神器 【${campaignName}】 - 开发者中心</div>
                 <%@ include file="advanced_info.jsp" %>
                 <%@ include file="/common/leftcon.jsp" %>
             </div>
  <!--尾部--> 
</div>
</div>
 </div>
</body>
</html>