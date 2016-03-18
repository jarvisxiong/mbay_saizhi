<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function() {
	$("#mx").bind("click", function() {
		window.location.href = '<c:url value="/traffic/record.mbay"/>';
	});
	$(".js_right_purchase").on("click",function() {
		window.location.href = '<c:url value="/smspurchase/renderSMSPurchase.mbay"/>';
	});
	$(".js_right_show").on("click",function() {
		window.location.href = '<c:url value="/smspurchase/sMSPurchaseRecord.mbay?pagenum=1"/>';
	});
});

function toPortrait(){
	window.location.href= ctx + "/certificate/toPortrait.mbay";
}
</script>
<style>
.portrait_img{float:right;width:37%;}
.portrait_img>img{width:80%;margin-top:10px; cursor:pointer;border: 1px solid #CCC;margin-left:5px;}
.zh_bh{}
</style>
<div class='right_con fr'>
	<div class='r_sec' style="position:relative;">
		<div class='r_sec_tit zhxx'>账户信息</div>
		<div class='zh'>
			<div style="width:57%;position:absolute;">
				<div class='zhbh'>
					<span class='zh_wz'>账户编号：</span><span class="zh_bh">${sessionScope.channel.userNumber}</span>
				</div>
	
				<div class='zhlx'>
					<span class='zh_wz'>账户类型：</span><span>${sessionScope.channel.property.value}账户</span>
				</div>
				<div>
					<span class='zh_wz'>实名认证：</span><span> <c:choose>
							<c:when test="${user.certStatus == 'UNAPPLY'}">
								<a target="_blank"
									href="<c:url value="/certificate/auth_result.mbay"/>">${user.certStatus.value}</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value="/certificate/auth_result.mbay"/>">${user.certStatus.value}</a>
							</c:otherwise>
						</c:choose>
					</span>
				</div>
			</div>
			<div class="portrait_img">
				<c:if test="${portrait eq ''}">
					<img src="${actx}/images/jcrop/portrait_default.jpg" onclick="toPortrait();" title="编辑头像"/>
				</c:if>
				<c:if test="${portrait ne ''}">
					<img src="${portrait}" onclick="toPortrait();" title="编辑头像"/>
				</c:if>
			</div>
		</div>
		<div class='mb_cx'>
			<div class='mbye'>
				<span class='zh_wz'>美贝余额：</span><span class='mb_qb'><fmt:formatNumber
						value="${useraccount.amount}" maxFractionDigits="2"
						minFractionDigits="2" /></span><span class='zh_wz'>美贝</span>
			</div>
			<div class='xyed'>
				<span class='zh_wz'>信用额度：</span><span class='mb_qb'> <fmt:formatNumber
						value="${useraccount.creditLimit}" maxFractionDigits="2"
						minFractionDigits="2" /></span><span class='zh_wz'>美贝</span>
			</div>
			<div class='yhmb'>
				<span class='zh_wz'>锁定额度：</span><span class='mb_qb'><fmt:formatNumber
						value="${useraccount.lockedamount}" maxFractionDigits="2"
						minFractionDigits="2" /></span><span class='zh_wz'>美贝</span>
			</div>
		</div>
		<c:if test="${sessionScope.channel.certStatus != 'APPROVED'}">
			<div class='btn_1'>
				<input type='button' data-uk-modal="{target:'#modal'}" class='sz'
					value='流量充值' /> <input type='button'
					data-uk-modal="{target:'#modal'}" class='cz' value='美贝充值' />
			</div>
		</c:if>
		<c:if test="${sessionScope.channel.certStatus == 'APPROVED'}">
			<div class='btn_1'>
				<input type='button'
					onclick="window.location.href='<c:url value="/traffic/purchase.mbay"/>'"
					class='sz' value='流量充值' /><input type='button'
					onclick="window.location.href='<c:url value="/account/depositPreprocess.mbay"/>'"
					class='cz cert_intercept' value='美贝充值' />
			</div>
		</c:if>

	</div>
	<div class='r_sec'>
		<div class='r_sec_tit yxxx'>营销信息</div>
		<div class='dl'>
			<span class='clzt'>营销中：</span> <span class='zt_wz'> <a
				href='<c:url value="/eventStatistics/event_statistics.mbay"/>'>
					<span class='zt_z'>${usercontext.event_inuse}</span>
			</a> <!-- <a href='#'><span>已过期</span></a>-->
			</span> <a href='<c:url value="/eventStatistics/event_statistics.mbay"/>'
				style="float: right;"><input type='button' class='sz check'
				value='查看' style='margin-left: 16px;' /></a>
		</div>
		<div style="text-align: center;">
			<input type='button' id="mx" class='mx' value='营销明细 '
				style="width: 254px" />
		</div>
	</div>
	<div class='r_sec'>
		<div class='r_sec_tit dlxx'>代理信息</div>
		<div class='dl'>
			<span>下级代理：</span> <span class='dl_sl'>${usercontext.subagent }</span>
			<%-- <c:if test="${usercontext.subagent!=0 }"> --%>
			<a href='<c:url value="/channel/sub_agents.mbay"/>'> <input
				type='button' class="js_right_show sms-purchase mx" value='查看'
				style='margin-left: 64px;' />
			</a>
			<%-- </c:if> --%>
		</div>
		<!-- 
		<div class='dl'>
			<span>短信条数：</span> <span class='dl_sl'>${usercontext.smscount}</span> <span class="js_right_purchase sms-purchase">购买短信</span> <span
				class="js_right_show sms-purchase">查看</span>
		</div>
		 -->
	</div>
</div>
