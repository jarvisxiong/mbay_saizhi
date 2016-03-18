<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销明细</title> 
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
 <script type="text/javascript"	src="${actx}/js/my97/WdatePicker.js"></script>
 <style type="text/css">  
  .hdrq{display: inline-block;vertical-align:middle;margin-right: 2px;vertical-align: text-top;}
  #rq_1,#rq_2{border:1px solid #94ABC4;width:68px;height:26px;background: #fff;border-radius: 0px;color:#000;padding-left:5px;vertical-align: baseline;}
  </style>  
</head>
<body>
 <div class='con'>
    <!--身体-->
       <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->  
             <div>        
              <form id="pagerForm" action="" method="post">        
                <div class='hdlb'>营销明细</div>
                <%@ include file="/common/leftcon.jsp" %>
                
                <div class='fr' style='width:828px'>
	                <div class='lb'>
	                  <div class='hd'>
	                    <div style='margin-bottom:20px'>
		                  <span class='hdbh'>活动类型：</span>
		                  <select  id="orderType" name="orderType" class='bh'> 
		                  <option value=""></option>
		                  <option value="DIRECT_RECHARGE">流量柜台</option>
		                  <option value="TRAFFIC_RED">流量红包</option>
		                  <option value="PROMOTION_CAMPAIGN">促销神器</option>
		                  <option value="WECHAT_CAMPAIGN">微信伴侣</option>
		                  <option value="APP_CAMPAIGN">APP诱惑</option>
		                  <option value="CUSTOMER_SERVER">客户关怀</option>
		                  </select>
		                  <span class='hdbh'>活动编号：</span><input type='text' name="eventnumber" value="${orderform.relateNumber}" class='bh'/> 
		                    
                        </div>
                        <div style='margin-bottom:20px'>                           
	                      <span class='hdmc'>订单编号：</span><input type='text' name="number"  value='${orderform.orderNumber}'  class='mc'/>	   
	                      <span class='hdmc'>手机号码：</span><input type='text' name="mobile"  value='${orderform.mobile}'  class='mc'/>	   
	                      
	                    </div>
	                    <div>
	                    <span class='hdmc'>查询日期：</span>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>"   class='hdrq'  onclick="WdatePicker()"/>
	                      <input id="rq_1" name="starttime" class="Wdate" value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${orderform.startTime.toDate()}"/>' type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
	                      <input id="rq_2" name="endtime" value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${orderform.endTime.toDate()}"/>' class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>
	                      <input type="submit" class='hd_btn formSearch' value='查询'/>
	                    </div>
	                   </div>
	                </div>
                <!--表格-->
	                <div class='table'>
	                   <table >
		                    <tr>
			                    <td width="20%">订单号|充值日期</td>
			                    <td width="26%">订单名称</td>
			                    <td width="10%">活动编号</td>
			                    <td width="10%">手机号</td>
			                    <td width="7%">流量</td>
			                    <td width="7%">美贝</td>                                  
			                    <td width="10%">状态</td>
			                    <td width="10%">操作</td>                 
			                </tr>
			                <c:forEach items="${trafficcharges}" var="order">
			                    <tr id="strategytr${order.number}">
			                        <td>${order.number}<br/><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${order.createTime.toDate()}"/></td>
			                        <td style="text-align: left;padding-left: 3px;">${order.orderName}</td>
			                        <td>${order.relateNumber}</td>                      
			                        <td>${order.mobile}</td>
			                        <td>${order.trafficPackage.traffic}M</td>
			                        <td>${order.mbayPrice}</td>            
			                        <td>${order.status.value}<br/></td> 
			                        <td>
			                        </td>
			                    </tr>
			                </c:forEach>        
	                   </table>  
	                   <div><a href="javascript:void(0)" class="download">下载查询结果</a></div>                
	                </div>
                <m:page pageinfo="${pageinfo}"/>
                </div>
                   </form>                   
               </div>       
            </div>
 </div>
  <!--尾部-->
  <script type="text/javascript">
$(function(){
	//活动类型选中
	$("#orderType").val("${orderform.orderType}");
	$(".formSearch").click(function(){
		$("#pagerForm").attr("action",'<c:url value="/traffic/record.mbay"/>');
		$("#pagerForm").submit();
	});
	$(".download").click(function(){
		$("#pagerForm").attr("action",'<c:url value="/traffic/record/download.mbay"/>');
		$("#pagerForm").submit();
	});
});

</script>
 
</div>
</body>

</html>
