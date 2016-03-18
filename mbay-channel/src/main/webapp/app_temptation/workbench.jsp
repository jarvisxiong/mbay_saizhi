<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝工作台</title>
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
<link	href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript"	src="${actx}/js/widget/jquery.Sonline.js"></script>
<script type="text/javascript" src="${actx}/js/tipswindown.js"></script>
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/inside_right_part/common_js.js"></script>
<style type="text/css">
.r_box {
	font-family: 'Microsoft YaHei';
}

.r_box_tit {
	width: 690px;
	height: 26px;
	text-align: center;
	background: url(../images/bar.jpg) no-repeat center;
	color: #35618F;
	font-size: 18px;
}

.r_box ul {
	list-style: none;
	margin-top: 30px;
	text-align: center;
}

.r_box ul a {
	display: inline-block;
	width: 140px;
	height: 140px;
	margin-right: 30px;
	vertical-align: bottom;
	border: 1px dotted #fff
}

.r_box li {
	width: 140px;
	height: 140px
}

.gzt_box {
	height: 120px;
	padding-top: 20px
}

.gb_pic {
	width: 86px;
	height: 85px;
	padding-left: 27px;
	padding-right: 27px;
}

.box_wz {
	color: #35618F;
	font-size: 16px;
	line-height: 35px;
	text-align: center;
}

.lb {
	width: 830px
}
</style>
</head>
<body>
<div class='con'>
	<!--身体-->
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div>
				<div class='hdlb'>app诱惑活动平台</div>
				<%@ include file="/common/leftcon.jsp"%>
				<div class='lb fr'>
					<div class='hd'>

						<div class='r_box'>
							<ul>
								<a href="<c:url value="/app_temptation/to_campaign_add.mbay"/>">
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
								<a href="${ctx}/app_temptation/campaign_list.mbay">
									<li>
										<div class='gzt_box'>
											<div class='gb_pic'>
												<img src='<c:url value="/images/workimages/dzhdcl.jpg"/>' />
											</div>
											<div class='box_wz'>活动列表</div>
										</div>
								</li>
								</a>
								<a href='${ctx}/app_temptation/result_list.mbay'><li>
										<div class='gzt_box'>
											<div class='gb_pic'>
												<img src='<c:url value="/images/workimages/ckhdxg.jpg"/>' />
											</div>
											<div class='box_wz'>结果查询</div>
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
