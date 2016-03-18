<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="<c:url value="/images/favicon.ico"/>" />  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册成功</title>
<t:assets/>
<link rel="shortcut icon" href="<c:url value="/images/favicon.png"/>" /> 
<link href="<c:url value="/css/person/3.css"/> " rel="stylesheet"	type="text/css" />
</head>
<body>
<div class='con'>    
    <!--注册栏目-->
     <div class='reg_s'>
      <!--进度栏-->
        <div class='jindu'>
           <div class='jd com_width'>
             <div class='jd_box fl jd_1'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_box fl jd_2'></div>
             <div class='jd_b fl sp'></div>
             <div class='jd_box fl jd_3'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_4'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_5'></div>
             
             <div class='wenzi_1'>阅读提示</div>
             <div class='wenzi_2'>注册</div>
             <div class='wenzi_3'>注册已成功</div>
             <div class='wenzi_4'>个人信息</div>
             <div class='wenzi_5'>完成</div>
             
           </div>
        </div>
      <!--注册完成-->
        <div class='zhuce'>
            <h3>恭喜您注册个人直通车账户成功！</h3>
            <div class='p_tip'><div class='tip_con'><h5 style='font-size:16px'>注册成功</h5>您可以现在登录继续完善账户信息<br/>同时您也可以稍后完善账户信息<br/> 完善账户信息过程中您需提交的电子档文件资料<br/>                 
                 1、中国公民身份证(第二代) 扫描件<br/>
                  2、个人与我司签订的免费服务协议 拍照件</div>
            </div>
            <div class='anniu'>
            <a href='<c:url value="/channel/index.mbay"/>'><input id='fanhui' type='button' name='btn_1' value='返回首页并登陆'/></a>                    
               <input id='tijiao' type='button' onclick="window.location.href='<c:url value="/certificate/auth/id_auth_apply.mbay"/>'" name='btn_2' value='继续完善账户信息'>
            </div>
        </div>        
     </div>
    <!--尾部-->
  <%@ include file="/common/footer.jsp" %>
     
 </div>
</body>
</html>
