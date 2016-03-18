$(document).ready(function() {
	var cnumber = $("#cnumber").val(); 
	
	$(".shake_start").touch(function() {
		var mobile = $(".import_number").val();		
		if (mobile == '' || !validateRegExp.mobile.test(mobile)) {
			$.messager.remind({ content: "请输入正确的手机号" }); 			
			return;
		}
		
		// 号码记录到cookie
		var validUrl = ctx + "/tr_mobile/remMobile.mbay";
		$.getJSON(validUrl, {
			mobile: mobile,
			number: cnumber
		}, function(data) { 
			if (data.status) { 
				gotoUrl(ctx + "/tr_mobile/shakeUI.mbay?number=" + cnumber);
			} else {
				$.messager.remind({ content: data.content });
			}
		});
	});
});