
$(function(){
	/**通用js控制按钮和a标签的样式**/
	$('input[type="submit"]').hover(function(){
         $(this).css('opacity',0.8);
		 $(this).css('cursor','pointer');			
	},function(){
	     $(this).css('opacity',1);	
	}) ;
	$('input[type="button"],button').hover(function(){
         $(this).css('opacity',0.8);
		 $(this).css('cursor','pointer');			
	},function(){
	     $(this).css('opacity',1);	
	});
	
	$('a').hover(function(){
        $(this).css('opacity',0.8);
		 $(this).css('cursor','pointer');			
	},function(){
	     $(this).css('opacity',1);	
	}) ;
	/*文本框高亮效果*/
	$("input[type='text']").not("[nohighlight]").on('focus.highlight',function(){
	  $(this).css('border','1px solid #80a4e5');   
      });	
	$("input[type='password']").not("[nohighlight]").on('focus.highlight',function(){
		$(this).css('border','1px solid #80a4e5');   
	});	
    $("input[type='text']").not("[nohighlight]").on('blur.highlight',function(){
	  $(this).css('border','1px solid  #c1c1c1') ;  
      });	
    $("input[type='password']").not("[nohighlight]").on('blur.highlight',function(){
    	$(this).css('border','1px solid  #c1c1c1') ;  
    });	
   
  });
  

  