$(function(){
	$.validator.addMethod('code', function (value, element) {
      var tel = /^([a-zA-Z0-9]|[_]){6,30}$/;
      var mail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
      return this.optional(element) || (tel.test(value))||mail.test(value);
       }, '');
	  jQuery.validator.addMethod("unique", function(value, element) {    //用jquery ajax验证用户名是否已注册的方法验证
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
	          return false;  
	      }else{  
	          return true;  
	      }  
	  }, ""); 
	  
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
	  
	  
	  
   $('#regForm').validate({
	  onkeyup:false,
      rules : {
               username : { 
                   required : true, 
                   rangelength : [6,30],	
                   code:true,
                   unique:true                   
               },
			   password:{
				   required : true, 
                   rangelength : [6,20]				       
					   },
			   repassword:{
				   required : true, 
				   equalTo:"#password"
				   },
			 authcode:{
					   required : true,					   
					   auth:true
					   }			   
              },
      messages : {
              username: {
                     required : '用户名不得为空！',
                     rangelength:'用户名长度为6~30位字符!',
                     unique:'用户名已存在！',
                     code:'长度为6～30个字符的英文字母，数字或下划线!'
                     },
			  password: {
                     required : '密码不得为空！',
                     rangelength : '密码6到20 位！'
                     },
			  repassword:{
				     required : '确认密码！',
					 equalTo:'密码不一致!'
				     },		
			  authcode:{
						   required : '',	
						   auth:''
						   }
           }
				 
   });	
    $('#password').bind("keyup blur",function () {
        var strongRegex = new RegExp("^(?=.{8,20})(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
        var mediumRegex = new RegExp("^(?=.{7,20})(((?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
        var enoughRegex = new RegExp("(?=.{6,20}).*", "g");
        var reg=$.trim($(this).val());

        if (false == enoughRegex.test(reg)) {
            $('#div2').addClass('ruox');                   
        }
         else if (strongRegex.test(reg)) {
             $('#div2').removeClass('zhong');
            $('#div2').addClass('qiang');                   
        }
        else if (mediumRegex.test(reg)) {
            $('#div2').removeClass('ruo');
            $('#div2').addClass('zhong');
        }
         else {
             $('#div2').removeClass('ruox');  
            $('#div2').addClass('ruo');
        }
        return true;
    });
   
	$('#uname').focus(function(){
	  $(this).css('border','1px solid #7ABD54');   
      });	
    $('#uname').blur(function(){
	  $(this).css('border','1px solid  #c1c1c1') ;  
      });	
    $('#password').focus(function(){
	  $(this).css('border','1px solid #7ABD54') ;  
      });	
    $('#password').blur(function(){
	  $(this).css('border','1px solid  #c1c1c1') ;  
      })	;
    $('#repassword').focus(function(){
	  $(this).css('border','1px solid #7ABD54') ;  
      });	
    $('#repassword').blur(function(){
	  $(this).css('border','1px solid  #c1c1c1') ;  
      });	
    $('#agree').click(function(){
        $('#xuan').attr('checked',true);	
     });
});