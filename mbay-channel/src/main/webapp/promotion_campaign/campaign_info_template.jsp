<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>促销神器-活动详情-活动模板</title>
<t:assets />
<link href="${actx}/wro/${version}/campaign_infoes.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/campaign_infoes.js"></script>
<script type="text/javascript"
	src="<c:url value="/js/zclip/jquery.zclip.min.js" />"></script>
<script>
$(function(){
	$("#redeemButton").zclip({
		path : '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
		copy : function() {
			return $("#redeemurl").val();
		}
	});
	
	$("#webButton").zclip({
		path : '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
		copy : function() {
			return $("#weburl").val();
		}
	});
	//页面来源标识，如果是从活动详情过来则标识为1
	$("input[name=info]").val(1);
});
</script>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width' >
				<!--左边-->
				<div class='ckxq'>促销神器 【${campaignName}】 - 活动详情</div>
				<%@ include file="/common/leftcon.jsp"%>
				<!--右部内容-->
				<div class='fr xq_con'>
					<div class='tab_tit clearfix'>
						<ul>
							<li><a href="<c:url value='/promotionCampaign/campaign_info.mbay?eventnumber=${decode_campaignNumber}'/>">基本信息</a></li>
							<li class="nav_list_now"><a href="javascript:void(0)">活动模板</a></li>
							<li><a href="<c:url value='/promotionCampaign/to_campaign_info_advanced.mbay?campaignNumber=${decode_campaignNumber}'/>">开发者中心</a></li>
						</ul>
					</div>
					<p style="border-left:1px solid #CCC;border-right:1px solid #CCC;height:15px;margin-top:-20px;"></p>
					<%-- 
				<div class="hd_2" style='padding-left: 0px;padding-bottom:10px;border-bottom: 0px'>
					<span class='rq' style='margin-left: 15px'>领取链接:</span>
					<span class='sj_0'>
						<input id='redeemurl' style='border: 1px solid #C2C2C2; width:635px; background: #f3f3f3; vertical-align: middle;height: 23px'
							readonly="readonly" type='text' value='<c:if test="${reviewEnable}">${campaignTempate.eventurl}</c:if>' />
						<span>
							<button id='redeemButton' type='button' class='right_copy' style='width: 86px;vertical-align: middle;margin-left: 10px;'>复制</button>
						</span>
					</span>
				</div>
				<div class="hd_2" style='padding-left: 0px;margin-bottom:20px;padding-bottom:10px;border-bottom: 1px dotted #C2C2C2;'>
					<span class='rq' style='margin-left: 15px'>兑换链接:</span>
					<span class='sj_0'>
						<input id='redeemurl' style='border: 1px solid #C2C2C2; width:635px; background: #f3f3f3; vertical-align: middle;height: 23px'
							readonly="readonly" type='text' value='<c:if test="${reviewEnable}">${campaignTempate.redeemurl}</c:if>' />
						<span>
							<button id='redeemButton' type='button' class='right_copy' style='width: 86px;vertical-align: middle;margin-left: 10px;'>复制</button>
						</span>
					</span>
				</div> 
				--%>
					<%@ include file="template_info.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>