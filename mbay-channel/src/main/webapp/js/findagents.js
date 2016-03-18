 /***
  * 获取下级联系人列表
  */
var subAgents=null;
    $(document).ready(function() {
    	 $('#usernumber').click(function(){
    		if(!$(".an_tc").is(":hidden")){
    			$(".an_tc").hide();
    			//美贝账户列表三角
    			$(".arrow").hide();
    			return;
    		}
    		 $(".an_tc").html("");	 
        		 if(subAgents==null){
        		 jQuery.ajax({
        	            type: "post",
        	            async: false,
        	            url: ctx+"/channel/findagents.mbay",
        	            contentType: "application/json; charset=utf-8",
        	            dataType: "json",
        	            cache: false,
        	            success: function (subAgentResult) {
        	            	subAgents=subAgentResult;
        	            },
        	            error: function (err) {
        	                alert(err);
        	            }
        	        });
        		 }
        		 $(subAgents).each(function(index,agent){
        			 var showname=agent.name;
        			 if(showname.length>4){
        				 showname=showname.substring(0,4)+"***";
        			 }
        			 $(".an_tc").append("<a href=javascript:appendPeople('" +showname+"','"+agent.usernumber+"'); style='color: #4d4d4d'><div title='"+showname+"' class='tc_tj'>"+agent.usernumber +"&nbsp;&nbsp;"+showname+ "</div></a>");
/*        			 $('.an_tc').show('fast');
        			 $(".arrow").show('fast');*/
        			 $('.an_tc').slideDown('fast');
        			 $(".arrow").slideDown('fast');
        		 });
    	    });
    	
	});  
    
    
    function appendPeople(showname,number){
    	//添加名称的同时还要修改隐藏域汇总的accountvalue值
    	 $("#accountvalue").val(number); 
   		$("#mbayaccount").val(showname+"("+number+")");	
		/* $('.an_tc').hide('slow');*/
   		$('.an_tc').hide();
   		$(".arrow").hide();
	}
    
    