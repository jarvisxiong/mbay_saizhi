<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝充值</title>
<t:assets/>
<link href="${actx}/wro/${version}/account_recharge_pay.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/account_recharge_pay.js"></script>
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
 <script type="text/javascript" src="${actx}/js/lib/easydialog.js"></script>
<script type="text/javascript"  src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript"	src="${actx}/js/lib/Validform_v5.3.2_min.js"></script> 
<script>
var btnNoFn = function() {
  easyDialog.close();
  return false;
};
var btnFn = function() {
 /// window.location.href="../channel/index.mbay";
  easyDialog.close();
  return false;
};

$(function(){
  $(".ljzf").click(function() {
    $("#pay").submit();
    easyDialog.open({
      container : {
        header : '支付',
        content : '请您在新打开的网上银行页面进行支付，支付完成前请不要关闭该窗口。',
        yesFn : btnFn,
        yesText: "已完成支付",
        noFn : btnNoFn,
        noText: "重新选择银行"
      },
      drag : false
    });
  });
  
  $(".hkjl").click(function() {
	  var demo = $("#acount").Validform({
		  tiptype:function(msg,o,cssctl){
			  var value = o.type;
			  if(value == 1 || value == 3){
				easyDialog.open({
			      container : {
			        content : msg
			      },
			      autoClose : 1000
			    });
			  }
			}
	  });
	  if(demo.check()){
		  $("#acount").submit();
	  }
  });
});

function check(){
	//收款银行
	$(".on_0").click();
	//汇款金额
	var zs = $("#price_z").val();
	var xs = $("#price_x").val();
	$("#price").val(zs+"."+xs);
	//备注
	$("#description").val($("#desc_area").val());
}

function getBankId(id){
	$("#bankId").val(id);
}
</script>
</head>
<body>
 <div class='con'>

 <div class='body clearfix'>          
        <!--左边-->               
          <div class='left_con com_width clearfix'>               
                <div class='ddqr'>美贝充值</div>
                 <%@ include file="/common/leftcon.jsp" %> 
                
                <!--右部付款-->
		  <div class='r_sec fr'>
		
		    <div class='r_sec_1 clearfix'> 
		      <div class='fl'>
		        <span class='xk_0'>账户余额:</span><span class='xk_1'>${accountAmount}</span><b>美贝</b>
		        <span class='xk_0 yd_0'>充值美贝:</span><span class='xk_1'>${depositorder.mbayQuantity }</span><b>美贝</b>
		        <span class='xk_0 yd_0'>应付金额:</span><span class='xk_1'>${depositorder.price }</span><b>元</b>
		      </div>
		      <div class='fr'>
		        <a href='<c:url value='/record/mbayDepositOrderRecord.mbay?pagenum=1'/>' style='color: #FDAB1B;margin-left:5px'>充值记录</a>
		      </div>
		    </div>
		<!--选择方式-->
		   <div class='r_sec_2 sel_tit'><h4><a href='javascript:void(0)'>在线支付</a> | <a href='javascript:void(0)'>汇款转账</a></h4></div>
		<!--汇款转账-->
		 <form id='acount' action="<c:url value="/account/remittanceRecord.mbay"/>" method="post">
		    <div class='acount_tip'>
		       <h4>填写汇款通知单</h4>
		       <p>1、向含有“（自助加款）”字样的收款银行汇款时，需先在网页提交汇款请求，再前往银行柜台，ATM或者网银汇款。</p>
		       <p>2、向不含有“（自助加款）”字样的收款银行汇款时，请先前往银行柜台，ATM或者网银汇款后再提交汇款请求填写，以通知公司及时帮您加款。</p>
		       <div><a href="<c:url value='/account/remittanceRecord/list.mbay'/>" class="button">查看汇款记录</a></div>
		    </div>
		    <div class='acount_bank'>
		       <h4>收款银行</h4>
		       <c:forEach items="${bankList}" var="bank" varStatus="vs">
		       	<p>
		       		<span>
		       			<span class="blue-radio"><a onclick="getBankId('${bank.id}')" <c:if test="${vs.index == 0}">class='on_0'</c:if>></a></span>
		       			<span class='acount_bank_name'>${bank.bankName}</span>
		       			<span class='acount_bank_com'>${bank.companyName}</span>
		       			<span class='acount_bank_num'>${bank.account}</span>
		       		</span>	
		       		<span class='acount_bank_nr2' title='${bank.description}'>${bank.description.substring(0,10)}...</span>
		       	</p>
		       </c:forEach>
		    </div>
		    <div class='acount_det'>
		      <div class='acount_det_con'>
		         <div><span class='acount_det_lab'>汇款金额：</span><span><input type='text' id="price_z" name="price_z" class='acount_txt_0' autocomplete="off" datatype="n" nullmsg="请输入汇款金额整数部分!"/> . <input type='text' id="price_x" name="price_x" class='acount_txt_1' autocomplete="off" datatype="n2-2" nullmsg="请输入汇款金额小数部分!" errormsg="小数部分最多2位"/>元</span><span>（建议使用商户编号末两位作为零头，以提高加款即时性和准确性）</span></div>
		         <div><span class='acount_det_lab'>汇款时间：</span><input type='text' id="time" name="time" class='acount_txt_2' autocomplete="off" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" nullmsg="请输入汇款时间!"/><img src="<c:url value='/images/workimages/rq.jpg'/>" style="height: 33px;margin-left: 5px;margin-bottom: 5px;"/></div>
		          <div><span class='acount_det_lab'>备注：</span><textarea id="desc_area" class='acount_txt_3' datatype="*1-400" errormsg="备注内容过长!" nullmsg="请输入备注信息!"/></textarea></div>
		      </div>
		    </div>
		    <div  class='acount_button'>
		    	<input type='text' id="bankId" name="bankId" style="display:none;"/>
		    	<input type='text' id="price" name="price" style="display:none;"/>
		    	<input type='text' id="description" name="description" style="display:none;"/>
		    	<button class="hkjl" onclick="check()">立即支付</button>
		    </div>
		 </form>
		<!--在线支付-->
		 <form action="<c:url value="/account/depositCashier.mbay"/>" target="_blank" id="pay" >
		 <input type="hidden"  name="orderNumber" value="${depositorder.depositNumber}" >
		 <input type="hidden"  name="payMethod" value="directPay" >
		 <input type="hidden"  name="platform" value="alipay" >
		 <input type="hidden"  name="bankCode" value="" >
		  <!--第二栏-->
		    <div class='r_sec_2'>
		        <h3>支付平台</h3> 
		    </div>
		  <!--第三栏-->
		    <div class='r_sec_3 clearfix'>
		       <ul>
		          <li payMethod="directPay" platform="alipay" style='border:1px solid #b7b7b7'>
		             <span class="blue-radio"><a class='on_0'></a></span>
		             <img src="<c:url value="/images/online/alipay.png" /> "  class='fl'/>
		          </li>
		          <li payMethod="directPay" platform="baifubao" style='border:1px solid #b7b7b7'>
		             <span class="blue-radio"><a></a></span>
		             <img src="<c:url value="/images/online/baifubao.png" /> "  class='fl'/>
		          </li>
		       </ul>
		    </div>
		  <!--第四栏-->
		    <div class='r_sec_2'>
		        <h4>银行支付</h4> 
		    </div>
		  <!--第五栏-->
		    <div class='r_sec_4 clearfix'>
		       <ul>
		          <li bankcode='CCB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/jsyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='ICBCB2C'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/gsyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='CMB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/zsyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='COMM'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/jtyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='SPDB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/pfyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='ABC'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/nyyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='CEBBANK'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/gdyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='GDB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/gfyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='CMBC'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/msyh.png" />'  class='fl'/>
		          </li>
		          <li bankcode='CIB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/xyyh.png" />' class='fl'/>
		          </li>
		          <li bankcode='SHBANK'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/shyh.png" />' class='fl'/>
		          </li>
		           <li bankcode='BJRCB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/bjyh.png" />' class='fl'/>
		          </li>
		          <li bankcode='SHRCB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/ncsyyh.png" />' class='fl'/>
		          </li>
		          <li bankcode="SPABANK">
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/hxyh.png" />' class='fl'/>
		          </li>
		          <li bankcode="SPABANK">
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/pnyh.png" />' class='fl'/>
		          </li>
		          <li bankcode="PSBC-DEBIT">
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/yzcx.png" />' class='fl'/>
		          </li>
		          <li bankcode="BOCB2C">
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/zgyh.png" />' class='fl'/>
		          </li>
		          <li bankcode="CITIC">
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/zxyh.png" />' class='fl'/>
		          </li>
		          <li bankcode='ICBCBTB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/gsyh.png" />'  class='fl'/>
		             <div class='enterp'>企业</div>
		          </li>
		           <li bankcode='CCBBTB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/jsyh.png" />'  class='fl'/>
		             <div class='enterp'>企业</div>
		          </li>
		          <li bankcode='ABCBTB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/nyyh.png" />'  class='fl'/>
		             <div class='enterp'>企业</div>
		          </li>
		          <%-- <li bankcode='CMB'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/zsyh.png" />'  class='fl'/>
		             <div class='enterp'>企业</div>
		          </li> --%>
		          <li bankcode='SPDBB2B'>
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/pfyh.png" />'  class='fl'/>
		             <div class='enterp'>企业</div>
		          </li>
<%-- 		          <li bankcode="BOCB2C">
		             <span class="blue-radio"><a></a></span>
		             <img src='<c:url value="/images/online/zgyh.png" />' class='fl'/>
		             <div class='enterp'>企业</div>
		          </li> --%>
		       </ul>
		    </div>
		    <!--按钮-->
		    <div style='text-align:right;margin-bottom:30px'>
		      <input type='button'  value='立即支付' class='ljzf'/>
		    </div>
		    </form>
		  </div>
              </div>
         </div>
</div>
</body>
</html>
