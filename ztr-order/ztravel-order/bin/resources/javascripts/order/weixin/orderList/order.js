
/**
 * orderList相关脚本
 * */
var orderList = {
		loadData : function(){//加载数据
			var orderListUrl = wxServer+"/order/weixin/load";
			count = parseInt($("#totalPage").val());
			startNum = parseInt($("#pageNum").val());
			startNum = startNum + 1;
			if (Number(startNum) > Number(count)) {//全部加载完
				document.getElementById('pullUp').querySelector('.pullUpLabel').innerHTML = '';
//				$("#tipPopWindow").html("已全部加载！").popup("open");
				console.log("voer");
				return false;
			}
			$.ajax({
					url : orderListUrl,
					type : 'POST',
					data:{startNum:startNum},
			       	dataType:"html",
					success : function(result) {
						$("#thelist").append(result);
						$("#pageNum").val(startNum);
						orderList.toDetail();
						orderList.toPay();
						orderList.toComment();
						if(null != myScroll){
							myScroll.refresh();
						}
					}
				});
		},
		toDetail : function(){
			$(document).on("tap", "#thelist li div.orderHeader,div.orderDetail div:not('.payInfo')", function(){
				$(this).unbind("tap");
				var orderId = $(this).closest("li").data("num");
				var productNature=$(this).find(".productNature").val();
				if(productNature=="VISA"){
					window.location.href = basepath + "/visaorder/weixin/detail/" + orderId;
				}else if(productNature=='PACKAGE'||productNature=='COMBINATION'||productNature==''){
					window.location.href = basepath + "/order/weixin/detail/" + orderId;
				}else{					
					window.location.href = basepath + "/localorder/weixin/detail/" + orderId;
				}			
			});
		},
		toPay : function(){
			$("a.pay").unbind("tap");
			$(document).on('tap', 'a.pay', function(){
				var orderId = $(this).attr("data-order");
				_paq.push(['trackEvent', 'orderlistpage', 'ztrselectpayorder']);
				if(wxServer.charAt(wxServer.length - 1) == '/'){
					window.location.href = wxServer + "weixin/orderPay/selectPayType?orderId=" + orderId;
				}else{
					window.location.href = wxServer + "/weixin/orderPay/selectPayType?orderId=" + orderId;
				}
			});
		},
		toComment : function(){
			$("a.evaluate").unbind("tap");
			$(document).on('tap', 'a.evaluate', function(){
				var orderId = $(this).attr("data-order");
				window.location.href = wxServer + "/order/weixin/comment/edit/" + orderId;
			});
		}
}