<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入券码</title>
<link href="${actx}/wro/${version}/mall_add.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"></script>
<script type="text/javascript" src="${actx}/wro/${version}/mall_add.js"></script>
<script type="text/javascript" src="${actx}/js/mall/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${actx}/js/mall/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${actx}/js/mall/jquery.fileupload.js"></script>
</head>
<body>
<section id="main-content" class="clearfix">
	<div class="config_lottery ">
		<h2>
			导入券码
			<c:if test="${not empty message}"><span class="message-box">*${message}</span></c:if>
			<a class="now-href" href="<c:url value='/mall/exchangeItem/list.mbay'/>"><span>&gt;</span> 我的兑换项</a>
		</h2>
		<form id='exchangeItemForm' action='<c:url value="/mall/exchangeItem/addCouponTicket.mbay"/>' enctype="multipart/form-data" method="post">
			<input type="hidden" name="itemNumber" value="${bean.itemNumber}"/>
			<input type="hidden" name="tickets" value="" />
			<m:token />
			<div class="lotter_info_content pt20">
				<dl>
					<dt>优惠券标题：</dt>
					<dd>
						<strong>${bean.title}</strong>
					</dd>
				</dl>
				<dl>
					<dt>商品有效期：</dt>
					<dd class="form-group">
						<div class="form-combination select-date-range w822" >
							<div class="start-day date input-append" style="display: block;float:left;width: 600px;">
								<input class="form-control" type='text' placeholder="开始时间" datatype='rq' errormsg='请正确填写日期！' 
									name="eventstarttime" id='rq_1' onFocus="WdatePicker({minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})">
								<span class="add-on" onclick="WdatePicker({el:'rq_1',minDate:'${now}',maxDate:'#F{$dp.$D(\'rq_2\')}'})"><i class="icon-th"></i></span>
								<span style="display: block;float:left;">-</span>
								<input class="form-control" type="text" placeholder="结束时间" datatype='rq' errormsg='请正确填写日期！'
									name="eventendtime" id='rq_2' onFocus="WdatePicker({minDate:'${now}'})" >
								<span class="add-on" onclick="WdatePicker({el:'rq_2',minDate:'${now}'})"><i class="icon-th"></i></span>
								<span class="Validform_checktip" style="height: 16px;display:inline;"></span>
							</div>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>导入券码：</dt>
					<dd>
						<div class="port_input w822 ml15">
							<button id="ticketButton" type="button" class="up_btn button-upload">选择上传</button>
							<input type="file" id="ticketFile" name="ticketFile" accept="text/plain" class="ticket_file">
							<span class="Validform_checktip"></span>
							<p class="gray ml20 fl">
								文件格式为txt格式。请严格按照“<strong>券码</strong>”-"<strong>密码</strong>"编辑。单次最多导入5000条。具体可以
								<a href="javascript:download();" class="text-line">下载示例文件</a>。
							</p>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>预览内容：</dt>
					<dd>
						<div class="port_input w822 ml15">
							<table class="coupon full w630">
								<thead>
						          <tr>
						            <th width='20%'>序号</th>
									<th width='45%'>劵码</th>
									<th width='35%'>密码</th>
						          </tr>
						        </thead>
								<tbody id="tbody"></tbody>
							</table>
						</div>
					</dd>
				</dl>
				<dl class="b-t-gray pt20">
					<dt>&nbsp;</dt>
					<dd>
						<input type="submit" class="button_sumbit btn-primary saveBtn" value="保存">
					</dd>
				</dl>
			</div>
		</form>
	</div>
</section>
</body>
</html>