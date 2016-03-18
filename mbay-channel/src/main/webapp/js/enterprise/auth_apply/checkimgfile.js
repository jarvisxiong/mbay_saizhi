/**注册时处理上传图片**/
function readURL(id) {
	//alert("id:"+id);
	var input=document.getElementById(id);
	//alert(input);
	if (input.files && input.files[0]) {
	var reader = new FileReader();
	reader.onload = function (e) {
	$('#div'+id).show();
	$('#img'+id).attr('src', e.target.result).width(200).height(150);
	     };
	     reader.readAsDataURL(input.files[0]);
	   }
	}
	function checkFileType(id){
		var fieldObj=document.getElementById(id);
	    var FileName  = fieldObj.value;
	    var FileExt = (FileName.substr(FileName.lastIndexOf('.')+1)).toLowerCase();
	    var _validFileExtensions = ["jpg", "jpeg", "bmp", "png","tif","tiff","gif","svg"];
	    var validimg=false;
	    for(var i=0;i<_validFileExtensions.length;i++){
	    	var exten=_validFileExtensions[i];
	    	if(exten==FileExt){
	    		validimg=true;
	    		break;
	    	}
	    }
	    if(!validimg){
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
		if (FileSize>(8192*8192)){
		        return false;
		 }/*else if(FileSize==1600*1200){
			 
		 }*/
		return true;
	}
