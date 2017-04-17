            <div class="searchModel">
                <div class="searchContent">
                    <table class="searchTab">
                        <colgroup>
                            <col width="70">
                            <col width="275">
                            <col width="70">
                            <col width="295">
                            <col width="">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>出发地</th>
                            <td>
                                <div class="startOffPlaceModel">
                                    <div class="addIcon startOffPlace">
                                        <span class="commonIcon startOffIcon"></span>
                                        <input type="text" class="startOffInput" id="departure" value="${departPlace!''}" style="width: 255px;" readonly="readonly" />
                                        <span class="caret"></span>
                                    </div>
                                    <ul class="selectStartOffPlace">
                                        <#if departurePlaceList??>
                                        <#list departurePlaceList as place>
                                        <li <#if (departPlace!'')==(place!'')>class="active"</#if>>${place!''}</li>
                                        </#list>
                                        </#if>
                                    </ul>
                                </div>
                            </td>
                            <th>目的地</th>
                            <td>
                                <div class="destinationModel">
                                    <div class="addIcon destination">
                                        <span class="commonIcon destinationIcon"></span>
                                        <input type="text" class="destinationInput" id="destination" value="${defaultDestination!''}" style="width: 255px;" placeholder="目的地" readonly="readonly" />
                                        <input id="selectedDestLevel" type="hidden" value="1" />
                                    </div>
                                    <div class="selectDestination">
                                        <ul class="cityList ul-dest-first-level">
                                            <#if destinations??>
                                            <#list destinations as dest>
                                            <li class="destSelect-first-level">
                                                <span class="fl-destName">${(dest.destName)!''}</span>
                                                <span class="nav-tree-arrow"></span>
                                                <ul class="second-level">
                                                    <#if (dest.subDestinations)??>
                                                    <#list dest.subDestinations as secLevelDest>
                                                    <li class="sl-destName">${secLevelDest!''}</li>
                                                    </#list>
                                                    </#if>
                                                </ul>
                                            </li>
                                            </#list>
                                            </#if>
                                            <!--<li class="empty"></li> -->
                                        </ul>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="commonBtn orangeBtn" style="width:120px;" id="searchBtn">搜索</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>