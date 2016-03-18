<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批充明细</title> 
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript"	src="${actx}/js/my97/WdatePicker.js"></script>
<style type="text/css">  
 .hdrq{display: inline-block;vertical-align:middle;margin-right: 2px;vertical-align: text-top;}
 #rq_1,#rq_2{border:1px solid #94ABC4;width:65px;height:26px;background: #fff;border-radius: 0px;color:#000;padding-left:5px;vertical-align: baseline;}
</style>  
</head>
<body>
 <div class='con'>
    <!--身体-->
       <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->  
             <div>        
              <form id="pagerForm" action="<c:url value="/traffic/recordByBatchChargeItem.mbay"/>" method="post">        
                <input type="text" id="itemid" name="itemid" value="${itemid}" style="display:none;"/>
                <div class='hdlb'>批充明细</div>
                <%@ include file="/common/leftcon.jsp" %>
                
                <div class='fr' style='width:828px'>
	                <div class='lb'>
	                  <div class='hd'>
	                  	<div style='margin-bottom:20px'>                           
	                      <span class='hdmc'>订单编号：</span><input type='text' name="number"  value='${orderform.orderNumber}'  class='mc'/>	   
	                      <span class='hdmc'>手机号码：</span><input type='text' name="mobile"  value='${orderform.mobile}'  class='mc'/>	   
	                    </div>
	                    <div>
		                  <%-- <span class='hdbh'>活动编号：</span><input type='text' name="eventnumber" value="${orderform.eventnumber}" class='bh'/>  --%>
		                  <span class='hdmc'>查询日期：</span>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>"   class='hdrq'  onclick="WdatePicker()"/>
	                      <input id="rq_1" name="starttime" class="Wdate" value='${orderform.startTime}' type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
	                      <input id="rq_2" name="endtime" value='${orderform.endTime}' class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>  
                          <input type="submit" class='hd_btn' value='查询' style="margin-left:50px;"/>
                        </div>
	                   </div>
	                </div>
                <!--表格-->
	                <div class='table'>
	                   <table >
	                    <tr>
	                    <td>订单号</td>
	                    <td>订单名称</td>
	                    <!-- <td>活动编号</td> -->
	                    <td>手机号</td>
	                    <td>流量</td>
	                    <td>美贝价格</td>                  
	                    <td>充值日期</td>                  
	                    <td>状态</td>
	                    <!-- <td>操作</td> -->                 
	                </tr>
	                <c:forEach items="${trafficcharges}" var="order">
	                    <tr id="strategytr${order.number}">
	                        <td>${order.number}</td>
	                        <td>${order.orderName}</td>
	                        <%-- <td>${order.eventnumber}</td> --%>                   
	                        <td>${order.mobile}</td>
	                        <td>${order.trafficPackage.traffic}M</td>
	                        <td>${order.mbayPrice}</td>            
	                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${order.createTime.toDate()}"/></td>           
	                        <td>${order.status.value}<br/></td> 
	                        <!-- <td></td> -->
	                    </tr>
	                </c:forEach>        
	                   </table>                  
	                </div>
                <m:page pageinfo="${pageinfo}"/>
                </div>
                   </form>                   
               </div>       
            </div>
 </div>
  <!--尾部-->
 
</div>
</body>
</html>
