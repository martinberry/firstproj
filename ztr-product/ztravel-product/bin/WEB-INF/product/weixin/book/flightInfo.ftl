<#macro bookFlight>
<!-- 机票信息开始 -->
<section class="commonModule">
<#if (productBookInfo.goFlightList)?? && (productBookInfo.goFlightList)?size gt 0>
    <h2 class="commonTitleH2 haveIcon"><i class="planeIcon"></i>机票信息</h2>
    <!--去程航班信息开始-->
	<#list productBookInfo.goFlightList as goflight>

	   <div class="airInfoContent">
            <label class="blueBg"><#if goflight_index == 0 >去<#else>去程-中转</#if></label>
            <div class="dayNumber">
                <strong class="days">第${(goflight.departDayIndex)!}天</strong>
            </div>
            <p class="airPFonts">${(goflight.airLineName)!}${(goflight.flightNum)!} / ${(goflight.cabin)!} / ${(goflight.planeCode)!}</p>
            <div class="timePlaceContent clearfix">
                <div class="timePlaceLeft">
                    <div class="placeBlueFonts">${(goflight.fromCity)!}</div>
                    <div class="timeFonts">${(goflight.departTime)!}</div>
                    <div class="airFonts">${(goflight.departAirPort)!}</div>
                </div>
                <!--<div class="totalTimeContent">
                    <label>
                        <i class="dottedIcon leftDotted"></i>
                        <span class="dashLine"></span>
                    </label>
                    <span class="totalTimeFonts">${(goflight.flightTimeCost)?replace("h","时")?replace("m","分")}</span>
                    <label>
                        <span class="dashLine"></span>
                        <i class="dottedIcon rightDotted"></i>
                    </label>
                </div>-->
                <div class="timePlaceRight">
                    <div class="placeBlueFonts">${(goflight.toCity)!}</div>
                    <div class="timeFonts">${(goflight.arriveTime)!}<#if (goflight.addDays)?? && (goflight.addDays) gt 0><i class="addDayName">+${(goflight.addDays)!}天</i></#if></div>
                    <div class="airFonts">${(goflight.arriveAirPort)!}</div>
                </div>
            </div>
            <#if (goflight.stop)?? && (goflight.stop) != ''>
            <div class="duringStop">经停: <em>${(goflight.stop)!''}</em></div>
            </#if>
        </div>
    </#list>
    <!--去程航班信息结束-->
</#if>

	<!--中间程航班信息开始-->
	<#if (productBookInfo.midlFlightList)?? && (productBookInfo.midlFlightList)?size gt 0>
		<#list productBookInfo.midlFlightList as midflight>


		<div class="airInfoContent">
            <div class="dayNumber">
                <strong class="days">第${(midflight.departDayIndex)!}天</strong>
                <#if (midflight.airRangeIndex)?ends_with("1")>
                   <span class="orangeBg">第${((midflight.airRangeIndex)?substring(0,1))?eval + 1}程</span>
                <#else>
                   <span class="orangeBg">第${((midflight.airRangeIndex)?substring(0,1))?eval + 1}程中转</span>
                </#if>
            </div>
            <p class="airPFonts">${(midflight.airLineName)!}${(midflight.flightNum)!} / ${(midflight.cabin)!} / ${(midflight.planeCode)!}</p>
            <div class="timePlaceContent clearfix">
                <div class="timePlaceLeft">
                    <div class="placeBlueFonts">${(midflight.fromCity)!}</div>
                    <div class="timeFonts">${(midflight.departTime)!}</div>
                    <div class="airFonts">${(midflight.departAirPort)!}</div>
                </div>
                <!--<div class="totalTimeContent">
                    <label>
                        <i class="dottedIcon leftDotted"></i>
                        <span class="dashLine"></span>
                    </label>
                    <span class="totalTimeFonts">${(midflight.flightTimeCost)?replace("h","时")?replace("m","分")}</span>
                    <label>
                        <span class="dashLine"></span>
                        <i class="dottedIcon rightDotted"></i>
                    </label>
                </div>-->
                <div class="timePlaceRight">
                    <div class="placeBlueFonts">${(midflight.toCity)!}</div>
                    <div class="timeFonts">${(midflight.arriveTime)!}<#if (midflight.addDays)?? && (midflight.addDays) gt 0><i class="addDayName">+${(midflight.addDays)!}天</i></#if></div>
                    <div class="airFonts">${(midflight.arriveAirPort)!}</div>
                </div>
            </div>
            <#if (goflight.stop)?? && (goflight.stop) != ''>
            <div class="duringStop">经停: <em>${(goflight.stop)!''}</em></div>
            </#if>
        </div>
        </#list>
        <!--返程航班信息结束-->
    </#if>



	<!--返程航班信息开始-->
	<#if (productBookInfo.backFlightList)?? && (productBookInfo.backFlightList)?size gt 0>
		<#list productBookInfo.backFlightList as backflight>


		<div class="airInfoContent">
            <label class="blueBg"><#if backflight_index == 0>返<#else>返程中转</#if></label>
            <div class="dayNumber">
                <strong class="days">第${(backflight.departDayIndex)!}天</strong>
            </div>
            <p class="airPFonts">${(backflight.airLineName)!}${(backflight.flightNum)!} / ${(backflight.cabin)!} / ${(backflight.planeCode)!}</p>
            <div class="timePlaceContent clearfix">
                <div class="timePlaceLeft">
                    <div class="placeBlueFonts">${(backflight.fromCity)!}</div>
                    <div class="timeFonts">${(backflight.departTime)!}</div>
                    <div class="airFonts">${(backflight.departAirPort)!}</div>
                </div>
                <!--<div class="totalTimeContent">
                    <label>
                        <i class="dottedIcon leftDotted"></i>
                        <span class="dashLine"></span>
                    </label>
                    <span class="totalTimeFonts">${(backflight.flightTimeCost)?replace("h","时")?replace("m","分")}</span>
                    <label>
                        <span class="dashLine"></span>
                        <i class="dottedIcon rightDotted"></i>
                    </label>
                </div>-->
                <div class="timePlaceRight">
                    <div class="placeBlueFonts">${(backflight.toCity)!}</div>
                    <div class="timeFonts">
                        ${(backflight.arriveTime)!}<#if (backflight.addDays)?? && (backflight.addDays) gt 0><i class="addDayName">+${(backflight.addDays)!}天</i></#if>
                    </div>
                    <div class="airFonts">${(backflight.arriveAirPort)!}</div>
                </div>
            </div>
            <#if (goflight.stop)?? && (goflight.stop) != ''>
            <div class="duringStop">经停: <em>${(goflight.stop)!''}</em></div>
            </#if>
        </div>
        </#list>
        <!--返程航班信息结束-->
	</#if>
    </section>
    

    <!-- 机票信息结束 -->
</#macro>