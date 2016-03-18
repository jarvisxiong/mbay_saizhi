<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">

<title><decorator:title default="Welcome!" />-美贝钱包</title>

<%@ include file="/common/file/base-style.jsp" %>
<%@ include file="/common/file/base-script.jsp" %>
<decorator:head />
</head>
<body 
	<decorator:getProperty property="body.id" writeEntireProperty="true" /> 
	<decorator:getProperty property="body.class" writeEntireProperty="true" />
	<decorator:getProperty property="body.delegate" writeEntireProperty="true" />>
	<decorator:body />
</body>
</html>