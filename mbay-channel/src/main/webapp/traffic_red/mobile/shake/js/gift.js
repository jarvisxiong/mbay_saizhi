$(function() {
	$("#chain").touch(function() {
		grab($("#cnumber").val(), $("#serialNumber").val());
	});
});

function grab(cnumber, serialNumber) {
	var mobile = $("#mobile").val();
	if (!validateRegExp.mobile.test(mobile)) {
		$.messager.remind({ content: "请输入正确的手机号码！" });
		return;
	}
	$.getJSON(ctx + '/tr_mobile/mbay/gift/grab.mbay',
		{
			cnumber: cnumber,
			serialNumber: serialNumber,
			mobile: $("#mobile").val()
		},
		function(resp) {
			if (!resp.status) {
				if (resp.code == "MBAY_GIFT_LINK_EXPIRED" 
						|| resp.code == "MBAY_GIFT_RECIEVERD" 
						|| resp.code == "MBAY_GIFT_RECIEVERD_YOURSELF"
						|| resp.code == "TIMES_EXCEED_DAY"
						|| resp.code == "TIMES_EXCEED_WEEK"
						|| resp.code == "TIMES_EXCEED_MONTH") {
					var url = ctx + "/tr_mobile/mbay/gift/grab/fail.mbay"
							+ "?code=" + resp.code 
							+ "&serialNumber=" + serialNumber
							+ "&cnumber=" + cnumber 
							+ "&mobile=" + mobile;
					gotoUrl(url);
				} else {
					$.messager.remind({ content: resp.content });
				}
			} else {
				gotoUrl(ctx + "/tr_mobile/mbay/gift/grab/success.mbay" 
						+ "?serialNumber=" + resp.data 
						+ "&cnumber=" + cnumber 
						+ "&mobile=" + mobile); 
			}
		}
	);
}