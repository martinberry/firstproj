                <div class="moduleContent">
                    <div class="titleContent clearfix">
                        <h3 class="titleFonts blue2fb0c4">支付信息</h3>
                    </div>
                    <table class="grayThead" style="width: auto;" id="payinfoTable">
                        <colgroup>
                            <col width="170">
                            <col width="190">
                            <col width="210">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>支付类型</th>
                            <th>金额</th>
                            <th>规则</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if ((orderDetail.statusEnum)!'') != "UNPAY">
                        <#if (orderDetail.feesDetail.paidInfo)??>
                        <#assign paidMap=orderDetail.feesDetail.paidInfo>
                        <#list paidMap?keys as key>
                        <tr>
                            <td class="fontSize16">${(key)!}</td>
                            <td class="orangeff6600">￥<span>${(paidMap[key])!''}</span></td>
                            <td class="fontSize16"></td>
                        </tr>
                        </#list>
                        </#if>
                        <tr>
                            <td class="fontSize16">代金券</td>
                            <td class="orangeff6600">￥<span>${(orderDetail.feesDetail.couponPrice)!''}</span></td>
                            <td class="fontSize16"></td>
                        </tr>
                        <tr>
                            <td class="fontSize16">订单总额</td>
                            <td colspan="2" class="fontSize16 orangeff6600">￥${(orderDetail.orderTotalPrice)!''}</td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>