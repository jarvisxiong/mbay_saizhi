<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加公告</title>
<link href="${actx}/wro/${version}/base.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->               
          <div class='left_con ' style="min-height: 600px;">                
              <div class='ddqr'>公告信息</div>
            <%@ include file="/common/leftcon.jsp" %>  
                <div class='dd fr'>                              
                    <div class='czsl'>
                   <span class='czll'><b>*</b>公告名称：</span>
                   <span class='shu'>${notice.title}</span>
                   </div>  
                   <div class='sm'>
                   <span class='czll'><b>*</b>公告内容：</span>
                   <textarea maxlength="600" name="content" readonly="readonly"  placeholder='请输入：。。。'>${notice.content}</textarea>
                   </div>   
                     <div class='czsl'>
                   <span class='czll'><b>*</b>发布时间：</span>
                   <span class='shu'>${notice.noticedate}</span>
                   </div>               
                   <div class='an'>
                   <input type='button' onclick="window.location.href='<c:url value='/message/notice_list.mbay'/>'"  class='an_btn' value='返回'/>
                   </div>                 
                </div>               
           </div>              
</div>
</div>
 </div>
</body>
</html>





