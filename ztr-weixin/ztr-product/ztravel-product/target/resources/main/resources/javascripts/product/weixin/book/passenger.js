/**
 *
 */
var disappear = '' ;
var commonPassengerList = [];
var creditData = [];
var dp;
var idCard = {name:"身份证",type:"IDCARD"};
var passPort = {name:"护照",type:"PASSPORT"};
var hkPass = {name:"港澳通行证",type:"GANGAOPASS"};
var nationality_hint_error='对不起，找不到：';

var passenger = {
		init : function(){
			//动态生成乘客数量
			var nature = $("#nature").val();
			if(typeof(nature) != undefined && nature != ''){
				if(nature != 'VISA'){
					passenger.create($("#adultNum").val(),$("#childNum").val(),$("#isDomestic").val(),$("#isLogin").val());
				}
			}else{
				passenger.create($("#adultNum").val(),$("#childNum").val(),$("#isDomestic").val(),$("#isLogin").val());
			}
			//国籍自动补全
			passenger.countryAutoComplete();

			//常旅客姓名提示

//			passenger.initDatePicker();
		},
		initDatePicker : function(){
			 $("#passengerContainer div.order-traveler-box").find("div.brithday,div.credentialsDeadLine").click(function(){
				 var eleId = $(this).attr("id");
				 var $this = $(this).find("input");
				 var dateType = $this.attr("date-type");
				 var defaultDay = 1;
				 var defaultMonth=0;
				 var defaultYear;
				 var defaultStartYear;
				 if(dateType == "deadTime"){
					 defaultYear = 2020;
					 defaultMonth = 11;
					 defaultDay = 1;
					 defaultEndYe =2035;
					 defaultStartYear= new Date().getYear() + 1900;
				 }else if(dateType == "brithDay"){
					 defaultEndYe = new Date().getYear() + 1900;
					 defaultYear = 1980;
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
				 $("#commonError").popup({
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
							if(dateType == "deadTime"){
								if(new Date(year,month-1,day) >= new Date()){
									$this.val(dateTimeHtml);
									$this.attr("data-time",dateTime);
								}else{
									$("#"+eleId+"Datepicker").popup({
										afterclose: function() {
											if(new Date(year,month-1,day) < new Date()){
												$("#commonErrorCont").html("有效期须大于当前时间");
												$("#commonError").popup("open");
											}
										}
									});
									$("#"+eleId+"Datepicker").popup("close");
								}
							}
							if(dateType == "brithDay"){
								if(new Date(year,month-1,day) < new Date()){
									$this.val(dateTimeHtml);
									$this.attr("data-time",dateTime);
								}else{
									$("#"+eleId+"Datepicker").popup({
										afterclose: function() {
											if(new Date(year,month-1,day) > new Date()){
												$("#commonErrorCont").html("出生日期须早于当前时间");
												$("#commonError").popup("open");
											}
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
					$("#wxPassengerTemplate").tmpl(data).appendTo("#passengerContainer");
				}
				passenger.getCommonPassenger();
				passenger.initDatePicker();
				passenger.translate();
				passenger.getCreditType();
				passenger.nationalityBlurIfNotFoundSetEmpty();
//				passenger.rebindEvent();
//				passenger.initDatePicker();
//				passenger.initDropAndCheck();
			}else{
				return;
			}
		},
		 translate : function(){
			 $("#passengerContainer div.order-traveler-box input.firstName").each(function(){
					$(this).blur(function(){
						var firstName = $(this).val();
						if(cnNameReg.test(firstName)){
							$(this).closest('div.order-traveler-box').find('input.firstNameEn').val( Pinyin.GetQP($.trim(firstName)).toUpperCase());
						}else if(enNameReg.test(firstName)){
							$(this).closest('div.order-traveler-box').find('input.firstNameEn').val(firstName);
						}
					});
				});
				$("#passengerContainer div.order-traveler-box input.lastName").each(function(){
					$(this).blur(function(){
						var lastName = $(this).val();
						if(cnNameReg.test(lastName)){
							$(this).closest('div.order-traveler-box').find('input.lastNameEn').val( Pinyin.GetQP($.trim(lastName)).toUpperCase());
						}else if(enNameReg.test(lastName)){
							$(this).closest('div.order-traveler-box').find('input.lastNameEn').val(lastName);
						}
					});
				});
		 },
		countryAutoComplete : function(){
			$("#passengerContainer input.country").each(function(i,item){
				var $this = $(this);
				$(this).typeahead({
				    source: function (query, process) {
				    	$((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,"&gt"));
				        var parameter = {query: query};
				        $.post(wxServer + "/usercenter/weixin/countryAutoComplete", parameter, function (data) {
				        	if(data.length==0 || !passenger.isExistNationality(data,i)){
				        		notFindHint =nationality_hint_error+$("#nationality_"+i).val();
				        		data=[notFindHint];
				        	}else{
				        		nationalityDropListValues=data;
				        		$("#nationalityDropList_"+i).val(data);
				        	}
				        	process(data);
				        });
				    }
				});

			});
		},
		getCommonPassenger : function(){
			$("#passengerContainer span.travel-lab").click(function(){
				_paq.push(['trackEvent', 'orderpage', 'ztrtraveller']);
				var passengerType = $(this).closest("div.order-traveler-box").find("div.passengerType").attr("passengerType");
				var commonPassengerUrl = wxServer + "/book/weixin/travelist/"+passengerType;//basepath
				var productType = $("#isDomestic").val();
				commonPassengerList = [];
				var $this = $(this);
				$.ajax({
					type:'POST',
					contentType:"application/json",
					dataType:'json',
					url:commonPassengerUrl,
					success:function(result){
						if(result.selectResult){
							commonPassengerList = result.travellers;
							data = passenger.convertPassenger(result.travellers);
							setTimeout(function(){
								$this.cusListPop({
									title: "请选择常旅客",
									scrollId: "travellerInfo",
									data: data,
									reCreate: true,
									callback : function(){
										$("#travellerInfoWrapper").find("li").click(function(){
											if ($(this).hasClass("current")) {
												passenger.fillPassenger($this,$(this).attr("data-number"));
											}
											$("#travellerInfoPopContainer").popup("close");
										});
									}
								});
							}, 500);
						}else{
							console.log("error");
						}
					}
				});
			});
		},
		fillPassenger : function($obj,indexNum){
			if(indexNum >= 0){
				var traveller =  commonPassengerList[indexNum];
				if(traveller){
					if(traveller.birthday != null && traveller.birthday != ""){
						$obj.closest("div.order-traveler-box").find("input.birthday").attr("data-time",traveller.birthday);
						var brith = new Date(traveller.birthday);
						var year = brith.getYear()+1900;
						var month = brith.getMonth()+1;
						var day = brith.getDate();
						$obj.closest("div.order-traveler-box").find("input.birthday").val(year+"年"+month+"月"+day+"日");
					}
					var cnName = traveller.travelerNameCn;
					var firstName = traveller.firstNameCn;
					var lastName = traveller.lastNameCn;
					var firstNameEn = traveller.firstEnName;
					var lastNameEn = traveller.lastEnName;
					$obj.closest("div.order-traveler-box").find("input.firstName").val(traveller.firstNameCn);
					$obj.closest("div.order-traveler-box").find("input.lastName").val(traveller.lastNameCn);

					if(cnNameReg.test(firstNameEn)){
						$obj.closest("div.order-traveler-box").find("input.firstNameEn").val(Pinyin.GetQP($.trim(firstNameEn)).toUpperCase());
					}else if(enNameReg.test(firstNameEn)){
						$obj.closest("div.order-traveler-box").find("input.firstNameEn").val(firstNameEn);
					}
					if(cnNameReg.test(lastNameEn)){
						$obj.closest("div.order-traveler-box").find("input.lastNameEn").val(Pinyin.GetQP($.trim(lastNameEn)).toUpperCase());
					}else if(enNameReg.test(lastNameEn)){
						$obj.closest("div.order-traveler-box").find("input.lastNameEn").val(lastNameEn);
					}
					if($obj.closest("div.order-traveler-box").find('input.country')){
						$obj.closest("div.order-traveler-box").find('input.country').val(traveller.nationality);
					}
					if(traveller.credentials.length > 0){
						var activeLiName = '';
						var productType = $("#isDomestic").val();
						var credentialNum = '';
						/*每次更换常旅客时替换掉隐藏的证件信息**/
						$obj.closest("div.order-traveler-box").find("input.idCardHidden").attr("data-validate","");
						$obj.closest("div.order-traveler-box").find("input.idCardHidden").attr("data-num","");
						$obj.closest("div.order-traveler-box").find("input.passPortHidden").attr("data-validate","");
						$obj.closest("div.order-traveler-box").find("input.passPortHidden").attr("data-num","");
						$obj.closest("div.order-traveler-box").find("input.hkPassHidden").attr("data-validate","");
						$obj.closest("div.order-traveler-box").find("input.hkPassHidden").attr("data-num","");
						/**填充前置空证件号内容*/
						$obj.closest("div.order-traveler-box").find("input.credentialNum").val("");
						for(i = 0 ; i < traveller.credentials.length ; i++){
							if(traveller.credentials[i].type == 'IDCARD'){
								$obj.closest("div.order-traveler-box").find("input.idCardHidden").attr("data-validate",traveller.credentials[i].deadLineDay);
								$obj.closest("div.order-traveler-box").find("input.idCardHidden").attr("data-num",traveller.credentials[i].number);
								if(productType == "domestic" && traveller.credentials[i].number != ""){
									$obj.closest("div.order-traveler-box").find("div.credentialType").html("身份证");
									$obj.closest("div.order-traveler-box").find("div.credentialType").attr("credentialType","IDCARD");
									$obj.closest("div.order-traveler-box").find("input.credentialNum").attr("data-cv","required,idCard");
									$obj.closest("div.order-traveler-box").find("input.credentialNum").val(traveller.credentials[i].number);
									$obj.closest("div.order-traveler-box").find("input.credentialsDeadLine").attr("data-time",traveller.credentials[i].deadLineDay);
									$obj.closest("div.order-traveler-box").find("input.credentialsDeadLine").val(converToFullDate(traveller.credentials[i].deadLineDay));
									credentialNum = $obj.closest("div.order-traveler-box").find("input.credentialNum").val();
								}
							}
							if(traveller.credentials[i].type == 'PASSPORT'){
								$obj.closest("div.order-traveler-box").find("input.passPortHidden").attr("data-validate",traveller.credentials[i].deadLineDay);
								$obj.closest("div.order-traveler-box").find("input.passPortHidden").attr("data-num",traveller.credentials[i].number);
								if(traveller.credentials[i].number != ""){
									if(credentialNum == ""){
										$obj.closest("div.order-traveler-box").find("div.credentialType").html("护照");
										$obj.closest("div.order-traveler-box").find("div.credentialType").attr("credentialType","PASSPORT")
										$obj.closest("div.order-traveler-box").find("input.credentialNum").attr("data-cv","required,passPort");
										$obj.closest("div.order-traveler-box").find("input.credentialNum").val(traveller.credentials[i].number);
										$obj.closest("div.order-traveler-box").find("input.credentialsDeadLine").attr("data-time",traveller.credentials[i].deadLineDay);
										$obj.closest("div.order-traveler-box").find("input.credentialsDeadLine").val(converToFullDate(traveller.credentials[i].deadLineDay));
										credentialNum = $obj.closest("div.order-traveler-box").find("input.credentialNum").val();
									}
								}
							}
							if(traveller.credentials[i].type == 'GANGAOPASS'){
								$obj.closest("div.order-traveler-box").find("input.hkPassHidden").attr("data-validate",traveller.credentials[i].deadLineDay);
								$obj.closest("div.order-traveler-box").find("input.hkPassHidden").attr("data-num",traveller.credentials[i].number);
								if(traveller.credentials[i].number != ""){
									if(credentialNum==""){
										$obj.closest("div.order-traveler-box").find("div.credentialType").html("港澳通行证");
										$obj.closest("div.order-traveler-box").find("div.credentialType").attr("credentialType","GANGAOPASS")
										$obj.closest("div.order-traveler-box").find("input.credentialNum").attr("data-cv","required,hkPass");
										$obj.closest("div.order-traveler-box").find("input.credentialsDeadLine").attr("data-time",traveller.credentials[i].deadLineDay);
										$obj.closest("div.order-traveler-box").find("input.credentialsDeadLine").val(converToFullDate(traveller.credentials[i].deadLineDay));
										$obj.closest("div.order-traveler-box").find("input.credentialNum").val(traveller.credentials[i].number);
										credentialNum = $obj.closest("div.order-traveler-box").find("input.credentialNum").val();
									}
								}
							}
						}
						if(traveller.gender == 'MALE'){
							$obj.closest("div.order-traveler-box").find("input.male").prop("checked",true);
							$obj.closest("div.order-traveler-box").find("input.female").prop("checked",false);
						}else{
							$obj.closest("div.order-traveler-box").find("input.male").prop("checked",false);
							$obj.closest("div.order-traveler-box").find("input.female").prop("checked",true);
						}
					}
				}
			}
		},
		getCreditType : function(){
			var isDomestic = $("#isDomestic").val();
			var current = {};
			$("div.credential").click(function(){
				var $this = $(this);
				var currentName = $this.closest("div.order-traveler-box").find("div.credentialType").html();
				if(isDomestic == "domestic"){
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
	        					$this.closest("div.order-traveler-box").find("div.credentialType").attr("credentialType",$(this).attr("data-type"));
	        					$this.closest("div.order-traveler-box").find("div.credentialType").html($(this).attr("data-name"));
	        					var cardNum = $this.closest("div.order-traveler-box").find("input[data-type='"+currentType+"']").attr("data-num");
	        					var deadTime = $this.closest("div.order-traveler-box").find("input[data-type='"+currentType+"']").attr("data-validate");
	        					$this.closest("div.order-traveler-box").find("input.credentialNum").val(cardNum);
	        					if($this.closest("div.order-traveler-box").find("input.credentialsDeadLine").length > 0){
	        						$this.closest("div.order-traveler-box").find("input.credentialsDeadLine").val(converToFullDate(deadTime));
	        						$this.closest("div.order-traveler-box").find("input.credentialsDeadLine").attr("data-time",deadTime);
	        					}
	        					if($(this).attr("data-type") == "IDCARD"){
	        						$this.closest("div.order-traveler-box").find("input.credentialNum").attr("data-cv","required,idCard");
	        					}else if($(this).attr("data-type") == "PASSPORT"){
	        						$this.closest("div.order-traveler-box").find("input.credentialNum").attr("data-cv","required,passPort");
	        					}else if($(this).attr("data-type") == "GANGAOPASS"){
	        						$this.closest("div.order-traveler-box").find("input.credentialNum").attr("data-cv","required,hkPass");
	        					}
	        				}
	        				$("#cardTypePopContainer").popup("close");
	        			});
	        		}
	        	});
			});
		},
		convertPassenger : function(data){
			var result = [];
			if(data.length > 0){
				for(i = 0; i < data.length ; i++){
					var passengerTemp = {};
					passengerTemp.name = data[i].travelerNameCn;
					passengerTemp.number = i;
					result.push(passengerTemp);
				}
			}else{
				var passengerTemp = {};
				passengerTemp.name = "没有常旅客";
				passengerTemp.number = -1;
				result.push(passengerTemp);
			}
			return result;
		},
		getParams : function(){
				var passengerList = [];
				var isDomestic = $("#isDomestic").val();
				$("#passengerContainer div.order-traveler-box").each(function(i) {
					var passengerTemp = {};
					passengerTemp.credentialType = $(this).find("div.credentialType").attr("credentialType");
					passengerTemp.credentialNum = $(this).find("input.credentialNum").val();
					passengerTemp.type = $(this).find("div.passengerType").attr("passengerType");
					passengerTemp.birthday = $(this).find(".birthday").attr("data-time");
					if($(this).find("input.male").prop("checked")){
						passengerTemp.gender = "MALE";
					}else{
						passengerTemp.gender = "FEMALE";
					}
					passengerTemp.savePassenger = true;

					var firstName = $(this).find(".firstName").val();
					var lastName = $(this).find(".lastName").val();
					var firstNameEn = $(this).find(".firstNameEn").val();
					var lastNameEn = $(this).find(".lastNameEn").val();
					$("#firstAndLastName"+i).val(firstName+lastName);
					passengerTemp.firstName = firstName;
					passengerTemp.lastName = lastName;
					passengerTemp.firstNameEn = firstNameEn;
					passengerTemp.lastNameEn = lastNameEn;

					if(cnNameReg.test(firstName) && cnNameReg.test(lastName)){
						passengerTemp.passengerName = firstName + lastName;
					}else{
						passengerTemp.passengerName = firstName + "/" + lastName;
					}
					passengerTemp.passengerEnName = firstNameEn + "/" + lastNameEn;
					if(isDomestic != "domestic"){
						if($(this).find("input.credentialsDeadLine").attr("data-time") != ""){
							passengerTemp.credentialsDeadLine = $(this).find("input.credentialsDeadLine").attr("data-time");
						}else{
							passengerTemp.credentialsDeadLine = "2020-01-01";//暂时添加
						}
						if($(this).find(".country").val() != ''){
							passengerTemp.country = $(this).find(".country").val();
						}else{
							passengerTemp.country = '中国';
						}
					}
					passengerList.push(passengerTemp);
				});
				return passengerList;
		},
	isExistNationality : function(data,i){
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
		},
		nationalityBlurIfNotFoundSetEmpty : function(){
			$("#passengerContainer div.order-traveler-box input.country").each(function(i,item){
				$(item).blur(function(){
//					toggleErrorHint(disappear,'#nationality_errorHintAdd_'+i) ;
					var nationalityDropList = $("#nationalityDropList_"+i).val();

					if(nationalityDropList.indexOf(nationality_hint_error) != -1 ){
						 $("#nationality_"+i).val('');
					}
					if(null!=nationalityDropList && nationalityDropList !='' && $.inArray( $("#nationality_"+i).val(), nationalityDropList.split(',')) <0){
						 $("#nationality_"+i).val('');
					}

				});
			});
		}
}
