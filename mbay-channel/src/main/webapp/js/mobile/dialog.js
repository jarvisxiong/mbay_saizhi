(function($) {
	
// 基本配置
var defConfig = {
	content: '',
	shadow: true,              // 是否背景阴影
	shadowCss: {},
	afterShow: $.noop,         // 弹出层已显示后的回调
	afterClose: $.noop,        // 关闭窗口后的回调
	autoClose: false,          // 是否自动关闭/点击屏幕任意地方是否关闭窗口
	delay: 0,                  // 延时关闭毫秒数，只有autoClose=true时才有效
	fullScreen: false,         // 是否全屏
	contentCss: {},
	button: {
		close: {
			visible: false
		},
		dialogClose: {
			visible: false
		}
	}
};

// 自定义
$.messager = {
	closeDialog: function(thiz) {
		if (thiz) {
			closeDialog($(thiz).closest(".messager_dialog"));
		} else {
			closeDialog();
		}
	},
	dialog: function(config) {
		var cfg = $.extend(true, {}, defConfig, config);
		drawDialogWindow(cfg);
	},
	remind: function(config) {
		var remindConfig = {
			shadowCss: {
				"background-color": "rgba(0,0,0,0)"
			},
			autoClose: true,
			delay: 2000
		};
		var cfg = $.extend(true, {}, defConfig, remindConfig, config);
		drawDialogWindow(cfg);
	}
};	

function drawDialogWindow(config) { 
	// 生成html
    var mh = '<div class="messager_dialog">'; 
	if (config.shadow === true) {
		mh += '<div class="window_shadow"></div>';
	}
	if (config.button.close.visible === true) {
		mh += '<span class="close-btn">X</span>';
	}
	mh += '<div class="popup-cont">';
	if (config.button.dialogClose.visible === true) {
		mh += '<span class="close-btn">X</span>';
	}
	mh += '<div class="pop-wrapper">' + config.content + '</div></div></div>';
	var $mh = $(mh);
	
	// 调整样式
	if (config.fullScreen === true) {
		$mh.find(".popup-cont").addClass("full-screen");
	}
	if (config.shadow === true) {
		$mh.find(".window_shadow").css(config.shadowCss);
	}
	$mh.find(".popup-cont").css(config.contentCss);
	
	// 垂直居中，处理滚动条
	if (document.body.scrollTop > 0) {
		$mh.find(".popup-cont").css("margin-top", document.body.scrollTop + "px");
	}
	
	// 水平居中处理，处理滚动条
	if (document.body.scrollLeft > 0) {
		$mh.find(".popup-cont").css("margin-left", document.body.scrollLeft + "px");
	}
	
	// 加入文档
	$mh.appendTo(document.body);
	
	// 关闭按钮
	if (config.button.close.visible === true
			|| config.button.dialogClose.visible === true) {
		$mh.find(".close-btn").on("touchend", function() {
			closeDialog($mh);
		});
	}
	
	// 显示完成回调
	config.afterShow($mh);
	
	// 自动关闭
	if (config.autoClose === true) {
		var timeout = 0;
		
		// 延时关闭
		if (config.delay > 0) {
			timeout = setTimeout(function() {
				closeDialog($mh);
			}, config.delay);
		}
		
		// 点击
		$mh.find(".window_shadow").bind("touchend", function() {
			clearTimeout(timeout);
			closeDialog($mh);
		});
	}
	
	// 将关闭回调保存到弹出层元素
	$mh.data("afterClose", config.afterClose);
}

//去除窗口
function closeDialog(ele) {
	var afterClose;
	if (ele) {
		afterClose = $(ele).data("afterClose");
		if (typeof(afterClose) == "function") {
			afterClose($(ele));
		}
		$(ele).remove();
	} else {
		afterClose = $(".messager_dialog").data("afterClose"); 
		if (typeof(afterClose) == "function") {
			afterClose($(".messager_dialog"));
		}
		$(".messager_dialog").remove();
	}
}

/* 内部样式 */
var innerStyle = {
	".messager_dialog .window_shadow": {
		position: "fixed", 
		"z-index": 99999,
		width: "100%",
		height: "100%",
		top: 0,
		right: 0,
		bottom: 0,
		left: 0,
		"-webkit-transition": "opacity .3s ease-out",
		"-moz-transition": "opacity .3s ease-out",
		"-o-transition": "opacity .3s ease-out",
		transition: "opacity .3s ease-out",
		background: "rgba(51, 92, 113, .7)",
		display: "block"
	},
	".messager_dialog .popup-cont": {
		width: "75%",
		"border-radius": "5px",
		position: "absolute",
		top: "50%",
		left: "50%",
		color: "#fff",
		background: "rgba(0, 0, 0, .7)",
		"z-index": 100000,
		"font-size": "16px",
		"white-space": "normal",
		"word-break": "break-all",
		display: "block",
		"-moz-transform": "translate(-50%, -50%)",
		"-webkit-transform": "translate(-50%, -50%)",
		"-o-transform": "translate(-50%, -50%)",
		"transform": "translate(-50%, -50%)"
	},
	".messager_dialog .popup-cont .pop-wrapper": {
		padding: "8px",
	},
	".messager_dialog .full-screen": {
		width: "100%",
		height: "100%",
		top: 0,
		left: 0,
		padding: "0",
		"border-radius": 0,
		"-moz-transform": "translate(0, 0)",
		"-webkit-transform": "translate(0, 0)",
		"-o-transform": "translate(0, 0)",
		"transform": "translate(0, 0)"
	},
	".messager_dialog .full-screen .pop-wrapper": {
		padding: 0
	},
	".messager_dialog .close-btn": {
		position: "absolute",
		top: "5px",
		right: "5px",
		border: "1px solid #FFF",
		"border-radius": "20px",
		height: "25px",
		width: "25px",
		"line-height": "25px",
		"font-family": "Arial",
		"text-align": "center",
		color: "#fff",
		"z-index": 100000,
		"font-size": "14px" 
	}
};

// 内部样式写入文档
var cssStr = "<style type='text/css'>";
for (var style in innerStyle) {
	cssStr += style + "{";
	for (var ele in innerStyle[style]) {
		cssStr += ele + ":" + innerStyle[style][ele] + ";";
	}
	cssStr += "}";
}
cssStr += "</style>";
document.write(cssStr);

})(jQuery);