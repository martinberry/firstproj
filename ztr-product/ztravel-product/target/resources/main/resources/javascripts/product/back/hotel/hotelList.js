var hotelNameCnReg = /^[a-zA-Z\u4E00-\u9FA5\d\,\.\;\:\\\/\，\。\、\：\；\”\“\’\（\）\【\】\—— ]{0,60}$/;

$(function(){
	submitFunc();
	//酒店中文名称格式校验
	$("#hotelNameInput").blur(function(){
		//var hotelNameCn = trim(this);
		var hotelNameCn = $.trim($(this).val());
		$(this).val(hotelNameCn);
		if( !isHotelNameCnValid(hotelNameCn) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	//目的地第一级下拉列表(大洲)
	$("#continent").on("click", ".active", function(){
		$("#country .dropdown-menu").remove();
		$("#city .dropdown-menu").remove();
		$("#city .dropdownBtn").children(".activeFonts").text("不限");

		var continentName = $("#continent .active").children("a").text();
		$.ajax({
		    type: "POST",
		    url: "../hotelResource/loadCountry",
		    data: continentName,
		    headers : {
			    'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    success: function(result){
		    	$("#country").html(result);
		    }
	    });
	});

	//目的地第二级下拉列表(国家)
	$("#country").on("click", "li a", function(){
		var thisHtml = $(this).html();
		var $thisParents = $(this).parents(".dropdown-menu li");
	    $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
	    $thisParents.addClass("active");
	    $thisParents.siblings().removeClass("active");

	    $("#country").siblings(".verifyStyle").hide();    //隐藏“请选择国家”错误提示框
	    $("#city .dropdown-menu").remove();  //重置城市下拉列表

	    var countryName = $("#country .active").children("a").text();
		$.ajax({
		    type: "POST",
		    url: "../hotelResource/loadCity",
		    data: countryName,
		    headers : {
			    'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    success: function(result){
		    	$("#city").html(result);
		    }
	    });
	});

	//目的地第三级下拉列表(城市)
	$("#city").on("click", "li a", function(){
		var thisHtml = $(this).html();
		var $thisParents = $(this).parents(".dropdown-menu li");
	    $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
	    $thisParents.addClass("active");
	    $thisParents.siblings().removeClass("active");
	});

	//搜索按钮点击
	$("#searchBtn").click(function(){
		if( $("#country .activeFonts").text() != "不限" || $("#continent .activeFonts").text() == "不限" ){
			$("input[name='pageNo']").val(1);
			submitFunc();
		}else{
			$("#country").siblings(".verifyStyle").show();
		}
	});

	//确认删除
	$("body").delegate("#deleteHotel", "click", function(){
		var id = $("#deleteHotel").val();
		$.ajax({
			type: "POST",
			url: "../hotelResource/delete",
			data: id,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "SO_PROD_1101" ){
					submitFunc();
				}else if( resp.res_code == "FO_PROD_1101" || resp.res_code == "FO_PROD_1103" ){
					$("#deleteErrorDlg").find(".popupContainer-fonts").text(resp.res_msg);
					$("#deleteErrorDlg").modal();
				}
			}
		});
	});

});

//提交查询请求
function submitFunc(){
	var criteria = {};
	criteria.hotelNameCn = $("#hotelNameInput").val();
	criteria.destCountry = $("#country .dropdownBtn").children(".activeFonts").text();
	criteria.destCityOrAttraction = $("#city .dropdownBtn").children(".activeFonts").text();
	criteria.status = $("#statusRadio .active").attr("name");

	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize = $("input[name=pageSize]").val();

	if( isHotelNameCnValid(criteria.hotelNameCn) ){
		$.ajax({
			type : "POST",
			url : "../hotelResource/searchHotel",
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
				$(".productTab tbody").html(tableData);
				$("#pagination").html(paginationData);
			},
		});
	}
}

function isHotelNameCnValid(hotelNameCn){
	if( hotelNameCn != "" && !hotelNameCnReg.test(hotelNameCn) )
		return false;
	else
		return true;
}

function deleteHotel(id){
	$("#dialog-deleteHotel").modal();
	$("#deleteHotel").val(id);
}

//function trim(selector){
//	return $(selector).val($(selector).val().replace(/\ +/g,"")).val();
//}
