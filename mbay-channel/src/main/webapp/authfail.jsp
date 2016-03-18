<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>未实名认证</title>
<t:assets/>
<link href="${actx}/wro/${version}/message_notice.css"	rel="stylesheet" type="text/css" />
<style type="text/css">
.ddqr{padding-left:40px;background:url(${actx}/images/workimages/llcz.jpg) no-repeat left;color:#35618F;font-size:18px;height:36px;line-height:35px;line-height: 45px;margin-bottom:15px;font-family: 'Microsoft YaHei'}
#tijiao{margin-left:260px; text-align: center;cursor:pointer;display: inline-block;width: 120px;height: 37px; color:#fff;  font-family:'Microsoft YaHei'; background-color:#FDAB1B;   font-size:14px;border-radius: 4px;}
</style>

</head>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->   
             <div class='left_con'>
                <c:if test="${sessionScope.channel!=null}">
                <div class='ddqr'>操作失败</div>
                  <%@ include file="/common/leftcon.jsp" %>
               </c:if>
                <div class='dd fr'>
                   <div class='dd_con'>为了更好的保障您的账户交易安全，请完成实名认证后完成此操作！</div>
                  <a href="<c:url value="/certificate/auth/id_auth_apply.mbay"/>" target="_blank" ><input id='tijiao' type='button'  name='btn_2' value='立即认证'></a>
                </div>                   
             </div>
          <!--右边-->
             </div>
  <!--尾部--> 
</div>
</div>
</body>
</html>
