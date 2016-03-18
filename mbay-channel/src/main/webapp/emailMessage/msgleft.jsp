<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<head>
</head>

     <!--邮箱左边索引-->
      <div class='email_index fl'>
           <div class='mbyx_0'>
              <span class='mbyx_tit'>美贝邮箱</span>
              <a href='javascript: window.location.reload()'><img class='sx_0' src='<c:url value='/images/workimages/sx.png'/>'/></a>
           </div>
           <div class='mbyx_sec_1'>
              <a href='<c:url value='/message/writemessage.mbay' />'><div class='xx'>写信</div></a>
              <div><a href='<c:url value='/message/msg_list.mbay'/>'><div class='sjx fl'>收件箱</div></a><a href='#'>
              <div class='sl_1 fr' id="num">
			
			</div></a></div>
               <div>
               <a href='<c:url value='/message/out_msg_list.mbay'/>'>
              <div class='fjx fl'>发件箱</div></a><a href='#'></a>                   		
			</div>                  
           </div>
           <div class='mbyx_1'>
              <span class='mbyx_tit'>收藏夹</span>
              <a href='#'><img class='sx_1'  src='<c:url value='/images/workimages/scj_0.jpg'/>'/></a>
           </div>
           <div class='mbyx_sec_1 sp'>
              <div><a href='#'><div class='wmm fl'>收藏1</div></a><a href='#'><div class='sl_1 fr'>0</div></a></div>
              <div><a href='#'><div class='wmm fl'>收藏2</div></a><a href='#'><div class='sl_1 fr'>0</div></a></div>
           </div>
           <div class='index_zt'>                   
           </div>
        </div>
