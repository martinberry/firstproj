<#if status == 'AVAILABLE'>
<div class="voucherList unused" >
	<input type="hidden" id="havailableCount" value="${availableCount!}">
	  		<input type="hidden" id="husedCount" value="${usedCount!}">
		  <input type="hidden" id="hexpiredCount" value="${expiredCount!}">
		    <input type="hidden" id="hdonateCount" value="${donateCount!}">
		    <input type="hidden" id="hstatus" value="${status!}">

   <#if couponItems??>
        <#list couponItems as couponItem>
			<#if couponItem.amount??> <#if couponItem.amount gt 0 && couponItem.amount lte 10>
          <div   class="voucherItem voucher-bg-0">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-green"></div>
                <#elseif couponItem.amount gt 10 && couponItem.amount lte 20>
                 <div   class="voucherItem voucher-bg-1">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-blue"></div>
                <#elseif couponItem.amount gt 20 && couponItem.amount lte 50>
				 <div   class="voucherItem voucher-bg-2">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-pink"></div>
                <#else>
                  <div   class="voucherItem voucher-bg-3">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-yellow"></div>
                </#if>
                 </#if>
                <div class="top">
                    <span class="title">${couponItem.name!}</span>
                       <#if couponItem.price ?? >
	                <#if couponItem.price  gt 0>
	                 	<span class="star-icon"></span>
	                </#if>
                </#if>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="middle">
                    <div class="privilege">满${couponItem.orderLeast!}元减</div>
                    <div class="validity">${couponItem.validTimeFrom!} - ${couponItem.validTimeTo!}</div>
                    <div class="applicability">适用范围：<#noescape>${couponItem.productRange!}</#noescape></div>
                    <span class="price"><span class="price-number">${couponItem.amount!}</span><em>元</em></span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="bottom">
                <#if couponItem.price ?? >
             	   <#if couponItem.voucherType ??>
             	   	<#if couponItem.voucherType =='NORMAL'>
	                	<#if couponItem.price  gt 0>
	                		<a class="refund_link" href="#alert-dialog-refund" data-rel="popup" data-transition="pop" data-position-to="window" name="refundCoupon">退款</a>
	                	</#if>
	                </#if>
	                </#if>
                </#if>
                <div class="share_link">
                 <input type = "hidden" name ="shareAmount" value = "${couponItem.amount!}">
                   <input type ="hidden" name="couponItemId" value="${couponItem.couponItemId!}">
                    <a href="" name="share-coupon-btn">分享代金券</a>
                </div>
                </div>
            </div>

        </#list>
 </#if>
 <#elseif status == 'USED'>
     <div class="voucherList used">
     	<input type="hidden" id="havailableCount" value="${availableCount!}">
	  		<input type="hidden" id="husedCount" value="${usedCount!}">
		  <input type="hidden" id="hexpiredCount" value="${expiredCount!}">
		      <input type="hidden" id="hdonateCount" value="${donateCount!}">
		    <input type="hidden" id="hstatus" value="${status!}">

   <#if couponItems??>
        <#list couponItems as couponItem>
			<#if couponItem.amount??> <#if couponItem.amount gt 0 && couponItem.amount lte 10>
          <div   class="voucherItem voucher-bg-0">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-green"></div>
                <#elseif couponItem.amount gt 10 && couponItem.amount lte 20>
                 <div   class="voucherItem voucher-bg-1">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-blue"></div>
                <#elseif couponItem.amount gt 20 && couponItem.amount lte 50>
				 <div   class="voucherItem voucher-bg-2">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-pink"></div>
                <#else>
                  <div   class="voucherItem voucher-bg-3">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-yellow"></div>
                </#if>
                 </#if>

                <div class="top">
                    <span class="title">${couponItem.name!}</span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="middle">
                    <div class="privilege">满${couponItem.orderLeast!}元减</div>
                    <div class="validity">${couponItem.validTimeFrom!} - ${couponItem.validTimeTo!}</div>
                    <div class="applicability">适用范围：<#noescape>${couponItem.productRange!}</#noescape></div>
                    <span class="price"><span class="price-number">${couponItem.amount!}</span><em>元</em></span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="bottom">
                <div class="share_link">
                 <input type = "hidden" name ="shareAmount" value = "${couponItem.amount!}">
                   <input type ="hidden" name="couponItemId" value="${couponItem.couponItemId!}">
                    <a href="" >分享代金券</a>
                </div>
                </div>
            </div>

        </#list>
 </#if>
 <#elseif status == 'SHARED'>
      <div class="voucherList donate">
      	<input type="hidden" id="havailableCount" value="${availableCount!}">
	  		<input type="hidden" id="husedCount" value="${usedCount!}">
		  <input type="hidden" id="hexpiredCount" value="${expiredCount!}">
		      <input type="hidden" id="hdonateCount" value="${donateCount!}">
		    <input type="hidden" id="hstatus" value="${status!}">
 <#if couponItems??>
   <#list couponItems as couponItem>
			<#if couponItem.amount??> <#if couponItem.amount gt 0 && couponItem.amount lte 10>
          <div   class="voucherItem voucher-bg-0">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-green"></div>
                <#elseif couponItem.amount gt 10 && couponItem.amount lte 20>
                 <div   class="voucherItem voucher-bg-1">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-blue"></div>
                <#elseif couponItem.amount gt 20 && couponItem.amount lte 50>
				 <div   class="voucherItem voucher-bg-2">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-pink"></div>
                <#else>
                  <div   class="voucherItem voucher-bg-3">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-yellow"></div>
                </#if>
                 </#if>

                <div class="top">
                    <span class="title">${couponItem.name!}</span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="middle">
                    <div class="privilege">满${couponItem.orderLeast!}元减</div>
                    <div class="validity">${couponItem.validTimeFrom!} - ${couponItem.validTimeTo!}</div>
                    <div class="applicability">适用范围：<#noescape>${couponItem.productRange!}</#noescape></div>
                    <span class="price"><span class="price-number">${couponItem.amount!}</span><em>元</em></span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="bottom">
                <div class="share_link">
                 <input type = "hidden" name ="shareAmount" value = "${couponItem.amount!}">
                   <input type ="hidden" name="couponItemId" value="${couponItem.couponItemId!}">
                    <a href="" >分享代金券</a>
                </div>
                </div>
            </div>
        </#list>
 </#if>
  <#else>
      <div class="voucherList">
      	<input type="hidden" id="havailableCount" value="${availableCount!}">
	  		<input type="hidden" id="husedCount" value="${usedCount!}">
		  <input type="hidden" id="hexpiredCount" value="${expiredCount!}">
		      <input type="hidden" id="hdonateCount" value="${donateCount!}">
		    <input type="hidden" id="hstatus" value="${status!}">

   <#if couponItems??>

        <#list couponItems as couponItem>
           <#if couponItem.status ??>
           	<#if couponItem.status =='REFUNDED'>
           		<div class="refund">
           			<#elseif couponItem.status == 'AUDITING'>
       	  		<div class ="refunding">
       	  	<#else>
       	  		<div class="expired">
       	       	</#if>
           </#if>
          <div   class="voucherItem voucher-bg-0">
                <div class="flag"></div>
                <div class="com-voucher rightBorder-grey"></div>
                <div class="top">
                    <span class="title">${couponItem.name!}</span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="middle">
                    <div class="privilege">满${couponItem.orderLeast!}元减</div>
                    <div class="validity">${couponItem.validTimeFrom!} - ${couponItem.validTimeTo!}</div>
                    <div class="applicability">适用范围：<#noescape>${couponItem.productRange!}</#noescape></div>
                    <span class="price"><span class="price-number">${couponItem.amount!}</span><em>元</em></span>
                </div>
                <div class="horizontal-white-split-line"></div>
                <div class="bottom">
                <div class="share_link">
                 <input type = "hidden" name ="shareAmount" value = "${couponItem.amount!}">
                   <input type ="hidden" name="couponItemId" value="${couponItem.couponItemId!}">
                    <a href="" >分享代金券</a>
                </div>
                </div>
		</div>
		</div>
        </#list>
 </#if>

 </#if>





 <script type="text/javascript">

       $(function(){
          setTabCounts();
          popUpShareCoupon();
       })

       function setTabCounts(){
            $("#availableCount").html($("#havailableCount").val());
	       	$("#usedCount").html($("#husedCount").val());
	       	$("#expiredCount").html($("#hexpiredCount").val());
	       	$("#donateCount").html($("#hdonateCount").val());
       }

	        function popUpShareCoupon(){
	          	  $(".voucherList.unused .bottom a[name='share-coupon-btn']").unbind("click");
				  $(".voucherList.unused .bottom a[name='share-coupon-btn']").bind("click",function(){
						initShareVoucher();
		                var share_couponItemId = $(this).siblings("[name='couponItemId']").val();
		                if(null!=share_couponItemId){
		                 	$("#couponItemId").val(share_couponItemId);
		                }
		                var shareAmount =  $(this).siblings("[name='shareAmount']").val();
		                if(shareAmount != null && shareAmount != ''){
		                  $("#shareAmount").val(shareAmount);
		                }
						if($("#btn-share").hasClass("hasShared")){
						 	$("#btn-share").removeClass("hasShared");
						}
		                $("#alert-dialog").popup();//id为弹出层的id
						$("#alert-dialog").popup("open");
	            });
	        }

    </script>

