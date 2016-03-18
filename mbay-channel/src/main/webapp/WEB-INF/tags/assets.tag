<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var ctx = '${pageContext.request.contextPath}';
	var actx = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript" src="${base}/webjars/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="${base}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${base}/js/mbayui.js"></script>
<%-- 内部通用样式   头尾 --%>
<link href="${base}/css/mbayui.css" rel="stylesheet" type="text/css" />
<%-- <script type="text/javascript"	src="${base}/webjars/jquery-cookie/1.3.1/jquery.cookie.js"></script> --%>