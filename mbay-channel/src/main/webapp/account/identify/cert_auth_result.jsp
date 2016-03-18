<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实名认证-美贝直通车</title>
<t:assets/>
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css" />
 <link href="${actx}/css/account/certauthresult.css" rel="stylesheet"    type="text/css" />
<script type="text/javascript"  src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"  src="${actx}/js/widget/jquery.Sonline.js"></script>

</head>
 <div class='con'>
    <!--头部-->
 <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->   
             <div class='left_con'>
                <c:if test="${sessionScope.channel!=null}">
                <div class='ddqr'>实名认证</div>
                  <%@ include file="/common/leftcon.jsp" %>
               </c:if>
                <div class='dd fr'>
                   <div class='dd_con'>${authInfo}</div>
                   <c:if test="${state == 'UNPASSED'}">
                    <a target="_blank" style="display:inline" href="<c:url value="/certificate/auth/reapply.mbay"/>">重新申请</a>
                   </c:if>
                </div>                   
             </div>
          <!--右边-->
             </div>
  <!--尾部--> 
</div>
</div>
</body>
</html>
