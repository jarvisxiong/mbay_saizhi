<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.sz.mbay.operator.enums.OperatorType" %>
<%@ page import="org.sz.mbay.operator.enums.TrafficType" %>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>制定策略</title>
   <meta name="description" content="">
   <%-- <link href="<c:url value="/style/global-min.css"/>" type="text/css" rel="stylesheet">
   <link href="<c:url value="/style/account-settings.css"/> " type="text/css" rel="stylesheet"> --%>
   <script type="text/javascript" src="<c:url value="/requiered/jquery.js" />"></script>
   <link rel="stylesheet" type="text/css" href="<c:url value="/js/framework/jquery-easyui-1.3.2/themes/metro/easyui.css" />"/>
   <link rel="stylesheet" type="text/css" href="<c:url value="/js/framework/jquery-easyui-1.3.2/themes/icon.css" />"/>
   <script type="text/javascript"src="<c:url value="/js/framework/jquery-easyui-1.3.2/jquery.easyui.min.js" />"></script>
   <script type="text/javascript"src="<c:url value="/js/framework/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" />"></script>
<%--    <script type="text/javascript" src="<c:url value="/js/json2.js" />"></script> --%>
   <script type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
   <script type="text/javascript" src="${actx}/js/zclip/jquery.zclip.min.js"></script>
    
    <script type="text/javascript">
        $(document).ready(function () {
         var operators=null;
            $('#selectarea').change(
                    function changeBiz() {                    
                    	$("input:radio[name='teloperator.key']").attr("checked",false);
                    	var area=$('#selectarea').val(); 
                    ////	if(area=="0")return;
                        $.getJSON('<c:url value="/operator/findOperatorsByArea.mbay"/>?area=' + area, function (json) {  
                        	 operators=json;                        	
                        	 $(".opertype").hide();
                        	 $(".traffictype").hide();
                        	 var types="";
                                for (var i = 0; i < json.length; i++) {
                                   var opertype=json[i].type;
                                   types+=opertype+"";
                                   $("#oper"+opertype).show();
                                 ///  $("#operradio"+opertype).val(json[i].id); 
                                }
                              ///var operatorable=false;
                                if(types.indexOf("1")>-1&&types.indexOf("2")>-1&&types.indexOf("3")>-1){
                                	 $("#oper"+0).show();
                                }
                        });
                   
                    }); 	
               
            $("input:radio[name='teloperator.key']" ).change(function(){
            	$(".traffictype").hide();
            	$("input:radio[name='traffictype.key']").attr("checked",false);
            	  var operid=$(this).val();
            	  if(operid==0){
            		var state=true;
            		var province=true;
            		  for (var i = 0; i < operators.length; i++) {
            			  var istate=false;
            			   var iprovince=false;
                          var traffictypes=operators[i].trffficbizs;
                          for(var j=0;j<traffictypes.length;j++){
                        		 if(traffictypes[j].traffictype==1){
                        			 istate=true;
                        		 }
                        		 if(traffictypes[j].traffictype==2){
                        			 iprovince=true;
                        		 }
                        	}
                          if(state&&!istate){
                        	  state=false;
                          }
                          if(province&&!iprovince){
                        	  state=false;
                          }                         
                       }
            		  if(state){
            			  $("#traffic1").show();
            		  }
            		  if(province){
            			  $("#traffic2").show();
            		  }
            	  }else{
            	  for (var i = 0; i < operators.length; i++) {
                      if(operators[i].type==operid){
                    	  var traffictypes=operators[i].trffficbizs;
                    	  for(var j=0;j<traffictypes.length;j++){
                    		  $("#traffic"+(traffictypes[j].traffictype)).show();
                    	  }
                      }                    
                   }
            	  }
            });
            
            $("input:radio[name='chargetype']" ).change(function(){
            	if($(this).val()==2){//定充值
            		$("#regularul").show();
            	}else{
            		$("#regularul").hide();
            	}
            });
            $('#regular').datebox({
                required: true
            });
            $("input[name='name']").validatebox({
                required: true,
                validType: 'length[0,100]',
                missingMessage: '请填写名称',
                invalidMessage: '名称不超过50字'
            });
        

            $('#subForm').click(function () {
                var flag = true;
                $(".validateNeed").each(function () {
                    if (!$(this).validatebox("isValid")) {
                        flag = false;
                    }
                });
                if (!flag) {
                    return false;
                }
               // alert(123);
                $("#myForm").submit();
            });
            
     
        });
    </script>
</head>
<body>
<!--head-->
<div id="wrapper">
    <div class="reg-items" style="padding-top:0;">
       <form id="myForm" action="<c:url value="/customerserver/batchcharge/charge.mbay?id=${chargeinfo.id }"/>" method="post" enctype="multipart/form-data" >      
            <div class="reg-title">
                <h2>充值确认</h2>
                <b class="line"></b></div>
            <div class="reg-items" style="padding-top:0;">
                <ul class="items">
                    <li class="txt"><em>*</em>名称：</li>
                    <li>
                      ${chargeinfo.name }
                    </li>
                </ul>

              
                <ul class="items">
                    <li class="txt"><em>*</em>运营商：</li>
                    <li>
                        ${chargeinfo.area.desc}${chargeinfo.teloperator.desc}                        
                    </li>
                </ul>
              
                <ul class="items trafficitem">
                    <li class="txt"><em>*</em>流量：</li>
                    <li id="operatorBizID_li">                       
                         ${chargeinfo.chargenum}MB${chargeinfo.traffictype.desc}       
                    </li>
                </ul>  
                <ul class="items trafficitem">
                    <li class="txt"><em>*</em>号码数量：</li>
                    <li id="operatorBizID_li">                       
                         ${chargeinfo.mobilenum}   
                    </li>
                </ul>  
                
                <ul class="items trafficitem">
                    <li class="txt"><em>*</em>消耗美贝：</li>
                    <li id="operatorBizID_li">                       
                         ${chargeinfo.costmbay}美贝      
                    </li>
                </ul>           
                <ul>
                   <li>
                    <input type="button" id="subForm" class="button grey" value="立即充值"> 
                    </li>
                </ul>
                
            </div>
        </form>
       </div>

</div>
</body>
</html>