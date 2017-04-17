$(function() {
	$("#searchField").on("click",".firstPage",function() {
		if($(this).hasClass("disabled")) return ;
		$("input[name='pageNo']").val(1);
		submitFunc();
	});

	$("#searchField").on("click",".prevPage",function() {
		if($(this).hasClass("disabled")) return ;
		if( parseInt($(".pageNo").text()) -1 >= 1 ){
			$("input[name='pageNo']").val(parseInt($(".pageNo").text()) - 1);
			submitFunc();
		}
	});

	$("#searchField").on("click",".nextPage",function() {
		if($(this).hasClass("disabled")) return ;
		if( parseInt($(".pageNo").text()) + 1 <= parseInt($(".totalPageCount").text()) ){
			$("input[name='pageNo']").val(parseInt($(".pageNo").text()) + 1);
			submitFunc();
		}
	});

	$("#searchField").on("click",".lastPage",function() {
		if($(this).hasClass("disabled")) return ;
		if( parseInt($(".totalPageCount").text()) > 0 ){
			$("input[name='pageNo']").val($(".totalPageCount").text());
			submitFunc();
		}
	});

	//每页条数选择菜单
	$("#searchField").on("click",".pageNum .pageNumClick",function() {
		$(".pageNumClick").siblings(".paginationNum").toggle()
	});

	//选择每页条数
	$("#searchField").on("click", ".paginationNum li", function(){
		$(".paginationNum").toggle();
		var pageSize = $(this).children("a").text();
		$("input[name='pageSize']").val(pageSize);
		$("input[name='pageNo']").val(1);
		$(".pageNumClick .pageSize").text(pageSize);
		submitFunc();
	});

	//“跳转到”输入框若输入非数字，置空
	$("#searchField").on("keyup", ".pageToNum", function(){
		if( !$(this).val().match(/^\d{1,4}$/) )
			$(this).val("");
	});

	$("#searchField").on("click", ".pageTo", function(){
		var pageToNum = $(".pageToNum").val();
		var totalPage = $(".totalPageCount").text();
		if( pageToNum != "" ){
			if( parseInt(pageToNum) > parseInt(totalPage) ){     //如果填写页码大于总页数，不进行任何操作
				return;
			}else if( pageToNum == "0" ){     //如果填写页码为0，不进行任何操作
				return;
			}else{
				$("input[name='pageNo']").val(pageToNum);
			}
			submitFunc();
		}
	});

})
