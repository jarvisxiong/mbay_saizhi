$(function(){
  /*弹出层*/
  $('.qy').click(function() {
      $( ".tanchu_1" ).dialog({resizable: false,modal: true,draggable:false});
    });
  $('.tyb').click(function() {
      $( ".tanchu_2" ).dialog({resizable: false,modal: true,draggable:false});
    });
  $('.sc').click(function() {
      $( ".tanchu_3" ).dialog({resizable: false,modal: true,draggable:false});
    });
});