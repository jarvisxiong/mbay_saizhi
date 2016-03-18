$(function(){ 
  $('.sec').hover(function(){
	     $('.sec:not(.selected)').removeClass('sec_1');
		 $(this).addClass('sec_1') ; 
	   },function(){
		 $('.sec').removeClass('sec_1');
	     $(".selected").addClass('sec_1');	
	   });
  $('.sec').click(function(){
	  var tid=$(this).attr("tid");
	  if(tid!=1){
		 $.messager.alert({ title: "模板选择", content: "此模板暂不可用,更多模板正在完善中！" });
		  return;
	  }
	  $("input[name=templateid]").val(tid);
	     $('.sec').removeClass('sec_1');
	     $('.sec').removeClass('selected');
		 $(this).addClass('sec_1') ; 
		 $(this).addClass('selected') ; 
	   });
});