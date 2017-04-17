 var orderNoReg =   /^\d{0,12}$/;

$(function(){
	
	submitFunc();
	
	$("#localOrderNo").blur(function(){
		var orderNo = trim(this);
		if( !isOrderNoValid(orderNo)){
			$(".orderNoTips").fadeIn("normal", function(){
				setTimeout(function(){
					$(".orderNoTips").fadeOut("fast");
				}, 2000);
			});		
		}		
	});
	
	$("#travellerNames").blur(function(){
		var tmp=$(this).val().trim();
		$("#travellerNames").val(tmp);
	})
	
	
	$("#productTitle").blur(function(){
		var tmp=$(this).val().trim();
		$("#productTitle").val(tmp);
	})
	
	
	
	
	
	$("#searchBtn").click(function(){
		$("#pageNo").val(1);
		$("#pageSize").val(10);
		submitFunc();
	})

	})



		function submitFunc(){			
			var criteria = $("form").serializeObject();
			criteria.status = $("div.dropdown").find("li.active").attr("data-val");
			criteria.productNature = $("div.pieceType").find("li.active").attr("data-val");
			if(isSearchParamsValid(criteria) ){
				$.ajax({
					type : "POST",
					url : basepath + "/localorder/travel/search",
					data : JSON.stringify(criteria),
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					dataType : "html",
					success : function(result) {
						var resultArray = result.split("<-split->");
						var data = resultArray[0];
						var pagination = resultArray[1];
						$(".commonTab tbody").html(data);
						$("#pagination").html(pagination);
						
						$(".productTitle").each(function(index){
							var productTitle=$(this).html().replace(new RegExp(" ",'gm'),"&nbsp;")
							$(this).html(productTitle);	
						})
						
					$(".costPrice").each(function(index){
					var costPrice=$(this).html().replace(new RegExp(" ",'gm'),"&nbsp;")
					$(this).html(costPrice);	
				})
						
					},
				});
			}

		}


     function isSearchParamsValid(criteria){
    		if( isOrderNoValid(criteria.orderNo) )
    			return true;
    		else
    			return false;
     }
     
     function isOrderNoValid(orderNo){
    		if( orderNo != "" && !orderNoReg.test(orderNo) )
    			return false;
    		else
    			return true;
    	}

     $.fn.serializeObject = function(){
    	    var o = {};
    	    var a = this.serializeArray();
    	    $.each(a, function() {
    	        if (o[this.name]) {
    	            if (!o[this.name].push) {
    	                o[this.name] = [o[this.name]];
    	            }
    	            o[this.name].push(this.value || '');
    	        } else {
    	            o[this.name] = this.value || '';
    	        }
    	    });
    	    return o;
    	};
    	
    	function trim(selector){
    		return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
    	}