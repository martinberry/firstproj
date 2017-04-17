 <#macro attention>
 <div class="commonModel">
                    <div id="matters"></div>
                    <div class="commonTitle"><i class="visaIcon noticeIcon"></i>预订须知</div>
                    <div class="ordereModel">
                        <div class="productDetail">
                           <#noescape>${(product.additionalInfos['BOOKING'])!''}</#noescape>
                        </div>
                    </div>
                </div>
</#macro>                