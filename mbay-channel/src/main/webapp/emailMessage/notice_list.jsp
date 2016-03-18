<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告列表</title>
<link href="${actx}/wro/${version}/table_list.css"	rel="stylesheet" type="text/css" />
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"  src="${actx}/js/my97/WdatePicker.js"></script>
<style type="text/css">
	.dialog_con{position:relative;font-size:14px}
	.title{font-size:20px;border-bottom:1px solid #d1d1d1;padding-bottom:5px}
	.time{position:absolute;right:17px;font-size:12px;top:0px}
</style>
<script type="text/javascript">
    function deleteNotice(id){       	
   		$.getJSON("${pageContext.request.contextPath}/message/delete_notice.mbay?id="+id, function(json){
   			if(json.success){
   				$("#notice"+id).hide();
   			}else{
               $.messager.alert({ content: json.message });
   			}
        });
    }
    
    function showNoticeDetail(id) {
    	var url = "${pageContext.request.contextPath}/message/notice_info.mbay?id=" + id;
    	$.getJSON(url, function(response) { 
    		if (response.state) {
    			var $detail = $("#notice-detail");
    			$detail.find(".title").text(response.title);
    			$detail.find(".time").text(response.noticedate);
    			$detail.find(".content").text(response.content);   	
    			$.messager.alert({ content: $detail.html() });
    			
    			if (response.unreadNoticeNum > 0) {
    				$("#noticenum").text("(" + response.unreadNoticeNum + ")");
    			} else {
    				$("#noticenum").text('');
    				$(".tip").trigger("hoverOut");
    			}
    		} else {
    			$.messager.alert({ title: "查看失败", content: response.msg });
    		}
    	});
    }
</script>
</head>
 <div class='con'>
 <div class='body clearfix'>
          <div class='b_con com_width clearfix' >
        <!--左边-->  
             <div class='left_con'>          
             <form id="pagerForm" action="<c:url value="/message/notice_list.mbay"/>" method="post">      
                <div class='hdlb'>公告列表</div>
                <!--身体中部左侧-->
                <%@ include file="/common/leftcon.jsp" %>                
                <!--身体中部右侧-->
                  <c:if test="${noticelist ne null}">  
                <div class='hd_con_r fr'>	           
	                <div class='table'>
	                 <table >
	                    <tr>
	                    <td>标题</td>
	                    <td width="150px">日期</td>
	                    <td width="120px">操作</td>                 
	                	</tr>
	              
	                <c:forEach items="${noticelist}" var="notice" varStatus="t">
	                    <tr id="notice${notice.id}">
	                       <%--  <td>${t.index+1}</td> --%>
	                        <td>${notice.title}</td>
	                        <td>
	                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"	value="${notice.noticedate.toDate()}" />
	                        </td>
	                        <td>
	                            <a href="javascript:void(0)" onclick="showNoticeDetail(${notice.id})">查看</a>	                          
	                        </td>
	                    </tr>
	                </c:forEach>  	                 
	                           
	                   </table>
	                </div>
	                 <m:page pageinfo="${pageinfo}"/>
                </div>
               </c:if>
               <c:if test="${noticelist eq null}">
                   <div class='hd_con_r fr'>	           
	               <center>
	                                                     谢谢您的关注， 暂无公告信息！
	               </center>
	                </div>
               </c:if>      
           </form>
             </div>
             </div>
  <!--尾部--> 

</div>
<div id="notice-detail" style="display:none;">
	<div class="title"></div>
	<div class="time"></div>
	<div class="content"></div>
</div>
 </div>
</body>
</html>