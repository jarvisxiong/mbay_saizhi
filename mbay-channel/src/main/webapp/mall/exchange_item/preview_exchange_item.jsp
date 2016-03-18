<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>手机预览</title>
<link href="${actx}/css/mall/preview_exchange_item.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="mb-content" style="padding-bottom: 80px;">
	<div class="mb-banner-style">
		<div class="mb-banner-swipe" id="swipe" style="visibility: visible;">
			<div>
	            <!-- banner循环开始 -->
	            <a><img src="${details[0].picture}"></a>
	            <!-- banner循环结束 -->
            </div>
        </div>
        <div class="mb-banner-position">
            <strong>1</strong><span>/1</span>
        </div>
    </div>
    <header>
	    <div class="item-info">
	    	<div class="left">
	        	<h3>${bean.title}</h3>
	        </div>
	        <div class="right">
	        	<div class="theme-color"><p>${bean.mbay}</p><span>MB</span></div>
	        	<span>￥${bean.price}元</span>
	        </div>
	    </div>
    </header>
    <section>
    	<p class="title">
    		<i class="arrow"></i><span>详情说明：</span>
        </p>
        <div class="description">
			<h4>${bean.ruleName}</h4>${bean.ruleContent}<br><br>
			<h4>${bean.detailName}</h4>${bean.detailContent}
        </div>
     </section>
</div>
<footer class="footer">
	<a href="javascript:void(0)">马上兑换</a>
</footer>
</body>
</html>