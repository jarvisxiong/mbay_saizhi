<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.mbpartner.cn/jsp/fastdfs/tags" prefix="fs"%>
<%@ taglib uri="http://www.mbpartner.cn/jsp/tags" prefix="m" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="t" %>

<%@ page language="java" import="org.sz.mbay.channel.web.util.SystemTime"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="actx" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=SystemTime.getNowDate()%>"></c:set>

<c:set var="version" value="09A"></c:set>

<!-- 调试用，调试js,css免清缓存 -->
<%-- 
<c:set var="version" value="09A"></c:set>
<c:set var="version" value="<%=System.currentTimeMillis()%>"></c:set> 
--%>