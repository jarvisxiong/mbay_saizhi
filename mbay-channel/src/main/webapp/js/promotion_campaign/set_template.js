$(function() {
	$.validator.addMethod('bkyz', function(value, element) {
		if (selected == null) {
			return true;
		}
		if (value != "") {
			return checkFileType("bk");
		}
		return true;
	}, '');

	$.validator.addMethod('bksizeyz', function(value, element) {
		if (selected == null) {
			return true;
		}
		return chanebgimg();
	}, '');

	$("#bk").change(function() {
		chanebgimg();
	});

	$("#redeemBk").change(function() {
		chaneRedeemBgimg();
	});

	function chanebgimg() {
		if (checkFileSize("bk")) {
			readBackURL("bk", "promotion_img", "btn_success");
			readBackURL("bk", "back_get", "btn_success");
			return true;
		}
		return false;
	}

	function chaneRedeemBgimg() {
		if (checkFileSize("redeemBk")) {
			readBackURL("redeemBk", "promotion_redeem_img", "btn_success_redeem");
			readBackURL("redeemBk", "back_redeem", "btn_success_redeem");
			return true;
		}
		return false;
	}
	
	function readBackURL(id, imgid, buttonid) {
		var input = document.getElementById(id);
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$("#" + imgid).attr("src", e.target.result);
				/*$("#" + buttonid).show(); 隐去恢复默认按钮*/
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	$("#btn_success_redeem").click(function(){
		showSuccessRedeemImg();
		$("#btn_success_redeem").hide();
	});
	
	$("#btn_success").click(function(){
		showSuccessGetImg();
		$("#btn_success").hide();
	});
	
	$('#h2_get').click(function(){
    	if($(this).next('.con_box').is(':visible')){
    		return false;
    	}
    	$('.con_box').slideUp('fast');
		$(this).next('.con_box').slideDown('fast');
		$('#part_get').show();
		$('#part_redeem').hide();
	});	
    
   $('#h2_redeem').click(function(){
    	if($(this).next('.con_box').is(':visible')){
    		return false;
    	}
    	$('.con_box').slideUp('fast');
		$(this).next('.con_box').slideDown('fast');
		$('#part_redeem').show();
		$('#part_get').hide();
	});	
   
   $("input[name=gotText]").blur(function(){
	   $("#get_text_template").html(this.value);
   });
   
   $("input[name=redeemText]").blur(function(){
	  $("#change_template").html(this.value); 
   });
   
   $("input[name=introductionText]").blur(function(){
	  $("#func_template").html(this.value); 
   });
});

// 手动修改领取背景图片
function manualChangeBk() {
	$('input[name=bk]').click();
}

//手动修改兑换背景图片
function manualChangeRedeemBk() {
	$('input[name=redeemBk]').click();
}

//显示领取页面默认背景
function showSuccessGetImg(){
	$("#promotion_img").attr("src",$("#default_get").val());
	$("#back_get").attr("src",$("#default_get").val());
	$("#bk").val("");
	$("#get_picture_id").val(0);
}

//显示兑换页面默认背景
function showSuccessRedeemImg(){
	$("#promotion_redeem_img").attr("src",$("#default_redeem").val());
	$("#back_redeem").attr("src",$("#default_redeem").val());
	$("#redeemBk").val("");
	$("#redeem_picture_id").val(0);
}