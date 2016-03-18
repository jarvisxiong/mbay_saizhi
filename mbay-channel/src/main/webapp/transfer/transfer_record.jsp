<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>转账明细</title>
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"  src="${actx}/js/my97/WdatePicker.js"></script>
<style>
table{table-layout:fixed;}
tbody>tr>td{margin:0;padding:0;}
.sub_string{/* text-overflow: ellipsis; */overflow: hidden;white-space: nowrap;margin-bottom:0px;word-break: break-all; margin:0; padding:0;}
.sub_stringtr{}
</style>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->  
             <div class='left_con '>                 
             <form id="pagerForm" action="<c:url value="/record/transfer_record.mbay"/>" method="post">      
                <div class='hdlb'>转账记录</div>
                <!--身体中部左侧-->
                <%@ include file="/common/leftcon.jsp" %>                
                <!--身体中部右侧-->
                <div class='hd_con_r fr'>
	               <!--表格-->
	              <div class='lb'>
                   <div class='hd'>
                      <span class='hdbh'>查询流水号：</span>
                      <input type='text' name="ordernumber" value="${orderForm.orderNumber}" class='bh'/>
                     <!--   <span class='hdbh'>收入/支出：</span>
                       <select class='sel' name="">									
							<option >支出</option>
							<option value="">收入</option>									
					  </select>   -->              
                      <span class='hdmc'>查询日期：</span>                      
                      <img src="<c:url value='/images/workimages/rq.jpg'/>"   class='hdrq'  onclick="WdatePicker()"/>
                      <input id="rq_1" name="starttime" class="Wdate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${detail.startTime.toDate()}"/>' type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
                      <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
                      <input id="rq_2" name="endtime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${detail.endTime.toDate()}"/>' class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>  
                      <input value='查询' type='submit' class='cx btn_confirm'/>
                   </div>
                  </div>
	                <div class='table'>
	                   <table >
	                    <tr>						
	                    <td style="width:170px;">订单号|日期</td>
	                    <td style="width:100px;">收款公司名称</td>
	                    <td style="width:100px;">交易金额</td>	                   
	                    <td style="width:100px;">赠送(美贝)</td>
	                    <td style="width:100px;">收款账户</td>           
	                    <td style="width:100px;">转账说明</td>   
	                              
	                </tr>  
	                <c:forEach items="${transferOrders}" var="detail">
	                    <tr>
	                        <td>${detail.orderNumber}<br><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${detail.createTime.toDate()}"/></td>
	                        <td>${detail.toUser.name}</td>
	                        <td>${detail.payAmount}</td>
	                        <td>${detail.sendAmount}</td>
	                      <!--   <td>支出</td>	     -->                    
	                       <%--  <td><fmt:formatNumber value="${detail.balance}" maxFractionDigits="2" minFractionDigits="2"/></td> --%>           
	                         <td>${detail.toUser.usernumber}</td>
	                        <td class="sub_stringtr">
	                        	<div class="sub_string">
									<span title="${detail.transferNote}">${detail.transferNote}</span>
								</div>
	                        </td>   
	                                     
	                    </tr>
	                </c:forEach>                   
	                   </table>
	                </div>
	                 <m:page pageinfo="${pageinfo}" formid="pagerForm" />
                </div>            
              </form>
             </div>           
             </div>
  <!--尾部--> 

</div>
</div>
</body>
</html>