$(function(){
 	$("#portraitForm").Validform({
	    showAllError:true,
		tiptype:3,
		datatype:{
			"suffix" : function(gets,obj,curform,regxp){
			    var FileExt = gets.substr(gets.lastIndexOf('.')+1);
			    var _validFileExtensions = ["jpg", "jpeg", "png"];
			    var validimg = false;
			    for(var i = 0; i < _validFileExtensions.length; i++){
			    	var exten = _validFileExtensions[i].toLowerCase();
			    	if(exten == FileExt){
			    		validimg = true;
			    		break;
			    	}
			    }
			    if(!validimg){
			    	return false;
			    }
			    return true;
			}
		}
	}); 
	
	var jcrop_api,boundx,boundy,xsize = $('#preview-pane .preview-container').width(),ysize = $('#preview-pane .preview-container').height();
	
	function init_jcrop(){
		$('#preview').Jcrop({
	      bgColor: '#e2e2e2',
	      onChange: updatePreview,
	      onSelect: updatePreview,
	      aspectRatio: xsize / ysize,
	      maxSize: [188,188]
	    },function(){
	        var bounds = this.getBounds();
	        boundx = bounds[0];
	        boundy = bounds[1];
	        jcrop_api = this;
	        jcrop_api.animateTo([100, 100, 200, 200]);
		});
	}

    function updatePreview(c){
    	var widget_size = jcrop_api.getWidgetSize();
    	if (parseInt(c.w) > 0){
    		$("#x").val(parseInt(c.x));
    		$("#y").val(parseInt(c.y));
    		$("#w").val(parseInt(c.w));
    		$("#h").val(parseInt(c.h));
    		$("#width").val(parseInt(widget_size[0]));
    		$("#height").val(parseInt(widget_size[1]));
    		
        	var rx = xsize / c.w;
        	var ry = ysize / c.h;
    	    $('#preview_short').css({
    	      width: Math.round(rx * boundx) + 'px',
    	      height: Math.round(ry * boundy) + 'px',
    	      marginLeft: '-' + Math.round(rx * c.x) + 'px',
    	      marginTop: '-' + Math.round(ry * c.y) + 'px'
    	    });
    	    
    	    $('.jcrop-holder').css({
    	      left: '50%',
    	      top: '50%',
      	      marginLeft: '-' + Math.round(widget_size[0]/2) + 'px',
      	      marginTop: '-' + Math.round(widget_size[1]/2) + 'px'
      	    });
        }
    }
    
    $("#portraitFile").on("change", function(){
    	if(jcrop_api){
    		jcrop_api.destroy();
    	}
    	var imgObjPreview = document.getElementById("preview"); 
    	imgObjPreview.style.width = '';
	    imgObjPreview.style.height = '';
    	var name = this.files[0].name;
    	var suffix = name.substring(name.lastIndexOf(".") + 1, name.length);
    	var _extensions = ["jpg", "jpeg", "png"];
	    var valid = false;
	    for(var i = 0; i < _extensions.length; i++){
	    	var exten = _extensions[i].toLowerCase();
	    	if(exten == suffix){
	    		valid = true;
	    		break;
	    	}
	    }
	    if(valid){
	    	$(".preview_div").css({background:"#e2e2e2"});
	    	$(".btn_confirm").show();
	    	$(".btn_default").hide();
	    	imgObjPreview.src = window.URL.createObjectURL(this.files[0]); 
	    	var shortimgPreview = document.getElementById("preview_short"); 
	    	shortimgPreview.src = window.URL.createObjectURL(this.files[0]);
	    	init_jcrop();
	    }else{
	    	$(".preview_div").css({background:"url(../images/portrait/div_back.jpg) no-repeat left"});
	    	$(".btn_confirm").hide();
	    	$(".btn_default").show();
	    	imgObjPreview.src = "";
	    }
    });
});