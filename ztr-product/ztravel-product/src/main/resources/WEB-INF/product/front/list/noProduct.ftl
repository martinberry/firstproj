
    <div class="withoutOrder-box">
        <div class="withoutOrder-cnt">
            <div class="withoutOrder-img"></div>
            <p class="withoutTips">由<span class="withoutSpl-color">${(searchCriteria.departure)!''}</span>前往<span class="withoutSpl-color">${(searchCriteria.destination)!''}</span>的产品还未上线</p>
            <p class="recDes"><#if hasRecomProd == true>下面这几个产品也很不错哦</#if></p>
            <div class="downArrow"></div>
        </div>
    </div>

    <#include "/product/front/list/productData.ftl" />
