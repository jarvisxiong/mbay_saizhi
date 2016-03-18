<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="<c:url value="/css/app_temptation/developmodel.css"/> " rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/js/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript"	src="${actx}/js/inside_right_part/common_js.js"></script>	
<script type="text/javascript" src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script type="text/javascript">
$(function(){	
	$("#ms").Validform({
	    showAllError:true,
		tiptype:4,
		ajaxPost:true,
		datatype:{
			"rightsz" : /^[a-zA-Z0-9]{3,32}$/
		},
		callback:function(form){
			if(form){
	    		$.messager.alert({ content: "操作成功!" });
	    	}else{
	    		$.messager.alert({ content: "操作失败!" });
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
	$.fileDownload(actx+'/filedownload/files/app_temptation_api_doc.mbay', {
	    failCallback: function (html, url) {
	    	$.messager.alert({ content: "下载失败！" });
	    }
	});
}
</script>

<div class='dd fr'>
   <div class='left_sec fl'>
      <div class='left_sec_u'>
        <img src='<c:url value="/images/workimages/kfzms.jpg"/>'/>
      </div>
      <div class='left_sec_d'>
                                                                      开发者模式
      </div>
   </div>
   <div class='right_sec fr'>
     <form id='ms' action='<c:url value="/app_temptation/set_advanced.mbay"/>' method="post">
      <input type="hidden" name="campaignNumber" value="${campaignNumber}" />
       <div class='right_tip'>接口地址是开发者用来请求流量充值的接口URL。Token可由开发者任意填写，用作生成签名。查看接口接入API<a href="javascript:download();">《开发者模式-接口文档V1.0.doc》</a></div>
       <div class='right_url'><span class='right_url_0'>接口地址:</span>
       	<span><input id="url" class='right_url_1' disabled="disabled" type='text' style="background-color: white;" value='http://www.mbpartner.cn/mbaychannel/api/app_temptation/advanced.mbay' /> </span>
       	<span><input type='button' class='right_copy' value='复制'/></span>
       	<c:if test="${isAddReq != 'yes' }">
       		<a class='tj record-search' href="<c:url value='/app_temptation/traffic_record.mbay?campaignNumber=${campaignNumber}' />">充值记录查询</a>
       	</c:if>
       </div>
       <div class='right_token'><span class='right_token_0'>token:</span>
       <input type='text' name="token" value="${token}" class='right_token_1' datatype="rightsz" errormsg="输入格式不正确！"/><span class="Validform_checktip">长度为3-32个数字或字母</span></div>
       <div class='right_an'><input type="submit" value="提交" class='tj'/></div>
     </form>
   </div>
</div>