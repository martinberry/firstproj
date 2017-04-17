<#macro attention>
    <section class="hotelModule">
        <div class="hotelModule-head">
            <h2 class="commonTitleH2 haveIcon"><i class="localCautious"></i>预定须知</h2>
        </div>
        <ul class="cautiousList"><#noescape>${(product.additionalInfos['BOOKING'])!''}</#noescape>
        </ul>
    </section>
</#macro>

