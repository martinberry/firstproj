<#macro hotelsInfo>

            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>酒店信息</span>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonStyle changeCommonStyle">
                <#list (orderDetail.product.hotelList)! as hotel>
                    <table class="hotelInfoTab">
                        <colgroup>
                            <col width="170">
                            <col width="170">
                            <col width="470">
                            <col width="230">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>
                                <em class="solidLine hotelFirstLine"></em>
                                <em class="solidLine hotelSecondLine"></em>
                                <span class="blueBgFonts">入住</span>
                                <div class="blueFontsWeight">${(hotel.checkInDate)!}</div>
                            </td>
                            <td>
                                <span class="orangeBgFonts">离店</span>
                                <div class="blueFontsWeight">${(hotel.checkOutDate)!}</div>
                            </td>
                            <td class="textAlignLeft">${(hotel.hotelName)!}</td>
                            <td>
                                <span class="commonIcon roomsIcon"></span>
                                <div>${(hotel.roomType)!}</div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    </#list>
                    <div class="bedType">
                        <span class="color9f9f9f">偏好的酒店床型:  </span>
                        <label class="editBedType" <#if orderDetail.canChange==true>id="edit-bedType"</#if>>
                            <#if orderDetail.bedPrefer??>
                            <span><#if orderDetail.bedPrefer=="big">优先大床房<#elseif orderDetail.bedPrefer=="double">优先双床房</#if></span>
                            </#if>
                            <span class="commonIcon editIcon"></span>
                        </label>
                        <span class="editBedTypeContent">
                            <span class="radioContent">
                                <label class="radio" val="big">
                                    <span class="commonIcon radioIcon"></span>
                                    <span class="genderSelect">优先大床房</span>
                                </label>
                                <label class="radio" val="double">
                                    <span class="commonIcon radioIcon"></span>
                                    <span class="genderSelect">优先双床房</span>
                                </label>
                            </span>
                            <span class="sureBtn">确定</span>
                            <span class="cancelBtn">取消</span>
                            <#if (orderDetail.isBedTips)?? && (orderDetail.isBedTips)?c == "true">
                            	<div id="bedTips" style="display:none;" class="hotelbed-tip">由于日本酒店的特殊政策，双床房价格需补差价。下单后，客服人员会联系您补拍差价。</div>
                            </#if>
                        </span>
                    </div>
                </div>
            </div>

</#macro>