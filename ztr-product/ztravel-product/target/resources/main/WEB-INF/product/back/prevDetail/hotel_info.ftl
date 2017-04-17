<#macro hotelInfo>

                <div class="commonModel">
                    <div id="hotelInfo"></div>
                    <div class="commonTitle noBorder"><i class="commonIcon hotelIcon"></i>酒店信息</div>
                    <#list (product.hotels)! as hotel>
                    <div class="hotelInfoModel clearfix">
                        <div class="leftHotelImg">
                          <div class="hotelTitleModel clearfix">
                            <h3 class="hotelTittle" title="${(hotel.name)!}">${(hotel.name)!}</h3>
                            <div class="nightsContent">
                                    <span class="commonIcon moonIcon"></span>
                                    <#if (hotel.checkinDaysStr)?? && hotel.checkinDaysStr =="全程">
                                    <span>
                                        <span class="blueFonts">${(hotel.checkinDaysStr)!}</span>
                                    </span>
                                    <#else>
                                    <span>
                                        第
                                        <span class="blueFonts">${(hotel.checkinDaysStr)!}</span>
                                        晚
                                    </span>
                                    </#if>
                                </div>
                            </div>
                            <div class="hotelBigImg">
                                <img <#if hotel.pictureIds[0]??>src="${mediaserver}imageservice?mediaImageId=${(hotel.pictureIds[0])!}"</#if>>
                            </div>
                            <ul class="hotelSmallImg clearfix">
                            <#list (hotel.pictureIds)! as pictureId>
                    	         <li <#if pictureId_index == 0>class="active"</#if> ><img src="${mediaserver}imageservice?mediaImageId=${pictureId!}"></li>
                            </#list>
                            </ul>
                        </div>
                        <div class="rightHotelTabs">
                            <ul class="hotel-info-tab clearfix">
                                 <li class="current">基本信息</li>
                                 <li>酒店设施</li>
                                 <li>入住须知</li>
                            </ul>
                            <div class="placeholder-bar"></div>
                            <div class="tab-cont-block">
                                <div class="scroll-cover-bar"></div>
                                <div style="width:355px;">
                                <div class="tab-cont listInfo">
                                <table class="hotelTabList">
                                    <colgroup>
                                        <col width="75">
                                        <col width="">
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>酒店星级:</th>
                                        <td>
                                            <div class="commonStarLevelIcon starLevelIcon starLevel-${(hotel.rate)!} allStar5">
                                                <span class="commonStarLevelIcon"></span>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>酒店类型:</th>
                                        <td>${(hotel.hoteltype)!}</td>
                                    </tr>
                                    <tr>
                                        <th>房间类型:</th>
                                        <td>${(hotel.roomType)!}</td>
                                    </tr>
                                    <tr>
                                        <th class="verticalAlignTop">酒店位置:</th>
                                        <td><pre>${(hotel.address)!}</pre>	</td>
                                    </tr>
                                    <tr class="last">
                                        <th class="verticalAlignTop">酒店电话:</th>
                                        <td>${(hotel.phone)!}</td>
                                    </tr>
                                    </tbody>
                                </table>
                                 <div class="fontsModelDetail">
                                    <h3 class="titleFonts">酒店特色:</h3>
                                    <pre>${(hotel.highLights)!}</pre>
                                </div>
                                <div class="fontsModelDetail">
                                    <h3 class="titleFonts">关于酒店:</h3>
                                    <pre>${(hotel.describe)!}</pre>
                                </div>
                                </div>
                                <div class="tab-cont hotel-facilities">
                                    <div class="fontsModelDetail">
                                        <h3 class="titleFonts" style="padding-top:10px;">房间设施:</h3>
                                        <pre>${(hotel.compFacilities)!}</pre>
                                    </div>
                                    <div class="fontsModelDetail">
                                        <h3 class="titleFonts">餐饮设施:</h3>
                                        <pre>${(hotel.cateringFacilities)!}</pre>
                                    </div>
                                    <div class="fontsModelDetail">
                                        <h3 class="titleFonts">网络设施:</h3>
                                        <pre>${(hotel.networkFacilities)!}</pre>
                                    </div>
                                    <div class="fontsModelDetail">
                                        <h3 class="titleFonts">酒店设施:</h3>
                                        <pre>${(hotel.activityFacilities)!}</pre>
                                    </div>
                                    <div class="fontsModelDetail">
                                        <h3 class="titleFonts">服务项目:</h3>
                                        <pre>${(hotel.serviceFacilities)!}</pre>
                                    </div>
                                </div>
                                <div class="tab-cont hotel-notes">
                                    <pre>${(hotel.notes)!}</pre>
                                </div>
                            </div>
                        </div>
                        <div class="placeholder-bar"></div>
                        </div>
                    </div>
                    </#list>
                    <div class="expand-hotel-block">
                        <span class="line"></span>
                        <span class="expand-btn">
                            <i class="arrow"></i>
                            <em>展开更多酒店信息</em>
                        </span>
                    </div>
                </div>


</#macro>