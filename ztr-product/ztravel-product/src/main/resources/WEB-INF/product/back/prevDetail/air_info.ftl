<#macro airInfo>

                <div class="commonModel">
                    <div id="airInfo"></div>
                    <div class="commonTitle"><i class="commonIcon airInfoIcon"></i>航班信息</div>
                    <#list (product.flight.main)! as flight>
                    <ul class="airInfoList clearfix">
                        <em class="dashedLine firstLine"></em>
                        <em class="dashedLine secondLine"></em>
                        <li class="leftGoBack">
                            <div class="lightBlue18Fonts">第${(flight.offsetDays)!}天</div>
                            <div class="goBackCommonStyle greenBg">${(flight.airRange)!}</div>
                        </li>
                        <li class="timePlaceAirport">
                            <#if (flight.stop)?? && flight.stop != ''>
                            <i class="stoppingPoint stop green-border-tooltip" title="${flight.stop}">经停</i>
                            <#else><span class="flyToIcon"></span></#if>
                            <div class="timePlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(flight.fromCity)!}</span>
                                    <span class="timeFonts">${(flight.departureTime)!}</span>
                                </div>
                                <div class="airportFonts">${(flight.fromAirPort)!}</div>
                            </div>
                            <div class="timePlaceInfo">
                                <div class="timePlaceFonts">
                                    <span class="placeFonts">${(flight.toCity)!}</span>
                                    <span class="timeFonts">${(flight.arrivalTime)!}</span>
                                </div>
                                <div class="color9f9f9fFonts">${(flight.toAirPort)!}</div>
                            </div>
                        </li>
                        <li class="shippingSpaceInfo">
                            <div class="lightBlue16Fonts">${(flight.flightNo)!} / ${(flight.cabin)!} / ${(flight.flightModel)!}</div>
                            <div class="airLogo">
                                <span class="pubFlights_MU verticalAlignMiddle"></span>
                                <span class="color9f9f9fFonts">${(flight.airLine)!}</span>
                            </div>
                        </li>
                    </ul>
                    </#list>

                </div>

</#macro>