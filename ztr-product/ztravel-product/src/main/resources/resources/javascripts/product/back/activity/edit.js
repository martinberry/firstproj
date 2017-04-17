/**
 *
 */
$(function(){
	activity.initBtn();
	activity.initWin();
	activity.showCoupons();
	activity.disableInput();
	activity.initEditCoupon();
	activity.initUserSelect();
	activity.initValidate();
	initCouponDatePickerStartValue();
});


function initCouponDatePickerStartValue(){
	var startDateTime = new Date($("#startTime").val()) > new Date() ? $("#startTime").val() : new Date();
	var endDateTime = $("#endTime").val();
	 //代金券起始时间
	$('#validTimeFrom').datepicker({
		format: "yyyy-mm-dd",
		language: "zh-CN",
		autoclose: true,
		todayHighlight: true,
		weekStart: 0,
		startDate: startDateTime,
	    endDate: endDateTime
	}).on("show", function(){
		$("div.datepicker table thead .prev").html("");
		$("div.datepicker table thead .next").html("");
	}).on("changeDate", function(){
		var startDate = $(this).datepicker("getDate");
		$(this).siblings('input.datepicker.endDate').datepicker("setStartDate", startDate);
	});
	$('#validTimeTo').datepicker('setStartDate',new Date());


	// 手动清空输入框时，清除对应的时间
	$('#validTimeFrom').focus(function(){
		var $endDate = $(this).siblings('input.datepicker.endDate');
		if ($endDate.val() == "") {
			$endDate.datepicker("clearDates");
		}
	});
	$("#startTime").datepicker('setStartDate', new Date());
}