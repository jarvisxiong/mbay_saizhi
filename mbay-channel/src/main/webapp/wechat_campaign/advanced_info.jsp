<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<link href="<c:url value="/css/wechat_campaign/developmodel.css"/> "
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<c:url value="/js/lib/Validform_v5.3.2_min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/js/zclip/jquery.zclip.min.js" />"></script>
<script type="text/javascript"
	src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script type="text/javascript">
$(function(){	
	//如果是已取消和已结束，则不让点击按钮
	if("OVER" == "${status}" || "CANCLED" == "${status}"){
 		$(".js_oper_ctl").removeAttr("onclick");
 		$(".js_oper_ctl").css("background","#CCC");
 	}
	
	$("#ms").Validform({
	    showAllError:true,
		tiptype:4,
		ajaxPost:true,
		datatype:{
			"rightsz" : /^[a-zA-Z0-9]{3,32}$/
		},
		callback:function(form){
			$.messager.alert("提示", form.message);
		}
	});
	$(".right_copys").zclip({
	    path: '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
	    copy: function () {
	        return $("#url").val();
	    }
	});
});

function download(){
	$.fileDownload(actx+'/filedownload/files/develop_URL.mbay', {
	    failCallback: function (html, url) {
	    	$.messager.alert("提示","下载失败！");
	    }
	});	
}
</script>
<div class='dd fr'>
	<div class='left_sec fl'>
		<div class='left_sec_u'>
			<img src='<c:url value="/images/workimages/kfzms.jpg"/>' />
		</div>
		<div class='left_sec_d'>开发者模式</div>

	</div>
	<div class='right_sec fr'>
		<form id='ms'
			action='<c:url value="/wechatCampaign/set_advanced.mbay"/>'
			method="post">
			<input type="hidden" name="campaignNumber" value="${campaignNumber}" />
			<div class='right_tip'>
				接口地址是开发者用来请求流量充值的接口URL。Token可由开发者任意填写，用作生成签名。查看<a
					href="javascript:download();">接口接入API</a>
			</div>
			<div class='right_url'>
				<span class='right_url_0'>接口地址:</span> <span><input id="url"
					class='right_url_1' disabled="disabled" type='text'
					style="background-color: white;"
					value='http://www.mbpartner.cn/mbaychannel/api/wechart/advanced.mbay' />
				</span><span><input type='button' class='right_copys btn_service'
					value='复制' /></span>
			</div>
			<div class='right_token'>
				<span class='right_token_0'>token:</span> <input type='text'
					name="token" value="${token}" class='right_token_1'
					datatype="rightsz" errormsg="输入格式不正确！" maxlength="32" /><span
					class="Validform_checktip">长度为3-32个字符！</span>
			</div>
			<div class='right_an'>
				<input type="button" value="提交" onclick="$('#ms').submit()"
					class='tj js_oper_ctl' />
			</div>
		</form>
	</div>
</div>