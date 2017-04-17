/**
 *
 */
$(function(){

	submitFunc();

	$('.lightBlueBtn').click(function(){
		$("input[name='pageNo']").val(1);
		submitFunc();
    });

});

function submitFunc(){
	var data = {};
	data.pName = $('#pName').val().replace(/\s+/g, "");

//	if(data.pName.length >30)){
//		$("#error_name").show();
//		return;
//	}

	data.pageNo = $("input[name=pageNo]").val();
	data.pageSize = $("input[name=pageSize]").val();

		$.ajax({
			type: "POST",
			url: basepath + '/member/opera/wish/search',
			data: JSON.stringify(data),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success: function (result) {
				var resultArray = result.split("<-split->");
				var tbody = resultArray[0];
				var pagination = resultArray[1];
				$(".commonTab tbody").html(tbody);
				$("#pagination").html(pagination);
    			}
	});
}

