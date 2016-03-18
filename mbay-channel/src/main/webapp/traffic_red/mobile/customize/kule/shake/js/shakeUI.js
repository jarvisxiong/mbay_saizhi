$(function() {
	share();
});

var remindShare = false;
function shakeAgain() { 
	if (remindShare && !shared) {
		$.messager.dialog({
			content: $("#share-popup").html(),
			fullScreen: true
		});
	} else {
		//开启页面摇一摇功能
		resetShakeAble();
	}
}