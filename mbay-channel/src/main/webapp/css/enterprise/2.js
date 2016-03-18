$(function() {
	$('.nav_list li').click(function() {
		$(this).addClass('action');
	});
	$('.nav_list li').hover(function() {
		$(this).css('background', '#FD8400');
	}, function() {
		$(this).css('background', '#363636');
	});
	/*
	 * $( document).tooltip({ show: null, position: { my: "left top", at: "left
	 * bottom" }, open: function( event, ui ) { ui.tooltip.animate({ top:
	 * ui.tooltip.position().top + 5 }, "fast" ); } });
	 */
});