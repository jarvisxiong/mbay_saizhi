<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>找回密码-美贝直通车</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${actx}/wro/manual-common.css" rel="stylesheet"	type="text/css" />
<t:assets/>
<script type="text/javascript"	src="${actx}/js/lib/jquery.validate.js"></script>
</head>
<body>   
<!-- 头部 -->
	<%@ include file="/common/top.jsp"%>
    <%@ include file="/common/advert_out.jsp"%>
       <div class='hr_30'></div>
    <!--注册栏目-->
     <div class='reg_s'>
      <!--进度栏-->
        <div class='zhaohui'>
           <div class='zh_tit '>
               <span class='tip'>您未实名认证，请选择“<span id='sp_wz'>人工服务</span>”</span>
           </div>
           <div class='zh_con'>
                                                 联系电话：4006-210-166
           </div>
        </div>
      <!--尾部-->
      <%@ include file="/common/footer.jsp" %>        
     </div>
</body>
</html>
