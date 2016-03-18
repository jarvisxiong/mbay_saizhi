function emptyLogin() {
	var mobile = $("#mobile").val();
	var flag = validateRegExp.mobile.test(mobile);  
	if (flag == false) {
		$.messager.remind({ content: "手机号不合法" });
		return;
	}
}

function pwdLogin() {
	var url = ctx + "/web/main/login/pwd.mbay";
	var mobile = $("#mobile").val();
	var flag = validateRegExp.mobile.test(mobile);  
	if (flag == false) {
		$.messager.remind({ content: "手机号不合法" });
		return;
	}
	var password = $("#pwd").val();
	if (password == "" || password == null) {
		$.messager.remind({ content: "密码不能为空" });
		return;
	}
	$.getJSON(url, {
		mobile : mobile,
		pwd : password
	}, function(data) {
		if (!data.status) {
			$.messager.remind({ content: data.errorMsg });
			return;
		}
		if (data.data) {
			gotoUrl(data.data);
		} else {
			gotoUrl(ctx + "/web/main/index/ui.mbay");
		}
	});
}

function codeLogin() { 
	var url = ctx + "/web/main/login/code.mbay";
	var mobile = $("#mobile").val();
	var flag = validateRegExp.mobile.test(mobile);  
	if (flag == false) {
		$.messager.remind({ content: "手机号不合法" });
		return;
	}
	var code = $("#smscode").val();
	if (code == "" || code == null) {
		$.messager.remind({ content: "验证码不能为空" });
		return;
	}
	$.getJSON(url, {
		mobile : mobile,
		code : code
	}, function(resp) {
		if (!resp.status) {
			$.messager.remind({ content: resp.errorMsg });
		} else {
			gotoUrl(ctx + "/web/main/pwd/forget/set/ui.mbay?type=WL_REGISTER_CODE&checkcode=" 
					+ $("#smscode").val());
		}
	});
}

var s = 60;
var second = s;
var timer;
function change() {
	second--;
	if (second > -1) {
		$("#checkcode").text(second);
		timer = setTimeout(change, 1000);
	} else {
		clearTimeout(timer);
		$("#checkcode").removeAttr("disabled");
		$("#checkcode").text("重新获取");
		$("#checkcode").css("background-color", "#DCDCDC");
		second = s;
	}
}

function getCode() {
	if ($("#checkcode").attr("disabled") == "disabled") {
		return;
	}
	
	var mobile = $("#mobile").val();
	if (!validateRegExp.mobile.test(mobile)) {
		$.messager.remind({ content: "手机号码不正确!" });
		return;
	}
	$("#checkcode").attr("disabled", true).css("background-color", "gray");
	setTimeout(function() { $(".voice").css("visibility", "visible"); }, 10000);
	timer = setTimeout(change, 1000);
	var url = ctx + "/web/main/login/code/get.mbay";
	$.getJSON(url, {
		mobile: mobile,
		type: 1
	}, function(data) { 
		if (!data.status) {
			$.messager.remind({ content: data.errorMsg });
		}
	});
}

function getVoiceCode() {
	var mobile = $("#mobile").val();
	if (!validateRegExp.mobile.test(mobile)) {
		$.messager.remind({ content: "手机号码不正确!" });
		return;
	}
	$(".voice").css("visibility", "hidden");
	var url = ctx + "/web/main/login/code/get.mbay";
	$.getJSON(url, {
		mobile: mobile,
		type: 2
	}, function(data) { 
		if (!data.status) {
			$.messager.remind({ content: data.errorMsg });
		}
	});
}

function setPasswordOfCode() {
	var url = ctx + "/web/main/pwd/forget/set/ui.mbay?type=WL_REGISTER_CODE&checkcode=" + $("#smscode").val();
	gotoUrl(url);
}

function notActive() { 
	$.get(ctx + "/web/main/login/code/delete.mbay"); 
	gotoUrl(ctx + '/web/main/index/ui.mbay');
}

$(function() {
    $(".mb-tab a").touch(function(){
        var _index = $(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".mb-form").find("form").eq(_index).show().siblings("form").hide();
        return false;
    });
    
    $(".forg").touch(function() {
    	var mobile = $("#mobile").val();
    	if (!validateRegExp.mobile.test(mobile)) {
    		mobile = '';
    	}
    	gotoUrl(ctx + "/web/main/pwd/forget/ui.mbay?mobile=" + mobile);
    });
    
    $("#pwdLogin").touch(emptyLogin);
    
    $("#voiceCode").touch(function() {
    	getVoiceCode();
    });
    
    $("#checkcode").touch(function() {
    	getCode();
    });
    
    var ajaxRun = null;
    $("#mobile").on("input propertychange", function() {
    	var mobile = $(this).val();
    	var flag = validateRegExp.mobile.test(mobile);  
    	if (flag) {
    		ajaxRun = $.ajax({
    			url: ctx + "/web/main/isUserExsits.mbay",
    			dataType: "json",
    			data: {
    				mobile: mobile
    			},
    			beforeSend: function() {
    				$("#loading").fadeIn(500);
    			},
    			success: function(exsit) {
    				if (exsit) {
	    				$("#pwd-wrap").slideDown();
	    				$("#checkcode").hide();
	    				$("#smscode-wrap").hide();
	    				$("#pwdLogin").val("登录").off("touchend").touch(pwdLogin);
	    			} else {
	    				$("#checkcode").show();
	    				$("#smscode-wrap").slideDown();
	    				$("#pwd-wrap").hide();
	    				$("#pwdLogin").val("注册").off("touchend").touch(codeLogin);
	    			}
    			},
    			complete: function(xhr, ts) {
    				$("#loading").fadeOut(500);
    				if (ts != "success" && ajaxRun != null) {
    					$.messager.remind({ content: "哎呀，网络连接似乎出问题了，稍后再试下吧~" });
    				}
    				ajaxRun = null;
    			}
    		});
    	} else {
    		$("#pwd-wrap").slideUp();
    		$("#smscode-wrap").slideUp();
    		$("#checkcode").hide();
    		$("#pwdLogin").val("登录").off("touchend").touch(emptyLogin);
    		
    		// 停止未执行完的ajax
    		if (ajaxRun != null) {
    			var tmp = ajaxRun;
    			ajaxRun = null;
    			tmp.abort();
    		}
    	}
    });
    
    $("#mobile").trigger("propertychange");
    
});