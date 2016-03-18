var hItems0 = 
		"<option value=\"\">时</option>" +
		"<option value=\"00\">00</option>" +
		"<option value=\"01\">01</option>" +
		"<option value=\"02\">02</option>" +
		"<option value=\"03\">03</option>" +
		"<option value=\"04\">04</option>" +
		"<option value=\"05\">05</option>" +
		"<option value=\"06\">06</option>" +
		"<option value=\"07\">07</option>" +
		"<option value=\"08\">08</option>" +
		"<option value=\"09\">09</option>" +
		"<option value=\"10\">10</option>" +
		"<option value=\"11\">11</option>" +
		"<option value=\"12\">12</option>" +
		"<option value=\"13\">13</option>" +
		"<option value=\"14\">14</option>" +
		"<option value=\"15\">15</option>" +
		"<option value=\"16\">16</option>" +
		"<option value=\"17\">17</option>" +
		"<option value=\"18\">18</option>" +
		"<option value=\"19\">19</option>" +
		"<option value=\"20\">20</option>" +
		"<option value=\"21\">21</option>" +
		"<option value=\"22\">22</option>" +
		"<option value=\"23\">23</option>";
var hItems1 = 
		"<option value=\"\">时</option>" +
		"<option value=\"01\">01</option>" +
		"<option value=\"02\">02</option>" +
		"<option value=\"03\">03</option>" +
		"<option value=\"04\">04</option>" +
		"<option value=\"05\">05</option>" +
		"<option value=\"06\">06</option>" +
		"<option value=\"07\">07</option>" +
		"<option value=\"08\">08</option>" +
		"<option value=\"09\">09</option>" +
		"<option value=\"10\">10</option>" +
		"<option value=\"11\">11</option>" +
		"<option value=\"12\">12</option>" +
		"<option value=\"13\">13</option>" +
		"<option value=\"14\">14</option>" +
		"<option value=\"15\">15</option>" +
		"<option value=\"16\">16</option>" +
		"<option value=\"17\">17</option>" +
		"<option value=\"18\">18</option>" +
		"<option value=\"19\">19</option>" +
		"<option value=\"20\">20</option>" +
		"<option value=\"21\">21</option>" +
		"<option value=\"22\">22</option>" +
		"<option value=\"23\">23</option>" ;
var mItems = 
		"<option value=\"\">分</option>" +
		"<option value=\"00\">00</option>" +
		"<option value=\"10\">10</option>" +
		"<option value=\"20\">20</option>" +
		"<option value=\"30\">30</option>" +
		"<option value=\"40\">40</option>" +
		"<option value=\"50\">50</option>"+
        "<option value=\"50\">59</option>";
var comTimeItems = 
		"<div class=\"input_p\">" +
			"<select name=\"h0\">" +
			hItems0 +
			"</select>&nbsp;" +
			"<label class=\"label\"> : </label>&nbsp;" +
			"<select name=\"m0\">" +
			mItems +
			"</select>&nbsp;" +
			"<label> - </label>&nbsp;" +
			"<select name=\"h1\">" +
			hItems1 +
			"</select>&nbsp;" +
			"<label class=\"label\"> : </label>&nbsp;" +
			"<select name=\"m1\" datatype=\"times\" nullmsg=\"请设置完整的时间段！\">" +
			mItems +
			"</select>&nbsp;";
var addTimeItems = 
		comTimeItems +
			"<a href=\"javascript:void(0)\" class=\"add_time time_btn\">添加</a>&nbsp;" +
			"<span class=\"Validform_checktip\">可选，未设置代表可全天参与</span>" +
		"</div>";
var delTimeItems = 
		comTimeItems +
			"<a href=\"javascript:void(0)\" class=\"time_btn\" onclick=\"removeTime(this)\">删除</a>&nbsp;" +
			"<span class=\"Validform_checktip\"></span>" +
		"</div>";

$(function() {
	/* 验证 */
	$("#hd").Validform({
		showAllError: true,
		tiptype: 3,
		datatype: {
			"hdmc": /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
			"redps": function(gets, ele) {
				if (!/^[0-9]+$/.test(gets)) {
					return "输入格式不正确！";
				}
				
				var mps = getMaxPS();
				if (parseInt(gets) < mps) {
					return "不能小于最大红包产品(" + mps + ")!";
				}
				
				function getMaxPS() {
					var max = 0;
					$(".traffics :checked").each(function() {
						var traffic = parseInt($(this).nextAll("span").text());
						if (max < traffic) {
							max = traffic;
						}
					});
					return max;
				} 
			},
			"redsel": function() {
				if ($(".traffics .chk_1:checked").size() > 0) {
					return false;
				}
			},
			"mbayps": function(gets, ele) {
				if (!/^[0-9]+$/.test(gets)) {
					return "输入格式不正确！";
				}
				
				var mps = getMaxPS();
				if (parseInt(gets) < mps) {
					return "不能小于最大MB产品(" + mps + ")!";
				}
				
				function getMaxPS() {
					var max = 0;
					$(".mbays :checked").each(function() {
						var traffic = parseInt($(this).nextAll("span").text());
						if (max < traffic) {
							max = traffic;
						}
					});
					return max;
				} 
			},
			"mbaysel": function() {
				if ($(".mbays .chk_1:checked").size() > 0) {
					return false;
				}
			},
			"redPackHighline": function(gets, ele) {
				if (!/^[0-9]*$/.test(gets)) {
					return "输入格式不正确！";
				}
				var redSize = $("[name=redPackPoolSize]").val();
				if (/^[0-9]+$/.test(gets) && /^[0-9]+$/.test(redSize)) {
					if (parseInt(gets) > parseInt(redSize)) {
						return "不能大于红包流量数量！";
					}
				}
			},
			"mbayTrafficHighline": function(gets, ele) {
				if (!/^[0-9]*$/.test(gets)) {
					return "输入格式不正确！";
				}
				var mbaySize = $("[name=mbayTrafficPoolSize]").val();
				if (/^[0-9]+$/.test(gets) && /^[0-9]+$/.test(mbaySize)) {
					if (parseInt(gets) > parseInt(mbaySize)) {
						return "不能大于MB流量数量！";
					}
				}
			},
			"rate": function(gets, ele) {
				if (!/^(\d*\.)?\d+$/.test(gets) 
						|| parseFloat(gets) < 0 
						|| parseFloat(gets) > 100) {
					return false;
				}
				if (!!RegExp.$1) {
					if (!/\.\d{1,5}$/.test(gets)) {
						return "小数点后最多5位！";
					}
				}
			},
			"cycleType": function(gets, ele) { 
				if (gets == '') {
					return "请选择！";
				}
				if (gets != "NO_LIMITED") {
					return /^[0-9]+$/.test($("[name=times]").val());
				}
			},
			"rq": /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
			"times": function(gets, ele, form) {
				var $sels = $(ele).prevAll("select").andSelf();
				var selIndexs = 0;
				$.each($sels.find("option").filter(":selected"), function() {
					if ($(this).index() > 0) {
						selIndexs++;
					}
				});
				if (selIndexs > 0) {
					if (selIndexs < 4) {
						return "请设置完整的时间段！";
					} else {
						var h0 = parseInt($sels.filter("[name=h0]").val());
						var m0 = parseInt($sels.filter("[name=m0]").val());
						var h1 = parseInt($sels.filter("[name=h1]").val());
						var m1 = parseInt($sels.filter("[name=m1]").val());
						if (h1 < h0 || (h1 == h0 && m1 <= m0)) {
							return "结束时间必须大于开始时间！";
						} 
					}
				}
			},
			"products": function(gets, ele) {
				if ($(".chk_1:checked").size() == 0) {
					return "请至少选择一项产品！";
				}
				var pass = true;
				$(".chk_1:checked").each(function() {
					if (!/^(?:[1-9]\d*)*$/.test($(this).siblings(":text").val())) {
						pass = false;
						return false;
					}
				});
				if (!pass) {
					return "权重必须为正整数(>=1)！";
				}
			},
			"file": function(gets, ele) {
				if (gets == '') {
					return false;
				} else {
					var suffix = gets.substring(gets.lastIndexOf(".") + 1);
					return /^(jpg|png|jpeg|gif)$/i.test(suffix);
				}
			},
			"shinfo": function() {
				if ($("#ses").attr("checked") == "checked") {
					return false;
				}
			},
			"empty": function(gets) {
				if (gets == '') {
					return true;
				}
				return false;
			},
			"mobile": validateRegExp.mobile,
			"url": /^(\w+:\/\/)?[\w-]+(\.[\w-]+)+.*$/
		},
		beforeSubmit: function(form) {
			// 处理流量产品
			var trafficIds = "";
			$("[id^=mobile-]:checked,[id^=unicom-]:checked,[id^=telecom-]:checked").each(function() {
				var $txt = $(this).siblings(":text");
				trafficIds += $txt.attr("packageId") + "-" + ($txt.val() == '' ? 1 : $txt.val()) + ",";
			});
			trafficIds = trafficIds.substring(0, trafficIds.length - 1); 
			$("#traffic_products").val(trafficIds); 
			
			// 处理美贝产品
			var mbayIds = "";
			$("[id^=mbay-]:checked").each(function() {
				var $txt = $(this).siblings(":text");
				mbayIds += $txt.attr("mbayId") + "-" + ($txt.val() == '' ? 1 : $txt.val()) + ",";
			});
			mbayIds = mbayIds.substring(0, mbayIds.length - 1);  
			$("#mbay_products").val(mbayIds);
			
			// 处理是否发送短信
			$("[name=sendsms]").val($("#tx").attr("checked") == "checked" ? "true" : "false");
			
			// 是否开启分享功能
			$("[name='shareInfo.enableState']").val($("#ses").attr("checked") == "checked" ? "ENABLED" : "DISENABLE");  
			
			// 中奖概率
			$("[name=trafficRate]").val($("#trafficRate").val());
			
			// 未开启分享，清除部分非法字段
			if (!$("#ses").attr("checked")) {
				if (!/^\d{1-3}$/.test($("[name='shareTimes']").val())) {
					$("[name='shareInfo.shareTimes']").val('');
				}
				
				var file = $("[name='shareInfo.shareImgFile']").val();
				var suffix = file.substring(file.lastIndexOf(".") + 1);
				if (!/^(jpg|png|jpeg|gif)$/i.test(suffix)) {
					$("[name='shareInfo.shareImgFile']").val('');
				}
			}  			
			return true;
		}    
	});

	$(".add_time").click(function() {
		$(delTimeItems).appendTo($(this).closest("td"));
	});
	
	$("#times-sel").change(function() {
		if ($(this).find(":selected").attr("value") == "NO_LIMITED") {
			$(".times").hide();
			$(this).next(".Validform_checktip").text('').removeClass("Validform_wrong Validform_right");
		} else {
			$(".times").show();
		}
	});
	$("#times-sel").trigger("change");
	
	$(":file").change(function() {
		var name = $(this).attr("name");
		name = name.replace(".", "-"); 
		$("#" + name).val($(this).val());
	});

	$(".table-content-ul a").each(function(index){
		$(this).click(function() {
			$(".table-content-ul a").removeClass();
			$(this).addClass("at-list");
			$(".table-list-content").hide();
			$(".table-list-content").eq(index).show();
		});
	});
	
	$(".tab-con-tab-ul a").each(function(index){
		$(this).click(function() {
			$(".tab-con-tab-ul a").removeClass();
			$(this).addClass("tab-ul-font");
			$(".table-10list-content").hide();
			$(".table-10list-content").eq(index).show();
		});
	});	
	
	$(".chk_1").change(function() {
		var traffic = $(".traffics .chk_1:checked").size();
		var mbay = $(".mbays .chk_1:checked").size(); 
		$("input:checkbox[name='selected_product']").remove();
		if(traffic>0){//选中有美贝产品
			$("#hd").append('<input type="checkbox" name="selected_product" value="TRAFFIC_PACKAGE" checked="checked" style="display: none"  />');
		}
		if(mbay>0){
			$("#hd").append('<input type="checkbox" name="selected_product" value="MBAY_PACKAGE" checked="checked" style="display: none"  />');	
		}
	});
	$(".chk_1:first").change();
	
	$("#ses").bind("change", function() {
		if ($(this).attr("checked") == "checked") {
			$(".shinfo").show();
		} else {
			$(".shinfo").hide();
		}
	});
});

function removeTime(ele) {
	$(ele).parent(".input_p").remove();
}

function writeTime(h0, m0, h1, m1) {
	if (arguments.callee.first === undefined) {
		arguments.callee.first = true;
	}
	if (arguments.callee.first) {
		if (!h0 && !m0 && !h1 && !m1) {
			document.write(addTimeItems);
		} else {
			setVal(addTimeItems);
		}
	} else {
		setVal(delTimeItems);
	}
	arguments.callee.first = false;
	
	function setVal(timeStr) {
		var $html = $("<div></div>").append(timeStr);
		$html.find("[name=h0] [value=" + h0 + "]").attr("selected", true);
		$html.find("[name=m0] [value=" + m0 + "]").attr("selected", true);
		$html.find("[name=h1] [value=" + h1 + "]").attr("selected", true);
		$html.find("[name=m1] [value=" + m1 + "]").attr("selected", true);
		document.write($html.html());
	}
}

function advancedSettings() { 
	var $ht = $("#advanced-form-pop");
	$ht.remove();
	
	$.messager.confirm({
		title: "模板配置",
		content: $ht.html(),
		button: {
			ok: {
				autoClose: false,
				callback: function() {
					$("#advanced-form").submit();
					$ht.appendTo(document.body);
				}
			},
			cancel: {
				callback: function() {
					$ht.appendTo(document.body);
				}
			}
		}
	});
	
	// 读取已设值
	$("#logoCycleLink").val($("[name='template.logoCycleLink']").val());
	$("#sharkResultLink").val($("[name='template.sharkResultLink']").val());
	$("#adLink1").val($("[name='template.adLink1']").val());
	$("#adLink2").val($("[name='template.adLink2']").val());
	$("#template-adImg1File").val($("[name='template.adImg1File']").val());
	$("#template-adImg2File").val($("[name='template.adImg2File']").val());
	/*$("#template-shakeIndexImgFile").val($("[name='template.shakeIndexImgFile']").val());
	$("#template-shakeUIImgFile").val($("[name='template.shakeUIImgFile']").val());*/
	
	// 绑定验证
	$("#advanced-form").Validform({
		showAllError: true,
		tiptype: 3,
		datatype: {
			"file": function(gets, ele) {
				if (gets == '') {
					return false;
				} else {
					var suffix = gets.substring(gets.lastIndexOf(".") + 1);
					return /^(jpg|png|jpeg|gif)$/i.test(suffix);
				}
			},
			"url": /^(\w+:\/\/)?[\w-]+(\.[\w-]+)+.*$/,
			"nullUrl": function(gets) {
				if (gets == '' || gets == "http://" || gets == "https://") {
					return true;
				}
				return false;
			}
		},
		beforeSubmit: function(form) {
			// 保存值
			$("[name='template.logoCycleLink']").val($("#logoCycleLink").val());
			$("[name='template.sharkResultLink']").val($("#sharkResultLink").val());
			$("[name='template.adLink1']").val($("#adLink1").val());
			$("[name='template.adLink2']").val($("#adLink2").val());
			$.messager.closeDialog();
			return false;
		}
	});
}

function chooseFile(fileEle) {
	$("[name='" + fileEle + "']").click();
}

function removeElement(ele) {
	$(ele).fadeOut();
}