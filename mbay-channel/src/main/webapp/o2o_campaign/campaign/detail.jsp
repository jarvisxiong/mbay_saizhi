<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<t:mobile-assets />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<title>领取红包</title>
<script type="text/javascript">
	$(function() {
		$("#getCode").click(
				function() {
					//alert("okkkk");
					var cellPhone = $("#cellPhone").val();
					//alert(cellPhone);
					var storeId = $("#storeId").val();
					var campaignId = $("#campaignId").val();
					if (cellPhone == "") {
						return;
					}
					//alert(cellPhone + "--" + storeId + "---" + campaignId);
					var postUrl = ctx + "/campaign/join/join.mbay";
					$.post(postUrl, {
						'cellPhone' : cellPhone,
						'campaignId' : campaignId,
						'storeId' : storeId
					}, function(result) {
						if (result.success) {
							window.location.href = ctx
									+ "/campaign/join/success.mbay?storeId="
									+ result.error_code + "&code="
									+ result.message;
						} else {
							$.messager.alert({ 
								title: "系统提示", 
								content: "错误代码:" + result.error_code + "<br/>错误提示:" + result.message 
							});
						}
					});
				});
	});
</script>

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

.act_url {
	text-align: center;
	height: 30px;
	width: 100%;
	background: #f3f3f3;
	line-height: 30px;
	margin-bottom: 5%
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

.act_tit {
	height: 50px;
	text-align: center;
	line-height: 50px;
	background: #f3f3f3;
	margin-bottom: 5%
}

.act_con {
	height: 150px;
	background: #f3f3f3;
	padding: 2%;
	margin-bottom: 5%
}

.act_txt {
	margin-bottom: 5%
}

.act_txt input {
	width: 94%;
	border: 1px solid #94ABC4;
	line-height: 20px;
	padding: 5px 5px;
	border-radius: 4px
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
		<div class='act_url'>${campaign.link }</div>

		<div class='ad_area fl'>预设广告区</div>
		<!--展示区域-->
		<div class='show_area fl'>
			<div class='logo'>logo部分</div>
			<div class='act_tit'>活动编号：&nbsp;&nbsp;&nbsp;${campaign.number }</div>
			<div class='act_tit'>活动名称：&nbsp;&nbsp;&nbsp;${campaign.name }</div>
			<div class='act_con'>${campaign.describtion }</div>
			<div>
				<input type="hidden" id="storeId" value="${storeId }"><input
					type="hidden" id="campaignId" value="${campaign.id }">
			</div>
			<div class='act_txt'>
				<input type='text' id="cellPhone" placeholder='请填写手机号'>
			</div>
			<div class='confirm'>
				<button id="getCode" type="button">确定提交</button>
			</div>
		</div>
		<div class='ad_area fr'>预设广告区</div>
		<div class='prev_ad'>预设广告区</div>
	</div>
	</section>
</body>
</html>