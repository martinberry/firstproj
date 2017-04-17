function submitQuery(action,pageNo){
	
}

$(function(){
    kendo.init('.block1');
    $("#buttonQuery").click(function(){
    	var gridTable = $("#tableQueryResult").data("kendoGrid");
    	gridTable.dataSource.read();
    });
    var gridTable = new GridTable('#tableQueryResult',{
         scrollable:true,
         resizable:true,
         dataSource: {
        	 transport: {
				read: {
					type: "POST",
					url: basepath + "/paygate/trade/queryTradeBatch",
					dataType:"json",
					data: {
						"gateType": function(){return $("#gateType").val();}
					}
				}
        	 },
				schema : {
					// the data which the data source will be bound to is in the
					// "results" field
					data : "data",
					total : "totalItemCount"
				},
				pageSize : 10,
				serverPaging : true
         },
         pageable:{
         	pageSize:10
         },
        rowTemplate: kendo.template($("#block2-grid-row-template").html())
    }).init();
});