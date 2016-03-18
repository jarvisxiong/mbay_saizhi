<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="actx" value="${pageContext.request.contextPath}" />
<c:set var="stsctx" value="http://192.168.21.181:8003/mbay-statistics" />

<link rel="stylesheet" href="${actx}/css/mobile/mbayui.css">
<link rel="stylesheet" href="${actx}/css/mobile/share.css">

<script type="text/javascript" src="${actx}/webjars/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="${actx}/js/mobile/dialog.js"></script>
<script type="text/javascript" src="${actx}/js/mobile/RegExps.js"></script>
<script type="text/javascript" src="${actx}/js/mobile/adaptive.js"></script>
<script type="text/javascript" src="${actx}/js/mobile/touch.js"></script>
<script type="text/javascript" src="${actx}/js/mobile/common.js"></script>
<script type="text/javascript" src="${stsctx }/js/ds.js?delay"></script>

<script type="text/javascript">
var ctx='${pageContext.request.contextPath}';

// 自定义统计参数设置
$.ajax({
	url: ctx + "/tr_mobile/getCookieMobile.mbay",
	type: "get",
	data: {
		cnumber: "${param.number}" || "${param.cnumber}" || "${param.cNumber}"
	},
	success: function(resp) { 
		$.statisticsSetup({
			user: resp,
			clickMethod: "touchend",
			clickSelector: "[evt-touch],[ontouchend]"
		});
	}
});

(function() {
	var hm = document.createElement("script");
	hm.src = "//hm.baidu.com/hm.js?310d90fd185960eebcc2ee78163e8215";
	var s = document.getElementsByTagName("script")[0]; 
	s.parentNode.insertBefore(hm, s);
})();
</script>
