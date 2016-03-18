/**
 * 工作区js
 * **/
$(function(){ 
  $('.r_box  a').hover(function(){
	$(this).css('border','1px dotted #D2D2D2');
	$(this).find('.box_wz').css('color','#FDAB1B');
  },function(){
    $(this).css('border','1px dotted #fff');
	$(this).find('.box_wz').css('color','#35618F');
  });
  $("#call_main").hover(function(){
	  $(this).animate({left:"0px"},'fast');
  },function(){
	  $(this).animate({left:"-120px"},'fast');
  });
  $('.wx_con').hover(function(){
      $('.wx_s').css('display','block')	;  
  },function(){
	  $('.wx_s').css('display','none');		  
  });
});