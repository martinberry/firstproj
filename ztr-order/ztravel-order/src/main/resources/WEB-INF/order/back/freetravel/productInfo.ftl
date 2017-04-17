                <div class="moduleContent">
                    <div class="titleContent clearfix">
                        <h3 class="titleFonts blue2fb0c4">产品信息</h3>
                    </div>
                    <table class="grayThead">
                        <colgroup>
                            <col width="127">
                            <col width="295">
                            <col width="127">
                            <col width="127">
                            <col width="127">
                            <col width="127">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>产品标题</th>
                            <th>套餐名称</th>
                            <th>出发日期</th>
                            <th>行程天数</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (orderDetail.products)??>
                        <#list orderDetail.products as product>
                        <tr>
                            <td class="fontSize16">${(product.productNo)!''}</td>
                            <td class="fontSize16">${(product.productTitle)!''}</td>
                            <td class="fontSize16">${(product.packageName)!''}</td>
                            <td>${(product.departureDate)!''}</td>
                            <td>${(product.travelDays)!''}</td>
                            <td>
                                <a href="${basepath}/product/back/prevDetail/${(product.productId)!''}" target="_blank">查看详情</a>
                            </td>
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>