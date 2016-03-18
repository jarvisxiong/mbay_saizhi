<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信伴侣-活动详情-活动模板</title>
<t:assets />
<link href="<c:url value="/css/manage/strategyinfo.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/switch.css"/> " rel="stylesheet" type="text/css" />
<link href="${actx}/css/wechat_campaign/set_template.css" rel="stylesheet" type="text/css" />
<link href="${actx}/css/wechat_campaign/advanced.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/js/tipswindown.js"></script>
<script type="text/javascript" src="${actx}/js/strategyinfo.js"></script>
<%-- <script type="text/javascript"	src="${actx}/js/inside_right_part/common_js.js?v=1.2"></script>	 --%>
<script type="text/javascript" src="${actx}/js/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/switch.js"></script>
<script type="text/javascript" src="${actx}/js/layer/layer.min.js"></script>
<script>
$(function(){
	$("#eventButton").zclip({
		path : '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
		copy : function() {
			return $("#eventurl").val();
		}
	});
	//页面来源标识，如果是从活动详情过来则标识为1
	$("input[name=info]").val(1);
});

function generateQrCode(domId){
	$.layer({
	    type: 1,
	    title: false,
	    area: ['auto', 'auto'],
	    border: [0], //去掉默认边框
	    shade: [0.5, '#000'], //遮罩
	    shadeClose: true, //用来控制点击遮罩区域是否关闭层
	    closeBtn: [0, true], //去掉默认关闭按钮
	    page: {
	        dom: '#'+domId
	    }
	});
}
</script>
<c:if test="${campaignStatus=='OVER'||campaignStatus=='CANCLED' }">
	<script>
$(function(){
	$(".js_oper_ctl").css("background","#ccc");
	$(".js_oper_ctl").removeAttr("onclick");
	
});
</script>
</c:if>
<c:if test="${campaignStatus=='NONE_FINISH'}">
	<script>
$(function(){
	$(".js_qcode").css("background","#ccc");
	$(".js_qcode").removeAttr("onclick");
	
});
</script>
</c:if>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width'>
				<!--左边-->
				<div class='ckxq'>微信伴侣 【${campaignName}】 - 活动详情</div>
				<%@ include file="/common/leftcon.jsp"%>
				<!--右部内容-->
				<div class='fr xq_con'>
					<div class='tab_tit clearfix'>
						<ul>
							<li><a
								href="<c:url value='/wechatCampaign/campaign_info.mbay?eventnumber=${decode_campaignNumber}'/>">基本信息</a></li>
							<li class="nav_list_now"><a href="javascript:void(0)">活动模板</a></li>
							<li><a
								href="<c:url value='/wechatCampaign/to_campaign_info_advanced.mbay?campaignNumber=${decode_campaignNumber}'/>">开发者中心</a></li>
						</ul>
					</div>
					<div class="hd_2"
						style='padding-left: 0px; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px dotted #C2C2C2;'>
						<span class='rq' style='margin-left: 15px'>活动链接:</span> <span
							class='sj_0'> <input id='eventurl'
							style='border: 1px solid #C2C2C2; width: 535px; background: #f3f3f3; vertical-align: middle; height: 23px'
							readonly="readonly" type='text'
							value='<c:if test="${reviewEnable}">${campaignTempate.eventurl}</c:if>' />
							<span> <input id='eventButton' type='button'
								class='right_copy js_oper_ctl js_qcode btn_confirm'
								style='width: 86px; vertical-align: middle; margin-left: 10px;'
								value="复制"> <input id="campaignQrCodeButton"
								type='button'
								class='right_copy js_oper_ctl js_qcode btn_confirm'
								style='width: 100px; vertical-align: middle; margin-left: 10px;'
								onclick='generateQrCode("getQrCodeImg")' value="生成二维码">
								<img id='getQrCodeImg' border="0" alt="领取页面二维码" title='领取页面二维码'
								src="${ctx}/qrcode/generateQrCode.mbay?url=<c:if test="${reviewEnable}">${campaignTempate.eventurl}</c:if>"
								style='display: none;' />
						</span>
						</span>
					</div>
					<%@ include file="template_info.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>