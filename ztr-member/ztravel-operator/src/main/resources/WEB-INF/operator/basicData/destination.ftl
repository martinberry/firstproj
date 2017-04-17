                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">目的地信息</h3>
                </div>
                <div class="selectClassify clearfix">
                    <div class="leftClassifyBtn">
                        <div class="uploadData">
                            <button class="commonButton blue-45c8dcButton">上传数据</button>
                            <form id="destForm" method="POST" enctype="multipart/form-data" action="${basepath}/operation/basicData/uploadDestination">
                                <input type="file" id="destInputer" name="destinationFile" />
                            </form>
                        </div>
                        <div>
                            <a href="javascript:void(0);" class="setDefault">设为默认显示</a>
                        </div>
                        <div class="defaultVal">
                            <label class="placeLabel">
                                <span class="fontsWidth" id="defaultDest">${(destinationInfo.defaultDestination)!''}</span>
                                <span class="closeIcon"></span>
                            </label>
                        </div>
                    </div>
                    <div class="rightClassifyInfo">
                        <div class="placeInfoContent">
                            <ul class="grayTitleFonts clearfix">
                                <li>一级</li>
                                <li>二级</li>
                                <li>三级</li>
                            </ul>
                            <div class="placeList">
                                <table class="placeTab">
                                    <colgroup>
                                        <col width="190">
                                        <col width="190">
                                        <col width="190">
                                    </colgroup>
                                    <tbody>
                                    <#if (destinationInfo.destinationList)??>
                                    <#list destinationInfo.destinationList as destination>
                                    <tr>
                                        <th>${(destination.continent)!''}</th>
                                        <th>${(destination.country)!''}</th>
                                        <td>${(destination.city)!''}</td>
                                    </tr>
                                    </#list>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>