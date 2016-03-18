<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
<meta name="keywords" content="直通车官网">
<meta name="description" content="直通车官网">
<meta name="csrf-param" content="token">
<meta name="csrf-token"
	content="MGt5dVNyZ1VAHA0kOxo9GWkkIBwZAD1iRR46PGY2Aj5IJFQDZBFUOw==">
<title>美贝直通车首页</title>
<link rel="icon" href="${actx}/images/favicon.png" />
<script type="text/javascript"
	src="${actx}/webjars/jquery/2.1.3/jquery.min.js"></script>
<link href="${actx}/webjars/bootstrap/3.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${actx}/webjars/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link href="${actx}/css/login/common.css" rel="stylesheet">
<link href="${actx}/css/index/index.css" rel="stylesheet">
<script src="${actx}/js/index/base.js" charset="UTF-8"></script>
<script src="${actx}/js/mbayui.js"></script>
<style>
[touch-action="none"] {
	-ms-touch-action: none;
	touch-action: none;
}

[touch-action="pan-x"] {
	-ms-touch-action: pan-x;
	touch-action: pan-x;
}

[touch-action="pan-y"] {
	-ms-touch-action: pan-y;
	touch-action: pan-y;
}

[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]
	{
	-ms-touch-action: pan-x pan-y;
	touch-action: pan-x pan-y;
}
</style>
<style type="text/css">
#MECHAT-PCBTN {
	font: 16px/24px 'Helvetica Neue', Helvetica, Arial;
	position: fixed;
	bottom: -100%;
	background: #1abc9c;
	color: #fff;
	text-align: center;
	padding: 10px 15px 8px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	cursor: pointer;
	z-index: 2147483645;
}

#MECHAT-PCBTN span {
	display: inline-block;
	height: 24px;
	padding: 0 0 0 34px;
	background: url('https://meiqia.com/images/pc_btn_icon.png') 0 0
		no-repeat;
}

#MECHAT-LAYOUT {
	position: fixed;
	right: 50px;
	bottom: -100%;
	width: 300px;
	height: 430px;
	z-index: 2147483647;
	overflow: hidden;
	border: 1px solid #1abc9c;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	box-shadow: 0 0 3px 2px rgba(135, 135, 135, 0.1);
}

#MECHAT-PCBTN,#MECHAT-LAYOUT {
	_position: absolute;
	_bottom: auto;
	_top: expression(eval(document.documentElement.scrollTop +
		document.documentElement.clientHeight-this.offsetHeight- ( parseInt(this.currentStyle.marginTop
		, 10)||0)-(parseInt(this.currentStyle.marginBottom, 10)||0)));
}

.myAccount {
	font-size: 14px;
	cursor: pointer;
	position: relative;
	background: #f7f7f7;
	padding: 10px 0px;
	padding-left: 30px;
	padding-right: 15px;
	border: solid #e6e6e6 1px;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	margin-top: -10px;
}

.myAccountImg {
	display: inline-block;
	position: absolute;
	left: 5px;
	width: 20px;
	height: 20px;
	background: url(../images/index_new/myAccount.png) no-repeat;
}
</style>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="javascript:void(0)"><img
					src="${actx}/images/index_new/logo.png" title="日常Logo"></a>
			</div>
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<c:if test="${username != null && username != ''}">
					<ul class="nav navbar-nav navbar-right">
						<li role="presentation"><a
							href="<c:url value='/channel/workbench.mbay'/>"><p
									class="myAccount">
									<i class="myAccountImg"></i>${username}</p></a></li>
					</ul>
				</c:if>
				<c:if test="${username == null || username == ''}">
					<ul class="nav navbar-nav navbar-right">
						<li role="presentation"><a
							href="<c:url value='/channel/toLogin.mbay'/>">登录</a></li>
						<li role="presentation"><a
							href="<c:url value='/signup/enterprise.mbay'/>">注册</a></li>
						<!-- <li role="presentation"><a class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="button" aria-expanded="false" href="javascript:;"><span class="name ellipsis">个人中心</span> <span class="caret"></span></a>

</li> -->
					</ul>
					<ul class="nav navbar-nav">
						<li class="active" role="presentation"><a
							href="#index">首页 <span class="sr-only">(current)</span></a></li>
						<li role="presentation"><a href="#chanpin">产品介绍</a></li>
						<li role="presentation"><a href="#hezuo">合作伙伴</a></li>
						<li role="presentation"><a href="#contact">联系我们</a></li>
						
						<!-- 
<li role="presentation"><a class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="button" aria-expanded="false" href="javascript:;"><span class="name">帮助中心</span>  <span class="caret"></span></a>
 -->
						<!-- <ul class="dropdown-menu right" role="menu">
</ul> 
-->
						</li>
					</ul>
				</c:if>
			</div>
		</div>
		<!-- /.container -->
	</nav>
	<!-- /nav -->

	<!--body_begin-->
	<div id="carousel-banner" class="carousel slide" data-ride="carousel">
		<a name="index"></a>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item loader banner-2 active" data-lazy-class="banner-2">
				<div class="carousel-caption carousel-caption-2">
					<h2>手机流量引爆商业生活</h2>
					<h3>互联网+时代 最优营销选择</h3>
					<p>社交媒体传播分享必备工具 &nbsp互动粉丝体验绝佳展示渠道</p>

					<p>
						<a class="btn btn-primary btn-primary1" href="javascript:void(0)"
							target="_blank">立即体验</a>
					</p>
				</div>
			</div>
			<div class="mb-pup"
				style="display: none; position: fixed; width: 100%; height: 100%; background: rgba(0, 0, 0, .6); top: 0; left: 0; z-index: 10000;">

				<div class="btn-wrap"
					style="text-align: center; width: 200px; position: relative; top: 50%; margin: 0 auto; transform: translateY(-50%); overflow: hidden; background-color: #fff;">
					<a href="#" style=""><img style="max-width: 100%;"
						src="/mbaychannel/images/index_new/wx-code.png" alt=""></a>
					<p style="font-size: 16px;">请用微信扫描二维码</p>
				</div>

			</div>
			<script type="text/javascript">
    $(function(){
    $(".btn-primary1").click(function() {
    $(".mb-pup").show();    
    });
    $(".mb-pup").click(function(){
        $(".mb-pup").hide();   
    });
});

  </script>

		</div>

		<!-- Controls -->
	</div>
	<!-- /#carousel-banner -->


	<!--     美贝是什么-->

	<section class="achievement">
		<div class="container">
			<div class="section-hd">
				<h2>了解美贝直通车</h2>
			</div>
			<div class="section-bd row">
				<div class="col-md-4">
					<span class="i-index i-bug"></span>

					<p class="title">
						&nbsp移动互联网4.0时代 <br>品牌推广必备工具

					</p>
				</div>
				<div class="col-md-4">
					<span class="i-index i-yuan"></span>

					<p class="title">
						&nbsp社交媒体互动时代 <br>内容营销首选装备

					</p>
				</div>
				<div class="col-md-4">
					<span class="i-index i-people"></span>

					<p class="title">
						O2O大数据时代 <br>产品销售转化发动机
					</p>
				</div>
			</div>
		</div>
	</section>


	<!--     美贝是什么end-->


	<!--       合作伙伴开始  -->
	<section class="vendor">
		<a name="hezuo"></a>
		<div class="section-hd">
			<h2>合作客户</h2>
		</div>
		<!--       <div class="container">
        <div id="carousel-vendor" class="carousel slide carousel-vendor" data-ride="carousel" data-interval="1000000">
          <div class="carousel-inner" role="listbox">
            <div class="item active">
          
              <div class="carousel-caption carousel-vendor-caption-1">
              
                <a class="right " href="#carousel-vendor" role="button" data-slide="next">
                  <img  style="width:100px; height:127px;"src="${actx}/images/index_new/philips.png" alt="">
                </a>
              </div>
            </div>
            <div class="item">
        
              <div class="carousel-caption carousel-vendor-caption-2">
                
                <a class="left " href="#carousel-vendor" role="button" data-slide="prev">
                  <img  style="width:100px; height:127px;"src="${actx}/images/index_new/philips.png" alt="">
                </a>
              </div>
          
            </div>
          </div>
          </div>
      </div> -->

		<div class="container">
			<div id="carousel-vendor" class="carousel slide carousel-vendor"
				data-ride="carousel" data-interval="1000000">
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img class="" width="467px" height="467px"
							data-lazy-src="${actx}/images/index_new/philips.png" alt="飞利浦">
						<div class="carousel-caption carousel-vendor-caption-1">
							<ul class="vendor-ul">
								<li>飞利浦</li>
								<li>Philips</li>
							</ul>
							<p class="vendor-p">美贝直通车平台通过O2O大数据时代产品销售转化动机
								，让我们更加的方便。对于注重营销的企业来讲，的确是不可多得的平台。感谢美贝直通车平台网络营销做出的努力与贡献！</p>
							<a class="right carousel-control" href="#carousel-vendor"
								role="button" data-slide="next"> <span class="sr-only">Next</span>
							</a>
						</div>
					</div>
					<div class="item">
						<img class="" width="262" height="262"
							data-lazy-src="${actx}/images/index_new/hp.png">
						<div class="carousel-caption carousel-vendor-caption-2">
							<ul class="vendor-ul">
								<li>惠普</li>
								<li>HP</li>
							</ul>
							<p class="vendor-p">生活服务O2O电商品牌在快速成长的同时，也面临着线上用户的黏性不够、线下服务者难管理等问题，美贝直通车平台通过打通移动、联通、电信三网数据流量接口，实现流量营销，增强用户黏性，提升用户活跃度！</p>
							<a class="left carousel-control" href="#carousel-vendor"
								role="button" data-slide="prev"> <span class="sr-only">Prev</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<!-- /#carousel-vendor -->
		</div>

	</section>




	<section class="vendor-others">
		<div class="container">
			<p class="vendor-p-1">
				超过 <span class="number">100</span> 家合作企业
			</p>
			<ul class="vendors-box clearfix">
				<li><a href="javascript:;" class="vendors vendor-wx"></a></li>
				<li><a href="javascript:;" class="vendors vendor-alipay"></a></li>
				<li><a href="javascript:;" class="vendors vendor-360"></a></li>
				<li><a href="javascript:;" class="vendors vendor-58"></a></li>
				<li><a href="javascript:;" class="vendors vendor-qunar"></a></li>
				<li><a href="javascript:;" class="vendors vendor-smartisan"></a>
				</li>
			</ul>
		</div>
	</section>

	<!--       合作伙伴end  -->

	<!--    产品业务-->

	<section class="product">
		<a name="chanpin"></a>
		<div class="container">
			<div class="section-hd">
				<h2>产品业务</h2>
			</div>
			<div class="section-bd row">
			<div class="col-sm-6 col-md-3">
					<div class="thumbnail">

						<div class="content">
							<img style="width: 90%; height: 90%;"
								src="${actx}/images/index_new/hongbao.png" alt="">
						</div>
						<div class="caption">
							<h3>流量红包</h3>
							<p>√多维引流 √指定产品 √消费变现</p>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="thumbnail">

						<div class="content">
							<img style="width: 90%; height: 90%;"
								src="${actx}/images/index_new/cuxiao.png" alt="">
						</div>
						<div class="caption">
							<h3>促销神器</h3>
							<p>√产品促销 √消费黏度 √渠道导流</p>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="thumbnail">

						<div class="content">
							<img style="width: 90%; height: 90%;"
								src="${actx}/images/index_new/wechat.png" alt="">
						</div>
						<div class="caption">
							<h3>微信伴侣</h3>
							<p>√多重广告 √社交媒体 √产品造势</p>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-md-3">
					<div class="thumbnail">

						<div class="content">
							<img style="width: 90%; height: 90%;"
								src="${actx}/images/index_new/guanhai.png" alt="">
						</div>
						<div class="caption">
							<h3>客户关怀</h3>
							<p>√客户维护 √新品直推 √数据搜集</p>
						</div>
					</div>
				</div>
				
				
			</div>
		</div>
	</section>
	<!--    产品业务end-->

	<!--     注册流程  -->

	<section class="intro">
		<div class="container">
			<div class="section-hd">
				<h2>活动流程</h2>
			</div>
			<div class="section-bd row">
				<!-- Indicators -->

				<div class="col-sm-3">
					<div class="intro-caption">
						<div class="item active">
							<h3>三步注册</h3>
							<p>账号-密码-认证</p>
							<c:if test="${username == null || username == ''}">
								<a class="btn btn-primary"
									href="<c:url value='/signup/enterprise.mbay'/>">免费注册</a>
							</c:if>
						</div>


					</div>
				</div>

				<div class="col-sm-8">
					<div class="intro-img">
						<div class="intro-img-box">
							<div class="intro-img-inner">
								<div class="item">
									<img class="loader"
										data-lazy-src="${actx}/images/index_new/intro-1.png"
										alt="三步闪电注册" src="" height="325" width="605">
								</div>


							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		</div>
	</section>
	<!--     注册流程end  -->
	<!--      联系我们-->

	<section class="moreinfo loader" data-lazy-class="bg-moreinfo">
		<a name="contact"></a>
		<div class="container">
			<div class="section-bd row">
				<div class="moreinfo-box clearfix">
					<!--  href="tencent://message/?uin=2985654507&Site"> mod by richard -->
						<li><a target="blank" href="http://wpa.qq.com/msgrd?v=1&amp;uin=2985654507&amp;site=wwww.mbpartner.cn&amp;menu=yes"><span
								class="i-moreinfo"></span><span class="title">腾讯</span><span>QQ</span></a>
						</li>
						<li><a href="tel:021-64186988"><span
								class="i-moreinfo i-contact"></span><span class="title">电话</span><span>021-64186988</span></a>
						</li>
						<li><a href="mailto:cs@mbqianbao.com"><span
								class="i-moreinfo i-control"></span><span class="title">邮箱</span>Mail<span></span></a>
						</li>
						<li><a href="http://j.map.baidu.com/aEtw4" target="_blank"><span
								class="i-moreinfo i-protection"></span><span class="title">地址</span>Address<span></span></a>
						</li>
					</ul>


				</div>
			</div>
		</div>
	</section>

	<!--      联系我们end-->
	<!--     扫二维码   -->

	<section class="sub lazy-box" data-lazy="yes">
		<div class="container">
			<div class="row">
				<div class="col-md-5 sub-img">
					<img class="loader" src=""
						data-lazy-src="${actx}/images/index_new/QR_program.png"
						alt="MB 微信二维码" height="159" width="159">
				</div>

				<div class="col-md-6 sub-text">
					<p>手机扫一扫</p>
					<p>营销全知晓</p>

				</div>
			</div>
		</div>
	</section>
	<!--     扫二维码end   -->


	<script>
    $(function(){
      var $window = $(window)
      var $document = $(document)
      var windowHeight = $(window).height()

      // lazy load
      $window.on('scroll', function(){
        var _scrollTop = $document.scrollTop() + windowHeight + 100
        $('[data-lazy-src]').each(function(){
          var _$this = $(this)
          if(_scrollTop >= _$this.offset().top && !_$this.data('load')){
            _$this.data('load', 'completed')
            _$this.attr('src', _$this.data('lazy-src')).load(function(){
              _$this.removeClass('loader')
            })
          }
        })
        $('[data-lazy-class]').each(function(){
          var _$this = $(this)
          if(_scrollTop >= _$this.offset().top && !_$this.data('load')){
            _$this.data('load', 'completed')
            _$this.addClass( _$this.data('lazy-class'))
          }
        })
      }).scroll();
                                                                                            
      // navbar
      if($document.scrollTop() > 0){
        $('.navbar').addClass('active')
      }
      $window.on('scroll', function(){
        var _scrollTop = $document.scrollTop()
        if(_scrollTop > 0){
          if(!$('.navbar').hasClass('active')){
            $('.navbar').addClass('active')
          }
        } else {
          $('.navbar').removeClass('active')
        }
      })

      // product
      var $product = $('.product');
      var windowHeight = $(window).height()
      $window.on('scroll', function(){
        $product.find('.content').each(function(){
          var $this = $(this)
          var _top = $(this).offset()['top']
          var _height = $(this).height()
          var _scrollTop = $document.scrollTop()
          var _distance = (_scrollTop + windowHeight) - (_top + _height)
          if(_distance >= 400){
            $this.addClass('clip-5')
          }
          if(_distance >= 300){
            $this.addClass('clip-4')
          }
          if(_distance >= 200){
            $this.addClass('clip-3')
          }
          if(_distance >= 100){
            $this.addClass('clip-2')
          }
          if(_distance >= 0){
            $this.addClass('clip-1')
          }
        })
      })

      // achievement
      $('[data-toggle="runner"]').each(function(){
        var init = 6
        $(this).find('.number-box').each(function(index){
          var $this = $(this)
          var number = $this.data('number')
          if(index == 0){
            var arr = []
            for(var i=0; i<number; i++){
              arr.push('<span>'+i+'</span>')
            }
          } else {
            var arr = ['<span>0</span>']
            for(var i=0; i<init; i++){
              arr.push('<span>'+parseInt(Math.random() * 10, 10)+'</span>')
            }
          }
          arr.push('<span>'+number+'</span>')
          $this.find('.number-inner').html(arr.join(''))
          init += 10
        })
      })
      $window.on('scroll', function(){
        var $achievement = $('.achievement')
        if($document.scrollTop() + windowHeight >= $achievement.offset().top + $achievement.height()){
          $('.number-box').each(function(){
            var dataNumber = $(this).data('number');
            var $inner = $(this).find('.number-inner')
            $inner.animate({
              'top': -$inner.height() + 55
            }, 3000, "easeInOutQuint")
          })
        }
      }).scroll();

      // intro
      var introIndex = 0
      var CAPTIONHEIGHT = 150
      var IMGWIDTH = 605  
      var showNote = true
      var introTimer = null
      var $intro = $('.intro')
      var $introIndicators = $('.intro-indicators').find('li')
      var $introCaptions = $('.intro-caption').find('.item')
      $introIndicators.on('click', function(e){
        var $this = $(this)
        introIndex = $this.data('slide')
        if(showNote){
          var _introIndex = introIndex +1
          if(_introIndex >= $introIndicators.length || introIndex-1 != $('.intro-indicators').find('.active').data('slide') && introIndex-1 >= 0){
            $introIndicators.removeClass('animate')
            showNote = false
          } else {
            $introIndicators.removeClass('animate')
            $introIndicators.eq(_introIndex).addClass('animate')
            introTimer = setTimeout(function(){
              $introIndicators.eq(_introIndex).removeClass('animate')
            }, 6200)
          }
        }
        $introIndicators.removeClass('active')
        $this.addClass('active')
        $('.intro-img-inner').animate({'left': -IMGWIDTH * introIndex})
        $introCaptions.removeClass('active').eq(introIndex).addClass('active')
      })

      var introScroll = true
      $window.on('scroll', function(){
        if($document.scrollTop() + windowHeight >= $intro.offset().top + $intro.height() && showNote && introScroll){
          $introIndicators.eq(0).click()
          introScroll = false
        }

      })

    })
    </script>
	<!-- <script type="text/javascript">
    document.write(unescape("%3Cscript src='https://s.meiqia.com/js/mechat.js?unitid=4166&btn=hide' type='text/javascript'%3E%3C/script%3E"));
    </script><script src="index_files/mechat.js" type="text/javascript"></script>  	  	&lt;!&ndash;body_end&ndash;&gt;
-->
	<%@include file="/common/footer.jsp"%>


</body>
</html>