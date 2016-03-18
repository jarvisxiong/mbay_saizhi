$(function() {
	/* 验证 */
	$("#user_remind_set").Validform({
		showAllError : true,
		tiptype : 3,
		datatype : {
			"sz" : /^[0-9]+$/
		},
		callback : function(form) {
			subform();
			return false;
		}
	});
	$("#slider_0,#slider_1").toggle(
			function() {
				$(this).css("left", "50px").removeClass('off').addClass('on')
						.parent().css('background', '#629FCE');

			},
			function() {
				$(this).css("left", "0").removeClass('on').addClass('off')
						.parent().css('background', '#E0E0E0');
			});
	$('div i ').hover(function() {
		$(this).find('.tooltip').show();
	}, function() {
		$(this).find('.tooltip').hide();
	});
	function subform() {
		var smsRemindNum = $("#smsnum").val();
		var mbayRemindNum = $("#mbnum").val();
		var psoturl = ctx + "/channel/do_set_remind.mbay";
		$.post(psoturl, {
			'smsRemindPoint' : smsRemindNum,
			'mbayRemindPoint' : mbayRemindNum
		}, function(data) {
			var msg = "";
			if (data.success) {
				msg = "修改成功！";
				$.messager.remind(msg);
			}
			if (!data.success) {
				msg = "修改失败," + data.message;
				$.messager.warning(msg);
			}

		}, "json");

	}
	;
});