<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接口管理</title>
<style>
.body {margin-bottom: 30px;}
.dd {width: 820px;font-size: 14px}
body {font-family: 'Microsoft YaHei'}
.ddqr {
	padding-left: 40px;
	background: url(../images/workimages/llcz.jpg) no-repeat left;
	color: #35618F;
	font-size: 18px;
	height: 36px;
	line-height: 35px;
	line-height: 45px;
	margin-bottom: 15px;
	font-family: 'Microsoft YaHei';
}
h3 {
	background: url(../images/phone.png) no-repeat left;
	padding-left: 60px;
	font-size: 16px;
	height: 60px;
	line-height: 60px;
	color: #69737b;
	border-bottom: 1px dashed #e7ecee;
	margin-bottom: 20px;
}
button {
	margin-right: 30px;
	width: 120px;
	height: 35px;
	line-height: 35px;
	letter-spacing: 1px;
	border-radius: 4px;
	color: #FFF;
	font-family: 'Microsoft YaHei';
	background: #FDAC1C;
}
.txt_1 {
	border: 1px solid #94ABC4;
	width: 270px;
	line-height: 14px;
	padding: 5px 5px;
}
.dd p {margin-bottom: 20px;margin-left: 33px}
.txt {
	margin-bottom: 20px
}
.txt_1 {margin-left: 5px}
.sp1{margin-right:20px}
.sp2{margin-right:132px}
.sp3{margin-right:145px}
/*验证*/
.Validform_checktip {margin-left: 0px}
.Validform_checktip {
	text-align: left;
	font-size: 1em;
	color: #999;
	width: 200px;
	margin-left: 34px;
}
.Validform_right {
	font-size: 1em;
	background: url(../images/icons/tb.png) no-repeat left;
	padding-left: 20px;
	background-position: 0 -29px;
	color: #5BB53C
}
.Validform_wro/${version}ng {
	font-size: 1em;
	color: #ff5243;
	background: url(../images/icons/tb.png) no-repeat left;
	padding-left: 20px;
	background-position: 0 -53px;
}
</style>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(
	function() {
		$(".js_sub_sign").bind("click",function() {
			document.getElementById("http").action = '<c:url value="/trafficSign/sub_sign.mbay"/>';
			$("#http").submit();
		});
		$('#http').Validform({
			showAllError : true,
			tiptype : 3,
			datatype : {
				"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
				"sz" : /^[0-9]+$/,
				"rq" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
			}
		});
});
</script>
</head>
<body>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<div class='left_con'>
				<div class='ddqr'>接口管理</div>
				<%@ include file="/common/leftcon.jsp"%>

				<div class='dd fr'>
					<h3>流量产品实时订购接口</h3>
					<form id='http' method="post">
					<m:token/>
						<div class='txt'>
							<span class="sp3">联系人:</span>
							<input type='text' class='txt_1' id="person" name="person" autocomplete="off" datatype="*" />
						</div>
						<div class='txt'>
							<span class="sp2">联系电话:</span>
							<input type='text' class='txt_1' id="phone" name="phone" autocomplete="off" datatype="m" />
						</div>
						<div class='txt'>
							<span class="sp1">需要接入的网站地址:</span>
							<span>http://</span><input type='text' class='txt_1' id="url" name="url" autocomplete="off" datatype="url" />
						</div>
					</form>
					<c:if test="${message != ''}"><div style="color:red;margin:10px 0px;">${message}</div></c:if>
					<div class="js_sign">
						<button class="js_sub_sign">立即签约</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
