$(function(){

	submitFunc();

	//搜索框 输入框格式校验
	//真实姓名格式校验
	$("#pid_input").focusout(function(){
		var pId = trim(this);
		var reg=/^[a-zA-Z][0-9]{5}$/;
		if( (pId != "") && !pId.match(reg)){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	$("#pname_input").focusout(function(){
		var pname = $.trim($(this).val());
		var strLength=0;
		if(null!=pname){
			strLength= pname.length;
		}
		if(strLength>100){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	$(".operationDiv").click(function(){
		window.location.href = basepath + '/pieces/basicInfo/add' ;
	})

	$(".modal").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });

	CONTINENTDD = new ZtrDropDown('#address_continent',{
		getData: function(request){
			var data = [];
			data.push("全部");
			$.each(ADDRESS.country, function(index){
				data.push(index);
			});
			return data;
		},
	});
	COUNTRYDD = new ZtrDropDown('#address_country', {
		getData: function(request){
			var data = [];
			$.each(ADDRESS.country, function(index,item){
				if(request == index){
					data = item;
				}
			});
			return data;
		},
	});
	REGIONDD = new ZtrDropDown('#address_region', {
		getData: function(request){
			var data = [];
			$.each(ADDRESS.region, function(index,item){
				if(request == index){
					data = item;
				}
			});
			return data;
		},
	});
	CONTINENTDD.casCading(COUNTRYDD);
	COUNTRYDD.casCading(REGIONDD);
	CONTINENTDD.init();
	CONTINENTDD.unSelect();

});

function searchByParams(){
	$(".pageNo").val("1");
	submitFunc();
}

//提交查询请求
function submitFunc() {

	if(!validateSearchFileds()){
		return false;
	}

	var criteria = {};
	criteria.pid=$("#pid_input").val();
	criteria.pname=$("#pname_input").val();
	criteria.type = $('#pieceType_dropdown').find('li.active').attr("value");
	criteria.status=$(".radio.active").attr("value");
	criteria.toContinent =	$("#address_continent .dropdownBtn").children(".activeFonts").text();
	criteria.toCountry = $("#address_country .dropdownBtn").children(".activeFonts").text();
	criteria.toCity	=	 $("#address_region .dropdownBtn").children(".activeFonts").text();

	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize = $("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../maintain/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];

			$(".pieceProductTab tbody").html(tableData);

			$(".pieceName").each(function(index){
				var pieceName = $(this).html().replace(new RegExp(" ",'gm'),"&nbsp;");
				$(this).html(pieceName);
			})

			$("#searchField").html(paginationData);

		},
	});
}

function validateSearchFileds(){
	var result=false;
	if(validateSearchId() && validatePname()){
		result=true;
	}
	return result;
}

function validatePname(){
	var	pname = $("#pname_input").val();
	var strLength = 0;
	if(null!=pname){
		strLength= pname.length;
	}
	return strLength<=100;
}

function validateSearchId(){
	var result=true;
	var searchId=$("#pid_input").val();
	if(null!=searchId && ""!=searchId){
		var reg=/^[a-zA-Z][0-9]{5}$/;
		result=reg.test(searchId);
	}

	return result;
}

function delProduct(){
	var objectId =$("#delObjectId").val();
	var nature =$("#delNature").val();
	$.ajax({
		type: "get",
		url: "../maintain/del/" + nature + "/" +objectId,
		dataType : "json",
		success: function(resp){
			$("#ac-del").modal("hide");
			if( resp.res_code == "SO_PROD_PRODUCT_1101" ){
				submitFunc();
			}else if(resp.res_code == "FO_PROD_PRODUCT_1101" ){
			   alert(resp.res_msg);
			}
		}
	});
}

function closeProduct(){
	var objectId =$("#closeObjectId").val();
	var nature =$("#closeNature").val();
   	$.ajax({
   		type: "POST",
		url: "../maintain/close/" + nature + "/" +objectId,
		dataType : "json",
		success: function(resp){
			$("#ac-close").modal("hide");
			if( resp.res_code == "SO_PROD_PRODUCT_1105" ){
				submitFunc();
			}else if(resp.res_code == "FO_PROD_PRODUCT_1105" ){
			   alert(resp.res_msg);
			}
		}
	});
}

function onlineProduct(){
	var objectId =$("#onlineObjectId").val();
	var nature =$("#onlineNature").val();
	$.ajax({
		type: "POST",
		url: "../maintain/online/" + nature + "/" +objectId,
		dataType : "json",
		success: function(resp){
			$("#ac-online").modal("hide");
			if( resp.res_code == "SO_PROD_PRODUCT_1103" ){
				submitFunc();
			}else if(resp.res_code == "FO_PROD_PRODUCT_1103" ){
				alert(resp.res_msg);
			}
		}
	});
}