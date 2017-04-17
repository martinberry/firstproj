(function($){
	$.fn.bookValidate = function(){
		var validatedEle = $(this),result = true;
		/***自定义验证规则，可以根据需求进行扩展***/
		var cellphoneReg = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/,
			emailReg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/,
			idCardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
			passPortReg = /^[A-Za-z0-9]{1,20}$/,
			hkPassReg = /^[a-zA-Z][0-9]{8}$/,//港澳通行证，字母开头，后面接8位数字
			streetReg = /^([(-|\_|\.)|a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|”|’]){1,100}$/,//街道地址验证
			remarkReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|!|！|”|’]){0,500}$/,//备注内容验证
			pyNameReg = /^[a-zA-Z|/.]{1,19}[\/]{1}[a-zA-Z|/.]{1,20}$/,//长度为40个字符内的中文名拼音
			chNameReg = /^[\u4e00-\u9fa5]+$/,
			enNameReg = /^[A-Za-z]+$/,//英文名
			contactorChNameReg = /^[\u4e00-\u9fa5|\.]{1,20}$/,
			contactorEnNameReg = /^[a-zA-z|\.]{1,40}$/;





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
					/*********手机号验证***************/
					if (validatorValues[j] == "cellphone") {
						if (val != "" && !cellphoneReg.test(val)) {
							geneErrorEle($this, "手机号格式错误");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********邮箱验证***************/
					if (validatorValues[j] == "email") {//邮箱验证
						if (val != "" && !emailReg.test(val)) {
							geneErrorEle($this, "邮箱格式错误");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********身份证验证*************/
					if (validatorValues[j] == "idCard") {//身份证验证
						if (val != "" && !idCardReg.test(val)) {
							geneErrorEle($this, "格式填写有误，请重新输入");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********护照验证*************/
					if (validatorValues[j] == "passPort") {//护照验证
						if (val != "" && !passPortReg.test(val)) {
							geneErrorEle($this, "格式填写有误，请重新输入");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********港澳通行证验证*************/
					if (validatorValues[j] == "hkPass") {//护照验证
						if (val != "" && !hkPassReg.test(val)) {
							geneErrorEle($this, "格式填写有误，请重新输入");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********中英文混合验证*************/
					if (validatorValues[j] == "contactor") {//
						if (val != "" && !(contactorChNameReg.test(val) || contactorEnNameReg.test(val))) {
							geneErrorEle($this, "请填写正确姓名");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********只能中文或者英文*************/
					if (validatorValues[j] == "chOrEnName") {//
						if (val != "" && !(chNameReg.test(val) || enNameReg.test(val))) {
							geneErrorEle($this, "请填写正确姓名");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}

					/*********英文名字验证*************/
					if (validatorValues[j] == "enName") {//
						if ((val != "" && !enNameReg.test(val)) || val.length > 30) {
							geneErrorEle($this, "请填写正确姓名");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
					/*********用户名字验证*************/
					if (validatorValues[j] == "userName") {//
						if (val != "" && !(cellphoneReg.test(val) || emailReg.test(val))) {
							if(!cellphoneReg.test(val))
							geneErrorEle($this, "输入手机或邮箱");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}

					/*********街道地址验证*************/
					if (validatorValues[j] == "streetName") {//
						if (val != "" && !streetReg.test(val)) {
							geneErrorEle($this, "地址格式不正确");
							result = false;
							break;
						} else {
							cleanErrorEle($this);
						}
					}
				}


			}
			//当前验证元素为dropdownlist 类型
			if ($ve.hasClass("dropdown-menu")) {
				if (!$ve.find("li.active").attr('name')) {
					result = false;
					if($ve.parent("div.dropdown").next(".errorBox").length == 0){
						$ve.parent("div.dropdown").after($("<div class='errorBox'>"
								+"<i class='commonIcon errorIcon'></i><span>"
								+validatorTitle+"不能为空</span></div>"));
					}
					setErrorElePosition($ve);  //  dropdownlist 定位有问题
				} else {
					$ve.parent("div.dropdown").next(".errorBox").remove();
//					$ve.parent("div.dropdown").siblings('.errorBox').remove();
				}
			}
			//当前验证元素为textarea 类型
			if ($ve.is("textarea")) {
				var $this = $ve,val = $.trim($this.val());
				/*********需求备注验证*************/
				if (validatorValues[0] == "remark") {//
					if (val != "" && !remarkReg.test(val)) {
						geneErrorEle($this, "备注内容不正确");
						result = false;
					} else {
						cleanErrorEle($this);
					}
				}
			}
		}
		return result;
	}

	/***创建错误信息dom结构***/
	function geneErrorEle($this, msg) {
		$this.addClass("errorStyle");
		if ($this.next("div.errorBox").length == 0) {
			$this.after($("<div class='errorBox'>"
						+"<i class='commonIcon errorIcon'></i><span>"
						+msg+"</span></div>"));
		} else {
			$this.next("div.errorBox").find("span").html(msg);
		}
		setErrorElePosition($this);
	}
	/***清楚错误信息dom结构***/
	function cleanErrorEle($this) {
		if ($this.next("div.errorBox").length != 0) {
			$this.removeClass('errorStyle');
			$this.next('div.errorBox').remove();
		}
	}


	function setErrorElePosition($this) {

		var pos = $this.data("cp"),
			$offsetParent = $this.offsetParent();
			modalOffsetTop = $offsetParent.offset().top,
			modalOffsetLeft = $offsetParent.offset().left;


		var height = $this.outerHeight(),
			width = $this.outerWidth(),
			offsetTop = $this.offset().top - modalOffsetTop,
			offsetLeft = $this.offset().left - modalOffsetLeft;


		var $errEle = $this.next("div.errorBox"),
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





