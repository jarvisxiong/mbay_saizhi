$(function(){
 /*验证*/
 $("#hd").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
			"sz" : /^[0-9]+$/,
			"rq" :/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
			"db" : /^[0-9]+\.{0,1}[0-9]{0,2}$/,
			"mobile": validateRegExp.mobile,
			"redsel": function() {
				if ($(".traffics .chk_1:checked").size() > 0) {
					return false;
				}
			},
			"mbaysel": function() {
				if ($(".mbays .chk_1:checked").size() > 0) {
					return false;
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
			"redps": function(gets, ele) {
				if (!/^[0-9]+$/.test(gets)) {
					$("#red_product_a").click();
					return "输入格式不正确！";
				}
				
				var mps = getMaxPS();
				if (parseInt(gets) < mps) {
					$("#red_product_a").click();
					//return "不能小于最大红包产品(" + mps + ")!";
					return "请填写正确的美贝值";
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
			"mbayps": function(gets, ele) {
				if (!/^[0-9]+$/.test(gets)) {
					$("#mb_product_a").click();
					return "输入格式不正确！";
				}
				
				var mps = getMaxPS();
				if (parseInt(gets) < mps) {
					$("#mb_product_a").click();
					//return "不能小于最大MB产品(" + mps + ")!";
					return "请填写正确的美贝值";
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
			"products": function(gets, ele) {
				if ($(".chk_1:checked").size() == 0) {
					return "请至少选择一项产品！";
				}
				/*var pass = true;
				$(".chk_1:checked").each(function() {
					if (!/^(?:[1-9]\d*)*$/.test($(this).siblings(":text").val())) {
						pass = false;
						return false;
					}
				});
				if (!pass) {
					return "权重必须为正整数(>=1)！";
				}*/
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
			"shinfo": function() {
				if ($("input[name='share']").attr("checked") == "checked") {
					return false;
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
			"limitSelect": function(gets, ele) { 
				var id = $(ele).attr("id");
				if (gets != "UNLIMITED") {
					return /^[0-9]+$/.test($("[name="+id+"Value]").val());
				}
			}
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
			
			return true;
		}
	}); 
});

$(document).ready(function() {
	$('input[type=radio][name="sendway"]').change(function() {
		if (this.value == 'TRAFFIC') {
			$("#mbaysendunit").html("");
			$("#traffic_only").show();
			$("#mbay_only").hide();
		} else if (this.value == 'MBAY') {
			$("#mbaysendunit").html($("#mbaysendunithtml").html());
			$("#traffic_only").hide();
			$("#mbay_only").show();
		}
	});
	
	$('input[type=checkbox][name="continuable"]').change(function() {
		if ($(this).attr("checked") == "checked") {
			$("#validityday_div").show();
			// 如果开启超出发放则出现再领一次
			$("#getAgain").show();
			$("#getAgainSpan").show();
		} else {
			$("input[name=validityday]").val(0);
			$("#validityday_div").hide();
			$("#getAgain").hide();
			$("#getAgainSpan").hide();
			// 如果没有开启超出发放默认是再玩一次
			$("#playAgain").click();
		}
	});
	
	
	$("#campaignLimit").change(function(){
		if($(this).val() == "UNLIMITED"){
			$("input[name='campaignLimitValue']").hide();
		}else{
			$("input[name='campaignLimitValue']").show();
		}
	});
	
	$("#mobileLimit").change(function(){
		if($(this).val() == "UNLIMITED"){
			$("input[name='mobileLimitValue']").hide();
		}else{
			$("input[name='mobileLimitValue']").show();
		}
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
	
	$("input[name='shareImage']").change(function() {
		var name = $(this).attr("textId");
		$("#" + name).val($(this).val());
	});
	
	$("input[name='share']").change(function(){
		if($(this).attr("checked") == "checked"){
			$("#chooseModel").show();
			$("#shareInfo").show();
			// 默认是再玩一次
			$("#playAgain").click();
		}else{
			$("#chooseModel").hide();
			$("#sendMBDiv").hide();
			$("#sendMB").val(0);
			$("#shareInfo").hide();
		}
	});
	
	$("#playAgain").bind("click",function(){
		$("#sendMB").val("");
		$("#sendMBDiv").show();
	});
	
	$("#getAgain").bind("click",function(){
		$("#sendMB").val(0);
		$("#sendMBDiv").hide();
	});
});

function chooseFile(fileEle) {
	$("[name='" + fileEle + "']").click();
}

//绑定运营商变更事件
$(document)
		.ready(
				function() {
					$('#sel,input[type=radio][name="gender_1"]').on(
							'change', function() {
								doRequest();
							});

					///绑定流量类型变更事件 国内/省内
					$('input[type=radio][name="gender_2"]').change(
							function() {
								doRequest();
							});

					function doRequest() {
						var area = $("#sel").val();
						if (area == "" || area == null) {
							return;
						}
						var trafficType = $(
								'input[type=radio][name="gender_2"]:checked')
								.val();
						if (trafficType == "" || trafficType == null) {
							return;
						}
						var oper = $('input[name=gender_1]:checked').val();
						if (oper == "" || oper == null) {
							return;
						}
						area = parseInt(area);
						var opers = [ oper ];
						if (oper == 0) {
							opers = [ 1, 2, 3 ];
						}
						for (var i = 0; i < opers.length; i++) {
							oper = opers[i];
							queryOperatorPackage(area, oper, trafficType);
						}
						$("#notmsg").html("");
					}

					function queryOperatorPackage(area, operator,
							trafficType) {
						$(".pact").html("");//清空所有流量包
						$(".yxz").html("");//清空已选择提示
						$(".traffictypeSTATE").show();
						$(".traffictypePROVINCE").show();
						$
								.getJSON(ctx+"/operator/queryTrafficPackage.mbay?area="
												+ area + "&operator="
												+ operator
												+ "&trafficType="
												+ trafficType,
										function(packages) {
											if (packages != null) {
												var state = false;
												for (var i = 0; i < packages.length; i++) {
													var tpackage = packages[i];
													if (tpackage.traffictype == 'PROVINCE') {
														province = true;
													}
													if (tpackage.traffictype == 'STATE') {
														state = true;
													}
													var opername = operator == 1 ? "移动"
															: operator == 2 ? "联通"
																	: "电信";
													var typestr = tpackage.traffictype == 'PROVINCE' ? "省内"
															: "全国";
													var packagehtml = "<div onclick='trafficpackage(this,"
															+ operator
															+ ","
															+ tpackage.id
															+ ")'  class='ll_box fl package ttype"
															+ tpackage.traffictype
															+ "'>"
															+ "<div class='box_wz fl'>"
															+ "<div class='box_wz_con'>"
															+ opername
															+ typestr
															+ "流量</div></div>"
															+ "<div class='box_cs fl'>"
															+ "<div class='box_cs_u'>"
															+ tpackage.traffic
															+ "MB</div>"
															+ "<div class='box_cs_d' value="+tpackage.mbayprice+" >"
															+ tpackage.mbayprice
															+ "美贝</div>"
															+ "</div><i></i></div>";
													$("#package" + operator)
															.append(
																	packagehtml);
												}
												///if(!state){$(".traffictypeSTATE").hide();}
												///if(!province){$(".traffictypePROVINCE").hide();}
												if (state) {
													showPackage('STATE');
												} else {
													showPackage('PROVINCE');
												}
											} else {
												$("#messagenot").html(
														"暂无此对应的数据流量包！");
											}
										});
					}
					function showPackage(ttype) {
						var $radios = $('input:radio[name=gender_2]');
						$radios.filter('[value=' + ttype + ']').prop(
								'checked', true);
						$(".package").hide();
						$(".ttype" + ttype).show();
					}
				});