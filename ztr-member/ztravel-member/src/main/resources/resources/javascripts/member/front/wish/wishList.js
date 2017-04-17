/**
 *
 */
		$(function(){
			$('.partner .partner-avatar li').hover(function(){
				var num=$(this).index();
				var leftvalue=57*(num-1);
				if(leftvalue<0){
					leftvalue=0;
					$(this).parent().siblings("ul").children("li").eq(num).addClass('private-bg-left');
				}
				if(leftvalue>204){
					leftvalue=204;
                    $(this).parent().siblings("ul").children("li").eq(num).addClass('private-bg-right');
				}
				$(this).parent().siblings("ul").children("li").eq(num).css("left",leftvalue);
				$(this).parent().siblings("ul").children("li").eq(num).toggle();
			});

			$('.private-letter-01').hover(function(){
                $(this).toggle();
            });

            $('.deletebtn').click(function(){
                $(this).siblings('.isdelete').show(300);
            });
            $('.cancel').click(function(){
                $(this).parent('.isdelete').hide(300);
            });
            $('.confirm').click(function(){
            	$(this).parent('.isdelete').hide(300);
            	$(this).parent('.related').hide(300);
            	deleteWish($(this).parents('.related').siblings('input[name="wishId"]').val());
            });
		});

		function deleteWish(wishId){

			$.ajax({
				type: "POST",
				url: basepath + '/member/wish/delete',
				data: {"wishId":wishId},
				dataType : "json",
				success: function (resp) {
					if( resp.res_code == "SF_MEMB_1002" ){
						_paq.push(['trackEvent', 'mywishpage', 'ztrremovewishlist', wishId, 'success']);
						window.location.href = basepath + "/member/wish/list";
			    	}else{
			    		alert("网络异常，请稍后重试");
			    		_paq.push(['trackEvent', 'mywishpage', 'ztrremovewishlist', wishId, 'fail']);
			    	}
				}
			});
		}

		function toProductList(){
	      window.location.href = memberServer + "/product/list?departure=上海&destination=&destLevel=1";
	    };