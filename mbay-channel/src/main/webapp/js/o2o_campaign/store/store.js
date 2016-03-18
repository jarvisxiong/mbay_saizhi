$(function() {

	/* 验证 */
	$("#all-store").click(function() {
		if ($("#all-store")[0].checked) {
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
	});
});

function createStore() {
	var storeNum = $(".storeNum").val();
	var operatorNum = $(".operatorNum").val();

	if (!(checkValidate(storeNum) && checkValidate(operatorNum))) {
		$.messager.alert({ content: "输入非法，请重新操作!" });
		return;
	}

	storeNum = parseInt(storeNum);
	operatorNum = parseInt(operatorNum);
	var addUrl = ctx + "/store/addStore.mbay";
	$.post(addUrl, {
		storeNum : storeNum,
		operatorNum : operatorNum
	}, function(data) {
		if (data == true) {
			window.location.href = ctx + '/store/list.mbay';
			// $.messager.alert({ title: "系统提示", content: "新增门店失败，联系客服人员解决！" });
			closeBg();
		} else {
			$.messager.alert({ title: "系统提示", content: "新增门店失败，联系客服人员解决！" });
		}
	});
}

function query(src) {
	var number = $("#store-ope > input:first").val();
	if (number == "") {
		return;
	}
	$("#liststore").submit();
}
function addStore(src) {
	var dialog = $(".add_template").html();
	// 移出html中隐藏的模板
	$.messager.confirm({
		title: "新增门店",
		content: dialog,
		button: {
			ok: {
				callback: function() {
					createStore();
				}
			}
		}
	});
	$(".add_store_form").Validform({
		showAllError : true,
		tiptype : 3,
		beforeCheck : function(curform) {
		}
	});
}

var edit_template = null;
function editStore(id, number, status, name) {
	if (edit_template == null) {
		edit_template = $("#edit_template").html();
		$("#edit_template").remove();
	}
	$.messager.confirm({
		title: "编辑门店信息",
		content: edit_template,
		button: {
			ok: {
				callback: function() {
					var name = $(".s_name").val();
					var enable = $("input[class='s_enable']:checked").val();
					enable = parseInt(enable);
					var url = ctx + "/store/update.mbay";
					$.post(url, {
						id : id,
						name : name,
						enable : enable
					}, function(result) {
						if (result.success) {
							$.messager.alert({ title: "系统提示", content: "成功!" });
							window.location.reload(true);
						} else {
							$.messager.alert({ title: "系统提示", content: "新增门店失败，联系客服人员解决！" });
						}
					});
				}
			}
		}
	});
	$(".s_id").val(id);
	$(".s_number").val(number);
	$(".s_name").val(name);
	var radios = $(".s_enable");
	$.each(radios, function(i, n) {
		if (i == status) {
			$(n).attr("checked", 'checked');
		}
	});

}
function deleteStore(src) {
	var number = $(src).attr("href");
	if (number.length < 1) {
		// 本系统有规定，长度为定长
		return;
	}
	number = number.substr(1, number.length - 1);
	if (number == "") {
		return;
	}
	var deleteUrl = ctx + "/store/";
	$.ajax({
		type : "DELETE",
		url : deleteUrl + number + "/delete.mbay",
		success : function(msg) {
			window.location.href = ctx + '/store/list.mbay';
		}
	});
}

function checkValidate(value) {
	if (value == "") {
		return false;
	}
	var reg = /^[0-9]*[1-9][0-9]*$/;// 正整数
	var r = value.match(reg);
	if (r == null) {
		return false;
	}
	return true;
}

function batchDeleteStore() {

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

	var postUrl = ctx + "/store/batchDelete.mbay";

	$.post(postUrl, {
		ids : ids
	}, function(result) {
		if (result.success) {
			window.location.href = ctx + '/store/list.mbay';
		} else {
			alert(result);
			$.messager.alert({ content: "批量删除失败，请尝试逐条的删除!" });
		}
	});
}
