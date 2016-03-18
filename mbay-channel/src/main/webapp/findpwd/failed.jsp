<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>密码找回失败-美贝直通车</title>
<t:assets/>
<link href="${actx}/wro/failed.css" rel="stylesheet"	type="text/css" />
</head>
<body>
<div class='con'>    
	<!-- 头部 -->
	<%@ include file="/common/top.jsp"%>
    <%@ include file="/common/advert_out.jsp"%>
    <!--注册栏目-->
     <div class='reg_s'> 
      <!--进度栏-->
       
      <!--个人注册-->
       <h2 style="margin-left: 600px;">密码找回失败：</h2>
        <div class='zhuce' style="height: 260px;">  
            <!--当前申请用户绑定的手机号  -->
              <div>
                 <h2 style="margin-left: 200px; margin-top: 30px;">通过人工找回密码，服务电话：4006-210-166</h2>
                
              </div>  
        </div>        
     </div>
    <!--尾部-->
        <%@ include file="/common/footer.jsp" %>
 </div>
</body>
</html>
