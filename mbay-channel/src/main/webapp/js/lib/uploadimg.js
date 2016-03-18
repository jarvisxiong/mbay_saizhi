/** 编辑模板页面引用的js文件* */
$(function() {
	$('#imgForm').validate({
		rules : {
			bk : {
				required : false,
				bkyz : true,
				bksizeyz : true
			}
		/***********************************************************************
		 * **, logo:{ required : true, logoyz : true, logosizeyz : true }
		 **********************************************************************/
		},
		messages : {
			bk : {
				required : '请上传背景图片！',
				bkyz : "图片格式不正确！",
				bksizeyz : "图片应小于2M！"
			}
		/***********************************************************************
		 * * logo: { required :'请上传logo图片！', logoyz:"图片格式不正确！",
		 * logosizeyz:"图片应小于2M！" }
		 **********************************************************************/
		}
	});

});

function readURL(id) {
	var input = document.getElementById(id);
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#div' + id).show();
			$('#img' + id).attr('src', e.target.result).width(43).height(43);
			// ///$("div.wx_body").attr("style","background-image:
			// url('"+e.target.result+"');background-size: cover;");
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function checkFileType(id) {
	var fieldObj = document.getElementById(id);
	var FileName = fieldObj.value;
	var FileExt = FileName.substr(FileName.lastIndexOf('.') + 1).toLowerCase();
	var _validFileExtensions = [ "jpg", "jpeg", "bmp", "png" ];
	var validimg = false;
	for ( var i = 0; i < _validFileExtensions.length; i++) {
		var exten = _validFileExtensions[i].toLowerCase();
		if (exten == FileExt) {
			validimg = true;
			break;
		}
	}
	if (!validimg) {
		return false;
	}
	return true;
}

function checkFileSize(id){
	//判断浏览器为IE浏览器
 ///   if(navigator.userAgent.indexOf("MSIE") != -1){
  ///  	var myFSO = new ActiveXObject("Scripting.FileSystemObject");
    	///var filepath = document.upload.file.value;
///    	var thefile = myFSO.getFile(filepath);
///    	 FileSizeKB = (thefile.size)/1024;
    	
///    }else{
    	var fieldObj=document.getElementById(id);
    	var FileSize =fieldObj.files[0].size;   
       
    	
 ///   }
	if (FileSize>(2050*1024)){
	        return false;
	 }
	return true;
}
	
