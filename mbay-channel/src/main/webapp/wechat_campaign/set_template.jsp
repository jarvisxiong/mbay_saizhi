<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>设置活动模板</title>
<t:assets />
<link href="${actx}/css/wechat_campaign/set_template.css"
	rel="stylesheet" type="text/css" />
<link href="${actx}/css/wechat_campaign/advanced.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<div class='hdlb'>微信伴侣 【${campaignName}】- 页面编辑</div>
				<%@ include file="/common/leftcon.jsp"%>
				<div class='xq'>
					<%@ include file="template_info.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>