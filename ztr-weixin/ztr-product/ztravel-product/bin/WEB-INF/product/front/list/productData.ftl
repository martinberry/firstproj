<#if productList??>
<#list productList as product>
    <section class="product-wrapper">
        <input type="hidden" name="productId" value="${(product.id)!''}">
        <input type="hidden" name="pid" value="${(product.pid)!''}">
        <input type="hidden" name="pName" value="${(product.pName)!''}">
        <div class="pro-item-box img-target-${product_index+1}"  onclick="window.location.href='${basepath}/product/front/detail/${(product.pid)!}'">
        <!--  为该DIV写click事件，控制页面跳转  -->
            <div class="content-above-image">
                <img src="${mediaserver}imageservice?mediaImageId=${(product.titleImageId)!''}" alt="">
                <a href="${basepath}/product/front/detail/${(product.pid)!''}" class="start-trip">
                    <i></i>
                    <span>开启旅程</span>
                </a>
            </div>
            <div class="parallax-image-wrapper" data-anchor-target=".img-target-${product_index+1}" data-bottom-top="bottom:-75%;" data-top-bottom="bottom:75%;">
                <img src="${mediaserver}imageservice?mediaImageId=${(product.imageId)!''}" data-original="${mediaserver}imageservice?mediaImageId=${(product.imageId)!''}">
            </div>
            <!--  <a class="img-link" href="${basepath}/product/front/detail/${(product.pid)!''}"></a>  -->
        </div>
        <div class="pro-list-white clearfix">
            <div class="list-white-left">
                <p class="list-white-title">标签</p>
                <div style="padding-top:43px;" class="clearfix">
                    <#if product.tags??>
                    <#list product.tags as tag>
                    <label class="border-radius label-color-${tag_index+1}">${tag!''}</label>
                    </#list>
                    </#if>
                </div>
            </div>
            <div class="list-white-right">
                <p class="list-white-title">加入心愿单</p>
                <div class="confirm-flag"><span class="wishList<#if product.isWish==true> active</#if>" onclick="javascript:void(0);"></span><p class="hasActiveTip">已加入心愿清单</p></div>
            </div>
            <div class="list-white-center">
                <p class="list-white-title">行程亮点</p>
                <div class="list-white-cnt clearfix">
                    <#if product.highLights??>
                    <#list product.highLights as highLight>
                    <div class="locBox">
                        <div class="locFlag"><label class="locImg img-${highLight_index+1}"></label></div>
                        <div class="locTxt"><span>${highLight!''}</span></div>
                    </div>
                    </#list>
                    </#if>
                </div>
            </div>
        </div>
    </section>
</#list>
</#if>