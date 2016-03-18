$(function() {
	/* 验证 */

	var hcode = null;
	var stateData = null;
	var provinceData = null;
	$("#myForm").Validform({
				showAllError : true,
				tiptype : 3,
				datatype : {
					"sj" : /1[3|5|7|8|][0-9]{9}/
				},
				callback : function(form) {
					var packagePrice = parseFloat($("#packageprice").html());
					var availableAmount = parseFloat($("#availableAmount")
							.val());
					if (packagePrice > availableAmount) {
						$.messager.alert({ content: "您的账户余额不足，请充值！" });
						return false;
					}
					return true;
				}

			});

	$('.sl_1').keyup(function() {
				var a = $('.sl_1').val();
				$('.queren').html(a);
			});
	$('input[type=radio][name="traffictype"]').change(function() {
		/* showPackage(this.value); */
		if (hcode != null) {
			queryOperatorPackage(hcode.provcode, hcode.operator, $(this).val());
		}
	});

	function getHcodeInfo(mobile) {
		$.getJSON(absurl + "/mobile/attribution.mbay?mobile=" + mobile,
				function(hcodeinfo) {
					if (hcodeinfo != null) {
						var trafficType = $('input[type=radio][name="traffictype"]:checked')
								.val();
						$("#attribution").html(hcodeinfo.attribution);
						queryOperatorPackage(hcodeinfo.provcode,
								hcodeinfo.operator, trafficType);
						hcode = hcodeinfo;
					} else {
						$("#messagenot").html("未查询到对应的手机号信息！");
					}
					return null;
				});
	}
	function queryOperatorPackage(area, operator, trafficType) {
		var packages = null;
		if (trafficType == 'STATE' && stateData != null) {
			packages = stateData;
		}
		if (trafficType == 'PROVINCE' && provinceData != null) {
			packages = provinceData;
		}
		if (packages == null) {
			$.ajax({
						url : absurl
								+ "/operator/queryTrafficPackage.mbay?area="
								+ area + "&operator=" + operator
								+ "&trafficType=" + trafficType,
						type : 'GET',
						dataType : 'json',
						async : false,
						success : function(packs) {
							packages = packs;
							if (trafficType == 'STATE') {
								stateData = packages;
							}
							if (trafficType == 'PROVINCE') {
								provinceData = packages;
							}
						}
					});
		}
		$("#trafficpackage").html("");
		$(".ttype").show();
		// 查询手机号对应运营商的全国流量包显示

		if (packages != null) {
			$("#trafficpackage").html("");
			for (var i = 0; i < packages.length; i++) {
				var tpackage = packages[i];
				var pack = "<span class='package ttype" + tpackage.traffictype
						+ " zhao' mbprice=" + tpackage.mbayprice + " value='"
						+ tpackage.id + "'>" + tpackage.traffic
						+ "M<i></i></span>";
				$("#trafficpackage").append(pack);
			}
			refreshcss();// 设置流量包onclick事件
			showPackage(trafficType);// 显示国内/或省内流量包。
			// 重新绑定流量包点击事件

		} else {
			$("#trafficpackage").html("暂无此对应的数据流量包！");
		}

	}
	// 显示流量包种类 全国/省内
	function showPackage(ttype) {
		$(".ttype" + ttype).eq(0).trigger("click");// 执行当前类型第一个流量包onclick事件
	}
	// ////显示对应流量包美贝价格
	function setPackagePrice(obj) {
		$("#packageprice").html($(obj).attr("mbprice"));
		$("#tpkid").val($(obj).attr("value"));
	}
	$('#chargemobile').keyup(function() {
				var mobile = $(this).val();
				var reg = /^(1[3|5|7|8])[\d]{9}$/;
				$("#attribution").html("");
				if (reg.test(mobile)) {
					getHcodeInfo(mobile);
				}
			});

	function refreshcss() {
		var selected = null;
		$("#tpkid").val("");// 清除所选的流量包
		$('.zhao').click(function() {
					// 显示选中标签
					$('.zhao i').css('display', 'none');
					$(this).find('i').css('display', 'block');
					// 显示选中外边框
					$('.zhao').css('border', '1px solid #CCCCCC');
					$(this).css('border', '1px solid red');
					selected = $(this);
					setPackagePrice(this);
				});
		$('.zhao').hover(function() {
					$('.zhao').css('border', '1px solid #CCCCCC');
					$(this).css('border', '1px solid red');
				}, function() {
					$('.zhao').css('border', '1px solid #CCCCCC');
					$(selected).css('border', '1px solid red');
				});
	}
});
