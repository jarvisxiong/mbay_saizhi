var giftConfig = null;

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
				giftConfig = resp;
			} 
		}
	});
});

function giftShare() {
	// 定义内部属性
	if (arguments.callee.failTimes == undefined) {
		arguments.callee.failTimes = 0;
	}
	
	// 加载失败超过3次不再执行
	if (arguments.callee.failTimes > 3) {
		return;
	}
	
	// 配置信息没加载完执行该方法，尝试延后重新执行
	if (giftConfig == null) {
		arguments.callee.failTimes++;
		setTimeout(giftShare, 200);
		return;
	}
	
	var serialNumber = $("#serialNumber").val();
	$.wechat.share({
		shareTitle: giftConfig.shareTitle,
		content: giftConfig.content,
		shareLink: giftConfig.shareLink,
		shareImage: giftConfig.shareImage
	}, function() {
		$.ajax({
			url: ctx + "/tr_mobile/mbay/gift/share.mbay",
			type: "GET",
			cache: false,
			data: {
				serialNumber: serialNumber
			},
			dataType: "JSON",
			success: function(resp) {
				$.messager.closeDialog();
				if (resp.status) {
					$.messager.remind({ content: "恭喜您MB已成功送出！" });						
				} else {
					$.messager.remind({ content: resp.content });	
				}
			}
		});
	});
}