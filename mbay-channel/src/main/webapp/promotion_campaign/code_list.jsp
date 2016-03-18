<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>兑换码列表</title>
<link href="${actx}/wro/${version}/code_list.css" rel="stylesheet"
	type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<%-- <script type="text/javascript" src="<c:url value="/js/wechatCampaign/activitylist.js" />"></script> --%>
<style>
#rq_get_1,#rq_get_2,#rq_redeem_1,#rq_redeem_2{
	  border: 1px solid #94ABC4;
	  width: 140px;
	  height: 26px;
	  background: #fff;
	  border-radius: 0px;
	  color: #000;
	  padding-left: 5px;
	  vertical-align: baseline;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$(".sel").val("${codeForm.codeStatus}");
	});
</script>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div>
					<form id="pagerForm"
						action="<c:url value="/promotionCampaign/rdeemcode_list.mbay"/>"
						method="post">
						<input type="hidden" name="eventNumber"
							value="${codeForm.eventNumber }" />
						<div class='hdlb'>促销神器 【${campaignName}】 - 兑换码列表</div>
						<%@ include file="/common/leftcon.jsp"%>
						<!--内容右部-->
						<div class='hd_con_r fr'>
							<div class='lb'>
								<div class='hd'>
									<span class='hdbh'>手机号：</span> <input type='text' name="mobile"
										value="${codeForm.mobile}" class='bh' /> <span class='hdbh'>兑换码：</span>
									<input type='text' name="code" value="${codeForm.code}"
										class='bh' /> <span class='hdbh'>状态：</span> <select
										class='sel' name="codeStatus">
										<option value="">未选择</option>
										<option value="UN_REDEEM">未兑换</option>
										<option value="GOT">已领取</option>
										<option value="REDEEMED">已兑换</option>
									</select>
									<!--  <span class='hdmc'>活动名称：</span><input type='text' name="eventname"class='mc'/>--->
									<br />
									<div style="height: 20px;"></div>
									<span class='hdmc'>领取时间：</span> <img
										src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq'
										onclick="WdatePicker()" /> <input id="rq_get_1" name="cgetstarttime"
										class="Wdate" value='${getstarttime}' type="text"
										onFocus="var rq_get_2=$dp.$('rq_get_2');WdatePicker({onpicked:function(){rq_get_2.focus();},maxDate:'#F{$dp.$D(\'rq_get_2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq' onclick="WdatePicker()" /> <input id="rq_get_2"
										name="cgetendtime" value='${getendtime}' class="Wdate"
										type="text" style="margin-right: 20px;"
										onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_get_1\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									<br/>
									<div style="margin-top:10px;"></div>
									<span class='hdmc'>兑换时间：</span> <img
										src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq'
										onclick="WdatePicker()" /> <input id="rq_redeem_1" name="credeemstarttime"
										class="Wdate" value='${redeemstarttime}' type="text"
										onFocus="var rq_redeem_2=$dp.$('rq_redeem_2');WdatePicker({onpicked:function(){rq_redeem_2.focus();},maxDate:'#F{$dp.$D(\'rq_redeem_2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq' onclick="WdatePicker()" /> <input id="rq_redeem_2"
										name="credeemendtime" value='${redeemendtime}' class="Wdate"
										type="text"
										onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_redeem_1\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									<br />
									<div style="height: 20px;"></div>
									<span class='hdbh'>核销码：</span> <input type='text' name="verificateCode"
										value="${codeForm.verificateCode}" class='bh' />
									<span class='hdmc'>生成时间：</span> <img
										src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq'
										onclick="WdatePicker()" /> <input id="rq_1" name="cstarttime"
										class="Wdate" value='${codeForm.starttime}' type="text"
										onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq' onclick="WdatePicker()" /> <input id="rq_2"
										name="cendtime" value='${codeForm.endtime}' class="Wdate"
										type="text"
										onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})" /> <input
										type="submit" style="margin-left: 30px;"
										class='hd_btn btn_confirm' value='查询' />
								</div>
							</div>
							<!--表格-->
							<div class='table'>
								<table>
									<tr>
										<td width="20%">兑换码|生成时间</td>
										<td width="15%">有效期</td>
										<td width="15%">领取时间</td>
										<td width="15%">兑换时间</td>
										<td width="10%">状态</td>
										<td width="25%">兑换单号|手机号|核销码</td>
									</tr>

									<c:forEach items="${codelist}" var="code">
										<tr>
											<td>
												${code.code}<br/>
												<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
														value="${code.createtime.toDate()}" />
											</td>
											<td><fmt:formatDate pattern="yyyy-MM-dd"
													value="${code.starttime.toDate()}" /><br/><fmt:formatDate
													pattern="yyyy-MM-dd" value="${code.expiretime.toDate()}" /></td>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${code.gettime.toDate()}" /></td>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${code.redeemtime.toDate()}" /></td>
											<td>${code.codeStatus.value}</td>
											<td>
												${code.ordernumber}<br/>
												${code.bindMobile}<br/>
												${code.verificateCode}
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<m:page pageinfo="${pageinfo}" />
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>