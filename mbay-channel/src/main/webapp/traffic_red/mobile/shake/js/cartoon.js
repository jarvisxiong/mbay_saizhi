// JavaScript Document
//摇一摇动画部分
window.onload = function(){  
var fudongAD = document.getElementById("headads");  
setTimeout("noneheadads()", 1000);   
  
}  
function getStyle(obj, attr)
{
	if(obj.currentStyle)
	{
		return obj.currentStyle[attr];
	}
	else
	{
		return getComputedStyle(obj, false)[attr];
	}
}
function move(obj, json, fn)
{
	clearInterval(obj.timer);
	obj.timer=setInterval(function(){
		var bStop=true;
		for(var attr in json)
		{
			var iCur=0;
			if(attr=='opacity')	
			{
				iCur=parseInt(parseFloat(getStyle(obj, attr)*100));
			}
			else
			{
				iCur=parseInt(getStyle(obj, attr));
		    }
		    var iSpeed=(json[attr]-iCur)/8;
		    if(iSpeed>0)
		    {
			     iSpeed=Math.ceil(iSpeed);
		    }
		    else
		    {
			    iSpeed=Math.floor(iSpeed);
		    }
			if(iCur!=json[attr])
			{
				bStop=false;
			}
		    if(attr=='opacity')
			{
				obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
				obj.style.opacity=(iCur+iSpeed)/100;
			}
			else
			{
				obj.style[attr]=iCur+iSpeed+'px';
			}
		}
		if(bStop==true)
		{
			clearInterval(obj.timer)
			    if(fn)
			    {fn();}
		}
	},30);
}

function noneheadads() {  
    var a=document.getElementById("headads");
	var b=document.getElementById("headads2");
	move(a,{"marginTop":-40},function(){
		    move(a,{"marginTop":0});
			move(b,{"marginTop":0},function(){
				alert("红包与你擦肩而过哦！"); 
		    });
		});
	move(b,{"marginTop":148});
	
}  
  
