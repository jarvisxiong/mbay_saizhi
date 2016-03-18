$(function(){
	
	//验证
	$.validator.addMethod('code', function (value, element) {
      var tel = /^([a-zA-Z0-9]|[_]){6,30}$/;
      var mail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
      return this.optional(element) || (tel.test(value))||mail.test(value);
       }, '');	
	  jQuery.validator.addMethod("unique", function(value, element) {    //用jquery ajax的方法验证用户名是不是已存在
		  var flag = 1;  
	      $.ajax({  
	          type:"POST",  
	          url:absurl+'/signup/authRegUserName.mbay',  
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
	  
	  //验证邀请码是否存在（用户的usernumber）
	  jQuery.validator.addMethod("authic", function(value, element) {    
		  var flag = 1;  
		  $.ajax({ 
			  type:"POST",  
			  url: absurl+'/signup/authInvitationCode.mbay',  
			  async:false,   //同步方法，如果用异步的话，flag永远为1  
			  data:{'usernumber':value},  
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
	          url:absurl+'/authcode/valAuthCode.mbay',  
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
      rules : {
               username : { 
                   required : true, 
                   rangelength:[6,30],
                   unique:true,
                   code:true                 
                       },
			   password:{
				   required : true, 
                   rangelength : [6,30]				       
					   },
			   repassword:{
				   required : true, 
				   equalTo:"#password"
				   },
			   authcode:{
					   required : true,	
					   number: true,
					   auth:true
					   },
			 supnumber:{
			   required : true,	
			   authic:true
				   }
			   
              },
      messages : {
              username: {
                     required : '用户名不得为空！',
                     rangelength:'用户名长度为6~30位字符!',
                     code:'长度为6～20个字符的英文字母，数字或下划线!',   
                     unique:'用户名已存在'
                     },
			  password: {
                     required : '密码不得为空，试试字母、数字、符号的组合！',
                     rangelength : '密码6到20位，试试字母、数字、符号的组合！'
                     },
			  repassword:{
				     required : '确认密码！',
					 equalTo:'密码不一致!'
				     },
			   authcode:{
						   required : '',	
						   number:'',
						   auth:''
						   },
				   supnumber:{
				    	 required : '请输入您的邀请码！',	
				    	 authic:'邀请不不存在！'
				     }
                 }
				 
   });	
   //设置密码的强弱
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
	$('#username').focus(function(){
	  $(this).css('border','1px solid #7ABD54');   
      });	
    $('#username').blur(function(){
	  $(this).css('border','1px solid  #c1c1c1') ;  
      })	;
    $('#password').focus(function(){
	  $(this).css('border','1px solid #7ABD54') ;  
      })	;
    $('#password').blur(function(){
	  $(this).css('border','1px solid  #c1c1c1');   
      });	
    $('#repassword').focus(function(){
	  $(this).css('border','1px solid #7ABD54');   
      });	
    $('#repassword').blur(function(){
	  $(this).css('border','1px solid  #c1c1c1') ;  
      });
    $('#agree').click(function(){
        $('#xuan').attr('checked',true);	
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
