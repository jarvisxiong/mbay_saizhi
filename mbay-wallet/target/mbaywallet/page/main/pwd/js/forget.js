var s = 60;
var second = s;
var timer;
function change() {
	second--;
	if (second > -1) {
		$("#checkcode").text(second);
		timer = setTimeout(change, 1000);//调用自身实现
	} else {
		clearTimeout(timer);
		$("#checkcode").removeAttr("disabled");
		$("#checkcode").text("重新获取");
		$("#checkcode").css("background-color", "#DCDCDC");
		second = s;
	}
}

$(function() {
	function getSmsCode(mobile, type) {
		var url = ctx + "/web/main/pwd/forget/reset/code/get.mbay";
		$.getJSON(url, {
			mobile: mobile,
			type: type
		}, function(resp) { 
			if (!resp.status) {
				$.messager.remind({ content: resp.errorMsg });
			}
		});
	}
	
	$("#checkcode").touch(function() { 
		if ($(this).attr("disabled") == "disabled") {
			return;
		}
		var mobile = $("#mobile").val();
		if (!validateRegExp.mobile.test(mobile)) {
			$.messager.remind({ content: "手机号码不正确" });
			return;
		}
		$(this).attr("disabled", true).css("background-color", "gray");
		setTimeout(function() { $(".voice").css("visibility", "visible"); }, 10000);
		timer = setTimeout(change, 1000); 
		getSmsCode(mobile, 1);
	});
	
	$("#voiceCode").touch(function() {
		var mobile = $("#mobile").val();
		if (!validateRegExp.mobile.test(mobile)) {
			$.messager.remind({ content: "手机号码不正确" });
			return;
		}
		$(".voice").css("visibility", "hidden");
		getSmsCode(mobile, 2);
	});

	$("#nextStep").touch(function() {
		var url = ctx + "/web/main/pwd/forget/reset/code/check.mbay";
		var mobile = $("#mobile").val();
		if (!validateRegExp.mobile.test(mobile)) {
			$.messager.remind({ content: "手机号码不正确" });
			return;
		}
		var checkcode = $("#codeText").val();
		if (checkcode == "") {
			$.messager.remind({ content: "请填写验证码!" });
			return;
		}
		
		$.getJSON(url, {
			checkcode: checkcode,
			mobile: mobile
		}, function(resp) {
			if (!resp.status) {
				$.messager.remind({ content: resp.errorMsg });
				return;
			}
			gotoUrl(ctx + "/web/main/pwd/forget/set/ui.mbay?checkcode=" + resp.data 
					+ "&type=WL_MODIFY_PASSWD"); 
		});
	});
});