		<!-- 出行计划start -->
        <div class="panel-wrap">
            <div class="panelWrap-title">
                <i class="visaIcon travelDate"></i>出行日期
            </div>
            <div class="calendarWrap" id="visaCalendar"></div>
            <div class="panelWrap-title">
                <i class="visaIcon priceType"></i>价格类型
            </div>
            <#if (product.priceInfos)??>
            <#assign prices = product.priceInfos>
	            <ul class="priceGroup clearfix">
	            	<#list prices as price>
		                <li >
		                    <a href="javascript:void(0);" cosid="${(price.id)!}">${(price.name)!}</a>
		                </li>
	                </#list>
	            </ul>
            </#if>
            <#if (product.priceInfos)??>
	            <div class="panelWrap-head">
	            	<#list prices as price>
	            		<#if price_index == 0>
			                <div class="panelWrap-box">
			                    <i class="visaIcon persionIcon"></i>成人：
			                    <div class="dropdown" style="width: 56px;">
			                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
			                            <span class="activeFonts">2</span>
			                            <span class="caret"></span>
			                        </a>
			                        <ul class="dropdown-menu adult">
			                            <li><a href="javascript:void(0);">1</a></li>
			                            <li class="active"><a href="javascript:void(0);">2</a></li>
			                            <li><a href="javascript:void(0);">3</a></li>
			                            <li><a href="javascript:void(0);">4</a></li>
			                            <li><a href="javascript:void(0);">5</a></li>
			                            <li><a href="javascript:void(0);">6</a></li>
			                            <li><a href="javascript:void(0);">7</a></li>
			                            <li><a href="javascript:void(0);">8</a></li>
			                            <li><a href="javascript:void(0);">9</a></li>
			                            <li><a href="javascript:void(0);">10</a></li>
			                        </ul>
			                    </div>
			                    <span class="priceNum">￥<span id="adultPrice">${(price.adultPrice)!''}</span><span>/人</span></span>
			                </div>
			                <div class="" style="<#if (price.hasChildPrice)?? && (price.hasChildPrice)?c == "true">display;<#else>display:none;</#if>">
			                    <span class="panelWrap-empty"></span>儿童：
			                    <div class="dropdown" style="width: 56px;">
			                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
			                            <span class="activeFonts">0</span>
			                            <span class="caret"></span>
			                        </a>
			                        <ul class="dropdown-menu child">
			                        	<li class="active"><a href="javascript:void(0);">0</a></li>
			                            <li><a href="javascript:void(0);">1</a></li>
			                            <li><a href="javascript:void(0);">2</a></li>
			                            <li><a href="javascript:void(0);">3</a></li>
			                            <li><a href="javascript:void(0);">4</a></li>
			                            <li><a href="javascript:void(0);">5</a></li>
			                            <li><a href="javascript:void(0);">6</a></li>
			                            <li><a href="javascript:void(0);">7</a></li>
			                            <li><a href="javascript:void(0);">8</a></li>
			                            <li><a href="javascript:void(0);">9</a></li>
			                            <li><a href="javascript:void(0);">10</a></li>
			                        </ul>
			                    </div>
			                    <span class="priceNum">￥<span id="childPrice">${(price.childPrice)!''}</span><span>/人</span></span>
			                </div>
		                </#if>
	                </#list>
	            </div>
            </#if>
            <div class="panelWrap-price">
                <i class="visaIcon paymentIcon"></i>价格：<span>￥<span id="total">--</span></span>
            </div>
            <div class="visaSubmit subBtn">
                <a href="javascript:void(0);" class="bigOrangeBtn active">立即预订</a>
            </div>
            <div id="leftFlag"></div>
        </div>
        <!-- 出行计划end -->