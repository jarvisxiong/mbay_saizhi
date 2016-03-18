function gotoUrl(url) {
	if (url) {
		if (url.indexOf("?") > 0) {
			url += "&_fg=";
		} else {
			url += "?_fg=";
		}
		url += Math.random();
		window.location.href = url; 
	}
}


//获取cookie
function getCookieValue(cookieName) {
	if (!cookieName) {
		return null;
	}
	// ie firefox
	if (document.cookie !== undefined) { 
		var cookies = unescape(document.cookie);
		var cookieIndex = cookies.indexOf(cookieName);
		
		if (cookieIndex != -1) {
			var cookieValueStart = cookieIndex + cookieName.length + 1;
			var cookieValueEnd = cookies.indexOf(";", cookieValueStart);
			if (cookieValueEnd == -1) {
				cookieValueEnd = cookies.length;
			}
			var cookieValue = cookies.substring(cookieValueStart, cookieValueEnd);
			if (cookieValue == 'undefined') return null;
			return cookieValue;
		}
		return null;
	} 
	// chrome
	else {
		return localStorage[cookieName];
	}
}
	
/*
 * 设置cookie
 * 
 * param(cookieName)  cookie名
 * param(cookieValue) cookie值
 * param(aliveDays)   cookie存活日期
 * param(path)        访问路径
 * param(domain)      跨域访问
 */
function setCookie(cookieName, cookieValue, aliveDays, path, domain) {
	// ie firefox
	if (document.cookie !== undefined) {
		var cookie = cookieName + " = " + escape(cookieValue);
		var now = new Date();
		now.setDate(now.getDate() + parseInt(aliveDays ? aliveDays : 0));
		
		cookie += ";expires=" + now.toGMTString();
		cookie += ";path=" + (path ? path : "/");
		cookie += domain ? ";domain=" + domain : "";
		
		document.cookie = cookie;
	} 
	// chrome
	else {
		localStorage[cookieName] = cookieValue;
	}
}