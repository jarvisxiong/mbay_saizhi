<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:mobile-assets />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>提示</title>
<link rel="stylesheet" type="text/css" href="${ctx }/traffic_red/mobile/shake/css/public.css?v=${version}" />
<link rel="stylesheet" type="text/css" href="${ctx }/traffic_red/mobile/shake/css/hotlinking.css?v=${version}" />
</head>
<body>
<section class="h5-hd">
    <div class="modal-succeed-content">
        <header>
            <img src="${ctx }/traffic_red/mobile/shake/image/gift/uncle-5.png">
        </header>
        <div class="modal-content">
            <h2><i>每天都来赚MB</i></h2>
            <p>您所打开的页面属于“MB钱包”</p>
            <p>请进入“MB钱包”进行相关的操作</p>
            <p>如果您需要参与“MB钱包”的活动</p>
            <p>请猛击按钮！</p>
        </div>
        <footer>
            <a href="javascript:void(0)" evt-touch="gotoUrl('http://192.168.21.181:8082/mbaywallet/web/main/index/ui.mbay')">进入MB钱包</a>
    	</footer>
    </div>
</section>
</body>
</html>