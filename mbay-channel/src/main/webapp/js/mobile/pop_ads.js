//显示灰色 jQuery 遮罩层 
function showBg() {
	var bh = $(".con").height();
	var bw = $(".con").width();
	$("#fullbg").css({
		height : bh,
		width : bw,
		display : "block"
	});
	$("#dialog").show();
}

// 关闭灰色 jQuery 遮罩
function closeBg() {
	$("#fullbg,#dialog").hide();
}

//显示灰色 jQuery 遮罩层 
function showBgAfter() { 
	var bh = $(".con").height(); 
	var bw = $(".con").width(); 
	$("#fullbg-after").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
	}); 
	$("#dialog-after").show(); 
	$('body,html').animate({scrollTop:0},1000);
} 

//关闭灰色 jQuery 遮罩 
function closeBgAfter() { 
	$("#fullbg-after,#dialog-after").hide(); 
} 