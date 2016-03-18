$(function() {
	$("#chooseAll").click(function() {
		// var trs = $(".checkStore");
		if ($("#chooseAll")[0].checked) {
			// 全选
			$("input[name='store']").each(function() {
				this.checked = true;
			});
		} else {
			// trs.attr("checked", 'false');
			$("input[name='store']").each(function() {
				this.checked = false;
			});
		}
		/*
		 * // 反选 $.each(trs, function(i, checkbox) { if
		 * ($(checkbox).attr("checked") == 'checked') {
		 * $(checkbox).attr("checked", 'false');
		 * $(checkbox).removeAttr("checked"); } else {
		 * $(checkbox).attr("checked", 'true'); } });
		 */
	});

	/*
	 * $(".checkStore").click(function() {
	 * $("#chooseAll").removeAttr("checked"); $(this).attr("checked") ==
	 * 'checked'; });
	 */

	$("#select-Store").click(
			function() {
				var checkedStores = $("input[name='store']:checked");

				if (checkedStores.length == 0) {
					$.messager.alert({ content: "请先选择门店!" });
					return;
				}
				var ids = new Array();
				$.each(checkedStores, function(i, checkbox) {
					var id = $(checkbox).attr("value");
					ids.push(id);
				});
				var postUrl = ctx + "/campaign/selectStore.mbay";
				$.post(postUrl, {
					ids : ids,
					campaignId : campaignId
				}, function(result) {
					if (result.success) {
						if (confirm("继续选择门店？")) {
							window.location.href = ctx
									+ "/campaign/chooseStore.mbay?campaignId="
									+ campaignId;
							// 清空父选项
							$("#chooseAll").removeAttr("checked");
						} else {
							window.location.href = ctx + "/campaign/list.mbay";
						}
					} else {
						$.messager.alert({ content: "选择门店失败，请重试！" });
					}
				});
			});
});