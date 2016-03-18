$(function() {
	// $("#loginform input[type='button']").click(function() {
	$("#btn_ope_login").click(function() {
		var authCode = $("#loginform input[name='authCode']").val();
		var cellphone = $("#loginform input[name='cellphone']").val();
		var password = $("#loginform input[name='password']").val();
		// var authCode = $("#loginform input[name='authCode']").val();
		if (authCode == "" || cellphone == "" || password == "") {
			$.messager.alert({ title: "系统提示", content: "表单填写有问题!" });
			return;
		}

		var p = /^((1[3,7][0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$/;
		if (!p.test(cellphone)) {
			$.messager.alert({ title: "系统提示", content: "手机号码格式有问题!" });
			return;
		}

		var url = ctx + "/store_ope/login.mbay";
		$.post(url, {
					authCode : authCode,
					cellphone : cellphone,
					password : password
				}, function(result) {
					if (result.success) {
						window.location.href = ctx + '/store_ope/redeemUI.mbay';
					} else {
						$.messager.alert({ title: "系统提示", content: "登录失败！" + result.message });
					}
				});
	});
});