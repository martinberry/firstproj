$(function(){
	$("#addTravelerForm").validate({
		rules :{
			travelerNameCn:"required",
			
		},
		messages : {
			travelerNameCn : '中文名不能为空'
		},
		errorPlacement : function(error, element) {
			element.addClass("verifyBox");
			element.next().show();
		},
		success : function(label){
			if(label.attr("for") == "travelerNameCn") {
				var currentObj = $("#addTravelerForm").find("input[name='travelerNameCn']");
				currentObj.removeClass("verifyBox");
				currentObj.next().hide();
			}
		}
	});
	
	//$("#addTravelerForm").valid();
	
	$("#addTravelerForm").find("input[name='travelerNameCn']").blur(function(){
		$("#addTravelerForm").valid();
	});
})