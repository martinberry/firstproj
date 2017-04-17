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
                            <th>费用说明</th>
                            <td><#noescape>${(product.additionalInfos['FEE_DETAIL'])!}</#noescape>
                            </td>
                        </tr>
                             <tr>
                            <th>退改政策</th>
                            <td><#noescape>${(product.additionalInfos['REFUND_POLICY'])!}</#noescape>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

</#macro>