function subform() {
	var url = ctx + "/web/main/pwd/forget/set.mbay";
	var passwd = $("#passwd").val();
	var passwd2 = $("#passwd2").val();
	if (passwd == "" || passwd2 == "") {
		$.messager.remind({ content: "密码不能为空" });
		return;
	}
	if (passwd != passwd2) {
		$.messager.remind({ content: "两次密码输入不一致" });
		return;
	}
	
	// 修改密码
	$.getJSON(url, {
		passwd : passwd,
		checkcode: $("#checkcode").val(),
		type: $("#type").val()
	}, function(resp) {
		if (!resp.status) {
			$.messager.remind({ content: resp.errorMsg });
			return;
		}
		
		// 请求登录
		$.getJSON(
			ctx + "/web/main/login/pwd.mbay",
			{
				mobile : resp.data,
				pwd : passwd
			},
			function(resp2) {
				if (!resp2.status) {
					$.messager.remind({ content: resp2.errorMsg });
					return;
				}
				
				// 转到钱包主页
				gotoUrl(ctx + "/web/main/index/ui.mbay");
			}
		);
	});
}