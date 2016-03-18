<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加批充</title>
<t:assets />
<link href="${actx}/wro/${version}/customerServerAdd.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/customerServerAdd.js"></script>
<script src='<c:url value="/js/lib/Validform_v5.3.2_min.js"/>'></script>
<script src='<c:url value="/js/lib/jquery-ui-1.10.4.custom.min.js"/>'></script>
<script type="text/javascript"
	src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script>
	$(function(){
		if("${message}" != ""){
			$.messager.alert({content:"${message}"});
		}
	});
</script>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>
					<div class='tjhd'>添加批充</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='hd fr' style='position: relative'>
						<form id="hd"
							action="<c:url value="/customerserver/batchcharge/add.mbay"/>"
							method="post" enctype="multipart/form-data">
							<m:token />
							<input type="hidden" id='period' name="period" value="15" />
							<div class='xz_1' style='margin-top: 20px;'>
								<span><b>*</b>名称:</span><input type='text' placeholder='请输入名称'
									name="name" class='name_txt' datatype="hdmc"
									errormsg="输入格式不正确！" /> <span class="Validform_checktip">请输入汉字，数字或字母！</span>
							</div>
							<div class='xz_1' style='display: none;'>
								<span><b>*</b>运营商:</span> <input type='radio' name="gender_1"
									class='hdlb' checked="checked" id='sw_1' datatype="*"
									errormsg="请选择类别！" value='0' /> <label for='sw_1' class='ll'>三网通用</label>
								<span class="Validform_checktip"></span>
							</div>
							<div style='display: none'>
								<select name="area" id='sel'>
									<option value="0" selected="selected">全国</option>
								</select>
							</div>
							<div class='xz_3'>
								<span><b>*</b>流量:</span> <input type='radio' name="gender_2"
									class='hdlb traffictypeSTATE' checked="checked" id='qg'
									datatype="*" errormsg="请选择类别！" value='STATE' /> <label
									for='qg' class='ll traffictypeSTATE'>全国</label> <span
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
							<!--订购流量表-->
							<div id='sel_table' class='table' style='display: none;'>
								<input type="hidden" name="eventnumber" value="${eventnumber}" />
								<table>
									<tr>
										<th>省份</th>
										<th>产品</th>
										<th>单价</th>
									</tr>
								</table>
							</div>
							<div class='cz_0'>
								<span class='czll' style='height: 40px; line-height: 40px;'><b>*</b>充值类型:</span>
								<input type='radio' name="chargetype" value="HAND_CHARGE"
									checked="checked" id='sd' /><label for='sd' class='ll'>手动充值</label>
								<input type='radio' name="chargetype" value="PERIOD_CHARGE"
									id='dq' style="display: none" /><label for='dq'
									style="display: none">定期充值</label>
								<div class='dqcz' style="display: none">
									<div class='dq_tit' id='id_1'>
										<div class='dq_con'>
											<div class="editor">
												<div class="text-input">
													充值周期<b>每月<em class='dat'>15</em>日
													</b>
												</div>
											</div>
										</div>

										<div class='dq_box' id='id_2'>
											<div class='dq_tip_0'>
												<!-- 建议在月中充值，价格更优惠哦 -->
											</div>
											<div class='dq_box_con'>
												<a href="javascript:void(0)">1</a> <a
													href="javascript:void(0)">2</a> <a
													href="javascript:void(0)">3</a> <a
													href="javascript:void(0)">4</a> <a
													href="javascript:void(0)">5</a> <a
													href="javascript:void(0)">6</a> <a
													href="javascript:void(0)" class='r_border'>7</a> <a
													href="javascript:void(0)">8</a> <a
													href="javascript:void(0)">9</a> <a
													href="javascript:void(0)">10</a> <a
													href="javascript:void(0)">11</a> <a
													href="javascript:void(0)">12</a> <a
													href="javascript:void(0)">13</a> <a
													href="javascript:void(0)" class='r_border'>14</a> <a
													href="javascript:void(0)">15</a> <a
													href="javascript:void(0)">16</a> <a
													href="javascript:void(0)">17</a> <a
													href="javascript:void(0)">18</a> <a
													href="javascript:void(0)">19</a> <a
													href="javascript:void(0)">20</a> <a
													href="javascript:void(0)" class='r_border'>21</a> <a
													href="javascript:void(0)">22</a> <a
													href="javascript:void(0)">23</a> <a
													href="javascript:void(0)">24</a> <a
													href="javascript:void(0)">25</a> <a
													href="javascript:void(0)">26</a> <a
													href="javascript:void(0)">27</a> <a
													href="javascript:void(0)" class='r_border'>28</a> <a
													href="javascript:void(0)" class='b_border'>29</a> <a
													href="javascript:void(0)" class='b_border'>30</a> <a
													href="javascript:void(0)" class='b_border'>31</a> <a
													href="javascript:void(0)" class='b_border d'></a> <a
													href="javascript:void(0)" class='b_border d'></a> <a
													href="javascript:void(0)" class='b_border d'></a> <a
													href="javascript:void(0)" class='r_border d b_border'></a>
											</div>
											<div class='dq_tip_1'>如果当月没有29、30、31号，则默认在当月最后一天发送充值提醒。</div>
										</div>
									</div>
								</div>
							</div>
							<div class='xz_1'>
								<span><b>*</b>Excel模板:</span> <input type="file"
									name="excelfile" datatype="excel" errormsg="请选择正确的Excel文件"
									class='scwj' />
								<button id='excel_download' type='button' class='btn_confirm'>模板下载</button>
								<span class="Validform_checktip" style='width: 180px'></span>
							</div>
							<!--编辑模式按钮-->
							<div id="notmsg"
								style="text-align: center; margin-top: 15px; color: #FDAB1B; height: 20px"></div>
							<div class='bj_an'>
								<input type="submit" id="subbtn" value='提交'
									class='bjms_1 btn_confirm' />
							</div>
						</form>
					</div>
				</div>
				<!--右边-->
			</div>
		</div>
		<!--尾部-->
	</div>
	<div class="yxz_package" style="display: none" id="info1"></div>
	<div class="yxz_package" style="display: none" id="info2"></div>
	<div class="yxz_package" style="display: none" id="info3"></div>
</body>
</html>