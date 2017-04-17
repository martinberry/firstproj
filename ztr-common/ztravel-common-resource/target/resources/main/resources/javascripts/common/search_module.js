$(function(){
	//首页搜索按钮点击
	$("#searchBtn").click(function(){
		search999() ;
	});
	
	$(".sl-destName").click(function(){
		search999($(this).html(), 2) ;
	});
	
	$(".destSelect-first-level").click(function(){
		search999($(this).find(".fl-destName").html(), 1) ;
	});

});

function search999(destination, destLevel){
	var departure = $("#departure").val();
	var destination = destination ? destination : $("#destination").val();
	var destLevel = destLevel? destLevel : $("#selectedDestLevel").val();

	var searchParam = "?departure=" + departure + "&destination=" + destination + "&destLevel=" + destLevel;
	window.location.href = basepath + "/product/list" + searchParam;
}
