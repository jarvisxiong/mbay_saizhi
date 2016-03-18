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
<script type="text/javascript">
	function cancer(cid) {
		var url = ctx + '/campaign/' + cid + '/cancel.mbay';
		alert(url);
	}
	function detail(cid) {
		var url = ctx + '/campaign/redeem_detail.mbay?campaignId='+cid;
		window.location.href=url;
	}
</script>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width'>
				<!--左边-->
				<div class='left_con fl'>
					<div class='ckxq'>活动详情</div>
					<%@ include file="/common/leftcon.jsp"%>
					<!--右部内容-->
					<div class='fr xq_con'>
						<div class='tab_tit clearfix'>
							<ul>
								<li class="nav_list_now"><a href="javascript:void(0)">基本信息</a></li>
							</ul>
						</div>
						<div class='xq'>
							<div class='hd_1'>
								<input type="hidden" id="eventInactive"
									value="${event.state=='IN_ACTIVE'}" /> <span class='mc'
									style='margin-left: 15px;'>活动名称:</span><span class='gz'
									style='display: inline-block; width: 220px;'>${campaign.name}</span>
								<span class='mc'>红包金额:</span><span class='gz'
									style='display: inline-block; width: 170px;'>送${campaign.price}</span>
								<span class='bh'>活动编号:</span><span class='shuzi'>${campaign.number }</span>
							</div>
							<div class='hd_2'>
								<span class='rq' style='margin-left: 15px;'>活动日期:</span> <span
									class='sj_0' id="eventdate"> <fmt:formatDate
										pattern="yyyy-MM-dd" value="${campaign.startTime.toDate()}" />~<fmt:formatDate
										pattern="yyyy-MM-dd" value="${campaign.endTime.toDate()}" />
								</span> <span id="changedate" style="display: none"> <fmt:formatDate
										pattern="yyyy-MM-dd" value="${campaign.startTime.toDate()}" />~
									<input id="rq_1" name="starttime" readonly="readonly"
									value="<fmt:formatDate pattern="yyyy-MM-dd" value="${campaign.startTime.toDate()}"/>"
									type="hidden" /> <input id="rq_2" name="endtime"
									style="width: 68px;" class="Wdate" type="text"
									onclick="WdatePicker({el:'rq_2',minDate:'#F{$dp.$D(\'rq_1\')}'})" />
								</span> <span class='yxq'>创建日期:</span><span class='ygy'
									style='margin-left: 0px;'><fmt:formatDate
										pattern="yyyy-MM-dd HH:mm:ss"
										value="${campaign.dateCreated.toDate()}" /></span>
							</div>
							<div class='hd_2'>
								<span class='rq' style='margin-left: 15px;'>预计总量:</span><span
									class='sj_0' style='display: inline-block; width: 165px;'>${campaign.quantity}
									&nbsp;份</span> <span class='yxq'>锁定美贝:</span><span class='ygy'
									style='display: inline-block; width: 156px;'>${campaign.lockMbay}
									&nbsp;美贝</span></span> <span class='syzt'>消耗美贝:</span><span class='shuzi'>${campaign.costMbay }&nbsp;美贝</span>
							</div>
							<div class="hd_2">
								<span class='rq'>已发放数量:</span><span class='sj_0'
									style='display: inline-block; width: 151px;'>${campaign.deliverNum}
									&nbsp;份</span> <span class='yxq'>已兑换数量:</span><span class='ygy'
									style='display: inline-block; width: 144px;'>${campaign.redeemNum}
									&nbsp;份</span> <span class='syzt'>状态:</span><span class='ty'>${campaign.status.value}</span>
							</div>
							<div class="hd_2">
								<span class='rq'>单号码多次领取:</span> <span> <!--开关设置-->
									<div class="slider-box off">
										<div class="slider"></div>
										<span class="m">是</span> <span class="w">否</span> <input
											type="checkbox" id="repeatEnable" name="repeatEnable"
											<c:if test="${campaign.repeatGet}">checked="checked"</c:if> />
									</div> <!--提示--> <i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											同一个手机号是否可以多次领取红包！<em></em>
										</div>
								</i>
								</span> <span class='bh'>立即领取:</span>
								<!--开关设置-->
								<div class="slider-box off">
									<div class="slider"></div>
									<span class="m">是</span> <span class="w">否</span> <input
										type="checkbox" id="continuable" name="continuable"
										<c:if test="${campaign.getInTime}">checked="checked"</c:if> />
								</div>
								<!--提示-->
								<i class='tip_i'>?
									<div class="tooltip" style="display: none;">
										可以立即领取红包，不用存入钱包！<em></em>
									</div>
								</i>
							</div>
							<div class='hd_3'>
								<button class="hdmx" onclick="detail(${campaign.id})">营销明细</button>
								<c:if
									test="${campaign.status!='OVER' and campaign.status!='CANCLED'}">
									<button class='qx' onclick="cancer(${campaign.id})">取消活动</button>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
