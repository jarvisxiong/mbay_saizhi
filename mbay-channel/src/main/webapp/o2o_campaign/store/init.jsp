<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>O2O红包</title>
<link href="${actx}/wro/${version}/workbenches.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<c:url value="/js/lib/Validform_v5.3.2_min.js"/>"></script>
<script type="text/javascript">
	$(function() {
		/* 验证 */
		$("#init_Store").Validform({
			showAllError : true,
			tiptype : 3,
			datatype : {
				"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
				"sz" : /^[0-9]+$/,
				"rq" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
			}
		});
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
					<div class='hdlb'>O2O门店活动平台</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='lb fr' style='width: 830px'>
						<div class='hd'>
							<div class='r_box'>
								<form id="init_Store"
									action="${pageContext.request.contextPath }/store/initStore.mbay"
									method="post">
									<div class='body'>
										<div class='com_width' style='margin: 20px auto;'>
											<div style='margin: 20px 0px'>
												<label
													style='width: 390px; text-align: right; display: inline-block; margin-right: 5px'>门店数:</label><input
													type="text" name="storeNum" class='input_txt' datatype="n"
													errormsg="格式不正确！"><span class="Validform_checktip"
													style='width: 200px'> 数字</span>
											</div>
											<div style='margin: 20px 0px'>
												<label
													style='width: 390px; text-align: right; display: inline-block; margin-right: 5px'>操作员数:</label><input
													type="text" name="operatorNum" class='input_txt'
													datatype="n" errormsg="格式不正确！"><span
													class="Validform_checktip" style='width: 200px'> 数字</span>
											</div>
											<div style='text-align: center'>
												<input type="reset" class='btn_reset'
													style='margin-right: 20px'><input type="submit"
													value="添加" class='btn_confirm'>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>