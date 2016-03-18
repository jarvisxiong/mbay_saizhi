function gift() {
	var serialNumber = $("#serialNumber").val();
	$.getJSON(ctx + "/tr_mobile/mbay/gift.mbay",
		{ serialNumber: serialNumber },
		function(resp) {
			if (!resp.status) {
				$.wechat.hide();
				$.messager.remind({ content: resp.content });
			} else {
				giftShare();
				$.messager.dialog({ 
					content: $("#gift-popup").html(),
					fullScreen: true,
					afterClose: function() {
						$.wechat.hide();
					}
				});
			}
		}
	);
}