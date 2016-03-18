<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码-美贝直通车</title>
<link href="${actx}/wro/manual.css" rel="stylesheet"	type="text/css" />
<t:assets/>
<script type="text/javascript"	src="${actx}/js/lib/jquery.validate.js"></script>
</head>
<body>
<div class='con'>     
	<!-- 头部 -->
	<%@ include file="/common/top.jsp"%>
    <%@ include file="/common/advert_out.jsp"%>
    <!--分割线-->
       <div class='hr_30'></div>
    <!--注册栏目-->
     <div class='reg_s'>
      <!--进度栏-->
       
      <!--个人注册-->
      
        <div class='zhuce' style="height: 260px;">  
            <h2 class='tip' style="">您的账号未提交认证信息：</h2>
            <!--当前申请用户绑定的手机号  -->
              <div>
                 <h2 class='tip_text' style="">通过人工找回密码，服务电话：4006-210-166</h2>
                
              </div>  
        </div>        
     </div>
    <!--尾部-->
        <%@ include file="/common/footer.jsp" %>
 </div>
</body>
</html>
