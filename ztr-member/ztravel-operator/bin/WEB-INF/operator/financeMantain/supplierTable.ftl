  <#if searchList??>
    <#list searchList as item>
    <tr>
    <input type ="hidden" name ="supplierId" value="${item.supplierId!}">
        <td>${item.supplierName!}</td>
       <td>${item.bussinessContacts!}</td>
        <td>${item.phone!}</td>
        <td> ${item.fax!}</td>
        <td>${item.email!}</td>
        <td><a href="javascript:void(0);" class="greenFontsLink">编辑</a></td>
    </tr>
    </#list>
  </#if>
       <-split->
    </tbody>
</table>
<#include "/common/opera/pagination.ftl"/>

<script>
	$(function(){
	$(".dropdown").delegate(".dropdown-menu","click",function(){
		$(this).parents(".dropdown").find(".searchFormField").val($(this).find(".active a").html());
	})


	$(".commonTab  tbody").delegate(".greenFontsLink","click",function(){
		var supplierId = $(this).parents("tr").find("[name='supplierId']").val();
		$("#supplierIdEdit").val(supplierId);
		clearEditForm();
		$.ajax({
		type : "GET",
		url : "../supplier/edit",
		data :{
			supplierId:supplierId
		},
		dataType : "json",
		success : function(data) {
			if(data.result=='successed'){
				if(data.supplier!= null){
					var supplier = data.supplier;
					$("#supplierIdEdit").val(supplier.supplierId);
					$("#supplierNameEdit").val(supplier.supplierName);
					$("#supplierNameOrigin").val(supplier.supplierName);
					$("#accountBankEdit").val(supplier.accountBank);
					$("#accountEdit").val(supplier.account);
					$("#accountNameEdit").val(supplier.accountName);
					$("#bussinessContactsEdit").val(supplier.bussinessContacts);
					$("#innerContactsEdit").val(supplier.innerContacts);
					$("#phoneEdit").val(supplier.phone);
					$("#emailEdit").val(supplier.email);
					$("#faxEdit").val(supplier.fax);
					if(null != supplier.settlementPeriod){
						if(supplier.settlementPeriod == 'SINGLE'){
							$("#secMenu li").eq(1).find("a").click();
						}
							if(supplier.settlementPeriod == 'WEEK'){
							$("#secMenu li").eq(2).find("a").click();
						}
							if(supplier.settlementPeriod == 'HALFMONTH'){
							$("#secMenu li").eq(3).find("a").click();
						}
							if(supplier.settlementPeriod == 'MONTH'){
							$("#secMenu li").eq(4).find("a").click();
						}
					}

				}
				$("#edtSupply").modal("show");

			}
		},
	})
	})
})

   function clearEditForm(){
		$("#supplierNameEdit").val("");
		$("#bussinessContactsEdit").val("");
		$("#accountBankEdit").val("");
		$("#phoneEdit").val("");
		$("#accountNameEdit").val("");
		$("#faxEdit").val("");
		$("#accountEdit").val("");
		$("#emailEdit").val("");
		$("#secMenu li").first().find("a").click();
		$("#innerContactsEdit").val("");
		toggleErrorHint(disappear,'.errorHint') ;
	}

</script>