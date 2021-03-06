<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<c:url value="/images/favicon.png"/>" />
<title>操作失败-美贝直通车</title>
<t:assets/>
<style type="text/css">
.ddqr{padding-left:40px;background:url(${actx}/images/workimages/money.jpg) no-repeat left;color:#35618F;font-size:18px;height:36px;line-height:35px;line-height: 45px;margin-bottom:15px;font-family: 'Microsoft YaHei'}
</style>
<link href="${actx}/wro/${version}/message_notice.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"	src="${actx}/js/widget/jquery.Sonline.js"></script>
</head>
 <div class='con'>
<c:choose>
		<c:when test="${sessionScope.channel!=null}">
	       <%@ include file="/common/header_in.jsp"%>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->   
             <div class='left_con'>
                <c:if test="${sessionScope.channel!=null}">
                <div class='ddqr'>操作失败</div>
                  <%@ include file="/common/leftcon.jsp" %>
               </c:if>
                <div class='dd fr'>
                   <div class='dd_con'>${exception.message}</div>
                <c:if test="${exception.url != null}">
                	<a href='<c:url value="${exception.url }"/>'>返回</a>     
                </c:if> 
                </div>
             </div>
          <!--右边-->
             </div>
  <!--尾部--> 
</div>
</div>
 <%@ include file="/common/footer.jsp"%>
</body>
</html>
