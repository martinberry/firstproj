$(function(){
    kendo.init('.block1');
    $("#buttonQuery").click(function(){
    	var gridTable = $("#tableQueryResult").data("kendoGrid");
    	gridTable.dataSource.read();
    });
    function buildQueryCondition(){
    	return {
    		"gateType": $("#gateType").val(),
    		"transDate":$("#transDate").val(),
    		"billId": dateBillId || "",
    		"tradeType":$("#tradeType").val()
    	}
    }
    var gridTable = new GridTable('#tableQueryResult',{
         scrollable:true,
         resizable:true,
         dataSource: {
        	 transport: {
				read: {
					type: "POST",
					url: basepath + "/paygate/trade/queryTradeDateRecord",
					dataType:"json",
					data: buildQueryCondition
				}
        	 },
				schema : {
					// the data which the data source will be bound to is in the
					// "results" field
					data : "data",
					total : "totalItemCount"
				},
				serverPaging : true
         },
         pageable:{
          	pageSize:10
          },
        rowTemplate: kendo.template($("#block2-grid-row-template").html())
    }).init();
});