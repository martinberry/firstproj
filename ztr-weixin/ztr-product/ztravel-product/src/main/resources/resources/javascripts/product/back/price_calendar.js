var batchSave = 'batchSave' ;
var singleSave = 'singleSave' ;
var pkgSaveLock = false ;
var saleSaveLock = false ;
var isSaleOk = true ;

var m1 = '信息输入有误' ;
var m2 = 'YYYY-MM-DD' ;
var s1 = /^(([0-1]{1}[0-9]{1})|(2[0-3]{1}))$/ ;
var s2 = /^(\d{4})\-(\d{2})\-(\d{2})$/ ;
var old_pkgId = '';
var old_pkgName = '';
var old_pkgDesc = '';
var old_pkgAdultNum = '2';
var old_pkgChildrenNum = '0';
var old_pkgPrice = '';
var old_pkgPerPrice = '';
var old_pkgHasTax = true;


var old_stock = '' ;
var old_marketPrice = '' ;
var old_adultPrice = '' ;
var old_childPrice = '' ;
var old_isAdultPriceHasTax = true ;
var old_hasChildPrice = true ;
var old_isChildPriceHasTax = true ;
var old_singleRoomPrice = '' ;
var old_pkgs = [] ;
var old_hours = '' ;
var old_days = '' ;

$(function(){

	//禁止退格键 作用于Firefox、Opera
    document.onkeypress = banBackSpace;
    //禁止退格键 作用于IE、Chrome
    document.onkeydown = banBackSpace;

    toggleMode() ;
    
    $(".addPriceType").click(function(){
    	old_pkgId = '';
    	old_pkgName = '';
    	old_pkgDesc = '';
    	old_pkgAdultNum = '2';
    	old_pkgChildrenNum = '0';
    	old_pkgPrice = '';
    	old_pkgPerPrice = '';
    	old_pkgHasTax = true;
    	$("#priceTypePopup .modal-title").html("新增套餐类型") ;
    	$("#priceTypePopup .savePkg").html("新增") ;
        $("#priceTypePopup").modal("show");
    });

    //保存时间价格
    $(".commonButton.red-fe6869Button.top").click(function(){
    }) ;

    //保存&下一步
    $(".commonButton.whiteBtn.top").click(function(){
    	saveCheck(true) ;
    }) ;

    //编辑视图
    $(".commonButton.blue-45c8dcButton.top").click(function(){
    	window.location.href = basepath + '/product/calendar/edit/' + $("#id").val() ;
    }) ;

	//批量设置开关
	$(".commonButton.blue-45c8dcButton.batch-set-btn").click(function(){
		$(".date-range").show() ;
		$(".set-price-container").show() ;
		$(".default-tip").hide() ;
		$(".show-date").hide() ;
		$(".right-cont-block").show() ;
		toggleCostInfo(true) ;
		$(".stock-cont").html("") ;
		$("#saveType").val(batchSave) ;
		$("#batchUpdPrice").parent().show() ;
		$(".commonButton.blue-45c8dcButton.addPriceType").css("display","") ;
		$(".commonButton.red-fe6869Button.pkgdel").css("display","") ;
		$(".commonButton.blue-45c8dcButton.pkgedit").css("display","") ;
		$(".commonButton.blue-45c8dcButton.closeBtn").css("display","") ;
		$(".commonButton.red-fe6869Button.deleteBtn").css("display","") ;
		$(".commonButton.gray-bbbButton.canOrderSet").css("display","") ;
		if(!$("#batchUpdPrice").hasClass("active")){
			$("#batchUpdPrice").addClass("active") ;
		}
		cleanSaleForm();
	});

	//取消
	$(".commonButton.gray-bbbButton.cancel").click(function(){
		cleanSaleForm() ;
		$(".right-cont-block").hide() ;
	});

	$(".savePkg").click(function(){
		var pkg = {} ;
		pkg.pkgId = $("#pkgId").val() ;
		pkg.name = trim($("#pkgName")) ;
		pkg.desc = trim($("#pkgDesc")) ;
		pkg.adultNum = trim($("#pkgAdultNum")) ;
		pkg.childrenNum = trim($("#pkgChildrenNum")) ;
		pkg.price = trim($("#pkgPrice")) ;
		pkg.perPrice = trimHtml($("#pkgPerPrice")) ;
		pkg.isPriceHasTax = $("#pkgHasTax").hasClass('active') ;
		if(!checkPkg(pkg)){
			return ;
		}
		if(!pkgSaveLock){
			pkgSaveLock = true ;
			if(isEmpty(pkg.pkgId)){
				$.ajax({
		    	    type: 'POST',
		    	    url: basepath + '/product/calendar/acquirePkgId' ,
		    	    contentType : 'application/json',
		    	    dataType: 'json' ,
		    	    success: function(result){
		    	    	if(result.success){
		    	    		pkg.pkgId = result.errMsg ;
		    	    		resetPkgListItem(pkg) ;
		    	    		closePricePopup() ;
		    	    	}else{
		    	    		popupTipWin("未能获取PKGID...") ;
		    	    	}
		    	    	pkgSaveLock = false ;
		    	    },error: function(){
		    	    	popupTipWin("保存套餐失败...") ;
		    	    	pkgSaveLock = false ;
		    	    }
		    	});
			}else{
				resetPkgListItem(pkg) ;
	    		closePricePopup() ;
	    		pkgSaveLock = false ;
			}
		}
	});
	
	$(".cancelPkg").click(function(){
		if((old_pkgId == $("#pkgId").val() && old_pkgName == trim($("#pkgName")) && old_pkgDesc == trim($("#pkgDesc"))
				&& old_pkgAdultNum == trim($("#pkgAdultNum")) && old_pkgChildrenNum == trim($("#pkgChildrenNum"))
				&& old_pkgPrice == trim($("#pkgPrice")) && old_pkgPerPrice == trimHtml($("#pkgPerPrice")) 
				&& old_pkgHasTax == $("#pkgHasTax").hasClass('active')) 
				){
			closePricePopup() ;
		}else{
			$("#pkgDoubleCheck").modal('show') ;
		}
	});
	
	$("#pkgAdultNum,#pkgChildrenNum,#pkgPrice").blur(function(){
		var price = trim($("#pkgPrice")) ;
		var adultNum = trim($("#pkgAdultNum")) ;
		var childNum = trim($("#pkgChildrenNum")) ;
		if(isNaN(price) || isNaN(adultNum) || isNaN(childNum)){
			$("#pkgPrice").val("") ;
			return ;
		}
		price = parseInt(price) ;
		adultNum = parseInt(adultNum) ;
		childNum = parseInt(childNum) ;
		if(adultNum + childNum == 0){
			$("#pkgPrice").val("") ;
			return ;
		}
		$("#pkgPerPrice").html(Math.round(price/(adultNum + childNum))) ;
	});
	
	//保存
	$(".commonButton.orange-f79767Btn.save").click(function(){
		var salesPackages = [] ;
		$(".prod-item").each(function(){
			var salesPackage = {} ;
			salesPackage.pkgId = $($(this).find('.pkgId')[0]).val() ;
			salesPackage.name = escape2Html($($(this).find('.pkgName')[0]).html()) ;
			salesPackage.price = $($(this).find('.pkgPrice')[0]).html() ;
			salesPackage.desc = escape2Html($($(this).find('.pkgDesc')[0]).val()) ;
			salesPackage.adultNum = $($(this).find('.pkgAdultNum')[0]).val() ;
			salesPackage.childrenNum = $($(this).find('.pkgChildrenNum')[0]).val() ;
			salesPackage.perPrice = $($(this).find('.pkgPerPrice')[0]).val() ;
			salesPackage.isPriceHasTax = $($(this).find('.pkgHasTax')[0]).val() ;
			salesPackages.push(salesPackage) ;
		})
		if(old_stock ==  trim($("#stockInputer")) && old_marketPrice == trim($("#marketPriceInputer"))
				&& old_hours == $("#inAdvanceHoursInputer").val() && old_days == $("#inAdvanceDaysInputer").val()
				&& old_adultPrice == trim($("#adultPriceInputer")) && old_childPrice == trim($("#childPriceInputer"))
				&& old_isAdultPriceHasTax == $("#adultTaxChecker").hasClass('active')
				&& old_hasChildPrice == $("#childChecker").hasClass('active')
				&& old_isChildPriceHasTax == $("#childTaxChecker").hasClass('active')
				&& old_singleRoomPrice == trim($("#singleRoomPriceInputer"))){
			if(old_pkgs.length != salesPackages.length){
				$("#saveConfirm").modal('show') ;
			}else{
				var isPkgUpd = false ;
				for(var i=0;i<salesPackages.length;i++){
					var salesPackage = salesPackages[i] ;
					var haveSame = false ;
					var isSame = false ;
					for(var j=0;j<old_pkgs.length;j++){
						var oldSalesPackage = old_pkgs[j] ;
						if(oldSalesPackage.pkgId == salesPackage.pkgId){
							haveSame = true ;
							if(salesPackage.name == escape2Html(oldSalesPackage.name) && salesPackage.price == oldSalesPackage.price
									&& salesPackage.desc == escape2Html(oldSalesPackage.desc) && salesPackage.adultNum == oldSalesPackage.adultNum
									&& salesPackage.childrenNum == oldSalesPackage.childrenNum && salesPackage.perPrice == oldSalesPackage.perPrice
									&& salesPackage.isPriceHasTax == (oldSalesPackage.isPriceHasTax + '')){
								isSame = true ;
							}
						}
					}
					if(!haveSame){
						isPkgUpd = true ;
						break ;
					}else{
						if(!isSame){
							isPkgUpd = true ;
							break ;
						}
					}
				}
				if(isPkgUpd){
					$("#saveConfirm").modal('show') ;
				}else{
					saveConfirmed() ;
				}
			}
		}else{
			$("#saveConfirm").modal('show') ;
		}
		
	});
	
	//批量关闭
	$(".commonButton.blue-45c8dcButton.closeBtn").click(function(){
		var isOk = true ;
		var sale = {} ;
		sale.id = $("#id").val() ;
		var now = $("#selectedDay").val() ;
		sale.start = $(".datepicker.startDate.hasIcon").val() ;
		if(!s2.test(sale.start)){
			$(".datepicker.startDate.hasIcon").focus() ;
			isOk = false ;
			popupTipWin('日期格式:' + m2) ;
			return false ;
		}
		sale.end = $(".datepicker.endDate.hasIcon").val() ;
		if(!s2.test(sale.end)){
			$(".datepicker.endDate.hasIcon").focus() ;
			isOk = false ;
			popupTipWin('日期格式:' + m2) ;
			return false ;
		}
		var weekDays = '' ;
		$(".checkboxContent.week-days .active").each(function(){
			var html = $(this).html().replace(/\ +/g,"")
			weekDays += html.substring(34,35) + " " ;
		})
		sale.weekDays = weekDays ;
		if(isOk){
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/calendar/batchClose' ,
	    	    data: JSON.stringify(sale) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	if(result == ''){
	    	    		var date = new Date(now.split("-")[0], now.split("-")[1] - 1, now.split("-")[2], 0, 0, 0) ;
	    	    		getMonthData(".price-calendar", date) ;
	    	    	}else{
	    	    		popupTipWin(result) ;
	    	    	}
	    	    }
	    	});
		}
	});
	
	//批量删除
	$(".commonButton.red-fe6869Button.deleteBtn").click(function(){
		var isOk = true ;
		var sale = {} ;
		sale.id = $("#id").val() ;
		var now = $("#selectedDay").val() ;
		sale.start = $(".datepicker.startDate.hasIcon").val() ;
		if(!s2.test(sale.start)){
			$(".datepicker.startDate.hasIcon").focus() ;
			isOk = false ;
			popupTipWin('日期格式:' + m2) ;
			return false ;
		}
		sale.end = $(".datepicker.endDate.hasIcon").val() ;
		if(!s2.test(sale.end)){
			$(".datepicker.endDate.hasIcon").focus() ;
			isOk = false ;
			popupTipWin('日期格式:' + m2) ;
			return false ;
		}
		var weekDays = '' ;
		$(".checkboxContent.week-days .active").each(function(){
			var html = $(this).html().replace(/\ +/g,"")
			weekDays += html.substring(34,35) + " " ;
		})
		sale.weekDays = weekDays ;
		if(isOk){
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/calendar/batchDelete' ,
	    	    data: JSON.stringify(sale) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	if(result == ''){
	    	    		var date = new Date(now.split("-")[0], now.split("-")[1] - 1, now.split("-")[2], 0, 0, 0) ;
	    	    		getMonthData(".price-calendar", date) ;
	    	    	}else{
	    	    		popupTipWin(result) ;
	    	    	}
	    	    }
	    	});
		}
	});
	
	//批量设置未开放
	$(".commonButton.gray-bbbButton.canOrderSet").click(function(){
		var isOk = true ;
		var sale = {} ;
		sale.id = $("#id").val() ;
		var now = $("#selectedDay").val() ;
		sale.start = $(".datepicker.startDate.hasIcon").val() ;
		if(!s2.test(sale.start)){
			$(".datepicker.startDate.hasIcon").focus() ;
			isOk = false ;
			popupTipWin('日期格式:' + m2) ;
			return false ;
		}
		sale.end = $(".datepicker.endDate.hasIcon").val() ;
		if(!s2.test(sale.end)){
			$(".datepicker.endDate.hasIcon").focus() ;
			isOk = false ;
			popupTipWin('日期格式:' + m2) ;
			return false ;
		}
		var weekDays = '' ;
		$(".checkboxContent.week-days .active").each(function(){
			var html = $(this).html().replace(/\ +/g,"")
			weekDays += html.substring(34,35) + " " ;
		})
		sale.weekDays = weekDays ;
		if(isOk){
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/calendar/batchCantOrder' ,
	    	    data: JSON.stringify(sale) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	if(result == ''){
	    	    		var date = new Date(now.split("-")[0], now.split("-")[1] - 1, now.split("-")[2], 0, 0, 0) ;
	    	    		getMonthData(".price-calendar", date) ;
	    	    	}else{
	    	    		popupTipWin(result) ;
	    	    	}
	    	    }
	    	});
		}
	});
});

function checkSale(opt,maxlen){
	var re = /^[1-9]+[0-9]*]*$/;
	if(!re.test(opt) || parseInt(opt).toString().length < 1 || parseInt(opt).toString().length > maxlen){
		return false ;
	}else{
		return true ;
	}
}

function checkSale2(opt,maxlen){
	var re = /^[0-9]+[0-9]*]*$/;
	if(!re.test(opt) || parseInt(opt).toString().length < 1 || parseInt(opt).toString().length > maxlen){
		return false ;
	}else{
		return true ;
	}
}

function checkDays(opt,maxlen){
	var re = /^[1-9]+[0-9]*]*$/;
	if(opt == '0'){
		return true ;
	}
	if(!re.test(opt) || parseInt(opt).toString().length < 1 || parseInt(opt).toString().length > maxlen){
		return false ;
	}else{
		return true ;
	}
}

function saveConfirmed(){
	$("#saveConfirm").modal('hide') ;
	isSaleOk = true ;
	var sale = {} ;
	sale.id = $("#id").val() ;
	if($("#saleUnitInputer").parent().hasClass("active")){
		sale.saleUnit = "SINGLE" ;
	}
	sale.adultPrice = $("#adultPriceInputer").val() ;		
	sale.isAdultPriceHasTax = $("#adultTaxChecker").hasClass("active") ;
	sale.isChildPriceHasTax = $("#childTaxChecker").hasClass("active") ;
	sale.hasChildPrice = $("#childChecker").hasClass("active") ;		
	sale.singleRoomPrice = $("#singleRoomPriceInputer").val() ;		
	sale.stock = trim($("#stockInputer")) ;
	sale.marketPrice = trim($("#marketPriceInputer")) ;
	sale.inAdvanceDays = $("#inAdvanceDaysInputer").val() ;
	sale.inAdvanceHours = $("#inAdvanceHoursInputer").val() ;
	
	var salesPackages = [] ;
	$(".prod-item").each(function(){
		var salesPackage = {} ;
		salesPackage.pkgId = $($(this).find('.pkgId')[0]).val() ;
		salesPackage.name = escape2Html($($(this).find('.pkgName')[0]).html()) ;
		salesPackage.price = $($(this).find('.pkgPrice')[0]).html() ;
		salesPackage.desc = escape2Html($($(this).find('.pkgDesc')[0]).val()) ;
		salesPackage.adultNum = $($(this).find('.pkgAdultNum')[0]).val() ;
		salesPackage.childrenNum = $($(this).find('.pkgChildrenNum')[0]).val() ;
		salesPackage.perPrice = $($(this).find('.pkgPerPrice')[0]).val() ;
		salesPackage.isPriceHasTax = $($(this).find('.pkgHasTax')[0]).val() ;
		salesPackages.push(salesPackage) ;
	})
	sale.salesPackages = salesPackages ;
	if($("#saveType").val() == batchSave){
		//批量保存
		var now = $("#selectedDay").val() ;
		sale.start = $(".datepicker.startDate.hasIcon").val() ;
		if(!s2.test(sale.start)){
			$(".datepicker.startDate.hasIcon").focus() ;
			popupTipWin('日期格式:' + m2) ;
			isSaleOk = false ;
			return false ;
		}
		sale.end = $(".datepicker.endDate.hasIcon").val() ;
		if(!s2.test(sale.end)){
			$(".datepicker.endDate.hasIcon").focus() ;
			popupTipWin('日期格式:' + m2) ;
			isSaleOk = false ;
			return false ;
		}
		var weekDays = '' ;
		$(".checkboxContent.week-days .active").each(function(){
			var html = $(this).html().replace(/\ +/g,"")
			weekDays += html.substring(34,35) + " " ;
		})
		sale.weekDays = weekDays ;
		var needCheckPrice = $("#batchUpdPrice").hasClass('active') ;
		sale.needUpdPrice = needCheckPrice ;
		if(!needCheckPrice && salesPackages.length == 0){
			popupTipWin('无新增套餐信息，请重新设置;') ;
			isSaleOk = false ;
			return false ;
		}
		isSaleOk = saleCheck(sale, needCheckPrice) ;
		if(isSaleOk && !saleSaveLock){
			saleSaveLock = true ;
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/calendar/batchSave' ,
	    	    data: JSON.stringify(sale) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	if(result.success){
	    	    		var date = new Date(now.split("-")[0], now.split("-")[1] - 1, now.split("-")[2], 0, 0, 0) ;
	    	    		getMonthData(".price-calendar", date) ;
	    	    		old_stock = sale.stock ;
	    		    	old_marketPrice = sale.marketPrice ;
	    		    	old_pkgs = sale.salesPackages ;
	    		    	old_hours = sale.inAdvanceHours ;
	    		    	old_days = sale.inAdvanceDays ;
	    		    	old_adultPrice = sale.adultPrice ;
	    		    	old_childPrice = sale.childPrice == undefined ? "" : sale.childPrice ;
	    		    	old_isAdultPriceHasTax = sale.isAdultPriceHasTax ;
	    		    	old_hasChildPrice = sale.hasChildPrice ;
	    		    	old_isChildPriceHasTax = sale.isChildPriceHasTax ;
	    		    	old_singleRoomPrice = sale.singleRoomPrice ;
	    	    		popupTipWin('价格保存成功') ;
	    	    		$(".pkgdel").hide() ;
	    	    		$(".pkgedit").hide() ;
	    	    	}else{
	    	    		if(result.errMsg == 'NO_DAYS'){
	    	    			popupTipWin('无有效日期') ;
	    	    		}else{
	    	    			popupTipWin('价格保存失败') ;
	    	    		}
	    	    	}
	    	    	saleSaveLock = false ;
	    	    },error: function(){
	    	    	saleSaveLock = false ;
	    	    }
	    	});
		}
	}else if($("#saveType").val() == singleSave){
		isSaleOk = saleCheck(sale, true) ;
		//单日保存
		sale.effectDay = $("#selectedDay").val() ;
		if(isSaleOk && !saleSaveLock){
			saleSaveLock = true ;
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/calendar/singleSave' ,
	    	    data: JSON.stringify(sale) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	if(result){
	    	    		var date = new Date(sale.effectDay.split("-")[0], sale.effectDay.split("-")[1] - 1, sale.effectDay.split("-")[2], 0, 0, 0) ;
	    	    		getMonthData(".price-calendar", date) ;
	    	    		old_stock = sale.stock ;
	    		    	old_marketPrice = sale.marketPrice ;
	    		    	old_pkgs = sale.salesPackages ;
	    		    	old_hours = sale.inAdvanceHours ;
	    		    	old_days = sale.inAdvanceDays ;
	    		    	old_adultPrice = sale.adultPrice ;
	    		    	old_childPrice = sale.childPrice == undefined ? "" : sale.childPrice ;
	    		    	old_isAdultPriceHasTax = sale.isAdultPriceHasTax ;
	    		    	old_hasChildPrice = sale.hasChildPrice ;
	    		    	old_isChildPriceHasTax = sale.isChildPriceHasTax ;
	    		    	old_singleRoomPrice = sale.singleRoomPrice ;
	    	    		popupTipWin('价格保存成功') ;
	    	    	}
	    	    	saleSaveLock = false ;
	    	    },error: function(){
	    	    	saleSaveLock = false ;
	    	    }
	    	});
		}
	}
}

function saleCheck(sale,needUpdPrice){
	var ret = true ;
	if(!needUpdPrice){
		return ret ;
	}
	if(!checkSale(sale.adultPrice,6)){		
		$("#adultPriceInputer").focus() ;	
		popupTipWin('成人价' + m1) ;
		ret = false ;
		return ret ;
	}	
	if(sale.hasChildPrice){		
		sale.childPrice = $("#childPriceInputer").val() ;		
		if(!checkSale(sale.childPrice,6)){		
			$("#childPriceInputer").focus() ;		
			popupTipWin('儿童价' + m1) ;
			ret = false ;
			return ret ;
		}		
	}else{		
		$("#childPriceInputer").val("") ;		
	}
	if(!checkSale2(sale.singleRoomPrice,6)){		
		$("#singleRoomPriceInputer").focus() ;		
		popupTipWin('单房差' + m1) ;
		ret = false ;
		return ret ;
	}
	if(isEmpty(sale.stock) || isNaN(sale.stock) || !isPositiveNum(sale.stock) || parseInt(sale.stock) > 100 || parseInt(sale.stock) < 0){
		$("#stockInputer").focus() ;
		popupTipWin('库存' + m1) ;
		ret = false ;
		return ret ;
	}
	if(isEmpty(sale.marketPrice) || isNaN(sale.marketPrice) || !isPositiveNum(sale.marketPrice) || parseInt(sale.marketPrice) > 999999 || parseInt(sale.marketPrice) < 0){
		$("#marketPriceInputer").focus() ;
		popupTipWin('市场价' + m1) ;
		ret = false ;
		return ret ;
	}
	if(!checkDays(sale.inAdvanceDays,2)){
		$("#inAdvanceDaysInputer").focus() ;
		popupTipWin('提前预定天数' + m1) ;
		ret = false ;
		return ret ;
	}
	if(!s1.test(sale.inAdvanceHours)){
		$("#inAdvanceHoursInputer").focus() ;
		popupTipWin('提前预定点数' + m1) ;
		ret = false ;
		return ret ;
	}
	return ret ;
}

function toggleMode(){
	$("#batchUpdPrice").parent().hide() ;
	if(isEdit()){
		$(".commonButton.blue-45c8dcButton.batch-set-btn").css("display","") ;
		$("#adultPriceInputer").attr("readonly",false) ;		
		$("#childPriceInputer").attr("readonly",false) ;		
		$("#singleRoomPriceInputer").attr("readonly",false) ;
		$("#stockInputer").attr("readonly",false) ;
		$("#marketPriceInputer").attr("readonly",false) ;
		$("#inAdvanceDaysInputer").attr("readonly",false) ;
		$("#inAdvanceHoursInputer").attr("readonly",false) ;
		$("#usedStockInputer").attr("readonly",false) ;
		$(".commonButton.orange-f79767Btn.save").css("display","") ;
		$(".commonButton.gray-bbbButton.cancel").css("display","") ;
		if($("#saveType").val() != singleSave){
			$(".commonButton.blue-45c8dcButton.closeBtn").css("display","") ;
			$(".commonButton.red-fe6869Button.deleteBtn").css("display","") ;
			$(".commonButton.gray-bbbButton.canOrderSet").css("display","") ;
		}
		$(".commonButton.blue-45c8dcButton.addPriceType").css("display","") ;
		$(".commonButton.red-fe6869Button.pkgdel").css("display","") ;
		$(".commonButton.blue-45c8dcButton.pkgedit").css("display","") ;
	}else{
		$(".commonButton.blue-45c8dcButton.batch-set-btn").css("display","none") ;
		$("#adultPriceInputer").attr("readonly",true) ;		
		$("#childPriceInputer").attr("readonly",true) ;		
		$("#singleRoomPriceInputer").attr("readonly",true) ;
		$("#stockInputer").attr("readonly",true) ;
		$("#marketPriceInputer").attr("readonly",true) ;
		$("#inAdvanceDaysInputer").attr("readonly",true) ;
		$("#inAdvanceHoursInputer").attr("readonly",true) ;
		$("#usedStockInputer").attr("readonly",true) ;
		$(".commonButton.orange-f79767Btn.save").css("display","none") ;
		$(".commonButton.gray-bbbButton.cancel").css("display","none") ;
		$(".commonButton.blue-45c8dcButton.closeBtn").css("display","none") ;
		$(".commonButton.red-fe6869Button.deleteBtn").css("display","none") ;
		$(".commonButton.gray-bbbButton.canOrderSet").css("display","none") ;
		$(".commonButton.red-fe6869Button.pkgdel").css("display","none") ;
		$(".commonButton.blue-45c8dcButton.pkgedit").css("display","none") ;
		$(".commonButton.blue-45c8dcButton.addPriceType").css("display","none") ;
	}
}

function saveCheck(nextStep){
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/calendar/saveCheck' ,
	    data: "id=" + $("#id").val() ,
	    dataType: 'json' ,
	    success: function(result){
	    	if(result && nextStep){
    			//跳转推荐行程
	    		window.location.href = basepath + '/product/recommendTrip/'+$("#mode").val()+'/' + $("#id").val() ;
	    	}else if(result && !nextStep){
	    		window.location.href = basepath + '/product/calendar/'+$("#mode").val()+'/' + $("#id").val() ;
	    	}
	    }
	});
}

function isEdit(){
	return $("#mode").val() == 'edit' ;
}

function genePriceCalendar(ele, data) {

    $(ele).datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        weekStart: 0,
        beforeShowDay: function(){
            $(ele).find("td .active").removeClass("active");
        }
    });

    var date = new Date();

    init(ele, date) ;

    $(ele).find(".change-calendar").click(function(){
        if ($(this).hasClass("prev-year")) {    //  前一年
            date = moveMonth(date, -12);

        } else if ($(this).hasClass("next-year")) {  //  后一年
            date = moveMonth(date, 12);

        } else if ($(this).hasClass("prev-month")) {  //  前一月
            date = moveMonth(date, -1);

        } else if ($(this).hasClass("next-month")) {  //  后一月
            date = moveMonth(date, 1);

        }
        getMonthData(ele, date) ;
    });

}

function getMonthData(ele, date){
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/calendar/getMonthData' ,
	    data: "now=" + getStandardDate(date) + "&id=" + $("#id").val() ,
	    dataType: 'json' ,
	    success: function(result){
	    	$("#data").html(JSON.stringify(result)) ;
	    	$(ele).datepicker("setDate", date);
	        init(ele, date) ;
	    }
	});
}

function init(ele, date){
	/*  这部分代码可以封装成一个单独的方法， 初始化和每次日历翻动都需要用*/
	/* wrap  start */
	setCalendarTitle(ele, date);
	geneCalendarTdContent(date, strToJson($("#data").html()));

	$(ele).find("td.active").removeClass("active");  //  默认会选中日期，active会有样式
	$(ele).find("tbody td").click(function(){
	    return false;  //  解除默认的td点击事件，否则会重写日历结构
	});

	$(".tdCont").click(function(event){
	    $(this).parents("table").find(".tdCont").removeClass("current");
	    $(this).addClass("current");
	    if($(this).hasClass("add") || $(this).hasClass("edit")){
	    	$(".right-cont-block").show() ;
	    	$(".set-price-container").show() ;
	    	$(".date-range").hide() ;
	    	$(".default-tip").hide() ;
	    }
	    if($(this).hasClass("add")){
	    	//单日新增
	    	var curDay = $(this).find("span").attr("data-daynum") ;
	    	showDate(curDay) ;
	    	$("#selectedDay").val(curDay) ;
	    	getCost(curDay) ;
	    	old_stock = '' ;
	    	old_marketPrice = '' ;
	    	old_pkgs = [] ;
	    	old_hours = '' ;
	    	old_days = '' ;
	    	old_adultPrice = '' ;
	    	old_childPrice = '' ;
	    	old_isAdultPriceHasTax = true ;
	    	old_hasChildPrice = true ;
	    	old_isChildPriceHasTax = true ;
	    	old_singleRoomPrice = '' ;
	    }else if($(this).hasClass("edit")){
	    	//单日修改
	    	var curDay = $(this).find(".day").attr("data-daynum") ;
	    	showDate(curDay) ;
	    	$("#selectedDay").val(curDay) ;
	    	getCost(curDay) ;
	    	getSale(curDay) ;
	    }
		$(".commonButton.blue-45c8dcButton.closeBtn").css("display","none") ;
		$(".commonButton.red-fe6869Button.deleteBtn").css("display","none") ;
		$(".commonButton.gray-bbbButton.canOrderSet").css("display","none") ;
	    event.stopPropagation();    //  同一个td内的很多元素都绑定点击事件，注意阻止事件冒泡
	});

	$(".tdCont .left-btn").click(function(event){
		//删除当日
		var curDay = $(this).parent().parent().parent().find(".top-block.clearfix .day").attr("data-daynum") ;
		$.ajax({
		    type: 'POST',
		    url: basepath + '/product/calendar/deleteSale' ,
		    data: "now=" + curDay + "&id=" + $("#id").val() ,
		    dataType: 'json' ,
		    success: function(result){
		    	if(result){
			    	var date = new Date(curDay.split("-")[0], curDay.split("-")[1] - 1, curDay.split("-")[2], 0, 0, 0) ;
    	    		getMonthData(".price-calendar", date) ;
			    }
		    }
		});
	    event.stopPropagation();
	});

	$(".tdCont .right-btn").click(function(event){
		var curDay = $(this).parent().parent().parent().find(".top-block.clearfix .day").attr("data-daynum") ;
		var close = false ;
		if($(this).html() == '关闭'){
			close = true ;
		}
		$.ajax({
		    type: 'POST',
		    url: basepath + '/product/calendar/toggleClose' ,
		    data: "close=" + close + "&now=" + curDay + "&id=" + $("#id").val() ,
		    dataType: 'json' ,
		    success: function(result){
		    	if(result){
		    		var date = new Date(curDay.split("-")[0], curDay.split("-")[1] - 1, curDay.split("-")[2], 0, 0, 0) ;
		    		getMonthData(".price-calendar", date) ;
		    	}
		    }
		});
	    event.stopPropagation();
	});
	
	$(".tdCont .top-btn").click(function(event){
		var curDay = $(this).parent().parent().parent().find(".top-block.clearfix .day").attr("data-daynum") ;
		var canOrder = true ;
		if($(this).html() == '未开放'){
			canOrder = false ;
		}
		$.ajax({
		    type: 'POST',
		    url: basepath + '/product/calendar/toggleCanOrder' ,
		    data: "canOrder=" + canOrder + "&now=" + curDay + "&id=" + $("#id").val() ,
		    dataType: 'json' ,
		    success: function(result){
		    	if(result){
		    		var date = new Date(curDay.split("-")[0], curDay.split("-")[1] - 1, curDay.split("-")[2], 0, 0, 0) ;
		    		getMonthData(".price-calendar", date) ;
		    	}
		    }
		});
	    event.stopPropagation();
	});

	/* wrap  end */
}

function toggleCostInfo(isHide, isPackage, isCombination, isContainFlightCost, isContainHotelCost){
	if(isHide){
		$(".sub-title.flight-cost").css("display","none") ;
		$(".sub-title.flight-cost").next("table").css("display","none") ;
		$(".sub-title.hotel-cost").css("display","none") ;
		$(".sub-title.hotel-cost").next("table").css("display","none") ;
		$(".sub-title.package-cost").css("display","none") ;
		$(".sub-title.package-cost").next("table").css("display","none") ;
		$(".sub-title.teamnum").css("display","none") ;
		$(".sub-title.teamnum").next("table").css("display","none") ;
		$(".sub-title.addition-cost").css("display","none") ;
		$(".sub-title.addition-cost").next("table").css("display","none") ;
		return ;
	}
	$(".sub-title.teamnum").css("display","") ;
	$(".sub-title.teamnum").next("table").css("display","") ;
	$(".sub-title.addition-cost").css("display","") ;
	$(".sub-title.addition-cost").next("table").css("display","") ;
	if(isPackage){
		$(".sub-title.flight-cost").css("display","none") ;
		$(".sub-title.flight-cost").next("table").css("display","none") ;
		$(".sub-title.hotel-cost").css("display","none") ;
		$(".sub-title.hotel-cost").next("table").css("display","none") ;
		$(".sub-title.package-cost").css("display","") ;
		$(".sub-title.package-cost").next("table").css("display","") ;
		return ;
	}
	if(isCombination){
		$(".sub-title.package-cost").css("display","none") ;
		$(".sub-title.package-cost").next("table").css("display","none") ;
		if(isContainFlightCost){
			$(".sub-title.flight-cost").css("display","") ;
			$(".sub-title.flight-cost").next("table").css("display","") ;
		}else{
			$(".sub-title.flight-cost").hide() ;
			$(".sub-title.flight-cost").next("table").hide() ;
		}
		if(isContainHotelCost){
			$(".sub-title.hotel-cost").css("display","") ;
			$(".sub-title.hotel-cost").next("table").css("display","") ;
		}else{
			$(".sub-title.hotel-cost").hide() ;
			$(".sub-title.hotel-cost").next("table").hide() ;
		}
		return ;
	}
}

function getCost(day){
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/calendar/getCost' ,
	    data: "now=" + day + "&id=" + $("#id").val() ,
	    dataType: 'json' ,
	    success: function(result){
	    	toggleCostInfo(false, result.isPackage, result.isCombination
	    			, result.isContainFlightCost, result.isContainHotelCost)
	    	if(result.isPackage){
	    		$("#packageAdultCost").html(result.packageAdultCost) ;
	    		$("#packageChildCost").html(result.packageChildCost) ;
	    	}
	    	if(result.isContainFlightCost){
	    		$("#flightAdultCost").html(result.flightAdultCost) ;
	    		$("#flightChildCost").html(result.flightChildCost) ;
	    	}
	    	if(result.isContainHotelCost){
	    		$("#hotelRoomCost").html(result.hotelRoomCost) ;
	    	}
	    	$("#teamNum").html(result.teamNum) ;
	    	if(result.isContainShuttleCost){
	    		$("#shuttleCost").html(result.shuttleCost) ;
	    	}else{
	    		$("#shuttleCostTh").hide() ;
	    		$("#shuttleCost").hide() ;
	    	}
	    	if(result.isContainWifiCost){
	    		$("#wifiCost").html(result.wifiCost) ;
	    	}else{
	    		$("#wifiCostTh").hide() ;
	    		$("#wifiCost").hide() ;
	    	}
	    	if(result.isContainOtherCost){
	    		$("#otherCost").html(result.otherCost) ;
	    	}else{
	    		$("#otherCostTh").hide() ;
	    		$("#otherCost").hide() ;
	    	}
	    	if(result.isContainZenbookCost){
	    		$("#zenbookCost").html(result.zenbookCost) ;
	    	}else{
	    		$("#zenbookCostTh").hide() ;
	    		$("#zenbookCost").hide() ;
	    	}
	    	$("#saveType").val(singleSave) ;
	    }
	});
	cleanSaleForm() ;
}

function getSale(day){
	$("#batchUpdPrice").parent().hide() ;
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/calendar/getSale' ,
	    data: "now=" + day + "&id=" + $("#id").val() ,
	    dataType: 'json' ,
	    success: function(sale){
	    	if(sale.saleUnit = "SINGLE"){
	    		if(!$("#saleUnitInputer").parent().hasClass("active")){
	    			$("#saleUnitInputer").parent().addClass("active") ;
	    		}
	    	}
	    	$("#adultPriceInputer").val(sale.adultPrice) ;		
	    	if(sale.adultPriceHasTax){		
	    		$("#adultTaxChecker").addClass("active") ;		
	    	}else{		
	    		$("#adultTaxChecker").removeClass("active") ;		
	    	}		
	    	if(sale.hasChildPrice){		
	    		$("#childChecker").addClass("active") ;		
	    	}else{		
	    		$("#childChecker").removeClass("active") ;		
	    	}		
	    	if(sale.childPrice != null){		
	    		$("#childPriceInputer").val(sale.childPrice) ;		
	    	}else{		
	    		$("#childPriceInputer").val("") ;		
	    	}		
	    	if(sale.childPriceHasTax){		
	    		$("#childTaxChecker").addClass("active") ;		
	    	}else{		
	    		$("#childTaxChecker").removeClass("active") ;		
	    	}		
	    	$("#singleRoomPriceInputer").val(sale.singleRoomPrice) ;
	    	$("#stockInputer").val(sale.stock) ;
	    	$("#marketPriceInputer").val(sale.marketPrice) ;
	    	$("#inAdvanceDaysInputer").val(sale.inAdvanceDays) ;
	    	$("#inAdvanceHoursInputer").val(sale.inAdvanceHours) ;
	    	$("#usedStockInputer").html(sale.usedStock) ;
	    	old_stock = sale.stock ;
	    	old_marketPrice = sale.marketPrice ;
	    	old_pkgs = [] ;
	    	old_hours = sale.inAdvanceHours ;
	    	old_days = sale.inAdvanceDays ;
	    	old_adultPrice = sale.adultPrice ;
	    	old_childPrice = sale.childPrice == undefined ? "" : sale.childPrice ;
	    	old_isAdultPriceHasTax = sale.adultPriceHasTax ;
	    	old_hasChildPrice = sale.hasChildPrice ;
	    	old_isChildPriceHasTax = sale.childPriceHasTax ;
	    	old_singleRoomPrice = sale.singleRoomPrice ;
	    	if(sale.salesPackages != null && sale.salesPackages != undefined){
	    		for(var i=0; i<sale.salesPackages.length ; i++){
	    			resetPkgListItem(sale.salesPackages[i]) ;
	    			old_pkgs.push(sale.salesPackages[i]) ;
	    		}
	    	}
	    	$("#saveType").val(singleSave) ;
	    	toggleMode() ;
	    }
	});
}

function cleanSaleForm(){
	$(".datepicker.startDate.hasIcon").val("") ;
	$(".datepicker.endDate.hasIcon").val("") ;
	$(".checkboxContent.week-days .checkboxInfo").each(function(){
		$(this).addClass("active") ;
	});
	$("#childChecker").addClass("active") ;
	$("#saleUnitInputer").parent().addClass("active") ;		
	$("#adultPriceInputer").val("") ;		
	$("#adultTaxChecker").addClass("active") ;		
	$("#childPriceInputer").val("") ;		
	$("#childTaxChecker").addClass("active") ;		
	$("#singleRoomPriceInputer").val("") ;
	$("#stockInputer").val("") ;
	$("#marketPriceInputer").val("") ;
	$("#inAdvanceDaysInputer").val("") ;
	$("#inAdvanceHoursInputer").val("") ;
	$("#usedStockInputer").html(0) ;
	$(".stock-cont").html("") ;
}

// 每次切换月份（含年份）都需要重新生成TD的内容
function geneCalendarTdContent(date, data) {

    var y = date.getFullYear(),
        m = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1),
        monthStr = y + "-" + m,
        monthDataArr = [];

    if (!!data && data != '') {
        monthDataArr = data.monthData;
    }

    $(".price-calendar .datepicker-days").find("tbody td").each(function(){
        var dayText = $(this).html();

        if ($(this).hasClass("old") || $(this).hasClass("new")) {
            $(this).html("<div class='tdCont disabled'></div>");
        } else {
            $(this).html(geneTdContTemplate(y, m, dayText, monthDataArr));
        }
    });
}

// year, month 参数只为了给当前td确定具体的日期值  "year-month-day"
function geneTdContTemplate(year, month, day, monthDataArr) {

    var template = "";

    var tmp = _.findWhere(monthDataArr, {day: day*1});

    if (tmp === undefined) {

        template += "<div class='tdCont'><div class='top-block clearfix'><span data-dayNum='" + year + "-" + month + "-" + day + "'>" + day +"</span></div>";
    } else {
		if (tmp.hasSale) {
    		template += "<div class='tdCont edit'>" ;
    	}else{
    		if(isEdit() && (tmp.flightFlag || tmp.hotelFlag)){
    			template += "<div class='tdCont add'>" ;
    		}else{
    			template += "<div class='tdCont'>" ;
    		}
    	}

        template += "<div class='top-block clearfix'><span class='day' data-dayNum='" + year + "-" + month + "-" + day + "'>" + day +"</span>";

        if (tmp.saleStatus == 'CLOSED') {  //  显示关闭图标
            template += "<span class='closed-icon'>关闭</span>";
        }else if (tmp.saleStatus == 'NOT_SCHEDULED') {  //  显示未开放图标
            template += "<span class='canntorder-icon'>未开放</span>";
        }else {
            template += "";
        }

        if (tmp.totalNum == 0) {  //  显示售完图标
            template += "<span class='stock'><i class='stock-icon'>售完</i></span></div>";
        }else if((!!tmp.totalNum || tmp.totalNum == 0) && (!!tmp.soldNum || tmp.soldNum == 0)){//  显示售卖数量
        	template += "<span class='stock'><em class='sold-num'>" + tmp.soldNum + "</em><em>/</em><em class='total-num'>" + tmp.totalNum +"</em></span></div>";
        }else{
        	template += "<span class='stock'></span></div>";
        }

        if (tmp.adultPrice != 0 && !!tmp.adultPrice) {  //  显示成人价格
        	template += "<div class='middle-block'><span class='adult-price'>￥" + tmp.adultPrice + "</span>";
        } else {
        	template += "<div class='middle-block'><span class='adult-price'></span>";
        }
        
        if (tmp.childPrice != 0 && !!tmp.childPrice) {   //  显示儿童价格 		
        	template += "<span class='child-price'>￥" + tmp.childPrice + "</span></div>";		
        } else {		
        	template += "<span class='child-price'></span></div>";		
        }

        if (tmp.flightFlag) {   //  显示机票图标
            template += "<div class='bottom-block'><span class='flight-icon'></span>";
        } else {
            template += "<div class='bottom-block'>";
        }

        if (tmp.hotelFlag) {   //   显示酒店图标
            template += "<span class='hotel-icon'></span>";
        }
        if (tmp.hasSale && isEdit()){
        	template += "<div class='oper-btn'><span class='left-btn'>删除</span><span class='right-btn'>";

            if (tmp.saleStatus != 'RELEASED') {
                template += "上线</span></div></div>";
            } else {
                template += "关闭</span><span class='top-btn'>未开放</span></div></div>";
            }
        }

    }

    template += "</div>";
    return template;
}

function moveMonth(date, dir) {

    if (!(date instanceof Date)) {   //  如果date不是日期类型
        return undefined;
    }

    if (!dir) {   // 如果 dir 参数为空，返回date
        return (date instanceof Date) ? date : undefined;
    }

    var dir_num = parseInt(dir),
        dir_absNum = Math.abs(dir_num),
        year = date.getFullYear(),
        month = date.getMonth(),
        day = date.getDate(),
        result_date;

    if (dir_num < 0) {   //  日期往前滚
        result_date = new Date(year, month - dir_absNum);

    } else if (dir_num > 0) {  // 日期往后滚
        result_date = new Date(year, month + dir_absNum);
    }

    return result_date;
}

function setCalendarTitle(ele, date) {

    $(ele).find(".cal-year-text .num").html(date.getFullYear());
    if (date.getFullYear() == new Date().getFullYear()) {
        $(ele).find(".cal-year-text .num").addClass("current");
    } else {
        $(ele).find(".cal-year-text .num").removeClass("current");
    }

    $(ele).find(".cal-month-text .num").html(date.getMonth() + 1);
    if (date.getMonth() == new Date().getMonth()) {
        $(ele).find(".cal-month-text .num").addClass("current");
    } else {
        $(ele).find(".cal-month-text .num").removeClass("current");
    }
}

function showDate(str){
	var date = new Date(str.split("-")[0], str.split("-")[1] - 1, str.split("-")[2], 0, 0, 0) ;
	var html = getStandardDate(date) ;
	html += " " ;
	switch(date.getDay()){
		case 1:
			html += "周一" ;
			break ;
		case 2:
			html += "周二" ;
			break ;
		case 3:
			html += "周三" ;
			break ;
		case 4:
			html += "周四" ;
			break ;
		case 5:
			html += "周五" ;
			break ;
		case 6:
			html += "周六" ;
			break ;
		case 0:
			html += "周日" ;
			break ;
		default:break ;
	}
	$(".show-date").html(html) ;
	$(".show-date").show() ;
}

function deletePkg(that){
	$("#pkgDeleteConfirm").modal('show') ;
	var item = $(that).parent().parent() ;
	var pkgId = $($(item).find('.pkgId')[0]).val() ;
	$("#currentDelPkgId").val(pkgId) ;
}

function deletePkgConfirmed(){
	$("#pkgDeleteConfirm").modal('hide') ;
	var id = $("#id").val() ;
	var effectDay = $("#selectedDay").val() ;
	var pkgId = $("#currentDelPkgId").val() ;
	if(pkgId == ''){
		return ;
	}
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/calendar/deletePkg' ,
	    data: "id=" + id + "&effectDay=" + effectDay + "&pkgId=" + pkgId ,
	    dataType: 'json' ,
	    success: function(result){
	    	if(result){
	    		$("#currentDelPkgId").val('') ;
	    		$(".prod-item."+pkgId).remove() ;
		    	var date = new Date(effectDay.split("-")[0], effectDay.split("-")[1] - 1, effectDay.split("-")[2], 0, 0, 0) ;
	    		getMonthData(".price-calendar", date) ;
	    	}else{
	    		popupTipWin('删除套餐失败...') ;
	    	}
	    }
	});
}

function setPkgEditModal(that){
	var item = $(that).parent().parent() ;
	$("#pkgId").val($($(item).find('.pkgId')[0]).val()) ;
	$("#pkgName").val(escape2Html($($(item).find('.pkgName')[0]).html())) ;
	$("#pkgDesc").val(escape2Html($($(item).find('.pkgDesc')[0]).val())) ;
	$("#pkgAdultNum").val($($(item).find('.pkgAdultNum')[0]).val()) ;
	$("#pkgChildrenNum").val($($(item).find('.pkgChildrenNum')[0]).val()) ;
	$("#pkgPrice").val($($(item).find('.pkgPrice')[0]).html()) ;
	$("#pkgPerPrice").html($($(item).find('.pkgPerPrice')[0]).val()) ;
	if($($(item).find('.pkgHasTax')[0]).val() != 'true'){
		$("#pkgHasTax").removeClass('active') ;
	}
	old_pkgId = $("#pkgId").val() ;
	old_pkgName = $("#pkgName").val() ;
	old_pkgDesc = $("#pkgDesc").val() ;
	old_pkgAdultNum = $("#pkgAdultNum").val() ;
	old_pkgChildrenNum = $("#pkgChildrenNum").val() ;
	old_pkgPrice = $("#pkgPrice").val() ;
	old_pkgPerPrice = $("#pkgPerPrice").html() ;
	old_pkgHasTax = $("#pkgHasTax").hasClass('active') ;
	$("#priceTypePopup .modal-title").html("编辑套餐类型") ;
	$("#priceTypePopup .savePkg").html("保存修改") ;
	$("#priceTypePopup").modal('show') ;
}

function closePricePopup(){
	$("#pkgId").val("") ;
	$("#pkgName").val("") ;
	$("#pkgDesc").val("") ;
	$("#pkgAdultNum").val("2") ;
	$("#pkgChildrenNum").val("0") ;
	$("#pkgPrice").val("") ;
	$("#pkgPerPrice").html("") ;
	if(!$("#pkgHasTax").hasClass('active')){
		$("#pkgHasTax").addClass('active') ;
	}
	$("#priceTypePopup").modal("hide");
}

function checkPkg(pkg){
	if(pkg == null){
		return false ;
	}
	if(isEmpty(pkg.name) || (pkg.name).indexOf('<script') >=0 || pkg.name.length > 30 || pkg.name.length < 1){
		$("#pkgName").focus() ;
		popupTipWin("套餐名称仅限输入字符N 1≤N≤30") ;
		return false ;
	}
	if(isEmpty(pkg.desc) || (pkg.desc).indexOf('<script') >=0 || pkg.desc.length > 100 || pkg.desc.length < 1){
		$("#pkgDesc").focus() ;
		popupTipWin("描述仅限输入字符N 1≤N≤100") ;
		return false ;
	}
	if(isEmpty(pkg.adultNum) || isNaN(pkg.adultNum) || !isPositiveNum(pkg.adultNum) || isZero(pkg.adultNum) || parseInt(pkg.adultNum) > 20 || parseInt(pkg.adultNum) < 0){
		$("#pkgAdultNum").focus() ;
		popupTipWin("成人数量仅限输入数字字符N 0&lt;N≤20") ;
		return false ;
	}
	if(isEmpty(pkg.childrenNum) || isNaN(pkg.childrenNum) || !isPositiveNum(pkg.childrenNum) || parseInt(pkg.childrenNum) > 20 || parseInt(pkg.childrenNum) < 0){
		$("#pkgChildrenNum").focus() ;
		popupTipWin("儿童数量仅限输入数字字符N 0≤N≤20") ;
		return false ;
	}
	if((parseInt(pkg.childrenNum) + parseInt(pkg.adultNum)) == 0){
		$("#pkgAdultNum").focus() ;
		popupTipWin("输入人数有误") ;
		return false ;
	}
	if(isEmpty(pkg.price) || isNaN(pkg.price) || !isPositiveNum(pkg.price) || parseInt(pkg.price) > 999999 || parseInt(pkg.price) < 1){
		$("#pkgPrice").focus() ;
		popupTipWin("套餐售价仅限输入数字字符N 1≤N≤999999") ;
		return false ;
	}
	
	return true ;
}

function popupTipWin(message){
	$("#tipMessage").html(message) ;
	$("#tipWin").modal('show') ;
}

function closePkgEditWin(){
	$("#pkgDoubleCheck").modal('hide') ;
	closePricePopup() ;
}

function resetPkgListItem(pkg){
	var isNew = true ;
	$(".prod-item").each(function(){
		if($(this).hasClass(pkg.pkgId)){
			$($(this).find('.pkgName')[0]).html(html2Escape(pkg.name)) ;
			$($(this).find('.pkgPrice')[0]).html(pkg.price) ;
			$($(this).find('.pkgDesc')[0]).val(html2Escape(pkg.desc)) ;
			$($(this).find('.pkgAdultNum')[0]).val(pkg.adultNum) ;
			$($(this).find('.pkgChildrenNum')[0]).val(pkg.childrenNum) ;
			$($(this).find('.pkgPerPrice')[0]).val(pkg.perPrice) ;
			$($(this).find('.pkgHasTax')[0]).val(pkg.isPriceHasTax) ;
			isNew = false ;
			return false;
		}
	})
	if(isNew){
		var html = "<div class='prod-item "+pkg.pkgId+"'>" ;
		html += "<p class='prod-title pkgName'>"+html2Escape(pkg.name)+"</p>" ;
		html += "<p class='prod-price'><span class='price'><em>￥</em><span class='pkgPrice'>"+pkg.price+"</span></span>" ;
		html += "<button class='commonButton red-fe6869Button pkgdel' onclick='javascript:deletePkg(this) ;'>删除</button>" ;
		html += "<button class='commonButton blue-45c8dcButton pkgedit' onclick='javascript:setPkgEditModal(this) ;'>编辑</button></p>" ;
		html += "<input type='hidden' class='pkgId' value='"+pkg.pkgId+"'/>" ;
		html += "<input type='hidden' class='pkgDesc' value='"+html2Escape(pkg.desc)+"'/>" ;
		html += "<input type='hidden' class='pkgAdultNum' value='"+pkg.adultNum+"'/>" ;
		html += "<input type='hidden' class='pkgChildrenNum' value='"+pkg.childrenNum+"'/>" ;
		html += "<input type='hidden' class='pkgPerPrice' value='"+pkg.perPrice+"'/>" ;
		html += "<input type='hidden' class='pkgHasTax' value='"+pkg.isPriceHasTax+"'/>" ;
		html += "</div>" ;
		$(".stock-cont").append(html) ;
	}
}
