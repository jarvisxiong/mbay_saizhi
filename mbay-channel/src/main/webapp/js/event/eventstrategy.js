/*全局变量选中*/

var selected=null;
var lockedmby=0;

/**联通流量包选中***/
function trafficpackage(obj,type,id){
	 $('.tp'+type+' .ll_box').removeClass('lt_xz');
	 //还原所有流量包为原样式
	 $('.tp'+type+' .ll_box').css('border','1px solid #000');
	 $(obj).css('border','1px solid #E71F19');
	 $(obj).addClass('lt_xz');
	 selected=$(obj);
	 $('.tp'+type+' i').css('display','none');
	 $(obj).find('i').css('display','block');
	 showPackageSlected(obj,type,id);
};
function showPackageSlected(obj,type,id){
	  var area=$("#sel option:selected").text();
	  var areacode=$("#sel option:selected").val();
	  var operator=$(obj).find('.box_wz_con').text();
	  var traffic=$(obj).find('.box_cs_u').text();
	  var mbay=$(obj).find('.box_cs_d').text();//10美贝
	  var mbayprice=$(obj).find('.box_cs_d').attr("value");//10
	  $("#yxz"+type).html("已选择："+area+" "+operator+" "+traffic+" 价格："+mbay);
	  var strhtml="<tr id='tr"+id+"'><td>"+area+"<input type='hidden' mbay='"+mbayprice+"' area='"+areacode+''+type+"' name='packageid' value='"+id+"'/> </td>";
	  strhtml+="'<td>"+operator+traffic+"</td><td>"+mbay+"</td><td><a href='javascript:removePackage(\"tr"+id+"\");'>删除</a></td></tr>";
	  $("#info"+type).html(strhtml);
}
function selectPackage(){
	var opertype=$('input[type=radio][name="gender_1"]:checked').val();
	$("#notmsg").html("");//清空提示信息
	var types=[opertype];
	if(opertype==0){//三网通用
		types=[1,2,3];
	}
	for(var i=0;i<types.length;i++){
	var type=types[i];
	var areacode=$("#sel option:selected").val()+''+type;
	var ieexist=false;
	$("table input[name=packageid]").each(function(){		
		var pid=$(this).attr("area");
		if(areacode==pid){
			 var area=$("#sel option:selected").text();
			 var opstr=type==1?"移动":type==2?"联通":"电信";
			$("#notmsg").html("您已添加 "+area+opstr+" 流量包，同一地区同一运营商流量包只可选取一种！");
			ieexist=true;
			return;
		}
	});
	if(!ieexist){		
   $('table').append( $("#info"+type).html()) ; 
   accountPackage();
  /// var speed = 1;//滚动速度
   //回到底部
   //var windowHeight = parseInt($("body").css("height"));//整个页面的高度
  /// $("body").stop().animate({ "scrollTop": windowHeight }, speed);
	}
  }
}
function removePackage(trid){
	$("#"+trid).remove();
	accountPackage();
}
function accountPackage(){
	 var quantity=$("#quantity").val();
	 var num=$("table input[name=packageid]").length;
	 $("#countstrategy").html(num);
	 var mbay=0;
	 $("table input[name=packageid]").each(function(){
		 var mb=parseFloat($(this).attr("mbay"));
		 if(mb>mbay){mbay=mb;};
	 });
	 lockedmby=quantity*mbay;
	 $("#lockedmbay").html(lockedmby);
}
$(function(){
	$("#subbtn").click(function(){
		if($("table input[name=packageid]").length==0){
			$("#notmsg").html("请至少选择一种策略！");
			return;
		}
	if(lockedmby>$("#availableamount").val()){
		$.messager.alert({ 
			title: "温馨提示", 
			content: "当前活动将锁定" + lockedmby + "美贝，您的账户余额不足，请充值！" 
		});		
		return;
	}
	$("#strateguform").submit();
	});

 /*验证*/
 $("#hd,#strateguform").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
			"sz" : /^[0-9]+$/,
			"rq" :/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
		}
	});
	$( "#tabs" ).tabs();
	$('#ui-id-1').click(function(){
	   $('#tabs ul').css('background','#0083CF');
	   $('#tabs').css('border','1px solid #0083CF')	;
    })
	$('#ui-id-2').click(function(){
	   $('#tabs ul').css('background','#E71F19')
	   $('#tabs').css('border','1px solid #E71F19')	
    })
	$('#ui-id-3').click(function(){
	   $('#tabs ul').css('background','#99BE0E')
	   $('#tabs').css('border','1px solid #99BE0E')	
    }); 
	
	/****/
    $('.yd_body .ll_box').hover(function(){
	 $('.yd_body .ll_box').css('border','1px solid #000') 
	 $(this).css('border','1px solid #0083CF')
   },function(){
	   $('.yd_body .ll_box').css('border','1px solid #000') 
	   $(selected).css('border','1px solid #0083CF')
	  })
	  
	  
	
    $('.lt_body .ll_box').hover(function(){
	 $('.lt_body .ll_box').css('border','1px solid #000') 
	 $(this).css('border','1px solid #E71F19')
   },function(){
	   $('.lt_body .ll_box').css('border','1px solid #000') 
	   $(selected).css('border','1px solid #E71F19')
	  })
	  
	  
    $('.dx_body .ll_box').hover(function(){
	 $('.dx_body .ll_box').css('border','1px solid #000') 
	 $(this).css('border','1px solid #9BBD0E')
   },function(){
	   $('.dx_body .ll_box').css('border','1px solid #000') 
	   $(selected).css('border','1px solid #9BBD0E')
	})
	
	$('#tabs .yd_body .ll_box').click(function(){
		var val=$('#sel').val();
		  var x_0=$(this).find('.box_wz_con').text();
		  var x_1=$(this).find('.box_cs_u').text()
		  var x_2=$(this).find('.box_cs_d').text()
		  $(".yxz_1").html('')
		  $('.yxz_1').append(val).append('<font>&nbsp;&nbsp;&nbsp;</font>').append(x_0).append('<font>&nbsp;&nbsp;&nbsp;</font>').append(x_1).append('<font>&nbsp;&nbsp;&nbsp;</font>').append('价值:').append(x_2)
	})
	$('#tabs .lt_body .ll_box').click(function(){
		var val=$('#sel').val();
		  var x_0=$(this).find('.box_wz_con').text()
		  var x_1=$(this).find('.box_cs_u').text()
		  var x_2=$(this).find('.box_cs_d').text()
		  $(".yxz_2").html('')
		  $('.yxz_2').append(val).append('<font>&nbsp;&nbsp;&nbsp;</font>').append(x_0).append('<font>&nbsp;&nbsp;&nbsp;</font>').append(x_1).append('<font>&nbsp;&nbsp;&nbsp;</font>').append('价值:').append(x_2)
	})
	$('#tabs .dx_body .ll_box').click(function(){
		var val=$('#sel').val();
		  var x_0=$(this).find('.box_wz_con').text()
		  var x_1=$(this).find('.box_cs_u').text()
		  var x_2=$(this).find('.box_cs_d').text()
		  $(".yxz_3").html('')
		  $('.yxz_3').append(val).append('<font>&nbsp;&nbsp;&nbsp;</font>').append(x_0).append('<font>&nbsp;&nbsp;&nbsp;</font>').append(x_1).append('<font>&nbsp;&nbsp;&nbsp;</font>').append('价值:').append(x_2)
	})
	/*四选一*/
	$('input[type=radio][name="gender_1"]').on('change', function() {
		 var selval=$(this).val();
		 $(".yxz").html("");
		 $(".info").html("");
		 if(selval!=0){
		 $(".pacbox").hide();
		 $(".box"+selval).show();
	   }else{
		   $(".pacbox").show();
	   }
	});
/*添加策略*/
   
	$('.sw_sec .cl_tj').click(function(){
		  var len=$('table tr').size()
	      var isChecked_1 =  $('input:radio:checked:eq(0)').val();
		  var isChecked_2 =  $('input:radio:checked:eq(1)').val();
	      var val=$('#sel').val();
		  var x_0=$('.yd_xz .box_wz_con').text();
		  var x_1=$('.yd_xz .box_cs_u').text();
		  var x_2=$('.yd_xz .box_cs_d').text();
		  var x_3=$('.lt_xz .box_wz_con').text();
		  var x_4=$('.lt_xz .box_cs_u').text();
		  var x_5=$('.lt_xz .box_cs_d').text();
		  var x_6=$('.dx_xz .box_wz_con').text();
		  var x_7=$('.dx_xz .box_cs_u').text();
		  var x_8=$('.dx_xz .box_cs_d').text();
	     $('table').append('<tr><td rowspan="3"></td><td rowspan="3">'+isChecked_1+'</td><td rowspan="3">'+val+'</td><td rowspan="3">'+isChecked_2+'</td><td >'+x_0+x_1+'</td><td>'+x_2+'</td><td rowspan="3"><a href="#">删除</a></td></tr> <tr><td >'+x_3+x_4+'</td><td>'+x_5+'</td></tr> <tr><td>'+x_6+x_7+'</td><td>'+x_8+'</td></tr>')  
		 $('.count_con').text(len)
	})
/*条数*/
})