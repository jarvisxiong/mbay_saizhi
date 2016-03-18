<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="fs" uri="http://www.mbpartner.cn/jsp/fastdfs/tags"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name=”renderer”content=”webkit|ie-comp|ie-stand”>
<meta name="keywords" content="美贝直通车" />
<meta name="description" content="美贝直通车" />
<title>企业信息</title>
<t:assets />
<link rel="stylesheet" href="${actx}/css/auth/enterprise/share.css">
<link rel="stylesheet" href="${actx}/css/auth/enterprise/public.css">
<link rel="stylesheet" href="${actx}/css/auth/enterprise/ieAlert.css">
<link rel="stylesheet" href="${actx}/css/login/common.css">
<link rel="shortcut icon" href="<c:url value="/images/favicon.png"/>" />
<script src="${actx}/js/index/head.js"></script>
<script src="${actx}/js/layer/layer.min.js"></script>
<script src="${actx}/webjars/jquery/2.1.3/jquery.min.js"></script>
<script src="${actx}/js/enterprise/auth_apply/mb-com.js"></script>
<script src="${actx}/js/enterprise/auth_apply/auth_apply.js"></script>
<script src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" href="${actx}/css/share.css">
<link rel="stylesheet" href="${actx}/css/form.css">
<style>
._img {
	width: 515px;
	float: left;
}

._img img {
	max-height: 120px;
	margin: 5px;
}

.form-step-unfinished {
	background-color: #e2e2e2;
}

.form-step-seperator-unfinished:after {
	border-left: 16px solid #e2e2e2;
}

.firm-register {
	margin: 40px auto;
}

.clearfix .Validform_wrong,.clearfix .Validform_right {
	float: right;
	margin: -15px 50px;
}
/*语音 */
#zzfb {
	float: left;
}
/*.form-item .getUnCode{padding:8px 4px;border-radius: 2px;position:relative;bottom: 2px;right: 0;background: #DDD;color: #FFF;margin-left:-8px;}*/
.yuyingtishi {
	height: 35px;
}

.yuying {
	height: 80px;
}

.form-item .getCode {
	bottom: 0;
	position: relative;
	background: #009AFF;
	color: #FFF;
	margin-left: -31px;
}

.voiceyzm {
	position: relative;
	display: none;
	float: right;
}

.huoqu-btn {
	float: right;
	height: 33px;
	border-radius: 2px;
}

.form-item .getUnCode {
	margin-left: -31px;
	width: 90px;
	height: 33px;
	text-align: center;
	line-height: 33px;
	position: relative;
	background: #009AFF;
	color: #FFF;
	bottom: 0;
}
</style>
<script>
$(function() {
	if("${message}" != ""){
		layer.msg('${message}');
	}
	
	$("#myForm").Validform({
		tiptype:3,
		datatype:{
			"gh": /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/
		}
	});
});
</script>
</head>
<body>

	<!-- 企业注册 -->
	<section class="firm-register">
		<h2>继续完善直通车账户信息</h2>
		<div class="form-steps clearfix">
			<div class="form-step-3 form-step-finished">填写资料</div>
			<div class="form-step-seperator form-step-seperator-finished"></div>
			<div class="form-step-3 form-step-finished">认证</div>
			<div class="form-step-seperator form-step-seperator-finished"></div>
			<div class="form-step-3 form-step-unfinished  form-step-final">注册成功</div>
		</div>
		<article class="firm-form">
			<form id="myForm" style="overflow: hidden;"
				enctype="multipart/form-data"
				action="<c:url value="/certificate/auth/auth_erp_apply_sub.mbay"/>"
				method="post">
				<m:token />
				<div class="form-item">
					<label class="form-title">联系人姓名</label> <span class="form-input">
						<input type='text' id='use' name='contactsname' placeholder='真实姓名'
						value="${enterprise.contactsname}" datatype="s1-20" />
					</span>
				</div>
				<div class="form-item">
					<label class="form-title">性别</label>
					<div class="form-input">
						<c:choose>
							<c:when test="${enterprise.gender == 'FEMALE'}">
								<div class="sex-select clearfix">
									<input class='gender' name='gender' type='radio' value='MALE'
										datatype="*" /> <label class='gender' for='male'>先生</label>
								</div>
								<div class="sex-select clearfix">
									<input class='gender' name='gender' type='radio' value='FEMALE'
										id='female' checked='checked' /> <label class='gender'
										for='female'>女士</label>
								</div>
							</c:when>
							<c:otherwise>
								<div class="sex-select clearfix">
									<input class='gender' name='gender' type='radio' value='MALE'
										checked='checked' id='male' /> <label class='gender'
										for='male'>先生</label>
								</div>
								<div class="sex-select clearfix">
									<input class='gender' name='gender' type='radio' value='FEMALE'
										id='female' datatype="*" /> <label class='gender' for='female'>女士</label>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-item">
					<label class="form-title">所在部门</label> <span class="form-input">
						<select class="form-sel" name="department" id="department"
						datatype="*">
							<option value="">请选择</option>
							<option value="MARKET">市场部</option>
							<option value="SALES">销售部</option>
							<option value="PERSONNEL">人事部</option>
							<option value="ADMINISTRATIVE">行政部</option>
							<option value="TECHNOLOGY">技术部</option>
							<option value="OTHER">其它</option>
					</select>
					</span>
				</div>
				<div class="form-item">
					<label class="form-title">固定电话</label> <span class="form-input">
						<input type='text' id='dhhm' placeholder='电话号码' name='fixphone'
						value="${enterprise.fixphone}" ignore="ignore" datatype="gh"
						errormsg="请正确填写电话号码，如021-22222222！" />
					</span>
				</div>
				<div class="form-item">
					<label class="form-title">联系人邮箱</label> <span class="form-input">
						<input type='text' id='email' name='contactsEmail'
						placeholder='电子邮箱地址' value="${enterprise.contactsEmail}"
						datatype="*,e" />
					</span>
				</div>
				<div class="form-item">
					<label class="form-title">具体地址</label> <span class="form-input">
						<input type='text' id='addr' name='address' placeholder='所在地的详细地址'
						value="${enterprise.address}" datatype="*">
					</span>
				</div>
				<div class="form-item">
					<label class="form-title">联系人手机</label> <span class="form-input">
						<input type='text' id='telephone' name='contactsphone'
						placeholder='11位手机号码' value="${enterprise.contactsphone}"
						datatype="*,m" />
					</span>
				</div>
				<div class="form-item yuying" style="margin-bottom: 1px;">
					<label class="form-title">验证码</label> <span
						class="form-input yuyingtishi"> <input type='text'
						id='yzm' name='authcode' placeholder='验证码' maxlength="6"
						onkeydown="onlyNum(event);"
						style="ime-mode: Disabled; width: 365px;" datatype="n6-6"
						errormsg="验证码不正确!"
						ajaxurl="<c:url value='/certificate/auth/varity_authcod.mbay'/>" />
						<a class="getCode huoqu-btn" href="javascript:getYzm();"
						name='fsyzm' id='hqyzma'>获取验证码</a>
					</span><a href="javascript:voiceYzm();" id='voiceyzm' class="voiceyzm">收不到短信?使用语音验证码</a>
				</div>
				<div class="form-item clearfix">
					<label class="form-title">营业执照副本</label> <span class="form-input">
						<input type='file' id='yyfb' name='licensefile' class='_file'
						datatype="*" />
					</span>
					<div class="_img"></div>
				</div>
				<div class="form-item clearfix">
					<label class="form-title">税务登记证副本</label> <span class="form-input">
						<input type='file' id='swfb' name='taxfile' class='_file'
						datatype="*" />
					</span>
					<div class="_img"></div>
				</div>
				<div class="form-item clearfix">
					<label class="form-title">组织代码机构证副本</label> <span
						class="form-input"> <input type='file' id='zzfb'
						name='codefile' class='_file' datatype="*" /></span>
					<div class="_img"></div>
				</div>
				<div class="form-item">
					<label class="form-title">上传说明</label> <span
						class="form-input _txt">
						扫描或者拍照的文件需要加盖公章，可以接受图片格式为JPG,JPEG,BMP,PMG,GIF,TIFF,TIF,SVG,建议每张图片文件大小不超过2M，图片过大会影响你的认证速度。
					</span>
				</div>
				<div class="form-item" style="padding-top: 30px;">
					<button type="submit">确认并提交</button>
				</div>
			</form>
		</article>
	</section>
	<!-- 企业注册 -->
	<!-- footer begin -->
	<%@include file="/common/footer.jsp"%>
	<!-- footer end -->

</body>
</html>
<script src="${actx}/js/enterprise/auth_apply/ieAlert.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("body").iealert();
    });
    $(".form-input ._file").change(function(){
        showPreview(this,$(this));
    });

    function showPreview(source,parent) {
        var file = source.files[0];
        if(window.FileReader) {
            var fr = new FileReader();
            fr.onloadend = function(e) {
                if(!/image\/\w+/.test(file.type) || file.size >= 4096000){
                    alert("请确保文件为图像类型或者图片大小<=2M");
                    return false;
                }else{
                  //  alert(this.result)
                    parent.parent(".form-input").siblings("._img").append('<img src="'+ this.result +'">');
                }
            };
            fr.readAsDataURL(file);
        }else {
            alert("对不起！您的浏览器不支持图片上传");
        }
    }
</script>