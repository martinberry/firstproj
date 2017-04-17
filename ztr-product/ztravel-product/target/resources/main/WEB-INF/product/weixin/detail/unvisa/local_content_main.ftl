<#macro contentMain>
    <div class="productBanner">
        <img src="${mediaserver}imageservice?mediaImageId=${(product.images[0])!}">
    </div>
    <section class="commonModule noBorderTop">
        <h2 class="titleH2">${product.pName!''}</h2>
        <ul class="localGroup clearfix">
            <li>目的地
            <span>
            <#if product.to ??>
            <#list product.to as city>
            ${city!''}
            </#list>
            </#if>
            </span>
            </li>
            <li>产品种类<span>${(product.pieceType)!''}</span></li>
            <li>服务语言<span>${(product.language)!''}</span></li>
            <li>服务时间<span>${(product.serviceTime)!''}</span></li>
        </ul>
    </section>
</#macro>
