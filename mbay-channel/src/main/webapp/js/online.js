/**支付选中js***/
$(function(){
	var con=null;
   $('.r_sec li').hover(function(){
	$(this).css('border','1px solid #b7b7b7') ;
	 
  },function(){
	$(this).css('border','1px solid #fff');  
	$(con).css('border','1px solid #b7b7b7') ;
  });
  $('.r_sec li').click(function(){
	if($(this).attr("payMethod")=="directPay"){
		if($(this).attr("platform")=="alipay"){
			$("input[name=platform]").val("alipay");
		}else if($(this).attr("platform")=="baifubao"){
			$("input[name=platform]").val("baifubao");
		}
		$("input[name=payMethod]").val("directPay");		
		$("input[name=bankCode]").val("");		
	}else{
		$("input[name=payMethod]").val("bankPay");	
		$("input[name=bankCode]").val($(this).attr("bankcode"));		
	}
	$('.r_sec li a').removeClass('on_0');
	$('.r_sec li').css('border','1px solid #fff');
	$(this).css('border','1px solid #b7b7b7').find('a').addClass('on_0');
	con=$(this) ; 
  });
  $('.acount_bank p').hover(function(){
	  $(this).css('background','#FDF7E3');
  },function(){
	  $(this).css('background','#fff');
  });
  $('.acount_bank p').click(function(){
	  $('.blue-radio a').removeClass('on_0');
	  $(this).find('.blue-radio a').addClass('on_0');
  });
  $('.sel_tit a').eq(0).click(function(){
	  $('#pay').show();
	  $('#acount').hide();
  });
  $('.sel_tit a').eq(1).click(function(){
	  $('#acount').show();
	  $('#pay').hide();
  });
});