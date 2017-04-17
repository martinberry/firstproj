<#if status ??>
<#if status == 'AVAILABLE'>
<div class="voucherList unused">
 <#elseif status == 'USED'>
     <div class="voucherList used">
 <#elseif status == 'SHARED'>
  		<div class="voucherList donate">
 <#else>
      <div class="voucherList expired">
 </#if>
 </#if>
   <#if couponItems??>
        <#list couponItems as couponItem>

          <div   class="<#if couponItem.amount??>
           <#if couponItem.amount gt 0 && couponItem.amount lte 1000> voucherItem voucher-bg-0    <#elseif couponItem.amount gt 1000 && couponItem.amount lte 2000>voucherItem voucher-bg-1
           <#elseif couponItem.amount gt 2000 && couponItem.amount lte 5000>voucherItem voucher-bg-2<#else>voucherItem voucher-bg-3 </#if>
           	  <#if couponItem.status ?? >
           	 <#if couponItem.status == 'AUDITING'> primary refunding "<#elseif couponItem.status == 'REFUNDED'> primary refund "<#elseif couponItem.status='EXPIRED'></#if></#if>
           	 </#if> "
           	 <#if couponItem_index%2==1> style="margin-right: 0;" </#if>>
                <div class="flag"></div>
                <div class="rightBorder"></div>
                <div class="top">
                    <span class="title">${couponItem.name!}</span>
                    <#if couponItem.price ?? >
                        <#if couponItem.price gt 0 >
                	      <span class="star-icon"></span>
                      </#if>
                  </#if>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="middle">
                    <div class="privilege">${couponItem.orderLeast!}</div>
                    <div class="validity">${couponItem.validTimeFrom!} - ${couponItem.validTimeTo!}</div>
                    <div class="applicability"><#noescape>${couponItem.productRange!}</#noescape></div>
                    <span class="price"><span class="price-number">${couponItem.amount!}</span><em>元</em></span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="bottom">
                 <input type = "hidden" name ="shareAmount" value = "${couponItem.amount!}">
                   <input type ="hidden" name="couponItemId" value="${couponItem.couponItemId!}">
                    <#if couponItem.status=='AVAILABLE'>
                    	<#if couponItem.price??>
                    		<#if couponItem.voucherType ??>
                    			<#if couponItem.voucherType =='NORMAL'>
                    				<#if couponItem.price gt 0 >
                    					<a class="refund-link"  name="refundCoupon">退款</a>
                    				</#if>
                    			</#if>
                    		</#if>
                    	</#if>
                    </#if>
                    <a  class="share-link">分享代金券</a>
                </div>
            </div>

        </#list>
 </#if>

   <-split->
   <#include "/common/front/pagination.ftl"/>

 <script type="text/javascript">
        $(function(){
         $(".middle").find(".price-number").each(function(){
           var amount = $(this).html();
           if(null!=amount){
            	$(this).html(new Number(amount/100));
           }
         })

         $(".middle").find(".privilege").each(function(){
           var amount = $(this).html();
           if(null!=amount){
            	$(this).html("满"+new Number(amount/100).toFixed(2)+"元减");
           }
         })


            $(".voucherList.unused").delegate(".bottom a.share-link","click",function(){
				initShareVoucher();

                var $voucherItem = $(this).parents(".voucherItem").clone();
                $voucherItem.find(".horizontal-white-split-line").last().remove();
                $voucherItem.find(".bottom").remove();
                $voucherItem.addClass("small");

                $(".appendContainer").html($voucherItem);
                var share_couponItemId = $(this).siblings("[name='couponItemId']").val();
                if(null!=share_couponItemId){
                 	$("#share_couponItemId").val(share_couponItemId);
                }

               var shareAmount = $(this).parent().find("[name='shareAmount']").val();
		     if(null!=shareAmount){
                 	$("#shareAmount").val(shareAmount);
                }

			$("#sharer_head_img").hide();
	                $("#share-voucher").modal("show");
	            });
	        });

	        function initShareVoucher(){
	        	$("#sharer_phone").val('');
				$("#phoneVerify").hide();
				$("#sharer_head_img").hide();
				$("#destMembeId").val('');
				$("#share_couponItemId").val('');
				$("#shareAmount").val('');
	        }
    </script>