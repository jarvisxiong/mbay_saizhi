<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="${actx}/wro/${version}/leftcon.css" rel="stylesheet"
	type="text/css" />
<script src='${actx}/js/left_nav.js'></script>
<style>
.gn_icon {
  padding-left: 32px;
  position: relative;
}
.sj {

  right: 11px;

}
</style>
<div class='l_nav fl'>
	<div class='l_nav_con'>
		<div class='gn_box clearfix'>
			<h5 class='gn_icon jc_icon'>
				基础功能
				<p class='sj'></p>
			</h5>
			<div class='clearfix' style="text-align:center">
				<a class="cert_intercept" href='<c:url value="/account/transfer/payment/fill.mbay"/>'><p>美贝转账</p></a>
				<a class="cert_intercept" href='<c:url value="/traffic/purchase.mbay"/>'><p>流量柜台</p></a> 
				<a class="cert_intercept" href='<c:url value="/account/depositPreprocess.mbay"/>'><p>充值美贝</p></a>
				<a class="cert_intercept" href='<c:url value="/mall/exchangeItem/list.mbay"/>'><p>商城管理</p></a>
			</div>
		</div>
		<div class='gn_box'>
			<h5 class='gn_icon yx_icon'>
				营销功能
				<p class='sj'></p>
			</h5>
			<div class='clearfix' style="text-align:center">
				<!-- <a href='<c:url value="/wechatCampaign/workbench.mbay"/>' class='cert_intercept'><p>微信伴侣</p></a> -->
				<a href='${ctx }/traffic_red/index.mbay' class='cert_intercept'><p>流量红包</p></a>
				<a href='<c:url value="/promotionCampaign/workbench.mbay"/>' class='cert_intercept '><p>促销神器</p></a> 
				<%-- <a href='${ctx }/o2o/index.mbay' class='cert_intercept fl'><p>O2O红包</p></a>
				<a href='javascript:$.messager.alert({ content: "功能完善中,敬请期待！" })' class='fr'><p>加粉工具</p></a>
				<a href='javascript:$.messager.alert({ content: "功能完善中,敬请期待！" })' class='fl'><p>会员互动</p></a> --%>
				<a href='<c:url value="/customerserver/servermanager.mbay"/>' class='cert_intercept'><p>客户关怀</p></a> 
				
				<a href='<c:url value="/app_temptation/workbench.mbay"/>' class=''><p>APP诱惑</p></a>
				<!-- <a href='javascript:$.messager.alert({ content: "功能完善中,敬请期待！" })' class='fl'><p>广告推送</p></a>
				<a href='javascript:$.messager.alert({ content: "功能完善中,敬请期待！" })' class='fr'><p class='sp'>积分互兑</p></a>  -->
			</div>
		</div>
	</div>
</div>



