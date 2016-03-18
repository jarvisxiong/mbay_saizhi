<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<title>美贝钱包</title>
<style>
* {
	margin: 0;
	padding: 0;
}

ul,ol,li {
	list-style: none;
}

html,body {
	width: 100%;
	height: 100%;
	font-size: 12px;
	font-family: 'Microsoft YaHei';
}

.fl {
	float: left
}

.fr {
	float: right
}

.con {
	height: 100%;
	width: 100%;
	background: #fff;
	background-size: cover;
	overflow: hidden
}

.body_con {
	width: 90%;
	margin: 5%;
	height: 95%
}

.show_area {
	width: 60%;
	margin: 0px 5%;
	margin-bottom: 10%
}

.logo {
	width: 100%;
	height: 40px;
	background: #f3f3f3;
	margin-bottom: 5%
}

.ad_area {
	width: 15%;
	height: 360px;
	background: #f3f3f3
}

.erwei {
	text-align: center;
	line-height: 50px;
	margin-bottom: 5%
}

.erwei img {
	width: 180px;
	height；180px
}

.confirm {
	text-align: center;
}

.confirm button {
	width: 60%;
	padding: 0px 5px;
	height: 26px;
	letter-spacing: 1px;
	border-radius: 4px;
	color: #FFF;
	font-family: 'Microsoft YaHei';
	cursor: pointer;
	background: #FDAC1C
}

.prev_ad {
	width: 100%;
	height: 50px;
	line-height: 50px;
	background: #f3f3f3;
	clear: left;
}
</style>
</head>
<body>
	<section class='con'>
	<div class='body_con'>
		<div class='ad_area fl'>预设广告区</div>
		<!--展示区域-->
		<div class='show_area fl'>

			<c:if test="${! empty code}">
				<div class='logo'>恭喜您获得红包</div>
				<div class='erwei'>
					<img border="0" alt="qrcode"
						src="${ctx}/qrcode/get.mbay?code=${code}" />
				</div>
				<div class='confirm'>
					<button>长按二维码保存到手机</button>
				</div>
			</c:if>
		</div>
		<div class='ad_area fr'>预设广告区</div>
		<div class='prev_ad'>预设广告区</div>
	</div>
	</section>
</body>
</html>