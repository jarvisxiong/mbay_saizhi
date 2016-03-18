<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <meta name="keywords" content="美贝直通车"/>
    <meta name="description" content="美贝直通车"/>
    <title>流量领取说明-美贝直通车</title>
    <t:assets/>
    <link rel="stylesheet" href="${actx}/css/mobile/share.css">
    <style>
        html,body,.wrap{position:relative;height:100%;width:100%;background:#FCEDAE;}
        body{font-size:12px;}
        .head{height:100px;background:#004C66;}
        .head h1{color:#FDFDFE;font-size:20px;text-align:center;line-height:100px;}
        .nav{width:100%;height:37px;background:#FDAB1B; -webkit-column-count:3;-moz-column-count:3;column-count:3;}
        .nav a{display:block;color:#FDFDFE;font-size:18px;line-height:37px;text-align:center;background:#FDAB1B;}
        .nav .nav-cur{color:#FDAB1B;background:#FCEDAE;}
        .tab{background:#FCEDAE;}
        .tab_con{overflow:hidden;padding:5px 15px 0 15px;}
        .tab_con_item{display:none;}
        .tab_con_item:first-child{display:block;}
        .tab_con_item .tab_title{font-size:14px;font-weight:bold}
        .tab_con_item p{margin:10px 0}
        .footer{position:fixed;bottom:20px;left:0;width:100%;text-align:center;border-top:1px solid #000;padding-top:8px;}
    </style>
</head>

<body>
  <div class="wrap">
    <header class="head">
       <h1>免费流量领取说明</h1>
    </header>
    <nav class="nav">
        <a href="javascript:;" class="nav-cur">电信</a>
        <a href="javascript:;">移动</a>
        <a href="javascript:;">联通</a>
    </nav>
    <div class="tab">
       <div class='tab_con'>
          <!--电信说明-->
           <div class="tab_con_item">
              <p class="tab_title">电信用户:</p>
              <p>1).电信用户参与活动所获赠流量即时生效，有效期为30天（默认优先使用本次活动赠送流量）;</p>
              <p>2).获赠流量详情可以在参与活动一个工作日后通过电信客服系统进行查询，方式包括：致电电信官方客服电话10000；或通过电信网上营业厅<a target='_blank' href='http://www.189.cn/'>http://www.189.cn/</a>,或发送短信“108”至10000等方式进行;</p>
              <p>3).如果您没有获取到免费流量，可能存在以下原因：</p>
              <p>&nbsp;&nbsp;&nbsp;&nbsp;A、您的手机号码处于欠费状态</p>
              <p>&nbsp;&nbsp;&nbsp;&nbsp;B、您的手机套餐不支持此免费流量</p>
           </div>
           <!--移动说明-->
           <div class="tab_con_item">
               <p class="tab_title">移动用户:</p>
               <p>1).移动用户参与活动所获赠流量会在一个工作日内生效，当月有效（默认优先使用本次活动赠送流量）;</p>           
               <p>2).获赠流量详情可以在参与活动一个工作日后通过移动客服系统进行查询，方式包括：致电移动官方客服电话1008611；或通过移动网上营业厅<a target='_blank' href="http://www.10086.cn/">http://www.10086.cn/</a>,或发送短信“1091”至10086等方式进行;</p>
               <p>3).如果您没有获取到免费流量，可能存在以下原因：</p>
			   <p>&nbsp;&nbsp;&nbsp;&nbsp;A、您的手机号码处于欠费状态</p>
			   <p>&nbsp;&nbsp;&nbsp;&nbsp;B、您的手机套餐不支持此免费流量</p>
          </div>
           <!--联通说明-->
           <div class="tab_con_item">
              <p class="tab_title">联通用户:</p>
              <p>1).联通用户参与活动所获赠流量即时生效，当月有效（默认优先使用兑换的流量）;</p>
              <p>2).获赠流量详情可以在参与活动一个工作日后通过联通客服系统进行查询，方式包括：致电联通官方客服电话10010；通过联通网上营业厅<a target='_blank' href='http://www.10010.com'>http://www.10010.com</a>,或发送短信“CXLL”至10010等方式进行;</p>
              <p>3).如果您没有获取到免费流量，可能存在以下原因：</p>
			  <p>&nbsp;&nbsp;&nbsp;&nbsp;A、您的手机号码处于欠费状态</p>
              <p>&nbsp;&nbsp;&nbsp;&nbsp;B、您的手机套餐不支持此免费流量</p>
          </div>
       </div>
    </div>
    <div class="footer">本次活动最终解释权归赛志科技“美贝钱包”所有，如有疑问请拨打“美贝钱包”咨询热线021-80175321进行咨询</div>
  </div>
</body>
</html>
<script>
   $(function(){
       $(".nav").on("touchend","a",function(){   // 给当然被触摸的a标签添加touchend 事件   > 事件委托
           var _index = $(this).index();  // 取当前被点击的
           $(this).addClass("nav-cur").siblings().removeClass("nav-cur");  // 让当前被点击的添加class 并让被点击的同辈元素去除class
           $(".tab_con").find(".tab_con_item").eq(_index).show().siblings(".tab_con_item").hide();  //让tab_con下面的第_index个显示  其他同辈元素隐藏掉
       });
   });
</script>