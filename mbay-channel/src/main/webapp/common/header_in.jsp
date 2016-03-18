<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/top.jsp"%>
<%@ include file="/common/advert_in.jsp"%>
<!-- 在线客服样式  css + js -->
<link href="${actx}/wro/${version}/header_in.css" rel="stylesheet"
	type="text/css" />
<link href="${actx}/wro/${version}/web_common.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/widget/jquery.Sonline.js"></script>
<script type="text/javascript"
	src="${actx}/js/inside_right_part/common_js.js"></script>
<!--分割线-->
<div class='hr_40'></div>
<!--导航栏-->
<div class='nav'>
	<div class='nav_list com_width'>
		<div class='nav_con com_width'>
			<ul class="fl">
				<li><a href="<c:url value="/channel/index.mbay"/>">网站首页</a></li>
				<li><a href="<c:url value="/channel/workbench.mbay"/>">工作台</a></li>
				<li><a class="cert_intercept"
					href="<c:url value="/eventStatistics/event_statistics.mbay"/>">活动管理</a></li>
				<li><a href="<c:url value="/account/accountset.mbay"/>">账户管理</a></li>
				<c:if test="${sessionScope.channel.property == 'ENTERPRISE'}">
					<li><a class="cert_intercept"
						href="<c:url value="/trafficSign/signManage.mbay"/>">接口管理</a></li>
				</c:if>
				<li><a class="cert_intercept"
					href="<c:url value="/account/transfer/payment/fill.mbay"/>">美贝转账</a></li>
			</ul>
			<div class="fanhui fl">
				<a id="back" href="javascript:history.go(-1); ">返回</a>
			</div>
		</div>
	</div>
</div>
<!--分割线-->


<div class='hr_6'></div>
<script type="text/javascript" src="<c:url value="/js/btnshow.js" />"></script>
<script type="text/javascript">
	$(function() {
		if (navigator.userAgent.toLowerCase().indexOf("chrome") >= 0) {
			$(window).load(function() {
				$('input:-webkit-autofill').each(function() {
					var text = $(this).val();
					var name = $(this).attr('name');
					$(this).after(this.outerHTML).remove();
					$('input[name=' + name + ']').val(text);
				});
			});
		}
		$('.login_bg_c .sel img').hover(function() {
			$(this).css('opacity', 0.8);
			$(this).css('cursor', 'pointer');
		}, function() {
			$(this).css('opacity', 1);
		});
	});
</script>