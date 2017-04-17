/**
 *
 */

$(document).ready(function(){
	FastClick.attach(document.body);//防击穿
	orderList.loadData();
	initOrderList.initScroll();
});
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function () { setTimeout(scroll.scrollFixed, 200); }, false);