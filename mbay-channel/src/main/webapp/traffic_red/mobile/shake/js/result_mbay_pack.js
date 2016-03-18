$(function() {
	share();
});
	
function shakeAgain(url) {
	var remindShare = $("#remindShare").val() == "true" ? true : false; 
	if(remindShare && !shared){
		$.messager.dialog({
			content: $("#share-popup").html(),
			fullScreen: true
		});
	}else{
		gotoUrl(url);
	}
	return;
}

function gift() {
	var serialNumber = $("#serialNumber").val();
	$.getJSON(ctx + "/tr_mobile/mbay/gift.mbay",
		{ serialNumber: serialNumber },
		function(resp) {
			if (!resp.status) {
				$.messager.remind({ content: resp.content });
			} else {
				$.messager.dialog({ 
					content: $("#gift-popup").html(),
					fullScreen: true,
					afterClose: function() {
						share();
					}
				});
				giftShare();
			}
		}
	); 
}
