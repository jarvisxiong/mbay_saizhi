<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:assets />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置活动模板</title>
<link href="${actx}/wro/${version}/set_template.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/set_template.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery.validate.js"></script>
</head>

<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<div class='hdlb'>促销神器 【${campaignName}】 - 页面编辑</div>
				<%@ include file="/common/leftcon.jsp"%>
				<%@ include file="template_info.jsp"%>
			</div>
		</div>
	</div>
</body>
</html>