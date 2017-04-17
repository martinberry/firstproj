<#macro airInfo>

    <section class="commonModule">
        <h2 class="commonTitleH2 haveIcon"><i class="planeIcon"></i>航班信息</h2>
        <#list (product.flight.main)! as flight>
        <div class="airInfoContent">
            <label <#if (flight.airRange)?index_of("去程")== -1 && (flight.airRange)?index_of("返程")== -1>class="orangeBg"<#else>class="blueBg"</#if>>${(flight.airRange)!}</label>
            <div class="dayNumber">
                <strong class="days">第${(flight.offsetDays)!}天</strong>
            </div>
            <p class="airPFonts">${(flight.airLine)!}${(flight.flightNo)!} / ${(flight.cabin)!} / ${(flight.flightModel)!}</p>
            <div class="timePlaceContent clearfix">
                <div class="timePlaceLeft">
                    <div class="placeBlueFonts">${(flight.fromCity)!}</div>
                    <div class="timeFonts">${(flight.departureTime)!}</div>
                    <div class="airFonts">${(flight.fromAirPort)!}</div>
                </div>
                <!--<div class="totalTimeContent">
                    <label>
                        <i class="dottedIcon leftDotted"></i>
                        <span class="dashLine"></span>
                    </label>
                    <span class="totalTimeFonts">
                    <#assign startHour = (flight.departureTime?substring(0,2))?number>
                    <#assign endHour = (flight.arrivalTime?substring(0,2))?number>
                    <#assign startMin = (flight.departureTime?substring(3))?number>
                    <#assign endMin = (flight.arrivalTime?substring(3))?number>
                    <#if startHour gt endHour && startMin gt endMin>
                        <#if (24+endHour-startHour-1) != 0 || flight.addDays != 0>${24+endHour-startHour-1+(flight.addDays-1)*24}时</#if><#if (60+endMin-startMin) != 0>${60+endMin-startMin}分</#if>
                    <#elseif startHour gt endHour && startMin lte endMin>
                        <#if (24+endHour-startHour) != 0 || flight.addDays != 0>${24+endHour-startHour+(flight.addDays-1)*24}时</#if><#if (endMin-startMin) != 0>${endMin-startMin}分</#if>
                    <#elseif startHour lte endHour && startMin gt endMin>
                        <#if (endHour-startHour-1) != 0 || flight.addDays != 0>${endHour-startHour-1+flight.addDays*24}时</#if><#if (60+endMin-startMin) != 0>${60+endMin-startMin}分</#if>
                    <#elseif startHour lte endHour && startMin lte endMin>
                        <#if (endHour-startHour) != 0 || flight.addDays != 0>${endHour-startHour+flight.addDays*24}时</#if><#if (endMin-startMin) != 0>${endMin-startMin}分</#if>
                    </#if>
                    </span>
                    <label>
                        <span class="dashLine"></span>
                        <i class="dottedIcon rightDotted"></i>
                    </label>
                </div>-->
                <div class="timePlaceRight">
                    <div class="placeBlueFonts">${(flight.toCity)!}</div>
                    <div class="timeFonts">${(flight.arrivalTime)!}<#if (flight.addDays)?? && (flight.addDays) gt 0><em class="addDayName">+${(flight.addDays)!}天</em></#if></div>
                    <div class="airFonts">${(flight.toAirPort)!}</div>
                </div>
            </div>
            <#if (flight.stop)?? && flight.stop != ''>
            <div class="duringStop">经停: <em>${(flight.stop)!}</em></div>
            </#if>
        </div>
        </#list>
        
    </section>
    
    
    

</#macro>