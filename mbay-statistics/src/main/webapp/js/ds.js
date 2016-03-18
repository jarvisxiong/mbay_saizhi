/**
 * 数据统计
 * 
 * 参数：
 * user：用户
 * clickMethod：页面元素点击统计使用方式，默认：PC-mousedown，移动端-touchend
 * clickSelector：指定哪些元素点击事件会被统计，jquery选择语法，默认为a,:button。注意：动态产生的元素无法统计
 * 
 * 用法：
 * 1）使用js配置参数
 * $.statisticsSetup({
 * 	   user: "${mobile}" || "${telephone}" || "${param.mobile}" || "${param.telephone}"
 * });
 * 
 * 当配置参数使用ajax获取时，可能会导致部分事件统计时未能获取到所需参数，如：
 * $.ajax({
 *		url: "xxx",
 *		success: function(resp) { 
 *			$.statisticsSetup({
 *				user: resp
 *			});
 *		}
 *  });
 * 此时应使用延时设置，如下：
 * <script type="text/javascript" src="${stsctx }/js/ds.js?delay"></script>
 * 在引用js文件时添加delay属性，此属性保证$.statisticsSetup执行完后才进行统计
 * 
 * 2）当超链接a内部不是简单文本时，可在某一子元素使用statistics-text类指定超链接名称
 * <a href="javascript:void(0)">
 *     <div>
 * 	       <h2 class="statistics-text">收藏MB钱包</h2>
 *	       <img src="${actx }/page/main/img/erweima.jpg">
 *     </div>
 * </a>
 * 
 * 3）当超链接a内部没有任何元素时，可使用statistics-text属性指定超链接名称，
 * 且statistics-text属性优先级高于子元素statistics-text类
 * <a href="javascript:void(0)" statistics-text="返回"></a>
 * 
 * @param window
 * @param $
 */
(function(window, $) {
	
var _path = "http://192.168.21.181:8003/mbay-statistics";
var _url = encodeURI(window.location.href);
var _user = null;
var _clickMethod = isPC() ? "mousedown" : "touchend"; 
var _clickSelector = "a,:button";

function ReqData() {
	this.user = _user;
	this.url = _url;
}

/**
 * 全局参数设置
 * 
 * 优先级高于从页面加载参数值
 */
$.extend({
	/**
	 * 全局参数设置
	 * 
	 * @param opts {user, clickMethod, clickSelector}
	 */
	statisticsSetup: function(opts) {
		if (typeof(opts) == "object") {
			_user = opts.user || _user;
			_clickMethod = opts.clickMethod || _clickMethod;
			_clickSelector = (opts.clickSelector && (opts.clickSelector + "," + _clickSelector)) || _clickSelector;
		}
		
		// 如客户端引用声明了delay参数，则部分事件在$.statisticsSetup执行完后执行
		if (getScriptParam("delay")) {
			// url进入事件
			urlEnter();
		}
	}
});
	
$(function() {  
	// 客户端引用未声明delay参数，立即执行
	if (!getScriptParam("delay")) {
		// url进入事件
		urlEnter();
	}
	
	// 按钮、链接点击事件
	$(_clickSelector).on(_clickMethod, elementClick);
});

/**
 * 取得script参数
 */
function getScriptParam(pm) {
	var dsSrc = $("script[src*='ds.js']").attr("src");
	return dsSrc.indexOf(pm) > 0;
}

function isPC() {
	var userAgentInfo = navigator.userAgent;
	var Agents = 
		[	
		 	/.*Android.*/i, /.*iPhone.*/i, 
		 	/.*SymbianOS.*/i, /.*Windows Phone.*/i,
	        /.*iPad.*/i, /.*iPod.*/i 
	    ];
	for (var v = 0; v < Agents.length; v++) {
	    if (Agents[v].test(userAgentInfo)) {
	        return false;
	    }
	}
	return true;
}

function formatUrl(url) {
	return _path + url + "?f=" + Math.random();
}

function urlEnter() {
	var data = new ReqData();
	$.ajax({
		url: formatUrl("/main/urlEnter.mbay"),
		cache: false,
		type: "POST",
		data: $.param(data),
		dataType: "jsonp"
	});
}

function elementClick() { 
	var type = $(this).is("a") ? "LINK" : $(this).is(":button") ? "BUTTON" : "OTHER";
	var name = $(this).is("input") ? $(this).val() : getElementText(this);	
	var data = new ReqData(); 
	data.type = type;
	data.name = encodeURI(name);
	$.ajax({
		url: formatUrl("/main/elementClick.mbay"),
		cache: false,
		type: "POST",
		data: $.param(data),
		dataType: "jsonp"
	});
}

function getElementText(ele) {
	var $ele = $(ele);
	// 先获取自身属性
	if ($ele.attr("statistics-text")) {
		return $ele.attr("statistics-text");
	}
	// 获取子元素类
	if ($ele.has(".statistics-text").size() > 0) {
		return $ele.find(".statistics-text:first").text();
	} else {
		return $ele.text();
	}
}
	
})(window, jQuery);