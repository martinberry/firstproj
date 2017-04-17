/**
 *
 */

var coupon = {
		refresh: function(){//刷新建单页可选择的代金券信息
			var criteria = {};
			criteria.bookDate = $("#bookDate").val();
			criteria.productId = $("#productId").val();
			criteria.adultNum = $("#orderAdultNum").html();
			criteria.childNum = $("#orderChildNum").html();
			criteria.packageId = $("#packageId").val();
			criteria.productNature = $("#nature").val();
			criteria.costPriceId = $("#costPriceId").val();
			$.ajax({
				type : "POST",
				url : "../book/refreshCoupons",
				data : JSON.stringify(criteria),
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					var couponsInfoArray = result.split("<-split->");
					var coupons = couponsInfoArray[0];
					var couponPriceInfo = couponsInfoArray[1];
					$("div.coupons").html(coupons);
					$("#couponPriceInfo").html(couponPriceInfo);
					coupon.initCouponSelect();
					$("#totalPrice").html(bookPrice.getTotalPrice());
				}
			});
		},
		initCouponSelect:function(){
			 //代金券勾选
			$("#couponCheckBox").click(function(){
				$(this).toggleClass("active");
				if(!($(this).hasClass("active")) && $("#couponCheckBox").closest("tr").find("div.dropdown").hasClass("open")){
					$("#couponCheckBox").closest("tr").find("div.dropdown").removeClass("open");
					$("#couponCheckBox").closest("tr").find("div.dropdown ul.dropdown-menu").slideUp("fast");
				}
		    });
			$("body").on("click","div .additional .dropdown-menu li a",function(){
				var thisHtml = $(this).html();
		        var $thisParents = $(this).parents(".dropdown-menu li");
		        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
		        $("#couponSub").html((new Number($(this).parent("li").data("val"))).toFixed(2));
		        $thisParents.addClass("active");
		        $thisParents.siblings().removeClass("active");
		        $("#couponName").html($(this).parent("li").data("poname"));
		        $("#equalTo").html(($(this).parent("li").data("val")).toFixed(2));
		        if($("#couponPrice").attr("style").indexOf("none") < 0){
		        	$("#totalPrice").html(bookPrice.getTotalPrice());
		        }
			});
			$("body").on("click","div .coupon",function(){
				if($("div .coupon").find('label.active').length > 0){
		    		$("div .coupon").parents('th').siblings('td').find('div.dropdown').removeClass('disabled');
		    		$(this).parents('th').siblings('td').find('div.dropdown a.dropdownBtn').attr("data-toggle",'dropdown');
		    		$("#couponSub").html($("div .coupon").parents('th').siblings('td').find('div.dropdown li.active').data("val").toFixed(2));
		    		$("#couponPrice").attr("style","display;");
		    	}else{
		    		$("div .coupon").parents('th').siblings('td').find('div.dropdown').addClass('disabled');
		    		$(this).parents('th').siblings('td').find('div.dropdown a.dropdownBtn').attr("data-toggle",'');
		    		$("#couponPrice").attr("style","display:none;");
		    		$("#couponSub").html(0.00);
		    	}
		    	$("#totalPrice").html(bookPrice.getTotalPrice());
			});
		},
}

