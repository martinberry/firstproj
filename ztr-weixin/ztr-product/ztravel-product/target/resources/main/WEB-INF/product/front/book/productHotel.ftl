                       <!-- 酒店 S -->
                       <#if (productBookInfo.isContainHotel)?c == "true" && (productBookInfo.hotelList)?? && (productBookInfo.hotelList)?size gt 0>
                        <div class="order-conBlock">
                            <table class="order-conBlockTab order-hotelTable">
                                <colgroup>
                                    <col width="70"/>
                                    <col width="130"/>
                                    <col width="180"/>
                                    <col width="330"/>
                                    <col width="115"/>
                                </colgroup>
                                <tbody>
                                <#list productBookInfo.hotelList as hotel>
                                   <tr>
                                   <#if hotel_index == 0>
	                                   <td rowspan="${(productBookInfo.hotelList)?size + 1}" class="leftTd">
	                                        <div><i class="hotel"></i></div>
	                                        <div>酒店</div>
	                                    </td>
	                                </#if>
                                    <td>
                                        <div class="adjustSpace">
                                            <i class="colorBgIcon bg-blue">入住</i>
                                        </div>
                                        <div class="fc11b9b7"><b>${(hotel.checkInDate)!''}</b></div>
                                    </td>
                                    <td>
                                        <div class="adjustSpace">
                                            <i class="colorBgIcon bg-orange">离店</i>
                                        </div>
                                        <div class="fc11b9b7"><b>${(hotel.checkOutDate)!''}</b></div>
                                    </td>
                                    <td class="leftAlignTd">
                                        <span class="dark14Fonts haveleftBorder"><i></i>${(hotel.hotelName)!''}</span>
                                    </td>
                                    <td class="haveLeftBorder">
                                        <div class="adjustSpace"><i class="houseIcon"></i></div>
                                        <div class="dark12Fonts sub-haveleftBorder"><i></i>${(hotel.hotelType)!''}</div>
                                    </td>
                                    </tr>
                                </#list>
                                <tr>
                                    <td>
                                        <span class="fs14">偏好的酒店床型：</span>
                                    </td>
                                    <td colspan="3" class="leftAlignTd">
                                        <div class="radioContent bedPrefer">
                                            <label class="radio active" val="big">
                                                <span class="commonIcon radioIcon"></span>优先大床房
                                            </label>
                                            <label class="radio" val="double" >
                                                <span class="commonIcon radioIcon"></span>优先双床房
                                            </label>
                                        </div>
                                        <#if (productBookInfo.isBedTips)?? && (productBookInfo.isBedTips)?c == 'true'>
                                        	<div class="hotel-tip" style="display:none;">由于日本酒店的特殊政策，双床房价格需补差价。下单后，客服人员会联系您补拍差价。</div>
                                        </#if>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        </#if>
                       <!-- 酒店 E -->