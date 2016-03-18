$(function(){
  $('.cb').click(function(){
	  if($(this).attr("checked")){
		   $('.btnq').css('display','inline-block') ; 
	  }else{
		   $('.btnq').css('display','none');  
	  }
  });
});

