/**
 * Created by Administrator on 2015/1/3.
 */
$(function(){
    $(".mb-head-nav li").click(function(){
        var _index  = $(this).index();
        $(this).addClass("nav_li"+_index).siblings("li").removeClass();
        // return false;
    });

    $(".nav-item").click(function(){
        $(".mb-head-nav").find("ul").slideToggle(160)
    });
});
$(window).resize(function(){
    var winW = $(window).width();
    if(winW > 840){
        $(".mb-head-nav").find("ul").show(10);
    }else{
        $(".mb-head-nav").find("ul").hide(10);
    }
});