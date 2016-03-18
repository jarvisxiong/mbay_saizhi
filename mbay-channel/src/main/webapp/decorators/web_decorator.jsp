<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="icon" href="<c:url value="/images/favicon.png"/>" />
<title><decorator:title default="Welcome!" />-美贝直通车</title>
<t:assets/>
<decorator:head />
</head>
<body 
	<decorator:getProperty property="body.id" writeEntireProperty="true"/> 
	<decorator:getProperty property="body.class" writeEntireProperty="true"/>
	<decorator:getProperty property="body.delegate" writeEntireProperty="true" />>
	
	<div id="header">		
	    <%@ include file="/common/header_in.jsp"%>
	</div>

	<div id="mainbody">
		<decorator:body />
	</div>
	<div id="footer">
			<%@ include file="/common/footer.jsp"%>
	</div>

</body>
</html>