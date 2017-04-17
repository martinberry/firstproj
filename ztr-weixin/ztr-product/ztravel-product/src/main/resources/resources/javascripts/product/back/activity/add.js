/**
 *
 */
$(function(){
	activity.initBtn();
	activity.initWin();
	activity.initValidate();
	activity.initUserSelect();
	initDatePickerStartValue();
});

function initDatePickerStartValue(){
	$("#startTime").datepicker('setStartDate', new Date());
	$("#endTime").datepicker('setStartDate', new Date());
}

