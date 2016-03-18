(function (doc, win) { 
    var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
            	// 调整根元素字体大小
                var clientWidth = docEl.clientWidth;
                var fontSize;
                if (clientWidth) {
                	fontSize = clientWidth > 640 ? 40 : 20 * (clientWidth / 320);
                	docEl.style.fontSize = fontSize + 'px';
                }
            };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);