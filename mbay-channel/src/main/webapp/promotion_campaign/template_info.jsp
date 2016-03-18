<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<link href="${actx}/wro/${version}/set_template.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/set_template.js"></script>
<link
	href="<c:url value="/css/smoothness/jquery-ui-1.10.4.custom.min.css"/> "
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<c:url value="/js/lib/jquery.validate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/js/lib/Validform_v5.3.2_min.js" />"></script>
<script type="text/javascript"
	src="${actx}/js/promotion_campaign/set_template.js"></script>
<style>
.text_area_content{
  border: 1px solid #ccc;
  -webkit-user-select: initial;
  -webkit-tap-highlight-color: #ccc;
  font-size: 10px;
  padding: 0;
  width: 90%;
  border-radius: 1px;
  height: 114px;
}
.font_text{
  width: 150px;
  margin-left: 20px;
  height: 25px;
}

.input_btn_con>a{display:block;width: 73px;height: 24px;  background-repeat: no-repeat;background-size: 100% 100%; line-height:24px;}
.btn_func{background:url(${actx}/images/campaign_common/mobile/redeem/btn1.png); float:right; margin-right:54px;color:#000;}
.btn_change{background:url(${actx}/images/campaign_common/mobile/redeem/btn2.png);float:left;margin-left:54px;color:#FFF}
</style>
<script>
$(function(){
 	$("#imgForm").Validform({
	    showAllError:true,
		tiptype:3
	}); 	
 	
 	if("${type}" == "ADVANCED"){
 		$("#h2_redeem").click();
 		$("#give_out_get").hide();
 	}
});
</script>

<c:if test="${reviewEnable}">
<script>
$(function(){
	//如果是已取消和已结束，则不让点击按钮
	if("OVER" == "${status}" || "CANCLED" == "${status}"){
 		$(".js_oper_ctl").removeAttr("onclick");
 		$(".js_oper_ctl").css("background","#CCC");
 	}
	
	//判断地址栏是否有message参数，以此来判断详情页面是否添加或更新成功
	//获取地址栏参数
	function getQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
	}
	if(getQueryString("message") == "success"){
		$.messager.remind("修改成功");
	}
	//设置领取图片
	var backphoto='${campaignTempate.backphoto}';
	if(backphoto != ''){
		/* $("#btn_success").show(); */
		$("#promotion_img").attr("src",backphoto);
		$("#back_get").attr("src",backphoto);
		/* $("#get_picture_id").val(parseInt(backphotoId)); */
	}
	//设置兑换图片
	var redeemBackphoto='${campaignTempate.redeemBackphoto}';
	if(redeemBackphoto != ''){
		/* $("#btn_success_redeem").show(); */
		$("#promotion_redeem_img").attr("src",redeemBackphoto);
		$("#back_redeem").attr("src",redeemBackphoto);
		/* $("#redeem_picture_id").val(parseInt(redeemBackphotoId)); */
	}
	//设置领取页面按钮文字
	$("input[name='gotText']").val('${campaignTempate.gotText}');
	$("#get_text_template").html('${campaignTempate.gotText}');
	//设置兑换页面按钮文字
	$("input[name='redeemText']").val('${campaignTempate.redeemText}');
	$("input[name='introductionText']").val('${campaignTempate.introductionText}');
	$("#change_template").html('${campaignTempate.redeemText}');
	$("#func_template").html('${campaignTempate.introductionText}');
});
</script>
</c:if>

<form id="imgForm" enctype="multipart/form-data" method="post"
	action="<c:url value="/promotionCampaign/set_template.mbay"/>">
	<m:token />
	<!-- <input type="hidden" id='get_picture_id' name="get_picture_id" value="0"> 
	<input type="hidden" id='redeem_picture_id' name="redeem_picture_id" value="0"> -->
	<input type="hidden" name="modelid" value="${modelid}"/> <input
		type="hidden" name="eventnumber" value="${eventnumber}"> <input
		type="hidden" name="info" value="0" />
	<input type="hidden" name="type" value="${type}"/>
	<div class='clearfix'>
		<!--右侧编辑选项-->
		<div class="right_con fr">
			<div class="bg_main_tit">促销神器模板设置</div>
			<!--更改领取页面-->
			<div id="give_out_get" class='give_out'>
				<h2 id="h2_get">设置领取页面</h2>
				<div id="div_get" class='con_box'>
					<div class='clearfix upload'>
						<div class='fl preview_img'>
							<img id="promotion_img"
								src='${actx}/images/campaign_common/tou-big.png' />
						</div>
						<div class='fr' style='margin-top: -10px;'>
							<button id='btn_success' class='fl cancil' type="button"
								style="background: #6D6D6D; display: none">使用默认</button>
							<input class='js_oper_ctl btn_confirm' type="button"
								onclick="manualChangeBk();" value="自定义上传"> <input
								type="file" id="bk" name='bk' style='display: none;' />
						</div>
					</div>
					<div class='sel_tip'>图片尺寸最佳为640px*500px</div>
					<div class='sel_tip'>
						按钮文字:<input type="text" name="gotText" value="点此获取免费流量兑换码" class="font_text"/>
					</div>
				</div>
			</div>
			
			<!--更改兑换页面-->
			<div class='give_out'>
				<h2 id="h2_redeem">设置兑换页面</h2>
				<div id="div_redeem" class='con_box' style='display: none'>
					<div class='clearfix upload'>
						<div class='fl preview_img'>
							<img id="promotion_redeem_img"
								src='${actx}/images/campaign_common/tou-big.png' />
						</div>
						<div class='fr' style='margin-top: -10px;'>
							<button id='btn_success_redeem' class='fl cancil' type="button"
								style="background: #6D6D6D; display: none">使用默认</button>
							<input class='js_oper_ctl btn_confirm' type="button"
								onclick='manualChangeRedeemBk()' value="自定义上传"> <input
								type="file" id="redeemBk" name='redeemBk' style='display: none' />
						</div>
					</div>
					<div class='sel_tip'>图片尺寸最佳为640px*500px</div>
					<div class='sel_tip'>
						按钮文字1:<input type="text" name="redeemText" value="立即兑换" class="font_text" style="width:100px;"/>
					</div>
					<div class='sel_tip'>
						按钮文字2:<input type="text" name="introductionText" value="兑换说明" class="font_text" style="width:100px;"/>
					</div>
				</div>
			</div>

			<!--预览效果,确定-->
			<div class='preview_result'>
				<input class='js_oper_ctl btn_confirm' type="button"
					onclick="$('#imgForm').submit();" value="确认提交"
					style="padding: 0px 92px;">
			</div>
			<!-- <div class='confirm_cancil'><button type="button" class='fl cancil'>我要重做</button><button class='fr'>确认提交</button></div> 	 -->
		</div>
		<!--左侧编辑-->
		<div class="left_con fr">
			<div class='phone_model'>
				<!--适配提示-->
				<div class='screen_tip'>*实际效果以手机为准</div>
				<div class='mod_con'>
					<!-- 更改领取页面 -->
					<div id="part_get">
						<input type="hidden" id="default_get"
							value="${actx}/images/campaign_common/tou-big.png">
						<!--可替换图片-->
						<div class='banner_pic'>
							<img id="back_get"
								src="${actx}/images/campaign_common/tou-big.png" />
						</div>
						<!--间距-->
						<div class='hr60'></div>
						<div class="content-wrap">
							<section class="page-content">
								<div class="m-contactUs m-link">
									<a id="get_text_template" href="#" class="textLink">点此获取免费流量兑换码</a>
								</div>
								<div class="m-copy">
									<span class="duihuan_code">***********</span>
								</div>
								<div class="zi">
									<p>点击长按复制兑换码</p>
								</div>
								<div class="m-copy">
									<c:if test="${reviewEnable}">
										<textarea placeholder="暂无领取说明" name="introduction" class="text_area_content">${campaignTempate.introduction}</textarea>
									</c:if>
									<c:if test="${!reviewEnable}">
										<textarea placeholder="暂无领取说明" name="introduction" class="text_area_content"></textarea>
									</c:if>
								</div>
							</section>
						</div>
					</div>
					<!-- 更改兑换页面 -->
					<div id="part_redeem" style="display: none;">
						<input type="hidden" id="default_redeem"
							value="${actx}/images/campaign_common/tou-big.png">
						<!--可替换图片-->
						<div class='banner_pic'>
							<img id="back_redeem"
								src="${actx}/images/campaign_common/tou-big.png" />
						</div>
						<!--间距-->
						<div class='hr60'></div>
						<!--文本框按钮-->
						<div class='input_btn'>
							<div class='input_btn_con'>
								<input type='text' placeholder='请输入兑换码' />
							</div>
							<div class='input_btn_con'>
								<input type='text' placeholder='请输入手机号码' />
							</div>
							<div class='input_btn_con'>
							<%-- 	<img class='btn_change'
									src="${actx}/images/campaign_common/mobile/redeem/btn2.png" />
								<img class='btn_func'
									src="${actx}/images/campaign_common/mobile/redeem/btn1.png"> --%>
									<a id="change_template" href="javascript:void(0)" class="btn_change">立即兑换</a>
									<a id="func_template" href="javascript:void(0)" class="btn_func">兑换说明</a> 
							</div>
						</div>
						<!--间距-->
						<div class='hr45'></div>
						<!--尾部图标-->
						<div class='model_foot'>
							<img src="${actx}/images/campaign_common/mb_icon.png" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>