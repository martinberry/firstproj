   <#macro productIntroduction>
   <div class="commonModel">
                    <div id="productDetail"></div>
                    <div class="commonTitle"><i class="visaIcon orderIcon"></i>产品介绍</div>
                    <div class="materialModel">
                        <div class="productDetail">
                       <#noescape>${(product.additionalInfos['FEATURES'])!''}</#noescape>
                        </div>
                    </div>
                </div>
    </#macro>            
    
    
    
          