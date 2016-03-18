<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销明细</title> 
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/account/tipswindown.js"></script>
<script type="text/javascript" src="${actx}/js/my97/WdatePicker.js"></script>
<script type="text/javascript" src="${actx}/js/mbayui.js"></script>
<style type="text/css">  
.hdrq{display: inline-block;vertical-align:middle;margin-right: 2px;vertical-align: text-top;}
#rq_1,#rq_2{border:1px solid #94ABC4;width:68px;height:26px;background: #fff;border-radius: 0px;color:#000;padding-left:5px;vertical-align: baseline;}
</style>  
<script type="text/javascript">
	$(document).ready(function() {
		var state = $("select[name='ors']").attr("val"); 
		$("select[name='ors'] option[value='" + state + "']").attr("selected", true);
	});
	
	// 退款
	function refundfn(orderNumber, ele) {
		$.ajax({
			url: "${ctx}/traffic/refund.mbay",
			type: "POST",
			cache: false,
			data: {
				orderNumber: orderNumber
			},
			dataType: "JSON",
			success: function(resp) {
				if (resp.status && resp.status == true) {
					$(ele).closest("td").prev().text(resp.refund);
					$(ele).remove();
				} else {
					$.messager.alert({ 
						title: "操作失败",
						content: resp.message
					});  
				}
			}
		});
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
              <form id="pagerForm" action="<c:url value="/app_temptation/traffic_record.mbay"/>" method="post"> 
              	<input type="hidden" value="${campaignNumber }" name="campaignNumber" />       
                <div class='hdlb'>app诱惑 - 充值记录</div>
                <%@ include file="/common/leftcon.jsp" %>
                
                <div class='fr' style='width:828px'>
	                <div class='lb'>
	                  <div class='hd'>
	                    <div style='margin-bottom:20px'>
	                      <span class='hdmc'>充值状态：</span>	
	                      <select name="ors" class='mc' val="${form.ors }">
	                      	<option value="">请选择</option>
	                      	<option value="OPER_RECHARGE_SUCCESS">充值成功</option>
	                      	<option value="OPER_RECHARGE_FAIL">充值失败</option>
	                      	<option value="OPER_RECHARGEING">充值中</option>
	                      	<option value="NON">未请求</option>
	                      </select>
		                  <span class='hdmc'>查询日期：</span>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>"   class='hdrq'  onclick="WdatePicker()"/>
	                      <input id="rq_1" name="startTime" class="Wdate" value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${form.startTime.toDate()}"/>' type="text" onFocus="var rq_2=$dp.$('rq_2');WdatePicker({onpicked:function(){rq_2.focus();},maxDate:'#F{$dp.$D(\'rq_2\')}'})"/>
	                      <img src="<c:url value='/images/workimages/rq.jpg'/>" class='hdrq' onclick="WdatePicker()"/>
	                      <input id="rq_2" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${form.endTime.toDate()}"/>' class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'rq_1\')}'})"/>  
                          <input type="submit" class='hd_btn' value='查询' style="margin-left:15px;"/>
                        </div>
                        <div class='hd'>
                        	<span>总数：${statistics.totalNums }</span>
                        	<span style="margin-left:20px;">充值成功：${statistics.successNums }</span>
                        	<span style="margin-left:20px;">充值失败：${statistics.failNums }</span>
                        	<span style="margin-left:20px;">充值中：${statistics.rechargingNums }</span>
                        	<span style="margin-left:20px;">未请求：${statistics.unknownNums }</span>
                        </div>
	                   </div>
	                </div>
                	<!--表格-->
	                <div class='table'>
		                <table>
		                    <tr>
			                    <td>订单号</td>
			                    <td>手机号</td>                
			                    <td>充值日期</td>                  
			                    <td>充值状态</td>
			                    <td>退款状态</td>
			                    <td>操作</td>                 
		                	</tr>
			                <c:forEach items="${trafficRecordList}" var="record">
			                    <tr id="strategytr${record.number}">
			                        <td>${record.number}</td>
			                        <td>${record.mobile}</td>          
			                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${record.createTime.toDate()}"/></td>           
			                        <td>
			                        	<c:choose>
			                        		<c:when test="${record.ors != null}">
			                        			${record.ors.value }
			                        		</c:when>
			                        		<c:otherwise>
			                        			未知
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td> 
			                        <td>
			                        	<c:if test="${record.refund != null }">
				                        	<c:choose>
				                        		<c:when test="${record.refund.status == 'FAIL' }">
				                        			<a href="javascript:void(0)" onclick="$.messager.alert({ title: '原因', content: '${record.refund.reason}' })">${record.refund.status.value }</a>
				                        		</c:when>
				                        		<c:otherwise>${record.refund.status.value }</c:otherwise>
				                        	</c:choose>
			                        	</c:if>
			                        </td> 
			                        <td>
			                        	<c:if test="${record.ors == 'OPER_RECHARGE_FAIL'}">
			                        		<c:if test="${record.refund == null }">
			                        			<input type="button" value="申请退款" class='hd_btn' onclick="refundfn('${record.number}', this)" />
			                        		</c:if>
			                        	</c:if>
			                        </td>
			                    </tr>
			                </c:forEach>        
		                </table>                  
	                </div>
                	<m:page pageinfo="${pageInfo}"/>
                </div>
                   </form>                   
               </div>       
            </div>
 </div>
  <!--尾部-->
 
</div>
</body>
</html>
