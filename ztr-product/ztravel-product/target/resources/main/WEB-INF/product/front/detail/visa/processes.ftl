	<!-- 办理流程start -->
    <div class="commonModel">
        <div id="handleFlow"></div>
        <div class="commonTitle"><i class="visaIcon handleIcon"></i>办理流程</div>
        <div class="handleModel">
        <#if (product.processes)?? && (product.processes)?size gt 0>
            <ul class="handleGroup clearfix">
                <#list product.processes as process>
                    <li>
                        <i class="leftFlag visaIcon circleIcon"></i>
                        <span class="leftLine"></span>
                        <div class="handleEle-head">
                        	<span>${(process_index + 1)}.${(process.title)!''}</span>
                        	<#if (process.images)?? && (process.images)?size gt 0>
                        		<#list process.images as image>	
                           	 		<a class="lookImg-link post-link" href="javascript:void(0);" data-image="${image!}">查看样图</a>
                           	 	</#list>
                        	</#if>
                        </div>
                        <div class="handleEle-content">
                            <#noescape>${(process.content)!''}</#noescape>
                        </div>
                    </li>
                </#list>
            </ul>
            </#if>
        </div>
    </div>
    <!-- 办理流程end -->