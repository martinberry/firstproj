<input type="hidden" id="productId" value="${(orderDetail.product.productId)!}">
<div class="commonOrderModel commonOPOrderModel">
    <div class="commonSmallTitleModel blueFonts">
        <span class="leftTitleFonts">机票信息</span>
        <label class="commonEditBtn flightEditBtn" <#if (readOnly)!false > style="display: none;" </#if>>
            <span class="commonIcon editIcon"></span>
        </label>
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
                <#else>
                <span class="flyToIcon"></span>
                </#if>
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
                    <span class="pubFlights_MU verticalAlignMiddle"></span>
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
                <#else>
                <span class="flyToIcon"></span>
                </#if>
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
                <#else>
                <span class="flyToIcon"></span>
                </#if>
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

<!--  添加机票弹窗内容  -->
    <div class="modal hasHeaderBtn" id="add-flight-popup">
        <div class="modal-dialog" style="width: 1040px;height:687px;">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-oper">
                        <button class="commonButton red-fe6869Button flight save">保存</button>
                        <button class="commonButton gray-bbbButton flight cancel">取消</button>
                    </div>
                    <h4 class="modal-title">机票信息</h4>
                </div>
                <div class="modal-body" style="height:635px;overflow-y: auto;">
                    <div class="section-cont-wrapper">
                        <section class="flight-form-module">
                            <div class="flight-info edit">
                                <div class="table-top-block clearfix">
                                    <ul class="table-tab">
                                        <li class="">
                                            <span>上海出发</span>
                                        </li>
                                    </ul>
                                </div>
                                <section class="go-air-range-block">
                                    <div class="table-top-title">
                                        <span class="go-flight title-flag">
                                            <span class="go-flight-icon"></span>
                                            <span>去程</span>
                                        </span>
                                        <span class="add-oper add-transfer go">
                                            <span class="plus-icon"></span>
                                            <span>添加中转</span>
                                        </span>
                                    </div>
                                </section>
                                <section class="middle-air-range-container">
                                    <div class="long-split-line"></div>
                                    <section class="middle-air-range-block clearfix">
                                        <div class="table-top-title">
                                            <span class="middle-flight title-flag">
                                                <span class="middle-flight-icon"></span>
                                                <span class="flight-num">中间程</span>
                                            </span>
                                            <span class="add-oper add-middle-flight">
                                                <span class="plus-icon"></span>
                                                <span>添加航程</span>
                                            </span>
                                            <span class="add-oper add-transfer" style="display: none;">
                                                <span class="plus-icon"></span>
                                                <span>添加中转</span>
                                            </span>
                                        </div>
                                    </section>
                                </section>
                                <section class="back-air-range-block">
                                    <div class="long-split-line"></div>
                                    <div class="table-top-title">
                                        <span class="back-flight title-flag">
                                            <span class="back-flight-icon"></span>
                                            <span>返程</span>
                                        </span>
                                        <span class="add-oper add-back-flight">
                                            <span class="plus-icon"></span>
                                            <span>添加航程</span>
                                        </span>
                                        <span class="add-oper add-transfer" style="display: none;">
                                            <span class="plus-icon"></span>
                                            <span>添加中转</span>
                                        </span>
                                    </div>
                                </section>
                                <div class="long-split-line"></div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>

<!--  航程table template  -->
    <script type="text/html" id="flight_form_table_template">
        <table class="flight-form-table">
            <colgroup>
                <col width="80"/>
                <col width="79"/>
                <col width="80"/>
                <col width="79"/>
                <col width="80"/>
                <col width="79"/>
                <col width="80"/>
                <col width="85"/>
                <col width="80"/>
                <col width="85"/>
                <col width="90"/>
                <col width="85"/>
                <col width="90"/>
                <col width="85"/>
            </colgroup>
            <tbody>
                <tr>
                    <th><span class="red-star">*</span>第几天</th>
                    <td><input type="text" class="offsetDays" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>航司名称</th>
                    <td><input type="text" class="airLine" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>航班号</th>
                    <td><input type="text" class="flightNo" style="width:100px;" data-cv="required"/></td>
                    <th>舱位</th>
                    <td><input type="text" class="cabin" style="width:80px;" /></td>
                    <th><span class="red-star">*</span>机型</th>
                    <td><input type="text" class="flightModel" style="width:80px;" /></td>
                    <th>经停信息</th>
                    <td colspan="3"><input type="text" class="stop" style="width:200px;" placeholder="经停:北京三水机场/停留时间:1小时"/></td>
                </tr>
                <tr>
                    <th><span class="red-star">*</span>起飞机场</th>
                    <td><input type="text" class="fromAirPort" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>起飞时间</th>
                    <td><input type="text" class="departureTime" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>到达机场</th>
                    <td><input type="text" class="toAirPort" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>到达时间</th>
                    <td colspan="3">
                        <input type="text" class="arrivalTime" style="width:100px;" data-cv="required"/>
                        <div class="dropdown" style="width: 67px;top:1px;">
                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="true">
                                <span class="activeFonts">+0日</span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li onclick=setDropDownMenu(this,0,'+0日','.addDays')><a href="javascript:void(0);">+0日</a></li>
                                <li onclick=setDropDownMenu(this,1,'+1日','.addDays')><a href="javascript:void(0);">+1日</a></li>
                                <li onclick=setDropDownMenu(this,2,'+2日','.addDays')><a href="javascript:void(0);">+2日</a></li>
                                <li onclick=setDropDownMenu(this,3,'+3日','.addDays')><a href="javascript:void(0);">+3日</a></li>
                            </ul>
                            <input type="hidden" class="addDays" value='0'>
                        </div>
                    </td>
                    <th><span class="red-star">*</span>出发城市</th>
                    <td><input type="text" class="fromCity" style="width:80px;" /></td>
                    <th><span class="red-star">*</span>到达城市</th>
                    <td><input type="text" class="toCity" style="width:80px;" /></td>
                </tr>
            </tbody>
        </table>
    </script>

    <!--  第一个中间程内容模板  -->
    <script type="text/html" id="first_middle_flight_template_front">
        <section class="clearfix">
    </script>

    <script type="text/html" id="first_middle_flight_template_back">
        <div class="del-oper del-middle-flight">
                <span class="del-icon"></span>
                <span>删除中间程信息</span>
            </div>
        </section>
    </script>

    <!--  normal中间程内容模板  -->
    <script type="text/html" id="normal_middle_flight_template_front">
        <section class="middle-air-range-block clearfix">
            <div class="long-split-line"></div>
            <div class="table-top-title">
                <span class="middle-flight title-flag">
                    <span class="middle-flight-icon"></span>
                    <span class="flight-num"></span>
                </span>
                <span class="add-oper add-middle-flight" style="display:none;">
                    <span class="plus-icon"></span>
                    <span>添加航程</span>
                </span>
                <span class="add-oper add-transfer">
                    <span class="plus-icon"></span>
                    <span>添加中转</span>
                </span>
            </div>
            <section class="clearfix">
    </script>

    <script type="text/html" id="normal_middle_flight_template_back">
        <div class="del-oper del-middle-flight">
                    <span class="del-icon"></span>
                    <span>删除中间程信息</span>
                </div>
            </section>
        </section>
    </script>

    <!--  第一个返程内容模板  -->
    <script type="text/html" id="first_back_flight_template_front">
        <section class="clearfix">
    </script>

    <script type="text/html" id="first_back_flight_template_back">
        <div class="del-oper del-back-flight">
            <span class="del-icon"></span>
            <span>删除返程信息</span>
        </div>
    </script>

    <!--  中转信息内容模板  -->
    <script type="text/html" id="transfer_flight_template_front">
        <section class="transfer-flight-block clearfix">
            <div class="short-split-line"></div>
    </script>

    <script type="text/html" id="transfer_flight_template_back">
        <div class="del-oper del-transfer">
                <span class="del-icon"></span>
                <span>删除中转信息</span>
            </div>
        </section>
    </script>