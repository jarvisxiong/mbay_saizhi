<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告列表</title>
<link href="${actx}/wro/${version}/mbayDepositOrderRecord.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"  src="${actx}/js/my97/WdatePicker.js"></script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->  
             <div class='left_con fl'>          
             <form id="pagerForm" action="<c:url value="/purchase/sMSPurchaseOrderRecord.mbay"/>" method="post">
                <div class='hdlb'>短信购买订单列表</div>
                <!--身体中部左侧-->
                <%@ include file="/common/leftcon.jsp" %>
                <!--身体中部右侧-->
                <div class='hd_con_r fr'>
                   <!--表格-->
                  <div class='lb'>
                   <div class='hd'>
                      <span class='hdbh'>订单号：</span>
                      <input type='text' name="depositNumber" class='bh' value="${sMSPurchaseOrderFormCriteria.depositNumber}"/>
                      <span class='hdmc'>查询日期：</span>
                       <img src="<c:url value='/images/workimages/rq.jpg'/>"   class='hdrq'  onclick="WdatePicker()"/>
                      <input id="rq_1" name="starttime" class="Wdate" value='${sMSPurchaseOrderFormCriteria.starttime}' type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
                      <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
                      <input id="rq_2" name="endtime" value='${sMSPurchaseOrderFormCriteria.endtime}' class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>  
                      <input value='查询' type='submit' class='cx'/>
                   </div>
                  </div>
                  <div class='table'>
                       <table >
                        <tr>
                        <td>订单号</td>
                        <td>短信数量</td>
                        <td>美贝价格</td>
                        <td>创建时间</td>
                        <td>状态</td>
                    </tr>
                    <c:forEach items="${sMSPurchaseOrderList}" var="sMSPurchaseOrder">
                        <tr id="notice${sMSPurchaseOrder.id}">
                            <td>${sMSPurchaseOrder.orderId}</td>
                            <td>${sMSPurchaseOrder.smsAmount}</td>
                            <td><fmt:formatNumber value="${sMSPurchaseOrder.mbayAmount}" type="currency" pattern=".00"/></td>
                            <td><fmt:formatDate value="${sMSPurchaseOrder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                              ${sMSPurchaseOrder.state.value}
                            </td>
                        </tr>
                    </c:forEach>
                       </table>
                    </div>
                     <m:page pageinfo="${pageinfo}"/>
                </div>
           </form>
         </div>
       </div>
       <!--尾部--> 
     </div>
   </div>
</body>
</html>