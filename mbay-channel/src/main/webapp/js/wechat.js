 (function ($) {
    $.wechat = {
		 hide : function() {
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			wx.ready(function(){
				// 隐藏分享操作
				wx.hideOptionMenu();
			});
		 },
		 share : function(share, fn) {
		 	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			wx.ready(function(){
				//显示右上角菜单接口
				wx.showOptionMenu();
				//隐藏所有非基础按钮接口
				wx.hideAllNonBaseMenuItem();
				
				//批量显示功能按钮接口
				wx.showMenuItems({
				    menuList: [
				              	'menuItem:share:appMessage',
				              	'menuItem:share:timeline'
				              ] // 要显示的菜单项
				});
				
				//分享给朋友
				wx.onMenuShareAppMessage({
				    title: share.shareTitle, // 分享标题
				    desc: share.content, // 分享描述
				    link: share.shareLink, // 分享链接
				    imgUrl: share.shareImage, // 分享图标
				    type: 'link', // 分享类型,music、video或link，不填默认为link
				    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
				    success: function () { 
				        // 用户确认分享后执行的回调函数
				    	fn();
				    },
				    fail: function (){
				    	$.messager.alert({ content: "分享失败,不要紧,可能是网络问题,一会儿再试试?" });
				    }
				});
				
				//分享到朋友圈
				wx.onMenuShareTimeline({
				    title: share.shareTitle, // 分享标题
				    link: share.shareLink, // 分享链接
				    imgUrl: share.shareImage, // 分享图标
				    success: function () { 
				        // 用户确认分享后执行的回调函数
				    	fn();
				    },
				    fail: function (){
				    	$.messager.alert({ content: "分享失败,不要紧,可能是网络问题,一会儿再试试?" });
				    }
				});
				
			});
		 }
	};
    
    // 默认隐藏
    $.wechat.hide();
})(window.jQuery);
 
 /**
 * 基础配置(要想使用其他功能必须先执行这个)
 */
$(function() {
	$.get(ctx+"/wechat/config_info.mbay",{url: window.location.href},function(result){	
		//微信js相关配置
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: result.appId, // 必填，公众号的唯一标识
		    timestamp: result.timestamp, // 必填，生成签名的时间戳
		    nonceStr: result.nonceStr, // 必填，生成签名的随机串
		    signature: result.signature,// 必填，签名，见附录1
		    jsApiList: [
		                'hideOptionMenu',
						'showOptionMenu',
		                'hideAllNonBaseMenuItem',
		                'showMenuItems',
		                'onMenuShareAppMessage',
		                'onMenuShareTimeline'
		               ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		wx.error(function(res){
			//$.messager.alert({ content: "微信JS_SDK配置错误,错误信息: " + res.errMsg });
		});
	});
});