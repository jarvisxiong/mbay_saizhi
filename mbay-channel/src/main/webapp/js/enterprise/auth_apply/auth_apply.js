//验证码  效果
var wait=59;  
var wait_voice=59;
function time() {  
	if (wait == 0) {					
	 $("#hqyzma").removeClass("getUnCode");	      
	 $("#hqyzma").addClass("getCode");	 
	 $("#hqyzma").attr("href",'javascript:getYzm()'); 
	 $("#hqyzma").html("获取验证码");  
	 wait = 59;  
	} else {  
	 $("#hqyzma").attr("href", 'javascript:void(0)');  				
	 $("#hqyzma").html("重新发送(" + wait + ")");  
	 $("#voiceyzm").show();
		wait--;  
		setTimeout(function() {  
			time() ; 
		},  
		1000);				
	}  
}  

function time_voice() {  
	if (wait_voice == 0) {					
	 $("#voiceyzm").attr("href",'javascript:voiceYzm()'); 
	 $("#voiceyzm").html("收不到短信?使用语音验证码");  
	 wait_voice=59;
	} else {  
	 $("#voiceyzm").attr("href", 'javascript:void(0)');  				
	 $("#voiceyzm").html("语音验证码已发送,重新发送(" + wait_voice + ")");  
		wait_voice--;
		setTimeout(function() {  
			time_voice() ; 
		},  
		1000);				
	}  
}  

//获取验证码
function getYzm(){
	if($("#telephone").val()!="")        {		
	      var value=$('#telephone').val();
	      var myreg = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/;
	      if(!myreg.test(value)){
	      	return;
	      }
	      $("#hqyzma").removeClass("getCode");	      
	      $("#hqyzma").addClass("getUnCode");	 
		  $.ajax({				  
	          type:"POST",  
	          url: ctx + '/certificate/auth/send_authcode_msg.mbay',  
	          async:true,  
	          data:{'telephone':value}, 
	          success: function(msg){ 	        	
	        	
	          } ,
	          error: function(error){ 	        	
		        	 
		      }  
	      });  
		  time();
	} 
}

//语音验证码
function voiceYzm(){
	if($("#telephone").val()!=""){		
	      var value=$('#telephone').val();
	      var myreg = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/;
	      if(!myreg.test(value)){
	      	return;
	      }
		  $.ajax({				  
	          type:"POST",  
	          url: ctx + '/certificate/auth/send_voiceyzm.mbay',  
	          async:true,  
	          data:{'telephone':value}, 
	          success: function(msg){ 	        	
	        	
	          } ,
	          error: function(error){ 	        	
		        	 
		      }  
	      });  
		  time_voice();
	} 
}

function onlyNum(event) {
    event = event || window.event;
	if (event.ctrlKey || event.shiftKey || event.altKey) {
		event.returnValue=false;
		event.preventDefault();
	}
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) {
	if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) {
		event.returnValue=false;
		event.preventDefault(); 
		}
	}
}