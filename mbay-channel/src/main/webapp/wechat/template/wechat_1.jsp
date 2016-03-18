<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <meta name="keywords" content="美贝直通车-流量领取"/>
    <meta name="description" content="美贝直通车-流量领取"/>
    <title>流量领取</title>
	<link href="${actx}/wro/${version}/wechat_1.css"	rel="stylesheet" type="text/css" />
    <t:mobile-assets/>
    <script type="text/javascript" src="${actx}/js/lib/WeixinJS-SDK.js"></script>
    <script type="text/javascript"	src="${actx}/js/mobile/dialog.js"></script>
    <style>
        html,body,.mb-wrap{height:100%;width:100%;}
        .mb-wrap{background:url(${backPhoto}) no-repeat  50% 50% ;background-size:cover;position: absolute;
		min-height:600px;
		min-width:320px;
		}
		.content-wrap{width:250px;margin:0 auto;position: relative;top:30%;}
		#shareCount{background:#aebece;color:#fff;border-radius:3px;text-align: center;line-height: 21px;}

        .content-wrap input{border: 1px solid #ccc;color:#656565;text-indent:0.5em; height:35px;width:100%;line-height:100%;font-size:15px;border-radius:5px;margin-top:5px;}
        .ct-item{height:30px;position:relative;margin-top: 80px;width: 92%;}
        .ct-item a{display:block;height:30px;width:90px;position:absolute;top:0;text-indent:-9999px;}
        .ct-item .duihuan-btn{left:12px;background:url(${actx}/images/wechat_campaign/btn_ljlq.png) 50% 50% no-repeat; background-size: cover;}
        .content-wrap p{display:block;color: red;left:0; font-size: 12px; text-indent:0.5em;height:30px;line-height: 30px;font-family: "微软雅黑" ;}
        .ct-item .shuom-btn{right:12px;background:url(${actx}/images/wechat_campaign/btn_lqsm.png) 50% 50% no-repeat; background-size: cover;}
        .pup-share{position:fixed;top:0;left:0;height:100%;width:100%;background:url(${advertisePhoto}) 50% 15% no-repeat rgba(0,0,0,0.9);background-size:63% auto;z-index:1;display:none;text-align:center;}
        .item_02{left: 99px;bottom: 191px;height:74px;width:50px;background:url(${actx}/images/wechat_campaign/wechat_share.png) 50% 50% no-repeat;background-size:cover;}
        
        .mb-pup-xl{position:fixed;width:100%;height:100%;background:rgba(0,0,0,.9);top:0;left:0;z-index:10;}
		.xiaoxi-xl{ width:220px;border-radius:5px;position:relative;top:40%;margin:0 auto;transform:translateY(-50%);overflow:hidden;background-color:#F1F1F1;}
        .btn-wrap-xl{height:150px;width:50%;float: left;}
        .btn-wrap-xl a{font-family:"黑体";display:block;height:100%;width:100%;text-align:center;}
        .btn-wrap-xl img{display:block;margin:20px auto 0 auto;}
        .btn-wrap-xl span{line-height:45px;}
        
        .ct-item .wallet-btn{left:0;background:url(${actx}/images/wechat_campaign/wallet_get.png) 50% 50% no-repeat; background-size: cover;}
        .ct-item .wallet-only-btn{left:0;background:url(${actx}/images/wechat_campaign/wallet_get_only.png) 50% 50% no-repeat; background-size: cover;}
        .ct-item .direct-btn{right:12px;background:url(${actx}/images/wechat_campaign/direct_get.png) 50% 50% no-repeat; background-size: cover;}
    	
    	.tip_i {display: inline-block;background: #F5F5F5;padding: 4px 0px 0px;cursor: pointer;font-size: 12px;border-radius:130px;font-style: normal;height: 16px;width: 20px;text-align: center;position: relative;}
    </style>
</head>
<body>
<body>

<div class="mb-wrap">
    <div class="content-wrap">       
          <c:if test="${shareTimes>0}">
            <div class="ct-tip">
               <div id="shareCount">分享到${shareTimes}个朋友圈或小伙伴即刻领取</div>
            </div>
          </c:if>
           
             <input type="tel" id="mobile" maxlength="11" class="numtel" placeholder="请输入手机号码">
            <%--  <a href="${ctx}/promotion_campaign/mobile/redeem_instruction.jsp"><i class='tip_i'>?</i></a> --%>
            
             <div class="ct-item">
                 <%-- <a id="exchange-wallet" href="javascript:void(0)" <c:if test="${directEnable}">class="wallet-btn"</c:if> <c:if test="${!directEnable}">class="wallet-only-btn" style="width:218px;"</c:if>>钱包兑换</a>   --%>                
                 
                 <a href="${ctx}/promotion_campaign/mobile/traffic_redeem_instruction.jsp" class="shuom-btn"></a> 
                 <c:if test="${directEnable}">
                 	<a id="exchange-traffic" href="javascript:void(0)" class="duihuan-btn"></a> 
                 </c:if>
                 <!-- <a href="javascript:void(0)" class="shuom-btn">兑换说明</a> -->                     
            </div>
            
           <c:if test="${shareTimes>0}">
            <div class="ct-item item_02 share-btn">
                <a href="#" class="share-btn">分享</a>				
             </div>
            </c:if>
    </div>
</div>
<!-- 分享提示   开始 -->
<div class="pup-share"></div>
<!-- 分享提示  结束 -->
</body>

<script type="text/javascript">
//分享次数
var shareTimes = parseInt("${shareTimes}");
$(function() {
	/* $(".shuom-btn").bind("touchend", function() {
		window.location.href = "${ctx}/promotion_campaign/mobile/redeem_instruction.jsp";
	}); */
	
	$(".pup-share").on("click",function(){
	     $(this).fadeOut(100);
	});
	
	// 兑换流量
	$("#exchange-traffic").on("click", function() {
		exchangeTraffic();
	});
	
	// 美贝钱包领取
	$("#exchange-wallet").on("click", function() {
		exchangeWallet();
	});
	
	function exchangeTraffic() {
		//$.messager.close_mydlg();
		//event.stopPropagation();
		
		if(shareTimes>0){
			$(".share-btn").trigger("click");
			return;
		}
		if(validateMobile()){
			$.getJSON("${ctx}/eventtraffic/get.mbay?eventnumber=${eventnumber}&mobile="
					+ $('#mobile').val() + "&tk=" + getUrlParam("tk"),
				function(json) {
			        if(json.success){
			        	var successPhoto='${successPhoto}';
			        	if(successPhoto==''){
			        		$(".mb-wrap").css("background-image","url(${actx}/images/wechat_campaign/get_traffic_success.jpg)");		
			        	}else{
			        	    $(".mb-wrap").css("background-image","url(${successPhoto})");			        		
			        	}
			        	$(".content-wrap").hide();
			        }else{
			        	$.messager.remind({ content: json.content });
			        }
				}
			);
		}	
	}
	
	function exchangeWallet() {
		//$.messager.close_mydlg();
		//event.stopPropagation();
		
		$("#exchange-wallet").attr("disabled","disabled");
		$.getJSON("${ctx}/eventtraffic/getByWallet.mbay?eventnumber=${eventnumber}&mobile="
				+ $('#mobile').val() + "&tk=" + getUrlParam("tk"),
			function(json) {
		        if(json.success){
		        	var successPhoto='${successPhoto}';
		        	if(successPhoto==''){
		        		$(".mb-wrap").css("background-image","url(${actx}/images/wechat_campaign/get_traffic_success.jpg)");		
		        	}else{
		        	    $(".mb-wrap").css("background-image","url(${successPhoto})");			        		
		        	}
		        	$(".content-wrap").hide();
		        }else{
		        	$.messager.remind({ content: json.content });
		        }
		        $("#exchange-wallet").removeAttr("disabled");
			}
		);
	}
	
	// 获取url参数
	function getUrlParam(paraName) {  
		var sUrl = location.href; 
		var sReg = "(?:\\?|&){1}" + paraName + "=([^&]*)";
		var re=new RegExp(sReg,"gi"); 
		re.exec(sUrl); 
		return RegExp.$1; 
	}
	
	//分享按钮(单独按钮)
	$(".share-btn").on("click",function(){
		$('body,html').animate({scrollTop:0},1000);
		 $(".pup-share").fadeIn(100);
		$(window).resize(function() {
			if(!$('.pup-share:hidden')){
				 $(".pup-share").fadeIn(100);
			}
		});
	});	
	
	/*暂不自定义按钮图片 
	if("${buttonphotoid}" != "0"){
		$(".content-wrap").attr("style","background: url(<c:url value='/images/${buttonphotoid}/get.mbay'/>) 15px 57px no-repeat;");
	} */
	
	//验证手机号
	function validateMobile(){
		if($("#mobile").val()==""){
			$.messager.remind({ content: "请输入手机号!" });
			return false;
		}else{
			var reg=/^1[3|4|5|8][0-9]\d{8}$/;
			if(!reg.test($("#mobile").val())){
				$.messager.remind({ content: "手机号格式不正确!" });
				return false;
			}
			return true;
		}
	}	
	
	//微信js相关配置
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appId}', // 必填，公众号的唯一标识
	    timestamp: '${timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: [
	                'hideOptionMenu',
	                'onMenuShareAppMessage',
	                'onMenuShareTimeline',
	                'onMenuShareQQ'
	               ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	wx.ready(function(){
		wx.checkJsApi({
	    	jsApiList: [
						'hideOptionMenu',
						'onMenuShareAppMessage',
						'onMenuShareTimeline',
						'onMenuShareQQ'
	    	           ], // 需要检测的JS接口列表，所有JS接口列表见附录2,
	    	success: function(res) {
		        // 以键值对的形式返回，可用的api值true，不可用为false
		        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
		        // alert("检测js接口,返回信息 -> " + res.errMsg);
	    	}
		});
		
		//分享图片设置
		var shargeImg='${sharePhoto}';	
		if(shareTimes == 0){
			// 隐藏分享操作
			wx.hideOptionMenu();
		}else{
			if('${sharePhoto}'==''){
				shargeImg='${appDomain}${ctx}/images/laozhang_speak.png';
			}
		}
		
		//分享给朋友
		wx.onMenuShareAppMessage({
		    title: '${shareTitle}', // 分享标题
		    desc: '${content}', // 分享描述
		    link: '${shareLink}', // 分享链接
		    imgUrl: shargeImg, // 分享图标
		    type: 'link', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    	share_success();
		    },
		    fail: function (){
		    	$.messager.remind({ content: "分享失败,不要紧,可能是网络问题,一会儿再试试?" });
		    }
		});
		
		//分享到朋友圈
		wx.onMenuShareTimeline({
		    title: '${shareTitle}', // 分享标题
		    link: '${shareLink}', // 分享链接
		    imgUrl: shargeImg, // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    	share_success();
		    },
		    fail: function (){
		    	$.messager.remind({ content: "分享失败,不要紧,可能是网络问题,一会儿再试试?" });
		    }
		});
		
		//分享到QQ
		wx.onMenuShareQQ({
		    title: '${shareTitle}', // 分享标题
		    desc: '${content}', // 分享描述
		    link: '${shareLink}', // 分享链接
		    imgUrl: shargeImg, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    	share_success();
		    },
		    fail: function (){
		    	$.messager.remind({ content: "分享失败,不要紧,可能是网络问题,一会儿再试试?" });
		    }
		});
		
		function share_success(){
			//如果分享成功，则次数减一
            shareTimes = shareTimes - 1;
            //分享次数
            if(shareTimes > 0){
            	$("#shareCount").html("再分享给"+shareTimes+"个朋友圈或小伙伴即刻领取");
            }else{
            	$(".ct-tip").hide();
            }
            //关闭遮罩
            $(".pup-share").trigger("touchend");
		}
	});
	 
	// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	wx.error(function(res){
		//$.messager.remind({ content: "微信JS_SDK配置错误,错误信息: " + res.errMsg });
	});
});
</script>
</html>