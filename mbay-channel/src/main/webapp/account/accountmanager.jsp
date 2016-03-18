<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户管理</title>
<link href="${actx}/wro/${version}/accountmanager.css"	rel="stylesheet" type="text/css" />
</head>
<body>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<div class='left_con'>
				<div class='ddqr'>账户管理</div>
				<%@ include file="/common/leftcon.jsp"%>
				<div class='dd fr'>
					<div>
						<span class='mc xx'>账户信息</span>
					</div>
					<div>
						<div class='zhye_1'>
							<span class='mc'>账户余额：</span> <span class='shu'><fmt:formatNumber
									value="${useraccount.amount}" maxFractionDigits="2"
									minFractionDigits="2" /></span> <span class='jie'>美贝</span> 
									<!-- 
							<a class="cert_intercept"
								href='<c:url value='/record/mbayDepositOrderRecord.mbay?pagenum=1'/>'>充值记录</a>
									 -->
						</div>
						<div class='zhye_2'>
							<a class="cert_intercept"
								href="<c:url value='/record/accountdetail.mbay'/>">收支明细</a>|<a class="cert_intercept"
								href='<c:url value="/account/depositPreprocess.mbay"/>'>充值美贝</a>|<a
								class="cert_intercept"
								href='<c:url value="/account/transfer/payment/fill.mbay"/>'>转账</a>
						</div>
					</div>
					<div>
						<span class='sded_0'>锁定额度：</span> <span class='shu'><fmt:formatNumber
								value="${useraccount.lockedamount}" maxFractionDigits="2"
								minFractionDigits="2" /></span> <span class='jie'>美贝</span>
					</div>
				</div>
				<div class='dd fr'>
					<div>
						<span class='mc xx'>美贝流量</span>
					</div>
					<div>
						<div class='zhye_1'>
							<span class='mc'>
							账户余额：</span> <span class='shu'><fmt:formatNumber
									value="${redtraffic.traffic}" maxFractionDigits="2"
									minFractionDigits="2" /></span> <span class='jie'>美贝
							 </span> 
						</div>
						<div class='zhye_2'>
						
							 <a class="cert_intercept"
								href="<c:url value='/account/redtraffic/tradedetail.mbay'/>">收支明细</a>|<a
								class="cert_intercept"
								href='<c:url value="/account/redtraffic/transfer/index.mbay"/>'>转账</a>|
								<a class="cert_intercept" href='${ctx}/account/redtraffic/mbay_transfer_in_red/ui.mbay'>转入</a>
						</div>
					</div>
					<div>
						<span class='sded_0'>锁定额度：</span> <span class='shu'><fmt:formatNumber
								value="${redtraffic.lockedTraffic}" maxFractionDigits="2"
								minFractionDigits="2" /></span> <span class='jie'>美贝</span>
					</div>
				</div>
				<div class='dd fr'>
					<div>
						<span class='mc xx'>MB流量</span>
					</div>
					<div>
						<div class='zhye_1'>
							<span class='mc'>流量余额：</span> <span class='shu'><fmt:formatNumber
									value="${mbaytraffic.traffic}" maxFractionDigits="2"
									minFractionDigits="2" /></span> <span class='jie'>MB</span> 
						</div>
						<div class='zhye_2'>
						 <a class="cert_intercept"
								href="<c:url value='/account/mbaytraffic/tradedetail.mbay'/>">收支明细</a>|<a
								class="cert_intercept"
								href='<c:url value="/account/mbaytraffic/transfer/index.mbay"/>'>转账</a>
						</div>
					</div>
					<div>
						<span class='sded_0'>锁定额度：</span> <span class='shu'><fmt:formatNumber
								value="${mbaytraffic.lockedTraffic}" maxFractionDigits="2"
								minFractionDigits="2" /></span> <span class='jie'>MB</span>
					</div>
				</div>				
				<div class='dd fr'>
					<div>
						<span class='mc xx'>用户信息</span>
					</div>
					<div>
						<span class='mc'>账户编号：</span> <span class='shu'>${user.usernumber}</span>
					</div>
					<div>
						<span class='mc'>激活时间：</span> <span class='shu'><fmt:formatDate
								pattern="yyyy-MM-dd HH:mm:ss" value="${user.createtime}" /></span>
					</div>
					<div>
						<span class='mc'>上级编号：</span>
						  <span class='shu'>${user.supnumber}
						       <!--<a class="cert_intercept" href='<c:url value="/channel/toChangeSupnumber.mbay"/>'class='xg'>&nbsp;修改</a>-->
						  </span>
					</div>
					<div>
						<span class='mc'>修改密码：</span> <span class='shu'>******&nbsp;<a class="cert_intercept"
							href='<c:url value="/channel/toChangepwd.mbay"/>' class='xg'>修改</a></span>
					</div>
					<div>
						<span class='mc'>提醒设置：</span> <span class='shu'>美贝余额提醒&nbsp;<a class="cert_intercept"
							href='<c:url value="/channel/set_remind.mbay"/>' class='xg'>设置</a></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
