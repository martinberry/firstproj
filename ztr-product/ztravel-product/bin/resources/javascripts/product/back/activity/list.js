/**
 *
 */
$(function(){
	activity.initBtn();
	submitFunc();
	initDatePickerStartValue();
});

function submitFunc(){
	var criteria = $("form").serializeObject();
	criteria.status = $("#status .active").data("val");
	criteria.type=$("#type .active").data("val");
	criteria.grantType=$("#grantType .active").data("val");
	$.ajax({
			type : "POST",
			url : "../activity/search",
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success : function(result) {
				var resultArray = result.split("<-split->");
				var data = resultArray[0];
				var pagination = resultArray[1];
				$(".commonTab tbody").html(data);
				$("#pagination").html(pagination);
			}
		});
}

function searchByParams(){
	var criteria = $("form").serializeObject();
	criteria.status = $("#status .active").data("val");
	criteria.type=$("#type .active").data("val");
	criteria.grantType=$("#grantType .active").data("val");
	criteria.pageNo = 1;
	$.ajax({
		type : "POST",
		url : "../activity/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var resultArray = result.split("<-split->");
			var data = resultArray[0];
			var pagination = resultArray[1];
			$(".commonTab tbody").html(data);
			$("#pagination").html(pagination);
		}
	});
}

function initDatePickerStartValue(){
	$("#startTime").datepicker('setStartDate', '2015-01-01');
	$("#endTime").datepicker('setStartDate', '2015-01-01');
}


$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
