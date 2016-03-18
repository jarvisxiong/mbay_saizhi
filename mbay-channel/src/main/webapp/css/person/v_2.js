$(function(){
	$("#frontfile").change(function(){
		chanefrontimg();
	});
	$("#backfile").change(function(){
		chanebackimg();
	});
	
	function chanefrontimg(){
		if(checkFileSize("frontfile")){
			readURL("frontfile");
			return true;
		}	
		return false;
	}
	
	function chanebackimg(){
		if(checkFileSize("backfile")){
			readURL("backfile");
			return true;
		}	
			return false;
	}
	
	$.validator.addMethod('zjzmtypeyz', function (value, element) {
	     return checkFileType("frontfile");	
	}, '');
	$.validator.addMethod('zjzmsizeyz', function (value, element) {		
		return chanefrontimg();
		
	}, '');
	$.validator.addMethod('zjfmtypeyz', function (value, element) {
		return checkFileType("backfile");			
	}, '');
	$.validator.addMethod('zjfmsizeyz', function (value, element) {			
		return chanebackimg();
	}, '');
   $.validator.addMethod('sfz', function (value, element) {
      var tel = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
      return this.optional(element) || (tel.test(value));
       }, '');
   $.validator.addMethod('sjh', function (value, element) {
      var tel = /^[1][3-8]\d{9}$/;
      if(tel.test(value)){
    	  $('#codeinput').show();
      }
      return this.optional(element) || (tel.test(value));
       }, '');
   $.validator.addMethod('true_name', function (value, element) {
      var tel = /[A-Za-z0-9\u4e00-\u9fa5]+$/;
      return this.optional(element) || (tel.test(value));
       }, '');
   $.validator.addMethod('lx', function (value, element) {
      var tel = /[A-Za-z\u4e00-\u9fa5]+$/;
      return this.optional(element) || (tel.test(value));
       }, '');
   //验证手机发送的验证码
   jQuery.validator.addMethod("varity", function(value, element) {    //用jquery  ajax验证验证码
		  var flag = 1;  
	      $.ajax({  
	          type:"POST",  
	          url:'/mbaychannel/certificate/auth/varity_authcod.mbay',  
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
   
   
   $('#regAuthForm').validate({
	    rules : {
	    	true_name : {
				required : true,
		        lx : true
				},				
				address : {
				required :true,
				true_name : true
				}, 
				IDType:{
					required : true,
					 minlength:1
				},
				identityNo : {
				required : true,
				sfz : true
				},
			email : {
				required :true,
				email : true
				},
				telephone : {
				required : true,
				sjh : true
				},
			authcode:{
				required :true,
				varity:true
			},
				frontfile : {
				 required : true,
				 zjzmtypeyz : true,
				 zjzmsizeyz : true
				},
				backfile : {
				 required : true,
				 zjfmtypeyz : true,
				 zjfmsizeyz : true
				}
	    },
		messages: {
			true_name : {
				 required:'姓名不能为空！',
				 lx : '请正确填写真实姓名！'
			   },			   
			   address : {
				 required:'请详细填写有用地址!',
				 true_name : '请正确填写地址!'
			   },
			   IDType:{
					required : '请选择证件类型!'
				},
			   identityNo : {
				 required:'证件号码不能为空!',
				 sfz : '请正确仔细填写证件号码!'
			   },
			email : {
				 required : '邮箱地址不能为空！',
				 email : '请正确填写邮箱！'
			   },
			   telephone : {
				 required : '手机号不能为空！',
				 sjh : '请正确输入手机号!'
			  },
			  authcode:{
					required :'请输入获取的验证码！',
					varity:'输入验证码不正确'
				},			  
			  frontfile : {
				 required :'请上传证件正面！',
				 zjzmtypeyz:"图片格式不正确！",
				 zjzmsizeyz:"图片应小于2M！"
				 } ,  
			   backfile : {
				 required :'请上传证件反面！',
				 zjfmtypeyz:"图片格式不正确！",
				 zjfmsizeyz:"图片应小于2M！"
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
		if($("#telephone").val()!=""){
		      $(this).removeClass("hqyma");	      
		      $(this).addClass("grayhqyma");	      
		       value=$('#telephone').val();
			  $.ajax({				  
		          type:"POST",  
		          url:'/mbaychannel/certificate/auth/send_authcode_msg.mbay',  
		          async:true,  
		          data:{'telephone':value}, 
		          success: function(msg){   
		          } ,
		          error: function(error){ 	
			          }  
		      });  
			  time(this);
		}			  
	});  
});