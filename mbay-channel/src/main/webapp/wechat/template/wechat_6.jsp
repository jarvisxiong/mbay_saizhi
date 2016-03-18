<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=0" />
<meta http-equiv="Access-Control-Allow-Origin" content="*"/>
<meta http-equiv="x-dns-prefetch-control" content="on"/>
<meta name="baidu-tc-cerfication" content="c3443c3c5dd359363e2d788ff201ddb4" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechat/template.css"/>" />
<script type="text/javascript"	src="<c:url value="/js/template.js" />"></script>
<script type="text/javascript"	src="<c:url value="/js/WeixinApi.js" />"></script>
<title>领取流量</title>
<t:assets/>
<script>
//分享次数
var shareTimes = Number("${shareTimes}");
$(document).ready(function() {
	//立即兑换
	$("#gettraffic").bind("click",function(){		
		$("#errormsg").html("");
		if(validateMobile()){
			$("#gettraffic").attr("disabled","disabled");
			$.getJSON("<c:url value='/eventtraffic/get.mbay'/>?eventnumber=${eventnumber}&mobile="
					+ $('#mobile').val(),
				function(json) {
			        if(json.success){
			        	window.location.href="<c:url value='/wechat/trafficsuccess.jsp?ordernumber='/>"+json.content;
			        }else{
						$("#errormsg").html(json.content).css({'background':'#FFFFCC','border-radius':'4px'});					        	
			        }
			        $("#gettraffic").removeAttr("disabled");
				}
			);
		}	
	});
	//隐藏遮罩
	$('#dialog-after button').click(function(){
		closeBgAfter();
	});	
	//分享按钮(与立即兑换同一位置)
	$("#shareFirst").bind("click",function(){
		$('body,html').animate({scrollTop:0},1000);
		showBg();
		$(window).resize(function() {
			if(!$('#fullbg:hidden')){
				showBg();
			}
		});
	});
	
	//分享按钮(单独按钮)
	$("#shareCommit").bind("click",function(){
		$('body,html').animate({scrollTop:0},1000);
		showBg();
		$(window).resize(function() {
			if(!$('#fullbg:hidden')){
				showBg();
			}
		});
	});	
	//根据分享次数显示不同内容
	if(shareTimes == 0){
		$("#gettraffic").show();
		$("#shareFirst").hide();
		$("#shareCommit").hide();
	}else if(shareTimes == 1){
		$("#gettraffic").hide();
		$("#shareFirst").show();
		$("#shareCommit").show();
	}else{
		$("#gettraffic").hide();
		$("#shareFirst").show();
		$("#count").html("分享还剩"+shareTimes+"次!");
		$("#shareCommit").show();
	}
	
	$(".body").attr("style","background: url(<c:url value='/images/${backphotoid}/get.mbay'/>) no-repeat center;");
	if("${buttonphotoid}" == "0"){
		$(".service").attr("style","background: url(../../images/workimages/button.png) no-repeat center;");
	}else{
		$(".service").attr("style","background: url(<c:url value='/images/${buttonphotoid}/get.mbay'/>) no-repeat center;");
	}	
	// 兑换流量 
	$("#traffic").bind("click",function(){
		$("#errormsg").html("");
		if(validateMobile()){
			$("#gettraffic").attr("disabled","disabled");
			$.getJSON("<c:url value='/eventtraffic/get.mbay'/>?eventnumber=${eventnumber}&mobile="
					+ $('#mobile').val() + "&tk=${tk}",
				function(json) {
			        if(json.success){
			        	window.location.href="<c:url value='/wechat/trafficsuccess.jsp?ordernumber='/>"+json.content;
			        }else{
						$("#errormsg").html(json.content).css({'background':'#FFFFCC','border-radius':'4px'});					        	
			        }
			        $("#gettraffic").removeAttr("disabled");
				}
			);
		}		
	});
	
	// 美贝钱包兑换
	$("#mbay").click(function() {
		$("#errormsg").html("");
		if(validateMobile()) {
			$("#wallet-exchange").attr("disabled","disabled");
			$.getJSON("<c:url value='/eventtraffic/getByWallet.mbay'/>?eventnumber=${eventnumber}&mobile="
					+ $('#mobile').val() + "&linkUrl=" + window.location.href + "&tk=${tk}",
				function(json) {
			        if(json.success) {
			        	window.location.href="<c:url value='/wechat/trafficsuccess.jsp?ordernumber='/>"+json.content;
			        }else{
						$("#errormsg").html(json.content).css({'background':'#FFFFCC','border-radius':'4px'});					        	
			        }
			        $("#gettraffic").removeAttr("disabled");
				}
			);
		}	
	}); 
	
	//验证手机号
	function validateMobile(){
		if($("#mobile").val()==""){
			$("#errormsg").html("请输入手机号").css({'background':'#FFFFCC','border-radius':'4px'});	
			return false;
		}else{
			var reg=/^1[3|4|5|8][0-9]\d{8}$/;
			if(!reg.test($("#mobile").val())){
				$("#errormsg").html("手机号输入不正确！").css({'background':'#FFFFCC','border-radius':'4px'});
				return false;
			}
			$("#errormsg").html("");
			return true;
		}
	}
	
	//广告图片
	if("${advertiseId}" != "0"){
		$("#advertise").attr("src","<c:url value='/images/${advertiseId}/get.mbay'/>");
	}
});
//需要分享的内容，请放到ready里
WeixinApi.ready(function(Api) {
	if(shareTimes == 0){
		// 隐藏
	    Api.hideOptionMenu();
	}	
    // 微信分享的数据
    var wxData = {
        "appId": '', // 服务号可以填写appId
        "imgUrl" : 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/images/${shareid}/get.mbay',
        "link" : '${shareLink}',
        "desc" : '${content}',
        "title" : '${shareTitle}'
    };
    // 分享的回调
    var wxCallbacks = {
        // 收藏操作是否触发回调，默认是开启的
        favorite : false,
        // 用async模式，表示每次分享之前，都需要更新分享内容
        async:true,
        // 分享失败了
        fail : function(resp) {
            // 分享失败了，是不是可以告诉用户：不要紧，可能是网络问题，一会儿再试试？
            alert("分享失败,不要紧,可能是网络问题,一会儿再试试?");
        },
        // 分享成功
        confirm : function(resp) {
            //如果分享成功，则次数减一
            shareTimes = shareTimes - 1;
            //分享次数
            if(shareTimes > 0){
            	$("#count").html("分享还剩"+shareTimes+"次!");
            }else{
            	$("#gettraffic").show();
                $("#shareFirst").hide();
            	$("#count").html("");
            	$("#shareCommit").hide();
            }
            //关闭遮罩
            closeBg();
        }
    };
    // 用户点开右上角popup菜单后，点击分享给好友，会执行下面这个代码
    Api.shareToFriend(wxData, wxCallbacks);
    // 点击分享到朋友圈，会执行下面这个代码
    Api.shareToTimeline(wxData, wxCallbacks);
}); 
</script>
</head>

<body>
  <div class='con'>
    <div class='body'>
       <div class='logo'>
        <!--   <img id="logo" src='<c:url value="/images/${logophotoid}/get.mbay"/>'/>-->
       </div>
       <div  id='back' class='wenzi'>
          
       </div>
       <div class='inp_text' id="trfficbody">
         <input type='text' id="mobile" class='txt' placeholder="请输入手机号"/>
       </div>
        <div class='tip'>
        <!--活动当前已送出&nbsp;${sendedquantity}&nbsp;份 -->
          <span id="errormsg"></span>
       </div>
       <div class='suc' id='success'>
          <span><b>恭喜您！流量获取成功，更多免费流量请关注美贝官方微信！</b></span>
       </div> 
       
       <div class='ser'>
       	 <div style="text-align:center;">
       	 	<span id="count"></span>
       	 	<button id="shareCommit"  class="button_wechart" style="display:none" >分享</button>
       	 </div>
         <div class='service'>
            <a href='javascript:void(0)' class='dh' id="gettraffic">
            </a>	
            <a href='javascript:void(0)' class='dh' id="shareFirst"></a>
            <a href='<c:url value="/wechat/trafficQuery.jsp" />' class='sm'>
            	<!-- 
            	<div class='box_ico'><img src='<c:url value="/images/wechat/dhsm.png"/>'/></div>
            	<div class='box_wz_2'>兑换说明</div>
            	 -->
            </a>
         </div>
       </div>
    </div>   
    
    <!--遮罩-->
	<div id="fullbg-after" onclick="closeBgAfter()"></div>
	<div id="dialog-after" >
	    <div>
	       <button id="traffic">兑换成流量</button>
	       <button id="mbay">兑换到美贝钱包</button>
	    </div>
	</div>

    <!--遮罩-->
    <div id="fullbg" onclick="closeBg()"></div> 
    <div id="dialog" ><img id="advertise" src="../images/mobile_traffic.png" /></div>
 </div>
</body>
</html>