<#macro add>

        <section class="rightContent addTravelerInfoModel add" style="float:none;width:100%">
        	<form id="addTravelerForm">
	            <div>
	                <div class="titleContent clearfix">
	                    <b class="titleFonts">添加常用旅客</b>
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
                                            <input type="text" style="width: 140px;" placeholder="姓" id="firstNameCnAdd" maxLength="20"  name="firstNameCn"/>
                                            <input type="text" style="width: 140px;" placeholder="名" id="lastNameCnAdd" maxLength="20" name="lastNameCn"/>
                                               <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='firstName_errorHintAdd' class="errorHint"><span>
					                            </div>
                                                 <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='lastName_errorHintAdd' class="errorHint"><span>
					                            </div>
					                                <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='repeateName_errorHintAdd' class="errorHint"><span>
					                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th><em>*</em>英文/拼音：</th>
                                       <td class="adjustpadding">
                                        <div class="guestContainer">
                                            <input type="text" style="width: 140px;" placeholder="First Name" id="firstNameEnAdd" maxLength="20" data-cv="required" name="firstEnName"/>
                                            <input type="text" style="width: 140px;" placeholder="Last Name" id="lastNameEnAdd" maxLength="20" data-cv="required" name="lastEnName"/>
                                               <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='firstNameEn_errorHintAdd' class="errorHint"><span>
					                            </div>
                                                 <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span id='lastNameEn_errorHintAdd' class="errorHint"><span>
					                            </div>
                                        </div>
                                    </td>
                                </tr>
	                        <tr>
	                            <th>手机号:</th>
	                            <td>
	                                <input type="text" style="width: 280px;" placeholder="方便接收旅途通知讯息" id="phoneNumAdd" name="phoneNum">
	                                 <div class="verifyStyle" style="display:none">
		                                <i class="commonIcon errorIcon"></i><span id='phoneNum_errorHintAdd' class="errorHint"><span>
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
	                <table class="travelerInfoTab idTypeInfo">
	                    <colgroup>
	                        <col width="90">
                                    <col width="370">
                                    <col width="75">
                                    <col width="">
	                    </colgroup>
	                    <tbody>
						<tr class="defaultTr">
                                        <th><em>*</em>证件类型:</th>
                                        <td>
                                            <div class="dropdown id-type" style="width: 100px;">
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                                    <span class="activeFonts"></span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <li class="active" data-val="IDCARD"><a href="javascript:void(0);">身份证</a></li>
                                                    <li data-val="PASSPORT"><a href="javascript:void(0);" >护照</a></li>
                                                    <li data-val="GANGAOPASS"><a href="javascript:void(0);" >通行证</a></li>
                                                </ul>
                                            </div>
                                            <input type="text" style="width: 240px;" class="number">
                                        </td>
                                        <th>有效期:</th>
                                        <td>
                                            <input type="text" style="width: 105px;"  class="datepicker hasIcon default" value="2020-01-01" readonly="readonly" disabled="disabled">
                                            <span class="commonIcon addRoundnessIcon"></span>
                                               <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span name='credit_errorHintAdd' class="errorHint"><span>
					                            </div>
                                        </td>
                                    </tr>
                                    <tr class="addTrContent">
                                        <th><em>*</em>证件类型:</th>
                                        <td>
                                            <div class="dropdown id-type" style="width: 100px;">
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                                    <span class="activeFonts"></span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <li data-val="IDCARD"><a href="javascript:void(0);">身份证</a></li>
                                                    <li data-val="PASSPORT"><a href="javascript:void(0);" >护照</a></li>
                                                    <li data-val="GANGAOPASS"><a href="javascript:void(0);">通行证</a></li>
                                                </ul>
                                            </div>
                                            <input type="text" style="width: 240px;" class="number">
                                        </td>
                                        <th>有效期:</th>
                                        <td>
                                            <input type="text" style="width:105px;"  class="datepicker hasIcon default" value="2020-01-01" readonly="readonly" disabled="disabled">
                                                <span class="commonIcon delRoundnessIcon"></span>
					                            <div class="verifyStyle" style="display:none">
					                                <i class="commonIcon errorIcon"></i><span name='credit_errorHintAdd' class="errorHint"><span>
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
                                    <tbody>
	                    <tr class="defaultTr">
	                        <th>性别:</th>
	                        <td>
	                            <div class="radioContent gender">
	                                <label class="radio" data-val="MALE">
	                                    <span class="commonIcon radioIcon"></span>男
	                                </label>
	                                <label class="radio active" data-val="FEMALE">
	                                    <span class="commonIcon radioIcon"></span>女
	                                </label>
	                            </div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th>旅客类型:</th>
	                        <td>
	                            <div class="radioContent travelType">
	                                <label class="radio active" data-val="ADULT">
	                                    <span class="commonIcon radioIcon"></span>成人
	                                </label>
	                                <label class="radio" data-val="CHILD">
	                                    <span class="commonIcon radioIcon"></span>儿童
	                                </label>
	                                <!--
	                                <label class="radio" data-val="BABY">
	                                    <span class="commonIcon radioIcon"></span>婴儿
	                                </label>-->

	                            </div>
	                        </td>
	                    </tr>
	                    <tr class="ui-widget">
                            <th>国籍:</th>
                            <td>
                                <input type="text" style="width: 280px;" id="nationality" name="nationality" value="中国"  maxLength="16">
                                <input type="hidden" id="nationalityDropList">
                                    <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span id='nationality_errorHintAdd' class="errorHint"><span>
	                            </div>
                            </td>
                        </tr>
	                    <tr>
	                        <th>出生日期:</th>
	                        <td>
	                            <input type="text"  style="width: 280px;" name="birthday" id="birthday" class="datepicker hasIcon default" readonly="readonly" value="1980-01-01">
	                        </td>
	                    </tr>
	                    <tr>
	                        <th>邮箱:</th>
	                        <td>
	                            <input type="text" placeholder="请输入邮箱地址" style="width: 285px;" name="email" id="emailAdd" maxLength="50" >
	                              <div class="verifyStyle" style="display:none">
	                                <i class="commonIcon errorIcon"></i><span id='email_errorHintAdd' class="errorHint"><span>
	                            </div>
	                        </td>
	                    </tr>
                                    <tr>
                                      <th>详细地址:</th>
                                    <td>
                                       <div class="dropdown" style="width: 110px;" id='address_province'>
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="选择省">
                                                    <span class="activeFonts">选择省</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                </ul>
                                            </div>
                                            <div class="dropdown" style="width: 110px;" id='address_city'>
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="选择市">
                                                    <span class="activeFonts">选择市</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                </ul>
                                            </div>
                                            <div class="dropdown" style="width: 110px;" id='address_area'>
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="选择区/县">
                                                    <span class="activeFonts">选择区/县</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                </ul>
                                            </div>
                                                <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='address_errorHintAdd' class="errorHint"><span>
                                            </div>
                                            <input type="text" id='address_detailAddressAdd' maxLength='50'  style="width: 356px;"  placeholder="填写详细地址" name="detailAddress">
                                            <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='detailAddress_errorHintAdd' class="errorHint"><span>
                                            </div>

                                    </td>
                                    </tr>

	                    </tbody>
	                </table>
	            </div>
	            <div class="saveContentModel">
	                <a href="javascript:void(0);" class="commonBtn blueBtn add">保存</a>
	                <div class="checkboxContent">
	                    <label class="checkbox" name="isDefault">
	                        <span class="commonIcon checkboxIcon"></span>设为默认常用旅客信息
	                    </label>
	                </div>
	            </div>
            </form>
        </section>


</#macro>