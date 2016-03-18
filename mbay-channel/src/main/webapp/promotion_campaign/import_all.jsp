<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量导入兑换码</title>
<t:assets />
<link href="${actx}/wro/${version}/promotion_campaign_add.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script>
$(function(){
	if("${message}" != ""){
		$.messager.alert({content:"${message}"});
	}
	
	$("#download_date").bind("click",function(){		
		$.fileDownload(actx+'/filedownload/files/redeem_code_import_date.mbay', {
		    failCallback: function (html, url) {
		    	$.messager.alert("提示","下载失败！");
		    }
		});	
	});
	
	$("#download_hour").bind("click",function(){		
		$.fileDownload(actx+'/filedownload/files/redeem_code_import_hour.mbay', {
		    failCallback: function (html, url) {
		    	$.messager.alert("提示","下载失败！");
		    }
		});	
	});
	
	/* 验证 */
	$("#hd").Validform({
		showAllError : true,
		tiptype : 3,
		datatype : {
			"sz" : /^[0-9]+$/,
			"rq" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
			"excel" : function(gets,obj,curform,regxp){
			    var FileExt = gets.substr(gets.lastIndexOf('.')+1);
			    var _validFileExtensions = ["xls","xlsx"];
			    var validimg=false;
			    for(var i=0;i<_validFileExtensions.length;i++){
			    	var exten=_validFileExtensions[i].toLowerCase();
			    	if(exten==FileExt){
			    		validimg=true;
			    		break;
			    	}
			    }
			    if(!validimg){
			    	return false;
			    }
			    return true;
			},
		}
	});
	
	$('input[type=radio][name="endType"]').change(function() {
		if (this.value == 'DATE') {
			$("#download_date").show();
			$("#download_hour").hide();
		} else if (this.value == 'HOUR') {
			$("#download_date").hide();
			$("#download_hour").show();
		}
	});
});

function linkTo(){
	var value = $("input[name=eventnumber]").val();
	window.location.href = ctx + "/promotionCampaign/campaign_info.mbay?eventnumber=" + value;
}
</script>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='tjhd'>促销神器 【${campaignName}】 - 批量导入兑换码</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='hd fr' style='position: relative'>
						<form id="hd" action="<c:url value="/promotionCampaign/importAll.mbay"/>" method="post" enctype="multipart/form-data">
							<input type="hidden" name="eventnumber" value="${eventnumber}"/>
							<m:token />
							<div class='xz_1' style='margin-top: 20px;'>
								<span><b>*</b>活动编号:</span>${eventnumber}
							</div>
							<div>
								<span><b>*</b>类别:</span> 
								<input type="radio" class='hdlb' datatype="*" errormsg="请选择类别！" id="time_date"
									name="endType" checked="checked" value="DATE" /> 
								<label for="time_date">截止日期</label> 
								<span style="width:20px;"></span>
								<input type="radio" class='hdlb' id="time_hour" name="endType" value="HOUR" /> 
								<label for="time_hour" class='ll'>截止小时</label> 
								<span class="Validform_checktip"></span>
							</div>
							<div class='xz_1'>
								<span><b>*</b>导入兑换码:</span> 
								<input type="file" name="file" datatype="excel" errormsg="请选择正确的txt文件" class='scwj' style="width: 180px;"/>
								<button id='download_date' type='button' class='btn_confirm'>下载示例文件</button>
								<button id='download_hour' type='button' class='btn_confirm' style="display:none;">下载示例文件</button>
								<span class="Validform_checktip" style='width: 180px'></span>
							</div>
							<div class='bj_an'>
								<input type="submit" value='提交' class='bjms_1 btn_confirm' />
								<input type="button" value='返回' class='bjms_1 btn_confirm' onclick="linkTo()"/>
							</div>
						</form>
					</div>
				</div>
				<!--右边-->
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>