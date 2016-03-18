<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝充值</title>
<t:assets/>
<link href="${actx}/wro/${version}/depositPreprocess.css"	rel="stylesheet" type="text/css" />
<link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
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
});
</script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>  
        <!--左边-->               
        <div class='left_con com_width'>               
                <div class='ddqr'>美贝充值</div>
                 <%@ include file="/common/leftcon.jsp" %> 
                <div class='b_con com_width clearfix' >
                <div class='dd fr'>
                 <form id='llcz'   action="<c:url value="/account/deposit.mbay"/>" method="post">
                  <m:token/>
                   <div class='czsl'><span class='czll'><b>*</b>充值数量：</span>
                   <input type='text' id='chargenum' class='sl_1 onlynum' placeholder='请输入数量' maxlength="7" class='hdmc' datatype="sz" name="mbay" errormsg="输入格式不正确！"/>
                   <span class='je'>美贝</span><span class="Validform_checktip"></span>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>应付金额：</span>
                   <span class='je' id="price"></span>
                   </div>
                   <div class='an'><input id="subForm" type="submit" class='an_btn' value='充值'/>
                   </div>
                 </form>
                </div>
          </div>
</div>
</div>
 </div>
</body>
</html>
