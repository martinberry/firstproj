var lock = false;

var isAdditionalEdit = false;

$(function(){

	var addiProData = [];   // 存放附加产品数据
	var addiProTmpData = [];   // 存放附加产品临时数据

	//附加产品编辑
    getAddiProData();

	//附加产品编辑
    $(".additionEditBtn").click(function(){
    	isAdditionalEdit = true;
        $that = $(this).parents(".commonSmallTitleModel").siblings(".commonStyle");
        $(this).hide();
        $that.find(".additionContent").hide();
        $that.find(".additionEditContent").show();
        $that.find(".additionSave").show();
        $that.find(".additionAddDelete").show();
    });

    // 附件产品-保存操作时记录数据到当前页面上
    $("#saveAddiPro").click(function(){
		getAddiProTmpData();
		if ($('.addiProTable .verifyStyle:visible').size() == 0) {
			saveAddiProData(this);
		}
		isAdditionalEdit = false;
    });

    // 附件产品-取消操作时将已保存数据恢复至页面结构中
    $("#cancelAddiPro").click(function(){
        renderAddProTable();
        isAdditionalEdit = false;

        $(this).parents(".additionSave").hide();
        $cancel = $(this).parents(".additionSave").siblings(".addiProTable");
        $cancel.find(".additionContent").show();
        $cancel.find(".additionEditContent").hide();
        $cancel.find(".additionAddDelete").hide();
        $(this).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".additionEditBtn").show();
    });

    $(".commonOPOrderModel").delegate(".additionAdd","click",function(){
        $(this).parents("thead").siblings("tbody").append($("#addAdditionTr").html());
    });
    $(".commonOPOrderModel").delegate(".additionDelete","click",function(){
        $(this).parents("tr").remove();
    });

    $(".addiProTable").delegate('input[name="nameInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if (val == "") {
    		$(this).next(".dropdown-error").show();
		} else {
			$(this).next(".dropdown-error").hide();
		}
    });

    $(".addiProTable").delegate('input[name="priceInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if(checkSale(val,5)){
    		$(this).siblings(".price-error").hide();
    		var num = $.trim($(this).parents("tr").find("[name='numInputer']").val());
    		if(checkSale(num,3)){
    			var totalPrice = parseInt(val) * parseInt(num);
    			$(this).parents("tr").find("[class='totalPrice']").html(totalPrice + ".00");
    		}else{
    			$(this).parents("tr").find("[class='totalPrice']").html("");
    		}
    	}else{
			$(this).parents("tr").find("[class='totalPrice']").html("");
    		$(this).siblings(".price-error").show();
    	}
    });

    $(".addiProTable").delegate('input[name="numInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if(checkSale(val,3) && (parseInt(val) >= 1 && parseInt(val) <= 100)){
    		$(this).siblings(".num-error").hide();
            setTotalPrice(this, val);
    	}else{
			$(this).parents("tr").find("[class='totalPrice']").html("");
    		$(this).siblings(".num-error").show();
    	}
    });

  //添加或减少数量
    var least = 1;
    var most = 100;
    $(".commonOPOrderModel").delegate(".addAndSubtract > .subtractBtn","click",function(){
    	var minus_num = parseInt($(this).next(".numberFonts").val());
        minus_num--;
        if(minus_num < least){
            $(this).next().val(least);
            setTotalPrice(this, least);
            return false;
        }
        $(this).next(".numberFonts").val(minus_num);
        setTotalPrice(this, minus_num);
    });

    $(".commonOPOrderModel").delegate(".addAndSubtract > .addBtn","click",function(){
    	var add_num = parseInt($(this).prev(".numberFonts").val());
        add_num++;
        if(add_num > most){
            $(this).prev().val(most);
            setTotalPrice(this, most);
            return false;
        }
        $(this).prev(".numberFonts").val(add_num);
        setTotalPrice(this, add_num);
    });

});

function setTotalPrice(scope, num){
	var price = $(scope).parents("tr").find("[name='priceInputer']").val();
	if(checkSale(price,5)){
		price = price.trim();
		var totalPrice = parseInt(num) * parseInt(price);
		$(scope).parents("tr").find("[class='totalPrice']").html(totalPrice + ".00");
	}else{
		$(scope).parents("tr").find("[class='totalPrice']").html("");
	}
}

function checkSale(opt,maxlen){
	var re = /^[1-9]+[0-9]*]*$/;
	if(!re.test(opt) || parseInt(opt).toString().length < 1 || parseInt(opt).toString().length > maxlen){
		return false ;
	}else{
		return true ;
	}
}

function getAddiProData() {

    addiProData = [];
    $("#addiProTable tbody").find('tr').each(function(){
        var that = $(this);
        var addiProObj = {};
        addiProObj.name = that.find("input[type='text']").eq(0).val().trim();
        addiProObj.price = that.find("input[type='text']").eq(1).val().trim() + ".00";
        addiProObj.num = that.find("input[type='text']").eq(2).val().trim();
        addiProObj.totalPrice = that.find("[class='totalPrice']").html();
        addiProData.push(addiProObj);
    });
}

function getAddiProTmpData() {

	var flag = true;
    addiProTmpData = [];
    $("#addiProTable tbody").find('tr').each(function(){
        var that = $(this);
        var addiProObj = {};
        that.find("input[type='text']").eq(0).change();
        that.find("input[type='text']").eq(1).change();
        addiProObj.name = that.find("input[type='text']").eq(0).val().trim();
        addiProObj.price = that.find("input[type='text']").eq(1).val().trim() + ".00";
        addiProObj.num = that.find("input[type='text']").eq(2).val().trim();
        addiProObj.totalPrice = that.find("[class='totalPrice']").html();
        addiProTmpData.push(addiProObj);
    });
    return flag;
}

function changeAddiProTmpToData() {
    addiProData = addiProTmpData;
}


function renderAddProTable() {
    $("#addiProTable tbody").html("");
    $("#addiProTemplate").tmpl(addiProData).appendTo('#addiProTable tbody');
}

function saveAddiProData(scope){

	if(lock){
		return ;
	}else{
		lock = true;
	}

	 var orderId = $('#orderId').val();
	 var data = {
			"orderId":orderId,
			"additionalProducts": addiProTmpData
	}
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/updateAdditionalProduct',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : JSON.stringify(data),
		   dataType: "json",
		   success: function(resp){
			   lock = false;
			   if (resp.res_code == 'SF_ORDE_1002') {
				   refreshAdditionalProduct(scope);
				   refreshFeesDetail();
				}else if (resp.res_code == 'FF_ORDE_1005') {
					alert(resp.res_msg);
				}else if (resp.res_code == 'FAILURE') {
					alert(resp.res_msg);
				}else{
					alert("网络异常，请稍后重试！");
				}
		   },
		   error: function(result){
			   lock = false;
		   }
	 });
}

function refreshAdditionalProduct(scope){
	var orderId = $('#orderId').val();
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/additionalProduct/refresh/' + orderId,
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
		   dataType: "html",
		   success: function(json){
			   $(".addiProTable tbody").html(json);

			   $(scope).parents(".additionSave").hide();
		        $cancel = $(scope).parents(".additionSave").siblings(".addiProTable");
		        $cancel.find(".additionContent").show();
		        $cancel.find(".additionEditContent").hide();
		        $cancel.find(".additionAddDelete").hide();
		        $(scope).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".additionEditBtn").show();
		   }
	 });
}

function refreshFeesDetail(){
	var orderId = $('#orderId').val();
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/feesDetail/refresh/' + orderId,
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
		   dataType: "html",
		   success: function(json){
			   $(".feesDetailTab tbody").html(json);
			   $('.defaultRowSpan').children('th').attr('rowspan', $('.defaultRowSpan').size());

			   var scope = $("#cancelFeesDetailBtn");
			   $(scope).parents(".requiredSave").hide();
		        $requiredCcancel = $(scope).parents(".requiredSave").siblings(".feesDetailTab");
		        $requiredCcancel.find(".requiredContent").show();
		        $requiredCcancel.find(".requiredEditContent").hide();
		        $requiredCcancel.find(".requiredAddDelete").hide();
		        $(scope).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".priceEditBtn").show();
		   }
	 });
}