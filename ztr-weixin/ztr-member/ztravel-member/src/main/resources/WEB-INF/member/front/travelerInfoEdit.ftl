  <script type="text/javascript" src="${basepath}/resources/javascripts/member/front/travelerInfo/edit.js"></script>

        <section class="rightContent addTravelerInfoModel edit" style="float:none;width:100%">
        	<form id="editTravelerForm">
	            <div>
	                <div class="titleContent clearfix">
	                    <b class="titleFonts">修改常用旅客</b>
	                </div>
	                <table class="travelerInfoTab">
	                    <colgroup>
	                        <col width="130">
	                        <col width="">
	                    </colgroup>
	                    <tbody>
	                   <th class="adjustpadding"><em>*</em>旅客姓名：</th>
                                    <td class="adjustpadding">
                                        <div class="guestContainer">
                                            <input type="hidden" id="firstEnNameHideEdit" value="${travelerInfo.firstNameCn!}">
                                            <input type="hidden" id="lastEnNameHideEdit" value="${travelerInfo.lastNameCn!}">
                                            <input type="text" style="width: 140px;" placeholder="First Name" id="firstNameEdit" maxLength="20"  name="firstNameCn" value="${travelerInfo.firstNameCn!}"/>
                                            <input type="text" style="width: 140px;" placeholder="Last Name" id="lastNameEdit" maxLength="20" name="lastNameCn" value="${travelerInfo.lastNameCn!}"/>
                                               <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='firstName_errorHintEdit' class="errorHint"><span>
					                            </div>
                                                 <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='lastName_errorHintEdit' class="errorHint"><span>
					                            </div>
					                                <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='repeateName_errorHintEdit' class="errorHint"><span>
					                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th><em>*</em>英文/拼音：</th>
                                       <td class="adjustpadding">
                                        <div class="guestContainer">
                                            <input type="text" style="width: 140px;" placeholder="First Name" id="firstNameEnEdit" maxLength="20" data-cv="required" name="firstEnName" value="${travelerInfo.firstEnName!}"/>
                                            <input type="text" style="width: 140px;" placeholder="Last Name" id="lastNameEnEdit" maxLength="20" data-cv="required" name="lastEnName" value="${travelerInfo.lastEnName!}"/>
                                               <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='firstNameEn_errorHintEdit' class="errorHint"><span>
					                            </div>
                                                 <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='lastNameEn_errorHintEdit' class="errorHint"><span>
					                            </div>
                                        </div>
                                    </td>
                                </tr>
	                    <tr>
	                        <th>手机号:</th>
	                        <td>
	                            <input type="text" style="width: 280px;"  name="phoneNum" placeholder="方便接收旅途通知讯息"  id ="phoneNumEdit"	 value="${travelerInfo.phoneNum!''}">
	                            <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span id='phoneNum_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>

	                    </tbody>
	                </table>
	            </div>
	            <div>
	                <div class="titleContent clearfix">
	                    <b class="titleFonts">证件信息</b>
	                </div>
	                <table class="travelerInfoTab idTypeInfoEdit">
	                    <colgroup>
	                      <col width="90">
                                    <col width="370">
                                    <col width="75">
                                    <col width="">
	                    </colgroup>
	                    <tbody>
	                    <#if travelerInfo.credentials??>
	                     <#list travelerInfo.credentials as credential>
	                     <#if credential_index==0>
	                    <tr class="defaultTr">
	                        <th><em>*</em>证件类型:</th>
	                        <td>
	                            <div class="dropdown id-type" style="width: 105px;">
	                             <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
	                             		<span class="activeFonts"><#if credential.type == 'IDCARD'>身份证</#if><#if credential.type == 'PASSPORT'>护照</#if><#if credential.type == 'GANGAOPASS'>港澳通行证</#if></span>
	                                    <span class="caret"></span>
	                             </a>
	                                <ul class="dropdown-menu" class="type">
	                                  <li class="active" data-val="${(credential.type)!}"><a href="javascript:void(0);" class="active" ><#if credential.type == 'IDCARD'>身份证</#if><#if credential.type == 'PASSPORT'>护照</#if><#if credential.type == 'GANGAOPASS'>港澳通行证</#if></a></li>
	                                </ul>
	                            </div>
	                            <input type="text" style="width: 250px;" class="number"  value="${credential.number!}">
	                        </td>
	                        <th>有效期:</th>
	                        <td>
	                            <input type="text" style="width: 110px;"   class="datepicker hasIcon default" placeholder="yy-mm-dd" value="${credential.deadLineDay!}" readonly="readonly">
	                            <span class="commonIcon addRoundnessIcon"></span>
	                              <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span name='credit_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>
	                    <#else>
	                     <tr class="addTrContent">
	                        <th><em>*</em>证件类型:</th>
	                        <td>
	                            <div class="dropdown id-type" style="width: 105px;">
	                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
	                                    <span class="activeFonts"><#if credential.type == 'IDCARD'>身份证</#if><#if credential.type == 'PASSPORT'>护照</#if><#if credential.type == 'GANGAOPASS'>港澳通行证</#if></span>
	                                    <span class="caret"></span>
	                                </a>
	                                       <ul class="dropdown-menu" class="type">
	                                    <li class="active" data-val="${(credential.type)!}"><a href="javascript:void(0);" class="active" ><#if credential.type == 'IDCARD'>身份证</#if><#if credential.type == 'PASSPORT'>护照</#if><#if credential.type == 'GANGAOPASS'>港澳通行证</#if></a></li>
	                                </ul>
	                            </div>
	                            <input type="text" style="width: 250px;" class="number"  value="${credential.number!}">
	                            <input type="hidden" id="travelerId" value="${travelerInfo.id!''}">
	                        </td>
	                        <th>有效期:</th>
	                        <td>
	                            <input type="text" style="width: 105px;" placeholder="yy-mm-dd" class="datepicker hasIcon default" value="${credential.deadLineDay!}" readonly="readonly" >
	                            <span class="commonIcon delRoundnessIcon"></span>
	                              <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span name='credit_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>
	                    </#if>
	                    </#list>
	                    <#else>
	                    	<tr class="defaultTr">
	                        <th><em>*</em>证件类型:</th>
	                        <td>
	                            <div class="dropdown id-type" style="width: 105px;">
	                             <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
	                             		<span class="activeFonts"><#if credential.type == 'IDCARD'>身份证</#if><#if credential.type == 'PASSPORT'>护照</#if><#if credential.type == 'GANGAOPASS'>港澳通行证</#if></span>
	                                    <span class="caret"></span>
	                             </a>
	                                    <ul class="dropdown-menu" class="type">
	                                    <li class="active" data-val="${(credential.type)!}"><a href="javascript:void(0);" class="active" ><#if credential.type == 'IDCARD'>身份证</#if><#if credential.type == 'PASSPORT'>护照</#if><#if credential.type == 'GANGAOPASS'>港澳通行证</#if></a></li>
	                                </ul>
	                            </div>
	                            <input type="text" style="width: 250px;" class="number"   value="${credential.number!}">
	                        </td>
	                        <th>有效期:</th>
	                        <td>
	                            <input type="text" style="width: 105px;" placeholder="yy-mm-dd" class="datepicker hasIcon default" value="${credential.deadLineDay!}" readonly="readonly" >
	                            <span class="commonIcon addRoundnessIcon"></span>
	                              <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span name='credit_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>
	                    </#if>
	                    <tr class="addTrContent">
	                        <th><em>*</em>证件类型:</th>
	                        <td>
	                            <div class="dropdown id-type" style="width: 105px;">
	                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
	                                    <span class="activeFonts"></span>
	                                    <span class="caret"></span>
	                                </a>
	                                <ul class="dropdown-menu" class="type">
	                                    <li data-val="IDCARD"><a href="javascript:void(0);">身份证</a></li>
	                                    <li data-val="PASSPORT"><a href="javascript:void(0);">护照</a></li>
	                                    <li data-val="GANGAOPASS"><a href="javascript:void(0);">港澳通行证</a></li>
	                                </ul>
	                            </div>
	                            <input type="text" style="width: 250px;" class="number"  >
	                            <input type="hidden" id="travelerId" value="${travelerInfo.id!''}">
	                        </td>
	                        <th>有效期:</th>
	                        <td>
	                            <input type="text" style="width: 105px;"  class="datepicker hasIcon default" value="2020-01-01" readonly="readonly" disabled="disabled">
	                            <span class="commonIcon delRoundnessIcon"></span>
	                              <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span name='credit_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>
	                    </tbody>
	                </table>
	            </div>
	            <div>
	                <div class="titleContent clearfix">
	                    <b class="titleFonts">详细信息</b>
	                </div>
	                <table class="travelerInfoTab">
	                    <colgroup>
	                        <col width="110">
	                        <col width="">
	                    </colgroup>
	                    <tbody>
	                    <tr class="defaultTr">
	                        <th>性别:</th>
	                        <td>
	                            <div class="radioContent gender">
	                                <label data-val="MALE" class="radio  <#if (travelerInfo.gender)=='MALE' || (travelerInfo.gender)=='male'>active</#if>">
	                                    <span class="commonIcon radioIcon"></span>男
	                                </label>
	                                <label data-val="FEMALE" class="radio  <#if (travelerInfo.gender) == 'FEMALE' || (travelerInfo.gender)=='female'>active</#if>">
	                                    <span class="commonIcon radioIcon"></span>女
	                                </label>
	                            </div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th>旅客类型:</th>
	                        <td>
	                            <div class="radioContent travelType">
	                                <label data-val="ADULT" class="radio <#if travelerInfo.travelType == 'ADULT'>active</#if>">
	                                    <span class="commonIcon radioIcon"></span>成人
	                                </label>
	                                <label data-val="CHILD" class="radio  <#if travelerInfo.travelType == 'CHILD'>active</#if>">
	                                    <span class="commonIcon radioIcon"></span>儿童
	                                </label>
	                            </div>
	                        </td>
	                    </tr>
	                      <tr>
	                        <th>国籍: </th>
	                        <td>
	                              <input type="hidden" id="nationalityDropListEdit">
	                            <input type="text" style="width: 280px;" name="nationality" id="nationalityEdit" value="${travelerInfo.nationality!''}">
	                             <input type="hidden" id="nationalityDropList">
                                    <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span id='nationality_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th>出生日期:</th>
	                        <td>
	                            <input type="text" placeholder="yy-mm-dd" style="width: 280px;" name="birthday" id="birthdayEdit" value="${travelerInfo.birthday}" class="datepicker hasIcon default" readonly="readonly" value="1980-01-01">
	                        </td>
	                    </tr>
	                    <tr>
	                        <th>邮箱:</th>
	                        <td>
	                            <input type="hidden" id="originEmail" value="${(travelerInfo.email)!}">
	                            <input type="text" placeholder="请输入邮箱地址" style="width: 285px;" name="email" value="${(travelerInfo.email)!}" id="emailEdit" maxlength="50">
	                                <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span id='email_errorHintEdit' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>

	                      <tr>
                                      <th>详细地址:</th>
                                         <input type='hidden' id='defaultProvince' value='${(travelerInfo.province)!}'/>
                                            <input type='hidden' id='defaultCity' value='${(travelerInfo.city)!}'/>
                                            <input type='hidden' id='defaultArea' value='${(travelerInfo.district)!}'/>
                                            <input type='hidden' id='defaultDetailAddress' value='${(travelerInfo.detailAddress)!}'/>
                                    <td>
                                       <div class="dropdown" style="width: 110px;" id='address_province_edit'>
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="选择省">
                                                    <span class="activeFonts">选择省</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                </ul>
                                            </div>
                                            <div class="dropdown" style="width: 110px;" id='address_city_edit'>
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="选择市">
                                                    <span class="activeFonts">选择市</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                </ul>
                                            </div>
                                            <div class="dropdown" style="width: 110px;" id='address_area_edit'>
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="选择区/县">
                                                    <span class="activeFonts">选择区/县</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                </ul>
                                            </div>
                                              <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='address_errorHint_edit' class="errorHint"><span>
                                            </div>
                                            <input type="text" id='address_detailAddress_edit' maxLength='50'  style="width: 356px;" value="${(travelerInfo.detailAddress)!}" placeholder="填写详细地址" name="detailAddress">
                                            <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='detailAddress_errorHint_edit' class="errorHint"><span>
                                            </div>
                                    </td>
                                    </tr>

	                    </tbody>
	                </table>
	            </div>
	            <div class="saveContentModel">
	                <a href="javascript:void(0);" class="commonBtn blueBtn edit">保存</a>
	                <div class="checkboxContent">
	                    <label class="checkbox <#if travelerInfo.isDefault?c == 'true'>active</#if>" name="isDefault">
	                        <span class="commonIcon checkboxIcon"></span>设为默认常用旅客信息
	                    </label>
	                </div>
	            </div>
            </form>
        </section>



