var giftFailConfig = null;

$(function() {
	var serialNumber = $("#serialNumber").val();
	var cnumber = $("#cnumber").val();
	
	// 加载送人配置
	$.ajax({
		url: ctx + "/tr_mobile/mbay/gift/wechatConfig.mbay",
		cache: false,
		type: "GET",
		data: {
			cnumber: cnumber,
			serialNumber: serialNumber
		},
		dataType: "JSON",
		success: function(resp) {
			if (resp.status) {
				giftFailConfig = resp;
			}
		}
	});
});

function giftFailShare() {
	// 定义内部属性
	if (arguments.callee.failTimes == undefined) {
		arguments.callee.failTimes = 0;
	}
	
	// 加载失败超过3次不再执行
	if (arguments.callee.failTimes > 3) {
		return;
	}
	
	// 配置信息没加载完执行该方法，尝试延后重新执行
	if (giftFailConfig == null) {
		arguments.callee.failTimes++;
		setTimeout(giftFailShare, 200);
		return;
	}
	
	$.wechat.share({
		shareTitle: giftFailConfig.shareTitle,
		content: giftFailConfig.content,
		shareLink: giftFailConfig.shareLink,
		shareImage: giftFailConfig.shareImage
	}, function() {
		$.messager.remind({ content: "恭喜您分享成功！" });
	});
}