$(function(){
    $(".shake-login-i0").touch(function() { 
    	$.messager.dialog({
    		content: $("#cmp-pop").html()
    	});
    });
    
    getData(1);
});

function getData(pagenum){
	$.get(ctx+"/web/campaign/list/data/get.mbay",{pagenum: pagenum},function(result){
		//如果没有数据返回，提示暂无活动
		if(!result.list){
			$("#campaign-list").html("暂无活动");
			return;
		}
		//解析数据
		$.each(result.list, function(index, val) { 
			var $html = $(
					"<article class='mb-detail-item'>" +
						"<a href='javascript:void(0)'>" +
							"<img src=" + val.image + " alt=" + val.name + " />" +
						"</a>" +
					"</article>");
			$html.find("a").touch(function() { 
				gotoUrl(val.link);
			});
			$("#campaign-list").append($html);
		});
	});
}