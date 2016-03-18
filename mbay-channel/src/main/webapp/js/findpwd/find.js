$(function(){
	$.validator.addMethod('code', function (value, element) {
		  var tel = /^([a-zA-Z0-9]|[_]){6,20}$/;
	      var mail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	      return this.optional(element) || (tel.test(value))||mail.test(value);
       }, '');	 
	
	  jQuery.validator.addMethod("unique", function(value, element) {    //用jquery ajax的方法验证
		  var flag = 1;  
	      $.ajax({  
	          type:"POST",  
	          url:'/mbaychannel/signup/authRegUserName.mbay',  
	          async:false,   //同步方法，如果用异步的话，flag永远为1  
	          data:{'username':value},  
	          success: function(msg){  
	        	 if(!msg.success){
	        		 flag=0;
	        	 }
	          }  
	      });  
	      if(flag == 0){  
	          return true;  
	      }else{  
	          return false;  
	      }  
	  }, "sorry number have been exist"); 
	  
	  jQuery.validator.addMethod("auth", function(value, element) {    //用jquery  ajax验证验证码
		  var flag = 1;  
	      $.ajax({  
	          type:"POST",  
	          url:'/mbaychannel/authcode/valAuthCode.mbay',  
	          async:false,   //同步方法，如果用异步的话，flag永远为1  
	          data:{'authcode':value},  
	          success: function(msg){ 
	        	
	        	 if(!msg.auth){
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
	  
	  
	  
   $('#findForm').validate({	 
      rules : {
               username : { 
                   required : true, 
                   code:true,
                   unique:true 
               },			  
			 authcode:{
					   required : true,
					   auth:true
					   }	   
              },
      messages : {
              username: {
                     required : '用户名不得为空！',                    
                     code:'长度为6～30个字符的英文字母，数字或下划线!',
                    unique:'用户名不存在请重新输入!'
                     },	
                   authcode: {
                         required : '',
                         auth:''
                         }
           }
				 
   });	 
	
});

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