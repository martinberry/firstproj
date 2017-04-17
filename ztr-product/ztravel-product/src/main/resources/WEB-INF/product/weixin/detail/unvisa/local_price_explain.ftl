  <#macro priceExplain>
    <section class="commonModule">
        <h2 class="commonTitleH2 haveIcon"><i class="costIcon"></i>费用说明</h2>
        <div class="costDetails">
            <h3 class="costTitle">费用说明</h3>
            <ul class="costList"><#noescape>${(product.additionalInfos['FEE_DETAIL'])!''}</#noescape></ul>
        </div>
         <div class="costDetails">
            <h3 class="costTitle">退改政策</h3>
            <ul class="costList"><#noescape>${(product.additionalInfos['REFUND_POLICY'])!''}</#noescape></ul>
        </div>
    </section>
  </#macro>
