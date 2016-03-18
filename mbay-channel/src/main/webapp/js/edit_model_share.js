/** 编辑模式-分享专用* */
// 默认分享内容
var default_content = "最近携程攻略社区关注微信赠送3G手机流量的丧心病狂活动，让我觉得不虐它千百遍就不能称为爱。大家请和我一起帮他们揩油瘦身。2014年最后10天， 火速围观疯抢！！！";
// 默认分享标题
var default_title = "携程攻略社区送流量";

$(function() {
	// 分享图片按钮
	$("#shareImage").change(
			function() {
				if (checkFileType("shareImage")) {
					var input = document.getElementById("shareImage");
					if (input.files && input.files[0]) {
						var reader = new FileReader();
						reader.onload = function(e) {
							$('#share_img').attr('src', e.target.result).width(
									60).height(60);
							$("#shareImgSrc").attr("src",e.target.result);
						};
						reader.readAsDataURL(input.files[0]);
					}
				}
			});

	// 根据需求,暂时隐藏高级选项按钮
	$("#advanced").hide();
	$("#advanced_span").show();
});

// 高级选项
function share() {
	var shareTimes = $("input[name=shareTimes]").val();
	var checked = "checked='checked'";
	var sharedEnable =parseInt($("#shareEnable").val());
	var shareImgsrc=$("#shareImgSrc").attr("src");
	if(shareImgsrc==""){shareImgsrc=actx+"/images/wechat_campaign/share_file.jpg";}
	var shareImgOnClick = $("#share_img").attr("onclick");
	if(!shareImgOnClick){shareImgOnClick="sharePhoto()";}
	if (!sharedEnable) {
		if(shareTimes==""||shareTimes==0)
		shareTimes = 1;
		checked = "";
	}
	var html = '<div class="option_box"><h3>分享设置</h3>';
	// //html += '<div class="share_checkbox"><input id="one"
	// name="wechartTimes" type="checkbox" value="one"
	// onclick="checkOne(\'one\');"/><label
	// for="one">分享给小伙伴方能领取流量</label></div>';
	html += '<div class="share_checkbox"><input id="more" name="wechartTimes" type="checkbox" '
			+ checked
			+ '  value="more"/><label for="more">分享给<input id="times" type="text" value="'
			+ shareTimes
			+ '" class="share_sl disabled"/>个小伙伴方能领取流量</label><span id="times_message" style="display:none;color:red;">*请输入1-4的数字!</span></div>';
	html += '<div class="clearfix"><h3 class="fl">分享标题设置</h3><div class="fl share_con_op_r">';
	html += '<div><input id="title" type="text" value="'
			+ $("input[name=shareTitle]").val()
			+ '" placeholder="'
			+ default_title
			+ '" class="headline disabled" maxlength="64"/><br/><span id="title_message" style="display:none;color:red;margin-left:2px;">*内容过长,不得超过64个字符!</span></div>';
	html += '<div><img id="share_img" src="'+shareImgsrc+'" class="upload_pic" onclick="'+shareImgOnClick+'"/><span class="size_tip">点击上传图片,最佳尺寸60*60</span></div>';
	html += '<div><textarea maxlength="400" id="content_area" name="content_area" placeholder="'
			+ default_content
			+ '" class="disabled">'
			+ $("input[name=content]").val() + '</textarea><br/>';
	html += '<span id="content_message" style="display:none;color:red;">*内容过长,不得超过400个字符!</span></div></div></div>';
	html += '<div class="clearfix"><h3 class="fl">分享链接设置</h3>';
	html += '<div class="fl share_link_op_r"><input class="link disabled" maxlength="400" id="link" type="text" value="'
			+ $("input[name=shareLink]").val()
			+ '"/><br/><span id="link_message" style="display:none;color:red;margin-left:2px;">*内容过长,不得超过400个字符!</span><span id="link_message_not_url" style="display:none;color:red;margin-left:2px;">*不是标准的微信网址格式!</span></div></div>';
	html += '<div class="op_btn"><button class="btn_reset" style="margin: 0 66px"id="cancel_button"  onclick="cancel();" >关闭</button><button class="btn_confirm" style="margin: 0 36px;"id="submit_button" onclick="submit_before();">确认</button></div></div>';
	$.messager.dialog({ title: '高级选项', content: html });

	// 选择分享类型,如不选择无法进行相关信息填写
	$("input[name=wechartTimes]").bind("click", function() {
		if ($(this)[0].checked) {
			$(".disabled").removeAttr("disabled");
		} else {
			$("#share_img").attr("onclick","");
			$(".disabled").attr("disabled", "disabled");
		}
	});
	// 默认是没有选择
	if (!sharedEnable) {
		$(".disabled").attr("disabled", "disabled");
	}
}

// 分享提交前处理
function submit_before() {
	// 判断是否有选中某种分享方式
	//var names = document.getElementsByName("wechartTimes");
	///var flag = false;
	var flag=document.getElementById("more").checked;
	/*for ( var i = 0; i < names.length; i++) {
		var tmp = names[i];
		if (tmp.checked) {
			flag = true;
		}
	}*/
	if (!flag) {
		 $("#shareEnable").val(0);
		 $("input[name=shareTimes]").val("0");
		 $(".share_info").hide();//隐藏相关分享信息
		// 关闭弹出层
		$.messager.closeDialog();
	} else {
		// 分享次数设置
		$("#shareEnable").val(1);
		var times = $("#times").val();
		var reg = new RegExp("^[1-4]{1}$");
		if (!reg.test(times)) {
			$("#times_message").show();
			$("input[name=shareTimes]").val(1);
			return;
		} else {
			$("#times_message").hide();
			$("input[name=shareTimes]").val(times);
		}
		
		$(".share_info").show();

		// 标题设置
		var title_value = $("#title").val();
		if (title_value.length > 64) {
			$("#title_message").show();
			return;
		} else {
			$("#title_message").hide();
		}
		if (title_value != "") {
			$("input[name=shareTitle]").val(title_value);
		} else {
			$("input[name=shareTitle]").val(default_title);
		}
		// 内容设置
		var content_value = $("#content_area").val();
		if (content_value.length > 300) {
			$("#content_message").show();
			return;
		} else {
			$("#content_message").hide();
		}
		if (content_value != "") {
			$("input[name=content]").val(content_value);
		} else {
			$("input[name=content]").val(default_content);
		}
		// 链接设置
		var link_value = $("#link").val();
		if (link_value.length > 400) {
			$("#link_message_not_url").hide();
			$("#link_message").show();
			return;
		} else if (!isURL(link_value)) {
			$("#link_message_not_url").show();
			return;
		} else {
			$("#link_message").hide();
			$("#link_message_not_url").hide();
		}
		if (link_value != "") {
			$("input[name=shareLink]").val(link_value);
		}
		// 隐藏按钮,显示文字
		$("#advanced").hide();
		$("#advanced_span").show();
		// 关闭弹出层
		$.messager.closeDialog();
	}
}

// 取消,恢复原始数据
function cancel() {
	///$("input[name=shareEnable]").val(0);
	$("#count").html("");
	$("#shareCommit").hide();	
	$.messager.closeDialog();
}

// 触发分享图片按钮
function sharePhoto() {
	$("#shareImage").click();
}

// 验证微信网址
function isURL(str_url) {
	var strRegex = /^(?:http:\/\/mp.weixin.qq.com).*/;
	return strRegex.test(str_url);
}