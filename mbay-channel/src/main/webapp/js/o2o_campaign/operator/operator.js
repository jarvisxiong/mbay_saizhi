$(function() {
	$("#ope_init_btn").click(
			function init() {
				var authCode = $("#ope_login_init input[name='authCode']")
						.val();
				var cellphone = $("#ope_login_init input[name='cellphone']")
						.val();
				var password = $("#ope_login_init input[name='password']")
						.val();
				var checkPassword = $(
						"#ope_login_init input[name='checkPassword']").val();

				// alert(authCode + "-" + checkPassword);
				if (authCode == "" || cellphone == "") {
					return;
				}
				var url = ctx + "/store_ope/init.mbay";
				$.post(url, {
					authCode : authCode,
					cellphone : cellphone,
					password : password,
					checkPassword : checkPassword
				}, function(result) {
					if (result.success) {
						window.location.href = ctx + '/store_ope/loginUI.mbay';
					} else {
						alert(result.error_code);
						if (result.error_code == "HAS INITED") {
							window.location.href = ctx
									+ '/store_ope/loginUI.mbay';
							return;
						}
						$.messager.alert({ title: "系统提示", content: "初始化店员登录信息失败，请联系客服人员!" });
					}
				});
			});
});
