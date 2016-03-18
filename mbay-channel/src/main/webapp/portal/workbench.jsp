<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝工作台</title>
<script type="text/javascript" src="${actx}/wro/${version}/workbench.js"></script>
<link href="${actx}/wro/${version}/workbench.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class='con'>
		<div class='hr_6'></div>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width'>
				<!--左边-->
				<div class='left_con fl'>
					<div class='r_box'>
						<div class='r_box_tit'>基础功能</div>
						<ul>
							<a href="<c:url value="/account/transfer/payment/fill.mbay"/>" class="cert_intercept">
								<li>
									<div class='gzt_box'>
										<div class='gb_pic'>
											<img src='<c:url value="/images/workimages/zz.jpg"/>' />
										</div>
										<div class='box_wz'>美贝转账</div>
									</div>
								</li>
							</a>
							<a href='<c:url value="/traffic/purchase.mbay"/>' class="cert_intercept">
								<li>
									<div class='gzt_box'>
										<div class='gb_pic'>
											<img src='<c:url value="/images/workimages/sjchongz.jpg"/>' />
										</div>
										<div class='box_wz'>流量柜台</div>
									</div>
								</li>
							</a>
							<a href='<c:url value="/account/depositPreprocess.mbay"/>' class="cert_intercept">
								<li>
									<div class='gzt_box'>
										<div class='gb_pic'>
											<img src='<c:url value="/images/workimages/czmb.jpg"/>' />
										</div>
										<div class='box_wz'>充值美贝</div>
									</div>
								</li>
							</a>
							<a href="<c:url value="/mall/exchangeItem/list.mbay"/>" class="cert_intercept" id='sp_1'>
								<li>
									<div class='gzt_box'>
										<div class='gb_pic'>
											<img src='<c:url value="/images/workimages/scgl.png"/>' />
										</div>
										<div class='box_wz'>商城管理</div>
									</div>
								</li>
							</a>
						</ul>
					</div>
					
					<!--营销功能-->
					<div class='r_box_1 mr_30'>
						<div class='r_box_tit'>营销功能</div>
						<ul class="clearfix">
						<!-- 
						 
							<div class='fl b_sec'>
								<a href='<c:url value="/wechatCampaign/workbench.mbay"/>' class="cert_intercept r_box_1_a">
									<li>
										<div class='img_sec fl'>
											<img src='<c:url value="/images/workimages/wxbanlv.jpg"/>' />
										</div>
										<div class='txt_sec fl'>
											<h3>微信伴侣</h3>
											<p>微信加粉，各类会员活动活跃度飙到爆!</p>
										</div>
									</li>
								</a>
								<!--  <div class='syxq'><a class='fr' href='#'>使用详情</a></div>
							</div>-->
							
							<div class='fl b_sec'>
								<a href='${ctx}/traffic_red/' class='r_box_1_a'>
									<li>
										<div class='img_sec fl'>
											<img src='<c:url value="/images/trafficred/trafficred_logo.png"/>' />
										</div>
										<div class='txt_sec fl'>
											<h3>流量红包</h3>
											<p>无限吸粉，根本停不下来!</p>
										</div>
									</li>
								</a>
							</div>

							<div class='fr b_sec'>
								<a style='position: relative' class="cert_intercept r_box_1_a" href='<c:url value="/promotionCampaign/workbench.mbay"/>'>
									<span style='position: absolute; bottom: 2px; right: 5px; COLOR: #EC912D; font-weight: bold'>BETA</span>
									<li>
										<div class='img_sec fl'>
											<img src='<c:url value="/images/workimages/cxshenq.jpg"/>' />
										</div>
										<div class='txt_sec fl'>
											<h3>促销神器</h3>
											<p>兑换码模式，线上线下无缝切换，尽在掌握中!</p>
										</div>
									</li>
								</a>
								<!-- <div class='syxq'><a class='fr' href='#'>使用详情</a></div>-->
							</div>

							

							<div class='fl b_sec'>
								<a style='position: relative' class="cert_intercept r_box_1_a" href='<c:url value="/customerserver/servermanager.mbay"/>'>
									<span style='position: absolute; bottom: 2px; right: 5px; COLOR: #EC912D; font-weight: bold'>BETA</span>
									<li>
										<div class='img_sec fl'>
											<img src='<c:url value="/images/workimages/khguanh.jpg"/>' />
										</div>
										<div class='txt_sec fl'>
											<h3>客户关怀</h3>
											<p>让客户时刻感受到温馨关怀，感受不一样的流量世界!</p>
										</div>
									</li>
								</a>
							</div>

							<div class='fr b_sec'>
								<a style='position: relative' href='<c:url value="/app_temptation/workbench.mbay"/>' class='r_box_1_a'> 
								<span style='position: absolute; bottom: 2px; right: 5px; COLOR: #EC912D; font-weight: bold'>BETA</span>
									<li>
										<div class='img_sec fl'>
											<img src='<c:url value="/images/workimages/jfgongj.jpg"/>' />
										</div>
										<div class='txt_sec fl'>
											<h3>APP诱惑</h3>
											<p>当你下载，惊艳就在眼前!</p>
										</div>
									</li>
								</a>
							</div>
						</ul>
					</div>
				</div>

				<!--右边-->
				<jsp:include page="/common/right.jsp"></jsp:include>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>
