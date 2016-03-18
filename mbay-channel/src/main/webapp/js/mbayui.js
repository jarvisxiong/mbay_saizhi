(function($) {
	
	// 基本配置
	var defConfig = {
		title: '提示',
		content: '',
		draggable: true,   // 是否可拖拽，依赖jquery.ui插件
		shadow: true,      // 是否背景阴影
		afterShow: $.noop, // 弹出层已显示后的回调
		css: {
			width: undefined,  // 数值类型px
			height: undefined  // 数值类型px
		},
		button: {
			ok: {
				visible: true,
				text: '确定',
				callback: $.noop,
				autoClose: true   // 点击ok按钮后是否自动关闭
			},
			cancel: {
				visible: false,
				text: '取消',
				callback: $.noop
			}
		}
	};
	
	$.messager = {
		remind: function(content) {
			var htm = "<div class='float_tip green_skin'>" + content + "</div>";
			$(htm).appendTo("body");	
			setTimeout(function() { $('.float_tip').remove(); }, 1200);
		},
		warning: function(content) {
			var htm = "<div class='float_tip yellow_skin'>" + content + "</div>";
			$(htm).appendTo("body");	
			setTimeout(function() { $('.float_tip').remove(); }, 1200);
		},
		alert: function(config) {
			var cfg = $.extend(true, {}, defConfig, config);
			drawWinow(cfg);
		},
		confirm: function(config) {
			var confirmConfig = {
				button: {
					cancel: {
						visible: true
					}
				}
			};
			var cfg = $.extend(true, {}, defConfig, confirmConfig, config);
			drawWinow(cfg);
		},
		progress: function(title, content) {//进度条
			drawWinowWithoutButton(title, content);
		},
		dialog: function(config) {
			var confirmConfig = {
				button: {
					ok: {
						visible: false 
					}
				}
			};
			var cfg = $.extend(true, {}, defConfig, confirmConfig, config);
			drawWinow(cfg);
		},
		closeDialog: function() {
			closeDialog();
		}
	};
	
	function drawWinow(config) {
		var mh = '<div class="messager_dialog" >'; 
		if (config.shadow === true) {
			mh += '<div class="window_shadow"></div>';
		}
		if (config.css.width) {
			mh += '<div class="dialog" style="width:' + config.css.width + 'px" >';			
		} else { 
			mh += '<div class="dialog">';
		}
		mh += '<p class="close"><span class="fl">' + config.title
				+ '</span><a href="javascript:void(0)" title="关闭" class="fr close_text">关闭</a></p>';
		mh += '<div class="dialog_con"';
		if (config.css.height) {
			mh += 'style="overflow-y:auto; height:' + config.css.height + 'px;"';
		}
		mh += '>' + config.content + '</div>';
		if (config.button.cancel.visible === true 
				|| config.button.ok.visible === true) {
			mh += '<p class="confirm">';
			if (config.button.cancel.visible === true) {
				mh += '<button class="cancel_btn">' + config.button.cancel.text + '</button>';
			}
			if (config.button.ok.visible === true) {
				mh += '<button class="ok_btn">' + config.button.ok.text + '</button>';
			}
			mh += '</p>';
		}
		mh += '</div></div>';
		var $mh = $(mh);
		$mh.appendTo("body");
		
		// 调整垂直居中
		var height = $mh.find(".dialog").height();
		$mh.find(".dialog").css("margin-top", "-" + height / 2 + "px");
		
		// 事件绑定
		$mh.find(".window_shadow, .close_text").bind("click", function() {
			closeDialog($mh);
		});
		
		// 窗口拖动
		if (config.draggable === true && $.fn.draggable) {
			$mh.find(".dialog").draggable();
		}
		
		// 取消
		if (config.button.cancel.visible === true) {
			$mh.find(".cancel_btn").bind("click", function() {
				config.button.cancel.callback($mh);
				closeDialog($mh);
			});
		}
		
		// 确定
		if (config.button.ok.visible === true) {
			$mh.find(".ok_btn").bind("click", function() {
				config.button.ok.callback($mh);
				if (config.button.ok.autoClose === true) {
					closeDialog($mh);
				}
			});
		}
		
		// 显示完成回调
		config.afterShow($mh);
	}
	
	//绘制窗口(无button版)
	function drawWinowWithoutButton(title, content) {
		var mh = '<div class="messager_dialog"><div class="window_shadow"></div>';// 屏幕遮罩
		mh += '<div class="dialog" style="top:70%;">';
		mh += '<p class="close"><span class="fl">' + title + '</span></p>';
		mh += '<div class="dialog_con"><img src="../js/echo/loading.gif" ' 
				+'style="width: 20px;height: 20px;margin-bottom: 5px;"/>' + content + '</div>';
		mh += '</div></div>';
		$(mh).appendTo("body");	
		
		// 调整垂直居中
		var height = $(mh).find(".dialog").height();
		$(mh).find(".dialog").css("margin-top", "-" + height / 2 + "px");
	}
	
	//去除窗口
	function closeDialog(ele) {
		if (ele) {
			$(ele).remove();
		} else {
			$(".messager_dialog").remove();
		}
	}
	
	$(window).resize(function() {
		if($('.window_shadow').is(':visible')){
			var bh = $(document).height();
			var bw = $(document).width();
			$(".window_shadow").css({
				height : bh,
				width : bw,
				display : "block"
			});
			$(".close_text").css({
				color:"#000000"
			});
		}		
	});
})(jQuery);

$(function(){
	/**文本框取消自动填充**/
	$("input[type='text']").attr("autocomplete","off");
	
	//$(".onlynum").keydown(function(){if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)
	//&&!(event.keyCode==39)) if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) event.returnValue=false; });
	//文本框只能输入数字处理。
	$(".onlynum").keypress(function(event) { 
        var keyCode = event.which; 
        if (keyCode == 8 || (keyCode >= 48 && keyCode <=57)) 
            return true; 
        else 
            return false; 
    }).focus(function() { 
        this.style.imeMode='disabled'; 
    }); 
});

var validateRegExp = {
    decmal: /^([+-]?)\\d*\\.\\d+$/,
    // 浮点数
    decmal1: /^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$/,
    // 正浮点数
    decmal2: /^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$/,
    // 负浮点数
    decmal3: /^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$/,
    // 浮点数
    decmal4: /^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$/,
    // 非负浮点数（正浮点数 + 0）
    decmal5: /^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$/,
    // 非正浮点数（负浮点数 +
    // 0）
    intege: /^-?[1-9]\\d*$/,
    // 整数
    intege1: /^[1-9]\\d*$/,
    // 正整数
    intege2: /^-[1-9]\\d*$/,
    // 负整数
    num: /^([+-]?)\\d*\\.?\\d+$/,
    // 数字
    num1: /^[1-9]\\d*|0$/,
    // 正数（正整数 + 0）
    num2: /^-[1-9]\\d*|0$/,
    // 负数（负整数 + 0）
    ascii: /^[\\x00-\\xFF]+$/,
    // 仅ACSII字符
    chinese: /^[\\u4e00-\\u9fa5]+$/,
    // 仅中文
    color: /^[a-fA-F0-9]{6}$/,
    // 颜色
    date: /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
    // 日期
    email: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
    // 邮件
    idcard: /^[1-9]([0-9]{14}|[0-9]{17})$/,
    // 身份证
    ip4: /^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/,
    // ip地址
    letter: /^[A-Za-z]+$/,
    // 字母
    letter_l: /^[a-z]+$/,
    // 小写字母
    letter_u: /^[A-Z]+$/,
    // 大写字母
    mobile: /^0?(13|15|18|14|17)[0-9]{9}$/,
    // 手机
    notempty: /^\\S+$/,
    // 非空
    password: /^.*[A-Za-z0-9\\w_-]+.*$/,
    // 密码
    fullNumber: /^[0-9]+$/,
    // 数字
    picture: "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",
    // 图片
    qq: /^[1-9]*[1-9][0-9]*$/,
    // QQ号码
    rar: "(.*)\\.(rar|zip|7zip|tgz)$",
    // 压缩文件
    tel: /^[0-9\-()（）]{7,18}$/,
    // url
    username: /^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$/,
    // 户名
    deptname: /^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$/,
    // 邮编
    realname: /^[A-Za-z\\u4e00-\\u9fa5]+$/,
    // 真实姓名
    companyname: /^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$/,
    companyaddr: /^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$/
};
