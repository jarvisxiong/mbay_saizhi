<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码-美贝直通车</title>
<t:assets/>
<link href="${actx}/wro/manual-common.css" rel="stylesheet"	type="text/css" />
<body>
<div class='con'>  
    <!-- 头部 -->
	<%@ include file="/common/top.jsp"%> 
    <%@ include file="/common/advert_out.jsp"%>
    <!--分割线-->
    <div class='hr_30'></div>
    <!--注册栏目-->
      <div class='reg_s'>
      <!--个人注册-->
        <div class='zhuce'>
      		<div class='zh_tit'>
               <span class='tip'>密码找回失败：</span>
            </div>
            <div class='use'>
            <label for='uname'></label>您的找回密码链接已经失效，请重新申请！
            </div>       
            <div class='anniu'>            
              <a href="<c:url value="/resetpwd/findpwd/tofind.mbay"/>"><input id='tijiao' type='button'  name='sub' value='重新申请' /></a>
            </div>          
        </div>      
     </div>
    <!--尾部-->
        <%@ include file="/common/footer.jsp" %>
 </div>
 
</body>
</html>
