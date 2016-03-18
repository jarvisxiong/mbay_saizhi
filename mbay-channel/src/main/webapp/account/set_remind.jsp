<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提醒设置</title>
<style type="text/css">
.ddqr{padding-left:40px;background:url(${actx}/images/workimages/bjms.jpg) no-repeat left;color:#35618F;font-size:18px;height:36px;line-height:35px;line-height: 45px;margin-bottom:15px;font-family: 'Microsoft YaHei'}
</style>
<script type="text/javascript"	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>

<link href="${actx}/wro/${version}/set_remind.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/set_remind.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		/*验证*/
		$("#user_remind_set").Validform({
			showAllError : true,
			tiptype : 3,
			datatype : {
				"sz" : /^[0-9]+$/
			}
		});
	});
</script>
</head>
<div id="mbzz" class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div class='left_con'>

				<div class='ddqr'>提醒设置</div>
				<%@ include file="/common/leftcon.jsp"%>
				<!--右侧设置-->
				<div class='tip_setting fr'>
					<div class="tip_setting_con">
						<form name="user_remind_set" id="user_remind_set">
							<div class='form_block'>
								<span class='tit_mb'>美贝:</span><input id="mbnum" type='text'
									datatype="sz" errormsg="输入错误" class='sl_1 onlynum'
									value='${remindPoint.mbayRemindPoint }' /><span class='sl_mb'>美贝</span>

								<!--提示-->
								<i>?
									<div class="tooltip" style="display: none;">
										当您的账户余额小于该值时，系统将以短信的方式通知到您！<em></em>
									</div>
								</i> <span class="Validform_checktip"></span>
							</div>
							<div class='form_block' style="display: none">
								<span class='tit_mb'>是否启用:</span>
								<!--开关设置-->
								<div class="box">
									<div class="slider off" id="slider_0"></div>
									<span class="m">开</span> <span class="w">关</span>
								</div>
								<!--提示-->
								<i>?
									<div class="tooltip" style="display: none;">
										灰色为关闭<em></em>
									</div>
								</i>
							</div>
							<div class='form_block sms_tip' style="display: none;">
								<span class='tit_mb'>短信:</span><input id="smsnum" type='text'
									datatype="sz" errormsg="输入错误" class='sl_1'
									value='${remindPoint.smsRemindPoint }' /><span class='sl_mb'>条</span>
								<!--提示-->
								<i>?
									<div class="tooltip" style="display: none;">
										当您的短信剩余条数小于该值时，系统将以短信的方式通知到您！<em></em>
									</div>
								</i><span class="Validform_checktip"></span>
							</div>
							<div class='form_block' style="display: none">
								<span class='tit_mb'>是否启用:</span>
								<!--开关设置-->
								<div class="box">
									<div class="slider off" id="slider_1"></div>
									<span class="m">开</span> <span class="w">关</span>
								</div>
								<!--提示-->
								<i>?
									<div class="tooltip" style="display: none;">
										灰色为关闭<em></em>
									</div>
								</i>
							</div>
							<div class='form_block an'>
								<button class='an_btn' type="submit" >确定</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>




