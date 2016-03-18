<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>


<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发件箱</title>
<link href="${actx}/wro/${version}/outboxmessage.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/wro/${version}/outboxmessage.js"></script>
 <link href="${actx}/css/smoothness/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="${actx}/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript">
function selectmsgInfo(id){ 
        $.getJSON("${pageContext.request.contextPath}/message/selectSelfMsgInfo.mbay?id="+id,function(json){            
            if(json!=null){             
                    $("#title").html("标题:"+json.title);             
                    $("#content").html(json.content);                   
                    $("#stime").html("发送时间:"+json.sendTime);
            }   
            $("#divIDNo"+id).find('.sjx_box_1_u_left').addClass('sjx_box_1_u_left_sp');	
        });         
}

function deleteMsg(id){ 
    $.getJSON("${pageContext.request.contextPath}/message/deleteSendMsg.mbay?id="+id,function(json){            
        if(json){               
            //删除成功
            $("#divIDNo"+id).hide();
            alert(json.message);
            window.location.reload();
        }else{
            //删除失败
            alert(json.message);
        }       
    });
}


$(function(){   
    var num=$("span#current").val();
    if(num==0){
     $("span#current").removeClass("current");
    }   
});


function searchPageSumit(pnum){
	var subform=document.forms[0];
	var totalrow=$('#totalrow').val();
	$('#totalrow').attr("display","none");
	var action=$(subform).attr("action");
	var orgaction=action;
	var index=action.indexOf("?");
	if(index>0){
	action=action+"&pagenum="+pnum+"&totalrow="+totalrow;
	}else{
	action=action+"?pagenum="+pnum+"&totalrow="+totalrow;
	}
	$(subform).attr("action",action);
	$(subform).submit();
	$(subform).attr("action",orgaction);
}
</script>
</head>
<body>
 <div class='con'>
 <div class='body clearfix'>
       <div class='b_con com_width clearfix'>
          <!--邮箱左边索引-->
               <%@ include file="/emailMessage/msgleft.jsp" %>
          <!--邮箱右边-->
             <div class='email_con fl'>
                <div class='sjx_middle fl'>
                 <div class='sjx_tit'>
                    <div class='sjx_tit_1 fl'>发件箱</div>                   
                 </div>
                 <div class='sjx_sec'>   
                   <form id="pagerForm" action="<c:url value='out_msg_list.mbay'/>" method="post">             
                   <!--邮箱信件-->
                   <c:forEach items="${hmsglist}" var="hmsg" varStatus="msgStatus">
                   <div class='sjx_box' onclick="selectmsgInfo(${hmsg.message.id})"  id="divIDNo${hmsg.message.id}"> 
                       <div class='sjx_box_1 clearfix'>                       
                                <div class='sjx_box_1_left sjx_box_1_u_left_sp fl'>${hmsg.receverUnumber}</div>
                           <a href="javascript:void(0);" onclick="deleteMsg(${hmsg.message.id})"><div class='sjx_box_1_right sjx_box_1_u_right fr' title='删除'></div></a>
                       </div>
                       <div class='sjx_box_2 clearfix'>
                           <div class='sjx_box_1_left sjx_box_1_d_left fl'>
                           <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${hmsg.message.sendTime.toDate()}"/>  
                            </div>
                           <a href='#'><div class='sjx_box_1_right sjx_box_1_d_right fr' title='收藏'></div></a>
                       </div>                      
                    </div>                      
                   </c:forEach>  
                 <div class="menea">
                    <div class="inner">     
                    <c:if test="${pageinfo.pagenum>1}">
                     <a href="javascript:searchPageSumit(${pageinfo.pagenum-1});">&lt;</a> 
                    </c:if>                                
                    <c:if test="${pageinfo.pagenum>=1}">
                      <span class="current" id="current">${pageinfo.pagenum}/${pageinfo.totalpage} </span>     
                    </c:if>                    
                     <c:if test="${pageinfo.pagenum!=pageinfo.totalpage}">
                      <a href="javascript:searchPageSumit(${pageinfo.pagenum+1});">&gt;</a> 
                    </c:if> 
                     <input id="totalrow" type="hidden" hidden="hidden" value="${pageinfo.totalrow}"/>    
                   </div>					
                </div>
                </form>             
                 </div>                  
               </div>              
                      
             <div class='sjx_detail fl'>
                  <div class='sjx_detail_u'>
                      <div class='yh fl' id="title">标题：${msg.title}</div>
                      <div class='sj fr' id="stime"><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${msg.sendTime.toDate()}" /> </div>
                  </div>
                  <div class='sjx_detail_d'>内容:<p id="content">${msg.content}</p></div>
               </div>
              </div>       
     </div>
  <!--尾部--> 
</div>
</div>
</body>
</html>
