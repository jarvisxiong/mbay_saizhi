$(function(){
	// 开关  （点击是否的开关）
	$(".slider-box .slider").bind({
		off: function() {
			$(this).parent().removeClass('on').addClass('off');
            $(this).siblings(":radio,:checkbox").attr("checked", false).trigger("change");
		},
		on: function() {
			$(this).parent().removeClass('off').addClass('on');
            $(this).siblings(":radio,:checkbox").attr("checked", true).trigger("change");
		}
	});
	
    $(".slider-box").not("[disabled]").click(function() {
    	if ($(this).hasClass("on")) {
    		$(this).find(".slider").trigger("off");
    	} else {
    		$(this).find(".slider").trigger("on");
    	}
    });
   
    // 提示信息
    $('.tip_i').hover(
    	function() {
    		$(this).find('.tooltip').show();   
    	},	
    	function() {
    		$(this).find('.tooltip').hide();   	
    	}
    );

    
    // 初始化值
    $(".slider-box :radio, .slider-box :checkbox").each(function() { 
    	if ($(this).is(":checked")) {
    		$(this).siblings(".slider").trigger("on");
    	} else {
    		$(this).siblings(".slider").trigger("off");
    	}
    });
    $('.mbtx label').hover(function(){
    	$(this).next('.mb_tip').show();
    },function(){
    	$(this).next('.mb_tip').hide();
    	
    });
});
