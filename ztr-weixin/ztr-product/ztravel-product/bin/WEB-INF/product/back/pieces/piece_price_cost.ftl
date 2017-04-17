<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","common/ZtrDropDown.js",
    "product/back/pieces/piece_price_cost.js","product/back/common_utils.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","css/jquery.tagsinput.css","css/maintain/productManagement.css"]
    curModule="产品管理"
    title="成本配置">

    <@pieceMenu.pieceMenu curPieceModule="priceCostInfo">
        <div class="main-container changeMainContent">
            <input id='productId_hidden' type='hidden' value='${(priceInfo.id)!}'/>
            <input id='productPid_hidden' type='hidden' value='${(priceInfo.pid)!}'/>
            <input id='progress_hidden' type='hidden' value='${(priceInfo.progress)!}'/>
            <input id='productNature_hidden' type='hidden' value='${(priceInfo.nature)!}'/>
            <input id='priceType_hidden' type='hidden' value='${(priceInfo.priceType)!}'/>
            <#if !(priceInfo.priceInfos)?? || ((priceInfo.priceInfos)?size == 0)>
            <section class="whiteBg sectionDiv">
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="110">
                        <col width="850">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>价格类型名称</th>
                        <td style="position: relative;">
                            <input name="priceName_input" type="text" style="width: 300px;" placeholder="请填写价格类型">
                            <div class="commonLinkWrap">
                                <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="configurationDiv">
                    <h3>游客类型及底价</h3>
                    <table class="noBorderCommonTab">
                        <colgroup>
                            <col width="110">
                            <col width="850">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>成人</th>
                            <td class="sourceNameTd">
                                <input name="adultCost_input" type="text" style="width: 300px;" placeholder="请填写成人底价">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <div class="checkboxContent">
                                    <label class="checkboxInfo" style="margin:0;">
                                        <span class="checkboxIcon"></span>儿童
                                    </label>
                                </div>
                            </th>
                            <td>
                                <input name="childCost_input" type="text" style="width: 300px;" placeholder="请填写儿童底价" disabled>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>
            <#else>
            <#list priceInfo.priceInfos as price>
            <section class="whiteBg sectionDiv">
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="110">
                        <col width="850">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>价格类型名称</th>
                        <td style="position: relative;">
                            <input name="priceId_hidden" type="hidden"value="${(price.id)!''}">
                            <input name="priceName_input" type="text" style="width: 300px;" placeholder="请填写价格类型" value="${(price.name)!''}">
                            <div class="commonLinkWrap">
                                <#if (price_index == 0)>
                                <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                <#else>
                                <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                </#if>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="configurationDiv">
                    <h3>游客类型及底价</h3>
                    <table class="noBorderCommonTab">
                        <colgroup>
                            <col width="110">
                            <col width="850">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>成人</th>
                            <td class="sourceNameTd">
                                <input name="adultCost_input" type="text" style="width: 300px;" placeholder="请填写成人底价" value="${(price.adultCost)!''}">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <div class="checkboxContent">
                                    <label class="checkboxInfo <#if (price.hasChildPrice)?? && (price.hasChildPrice)?c == "true">active</#if>" style="margin:0;">
                                        <span class="checkboxIcon"></span>儿童
                                    </label>
                                </div>
                            </th>
                            <td>
                                <input name="childCost_input" type="text" style="width: 300px;" placeholder="请填写儿童底价" <#if (price.hasChildPrice)?? && (price.hasChildPrice)?c == "true">value="${(price.childCost)!''}"<#else>disabled</#if>>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>
            </#list>
            </#if>
        </div>

        <div class="addSectionTemp" style="display: none;">
            <section class="whiteBg sectionDiv">
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="110">
                        <col width="850">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>价格类型名称</th>
                        <td style="position: relative;">
                            <input name="priceName_input" type="text" style="width: 300px;" placeholder="请填写价格类型">
                            <div class="commonLinkWrap">
                                <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="configurationDiv">
                    <h3>游客类型及底价</h3>
                    <table class="noBorderCommonTab">
                        <colgroup>
                            <col width="110">
                            <col width="850">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>成人</th>
                            <td class="sourceNameTd">
                                <input name="adultCost_input" type="text" style="width: 300px;" placeholder="请填写成人底价">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <div class="checkboxContent">
                                    <label class="checkboxInfo" style="margin:0;">
                                        <span class="checkboxIcon"></span>儿童
                                    </label>
                                </div>
                            </th>
                            <td>
                                <input name="childCost_input" type="text" style="width: 300px;" placeholder="请填写儿童底价" disabled>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </@pieceMenu.pieceMenu>
</@html.htmlIndex>