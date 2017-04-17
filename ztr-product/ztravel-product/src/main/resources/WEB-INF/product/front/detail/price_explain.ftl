<#macro priceExplain>
   <div class="commonModel">
        <div id="priceExplain" class="anchor"></div>
        <div class="commonTitle"><i class="commonIcon costIcon"></i>费用说明</div>
        <ul class="wrapperList clearfix">
            <li class="row twoRow">
                <h3 class="titleFonts">费用包含</h3>
                <ul class="listDetails">
                    <#noescape>${(product.additionalInfos['FEE_INCLUDE'])!}</#noescape>
                </ul>
            </li>
            <li class="row twoRow">
                <h3 class="titleFonts">真旅行赠送项目</h3>
                <ul class="listDetails">
                    <#noescape>${(product.additionalInfos['GIFT_ITEMS'])!}</#noescape>
                </ul>
            </li>
        </ul>
        <ul class="wrapperList clearfix">
            <li class="row twoRow">
                <h3 class="titleFonts">费用不含</h3>
                <ul class="listDetails">
                    <#noescape>${(product.additionalInfos['FEE_NOT_INCLUDE'])!}</#noescape>
                </ul>
            </li>
            <li class="row twoRow">
                <h3 class="titleFonts">退改政策</h3>
                <ul class="listDetails">
                <#if product.additionalInfos['REFUND_POLICY'] ?? ><#noescape>${(product.additionalInfos['REFUND_POLICY'])!}</#noescape><#else>本产品为特惠产品，订单确认后不接受任何变更和退订</#if>
                </ul>
            </li>
        </ul>
    </div>
</#macro>