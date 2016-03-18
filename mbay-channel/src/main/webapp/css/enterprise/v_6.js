$(function(){
     $.validator.addMethod('xm', function (value, element) {
      var tel = /[A-Za-z0-9\u4e00-\u9fa5]+$/;
      return this.optional(element) || (tel.test(value));
       }, '');
	 $.validator.addMethod('zz', function (value, element) {
      var tel = /^\d{15}$/;
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
	 $('#reg_qy_2').validate({
	    	 rules : {
	    		 licenseNumber : {
					   required : true,
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
					   zz :true
					   },
				 licensefile : {
					   required : true,
					   
					   },
			     taxfile : {
					    required :true 
					  },
				 codefile :{
					    required :true
					  }
				 },
			 messages : {
				 licenseNumber : {
					    required :'营业执照编号不能为空！',
					    zz : '请正确填写营业执照编号！'
					   },
					   legaltative : {
					   required :'法定代表人不能为空！',
					   lx :'请正确填写法定代表人！'
					   },
			     capital : {
					   required : '注册资本不能为空！',
					   zb : '请正确填写注册资本！'
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
					   required : '请上传营业执照副本！'
					   
					   },
				 taxfile :{
					   required :'请上传税务登记证副本！'
					   },
				 codefile :{
					    required :'请上传组织代码机构证副本！'
					  }
					  
				 }
	 })	;
});