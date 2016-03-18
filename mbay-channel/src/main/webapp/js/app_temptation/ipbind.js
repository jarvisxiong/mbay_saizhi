var ip_txt;
var ipReg = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

function delIp(ele) {
	$(ele).parent().parent().remove();
}

function addIp() {
	var ipVal = $(".ip_txt").val().trim();
	if(ipVal != '') {
	    if (!ipReg.test(ipVal)) {
	    	$("#notmsg").text("ip地址格式不正确！");
	    	return;
	    }
	    
	    var txt, exist = false;
		$("#ip-table td:first-child").each(function() {
			txt = $(this).text().trim();
			if (txt == ipVal) {
				$("#notmsg").text("ip地址已存在！");
				exist = true;
		    	return false;
			}
		});
		
		if (exist) {
			return;
		}
	    
		$("#notmsg").text('');
		
		ip_txt="<tr><td>"+ipVal+"</td><td><a href='javascript:void(0)' onclick='delIp(this)'>删除</a></td></tr>";
		$(ip_txt).appendTo('.activ_detail div.table table');
	}
}

function delIpReal(ele, id) {
	$.ajax({
		url: ctx + "/app_temptation/delete_bind_ip.mbay",
		type: "POST",
		cache: false,
		data: { id: id },
		dataType: "json",
		success: function(resp) {
			if (resp.status) {
				delIp(ele);
			} else {
				$("#notmsg").text("删除失败！");
			}
		}
	});
}

function addIpReal(number) {
	var ipVal = $(".ip_txt").val().trim();
	if(ipVal != '') {
	    if (!ipReg.test(ipVal)) {
	    	$("#notmsg").text("ip地址格式不正确！");
	    	return;
	    }
	    
	    var txt, exist = false;
		$("#ip-table td:first-child").each(function() {
			txt = $(this).text().trim();
			if (txt == ipVal) {
				$("#notmsg").text("ip地址已存在！");
				exist = true;
		    	return false;
			}
		});
		
		if (exist) {
			return;
		}
	    
		$("#notmsg").text('');
		
		$.ajax({
			url: ctx + "/app_temptation/add_bind_ip.mbay",
			type: "POST",
			cache: false,
			data: { 
				campaignNumber: number,
				ip: ipVal
			},
			dataType: "json",
			success: function(resp) { 
				if (resp.status) {
					ip_txt = "<tr><td>" 
							+ ipVal 
							+ "</td><td><a href='javascript:void(0)' onclick='delIpReal(this, \"" 
							+ resp.id 
							+ "\")'>删除</a></td></tr>";
					$(ip_txt).appendTo('.activ_detail div.table table');
				} else {
					$("#notmsg").text("新增失败！");
				}
			}
		});
	}
}