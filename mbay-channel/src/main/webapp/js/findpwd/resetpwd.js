$(function(){
	$.validator.addMethod('code', function (value, element) {
      var tel = /^([a-zA-Z0-9]|[_]){6,20}$/;
      var mail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
      return this.optional(element) || (tel.test(value))||mail.test(value);
       }, '');	 
	  
	  
   $('#pwdForm').validate({
	  onkeyup:false,
      rules : {
               username : { 
                   required : true, 
                   code:true     
               },
			   password:{
				   required : true, 
                   rangelength : [6,20]				       
					   },
			   repassword:{
				   required : true, 
				   equalTo:"#password"
				   }	   
              },
      messages : {             
			  password: {
                     required : '密码不得为空，试试字母、数字、符号的组合！',
                     rangelength : '密码6到20位，试试字母、数字、符号的组合！！'
                     },
			  repassword:{
				     required : '确认密码！',
					 equalTo:'密码不一致!'
				     }
           }
				 
   });	
   
});