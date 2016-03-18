<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动列表</title>
<t:assets/>
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
 <script type="text/javascript"  src="${actx}/js/my97/WdatePicker.js"></script> 
 <script type="text/javascript"	src="${actx}/js/event/activitylist.js"></script>  <!-- 不存在 -->
  </head>
  <body>
 <div class='con'>
    <!--身体-->
       <div class='body clearfix'>
          <div class='b_con com_width clearfix'>
        <!--左边-->  
             <div>        
              <form id="pagerForm" action="<c:url value="/wechatCampaign/agents_list.mbay"/>" method="post">        
                <div class='hdlb'>代理商列表</div>                
                <%@ include file="/common/leftcon.jsp" %>
                <!--内容右部-->
                  <div class='hd_con_r fr'>
	                <!--表格-->
	                <div class='table'>
	                   <table >
	                    <tr>
	                    <td>代理账号</td>
	                    <td>账户名称</td>
	                    <td>企业名称</td>
	                    <td>账户余额(美贝)</td>	                    
	                </tr>
	                <c:forEach items="${subagentslist}" var="agent">
	                    <tr id="agent${agent.usernumber}">
	                        <td >${agent.usernumber}</td>
	                        <td style="text-align: left;padding-left: 10px"  >${agent.username}</td>
	                        <td>${agent.name}</td>
	                        <td>${agent.account.amount}</td> 
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
