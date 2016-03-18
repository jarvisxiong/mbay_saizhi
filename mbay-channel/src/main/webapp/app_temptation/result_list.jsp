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
.hdrq{
	display: inline-block;
	vertical-align:middle;
	margin-right: 2px;
	vertical-align: text-top;
}
#rq_1,#rq_2{
	border:1px solid #94ABC4;
	width:68px;
	height:26px;
	background: #fff;
	border-radius: 0px;
	color:#000;
	padding-left:5px;
	vertical-align: baseline;
}
.export {
	margin-left: 20px;
}
/* table{table-layout:fixed;} */
/*订单名称*/
/* .indent_name{ white-space:nowrap; text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow:hidden;width:20%;height:47px;display:inline-block;border:none;} */
.indent_name{height:38px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow:hidden; line-height:38px;white-space:nowrap;width:100px;}
</style>  
<script type="text/javascript">
$(document).ready(function() {
	var ors = $("[name=ors]").attr("val");
	if (ors) {
		$("[name=ors] option[value=" + ors + "]").attr("selected", true);
	}
});

function exportExcel() {
	var str = "mobile=" + $("#mobileHid").val()
			+ "&starttime=" + $("#starttimeHid").val()
			+ "&endtime=" + $("#endtimeHid").val()
			+ "&number=" + $("#numberHid").val()
			+ "&eventnumber=" + $("#eventnumberHid").val()
			+ "&ors=" + $("#orsHid").val(); 
	window.location.href = "<c:url value='/app_temptation/export_result_list.mbay?" + str + "' />";
}
</script>
</head>
<body>
 <div class='con'>
    <!--身体-->
       <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->  
             <div>        
              <form id="pagerForm" action="<c:url value="/app_temptation/result_list.mbay"/>" method="post">        
                <div class='hdlb'>营销明细</div>
                <%@ include file="/common/leftcon.jsp" %>
                
                <div class='fr' style='width:828px'>
	                <div class='lb'>
	                  <div class='hd'>
	                    <div style='margin-bottom:20px'>
	                      <span class='hdmc'>手机号码：</span>
	                      <input type='text' name="mobile"  value='${orderform.mobile}'  class='mc'/>	 
		                  <span class='hdmc'>查询日期：</span>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>"   class='hdrq'  onclick="WdatePicker()"/>
	                      <input id="rq_1" name="starttime" class="Wdate" value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${orderform.startTime.toDate()}"/>' type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
	                      <input id="rq_2" name="endtime" value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${orderform.endTime.toDate()}"/>' class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>  
                        </div>
                        <div style='margin-bottom:20px'>                           
	                      <span class='hdmc'>订单编号：</span>
	                      <input type='text' name="number" value='${orderform.orderNumber}'  class='mc'/>	   
	                      <span class='hdbh'>活动编号：</span>
	                      <input type='text' name="eventnumber" value="${orderform.relateNumber}" class='bh'/> 
	                    </div>
	                    <div>
	                    	<span class='hdmc'>充值状态：</span>
	                    	<select name="ors" class="bh" val="${orderform.ors }">
	                    		<option value="">请选择</option>
	                    		<option value="OPER_RECHARGE_SUCCESS">充值成功</option>
	                    		<option value="OPER_RECHARGE_FAIL">充值失败</option>
	                    		<option value="OPER_RECHARGEING">充值中</option>
	                    		<option value="NON">未请求</option>
	                    	</select>
	                    	<input type="submit" class='hd_btn' value='查询'/>
	                    	<input type="button" class='hd_btn export' value='导出excel' onclick="exportExcel()" />
	                    </div>
	                   </div>
	                </div>
                	<!--表格-->
	                <div class='table'>
	                   <table >
	                    <tr>
	                    <td>订单号 | 充值日期</td>
	                    <td style="width:20%;">订单名称</td>
	                    <td>活动编号</td>
	                    <td>手机号</td>
	                    <td style="width:7%;">流量</td>
	                    <td style="width:17%;">MB价格</td>                  
	                    <!-- <td>充值日期</td>   -->                
	                    <td style="width:22%">充值状态</td>
	                    <td style="width:10%;">操作</td>                 
	                </tr>
	                <c:forEach items="${trafficcharges}" var="order">
	                    <tr id="strategytr${order.number}">
	                        <td>${order.number}<br><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${order.createTime.toDate()}"/></td>
	                        <td title="${order.orderName}"><p class="indent_name">${order.orderName}</p></td>
	                        <td>${order.relateNumber}</td>                      
	                        <td>${order.mobile}</td>
	                        <td>${order.trafficPackage.traffic}M</td>
	                        <td>${order.mbayPrice}</td>            
	                        <%-- <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${order.createTime.toDate()}"/></td>  --%>          
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${order.ors == null }">未请求</c:when>
	                        		<c:otherwise>${order.ors.value}</c:otherwise>
	                        	</c:choose>
	                        </td> 
	                        <td>
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
 </div>
 
<!-- 查询条件隐藏域(防止文本框输入值但未点击查询，对excel导出造成影响) -->
<input type="hidden" id="mobileHid" value="${orderform.mobile }" />
<input type="hidden" id="starttimeHid" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${orderform.startTime.toDate()}" />" />  
<input type="hidden" id="endtimeHid" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${orderform.endTime.toDate()}"/>" />
<input type="hidden" id="numberHid" value="${orderform.orderNumber }" />
<input type="hidden" id="eventnumberHid" value="${orderform.relateNumber }" />
<input type="hidden" id="orsHid" value="${orderform.ors }" />
 
</div>
</body>
</html>
