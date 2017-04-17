<#macro additionalInfos>

            <#assign product = (orderDetail.product)!/>
            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>费用说明</span>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonStyle">
                    <table class="commonLightBlueThead leftTh costTab">
                        <colgroup>
                            <col width="170">
                            <col width="">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>费用包含</th>
                            <td><#noescape>${(product.additionalInfos['FEE_INCLUDE'])!}</#noescape>
                            </td>
                        </tr>
                        <tr>
                            <th>费用不含</th>
                            <td><#noescape>${(product.additionalInfos['FEE_NOT_INCLUDE'])!}</#noescape>
                            </td>
                        </tr>
                        <tr>
                            <th>赠送项目</th>
                            <td><#noescape>${(product.additionalInfos['GIFT_ITEMS'])!}</#noescape>
                            </td>
                        </tr>
                        <tr>
                            <th>退改政策</th>
                            <td><#if product.additionalInfos['REFUND_POLICY'] ?? ><#noescape>${(product.additionalInfos['REFUND_POLICY'])!}</#noescape><#else>本产品为特惠产品，订单确认后不接受任何变更和退订</#if>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

</#macro>