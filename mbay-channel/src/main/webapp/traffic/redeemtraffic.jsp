<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>美贝流量兑换</title>
<t:assets/>
<link href="${actx}/wro/${version}/redeemtraffic.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript"	src="<c:url value="/js/traffic/redeemtraffic.js?v=1.6" />"></script>
<script type="text/javascript"	src="<c:url value="/js/lib/Validform_v5.3.2_min.js?v=1.3" />"></script>
<script type="text/javascript">
</script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix'>          
        <!--左边-->        
             <div class='left_con'>                
                  <div class='ddqr'>流量柜台<div id='aaa'></div></div>
                   <%@ include file="/common/leftcon.jsp" %> 
                <div class='dd fr'>
                 <form id='myForm' action="<c:url value="/traffic/charge/order_create.mbay"/>" method="post" >
                 <m:token/>
                 <input type="hidden" id="availableAmount" value="${availableAmount}"  >
                   <div class='czsl'>
                   <span class='czll'><b>*</b>充值号码：</span>
                   <input type='text' class='sl_1 onlynum'   id='chargemobile' autocomplete="off" maxlength="11" placeholder='请输入号码'  name="mobile" class='hdmc' datatype="sj" errormsg="输入格式不正确！"/>
                   <span class="Validform_checktip"></span>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>归属地：</span >
                   
                   <span class='queren' id="messagenot"></span>
                   <span class='je'  id='attribution'></span>
                   </div>                                    
                   <div class='xz'>
                   <span class='czll'><b>*</b>流量类型：</span>
                      <span id="traffictypeSTATE" class="ttype">
                      <input type='radio' checked="checked"   name="traffictype" value="STATE" class='hdlb' id='mb_2'/>
                      <label for='mb_2' class='ll'>全国</label>
                      </span>
                      <span id="traffictypePROVINCE"  class="ttype">
                      <input  type='radio'   name="traffictype" value="PROVINCE" class='hdlb'  id='mb_1' datatype="*" errormsg="请选择类别！" />
                      <label for='mb_1'>省内</label>
                      </span>
                      <span class="Validform_checktip"></span>
                   </div>
                   <div class='czl' style='height:auto'>
                   <span class='czll' style='display: inline-block;'><b>*</b>充值流量：</span>
                   <span  id="trafficpackage" style='display: inline-block;width:350px;vertical-align: top;'></span>                                
                   <input type="hidden"  datatype="n" nullmsg="请选择流量包！" name="packageid" id="tpkid"/>
                   </div>
                   <div class='yfje'>
                   <span class='czll'>销售价格：</span>
                   <span class='jg' id="packageprice"></span>
                   <span class='je'>美贝</span>
                   </div>
                   <div class='an'>
                   <input type="submit"  id="subForm"class='an_btn' value='立即充值'/>
                   </div>
                 </form>
                </div>  
            </div>
</div>
</div>
 </div>
</body>
</html>
