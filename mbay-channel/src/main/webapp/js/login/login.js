/**登录使用的js文件**/
$(function(){
	$('input').attr('autocomplete', 'off');
  
    $('.yanzhenma').focus(function(){
    	$(this).css('color','#000');
    	var txt_value=$(this).val();
    	if(txt_value==this.defaultValue) {
    		$(this).val('');
    	}
    });
    
    $('.yanzhenma').blur(function(){
    	$(this).css('color','#999999');
	    var txt_value=$(this).val();
		if(txt_value=='') {
			$(this).val(this.defaultValue);
		}
	});	
	
	$("#subbtn").on("click",function(){		
		if(valForm()){
			$("#loginForm").submit();
		}
	});
});

function valForm() {
	$(".help-block").hide();
	$("#showMsg").text("");
	var username = $.trim($("input[name=username]").val());
	var captcha = $.trim($("#captcha").val());
	var passwd = $.trim($("#encryptionPassword").val());
	if (!username) {
		$(".help-block").show();
		$("#showMsg").text("请输入用户名");
		return false;
	}
	if (!passwd) {
		$(".help-block").show();
		$("#showMsg").text("请输入密码");
		return false;
	}	
	if (!captcha) {
		$(".help-block").show();
		$("#showMsg").text("请输入验证码");
		return false;
	}
	if (captcha.length != 6) {
		$(".help-block").show();
		$("#showMsg").text("验证码不正确");
		return false;
	}	
	//加密方式:md5(md5(md5(密码) + 登录账户 + 验证码)
	var hash_pwd = hex_md5(passwd);//md5(密码)
	var hash_first = hex_md5(hash_pwd + username + captcha);
	var hash = hex_md5(hash_first);
	$("input[name=password]").val(hash);
	return true;
};

document.onkeydown = function(event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];          
    if (e && e.keyCode == 13) { // enter 键
    	$("#subbtn").trigger("click");
    }
}; 

function onlyNum(event) {
    event = event || window.event;
	if (event.ctrlKey || event.shiftKey || event.altKey) {
		event.returnValue=false;
		event.preventDefault();
	}
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) {
	if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) {
		event.returnValue=false;
		event.preventDefault(); 
		}
	}
}