<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","common/ZtrDropDown.js",
    "product/back/pieces/piece_price_sale.js","product/back/common_utils.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","css/jquery.tagsinput.css","css/maintain/productManagement.css"]
    curModule="产品管理"
    title="价格维护">

    <@pieceMenu.pieceMenu curPieceModule="priceSaleInfo">
        <div class="main-container changeMainContent">
            <section class="whiteBg sectionDiv">
                <input id='productId_hidden' type='hidden' value='${(priceInfo.id)!}'/>
                <input id='productPid_hidden' type='hidden' value='${(priceInfo.pid)!}'/>
                <input id='progress_hidden' type='hidden' value='${(priceInfo.progress)!}'/>
                <input id='productNature_hidden' type='hidden' value='${(priceInfo.nature)!}'/>
                <input id='priceType_hidden' type='hidden' value='${(priceInfo.priceType)!}'/>
                <#if (priceInfo.priceInfos)??>
                <#list priceInfo.priceInfos as price>
                <table class="noBorderCommonTab priceContain">
                    <colgroup>
                        <col width="110">
                        <col width="850">
                    </colgroup>
                    <tbody>
                    <input name='priceId_hidden' type='hidden' value='${(price.id)!}'/>
                    <input name='priceName_hidden' type='hidden' value='${(price.name)!}'/>
                    <input name='hasChildPrice_hidden' type='hidden' value='${(price.hasChildPrice)?string("true", "false")}'/>
                    <tr>
                        <th>价格类型名称</th>
                        <td>${(price.name)!''}</td>
                    </tr>
                    <tr>
                        <th>售价</th>
                        <td>
                            <div class="saleGroup">
                                <span>成人</span><input name='adultPrice_input' class="adult" type="text" style="width: 150px;" placeholder="请填写成人售价" value="${(price.adultPrice)!''}">
                                <span>儿童</span><input name='childPrice_input' class="child" type="text" style="width: 150px;" placeholder="请填写儿童售价" <#if (price.hasChildPrice)?? && (price.hasChildPrice)?c == "true">value="${(price.childPrice)!''}"<#else>disabled</#if>>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                </#list>
                </#if>
            </section>
        </div>
    </@pieceMenu.pieceMenu>
</@html.htmlIndex>