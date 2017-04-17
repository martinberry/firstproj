<#macro additionalProducts>
	<#if orderDetail.additionalProducts??>
            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>附加产品</span>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonStyle changeCommonStyle">
                    <table class="commonLightBlueThead">
                        <colgroup>
                            <col width="400">
                            <col width="230">
                        </colgroup>
                        <#if orderDetail.additionalProducts??>
                        <thead>
                            <tr>
                                <th>名称</th>
                                <th>数量</th>
                            </tr>
                        </thead>
                        <tbody>
                        	
								<#list orderDetail.additionalProducts as item>
		                            <tr>
		                                <td>${item.name!}</td>
		                                <td>${item.num!}</td>
		                            </tr>
                            	</#list>
							
                        </tbody>
                        </#if>
                    </table>
                </div>
            </div>
	</#if>
</#macro>