
var hotelNameCnReg = /^([a-zA-Z0-9|\u4e00-\u9fa5| |\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’]){4,60}$/;
var hotelNameEnReg = /^([a-zA-Z0-9| |\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’]){4,60}$/;
var phoneReg = /^[\+|0-9][0-9|\+|-]{0,18}[0-9]$/;
var lonAndlatReg = /^[0-9]{1,3}\.[0-9]{1,3}$/;
var addressReg = /^([a-zA-Z0-9|\u4e00-\u9fa5| |\.|（|）|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’]){4,60}$/;
var advantageReg = /^([a-zA-Z0-9|\u4e00-\u9fa5| |\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’]){2,100}$/;
var describeReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’| |\n]){10,500}$/;
var facilitiesReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\n| |\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’|！|·|@|#|￥|%|……|&|\*|\+|=|\-|\$|{|}|\[|\]|<|>|《|》|\?|~]){2,500}$/;
var notesReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\n| |\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’|！|·|@|#|￥|%|……|&|\*|\+|=|\-|\$|{|}|\[|\]|<|>|《|》|\?|~]){2,500}$/;

function preserveValiation() {

	var hotelNameCn= $.trim($('#hotelNameCn').val());
	if ( !testNameCn(hotelNameCn)) {
//		error("仅可输入4-60位字符数以内的中文、字母、数字及常用字符");
		error("中文名称仅可输入4-60位字符");
		$("#hotelNameCn").focus();
		return false;
	}
	var phone= $.trim($('#phone').val());
	if ( phone !='' && !testPhone(phone)) {
		error("酒店电话请输入20位以内字符（数字和“-”）");
		$("#phone").focus();
		return false;
	}
	var hotelNameEn= $.trim($('#hotelNameEn').val());
	if ( hotelNameEn !='' && !testNameEn(hotelNameEn)) {
//		error("仅可输入4-60位字符数以内的字母、数字及常用字符");
		error("英文名称仅可输入4-60位字符");
		$("#hotelNameEn").focus();
		return false;
	}
	var lon= $.trim($('#lon').val());
	if ( lon !='' && !testLonAndLat(lon)) {
		error("酒店坐标经度格式输入有误");
		$("#lon").focus();
		return false;
	}
	var lat= $.trim($('#lat').val());
	if ( lat !='' && !testLonAndLat(lat)) {
		error("酒店坐标纬度格式输入有误");
		$("#lat").focus();
		return false;
	}
	var address= $.trim($('#address').val());
	if ( address !='' && !testAddress(address)) {
//		error("仅可输入4-60位字符数以内的中文、字母、数字及常用字符");
		error("酒店地址仅可输入4-60位字符");
		$("#address").focus();
		return false;
	}
	var advantage= $.trim($('#advantage').val());
	if ( advantage !='' && !testAdvantage(advantage)) {
//		error("仅可输入2-100位字符数以内的中文、字母、数字及常用字符");
		error("卖点仅可输入2-100位字符");
		$("#advantage").focus();
		return false;
	}
	var describe= $.trim($('#describe').val());
	if ( describe !='' && !testDescribe(describe)) {
//		error("仅可输入10-500位字符数以内的中文、字母、数字及常用字符");
		error("酒店描述仅可输入10-500位字符");
		$("#describe").focus();
		return false;
	}
	var compFacilities= $.trim($('#compFacilities').val());
	if ( compFacilities !='' && !testFacilities(compFacilities)) {
//		error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		error("综合设施请输入2-500位字符");
		facilitiesSelect($("#compFacilities"), 0);
		return false;
	}
	var cateringFacilities= $.trim($('#cateringFacilities').val());
	if ( cateringFacilities !='' && !testFacilities(cateringFacilities)) {
//		error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		error("餐饮设施请输入2-500位字符");
		facilitiesSelect($("#cateringFacilities"), 1);
		return false;
	}
	var networkFacilities= $.trim($('#networkFacilities').val());
	if ( networkFacilities !='' && !testFacilities(networkFacilities)) {
//		error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		error("网络设施请输入2-500位字符");
		facilitiesSelect($("#networkFacilities"), 2);
		return false;
	}
	var activityFacilities= $.trim($('#activityFacilities').val());
	if ( activityFacilities !='' && !testFacilities(activityFacilities)) {
//		error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		error("活动设施请输入2-500位字符");
		facilitiesSelect($("#activityFacilities"), 3);
		return false;
	}
	var serviceFacilities= $.trim($('#serviceFacilities').val());
	if ( serviceFacilities !='' && !testFacilities(serviceFacilities)) {
//		error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		error("服务项目请输入2-500位字符");
		facilitiesSelect($("#serviceFacilities"), 4);
		return false;
	}
	var notes= $.trim($('#notes').val());
	if ( notes !='' && !testNotes(notes)) {
//		error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		error("入住须知请输入2-500位字符");
		$("#notes").focus();
		return false;
	}
	$("#hotel-hangUp").modal("hide");
	return true;
}

function completeValiation() {
	if($.trim($('#hotelNameCn').val())==''||$.trim($('#address').val())==''||$.trim($('#advantage').val())==''||$.trim($('#describe').val())==''
		||$.trim($('#continent').html())==''||$.trim($('#nation').html())==''||$.trim($('#city').html())==''){
		error("中文名称、目的地、酒店地址、酒店类型、酒店星级、卖点、酒店描述完成后才能发布");
		return false;
	}

	if($("#file").parent().parent().parent().prevAll("li").size() <5){
		error("至少5张高清（XXX*XXX）JPG/PNG图");
		return false;
	}

	$("#hotel-hangUp").modal("hide");
	return true;
}


//function testNameCn(nameCn){
//	if(hotelNameCnReg.test(nameCn)){
//		return true;
//	}else{
//		return false;
//	}
//}
function testNameCn(nameCn){
	if(nameCn.length >= 4 && nameCn.length <= 60){
		return true;
	}else{
		return false;
	}
}

	function testPhone(phone){
		if(phoneReg.test(phone)){
			return true;
		}else{
			return false;
		}
	}

//	function testNameEn(nameEn){
//		if(hotelNameEnReg.test(nameEn)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	function testNameEn(nameEn){
		if(nameEn.length >= 4 && nameEn.length <= 60){
			return true;
		}else{
			return false;
		}
	}

	function testLonAndLat(l){
		if(lonAndlatReg.test(l)){
			return true;
		}else{
			return false;
		}
	}

//	function testAddress(address){
//		if(addressReg.test(address)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	function testAddress(address){
		if(address.length >= 4 && address.length <= 60){
			return true;
		}else{
			return false;
		}
	}

//	function testAdvantage(advantage){
//		if(advantageReg.test(advantage)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	function testAdvantage(advantage){
		if(advantage.length >= 2 && advantage.length <= 100){
			return true;
		}else{
			return false;
		}
	}

//	function testDescribe(describe){
//		if(describeReg.test(describe)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	function testDescribe(describe){
		if(describe.length >= 10 && describe.length <= 500){
			return true;
		}else{
			return false;
		}
	}

//	function testFacilities(facilities){
//		if(facilitiesReg.test(facilities)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	function testFacilities(facilities){
		if(facilities.length >= 2 && facilities.length <= 500){
			return true;
		}else{
			return false;
		}
	}

//	function testNotes(notes){
//		if(notesReg.test(notes)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	function testNotes(notes){
		if(notes.length >= 2 && notes.length <= 500){
			return true;
		}else{
			return false;
		}
	}


function error(message){
    $(".error_edit_hotel").html(message);
    $(".error_edit_hotel").show();
}

function facilitiesSelect(section, index){
	$(".facilityContent .facilityTextarea").hide();
	section.show();
    $(".facilityList li").removeClass("active");
    $(".facilityList li:eq(" + index + ")").addClass("active");
    section.focus();
}