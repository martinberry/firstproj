
/**
 * orderList相关脚本
 * */
var accountBalanceList = {
		loadData : function(){//加载数据
			var searchUrl = basepath+"/accountBalance/load";
			var count = parseInt($("#totalPage").val());
			var startNum = parseInt($("#pageNum").val());
			startNum = startNum + 1;
			if (Number(startNum) > Number(count)) {//全部加载完
				document.getElementById('pullUp').querySelector('.pullUpLabel').innerHTML = '';
				return false;
			}
			$.ajax({
					url : searchUrl,
					type : 'GET',
					data:{startNum:startNum},
			       	dataType:"html",
					success : function(result) {
						$("#thelist").append(result);
						$("#thelist").append("<div style='height:1rem;></div>");
						$("#pageNum").val(startNum);
					}
				});
		}
}