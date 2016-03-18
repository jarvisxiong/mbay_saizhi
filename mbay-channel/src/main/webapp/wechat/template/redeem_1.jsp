<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include  file="/common/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <meta name="keywords" content="美贝直通车"/>
    <meta name="description" content="美贝直通车"/>
    <title>美贝直通车</title>
    <link href="${actx}/wro/${version}/redeem_1.css"	rel="stylesheet" type="text/css" />
    <t:assets/>
    <script type="text/javascript" src="${ctx}/js/zclip/jquery.zclip.min.js"></script>
    <style>
        html,body,.mb-wrap{height:100%;width:100%;}
        .mb-wrap{background:url(${ctx}/wechat/template/temp/img/content-bg.jpg) 50% 50% no-repeat;background-size:cover;}
        .head-big{display:block;margin:0 auto 40px auto;}
        .content-wrap{height:280px;width:267px;position:relative;margin:0 auto;bottom:60px;background:url(${ctx}/wechat/template/temp/img/duihuan2.png) 0 160px no-repeat;background-size:contain;}
        .page-content{overflow:hidden;-webkit-box-flex:100;}
        .m-contactUs{width:100%;text-align:center;position:absolute;bottom:33%;}
        .m-link .textLink{width:100%;height:50px;line-height:50px;margin:0 auto;color:#000;font-size:20px;text-align:center;text-decoration:none;background:#ffb504;border-radius:12px;display:inline-block;-webkit-transition:all .3s;}
        .m-copy{width:100%;text-align:center;position:absolute;bottom:10%;}
        .copyinner{width:100%;height:33px;border-radius:10px;}
        .m-copy .textcopy{ width:100%;height:33px;line-height:33px;margin:0 auto;font-size:18px;text-align:center;text-decoration:none;border-radius:10px;}

    </style>
    
     <script type="text/javascript">
    $(function(){    	
    $(".textLink").click(function(){
    	$.post("${ctx}/promotionCampaign/generate_code.mbay",{'eventnumber':'20150109120100033'},function(json) {
    		if (json.success) {
    			$(".textLink").html(json.code);
    			$(".textLink").click(function(){});
    		} else {
    			alert("兑换码生成失败：" + json.message);
    		}
    	});
    	
    });
    $(".textcopy").zclip({
	    path: '<c:url value="/js/zclip/ZeroClipboard.swf"/>',
	    copy: function () {
	        return $(".textLink").html();
	    }
	});
    });
    </script>
</head>

<body>

<div class="mb-wrap">

    <img class="head-big" src="${ctx}/wechat/template/temp/img/tou-big.png">
    <div class="content-wrap">

              
            <section class="page-content">
        
            <div class="m-contactUs m-link">
                    <a href="javascript:void(0);" class="textLink" style='background-color:#ffb504'>点此获取免费流量兑换码</a>
            </div>
               <div class="m-copy">
                  <div class="copyinner">
                  
                
                   <button type="submit" class="textcopy">复制兑换码</button>
                  </div>
               </div>

            </section>

      
    </div>
</div>

</body>
<script type="text/javascript">
		$(function(){
			var textLinkTimes=0;			
			$(".textLink").click(
				function(){
					if(textLinkTimes<1){
						var roundStr=_getRandomString(16);
					    $(this).html(roundStr);
						textLinkTimes++;
						}
					
					}
			);
		
		});
		function _getRandomString(len) {  
		var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1  
		var maxPos = $chars.length;  
		var pwd = '';  
		for (i = 0; i < len; i++) {  
			pwd += $chars.charAt(Math.floor(Math.random() * maxPos));  
		}  
    return pwd;  
}  
	</script>
</html>