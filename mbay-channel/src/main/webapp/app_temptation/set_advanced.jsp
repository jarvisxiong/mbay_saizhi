<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开发者模式</title>
</head>
 <div class='con'>
 <div class='body clearfix'>
     <div class='b_con com_width clearfix'>
        <!--左边-->               
        <div class='left_con fl'>                
           <div class='ddqr'>开发者中心</div>
           <c:set value="yes" scope="request" var="isAddReq"></c:set>
           <%@ include file="advanced_info.jsp" %>
           <%@ include file="/common/leftcon.jsp" %>
        </div>
  <!--尾部--> 
	</div>
</div>
 </div>
</body>
</html>