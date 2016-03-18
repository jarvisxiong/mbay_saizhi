//绑定运营商变更事件
$(document).ready(function() {
	var opers = [ 1, 2, 3 ];
	for (var i = 0; i < opers.length; i++) {
		oper = opers[i];
		queryOperatorPackage(oper);
	}
	$("#notmsg").html("");

	function queryOperatorPackage(operator) {
		$(".pact").html("");//清空所有流量包
		$(".yxz").html("");//清空已选择提示
		$(".traffictypeSTATE").show();
		$(".traffictypePROVINCE").show();
		$.getJSON(ctx+"/operator/queryTrafficPackage.mbay?area=0&operator="+ operator + "&trafficType=STATE",
		function(packages) {
			if (packages != null) {
				for (var i = 0; i < packages.length; i++) {
					var tpackage = packages[i];
					var opername = operator == 1 ? "移动" : operator == 2 ? "联通" : "电信";
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
							+ "全国流量</div></div>"
							+ "<div class='box_cs fl'>"
							+ "<div class='box_cs_u'>"
							+ tpackage.traffic
							+ "MB</div>"
							+ "<div class='box_cs_d' value="+tpackage.mbayprice+" >"
							+ tpackage.mbayprice
							+ "美贝</div>"
							+ "</div><i></i></div>";
					$("#package" + operator).append(packagehtml);
				}
			} else {
				$("#notmsg").html("暂无此对应的数据流量包！");
			}
		});
	}
});

/*全局变量选中*/
var selected = null;
/** 流量包选中** */
function trafficpackage(obj, type, id) {
	$('.tp' + type + ' .ll_box').removeClass('lt_xz');
	// 还原所有流量包为原样式
	$('.tp' + type + ' .ll_box').css('border', '1px solid #000');
	$(obj).css('border', '1px solid #E71F19');
	$(obj).addClass('lt_xz');
	selected = $(obj);
	$('.tp' + type + ' i').css('display', 'none');
	$(obj).find('i').css('display', 'block');
	showPackageSlected(obj, type, id);
};

function showPackageSlected(obj, type, id) {
	var area = $("#sel option:selected").text();
	var areacode = $("#sel option:selected").val();
	var operator = $(obj).find('.box_wz_con').text();
	var traffic = $(obj).find('.box_cs_u').text();
	var mbay = $(obj).find('.box_cs_d').text();
	var mbayprice = $(obj).find('.box_cs_d').attr("value");
	
	//判断是否选择同种类型的流量包，如果选择相同类型的，则删除原来的
	//已选择过的
	$(".yxz_package input[name=packageid]").each(function() {
		var alreadytype=$(this).attr("p_type");
		var alreadyareacode = $(this).attr("area")+""+alreadytype;
		//现在选择的
		var pid = areacode + type;
		if (alreadyareacode == pid) {
			$("tr[name='tr_category_"+alreadytype+"']").remove();
		}
	});	
	
	var strhtml = "<tr id='tr" + id + "' name='tr_category_" + type + "'><td>" + area
			+ "<input p_type='"+type+"' type='hidden' mbay='" + mbayprice + "' area='" + areacode + "' name='packageid' value='" + id + "'/>";
	strhtml+="<input  type='hidden'  name='area' value='" + areacode + "'/>";
	strhtml+="<input  type='hidden'  name='operator' value='" + type+ "'/>";
	strhtml+="</td>";
	strhtml += "'<td>" + operator + traffic + "</td><td>" + mbay
			+ "</td></tr>";
	$("#info" + type).html(strhtml);
	$('#sel_table table').append($("#info" + type).html());
	$("#notmsg").html("");//清空提示
}

$(function() {
	$("#excel_download").bind("click",function(){		
		$.fileDownload(actx+'/filedownload/files/batch_excel.mbay', {
		    failCallback: function (html, url) {
		    	$.messager.alert({ content: "下载失败！" });
		    }
		});	
	});
	
	$('input[type=radio][name="chargetype"]').on('change', function() {
		if ($(this).val() == "PERIOD_CHARGE") {
			$(".dqcz").show();
		} else {
			$(".dqcz").hide();
		}
	});
	$("#subbtn").click(function() {
		if ($("table input[name=packageid]").length == 0) {
			$("#notmsg").html("请至少选择一种策略！");
			return;
		}
		$("#hd").submit();
	});

	/* 验证 */
	$("#hd").Validform({
		showAllError : true,
		tiptype : 3,
		datatype : {
			"hdmc" : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,
			"sz" : /^[0-9]+$/,
			"rq" : /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
			"excel" : /^.*?\.(xls)|.(xlsx)$/
		}
	});
	
	$("#tabs").tabs();
	$('#ui-id-1').click(function() {
		$('#tabs ul').css('background', '#0083CF');
		$('#tabs').css('border', '1px solid #0083CF');
	});
	$('#ui-id-2').click(function() {
		$('#tabs ul').css('background', '#E71F19');
		$('#tabs').css('border', '1px solid #E71F19');
	});
	$('#ui-id-3').click(function() {
		$('#tabs ul').css('background', '#99BE0E');
		$('#tabs').css('border', '1px solid #99BE0E');
	});

	/** * */
	$('.yd_body .ll_box').hover(function() {
		$('.yd_body .ll_box').css('border', '1px solid #000');
		$(this).css('border', '1px solid #0083CF');
	}, function() {
		$('.yd_body .ll_box').css('border', '1px solid #000');
		$(selected).css('border', '1px solid #0083CF');
	});

	$('.lt_body .ll_box').hover(function() {
		$('.lt_body .ll_box').css('border', '1px solid #000');
		$(this).css('border', '1px solid #E71F19');
	}, function() {
		$('.lt_body .ll_box').css('border', '1px solid #000');
		$(selected).css('border', '1px solid #E71F19');
	});

	$('.dx_body .ll_box').hover(function() {
		$('.dx_body .ll_box').css('border', '1px solid #000');
		$(this).css('border', '1px solid #9BBD0E');
	}, function() {
		$('.dx_body .ll_box').css('border', '1px solid #000');
		$(selected).css('border', '1px solid #9BBD0E');
	});

	$('#tabs .yd_body .ll_box').click(
			function() {
				var val = $('#sel').val();
				var x_0 = $(this).find('.box_wz_con').text();
				var x_1 = $(this).find('.box_cs_u').text();
				var x_2 = $(this).find('.box_cs_d').text();
				$(".yxz_1").html('');
				$('.yxz_1').append(val).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append(x_0).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append(x_1).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append('价值:')
						.append(x_2);
			});
	$('#tabs .lt_body .ll_box').click(
			function() {
				var val = $('#sel').val();
				var x_0 = $(this).find('.box_wz_con').text();
				var x_1 = $(this).find('.box_cs_u').text();
				var x_2 = $(this).find('.box_cs_d').text();
				$(".yxz_2").html('');
				$('.yxz_2').append(val).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append(x_0).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append(x_1).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append('价值:')
						.append(x_2);
			});
	$('#tabs .dx_body .ll_box').click(
			function() {
				var val = $('#sel').val();
				var x_0 = $(this).find('.box_wz_con').text();
				var x_1 = $(this).find('.box_cs_u').text();
				var x_2 = $(this).find('.box_cs_d').text();
				$(".yxz_3").html('');
				$('.yxz_3').append(val).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append(x_0).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append(x_1).append(
						'<font>&nbsp;&nbsp;&nbsp;</font>').append('价值:')
						.append(x_2);
			});
	/* 四选一 */
	$('input[type=radio][name="operatortype"]').on('change', function() {
		var selval = $(this).val();
		$(".yxz").html("");
		$(".info").html("");
		$(".yxz_package").html("");//清空已选择提示
		if (selval != 0) {
			$(".pacbox").hide();
			$(".box" + selval).show();
		} else {
			$(".pacbox").show();
		}
	});
	
	//默认选中15号
	$(".dq_box a").each(function(){
		if($(this).text()==15){
			$(this).addClass("hight");
			$(this).addClass("selected");
		}
	});
	/////显示日期盒子
	 $('.text-input').click(function(){
	    $('.dq_box').show();	 
	    $('.dq_con').addClass('c_b');
	 });
	
	$('.dq_box a').hover(function(){
	   $(this).addClass('hight');
        },function(){
		$(this).removeClass('hight');	
		$(".selected").addClass("hight");
		}); 
		
		//选中日期
  $('.dq_box a:not(".d")').click(function(){
	   $('.dat').html(''); 
	   var c=$(this).text();
	   $("#period").val(c);
	     $('.dat').append(c);
	     $('.dq_con').removeClass('c_b');
	     $('.dq_box').toggle();	
	     $('.dq_box a').removeClass("selected");//去除所有selected
	     $('.dq_box a').removeClass("hight");//去除所有selected
	     $(this).addClass("selected");//标注当前选中标签
	   });
});