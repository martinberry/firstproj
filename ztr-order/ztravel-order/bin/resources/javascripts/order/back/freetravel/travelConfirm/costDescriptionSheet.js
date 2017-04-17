var lock = false;

var isCostDescriptionEdit = false;
var KEDITOR = [];

$(function(){

	initKindEditor();

	//费用编辑
    $(".costContainEditBtn").click(function(){
    	isCostDescriptionEdit = true;
    	getCostDescription();

        $that = $(this).parents(".commonSmallTitleModel").siblings(".commonStyle");
        $(this).hide();
        $that.find(".costContainContent").hide();
        $that.find(".costContainEdit").show();
        $that.find(".costContainSave").show();
    });

    $(".costContainSave .commonBtn").click(function(){
    	if ($("#feeIncludeInputer").val() == "" || $("#feeNotIncludeInputer").val() == "" || $("#giftItemsInputer").val() == "" || $("#refundPolicyInputer").val() == "") {
    		if ($("#feeIncludeInputer").val() == "") {
    			$("#feeIncludeInputer").next(".dropdown-error").show();
    		}
    		if ($("#feeNotIncludeInputer").val() == "") {
    			$("#feeNotIncludeInputer").next(".dropdown-error").show();
    		}
    		if ($("#giftItemsInputer").val() == "") {
    			$("#giftItemsInputer").next(".dropdown-error").show();
    		}
    		if ($("#refundPolicyInputer").val() == "") {
    			$("#refundPolicyInputer").next(".dropdown-error").show();
    		}
		}else{
			$(".dropdown-error").hide();
			saveCostDescription(this);
		}
    	isCostDescriptionEdit = false;
    });

    $(".costContainSave .cancelBtn").click(function(){
    	isCostDescriptionEdit = false;
        $(this).parents(".costContainSave").hide();
        $cancel = $(this).parents(".costContainSave").siblings(".costTab");
        $cancel.find(".costContainContent").show();
        $cancel.find(".costContainEdit").hide();
        $(this).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".costContainEditBtn").show();
    });

});

function initKindEditor(){
	KindEditor.options.items = ['forecolor', 'bold', 'insertorderedlist'];
    $('textarea.editorText').each(function(){
    	var ed = KindEditor.create(this, {
    		width : "95%",
            afterCreate : function() {
                this.loadPlugin('autoheight');
                $(".facilityContent .ke-container:gt(0)").hide();
            }
        });
    	KEDITOR.push(ed);
    });
}

function saveCostDescription(scope){
	if(lock){
		return ;
	}else{
		lock = true;
	}

	var additionalInfos = {};
	additionalInfos.FEE_INCLUDE = KEDITOR[0].html();
	additionalInfos.FEE_NOT_INCLUDE = KEDITOR[1].html();
	additionalInfos.GIFT_ITEMS = KEDITOR[2].html();
	additionalInfos.REFUND_POLICY = KEDITOR[3].html();

	 var orderId = $('#orderId').val();
	 var data = {
			"orderId":orderId,
			"additionalInfos": additionalInfos
	}

	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/updateCostDescription',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : JSON.stringify(data),
		   dataType: "json",
		   success: function(resp){
			   lock = false;
			   if (resp.res_code == 'SF_ORDE_1002') {
				   $(".costTab").show();
				   refreshCostDescription(scope);
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

function refreshCostDescription(scope){
	var orderId = $('#orderId').val();
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/costDescription/refresh/' + orderId,
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
		   dataType: "html",
		   success: function(json){
			   $(".costTab tbody").html(json);

		        $(scope).parents(".costContainSave").hide();
		        $cancel = $(scope).parents(".costContainSave").siblings(".costTab");
		        $cancel.find(".costContainContent").show();
		        $cancel.find(".costContainEdit").hide();
		        $(scope).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".costContainEditBtn").show();

		    	initKindEditor();
		   }
	 });
}

function getCostDescription(){

	KEDITOR[0].html($('input[name="feeInclude"]').val());
	KEDITOR[1].html($('input[name="feeNotInclude"]').val());
	KEDITOR[2].html($('input[name="giftItems"]').val());
	KEDITOR[3].html($('input[name="refundPolicy"]').val());
}