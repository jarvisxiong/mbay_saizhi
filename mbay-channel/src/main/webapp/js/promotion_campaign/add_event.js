$(function(){
 /*验证*/
 $("#hd").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
			"sz" : /^[0-9]+$/,
			"rq" :/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
		}
	});
});