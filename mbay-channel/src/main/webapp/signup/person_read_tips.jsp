<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>阅读提示</title>
<link rel="shortcut icon" href="<c:url value="/images/favicon.png"/>" /> 
<t:assets/>
<link href="<c:url value="/css/person/1.css"/> " rel="stylesheet"	type="text/css" />
<script type="text/javascript"	 src="<c:url value="/css/person/1.js"/> "/></script>
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
</head>
<body>
<div class='con'>
    <!--注册栏目-->
     <div class='reg_s'>
      <!--进度栏-->
        <div class='jindu'>
           <div class='jd com_width'>
             <div class='jd_box fl jd_1'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_2'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_3'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_4'></div>
             <div class='jd_b fl'></div>
             <div class='jd_box fl jd_5'></div>
             
             <div class='wenzi_1'>阅读提示</div>
             <div class='wenzi_2'>注册</div>
             <div class='wenzi_3'>注册已完成</div>
             <div class='wenzi_4'>个人信息</div>
             <div class='wenzi_5'>完成</div>
           </div>
        </div>
      <!--企业账户-->
        <div class='zhuce com_width'>
           <div class='zhanghu'>
              <a  class='hu' href='#'><img src="<c:url value="/images/person/images/gr.jpg" />"></a>              
              <a  class='hu'  href='<c:url value="/channel/reg/company.mbay"/>'><img src="<c:url value="/images/person/images/qy.jpg" />"></a>
              <div class='sj'></div>
              <div class='zh_con'>
                 <div class='zcsm'>
                    <textarea disabled='disabled'>
1、个人直通车账户只限针对个人进行流量营销。
2、个人直通车账户注册开户提交的个人信息需真实有效。
3、个人直通车账户注册开户过程中，需提交资质扫描件进行审核。
4、单个扫描文件大小不大于2MB。接受格式为：JPG、PNG
5、个人直通车账户注册开户后我方需对资质审核，需等待1-2个工作日。
6、个人申请经审核后，我方会向注册手机和邮箱发送审核结果信息。</textarea>
                 </div>
                 <div class='yd'>
                   <input id="xuan" type='checkbox' class='cb'/><label for='xuan' >已明确理解提示全部内容</label>
                 </div>
                 <!--转入个人注册页  -->                
                 <input onclick="window.location.href='<c:url value="/channel/signup/person_signup.mbay?"/>'"  class='nextbtn' type='button' value='下一步'>             
               </div>
           </div>           
        </div>
     </div>
    <!--尾部-->
        <%@ include file="/common/footer.jsp" %>     
 </div>
</body>
</html>

