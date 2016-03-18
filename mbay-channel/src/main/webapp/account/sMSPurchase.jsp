<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流量充值</title>
<t:assets/>
<link href="${actx}/wro/${version}/depositPreprocess.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"  src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript"	src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript">
var pakages = "";
var validator_result = false;
$(document).ready(function () {
    $.get(
         "sMSPackage.mbay",
         {},
         function(data) {
           var selector = $(".js-package-select");
           pakages = data;
           $.each(data, function(index, item) {
        	   selector.append('<option value="' + item.id + '">' + item.smsAmount + '</option>');
           });
         },
         "json"
    );
    $(".js-package-select").change(function() {
      $(".js-mbay-amount").html("");
      if (!(/^[0-9]*$/g.test($(".js-package-select option:selected").html()))) {
        $(".message").removeClass("Validform_right");
        $(".message").html("请选择短信数量").addClass("Validform_wro/${version}ng");
        validator_result = false;
      } else {
        var id = $(".js-package-select option:selected").val();
        $.each(pakages, function(index, item) {
          if(item.id == id) {
            $(".js-mbay-amount").html(item.mbayAmount);
          }
        });
        $(".message").removeClass("Validform_wro/${version}ng");
        $(".message").html("校验通过").addClass("Validform_right");
        validator_result = true;
      }
    });

    $("#subForm").bind("click",function(){
        $("#llcz").submit();
        ////alert('请点击右侧客服中心联系客服人员充值！'); 
    });
      $('input[name=mbay]').keyup(function () {
          calculatePrice();
      });

      /*验证*/
      $("#llcz").Validform({
            showAllError:true,
            tiptype:3,
            datatype:{
                "sz" : /^[0-9]+$/
            }
        });     
        
  
    function calculatePrice(){
        var num=$("#chargenum").val();
        if(!isNaN(num)){
            num=(num*0.2).toFixed(2);
            $("#price").html(num+"元");
            
        }
        
    }
    
    $(".js-sms-buy").on("click", function() {
      $(".js-package-select option:selected").change();
      if (validator_result) {
        window.location.href = "operateSMSPurchaseOrder.mbay?smsAmount=" + $(".js-package-select option:selected").html()
        + "&mbayAmount=" + $(".js-mbay-amount").html();
      }
    });
});
</script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
 <!--左边-->
   <div class='left_con com_width'>
         <div class='ddqr'>短信购买</div>
          <%@ include file="/common/leftcon.jsp" %> 
         <div class='b_con com_width clearfix' >
         <div class='dd fr'>
          <form id='llcz'>
           <m:token/>
           <div>
              <span class='czll'>现有短信条数：</span>
              <span class="current-quantity">${smsCount}</span>
              <span class="measure">条</span>
           </div>
            <div class='czsl'>
              <span class='czll'>
                <b>*</b>购买短信条数：
              </span>
	          <select id='chargenum' class='js-package-select purchase-select-show'  name='smsAmount'>
	            <option>请选择</option>
	          </select>
	          <span class="measure">条</span>
	          <span class="message"></span>
            </div>
            <div class='yfje'>
              <span class='czll'>应付金额：</span>
              <span class='je js-mbay-amount' id="price" name="mbayAmount"></span>
              <span class='je'>美贝</span><span class="Validform_checktip"></span>
            </div>
            <div class='an'><input type="button" class='an_btn js-sms-buy' value='购买'/>
            </div>
          </form>
         </div>
       </div>
</div>
</div>
 </div>
</body>
</html>
