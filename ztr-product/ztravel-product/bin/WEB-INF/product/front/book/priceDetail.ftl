                <div class="right-orderPriceDetail">
                	<input id="originTotalPrice" type="hidden" value=${(productBookInfo.totalPrice)!''}>
                    <div class="orderPriceTitle">￥价格明细</div>
                    <div class="orderPriceItem">
                        <div class="orderPriceItemTit">必选产品</div>
                        <table>
                            <colgroup>
                                <col width="99"/>
                                <col width="129"/>
                                <col width="89"/>
                            </colgroup>
                            <tbody>
                            <#if (productBookInfo.totalAdultPrice)?? && (productBookInfo.totalAdultPrice)?number gt 0 >
                            <tr id="adultTr">
                                <td >成人价</td>
                                <td>￥<span id="adultPrice">${(productBookInfo.adultPrice)!''}</span>× <span class="adaultNum">${(productBookInfo.adultNum)!'1'}</span>人</td>
                                <td class="colorTd"><b>￥<span id="totalAdult">${(productBookInfo.totalAdultPrice)!''}</span></b></td>
                            </tr>
                            </#if>
                             <#if (productBookInfo.totalChildPrice)?? && (productBookInfo.totalChildPrice)?number gt 0 >
                            <tr id="childTr">
                                <td >儿童价</td>
                                <td>￥<span id="childPrice">${(productBookInfo.childrenPrice)!''}</span>× <span class="childNum">${(productBookInfo.childNum)!'1'}</span>人</td>
                                <td class="colorTd"><b>￥<span id="totalChild">${(productBookInfo.totalChildPrice)!''}</span></b></td>
                            </tr>
                            </#if>
                            <#if (productBookInfo.totalSingleDiff)?? && (productBookInfo.totalSingleDiff)?number gt 0 >
                            <tr id="singleTr">
                                <td >单房差</td>
                                <td>￥<span id="singlePrice">${(productBookInfo.singleRoomDiff)!''}</span>× <span id="singleNum">${(productBookInfo.singleNum)!'1'}</span>间</td>
                                <td class="colorTd"><b>￥<span id="totalSingle">${(productBookInfo.totalSingleDiff)!''}</span></b></td>
                            </tr>
                            </#if>
                            <#if (productBookInfo.totalPackagePrice)?? && (productBookInfo.totalPackagePrice)?number gt 0 >
                            <tr id="packageTr">
                                <td >${(productBookInfo.packageName)!''}</td>
                                <td>￥<span id="packagePrice">${(productBookInfo.packagePrice)!''}</span>× <span class="packageNum">1</span>份</td>
                                <td class="colorTd"><b>￥<span id="totalPackage">${(productBookInfo.totalPackagePrice)!''}</span></b></td>
                            </tr>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                    <!--
                    <div class="orderPriceItem">
                        <div class="orderPriceItemTit">附加产品</div>
                        <table>
                            <colgroup>
                                <col width="99"/>
                                <col width="129"/>
                                <col width="69"/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td>欧洲WIFI</td>
                                <td>￥100× 12天</td>
                                <td class="colorTd"><b>￥1200</b></td>
                            </tr>
                            <tr>
                                <td>保险</td>
                                <td>￥1500× 1份</td>
                                <td class="colorTd"><b>￥1500</b></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    -->
	                 <div id="couponPriceInfo">
	                 </div>
                    <div class="payTotalBlock">
                        <table>
                            <colgroup>
                                <col width="99"/>
                                <col width="198"/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td class="fs18"><b>应付总额</b></td>
                                <td class="colorTd fs24">￥<b class="totalPrice" id="totalPrice">${(productBookInfo.totalPrice)!''}</b></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>