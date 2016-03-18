
$(function(){
	   //验证手机发送的验证码
	   jQuery.validator.addMethod("varity", function(value, element) {    //用jquery  ajax验证验证码
			  var flag = 1;  
		      $.ajax({  
		          type:"POST",  
		          url:'/mbaychannel/resetpwd/findpwd/varity_authcod.mbay',  
		          async:false,   //同步方法，如果用异步的话，flag永远为1  
		          data:{'authcode':value},  
		          success: function(msg){ 	        	
		        	 if(!msg.varity){
		        		 flag=0;
		        	 }
		          }  
		      });  
		      if(flag == 0){ 
		          return false;  
		      }else{  
		          return true; 
		      }  
		  }, "");    
	   
	   $('#phoneForm').validate({
		    rules : {	    	
				authcode:{
					required :true,
					varity:true
				}
		    },
			messages: {			
				  authcode:{
						required :'请输入获取的验证码！',
						varity:'输入验证码不正确'
					}
			}   
	   });
		 var wait=59;  
		function time(o) {  
				if (wait == 0) {
					 $("#hqyzma").removeClass("grayhqyma");	      
				     $("#hqyzma").addClass("hqyma");	 
					o.removeAttribute("disabled"); 
					o.value="获取验证码";  
					wait = 59;  				
				} else {  
					o.setAttribute("disabled", true);  				
					o.value="重新发送(" + wait + ")";  
					wait--;  
					setTimeout(function() {  
						time(o) ; 
					},  
					1000);				
				}  
			}  
		$("#hqyzma").click(function(value){					
				$('#codeinput').show();
			      $(this).removeClass("hqyma");	      
			      $(this).addClass("grayhqyma");	      
			      value=$('#telephone').val();	
			      alert(value);
				  $.ajax({				  
			          type:"POST",  
			          url:'/mbaychannel/resetpwd/findpwd/send_authcode_msg.mbay',  
			          async:true,  
			          data:{'telephone':value}, 
			          success: function(msg){   
			          } ,
			          error: function(error){ 	
				          }  
			      });  
				  time(this);
		});  
	});
