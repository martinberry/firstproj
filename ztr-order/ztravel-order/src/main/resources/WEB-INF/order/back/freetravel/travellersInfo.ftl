                <div class="moduleContent">
                    <div class="titleContent clearfix">
                        <h3 class="titleFonts blue2fb0c4">旅客信息</h3>
                        <div class="titleRight">
                            <span class="grayBgFonts" id="domesticTravellerLable">中</span>
                            <span class="grayBgFonts" id="internationalTravellerLable">外</span>
                        </div>
                    </div>
                    <table class="grayThead ChinaForeignCountries" id="domesticTravellerTable" style="display: none;">
                        <colgroup>
                            <col width="150">
                            <col width="150">
                            <col width="160">
                            <col width="200">
                            <col width="220">
                            <col width="150">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>旅客中文名</th>
                            <th>拼音/英文名</th>
                            <th>乘客类型</th>
                            <th>证件类型</th>
                            <th>证件号</th>
                            <th>出生年月</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (orderDetail.travellers)??>
                        <#list orderDetail.travellers as traveller>
                        <tr>
                            <td class="fontSize16">${(traveller.nameCn)!''}</td>
                            <td class="fontSize16">${(traveller.nameEn)!''}</td>
                            <td class="fontSize16">${(traveller.type)!''}</td>
                            <td class="fontSize16">${(traveller.credentialType)!''}</td>
                            <td>${(traveller.credentialNo)!''}</td>
                            <td>${(traveller.birthday)!''}</td>
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                    <table class="grayThead ChinaForeignCountries" id="internationalTravellerTable" style="display: none;">
                        <colgroup>
                            <col width="150">
                            <col width="150">
                            <col width="120">
                            <col width="55">
                            <col width="85">
                            <col width="100">
                            <col width="160">
                            <col width="100">
                            <col width="100">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>旅客中文名</th>
                            <th>拼音/英文名</th>
                            <th>国籍</th>
                            <th>性别</th>
                            <th>乘客类型</th>
                            <th>证件类型</th>
                            <th>证件号</th>
                            <th>证件有效期</th>
                            <th>出生年月</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (orderDetail.travellers)??>
                        <#list orderDetail.travellers as traveller>
                        <tr>
                            <td class="fontSize16">${(traveller.nameCn)!''}</td>
                            <td class="fontSize16">${(traveller.nameEn)!''}</td>
                            <td class="fontSize16">${(traveller.nationality)!''}</td>
                            <td class="fontSize16">${(traveller.gender)!''}</td>
                            <td class="fontSize16">${(traveller.type)!''}</td>
                            <td class="fontSize16">${(traveller.credentialType)!''}</td>
                            <td>${(traveller.credentialNo)!''}</td>
                            <td>${(traveller.credentialExpireDate)!''}</td>
                            <td>${(traveller.birthday)!''}</td>
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>