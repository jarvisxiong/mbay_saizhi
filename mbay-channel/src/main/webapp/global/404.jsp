<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝直通车-NOT FOUND</title>
<t:assets/>
<link href="${actx}/wro/${version}/prompt.css"	rel="stylesheet" type="text/css" />
<link href="<c:url value="/images/favicon.ico"/>" rel="icon"	type="image/x-icon" />
<script>
$(document).ready(function(){	
$(".js_goindex").bind("click",function(){
	<c:if test="${sessionScope.channel==null}">
	    window.location.href="<c:url value="/channel/index.mbay"/>";
	</c:if>
	<c:if test="${sessionScope.channel!=null}">
	    window.location.href="<c:url value="/channel/workbench.mbay"/>";
	</c:if>
});
});
</script>
</head>
<body>
	<div class='con'>
		<div class='body'>
			<div class='error_tip'>
				<img src="<c:url value="/images/404error.png"/>" />
			</div>
			<div class='sec_404'>
				<div class='wz_404'>抱歉，你撞到了不存在的页面！</div>
				<div class='an_404'>
					<button class='js_goindex'>返回首页</button>
					<button onclick="javascript:window.history.go(-1)">返回上一页</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
