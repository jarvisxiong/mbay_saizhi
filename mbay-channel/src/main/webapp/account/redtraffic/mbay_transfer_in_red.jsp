<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>美贝转入-美贝直通车</title>
<link href="${actx}/wro/${version}/fill.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){	
	var mbzzValid=$("#mbzz").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"enough_balance" : function(gets,obj,curform,regxp){
				var mbaybalance=${mbayBalance};
				var transferAmount=$("input[name='transferAmount']").val();
				if(transferAmount!=""){
					if(transferAmount>mbaybalance){
						return false;
					}
				}
				return true;
			}
		},
		beforeSubmit:function(curform){
			if(confirm("确认转入？")){
				return true;
			}else{
				return false;
			}
		}
	});
});
</script>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<!--左边-->
				<div class='left_con'>

					<div class='ddqr'>美贝转入</div>
					<%@ include file="/common/leftcon.jsp"%>
					<div class='dd fr'>
						<c:if test="${not empty message }">
							<div class="qs_alert">
								<p>${message}</p>
							</div>
						</c:if>
						<form id='mbzz'
							action="${ctx}/account/redtraffic/mbay_transfer_in_red/transfer.mbay"
							method="post">
							<m:token />
							<div class='yfje'>
								<span class='czll'>美贝流量总额：</span> <span id="amount">
								<fmt:formatNumber
									value="${redBalance}" maxFractionDigits="2"
									minFractionDigits="2" />
								</span> 								
								<span class='je'>美贝</span>
							</div>
							
							<div class='yfje'>
								<span class='czll'>美贝账户余额：</span> 
								<span id="amount">
								
								<fmt:formatNumber
									value="${mbayBalance}" maxFractionDigits="2"
									minFractionDigits="2" />
								</span>
								<span class='je'>美贝</span>
							</div>
							
							<div class='an_tc' style='height: 320px; width: 210px;'>
								<i></i>
								<!--当前用户的下级  -->
							</div>
							<div class='czsl'>
								<span class='czll'><b>*</b>转入美贝：</span> <input type='text'
									maxlength="11" class='sl_1 onlynum' placeholder='请输入转账数量'
									name="transferAmount" class='hdmc' datatype="*,enough_balance"
									errormsg="对不起,您的余额不足！" /> <span class='je'>美贝</span> <span
									class="Validform_checktip"></span>
							</div>
	
							<div class='an'>
								<input type="submit" id="subForm" class='an_btn' value='确认转入' />
							</div>
							
						</form>
					</div>
				</div>
				<!--尾部-->
			</div>
		</div>
	</div>
</body>
</html>
