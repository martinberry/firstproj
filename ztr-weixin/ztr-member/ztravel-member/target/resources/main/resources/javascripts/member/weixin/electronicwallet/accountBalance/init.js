/**
 *
 */

var initList = {
	initScroll:function(){
		// 设置页面根字大小
		var root = document.getElementsByTagName("html")[0],
			w = window.innerWidth >= 640 ? 640 : window.innerWidth;
		root.style.fontSize = (w / 320) * 20 + "px";
		root.style.minHeight = window.innerHeight + "px";
		}
}