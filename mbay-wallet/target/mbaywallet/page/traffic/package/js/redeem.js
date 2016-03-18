var rechargingDesc = ["中国联通500M流量包", "中国移动500M流量包", "中国电信500M流量包"]; 
var rechargingInfo = 
	[
	 	[
	 		"1.所有欠费用户暂不可兑换本流量产品。",
	 		"2.单用户每个月最多可兑换本流量产品三次。",
	 		"3.个别专用数据卡或者机器卡用户暂不可兑换本流量产品。"
	 	],
	 	[
	 		"1.所有欠费用户暂不可兑换本流量产品。",
	 		"2.个别专用数据卡或者机器卡用户暂不可兑换本流量产品。"
	 	],
	 	[
			"1.所有欠费用户暂不可兑换本流量产品。",
			"2.各省活动限制领取用户(主要但不限于天翼无线宽带套餐) 暂不可兑换本流量产品。",
			"3.时长计费用户暂不可兑换本流量产品。"
	 	]
	];

function openRecharging(packId, type) {
	$("#recharging .sus-pop-p1").text(rechargingDesc[type]);
	var $ul = $("#recharging .sus-pop-p3");
	$ul.empty();
	for (var i = 0; i < rechargingInfo[type].length; i++) {
		$ul.append("<span>" + rechargingInfo[type][i] + "</span>");
	}
	$("#recharging").show();
	$("#rechargingId").val(packId);
}

function redeem() {
	if ($("#recharging .zhuan-mb").hasClass("red-dis")) {
		return;
	}
	$("#recharging .zhuan-mb").addClass("red-dis");
	var packId = $("#rechargingId").val();
	var url = ctx + "/web/traffic/package/redeem.mbay"; 
	$.getJSON(url, { packageId: packId }, function(resp) {
		$("#recharging").hide();
		$("#recharging .zhuan-mb").removeClass("red-dis");
		if (!resp.status) {
			if (resp.errorCode == "BALANCE_INSUFFICIENT") {
				$("#accountInsufficient").show();
			} else {
				$.messager.remind({ content: resp.errorMsg });
			}
		} else {
			$("#rechargeSuc").show();
			$(".cloud-mb").text(resp.data + "MB");
		}
	});
}

function reloadPage(){
	window.location.reload();
}

$(function() {
	$(".shake-inner-list .unicom").touch(function() {
		openRecharging($("#unicom-code").val(), 0);
	});
	
	$(".shake-inner-list .mobile").touch(function() {
		openRecharging($("#mobile-code").val(), 1);
	});

	$(".shake-inner-list .telecom").touch(function() {
		openRecharging($("#telecom-code").val(), 2);
	});
	
	$("#accountInsufficient .zhuan-mb, #go-on-zhuan-mb").touch(function() {
		gotoUrl(ctx + "/web/campaign/list/ui.mbay");
	});
	
	$("#accountInsufficient .close").touch(function() {
		$('#accountInsufficient').hide();
	});
	
	$("#recharging .redeem").touch(function() {
		redeem();
	});
	
	$("#recharging .close").touch(function() {
		$('#recharging').hide();
	});
	
	$("#rechargeSuc .sure, #rechargeSuc .close").touch(function() {
		$("#rechargeSuc").hide();
	});
	
	$("#back").touch(function() {
		gotoUrl(ctx + "/web/main/index/ui.mbay");
	});
});