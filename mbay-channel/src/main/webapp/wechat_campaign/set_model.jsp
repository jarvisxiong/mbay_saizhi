<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择活动模式</title>
<script type="text/javascript"
	src="<c:url value="/js/lib/banbackbrowser.js" />"></script>
<link href="${actx}/wro/${version}/wechat_campaign_selectpattern.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='ddqr'>微信伴侣 【${campaignName}】 - 选择活动模式</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='dd fr'>
						<div class='bjms'>
							<div class='bj fl'>
								<img src='<c:url value='/images/workimages/bjms.jpg'/>' />
							</div>
							<div class='ms fl'>
								<div class='js'>
									<div class='js_tit'>
										<span class='js_bj'>编辑模式</span> <input type='button'
											class='js_button_1 modeltype' value='设置'
											onclick="window.location.href='<c:url value="/wechatCampaign/to_set_template.mbay?campaignNumber=${campaignNumber}"/>'" />
									</div>
									<div class='js_con'>
										<span>介绍：</span><span>编辑模式指您可以简单的通过选取活动模板实现流量营销</span>
									</div>
								</div>
							</div>
						</div>
						<div class='kfzms'>
							<div class='kfz fl'>
								<img src='<c:url value='/images/workimages/kfzms.jpg'/>' />
							</div>
							<div class='ms fl'>
								<div class='js'>
									<div class='js_tit'>
										<span class='js_bj'>开发者模式</span> <input type='button'
											class='js_button_2 modeltype' value='设置'
											onclick="window.location.href='<c:url value="/wechatCampaign/to_set_advanced.mbay?campaignNumber=${campaignNumber}"/>'" />
									</div>
									<div class='js_con'>
										<span>介绍：</span><span>开发模式是为开发者提供与用户进行消息交互的能力.</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--右边-->
			</div>
			<!--尾部-->
		</div>
	</div>
</body>
</html>