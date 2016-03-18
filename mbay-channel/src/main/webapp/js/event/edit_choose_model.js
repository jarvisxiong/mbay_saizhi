$(function(){
	/*边框选中效果*/
	var selected=null;
    $('.bj_box_1 a').click(function(){
	 $('.bj_box_1 i').css('display','none');
	  $(this).find('i').css('display','block');
	 $('.bj_box_1 a').css('border','1px solid #fff');  
	 $(this).css('border','1px solid red');
	 selected=$(this);
  });
   $('.bj_box_1 a').hover(function(){
	 
	 $(this).css('border','1px solid red');
	 $(selected).css('border','1px solid red');
  },function(){
	   $('.bj_box_1 a').css('border','1px solid #fff') ;
	   $(selected).css('border','1px solid red');
  });
	  
  $('.bj_box_2 a').click(function(){
	 $('.bj_box_2 i').css('display','none');
	  $(this).find('i').css('display','block') ; 
	 $('.bj_box_2 a').css('border','1px solid #fff') ; 
	 $(this).css('border','1px solid red');
	 selected=$(this);
  });
   $('.bj_box_2 a').hover(function(){	 
	 $(this).css('border','1px solid red');
	 $(selected).css('border','1px solid red');
  },function(){
	   $('.bj_box_2 a').css('border','1px solid #fff') ;
	   $(selected).css('border','1px solid red');
  });
	  
  $('.bj_box_3 a').click(function(){
	 $('.bj_box_3 i').css('display','none');
	  $(this).find('i').css('display','block') ; 
	 $('.bj_box_3 a').css('border','1px solid #fff'); 
	 $(this).css('border','1px solid red');
	 selected=$(this);
  });
   $('.bj_box_3 a').hover(function(){
	 
	 $(this).css('border','1px solid red');
	 $(selected).css('border','1px solid red');
  },function(){
	   $('.bj_box_3 a').css('border','1px solid #fff') ;
	   $(selected).css('border','1px solid red');
  });
	  
	/*点击换背景图*/
	 $('.bj_box_1 a:eq(0)').click(function(){
		 $('.wx_body').removeClass('bg_2');
		 $('.wx_body').removeClass('bg_3');
		 $('.wx_body').removeClass('bg_4');
		 $('.wx_body').addClass('bg_1');
	 });
	 $('.bj_box_1 a:eq(1)').click(function(){
		 $('.wx_body').removeClass('bg_1');
		 $('.wx_body').removeClass('bg_3');
		 $('.wx_body').removeClass('bg_4');
		 $('.wx_body').addClass('bg_2');
	 });
	 $('.bj_box_1 a:eq(2)').click(function(){
		 $('.wx_body').removeClass('bg_1');
		 $('.wx_body').removeClass('bg_2');
		 $('.wx_body').removeClass('bg_4');
		 $('.wx_body').addClass('bg_3');
	 });
	 $('.bj_box_1 a:eq(3)').click(function(){
		 $('.wx_body').removeClass('bg_1');
		 $('.wx_body').removeClass('bg_2');
		 $('.wx_body').removeClass('bg_3');
		 $('.wx_body').addClass('bg_4');
	 });
	 /*点击换文字风格*/
	 $('.bj_box_2 a:eq(0)').click(function(){
		 $('.wenzi img').attr('src','../images/workimages/img_1_0.png');
	 });
	 $('.bj_box_2 a:eq(1)').click(function(){
		 $('.wenzi img').attr('src','../images/workimages/img_1_1.png');
	 });
	 $('.bj_box_2 a:eq(2)').click(function(){
		 $('.wenzi img').attr('src','../images/workimages/img_1_2.png');
	 });
	 $('.bj_box_2 a:eq(3)').click(function(){
		 $('.wenzi img').attr('src','../images/workimages/img_1_3.png');
	 });
	 /*点击替换按钮风格*/
	 $('.bj_box_3 a:eq(0)').click(function(){
         $('.service').removeClass('ser_bg_2');
		 $('.service').removeClass('ser_bg_3');
		 $('.service').removeClass('ser_bg_4');
		 $('.service').addClass('ser_bg_1');
	 });
	 $('.bj_box_3 a:eq(1)').click(function(){
		 $('.service').removeClass('ser_bg_1');
		 $('.service').removeClass('ser_bg_3');
		 $('.service').removeClass('ser_bg_4');
		 $('.service').addClass('ser_bg_2');
	 });
	 $('.bj_box_3 a:eq(2)').click(function(){
		 $('.service').removeClass('ser_bg_1');
		 $('.service').removeClass('ser_bg_2');
		 $('.service').removeClass('ser_bg_4');
		 $('.service').addClass('ser_bg_3');
	 });
	 $('.bj_box_3 a:eq(3)').click(function(){
		 $('.service').removeClass('ser_bg_1');
		 $('.service').removeClass('ser_bg_2');
		 $('.service').removeClass('ser_bg_3');
		 $('.service').addClass('ser_bg_4');
	 });
});