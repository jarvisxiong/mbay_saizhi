$(function() {
	$.wechat.share({
		shareTitle: $("#shareTitle").val(),
		content: $("#content").val(),
		shareLink: $("#shareLink").val(),
		shareImage: $("#shareImage").val()
	},function() {
		$.ajax({
			url: ctx + "/web/wechat/share/mb/get.mbay",
			type: "GET",
			cache: false,
			dataType: "JSON",
			success: function(resp) {
				if(resp.status){
					$.messager.remind({ content: "分享成功,恭喜你获取到20MB" });
				}
			}
		});
	});
	
	$(".gotoPage").not(":disabled").touch(function() { 
		var pagenum = $(this).attr('name');
		var url = ctx + "/web/trade/record/list/ui.mbay";
		if(pagenum != "1"){
			url = url + "?pagenum=" + pagenum;
		}
		gotoUrl(url);	
		return;
	});
	
	$("#share-mb").touch(function() {
		$.messager.dialog({
			content: $("#share-notice").html(),
			contentCss: {
				width: "80%"
			}
		});
	});
});

function getDetail(snumber) {
	$.ajax({
		url: ctx + "/web/trade/record/get.mbay",
		type: "POST",
		cache: false,
		data: { serialNumber: snumber },
		dataType: "json",
		success: function(resp) {
			if (!resp.status) {
				$.messager.remind({ content: resp.errorMsg });
			} else {
				var data = resp.data;
				var html = "<div class='trade-detail'>";
				
				if (data.tradeType != "TRAFFIC_RED_TRAFFIC") {
					html += "<p>" + (data.paymentType == "INCOME" ? "+" : data.paymentType == "EXPENSE" ? "-" : "") 
							+ data.amount + "MB</p>"
				} else {
					html += "<p></p>";
				}
						
				html += "<p>交易类型：" + data.description.substring(0, data.description.indexOf("：")) + "</p>";
				html += "<p>交易描述：" + data.description.substring(data.description.indexOf("：") + 1) + "</p>";
				html += "<p>交易流水号：" + data.serialNumber + "</p>";
				html += "<p>交易日期：" + data.createTime + "</p>";
				
				switch (resp.data.tradeType) {
					case "TRAFFIC_EXCHANGE":
						html += "<p class='spt'>充值单号：" + data.relatedNumber + "</p>";
						html += "<p>充值状态：" + data.status + "</p>";
						html += "<p>运营商充值状态：" + data.ors + "</p>";
						break;
					case "DUIBA_MARKET":
						html += "<p class='spt'>商城名称：" + data.mallName + "</p>"; 
						html += "<a href='javascript:void(0)' ontouchend='gotoUrl(\"" + data.url + "\")'>进入商城</a>";
						break;
					case "TRAFFIC_RED_TRAFFIC":
					case "TRAFFIC_RED_MBAY":
						html += "<p class='spt'>活动名称：" + data.name + "</p>";
						html += "<p>活动编号：" + data.number + "</p>";
						html += "<p>活动状态：" + data.status + "</p>";
						html += "<p>接收状态：" + data.packageStatus + "</p>";
						html += "<p>商城名称：" + data.mallName + "</p>"; 
						html += "<a href='javascript:void(0)' ontouchend='gotoUrl(\"" + data.url + "\")'>进入商城</a>";
						break;
					case "DUIBA_MARKET_ROLLBACK":
						html += "<p class='spt'>回滚单号：" + data.relatedNumber + "</p>";
						html += "<p>商城名称：" + data.mallName + "</p>"; 
						html += "<a href='javascript:void(0)' ontouchend='gotoUrl(\"" + data.url + "\")'>进入商城</a>";
						break;
					case "TRAFFIC_RED_MBAY_GIFT":
						html += "<p class='spt'>来源单号：" + data.relatedNumber + "</p>";
						break;
					case "TRAFFIC_RED_MBAY_GIFT_GRAB":
						html += "<p class='spt'>发送方：" + data.sender + "</p>";
						html += "<p class='spt'>状态：" + data.giftState + "</p>";
						break;
					case "PROMOTION_MBAY":
						html += "<p class='spt'>活动名称：" + data.name + "</p>";
						html += "<p>活动编号：" + data.number + "</p>";
						html += "<p>活动状态：" + data.status + "</p>";
						html += "<p>商城名称：" + data.mallName + "</p>"; 
						html += "<a href='javascript:void(0)' ontouchend='gotoUrl(\"" + data.url + "\")'>进入商城</a>";
						break;
					// 没有相关单号的业务
					case "TASK_TRANSFER_MB":
					default:
				}
				
				html += "</div>";
				
				$.messager.dialog({
					button: {
						dialogClose: {
							visible: true
						}
					},
					content: html
				});
			}
		},
		error: function(xhq, status) {
			$.messager.remind({ content: status });
		}
	});
}