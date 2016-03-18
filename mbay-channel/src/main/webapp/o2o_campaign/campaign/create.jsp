<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建门店活动</title>
<meta name="description" content="">
<t:assets />
<link href="${actx}/css/promotion_event/event_add.css" rel="stylesheet"
	type="text/css" />
<link href="<c:url value="/css/switch.css"/> " rel="stylesheet"
	type="text/css" />
<!-- <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet"> -->
<script type="text/javascript"
	src="<c:url value="/js/my97/WdatePicker.js"/>"></script>
<script type="text/javascript"
	src="${actx}/wro/${version}/campaign_info.js"></script>
<script type="text/javascript"
	src="<c:url value="/js/lib/Validform_v5.3.2_min.js"/>"></script>
<script type="text/javascript"
	src="${ctx}/js/o2o_campaign/jquery.tmpl.min.js"></script>
<script type="text/javascript"
	src="${actx}/js/o2o_campaign/campaign/campaign.js"></script>

<script type="text/javascript">
	$(function() {
		$("#cao").on(
				'click',
				function() {
					alert(($("#getInTime").attr('checked') == 'checked' ? true
							: false));
				});
	});

	/* function getValue() {
		
	} */
</script>

</head>
<body>
	<!--head-->
	<div id="con">
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='tjhd'>创建门店活动</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='hd fr'>
						<div class='hd_con' id="fillInfo">
							<form id='hd'<%-- action="${ctx}/campaign/create.mbay" method="post" --%>>
								<m:token />
								<div>
									<span><b>*</b>活动名称:</span><input type='text'
										placeholder='请输入活动名称' name="name" class='hdmc' datatype="hdmc"
										errormsg="输入格式不正确！" maxlength="40" /> <span
										class="Validform_checktip" style='width: 200px'>请输入汉字，数字或字母！</span>
								</div>

								<div class='rq'>
									<span><b>*</b>活动日期:</span> <img
										src='<c:url value="/images/workimages/rq.jpg"/>' class='hdrq'
										onclick="WdatePicker({el:'rq_1',minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />
									<input datatype='rq' errormsg='请正确填写日期！' name="start_Time"
										type='text' id='rq_1'
										onFocus="WdatePicker({minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})" />-
									<img src="<c:url value='/images/workimages/rq.jpg'/>"
										class='hdrq'
										onclick="WdatePicker({el:'rq_2',minDate:'${now}'})" /> <input
										type="text" datatype='rq' errormsg='请正确填写日期！' name="end_Time"
										id='rq_2' onFocus="WdatePicker({minDate:'${now}'})" /> <span
										class="Validform_checktip">输入日期！</span>
								</div>

								<div class='yxq'>
									<span><b>*</b>兑换码有效期:</span><input type='text'
										class='yxqx onlynum' name="validity" value="" datatype='sz'
										errormsg="请填写准确！" /><i>天</i> <span class="Validform_checktip">请准确填写天数！</span>
								</div>

								<div class='d'>
									<span><b>*</b>红包价值:</span><input type='text'
										class='yjzl onlynum price' maxlength="6" name="price"
										datatype='sz' errormsg="请填写准确！" /><i>元</i> <span
										class="Validform_checktip" style='margin-left: 3px'>请准确输入价值！</span>
								</div>
								<div class='d'>
									<span><b>*</b>预计总量:</span><input type='text'
										class='yjzl onlynum quantity' maxlength="6" name="quantity"
										datatype='sz' errormsg="请填写准确！" /><i>份</i> <span
										class="Validform_checktip" style='margin-left: 3px'>请准确输入数量！</span>
								</div>

								<div class='d'>
									<span><b>&nbsp;</b>分销总量:</span><input type='text'
										class='yjzl onlynum mbayPlatSend' maxlength="6"
										ignore="ignore" name="mbayPlatSend" datatype='sz'
										errormsg="请填写准确！" /><i>份</i> <span class="Validform_checktip"
										style='margin-left: 3px'>请准确输入数量！</span>
								</div>

								<div class='d'>
									<span><b>&nbsp;</b>活动锁定美贝值:</span><span type='text'
										class='yjzl onlynum lockMbay' name="lockMbay"
										errormsg="请填写准确！"
										style='font-size: 18px; font-weight: bold; color: red; border: 0px; text-align: left; display: inline-block; width: 340px; height: 32px; vertical-align: middle;' /></span><i>美贝</i>
									<span class="Validform_checktip" style='margin-left: 3px;'></span>
								</div>

								<div class='xz_3'>
									<span><b>*</b>活动领取限制:</span> <input type='radio'
										name="repeatGet" class='hdlb repeatGet' checked="checked"
										id='qg' datatype="*" errormsg="请选择类别！" value='false' /> <label
										for='qg' class='ll '>单人领取一次</label> <input type='radio'
										name="repeatGet" class='hdlb repeatGet' id='sn' value='true' />
									<label for='sn' class='ll '>不限次数</label> <span
										class="Validform_checktip"></span>
								</div>

								<div class="hd_2">
									<span class='rq'><b></b>是否立即领取:</span> <span> <!--开关设置-->
										<div class="slider-box off">
											<div class="slider"></div>
											<!-- <span class="m">是</span> <span class="w">否</span>  -->
											<span class="m" style="margin: 0px -165px;">是</span> <span
												class="w" style="margin: 0px 18px;">否</span> <input
												type="checkbox" id="getInTime" name="getInTime" />
										</div> <input type="button" value="cao" id="cao"> <!--提示-->
										<i class='tip_i'>?
											<div class="tooltip" style="display: none;">
												可以立即领取红包，不用存入钱包！<em></em>
											</div>
									</i>
								</div>
								<div class='d'>
									<span><b></b>活动链接:</span><input type='text' class='yjzl link'
										name="link" errormsg="请填写准确！" ignore="ignore" datatype='url' /><i></i>
									<span class="Validform_checktip" style='margin-left: 3px'>请准确输入网址！</span>
								</div>
								<div class='d'>
									<span><b>*</b>活动说明:</span>
									<textarea class='textarea describtion' datatype='*'
										placeholder='请输入活动说明' rows="2" cols="40" name="describtion"
										errormsg="请填写准确！"></textarea>
									<i>&nbsp;</i> <span class="Validform_checktip"
										style='margin-left: 3px'>请准确输入活动主要内容!</span>
								</div>
								<!--编辑模式按钮-->
								<div id="notmsg"
									style="text-align: center; margin-top: 15px; color: #FDAB1B; height: 20px"></div>
								<div class='bj_an' style='text-align: center'>
									<input type="submit" id="cam_subbtn" value='提交'
										class='bjms_1 btn_confirm' />
								</div>
							</form>
						</div>
						<div id="selectStore" class="center-block" title=""
							style="height: 500px; display: none">
							<table class="table table-bordered">
								<caption align="top">
									<span><h2>选择门店</h2></span><input type="button"
										id="select-Store" value="选择">
								</caption>
								<tr>
									<th class="active"><input type="checkbox" id="chooseAll" /><label>全选</label></th>
									<th class="success">门店编号</th>
									<th class="warning">门店名称</th>
									<th class="danger">门店状态</th>
									<th class="info">授权码</th>
								</tr>
								<tbody id="selectBody">
								</tbody>
								<script id="myTemplate" type="text/x-jquery-tmpl"> 
									<tr><td><input type='checkbox' class="checkStore" value='\${id}'><label>\${id}</label></td><td>\${number}</td><td>\${name}</td><td>\${status}</td><td>\${authCode}</td></tr>
									</script>
								<script>
									var showData = function(stores, page) {
										$('#myTemplate').tmpl(stores).appendTo(
												'#selectBody');
										$('#myTemplate2').tmpl(page).appendTo(
												'#pageinfo');
									}
								</script>
							</table>
							<div>
								<table class="table">
									<tbody id="pageinfo"></tbody>
									<script id="myTemplate2" type="text/x-jquery-tmpl"> 
										<tr><td><a href="#" class="pageinfo" onClick="gotoPage('\${prepage}')">上一页</a></td><td><a href="#" class="pageinfo" onClick="gotoPage('\${currentpage}')">当前页[\${currentpage}]</a></td><td><a href="#" class="pageinfo" onClick="gotoPage('\${nextpage}')">下一页</a></td></tr>
									</script>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>