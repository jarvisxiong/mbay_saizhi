$(function(){
	$("#mbzz").Validform({
		tiptype:3,
		datatype:{			
			"sz" : /^[0-9]+$/,
			"transfer_account_auth":function(gets,obj,curform,regxp){
				if($("#mbayaccount").val()==""){
					return false;
				}
				var unumber=$("#accountvalue").val();
				if(unumber==""){
					unumber=$("#mbayaccount").val();
				   $("#accountvalue").val(unumber);
				}
				var auth_success=true;
				var auth_message="";
				 jQuery.ajax({
			            type: "get",
			            async: false,
			            url: absurl+"/account/check_transfer_account.mbay?usernumber="+unumber,
			            contentType: "application/json; charset=utf-8",
			            dataType: "json",
			            cache: false,
			            success: function (result) {
			            	if(result.success){
			            		auth_success=true;
							}else{
								auth_success=false;
								
							}
			            	auth_message=result.message;
			            },
			            error: function (err) {
			                return "验证失败！";
			            }
			        });
				if(auth_success){
					$("#mbayaccount").val(auth_message);
					return true;					
				}else{
					return auth_message;
				}
			}
		}
	});
	
	$("input[name=payAmount]").bind("keyup",function(){
		calculateTotalTransfer();		
	});
	$("input[name=sendAmount]").bind("keyup",function(){
		calculateTotalTransfer();		
	});
	
	function calculateTotalTransfer(){
		var transfer=$("input[name=payAmount]").val();
		var send=$("input[name=sendAmount]").val();
		if(transfer==""){
			transfer=0;
		}
		if(send==""){send=0;}
		var total=parseInt(transfer)+parseInt(send);
		$("#transfertotal").html(total);
	}
	//
	$('#subForm').click(function() {
		var send=$("input[name=sendAmount]").val();
		if(send==""){
			$("input[name=sendAmount]").val(0)
		}
		$.messager.confirm({
			title: "提示",
			content: "确认提交?",
			button: {
				cancel: {
					visible: false
				},
				ok: {
					callback: function() {
						$("#mbzz").submit();
					}
				}
			}
		});
	});
});