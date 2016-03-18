<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量充值</title>
<link href="${actx}/wro/${version}/batch_mobile_info.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
 $(function(){
	$(".tj").bind("click",function(){
		//短信提醒
		var sms = document.getElementById('tx').checked;
		$.messager.progress("充值提示","正在为您执行流量批充，请稍后...");
		$.getJSON("${pageContext.request.contextPath}/customerserver/batchcharge/charge.mbay?id=${chargeinfo.id}&sms="+sms, function(flag){
  			setTimeout("linkto("+flag.success+",'"+flag.message+"')",1500);/*setTimeout(指定的方法,指定的等候时间1500=1.5秒)*/
        });
	});	
	
	$("#addMbtn").bind("click",function(){
		 var cmobile=$("#chargemobile").val();	
		 var reg= /^(1[3|5|7|8])[\d]{9}$/;
		 if(reg.test(cmobile)){		
			 getHcodeInfo(cmobile);
		 }else{
			 $("#messagenot").html("请输入正确的手机号！");	
		 }
	});
	
 	//修改返回按钮跳转页面
 	$("#back").attr("href","${pageContext.request.contextPath}/customerserver/servermanager.mbay");
 });
 
 //跳转页面
 function linkto(flag,message){
	if(flag){
		window.location.href='<c:url value="/customerserver/batch_charge_success.mbay"/>';
	}else{
		$.messager.close_dialog();
		$.messager.alert("充值提示",message);
	}
 }
 
 function getHcodeInfo(mobile) {
 	$("#messagenot").html("");
    var options = {  
        type: 'POST',  
        url: "${pageContext.request.contextPath}/mobile/attribution.mbay",  
        data: { "mobile": mobile },  
        async:false,  
        success: function (hcodeinfo) {  
        	if(hcodeinfo!=null){
        	   //判断手机号跟所选的流量包是否对应
        	   var hoperator = hcodeinfo.operator;
        	   var flag = false;
        	   $("input[name='strategy_operator']").each(function(){
        		   var operator = $(this).val();
        		   if(hoperator == operator){
        			   flag = true;
        			   var value = $("#strategy_operator_"+operator).val();
        			   $("#trafficoperator").val(value);
        		   }
        	   });
        	   if(!flag){
        		   $("#messagenot").html("手机号不对应所选类型流量包,请重新填写号码！");	
  				   return;
        	   }else{
				   $("#cmobile").val(mobile);
				   $("#addCMForm").submit();
			   }
	        }else{
	        	 $("#messagenot").html("未查询到对应的手机号信息！");
	        	 return;
	        }	        	
        },  
        dataType: "json",  
        error: function (hcodeinfo) {  
        	$("#messagenot").html("未查询到对应的手机号信息！");	
		    return;
        }  
    };  
    $.ajax(options); 	   
 } 
 
 function delchargemobile(mobile,operator){
	$.messager.confirm("提示","确定删除此充值号码？",function(){
	 	var bid=$("input[name=batchid]").val();
	 	$.getJSON("${pageContext.request.contextPath}/customerserver/deletemobile.mbay?batchid="+bid+"&mobile="+mobile+"&operator="+operator, function(json){
	         if(json.success){
	        	$("#bmf").submit();
	         }else{
	        	 $.messager.alert("提示","删除失败！");
	         }
	    });
	});
 }	
 
 function showadd(){
	$("div.add").show();
 }

 $(function(){
    $('.mbtx').hover(function(){    	
	    $(this).find('.mb_tip').show(); 
	 },function(){
		  $(this).find('.mb_tip').hide();	
	 })	; 
 });
</script>
</head>
<body>
	<div class='con'>
		<!--身体-->
		<div class='body clearfix'>
			<div class='body_con com_width'>
				<div class='b_tit'>批量充值</div>
				<%@ include file="/common/leftcon.jsp"%>
				<form id="addCMForm"
					action="<c:url value="/customerserver/batchcharge/addMobile.mbay"/>"
					method="post">
					<input type="hidden" name="batchid" value="${chargeinfo.id}">
					<c:forEach items="${chargeinfo.strategys}" var="strategy">
						<input type="hidden" name="strategy_operator"
							value="${strategy.operator.ordinal()}">
						<input type="hidden"
							id="strategy_operator_${strategy.operator.ordinal()}"
							value="${strategy.operator}">
					</c:forEach>
					<input type="hidden" id="trafficoperator" name="operator" value="">
					<input type="hidden" id="cmobile" name="mobile" value="">
				</form>
				<form id='bmf'
					action="<c:url value="/customerserver/batch_mobileinfo.mbay"/>"
					method="post" class='clearfix fr'>
					<input type="hidden" name="batchid"
						value="${chargeMobileForm.batchid}">
					<div class='fr' style='width: 828px'>
						<div class='cxhm'>
							<span class='hm'>查询号码</span> <input type='text' name="mobile"
								value="${chargeMobileForm.mobile}" placeholder='请输入手机号'
								class='hm_txt onlynum' maxlength="11" /> <input type='submit'
								class='btn_1 btn_confirm' style="height: 24px; width: 76px;"
								value='查询' /> <input type='button' class='hd_btn btn_confirm'
								style="height: 24px; width: 85px;" onclick="showadd()"
								value='新增号码' />
						</div>
						<div class='cxhm add' style="display: none">
							<span class='hm'>添加号码</span><input type='text' id="chargemobile"
								placeholder='请输入手机号码' class='hm_txt onlynum' maxlength="11" /> <input
								type='button' class='btn_1 btn_confirm'
								style="height: 24px; width: 76px;" id="addMbtn" value='确定' /> <span
								style="color: red" id="messagenot"></span>
						</div>
						<!--表格-->
						<div class='tb'>
							<table>
								<tr>
									<th width="200px">手机号码</th>
									<th>地区</th>
									<!-- <th>流量</th>
		                      <th>价格</th> -->
									<th>操作</th>
								</tr>
								<c:forEach items="${batchchargemobiles }" var="bm">
									<tr>
										<td width="200px">${bm.mobile }</td>
										<td>${bm.operator.value}全国</td>
										<%--  <td>${bm.trafficpackage.traffic }M</td>
		                      <td>${bm.trafficpackage.mbayprice}美贝</td> --%>
										<td><a href="javascript:void(0)"
											onclick='javascript:delchargemobile("${bm.mobile}","${bm.operator}")'
											class='del'>删除</a></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<m:page pageinfo="${pageinfo}" formid="bmf" />
					</div>
					<!--付费信息-->
					<div class='ser_box' style='clear: both'>
						<div class='ser_1'>
							<div class='zjhm fl'>
								<span>共<span class='sl_0'>${chargeinfo.mobilenum }</span>个号码
								</span>
							</div>

							<!-- 
                      <div class='sy fr'>
                         <span>预计消耗美贝:<span class='sl_1'>${chargeinfo.costmbay }美贝</span></span>
                      </div>
                       -->
						</div>
						<div class='ser_2'>
							<input type='checkbox' checked="checked" id='tx' /> <span
								class='mbtx'> <label>短信提醒 <a>短信模板</a></label>
								<div class='mb_tip' style='display: none'>${sms_template}<em></em>
								</div>
							</span>
						</div>
					</div>
					<c:if test="${chargeinfo.mobilenum>0}">
						<!--选择日期-->
						<div class='xzrq'>
							<input type='button' class='tj btn_confirm'
								style="height: 24px; width: 85px;" value='立即批充' />
						</div>
					</c:if>
				</form>
			</div>
		</div>
		<!--尾部-->
	</div>
</body>
</html>