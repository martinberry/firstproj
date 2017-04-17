  <#macro priceExplain>
  <div class="commonModel">
                    <div id="feeInstructor"></div>
                    <div class="commonTitle"><i class="visaIcon feeIcon"></i>费用说明</div>
                   
                        <div class="feeModel">
                            <div class="feeEle-head">费用说明</div>
                            <div class="feeEle-content">
                            <#noescape>${(product.additionalInfos['FEE_DETAIL'])!''}</#noescape>
                            </div>
                        </div>
                    
                    
                    <div class="feeModel">
                        <div class="feeEle-head">退改政策</div>
                        <div class="feeEle-content">
                        <#noescape>${(product.additionalInfos['REFUND_POLICY'])!''}</#noescape>
                        </div>
                    </div>
                </div>
</#macro>                