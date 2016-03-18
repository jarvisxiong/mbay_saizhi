/**
 * 页面加载会预处理evt-touch，evt-win-touch等元素
 * 
 * evt-touch：绑定touchend事件，非委托。若元素被动态处理导致事件丢失（如移动元素到弹出层），触摸事件将失效，
 * 有以下3种处理方式：
 * 1）不在页面使用evt-touch，在js中使用委托，$(ele).touch(selector, callback)
 * 2）evt-touch + delegate-id，被委托元素（如body）delegate
 * 3）evt-win-touch，此方式将事件委托给window.document对象
 * 
 * @param window
 * @param $
 */
(function(window, $) {
	
// 触摸坐标
var clientX = -1;
var clientY = -1;

// 定时器标识
var tflag = null;

$.fn.extend({
	touch: function(sel, callback, /*internal*/ one) {  
		// 参数处理
		if (typeof(sel) == "function") {
			if (typeof(callback) == "boolean") {
				one = callback;
				callback = sel;
				sel = null;
			} else {
				callback = sel;
				sel = null;
				one = false;
			}
		} else {
			if (typeof(one) != "boolean") {
				one = false;
			}
		}

		return this.each(function() {
			var $thiz = $(this);

			var moveFn = function(e) {
				if (e.touches) {
					var x = e.touches[0].clientX;
					var y = e.touches[0].clientY;
					if (Math.abs(x - clientX) > 10 || Math.abs(y - clientY) > 10) {
						$thiz.addClass("touchmove-flag");
						clearTimeout(tflag);
						tflag = setTimeout(function() {
							$thiz.removeClass("touchmove-flag");
						}, 100);
					}
				} 
			};
				
			var startFn = function(e) {
				if (e.touches) {
					clientX = e.touches[0].clientX;
					clientY = e.touches[0].clientY;
				}
			};

			// 最终绑定
			$thiz.on("touchend", sel, function(event) { 
				if (!$thiz.hasClass("touchmove-flag") && !$thiz.hasClass("touchend-flag")) {   
					// 200ms内禁止点击
					$thiz.addClass("touchend-flag");
					setTimeout(function() { 
						$thiz.removeClass("touchend-flag");
					}, 200);
					
					// 执行回调
					callback.call(this, event);

					// one事件
					if (one == true) {
						$thiz.off("touchend");
						rmEvent(this, "touchstart", startFn);
						rmEvent(this, "touchmove", moveFn);
					}
				}
				
				// 清除移动标志
				$thiz.removeClass("touchmove-flag");
			}); 
		
			// 绑定touchstart，记录初始触摸坐标
			addEvent(this, "touchstart", startFn);
			
			// 绑定touchmove，当移动距离在指定范围外加标志
			// 目的：在触摸移动时不触发touchend事件
			// 浏览器不同，touchmove事件完后touchend事件可能执行也可能不执行
			addEvent(this, "touchmove", moveFn);
		});
	},

	oneTouch: function(sel, callback) {
		// 参数处理
		if (typeof(sel) == "function") {
			callback = sel;
			sel = null;
		} 
		this.touch(sel, callback, true);
	}
});

/*
 * js原生事件
 */
function addEvent(ele, type, fn) {
	if (ele) {
		if (ele.addEventListener) {
			ele.addEventListener(type, fn, false);
		} else if (ele.attachEvent) {
			ele.attachEvent("on" + type, fn);
		}
	}
}

/*
 * 移除事件
 */
function rmEvent(ele, type, fn) {
	if (ele) {
		if (ele.removeEventListener) {
			ele.removeEventListener(type, fn, false);
		} else if (ele.detachEvent) {
			ele.detachEvent("on" + type, fn);
		}
	}
}

/*---------------------------------------------------
 *                     页面预处理
 *--------------------------------------------------*/

$(function() { 
	$("[evt-touch]").not("[delegate-id]").each(function() {
		$(this).touch(function() {
			eval($(this).attr("evt-touch"));
		});
	});
	
	$("[delegate]").each(function() {
		var $thiz = $(this);
		var dlgs = $thiz.attr("delegate").split(/[\s]/);
		
		$.each(dlgs, function(i, n) {
			$thiz.touch("[delegate-id='" + n + "']", function() {
				eval($("[delegate-id='" + n + "']").attr("evt-touch"));
			});
		});
	});
	
	$("[evt-win-touch]").each(function() {
		var fg = uuid();
		$(this).attr("evt-flag", fg);
		$(window.document).touch("[evt-flag=" + fg + "]", function() {
			eval($(this).attr("evt-win-touch"));
		});
	});
});

function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; 
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
} 
	
})(window, jQuery);