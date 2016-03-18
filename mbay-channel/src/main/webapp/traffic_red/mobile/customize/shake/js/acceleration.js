//JavaScript Document
//摇一摇动画部分 
function getStyle(obj, attr){
	if(obj.currentStyle)
	{
		return obj.currentStyle[attr];
	}
	else
	{
		return getComputedStyle(obj, false)[attr];
	}
}
function move(obj, json, fn){
	clearInterval(obj.timer);
	obj.timer=setInterval(function(){
		var bStop=true;
		for(var attr in json)
		{
			var iCur=0;
			if(attr=='opacity')	
			{
				iCur=parseInt(parseFloat(getStyle(obj, attr)*100));
			}
			else
			{
				iCur=parseInt(getStyle(obj, attr));
		    }
		    var iSpeed=(json[attr]-iCur)/8;
		    if(iSpeed>0)
		    {
			     iSpeed=Math.ceil(iSpeed);
		    }
		    else
		    {
			    iSpeed=Math.floor(iSpeed);
		    }
			if(iCur!=json[attr])
			{
				bStop=false;
			}
		    if(attr=='opacity')
			{
				obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
				obj.style.opacity=(iCur+iSpeed)/100;
			}
			else
			{
				obj.style[attr]=iCur+iSpeed+'px';
			}
		}
		if(bStop==true)
		{
			clearInterval(obj.timer);
			    if(fn)
			    {fn();}
		}
		//速度
	},22);
}

function noneheadads() {  
	var mobile = $("#mobile").val();
	var cnumber = $("#cnumber").val();
	
	$.messager.closeDialog();
	
	// 重置参数
	remindShare = false;
	shared = false;
	
	var url = ctx + "/tr_mobile/redeem/before.mbay";
	$.getJSON(
		url, 
		{
			number: cnumber,
			mobile: mobile
		}, 
		function(resp) {
			// 记录cookie
			if (resp.status) {
				// 延时，是声音正常播放
				setTimeout(function() {
					// 进入产品
					var url = ctx + "/tr_mobile/";
					if (resp.data.type == 'TRAFFIC') {
						url = url
								+ "result_traffic_pack.mbay?"
								+ "recordId="
								+ resp.data.recordId
								+ "&size="
								+ resp.data.size
								+ "&cnumber="
								+ resp.data.cNumber;
					} else if (resp.data.type == 'MBAY') {
						url = url
								+ "result_mbay_pack.mbay?"
								+ "recordId="
								+ resp.data.recordId
								+ "&size="
								+ resp.data.size
								+ "&cnumber="
								+ resp.data.cNumber
								+ "&serialNumber="
								+ resp.data.serialNumber;
					}
					window.location.href = url;
				}, 500);
			} else {
				if (resp.code == "SHAKE_AGAIN") { 
					$("#shakeagain").show();
				} else if (resp.code == "SHAKE_REMIND") {
					remindShare = true;
					$("#shakeagain").show();
				} else {
					$("#shakeagain").hide();
				}
				$(".tip-text").html(resp.content);
				var tishi = $("#shakeTipDiv").html();
				$.messager.dialog({ content: tishi });	
			}
		}
	);
}


//摇一摇时间控制
function yaoyiyaotime(){$("body").append('<audio src="'+ctx+'/traffic_red/mobile/shake/music/shake_match.mp3" autoplay="autoplay"></audio>');}
/*摇一摇同时声音时间*/
function yaoyiyao(){$("body").append('<audio src="'+ctx+'/traffic_red/mobile/shake/music/shake.mp3" autoplay="autoplay"></audio>'); }
var shaked=false;
$(document).ready(function() {
	function phoneShake(){
		if(!shaked){
			shaked=true;
			noneheadads();
			yaoyiyao();
		}
	}
	$.shake({
		callback: function(){
			phoneShake();
		}
	});
});

function resetShakeAble(closeDialog){
	if (closeDialog === undefined) {
		closeDialog = true;
	}
	if (closeDialog) {
		$.messager.closeDialog();
	}
	shaked=false;
}

