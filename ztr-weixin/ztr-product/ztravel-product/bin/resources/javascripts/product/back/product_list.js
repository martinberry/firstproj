var allChinese="全部";
var idErrorHint="ID格式输入错误";
var titleErrorHint ="产品标题输入错误";
var noLimit ="不限";


$(function(){
	//目的地第一级下拉列表(大洲)
	$("#continent").on("click", ".active", function(){
		$("#country .dropdown-menu").remove();
		$("#city .dropdown-menu").remove();
		$("#city .dropdownBtn").children(".activeFonts").text("不限");

		var continentName = $("#continent .active").children("a").text();
		$.ajax({
		    type: "POST",
		    url: "../productMaintain/loadCountry",
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
		    url: "../productMaintain/loadCity",
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
})

$(function(){
	$(".operationDiv").click(function(){
		window.location.href = basepath + '/product/basicInfo/add' ;
	})

	$(".modal").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });
})



function searchByParams(){
	if(!validateSearchFileds()){
		return ;
	}

	var criteria = {};
	criteria.pid=$("#searchId").val();
	criteria.pName=$("#title").val()+'';
	criteria.from=$("#from.activeFonts").html();
	criteria.theme=$("#subject.activeFonts").html();
	criteria.status=$(".radio.active").find(".labelFonts").html();

	criteria.toContinent =	$("#continent .dropdownBtn").children(".activeFonts").text();
	criteria.toCountry = $("#country .dropdownBtn").children(".activeFonts").text();
	criteria.to	=	 $("#city .dropdownBtn").children(".activeFonts").text();

	if(allChinese==criteria.from){
		criteria.from="";
	}
	if(allChinese==criteria.theme){
		criteria.theme="";
	}
	if(allChinese==criteria.status){
		criteria.status="";
	}

	if(noLimit==criteria.toContinent){
		criteria.toContinent="";
	}
	if(noLimit==criteria.toCountry){
		criteria.toCountry="";
	}
	if(noLimit==criteria.to){
		criteria.to="";
	}


	criteria.pageNo = 0;
//	criteria.pageSize = 10;
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../productMaintain/search",
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

			$("#searchField").html(paginationData);

//				$("#searchField").html(result);
		},
	})
}

$(function(){
//    $(".radioList .radio").click(function(){
//        $(this).addClass("active");
//        $(this).siblings().removeClass("active");
//    });
//
//    $(".allCheckbox").click(function(){
//        $(this).toggleClass("active");
//        if ($(this).hasClass("active")) {
//            $(this).parents("table").find(".checkbox").addClass("active");
//        } else {
//            $(this).parents("table").find(".checkbox").removeClass("active");
//        }
//    });



    submitFunc();

  //搜索框 输入框格式校验
	//真实姓名格式校验
	$("#searchId").focusout(function(){
		var pId = trim(this);
		var reg=/^[a-zA-Z][0-9]{5}$/;
		if( (pId != "") && !pId.match(reg)){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	$("#title").focusout(function(){
		var pName = trim(this);
		var strLength=0;
		if(null!=title){
			strLength= pName.length;
		}
		if(strLength>60){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}

//		var reg=/[\w\u4e00-\u9fa5]{0,60}/;
//		if( (pName != "") && !pName.match(reg)){
//			$(this).siblings(".verifyStyle").show();
//		}else{
//			$(this).siblings(".verifyStyle").hide();
//		}
	});


});



//提交查询请求
function submitFunc() {

	if(!validateSearchFileds()){
		return false;
	}

	var criteria = {};
	var destination={};
	criteria.pid=$("#searchId").val();
	criteria.pName=$("#title").val()+'';
	criteria.from=$("#from.activeFonts").html();
	criteria.theme=$("#subject.activeFonts").html();
	criteria.status=$(".radio.active").find(".labelFonts").html();
	criteria.toContinent =	$("#continent .dropdownBtn").children(".activeFonts").text();
	criteria.toCountry = $("#country .dropdownBtn").children(".activeFonts").text();
	criteria.to	=	 $("#city .dropdownBtn").children(".activeFonts").text();

	if(allChinese==criteria.from){
		criteria.from="";
	}
	if(allChinese==criteria.theme){
		criteria.theme="";
	}
	if(allChinese==criteria.status){
		criteria.status="";
	}

	if(noLimit==criteria.toContinent){
		criteria.toContinent="";
	}
	if(noLimit==criteria.toCountry){
		criteria.toCountry="";
	}
	if(noLimit==criteria.to){
		criteria.to="";
	}

	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize = $("input[name=pageSize]").val();



	$.ajax({
		type : "POST",
		url : "../productMaintain/search",
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

			$("#searchField").html(paginationData);

//				$("#searchField").html(result);
		},
	})
}

function validateSearchFileds(){
	var result=false;
	if(validateSearchId() && validateTitle()){
		result=true;
	}
	return result;
}

function validateTitle(){
	var	title = $("#title").val();
//	var reg1=/[\w\u4e00-\u9fa5]{0,60}/;
//	var reg=/[a-zA-Z0-9]{0,60}/
	var strLength = 0;
	if(null!=title){
		strLength= title.length;
	}
	return strLength<60;
}

function validateSearchId(){
	var result=true;
	var searchId=$("#searchId").val();
	if(null!=searchId && ""!=searchId){
		var reg=/^[a-zA-Z][0-9]{5}$/;
		result=reg.test(searchId);
	}

	return result;
}

 function delProduct(){
	var objectId =$("#delObjectId").val();
	$.ajax({
		type: "get",
		url: "../productMaintain/del",
		data: "id="+objectId ,
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
	   	$.ajax({
				type: "POST",
				url: "../productMaintain/close",
				data: "id="+objectId ,
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
       	$.ajax({
				type: "POST",
				url: "../productMaintain/online",
				data: "id="+objectId ,
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

