<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建流量红包活动</title>
<meta name="description" content="">
<t:assets />
<link rel="stylesheet" type="text/css" href="${actx }/css/switch.css" />
<link rel="stylesheet" type="text/css" href="${actx }/traffic_red/css/campaign_table.css?v=1" />
<link rel="stylesheet" type="text/css" href="${actx }/traffic_red/css/create.css" />
<script type="text/javascript" src="${actx }/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx }/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx }/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx }/js/o2o_campaign/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="${actx }/js/lib/switch.js"></script>
<script type="text/javascript" src="${actx }/traffic_red/js/create.js?v=2"></script>
</head>
<body>
<!--head-->
<div id="con">
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div class='left_con'>
				<div class='tjhd'>创建流量红包活动</div>
				<%@ include file="/common/leftcon.jsp"%>
				<div class='hd fr'>
					<c:if test="${not empty message }">
						<div class="qs_alert">
							<p>${message}</p>
						</div>
					</c:if>
					<div class='hd_con'>
						<form id='hd' class="cmp-form" action="<c:url value="/traffic_red/create.mbay" />"
							method="post" enctype="multipart/form-data">
							<m:token />
							<table class="cmp-table">
								<tr style="display: none;">
									<td width="140px"><b class="warn">*</b>场景设置:</td>
									<td>
										<input type="radio" name="firstShark" value="0" id="cell" datatype="*" checked="checked" /> 
										<label for="cell">先输入手机号</label> 
										<!-- <input type="radio" name="firstShark" value="1" id="shark" style="margin-left:20px;" />
										<label for="shark">先摇一摇</label> -->
									</td>
								</tr>
								<tr>
									<td width="130px"><b class="warn">*</b>活动名称:</td>
									<td>
										<input type='text' name="name" class='txt' datatype="hdmc" errormsg="输入格式不正确！" maxlength="40" /> 
										<span class="Validform_checktip">请输入汉字，数字或字母</span>
									</td>
								</tr>
								<tr>
									<td><b class="warn">*</b>活动日期:</td>
									<td>
										<img src='<c:url value="/images/workimages/rq.jpg"/>' class='date-img'
											onclick="WdatePicker({el:'rq_1',minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
										<input datatype='rq' errormsg='请正确填写日期！' name="start_Time" type='text' id='rq_1' class="date-txt"
											onFocus="WdatePicker({minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
										<label>-</label> 
										<img src="<c:url value='/images/workimages/rq.jpg'/>" class='date-img'
											onclick="WdatePicker({el:'rq_2',minDate:'${now}'})" /> 
										<input type="text" datatype='rq' errormsg='请正确填写日期！' name="end_Time" class="date-txt" id='rq_2'
											onFocus="WdatePicker({minDate:'${now}'})" /> 
										<span class="Validform_checktip">设置活动开始和结束时间</span>
									</td>
								</tr>
								<tr>
									<td class="top-td" style="padding-top: 15px;">活动时间段:</td>
									<td>
										<c:choose>
											<c:when test="${not empty h0 }">
												<c:forEach items="${h0 }" varStatus="status">
													<script type="text/javascript">
														writeTime('${h0[status.index] }',
															'${m0[status.index]}',
															'${h1[status.index]}',
															'${m1[status.index]}');
													</script>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<script type="text/javascript">
													writeTime();
												</script>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td class="top-td" style="padding-top: 15px;"><b class="warn">*</b>参与限制(次):</td>
									<td>
										<input type="text" class="times onlynum" name="times" maxlength="10" /> 
										<select style="width: 105px;" id="times-sel" name="type" datatype="cycleType" errormsg="请输入正整数！">
											<option value="DAY">每天</option>
											<option value="WEEK">每周</option>
											<option value="MONTH">每月</option>
											<option value="TOTAL">永久</option>
											<option value="NO_LIMITED">不限</option>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="line-bj"></div>
									</td>
								</tr>
								<tr class="table-content">
									<td><b class="warn">*</b>产品选择:</td>
									<td>
										<!--table选项卡-start-->
										<ul class="table-content-ul table-ul">
											<li><a href="javascript:void(0)" class="at-list">红包产品</a></li>
											<li><a href="javascript:void(0)">MB产品</a></li>
										</ul> 
										<input type="hidden" datatype="products" value="null" /> 
										<span class="Validform_checktip">已勾选的产品权重默认为1</span>
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
																<span>${MOBILE[status.index].traffic }MB</span> 
																<input class="onlynum" type="text" placeholder="权重"
																	packageId="${MOBILE[status.index].packageId }"
																	value="${MOBILE[status.index].ratio }" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty MOBILE[status.index + 1] }">
															<p class="weight-list">
																<input type="checkbox" id="mobile-${status.index + 1 }" class="chk_1" /> 
																<label for="mobile-${status.index + 1 }"></label> 
																<span>${MOBILE[status.index + 1].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
																	packageId="${MOBILE[status.index + 1].packageId }"
																	value="${MOBILE[status.index + 1].ratio }" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty MOBILE[status.index + 2] }">
															<p class="weight-list">
																<input type="checkbox" id="mobile-${status.index + 2 }" class="chk_1" /> 
																<label for="mobile-${status.index + 2 }"></label> 
																<span>${MOBILE[status.index + 2].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
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
																<span>${UNICOM[status.index].traffic }MB</span> 
																<input class="onlynum" type="text" placeholder="权重"
																	packageId="${UNICOM[status.index].packageId }"
																	value="${UNICOM[status.index].ratio}" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty UNICOM[status.index + 1] }">
															<p class="weight-list">
																<input type="checkbox" id="unicom-${status.index + 1 }" class="chk_1" /> 
																<label for="unicom-${status.index + 1 }"></label> 
																<span>${UNICOM[status.index + 1].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
																	packageId="${UNICOM[status.index + 1].packageId }"
																	value="${UNICOM[status.index + 1].ratio}" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty UNICOM[status.index + 2] }">
															<p class="weight-list">
																<input type="checkbox" id="unicom-${status.index + 2 }" class="chk_1" /> 
																<label for="unicom-${status.index + 2 }"></label> 
																<span>${UNICOM[status.index + 2].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
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
																<span>${TELECOM[status.index].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
																	packageId="${TELECOM[status.index].packageId }"
																	value="${TELECOM[status.index].ratio }" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty TELECOM[status.index + 1] }">
															<p class="weight-list">
																<input type="checkbox" id="telecom-${status.index + 1 }" class="chk_1" /> 
																<label for="telecom-${status.index + 1 }"></label> 
																<span>${TELECOM[status.index + 1].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
																	packageId="${TELECOM[status.index + 1].packageId }"
																	value="${TELECOM[status.index + 1].ratio }" maxlength="3" />
															</p>
														</c:if>
														<c:if test="${not empty TELECOM[status.index + 2] }">
															<p class="weight-list">
																<input type="checkbox" id="telecom-${status.index + 2 }" class="chk_1" /> 
																<label for="telecom-${status.index + 2 }"></label> 
																<span>${TELECOM[status.index + 2].traffic }MB</span>
																<input class="onlynum" type="text" placeholder="权重"
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
															<span>${MBAYLIST[status.index].mbay }MB</span> 
															<input class="onlynum" type="text" placeholder="权重"
																mbayId="${MBAYLIST[status.index].mbayId }"
																value="${MBAYLIST[status.index].ratio }" maxlength="3" />
														</p>
													</c:if>
													<c:if test="${not empty MBAYLIST[status.index + 1] }">
														<p class="weight-list weight-list2">
															<input type="checkbox" id="mbay-${status.index + 1 }" class="chk_1" /> 
															<label for="mbay-${status.index + 1 }"></label> 
															<span>${MBAYLIST[status.index + 1].mbay }MB</span>
															<input class="onlynum" type="text" placeholder="权重"
																mbayId="${MBAYLIST[status.index + 1].mbayId }"
																value="${MBAYLIST[status.index + 1].ratio }" maxlength="3" />
														</p>
													</c:if>
													<c:if test="${not empty MBAYLIST[status.index + 2] }">
														<p class="weight-list weight-list2">
															<input type="checkbox" id="mbay-${status.index + 2 }" class="chk_1" /> 
															<label for="mbay-${status.index + 2 }"></label> 
															<span>${MBAYLIST[status.index + 2].mbay }MB</span>
															<input class="onlynum" type="text" placeholder="权重"
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
														datatype="redsel|redPackHighline" maxlength="15" /> 
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
									<td><b class="warn">*</b>告罄提醒号码:</td>
									<td>
										<input type='text' class='txt' datatype='mobile' name="thresholdMobile" 
											errormsg="请输入正确的手机号码！" maxlength="11" />
										<i class='tip_i'>?
											<div class="tooltip" style="display: none;">${sms_threshold }</div>
										</i>
									</td>
								</tr>
								<tr>
									<td><b class="warn">*</b>中奖概率(%):</td>
									<td>
										<input type='text' class='txt' datatype='rate' id="trafficRate" errormsg="请输入整数或小数(0-100)！" maxlength="9" />
										<input type="hidden" name="trafficRate" /> 
										<span class="Validform_checktip">请输入整数或小数(0-100)</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="line-bj"></div>
									</td>
								</tr>
								<tr>
									<td><b class="warn">*</b>送人链接:</td>
									<td>
										<input type="text" name="mbayGiftConfig.participationLink" class="txt" autocomplete="off" datatype="url" 
											value="http://" errormsg="链接格式不合法！" maxlength="255" /> 
										<span class="Validform_checktip">送人功能参与活动链接</span>
									</td>
								</tr>
								<tr>
									<td><b class="warn">*</b>送人标题:</td>
									<td>
										<input type="text" name="mbayGiftConfig.shareTitle" class="txt" autocomplete="off" datatype="*" maxlength="255" />
										<span class="Validform_checktip">送人功能分享标题</span>
									</td>
								</tr>
								<tr>
									<td><b class="warn">*</b>送人内容:</td>
									<td>
										<input type="text" name="mbayGiftConfig.shareContent" class="txt" autocomplete="off" datatype="*" maxlength="255" />
										<span class="Validform_checktip">送人功能分享内容</span>
									</td>
								</tr>
								<tr>
									<td><b class="warn">*</b>送人图标:</td>
									<td>
										<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('mbayGiftConfig.giftShareImgFile')" /> 
										<input type="text" datatype="file" id="mbayGiftConfig-giftShareImgFile" class="advanced-finfo" readonly="readonly"
											errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
										<span class="Validform_checktip">送人功能分享图标</span>
									</td>
								</tr>
								<tr>
									<td>送人背景图:</td>
									<td>
										<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('mbayGiftConfig.giftBgImgFile')" /> 
										<input type="text" datatype="empty|file" id="mbayGiftConfig-giftBgImgFile" class="advanced-finfo" readonly="readonly"
											errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
										<span class="Validform_checktip">送人背景图片，未设置使用默认图片</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="line-bj"></div>
									</td>
								</tr>
								<c:choose>
									<c:when test="${relationList!=null and relationList.size()>0}">
										<tr>
											<td class="top-td" style="padding-top: 15px;">商城:</td>
											<td>
												<select style="width: 421px;" name="mall.id" datatype="*">
													<option value="0">默认商城</option>
													<c:forEach items="${relationList}" var="relation">
														<option value="${relation.mall.id}">${relation.mall.name}</option>
													</c:forEach>
												</select> 
												<span class="Validform_checktip">选择MB兑换商城</span>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="mall.id" value="0"/>
									</c:otherwise>
								</c:choose>
								<tr>
									<td>模板配置:</td>
									<td><a href="javascript:void(0)" onclick="advancedSettings()">点击设置分享信息</a></td>
								</tr>
								<tr>
									<td>短信提醒:</td>
									<td>
										<span class='mbtx'>
											<div class="slider-box on">
												<div class="slider"></div>
												<span class="m" style="margin-left: 16px;">是</span> 
												<span class="w" style="color: #118EEF; margin-right: 17px;">否</span>
												<input type="checkbox" id='tx' checked="checked" /> 
												<input type="hidden" name="sendsms" />
											</div> 
											<i class='tip_i'>?
												<div class="tooltip" style="display: none;">${sms_tempate}</div>
											</i>
										</span>
									</td>
								</tr>
								<tr>
									<td>开启分享:</td>
									<td>
										<span class='mbtx'>
											<div class="slider-box off">
												<div class="slider"></div>
												<span class="m" style="margin-left: 16px;">是</span> 
												<span class="w" style="color: #118EEF; margin-right: 17px;">否</span>
												<input type="checkbox" id='ses' /> 
												<input type="hidden" name="shareInfo.enableState" />
											</div> 
											<i class='tip_i'>?
												<div class="tooltip" style="display: none;">开启分享加油功能，用户分享后可获得额外的摇一摇机会！</div>
											</i>
										</span>
									</td>
								</tr>
								<tr class="hide shinfo">
									<td><b class="warn">*</b>分享链接:</td>
									<td>
										<input type="text" name="shareInfo.shareLink" class="txt" autocomplete="off" datatype="url|shinfo"
											value="http://" errormsg="链接格式不合法！" maxlength="255" />
										</td>
								</tr>
								<tr class="hide shinfo">
									<td><b class="warn">*</b>分享标题:</td>
									<td>
										<input type="text" name="shareInfo.shareTitle" class="txt" autocomplete="off" datatype="*|shinfo"
											maxlength="255" />
									</td>
								</tr>
								<tr class="hide shinfo">
									<td><b class="warn">*</b>分享内容:</td>
									<td>
										<input type="text" name="shareInfo.content" class="txt" autocomplete="off" datatype="*|shinfo"
											maxlength="255" />
									</td>
								</tr>
								<tr class="hide shinfo">
									<td><b class="warn">*</b>分享有效次数:</td>
									<td>
										<input type="text" name="shareInfo.shareTimes" class="txt" autocomplete="off" maxlength="3"
											datatype="n1-3|shinfo" errormsg="请输入正整数(0-999)！" />
									</td>
								</tr>
								<tr class="hide shinfo">
									<td><b class="warn">*</b>分享图标:</td>
									<td>
										<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('shareInfo.shareImgFile')" /> 
										<input type="text" datatype="file|shinfo" id="shareInfo-shareImgFile" class="advanced-finfo" readonly="readonly"
											errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
									</td>
								</tr>
								<tr>
									<td colspan="2" class="cent-td" style="padding-top: 20px;">
										<input type="submit" id="subbtn" value='提交' class='bjms' />
									</td>
								</tr>
							</table>
							
							<input type="hidden" name="template.logoCycleLink" value="http://" /> 
							<input type="hidden" name="template.sharkResultLink" value="http://" /> 
							<input type="hidden" name="template.adLink1" value="http://" /> 
							<input type="hidden" name="template.adLink2" value="http://" /> 
							<input type="file" name="template.adImg1File" class="hide" /> 
							<input type="file" name="template.adImg2File" class="hide" /> 
							<!-- <input type="file" name="template.shakeIndexImgFile" class="hide" /> 
							<input type="file" name="template.shakeUIImgFile" class="hide" />  -->
							<input type="file" name="shareInfo.shareImgFile" class="hide" /> 
							<input type="file" name="mbayGiftConfig.giftShareImgFile" class="hide" />
							<input type="file" name="mbayGiftConfig.giftBgImgFile" class="hide" /> 
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="hide" id="advanced-form-pop">
	<form id="advanced-form" class="cmp-form">
		<table class="cmp-table">
			<tr>
				<td class="top-td" width="120px">logo悬浮链接:</td>
				<td>
					<input type="text" id="logoCycleLink" class="txt advanced-txt" autocomplete="off"
						datatype="nullUrl|url" ignore="ignore" value="http://"  errormsg="链接格式不合法！" maxlength="255" />
				</td>
			</tr>
			<tr>
				<td class="top-td">摇一摇结果链接:</td>
				<td>
					<input type="text" id="sharkResultLink" class="txt advanced-txt" autocomplete="off"
						datatype="nullUrl|url" ignore="ignore" value="http://" errormsg="链接格式不合法！" maxlength="255" />
				</td>
			</tr>
			<tr>
				<td class="top-td">广告链接1:</td>
				<td>
					<input type="text" id="adLink1" class="txt advanced-txt" autocomplete="off"
						datatype="nullUrl|url" ignore="ignore" value="http://" errormsg="链接格式不合法！" maxlength="255" />
				</td>
			</tr>
			<tr>
				<td class="top-td">广告链接2:</td>
				<td>
					<input type="text" id="adLink2" class="txt advanced-txt" autocomplete="off\"
						datatype="nullUrl|url" ignore="ignore" value="http://" errormsg="链接格式不合法！" maxlength="255" />
				</td>
			</tr>
			<tr>
				<td class="top-td">广告图片1:</td>
				<td>
					<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('template.adImg1File')" />
					<input type="text" datatype="file" id="template-adImg1File" class="advanced-finfo" readonly="readonly"
						ignore="ignore" errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
				</td>
			</tr>
			<tr>
				<td class="top-td">广告图片2:</td>
				<td>
					<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('template.adImg2File')" />
					<input type="text" datatype="file" id="template-adImg2File" class="advanced-finfo" readonly="readonly"
						ignore="ignore" errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
				</td>
			</tr>
			<!-- <tr>
				<td class="top-td">摇一摇首页图:</td>
				<td>
					<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('template.shakeIndexImgFile')" />
					<input type="text" datatype="file" id="template-shakeIndexImgFile" class="advanced-finfo" readonly="readonly"
						ignore="ignore" errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
				</td>
			</tr>
			<tr>
				<td class="top-td">摇一摇抽奖图:</td>
				<td>
					<input type="button" class="advanced-file" value="选择图片..." onclick="chooseFile('template.shakeUIImgFile')" />
					<input type="text" datatype="file" id="template-shakeUIImgFile" class="advanced-finfo" readonly="readonly"
						ignore="ignore" errormsg="图片格式不正确(jpg,png,gif,jpeg)！" nohighlight="nohighlight" />
				</td>
			</tr> -->
		</table>
	</form>
</div>
</body>
</html>