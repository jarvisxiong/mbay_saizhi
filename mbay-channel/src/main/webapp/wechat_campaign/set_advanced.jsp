<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开发者模式</title>
<t:assets />
<link href="<c:url value="/css/manage/developmodel.css"/> "
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript"
	src="${actx}/js/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript"
	src="${actx}/js/inside_right_part/common_js.js"></script>
<script type="text/javascript"
	src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script type="text/javascript">
$(function(){	

$("#ms").Validform({
    showAllError:true,
	tiptype:3,
	ajaxPost:true,
	datatype:{
		"rightsz" : /^[a-zA-Z0-9]{3,32}$/
	},
	callback:function(form){
		if(form){
    		$.messager.alert("提示", "操作成功!");
    	}else{
    		$.messager.alert("提示", "操作失败!");
    	}
	}
});
$(".right_copy").zclip({
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

</head>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div class='left_con fl'>
				<div class='ddqr'>微信伴侣 【${campaignName}】 - 开发者中心</div>
				<%@ include file="advanced_info.jsp"%>
				<%@ include file="/common/leftcon.jsp"%>
			</div>
			<!--尾部-->
		</div>
	</div>
</div>
</body>
</html>