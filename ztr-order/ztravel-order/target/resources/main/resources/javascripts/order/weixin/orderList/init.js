/**
 *
 */

var initOrderList = {
	initMenu : function(){//初始化订单列表页右侧菜单栏
		$('.headerListbtn').click(function(event){
			event.stopPropagation();
			if($('.navlist').is(':visible')){
				$('.navlist').slideUp(200);
			}else{
				$('.navlist').slideDown(200);
			}
		})},
	initScroll:function(){
		// 设置页面根字大小
		var root = document.getElementsByTagName("html")[0],
			w = window.innerWidth >= 640 ? 640 : window.innerWidth;
		root.style.fontSize = (w / 320) * 20 + "px";
		root.style.minHeight = window.innerHeight + "px";
		}
}