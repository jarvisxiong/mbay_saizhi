$(function(){
	$.validator.addMethod('xm', function (value, element) {
      var tel = /[A-Za-z0-9\u4e00-\u9fa5]+$/;
      return this.optional(element) || (tel.test(value));
       }, '');
    $('#reg_qy_1').validate({
	    rules :{
	    	enterpriseName : {
				      required : true,
					  xm : true
				   },
				   address : {
				      required : true,
					  xm : true
				   },
				   website : {
					  required :true,
					  url : true
					}
			},
	    messages : {
	    	enterpriseName : {
				      required :'企业名称不能为空！',
					  xm : '请正确填写企业名称！'
				   },
				   address : {
					 required :'具体地址不能为空！',
					  xm : '请正确填写具体地址!'
				   },
				   website : {
					  required :'企业网址不能为空！',
					  url : '请正确填写企业网址！'
					}
			}	
	});	
});