<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>美贝转账</title>
<link href="${actx}/wro/${version}/fill.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/fill.js"></script>
<script type="text/javascript" src="${actx}/js/transfer/fill_transfer_order.js?v=1.4.3"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
	//E55E4D5B  判断手动输入账号时如果账号存在显示名称+编号   onblur
	$(function() {		
		$("#mbayaccount").focus(function() {
			if($("#mbayaccount").val().trim()!=""){
				var accountvalue = $("#accountvalue").val();				
					$("#mbayaccount").val(accountvalue);				
			}					
		});
		$("#mbayaccount").keyup(function() {
			if($("#mbayaccount").val().trim()!=""){
				 $("#accountvalue").val($(this).val());
			}					
		});
	});
</script>
<style type="text/css">
input[type="text"]{border:1px solid #C1C1C1;}
/* .clear-icon{margin: -2px 0 0 -5px;border:1px solid #c1c1c1;} */
textarea[class="czll_msg"]{border:1px solid #c1c1c1;width:250px;}
input[type="text"]:focus{border:1px solid #356391;}
textarea[class="czll_msg"]:focus{border:1px solid #81A5E5;}
.czll_a{text-decoration:none;border-radius:4px;padding:4px 8px;color:#FFF; background:#fdac1c;font-size:12px;}
</style>
</head>
<body>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<!--左边-->
			<div class='left_con'>

				<div class='ddqr'>美贝转账</div>
				<%@ include file="/common/leftcon.jsp"%>
				<div class='dd fr'>
					<c:if test="${not empty message }">
						<div class="qs_alert">
							<p>${message}</p>
						</div>
					</c:if>
					<form id='mbzz'
						action="<c:url value="/account/transfer/payment/submit.mbay"/>"
						method="post">
                       <m:token/>
                        <!-- <div class='mb_exchange'> 
                           <span class='czll'>换算美贝：</span>
							<input id="money"  maxlength="11" class='sl_1 onlynum' style="width:90px;"   />&nbsp;元&nbsp;&nbsp;&nbsp;
							<input type='button'  id="convert" class='an_btn' style="margin:0px; width:65px; height:23px; line-height:23px;" value='换算' />
							<span id="result" class='je' style="display:none;"></span>
						</div> -->

						<div class='yfje'>
							<span class='czll'>账户余额：</span> <span id="amount"></span> <span class='je'>美贝</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="czll_a" href="<c:url value="/account/depositPreprocess.mbay"/>">立即充值</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="czll_a" href="<c:url value="/record/transfer_record.mbay"/>">转账记录</a>
						</div>
						<div class='czsl'>
							<span class='czll'><b>*</b>美贝账户：</span> 
							<input name="toUserNumber" type="hidden" id="accountvalue" />
							<input type='text' id="mbayaccount" placeholder='请输入代理账户编号'  nullmsg="请输入账号" datatype="transfer_account_auth"  class='sl_1 sl_111' class='hdmc' />
							<a class="clear-icon" id="usernumber" href="javascript:void(0)"></a> 
							<!-- <a id="usernumber" href="javascript:void(0)">&nbsp;&nbsp;选择账户</a> -->
							<span class="Validform_checktip" style="margin-left:33px;"></span>
						</div>
						<!--选择账户弹框-start-->
						<span class="arrow" style="display:none"></span>
						<div class='an_tc'>
							<i></i>
							<!--当前用户的下级  -->
						</div>
						
						<!--选择账户弹框-end-->
						<div class='czsl'>
							<span class='czll'><b>*</b>转账金额：</span> 
							<input type='text' maxlength="11" class='sl_1 onlynum' placeholder='请输入转账数量'name="mbayAmount" class='hdmc' datatype="sz"
								errormsg="对不起,您的余额不足！" /> <span class='je'>美贝</span>
								<span class="Validform_checktip"style="margin-left:-1px;"></span>
						</div>
						<div class='czsl'>
							<span class='czll'>&nbsp;赠送金额：</span> <input type='text'
								maxlength="11" ignore="ignore" name='sendAmount' value="0"
								datatype="sz" class='sl_1 hdmc onlynum' placeholder='赠送美贝数量'
								errormsg="对不起,您的余额不足！"/> 
								<span class='je'>美贝</span>
								<span class="Validform_checktip" style="margin-left:-1px;"></span>
								<!-- <span class="Validform_checktip Validform_right" style="margin-left:39px;">通过信息验证！</span> -->
						</div>
						<div class='czsl'>
							<span class='czll'>&nbsp;转账合计：</span> <span id="transfertotal">0</span>
							<span class='je'>美贝</span><!-- <span class="Validform_checktip"></span> -->
						</div>
						<div class='sm'>
							<span class='czll'>&nbsp;转账说明：</span>
							<textarea class="czll_msg" maxlength="100" datatype="*1-100" ignore="ignore"name="remark" placeholder='请输入说明信息'></textarea>							
						</div>
						<div style="margin-left: 170px">
							<span class='ssm' id="amounterror">注意：转账说明栏最多可输入100个半角字符。</span>
						</div>
						<div class='an'>
							<input type='button' id="subForm" class='an_btn' value='下一步'/>
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
