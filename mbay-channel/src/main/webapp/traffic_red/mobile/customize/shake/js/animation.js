$(document).ready(function() {
	//dropthing();	
	//对象跳动动画
	//左右摇摆
	//setInterval('swings()',100);
	//摇起来
	//setInterval('swing()',300);
	//setInterval('sways()',200);
	
	setInterval('swings()',100);
	setInterval('ms1()',100);
});

function swings(){
	var s=$(".shake_img>img");
	$(s).animate({marginLeft:"10px"},200)
		.animate({marginLeft:"-20"},200);				 
}
function swing(){
	var s=$(".shake_img");			
	$(s).css({'-webkit-transform':'rotate(-6deg)'});
}
function sways(){
	var s=$(".shake_img");
	$(s).css({'-webkit-transform':'rotate(6deg)'});
}
function ms1(){
	$('.mess1').animate({marrinTop:'5px'},200)
			   .animate({marrinTop:'-10px'},200);
	$('.mess2').animate({marrinTop:'-5px'},200)
			   .animate({marrinTop:'10px'},200);	
	$('.mess3').animate({marrinTop:'5px'},200)
			   .animate({marrinTop:'-10px'},200);					
}

//倒计时
//var t=3;//设定跳转的时间
//setInterval("refer()",1000); //启动1秒定时
//function refer(){
//	if(t==0) {
//		//$("#time").hide();
//		//$(".mess").hide();
//		$(".jiggle").slideDown();
//		$(".mess").hide();
//		$("#time").hide();
//		//$(".mess").show();
//		setInterval('swings()',100);
//		//
//		setInterval('ms1()',100);
//		return;	
//	}
//	$("#time").html(t);
//	t--; 
//}