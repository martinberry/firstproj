<#macro flightInfo>

            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>机票信息</span>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonStyle changeCommonStyle">
                <#if (orderDetail.product.goFlightList)??>
                <#list orderDetail.product.goFlightList as go>
                    <ul class="airInfoList clearfix">
                        <em class="dashedLine firstLine"></em>
                        <em class="dashedLine secondLine"></em>
                        <li class="leftGoBack">
                            <div class="lightBlue18Fonts">第${go.offsetDays!}天</div>
                            <#if go_index == 0 >
	                            <div class="goBackCommonStyle greenBg">去程</div>
	                         <#else>
	                          	<div class="goBackCommonStyle greenBg">去程-中转</div>
	                         </#if>
                        </li>
                        <li class="timePlaceAirport">
                        <#if (go.stop)?? && go.stop != ''>
                            <i class="stoppingPoint stop green-border-tooltip" title="${go.stop}">经停</i>
                            <#else><span class="flyToIcon"></span></#if>
                            <div class="timePlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(go.fromCity)!}</span>
                                    <span class="timeFonts">${(go.departTime)!}</span>
                                </div>
                                <div class="airportFonts">${(go.departAirPort)!}</div>
                            </div>
                            <div class="timePlaceInfo rightPlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(go.toCity)!}</span>
                                    <span class="timeFonts">${(go.arriveTime)!}<#if (go.addDays)?? && (go.addDays) gt 0><em>+${(go.addDays)!}天</em></#if></span>
                                </div>
                                <div class="color9f9f9fFonts">${(go.arriveAirPort)!}</div>
                            </div>
                        </li>
                        <li class="shippingSpaceInfo">
                            <div class="lightBlue18Fonts">${(go.flightNum)!} / ${(go.cabin)!} / ${(go.planeCode)!}</div>
                            <div class="airLogo">
                                <!--<span class="pubFlights_MU verticalAlignMiddle"></span>-->
                                <span class="color9f9f9fFonts">${(go.airLineName)!}</span>
                            </div>
                        </li>
                    </ul>
                    </#list>
                    </#if>

                <#if (orderDetail.product.midlFlightList)??>
                <#list orderDetail.product.midlFlightList as mid>
                    <ul class="airInfoList clearfix">
                        <em class="dashedLine firstLine"></em>
                        <em class="dashedLine secondLine"></em>
                        <li class="leftGoBack">
                            <div class="lightBlue18Fonts">第${mid.offsetDays!}天</div>
                            <#if (mid.airRangeIndex)?ends_with("1") >
	                            <div class="goBackCommonStyle greenBg">第${((mid.airRangeIndex)?substring(0,1))?eval + 1}程</div>
	                         <#else>
	                          	<div class="goBackCommonStyle greenBg">第${((mid.airRangeIndex)?substring(0,1))?eval + 1}程-中转</div>
	                         </#if>
                        </li>
                        <li class="timePlaceAirport">
                        <#if (mid.stop)?? && mid.stop != ''>
                            <i class="stoppingPoint stop green-border-tooltip" title="${mid.stop}">经停</i>
                            <#else><span class="flyToIcon"></span></#if>
                            <div class="timePlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(mid.fromCity)!}</span>
                                    <span class="timeFonts">${(mid.departTime)!}</span>
                                </div>
                                <div class="airportFonts">${(mid.departAirPort)!}</div>
                            </div>
                            <div class="timePlaceInfo rightPlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(mid.toCity)!}</span>
                                    <span class="timeFonts">${(mid.arriveTime)!}<#if (mid.addDays)?? && (mid.addDays) gt 0><em>+${(mid.addDays)!}天</em></#if></span>
                                </div>
                                <div class="color9f9f9fFonts">${(mid.arriveAirPort)!}</div>
                            </div>
                        </li>
                        <li class="shippingSpaceInfo">
                            <div class="lightBlue18Fonts">${(mid.flightNum)!} / ${(mid.cabin)!} / ${(mid.planeCode)!}</div>
                            <div class="airLogo">
                                <!--<span class="pubFlights_MU verticalAlignMiddle"></span>-->
                                <span class="color9f9f9fFonts">${(mid.airLineName)!}</span>
                            </div>
                        </li>
                    </ul>
                    </#list>
                    </#if>

                <#if (orderDetail.product.backFlightList)??>
                <#list orderDetail.product.backFlightList as back>
                    <ul class="airInfoList clearfix">
                        <em class="dashedLine firstLine"></em>
                        <em class="dashedLine secondLine"></em>
                        <li class="leftGoBack">
                            <div class="lightBlue18Fonts">第${back.offsetDays!}天</div>
                            <#if back_index == 0 >
	                            <div class="goBackCommonStyle greenBg">返程</div>
	                         <#else>
	                          	<div class="goBackCommonStyle greenBg">返程-中转</div>
	                         </#if>
                        </li>
                        <li class="timePlaceAirport">
                        <#if (back.stop)?? && back.stop != ''>
                            <i class="stoppingPoint stop green-border-tooltip" title="${back.stop}">经停</i>
                            <#else><span class="flyToIcon"></span></#if>
                            <div class="timePlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(back.fromCity)!}</span>
                                    <span class="timeFonts">${(back.departTime)!}</span>
                                </div>
                                <div class="airportFonts">${(back.departAirPort)!}</div>
                            </div>
                            <div class="timePlaceInfo rightPlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(back.toCity)!}</span>
                                    <span class="timeFonts">${(back.arriveTime)!}<#if (back.addDays)?? && (back.addDays) gt 0><em>+${(back.addDays)!}天</em></#if></span>
                                </div>
                                <div class="color9f9f9fFonts">${(back.arriveAirPort)!}</div>
                            </div>
                        </li>
                        <li class="shippingSpaceInfo">
                            <div class="lightBlue18Fonts">${(back.flightNum)!} / ${(back.cabin)!} / ${(back.planeCode)!}</div>
                            <div class="airLogo">
                                <!--<span class="pubFlights_MU verticalAlignMiddle"></span>-->
                                <span class="color9f9f9fFonts">${(back.airLineName)!}</span>
                            </div>
                        </li>
                    </ul>
                    </#list>
                    </#if>
                </div>
            </div>


        </#macro>