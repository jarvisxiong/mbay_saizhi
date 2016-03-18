$(function() {
			/* 验证 */
			$("#hd").Validform({
						showAllError : true,
						tiptype : 3,
						datatype : {
							"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
							"sz" : /^[0-9]+$/,
							"rq" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
						},
						callback : function(form) {
							formSub();
							return false;
						}

					});
			var campaignId = '';

			$(".quantity,.price").blur(function() {
						var price = $(".price").val();
						var quantity = $(".quantity").val();
						if (price == "" || quantity == "") {
							return;
						}
						var url = ctx + "/campaign/countLockMbay.mbay";
						$.post(url, {
									price : price,
									quantity : quantity
								}, function(result) {
									$('.lockMbay').html(result);
								});
					});

			function formSub() {
				var activityName = $(".hdmc").val();
				var start_Time = $("#rq_1").val();
				var end_Time = $("#rq_2").val();
				var validity = $(".yxqx.onlynum").val();
				var price = $(".price").val();
				var quantity = parseInt($(".quantity").val());
				var mbay = $(".mbayPlatSend").val();
				if (mbay == "") {
					mbay = 0;
				}
				var mbayPlatSend = parseInt(mbay);
				if (quantity < mbayPlatSend) {
					$.messager.alert({ title: "系统提示", content: "美贝平台分销量不能大于总量！" });
					return;
				}
				var lockMbay = parseFloat($(".lockMbay").html());
				var repeatGet = $("input:radio[name='repeatGet']:checked")
						.val();
				var getInTime = $("#getInTime").attr('checked') == 'checked'
						? true
						: false;
				alert(getInTime);
				if (repeatGet == "true") {
					repeatGet = true;
				} else {
					repeatGet = false;
				}
				if (price != "") {
					price = parseFloat(price);
				}
				var link = $(".link").val();
				var describtion = $(".describtion").val();
				var url = ctx + "/campaign/create.mbay";
				$.post(url, {
							name : activityName,
							start_Time : start_Time,
							end_Time : end_Time,
							validity : validity,
							price : price,
							quantity : quantity,
							mbayPlatSend : mbayPlatSend,
							lockMbay : lockMbay,
							repeatGet : repeatGet,
							getInTime : getInTime,
							link : link,
							describtion : describtion
						}, function(result) {
							if (result.success) {
								window.location.href = ctx
										+ "/campaign/chooseStore.mbay?campaignId="
										+ result.message;
								/*
								 * campaignId = result.message;
								 * $("#fillInfo").hide();
								 * $('#selectStore').show(); gotoPage(1);
								 */
							}
						});
			};
		});
/**
 * 
 * @param {}
 *            pagenum
 * @param {}
 *            totalpage
 * @return {}
 */
function getPage(pagenum, totalpage) {
	var page = {
		prepage : function() {
			var num = pagenum;
			if (num <= 1) {
				return num;
			} else {
				return num - 1;
			}
		},
		currentpage : pagenum,
		nextpage : function() {
			var num = pagenum;
			if (num >= totalpage) {
				return num;
			} else {
				return num + 1;
			}
		}
	};
	return page;
}

/**
 * 
 * @param {}
 *            pagenum
 */
function gotoPage(pagenum) {
	$("#selectBody").html('');
	$("#pageinfo").html('');
	// 清空子选项
	$('.checkStore').removeAttr("checked");
	// 清空父选项
	$("#chooseAll").removeAttr("checked");
	$("#select-Store").removeAttr('disabled');
	if (pagenum == '' || pagenum == null) {
		return;
	}
	var url = ctx + "/campaign/get.mbay";
	var map = {};
	$.get(url, {
				pagenum : pagenum
			}, function(m) {
				map = m;
				showData(map.stores, getPage(map.page.pagenum,
								map.page.totalpage));
				var url = window.location.href;
				var suffix = url.substring(url.length - 5);
				if (suffix == ".mbay") {
					window.location.hash = "selectStore";
				}
			});
}