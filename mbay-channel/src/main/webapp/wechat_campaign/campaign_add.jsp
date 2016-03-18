<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建微信伴侣</title>
<meta name="description" content="">
<link href="${actx}/wro/${version}/wechat_campaign_add.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/wechat_campaign_add.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript"
	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<style>
.zj {
	margin-left: 2px;
}

.zjdxtx {
	margin-left: -2px;
}

.zjhdrq {
	margin-left: -1px;
}

.m {
	color: #118EEF;
	margin-right: 16px;
}

.w {
	color: #118EEF;
	margin-right: 17px;
}
</style>
</head>
<body>
	<!--head-->
	<div id="con">
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='tjhd'>创建微信伴侣</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='hd fr'>
						<c:if test="${not empty message }">
							<div class="qs_alert">
								<p>${message}</p>
							</div>
						</c:if>
						<div class='hd_con'>
							<form id='hd'
								action='<c:url value="/wechatCampaign/campaign_add.mbay"/>'
								method="post">
								<m:token />
								<div>
									<span><b>*</b>活动名称:</span><input type='text'
										placeholder='请输入活动名称' name="eventname" class='hdmc zj'
										datatype="hdmc" errormsg="输入格式不正确！" maxlength="12" /> <span
										class="Validform_checktip" style='width: 200px'>请输入汉字，数字或字母！</span>
								</div>
								<div>
									<span><b>*</b>类别:</span> <input type="radio"
										class='hdlb zjdxtx' datatype="*" errormsg="请选择类别！"
										id="activityContent_sendFlow" name="sendway" checked="checked"
										value="TRAFFIC" /> <label for="activityContent_sendFlow">送流量</label>
									<!-- 
	                        <input type="radio" class='hdlb' id="activityContent_sendMeiCoin" name="sendway" value="MBAY" />
	                        <label for="activityContent_sendMeiCoin" class='ll'>送美贝</label>
	                         -->
									<span class="Validform_checktip"></span>
								</div>
								<div id="mbaysendunit"></div>
								<div class='rq'>
									<span><b>*</b>活动日期:</span> <img
										src='<c:url value="/images/workimages/rq.jpg"/>'
										class='hdrq zjhdrq'
										onclick="WdatePicker({el:'rq_1',minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
									<input datatype='rq' errormsg='请正确填写日期！' name="eventstarttime"
										type='text' id='rq_1'
										onFocus="WdatePicker({minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />-
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq'
										onclick="WdatePicker({el:'rq_2',minDate:'${now}'})" /> <input
										type="text" datatype='rq' errormsg='请正确填写日期！'
										name="eventendtime" id='rq_2'
										onFocus="WdatePicker({minDate:'${now}'})" /> <span
										class="Validform_checktip " style="margin-left: 31px;">输入日期！</span>
								</div>

								<div class='d'>
									<span><b>*</b>预计总量:</span><input type='text'
										class='yjzl onlynum zj' maxlength="6" name="quantity"
										datatype='sz' errormsg="请填写准确！" /><i>份</i> <span
										class="Validform_checktip" style='margin-left: 3px'>请准确输入数量！</span>
								</div>
								<div class='xz_1'>
									<span><b>*</b>运营商:</span> <input type='radio' name="gender_1"
										class='hdlb zjdxtx' checked="checked" id='yd_1' datatype="*"
										errormsg="请选择类别！" value='1' /><label for='yd_1' class='ll'>中国移动</label>
									<input type='radio' name="gender_1" class='hdlb' id='lt_1'
										value='2' /><label for='lt_1' class='ll'>中国联通</label> <input
										type='radio' name="gender_1" class='hdlb' id='dx_1' value='3' /><label
										for='dx_1' class='ll'>中国电信</label> <input type='radio'
										name="gender_1" class='hdlb' id='sw_1' value='0' /><label
										for='sw_1' class='ll'>三网通用</label> <span
										class="Validform_checktip" style='margin-left: 5px;'></span>
								</div>
								<div class='xz_2'>
									<span><b>*</b>地区:</span> <select name="area" datatype="*"
										nullmsg="请选择所在城市！" errormsg="请选择所在城市！" id='sel'>
										<option value="" selected="selected">请选择</option>
										<option value="0">全国</option>
										<option value="11">北京市</option>
										<option value="12">天津市</option>
										<option value="13">河北省</option>
										<option value="14">山西省</option>
										<option value="15">内蒙古自治区</option>
										<option value="21">辽宁省</option>
										<option value="22">吉林省</option>
										<option value="23">黑龙江省</option>
										<option value="31">上海市</option>
										<option value="32">江苏省</option>
										<option value="33">浙江省</option>
										<option value="34">安徽省</option>
										<option value="35">福建省</option>
										<option value="36">江西省</option>
										<option value="37">山东省</option>
										<option value="41">河南省</option>
										<option value="42">湖北省</option>
										<option value="43">湖南省</option>
										<option value="44">广东省</option>
										<option value="45">广西壮族自治区</option>
										<option value="46">海南省</option>
										<option value="50">重庆市</option>
										<option value="51">四川省</option>
										<option value="52">贵州省</option>
										<option value="53">云南省</option>
										<option value="54">西藏自治区</option>
										<option value="61">陕西省</option>
										<option value="62">甘肃省</option>
										<option value="63">青海省</option>
										<option value="64">宁夏回族自治区</option>
										<option value="65">新疆维吾尔自治区</option>
									</select> <span class="Validform_checktip" style='margin-left: 7px;'></span>
								</div>
								<div class='xz_3'>
									<span><b>*</b>流量:</span> <input type='radio' name="gender_2"
										class='hdlb traffictypeSTATE zjdxtx' checked="checked" id='qg'
										datatype="*" errormsg="请选择类别！" value='STATE' /> <label
										for='qg' class='ll traffictypeSTATE'>全国</label> <input
										type='radio' name="gender_2" class='hdlb traffictypePROVINCE'
										id='sn' value='PROVINCE' /> <label for='sn'
										class='ll traffictypePROVINCE'>省内</label> <span
										class="Validform_checktip"></span>
								</div>

								<!--四选一盒子移动-->
								<div class='yd_sec pacbox box1'>
									<div class='yd'>
										<div class='yd_con'>
											<div class='yd_head'>中国移动</div>
											<div id='package1' class='pact yd_body tp1 clearfix'></div>
										</div>
									</div>

								</div>
								<!--四选一盒子联通-->
								<div class='lt_sec pacbox box2'>
									<div class="lt">
										<div class="lt_con">
											<div class="lt_head">中国联通</div>
											<div></div>
											<div id='package2' class="pact lt_body tp2 clearfix"></div>
										</div>
									</div>

								</div>
								<!--四选一盒子电信-->
								<div class='dx_sec pacbox box3'>
									<div class="dx">
										<div class="dx_con">
											<div class="dx_head">中国电信</div>
											<div id='package3' class="pact dx_body tp3 clearfix"></div>
										</div>
									</div>
								</div>

								<!--三网通用-->
								<div class='cj'>
									<div class='yd_jd' style='padding-bottom: 50px; width: 502px'>

										<div class='yxz pacbox box1' id="yxz1"></div>
										<div class='yxz pacbox box2' id="yxz2"></div>
										<div class='yxz pacbox box3' id="yxz3"></div>
									</div>
									<div class='clfa' style='margin-bottom: 0px'>
										<input type='button' onclick="selectPackage();" value='添加'
											class='cl_tj btn_service' />
									</div>
								</div>

								<!--订购流量表-->
								<div class='table'>
									<input type="hidden" name="eventnumber" value="${eventnumber}" />
									<table>
										<tr>
											<th>省份</th>
											<th>产品</th>
											<th>单价</th>
											<th>操作</th>
										</tr>
									</table>
								</div>

								<div style="margin: 10px 0px;">
									<span class='span_len'>超出继续兑换:</span>
									<!--开关设置-->
									<div class="slider-box on">
										<div class="slider"></div>
										<span class="m" style="margin-left: 16px;">是</span> <span
											class="w" style="color: #118EEF; margin-right: 17px;">否</span>
										<input type="checkbox" name="continuable" checked="true" />
									</div>
									<!--提示-->
									<i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											当活动参与人数超出您设置的预计总量后，是否可继续参与活动。<em></em>
										</div>
									</i>
								</div>
								<div style='display: none;'>
									<span class='span_len'>单号码多次领取:</span>
									<!--开关设置-->
									<div class="slider-box off">
										<div class="slider"></div>
										<span class="m" style="margin-left: 16px;">是</span> <span
											class="w" style="color: #118EEF; margin-right: 17px;">否</span>
										<input type="checkbox" name="repeatEnable" />
									</div>
									<!--提示-->
									<i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											一个号码是否可多次参与此活动。<em></em>
										</div>
									</i>
								</div>
								<!-- 是否可以直接领取(暂时隐去,默认是true) -->
								<div style="margin: 10px 0px; display: none;">
									<span class='span_len'>直接领取:</span>
									<!--开关设置-->
									<div class="slider-box off">
										<div class="slider"></div>
										<span class="m" style="margin-left: 16px;">是</span> <span
											class="w" style="color: #118EEF; margin-right: 17px;">否</span>
										<input type="checkbox" name="directEnable" checked="true" />
									</div>
									<!--提示-->
									<i class='tip_i'>?
										<div class="tooltip" style="display: none;">
											如果选择是，则可以进行直接领取，反之则不能。<em></em>
										</div>
									</i>
								</div>
								<!--是否短信发送通知  -->
								<div style="margin: 10px 0px;">
									<span class='span_len'>短信提醒:</span> <input type='checkbox'
										name="sendsms" checked="checked" id='tx' class="zjdxtx" /> <span
										class='mbtx'> <label> 查看<a>短信模板</a></label>
										<div class='mb_tip' style='display: none; margin-bottom: 0px'>${sms_tempate}<em></em>
										</div>
									</span>
								</div>

								<!--编辑模式按钮-->
								<div id="notmsg"
									style="text-align: center; margin-top: 15px; color: #FDAB1B; height: 20px"></div>
								<div class='bj_an'>
									<input type='button' id="subbtn" value='提交'
										class='bjms_1 btn_confirm' />
								</div>
							</form>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	<div id="mbaysendunithtml" style="display: none;">
		<span><b>*</b>单用户赠送:</span><input type='text' name="mbaysendunit"
			class='dyhzs' datatype='sz' errormsg="请填写准确！" /><i>美贝</i> <span
			class="Validform_checktip">请准确输入数量！</span>
	</div>
	<div class="yxz_package" style="display: none" id="info1"></div>
	<div class="yxz_package" style="display: none" id="info2"></div>
	<div class="yxz_package info" style="display: none" id="info3"></div>
</body>
</html>