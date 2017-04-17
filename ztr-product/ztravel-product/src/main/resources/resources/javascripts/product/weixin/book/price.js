/**
 *
 */

var price = {
		showPriceInfo : function(){
			var params = {};
			params.childNum =$("#childNum").val();
			params.adultNum =$("#adultNum").val();
			params.productId =$("#productId").val();
			params.packageId =$("#packageId").val();
			params.packageName =$("#packageName").val();
			params.bookDate =$("#bookDate").val();

			params.adultPrice = $("#adultPrice").val();
			params.childPrice=$("#childPrice").val();
			params.singlePrice =$("#singlePrice").val();
			params.productNature = $("#nature").val();
			params.costPriceId = $("#costPriceId").val();

			if($("#couponContainer").find("input").prop("checked")){
				params.couponAmount = new Number($("#couponAmount").html()).multiply(100);
				params.couponName = $("#couponName").html();
				params.couponItemId =$("#couponContainer").find("div.couponList").attr("data-couponid");
			}else{
				params.couponAmount = "";
				params.couponItemId = "";
			}
			var url = wxServer + "/book/weixin/getPriceInfo";
			$.ajax({
				type : "POST",
				url : url,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				data : JSON.stringify(params),
				success : function(result) {
					var priceInfoArray = result.split("<-split->");
					var detail = priceInfoArray[0];
					var totalInfo = priceInfoArray[1];
					$("#priceDetail").html(detail);
					$("#firstPrice").html(totalInfo);
					$("#secondPrice").html(totalInfo);
				}
			});
		}
}