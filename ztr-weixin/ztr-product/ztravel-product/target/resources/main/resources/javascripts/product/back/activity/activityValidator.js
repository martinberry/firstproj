(function($){
	$.fn.bookValidate = function(){
		var validatedEle = $(this),result = true;
		/***自定义验证规则，可以根据需求进行扩展***/
		var couponNameReq = /^([a-zA-Z0-9|\u4e00-\u9fa5|\s]){1,100}$/;
		var positiveNumReq = /^[1-9]\d*$/;//正数
		var numberReq =/^(0|[1-9][0-9]*)$/;//非负整数
		var hourReq =/([0-1][0-9])|([2][0-3])$/;
		var minReq = /[0-5][0-9]$/;
		var userRangeReq =/^(((?:13\d|14\d|15\d|17\d|18\d)\d{8},)*)((?:13\d|14\d|15\d|17\d|18\d)\d{8})$/;
		var productRangeReq =/^(([Z]{1}\d{5},)*)([Z]{1}\d{5})$/;
		var pieceRangeReq =/^(([S]{1}\d{5},)*)([S]{1}\d{5})$/;
		/***找到所有需要自定义验证的元素***/
		if ($(this).attr("readonly") !== "readonly") {
			$(this).bind("keyup", function(event) {
				if ($.trim($(this).val()) != "") {
					cleanErrorEle($(this));
				}
			});
		}
		/*******对元素进行验证************/
		var $ve = $(this);
		if ($ve.data("cv") != '') {
			var validatorValues = $ve.attr("data-cv").split(',');  // 得到所有需要验证的类型[required,email,...]
			var validatorTitle = $ve.data("ct");  // 得到验证字段名
			var validatorPosition = $ve.data("cp");  //  得到错误消息显示位置，默认bottom
			validatorTitle = (validatorTitle == undefined) ? "" : validatorTitle;
			//验证元素为 input 类型
			if ($ve.is("input") && $ve.is(":visible")) {
				var $this = $ve,val = $.trim($this.val());
				for(var j = 0; j < validatorValues.length; j++) {
					/*********必填验证***************/
					if (validatorValues[j] == "required") {
						if (val == "") {
							geneErrorEle($this, validatorTitle+"不能为空");//生成报错信息DOM结构
							result = false;
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}else {
							cleanErrorEle($this);//清除报错信息DOM结构
						}
					}

					/*********代金券名称***************/
					if (validatorValues[j] == "couponName") {
						if (val != "" && !couponNameReq.test(val)) {
							geneErrorEle($this, "名称格式错误");//生成报错信息DOM结构
							result = false;
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}else {
							cleanErrorEle($this);//清除报错信息DOM结构
						}
					}

					if (validatorValues[j] == "positiveNum") {
						if (val != "" && !positiveNumReq.test(val)) {
							geneErrorEle($this, "输入正数");//生成报错信息DOM结构
							result = false;
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}else {
							$(this).val($.trim(val));
							cleanErrorEle($this);//清除报错信息DOM结构
						}
					}

					if (validatorValues[j] == "number") {
						if (val != "" && !numberReq.test(val)) {
							geneErrorEle($this, "输入数字");//生成报错信息DOM结构
							result = false;
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}else {
							$(this).val($.trim(val));
							cleanErrorEle($this);//清除报错信息DOM结构
						}
					}

					if (validatorValues[j] == "hour") {
						if (val != "" && !hourReq.test(val)) {
							geneErrorEle($this, "时间错误");//生成报错信息DOM结构
							result = false;
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}else {
							cleanErrorEle($this);//清除报错信息DOM结构
						}
					}

					if (validatorValues[j] == "minute") {
						if (val != "" && !minReq.test(val)) {
							geneErrorEle($this, "时间错误");//生成报错信息DOM结构
							result = false;
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}else {
							cleanErrorEle($this);//清除报错信息DOM结构
						}
					}
				}

			}
			//当前验证元素为textarea 类型
			if ($ve.is("textarea")) {
				var $this = $ve,val = $.trim($this.val());
				/*********需求备注验证*************/
				for(var j = 0; j < validatorValues.length; j++) {
				if (validatorValues[j] == "userRange") {//
					if (val != "" && !userRangeReq.test(val)) {
						geneErrorEle($this, "用户信息错误");
						result = false;
						break;
					} else {
						cleanErrorEle($this);
					}
				}

				if (validatorValues[j] == "required") {//
					if (val == "") {
						geneErrorEle($this, "必填");
						result = false;
						break;
					} else {
						cleanErrorEle($this);
					}
				}


				if (validatorValues[j] == "productRange") {//
					if (val != "" && !productRangeReq.test(val) && !pieceRangeReq.test(val)) {
						geneErrorEle($this, "支持产品名称格式不正确");
						result = false;
						break;
					} else {
						cleanErrorEle($this);
					}
				}

				if (validatorValues[j] == "remark") {//
					if (val.length > 500) {
						geneErrorEle($this, "最多500个字符");
						result = false;
						break;
					} else {
						cleanErrorEle($this);
					}
				}
				}
			}
		}
		return result;
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

	function cleanErrorEle($this) {
		if ($this.next("div.verifyStyle").length != 0) {
			$this.removeClass('verifyBox');
			$this.next('div.verifyStyle').remove();
		}
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

})(jQuery);





