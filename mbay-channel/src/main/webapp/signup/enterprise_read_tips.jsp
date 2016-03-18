<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>阅读提示</title>
<script type="text/javascript"	src="${actx}/webjars/jquery/1.8.3/jquery.min.js"></script>
<link href="${actx}/wro/${version}/enterprise_read_tips.css" rel="stylesheet"	type="text/css" />
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
             <div class='wenzi_3'>联系人信息</div>
             <div class='wenzi_4'>企业信息</div>
             <div class='wenzi_5'>完成</div>
           </div>
        </div>
      <!--企业账户-->
        <div class='zhuce com_width'>
           <div class='zhanghu'> 
              <!--<a class='hu' href='#'><img src="<c:url value="/images/enterprise/images/gr.jpg" />"></a> -->             
              <div class='hu' ><img src="<c:url value="/images/enterprise/images/qy.jpg" />"></div>
              <!--<div class='sj'></div> -->
              <div class='zh_con'>
                 <div class='zcsm'>
               <textarea disabled='disabled'>
1、企业直通车账户只限针对企业进行流量营销。
2、企业直通车账户注册开户提交的企业信息需真实有效。
3、企业直通车账户注册开户过程中，需提交资质扫描件进行审核。
4、资质需盖当期年检章，单个文件大小不大于2MB。
5、企业直通车账户注册开户后我方需对资质审核，需等待1-2个工作日。
6、企业经审核后，我方会向注册手机和邮箱发送审核结果信息。</textarea>
                 </div>
               <!--   <div class='yd'>
                   <input id="xuan" type='checkbox' class='cb'/><label for="xuan" >已明确理解提示全部内容</label>
                 </div>                 -->
                 <div class='btn_con'>
                    <button class="btnq btn_reset" onclick="window.location.href='<c:url value="/channel/index.mbay"/>'">我不同意这些条款</button>
                    <button class="btnq" onclick="window.location.href='<c:url value="/signup/enterprise.mbay"/>'">我同意这些条款</button>
                    
                 </div>
              </div>
           </div>
           
        </div>
     </div>
    <!--尾部-->
       <%@ include file="/common/footer.jsp" %>
 </div>
</body>
</html>
