	<!-- 费用说明start -->
      <div class="commonModel">
          <div id="feeInstructor"></div>
          <div class="commonTitle"><i class="visaIcon feeIcon"></i>费用说明</div>
          <div class="feeInclude clearfix">
              <div class="feeEle">
                  <div class="feeEle-head">费用包含</div>
                  <div class="feeEle-content">
                     <#noescape><#if product.additional??>${(product.additional['FEE_DETAIL'])!}</#if></#noescape>
                  </div>
              </div>
          </div>
          <div class="feeModel">
              <div class="feeEle-head">退改政策</div>
              <div class="feeEle-content">
              <#if product.additional??>
              	<#if product.additional['REFUND_POLICY'] ?? >
              		<#noescape>${(product.additional['REFUND_POLICY'])!}</#noescape>
              	</#if>
              </#if>
              </div>
          </div>
      </div>
      <!-- 费用说明end -->


      <!-- 预定须知start -->
       <div class="commonModel">
           <div id="orderNotice"></div>
           <div class="commonTitle"><i class="visaIcon orderIcon"></i>预订须知</div>
           <div class="ordereModel">
               <div class="orderEle-content">
                   <#if product.additional??><#noescape>${(product.additional['BOOKING'])!}</#noescape></#if>
               </div>
           </div>
       </div>
       <!-- 预定须知end -->