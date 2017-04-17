<#macro bookingNote>
	<div id="orderPoint"></div>
    <section class="commonModule">
        <h2 class="commonTitleH2 haveIcon"><i class="noticeIcon"></i>预定须知</h2>
        <div class="costDetails">
            <h3 class="costTitle">订单须知</h3>
            <ul class="costList maxHeight" id="orderWrap">
                <#noescape>${(product.additionalInfos['BOOKING'])!}</#noescape>
            </ul>
        </div>
        <div class="commonBottomStyle orderNotice">
           <h3 class="commonBottomTil"><i class="bAndTArrow btmAndTopArrow"></i>查看更多</h3>
        </div>
        <div class="commonBottomStyle closeOrderNotice">
           <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
        </div>
    </section>
    <div id="visaPoint"></div>
    <section class="commonModule">
      <h2 class="commonTitleH2 haveIcon"><i class="visaIcon"></i>签证须知</h2>
      <div class="costDetails">
      <h3 class="costTitle">签证须知</h3>
      <ul class="costList maxHeight" id="visaWrap">
      	<#noescape>${(product.additionalInfos['VISA'])!}</#noescape>
      </ul>
      </div>
      <div class="commonBottomStyle visaNotice">
         <h3 class="commonBottomTil"><i class="bAndTArrow btmAndTopArrow"></i>查看更多</h3>
      </div>
      <div class="commonBottomStyle closeVisaNotice">
          <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
      </div>
    </section>

</#macro>