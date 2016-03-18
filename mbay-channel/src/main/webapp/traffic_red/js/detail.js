var cid, number;

$(function() { 
	cid = $("[name=id]").val();
	number = $("[name=number]").val();
	
	$("#form").Validform({
		showAllError: true,
		tiptype: 3,
		datatype: {
			"rq": /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,
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
						var h0 = parseInt($sels.filter("[name=startHour]").val());
						var m0 = parseInt($sels.filter("[name=startMinute]").val());
						var h1 = parseInt($sels.filter("[name=endHour]").val());
						var m1 = parseInt($sels.filter("[name=endMinute]").val());
						if (h1 < h0 || (h1 == h0 && m1 <= m0)) {
							return "结束时间必须大于开始时间！";
						} 
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
			"redPackHighline": function(gets, ele) {
				if (!/^[0-9]*$/.test(gets)) {
					return "输入格式不正确！";
				}
				var redSize = $(".tpool").text();
				if (/^[0-9]+$/.test(gets) && /^[0-9]+$/.test(redSize)) {
					if (parseInt(gets) > parseInt(redSize)) {
						return "不能大于红包产品流量总量！";
					}
				}
			},
			"mbayTrafficHighline": function(gets, ele) {
				if (!/^[0-9]*$/.test(gets)) {
					return "输入格式不正确！";
				}
				var mbaySize = $(".mpool").text();
				if (/^[0-9]+$/.test(gets) && /^[0-9]+$/.test(mbaySize)) {
					if (parseInt(gets) > parseInt(mbaySize)) {
						return "不能大于MB产品流量总量！";
					}
				}
			},
			"shinfo": function() {
				if ($("#ses").attr("checked") == "checked") {
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
			"url": /^(\w+:\/\/)?[\w-]+(\.[\w-]+)+.*$/
		},
		beforeSubmit: function(form) {
			// 未开启分享，清除部分非法字段
			if (!$("#ses").attr("checked")) {
				if (!/^\d{1-3}$/.test($("[name='shareInfo.shareTimes']").val())) {
					$("[name='shareInfo.shareTimes']").val('');
				}
				
				var file = $("[name='shareImg']").val();
				var suffix = file.substring(file.lastIndexOf(".") + 1);
				if (!/^(jpg|png|jpeg|gif)$/i.test(suffix)) {
					$("[name='shareImg']").val('');
				}
			}  
			
			return true;
		}
	});
	
	$("#recorddetail").click(function() {
		window.location.href = ctx
				+ "/traffic_red/redeem_detail.mbay?cnumber=" + number;
	});

	$(".qrcode").click(function() {
		$.layer({
		    type: 1,
		    title: false,
		    area: ['auto', 'auto'],
		    border: [0], //去掉默认边框
		    shade: [0.5, '#000'], //遮罩
		    shadeClose: true, //用来控制点击遮罩区域是否关闭层
		    closeBtn: [0, true], //去掉默认关闭按钮
		    page: {
		        dom: "#qrImg"
		    }
		});
	});
	
	//点击复制复制活动连接
	$('#copyCampaignUrl').zclip({
		path : ctx + '/js/zclip/ZeroClipboard.swf',
		copy : function() {
			return $("#qrLink").val();
		}	
	});
	
	$(".clearfix_list a:eq(0)").click(function(){
		$(".clearfix_list a").removeClass();
		$(this).addClass("clearfix_list2");
		$(".dabian").hide();
		$(".dabian:eq(0)").show();
	});
	
	$(".clearfix_list a:eq(1)").click(function(){
		$(".clearfix_list a").removeClass();
		$(this).addClass("clearfix_list2");
		$(".dabian").hide();
		$(".dabian:eq(1)").show();
	});	
	
	$("#ses").bind("change", function() {
		if ($(this).attr("checked") == "checked") {
			$(".shinfo").show();
			$("[name='shareInfo.enableState']").val("ENABLED"); 
		} else {
			$(".shinfo").hide();
			$("[name='shareInfo.enableState']").val("DISENABLE"); 
		}
	});
	$("#ses").change();
	
	//异步加载流量包产品
	$.getJSON(ctx+"/traffic_red/campaign/" + cid + "/trafficProducts.mbay",function(trafficProeucts){
		drawTrafficProducts(trafficProeucts);
	});
	
	//异步加载美贝产品
	$.getJSON(ctx+"/traffic_red/campaign/" + cid + "/mbayProducts.mbay",function(trafficProeucts){
		drawMbayProducts(trafficProeucts);
	});
	
	
	$("#ses").bind("change", function() {
		if ($(this).attr("checked") == "checked") {
			$(".shinfo").show();
			$("[name='shareInfo.enableState']").val("ENABLED"); 
		} else {
			$(".shinfo").hide();
			$("[name='shareInfo.enableState']").val("DISENABLE"); 
		}
	});
	$("#ses").change();
	
	triggerFileChange();
	
	changeCampaignType(".camp-type");
	
	//点击复制复制活动连接
	$('#ww').zclip({
		path : ctx + '/js/zclip/ZeroClipboard.swf',
		copy : function() {
			return "rwrwerwe";
		}	
	});
	
	$(".deploy>p:eq(0)").click(function(){
		$(".deploy>p").removeClass();
		$(this).addClass('p-border');
		$(".division").hide();
		$(".division:eq(0)").show();
	});
	$(".deploy>p:eq(1)").click(function(){
		$(".deploy>p").removeClass();
		$(this).addClass('p-border');
		$(".division").hide();
		$(".division:eq(1)").show();
	});	
	//上传图片
	$(".tab-ttrp>span:eq(0)").click(function(){
		$(".tab-ttrp>span").removeClass();
		$(this).addClass('tab-tr0');
		$(".tab-tr").hide();
		$(".tab-tr:eq(0)").show();
	})
	//上传背景图 
	$(".tab-ttrp>span:eq(1)").click(function(){
		$(".tab-ttrp>span").removeClass();
		$(this).addClass('tab-tr0');
		$(".tab-tr").hide();
		$(".tab-tr:eq(1)").show();
	});
	
	$(".key_reset").click(function(){
		$.messager.confirm({
			title:"温馨提示",
		    content:"你确定要重置key吗？<br/>重置key立即生效。所有使用旧key的接口将立即失效",
		    button:{
		    	ok: {
		    		callback:function(){
						$.post(ctx+"/traffic_red/campaign/"+cid+"/advanced/keyreset.mbay",function(result){
							if(result.success){
								$("#advanced_key_val").html(result.message);
								$.messager.remind("key重置成功！");
							}
						});
		    		}
		    	}
		    }
		});
	});

});

//绑定添加流量包产品事件
function addTrafficProduct(){
	$.getJSON(ctx+"/traffic_red/campaign/" + cid + "/traffic_package/unselected.mbay", function(result){
		var trafficProductHtml = $('#trafficProductTemplate').render(result);
		$.messager.confirm({
			title: "添加红包产品",
			content: trafficProductHtml,
			draggable: false,
			afterShow: function() {
				triggerTab();
			    validProductForm("#redPForm", function() {
			    	if ($("#redPForm").find(".chk_1:checked").size() > 0) {
			    		$.ajax({
							type: "POST",
							url: ctx+"/traffic_red/campaign/" + cid + "/producttype/TRAFFIC_PACKAGE/add.mbay",
							data: $("#redPForm").serialize(), 
							dataType: "json",
							success: function(data){
								//drawTrafficProducts(data);
								$.messager.remind("添加成功！");
								window.location.reload();
							}
		    	        });
			    	}
					$.messager.closeDialog();
				});
			},
			button: {
				ok: {
					autoClose: false,
					callback: function() {
						$("#redPForm").submit();
					}
				}
			},
			css:{width:620}
		});	
	});		
}


function increaseRedTrafficPool(){	
	var addTrafficHtml = '<div class="red-packet">增加大小：<input id="add_traffic_size" class="win-probability onlynum" >' +
			'&nbsp;MB</div><div id="redtip"></div>';
	$.messager.confirm({
		title: "增加红包池大小",
		content: addTrafficHtml,
		button: {
			ok: {
				autoClose: false,
				callback: function(dialog) {
					var addTraffic = $("#add_traffic_size").val();
					$.getJSON(ctx + "/account/redtraffic/balance.mbay", function(jsonResult){
						var balance = jsonResult.balance;
						if (addTraffic > balance) {
							$("#redtip").html("你的红包账户余额为：" + balance + "MB,不足于本次额度增加！");
							return ;
						}
						
						//@RequestMapping("campaign/{campaignId}/producttype/{productType}/increase/${poolsize}/add")
						var url = ctx+"/traffic_red/campaign/" + cid + "/producttype/TRAFFIC_PACKAGE/increase/" + 
								addTraffic + "/add.mbay";
						$.getJSON(url, function(resultJson){
							$.messager.closeDialog(dialog);
							if (resultJson.success) { 
								$("#tpool").html(parseInt($("#tpool").html()) + parseInt(addTraffic));
								$("#trpool").html(parseInt($("#trpool").html()) + parseInt(addTraffic));
							} else {
								$.messager.alert({ content: resultJson.message });
							}
						});
					});
				}
			}
		}
	});
}


function addMBProduct(){
	$.getJSON(ctx+"/traffic_red/campaign/" + cid + "/mbay_package/unselected.mbay",function(result){
		var mbayProductHtml = $('#mbayProductTemplate').render(result);
		$.messager.confirm({
			title: "添加MB产品",
	    	content: mbayProductHtml,
	    	draggable: false,
			afterShow: function() {
			    validProductForm("#mbayPForm", function() {
			    	if ($("#mbayPForm").find(".chk_1:checked").size() > 0) {
			    		$.ajax({
	    					type: "POST",
	    					url: ctx+"/traffic_red/campaign/" + cid + "/producttype/MBAY_PACKAGE/add.mbay",
	  	    	            data: $("#mbayPForm").serialize(), 
	  	    	            dataType: "json",
	  	    	            success: function(data){
	  	    	            ///	drawMbayProducts(data);
	  	    	            	$.messager.remind("添加成功！");
	  	    	            	window.location.reload();
	  	    	            }
	  	    	        });
			    	}
					$.messager.closeDialog();
				});
			},
	    	button: {
	    		ok: {
	    			autoClose: false,
					callback: function() {
						$("#mbayPForm").submit();
					}
	    		}
	    	},
	    	css:{width:620}
		});
	});
}

function increaseMBPool(){	
	var addTrafficHtml = '<div class="red-packet">增加大小：<input id="add_mbay_size" class="win-probability onlynum" >' +
			'&nbsp;MB</div><div id="mtip"></div>';
	$.messager.confirm({
		title: "增加MB池大小",
		content: addTrafficHtml,
		button: {
			ok: {
				autoClose: false,
				callback: function(dialog) {
					var addTraffic = $("#add_mbay_size").val();
					$.getJSON(ctx + "/account/mbaytraffic/balance.mbay", function(jsonResult) {
						var balance = jsonResult.balance;
						if (addTraffic > balance){
							$("#mtip").html("你的美贝账户余额为：" + balance + "MB,不足于本次额度增加！");
							return ;
						}
						
						//@RequestMapping("campaign/{campaignId}/producttype/{productType}/increase/${poolsize}/add")
						var url = ctx + "/traffic_red/campaign/" + cid + "/producttype/MBAY_PACKAGE/increase/" + 
								addTraffic + "/add.mbay";
						$.getJSON(url, function(resultJson) {
							$.messager.closeDialog(dialog);
							if(resultJson.success){ 
						    	$("#mpool").html(parseInt($("#mpool").html()) + parseInt(addTraffic));		
						    	$("#mrpool").html(parseInt($("#mrpool").html()) + parseInt(addTraffic));		
							}else{
								$.messager.alert({ content: resultJson.message });
							}
						});
					});	
				}
			}
		}
	});
}

//绘制流量产品html
function drawTrafficProducts(trafficProeuctJson){
	 var trafficProductsHtml=$('#trafficProductsJsrender').render(trafficProeuctJson);
	 $("#trafficProductsTable").html(trafficProductsHtml);
	 bindProductEditEvent();
}

//绘制美贝产品html
function drawMbayProducts(mbayProeuctsJson){
	 var mbayProductsHtml=$('#mbayProductsJsrender').render(mbayProeuctsJson);
	 $("#mbayProductsTable").html(mbayProductsHtml);
	 bindProductEditEvent();
}

//绑定产品编辑事件
function bindProductEditEvent(){
	$(".redact").click(function(){
		$(this).parent("td").children(".product_value").hide();
		$(this).parent("td").children(".product_update").show();
	});
	$(".cancel").click(function(){
		$(this).parent("td").children(".product_update").hide();
		$(this).parent("td").children(".product_value").show();
	});
}
 function updateProperty(pType){
	$("."+pType+"old").hide();
	$("."+pType+"new").show();
}
function cancelUpdate(pType){
	$("."+pType+"old").show();
	$("."+pType+"new").hide();
}
function saveProductLimt(pType,flag){
	var limit=$("#"+pType+"_value").val();
	if(limit==""){
		limit="-1";
	}
	$.post(ctx+"/traffic_red/campaign/"+cid+"/producttype/"+flag+"/dailylimit/"+limit+"/update.mbay",function(data){
		$.messager.remind(data.message);
		if(data.success){
			$.messager.remind("修改成功！");
			window.location.reload();
		}
	});
}

function saveProductThreshold(pType,flag){
	var threshold=$("#"+pType+"_value").val();
	if(threshold==""){
		threshold="-1";
	}
	$.post(ctx+"/traffic_red/campaign/"+cid+"/producttype/"+flag+"/threshold/"+threshold+"/update.mbay",function(data){
		$.messager.remind(data.message);
		if(data.success){
			$.messager.remind("修改成功！");
			window.location.reload();
		}
	});
}

function deleteTimeZone(obj){
	var table=$(obj).parents(".timeZoneTable");
	$(obj).closest("tr").remove();
	 if($(table).find("tr").length==1){			  
		  $(table).find(".delete0").hide();
	  }
	 $(table).find(".js_tadd").last().show();
}

function addTimeZone(obj){
	var table=$(obj).parents(".timeZoneTable");
	$(table).append($("#timeZoneTemplate").html());
	$(table).find(".js_tadd").hide();
	$(table).find(".js_tdelete").show();
	$(table).find(".js_tdelete:last").hide();
	$(table).find(".js_tadd").last().show();
}

function cancel(id,src) {
	$.messager.confirm({
		content: "确认取消活动？取消后不可恢复！",
		button: {
			ok: {
				callback: function() {
					var url = ctx + "/traffic_red/" + id + "/cancel.mbay";
					$.getJSON(url, {}, function(data) {
						if (data.success) {
							window.location.reload();
						} else {
							$.messager.alert({ content: "活动取消失败，请重试！" });
						}
					});
				}
			}
		}
	});
}

function deleteProduct(product,mbayid,obj){
	var url = ctx + "/traffic_red/campaign/" + cid + "/producttype/" + product + 
			"/productNumber/" + mbayid + "/delete.mbay";
	$.getJSON(url, function(result){
		if(result.success){	
		     $(obj).closest('tr').remove();
		}else{
			$.messager.alert({ content: result.message });
		}		
    });
}

function updateProductRatio(product,mbayid,obj){
	var ratio = $(obj).parent("td").children("input").val();
	if (!/^(?:[1-9]\d*)*$/.test(ratio)) {
		$.messager.alert({ content: "权重必须为正整数(>=1)！" });
		return;
	}
	var url = ctx+"/traffic_red/campaign/" + cid + "/producttype/" + product + "/productNumber/" + 
			mbayid + "/ratio/" + ratio + "/patch.mbay";
	$.getJSON(url, function(result){
		if(result.success){										
			$(obj).parent("td").children(".ratio_val").html(ratio);
			$(obj).parent("td").children(".product_update").hide();
			$(obj).parent("td").children(".product_value").show();
		}else{
			$.messager.alert({ content: result.message });
		}							
	});
}

function changeCampaignType(ele) {
	if ($(ele).val() == "NO_LIMITED") {
		$(ele).prev().hide();
	} else {
		$(ele).prev().show();
	}
}

function triggerTab(){
	$(".table-ul a").each(function(index) {
		$(this).click(function() {
			$(".table-ul a").removeClass();
			$(this).addClass("tab-ul-font");
			$(".weight").hide();
			$(".weight").eq(index).show();
		});
	});					
}

function bindTemplateValid() {
	$("#shareInfoForm").Validform({
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
		}
	});
}

function validProductForm(form, callback) {
	$(form).Validform({
		showAllError: true,
		tiptype: 3,
		datatype: {
			"products": function(gets, ele) {
				var pass = true;
				$(form).find(".chk_1:checked").each(function() {
					if (!/^(?:[1-9]\d*)*$/.test($(this).siblings(":text").val())) {
						pass = false;
						return false;
					}
				});
				if (!pass) {
					return "权重必须为正整数(>=1)！";
				}
			}
		},
		beforeSubmit: function() {
			callback();
			return false;
		}
	});
}

function chooseFile(name) {
	$("[name='" + name + "']").click();
}

function triggerFileChange() {
	$(".uplod_file").on("change", function() {
    	var name = $(this).attr("name");
    	var input = $("[name='" + name + "']")[0];
    	if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				name = name.replace(".", "-");
				$("#" + name).attr("src", e.target.result);
			};
			reader.readAsDataURL(input.files[0]);
		}
    });	
}

function enabledAdvanced(){
	$.post(ctx+"/traffic_red/campaign/"+cid+"/advanced/enabled.mbay",function(result){
		 if(result.success){
			 refresAdvanceEnabledValue(true);
			 $.messager.remind("开发者模式已启用！");
		 }
	});
}
function disabledAdvanced(){
	$.post(ctx+"/traffic_red/campaign/"+cid+"/advanced/disabled.mbay",function(result){
		 if(result.success){
			 refresAdvanceEnabledValue(false);
			 $.messager.remind("开发者模式已禁用！");
		 }
	});
}
function refresAdvanceEnabledValue(enabled){
	$("#advancedEnabledDic").html(enabled?"已启用":"已禁用");
	$(".js_advance_switch").hide();
	if(enabled){
		$("#advancedDisabled").show();
	}else{			
		$("#advancedEnabled").show();
	}
}