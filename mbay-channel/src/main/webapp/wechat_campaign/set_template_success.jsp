<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑者模式信息</title>
<link href="${actx}/wro/${version}/set_template_success.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/set_template_success.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"
	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript"
	src="${actx}/js/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript" src="${actx}/js/layer/layer.min.js"></script>
<script type="text/javascript">
$(function(){	
	$("#copyButton").zclip({
	    path: '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
	    copy: function () {
	        return $("#weburl").val();
	    }
	});
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
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='ddqr'>微信伴侣 【${campaignName}】- 编辑者模式信息</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='dd fr'>
						<div class='left_sec fl'>
							<div class='left_sec_u'>
								<img src='<c:url value="/images/workimages/kfzms.jpg"/>' />
							</div>
							<div class='left_sec_d'>编辑者模式</div>
						</div>
						<div class='right_sec fl'>
							<div class='right_tip'>请将以下模板地址嵌入到您的微信账户中</div>
							<div class='right_url'>
								<span class='right_url_0'>模板地址:</span> <span> <input
									class='right_url_1' id='weburl' disabled="disabled" type='text'
									style="background-color: white;" value='${model.eventurl}' />
								</span> <span> <input id='copyButton' type='button'
									class='right_copy btn_confirm' value='复制' /> <input
									id="campaignQrCodeButton" type='button'
									class='right_copy btn_confirm' value='生成二维码'
									style='width: 100px;' onclick='generateQrCode("getQrCodeImg")' />
									<img id='getQrCodeImg' border="0" alt="领取页面二维码" title='领取页面二维码'
									src="${ctx}/qrcode/generateQrCode.mbay?url=${model.eventurl}"
									style='display: none;' />
								</span>
							</div>
						</div>
					</div>
				</div>
				<!--尾部-->
			</div>
		</div>
	</div>
</body>
</html>