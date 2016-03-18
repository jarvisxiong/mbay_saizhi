<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝工作台</title>
<link href="${actx}/wro/${version}/workbenches.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />

</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div>
					<div class='hdlb'>微信伴侣活动平台</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='lb fr' style='width: 830px'>
						<div class='hd'>
							<div class='r_box'>
								<ul>
									<a href="<c:url value="/wechatCampaign/to_campaign_add.mbay"/>">
										<li>
											<div class='gzt_box'>
												<div class='gb_pic'>
													<img
														src='<c:url value="/images/workimages/create_wechat_event.png"/>' />
												</div>
												<div class='box_wz'>创建活动</div>
											</div>
									</li>
									</a>
									<a href="${ctx}/wechatCampaign/campaign_list.mbay">
										<li>
											<div class='gzt_box'>
												<div class='gb_pic'>
													<img src='<c:url value="/images/workimages/dzhdcl.jpg"/>' />
												</div>
												<div class='box_wz'>活动列表</div>
											</div>
									</li>
									</a>
									<a href='javascript:void(0)'><li>
											<div class='gzt_box'>
												<div class='gb_pic'>
													<img src='<c:url value="/images/workimages/ckhdxg.jpg"/>' />
												</div>
												<div class='box_wz'>活动效果</div>
											</div>
									</li></a>
									<a href='javascript:void(0)'><li>
											<div class='gzt_box'>
												<div class='gb_pic'>
													<img src='<c:url value="/images/workimages/gzcx.jpg"/>' />
												</div>
												<div class='box_wz'>规则查询</div>
											</div>
									</li></a>
								</ul>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>
