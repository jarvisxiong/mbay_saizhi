var shared = false;
var shareConfig = null;

$(function() {
	var cnumber = $("#cnumber").val();
	
	// 加载分享加油配置
	$.ajax({
		url: ctx + "/tr_mobile/wechatConfig.mbay",
		cache: false,
		type: "GET",
		data: {
			cnumber: cnumber,
		},
		dataType: "JSON",
		success: function(resp) {
			if (resp.status) {
				shareConfig = resp;
			}
		}
	});
});

function share() { 
	// 定义内部属性
	if (arguments.callee.failTimes == undefined) {
		arguments.callee.failTimes = 0;
	}
	
	// 加载失败超过3次不再执行
	if (arguments.callee.failTimes > 3) {
		return;
	}
	
	// 配置信息没加载完执行该方法，尝试延后重新执行
	if (shareConfig == null) {
		arguments.callee.failTimes++;
		setTimeout(share, 200);
		return;
	}

	var cnumber = $("#cnumber").val();
	shared = false;
	if (shareConfig.enableState == 'DISENABLE') {
		$.wechat.hide();
	} else {
		$.wechat.share({
			shareTitle: shareConfig.shareTitle,
			content: shareConfig.content,
			shareLink: shareConfig.shareLink,
			shareImage: shareConfig.shareImage
		}, function() {
			$.messager.closeDialog();
			$.ajax({
				url: ctx + "/tr_mobile/share_success.mbay",
				type: "GET",
				cache: false,
				data: {
					cNumber: cnumber
				},
				dataType: "JSON",
				success: function(resp) { 
					if (resp.status) {
						$.messager.remind({ content: "分享成功，恭喜你获取到多一次的摇一摇机会!" });	
						shared = true; 
						if (window.resetShakeAble) {
							resetShakeAble(false); 
						}
					} else {
						// 分享已不能再加摇一摇机会
						if (resp.code == "INVALID_SHARE_TIMES") {
							$.messager.remind({ content: "分享成功" });	
						} else {
							$.messager.remind({ content: "分享成功，但是您的分享有效次数已经用完啦/(ㄒoㄒ)/~~" });	
						}
					}
				}
			});
		});
	}
}