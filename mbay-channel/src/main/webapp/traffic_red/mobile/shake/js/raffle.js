$(function(){
	getYzm();
});

var wait=59;  
var wait_voice=59;
function time(){  
	if (wait == 0){					
	 $("#hqyzma").attr("href",'javascript:getYzm()'); 
	 $("#hqyzma").html("获取验证码");  
	 wait = 59;  
	}else{  
	 $("#hqyzma").attr("href", 'javascript:void(0)');  				
	 $("#hqyzma").html("重新发送(" + wait + ")");
	 $("#voicep").show();
	 wait--;  
	 setTimeout(function(){time()}, 1000);
	}
}  

function time_voice() {  
	if (wait_voice == 0) {					
	 $("#voicep").html("<span>收不到验证码？使用</span><a href='javascript:voiceYzm();' id='voiceyzm'>语音验证码</a>");  
	 wait_voice=59;
	} else {  
	 $("#voicep").html("<span>语音验证码已发送,</span><a href='javascript:void(0);' id='voiceyzm'>重新发送(" + wait_voice + ")</a>");
	 wait_voice--;
	 setTimeout(function(){time_voice()}, 1000);				
	}  
}  

function getYzm(){
	var cnumber=$("#cNumber").val();
	$.ajax({				  
		type: "POST",  
		url: ctx + '/tr_mobile/send_authcode.mbay',
		data: {'cNumber' : cnumber}
	});  
	time();
}

//语音验证码
function voiceYzm(){
	var cnumber=$("#cNumber").val();
	 $.ajax({				  
         type:"POST",  
         url: ctx + '/tr_mobile/send_voiceyzm.mbay',  
         async:true,  
         data:{'cNumber' : cnumber}
     });  
	 time_voice();
}

function submit_before(){
	var code = $("#yzm").val();
	var cnumber = $("#cNumber").val();
	if(!code){
		$.messager.remind({ content: "请输入手机收到的验证码" });
		return;
	}else{
		$.get(ctx + "/tr_mobile/raffle.mbay", {cNumber : cnumber, authcode : code}, function(result){
			if(result.success){
				window.location.href = result.message;
			}else{
				$.messager.remind({ content: result.message });
			}
		});
	}
}