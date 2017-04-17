var allNumberValidator =/^-?[1-9]\d*$/;//整数
var numberValidator =/^(0|[1-9][0-9]*)$/;//非负整数

$(function(){

	var hasChange = false;

    $("body").delegate(".modifiedDeparture", "click", function(){
        $(".modifiedDeparture").hide();
        $(".saveCancelBtn").show();

        $(".commonTab.setGameParams").find("input").removeAttr("readonly");
    });

    $("body").delegate(".cancel", "click", function(){
        $(".modifiedDeparture").show();
        $(".saveCancelBtn").hide();

        $(".commonTab.setGameParams").find("input").attr("readonly", "readonly");
    });

    $("body").delegate(".save", "click", function(){

    	var $ve = $(this);
    	var $this = $ve,val = $.trim($this.val());

    	var bagLeftNum = $("#bagLeftNum").html();
    	var calendaryLeftNum = $("#calendaryLeftNum").html();
    	var bagChangeNum = $("#bagChangeNum").val();
    	var calendaryChangeNum = $("#calendaryChangeNum").val();
    	if (bagChangeNum != '') {
    		if (!allNumberValidator.test(bagChangeNum)) {
    			showFailMessage("包包余量增减请输入整数");
        		return ;
    		} else if (Number(bagChangeNum) + Number(bagLeftNum) < 0 ) {
    			showFailMessage("包包减少数量不得少于剩余数量");
        		return ;
    		}
    		hasChange = true;
    	}
    	if (calendaryChangeNum != '') {
    		if (!allNumberValidator.test(calendaryChangeNum)) {
    			showFailMessage("台历余量增减请输入整数");
        		return ;
    		} else if (Number(calendaryChangeNum) + Number(calendaryLeftNum) < 0 ) {
    			showFailMessage("台历减少数量不得少于剩余数量");
        		return ;
    		}
    		hasChange = true;
    	}

    	var oldCoupon1Weight = $("#oldCoupon1Weight").val();
    	var oldCoupon2Weight = $("#oldCoupon2Weight").val();
    	var oldCoupon3Weight = $("#oldCoupon3Weight").val();
    	var oldBagWeight = $("#oldBagWeight").val();
    	var oldCalendaryWeight = $("#oldCalendaryWeight").val();

    	var coupon1Weight = $("#coupon1Weight").val();
    	var coupon2Weight = $("#coupon2Weight").val();
    	var coupon3Weight = $("#coupon3Weight").val();
    	var bagWeight = $("#bagWeight").val();
    	var calendaryWeight = $("#calendaryWeight").val();

    	var sumWeight = 0;

    	if (coupon1Weight == "" || !numberValidator.test(coupon1Weight)) {
    		showFailMessage("50元代金券权重请输入整数");
    		return ;
    	} else if (oldCoupon1Weight != coupon1Weight) {
    		sumWeight = sumWeight + Number(coupon1Weight);
    		hasChange = true;
    	}
    	if (coupon2Weight == "" || !numberValidator.test(coupon2Weight)) {
    		showFailMessage("100元代金券权重请输入整数");
    		return ;
    	} else if (oldCoupon2Weight != coupon2Weight) {
    		sumWeight = sumWeight + Number(coupon2Weight);
    		hasChange = true;
    	}
    	if (coupon3Weight == "" || !numberValidator.test(coupon3Weight)) {
    		showFailMessage("800元代金券权重请输入整数");
    		return ;
    	} else if (oldCoupon3Weight != coupon3Weight) {
    		sumWeight = sumWeight + Number(coupon3Weight);
    		hasChange = true;
    	}
    	if (bagWeight == "" || !numberValidator.test(bagWeight)) {
    		showFailMessage("包包权重请输入整数");
    		return ;
    	} else if (oldBagWeight != bagWeight) {
    		sumWeight = sumWeight + Number(bagWeight);
    		hasChange = true;
    	}
    	if (calendaryWeight == "" || !numberValidator.test(calendaryWeight)) {
    		showFailMessage("台历权重请输入整数");
    		return ;
    	} else if (oldCalendaryWeight != calendaryWeight) {
    		sumWeight = sumWeight + Number(calendaryWeight);
    		hasChange = true;
    	}
    	if (sumWeight > 100) {
    		showFailMessage("各奖品权重之和不能大于100");
    		return ;
    	}

    	if (hasChange) {
    		var params = {};
    		params.bagChangeNum = bagChangeNum;
    		params.calendaryChangeNum = calendaryChangeNum;
    		params.coupon1Weight = coupon1Weight;
    		params.coupon2Weight = coupon2Weight;
    		params.coupon3Weight = coupon3Weight;
    		params.bagWeight = bagWeight;
    		params.calendaryWeight = calendaryWeight;
    		$.ajax({
    			type : "POST",
    			url : basepath + "/happy2016/modifyActivityAwardWeight",
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
    			data :  JSON.stringify(params),
    			dataType : "json",
    			success : function(result) {
    				if( result.res_code == "SUCCESS" ){
    					showFailMessage("修改成功");
    				} else {
    					showFailMessage("修改失败");
    				}
    				window.location.href=window.location.href;
    			}
    		});
    	} else {
    		showFailMessage("请确认需要修改的内容");
    	}
    });
});

function showFailMessage(message){
	$(".popupContainer").children("i").removeClass("passIcon");
	$(".popupContainer").children("i").addClass("warnIcon");
	$(".popupContainer-fonts").text(message);
	$("#messageDlg").modal("show");
}

function geneErrorEle($this, msg) {
	$this.addClass("verifyBox");
	if ($this.next("div.verifyStyle").length == 0) {
		$this.after($("<div class='verifyStyle'>"
					+"<i class='verifyIcon'></i><span class='verifyFonts'>"
					+msg+"</span></div>"));
	} else {
		$this.next("div.verifyStyle").find(".verifyFonts").html(msg);
	}
	setErrorElePosition($this);
}

function setErrorElePosition($this) {

	var pos = $this.data("cp"),
		$modalEle = $this.parents(".modal-body"),
		modalOffsetTop = 0,
		modalOffsetLeft = 0;

	if ($modalEle.length != 0) {  //  如果验证元素在modal内，需要考虑Modal本身的offset
		modalOffsetLeft = $modalEle.offset().left;
	}

	var height = $this.outerHeight(),
		width = $this.outerWidth(),
		offsetTop = $this.offset().top,
		offsetLeft = $this.offset().left - modalOffsetLeft;

	var $errEle = $this.next("div.verifyStyle"),
		err_height = $errEle.height(),
		err_outerHeight = $errEle.outerHeight(),
		err_width = $errEle.width(),
		err_outerWidth = $errEle.outerWidth();

	var w, left, top;

	if (pos == "right") {

		left = offsetLeft + width;
		$errEle.css({
			"width": err_outerWidth + "px",
			"left": left + "px",
			"top": offsetTop + "px",
			"padding-top": (height - err_height)/2 + "px",
			"padding-bottom": (height - err_height)/2 + "px"
		});

	} else if (pos == "top") {

		w = (width >= err_outerWidth) ? width : err_outerWidth;
		top = offsetTop - err_outerHeight;
		$errEle.css({
			"width": w + "px",
			"left": offsetLeft + "px",
			"top": top + "px"
		});

	} else if (pos == "left") {

		left = offsetLeft - err_outerWidth;
		$errEle.css({
			"width": err_outerWidth + "px",
			"left": left + "px",
			"top": offsetTop + "px",
			"padding-top": (height - err_height)/2 + "px",
			"padding-bottom": (height - err_height)/2 + "px"
		});

	} else {  //  其他情况均视为默认在下方显示

		w = (width >= err_outerWidth) ? width : err_outerWidth;
		$errEle.css({
			"width": w + "px",
			"left": offsetLeft + "px"
		});
	}
}