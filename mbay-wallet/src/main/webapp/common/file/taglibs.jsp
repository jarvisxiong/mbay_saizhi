<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.mbpartner.cn/jsp/fastdfs/tags" prefix="fs"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="actx" value="${pageContext.request.contextPath}" />
<c:set var="stsctx" value="http://192.168.21.181:8003/mbay-statistics" />

<c:set var="version" value="1.2.7" />
<%-- 
<c:set var="version" value="1.2.6" />
<c:set var="version" value="<%=System.currentTimeMillis()%>" />
--%>
