$(function(){
 	/*验证*/
 	$("#exchangeItemForm").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"zs1-8" : /^[1-9]\d{0,7}$/,
			"rq" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
			"ticket" : function(gets,obj,curform,regxp){
			    var FileExt = gets.substr(gets.lastIndexOf('.')+1);
			    var _validFileExtensions = ["txt"];
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
			"suffix" : function(gets,obj,curform,regxp){
			    var FileExt = gets.substr(gets.lastIndexOf('.')+1);
			    var _validFileExtensions = ["jpg", "jpeg", "png"];
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
			}
		}
	}); 
    
    //添加或者更新页面图片部分tab切换
    $(".tooltips").click(function() {
        $(".tooltips").removeClass('active');
        $(this).addClass('active');
        var tabname = $(this).attr("tabname");
        $(".upload-content").hide();
        $("#"+tabname).show();
    });
    
    //点击上传调用隐藏的input[file]
    $("#ticketButton").on("click",function(){
		$("#ticketFile").click();
	});
    
    //上传立即显示结果
	$('#ticketFile').fileupload({
		url: ctx + "/mall/exchangeItem/showTicket.mbay",
	    done: function (e, data) {  
	    	if(data.result.success){
	    		var html = "";
	    		var json = data.result.value;
	    		var num = data.result.num;
	    		var i = 1;
	    		var tickets = "";
	    		$.each(json,function(index,val){
	    			//拼接json字符串
	    			if(i == 1){
	    				tickets = "{";
	    			}
	    			//显示前三条数据
	    			if(i == 1 || i == 2 || i == 3){
	    				html = html + "<tr><td style='padding-left:20px'>" + i + "</td><td>" + index + "</td><td>" + val + "</td></tr>";
	    			}
	    			tickets = tickets + index + ":" + val + ",";
	    			//显示最后一条
	    			if(i == num){
	    				html = html + "<tr><td style='padding-left:20px'>...</td><td>......</td><td>......</td></tr>"
	    				html = html + "<tr><td style='padding-left:20px'>" + i + "</td><td>" + index + "</td><td>" + val + "</td></tr>";
	    				tickets = tickets.substring(0,tickets.length-1);
	    				tickets = tickets + "}";
	    			}
	    			i++;
	    		});
	    		$("#tbody").html(html);
	    		$("input[name='tickets']").val(tickets);
	    	}else{
	    		$.messager.alert({ content: data.result.value });
	    	}
	    }  
	});  
});

//表单提交前操作
function submit_before(){
	//兑换项区隐藏
	var hide = $("input[name='hide_checkbox']").attr("checked");
	if(hide == "checked"){
		$("input[name='hide']").val("ENABLED");
	}else{
		$("input[name='hide']").val("DISENABLE");
	}
	
	//额外兑换限制
	var check = $("input[name='extendLimit_checkbox']").attr("checked");
	if(check == "checked"){
		//每日兑换限制
		var startHour = $("#startHour").val();
		var endHour = $("#endHour").val();
		var startMinute = $("#startMinute").val();
		var endMinute = $("#endMinute").val();
		//只要有一个时间没选择，则默认不开启额外兑换限制
		if(startHour == "" || endHour == "" || startMinute == "" || endMinute == ""){
			$("input[name='extendLimit']").val("DISENABLE");
		}else{
			$("input[name='extendLimit']").val("ENABLED");
			$("input[name='startTime']").val(startHour + ":" + startMinute);
			$("input[name='endTime']").val(endHour + ":" + endMinute);
		}
	}else{
		$("input[name='extendLimit']").val("DISENABLE");
	}
	
	$("#exchangeItemForm")[0].submit();
}

//点击额外兑换限制显示或隐藏内容
function showOrHide(){
	var check = $("input[name='extendLimit_checkbox']").attr("checked");
	if(check == "checked"){    
		$(".object_inside").show();
    }else{
    	$(".object_inside").hide();
    }
}

//点击input[file]立即显示图片,显示下一个div,给当前input[file]显示X按钮
function showImage(inputId, imgId, divId, spanId) {
	//显示图片
	var input = document.getElementById(inputId);
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			$('#' + imgId).attr('src', e.target.result);
	    };
	    reader.readAsDataURL(input.files[0]);
	}
	//如果存在divId则显示下一个div
	if(divId != ""){
		$("#" + divId).show();
	}
	//显示X按钮
	$("#" + spanId).show();
}

//清空input[file]内容,移除图片,隐藏下一个div,隐藏当前input[file]的X按钮
function removeImage(inputId, imgId, divId, spanId){
	//清空input[file]内容
	$("#" + inputId).val("");
	//移除图片
	$("#" + imgId).attr("src", ctx + "/images/mall/default_img_3.jpg");
	//隐藏下一个div
	$("#" + divId).hide();
	//隐藏当前input[file]的X按钮
	$("#" + spanId).hide();
}

//判断是删除图片还是更新图片:true->删除,false->更新
function delPicture(name){
	$("input[name='"+name+"']").val("true");
}

//券码页面下载示例文件
function download(){
	$.fileDownload(actx+'/filedownload/files/coupon-code-template.mbay', {
	    failCallback: function (html, url) {
	    	$.messager.alert({ content: "下载失败！" });
	    }
	});
}