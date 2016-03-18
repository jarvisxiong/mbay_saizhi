<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/file/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人主页</title>
<link rel="stylesheet" href="${ctx }/page/main/css/index.css?v=${version}">
</head>
<body>
<header class="m-user-head">
 	<!-- 2015-8-3 daisy.wang更新 -->
    <div class="slideNav">
		<a class="m-user-tel" href="javascript:void(0)"><img src="${actx}/page/main/img/user.png">客服</a>
       	<nav class="userList">
           	<a class="m-user-phone" href="tel:4007280057"  data-tel="4007-280-057"><img src="${actx}/page/main/img/tel-bg.png">电话</a>
        	<a class="m-user-qq" href="http://wpa.qq.com/msgrd?v=1&amp;uin=2985654507&amp;site=wwww.mbpartner.cn&amp;menu=yes">
            	<img src="${actx}/page/main/img/qq.png">Q&nbsp;Q
            </a>
    	</nav>
    </div>
	<span class="m-user-lay absolute-vert-center"> 
		<div class="lay-chd img-wp">
			<span class="m-user-img absolute-vert-center"></span>
		</div>
		<div class="lay-chd">
			<div class="item-wrap absolute-vert-center">
				<span class="m-user-name"> 
					<c:if test="${not empty telephone}">
						<c:choose>
							<c:when test="${empty session_user }">
								${fn:substring(telephone, 0, 3) }xxxx${fn:substring(telephone, 8, 11) }
							</c:when>
							<c:otherwise>
								${telephone }
							</c:otherwise>
						</c:choose>
					</c:if>
				</span> 		
				<c:choose>
					<c:when test="${empty session_user }">
						<a href="javascript:void(0)" id="login" class="m-user-login">登录</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" id="logout" class="m-user-login">注销</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</span>
</header>
<article class="m-main clearfix">
	<a href="javascript:void(0)" id="trade" class="m-item m-item-a item-a">
		<div class="item-content-a touchHover">
			<h2 class="statistics-text">收入</h2>
			<span>${balance } MB</span> 
			<img src="${actx }/page/main/img/modile-1-1.png">
		</div>
	</a> 
	<a href="javascript:void(0)" id="enshrine" class="m-item m-item-b item-b">
		<div class="item-content-b touchHover">
			<h2 class="statistics-text">收藏<br>MB钱包</h2>
			<img src="${actx }/page/main/img/erweima.jpg">
		</div>
	</a> 
	<a href="javascript:void(0)" class="m-item m-item-b item-c" id="red-pack">
		<div class="item-content-c touchHover">
			<h2 class="statistics-text">红包<span>0个</span></h2>
		</div>
	</a> 
	<a href="javascript:void(0)" id="traffic-package" class="m-item m-item-a item-d">
		<div class="item-content-d touchHover">
			<h2 class="statistics-text">欢乐电玩城</h2>
			<span>GAME</span>
		</div>
	</a> 
	<a href="javascript:void(0)" id="campaign" class="m-item m-item-b item-e"> 
		<div class="item-content-e touchHover">
			<h2 class="statistics-text">活动</h2>
			<img src="${actx }/page/main/img/modile-3-3.png">
		</div>
	</a> 
	<a href="javascript:void(0)" class="m-item-footer" id="mall">
		<div class="item-content-f touchHover">
			<h2 class="statistics-text">MB商城</h2>
			<img src="${actx }/page/main/img/modile-4-4.png">
		</div>
	</a>
</article>   

<div class="popup absolute-center" id="qrReadOnline">
	<div class="close-wrap"><span class="pup-p1">X</span></div>
	<img src="${ctx }/common/img/code-16.png">
	<span class="desc">长按二维码可直接识别</span>
</div>

<input type="hidden" value="${telephone }" id="telephone" />

<script type="text/javascript" src="${ctx }/page/main/js/index.js?v=${version}"></script>
</body>
</html>