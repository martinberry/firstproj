<#macro mainContent>
            <div id="scrollImg" class="scrollImg">
            <#list (product.images) as imageId>
                <a href="javascript:void(0);" class="localTravelBg" style="visibility:visible; background: url(${mediaserver}imageservice?mediaImageId=${imageId!''}) center no-repeat;"></a>               
            </#list>            
            </div>
            <div class="visa-instructor">
                <table>
                    <colgroup>
                        <col width="100">
                        <col width="">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>目的地：</th>
                        <td>
                        <#if product??>
                        <#list product.to as city>
                        ${city!''} 
                        </#list>
                        </#if>
                        </td>
                    </tr>
                    <tr>
                        <th>产品种类：</th>
                        <td>${(product.pieceType)!''}</td>
                    </tr>
                    <tr>
                        <th>服务语言：</th>
                        <td>${(product.language)!''}</td>
                    </tr>
                    <tr>
                        <th>服务时间：</th>
                        <td>${(product.serviceTime)!''}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
</#macro>            

