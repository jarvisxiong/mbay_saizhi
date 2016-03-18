<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="美贝直通车" />
<meta name="description" content="美贝直通车" />
<link rel="icon" href="<c:url value="/images/favicon.png"/>" />
<title>兑换说明</title>
<t:mobile-assets />
<style>
html,body,.wrap {
	position: relative;
	height: 100%;
	width: 100%;
}

body {
	font-size: 12px;
}

.wrap {
	background:
		url(${ctx}/images/campaign_common/mobile/redeem_instruction/bg.jpg)
		50% 50% no-repeat;
	background-size: cover;
	box-sizing: border-box;
	padding-top: 175px;
}

.nav {
	width: 85%;
	height: 32px;
	margin: 0 auto;
	border-bottom: 1px solid #FFF;
}

.nav a {
	display: block;
	color: #FFF;
	font-size: 14px;
	height: 35px;
	line-height: 35px;
	text-align: center;
	float: left;
}

.nav .nav_01 {
	width: 20%;
}

.nav .nav_02 {
	width: 60%;
}

.nav .nav_03 {
	width: 20%;
}

.nav .nav-cur {
	background:
		url(${ctx}/images/campaign_common/mobile/redeem_instruction/cur.png)
		bottom center no-repeat;
	background-size: 5px auto;
}

.tab {
	padding-top: 10px;
}

.tab_con {
	overflow: hidden;
	box-sizing: border-box;
	width: 85%;
	margin: 0 auto
}

.tab_con_item {
	display: none;
}

.tab_con_item:first-child {
	display: block;
}

.tab_con_item .tab_title {
	font-size: 14px;
	font-weight: bold
}

.tab_con_item p {
	margin: 5px 0;
	color: #FFF;
}

.tab_con_item p a {
	color: orange;
}

.footer {
	position: fixed;
	bottom: 15px;
	left: 0;
	width: 100%;
	text-align: center;
	color: #000;
}
</style>
</head>

<body>
	<div class="wrap">
		<div class="tab">
			<div class='tab_con'>
				<!--美贝说明-->
				<div class="tab_con_item">
					<p class="tab_title">感谢您参与本活动，成功领取MB后您可以发起如下操作:</p>
					<p>1.进入“活动发起者”官方微信或指定查询地址进行MB余额查询；</p>
					<p>2.进入“MB钱包”页面参与抽奖或其他活动；</p>
				</div>
			</div>
		</div>
		<div class="footer">Copyright © 2014 赛志科技沪ICP备13001400号-4号</div>
	</div>
</body>
</html>
<script>
   $(function(){
       $(".nav").on("touchend","a",function(){   // 给当然被触摸的a标签添加touchend 事件   > 事件委托
           var _index = $(this).index();  // 取当前被点击的
           $(this).addClass("nav-cur").siblings().removeClass("nav-cur");  // 让当前被点击的添加class 并让被点击的同辈元素去除class
           $(".tab_con").find(".tab_con_item").eq(_index).show().siblings(".tab_con_item").hide();  //让tab_con下面的第_index个显示  其他同辈元素隐藏掉
       });
   });
</script>
