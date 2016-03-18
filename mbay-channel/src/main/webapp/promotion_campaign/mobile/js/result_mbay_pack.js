$(function() {
	share();
});
	
function shakeAgain(type) {
	var remindShare = $("#remindShare").val() == "true" ? true : false; 
	if(remindShare && !shared){
		$.messager.dialog({
			content: $("#share-popup").html(),
			fullScreen: true
		});
	}else{
		// 再玩一次
		if(type == "PLAY"){
			//赠送mb
			$.ajax({
				url: ctx + "/promotionCampaign/redeem_code/play_sendMB.mbay",
				cache: false,
				type: "GET",
				data: {
					number: $("#enumber").val(),
					code: $("#code").val()
				},
				dataType: "JSON",
				success: function(resp) {
					if (resp.success) {
						var url = ctx + 
						"/promotionCampaign/redeem_code/result_mbay_pack.mbay?from=play&number="
						+ $("#enumber").val()+"&mb=" + resp.mb
						+ "&mobile=" + resp.mobile
						+ "&code=" + $("#code").val();;
						gotoUrl(url);
					} else {
						$.messager.dialog({
							content: resp.message
						});
					}
				}
			});
		}
		// 再领一次
		if(type == "GET"){
			var enumber = $("#enumber").val();
			var url = ctx + "/promotionCampaign/redeem_code/" + enumber + "/get.mbay?method=auto";
			gotoUrl(url);
		}
	}
	return;
}

