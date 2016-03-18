$(document).ready(function() {
	(function(doc, win) {
		// 根据页面宽度不同 计算相应的大小
		var docEl = doc.documentElement, resizeEvt = 'orientationchange' in window ? 'orientationchange'
				: 'resize', recalc = function() {
			var clientWidth = docEl.clientWidth;
			var fontSize;
			if (!clientWidth)
				return;
			fontSize = clientWidth > 640 ? 40
					: 20 * (clientWidth / 320);
			docEl.style.fontSize = fontSize + 'px';
		};
		if (!doc.addEventListener)
			return;
		win.addEventListener(resizeEvt, recalc, false);
		doc.addEventListener('DOMContentLoaded', recalc, false);
	})(document, window);
});

function submit_before() {
	var Phone_number = $(".cell_phone_number").val();
	if (Phone_number.length <= 0) {
		$.messager.remind({content : "请输入手机号"});
		return;
	} 
	var myreg = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/;
    if(!myreg.test(Phone_number)){
    	$.messager.remind({content : "请输入正确的手机号"});
    	return;
    }
	$("#pinganForm").submit();
}