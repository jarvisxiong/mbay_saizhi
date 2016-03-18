<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑模式预览</title>
<link href="${actx}/wro/${version}/review_template.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${actx}/wro/${version}/review_template.js"></script>
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery.validate.js"></script>
<script type="text/javascript"
	src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<%--  <script type="text/javascript"	src="<c:url value="/js/wx_edit.js" />"></script> --%>
<script type="text/javascript">
$(function(){
	
	$(".wx_body").attr("style","background: url(<c:url value='/images/${backphotoid}/get.mbay'/>) no-repeat center;");
	if("${buttonphotoid}" == "0"){
		$(".service").attr("style","background: url(../images/workimages/button.png) no-repeat center;");
	}
	
	//手动触发选中事件
	var btn = document.getElementById("${backphotoid}");
	if(btn != null){
		btn.click();
	}
	
	$(".js_btn").bind("click",function(event){		 
		  var formData = new FormData($("#imgForm")[0]);		 
		  $.ajax({
		    url: '<c:url value="/wechatCampaign/review_edit_model.mbay"/>',
		    type: 'POST',
		    data: formData,
		    async: false,
		    cache: false,
		    contentType: false,
		    processData: false,
		    success: function (returndata) {
		    	$.messager.alert('提示',returndata.message);
		    	if(returndata.success){
		    		setTimeout(function() {  
		    			window.location.href='<c:url value="/wechatCampaign/campaign_info.mbay"/>?eventnumber=${eventnumber}'; 
					}, 2000);
		    	}
		    }
		  });
		  return false;
		});
$(".js_back").bind("click",function(){
	window.location.href='<c:url value="/wechatCampaign/campaign_info.mbay"/>?eventnumber=${eventnumber}';	
});

});

//选中效果
function selectEffect(id){
	document.getElementById(id).click();
}
</script>

</head>
<body>
	<div class='con'>
		<div class='body clearfix'>
			<div class='b_con com_width clearfix'>
				<div class='hdlb'>微信页面编辑</div>
				<%@ include file="/common/leftcon.jsp"%>
				<form id="imgForm" enctype="multipart/form-data"
					action="<c:url value="/wechatCampaign/review_template.mbay"/>"
					method="post">
					<input type="hidden" name="modelid" value="${modelid}"> <input
						type="hidden" name="eventnumber" value="${eventnumber}"> <input
						type="hidden" name="eventDefaultTemplateId" value="0">
					<div class='clearfix'>
						<!--编辑模块-->
						<div class='right_con fr'>
							<h2 class='right_tit'>设置</h2>
							<div class='ggbj'>更改背景</div>
							<!-- 
						<div class='bj_box_1' id="divbk" style="display: none">
						  <a href='javascript:void(0)'><i></i><img id="imgbk"  alt="your image" /></a> 						
						</div>
						 -->
							<div class='right_sc'
								style="margin-bottom: 10px; position: relative;">
								<input type="file" id="bk" name='bk'
									style='visibility: hidden; height: 1px; position: absolute' />
								<img src='<c:url value="/images/workimages/temp_back_up.jpg" />'
									onclick="manualChange();" />
							</div>
							<div class='bj_box'>
								<div class='arrow_1 arrow_icon sec_1'></div>
								<c:forEach items="${list}" var="bean" varStatus="vs">
									<!-- 只为修改selected选中样式，无其他作用 -->
									<c:if test="${bean.backPictureId eq backphotoid}">
										<a id="${backphotoid}" style="display: none;"
											href="javascript:selectEffect('bj_box_a_${vs.index}');"></a>
									</c:if>
									<!-- 每页显示4条，超过的隐藏 -->
									<a id="bj_box_a_${vs.index}" name="bj_box_a"
										href='javascript:change("${bean.id}","${bean.backPictureId}","${bean.buttonPictureId}")'
										<c:if test="${vs.index > 3}">style='display:none;'</c:if>><i
										<c:if test="${bean.backPictureId eq backphotoid}">style='display:block;'</c:if>></i><img
										src='<c:url value="/images/${bean.backPictureId}/get.mbay"/>' /></a>
								</c:forEach>
								<div class='arrow_0 arrow_icon sec_0'></div>
							</div>
							<div class='right_tip'>背景图片尺寸最佳为400像素*550像素</div>
							<div class='right_an'>
								<input type="button" class='right_btn js_btn' value='确定' /> <input
									type="button" class='right_btn js_back' value='返回' />
							</div>
						</div>
						<!--微信模块-->
						<div class='left_con fr'>
							<div class='con_wx'>
								<div class='wx_body'>
									<!-- .wx_body{margin-top: 40px;width:100%;height:683px;background:url(../../images/workimages/bg_1_0.jpg) center} -->
									<!--  <div class='wx_logo' id="divlogo">
                             <img  id="tempimglogo" src='<c:url value='/images/${eventModel.backphoto.id}/get.mbay'/>' alt="your image" />
                             
                           </div>-->
									<div class='wenzi'></div>
									<div class='inp_text'>
										<input type='text' class='txt' placeholder="请输入手机号" />
									</div>
									<div class='ser'>
										<div class='service' style="height: 250px;">
											<!-- 
                                <a href='#'><div class='ser_box fl'><div class='box_ico'><img src='<c:url value="/images/workimages/ljdh.png" />'/></div><div class='box_wz_1'>立即兑换</div></div></a>
                                <a href='#'><div class='ser_box fl'><div class='box_ico'><img src='<c:url value="/images/workimages/dhsm.png" />'/></div><div class='box_wz_2'>兑换说明</div></div></a>
                             	 -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
