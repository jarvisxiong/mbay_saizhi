<script type="text/javascript" src="${ctx }/common/js/jquery/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx }/common/js/adaptive.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/common/js/common.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/common/js/dialog.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/common/js/RegExps.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/common/js/touch.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/common/js/wechat/WeixinJS-SDK.js?v=${version}"></script>
<script type="text/javascript" src="${ctx }/common/js/wechat/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${stsctx }/js/ds.js"></script>

<script type="text/javascript">
var ctx = "${ctx}";
var actx = "${actx}";

// 自定义统计参数设置
$.statisticsSetup({
	user: "${sessionScope.session_user.mobile}" || "${mobile}" || "${telephone}" 
			|| "${param.mobile}" || "${param.telephone}",
	clickMethod: "touchend",
	clickSelector: "[evt-touch],[ontouchend]" 
});

// 百度统计
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?310d90fd185960eebcc2ee78163e8215";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
