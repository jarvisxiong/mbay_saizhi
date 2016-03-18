/**
 * 兑换码兑换js
 * @author frank
 */
$(document).ready(function() {
	$("#dhm").blur(function() {
		checkDhm();
	});
	
	$("#sjh").blur(function() {
		checkSjh();
	});

	$(".duihuan-btn").bind("click",function(){
		var code_flag = checkDhm();
		var mobile_flag = checkSjh();
		if(code_flag && mobile_flag){
			$("#sjh_error").html("");
			//兑换
			$.post(ctx+"/redeemcode/redeem.mbay", {
				'code' : $("#dhm").val(),
				'mobile' : $("#sjh").val(),
				'campaignNumber':$("#campaignNumber").val(),
				'captcha' : $("#captcha").val(),
				'yzm' : $("#yzm").val()
			}, function(data) {		
				if(data.success){
					if(data.codeType == "MBAY"){
						window.location.href = ctx + 
						"/promotionCampaign/redeem_code/result_mbay_pack.mbay?from=redeem&number="
						+ $("#campaignNumber").val()+"&mb=" + data.message
						+ "&mobile=" + $("#sjh").val()
						+ "&code=" + $("#dhm").val();
					}else{
						//解绑click事件
						$('.duihuan-btn').unbind("click");
						$.messager.dialog({ 
							content: "您已成功领取" + data.message + "M流量，预计1-2个工作日内到帐!",
							autoClose: true,
							button: {
								close:{
									visible:true
								}
							}
						});
						//是否显示核销码
						if(data.verificate){
							$("#hxm").html(data.verificateCode);
							$("#hxm_div").show();
						}
					}
				}else{
					if(data.captcha){
						$("#captcha").val(true);
						$("#authImg").click();
						$("#yzm_div").show();
					}
					$.messager.remind({ content: data.message });
				}
			}, "json");
		}
	});
	
	//验证兑换码
	function checkDhm(){
		var code = $("#dhm").val();// 兑换码
		if(code.length == 0){
			$("#dhm_error").html("请输入兑换码");
			return false;
		}else{
			var number = $("#campaignNumber").val();
			//获取兑换说明
			$.getJSON(ctx+"/redeemcode/queryCode.mbay?code="+code+"&campaignNumber="+number, function(json){
				var rs = "无效"
				if(json.status){
					rs = "有效"
				}
				var title = "您输入的兑换码有误";
				if(json.queryTitle != ""){
					title = json.queryTitle;
				}
				var html = title + "(" + rs + ")";
				$("#dhm_error").html(html);
			});
			return true;
		}
	}
	
	//验证手机号
	function checkSjh(){
		var mobile = $("#sjh").val();// 手机号
		var reg = /^[1][3-8]\d{9}$/;
		if(mobile.length == 0){
			$("#sjh_error").html("请输入手机号");
			return false;
		}else if(mobile.length != 11 || !reg.test(mobile)){
			$("#sjh_error").html("手机号格式不正确");
			return false;
		}else{
			$("#sjh_error").html("");
			return true;
		}
	}
});