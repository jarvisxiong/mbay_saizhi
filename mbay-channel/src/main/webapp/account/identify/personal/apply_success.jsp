<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>完成</title>
<t:assets/>
<link href="<c:url value="/css/person/5.css"/> " rel="stylesheet"	type="text/css" />
</head>
<body>
<div class='con'>    
     <!--头部-->
   	<%@ include file="/common/top.jsp"%>
    <%@ include file="/common/advert_out.jsp"%>
    <!--注册栏目-->
     <div class='reg_s'>
      <!--进度栏-->
        <div class='jindu'>
           <div class='jd com_width'>
             <div class='jd_box fl jd_1'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_box fl jd_3'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_box fl jd_5'></div>                
             <div class='wenzi_1'>提交认证信息</div>
             <div class='wenzi_3'>等待审核</div>
             <div class='wenzi_5'>完成实名认证</div>
             
           </div>
        </div>
      <!--企业注册-->
        <div class='zhuce'>
           <h3 >您的认证信息已成功提交，审核结果将于1-2个工作日内给你回复请你注意查看审核结果，谢谢！</h3>
           <div class='cbtn'>
           <c:choose>
		<c:when test="${sessionScope.channel!=null}">
           <a href='<c:url value="/channel/workbench.mbay"/>'><input id='btn' type='button' name='btn_1' value='返回首页'/></a>
		</c:when>
		<c:otherwise>
           <a href='<c:url value="/channel/index.mbay"/>'><input id='btn' type='button' name='btn_1' value='返回首页并登录'/></a>
		</c:otherwise>
	</c:choose>
           
           </div>
        </div>
        
     </div>
    <!--尾部-->
       <%@ include file="/common/footer.jsp" %>
 </div>
</body>
</html>


