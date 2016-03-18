$(function(){
 /*验证*/
 $("#hd").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
			"sz" : /^[0-9]+$/,
			"rq" :/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
		}
	}); 
});

$(document).ready(function() {
	$('input[type=radio][name="sendway"]').change(function() {
		if (this.value == 'TRAFFIC') {
			$("#mbaysendunit").html("");
		} else if (this.value == 'MBAY') {
			$("#mbaysendunit").html($("#mbaysendunithtml").html());
		}
	});
});
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
						$.getJSON(ctx+"/operator/queryTrafficPackage.mbay?area="
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