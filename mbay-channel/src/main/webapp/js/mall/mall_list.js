$(function(){
	//选中条数
	var selectNum = 0;
	
	//全选
	$("#checkAll").on("click",function() {
    	if(this.checked){
        	$("input[name='select']:checkbox").each(function() {
        		selectNum++;
        		this.checked = true;
        		$(this).parents("tr").addClass("piece-2");
        	});
        }else{
            $("input[name='select']:checkbox").each(function() {
            	selectNum--;
        		this.checked = false;
        		$(this).parents("tr").removeClass("piece-2");
        	});;
        }
        $(".num").html(selectNum);
    });
	
	//单选
	$("input[name='select']:checkbox").on("click",function() {   
    	if(this.checked){
    		selectNum++;
        	$(this).parents("tr").addClass("piece-2");
        }else{
        	selectNum--;
        	$(this).parents("tr").removeClass("piece-2");
        	$("#checkAll")[0].checked = false;
        }
    	
    	var arrs_checked = $("input[name='select']:checked");
    	var arrs = $("input[name='select']:checkbox");
    	if(arrs.length == arrs_checked.length){
    		$("#checkAll")[0].checked = true;
    	}else{
    		$("#checkAll")[0].checked = false;
    	}
    	
    	$(".num").html(selectNum);
    });
	
	//批量上架
	$("#shelfAll").on("click",function() { 
		var arrs = $("input[name='select']:checked");
		if(arrs.length == 0){
			$.messager.alert({ content: "请至少选择一条记录!" });
			return;
		}
		var itemNumbers = "";
		for(var i = 0; i < arrs.length; i++){
			var arr = arrs[i].value;
			itemNumbers = itemNumbers + arr + ",";
		}
		itemNumbers = itemNumbers.substring(0,itemNumbers.length-1);
		$.get(ctx+"/mall/exchangeItem/shelfAll.mbay",{itemNumbers:itemNumbers},function(result){	
			$.messager.alert({
				content: result.message,
				button: {
					ok: {
						callback: function() {
							window.location.href = ctx + "/mall/exchangeItem/list.mbay";
						}
					}
				}
			});
		}); 
    });
	
	//批量删除
	$("#delAll").on("click",function() { 
		var arrs = $("input[name='select']:checked");
		if(arrs.length == 0){
			$.messager.alert({ content: "请至少选择一条记录!" });
			return;
		}
		var itemNumbers = "";
		for(var i = 0; i < arrs.length; i++){
			var arr = arrs[i].value;
			itemNumbers = itemNumbers + arr + ",";
		}
		itemNumbers = itemNumbers.substring(0,itemNumbers.length-1);
		$.get(ctx+"/mall/exchangeItem/delAll.mbay",{itemNumbers:itemNumbers},function(result){	
			$.messager.alert({
				content: result.message,
				button: {
					ok: {
						callback: function() {
							window.location.href = ctx + "/mall/exchangeItem/list.mbay";
						}
					}
				}
			});
		}); 
    });
    
    //点击添加兑换项弹出下拉选项
	$(".filtrate-button-add").click(function(){
		$(".convertibility").toggle();
	});
	
	/*鼠标指向图标弹出提示(上架、下架、编辑、预览、删除)*/
	$(".hint-ico>a").mouseover(function(){
		var index = $(this).attr("index");
		$("#" + index).show();
	});
	
	/*鼠标离开关系提示(上架、下架、编辑、预览、删除)*/
	$(".hint-ico>a").mouseout(function(){
		var index = $(this).attr("index");
		$("#" + index).hide();
	});
	
	/*鼠标指向图标弹出提示(图片)*/
	$(".preview-img").mouseover(function(){
		var index = $(this).attr("index");
		$("#" + index).show();
	});
	
	/*鼠标离开关系提示(图片)*/
	$(".preview-img").mouseout(function(){
		var index = $(this).attr("index");
		$("#" + index).hide();
	});
	
	//审核中的记录无法选择
	$("input[name='auditing']:checkbox").on("click",function(){
		if(this.checked){
			$.messager.alert({ content: "审核中的记录无法进行操作!" });
		}
		this.checked = false;
	});
});

//更改上/下架状态
function changeStatus(itemNumber,status){
	$.get(ctx+"/mall/exchangeItem/changeStatus.mbay",{itemNumber:itemNumber, status:status},function(result){	
		if(result.success){
			$.messager.alert({
				content: "操作成功",
				button: {
					ok: {
						callback: function() {
							window.location.href = ctx + "/mall/exchangeItem/list.mbay";
						}
					}
				}
			});
		}else{
			$.messager.alert({ content: result.message });
		}
	});
}

//删除
function del(itemNumber){
	$.get(ctx+"/mall/exchangeItem/del.mbay",{itemNumber:itemNumber},function(result){	
		if(result.success){
			$.messager.alert({ 
				content: "操作成功", 
				button: {
					ok: {
						callback: function() {
							window.location.href = ctx + "/mall/exchangeItem/list.mbay";
						}
					}
				}
			});
		}else{
			$.messager.alert({ content: result.message });
		}
	});
}

//手机预览
function preview(itemNumber,mobileDomain){
	var url = mobileDomain + ctx +"/mall/exchangeItem/preview.mbay?itemNumber=" + itemNumber;
	$("#qr-code-a").attr("href", url);
	var src = ctx + "/qrcode/generateQrCode.mbay?url=" + url;
	$("#qr-code-img").attr("src", src);
	$.messager.dialog({
		content: $("#preview-modal").html(),
		css: {
			width: 400,
			height: 200
		}
	});
}

//审核中记录无法进行操作(除了预览)
function showMessage(){
	$.messager.alert({ content: "审核中的记录无法进行操作!" });
}
