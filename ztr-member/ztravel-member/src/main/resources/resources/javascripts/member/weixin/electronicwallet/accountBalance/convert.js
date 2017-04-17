/**
 *
 */
var isMobile = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
var convert = {
		init : function(){
			FastClick.attach(document.body);
			// 设置页面根字大小
	        var root = document.getElementsByTagName("html")[0],
	        w = window.innerWidth >= 640 ? 640 : window.innerWidth;
	        root.style.fontSize = (w / 320) * 20 + "px";
	        root.style.minHeight = window.innerHeight + "px";
	        $(".convert-cnt > ul > li").click(function(){
	            $(this).siblings().removeClass("current-item");
	            $(this).addClass("current-item");
	        });
	        $("#mobile").blur(function(){
	        	var mobile = escape(trim("#mobile"));
	        	if(mobile != '' && !isMobile.test(mobile)){
	        		$("#commonErrorCont").html("手机格式错误");
	        		$("#commonError").popup("open");
	        	}
	        });
	        $("div.convert-wrap a.ui-link").click(function(){
	        	convert.confirmConvert();
	        });
		},
		confirmConvert : function(){
			var convertUrl = wxServer + "/account/weixin/convert";
			var mobile = escape(trim("#mobile"));
			var convertAmount = $("div.convert-wrap").find("li.current-item").attr("value");
			if(convertAmount < 10){
				$("#commonErrorCont").html("10块钱以上才能兑换电话卡,攒攒呗");
        		$("#commonError").popup("open");
        		return;
			}
			$("div.convert-wrap a.ui-link").addClass("forbid-link");
			$("div.convert-wrap a.ui-link").removeClass("ui-link");
			$.ajax({
				url : convertUrl,
				type : 'POST',
				data:{mobile:mobile,amount:convertAmount},
		       	dataType:"json",
				success : function(result) {
					if(result.res_code == 'SF_WXACCOUNT_CONVERT_0001'){//成功
						window.location.href = wxServer + "/accountBalance/index";
					}else if(result.res_code == 'FF_WXACCOUNT_CONVERT_0003'){
						$("#commonErrorCont").html("手机号为空");
		        		$("#commonError").popup("open");
		        		$("div.convert-wrap a").addClass("ui-link");
		    			$("div.convert-wrap a").removeClass("forbid-link");
					}else if(result.res_code == 'EF_MEMB_1017'){
						$("#commonErrorCont").html("手机格式错误");
		        		$("#commonError").popup("open");
		        		$("div.convert-wrap a").addClass("ui-link");
		    			$("div.convert-wrap a").removeClass("forbid-link");
					}else if(result.res_code == 'FF_WXACCOUNT_CONVERT_0004'){
						$("#commonErrorCont").html("兑换金额为空");
		        		$("#commonError").popup("open");
		        		$("div.convert-wrap a").addClass("ui-link");
		    			$("div.convert-wrap a").removeClass("forbid-link");
					}else if(result.res_code == 'FF_WXACCOUNT_CONVERT_0005'){
						$("#commonErrorCont").html("兑换金额为负数");
		        		$("#commonError").popup("open");
		        		$("div.convert-wrap a").addClass("ui-link");
		    			$("div.convert-wrap a").removeClass("forbid-link");
					}else{
						$("#commonErrorCont").html(result.res_msg);
		        		$("#commonError").popup("open");
		        		$("div.convert-wrap a").addClass("ui-link");
		    			$("div.convert-wrap a").removeClass("forbid-link");
					}
				}
			});
		}
}

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}