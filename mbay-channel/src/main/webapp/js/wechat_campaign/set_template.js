$(function() {
	/* 边框选中效果 */
	selected = null;
	/* 切换图片组计数,默认4个一组 */
	var count = 1;
	var group = 4;
	var arrs = document.getElementsByName("bj_box_a");
	// 最大count数
	var count_max = arrs.length == 0 ? 0 : (parseInt(arrs.length / group));
	
	$('.bj_box a').hover(function() {
		$(this).css('border', '1px solid red');
		$(selected).css('border', '1px solid red');
	}, function() {
		$('.bj_box a').css('border', '1px solid #fff');
		$(selected).css('border', '1px solid red');
	});

	$('.sec_0').click(function() {
		if (count >= 1 && count <= count_max && arrs.length != group) {
			for ( var x = count - 1; x < group * count; x++) {
				$('#bj_box_a_' + x).hide();
			}
			for ( var y = group * count; y < group * (count + 1); y++) {
				$('#bj_box_a_' + y).show();
			}
			count = count + 1;
		}
	});
	$('.sec_1')
			.click(
					function() {
						if (count >= 2) {
							for ( var y = group * count - 1; y >= group
									* (count - 1); y--) {
								$('#bj_box_a_' + y).hide();
							}
							for ( var x = group * (count - 1) - 1; x >= group
									* (count - 2); x--) {
								$('#bj_box_a_' + x).show();
							}
							count = count - 1;
						}
					});

	//更换系统默认图片
	$("#bk").change(function() {
		chanebgimg();
	});
	function chanebgimg() {
		if (checkFileSize("bk")) {
			readBackURL("bk");
			return true;
		}
		return false;
	}
	//读取用户设置的自定义背景，恢复按钮为系统默认按钮
	function readBackURL(id) {
		var input = document.getElementById(id);
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$("div.wx_body").attr(
						"style",
						"background-image: url('" + e.target.result
								+ "');background-size: cover;");
				$("#back_short_img").attr("src",e.target.result);
			};
			reader.readAsDataURL(input.files[0]);
			showDefaultButton();//
			selected == null;
		}
	}
	//定义点击 领取成功设置按钮事件
	$("#give_success_file").change(function(){
		if (checkFileSize("give_success_file")) {
			var input = document.getElementById("give_success_file");
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$("#privew_succss_img").attr(
							"src",e.target.result);
					$("#succss_short_img").attr("src",e.target.result);
				////	$("#btn_success_def").show(); 隐去恢复默认按钮
				};
				reader.readAsDataURL(input.files[0]);
			}		
			return true;
		}	
		return false;	
	});
	
	$("#btn_success_def").click(function(){
		showSuccessDefImg();
		$("#btn_success_def").hide();
	});
	
	//调用此方法用户显示领取成功默认背景
	showSuccessDefImg();
});
//显示领取成个默认背景
function showSuccessDefImg(){
	$("#privew_succss_img").attr("src",$("#success_def_src").val());
	$("#succss_short_img").attr("src",$("#success_def_src").val());
	$("#give_success_file").val("");
}

//点击系统默认模板onclick 事件。 显示效果//更换系统默认图片
function change(eventDefaultTemplateId, backPicture, buttonPicture,obj) {
	selected = $(obj);
	$("#bk").val("");// 清除用户自选图片
	$('.wx_body').attr(
			'style',
			'background:url(' + backPicture
					+ ') 50% 50% no-repeat;background-size: cover;');
	///$("#getbtn").attr("src",ctx+'/images/'+ buttonPicture+ '/get.mbay');
	$('input[name="eventDefaultTemplateId"]').val(eventDefaultTemplateId);
	//设置选中效果
	$('.bj_box i').hide();//隐藏所有打勾效果
	$(obj).find('i').show();//显示当前选中的打勾效果
	$('.bj_box a').css('border', '1px solid #fff');//取消所有的选中红色边框
	$(obj).addClass("template_selected");
	$("#back_short_img").attr("src",actx+"/images/campaign_common/default.png");//更改缩略图
}
// 手动修改背景图片
function manualChange() {
	$('input[name=bk]').click();
}
//点击自定义成功背景
function customGiveSuccessImg(){
	$('input[name=givesuccess]').click();
}


// 显示默认按钮
function showDefaultButton() {
	///$("#getbtn").attr("src",actx+'/images/wechat_campaign/button.png');
	$('input[name="eventDefaultTemplateId"]').val("0");
	$('.bj_box i').css('display', 'none');
	$('.bj_box a').css('border', '1px solid #FFF');
}