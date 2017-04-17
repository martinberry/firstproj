 <#macro useIntroduction>
   <div class="commonModel">
                    <div id="instructor"></div>
                    <div class="commonTitle"><i class="visaIcon instructIcon"></i>使用说明</div>
                    <div class="handleModel">
                        <div class="productDetail">
                         <#noescape>${(product.additionalInfos['INTRODUCTIONS'])!''}</#noescape>
                        </div>
                    </div>
                </div>
 </#macro>               