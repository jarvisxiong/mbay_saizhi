//直通车登录后左侧导航
$(function(){
  $('.gn_box h5').click(function(){
    $(this).parent().find('div').slideToggle(100);
	$(this).find('p').toggleClass('sj_1');
  });
});