<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问失败</title>
<t:assets/>
<link href="${actx}/wro/${version}/error.css"	rel="stylesheet" type="text/css" />
</head>

<body>
 <div class='con'>
   <!--头部-->
   <!--身体-->
       <div class='body'>
          <div class='b_con com_width'>
             <img src='<c:url value="/images/workimages/404.jpg"/>' class='error'/>
             <div class='gif'>
                <img src="<c:url value="/images/workimages/404zfb.gif"/>" />
             </div>
             <div class='wz'> 
               <span class='tip'>抱歉！</span>您所要访问的页面不存在。
               <span class='yy'>可能被删除或暂时不可用，点击以下链接</span>
               <br /><a href='javascript:history.back(-1)'>&gt;&gt;返回上一步</a><br /><a href="<c:url value="/channel/workbench.mbay"/>">&gt;&gt;返回首页</a>
             </div>
          </div>
       </div>
  </div>
</body>
</html>
