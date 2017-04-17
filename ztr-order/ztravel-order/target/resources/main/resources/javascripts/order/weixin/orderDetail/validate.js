(function($){
	$.fn.bookValidate = function(){
		var validatedEle = $(this),result = true;
		/***自定义验证规则，可以根据需求进行扩展***/
		var cellphoneReg = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/,
			emailReg =  /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/,
			idCardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
			passPortReg = /^[A-Za-z0-9]{1,20}$/,
			hkPassReg = /^[a-zA-Z][0-9]{8}$/,//港澳通行证，字母开头，后面接8位数字
			streetReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|”|’]){1,100}$/,//街道地址验证
			remarkReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|”|’]){0,500}$/,//备注内容验证
			pyNameReg = /^[a-zA-Z|/.]{1,19}[\/]{1}[a-zA-Z|/.]{1,20}$/,//长度为40个字符内的中文名拼音
			chNameReg = /^[\u4e00-\u9fa5]+$/,
			enNameReg = /^[A-Za-z]+$/,//英文名
			contactorChNameReg = /^[\u4e00-\u9fa5|\.]{1,20}$/,
			contactorEnNameReg = /^[a-zA-z|\.]{1,40}$/;

		/***找到所有需要自定义验证的元素***/
		if ($(this).attr("readonly") !== "readonly") {
			$(this).bind("keyup", function(event) {
				if ($.trim($(this).val()) != "") {
				}
			});
		}
		/*******对元素进行验证************/
		var $ve = $(this);
		if ($ve.data("cv") != '') {
			var validatorValues = $ve.attr("data-cv").split(',');  // 得到所有需要验证的类型[required,email,...]
			var validatorTitle = $ve.data("ct");  // 得到验证字段名
			validatorTitle = (validatorTitle == undefined) ? "" : validatorTitle;
			//验证元素为 input 类型
			if ($ve.is("input") && $ve.is(":visible")) {
				var $this = $ve,val = $.trim($this.val());
				for(var j = 0; j < validatorValues.length; j++) {
					/*********必填验证***************/
					if (validatorValues[j] == "required") {
						if (val == "") {
							result = false;
							popTip(validatorTitle+"不能为空");
							break;//每次只验证一个类型，若出错，则跳出循环结束该字段验证
						}
					}
					/*********手机号验证***************/
					if (validatorValues[j] == "cellphone") {
						if (val != "" && !cellphoneReg.test(val)) {
							popTip(validatorTitle+"格式错误");
							result = false;
							break;
						}
					}
					/*********邮箱验证***************/
					if (validatorValues[j] == "email") {//邮箱验证
						if (val != "" && !emailReg.test(val)) {
							popTip(validatorTitle+"格式错误");
							result = false;
							break;
						}
					}
					/*********身份证验证*************/
					if (validatorValues[j] == "idCard") {//身份证验证
						if (val != "" && !idCardReg.test(val)) {
							popTip(validatorTitle+"格式填写有误，请重新输入");
							result = false;
							break;
						}
					}
					/*********护照验证*************/
					if (validatorValues[j] == "passPort") {//护照验证
						if (val != "" && !passPortReg.test(val)) {
							popTip(validatorTitle+"格式填写有误，请重新输入");
							result = false;
							break;
						}
					}
					/*********港澳通行证验证*************/
					if (validatorValues[j] == "hkPass") {//护照验证
						if (val != "" && !hkPassReg.test(val)) {
							popTip(validatorTitle+"格式填写有误，请重新输入");
							result = false;
							break;
						}
					}
					/*********中英文混合验证*************/
					if (validatorValues[j] == "contactor") {//
						if (val != "" && !(contactorChNameReg.test(val) || contactorEnNameReg.test(val))) {
							popTip(validatorTitle+"填写不正确");
							result = false;
							break;
						}
					}
					/*********只能中文或者英文*************/
					if (validatorValues[j] == "chOrEnName") {//
						if (val != "" && !(chNameReg.test(val) || enNameReg.test(val))) {
							popTip(validatorTitle+"填写不正确");
							result = false;
							break;
						}
					}

					/*********英文名字验证*************/
					if (validatorValues[j] == "enName") {//
						if (val != "" && !enNameReg.test(val)) {
							popTip(validatorTitle+"填写不正确");
							result = false;
							break;
						}
					}
					/*********用户名字验证*************/
					if (validatorValues[j] == "userName") {//
						if (val != "" && !(cellphoneReg.test(val) || emailReg.test(val))) {
							if(!cellphoneReg.test(val))
							popTip(validatorTitle+":手机号");
							result = false;
							break;
						}
					}

					/*********街道地址验证*************/
					if (validatorValues[j] == "streetName") {//
						if (val != "" && !streetReg.test(val)) {
							popTip(validatorTitle+"格式不正确");
							result = false;
							break;
						}
					}
				}
			}
			//当前验证元素为textarea 类型
			if ($ve.is("textarea")) {
				var $this = $ve,val = $.trim($this.val());
				/*********需求备注验证*************/
				if (validatorValues[0] == "remark") {//
					if (val != "" && !remarkReg.test(val)) {
						popTip(validatorTitle+"格式不正确");
						result = false;
					}
				}
			}
		}
		return result;
	}
	function popTip(msg){
		$("#tipMsg").html(msg);
		$("#detailTip").popup("open");
	}
})(jQuery);





