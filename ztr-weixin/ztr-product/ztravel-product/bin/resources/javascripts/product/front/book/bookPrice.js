var bookPrice = {
		getTotalPrice : function() {
			var totalPrice = $("#originTotalPrice").val();
			var couponSub = typeof($("#couponSub").html()) == 'undefined'? '0' : $("#couponSub").html();
			var newPrice = (new Number(totalPrice)).subtract(new Number(couponSub)) < 0 ? 0 : (new Number(totalPrice)).subtract(new Number(couponSub));
			return newPrice.toFixed(2);
		},

		refresh : function(productId,bookDate) {
			if(typeof(productId) == undefined || typeof(bookDate) == undefined || productId == '' || bookDate == ''){
				tipWindow.hideWin();
			}else{
				tipWindow.hideWin();
				$.ajax({
					url: basepath + '/product/book/adultChildPriceInfo/'+ productId + "/" + bookDate,
					type: 'POST',
					success: function(result){
						var totalPrice = new Number(0);
						if(result.adultPrice != null){
							var adultNum = new Number($("#orderAdultNum").html());
							$("#adultTr").attr("style","display;");
							$("#adultPrice").html((new Number(result.adultPrice).toFixed(2)));
							$("#totalAdult").html(((new Number(result.adultPrice)).multiply(adultNum)).toFixed(2));
							totalPrice += (new Number(result.adultPrice)).multiply(adultNum);
						}else{
							$("#adultTr").attr("style","display:none;");
						}
						if(result.childPrice != null){
							$("#childTr").attr("style","display;");
							var childNum = new Number($("#orderChildNum").html());
							$("#childPrice").html((result.childPrice).toFixed(2));
							$("#totalChild").html(((new Number(result.childPrice)).multiply(childNum)).toFixed(2));
							totalPrice += (new Number(result.childPrice)).multiply(childNum);
						}else{
							$("#childTr").attr("style","display:none;");
						}
						if(result.singlePrice != null){
							var totalNum = new Number($("#orderAdultNum").html()).add($("#orderChildNum").html());
							var singleNum = totalNum.mod(2);
							$("#singleTr").attr("style","display;");
							$("#singlePrice").html((result.singlePrice).toFixed(2));
							$("#totalSingle").html(((new Number(result.singlePrice)).multiply(singleNum)).toFixed(2));
							totalPrice += (new Number(result.singlePrice)).multiply(singleNum);
						}else{
							$("#singleTr").attr("style","display:none;");
						}
						$("#originTotalPrice").val((new Number(totalPrice)).toFixed(2));
						$(".totalPrice").html(bookPrice.getTotalPrice);
					}
				});
			}
		},

		refreshPackage : function(productId,bookDate,packageId) {
			if(typeof(productId) == undefined || typeof(bookDate) == undefined|| typeof(packageId) == undefined || productId == '' || bookDate == '' || packageId == ''){
				tipWindow.hideWin();
			}else{
				tipWindow.hideWin();
				$.ajax({
					url: basepath + '/product/book/packagePriceInfo/'+ productId + "/" + bookDate + "/" + packageId,
					type: 'POST',
					success: function(result){
						var totalPrice = new Number(0);
						if(result.packagePrice != null){
							$("#packageTr").attr("style","display;");
							$("#packagePrice").html((new Number(result.packagePrice)).toFixed(2));
							$("#totalPackage").html((new Number(result.packagePrice)).toFixed(2));
							totalPrice += (new Number(result.packagePrice));
						}else{
							$("#packageTr").attr("style","display:none;");
						}
						$("#originTotalPrice").val((new Number(totalPrice)).toFixed(2));
						$(".totalPrice").html(bookPrice.getTotalPrice);
					}
				});
			}
		}
}