<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>汇款记录</title>
<t:assets/>
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
<link	href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"  src="${actx}/js/my97/WdatePicker.js"></script> 
<script type="text/javascript">
	$(document).ready(function() {
		$(".sel").val("${status}");
	});
</script>
</head>
<body>
<div class='con'>
	<!--身体-->
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div>
				<form id="pagerForm"
					action="<c:url value="/account/remittanceRecord/list.mbay"/>"
					method="post">
					<div class='hdlb'>汇款记录</div>
					<%@ include file="/common/leftcon.jsp"%>
					<!--内容右部-->
					<div class='hd_con_r fr'>
						<div class='lb'>
							<div class='hd'>
								<span class='hdmc'>查询时间：</span>
			                    <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq'  onclick="WdatePicker()"/>
			                    <input id="rq_1" name="starttime" class="Wdate" value='${starttime}' autocomplete="off" type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
			                    <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
			                    <input id="rq_2" name="endtime" value='${endtime}' autocomplete="off" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>  
			                    <span class='hdbh' style="margin-left:20px;">状态：</span>
		                      	<select class='sel' name="status">
			                      <option value="">请选择</option>
			                      <option value="UNPROCESSED">未处理</option>
			                      <option value="PROCESSED">已处理</option>
			                      <option value="CANCLED">已取消</option>
		                      	</select>
			                    <input type="submit" style="margin-left: 30px;" class='hd_btn' value='查询'/>
							</div>
						</div>
						<!--表格-->
						<div class='table'>
							<table>
								<tr>
									<td>序号</td>
									<td>收款银行</td>
									<td>汇款金额</td>
									<td>汇款时间</td>
									<td>状态</td>
									<td>备注</td>
								</tr>
								<c:forEach items="${list}" var="vs" varStatus="vs2">
									<tr>
										<td>${vs2.index+1}</td>
										<td>${vs.bank.bankName}</td>
										<td>${vs.price}</td>
										<td>${vs.time}</td>
										<td>${vs.status.value}</td>
										<td>
											<span title="${vs.description}">
												<c:if test="${vs.description.length() > 10}">
													${vs.description.substring(0,10)}...
												</c:if>
												<c:if test="${vs.description.length() <= 10}">
													${vs.description}
												</c:if>
											</span>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<m:page pageinfo="${pageinfo}" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--尾部-->
</div>
</body>
</html>
