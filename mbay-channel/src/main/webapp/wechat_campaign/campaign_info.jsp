<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信伴侣-活动详情</title>
<link href="${actx}/wro/${version}/campaign_info.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/campaign_info.js"></script>
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript"
	src="${actx}/js/zclip/jquery.zclip.min.js"></script>
	 <style>
	 .yxq{
  margin-left: 15px;
}
	 </style>
<script type="text/javascript">
	$(function() {
		$("#changeToken").bind("click",function() {
			window.location.href = '<c:url value="/wechatCampaign/to_set_develop_model.mbay?eventnumber=${eventnumber}"/>';
		});

		$(".hdmxs").bind("click",function() {
			window.location.href = '<c:url value="/traffic/record.mbay?eventnumber=${event.eventnumber}"/>';
		});
		
		$(".qxs").bind("click",function() {
			var confirmmsg = "确定取消当前活动？";
			if ($("#eventInactive").val() == true) {
				confirmmsg = "取消活动将会对您的营销造成影响，确认取消活动？取消后将无法恢复！";
			}
			$.messager.confirm({
				title:"重要提示",
				content:confirmmsg,
				button:{
					ok:{
						callback:function() {
							$.getJSON("${pageContext.request.contextPath}/wechatCampaign/cancle_campaign.mbay?eventnumber=${eventnumber}",function(json) {
								if (json.success) {
									window.location.reload(true);
								} else {
									$.messager.alert({content:json.message});
								}
							});
						}
					}
				}
			});		
		});

		$("#repeatEnable").bind("change",function(obj) {
			var model = $(this).attr("checked");
			var repeatmodel = model == "checked" ? true : false;
			$.getJSON("${pageContext.request.contextPath}/wechatCampaign/upd_repeat_model.mbay?eventnumber=${eventnumber}&repeat_enable="+ repeatmodel,function(suc) {
				if (!suc) {
					$.messager.alert({content:"操作失败!"});
				}
			});
		});

		$("#continuable").bind("change",function(obj) {
			var checked = $(this).is(":checked");
			var url = "${pageContext.request.contextPath}/wechatCampaign/upd_continuable_model.mbay?eventnumber=${eventnumber}&continuable="+ checked;
			$.getJSON(url, function(suc) {
				if (!suc) {
					$.messager.alert({content:"操作失败!"});
				}
			});
		});
		
		$("#directEnable").bind("change",function(obj) {
			var checked = $(this).is(":checked");
			var url = "${pageContext.request.contextPath}/wechatCampaign/changeDirectEnable.mbay?eventnumber=${eventnumber}&directEnable="+ checked;
			$.getJSON(url, function(suc) {
				if (!suc) {
					$.messager.alert({content: "操作失败!"});
				}
			});
		});

		$(".right_copy").zclip({
			path : '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
			copy : function() {
				return $("#weburl").val();
			}
		});

	});
	function changeEventDate() {
		$("#eventdate").hide();
		$("#changedate").show();
		var edit = $("#edit").html();
		if (edit == '修改') {
			$("#edit").html("确认");
			$("#edit").append("<a href='javascript:show()'>取消<a>");
		} else {
			//执行日期修改
			if (changedate()) {
				$("#edit").html("修改");
			}
		}
	}

	function show() {
		$("#changedate").hide();
		$("#eventdate").show();
		$("#partedit").find("a").html("修改");
	}
	function changedate() {
		var start = $('[name="starttime"]').val();
		var end = $('[name="endtime"]').val();
		if (start == '') {
			alert("日期不能为空！");
		} else if (end == '') {
			alert("日期不能为空！");
		} else {
			var flag = false;
			$.getJSON("${pageContext.request.contextPath}/wechatCampaign/edit_campaign_date.mbay?eventnumber=${eventnumber}&starttime="+ start + "&endingtime=" + end, function(json) {
				if (json) {
					window.location.reload();
					flag = true;
				} else {
					$.messager.alert({content: "操作失败!"});
					flag = false;
				}
			});
			return flag;
		}
	}
</script>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width'>
				<!--左边-->
				<div class='left_con fl'>
					<div class='ckxq'>微信伴侣 【${event.eventname}】 - 活动详情</div>
					<%@ include file="/common/leftcon.jsp"%>
					<!--右部内容-->
					<div class='fr xq_con'>
						<div class='tab_tit clearfix'>
							<ul>
								<li class="nav_list_now"><a href="javascript:void(0)">基本信息</a></li>
								<li><a
									href="<c:url value='/wechatCampaign/to_campaign_info_template.mbay?eventnumber=${event.eventnumber}'/>">活动模板</a></li>
								<li><a
									href="<c:url value='/wechatCampaign/to_campaign_info_advanced.mbay?campaignNumber=${event.eventnumber}'/>">开发者中心</a></li>
							</ul>
						</div>
						<div class='xq'>
							<div class='hd_1'>
								<input type="hidden" id="eventInactive"
									value="${event.state=='IN_ACTIVE'}" /> <span class='mc'
									style='margin-left: 15px;'>活动名称:</span><span class='gz'
									style='display: inline-block; width: 220px;'>${event.eventname}</span>
								<span class='mc'>活动方式:</span><span class='gz'
									style='display: inline-block; width: 170px;'>送${event.sendway.getValue()}</span>
								
							</div>
							<div class='hd_2'>
							<span class='bh'>活动编号:</span><span class='shuzi'>${event.eventnumber }</span>
								<span class='rq' style='margin-left: 85px;'>活动日期:</span> <span
									class='sj_0' id="eventdate"> <fmt:formatDate
										pattern="yyyy-MM-dd" value="${event.starttime.toDate()}" />~<fmt:formatDate
										pattern="yyyy-MM-dd" value="${event.endingtime.toDate()}" />
								</span> <span id="changedate" style="display: none"> <fmt:formatDate
										pattern="yyyy-MM-dd" value="${event.starttime.toDate()}" />~
									<input id="rq_1" name="starttime" readonly="readonly"
									value="<fmt:formatDate pattern="yyyy-MM-dd" value="${event.starttime.toDate()}"/>"
									type="hidden" /> <input id="rq_2" name="endtime"
									style="width: 68px;" class="Wdate" type="text"
									onclick="WdatePicker({el:'rq_2',minDate:'#F{$dp.$D(\'rq_1\')}'})" />
								</span>
								<c:if
									test="${event.state=='NOT_STARTED' or event.state=='IN_ACTIVE'}">
									<span id="partedit"> <a id="edit"
										href="javascript:changeEventDate()">修改</a>
									</span>
								</c:if>
                             </div>
							<div class='hd_2'>
							<span class='yxq'>创建日期:</span><span class='ygy'
									style='margin-left: 0px;'><fmt:formatDate
										pattern="yyyy-MM-dd HH:mm:ss"
										value="${event.createtime.toDate()}" /></span>
								<span class='rq' style='margin-left: 86px;'>预计总量:</span><span
									class='sj_0' style='display: inline-block; width: 165px;'>${event.quantity}
									&nbsp;份</span> 
							</div>
							<div class="hd_2">
							<span class='yxq'>锁定额度:</span><span class='ygy'
									style='display: inline-block; width: 156px;'>${event.amount}
									&nbsp;美贝</span>
								<c:if test="${event.sendway=='MBAY' }">
									<span class='rq'>单用户赠送:</span>
									<span class='sj'>${event.mbaysendunit} &nbsp;</span>
								</c:if>
								<span class='rq'  style="margin-left: 77px">已送出数量:</span><span class='sj_0'
									style='display: inline-block; width: 151px;'>${event.sendednum}
									&nbsp;份</span> 
							</div>
							<div class="hd_2">
							<span class='yxq'>已消耗美贝:</span><span class='ygy'
									style='display: inline-block; width: 144px;'>${event.costamount}
									&nbsp;美贝</span> <span class='syzt'style="margin-left:75px">状态:</span><span class='ty'>${event.state.value}</span>
								<c:if test="${event.state=='NONE_FINISH'}">
									<%-- <a href="<c:url value="/wechatCampaign/continue_campaign.mbay?eventnumber=${event.eventnumber}"/>"><button>继续完善</button></a> --%>
								</c:if>
								<c:if
									test="${event.state=='NOT_STARTED' or event.state=='IN_ACTIVE'}">
									<!-- 编辑模式 
                      <a href="<c:url value="/wechatCampaign/to_review_template.mbay?eventnumber=${event.eventnumber}"/>"><button onclick="javascript:void(0)">编辑查看</button></a> 
                     -->
								</c:if>
								</div>
							<div class="hd_2">
								<span class='rq' style="display: none">单号码多次领取:</span>
								<!--开关设置-->
								<div class="slider-box off" style="display: none">
									<div class="slider"></div>
									<span class="m" style="margin-left: 16px;">是</span> <span
										class="w" style="color: #118EEF; margin-right: 17px;">否</span>
									<input type="checkbox" id="repeatEnable" name="repeatEnable"
										<c:if test="${event.repeatEnable}">checked="checked"</c:if> />
								</div>
								<!--提示-->
								<i class='tip_i' style="display: none">?
									<div class="tooltip" style="display: none;">
										一个号码是否可多次领取<em></em>
									</div>
								</i> <span class='bh' style="display: none">直接领取:</span>
								<!--开关设置-->
								<div class="slider-box off" style="display: none">
									<div class="slider"></div>
									<span class="m" style="margin-right: 16px;">是</span> <span
										class="w" style="color: #118EEF; margin-right: 17px;">否</span>
									<input type="checkbox" id="directEnable" name="directEnable"
										<c:if test="${event.directEnable}">checked="checked"</c:if> />
								</div>
								<!--提示-->
								<i class='tip_i' style="display: none">?
									<div class="tooltip" style="display: none;">
										是否可以直接领取<em></em>
									</div>
								</i> <span class='bh'>超出继续兑换:</span>
								<!--开关设置-->
								<div class="slider-box off">
									<div class="slider"></div>
									<span class="m" style="margin-left: 16px;">是</span> <span
										class="w" style="color: #118EEF; margin-right: 17px;">否</span>
									<input type="checkbox" id="continuable" name="continuable"
										<c:if test="${event.continuable}">checked="checked"</c:if> />
								</div>
								<!--提示-->
								<i class='tip_i'>?
									<div class="tooltip" style="display: none;">
										超出预期数量是否可继续兑换<em></em>
									</div>
								</i>
							</div>
							<%-- <div class="hd_2" style='line-height:0px;padding: 5px 0px 5px 20px;'>
                     <span class='rq' style='margin-left:15px'>活动连接:</span>
                     <span class='sj_0'><input  id='weburl' style='vertical-align: bottom;border:1px solid #C2C2C2;width:450px;background: #f3f3f3;height:23px' readonly="readonly" type='text' value='${url}'/>
                    <span> <button type='button' class='right_copy' value=''>复制</button></span>
                     </span>
                   </div>   --%>
							<div class='hd_3'>
								<input type="button" class="btn_confirm hdmxs" value="营销明细">
								<c:if test="${event.state!='OVER' and event.state!='CANCLED'}">
									<input type="button"  class='btn_confirm qxs' value="取消活动">
								</c:if>
							</div>
						</div>
						<!--表格-->
						<div class='table'>
							<table>
								<tr style="height:30px;">
									<th>地区</th>
									<th>运营商</th>
									<th>流量包</th>
									<th>美贝价格</th>
									<th>已送出</th>
								</tr>
								<c:forEach items="${event.stratetgylist}" var="strategy">
									<tr style="line-height:0px;">
										<td>${strategy.trafficPackage.operator.area.name }</td>
										<td>${strategy.trafficPackage.operator.type.getValue() }</td>
										<td>${strategy.trafficPackage.traffic }MB${strategy.trafficPackage.trafficType.getValue() }</td>
										<td><fmt:formatNumber
												value="${strategy.trafficPackage.mbayprice }"
												maxFractionDigits="2" minFractionDigits="2" />美贝</td>
										<td>${strategy.sendednum }个</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
