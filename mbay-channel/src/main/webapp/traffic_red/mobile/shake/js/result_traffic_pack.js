$(function() {
	var cnumber = $("#cnumber").val();
	var traddicSize = $("#traddicSize").val();
	var recordId = $("#recordId").val();
	
	$("#redeemLink").touch(function() { 
		var tokenValue=$("input[name='org.sz.mbay.taglib.html.TOKEN']").val();
		var url = href = ctx + "/tr_mobile/redeem/exchange_traffic.mbay?org.sz.mbay.taglib.html.TOKEN=" + tokenValue;
		
		$.ajax({
			url: url,
			type: "post",
			cache: false,
			data: {
				recordId : recordId
			},
			dataType: "json",
			success: function(data) {
				if (data.status) {
					var result = ctx + "/tr_mobile/redeem_result.mbay?cnumber=" + cnumber + "&size=" + traddicSize;
					window.location.href = result;
					return;
				}
				$.messager.remind({ content: data.content });
			},
			error: function() {
				$.messager.remind({ content: "已兑换" });
			}
		});
	});
});