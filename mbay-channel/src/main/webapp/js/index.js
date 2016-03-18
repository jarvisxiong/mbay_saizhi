/**首页使用的js文件**/
$(function(){
	/**
	 * 验证码刷新
	 *//*
	var refreshUrl = ctx+'/authcode/authImg.mbay';
	function authImgRefresh() {
		$("#authImg").attr("src", refreshUrl + "?" + Math.random());
	}
	$(function() {
			var errormsg = "${message}";
			if (errormsg != "") {
			$(".prompt").show();//显示图片
			$("#showMsg").text(errormsg);//在图片上显示提示信息
	}
		$("#enterwork").click(function() { 
			var str =ctx+"/channel/workbench.mbay"; 
			window.location.href = str;
		});
	});
	*/
	if($.browser.webkit) {
		$('input').attr('autocomplete', 'off');
	}
	
    $(".slideGroup .slideBox").slide({
    	autoPlay:true, 
    	mainCell:"ul",
    	vis:4,
    	scroll:1,
    	prevCell:".sPrev",
    	nextCell:".sNext",
    	effect:"leftLoop"
    });
    
    $( "#tabs" ).tabs({
    	event: "mouseover"
    });
 
    $('.tab_tit li').hover(
		function() {
			$(this).css('background','#FFFFFF')
					.css('border','1px solid #DCDCDC')
					.css('border-bottom-color','#fff').find('a').css('color','#000');
		},
		function() {
			$(this).css('background','#009fc3')
					.css('border','0px').find('a').css('color','#fff');	
		}
    );
  
    $('.yanzhenma').focus(function(){
    	$(this).css('color','#000');
    	var txt_value=$(this).val();
    	if(txt_value==this.defaultValue) {
    		$(this).val('');
    	}
    });
    
    $('.yanzhenma').blur(function(){
    	$(this).css('color','#999999');
	    var txt_value=$(this).val();
		if(txt_value=='') {
			$(this).val(this.defaultValue);
		}
	});	
		
    ajaxGetChannelPartner();
	ajaxGetDynamicItem();
	
	$("#subbtn").bind("click",function(){		
		if(valForm()){
			$("#loginForm").submit();
		}
	});
});

function ajaxGetChannelPartner(){
	$.ajax({
		type:"POST",
		dataType:"json",
		url:absurl+"/channel/ajax/channelPartner.mbay",
		success:function(data){
			var value = "";
			for(var o in data){  
				var url = data[o].url;
				var link = data[o].link;
				value += "<li><div class='pic'>"
					+"<a href='"+link+"' target='_blank'><img src='"+absurl+"/images/"+url+"/get.mbay' /></a></div></li>";
			}; 
			$("#channelPartnerShow").html(value);
			/* Echo.init({
       			offset: 0,
       			throttle: 0
       		}); */
			$(".slideGroup .slideBox").slide({
				autoPlay:true, 
				mainCell:"ul",
				vis:4,
				scroll:1,
				prevCell:".sPrev",
				nextCell:".sNext",
				effect:"leftLoop"
			});
		},
		error: function(){
		}        
	});
}

var index = 1;
function ajaxGetDynamicItem(){
	$.ajax({
		type:"POST",
		dataType:"json",
		url:absurl+"/channel/ajax/dynamicItem.mbay",
		success:function(data){
			var inner = "";
			var tab = "";
			for(var o in data){  
				var values = data[o].channelDynamics;
				var id = data[o].id;
				var name1 = data[o].name;
				var inner2 = "";
				for(var i in values){
					var name2 = values[i].category.name;
					var url = values[i].url;
					var description = values[i].description;
					if(name2 == id){
						inner2 += "<li><a href='javascript:void(0)'><img src='"+absurl+"/images/"+url+"/get.mbay' /></a><span>"+description+"</span></li>";
					};
				}
				if(index == 1){
					inner += "<div id='tabs-"+index+"'><ul>"+inner2+"</ul></div>";
				}else{
					inner += "<div id='tabs-"+index+"' style='display:none'><ul>"+inner2+"</ul></div>";
				}
				tab += "<li onmouseover='javascript:switchDiv(\"#tabs-"+index+"\")'><a href='javascript:void(0)'>"+name1+"</a></li>";
				index = index + 1;
			}; 
			$("#tabs").html("<div class='tab_tit'><ul>"+tab+"</ul></div>"+inner);
			
			$('.tab_tit li').hover(function(){
				$(this).css('background','#FFFFFF')
				.css('border','1px solid #DCDCDC')
				.css('border-bottom-color','#fff').find('a').css('color','#000');
			},function(){
				$(this).css('background','#009fc3')
				.css('border','0px').find('a').css('color','#fff');	
			});
		},
		error: function(){
		}        
	});
}

//tab切换
function switchDiv(divId){
	for(var i = 1; i < index; i++){
		var value = "#tabs-" + i;
		if(divId == value){
			$(divId).show();
		}else{
			$(value).hide();
		};
	};
}

function valForm() {
	$(".prompt").hide();
	$("#showMsg").text("");
	var username = $.trim($("input[name=username]").val());
	var captcha = $.trim($("#captcha").val());
	var passwd = $.trim($("#encryptionPassword").val());
	if (username == "") {
		$(".prompt").show();
		$("#showMsg").text("请输入用户名");
		return false;
	}
	if (passwd == "") {
		$(".prompt").show();
		$("#showMsg").text("请输入密码");
		return false;
	}	
	if (captcha == "") {
		$(".prompt").show();
		$("#showMsg").text("请输入验证码");
		return false;
	}
	if (captcha.length != 6) {
		$(".prompt").show();
		$("#showMsg").text("验证码不正确");
		return false;
	}	
	//加密方式:md5(md5(md5(密码) + 登录账户 + 验证码)
	var hash_pwd = hex_md5(passwd);//md5(密码)
	var hash_first = hex_md5(hash_pwd + username + captcha);
	var hash = hex_md5(hash_first);
	$("input[name=password]").val(hash);
	return true;
};


/*$(function(){
	var browser=navigator.appName;
	var b_version=navigator.appVersion;
	var version=b_version.split(";");
	var trim_Version=version[1].replace(/[ ]/g,"");
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0"||browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0")
	{
	    window.location.href = '../global/update-browser.html';
	}
	// jQuery plugin to prevent double submission of forms
	jQuery.fn.preventDoubleSubmission = function() {
		$(this).on('submit',function(e){
			var $form = $(this);
		    if ($form.data('submitted') === true) {///
		        // Previously submitted - don't submit again
		    	e.preventDefault();
		    } else {
		        // Mark it so that the next submit can be ignored
		    	$form.data('submitted', true);
		    }
		});
		
		// Keep chainability
		return this;
	};
	$('form').preventDoubleSubmission();    
});*/

document.onkeydown = function(event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];          
    if (e && e.keyCode == 13) { // enter 键
    	$("#subbtn").trigger("click");
    }
}; 

function onlyNum(event) {
    event = event || window.event;
	if (event.ctrlKey || event.shiftKey || event.altKey) {
		event.returnValue=false;
		event.preventDefault();
	}
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) {
	if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) {
		event.returnValue=false;
		event.preventDefault(); 
		}
	}
}
