/**
 *
 */
var commonContactorList = [];
var contactor = {
		init : function(){
			$.ajax({
				type : "POST",
				url : "../book/getContactor",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					$("div.contactorInfo").html(result);
					bookInit.detailAddressCascade();
					contactor.initAddress();
					contactor.initValidate();
					$("div.contactorInfo span.guestNameIcon").click(function(){
		            	contactor.getComContactors($(this));
		            	$(this).next(".guestNameList").slideToggle("fast",function(obj){
		                	if($(this).css('display') !== 'none'){
		                		_paq.push(['trackEvent', 'orderpage', 'ztrtraveller']);
		                	}
		                });
		            });
				}
			});
		},
		initValidate : function(){
			$('div.contactorInfo input[data-cv]:not(".datepicker")').each(function(){
		  		$(this).blur(function(){
		  			var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
		  		});
		  	});
			/**
			 * 初始化联系人的地址验证。联系人第一次加载时，只会初始化省级的下拉，市／县都未加载；
			 * 因此需要点击完省级下拉的同时，去初始化市级的校验；点击完市级去初始化县级
			 * */
			$("div.contactorInfo").find(".dropdown-menu li a").click(function(){
		    	var thisHtml = $(this).html();
		    	var $thisParents = $(this).parents(".dropdown-menu li");
		        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
		        var checkResult = $(this).parents(".dropdown-menu").bookValidate({
	                validateAll: false
	            });
		        if($(this).closest(".dropdown").hasClass("province")){
		        	$(this).closest(".dropdown").siblings("div.city").find(".dropdown-menu li a").click(function(){
		        		var checkResult = $(this).parents(".dropdown-menu").bookValidate({
			                validateAll: false
			            });
		        		if($(this).closest(".dropdown").hasClass("city")){
		        			$(this).closest(".dropdown").siblings("div.county").find(".dropdown-menu li a").click(function(){
		        				var checkResult = $(this).parents(".dropdown-menu").bookValidate({
		        					validateAll: false
		        				});
		        			});
		        		}
		        	});
		        }
		    });
		},
		initAddress : function(){
			var defaultProvince = $('#hideProvince').val();
			var defaultCity = $('#hideCity').val();
			var defaultArea = $('#hideCounty').val();
			PROVINCEDD.select(defaultProvince);
			CITYDD.select(defaultCity);
			AREADD.select(defaultArea);
		},
		getComContactors : function($obj){
			commonContactorList = [];
			$.ajax({
				type : "POST",
				url : "../book/contactors",
				dataType : "json",
				contentType : "application/json",
				success : function(result){
					if(result.selectResult){
						$($obj).closest("div.order-conBlock").find('ul.guestNameList li').remove();
						if(null != result.contactors && result.contactors.length > 0){
							commonContactorList = result.contactors ;
							for(var i = 0 ; i < commonContactorList.length ; i ++){
								var liObj = "<li data-num="+i+" data-conname="+result.contactors[i].contactor+">"+result.contactors[i].contactor+"</li>";
								$($obj).closest("table.order-linkManTable").find('ul.guestNameList').append(liObj);
							}
						}else{
							var liObj = "<li data-num=-1>没有常用联系人</li>";
							$($obj).closest("table.order-linkManTable").find('ul.guestNameList').append(liObj);
						}
						$($obj).closest("table.order-linkManTable").find('ul.guestNameList li').click(function(){
							contactor.fillContactor($(this));
			            	$(this).parents('ul.guestNameList').slideUp("fast");
			            	$(this).closest('table.order-linkManTable').find('div.errorBox').remove();
			            	$(this).closest('table.order-linkManTable').find('input.errorStyle').removeClass("errorStyle");
			            });
					}else{
						console.log("error");
					}
				}
			});
		},
		fillContactor : function($obj){
			$obj.closest('tbody').find('ul.dropdown-menu li').attr("data-number","");
			var index = $($obj).data("num");
			if(index >= 0){
				var contactor = commonContactorList[index];
				if(contactor){
					$obj.closest('tbody').find("#contactorName").val(contactor.contactor);
					$obj.closest('tbody').find("#contactorPhone").val(contactor.phone);
					$obj.closest('tbody').find("#contactorEmail").val(contactor.email);
					$obj.closest('tbody').find("#contactorAddress").val(contactor.address);
					PROVINCEDD.select(contactor.province);
					CITYDD.select(contactor.city);
					AREADD.select(contactor.county);
				}
			}
		},
		clean : function(){
			$("table.order-linkManTable input").val("");
				 PROVINCEDD.init('');
				 PROVINCEDD.select('北京市');
		}
}