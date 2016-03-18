<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ taglib prefix="fs" uri="http://www.mbpartner.cn/jsp/fastdfs/tags"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="${actx}/css/wechat_campaign/set_template.css"
	rel="stylesheet" type="text/css" />
<link href="${actx}/css/wechat_campaign/advanced.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery.validate.js"></script>
<script type="text/javascript" src="${actx}/js/uploadimg.js"></script>
<script type="text/javascript"
	src="${actx}/js/wechat_campaign/set_template.js"></script>
<script type="text/javascript" src="${actx}/js/edit_model_share.js"></script>
<script type="text/javascript"
	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script>
	$(function() {
		$('.give_bar h2').click(function() {
			if ($(this).next('.con_box').is(':visible')) {
				return false;
			}
			$('.con_box').slideUp('fast');
			$(this).next('.con_box').slideDown('fast');
			var setType = $(this).attr("setType");
			$(".show_window").hide();
			$("#" + setType).show();
		});

		//防止重复提交
		$("#templateForm").Validform({
			showAllError : true,
			tiptype : 3
		});

	});
</script>

<div>${ids }</div>

<c:choose>
	<c:when test="${reviewEnable}">
		<script>
			$(function() {
				//判断地址栏是否有message参数，以此来判断详情页面是否添加或更新成功
				//获取地址栏参数
				function getQueryString(name) {
					var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
					var r = window.location.search.substr(1).match(reg);
					if (r != null)
						return unescape(r[2]);
					return null;
				}
				if (getQueryString("message") == "success") {
					$.messager.remind("修改成功");
				}
				//设置背景
				$("div.wx_body")
						.attr("style",
								"background-image: url('${campaignTempate.backphoto}');background-size: cover;");
				$("#back_short_img")
						.attr("src", '${campaignTempate.backphoto}');

				var shareTimes = '${campaignTempate.shareTimes}';
				if (parseInt(shareTimes) > 0) {
					$("#shareEnable").val("1");//设置为分享
					$("#shareImgSrc").attr("src",
							"${campaignTempate.shareImage}");
				}
				$("input[name=shareTimes]")
						.val('${campaignTempate.shareTimes}');
				$("input[name=shareTitle]")
						.val('${campaignTempate.shareTitle}');
				$("input[name=content]").val('${campaignTempate.content}');
				$("input[name=shareLink]").val('${campaignTempate.shareLink}');
				var shareSuccess = '${campaignTempate.successPhoto}';
				if (shareSuccess != '') {
					$("#privew_succss_img").attr("src",
							"${campaignTempate.successPhoto}");
					$("#succss_short_img").attr("src",
							"${campaignTempate.successPhoto}");
				}
			});
		</script>
	</c:when>
	<c:otherwise>
		<script>
			$(function() {
				//默认选中第一个系统模板
				$(".bj_box").find("a").first().trigger("click");
			});
		</script>
	</c:otherwise>
</c:choose>

<form id="templateForm" enctype="multipart/form-data" method="post"
	action="<c:url value="/wechatCampaign/set_template.mbay"/>">
	<m:token />
	<input type="hidden" id="shareEnable" value="0"> <input
		type="hidden" id="shareImgSrc" src=""> <input type="hidden"
		name="modelid" value="${modelid}"> <input type="hidden"
		name="eventnumber" value="${eventnumber}"> <input
		type="hidden" name="eventDefaultTemplateId" value="0"> <input
		type="hidden" name="shareTimes" value="0" /> <input type="hidden"
		name="content" value="" /> <input type="hidden" name="shareTitle"
		value="" /> <input type="hidden" name="shareLink" value="" /> <input
		type="file" id="shareImage" name="shareImageFile"
		style="display: none" /> <input type="hidden" name="info" value="0" />
	<div class='clearfix'>
		<!--右侧编辑选项-->
		<div class="right_con fr">
			<div class="bg_main_tit">微信伴侣模板设置</div>
			<!--更改发放页面-->
			<div class='give_bar'>
				<h2 setType="give_out">一.设置微信营销流量领取模板</h2>
				<div class='con_box'>
					<div class='sel_tip'>系统默认摸版：</div>
					<div class="bj_box">
						<div class="arrow_1 arrow_icon sec_1"></div>
						<!-- 每页显示4条，超过的隐藏 -->
						<c:forEach items="${list}" var="bean" varStatus="vs">
							<a id="bj_box_a_${vs.index}" name="bj_box_a"
								href="javascript:void(0)" defaultId="${bean.backPicture}"
								onclick='change("${bean.id}","${bean.backPicture}",this)'
								<c:if test="${vs.index > 3}">style='display:none;'</c:if>><i
								class="hidden"></i><img src='${bean.backPicture}' /></a>
						</c:forEach>
						<div class="arrow_0 arrow_icon sec_0"></div>
					</div>
					<div class='clearfix upload'>
						<div class='fl preview_img'>
							<img id='back_short_img'
								src='${actx}/images/campaign_common/default.png' />
						</div>
						<div class='fr'>
							<input type="button" class="js_oper_ctl btn_confirm"
								onclick="manualChange();" value="自定义上传">
						</div>
						<input type="file" id="bk" name='bk' style="display: none" />
					</div>
					<div class='sel_tip'>
						图片尺寸最佳为750px*1334px
						<!-- <button type="button" id="give_psd" class='download'>下载模板</button> -->
					</div>
					<div class='advanc_set'>
						<input type="button" class="js_oper_ctl btn_confirm"
							onclick='share()' value="高级设置">
						<div class='sel_tip' style='padding: 0px'>设置用户分享信息，达到更好的营销效果！</div>
					</div>
				</div>
			</div>
			<!--更改发放成功页面-->
			<div class='give_bar'>
				<h2 setType="give_success">二.设置领取成功模板页面</h2>
				<div class='con_box' style='display: none'>
					<div class='clearfix upload'>
						<div class='fl preview_img'>
							<img id="succss_short_img"
								src='${actx}/images/campaign_common/default.png' />
						</div>
						<div class='fr'>
							<button id='btn_success_def' class='fl cancil' type="button"
								style="background: #6D6D6D; display: none">使用默认</button>
							<input type="button" class="js_oper_ctl btn_confirm"
								onclick="customGiveSuccessImg()" value="自定义上传">
						</div>
						<input type="file" id='give_success_file' name='givesuccess'
							style="display: none" />
					</div>
					<div class='sel_tip'>
						图片尺寸最佳为750px*1334px
						<!--<button class='download' id="" type="button">下载模板</button>-->
					</div>
				</div>
			</div>
			<!--预览效果,确定-->
			<div class='preview_result'>
				<input id="sub_btn" class="js_oper_ctl btn_confirm" type="button"
					onclick="javascript:$('#templateForm').submit();" value="确认提交"
					style="padding: 0px 92px;">
			</div>
			<!-- 
			<div class='confirm_cancil'>
				<button class='fl cancil'>我要重做</button>
				<button class='fr'>确认提交</button>
			</div>							
			 -->
		</div>
		<!--左侧编辑-->
		<div class="left_con fr">
			<div class='phone_model'>
				<!--适配提示-->
				<div class='screen_tip'>*实际效果以手机为准</div>
				<div class='mod_con'>
					<!-- 流量下发 -->
					<div class="show_window" id="give_out">
						<div class='wx_body' id="imgbk">
							<div class='wx_logo' id="divlogo">
								<!--  <img  id="tempimglogo" src='<c:url value="/images/workimages/logo_2.png" />' alt="your image" />-->
							</div>
							<div class='wenzi'></div>
							<div class="share_content share_info">分享到朋友圈或小伙伴即刻领取流量！</div>
							<div class='inp_text'>
								<input type='text' class='txt' placeholder="请输入手机号" />
							</div>
							<div class='ser'>
								<div class='ser_tip'>
									<!-- <button id="shareCommit"  type="button" class="button_wechart" style="display: none">分享</button> -->
									<img class="share_img share_info"
										src="${actx}/images/wechat_campaign/wechat_share.png">
								</div>
								<div class='service'>
									<img id="getbtn"
										src='${actx}/images/wechat_campaign/direct_get_only.png'>
									<%-- <img
										id="getbtn" src='${actx}/images/wechat_campaign/btn_lqsm.png'> --%>
								</div>
							</div>
						</div>
					</div>
					<!-- 下发成功  -->
					<div class="show_window" id="give_success" style="display: none">
						<!--可替换图片-->
						<div class='banner_give_success'>
							<input type="hidden" id="success_def_src"
								value="${actx}/images/wechat_campaign/get_traffic_success.jpg">
							<img id="privew_succss_img"
								src="${actx}/images/wechat_campaign/get_traffic_success.jpg" />
						</div>
						<!--间距-->
						<div class='hr60'></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>