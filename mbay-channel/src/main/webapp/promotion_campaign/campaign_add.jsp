<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建促销神器</title>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<link href="${actx}/wro/${version}/promotion_campaign_add.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${actx }/traffic_red/css/create.css" />
<link rel="stylesheet" type="text/css" href="${actx }/traffic_red/css/campaign_table.css?v=1" />
<script type="text/javascript"
	src="<c:url value="/js/my97/WdatePicker.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/js/lib/Validform_v5.3.2_min.js"/>"></script>
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<%-- <script type="text/javascript"
	src="${actx}/wro/${version}/promotion_campaign_add.js"></script> --%>
<script type="text/javascript" src="${actx}/js/wechat_campaign/addevent.js"></script>
<script type="text/javascript" src="${actx}/js/campaign_common/select_strategy.js"></script>
<script type="text/javascript" src="${actx}/js/lib/switch.js"></script>
<style type="text/css">
.product-num{margin-left:-34px;}
.pciuar{margin-left: -46px;}
</style>
</head>
<body>
	<!--head-->
	<div id="con">
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='tjhd'>创建促销神器</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='hd fr'>
						<div class='hd_con'>
							<form id='hd' class="cmp-form" method="post" enctype="multipart/form-data"
								action='<c:url value="/promotionCampaign/campaign_add.mbay"/>'>
								<m:token />
								<div>
									<span><b>*</b>活动名称:</span><input type='text'
										placeholder='请输入活动名称' name="eventname" class='hdmc'
										datatype="hdmc" errormsg="输入格式不正确！" maxlength="40" /> <span
										class="Validform_checktip" style='width: 200px'>请输入汉字，数字或字母！</span>
								</div>
								
								<div id="mbaysendunit"></div>
								<div class='rq'>
									<span><b>*</b>活动日期:</span> <img
										src='<c:url value="/images/workimages/rq.jpg"/>' class='hdrq'
										onclick="WdatePicker({el:'rq_1',minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
									<input datatype='rq' errormsg='请正确填写日期！' name="eventstarttime"
										type='text' id='rq_1'
										onFocus="WdatePicker({minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />-
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq'
										onclick="WdatePicker({el:'rq_2',minDate:'${now}'})" /> <input
										type="text" datatype='rq' errormsg='请正确填写日期！'
										name="eventendtime" id='rq_2'
										onFocus="WdatePicker({minDate:'${now}'})" /> <span
										class="Validform_checktip">输入日期！</span>
								</div>

								<div id="campaign_limit" class='d'>
									<span><b>*</b>活动限制:</span>
									<select name="campaignLimitType" datatype="limitSelect" errormsg="请填写正整数！"
										id='campaignLimit' style="width:106px">
										<option value="DAY" selected="selected">每日</option>
										<option value="WEEK">每周</option>
										<option value="MONTH">每月</option>
										<option value="ACTIVE">活动期间</option>
										<option value="UNLIMITED">不限</option>
									</select>
									<input type='text' class='yjzl onlynum' maxlength="6" 
										name="campaignLimitValue" style="width:230px"/><i>次</i>
									<span class="Validform_checktip" style='margin-left: 3px'>请准确输入限制值！</span>
								</div>
								
								<div id="campaign_limit" class='d'>
									<span><b>*</b>手机号限制:</span>
									<select name="mobileLimitType" datatype="limitSelect" errormsg="请填写正整数!"
										id='mobileLimit' style="width:106px">
										<option value="DAY" selected="selected">每日</option>
										<option value="WEEK">每周</option>
										<option value="MONTH">每月</option>
										<option value="ACTIVE">活动期间</option>
										<option value="UNLIMITED">不限</option>
									</select>
									<input type='text' class='yjzl onlynum' maxlength="6" 
										name="mobileLimitValue" style="width:230px"/><i>次</i>
									<span class="Validform_checktip" style='margin-left: 3px'>请准确输入限制值！</span>
								</div>
								
								<table class="cmp-table" style="margin-left: 40px;">
									<tr class="table-content">
										<td><b class="warn">*</b>产品选择:</td>
										<td>
											<!--table选项卡-start-->
											<ul class="table-content-ul table-ul">
												<li><a href="javascript:void(0)" class="at-list" id="red_product_a">红包产品</a></li>
												<li><a href="javascript:void(0)" id="mb_product_a">MB产品</a></li>
											</ul> 
											<input type="hidden" datatype="products" value="null" /> 
											<span class="Validform_checktip">可以根据需求选择不同的产品</span>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<!-- 流量产品 start -->
											<div class="table-list-content traffics">
												<!--活动内部选项卡 start-->
												<ul class="tab-con-tab-ul table-ul">
													<li><a href="javascript:void(0)" class="tab-ul-font" style="font-style: normal;">移动</a></li>
													<li><a href="javascript:void(0)" style="font-style: normal;">联通</a></li>
													<li><a href="javascript:void(0)" style="font-style: normal;">电信</a></li>
												</ul>
												<!--END 移动活动内部选项卡 -->
	
												<!--移动  start-->
												<div class="table-10list-content">
													<c:forEach items="${MOBILE}" varStatus="status" step="3">
														<div class="weight">
															<c:if test="${not empty MOBILE[status.index] }">
																<p class="weight-list">
																	<input type="checkbox" id="mobile-${status.index }" class="chk_1" /> 
																	<label for="mobile-${status.index }"></label>
																	<span style="width:50px;">${MOBILE[status.index].traffic }MB</span> 
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${MOBILE[status.index].packageId }"
																		value="${MOBILE[status.index].ratio }" maxlength="3" />
																</p>
															</c:if>
															<c:if test="${not empty MOBILE[status.index + 1] }">
																<p class="weight-list">
																	<input type="checkbox" id="mobile-${status.index + 1 }" class="chk_1" /> 
																	<label for="mobile-${status.index + 1 }"></label> 
																	<span style="width:50px;">${MOBILE[status.index + 1].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${MOBILE[status.index + 1].packageId }"
																		value="${MOBILE[status.index + 1].ratio }" maxlength="3" />
																</p>
															</c:if>
															<c:if test="${not empty MOBILE[status.index + 2] }">
																<p class="weight-list">
																	<input type="checkbox" id="mobile-${status.index + 2 }" class="chk_1" /> 
																	<label for="mobile-${status.index + 2 }"></label> 
																	<span style="width:50px;">${MOBILE[status.index + 2].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${MOBILE[status.index + 2].packageId }"
																		value="${MOBILE[status.index + 2].ratio }" maxlength="3" />
																</p>
															</c:if>
														</div>
													</c:forEach>
												</div>
												<!-- 移动 end -->
	
												<!-- 联通 start-->
												<div class="table-10list-content" style="display: none;">
													<c:forEach items="${UNICOM}" varStatus="status" step="3">
														<div class="weight">
															<c:if test="${not empty UNICOM[status.index] }">
																<p class="weight-list">
																	<input type="checkbox" id="unicom-${status.index }" class="chk_1" /> 
																	<label for="unicom-${status.index }"></label>
																	<span style="width:50px;">${UNICOM[status.index].traffic }MB</span> 
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${UNICOM[status.index].packageId }"
																		value="${UNICOM[status.index].ratio}" maxlength="3" />
																</p>
															</c:if>
															<c:if test="${not empty UNICOM[status.index + 1] }">
																<p class="weight-list">
																	<input type="checkbox" id="unicom-${status.index + 1 }" class="chk_1" /> 
																	<label for="unicom-${status.index + 1 }"></label> 
																	<span style="width:50px;">${UNICOM[status.index + 1].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${UNICOM[status.index + 1].packageId }"
																		value="${UNICOM[status.index + 1].ratio}" maxlength="3" />
																</p>
															</c:if>
															<c:if test="${not empty UNICOM[status.index + 2] }">
																<p class="weight-list">
																	<input type="checkbox" id="unicom-${status.index + 2 }" class="chk_1" /> 
																	<label for="unicom-${status.index + 2 }"></label> 
																	<span style="width:50px;">${UNICOM[status.index + 2].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${UNICOM[status.index + 2].packageId }"
																		value="${UNICOM[status.index + 2].ratio}" maxlength="3" />
																</p>
															</c:if>
														</div>
													</c:forEach>
												</div>
												<!-- 联通 end -->
	
												<!-- 电信 start -->
												<div class="table-10list-content" style="display: none;">
													<c:forEach items="${TELECOM}" varStatus="status" step="3">
														<div class="weight">
															<c:if test="${not empty TELECOM[status.index] }">
																<p class="weight-list">
																	<input type="checkbox" id="telecom-${status.index }" class="chk_1" /> 
																	<label for="telecom-${status.index }"></label> 
																	<span style="width:50px;">${TELECOM[status.index].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${TELECOM[status.index].packageId }"
																		value="${TELECOM[status.index].ratio }" maxlength="3" />
																</p>
															</c:if>
															<c:if test="${not empty TELECOM[status.index + 1] }">
																<p class="weight-list">
																	<input type="checkbox" id="telecom-${status.index + 1 }" class="chk_1" /> 
																	<label for="telecom-${status.index + 1 }"></label> 
																	<span style="width:50px;">${TELECOM[status.index + 1].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${TELECOM[status.index + 1].packageId }"
																		value="${TELECOM[status.index + 1].ratio }" maxlength="3" />
																</p>
															</c:if>
															<c:if test="${not empty TELECOM[status.index + 2] }">
																<p class="weight-list">
																	<input type="checkbox" id="telecom-${status.index + 2 }" class="chk_1" /> 
																	<label for="telecom-${status.index + 2 }"></label> 
																	<span style="width:50px;">${TELECOM[status.index + 2].traffic }MB</span>
																	<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																		packageId="${TELECOM[status.index + 2].packageId }"
																		value="${TELECOM[status.index + 2].ratio }" maxlength="3" />
																</p>
															</c:if>
														</div>
													</c:forEach>
												</div>
												<!-- 电信 end -->
												<ul class="product-num">
													<li>
														<span>预设总量(美贝):</span>
														<input type='text' name="TRAFFIC_PACKAGE_poolsize" class='txt onlynum' 
															datatype="redsel|redps" maxlength="15" /> 
														<span class="Validform_checktip">请输入正整数</span>
													</li>
												</ul>
												<ul class="product-num">
													<li>
														<span>单日上限(美贝):</span>
														<input type="text" class="txt onlynum" name="TRAFFIC_PACKAGE_dailylimit" 
															datatype="redsel|redPackHighline" maxlength="15" /> 
														<span class="Validform_checktip">不填表示不限</span>
													</li>
												</ul>
												<ul class="product-num">
													<li>
														<span>告警阀值(美贝):</span>
													 	<input type="text" class="txt onlynum" name="TRAFFIC_PACKAGE_threshold" 
															datatype="redsel|redPackHighline" maxlength="15" /> 
														<span class="Validform_checktip">不填表示不限</span>
													</li>
												</ul>
											</div> <!-- 流量产品 END --> <!-- 美贝产品  start-->
											<div class="table-list-content table-list-content2 mbays"
												style="display: none;">
												<c:forEach items="${MBAYLIST}" varStatus="status" step="3">
													<div class="weight">
														<c:if test="${not empty MBAYLIST[status.index] }">
															<p class="weight-list weight-list2">
																<input type="checkbox" id="mbay-${status.index }" class="chk_1" /> 
																<label for="mbay-${status.index }"></label>
																<span style="width:50px;">${MBAYLIST[status.index].mbay }MB</span> 
																<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																	mbayId="${MBAYLIST[status.index].mbayId }"
																	value="${MBAYLIST[status.index].ratio }" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty MBAYLIST[status.index + 1] }">
															<p class="weight-list weight-list2">
																<input type="checkbox" id="mbay-${status.index + 1 }" class="chk_1" /> 
																<label for="mbay-${status.index + 1 }"></label> 
																<span style="width:50px;">${MBAYLIST[status.index + 1].mbay }MB</span>
																<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																	mbayId="${MBAYLIST[status.index + 1].mbayId }"
																	value="${MBAYLIST[status.index + 1].ratio }" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty MBAYLIST[status.index + 2] }">
															<p class="weight-list weight-list2">
																<input type="checkbox" id="mbay-${status.index + 2 }" class="chk_1" /> 
																<label for="mbay-${status.index + 2 }"></label> 
																<span style="width:50px;">${MBAYLIST[status.index + 2].mbay }MB</span>
																<input class="onlynum" type="text" placeholder="权重" style="display:none;"
																	mbayId="${MBAYLIST[status.index + 2].mbayId }"
																	value="${MBAYLIST[status.index + 2].ratio }" maxlength="3" />
															</p>
														</c:if>
													</div>
												</c:forEach>
												<ul class="product-num">
													<li>
														<span>预设总量(MB):</span>
													 	<input type='text' name="MBAY_PACKAGE_poolsize" class='txt onlynum mbay'
															datatype="mbaysel|mbayps" maxlength="15" /> 
														<span class="Validform_checktip">请输入正整数</span>
													</li>
												</ul>
												<ul class="product-num">
													<li>
														<span>单日上限(MB):</span>
													 	<input type="text" name="MBAY_PACKAGE_dailylimit" class="txt onlynum mbay"
															datatype="mbaysel|mbayTrafficHighline" maxlength="15" />
														<span class="Validform_checktip">不填表示不限</span>
													</li>
												</ul>
												<ul class="product-num">
													<li>
														<span>告警阀值(MB):</span>
														<input type="text" name="MBAY_PACKAGE_threshold" class="txt onlynum mbay" 
															datatype="mbaysel|mbayTrafficHighline" maxlength="15" /> 
														<span class="Validform_checktip">不填表示不限</span>
													</li>
												</ul>
											</div> 
											<!-- 美贝产品 end --> 
											<input type="hidden" name="traffic_products" id="traffic_products" /> 
											<input type="hidden" name="mbay_products" id="mbay_products" />
										</td>
									</tr>
									<tr class="threshold-mobile">
										<td colspan="2">
											<div class='yxq' style="text-align:left;">
												<span class="pciuar"><b>*</b>告罄提醒号码:</span><input type='text' class='yxqx onlynum' datatype='mobile' name="thresholdMobile" 
													errormsg="请输入正确的手机号码！" maxlength="11" />
												<i class='tip_i'>?
													<div class="tooltip" style="display: none;">${sms_threshold }</div>
												</i>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div class='yxq' style="text-align:left;">
												<span class="pciuar"><b>*</b>中奖概率(%):</span><input type='text' class='yxqx onlynum' name="trafficRate" datatype='rate' id="trafficRate" errormsg="请输入整数或小数(0-100)！" maxlength="9" />
												<i class='tip_i'>?
													<div class="tooltip" style="display: none;">概率是指对红包产品而言(0->不会是红包产品,100->全部都是红包产品)</div>
												</i>
												<span class="Validform_checktip">请输入整数或小数(0-100)</span>
											</div>
										</td>
									</tr>
								</table>
								
								<div id="validityday_div" class='yxq'>
									<span><b>*</b>兑换码有效期:</span><input type='text'
										class='yxqx onlynum' name="validityday" value="" datatype='sz'
										errormsg="请填写准确！" /><i>天</i> 
										<i class='tip_i'>?
											<div class="tooltip" style="display: none;">兑换码截至时间=领取兑换码时间+兑换码有效期(只对应系统生成的兑换码)</div>
										</i>
										<span class="Validform_checktip">请准确填写天数！</span>
								</div>
								<div class='yxq'>
									<span><b>*</b>兑换码查询说明:</span><input type='text'
										class='yxqx' name="queryTitle" value="" datatype='s1-20' maxlength="20"
										errormsg="请填写准确！" /> <span class="Validform_checktip">请填写说明标题(最多20个字符)！</span>
								</div>
								<div class='yxq'>
									<span><b>*</b>兑换码查询详情:</span><textarea class='yxqx' style="height:100px;"
										name="queryContent" datatype='s1-200' maxlength="200"
										errormsg="请填写准确！" ></textarea> <span class="Validform_checktip">请填写说明详情(最多200个字符)！</span>
								</div>
								<div class='yxq'>
								<c:choose>
									<c:when test="${relationList!=null and relationList.size()>0}">
										<span><b>*</b>商城:</span>
										<select style="width: 421px;" name="mall.id" datatype="*">
											<option value="0">默认商城</option>
											<c:forEach items="${relationList}" var="relation">
												<option value="${relation.mall.id}">${relation.mall.name}</option>
											</c:forEach>
										</select> 
										<span class="Validform_checktip">选择MB兑换商城</span>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="mall.id" value="0"/>
									</c:otherwise>
								</c:choose>
								</div>
								<div id="traffic_only">
									<!--是否短信发送通知  -->
									<div style="margin: 10px 0px;">
										<span class='span_len'>短信提醒:</span> <input type='checkbox'
											name="sendsms" checked="checked" id='tx' /> <span
											class='mbtx'> <label> 查看<a>短信模板</a></label>
											<div class='mb_tip' style='display: none; margin-bottom: 0px'>${sms_tempate}<em></em>
											</div>
										</span>
									</div>
								</div>
								
								<div id="mbay_only" style="margin: 10px 0px;">
									<span class='span_len'>超出发放:</span>
									<!--开关设置-->
									<div class="slider-box on">
										<div class="slider"></div>
										<span class="m" style="width:auto;">是</span> <span class="w" style="width:auto;">否</span> <input
											type="checkbox" name="continuable" checked="true"/>
									</div>
									<!--提示-->
									<i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											当没有导入兑换码时，是否可以系统自动发放兑换码。<em></em>
										</div>
									</i>
								</div>
								
								<div style="margin: 10px 0px;">
									<span class='span_len'>开启核销码:</span>
									<!--开关设置-->
									<div class="slider-box on">
										<div class="slider"></div>
										<span class="m" style="width:auto;">是</span> <span class="w" style="width:auto;">否</span> <input
											type="checkbox" name="verificate" checked="true"/>
									</div>
									<!--提示-->
									<i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											是否开启核销码,用于线下操作。<em></em>
										</div>
									</i>
								</div>
								
								<div style="margin: 10px 0px;">
									<span class='span_len'>开启分享:</span>
									<!--开关设置-->
									<div class="slider-box off">
										<div class="slider"></div>
										<span class="m" style="width:auto;">是</span> <span class="w" style="width:auto;">否</span> <input
											type="checkbox" name="share" />
									</div>
									<!--提示-->
									<i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											开启分享加油功能，用户分享后可获得额外的摇一摇机会！<em></em>
										</div>
									</i>
								</div>
								
								<!-- 选择模式:再玩一次,再领一次 -->
								<div id="chooseModel" style="display:none;">
									<span><b>*</b>选择模式:</span>
									<input id="playAgain" type="radio" name="model" datatype="*" value="PLAY" checked="checked"/><span id="playAgainSpan" style="width:60px;margin-right:20px;">再玩一次</span>
									<input id="getAgain" type="radio" name="model" datatype="*" value="GET"/><span id="getAgainSpan" style="width:60px;">再领一次</span>
									<span class="Validform_checktip">请至少选择一种模式！</span>
								</div>
								
								<!-- 如果选择再玩一次需填写此项 -->
								<div id="sendMBDiv" style="display:none;">
									<span><b>*</b>赠送:</span>
									<input id="sendMB" type="text" name="sendMB" datatype="n1-2" value="" style="width: 50px;margin-right: 10px;"/>MB
									<span class="Validform_checktip">请填写赠送MB！</span>
								</div>
								
								<!-- 分享 -->
								<div id="shareInfo" style="display:none;">
									<div class='yxq'>
										<span><b>*</b>分享链接:</span><input type='text'
											class='yxqx' name="shareLink" value="http://" datatype='url|shinfo' maxlength="255"
											errormsg="链接格式不合法！" /> <span class="Validform_checktip">请填写分享链接(最多255个字符)！</span>
									</div>
									
									<div class='yxq'>
										<span><b>*</b>分享标题:</span><input type='text'
											class='yxqx' name="shareTitle" value="" datatype='*|shinfo' maxlength="50"
											errormsg="请填写准确！" /> <span class="Validform_checktip">请填写分享标题(最多50个字符)！</span>
									</div>
									
									<div class='yxq'>
										<span><b>*</b>分享内容:</span><input type='text'
											class='yxqx' name="content" value="" datatype='*|shinfo' maxlength="255"
											errormsg="请填写准确！" /> <span class="Validform_checktip">请填写分享内容(最多255个字符)！</span>
									</div>
									
									<div class='yxq'>
										<span><b>*</b>分享有效次数:</span><input type='text'
											class='yxqx' name="shareTimes" value="" datatype='n1-3|shinfo' maxlength="3"
											errormsg="请输入正整数(0-999)！" /> <span class="Validform_checktip">请填写分享有效次数(正整数(0-999))！</span>
									</div>
									
									<div class='yxq'>
										<span><b>*</b>分享图标:</span><input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('shareImage')" /> 
										<input type="text" datatype="file|shinfo" id="shareInfo-shareImgFile" class="advanced-finfo" readonly="readonly"
											errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
										<input type="file" name="shareImage" style="display:none;" textId="shareInfo-shareImgFile"/> 
									</div>
								</div>
								
								<!--编辑模式按钮-->
								<div id="notmsg"
									style="text-align: center; margin-top: 15px; color: #FDAB1B; height: 20px"></div>
								<div class='bj_an'>
									<input type='button' id="subbtn" value='提交'
										class='bjms_1 btn_confirm' />
								</div>
							</form>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
</body>
</html>