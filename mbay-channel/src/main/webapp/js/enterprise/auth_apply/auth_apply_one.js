
$(function(){
/*
	   $("#test").ProvinceCity();
	    var selectProvinceValue = $(".js-enterprise-province").val();
	    $("#province").find("option:selected").attr("selected", false);
	    $("#province option[value='" + selectProvinceValue + "']").attr("selected", true);
	    $("#province").change();
	    */
	    var selectDepartmentValue = $(".js-enterprise-department").val();
	    $("#department").find("option:selected").attr("selected", false);
	    $("#department option[value='" + selectDepartmentValue + "']").attr("selected", true); 
	    
	    /* var selectEmployeesValue = $(".js-enterprise-employees").val();
	    $("#rs").find("option:selected").attr("selected", false);
	    $("#rs option[value='" + selectEmployeesValue + "']").attr("selected", true);
	    
	    var selectPropertyValue = $(".js-enterprise-property").val();
	    $("#xz").find("option:selected").attr("selected", false);
	    $("#xz option[value='" + selectPropertyValue + "']").attr("selected", true);
	    
	    var selectCategoryValue = $(".js-enterprise-category").val();
	    $("#fl").find("option:selected").attr("selected", false);
	    $("#fl option[value='" + selectCategoryValue + "']").attr("selected", true);
	*/
	//导航条  （点击高亮 变色）
    $('.nav_list li').click(function(){
    	$(this).addClass('action') ;
      });
    //导航条  （鼠标移上去  变色）
      $('.nav_list li:not(:first)').hover(function(){
    	$(this).css('background','#FD8400') ;
    	},function(){
        $(this).css('background','#363636'); 
        });
    /*  $('#rq').datepicker();*/
      /*$( document).tooltip({
          show: null,
          position: {
            my: "left top",
            at: "left bottom"
          },
          open: function( event, ui ) {
            ui.tooltip.animate({ top: ui.tooltip.position().top + 5 }, "fast" );
          }
        });*/
	
	//验证
	$("#yyfb").change(function(){
		chaneyyfb();
	});
	$("#swfb").change(function(){
		chaneswfb();
	});
	
	$("#zzfb").change(function(){
		chanezzfb();
	});
	
	
	function chaneyyfb(){
		if(checkFileSize("yyfb")){		
			readURL("yyfb");
			return true;
		}else{			
		return false;
		}	
	}
	
	function chaneswfb(){
		if(checkFileSize("swfb")){
			readURL("swfb");
			return true;
		}	
		return false;
	}
	function chanezzfb(){
		if(checkFileSize("zzfb")){
			readURL("zzfb");
			return true;
		}	
		return false;
	}
	
	
	
	$.validator.addMethod('yyfbtypeyz', function (value, element) {
	     return checkFileType("yyfb");		 
	}, '');
	$.validator.addMethod('yyfbsizeyz', function (value, element) {
		return   chaneyyfb();
	}, '');
	
	$.validator.addMethod('swfbtypeyz', function (value, element) {
		return checkFileType("swfb");		 
	}, '');
	$.validator.addMethod('swfbsizeyz', function (value, element) {
		return   chaneswfb();
	}, '');
	
	$.validator.addMethod('zzfbtypeyz', function (value, element) {
		return checkFileType("zzfb");		 
	}, '');
	$.validator.addMethod('zzfbsizeyz', function (value, element) {
		return   chanezzfb();
	}, '');
	
	 $.validator.addMethod('web', function (value, element) {
		 if(value!="http://"){
			 var web = /^((https|http|):\/\/)(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\.[a-z]{2,6})(:[0-9]{1,4})?((\/?)|(\/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+\/?)$/;
		      return this.optional(element) || (web.test(value));
		 }
		 return true;
	     
	       }, '');
	 
	 
   $.validator.addMethod('xm', function (value, element) {
      var tel = /[A-Za-z0-9\u4e00-\u9fa5]+$/;
      return this.optional(element) || (tel.test(value));
       }, '');
   $.validator.addMethod('lx', function (value, element) {
      var tel = /[A-Za-z\u4e00-\u9fa5]+$/;
      return this.optional(element) || (tel.test(value));
       }, '');
   $.validator.addMethod('sjh', function (value, element) {
      var tel = /^[1][3-8]\d{9}$/;
      if(tel.test(value)){
    	  $('#codeinput').show();
      }
      return this.optional(element) || (tel.test(value));
       }, '');
   $.validator.addMethod('gh', function (value, element) {
      var tel =   /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;    
      return this.optional(element) || (tel.test(value));
       }, '');
   //5
   $.validator.addMethod('xm', function (value, element) {
	      var tel = /[A-Za-z0-9\u4e00-\u9fa5]+$/;
	      return this.optional(element) || (tel.test(value));
	       }, '');
   //6
   $.validator.addMethod('xm', function (value, element) {
	      var tel = /[A-Za-z0-9\u4e00-\u9fa5]+$/;
	      return this.optional(element) || (tel.test(value));
	       }, '');
		 $.validator.addMethod('zz', function (value, element) {
			 var tel = /^[0-9]{14}[a-zA-Z0-9]{1}$/;
	      return this.optional(element) || (tel.test(value));
	       }, '');
		 $.validator.addMethod('jg', function (value, element) {
	      var tel = /^[a-zA-Z0-9]{8}-[a-zA-Z0-9]$/;
	      return this.optional(element) || (tel.test(value));
	       }, '');
		 $.validator.addMethod('zb', function (value, element) {
	      var tel = /^\d{1,}$/;
	      return this.optional(element) || (tel.test(value));
	       }, '');
		 $.validator.addMethod('lx', function (value, element) {
	      var tel = /[A-Za-z\u4e00-\u9fa5]+$/;
	      return this.optional(element) || (tel.test(value));
	       }, '');
		//  
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
		   
   $('#myForm').validate({
       rules : {
		     contactsname : {
				       required : true,
				       lx : true
				  },
				  department:{
					required:true,
					minlength:1
			      },
				  jobtitle : {
				       required : true,
					   xm :true
				  },
				  contactsEmail : {
			           required :true,
				       email : true
				  },
				  authcode:{
						required :true,
						varity:true
					},
				  contactsphone : {
				       required : true,
					   sjh : true
				  },
				  fixphone : {
				     /* required :true,*/
					  gh : true
				 },
				 enterpriseName : {
				      required : true,
					  xm : true
				   },
				   address : {
				      required : true,
					  xm : true
				   },
				   employees : {
					      required : true,
					      minlength:1
					   },
				 businessCategory : {
						     required : true,
						     minlength:1
						   },
				  property : {
						 required : true,
						 minlength:1
							},
				   website : {
					  required :false,
					  web : true
					},
					licenseNumber : {
						   required : true,
						   maxlength:15,
						   zz : true
						   },
				     legaltative : {
						   required :true,
						   lx :true
						   },
				     capital : {
						   required : true,
						   zb : true
						   },
					 registerDate : {
						   required : true,   
						   date : true
						   },
					organizationCode : {
						   required : true,
						   jg : true
						   },
					 taxNumber :{
						   required : true,
						   maxlength:15,
						   zz :true
						   },
					 licensefile : {
						   required : true,
						   yyfbtypeyz:true,
						   yyfbsizeyz:true
						   },
				     taxfile : {
						    required :true,
						    swfbtypeyz:true,
						    swfbsizeyz:true
						  },
					 codefile :{
						    required :true,
						    zzfbtypeyz:true,
						    zzfbsizeyz:true
						  }
				  
				  
		   }, 
	messages :{
		     contactsname : {
				       required:'联系人姓名不能为空,可由中文或英文组成！',
				       lx :'请填写真实姓名，可由中文或英文组成！'
				  },				  
					department:{
						required:'请选择部门!'
					      },
				  jobtitle : {
				       required : '职务/头衔不能为空！',
					   xm :'请正确填写职务/头衔！'
				  },
				  contactsEmail : {
			          required : '邮箱地址不能为空！',
				      email : '请正确填写邮箱！'
				},
				  authcode:{
						required :'请输入获取的验证码！',
						varity:'输入验证码不正确'
					},			  
				contactsphone : {
				 required : '手机号不能为空！',
				 sjh : '请正确输入手机号!'
			  },
			  	fixphone : {
				      /*required :'电话号码不能为空，如021-22222222',*/
					  gh : '请正确填写电话号码，如021-22222222！'
				 },
				 enterpriseName : {
				      required :'企业名称不能为空！',
					  xm : '请正确填写企业名称！'
				   },
				   address : {
					 required :'具体地址不能为空！',
					  xm : '请正确填写具体地址!'
				   },
				   employees : {
					      required : '请选择企业人数!'
					   },
				 businessCategory : {
						     required :'请选择企业类型！'
						   },
				  property : {
						 required : '请选择企业性质!'
							},
				   website : {					  
					   web : '请正确填写企业网址！'
					},
					licenseNumber : {
					    required :'营业执照编号不能为空！',
					    zz : '请正确填写营业执照编号！'
					   },
					   legaltative : {
					   required :'法定代表人不能为空！',
					   lx :'请正确填写法定代表人！',
					   },
			     capital : {
					   required : '注册资本不能为空！',
					   zb : '请正确填写注册资本！',
					   },
			      registerDate : {
						required :'请选择日期！',
						date : '请选择正确的日期！'
						},
				organizationCode : {
					   required : '组织机构代码不能为空！',
					   jg : '请正确填写组织机构代码！'
					   },
				 taxNumber :{
					   required : '税务登记证号不能为空！',
					   zz :'请正确填写税务登记证号！'
					   },
				licensefile : {
					   required : '请上传营业执照副本！',
					  yyfbtypeyz:"图片格式不正确！",
					  yyfbsizeyz:"图片应小于2M！"
					   },
				 taxfile :{
					   required :'请上传税务登记证副本！',
					   swfbtypeyz:"图片格式不正确！",
					   swfbsizeyz:"图片应小于2M！"
					   },
				 codefile :{
					    required :'请上传组织代码机构证副本！',
					    zzfbtypeyz:"图片格式不正确！",
					    zzfbsizeyz:"图片应小于2M！"
					  }
 		   }
   });	
   //验证码  效果
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
		//交互  前后台交互
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
//验证验证码
function onlyNum(event) {
	event = event || window.event;
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) {
		if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) {
			event.returnValue=false;
			event.preventDefault(); 
		}
	}
}
