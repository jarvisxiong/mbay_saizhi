$(function(){	
   $('.nav_list li').click(function(){
	$(this).addClass('action') ;
  });
  $('.nav_list li').hover(function(){
	$(this).css('background','#FD8400'); 
	},function(){
    $(this).css('background','#363636'); 
    });
  $('.cb').click(function(){
	  if($(this).attr("checked")){
		   $('.nextbtn').css('display','inline-block') ; 
	  }else{
		   $('.nextbtn').css('display','none');  
	  }
  });
});