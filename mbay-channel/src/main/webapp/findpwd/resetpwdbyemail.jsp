<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重置密码-美贝直通车</title>
<t:assets/>
<script type="text/javascript"	src="${actx}/js/lib/jquery.validate.js"></script>
<link href="${actx}/wro/resetpwd.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript"	src="${actx}/wro/resetpwd.js"></script>
</head>
<body> 
<div class='con'>
   <!-- 头部 -->
	<%@ include file="/common/top.jsp"%>
    <%@ include file="/common/advert_out.jsp"%>
     <div class='reg_s'>  
        <div class='zhaohui'>
           <form id='pwdForm' action='<c:url value="/resetpwd/findpwd/resetpwdbyemai.mbay" />' method="post">
              <h2 class='zc_tit'>找回密码</h2>            
              <div class='newpwd'>
              <label for='password' class='jym_wz'><b>* </b>新密码：</label>
              <input type="hidden"  class="xmm" name='username' value="${username}" />
              <input type="hidden"  class="xmm" name='digest' value="${digest}" />
              <input type='password' id='password' class="xmm" name='password' placeholder='请输入密码' />
              </div>        
              <div style="height: 36px;"></div>     
              <div class='renewpwd'>
              <label for='repassword' class="sfz_wz"><b>* </b>确认新密码：</label>
              <input type='password' id='repassword' class='qrxmm' name='repassword' placeholder='请重复输入密码'/>
              </div>   
              <div class='anniu'>              
              <input id='tijiao' type='submit' name='sub' value='确认' />
              </div>
           </form>
        </div>        
     </div>
    <!--尾部-->
        <%@ include file="/common/footer.jsp" %>           
 </div>
</body>
</html>
