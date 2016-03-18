$(function(){
  $('.email_txl').toggle(function(){
	  $('.an_tc').css('display','block');

  },function(){
	  $('.an_tc').css('display','none');
  });
  /*收藏夹下拉*/
  
  /*收藏夹下拉*/
  $('.mbyx_1').toggle(function(){
		  $('.sp div').css('display','block');
		  $('.mbyx_1 img').attr('src',"../images/workimages/scj_1.jpg");
	  },function(){
		  $('.sp div').css('display','none');
		  $('.mbyx_1 img').attr('src','../images/workimages/scj_0.jpg');
	  });
  
  /*左侧效果*/
  $('.xx').hover(function(){
	 $(this).css('color','#FDAB1B');
	 $(this).addClass('xx_1');
	  },function(){
     $(this).css('color','#35618F')	;
	 $(this).removeClass('xx_1');	  
	});
	
  $('.sjx').hover(function(){
	 $(this).css('color','#FDAB1B');
	 $(this).addClass('sjx_1');
	  },function(){
     $(this).css('color','#35618F')	;
	 $(this).removeClass('sjx_1');	  
	});
	
	$('.fjx').hover(function(){
	 $(this).css('color','#FDAB1B');
	 $(this).addClass('fjx_1');
	  },function(){
     $(this).css('color','#35618F')	;
	 $(this).removeClass('fjx_1');	  
	});
	
	$('.ysc').hover(function(){
	 $(this).css('color','#FDAB1B');
	 $(this).addClass('ysc_1');
	  },function(){
     $(this).css('color','#35618F')	;
	 $(this).removeClass('ysc_1');	  
	});
	
	$('.wmm').hover(function(){
	 $(this).css('color','#FDAB1B');
	 $(this).addClass('wmm_1');
	  },function(){
     $(this).css('color','#35618F')	;
	 $(this).removeClass('wmm_1');	  
	});
});