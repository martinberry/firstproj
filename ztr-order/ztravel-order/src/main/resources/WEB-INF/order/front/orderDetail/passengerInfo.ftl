<#macro passengerInfo>

            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>游客信息</span>
                    <label class="editTouristInfo" <#if orderDetail.canChange==true>id="edit-tourist"</#if>>
                        <span>(出行日15天之内不可更改)</span>
                        <span class="commonIcon editIcon"></span>
                    </label>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <input type="hidden" name="isDomestic" value="${(orderDetail.isDomestic)!'0'}">
                <div class="commonStyle">
                <#if orderDetail.isDomestic == "1">
                    <table class="commonLightBlueThead touristInfoTab">
                        <colgroup>
                            <col width="120">
                            <col width="150">
                            <col width="65">
                            <col width="80">
                            <col width="85">
                            <col width="190">
                            <col width="115">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>英文/拼音名</th>
                            <th>性别</th>
                            <th>乘客类型</th>
                            <th>证件类型</th>
                            <th>证件号</th>
                            <th>出生年月</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <#else>
                    <table class="commonLightBlueThead touristInfoTab">
                        <colgroup>
                            <col width="120">
                            <col width="150">
                            <col width="85">
                            <col width="65">
                            <col width="80">
                            <col width="85">
                            <col width="190">
                            <col width="115">
                            <col width="115">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>英文/拼音名</th>
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
                        </tbody>
                    </table>
                    </#if>
                    <div class="editTouristInfoContent">
                    <div class="editTouristInfo-tab"></div>

                        <div id="saveBtn" style="display:none;">
                        <table class="commonEditTab editTouristInfoTab">
                            <colgroup>
                                <col width="90">
                                <col width="100">
                                <col width="">
                            </colgroup>
                            <tbody>
                           <tr class="last">
                                <td></td>
                                <td>
                                    <a href="javascript:void(0);" class="commonBtn blueBtn">保 存</a>
                                    <a href="javascript:void(0);" class="cancelBtn">取消</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </div>
                   </div>
                </div>
            </div>

                    <div id="isDomestic" style="display:none;">
                        <table class="commonEditTab editTouristInfoTab">
                            <colgroup>
                                <col width="90">
                                <col width="100">
                                <col width="">
                            </colgroup>
                            <tbody>
                            <input type="hidden" name="passengerId" value="">
                            <tr class="first">
                                <td rowspan="6" class="numberFonts"></td>
                                <th>旅客姓名:</th>
                                <td>
                                    <input type="text" name="firstName" readonly="readonly" style="width: 110px;" placeholder="姓" value="" onclick="focus();select()">
                                    <input type="text" name="lastName" readonly="readonly" style="width: 110px;" placeholder="名" value="" onclick="focus();select()">
                                    <div class="verifyStyle name-error" style="display: none;"><i class="commonIcon errorIcon"></i>请按照所持有效证件上的姓名填写</div>
                                </td>
                            </tr>
                            <tr>
                                <th>英文/拼音名:</th>
                                <td>
                                    <input type="text" name="firstNameEn"  style="width: 110px;" placeholder="First Name" value="" onclick="focus();select()">
                                    <input type="text" name="lastNameEn"  style="width: 110px;" placeholder="Last Name" value="" onclick="focus();select()">
                                    <span class="inputTip">为确保出行，请仔细核对拼音名</span>
                                    <div class="verifyStyle nameEn-error" style="display: none;"><i class="commonIcon errorIcon"></i>请按照所持有效证件上的英文/拼音名填写</div>
                                </td>
                            </tr>
                            <tr>
                                <th>旅客类型:</th>
                                <td class="passengerType"></td>
                            </tr>
                            <tr>
                                <th>证件类型:</th>
                                <td>
                                    <div class="dropdown" style="width: 95px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                            <span class="activeFonts credentialType" data-val="">身份证</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li data-val="IDCARD">
                                            <a href="javascript:void(0);">身份证</a></li>
                                            <li data-val="PASSPORT">
                                            <a href="javascript:void(0);">护照</a></li>
                                            <li data-val="GANGAOPASS">
                                            <a href="javascript:void(0);">港澳通行证</a></li>
                                        </ul>
                                    </div>
                                    <input type="text" name="credentialNum" style="width: 200px;" placeholder="证件号" value="" onclick="focus();select()">
                                    <div class="verifyStyle credentialNum-error" style="display: none;"><i class="commonIcon errorIcon"></i>格式填写有误，请重新输入</div>
                                </td>
                            </tr>
                            <tr>
                                <th>出生日期:</th>
                                <td>
                                    <input type="text" name="birthday" style="width: 200px;" placeholder="yyyy-mm-dd" value="" class="datepicker hasIcon default" readonly>
                                    <div class="verifyStyle birthday-error" style="display: none;"><i class="commonIcon errorIcon"></i>出生日期必填</div>
                                </td>
                            </tr>
                            <tr class="last">
                                <th>旅客性别:</th>
                                <td>
                                    <span class="radioContent gender">
                                        <label class="radio" data-val="MALE">
                                            <span class="commonIcon radioIcon"></span>
                                            <span class="genderSelect">男</span>
                                        </label>
                                        <label class="radio" data-val="FEMALE">
                                            <span class="commonIcon radioIcon"></span>
                                            <span class="genderSelect">女</span>
                                        </label>
                                    </span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </div>

                        <div id="isNotDomestic" style="display:none;">
                        <table class="commonEditTab editTouristInfoTab">
                            <colgroup>
                                <col width="90">
                                <col width="100">
                                <col width="">
                            </colgroup>
                            <tbody>
                            <input type="hidden" name="passengerId" value="">
                            <tr class="first">
                                <td rowspan="9" class="numberFonts"></td>
                                <th>旅客姓名:</th>
                                <td>
                                    <input type="text" name="firstName" readonly="readonly" style="width: 110px;" placeholder="姓" value="" onclick="focus();select()">
                                    <input type="text" name="lastName" readonly="readonly" style="width: 110px;" placeholder="名" value="" onclick="focus();select()">
                                    <div class="verifyStyle name-error" style="display: none;"><i class="commonIcon errorIcon"></i>请按照所持有效证件上的姓名填写</div>
                                </td>
                            </tr>
                            <tr>
                                <th>英文/拼音名:</th>
                                <td>
                                    <input type="text" name="firstNameEn"  style="width: 110px;" placeholder="First Name" value="" onclick="focus();select()">
                                    <input type="text" name="lastNameEn"  style="width: 110px;" placeholder="Last Name" value="" onclick="focus();select()">
                                    <span class="inputTip">为确保出行，请仔细核对拼音名</span>
                                    <div class="verifyStyle nameEn-error" style="display: none;"><i class="commonIcon errorIcon"></i>请按照所持有效证件上的英文/拼音名填写</div>
                                </td>
                            </tr>
                            <tr>
                                <th>旅客类型:</th>
                                <td class="passengerType"></td>
                            </tr>
                            <tr>
                                <th>证件类型:</th>
                                <td>
                                    <div class="dropdown" style="width: 95px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                            <span class="activeFonts credentialType" data-val="">护照</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li data-val="PASSPORT">
                                            <a href="javascript:void(0);">护照</a></li>
                                            <li data-val="GANGAOPASS">
                                            <a href="javascript:void(0);">港澳通行证</a></li>
                                        </ul>
                                    </div>
                                    <input type="text" name="credentialNum" style="width: 200px;" placeholder="证件号" value="">
                                    <div class="verifyStyle credentialNum-error" style="display: none;"><i class="commonIcon errorIcon"></i>格式填写有误，请重新输入</div>
                                </td>
                            </tr>
                            <tr>
                                <th>证件有效期:</th>
                                <td>
                                    <input type="text" name="deadLine" style="width: 200px;" placeholder="yyyy-mm-dd" value="" class="datepicker hasIcon default" readonly>
                                    <div class="verifyStyle deadLine-error" style="display: none;"><i class="commonIcon errorIcon"></i>证件有效期必填</div>
                                </td>
                            </tr>
                            <tr>
                                <th>旅客国籍:</th>
                                <td>
                                    <input type="text" class="nationality" name="country" style="width: 200px;" placeholder="中文或英文" value="" onclick="focus();select()">
                                    <input type="hidden" class="nationalityDropList">
                                    <div class="verifyStyle country-error" style="display: none;"><i class="commonIcon errorIcon"></i>请输入正确国籍</div>
                                </td>
                            </tr>
                            <tr>
                                <th>出生日期:</th>
                                <td>
                                    <input type="text" name="birthday" style="width: 200px;" placeholder="yyyy-mm-dd" value="" class="datepicker hasIcon default" readonly>
                                    <div class="verifyStyle birthday-error" style="display: none;"><i class="commonIcon errorIcon"></i>出生日期必填</div>
                                </td>
                            </tr>
                            <tr class="last">
                                <th>旅客性别:</th>
                                <td>
                                    <span class="radioContent gender">
                                        <label class="radio" data-val="MALE">
                                            <span class="commonIcon radioIcon"></span>
                                            <span class="genderSelect">男</span>
                                        </label>
                                        <label class="radio" data-val="FEMALE">
                                            <span class="commonIcon radioIcon"></span>
                                            <span class="genderSelect">女</span>
                                        </label>
                                    </span>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                        </div>




</#macro>