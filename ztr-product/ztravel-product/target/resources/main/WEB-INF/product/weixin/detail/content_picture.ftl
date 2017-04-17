<#macro contentPicture>

    <div class="productBanner">
        <img src="${mediaserver}imageservice?mediaImageId=s1${(product.images[0])!}">
    </div>
    <section class="commonModule noBorderTop">
        <h2 class="titleH2">[${(product.pName)!}] ${(product.subName)!}</h2>
        <div class="destinationCont">
            <span>目的地:</span>
            <span>
            <#if (product.to)??>
                <#list product.to as to>
                    <#if to_index != 0>,</#if>
                    ${to!}
                </#list>
            </#if>
            </span>
        </div>
        <div class="labelNames">
            <#if (product.tags)??>
            <#list product.tags as tag>
                <span
                <#if (tag_index % 4 == 0)>
                class="lightBlueBg"
                <#elseif (tag_index % 4 == 1)>
                class="purpleBg"
                <#elseif (tag_index % 4 == 2)>
                class="orangeBg"
                <#elseif (tag_index % 4 == 3)>
                class="deepBlueBg"
                </#if>>${(tag)!}</span>
            </#list>
            </#if>
        </div>
    </section>

    <section class="commonModule" style="margin-bottom:0">
        <ul class="lightSpotList">
        <#list (product.highLights)! as highLight>
            <li>
                <#if highLight_index == 0>
                <i class="planeIcon"></i>
                <#elseif highLight_index == 1>
                <i class="hotelIcon"></i>
                <#elseif highLight_index == 2>
                <i class="experienceIcon"></i>
                <#elseif highLight_index == 3>
                <i class="serveIcon"></i>
                </#if>
                <h3 class="blueTitles">${(product.highLightTitles[highLight_index])!}</h3>
                <p>${highLight!}</p>
            </li>
        </#list>
        </ul>
    </section>
    <div class="pathSeparate" onClick="window.open('journeyRecommend/${(product.pid)!}','_blank')">
        <a class="pathRuleBg ui-link" href="javascript:void(0);">
            <span class="pathRuleBg-title">
                <i class="pathRuleBg-icon"></i>查看推荐行程</span>
        </a>
    </div>
</#macro>