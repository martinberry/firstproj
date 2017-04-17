        <!-- 机票 S -->
        <#if (productBookInfo.isContainFlight)?c == "true">
        	<div class="order-conBlock">
                            <table class="order-conBlockTab order-ticketTable">
                                <colgroup>
                                    <col width="70"/>
                                    <col width="130"/>
                                    <col width="180"/>
                                    <col width="150"/>
                                    <col width="180"/>
                                    <col width="115"/>
                                </colgroup>
                                <tbody>
                               <!-- 去程航班信息start-->
                                <#if (productBookInfo.goFlightList)?? && (productBookInfo.goFlightList)?size gt 0>
                                	<#list productBookInfo.goFlightList as goflight>
                                	<tr>
                                	<#if goflight_index == 0 >
	                                     <td rowspan="${(productBookInfo.goFlightList)?size + (productBookInfo.midlFlightList)?size + (productBookInfo.backFlightList)?size}" class="leftTd">
	                                        <div><i class="ticket"></i></div>
	                                        <div>机票</div>
	                                    </td>
                                	</#if>
                                    <td>
                                        <div class="adjustSpace">
                                        <#if goflight_index == 0 >
                                            <i class="colorBgIcon bg-green">去程</i>
                                         <#else>
                                          	<i class="colorBgIcon bg-green">去程中转</i>
                                         </#if>
                                        </div>
                                        <div class="fc11b9b7"><b>${(goflight.departDate)!}</b></div>
                                    </td>

                                   <td>
                                        <div><span class="timeFonts">${(goflight.departTime)!}</span></div>
                                        <div>${(goflight.departAirPort)!}</div>
                                    </td>

                                    <td>
                                    	 <div class="adjustSpace">
                                        	<#if (goflight.stop)?? && (goflight.stop) != ''>
                        						<i class="stoppingPoint stop green-border-tooltip" title="${(goflight.stop)!}">经停</i>
                        					<#else>
                                        		<i class="planeIcon"></i>
                        					</#if>
                                        </div>
                                        <div>
                                            <span class="limitFontsWidth">${(goflight.airLineName)!}&nbsp;<em class="fc484848">${(goflight.flightNum)!}</em>&nbsp;<em class="fc484848">${(goflight.planeCode)!}</em></span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="timeFonts markDays"><span class="timeFonts">${(goflight.arriveTime)!}</span><#if (goflight.addDays)?? && (goflight.addDays) gt 0><i>+${(goflight.addDays)!}天</i></#if></div>
                                        <div>${(goflight.arriveAirPort)!}</div>
                                    </td>

                                    <td>
                                        <div class="adjustSpace"><i class="clockIcon"></i></div>
                                        <div><span class="fc11b9b7"><b>${(goflight.flightTimeCost)!}</b></span></div>
                                    </td>
                                    </tr>
                                </#list>
                                </#if>
                                <!-- 去程航班信息end-->

								<!--中间程航班信息start-->
                                <#if (productBookInfo.midlFlightList)?? && (productBookInfo.midlFlightList)?size gt 0>
                                	<#list productBookInfo.midlFlightList as midflight>
                                	<tr>
                                	<#if midflight_index == 0 && (productBookInfo.goFlightList)?size == 0 >
	                                     <td rowspan="${(productBookInfo.midlFlightList)?size + (productBookInfo.backFlightList)?size}" class="leftTd">
	                                        <div><i class="ticket"></i></div>
	                                        <div>机票</div>
	                                    </td>
                                	</#if>
                                    <td>
                                        <div class="adjustSpace">
                                        <#if (midflight.airRangeIndex)?ends_with("1")>
                                            <i class="colorBgIcon bg-darkGreen">第${((midflight.airRangeIndex)?substring(0,1))?eval + 1}程</i>
                                            <#else>
                                             <i class="colorBgIcon bg-darkGreen">第${((midflight.airRangeIndex)?substring(0,1))?eval + 1}程中转</i>
                                         </#if>
                                        </div>
                                        <div class="fc11b9b7"><b>${(midflight.departDate)!}</b></div>
                                    </td>

                                   <td>
                                        <div><span class="timeFonts">${(midflight.departTime)!}</span></div>
                                        <div>${(midflight.departAirPort)!}</div>
                                    </td>

                                    <td>
                                    	<div class="adjustSpace">
                                        	<#if (midflight.stop)?? && (midflight.stop) != ''>
                        						<i class="stoppingPoint stop green-border-tooltip" title="${(midflight.stop)!}">经停</i>
                        					<#else>
                                        		<i class="planeIcon"></i>
                        					</#if>
		                                   </div>
                                        <div>
                                            <span class="limitFontsWidth">${(midflight.airLineName)!}&nbsp;<em class="fc484848">${(midflight.flightNum)!}</em>&nbsp;<em class="fc484848">${(midflight.planeCode)!}</em></span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="timeFonts markDays"><span class="timeFonts">${(midflight.arriveTime)!}</span><#if (midflight.addDays)?? && (midflight.addDays) gt 0><i>+${(midflight.addDays)!}天</i></#if></div>
                                        <div>${(midflight.arriveAirPort)!}</div>
                                    </td>

                                    <td>
                                        <div class="adjustSpace"><i class="clockIcon"></i></div>
                                        <div><span class="fc11b9b7"><b>${(midflight.flightTimeCost)!}</b></span></div>
                                    </td>
                                    </tr>
                                </#list>
                                </#if>
								<!--中间程航班信息end-->

								<!--返程航班信息start-->
                                <#if (productBookInfo.backFlightList)?? && (productBookInfo.backFlightList)?size gt 0>
                                	<#list productBookInfo.backFlightList as backflight>
                                	<tr>
                                	<#if backflight_index == 0 && (productBookInfo.goFlightList)?size == 0 && (productBookInfo.midlFlightList)?size == 0>
	                                     <td rowspan="${(productBookInfo.backFlightList)?size}" class="leftTd">
	                                        <div><i class="ticket"></i></div>
	                                        <div>机票</div>
	                                    </td>
                                	</#if>
                                    <td>
                                        <div class="adjustSpace">
                                        	<#if backflight_index == 0>
                                            	<i class="colorBgIcon bg-blue">返程</i>
                                            <#else>
                                             	<i class="colorBgIcon bg-blue">返程中转</i>
                                            </#if>
                                        </div>
                                        <div class="fc11b9b7"><b>${(backflight.departDate)!}</b></div>
                                    </td>

                                   <td>
                                        <div><span class="timeFonts">${(backflight.departTime)!}</span></div>
                                        <div>${(backflight.departAirPort)!}</div>
                                    </td>

                                    <td>
                                     <div class="adjustSpace">
                                    	<#if (backflight.stop)?? && (backflight.stop) != ''>
                        						<i class="stoppingPoint stop green-border-tooltip" title="${(backflight.stop)!}">经停</i>
                        					<#else>
                                        		<i class="planeIcon"></i>
                        				</#if>
                        				</div>
                                        <div>
                                            <span class="limitFontsWidth">${(backflight.airLineName)!}&nbsp;<em class="fc484848">${(backflight.flightNum)!}</em>&nbsp;<em class="fc484848">${(backflight.planeCode)!}</em></span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="timeFonts markDays"><span class="timeFonts">${(backflight.arriveTime)!}</span><#if (backflight.addDays)?? && (backflight.addDays) gt 0><i>+${(backflight.addDays)!}天</i></#if></div>
                                        <div>${(backflight.arriveAirPort)!}</div>
                                    </td>

                                    <td>
                                        <div class="adjustSpace"><i class="clockIcon"></i></div>
                                        <div><span class="fc11b9b7"><b>${(backflight.flightTimeCost)!}</b></span></div>
                                    </td>
                                    </tr>
                                </#list>
                                </#if>
                                <!--返程航班信息start-->

                                </tbody>
                            </table>
                        </div>
                        <!-- 机票 E -->
        </#if>
