/**
 *
 */

var coupon = {
		initSelect : function(){
			$("#couponContainer").find("input[type='checkbox'],span.couponspan").click(function(){
				if($(this).is("span")){
					$(this).closest("div").find("input[type='checkbox']").prop("checked", !$(this).closest("div").find("input[type='checkbox']").prop("checked"));
				}
				if($(this).closest("div").find("input[type='checkbox']").prop("checked")){
					$("#couponContainer div.couponList").attr("style","display");
				}else{
					$("#couponContainer div.couponList").attr("style","display:none;");
				}
				price.showPriceInfo();
			});
		},
		refresh : function(){
			var criteria = {};
			criteria.bookDate = $("#bookDate").val();
			criteria.productId = $("#productId").val();
			criteria.adultNum = $("#adultNum").val();
			criteria.childNum = $("#childNum").val();
			criteria.packageId = $("#packageId").val();
			criteria.productNature = $("#nature").val();
			criteria.costPriceId = $("#costPriceId").val();
			var url = wxServer + "/book/weixin/refreshCoupons";
			$.ajax({
				type : "POST",
				url : url,
				data : JSON.stringify(criteria),
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					$("#couponContainer").html(result);
					price.showPriceInfo();
					coupon.getCoupons();
					coupon.initSelect();
				}
			});
		},
		getCoupons : function(){
			$("div.couponList,#couponInvalidConfirm").click(function(){
				var criteria = {};
				criteria.bookDate = $("#bookDate").val();
				criteria.productId = $("#productId").val();
				criteria.adultNum = $("#adultNum").val();
				criteria.childNum = $("#childNum").val();
				criteria.packageId = $("#packageId").val();
				criteria.productNature = $("#nature").val();
				criteria.costPriceId = $("#costPriceId").val();
				var $this = $(this);
				var currentCoupon = $("#couponName").html();
				var url = wxServer + "/book/weixin/getCoupons";
				$.ajax({
					type : "POST",
					url : url,
					data : JSON.stringify(criteria),
					contentType:"application/json",
					dataType:'json',
					success : function(result) {
						if(result.selectResult){
							$($this).cusListPop({
								title: "请选择优惠券",
								scrollId: "couponType",
								data: coupon.convertCoupon(result.couponList),
								reCreate: true,
								currentData: {name:currentCoupon},
								callback: function() {
									$("#couponTypeWrapper").find("li").click(function(){
										if($(this).hasClass("current")){
											$("div.couponList").attr("data-couponid",$(this).attr("data-couponId"));
											$("#couponName").html($(this).attr("data-name"));
											$("#couponAmount").html($(this).attr("data-amount"));
											price.showPriceInfo();
										}
										$("#couponTypePopContainer").popup("close");
									});
								}
							});
						}
					}
				});
			});
		},
		convertCoupon : function(data){
			var coupons = [];
			if(data != null && data.length > 0){
				for(i = 0;i<data.length;i++){
					var coupon  = {};
					coupon.name = data[i].name;
					coupon.couponId = data[i].accountCouponId;
					coupon.amount = data[i].amount;
					coupons.push(coupon);
					}
				return coupons;
			}
		}

}