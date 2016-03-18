/*
 * IE Alert! jQuery plugin
 * version 1
 * author: David Nemes http://nmsdvid.com
 * http://nmsdvid.com/iealert/
 */

(function($){

function initialize(){

		var panel ='<div class="browser-con">' +
			'<div class="browser-body">' +
			'<h3>请您升级您的浏览器</h3>' +
			'<p>尊敬的用户，您现在使用的浏览器版本过低，请先升级后再访问</p>' +
			'<h5>您可以选择:</h5>' +
			'<ul>' +
			'<li class="fl"><a href="http://www.mozilla.org/en-US/firefox/new/" target="_blank"><div class="img fox"></div><div>Mozilla Firefox</div></a></li>' +
			'<li class="fl"><a href="http://www.google.cn/intl/zh-CN/chrome/browser/" target="_blank"><div class="img chrome"></div><div>Google Chrome </div></a></li>' +
			'<li class="fl nm"><a href="http://www.microsoft.com/china/windows/IE/upgrade/index.aspx" target="_blank"><div class="img ie"></div><div>Internet Explorer 8+</div></a></li>' +
			'</ul></div></div>';

        	$("body").prepend(panel);
			$("body").css({"height":100+'%',"overflow":"hidden"});
};


	$.fn.iealert = function(options){
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		var s;
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
			(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
				(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
					(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
						(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
		var ieversion;
		if (Sys.ie){
			ieversion = parseInt(Sys.ie);
			if(ieversion<9) {
				initialize();
			}
		}
	};
})(jQuery);

