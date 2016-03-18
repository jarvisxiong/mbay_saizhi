/** 登录后页面公用的js* */
$(function() {
	/* 导航栏 */
	$('.nav_list li ').hover(function() {
		$(this).css('background', '#FDAC1C');
	}, function() {
		$(this).css('background', '#356190');
	});
	/* 微信 */
	$('.wx_con').hover(function() {
		$('.wx_s').css('display', 'block');
	}, function() {
		$('.wx_s').css('display', 'none');
	});
	/* 客服 */
	/*$("body").Sonline({
		Position : "right",
		Top : 250,
		Effect : true,
		DefaultsOpen : false,
		Qqlist : "2985654507|客服01"
	});*/
	/* 头部 */
	$('.wx').hover(function() {
		$(this).addClass('wx_1');
	}, function() {
		$(this).removeClass('wx_1');
	});
	
	// 公示栏
	$(".tip").bind({
		hoverIn: function() {
			$(this).addClass('tip_1');
			$('.tip span').css('text-decoration', 'underline').css("color", "#059");
		},
		hoverOut: function() {
			if ($(this).find("#noticenum").text().indexOf('(') < 0) { 
				$(this).removeClass('tip_1');
				$('.tip span').css('text-decoration', 'none').css("color", "#6e716d");
			}
		}
	});
	$('.tip').hover(function() {
		$(this).trigger("hoverIn");
	}, function() {
		$(this).trigger("hoverOut");
	});
	if ($(".tip").find("#noticenum").text().indexOf('(') >= 0) { 
		$(".tip").trigger("hoverIn");
	}
	
	// 站内信
	$('.znx').bind({
		hoverIn: function() {
			$(this).addClass('znx_1');
			$('.znx span').css('text-decoration', 'underline').css("color", "#059");
		},
		hoverOut: function() {
			if ($(this).find("#msgnum").text().indexOf('(') < 0) { 
				$(this).removeClass('znx_1');
				$('.znx span').css('text-decoration', 'none').css("color", "#6e716d");
			}
		}
	});
	$('.znx').hover(function() {
		$(this).trigger("hoverIn");
	}, function() {
		$(this).trigger("hoverOut");
	});
	
	$('.zz').hover(function() {
		$(this).addClass('zz_1');
		$('.zz span').css('text-decoration', 'underline');
	}, function() {
		$(this).removeClass('zz_1');
		$('.zz span').css('text-decoration', 'none');
	});
	$('.b_sec').hover(function() {
		$(this).css('border', '1px solid #FBDF5D');
	}, function() {
		$(this).css('border', '1px solid #B5B4B4');
	});
	/** 导航栏当前页面显示选中样式*/
	
	/**文本框取消自动填充**/
	$("input[type='text']").attr("autocomplete","off");
	//$(".onlynum").keydown(function(){if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) event.returnValue=false; });	
	$(".onlynum").keypress(function(event) { 
        var keyCode = event.which; 
        if (keyCode == 8 || (keyCode >= 48 && keyCode <=57)) 
            return true; 
        else 
            return false; 
    }).focus(function() { 
        this.style.imeMode='disabled'; 
    }); 

	/***************************************************************************
	 * // jQuery plugin to prevent double submission of forms
	 * jQuery.fn.preventDoubleSubmission = function() {
	 * $(this).on('submit',function(e){ var $form = $(this);
	 * 
	 * if ($form.data('submitted') === true) { // Previously submitted - don't
	 * submit again e.preventDefault(); } else { // Mark it so that the next
	 * submit can be ignored $form.data('submitted', true); } });
	 *  // Keep chainability return this; };
	 * $('form').preventDoubleSubmission();
	 **************************************************************************/
	// /自定义alert方法

});
