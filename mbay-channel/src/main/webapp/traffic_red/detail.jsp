<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流量红包-活动详情</title>
<link href="${actx}/wro/${version}/campaign_info.css" rel="stylesheet" type="text/css" />
<link href="${actx}/traffic_red/css/campaign_table.css" rel="stylesheet" type="text/css" />
<link href="${actx}/traffic_red/css/detail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/campaign_info.js"></script>
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript" src="${actx}/js/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript" src="${actx}/js/jquery/jsrender.min.js"></script>
<script type="text/javascript" src="${actx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${actx}/traffic_red/js/detail.js?v=1"></script>
</head>
<body>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width'>
			<!--左边-->
			<div class='left_con'>
				<div class='ckxq'>基本信息</div>
				<%@ include file="/common/leftcon.jsp"%>
				
				<!--右部内容-->
				<!-- 基础配置 -->
				<div class='fr xq_con'>

					<div class="deploy">
						<p class="basis">基础配置</p>
						<p class="developers">开发者中心</p>
					</div>

					<!-- 基础配置 -->
					<div class="division">		
	                   <form method="post" enctype="multipart/form-data" id="form"
							<c:choose>
								<c:when test="${c.status == 'CANCLED' || c.status == 'OVER' }">
									action="${ctx}/traffic_red/campaign/update/forOver.mbay"
								</c:when>
								<c:otherwise>
									action="${ctx}/traffic_red/campaign/update.mbay"	
								</c:otherwise>
							</c:choose>>	
							<input type="hidden" name="id" value="${c.id }" >	
							<input type="hidden" name="number" value="${c.number}">	
							<input type="hidden" name="shareInfo.id" value="${c.shareInfo.id }">	
							<input type="hidden" name="mbayGiftConfig.id" value="${c.mbayGiftConfig.id }">
							<table border="1">
								<tr class="acti">
							    	<td class="activit2" colspan="2">
							    		<div class="base-left">
							    			<div style="padding-bottom: 5px">
								        		<span class="strong">活动名称:</span>
							   					<span>${c.name}</span>
								   			</div>
								   			<div>
								       			<span class="strong">参与方式:</span>
							       				<span class="tit-str">${c.firstShark == false ? '先输手机号' : '先摇一摇' }</span>
							       				<span class="strong">活动编号:</span>
							       				<span class="tit-str">${c.number }</span>
							       				<span class="strong">状态:</span>
							       				<span>${c.status.value}</span>	
								   			</div>		
							    		</div>
							    		<div class="base-right">
							    			<input type="button" id="recorddetail" class="btn-1 btn_confirm" value="营销明细" />
							    			<c:if test="${c.status != 'OVER' and c.status != 'CANCLED' }">
							    				<input class="btn-1 btn_confirm" onclick="cancel('${c.id}',this)" type="button" value="取消活动" />
							    			</c:if>
							    		</div>
					   		    	</td>
							    </tr>
							    <c:if test="${not empty message }">
								    <tr>
								    	<td colspan="2">
								    		<div class="qs_alert">
												<p>${message }</p>
											</div>
								    	</td>
								    </tr>
							    </c:if>
							    <tr>
							   		<td class="activit">活动日期:</td>
							   		<td class="activit2">
							   			<fmt:formatDate pattern="yyyy-MM-dd" value="${c.startTime.toDate()}" />
							   			<span>&nbsp;&nbsp;--&nbsp;&nbsp;</span>
							   			<input type="hidden" id="rq_1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${c.startTime.toDate()}" />" />
							   			<input type="hidden" id="rq_2" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${c.endTime.toDate()}" />" />
							   			<c:choose>
							   				<c:when test="${c.status == 'OVER' }">
							   					<img src="<c:url value='/images/workimages/rq.jpg'/>" class='date-img' style="vertical-align:middle;"
													onclick="WdatePicker({el:'rq',minDate:'#F{$dp.$D(\'rq_2\')}'})" /> 
												<input type="text" name="end_Time" id="rq" style="width:100px;"
													value="<fmt:formatDate pattern="yyyy-MM-dd" value="${c.endTime.toDate()}" />"
													onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_2\')}'})"
													datatype='rq' errormsg='请正确填写日期！' />
							   				</c:when>
							   				<c:when test="${c.status == 'CANCLED' }">
							   					<img src="<c:url value='/images/workimages/rq.jpg'/>" class='date-img' style="vertical-align:middle;" /> 
												<input type="text" name="end_Time" id="rq" style="width:100px;" disabled="disabled"
													value="<fmt:formatDate pattern="yyyy-MM-dd" value="${c.endTime.toDate()}" />" />
							   				</c:when>
							   				<c:otherwise>
							   					<img src="<c:url value='/images/workimages/rq.jpg'/>" class='date-img' style="vertical-align:middle;"
													onclick="WdatePicker({el:'rq',minDate:'#F{$dp.$D(\'rq_1\')&&\'${now}\'}'})" /> 
												<input type="text" name="end_Time" id="rq" style="width:100px;"
													value="<fmt:formatDate pattern="yyyy-MM-dd" value="${c.endTime.toDate()}" />"
													onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')&&\'${now}\'}'})"
													datatype='rq' errormsg='请正确填写日期！' />
							   				</c:otherwise>
							   			</c:choose>
							   		</td>
							    </tr>
								<tr>
							    	<td class="activit">活动地址:</td>
							   		<td class="activit2">
							  			<input readonly="readonly" size="70" id="qrLink" 
							  				value="${mobileDomain }/mbaychannel/tr_mobile/shakeIndex.mbay?number=${campaignNumber}">				
										<div class="relative">
									    	<span>									    
												<input type="button" id="copyCampaignUrl" class="btn-js btn_service" value="复制"/>
												<input type="button" class="btn-0 btn-js btn_service qrcode" value="活动二维码"/>
									     	</span>			 		
										</div>
						   			</td>
						   		</tr>
							    <tr>
			       					<td class="activit">中奖概率(%):</td>
			       					<td class="activit2">
			       						<input type="text" autocomplete="off" name="trafficRate" value="${c.trafficRate}"
			       							datatype="rate" errormsg="请输入整数或小数(0-100)！"
			       							<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if> />
			       					</td>
			       				</tr>
							    <tr>
						        	<td class="activit" valign="top" style="padding-top:20px;">参与时间:</td>
							   		<td class="activit2">
								      	<table style="margin-bottom:0;border:none;" class="timeZoneTable">
								      		<c:choose>
								         		<c:when test="${c.timeQuantums == null || c.timeQuantums.size() == 0 }">
											         <script>
											            $(function(){
											            	$(".timeZoneTable").append($("#timeZoneTemplate").html());
											            	$(".timeZoneTable").find(".js_tdelete").hide();
											            });
											         </script>
								         		</c:when>
								         		<c:otherwise>
								           			<c:forEach var="t" items="${c.timeQuantums }" varStatus="vs">
								            			<tr class="timeZoneTr" >
								            				<td class="activit6" style="text-align:left;">
																<div class="act">
											 						<select class="choice-list1" name="startHour" 
											 							<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>>
											 							<option value="">时</option>
										 								<c:forEach items="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23" var="hour" >
										   									<option value="${hour}" <c:if test="${t.startTime.hours==hour}">selected="selected"</c:if>>${hour}</option>
										 								</c:forEach>
									 								</select>
											 						<select class="choice-list1" name="startMinute" 
											 							<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>>
											 							<option value="">分</option>
									  									<c:forEach items="0,10,20,30,40,50,59" var="minute" >
											   								<option value="${minute }" <c:if test="${t.startTime.minutes==minute}">selected="selected"</c:if>>${minute }</option>
											  							</c:forEach>
									 								</select>
											 						<span style="margin:0 26px">--</span>
											 						<select class="choice-list1" name="endHour" 
											 							<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>>
											 							<option value="">时</option>
											 							<c:forEach items="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23" var="hour" >
											   								<option value="${hour}" <c:if test="${t.endTime.hours==hour}">selected="selected"</c:if> >${hour}</option>
											 							</c:forEach>
										 						    </select>
											 						<select class="choice-list1" name="endMinute" datatype="times" nullmsg="请设置完整的时间段！"
											 							<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>>
											 							<option value="">分</option>
										  								<c:forEach items="0,10,20,30,40,50,59" var="minute" >
											   								<option value="${minute }" <c:if test="${t.endTime.minutes==minute}">selected="selected"</c:if>>${minute }</option>
											  							</c:forEach>
											 						</select>	
											 						<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
											 							<a class="delete0 js_tdelete btn_service" <c:if test="${c.timeQuantums.size()==1 || vs.last}">style="display: none"</c:if>  onclick="deleteTimeZone(this)">删除</a>
																		<a class="delete0 js_tadd addtime btn_service" <c:if test="${vs.count!=c.timeQuantums.size()}"> style="display: none" </c:if>  onclick="addTimeZone(this)">增加</a>
											 						</c:if>	
											 						<span class="Validform_checktip"></span>							
																</div>
															</td>
														</tr>
													</c:forEach>		
								         		</c:otherwise>					      
								      		</c:choose>
								      	</table>	 		      
							   		</td>
						   	    </tr>
							    <tr>
							    	<td class="activit">参与限制(次):</td>
							   		<td class="activit2">
							   			<input name="times" class="onlynum" value="${c.type == 'NO_LIMITED' ? '' : c.times }" style="width:317px" 
							   				<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if> />
								      	<select name="type" onchange="changeCampaignType(this)" class="ctype camp-type" style="margin-left:-5px;"
								      		<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
								      		datatype="cycleType" errormsg="请输入正整数！">
								      		<option <c:if test="${c.type=='DAY'}">selected="selected"</c:if>  value="DAY" >每日</option>
								      		<option <c:if test="${c.type=='WEEK'}">selected="selected"</c:if> value="WEEK" >每周</option> 
								     	 	<option <c:if test="${c.type=='MONTH'}">selected="selected"</c:if> value="MONTH">每月</option>
								      		<option <c:if test="${c.type=='TOTAL'}">selected="selected"</c:if> value="TOTAL">永久</option>
									      	<option <c:if test="${c.type=='NO_LIMITED'}">selected="selected"</c:if> value="NO_LIMITED">不限</option>					      
								      	</select>
							  		</td>
								</tr>
								<tr>
			       					<td class="activit">告罄提醒:</td>
			       					<td class="activit2">
			       						<input type="text" autocomplete="off" name="thresholdMobile" value="${c.thresholdMobile}"
			       							<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if> />
			       						<i class="tip_i">?</i>
			       						<div class="tooltip">${thresholdSMS}<em></em></div>
			       					</td>
			       				</tr>
			       				<c:choose>
								<c:when test="${relationList!=null && relationList.size()>0 }">
									<tr>
										<td class="activit">商城:</td>
								   		<td class="activit2">
											<select name="mall.id" style="width:402px;" class="ctype" datatype="*"
												<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>>
									      		<option value="0">默认商城</option>
									      		<c:forEach items="${relationList}" var="relation">
													<option value="${relation.mall.id}" 
														<c:if test="${relation.mall.id eq c.mall.id}">selected="selected"</c:if>>
														${relation.mall.name}
													</option>
												</c:forEach>
									      	</select>
								  		</td>
									</tr>
								</c:when>
								<c:otherwise>
								<input type="hidden" name="mall.id" value="0"/>
								</c:otherwise>
			       				</c:choose>
								<tr>
									<td class="activit">送人链接:</td>
									<td class="activit2">
										<input type="text" autocomplete="off" value="${c.mbayGiftConfig.participationLink }" name="mbayGiftConfig.participationLink"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="url" />
									</td>
								</tr>
								<tr>
									<td class="activit">送人标题:</td>
									<td class="activit2">
										<input type="text" autocomplete="off" value="${c.mbayGiftConfig.shareTitle }" name="mbayGiftConfig.shareTitle"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="*" />
									</td>
								</tr>
								<tr>
									<td class="activit">送人内容:</td>
									<td class="activit2">
										<input type="text" autocomplete="off" value="${c.mbayGiftConfig.shareContent }" name="mbayGiftConfig.shareContent"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="*" />
									</td>
								</tr>
								<tr class="tab-ttr">
									<td class="activit">图片:</td>
									<td>
										<p class="tab-ttrp">
											<span class="give-ico">送人图标</span>
											<span class="give-bg">送人背景图</span>
										</p>
									</td>
								</tr>
								<tr class="tab-tr">
									<td class="activit" valign="top"></td>
									<td class="activit2" style="padding-top:0;">
										<img id="mbayGiftConfig-giftShareImgFile" src='<fs:fdfs value="${c.mbayGiftConfig.shareImg }" />' class="share-img" />
										<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
											<span class="ftp-msg">尺寸100*100，格式jpg,png,jpeg,gif</span>
											<div class="inputa btn_service" onclick="chooseFile('mbayGiftConfig.giftShareImgFile')">选择图片</div>
											<input type="file" name="mbayGiftConfig.giftShareImgFile" datatype="file" class="uplod_file hide"
												errormsg="图片格式不正确(jpg,png,jpeg,gif)！" ignore="ignore" />
											<input type="hidden" value="${c.mbayGiftConfig.shareImg }" name="mbayGiftConfig.shareImg" />
										</c:if>							
									</td>
								</tr>
								<tr class="tab-tr" style="display:none">
									<td class="activit" valign="top"></td>
									<td class="activit2" style="padding-top:0;">
										<img id="mbayGiftConfig-giftBgImgFile" src='<fs:fdfs value="${c.mbayGiftConfig.bgImg }" />' class="share-img share-img2" />
										<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
											<span class="ftp-msg">尺寸640*1136，格式jpg,png,jpeg,gif</span>
											<div class="inputa btn_service" onclick="chooseFile('mbayGiftConfig.giftBgImgFile')">选择图片</div>
											<input type="file" name="mbayGiftConfig.giftBgImgFile" datatype="file" class="uplod_file hide"
												errormsg="图片格式不正确(jpg,png,jpeg,gif)！" ignore="ignore" />
											<input type="hidden" value="${c.mbayGiftConfig.bgImg }" name="mbayGiftConfig.bgImg" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="activit">模板配置:</td>
									<td class="activit2">
										<a style="color: #666;text-decoration: underline;" href="javascript:void(0)" class="check_share" >点击查看</a>
										<c:choose>
											<c:when test="${c.status == 'CANCLED' || c.status == 'OVER' }">
												<script type="text/javascript">
													$(function() {
														$(".check_share").click(function(){
															$.getJSON(ctx+"/traffic_red/campaign/${c.id}/template/${c.template.id}/shareTemplate.mbay", function(jsonTemplate){
																if(!jsonTemplate.success){
																	$.messager.alert({ content: jsonTemplate.message });
																	return;
																  }
																  var shareTemplate = $('#shareTemplate').render(jsonTemplate.template);
																  $.messager.alert({ title: "模板配置", content: shareTemplate, css: { width: 570 } });
																  triggerFileChange();
																  bindTemplateValid();
															});
														});
													});
												</script>
											</c:when>
											<c:otherwise>
												<script type="text/javascript">
													$(function() {
														$(".check_share").click(function() {
															$.getJSON(ctx+"/traffic_red/campaign/${c.id}/template/${c.template.id}/shareTemplate.mbay", function(jsonTemplate){
																if(!jsonTemplate.success){
																	$.messager.alert({ content: jsonTemplate.message });
																	return;
																}
																var shareTemplate = $('#shareTemplate').render(jsonTemplate.template);
																$.messager.confirm({
																	title: "模板配置",
																	content: shareTemplate,
																	css: { width: 570 },
																	button: {
																		ok: {
																			autoClose: false,
																			callback: function() {
																				$("#shareInfoForm").submit();
																			}
																		}
																	}
																});
																triggerFileChange();
																bindTemplateValid();
															});
														});
													});
												</script>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td class="activit">是否开启分享功能:</td>
									<td class="activit2">
										<span class='mbtx'>
											<div class="slider-box ${c.shareInfo.enableState == 'ENABLED' ? 'on' : 'off' }"
												<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>>
												<div class="slider"></div>
												<span class="m" style="margin-left: 16px;">是</span> 
												<span class="w" style="color: #118EEF; margin-right: 17px;">否</span> 
												<input id="ses" type="checkbox" <c:if test="${c.shareInfo.enableState == 'ENABLED' }">checked="checked"</c:if> />
												<input type="hidden" value="${c.shareInfo.enableState }" name="shareInfo.enableState" />
											</div>
										</span>
									</td>
								</tr>
								<tr class="shinfo">
									<td class="activit">分享链接:</td>
									<td class="activit2">
										<input type="text" autocomplete="off" value="${c.shareInfo.shareLink }" name="shareInfo.shareLink"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="url|shinfo" />
									</td>
								</tr>
								<tr class="shinfo">
									<td class="activit">分享标题:</td>
									<td class="activit2">
										<input type="text" autocomplete="off" value="${c.shareInfo.shareTitle }" name="shareInfo.shareTitle"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="*|shinfo" />
									</td>
								</tr>
								<tr class="shinfo">
									<td class="activit">分享内容:</td>
									<td class="activit2">
										<input type="text" autocomplete="off" value="${c.shareInfo.content }" name="shareInfo.content"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="*|shinfo" />
									</td>
								</tr>
								<tr class="shinfo">
									<td class="activit">分享有效次数:</td>
									<td class="activit2">
										<input type="text" class="onlynum" autocomplete="off" value="${c.shareInfo.shareTimes }" name="shareInfo.shareTimes"
											<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
											datatype="n1-3|shinfo" errormsg="请输入正整数(0-999)！" />
									</td>
								</tr>
								<tr class="shinfo">
									<td class="activit" valign="top">分享图标:</td>
									<td class="activit2">
										<img src='<fs:fdfs value="${c.shareInfo.shareImg }" />' class="share-img" id="shareInfo-shareImgFile" />
										<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
											<span class="ftp-msg">尺寸100*100，格式jpg,png,jpeg,gif</span>
											<div class="btn_service inputa" onclick="chooseFile('shareInfo.shareImgFile')">选择图片</div>
											<input type="file" name="shareInfo.shareImgFile" style="border:0;vertical-align:top;width:260px;"
												datatype="file|shinfo" errormsg="图片格式不正确(jpg,png,jpeg,gif)！" ignore="ignore" class="uplod_file hide" />
											<span class="Validform_checktip" style="vertical-align:top;"></span>
											<input type="hidden" value="${c.shareInfo.shareImg }" name="shareInfo.shareImg" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<c:if test="${c.status != 'CANCLED' }">
											<input type="submit"  class="win-probability-btn btn-js btn_confirm" value="保存配置" />
										</c:if>
									</td>
								</tr>
							 </table>
						</form>												
						<div class="table">
							<div class='tab_tit0 clearfix0'>
								<ul class="clearfix_list">								
									<li class="nav_list_now0"> <a href="javascript:void(0)" class="clearfix_list2">红包产品</a></li>
									<li><a href="javascript:void(0)">MB产品</a></li>
								</ul>
							</div>						
	       					<article class="dabian"  id="trafficProductsTable" ></article>	
		    		  		<article class="dabian" class="dabian" style="display:none" id="mbayProductsTable"></article>
			    		</div>				    
					</div>
					<!-- 基础配置END -->		
								
					<!-- 开发者配置 -->
					<div class="division" style="display:none">
						<div class="basic-message bbc">						
							<p><label>用户编号:</label><span>${sessionScope.channel.userNumber}</span></p>
							<p><label>活动ID:</label><span>${c.id }</span></p> 
						</div>
						<div class="server-basic">
							<label>服务器配置</label>(<span id="advancedEnabledDic">${advancedConfig.enabled?"已启用":"未启用" }</span>)
							<p class="up-start-btn">
								<a href="javascript:void(0)" id="advancedDisabled" <c:if test="${!advancedConfig.enabled}"> style="display: none"</c:if> 
									onclick="disabledAdvanced();" class="btn_confirm js_advance_switch">禁用</a>
								<a href="javascript:void(0)" id="advancedEnabled" <c:if test="${advancedConfig.enabled}"> style="display: none"</c:if> 
									onclick="enabledAdvanced();" class="btn_confirm js_advance_switch">启用</a>
							</p>
						</div>
						<div class="server-message bbc">						
							<p><label>URL(服务器地址):</label><span>${advancedURL }</span></p>
							<p>
								<label>key(密钥):</label><span id="advanced_key_val">${advancedConfig.key}</span>
								<a href="javascript:void(0)" class="key_reset" >重置</a>
							</p>
						</div>  
						<div style="padding-top: 30px">
							<span style="font-size: 16px;text-shadow: 0 0 1px #999;color: #333;">说明文档</span>
							<table class="message-table">
								<tr><td>参数</td><td>描述</td></tr>
								<tr><td>userNumber</td><td>商户编号（8位）</td></tr>
								<tr><td>campaignID</td><td>活动ID</td></tr>
								<tr><td>signtime</td><td>签名时间(毫秒，时差不超过3分钟)</td></tr>
								<tr><td>code</td><td>消息加盐码</td></tr>
								<tr><td>sign</td><td>签名 MD5(userNumber+campaignID+signtime+code+key)</td></tr>
							</table>
							<span style="font-size: 16px;text-shadow: 0 0 1px #999;color: #333;"></span>
							<div class="message-div">
						  		<p>1.用户请求商家平台，商家为此请求生成唯一消息加盐码code。</p>
							  	<p>2.商户加密请求消息。加密方式：MD5(userNumber+campaignID+signtime+code+key),请严格遵照此顺序。</p>
							  	<p>3.商户将用户请求重定向至美贝直通车接口</p>
							  	<p>4.美贝直通车收到请求后，判断当前时间与签名时间（signtime）是否在有效时差内，并根据活动编号判断是否已开启开发者模式。MD5加密
							  		(userNumber+campaignID+signtime+code+key)与sign比较一致后返回流量红包入口页面。
								</p>
						  	</div>
						  	<div style="padding-top: 30px">
						  	<span style="font-size: 16px;text-shadow: 0 0 1px #999;color: #333;">错误描述</span>
							<table class="message-table">
								<tr><td>错误编码</td><td>描述</td></tr>
								<tr><td>ILLEGAL_ARGUMENT</td><td>输入参数有错误</td></tr>
								<tr><td>SELLER_OR_CAMPAIGN_NOT_EXIST</td><td>商家或活动不存在</td></tr>
								<tr><td>REQUEST_TIMEOUT</td><td>请求超时</td></tr>
								<tr><td>DISABLED_ADVANCED</td><td>开发者模式未启用</td></tr>
								<tr><td>ILLEGAL_SIGN</td><td>签名不正确</td></tr>
							</table>
						  	</div>
						</div>
					</div>
					<!-- 开发者配置END -->	
									
					<table style="display: none" id="timeZoneTemplate">					   
	             	 	<tr class="timeZoneTr">
	             	 		<td style="text-align:left;" class="activit6">
								<div class="act">
						 			<select class="choice-list1" name="startHour" 
						 				<c:if test="${c.status == 'OVER' || c.status == 'CANCLED' }">disabled="disabled"</c:if>> 
						  				<option value="">时</option>
						 				<c:forEach items="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23" var="hour">
						   					<option value="${hour}">${hour}</option>
						 				</c:forEach>
						 			</select>
						 			<select class="choice-list1" name="startMinute" 
						 				<c:if test="${c.status == 'OVER' || c.status == 'CANCLED' }">disabled="disabled"</c:if>>
						  				<option value="">分</option>
						  				<c:forEach items="0,10,20,30,40,50,59" var="minute">
						   					<option value="${minute }">${minute }</option>
						  				</c:forEach>
						 			</select>
						 			<span style="margin:0 23px">--</span>
						 			<select class="choice-list1" name="endHour" 
						 				<c:if test="${c.status == 'OVER' || c.status == 'CANCLED' }">disabled="disabled"</c:if>>
						 		 		<option value="">时</option>
						 				<c:forEach items="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23" var="hour">
						   					<option value="${hour }">${hour}</option>
						 				</c:forEach>
						 			</select>
						 			<select class="choice-list1" name="endMinute" datatype="times" nullmsg="请设置完整的时间段！"
						 				<c:if test="${c.status == 'OVER' || c.status == 'CANCLED' }">disabled="disabled"</c:if>>
						  				<option value="">分</option>
						  				<c:forEach items="0,10,20,30,40,50,59" var="minute">
						   					<option value="${minute }" >${minute }</option>
						  				</c:forEach>
						 			</select>			
						 			<c:if test="${c.status != 'OVER' && c.status != 'CANCLED' }">
						 				<a class="delete0 js_tdelete btn_service" onclick="deleteTimeZone(this);" >删除</a>								
										<a class="delete0 js_tadd btn_service" onclick="addTimeZone(this)">增加</a>			
						 			</c:if>		
						 			<span class="Validform_checktip"></span>		
								</div>
							</td>
						</tr>
			    	</table>
					<img id="qrImg" style="display:none;" src="${ctx}/qrcode/generate.mbay?content=${mobileDomain }/mbaychannel/tr_mobile/shakeIndex.mbay?number=${campaignNumber}">                	
				</div>				
			</div>
		</div>
	</div>
</div>

<script type="text/x-jsrender" id="trafficProductsJsrender">                         
<p class="red-packet">
	<span>预设总量：</span>
	<span class="marg" id="tpool">{{:productConfig.poolSize}}</span>&nbsp;美贝
	<span>红包剩余：</span>
	<span class="marg" id="trpool">{{:productConfig.poolRemain}}&nbsp;美贝</span>
    <c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="win-probability-btn trafficPoolbtn btn_service" onclick="increaseRedTrafficPool()">增加</a>
	</c:if>       					
	<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="add_trafficProduct add-bt"  onclick="addTrafficProduct();">添加待选红包产品</a>
	</c:if>
</p>
<p class="red-packet">
	<span>单日上限(美贝):</span>
	<span class="marg">
   		<span class='redold' >{{:productConfig.dailyLimit==-1?'不限':productConfig.dailyLimit}}</span>
    	<span class='rednew hide'><input id="red_value" class="poolsize" value="{{:productConfig.dailyLimit==-1?'':productConfig.dailyLimit}}"/></span>
    </span>
    <a class='add-bt redold' onclick="updateProperty('red')">修改</a>
    <a class="add-bt hide rednew" onclick="saveProductLimt('red','TRAFFIC_PACKAGE');">保存</a>
    <a class="add-bt hide rednew" onclick="cancelUpdate('red')">取消</a>&nbsp;&nbsp;
	<span>当日剩余(美贝):</span>
	<span class="marg trpool">{{:productConfig.dailyRemain==-1?'不限':productConfig.dailyRemain}}</span>
	<span>告警阀值(美贝):</span>
	<span class="marg thold">{{:productConfig.threshold==-1?'不限':productConfig.threshold}}</span>
    <span class='thnew hide'><input id="th_value" class="poolsize" value="{{:productConfig.threshold==-1?'':productConfig.threshold}}"/></span>
    <a class='add-bt thold' onclick="updateProperty('th')">修改</a>
    <a class="add-bt hide thnew" onclick="saveProductThreshold('th','TRAFFIC_PACKAGE');">保存</a>
    <a class="add-bt hide thnew" onclick="cancelUpdate('th')">取消</a>&nbsp;&nbsp;
</p>
<table class="tables0c" style="margin-top:3px;border: 10px">
	<tr>
		<td>产品</td>
		<td>运营商</td>
		<td width="200px">权重</td>
		<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
			<td>操作</td>
		</c:if>
	</tr> 
	{{for trafficProducts}}																 
		<tr class="bian">
			<td>{{:trafficPackage.traffic }}MB</td>
			<td>{{:trafficPackage.operatorType}}</td>
			<td width="350px" style="text-align: ${c.status != 'OVER' && c.status != 'CANCLED' ? 'right' : 'center'};padding-right: 5px;padding-top:0;padding-bottom:0;">
				<span class="product_value ratio_val" style="${c.status != 'OVER' && c.status != 'CANCLED' ? 'float:left;' : ''} margin-left:20px;margin-top:0px;">{{:ratio}}</span>
				<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
					<input class="td-num product_update" style="float:left; margin-left:20px;margin-top:8px;" value="{{:ratio}}"  maxlength="3"/>
					<a class="redact product_value btn_service">编辑</a>
					<a class="product_update handle btn_service" href="javascript:void(0)" onclick="updateProductRatio('TRAFFIC_PACKAGE',{{:trafficPackage.packageId}},this)" >保存</a>
					<a class="cancel product_update handle btn_service">取消</a>
				</c:if>		
			</td>
			<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
				<td class="right1">                          
					<a class="romve btn_service" href="javascript:void(0)" onclick="deleteProduct('TRAFFIC_PACKAGE',{{:trafficPackage.packageId}},this)" style="padding: 5px 20px;">删除</a>
				</td>	
			</c:if>							 
		</tr>
	{{/for}}
</table>
</script>
                    
<script type="text/x-jsrender" id="shareTemplate">
<div class="share-table" style="position:relative">
	<form id="shareInfoForm" enctype="multipart/form-data" method="post"
		action="${ctx}/traffic_red/campaign/${c.id}/template/${c.template.id}/update.mbay">
		<table style="border: 0px" id="shareTab">
			<tr>
				<td class="share-tr" valign="top">惊喜连接:</td>
				<td class="share-tr2">
					<input type="text" class="txt" name="logoCycleLink" value="{{:logoCycleLink}}" autocomplete="off"
					<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
						datatype="nullUrl|url" ignore="ignore" errormsg=" " sucmsg=" " />
					<span class="Validform_checktip" style="margin-left:10px;"></span>
				</td>
			</tr>
			<tr>
				<td class="share-tr" valign="top">结果连接:</td>
				<td class="share-tr2">
					<input type="text" class="txt" name="sharkResultLink" value="{{:sharkResultLink}}"  autocomplete="off"
						<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
						datatype="nullUrl|url" ignore="ignore" errormsg=" " sucmsg=" " />
					<span class="Validform_checktip" style="margin-left:10px;"></span>
				</td>
			</tr>
			<tr>
				<td class="share-tr" valign="top">广告连接(1):</td>
				<td class="share-tr2">
					<input type="text" class="txt" name="adLink1" value="{{:adLink1}}"  autocomplete="off"
						<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
						datatype="nullUrl|url" ignore="ignore" errormsg=" " sucmsg=" " />	
					<span class="Validform_checktip" style="margin-left:10px;"></span>
				</td>
			</tr>
			<tr>
				<td class="share-tr" valign="top">广告连接(2):</td>
				<td class="share-tr2">
					<input type="text" class="txt" name="adLink2" value="{{:adLink2}}"  autocomplete="off"
						<c:if test="${c.status == 'CANCLED' || c.status == 'OVER' }">disabled="disabled"</c:if>
						datatype="nullUrl|url" ignore="ignore" errormsg=" " sucmsg=" " />
					<span class="Validform_checktip" style="margin-left:10px;"></span>
				</td>
			</tr>
			<tr>
				<td class="share-tr" valign="top">广告图片(1):</td>
				<td class="share-tr2">
					<img src="http://<fs:fdfs value=""/>/{{:adImg1}}" id="adImg1File" />
					<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
						<span class="ftp-msg">尺寸100*100，格式jpg,png,jpeg,gif</span>
						<input type="button" value="选择图片" class="btn_service inputa tmp-file" onclick="chooseFile('adImg1File')" />
						<input name="adImg1File" type="file" class="uplod_file hide"
							datatype="file" ignore="ignore" errormsg=" " sucmsg=" " />
						<span class="Validform_checktip" style="margin-left:16px;"></span>
						<input type="hidden" name="adImg1" value="{{:adImg1}}" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="share-tr" valign="top">广告图片(2):</td>
				<td class="share-tr2">
					<img src="http://<fs:fdfs value=""/>/{{:adImg2}}" id="adImg2File" />
					<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
						<span class="ftp-msg">尺寸100*100，格式jpg,png,jpeg,gif</span>
						<input type="button" value="选择图片" class="btn_service inputa tmp-file" onclick="chooseFile('adImg2File')" />
						<input name="adImg2File" type="file" class="uplod_file hide"
							datatype="file" ignore="ignore" errormsg=" " sucmsg=" " />
						<span class="Validform_checktip" style="margin-left:16px;"></span>
						<input type="hidden" name="adImg2" value="{{:adImg2}}" />
					</c:if>
				</td>
			</tr>
			<!--<tr>
				<td class="share-tr" valign="top">摇一摇首页图:</td>
				<td class="share-tr2">
					<img src="http://<fs:fdfs value=""/>/{{:shakeIndexImg}}" id="shakeIndexImgFile" />
					<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
						<span class="ftp-msg"></span>
						<input type="button" value="选择图片" class="btn_service inputa tmp-file" onclick="chooseFile('shakeIndexImgFile')" />
						<input name="shakeIndexImgFile" type="file" class="uplod_file hide"
							datatype="file" ignore="ignore" errormsg=" " sucmsg=" " />
						<span class="Validform_checktip" style="margin-left:16px;"></span>
						<input type="hidden" name="shakeIndexImg" value="{{:shakeIndexImg}}" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="share-tr" valign="top">摇一摇抽奖图:</td>
				<td class="share-tr2">
					<img src="http://<fs:fdfs value=""/>/{{:shakeUIImg}}" id="shakeUIImgFile" />
					<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
						<span class="ftp-msg"></span>
						<input type="button" value="选择图片" class="btn_service inputa tmp-file" onclick="chooseFile('shakeUIImgFile')" />
						<input name="shakeUIImgFile" type="file" class="uplod_file hide"
							datatype="file" ignore="ignore" errormsg=" " sucmsg=" " />
						<span class="Validform_checktip" style="margin-left:16px;"></span>
						<input type="hidden" name="shakeUIImg" value="{{:shakeUIImg}}" />
					</c:if>
				</td>
			</tr>-->
		</table>
	</form>
</div>
</script>

<script type="text/x-jsrender" id="trafficProductTemplate">
<!-- 流量产品 start -->
{{if !selected}}
	<div>预设总量(美贝):<input name="poolSize" class="poolsize" /> 单日上限(美贝)：<input name="dailyLimit" class="poolsize"/> 告警阀值(美贝)：<input name="threshold" class="poolsize" /></div>
{{/if}}
<div class="table-list-content" id="redPdiv">
	<form id="redPForm">
		<!--活动内部选项卡 start-->
		<ul class="tab-con-tab-ul table-ul">
			<li><a href="javascript:void(0)" class="tab-ul-font">移动</a></li>
			<li><a href="javascript:void(1)">联通</a></li>
			<li><a href="javascript:void(2)">电信</a></li>
		</ul>
		<!--END 移动活动内部选项卡 -->		
		<div class="table-10list-content">	
			<div class="weight">
            	{{for MOBILE}}	
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mobile-{{:packageId}}" value="{{:packageId}}" class="chk_1 mobile-1" />
						<label for="mobile-{{:packageId}}"></label>
						<span>{{:traffic}}MB</span>
						<input class="onlynum" name="ratio_{{:packageId}}" type="text" placeholder="权重" packageId="1" maxlength="3" />
					</p>				
                {{/for}}					
			</div>
			<div class="weight weight2">
            	{{for UNICOM}}
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mobile-{{:packageId}}" value="{{:packageId}}" class="chk_1" />
						<label for="mobile-{{:packageId}}"></label>
						<span>{{:traffic}}MB</span>
						<input class="onlynum" name="ratio_{{:packageId}}" type="text" placeholder="权重" packageId="1" maxlength="3" />
					</p>					
                {{/for}}
			</div>
			<div class="weight weight2">
            	{{for TELECOM}}
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mobile-{{:packageId}}" value="{{:packageId}}" class="chk_1" />
						<label for="mobile-{{:packageId}}"></label>
						<span>{{:traffic}}MB</span>
						<input class="onlynum" name="ratio_{{:packageId}}" type="text" placeholder="权重" packageId="1" maxlength="3" />
					</p>					
                {{/for}}
			</div>
		</div>
		<input type="hidden" datatype="products" value="null" /> 
		<span class="Validform_checktip" style="margin:0;"></span>		
   	</form>
</div>
<!-- 流量产品 END -->
</script>
							
<script type="text/x-jsrender" id="mbayProductsJsrender">
<p class="red-packet">
	<span>预设总量：</span>
	<span class="marg" id="mpool">{{:productConfig.poolSize}}</span>&nbsp;MB
	<span>MB剩余：</span>
	<span class="marg" id="mrpool">{{:productConfig.poolRemain}}</span>&nbsp;MB
    <c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="win-probability-btn trafficPoolbtn btn_service" onclick="increaseMBPool()">增加</a>
	</c:if>       					
	<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="add_trafficProduct add-bt"  onclick="addMBProduct();">添加待选MB产品</a>
	</c:if>
</p>
<p class="red-packet">
	<span>单日上限(MB):</span>
	<span class="marg">
    	<span class='mbold' >{{:productConfig.dailyLimit==-1?'不限':productConfig.dailyLimit}}</span>
        <span class='mbnew hide'><input id="mb_value" class="poolsize" value="{{:productConfig.dailyLimit==-1?'':productConfig.dailyLimit}}"/></span>
    </span>
   	<a class='add-bt mbold' onclick="updateProperty('mb')">修改</a>
    <a class="add-bt hide mbnew" onclick="saveProductLimt('mb','MBAY_PACKAGE');">保存</a>
    <a class="add-bt hide mbnew" onclick="cancelUpdate('mb')">取消</a>&nbsp;&nbsp;
	<span>当日剩余(MB):</span>
	<span class="marg trpool">{{:productConfig.dailyRemain==-1?'不限':productConfig.dailyRemain}}</span>
	<span>告警阀值(MB):</span>
	<span class="marg mbthold">{{:productConfig.threshold==-1?'不限':productConfig.threshold}}</span>
    <span class='mbthnew hide'><input id="mbth_value" class="poolsize" value="{{:productConfig.threshold==-1?'':productConfig.threshold}}"/></span>
    <a class='add-bt mbthold' onclick="updateProperty('mbth')">修改</a>
    <a class="add-bt hide mbthnew" onclick="saveProductThreshold('mbth','MBAY_PACKAGE');">保存</a>
    <a class="add-bt hide mbthnew" onclick="cancelUpdate('mbth')">取消</a>&nbsp;&nbsp;
</p>
<table class="tables0">
	<tr>
		<td>产品</td>
		<td>权重</td>
		<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
			<td>操作</td>
		</c:if>
	</tr>
    {{for mbayProducts}}						
		<tr class="bian">
			<td>{{:mbay.mbay}}MB</td>
			<td width="350px" style="text-align: ${c.status != 'OVER' && c.status != 'CANCLED' ? 'right' : 'center'};padding-right: 5px;padding-top:0;padding-bottom:0;">
				<span class="product_value ratio_val" style="${c.status != 'OVER' && c.status != 'CANCLED' ? 'float:left;' : ''} margin-left:20px;margin-top:8px;">{{:ratio}}</span>
				<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
					<a class="redact product_value btn_service">编辑</a>
					<input class="td-num product_update" style="float:left; margin-left:20px;margin-top:8px;" value="{{:ratio}}" maxlength="3"/>
					<a class="product_update handle btn_service" href="javascript:void(0)" onclick="updateProductRatio('MBAY_PACKAGE',{{:mbay.id}},this)">保存</a>
					<a class="cancel product_update handle btn_service">取消</a>
				</c:if>		
			</td>
			<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
				<td class="right1">
					<a href="javascript:void(0)" onclick="deleteProduct('MBAY_PACKAGE','{{:mbay.id}}',this)" class="btn_service" style="padding: 5px 20px;">删除</a>
				</td>	
			</c:if>					 
		</tr>
    {{/for}}
</table>
</script>
			    				    			
<script type="text/x-jsrender" id="mbayProductTemplate">
<!-- 美贝产品 start -->
<form id="mbayPForm" >
	{{if !selected}}
    	<div>预设总量(MB):<input name="poolSize" class="poolsize" /> 单日上限(MB)：<input name="dailyLimit" class="poolsize"/> 告警阀值(MB)：<input name="threshold" class="poolsize" /></div>
    {{/if}}
	<div class="table-list-content" id="redPdiv">
		<!--END 移动活动内部选项卡 -->		
		<div class="table-10list-content">	
			<div class="weight">
            	{{for mbayProducts}}	
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mbay-{{:id}}" value="{{:id}}" class="chk_1 mobile-1" />
						<label for="mbay-{{:id}}"></label>
						<span>{{:mbay}}MB</span>
						<input class="onlynum" name="ratio_{{:id}}" type="text" placeholder="权重" packageId="1" maxlength="3" />
					</p>				
                {{/for}}					
			</div>				
		</div>
		<input type="hidden" datatype="products" value="null" /> 
		<span class="Validform_checktip" style="margin:0;"></span>	
	</div>
</form>
<!-- 美贝产品 END -->
</script>	
</body>		
</html>