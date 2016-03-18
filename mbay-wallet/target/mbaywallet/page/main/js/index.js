$(function(){
	var telephone = $("#telephone").val();

    $("#login").touch(function() { 
    	gotoUrl(ctx + "/web/main/login/ui.mbay?mobile=" + telephone);
    });

    $("#logout").touch(function() {
    	gotoUrl(ctx + "/web/main/logout.mbay");
    });
    
    $("#trade").touch(function() {
    	tapAnimate(this, function() {
    		gotoUrl(ctx + "/web/trade/record/list/ui.mbay");
    	});
    });
    
    $("#enshrine").touch(function() {
    	tapAnimate(this, function() {
    		$("#qrReadOnline").show();
    	});
    });
    
    $("#red-pack").touch(function() {
    	tapAnimate(this, function() {
    		$.messager.remind({ content: "即将上线，敬请期待！" });
    	});
    });
    
    $("#traffic-package").touch(function() {
    	tapAnimate(this, function() {
    		/*$.getJSON(ctx + "/web/partner/izhangxin.mbay", function(resp) {
    			if (!resp.status) {
    				$.messager.remind({ content: resp.errorMsg });
    			} else {
    				gotoUrl(resp.data);
    			}
    		});*/
    		//gotoUrl(ctx + "/web/traffic/package/redeem/ui.mbay");
    		gotoUrl(ctx + "/web/partner/izhangxin.mbay");
    	});
    });
    
    $("#campaign").touch(function() {
    	tapAnimate(this, function() {
    		gotoUrl(ctx + "/web/campaign/list/ui.mbay?mobile=" + telephone);
    	});
    });
    
    $("#mall").touch(function() {
    	tapAnimate(this, function() {
    		gotoUrl(ctx + "/web/main/duiba/ui.mbay");
    	});
    });
    
    $("#qrReadOnline .pup-p1").touch(function() {
    	$("#qrReadOnline").hide();
    });
    
    $(".m-user-tel").touch(function() {
        $(".userList").slideToggle(200);
    });
});

function tapAnimate(ele, fn) {
	var $thiz = $(ele).find(".touchHover");
    $thiz.addClass("active");
    setTimeout(function() {
    	$thiz.removeClass("active");
    	fn();
    }, 300);
}


