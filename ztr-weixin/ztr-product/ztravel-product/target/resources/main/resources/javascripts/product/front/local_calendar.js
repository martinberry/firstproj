  var costIndex=0;
  var adultnum=1;
  var childnum=1;
$(function(){
	 /** jquery ui datepicker本地化 **/
	  $.datepicker.regional['zh-CN'] = {
	            closeText: '关闭',
	            prevText: '<上月',
	            nextText: '下月>',
	            currentText: '今天',
	            monthNames: ['一月','二月','三月','四月','五月','六月',
	                '七月','八月','九月','十月','十一月','十二月'],
	            monthNamesShort: ['一','二','三','四','五','六',
	                '七','八','九','十','十一','十二'],
	            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
	            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
	            dayNamesMin: ['日','一','二','三','四','五','六'],
	            weekHeader: '周',
	            dateFormat: 'yy-mm-dd',
	            firstDay: 1,
	            isRTL: false,
	            showMonthAfterYear: true,
	            yearSuffix: '年'};
	        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
	        $( "#visaCalendar" ).datepicker({
	            firstDay: 1,
	            minDate: new Date(),
	            onSelect: function(dateText, inst) {

	            }
	        });
	        
	        initCalendar();
	    	
	     $(".costPrice").click(function(){	
	    	 obj=this;
	    	 $(".costPrice").removeClass("active");
	    	 $(".costPrice").removeClass("priceActive");
	    	 costIndex= $(".priceGroup li").index($(this));
	    	 $(obj).addClass("active");
	    	 $(obj).addClass("priceActive");
	    	 $(".panelWrap-box .priceNum").attr("style",'display:none');
	    	 $(".panelWrap-box-child .priceNum").attr("style",'display:none');
	    	 $(".panelWrap-box-child").attr("style",'display:block;');
	    	 $(".panelWrap-box .priceNum").eq(costIndex).removeAttr("style");
	    	 adultnum=$(".panelWrap-box li.active a").html();
	    	 var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
	    	 var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
	    	 var totalPrice;
	    	 if(childPrice=="''"){
	    		 $(".panelWrap-box-child").attr("style",'display:none;');
	    		 totalPrice =adultPrice*adultnum;
	    		
	    	 }else{
	    		 $(".panelWrap-box-child .priceNum").eq(costIndex).removeAttr("style");	
	    		 childnum=$(".panelWrap-box-child li.active a").html();
	    		 totalPrice=adultPrice*adultnum+childPrice*childnum;
	    	 }	
	    	 totalPrice=totalPrice.toFixed(2);
	    	 $("#totalPrice").html("￥"+totalPrice);
	    	});
	     
	      $(".dropdown-menu.adultnum").click(function(){
	    	   adultnum=$(this).find("li.active a").html();    	  
	    	   costIndex= $(".priceGroup li").index($(".costPrice.active"));
	    	   var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		       var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		   	   var totalPrice;
		       if(childPrice=="''"){
		   		   totalPrice=adultPrice*adultnum;
		   	   }else{
		   		 childnum=$(".panelWrap-box-child li.active a").html();
		   		totalPrice=adultPrice*adultnum+childPrice*childnum;
		   	   }
		   	   totalPrice=totalPrice.toFixed(2);
	    	   $("#totalPrice").html("￥"+totalPrice);
	      })
	      
	       $(".dropdown-menu.childnum").click(function(){
	    	   childnum=$(this).find("li.active a").html();
	    	   adultnum=$(".panelWrap-box li.active a").html();
	    	   costIndex= $(".priceGroup li").index($(".costPrice.active"));
	    	   var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		       var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		   	   var totalPrice=adultPrice*adultnum+childPrice*childnum;
		   	   totalPrice=totalPrice.toFixed(2);
	    	   $("#totalPrice").html("￥"+totalPrice);
	      })

	      
	      $("#orderinstance").click(function(){	          
	  	    var url=memberServer +"/product/book/tobookpage";
	  	    var productId=$("#productId").val();  
	  	    var productNature=$("#productNature").val();	  
	    	var month=parseInt($(".ui-datepicker-current-day").attr("data-month"))+1;
		    month=addzero(month);
		    var day=$(".ui-datepicker-current-day a").text();
		    day=addzero(day);
	    	var year=$(".ui-state-active").parents("td").attr("data-year");
	    	var currentdate=new Date();
	    	var currentyear=currentdate.getFullYear();
	    	var currentmonth=currentdate.getMonth()+1;
	    	var currentday=currentdate.getDate();	    	
	    	   	var bookDate=year+'-'+month+'-'+day;  
		    	var adultNum=$(".panelWrap-box li.active a").html();
		  	    var childNum=$(".panelWrap-box-child li.active a").html();
		  	    var costIndex= $(".priceGroup li").index($(".costPrice.active"));
		  	    var costPriceId= $("input[name='costPriceId']").eq(costIndex).val();  	    
		  	  if(year>currentyear||(year==currentyear&&month>currentmonth)||(year==currentyear&&month==currentmonth&&day>=currentday)){
		  	    var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
		  		$form.append('<input name="productId" value="'+ productId +'"/>');
		  		$form.append('<input name="bookDate" value="'+bookDate +'"/>');
		  		$form.append('<input name="adultNum" value="'+ adultNum +'"/>');
		  		$form.append('<input name="childNum" value="'+ childNum +'"/>');
		  		$form.append('<input name="costPriceId" value="'+costPriceId+'"/>');
		  		$form.append('<input name="productNature" value="'+productNature+'"/>');
		  		$form.appendTo('body');
		  		_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', productId,$form.serialize()]);
		  		$form.submit();	    	
	    	}else{
	    		$(".dateTips").fadeIn("normal", function(){
					setTimeout(function(){
						$(".dateTips").fadeOut("fast");
					}, 2000);
				});
	    	}	 
	      })     
	      
	      
	      $(".fixOrder").click(function(){
    
	    var url=memberServer +"/product/book/tobookpage";
	    var productId=$("#productId").val();  
	    var productNature=$("#productNature").val();	  
	    var month=parseInt($(".ui-datepicker-current-day").attr("data-month"))+1;
	    month=addzero(month);
	    var day=$(".ui-datepicker-current-day a").text();
	    day=addzero(day);
	    var year=$(".ui-datepicker-current-day").attr("data-year");
	    var bookDate=year+'-'+month+'-'+day;  
	    var adultNum=$(".panelWrap-box li.active a").html();
	    var childNum=$(".panelWrap-box-child li.active a").html();
	    var costIndex= $(".priceGroup li").index($(".costPrice.active"));
	    var costPriceId= $("input[name='costPriceId']").eq(costIndex).val();
	    if(year>currentyear||(year==currentyear&&month>currentmonth)||(year==currentyear&&month==currentmonth&&day>=currentday)){
		    var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
			$form.append('<input name="productId" value="'+ productId +'"/>');
			$form.append('<input name="bookDate" value="'+bookDate +'"/>');
			$form.append('<input name="adultNum" value="'+ adultNum +'"/>');
			$form.append('<input name="childNum" value="'+ childNum +'"/>');
			$form.append('<input name="costPriceId" value="'+costPriceId+'"/>');
			$form.append('<input name="productNature" value="'+productNature+'"/>');
			$form.appendTo('body');
			_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', productId,$form.serialize()]);
			$form.submit();
	    }else{
	    	$(".dateTips").fadeIn("normal", function(){
				setTimeout(function(){
					$(".dateTips").fadeOut("fast");
				}, 2000);
			});	    	
	    }
})
	//数据回填
	dataBind();
});





      

function initCalendar(){	
	            if($(".priceGroup li").size()==0){
	            	$(".panelWrap-title").eq(1).attr("style",'display:none;');
	            	$(".panelWrap-head").attr("style",'display:none;');
	            	$(".panelWrap-price").attr("style",'display:none;');
	            	$(".visaSubmit").attr("style","display:none;");
	            	
	            }else{
	                $(".priceGroup li:first").addClass("priceActive");
		            $(".priceGroup li:first").addClass("active");
		            $(".panelWrap-box .priceNum").eq(0).removeAttr("style");
		           
			    	var initadultPrice=$(".panelWrap-box .priceNum").eq(0).html().replace("￥","").replace("<span>/人</span>","");
			    	var initchildPrice=$(".panelWrap-box-child .priceNum").eq(0).html().replace("￥","").replace("<span>/人</span>","");	
		            if(initchildPrice=="''"){
		            	$(".panelWrap-box-child").attr("style",'display:none;');
		            }else{
		            	 $(".panelWrap-box-child .priceNum").eq(0).removeAttr("style");
		            }
			    	var totalPrice=initadultPrice*2;
			    	totalPrice=totalPrice.toFixed(2);
			    	$("#totalPrice").html("￥"+totalPrice);
	            }
	
	      }

	function dataBind(){
		var selectDay = $("#selectedDay").val();
		var adultNum = $("#adultNum").val();
		var childNum = $("#childNum").val();
		var costPriceId = $("#costPriceId").val();
		if(typeof(selectDay) != undefined && selectDay != ''){
			var tempDay = selectDay.split("-");
			var year = new Number(tempDay[0]);
			var month = new Number(tempDay[1]).subtract(1);
			var day = new Number(tempDay[2]);
			$( "#visaCalendar" ).datepicker("setDate",new Date(year,month,day,0,0,0));
		}
		if(typeof(costPriceId) != undefined && costPriceId != ''){
			$("ul.priceGroup li").each(function(){
				var costIndex = $("ul.priceGroup li").index($(this));
				var tempPid = $("input[name='costPriceId']").eq(costIndex).val();
				if(tempPid == costPriceId){
					$(this).click();
				}
			});
		}else{
			$("ul.priceGroup li").removeClass("priceActive");
			$("ul.priceGroup li:first").addClass("priceActive");
		}
		if(typeof(adultNum) != undefined && adultNum != ''){
			$("div.panel-wrap").find("ul.adultnum li").each(function(){
				var num = new Number($(this).find("a").html());
				if(num == adultNum){
					$(this).siblings("li").removeClass("active");
					$(this).addClass("active");
					$(this).closest("div.dropdown").find("span.activeFonts").html(adultNum);
				}
			});
		}
		if(typeof(childNum) != undefined && childNum != ''){
			$("div.panel-wrap").find("ul.childnum li").each(function(){
				var num = new Number($(this).find("a").html());
				if(num == childNum){
					$(this).siblings("li").removeClass("active");
					$(this).addClass("active");
					$(this).closest("div.dropdown").find("span.activeFonts").html(childNum);
				}
			});
		}
		countPrice();
	}
	
	function countPrice(){
		var adultNum = typeof($("div.panel-wrap").find("ul.adultnum li.active a").html()) == 'undefined'? 0 :$("div.panel-wrap").find("ul.adultnum li.active a").html();
   	 	var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		var totalAdult = 0;
		var totalChild = 0;
		if(adultNum != 0){
			totalAdult = new Number(adultPrice).multiply(adultNum);
		}
		if($("#childDiv").attr("style") == "display"){
			var childNum = typeof($("div.panel-wrap").find("ul.childnum li.active a").html()) == 'undefined'? 0 : $("div.panel-wrap").find("ul.childnum li.active a").html();
			var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
			if(childNum != 0){
				totalChild = new Number(childPrice).multiply(childNum);
			}
		}
		var total = totalChild + totalAdult;
		return total.toFixed(2);
	}
	
	
	function addzero(day){
		day=parseInt(day);
		if(day<10){
			day='0'+day;
		}
		return day;
	}
