<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<t:assets/>
<link href="<c:url value="/css/smoothness/jquery-ui-1.10.4.custom.min.css"/>"	rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/person/4.css"/> " rel="stylesheet" type="text/css" />
<script type="text/javascript"	src="${actx}/js/lib/jquery.validate.js"></script>
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="<c:url value="/css/person/v_2.js" />"></script>
<script type="text/javascript"	 src="<c:url value="/js/enterprise/auth_apply/checkimgfile.js" />"></script><!-- 不存在ajax -->
<link href="<c:url value="/css/header.css"/> " rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/js/header.js" />"></script>
<!--省市县级联  -->
<script type="text/javascript"	src="<c:url value="/js/lib/jquery.provincesCity.js" />"></script>
<script type="text/javascript" 	src="<c:url value="/js/provincesdata.js" />"></script>
<style type="text/css">
#test select {
	margin-left: 20px;
}
</style>
<script type="text/javascript">
	$(function() {
	  $("#test").ProvinceCity();	
	  var selectValue = $(".js-select-provice").val();
	  $("#province").find("option:selected").attr("selected", false);
	  $("#province option[value='" + selectValue + "']").attr("selected", true);
	  $("#province").change();
	});
</script>
</head>
<body>
	    <!--头部-->
   	<%@ include file="/common/top.jsp"%>
    <%@ include file="/common/advert_out.jsp"%>
	<div class='con'>
		<!--注册栏目-->
		<div class='reg_s'>
			<!--进度栏-->
			<div class='jindu'>
				<div class='jd com_width'>
					<div class='jd_box fl jd_1'></div>
					<div class='jd_b fl sp'></div>
					<div class='jd_b fl sp'></div>
					<div class='jd_box fl jd_2'></div>
					<div class='jd_b fl sp'></div>
					<div class='jd_b fl sp'></div>
					<div class='jd_box fl jd_3'></div>
					<div class='wenzi_1'>提交认证信息</div>
					<div class='wenzi_3'>等待审核</div>
					<div class='wenzi_5'>完成实名认证</div>
				</div>
			</div>
			<!--企业账户联系人-->
			<div class='person'>
				<h1>继续完善个人直通车账户信息</h1>
				<h2>个人信息</h2>
				<h2 style="color: red">${error_msg}</h2>
				<div class='qy_con'>
				<input type="hidden" class="js-select-provice" value="${certificate.province}">
					<form id='regAuthForm' enctype="multipart/form-data"
						action="<c:url value="/certificate/auth/auth_apply_sub.mbay"/>"
						method="post">
						<div class='zsxm'>
							<%-- <input type="hidden" name="loginname" value="${loginname}" /> --%>
							 <label><b>*</b>您的真实姓名:</label> <input type='text' id='true_name' name='true_name'
								placeholder='真实姓名' title='真实姓名，可有中文或英文组成' value="${certificate.true_name}"/>
						</div>
						<div>
							<label class='sex'><b>* </b>性别:</label>
							<c:choose>							
								<c:when test="${certificate.gender == 'FEMALE'}">
									<input class='gender' name='gender' type='radio' value='MALE' id='male' />
		                            <label class='gender' for='male'>先生</label>
		                            <input class='gender' name='gender' type='radio' value='FEMALE' id='female' checked='checked'/>
		                            <label class='gender' for='female'>女士</label>
								</c:when>
								<c:otherwise>
								    <input class='gender' name='gender' type='radio' value='MALE' id='male' checked='checked' />
                                    <label class='gender' for='male'>先生</label>
                                    <input class='gender' name='gender' type='radio' value='FEMALE' id='female' />
                                    <label class='gender' for='female'>女士</label>
								</c:otherwise>
							</c:choose>
						</div>
						<div class='hkszd'>
							<label for="test"><b>* </b>户口所在地:</label>
							<div style="margin-left: 313px; margin-top: -30px;" id="test"></div>
						</div>
						<div class='xxdz'>
							<label><b>* </b>详细地址:</label> <input type='text' id='address'
								name='address' title='请详细填写地址 ' value="${certificate.address}"/>

						</div>
						<div class='zjlx'>
							<label><b>* </b>证件类型:</label> <select name="IDType">
								<option value="1">中国居民身份证</option>
							</select>
						</div>
						<div class='zjhm'>
							<label><b>* </b>证件号码:</label> <input type='text' id='identityNo' 
								name='identityNo' title='请填写仔细18位身份证件号' value="${certificate.identityNo}"/>

						</div>
						<div class='yxdz'>
							<label><b>* </b>邮箱地址:</label> <input type='text' id='email'
								name='email' title='请输入常用邮箱地址' value="${certificate.email}"/>

						</div>
						<div class='lxsj'>
							<label><b>* </b>联系手机:</label> <input type='text' id='telephone'
								name='telephone' title='请输入常用手机号码' value="${certificate.telephone}"/> 
								<input type='button' id='hqyzma' class="hqyma" name='hqyma' value='获取验证码' />
						</div>
						<div id="codeinput" class='sryzm'>
							<label><b>* </b>输入验证码:</label> <input type='text' id='yzm'
								name='authcode' />
						</div>
						<div class='zjzm'>
							<label><b>* </b>证件正面:</label> <input type='file' id='frontfile'
								name='frontfile' />
						</div>
						<div class='zjzm' id="divfrontfile" style="display: none">
							<label></label> <span>
							<img id="imgfrontfile" src="#"	alt="your image" />
							</span>
						</div>
						<div class='zjfm'>
							<label><b>* </b>证件反面:</label> <input type='file' id='backfile'
								name='backfile' />
						</div>
						<div class='zjfm' id="divbackfile" style="display: none">
							<label></label> <span>
							<img id="imgbackfile" src="#"alt="your image" /></span>
						</div>
						<div class='fddbr'>
							<label>上传文件说明:</label> <span>支持jpg,jpeg,bmp,png图片格式,单个文件大小不超过2M</span>
						</div>
						<div class='btn'>
							<input type='submit' name='btn_n' for='btn_n' id='btn_2'
								value='提交' />
						</div>
					</form>
				</div>
			</div>

		</div>
		<!--尾部-->
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>
