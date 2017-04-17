/**
 *
 */
var commonPassengerList = [];

var cnNameReg = /^[\u4E00-\u9FA5]/;
var enNameReg = /^[A-Za-z]+$/;

var passenger = {
		/*** 创建旅客*******/
		create : function(adultNum,childNum,productType,login){
			var num = parseInt(adultNum) + parseInt(childNum);
			var data = {};
			data.productType = productType;
			data.login = login;
			if(parseInt(num) > 0){
				for(i = 0 ; i < parseInt(num) ; i++){
					data.indexNum = i+1;
					if(i > parseInt(adultNum -1)){
						data.passengerType = 'child';
					}else{
						data.passengerType = 'adult';
					}
					$("#passengerTemplate").tmpl(data).appendTo("#order-conBlock");
				}
				passenger.rebindEvent();
				passenger.initDatePicker();
				passenger.initDropAndCheck();
			}else{
				return;
			}
		},
		/*** 旅客模板内的节点元素绑定事件,
		 * jqueryTemplate加载模板需对事件再绑定,否则事件无效
		 * **/
		rebindEvent : function(){
			$(document).on("click",".commonIcon.radioIcon",function(){
        		$(this).parent().siblings(".radio").removeClass("active") ;
        		$(this).parent().addClass("active") ;
        	}) ;
			$(document).on("click","#order-conBlock span.commonIcon.checkboxIcon",function(){
        		if($(this).parent().hasClass("active")){
        			$(this).parent().removeClass("active") ;
        		}else{
        			$(this).parent().addClass("active") ;
        		}
        	}) ;
			$(document).on("click","#order-conBlock .dropdown-menu li a",function(){
				var thisHtml = $(this).html();
		        var $thisParents = $(this).parents(".dropdown-menu li");
		        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
		        if(thisHtml === '护照'　|| thisHtml === '港澳通行证'){
		        	if($(this).parents('tr').siblings().find('input[class*="birthday"]')){
		        		$(this).parents('tr').siblings().find('input[class*="birthday"]').attr("data-cv","required")
		        	}
		        }
		        $thisParents.addClass("active");
		        $thisParents.siblings().removeClass("active");
        	}) ;
			$(document).on("#order-conBlock input.datepicker",function(){
				$("input.datepicker").datepicker({
					format: "yyyy-mm-dd",
					language: "zh-CN",
					autoclose: true,
					todayHighlight: true,
					weekStart: 0
				}).on("show", function(){
					$("div.datepicker table thead .prev").html("");
					$("div.datepicker table thead .next").html("");
				});
        	}) ;
		},
		initDatePicker : function(){
			if ($("input.datepicker.default").length > 0) {
		        $("input.datepicker.default").datepicker({
		          format: "yyyy-mm-dd",
		          language: "zh-CN",
		          autoclose: true,
		          todayHighlight: true,
		          weekStart: 0
		        }).on("show", function(){
		            $("div.datepicker table thead .prev").html("");
		            $("div.datepicker table thead .next").html("");
		        });
		    }
		},
		initTranselate : function(){
			$("div.guestInfo-item input.firstName").each(function(){
				$(this).blur(function(){
					if($(this).closest('tbody').find('tr.trans input.firstNameEn').hasClass('errorStyle')){
						$(this).closest('tbody').find('tr.trans input.firstNameEn').next().remove();
						$(this).closest('tbody').find('tr.trans input.firstNameEn').removeClass("errorStyle");
					}
					var firstName = $(this).val();
					if(cnNameReg.test(firstName)){
						$(this).parent().parent().parent().siblings('tr.trans').find('input.firstNameEn').val( Pinyin.GetQP($.trim(firstName)).toUpperCase());
						$(this).parent().parent().parent().siblings('tr.trans').find('input.firstNameEn').blur();
					}else if(enNameReg.test(firstName)){
						$(this).parent().parent().parent().siblings('tr.trans').find('input.firstNameEn').val(firstName);
						$(this).parent().parent().parent().siblings('tr.trans').find('input.firstNameEn').blur();
					}
				});
			});
			$("div.guestInfo-item input.lastName").each(function(){
				$(this).blur(function(){
					if($(this).closest('tbody').find('tr.trans input.lastNameEn').hasClass('errorStyle')){
						$(this).closest('tbody').find('tr.trans input.lastNameEn').next().remove();
						$(this).closest('tbody').find('tr.trans input.lastNameEn').removeClass("errorStyle");
					}
					var lastName = $(this).val();
					if(cnNameReg.test(lastName)){
						$(this).parent().parent().parent().siblings('tr.trans').find('input.lastNameEn').val( Pinyin.GetQP($.trim(lastName)).toUpperCase());
						$(this).parent().parent().parent().siblings('tr.trans').find('input.lastNameEn').blur();
					}else if(enNameReg.test(lastName)){
						$(this).parent().parent().parent().siblings('tr.trans').find('input.lastNameEn').val(lastName);
						$(this).parent().parent().parent().siblings('tr.trans').find('input.lastNameEn').blur();
					}
				});
			});
		},
		cleanCredentialError: function(obj){
			var $inputObj =  $(obj).parents('div').siblings('input').val("");
        	if ($inputObj.next("div.errorBox").length != 0) {
        		$inputObj.removeClass('errorStyle');
        		$inputObj.next('div.errorBox').remove();
    		}
		},
		getCommonPassenger: function($obj,travellerType){
			var commonPassengerUrl = basepath + "/product/book/travelist/"+travellerType;
			var productType = $("#isDomestic").val();
			commonPassengerList = [];
			$.ajax({
				type:'POST',
				contentType:"application/json",
				dataType:'json',
				url:commonPassengerUrl,
				success:function(result){
					if(result.selectResult){
						$($obj).closest("div.guestContainer").find('ul li').remove();
						if(result.travellers.length > 0){
							commonPassengerList = result.travellers;
							for(var i = 0 ; i < result.travellers.length ; i ++){
								var liObj = "<li data-num="+i+" data-memId="+result.travellers[i].pageStoreId+">"+result.travellers[i].travelerNameCn+"</li>";
								$($obj).closest("div.guestContainer").find('ul').append(liObj);
							}
						}else{
							var liObj = "<li data-num=-1>没有常用联系人</li>";
							$($obj).closest("div.guestContainer").find('ul').append(liObj);
						}
						$("div.guestContainer li").click(function(){
							passenger.fillPassenger($(this));
			            	$(this).parents('ul.guestNameList').slideUp("fast");
			            	$(this).closest('div.guestInfo-item').find('div.errorBox').remove();
			            	$(this).closest('div.guestInfo-item').find('input.errorStyle').removeClass("errorStyle");
			            });
						console.log(result);
					}else{
						console.log("error");
					}
				}
			});
		},
		fillPassenger : function($obj){
			$obj.closest('tbody').find('ul.dropdown-menu li').attr("data-number","");
			var index = $($obj).data("num");
			var productType = $("#isDomestic").val();
			if(index >= 0){
				var traveller =  commonPassengerList[index];
				if(traveller){
					$obj.parents("div.guestInfo-item").find("input.birthday").datepicker('setDate', traveller.birthday);
					var cnName = traveller.travelerNameCn;
					var firstName = traveller.firstNameCn;
					var lastName = traveller.lastNameCn;
					var firstNameEn = traveller.firstEnName;
					var lastNameEn = traveller.lastEnName;
					$obj.parents("div.guestInfo-item").find("input.firstName").val(traveller.firstNameCn);
					$obj.parents("div.guestInfo-item").find("input.lastName").val(traveller.lastNameCn);

					if(cnNameReg.test(firstNameEn)){
						$obj.parents("div.guestInfo-item").find("input.firstNameEn").val(Pinyin.GetQP($.trim(firstNameEn)).toUpperCase());
						$obj.parents("div.guestInfo-item").find("input.firstNameEn").blur();
					}else if(enNameReg.test(firstNameEn)){
						$obj.parents("div.guestInfo-item").find("input.firstNameEn").val(firstNameEn);
						$obj.parents("div.guestInfo-item").find("input.firstNameEn").blur();
					}
					if(cnNameReg.test(lastNameEn)){
						$obj.parents("div.guestInfo-item").find("input.lastNameEn").val(Pinyin.GetQP($.trim(lastNameEn)).toUpperCase());
						$obj.parents("div.guestInfo-item").find("input.lastNameEn").blur();
					}else if(enNameReg.test(lastNameEn)){
						$obj.parents("div.guestInfo-item").find("input.lastNameEn").val(lastNameEn);
						$obj.parents("div.guestInfo-item").find("input.lastNameEn").blur();
					}
					if($obj.parents("div.guestInfo-item").find('input.country')){
						$obj.closest('div.guestInfo-item').find('input.nationalityDropList').val(traveller.nationality);
						$obj.parents("div.guestInfo-item").find('input.country').val(traveller.nationality);
					}
					$("#order-conBlock").find("div.credentialType li").attr("data-number","");
					$("#order-conBlock").find("div.credentialType li").attr("data-dead","");
					if(traveller.credentials.length > 0){
						var activeLiName = '';
						for(i = 0 ; i < traveller.credentials.length ; i++){
							if(traveller.credentials[i].type == 'IDCARD' && $obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="IDCARD"]').length > 0){
								$obj.parents("div.guestInfo-item").find('div.credentialType a.dropdownBtn span.activeFonts').html("身份证");
								$obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="IDCARD"]').addClass("active");
								$obj.parents("div.guestInfo-item").find("input.credentialNum").attr("data-cv","required,idCard");
								$obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="IDCARD"]').siblings().removeClass("active");
								$obj.parents("div.guestInfo-item").find("ul.dropdown-menu").find("li.idcard").attr("data-number",traveller.credentials[i].number);
								$obj.parents("div.guestInfo-item").find("ul.dropdown-menu").find("li.idcard").attr("data-dead",traveller.credentials[i].deadLineDay);
								$obj.parents("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', traveller.credentials[i].deadLineDay);
								activeLiName = $obj.parents("div.guestInfo-item").find('div.credentialType ul li.active').attr('name');
							}
							if(traveller.credentials[i].type == 'PASSPORT' && $obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="PASSPORT"]').length > 0){
								$obj.parents("div.guestInfo-item").find("ul.dropdown-menu").find("li.passport").attr("data-dead",traveller.credentials[i].deadLineDay);
								if(activeLiName != 'IDCARD'){
									$obj.parents("div.guestInfo-item").find('div.credentialType a.dropdownBtn span.activeFonts').html("护照");
									$obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="PASSPORT"]').addClass("active");
									$obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="PASSPORT"]').siblings().removeClass("active");
									activeLiName = $obj.parents("div.guestInfo-item").find('div.credentialType ul li.active').attr('name');
									$obj.parents("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', traveller.credentials[i].deadLineDay);
									$obj.parents("div.guestInfo-item").find("input.credentialNum").attr("data-cv","required,passPort");
								}
								$obj.parents("div.guestInfo-item").find("ul.dropdown-menu").find("li.passport").attr("data-number",traveller.credentials[i].number);
							}
							if(traveller.credentials[i].type == 'GANGAOPASS' && $obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="GANGAOPASS"]').length > 0){
								$obj.parents("div.guestInfo-item").find("ul.dropdown-menu").find("li.gangaopass").attr("data-dead",traveller.credentials[i].deadLineDay);
								if(activeLiName != 'IDCARD' && activeLiName != 'PASSPORT'){
									$obj.parents("div.guestInfo-item").find('div.credentialType a.dropdownBtn span.activeFonts').html("港澳通行证");
									$obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="GANGAOPASS"]').addClass("active");
									$obj.parents("div.guestInfo-item").find('div.credentialType ul li[name*="GANGAOPASS"]').siblings().removeClass("active");
									activeLiName = $obj.parents("div.guestInfo-item").find('div.credentialType ul li.active').attr('name');
									$obj.parents("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', traveller.credentials[i].deadLineDay);
									$obj.parents("div.guestInfo-item").find("input.credentialNum").attr("data-cv","required,hkPass");
								}
								$obj.parents("div.guestInfo-item").find("ul.dropdown-menu").find("li.gangaopass").attr("data-number",traveller.credentials[i].number);
							}
						}
						var activeLiNum = $obj.parents("div.guestInfo-item").find('div.credentialType ul li.active').attr("data-number");
						$obj.parents("div.guestInfo-item").find("input.credentialNum").val(activeLiNum);
						if(traveller.gender == 'MALE'){
							$obj.parents("div.guestInfo-item").find('div.gender label[data-val=MALE]').addClass("active");
							$obj.parents("div.guestInfo-item").find('div.gender label[data-val=MALE]').siblings().removeClass("active");
						}else{
							$obj.parents("div.guestInfo-item").find('div.gender label[data-val=FEMALE]').addClass("active");
							$obj.parents("div.guestInfo-item").find('div.gender label[data-val=FEMALE]').siblings().removeClass("active");
						}
					}
				}
			}
		},
		initDropAndCheck: function(){
		    //证件下拉
		    $("#order-conBlock").find(".dropdown-menu li a").click(function(){
		        var thisHtml = $(this).html();
		        var $thisParents = $(this).parents(".dropdown-menu li");
		        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
		        $thisParents.addClass("active");
		        $thisParents.siblings().removeClass("active");
		        if($(this).html() == '护照'){
		        	passenger.cleanCredentialError($(this));
		        	var deadTime = $(this).closest("div.guestInfo-item").find("li.passport").attr("data-dead");
		        	if(typeof(deadTime) != "undefined" && deadTime != null && deadTime != ""){
		        		$(this).closest("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', deadTime);
		        	}else{
		        		$(this).closest("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', "2020-01-01");
		        	}
		        	$(this).parents('div').siblings('input').attr("data-cv","required,passPort");
		        	$(this).parents('tbody').find('input[class *="birthday"]').attr("data-cv","required");
		        }else if($(this).html() == '身份证'){
		        	passenger.cleanCredentialError($(this));
		        	var deadTime = $(this).closest("div.guestInfo-item").find("li.idcard").attr("data-dead");
		        	if(typeof(deadTime) != "undefined" && deadTime != null && deadTime != ""){
		        		$(this).closest("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', deadTime);
		        	}else{
		        		$(this).closest("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', "2020-01-01");
		        	}
		        	$(this).parents('div').siblings('input').attr("data-cv","required,idCard");
		        	$(this).parents('tbody').find('input[class *="birthday"]').attr("data-cv","");
		        }else if($(this).html() == '港澳通行证'){
		        	passenger.cleanCredentialError($(this));
		        	var deadTime = $(this).closest("div.guestInfo-item").find("li.gangaopass").attr("data-dead");
		        	if(typeof(deadTime) != "undefined" && deadTime != null && deadTime != ""){
		        		$(this).closest("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', deadTime);
		        	}else{
		        		$(this).closest("div.guestInfo-item").find("input.credentialsDeadLine").datepicker('setDate', "2020-01-01");
		        	}
		        	$(this).parents('div').siblings('input').attr("data-cv","required,hkPass");
		        	$(this).parents('tbody').find('input[class *="birthday"]').attr("data-cv","required");
		        }
		        $(this).parents('div').siblings('input').val($(this).closest('li').attr("data-number"));
		    });
	},
	cleanInfo : function(){
		$("#order-conBlock").find('input:not(".datepicker")').val("");
		if($("#order-conBlock").find("input.country").length > 0){
			$("#order-conBlock").find("input.country").val("中国");
		}
		$("#order-conBlock").find("div.credentialType li").attr("data-number","");
		$("#order-conBlock").find("div.credentialType li").attr("data-dead","");
		$("#order-conBlock").find('span.first').html("XING");
		$("#order-conBlock").find('span.last').html("MING");
	}
}

function isExistNationality(data,i){
	var isExist= false;
	var nationality = $("#nationality_"+i).val();
	if(null!=nationality  ){
		for(var i=0;i<data.length;i++){
			if(data[i].indexOf(nationality) != -1){
				isExist=true;
				break;
			}
		}
	}
	return isExist;
}

/**
 *  国籍blur
 */
function nationalityBlurIfNotFoundSetEmpty(){
	$("#order-conBlock input.country").each(function(i,item){
		$(item).blur(function(){
			toggleErrorHint(disappear,'#nationality_errorHintAdd_'+i) ;
			var nationalityDropList = $("#nationalityDropList_"+i).val();

			if(nationalityDropList.indexOf(nationality_hint_error) != -1 ){
				 $("#nationality_"+i).val('');
			}
			if(null!=nationalityDropList && nationalityDropList !='' && $.inArray( $("#nationality_"+i).val(), nationalityDropList.split(',')) <0){
				 $("#nationality_"+i).val('');
			}
			if((null == nationalityDropList || nationalityDropList == "") && nationality_hint_error == "对不起，找不到："){
				$("#nationality_"+i).val('');
			}

		});
	});

}