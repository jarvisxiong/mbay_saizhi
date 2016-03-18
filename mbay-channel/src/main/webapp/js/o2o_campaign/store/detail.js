/** qrcode is loaded* */
var qrCodeLoad = false;
/** qrcode is show* */
var qrShow = false;
/**
 * show QRCode
 * 
 * @param storeId
 * @param obj
 */
function showQRCode(storeId, obj) {
	if (qrShow) {
		$("#qrcode").hide();
		$(obj).text("查看");
		qrShow = false;
		return;
	}
	if (qrCodeLoad) {
		$("#qrcode").show();
		$(obj).text("取消");
		qrShow = true;
		return;
	}
	var qrUrl = ctx + "/qrcode/" + storeId + "/get.mbay";
	$("#qrcode").attr('src', qrUrl);
	$(obj).text("取消");
	qrCodeLoad = true;
	qrShow = true;
	return;
}

function deleteOperator(opeId, storeId, cellphone) {
	if (cellphone == null || cellphone == "") {
		return;
	}
	var postUrl = ctx + "/store_ope/delete.mbay";
	$.post(postUrl, {
		opeId : opeId,
		storeId : storeId
	}, function(result) {
		if (result.success) {
			alert(result.message);
			window.location.reload(false);
			// window.location.href = ctx + '/store/list.mbay';
		} else {
			// $.messager.alert({ title: "系统提示", content: "批量删除失败，请尝试逐条的删除!" });
			alert(result.message);
		}
	});
}

/**
 * 加入活动
 * 
 * @param {}
 *            storeId
 * @param {}
 *            activityId
 */
function joinActivity(storeId, campaignId) {
	alert(storeId + "--" + campaignId);
	var url = ctx + "/store/join_check.mbay";
	doPost(url, storeId, campaignId);
}
/**
 * 继续参加活动
 * 
 * @param {}
 *            storeId
 * @param {}
 *            activityId
 */
function continuejoinActivity(storeId, campaignId) {
	var url = ctx + "/store/continueJoin.mbay";
	doPost(url, storeId, campaignId);
}
/**
 * 退出活动
 * 
 * @param {}
 *            storeId
 * @param {}
 *            activityId
 */
function exitActivity(storeId, campaignId) {
	var url = ctx + "/store/exit.mbay";
	doPost(url, storeId, campaignId);
}
/**
 * 请求函数
 * 
 * @param {}
 *            url
 * @param {}
 *            storeId 门店Id
 * @param {}
 *            activityId 活动Id
 */
function doPost(url, storeId, campaignId) {
	$.post(url, {
		id : storeId,
		campaignId : campaignId
	}, function(success) {
		if (success) {
			window.location.reload(false);
		} else {
			$.messager.alert({ title: "系统提示", content: "参加活动失败!" });
		}
	});
}
