<#macro orderFlights>
			<section class="commonModule">
                <h2 class="commonTitleH2 haveIcon"><i class="planeIcon"></i>机票信息</h2>
                <!--去程机票信息开始-->
                <#if (flightInfoVo.goFlightList)?? && (flightInfoVo.goFlightList)?size gt 0>
	                <#list flightInfoVo.goFlightList as goFlight>
		                <div class="airInfoContent">
		                	<label class="blueBg"><#if goFlight_index == 0 >去<#else>去程-中转</#if></label>
		                    <div class="dayNumber">
		                        <strong class="days">第${(goFlight.offsetDays)!}天</strong>
		                        <span class="travelTime"><em>${(goFlight.departDate)!}</em> 出发</span>
		                    </div>
		                    <p class="airPFonts">${(goFlight.airLineName)!}${(goFlight.flightNum)!} / ${(goFlight.cabin)!} / ${(goFlight.planeCode)!}</p>
		                    <div class="timePlaceContent clearfix">
		                        <div class="timePlaceLeft">
		                            <div class="placeBlueFonts">${(goFlight.fromCity)!}</div>
		                            <div class="timeFonts">${(goFlight.departTime)!}</div>
		                            <div class="airFonts">${(goFlight.departAirPort)!}${(goFlight.departTerminal)!}</div>
		                        </div>
		                        <!--<div class="totalTimeContent">
		                            <label>
		                                <i class="dottedIcon leftDotted"></i>
		                                <span class="dashLine"></span>
		                            </label>
		                            <span class="totalTimeFonts"><#if (goFlight.flightTimeCost)??>${(goFlight.flightTimeCost)?replace("h","小时")?replace("m","分")}</#if></span>
		                            <label>
		                                <span class="dashLine"></span>
		                                <i class="dottedIcon rightDotted"></i>
		                            </label>
		                        </div>-->
		                        <div class="timePlaceRight">
		                            <div class="placeBlueFonts">${(goFlight.toCity)!}</div>
		                            <div class="timeFonts">${(goFlight.arriveTime)!}<#if (goFlight.addDays)?? && (goFlight.addDays) gt 0><i class="addDayName">+${(goFlight.addDays)!}天</i></#if></div>
		                            <div class="airFonts">${(goFlight.arriveAirPort)!}${(goFlight.arriveTerminal)!}</div>
		                        </div>
		                    </div>
		                    <#if (goFlight.stop)?? && (goFlight.stop) != ''>
		                    	<div class="duringStop">经停: <em>${(goFlight.stop)!''}</em></div>
		                    </#if>
		                </div>
		                </#list>
                </#if>
                <!--去程机票信息结束-->

                <!--中间程机票信息开始-->
                <#if (flightInfoVo.midlFlightList)?? && (flightInfoVo.midlFlightList)?size gt 0>
                	<#list flightInfoVo.midlFlightList as midflight>
		                <div class="airInfoContent">
		                    <div class="dayNumber">
		                        <strong class="days">第${(midflight.offsetDays)!}天</strong>
		                         <#if (midflight.airRangeIndex)?ends_with("1")>
				                   <span class="orangeBg">第${((midflight.airRangeIndex)?substring(0,1))?eval + 1}程</span>
				                <#else>
				                   <span class="orangeBg">第${((midflight.airRangeIndex)?substring(0,1))?eval + 1}程中转</span>
				                </#if>
		                        <span class="travelTime"><em>${(midflight.departDate)!}</em> 出发</span>
		                    </div>
		                    <p class="airPFonts">${(midflight.airLineName)!}${(midflight.flightNum)!} / ${(midflight.cabin)!} / ${(goFlight.midflight)!}</p>
		                    <div class="timePlaceContent clearfix">
		                        <div class="timePlaceLeft">
		                            <div class="placeBlueFonts">${(midflight.fromCity)!}</div>
		                            <div class="timeFonts">${(midflight.departTime)!}</div>
		                            <div class="airFonts">${(midflight.departAirPort)!}${(midflight.departTerminal)!}</div>
		                        </div>
		                        <!--<div class="totalTimeContent">
		                            <label>
		                                <i class="dottedIcon leftDotted"></i>
		                                <span class="dashLine"></span>
		                            </label>
		                            <span class="totalTimeFonts"><#if (midflight.flightTimeCost)??>${(midflight.flightTimeCost)?replace("h","小时")?replace("m","分")}</#if></span>
		                            <label>
		                                <span class="dashLine"></span>
		                                <i class="dottedIcon rightDotted"></i>
		                            </label>
		                        </div>-->
		                        <div class="timePlaceRight">
		                            <div class="placeBlueFonts">${(midflight.toCity)!}</div>
		                            <div class="timeFonts">${(midflight.arriveTime)!}<#if (midflight.addDays)?? && (midflight.addDays) gt 0><i class="addDayName">+${(midflight.addDays)!}天</i></#if></div>
		                            <div class="airFonts">${(midflight.arriveAirPort)!}${(midflight.arriveTerminal)!}</div>
		                        </div>
		                    </div>
		                    <#if (midflight.stop)?? && (midflight.stop) != ''>
		                    	<div class="duringStop">经停: <em>${(midflight.stop)!''}</em></div>
		                    </#if>
		                </div>
	                </#list>
	            </#if>
                <!--中间程机票信息结束-->

                <!--返程机票信息开始-->
                <#if (flightInfoVo.backFlightList)?? && (flightInfoVo.backFlightList)?size gt 0>
	                <#list flightInfoVo.backFlightList as backflight>
		                <div class="airInfoContent">
		                    <label class="blueBg"><#if backflight_index == 0>返<#else>返程中转</#if></label>
		                    <div class="dayNumber">
		                        <strong class="days">第${(backflight.offsetDays)!}天</strong>
		                        <span class="travelTime"><em>${(backflight.departDate)!}</em> 出发</span>
		                    </div>
		                    <p class="airPFonts">${(backflight.airLineName)!}${(backflight.flightNum)!} / ${(backflight.cabin)!} / ${(backflight.planeCode)!}</p>
		                    <div class="timePlaceContent clearfix">
		                        <div class="timePlaceLeft">
		                            <div class="placeBlueFonts">${(backflight.fromCity)!}</div>
		                            <div class="timeFonts">${(backflight.departTime)!}</div>
		                            <div class="airFonts">${(backflight.departAirPort)!}${(backflight.departTerminal)!}</div>
		                        </div>
		                        <!--<div class="totalTimeContent">
		                            <label>
		                                <i class="dottedIcon leftDotted"></i>
		                                <span class="dashLine"></span>
		                            </label>
		                            <span class="totalTimeFonts"><#if (backflight.flightTimeCost)??>${(backflight.flightTimeCost)?replace("h","小时")?replace("m","分")}</#if></span>
		                            <label>
		                                <span class="dashLine"></span>
		                                <i class="dottedIcon rightDotted"></i>
		                            </label>
		                        </div>-->
		                        <div class="timePlaceRight">
		                            <div class="placeBlueFonts">${(backflight.toCity)!}</div>
		                            <div class="timeFonts">
		                                <strong>${(backflight.arriveTime)!}<#if (backflight.addDays)?? && (backflight.addDays) gt 0><i class="addDayName">+${(backflight.addDays)!}天</i></#if></strong>
		                            </div>
		                            <div class="airFonts">${(backflight.arriveAirPort)!}${(backflight.arriveTerminal)!}</div>
		                        </div>
		                    </div>
		            		<#if (backflight.stop)?? && (backflight.stop) != ''>
			                    <div class="duringStop">经停: <em>${(backflight.stop)!''}</em></div>
		                    </#if>
		                </div>
	                </#list>
                </#if>
            <!--返程机票信息结束-->
            
            </section>
            
            
            
</#macro>