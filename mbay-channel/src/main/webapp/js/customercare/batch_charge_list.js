$(function(){
  /*弹出层*/
  $('.tanchu_1 .btn_c_1').click(function(){
	  alert('1');
	  });
  $('.tanchu_2 .btn_c_1').click(function(){
	  alert('2');
	  });
  $('.tanchu_3 .btn_c_1').click(function(){
	  alert('3');
	  });
  $('.tanchu_4 .btn_c_1').click(function(){
	  alert('4');
	  });
  $('.ck').click(function() {
      $( ".tanchu_1" ).dialog({resizable: false,modal: true,draggable:false});
    });
  $('.sc').click(function() {
      $( ".tanchu_2" ).dialog({resizable: false,modal: true,draggable:false});
    });
  $('.ty').click(function() {
      $( ".tanchu_3" ).dialog({resizable: false,modal: true,draggable:false});
    });
  $('.qy').click(function() {
      $( ".tanchu_4" ).dialog({resizable: false,modal: true,draggable:false});
    });
});