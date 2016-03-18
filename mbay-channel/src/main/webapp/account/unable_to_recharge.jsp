<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作失败</title>
<link href="${actx}/wro/${version}/message_notice.css"	rel="stylesheet" type="text/css" />
<style type="text/css">
.ddqr{padding-left:40px;background:url(${actx}/images/workimages/money.jpg) no-repeat left;color:#35618F;font-size:18px;height:36px;line-height:36px;margin:4px 0 10px 0;font-family: 'Microsoft YaHei'}
</style>
</head>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->   
             <div class='left_con'>
                <c:if test="${sessionScope.channel!=null}">
                <div class='ddqr'>美贝充值</div>
                  <%@ include file="/common/leftcon.jsp" %>
               </c:if>
                <div class='dd fr'>
                   <div class='dd_con'>请联系您的上级为您充值！</div>
         <input type="button" onclick="javascript:window.history.go(-1);" class="btn_confirm" value="返回" >             
                </div>
             </div>
          <!--右边-->
             </div>
  <!--尾部--> 
</div>
</div>
</body>
</html>
