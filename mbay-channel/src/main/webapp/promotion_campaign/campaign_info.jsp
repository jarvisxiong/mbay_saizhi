<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>促销神器-活动详情</title>
<t:assets/>
<link href="${actx}/wro/${version}/campaign_infoes.css" rel="stylesheet"
	type="text/css" />
<link href="${actx}/traffic_red/css/detail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/campaign_infoes.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript"
	src="${actx}/js/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${actx}/js/jquery/jsrender.min.js"></script>
<script type="text/javascript">
$(function() {
	//如果不是未开始和活动中，则不让点击按钮
	if("NOT_STARTED" != "${status}" && "IN_ACTIVE" != "${status}"){
 		$(".js_oper_ctl").removeAttr("onclick");
 		$(".js_oper_ctl").css("background","#CCC");
 	}
	
	$(".hdmx").bind("click",function() {
		window.location.href = '<c:url value="/promotionCampaign/rdeemcode_list.mbay?eventNumber=${event.eventnumber}"/>';
	});
	$(".qx").bind("click",function() {
		$.messager.confirm({
			title: "重要提示",
			content: "取消活动将会对您的营销造成影响，确认取消活动？取消后将无法恢复！",
			button: {
				ok: {
					callback: function() {
						$.getJSON("${pageContext.request.contextPath}/promotionCampaign/cancel_campaign.mbay?eventnumber=${event.eventnumber}",function(json) {
							if (json.success) {
								window.location.reload(true);
							} else {
								$.messager.dialog({ content: json.message });
							}
						});
					}
				}
			}
		});
	});

	$("#verificate").bind("change",function(obj) {
	    var checked = $(this).is(":checked");
	    var url = "${pageContext.request.contextPath}/promotionCampaign/update_campaign_verificate.mbay?campaignNumber=${eventnumber}&verificate=" + checked;	    
	    $.getJSON(url, function(suc) {
	    	if (!suc) {
	    		$.messager.alert({ content: "操作失败!" });
	    	} else {
	    		window.location.reload();
	    	}
	    });
	}); 
	
	$("#continuable").bind("change",function(obj) {
	    var checked = $(this).is(":checked");
	    var url = "${pageContext.request.contextPath}/promotionCampaign/update_campaign_continuable.mbay?campaignNumber=${eventnumber}&continuable=" + checked;	    
	    $.getJSON(url, function(suc) {
	    	if (!suc) {
	    		$.messager.alert({ content: "操作失败!" });
	    	} else {
	    		window.location.reload();
	    	}
	    });
	}); 
	
	$("#share").bind("change",function(obj) {
	    var checked = $(this).is(":checked");
	    var url = "${pageContext.request.contextPath}/promotionCampaign/update_campaign_share.mbay?campaignNumber=${eventnumber}&share=" + checked;	    
	    $.getJSON(url, function(suc) {
	    	if (!suc) {
	    		$.messager.alert({ content: "操作失败!" });
	    	} else {
	    		window.location.reload();
	    	}
	    });
	}); 
	
	$("#redeemButton").zclip({
		path : '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
		copy : function() {
			return $("#redeemurl").val();
		}
	});
	
	$("#webButton").zclip({
		path : '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
		copy : function() {
			return $("#weburl").val();
		}
	});
	
	//异步加载流量包产品
	$.getJSON(ctx+"/promotionCampaign/${event.eventnumber}/trafficProducts.mbay",function(trafficProeucts){
		drawTrafficProducts(trafficProeucts);
	});
	
	//异步加载美贝产品
	$.getJSON(ctx+"/promotionCampaign/${event.eventnumber}/mbayProducts.mbay",function(trafficProeucts){
		drawMbayProducts(trafficProeucts);
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
});

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

//修改产品单日上限
function updateProperty(pType){
	$("."+pType+"old").hide();
	$("."+pType+"new").show();
}

//保存修改产品单日上限
function saveProductLimt(pType,flag){
	var limit=$("#"+pType+"_value").val();
	if(limit==""){
		limit="-1";
	}
	$.post(ctx+"/promotionCampaign/${event.eventnumber}/producttype/"+flag+"/dailylimit/"+limit+"/update.mbay",function(data){
		$.messager.remind(data.message);
		if(data.success){
			$.messager.remind("修改成功！");
			window.location.reload();
		}
	});
}

//取消修改产品单日上限
function cancelUpdate(pType){
	$("."+pType+"old").show();
	$("."+pType+"new").hide();
}

//增加流量池
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
						var url = ctx+"/promotionCampaign/${event.eventnumber}/producttype/TRAFFIC_PACKAGE/increase/" + 
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

//保存告警阀值
function saveProductThreshold(pType,flag){
	var threshold=$("#"+pType+"_value").val();
	if(threshold==""){
		threshold="-1";
	}
	$.post(ctx+"/promotionCampaign/${event.eventnumber}/producttype/"+flag+"/threshold/"+threshold+"/update.mbay",function(data){
		$.messager.remind(data.message);
		if(data.success){
			$.messager.remind("修改成功！");
			window.location.reload();
		}
	});
}

//添加流量产品
function addTrafficProduct(){
	$.getJSON(ctx+"/promotionCampaign/${event.eventnumber}/traffic_package/unselected.mbay", function(result){
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
							url: ctx+"/promotionCampaign/${event.eventnumber}/producttype/TRAFFIC_PACKAGE/add.mbay",
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
				/* if (!pass) {
					return "权重必须为正整数(>=1)！";
				} */
			}
		},
		beforeSubmit: function() {
			callback();
			return false;
		}
	});
}

//删除产品
function deleteProduct(product,mbayid,obj){
	var url = ctx + "/promotionCampaign/${event.eventnumber}/producttype/" + product + 
			"/productNumber/" + mbayid + "/delete.mbay";
	$.getJSON(url, function(result){
		if(result.success){	
		     $(obj).closest('tr').remove();
		}else{
			$.messager.alert({ content: result.message });
		}		
    });
}

//增加MB池
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
						
						var url = ctx + "/promotionCampaign/${event.eventnumber}/producttype/MBAY_PACKAGE/increase/" + 
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

//添加MB产品
function addMBProduct(){
	$.getJSON(ctx+"/promotionCampaign/${event.eventnumber}/mbay_package/unselected.mbay",function(result){
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
	    					url: ctx+"/promotionCampaign/${event.eventnumber}/producttype/MBAY_PACKAGE/add.mbay",
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

//修改日期
function changeEventDate() {
	$("#eventdate").hide();
	$("#changedate").show();
	var edit = $("#edit").html();
	if (edit == '修改') {
		$("#edit").html("确认");
		$("#edit").append("<a href='javascript:show()'>取消<a>");
	} else {
		if (changedate()) {
			$("#edit").html("修改");
		}
	}
}

//取消修改活动截至日期
function show() {
	$("#changedate").hide();
	$("#eventdate").show();
	$("#partedit").find("a").html("修改");
}

//确认修改活动日期
function changedate() {
	var start = $('[name="starttime"]').val();
	var end = $('[name="endtime"]').val();
	if (start == '') {
		$.messager.dialog({ content : "日期不能为空！"});
	} else if (end == '') {
		$.messager.dialog({ content : "日期不能为空！"});
	} else {
		var flag = false;
		$.getJSON("${pageContext.request.contextPath}/promotionCampaign/edit_campaign_date.mbay?eventnumber=${eventnumber}&starttime=" + start + "&endingtime=" + end, function(json) {
			if (json) {
				window.location.reload();
				flag = true;
			} else {
				$.messager.dialog({ content : "更改失败！"});
				flag = false;
			}
		});
		return flag;
	}
}

//修改概率
function changeTrafficRate() {
	$("#rate_span").hide();
	$("#rate_edit_span").show();
	var edit = $("#rate_edit_a").html();
	if (edit == '修改') {
		$("#rate_edit_a").html("确认");
		$("#rate_edit_a").append("<a href='javascript:cancelTrafficRate()'>取消<a>");
	} else {
		if (changeRate()) {
			$("#rate_edit_a").html("修改");
		}
	}
}

//取消修改概率
function cancelTrafficRate(){
	$("#rate_edit_span").hide();
	$("#rate_span").show();
	$("#rate_a_span").find("a").html("修改");
}

//确认修改概率
function changeRate() {
	var rate = $("#rate_input").val();
	if (rate == '') {
		$.messager.dialog({ content : "概率值不能为空"});
	} else {
		var flag = false;
		$.getJSON("${pageContext.request.contextPath}/promotionCampaign/edit_campaign_rate.mbay?eventnumber=${eventnumber}&rate=" + rate, function(json) {
			if (json) {
				window.location.reload();
				flag = true;
			} else {
				$.messager.dialog({ content : "更改失败！"});
				flag = false;
			}
		});
		return flag;
	}
}

//显示二维码图片
function generateQrCode(domId){
	$.layer({
	    type: 1,
	    title: false,
	    area: ['auto', 'auto'],
	    border: [0], //去掉默认边框
	    shade: [0.5, '#000'], //遮罩
	    shadeClose: true, //用来控制点击遮罩区域是否关闭层
	    closeBtn: [0, true], //去掉默认关闭按钮
	    page: {
	        dom: '#'+domId
	    }
	});
}

//批量导入
function importAll(campaignNumber){
	window.location.href= ctx + "/promotionCampaign/toImportAll.mbay?eventnumber=" + campaignNumber;
}
</script>
<c:if test="${event.share }">
<script>
$(function(){
	$("#shareForm").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"file": function(gets, ele) {
				if (gets == '') {
					return false;
				} else {
					var suffix = gets.substring(gets.lastIndexOf(".") + 1);
					return /^(jpg|png|jpeg|gif)$/i.test(suffix);
				}
			}
		}
	});
	
	$("input[type=file][name=shareImage]").change(function(){
    	var input = $(this)[0];
    	if (input.files && input.files[0]) {
    		$(this).attr("ignore","");
			var reader = new FileReader();
			reader.onload = function(e) {
				$("#shareImg").attr("src", e.target.result);
			};
			reader.readAsDataURL(input.files[0]);
		}
	});
});
</script>
</c:if>
<style type="text/css">
.hd_1,.hd_2{border:none;line-height:40px;}
.hd_list1{width:411px;float:left;border-bottom:1px solid #CCC; height:40px;}
.hd_1,.hd_2{padding:0;}
.yxq,.syzt{margin-left:15px;}
</style>
</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width'>
				<!--左边-->
				<div class='left_con fl'>
					<div class='ckxq'>促销神器 【${event.eventname}】 - 活动详情</div>
					<%@ include file="/common/leftcon.jsp"%>
					<!--右部内容-->
					<div class='fr xq_con'>
						<div class='tab_tit clearfix'>
							<ul>
								<li class="nav_list_now"><a href="javascript:void(0)">基本信息</a></li>
								<li><a href="<c:url value='/promotionCampaign/to_campaign_info_template.mbay?eventnumber=${event.eventnumber}'/>">活动模板</a></li>
								<li><a href="<c:url value='/promotionCampaign/to_campaign_info_advanced.mbay?campaignNumber=${event.eventnumber}'/>">开发者中心</a></li>
							</ul>
						</div>
						<p style="border-left:1px solid #CCC;border-right:1px solid #CCC;height:15px;margin-top:-20px;"></p>
						<div class='xq'>
							<div class='hd_2' style="border-bottom:1px solid #CCC;">
								<span class='mc' style='margin-left: 15px;'>活动名称:</span><span class='gz' style='display: inline-block; width: 220px;'>${event.eventname}</span>														
								<p class="hd_list1"><span class='bh'>活动编号:</span><span class='shuzi'>${event.eventnumber}</span></p>	
							</div>
							
							<div class='hd_2'>
								<p class="hd_list1"><span class='rq' style='margin-left: 15px;'>活动日期:</span> <span class='sj_0' id="eventdate"> 
									<joda:format value="${event.starttime}" pattern="yyyy-MM-dd" /> - 
									<joda:format value="${event.endingtime}" pattern="yyyy-MM-dd" />
									</span> <span id="changedate" style="display: none"> 
									<fmt:formatDate pattern="yyyy-MM-dd" value="${event.starttime.toDate()}" />~
									<input id="rq_1" name="starttime" readonly="readonly"
									value="<fmt:formatDate pattern="yyyy-MM-dd" value="${event.starttime.toDate()}"/>"
									type="hidden" /> <input id="rq_2" name="endtime"
									style="width: 68px;" class="Wdate" type="text"
									onclick="WdatePicker({el:'rq_2',minDate:'#F{$dp.$D(\'rq_1\')}'})" />
								</span>
								<c:if test="${event.state=='NOT_STARTED' or event.state=='IN_ACTIVE'}">
									<span id="partedit"> <a id="edit"
										href="javascript:changeEventDate()">修改</a>
									</span>
								</c:if>
								</p>
								<p class="hd_list1">
								<span class='yxq'>创建日期:</span> <span class='ygy'
									style='margin-left: 0px;'> <joda:format
										value="${event.createtime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</span></p>
							</div>
							<div class="hd_2">
								<p class="hd_list1">
									<span class='rq'>已发放数量:</span> <span class='sj_0'
										style='display: inline-block; width: 155px;'>${event.gotnum}&nbsp;份</span></p>
								<p class="hd_list1">
									<span class='rq'>已送出数量:</span> <span class='sj_0'
										style='display: inline-block; width: 155px;'>${event.sendednum}&nbsp;份</span></p>
							</div>
							
							<div class="hd_2">
								<p class="hd_list1">
								<span class='syzt'>状态:</span> <span class='ty'>${event.state.value}</span></p>
								<p class="hd_list1">
									<span class='syzt'>概率:</span> 
									<span id="rate_span" class='ty'>${event.trafficRate}%</span>
									<span id="rate_edit_span" class='ty' style="display:none;"><input type="text" id="rate_input" style="width:68px;border: 1px solid rgb(193, 193, 193);height:25px;"/>%</span>
									<span id="rate_a_span"> <a id="rate_edit_a"
										href="javascript:changeTrafficRate()">修改</a>
									</span>
								</p>
							</div>
							
							<div class="hd_2">
								<div class="hd_list1"> 
						        <span class='bh'>超出发放:</span>  
		                       <!--开关设置-->
						        <div class="slider-box off">
						        	<div class="slider"></div>
						            <span class="m">是</span>
						            <span class="w">否</span>
						            <input type="checkbox" id="continuable" name="continuable" <c:if test="${event.continuable}">checked="checked"</c:if> />
						        </div>
						        <!--提示-->
						        <i class='tip_i'>?<div class="tooltip" style="display: none;">当没有导入兑换码时，是否可以系统自动发放兑换码。<em></em></div></i>		
								</div>
								<div class="hd_list1">
									<span class='bh'>开启核销码:</span>  
			                       <!--开关设置-->
							        <div class="slider-box off">
							        	<div class="slider"></div>
							            <span class="m">是</span>
							            <span class="w">否</span>
							            <input type="checkbox" id="verificate" name="verificate" <c:if test="${event.verificate}">checked="checked"</c:if> />
							        </div>
							         <!--提示-->
							        <i class='tip_i'>?<div class="tooltip" style="display: none;">是否开启核销码,用于线下操作。<em></em></div></i>		
							       </div> 
							</div>
							
							<div class="hd_2">
								<div class="hd_list1">
									<span class='bh'>开启分享:</span>  
			                       <!--开关设置-->
							        <div class="slider-box off">
							        	<div class="slider"></div>
							            <span class="m">是</span>
							            <span class="w">否</span>
							            <input type="checkbox" id="share" name="share" <c:if test="${event.share}">checked="checked"</c:if> />
							        </div>
							         <!--提示-->
							        <i class='tip_i'>?<div class="tooltip" style="display: none;">开启分享功能，用户分享后可获得额外的再玩一次机会！<em></em></div></i>		
							       </div> 
							       <div class="hd_list1">
							       		<c:if test="${event.share}">
							       			<span class='syzt'>选择模式:</span> <span>${event.model.value}</span>
							       		</c:if>
							       </div> 
							</div>
							
							<div class="hd_2">
								<span class='rq' style='margin-left: 15px'>领取连接:</span> <span
									class='sj_0'> <input id='redeemurl'
									style='border: 1px solid #C2C2C2; width: 548px; background: #f3f3f3; height: 23px'
									readonly="readonly" type='text' value='${weburl}' /> <span>
										<input id='redeemButton' type='button'
										class='right_copy js_oper_ctl btn_confirm' value='复制'
										style="padding: 0px 15px;"> <input
										id="redeemQrCodeButton" type='button'
										class='right_copy js_oper_ctl btn_confirm' value='生成二维码'
										onclick='generateQrCode("getQrCodeImg")'
										style="padding: 0px 18px;"> <img id='getQrCodeImg'
										border="0" alt="领取页面二维码" title='领取页面二维码'
										src="${ctx}/qrcode/generateQrCode.mbay?url=${weburl}"
										style='display: none;' />
								</span>
								</span>
							</div>
							
							<div class="hd_2">
								<span class='rq' style='margin-left: 15px'>兑换连接:</span> <span
									class='sj_0'> <input id='weburl'
									style='border: 1px solid #C2C2C2; width: 548px; background: #f3f3f3; height: 23px'
									readonly="readonly" type='text' value='${redeemurl}' /> <span>
										<input id='webButton' type='button'
										class='right_copy js_oper_ctl btn_confirm' value='复制'
										style="padding: 0px 15px;"> <input
										id="redeemQrCodeButton" type='button'
										class='right_copy js_oper_ctl btn_confirm' value='生成二维码'
										onclick='generateQrCode("redeemQrCodeImg")'
										style="padding: 0px 18px;"> <img
										id='redeemQrCodeImg' border="0" alt="兑换页面二维码" title='兑换页面二维码'
										src="${ctx}/qrcode/generateQrCode.mbay?url=${redeemurl}"
										style='display: none;' />
								</span>
								</span>
							</div>
							
							<!-- 暂时隐藏生成兑换码操作 -->
							<%-- <div class="hd_2">
								<span class='rq' style='margin-left: 15px'>操作:</span> <span
									class='sj_0'> <c:if
										test="${event.state != 'OVER' and  event.state != 'CANCLED'}">
										<button type='button' id="generate_code" class='button'
											value=''>生成兑换码</button>
									</c:if>
								</span>
							</div>  --%>
							<div class='hd_3'>
								<input type="button" class="btn_confirm js_oper_ctl" value="批量导入" onClick="importAll('${event.eventnumber}')"/>
								<input type="button" class="hdmx btn_confirm" value="营销明细">
								<c:if
									test="${event.state != 'OVER' and  event.state != 'CANCLED'}">
									<input type="button" class='qx btn_confirm' value="取消活动">
								</c:if>
							</div>
						</div>
						
						<!-- 设置产品信息 -->
						<div class="table">
							<div class='tab_tit0 clearfix0'>
								<ul class="clearfix_list">								
									<li class="nav_list_now0"> <a href="javascript:void(0)" class="clearfix_list2">红包产品</a></li>
									<li><a href="javascript:void(0)">MB产品</a></li>
								</ul>
							</div>						
	       					<article class="dabian"  id="trafficProductsTable" ></article>	
		    		  		<article class="dabian" style="display:none" id="mbayProductsTable"></article>
			    		</div>
			    		
			    		<!-- 分享信息 -->
						<c:if test="${event.share }">
							<div class='table'>
								<div class='tab_tit0 clearfix0'>
									<ul class="clearfix_list">								
										<li class="nav_list_now0"> <a href="javascript:void(0)" class="clearfix_list2">分享信息</a></li>
									</ul>
								</div>
								<form id="shareForm" method="post" enctype="multipart/form-data"
									action="<c:url value='/promotionCampaign/shareChange.mbay'/>">
									<input type="hidden" name="cNumber" value="${event.eventnumber }"/>
									<c:if test="${event.model == 'PLAY'}">
									<p class="red-packet" style="margin-top:15px;">
										<span>赠送&nbsp;MB：</span>
										<input type="text" name="sendMB" datatype="n1-2" value="${event.sendMB }" style="width:35em;" maxlength="2"/>
										<span class="Validform_checktip" style='width: 200px'>请填写赠送MB(正整数0-99)！</span>
									</p>	
									</c:if>
									<p class="red-packet" style="margin-top:15px;">
										<span>分享链接：</span>
										<input type="text" name="shareLink" datatype="url" value="${shareInfo.shareLink }" style="width:35em;" maxlength="255"/>
										<span class="Validform_checktip" style='width: 200px'>请填写分享链接(最多255个字符)！</span>
									</p>	
									<p class="red-packet" style="margin-top:15px;">
										<span>分享标题：</span>
										<input type="text" name="shareTitle" datatype="*" value="${shareInfo.shareTitle }" style="width:35em;" maxlength="50"/>
										<span class="Validform_checktip" style='width: 200px'>请填写分享标题(最多50个字符)！</span>
									</p>
									<p class="red-packet" style="margin-top:15px;">
										<span>分享内容：</span>
										<input type="text" name="content" datatype="*" value="${shareInfo.content }" style="width:35em;" maxlength="255"/>
										<span class="Validform_checktip" style='width: 200px'>请填写分享内容(最多255个字符)！</span>
									</p>
									<p class="red-packet" style="margin-top:15px;">
										<span>有效次数：</span>
										<input type="text" name="shareTimes" datatype="n1-3" value="${shareInfo.shareTimes }"  style="width:35em;" maxlength="3"/>
										<span class="Validform_checktip" style='width: 200px'>请填写分享有效次数(正整数(0-999))！</span>
									</p>
									<p class="red-packet" style="margin-top:15px;">
										<span>分享图片：</span>
										<img id="shareImg" src="${shareInfo.shareImage }" style="width:80px;height:80px;"/>
										<input type="file" name="shareImage" datatype="file" ignore="ignore"
											errormsg="图片格式不正确(jpg,png,gif,jpeg)！" style="width:15em;"/>
									</p>
									<p class="red-packet" style="margin-top:80px;">
										<input type="submit" value="保存"/>
									</p>
								</form>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

<script type="text/x-jsrender" id="trafficProductsJsrender">                         
<p class="red-packet">
	<span>预设总量：</span>
	<span class="marg" id="tpool">{{:productConfig.poolSize}}</span>&nbsp;美贝
	<span>红包剩余：</span>
	<span class="marg" id="trpool">{{:productConfig.poolRemain}}&nbsp;美贝</span>
    <c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="win-probability-btn trafficPoolbtn btn_service" onclick="increaseRedTrafficPool()">增加</a>
	</c:if>       					
	<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="add_trafficProduct add-bt"  onclick="addTrafficProduct();">添加待选红包产品</a>
	</c:if>
</p>
<p class="red-packet">
	<span>单日上限(美贝):</span>
	<span class="marg">
   		<span class='redold' >{{:productConfig.dailyLimit==-1?'不限':productConfig.dailyLimit}}</span>
    	<span class='rednew hide'><input id="red_value" class="poolsize" value="{{:productConfig.dailyLimit==-1?'':productConfig.dailyLimit}}"/></span>
    </span>
    <a class='add-bt redold' onclick="updateProperty('red')">修改</a>
    <a class="add-bt hide rednew" onclick="saveProductLimt('red','TRAFFIC_PACKAGE');">保存</a>
    <a class="add-bt hide rednew" onclick="cancelUpdate('red')">取消</a>&nbsp;&nbsp;
	<span>当日剩余(美贝):</span>
	<span class="marg trpool">{{:productConfig.dailyRemain==-1?'不限':productConfig.dailyRemain}}</span>
	<span>告警阀值(美贝):</span>
	<span class="marg thold">{{:productConfig.threshold==-1?'不限':productConfig.threshold}}</span>
    <span class='thnew hide'><input id="th_value" class="poolsize" value="{{:productConfig.threshold==-1?'':productConfig.threshold}}"/></span>
    <a class='add-bt thold' onclick="updateProperty('th')">修改</a>
    <a class="add-bt hide thnew" onclick="saveProductThreshold('th','TRAFFIC_PACKAGE');">保存</a>
    <a class="add-bt hide thnew" onclick="cancelUpdate('th')">取消</a>&nbsp;&nbsp;
</p>
<table class="tables0c" style="margin-top:3px;border: 10px">
	<tr>
		<td>产品</td>
		<td>运营商</td>
		<!--<td width="200px">权重</td>-->
		<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
			<td>操作</td>
		</c:if>
	</tr> 
	{{for trafficProducts}}																 
		<tr class="bian">
			<td>{{:trafficPackage.traffic }}MB</td>
			<td>{{:trafficPackage.operatorType}}</td>
			<%--
			<td width="350px" style="text-align: ${c.status != 'OVER' && c.status != 'CANCLED' ? 'right' : 'center'};padding-right: 5px;padding-top:0;padding-bottom:0;">
				<span class="product_value ratio_val" style="${c.status != 'OVER' && c.status != 'CANCLED' ? 'float:left;' : ''} margin-left:20px;margin-top:0px;">{{:ratio}}</span>
				<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
					<input class="td-num product_update" style="float:left; margin-left:20px;margin-top:8px;" value="{{:ratio}}"  maxlength="3"/>
					<a class="redact product_value btn_service">编辑</a>
					<a class="product_update handle btn_service" href="javascript:void(0)" onclick="updateProductRatio('TRAFFIC_PACKAGE',{{:trafficPackage.packageId}},this)" >保存</a>
					<a class="cancel product_update handle btn_service">取消</a>
				</c:if>		
			</td>
			--%>
			<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
				<td class="right1">                          
					<a class="romve btn_service" href="javascript:void(0)" onclick="deleteProduct('TRAFFIC_PACKAGE',{{:trafficPackage.packageId}},this)" style="padding: 5px 20px;">删除</a>
				</td>	
			</c:if>							 
		</tr>
	{{/for}}
</table>
</script>

<script type="text/x-jsrender" id="mbayProductsJsrender">
<p class="red-packet">
	<span>预设总量：</span>
	<span class="marg" id="mpool">{{:productConfig.poolSize}}</span>&nbsp;MB
	<span>MB剩余：</span>
	<span class="marg" id="mrpool">{{:productConfig.poolRemain}}</span>&nbsp;MB
    <c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="win-probability-btn trafficPoolbtn btn_service" onclick="increaseMBPool()">增加</a>
	</c:if>       					
	<c:if test="${c.status != 'CANCLED' && c.status != 'OVER' }">
		<a class="add_trafficProduct add-bt"  onclick="addMBProduct();">添加待选MB产品</a>
	</c:if>
</p>
<p class="red-packet">
	<span>单日上限(MB):</span>
	<span class="marg">
    	<span class='mbold' >{{:productConfig.dailyLimit==-1?'不限':productConfig.dailyLimit}}</span>
        <span class='mbnew hide'><input id="mb_value" class="poolsize" value="{{:productConfig.dailyLimit==-1?'':productConfig.dailyLimit}}"/></span>
    </span>
   	<a class='add-bt mbold' onclick="updateProperty('mb')">修改</a>
    <a class="add-bt hide mbnew" onclick="saveProductLimt('mb','MBAY_PACKAGE');">保存</a>
    <a class="add-bt hide mbnew" onclick="cancelUpdate('mb')">取消</a>&nbsp;&nbsp;
	<span>当日剩余(MB):</span>
	<span class="marg trpool">{{:productConfig.dailyRemain==-1?'不限':productConfig.dailyRemain}}</span>
	<span>告警阀值(MB):</span>
	<span class="marg mbthold">{{:productConfig.threshold==-1?'不限':productConfig.threshold}}</span>
    <span class='mbthnew hide'><input id="mbth_value" class="poolsize" value="{{:productConfig.threshold==-1?'':productConfig.threshold}}"/></span>
    <a class='add-bt mbthold' onclick="updateProperty('mbth')">修改</a>
    <a class="add-bt hide mbthnew" onclick="saveProductThreshold('mbth','MBAY_PACKAGE');">保存</a>
    <a class="add-bt hide mbthnew" onclick="cancelUpdate('mbth')">取消</a>&nbsp;&nbsp;
</p>
<table class="tables0">
	<tr>
		<td>产品</td>
		<!--<td>权重</td>-->
		<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
			<td>操作</td>
		</c:if>
	</tr>
    {{for mbayProducts}}						
		<tr class="bian">
			<td>{{:mbay.mbay}}MB</td>
			<%--
			<td width="350px" style="text-align: ${c.status != 'OVER' && c.status != 'CANCLED' ? 'right' : 'center'};padding-right: 5px;padding-top:0;padding-bottom:0;">
				<span class="product_value ratio_val" style="${c.status != 'OVER' && c.status != 'CANCLED' ? 'float:left;' : ''} margin-left:20px;margin-top:8px;">{{:ratio}}</span>
				<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
					<a class="redact product_value btn_service">编辑</a>
					<input class="td-num product_update" style="float:left; margin-left:20px;margin-top:8px;" value="{{:ratio}}" maxlength="3"/>
					<a class="product_update handle btn_service" href="javascript:void(0)" onclick="updateProductRatio('MBAY_PACKAGE',{{:mbay.id}},this)">保存</a>
					<a class="cancel product_update handle btn_service">取消</a>
				</c:if>		
			</td>
			--%>
			<c:if test="${c.status != 'OVER' && c.status != 'CANCLED'}">
				<td class="right1">
					<a href="javascript:void(0)" onclick="deleteProduct('MBAY_PACKAGE','{{:mbay.id}}',this)" class="btn_service" style="padding: 5px 20px;">删除</a>
				</td>	
			</c:if>					 
		</tr>
    {{/for}}
</table>
</script>

<script type="text/x-jsrender" id="trafficProductTemplate">
<!-- 流量产品 start -->
{{if !selected}}
	<div>预设总量(美贝):<input name="poolSize" class="poolsize" /> 单日上限(美贝)：<input name="dailyLimit" class="poolsize"/> 告警阀值(美贝)：<input name="threshold" class="poolsize" /></div>
{{/if}}
<div class="table-list-content" id="redPdiv">
	<form id="redPForm">
		<!--活动内部选项卡 start-->
		<ul class="tab-con-tab-ul table-ul">
			<li><a href="javascript:void(0)" class="tab-ul-font">移动</a></li>
			<li><a href="javascript:void(1)">联通</a></li>
			<li><a href="javascript:void(2)">电信</a></li>
		</ul>
		<!--END 移动活动内部选项卡 -->		
		<div class="table-10list-content">	
			<div class="weight">
            	{{for MOBILE}}	
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mobile-{{:packageId}}" value="{{:packageId}}" class="chk_1 mobile-1" />
						<label for="mobile-{{:packageId}}"></label>
						<span>{{:traffic}}MB</span>
						<input class="onlynum" name="ratio_{{:packageId}}" type="text" placeholder="权重" packageId="1" maxlength="3" style="display:none;" />
					</p>				
                {{/for}}					
			</div>
			<div class="weight weight2">
            	{{for UNICOM}}
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mobile-{{:packageId}}" value="{{:packageId}}" class="chk_1" />
						<label for="mobile-{{:packageId}}"></label>
						<span>{{:traffic}}MB</span>
						<input class="onlynum" name="ratio_{{:packageId}}" type="text" placeholder="权重" packageId="1" maxlength="3" style="display:none;" />
					</p>					
                {{/for}}
			</div>
			<div class="weight weight2">
            	{{for TELECOM}}
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mobile-{{:packageId}}" value="{{:packageId}}" class="chk_1" />
						<label for="mobile-{{:packageId}}"></label>
						<span>{{:traffic}}MB</span>
						<input class="onlynum" name="ratio_{{:packageId}}" type="text" placeholder="权重" packageId="1" maxlength="3" style="display:none;" />
					</p>					
                {{/for}}
			</div>
		</div>
		<input type="hidden" datatype="products" value="null" /> 
		<span class="Validform_checktip" style="margin:0;"></span>		
   	</form>
</div>
<!-- 流量产品 END -->
</script>

<script type="text/x-jsrender" id="mbayProductTemplate">
<!-- 美贝产品 start -->
<form id="mbayPForm" >
	{{if !selected}}
    	<div>预设总量(MB):<input name="poolSize" class="poolsize" /> 单日上限(MB)：<input name="dailyLimit" class="poolsize"/> 告警阀值(MB)：<input name="threshold" class="poolsize" /></div>
    {{/if}}
	<div class="table-list-content" id="redPdiv">
		<!--END 移动活动内部选项卡 -->		
		<div class="table-10list-content">	
			<div class="weight">
            	{{for mbayProducts}}	
					<p class="weight-list">
						<input type="checkbox" name="productId" id="mbay-{{:id}}" value="{{:id}}" class="chk_1 mobile-1" />
						<label for="mbay-{{:id}}"></label>
						<span>{{:mbay}}MB</span>
						<input class="onlynum" name="ratio_{{:id}}" type="text" placeholder="权重" packageId="1" maxlength="3" />
					</p>				
                {{/for}}					
			</div>				
		</div>
		<input type="hidden" datatype="products" value="null" /> 
		<span class="Validform_checktip" style="margin:0;"></span>	
	</div>
</form>
<!-- 美贝产品 END -->
</script>	
</body>
</html>