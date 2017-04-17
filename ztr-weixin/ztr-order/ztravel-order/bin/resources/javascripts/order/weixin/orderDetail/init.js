/**
 *
 */
var creditData = [];
var idCard = {name:"身份证",type:"IDCARD",num:""};
var passPort = {name:"护照",type:"PASSPORT",num:""};
var hkPass = {name:"港澳通行证",type:"GANGAOPASS",num:""};
var dp;
var orderDetailInit = {
		initRootFont : function(){
			var root = document.getElementsByTagName("html")[0],
            w = window.innerWidth >= 640 ? 640 : window.innerWidth;
		    root.style.fontSize = (w / 320) * 20 + "px";
		    root.style.minHeight = window.innerHeight + "px";
		},
		initPrg : function(){
			$("#prg-now").click(function(event){
	            $(".prg-dialog").toggleClass("hidden");

	            if(!$(".prg-dialog").hasClass("hidden")){
	                $(document).click(function(){
	                    $(".prg-dialog").addClass("hidden");
	                });
	            }
	            event.stopPropagation();
	        });
		},
		initPassengerEdit : function(){
			 $(".row-edt-traveler").click(function(){
				 $(".travelerInfo").hide();
		         $(".order-traveler-edt").show();
				 var orderStatus = $("#orderStatus").val();
				 if(orderStatus != "CANCELED"){
					 $(".black_overlay-sec").fadeIn("fast");
					 $(".order-traveler-edt").slideDown("fast");

					 $(".travel-edt-btn .edt-cancel").click(function(){
						 passenger.init();
						 $(".order-traveler-edt").slideUp("fast");
						 $(".black_overlay-sec").fadeOut("fast");
						 $(".travelerInfo").show();
			             $(".order-traveler-edt").hide();
					 });
					 $(".travel-edt-btn .edt-save").click(function(){
						 $(".black_overlay-sec").fadeOut("fast");
						 passenger.update();
						 $(".travelerInfo").show();
			             $(".order-traveler-edt").hide();
					 })
				 }
		        });
		},
		initContactorEdit : function(){
			
			
			
			 $(".row-edt-pop").click(function(){
				 $("#contactorContainer div.row-content").hide();
		         $(".order-pop-edt").show();
				 var orderStatus = $("#orderStatus").val();
				 if(orderStatus != "CANCELED"){
					 $(".black_overlay").fadeIn("fast");
					 $(".order-traveler-edt").hide("fast");
					 $(".black_overlay-sec").hide("fast");
					 $(".order-pop-edt").slideDown("fast");

					 $(".pop-edt-btn .edt-cancel").click(function(){
						 contactor.init();
						 $("#contactorContainer div.row-content").show();
			             $(".order-pop-edt").hide();
						 $(".order-pop-edt").slideUp("fast");
						 $(".black_overlay").fadeOut("fast");
					 });
					 $(".pop-edt-btn .edt-save").click(function(){
						$(".black_overlay").fadeOut("fast");
					 	contactor.update();
					 	$("#contactorContainer div.row-content").show();
		                $(".order-pop-edt").hide();
					 })
				 }
		        });
		},
		initCreditType : function(){
			var isDomestic = $("#isDomestic").val();
			$("div.credential").click(function(){
				var $this = $(this);
				var currentName = $this.closest("div.order-traveler-box").find("div.credentialType").html();
				if(isDomestic == "true"){
					creditData = [idCard,passPort,hkPass];
				}else{
					creditData = [passPort,hkPass];
				}
				$(this).cusListPop({
	        		title: "请选择证件类型",
	        		scrollId: "cardType",
	        		data: creditData,
	        		reCreate: true,
	        		currentData: {name:currentName},
	        		callback: function() {
	        			$("#cardTypeWrapper").find("li").click(function(){
	        				if($(this).hasClass("current")){
	        					var currentType = $(this).attr("data-type");
	        					if(($this.closest("div.traveler-edt-box").find("input[data-type='"+currentType+"']")).length > 0){
	        						var cardNum = $this.closest("div.traveler-edt-box").find("input[data-type='"+currentType+"']").attr("data-num");
	        						var cardDead = $this.closest("div.traveler-edt-box").find("input[data-type='"+currentType+"']").attr("data-dead");
	        						$this.closest("div.traveler-edt-box").find("input.credentialNum").val(cardNum);
	        						$this.closest("div.traveler-edt-box").find("div.dead-time").attr("data-time",cardDead);
	        						$this.closest("div.traveler-edt-box").find("div.dead-time").html(cardDead);
	        					}else{
	        						$this.closest("div.traveler-edt-box").find("input.credentialNum").val($(this).attr("data-num"));
	        						$this.closest("div.traveler-edt-box").find("div.dead-time").attr("data-time","2020-01-01");
	        						$this.closest("div.traveler-edt-box").find("div.dead-time").html("2020-01-01");
	        					}
	        					$this.closest("div.traveler-edt-box").find("div.credentialType").attr("credentialType",$(this).attr("data-type"));
	        					$this.closest("div.traveler-edt-box").find("div.credentialType").html($(this).attr("data-name"));
	        					$this.closest("div.traveler-edt-box").find("input.credentialNum").attr("data-ct",$(this).attr("data-name"));
	        					if($(this).attr("data-type") == "IDCARD"){
	        						$this.closest("div.traveler-edt-box").find("input.credentialNum").attr("data-cv","required,idCard");
	        					}else if($(this).attr("data-type") == "PASSPORT"){
	        						$this.closest("div.traveler-edt-box").find("input.credentialNum").attr("data-cv","required,passPort");
	        					}else if($(this).attr("data-type") == "GANGAOPASS"){
	        						$this.closest("div.traveler-edt-box").find("input.credentialNum").attr("data-cv","required,hkPass");
	        					}
	        				}
	        				$("#cardTypePopContainer").popup("close");
	        			});
	        		}
	        	});
			});
		},
		initDatePicker : function(){
			 $("div.traveler-edt-box").find("div.brithday,div.credentialsDeadLine").click(function(){
				 var eleId = $(this).attr("id");
				 var $this = $(this).find("div.datetime");
				 var dateType = $this.attr("date-type");
				 var defaultDay = 1;
				 var defaultMonth=0;
				 var defaultYear;
				 var defaultStartYear;
				 if(dateType == "deadTime"){
					 defaultYear = 2020;
					 defaultMonth = 0;
					 defaultEndYe =2035;
					 defaultStartYear= new Date().getYear() + 1900;
				 }else if(dateType == "brithDay"){
					 defaultEndYe = new Date().getYear() + 1900;
					 defaultYear = 1900;
					 defaultMonth = 0;
					 defaultStartYear = 1920;
				 }
				 if(typeof($this.attr("data-time")) != "undefined" && null != $this.attr("data-time") &&  $this.attr("data-time") != ""){
					 var defaultDateTime = new Date($this.attr("data-time"));
					 defaultYear = defaultDateTime.getYear() + 1900;
					 defaultMonth = defaultDateTime.getMonth();
					 defaultDay = defaultDateTime.getDate();
				 }
				 /**
				  * 提示弹窗初始化，不能删除，否则会出现日期选择出现一次错误后，会一直提示
				  * */
				 $("#validateTip").popup({
						afterclose: function(){
							$("#"+eleId+"Datepicker").popup({
								afterclose: function() {
									return;
								}
							});
						}
					});
				 dp = new DatepickerScroll("#"+eleId, {
			        	startYear: defaultStartYear,
						endYear: defaultEndYe,
						defaultDate: new Date(defaultYear,defaultMonth,defaultDay),
						cancelCallback: function(){
							console.log(1);
						},
						confirmCallback: function() {
							var year = $("#"+eleId+"Datepicker").find("#yearWrapper li.current").attr("data-year");
							var month = $("#"+eleId+"Datepicker").find("#monthWrapper li.current").attr("data-month");
							var day = $("#"+eleId+"Datepicker").find("#dayWrapper li.current").attr("data-day");
							var yearHtml = $("#"+eleId+"Datepicker").find("#yearWrapper li.current").html();
							var monthHtml = $("#"+eleId+"Datepicker").find("#monthWrapper li.current").html();
							var dayHtml = $("#"+eleId+"Datepicker").find("#dayWrapper li.current").html();
							var dateTimeHtml = yearHtml+monthHtml+dayHtml;
							var dateTime = new Date(year,month-1,day).Format("yyyy-MM-dd");
							var defaultDateTime = new Date(defaultYear,defaultMonth-1,defaultDay).Format("yyyy-MM-dd");
							if(dateType == "deadTime"){
								if(new Date(year,month-1,day) >= new Date()){
									$this.val(dateTimeHtml);
									$this.html(dateTimeHtml);
									$this.attr("data-time",dateTime);
								}else{
									$("#"+eleId+"Datepicker").popup({
										afterclose: function() {
											$("#tipMsg").html("有效期须大于当前时间");
											dp.setDate(new Date(defaultYear,defaultMonth,defaultDay));
											$("#validateTip").popup("open");
										}
									});
									$("#"+eleId+"Datepicker").popup("close");
								}
							}
							if(dateType == "brithDay"){
								if(new Date(year,month-1,day) < new Date()){
									$this.val(dateTimeHtml);
									$this.html(dateTimeHtml);
									$this.attr("data-time",dateTime);
								}else{
									$("#"+eleId+"Datepicker").popup({
										afterclose: function() {
											$("#tipMsg").html("出生日期须早于当前时间");
											dp.setDate(new Date(defaultYear,defaultMonth,defaultDay));
											$("#validateTip").popup("open");
										}
									});
									$("#"+eleId+"Datepicker").popup("close");
								}
							}
						}
			        }).init();
				 	console.log([defaultYear,defaultMonth,defaultDay]);
		        	dp.setDate(new Date(defaultYear,defaultMonth,defaultDay));
		        	$("#"+eleId+"Datepicker").popup("open");
		        });
		},
		initToPay : function(){
			$("a.toPay").click(function(){
				var orderId = $(this).attr("data-order");
				_paq.push(['trackEvent', 'myorderpage', 'ztrselectpayorder']);
				if(wxServer.charAt(wxServer.length - 1) == '/'){
					window.location.href = wxServer + "weixin/orderPay/selectPayType?orderId=" + orderId;
				}else{
					window.location.href = wxServer + "/weixin/orderPay/selectPayType?orderId=" + orderId;
				}
			});
		},
		initToComment : function(){
			$("a.toEvaluate").click(function(){
				var orderId = $(this).attr("data-order");
				window.location.href = wxServer + "/order/weixin/comment/edit/" + orderId;
			});
		},
		initStateShow:function(){
			$('.detailBtn').click(function(){
	            if(!$(this).hasClass("heightInherit")){
	                $('.state-detail').css("height","inherit");
	                $(this).addClass("heightInherit");
	                $(this).text("收起");
	            }else{
	                $('.state-detail').css("height","2rem");
	                $(this).removeClass("heightInherit");
	                $(this).text("详情");
	            }
	        })
		}

}