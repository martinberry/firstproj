                        <!-- 联系人信息 S -->
                        <div class="order-conBlock">
                            <div class="order-conBlockTitle">
                                <i class="linkManIcon"></i>
                                <span>联系人信息</span>
                                <span class="titleTips">(为避免耽误出行，请仔细填写联系人信息)</span>
                            </div>
                                <table class="order-conBlockTab order-linkManTable">
                                    <colgroup>
                                        <col width="180"/>
                                        <col width="645"/>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th class="adjustpadding">姓名：</th>
                                        <td class="adjustpadding">
                                        	<div class="guestContainer">
	                                            <input id="contactorName" type="text" data-cv="required,contactor" value="${(contactorInfo.name)!''}" style="width: 280px;" placeholder="" data-cp="right"/>
									            <#if login?? && login?c == 'true'>
									            	<span class="guestNameIcon" style="display;"></span>
									            <#else>
									             	<span class="guestNameIcon" style="display:none;"></span>
									            </#if>
									            <ul class="guestNameList" style="width:284px;">
	                							</ul>
	                						</div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>手机号：</th>
                                        <td>
                                            <input id="contactorPhone" type="text" data-cv="required,cellphone" value="${(contactorInfo.phone)!''}" style="width: 280px;" data-cp="right" placeholder=""/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>邮箱：</th>
                                        <td>
                                            <input id="contactorEmail" type="text" data-cv="required,email" value="${(contactorInfo.email)!''}" style="width: 280px;" data-cp="right" placeholder="接收行程单和航班信息" maxlength="50" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: top;padding-top:18px;">详细地址：</th>
                                        <td>
                                        	<input type="hidden" id="hideProvince" value="${(contactorInfo.province)!''}">
                                        	<input type="hidden" id="hideCity" value="${(contactorInfo.city)!''}">
                                        	<input type="hidden" id="hideCounty" value="${(contactorInfo.county)!''}">

                                            <div class="dropdown province" style="width: 115px;" id="address_province">
                                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                                    <span class="activeFonts">省/直辖市</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu" data-cv='required' data-cp="bottom">
                                                </ul>
                                            </div>
                                            <div class="dropdown city " style="width: 115px;" id="address_city">
                                                <a href="javascript:void(0);"  data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="市/区">
                                                    <span class="activeFonts">市/区</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu" data-cv='required' data-cp="bottom">
                                                </ul>
                                            </div>
                                            <div class="dropdown county" style="width: 115px;" id="address_area">
                                                <a href="javascript:void(0);"  data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title="县">
                                                    <span class="activeFonts">县</span>
                                                    <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu" data-cv='required' data-cp="bottom">

                                                </ul>
                                            </div>
                                            <input id="contactorAddress" type="text" value="${(contactorInfo.addressDetail)!''}" style="width: 354px;margin-top:10px;" data-cv="required,streetName" data-cp="right" placeholder="填写详细通讯地址，好签收我们为您寄出的真旅礼盒哦"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                    </table>
                        </div>
                        <!-- 联系人信息 E -->