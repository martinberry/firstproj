<#macro priceExplain>

    <section class="commonModule">
        <h2 class="commonTitleH2 haveIcon"><i class="costIcon"></i>费用说明</h2>
        <div class="costDetails">
            <h3 class="costTitle">费用包含</h3>
            <ul class="costList">
                <#noescape>${(product.additionalInfos['FEE_INCLUDE'])!}</#noescape>
            </ul>
        </div>
        <div class="costDetails">
            <h3 class="costTitle">费用不含</h3>
            <ul class="costList">
                <#noescape>${(product.additionalInfos['FEE_NOT_INCLUDE'])!}</#noescape>
            </ul>
        </div>
        <div class="costDetails">
            <h3 class="costTitle">退改政策</h3>
            <ul class="costList">
                <#if product.additionalInfos['REFUND_POLICY'] ?? ><#noescape>${(product.additionalInfos['REFUND_POLICY'])!}</#noescape><#else>本产品为特惠产品，订单确认后不接受任何变更和退订</#if>
            </ul>
        </div>
    </section>
</#macro>