			<!--基本信息start  -->
			<img class="defaultImg" <#if product.firstImage??>src="${mediaserver}imageservice?mediaImageId=${(product.firstImage)!}"</#if> alt="image">
            <#if (product.detailInfo)??>
	            <div class="visa-instructor">
	                <table>
	                    <colgroup>
	                        <col width="100">
	                        <col width="">
	                    </colgroup>
	                    <tbody>
	                    <tr>
	                        <th>面试要求：</th>
	                        <td>
	                        	<#if product.detailInfo.isInterview?c == "true">
	                        		是
                        		<#else>
                        			否
                        		</#if>
                    		</td>
	                    </tr>
	                    <tr>
	                        <th>签证有效期：</th>
	                        <td><#if product.detailInfo.validate??>${(product.detailInfo.validate)!}</#if></td>
	                    </tr>
	                    <tr>
	                        <th>可停留日期：</th>
	                        <td><#if product.detailInfo.stayTime??>${(product.detailInfo.stayTime)!}</#if></td>
	                    </tr>
	                    <tr>
	                        <th>可入境次数：</th>
	                        <td><#if product.detailInfo.times??>${(product.detailInfo.times)!}</#if>次</td>
	                    </tr>
	                    <tr>
	                        <th style="vertical-align: top;">受理范围：</th>
	                        <td>
	                            <#if product.detailInfo.scope??>${(product.detailInfo.scope)!}</#if>
	                        </td>
	                    </tr>
	                    </tbody>
	                </table>
	            </div>
            </#if>
            <!--基本信息end  -->
            
            <!-- bar start-->
	            <div class="visa-navbar" id="navbar">
	                <ul class="nav fixedList clearfix">
	                    <li>
	                        <a href="#visaMaterial">签证材料</a>
	                    </li>
	                    <li>
	                        <a href="#handleFlow">办理流程</a>
	                    </li>
	                    <li>
	                        <a href="#feeInstructor">费用说明</a>
	                    </li>
	                    <li>
	                        <a href="#orderNotice">预定须知</a>
	                    </li>
	                    <li>
	                        <a href="#userJudge">用户评价</a>
	                    </li>
	                    <li class="lastBar subBtn">
	                        <a href="javascript:void(0);" class="fixOrder">立即预订</a>
	                    </li>
	                </ul>
	            </div>
            <!-- bar end-->
