 <!-- 订单乘客信息开始 -->
 	
    <div class="row-til clearfix">
	    <div class="borderB">
	    	<i class="travelerIcon"></i>旅客信息
    	<#if canEdit?? && canEdit?c == "true">
    		<div class="row-edt-traveler">出行15天内不可修改<span class="edt-icon"></span>
    		</div>
    	</#if>
	    </div>
    </div>
    <div class="row-content travelerInfo">
    	<#if passengerInfoVoList?? && passengerInfoVoList?size gt 0>
    		<#list passengerInfoVoList as passengerInfoVo>
	        <div class="ui-grid-a">
	        	<input type="hidden" value="${(passengerInfoVo.passengerId)!}">
	            <div class="ui-block-a">
	                <div><span class="row-span">旅客姓名：</span><span class="row-val">${(passengerInfoVo.name)!}</span></div>
	                <div><span class="row-span">旅客类型：</span><span class="row-val">${(passengerInfoVo.passengerTypeDesc)!}</span></div>
	            	<#if isDomestic?c != "true">
	                	<div><span class="row-span">旅客国籍：</span><span class="row-val">${(passengerInfoVo.country)!}</span></div>
	                </#if>
	                <div><span class="row-span">证件类型：</span><span class="row-val">${(passengerInfoVo.credentialTypeDesc)!}</span></div>
	                <div class="width100"><span class="row-span">证件号码：</span><span class="row-val">${(passengerInfoVo.credentialNum)!}</span></div>
	            </div>
	            <div class="ui-block-b">
	                <div><span class="row-span">英文拼音：</span><span class="row-val">${(passengerInfoVo.firstEnName)!}/${(passengerInfoVo.lastEnName)!}</span></div>
	                <div><span class="row-span">旅客性别：</span><span class="row-val">${(passengerInfoVo.genderDesc)!}</span></div>
	                <div><span class="row-span">出生日期：</span><span class="row-val">${(passengerInfoVo.birthday)!}</span></div>
	                <#if isDomestic?c != "true">
	                <div><span class="row-span">证件有效期：</span><span class="row-val">${(passengerInfoVo.credentialDeadLine)!}</span></div>
	                </#if>
	            </div>
	        </div>
	        </#list>
        </#if>
    </div>
     <!-- 订单乘客信息结束 -->

     <!--乘客信息编辑-->
        <div class="order-traveler-edt">
	             <#if passengerInfoVoList?? && passengerInfoVoList?size gt 0>
	    			<#list passengerInfoVoList as passengerInfoVo>
		                <div class="traveler-edt-box">
		                    <div class="ui-grid-b">
		                    <input type="hidden" class="hiddenId" value="${(passengerInfoVo.passengerId)!}">
		                        <div class="ui-block-a">旅客中文名</div>
		                        <div class="ui-block-b"><input class="traveler-val firstName" readonly="readonly" maxlength="20" data-cv="required,chOrEnName" data-ct="旅客姓" type="text" placeholder="请输入姓" data-role="none" value="${(passengerInfoVo.firstName)!}"></div>
		                    </div>
		                    <div class="ui-grid-a">
		                        <div class="ui-block-a">&nbsp;</div>
		                        <div class="ui-block-b"><input class="traveler-val lastName" readonly="readonly" maxlength="20" data-cv="required,chOrEnName"  data-ct="旅客名" type="text" placeholder="请输入名" data-role="none" value="${(passengerInfoVo.lastName)!}"></div>
		                    </div>
		                    <div class="ui-grid-b">
		                        <div class="ui-block-a">旅客英文名</div>
		                        <div class="ui-block-b"><input class="traveler-val firstEnName"  maxlength="30" data-cv="required,enName" data-ct="旅客英文姓" type="text" placeholder="first name" data-role="none" value="${(passengerInfoVo.firstEnName)!}"></div>
		                        <div class="ui-block-c">&nbsp;</div>
		                    </div>
		                    <div class="ui-grid-a">
		                        <div class="ui-block-a">&nbsp;</div>
		                        <div class="ui-block-b"><input class="traveler-val lastEnName"  maxlength="30" data-cv="required,enName" data-ct="旅客英文名" type="text" placeholder="last name" data-role="none" value="${(passengerInfoVo.lastEnName)!}"></div>
		                    </div>
		                    <div class="ui-grid-b">
		                        <div class="ui-block-a">旅客类型</div>
		                        <div class="ui-block-b"><input class="traveler-val passengerType" type="text" attr-type="${(passengerInfoVo.passengerType)!}" value="${(passengerInfoVo.passengerTypeDesc)!}" data-role="none" readonly="readonly"></div>
		                        <div class="ui-block-c">&nbsp;</div>
		                    </div>
		                    <div class="ui-grid-b credential">
		                        <div class="ui-block-a">证件类型</div>
		                        <input type="hidden" data-dead="${(passengerInfoVo.credentialDeadLine)!}" data-num="${(passengerInfoVo.credentialNum)!}" data-type="${(passengerInfoVo.credentialType)!}">
		                        <div class="ui-block-b credentialType" credentialType="${(passengerInfoVo.credentialType)!}"><#if (passengerInfoVo.credentialType == "IDCARD")>身份证</#if><#if (passengerInfoVo.credentialType == "PASSPORT")>护照</#if><#if (passengerInfoVo.credentialType == "GANGAOPASS")>港澳通行证</#if></div>
		                        <div class="ui-block-c"><label class="travel-arrow" data-role="none"></label></div>
		                    </div>
		                    <div class="ui-grid-b">
		                        <div class="ui-block-a">证件号码</div>
		                        <div class="ui-block-b">
		                        	<#if passengerInfoVo.credentialType == "IDCARD">
		                        		<input class="traveler-val credentialNum" type="text" data-cv="requirded,idCard" data-ct="身份证" value="${(passengerInfoVo.credentialNum)!}" placeholder="请输入证件号码" data-role="none">
		                        	</#if>
		                        	<#if passengerInfoVo.credentialType == "PASSPORT">
		                        		<input class="traveler-val credentialNum" type="text" data-cv="required,passPort" data-ct="护照" value="${(passengerInfoVo.credentialNum)!}" placeholder="请输入证件号码" data-role="none">
		                        	</#if>
		                        	<#if passengerInfoVo.credentialType == "GANGAOPASS">
		                        		<input class="traveler-val credentialNum" type="text" data-cv="required,hkPass" data-ct="港澳通行证" value="${(passengerInfoVo.credentialNum)!}" placeholder="请输入证件号码" data-role="none">
		                        	</#if>
		                        </div>
		                        <div class="ui-block-c">&nbsp;</div>
		                    </div>
		                    <#if isDomestic?c != "true">
			                    <div class="ui-grid-b credentialsDeadLine" id="deadTime${passengerInfoVo_index}">
			                        <div class="ui-block-a">证件有效期</div>
			                        <div class="ui-block-b datetime dead-time" date-type="deadTime" data-time="${(passengerInfoVo.credentialDeadLine)!}">${(passengerInfoVo.credentialDeadLine)!"2020年1月1日"}</div>
			                        <div class="ui-block-c"><label class="travel-arrow" data-role="none"></label></div>
			                    </div>
			                    <div class="ui-grid-b">
			                        <div class="ui-block-a traveler-til">旅客国籍</div>
						            <div class="ui-block-b">
						            	<input class="traveler-val country" id="nationality_${passengerInfoVo_index}" data-cv="required" data-ct="国籍" type="text" placeholder="" data-cv="required" data-ct="国籍" value="${(passengerInfoVo.country)!'中国'}" data-role="none">
						            	<input type="hidden" id="nationalityDropList_${passengerInfoVo_index}" class="nationalityDropList">
							        	<div class="verifyStyle" style="display:none">
						                    <i class="commonIcon errorIcon"></i><span id='nationality_errorHintAdd_${passengerInfoVo_index}' class="errorHint"><span>
						                </div>
						            </div>
						            <div class="ui-block-c">&nbsp;</div>
			                    </div>
		                    </#if>
		                    <div class="ui-grid-b brithday" id="brithDay${passengerInfoVo_index}">
		                        <div class="ui-block-a">出生日期</div>
		                        <div class="ui-block-b brithdayDiv datetime" date-type="brithDay" data-time="${(passengerInfoVo.birthday)!}">${(passengerInfoVo.birthday)!"1980年1月1日"}</div>
		                        <div class="ui-block-c"><label class="travel-arrow" data-role="none"></label></div>
		                    </div>
		                     <div class="ui-grid-b">
		                        <div class="ui-block-a">旅客性别</div>
		                        <div class="ui-block-b block-val genderDiv">
		                            <label>
		                                <input type="radio" class="male" name="sex${passengerInfoVo_index}" value="MALE" value="MALE" data-role="none" <#if passengerInfoVo.gender=="MALE">checked</#if>>
		                                <span>男</span>
		                            </label>
		                            <label>
	                                	<input type="radio" name="sex${passengerInfoVo_index}" value="FEMALE" value="FEMALE" data-role="none" <#if passengerInfoVo.gender=="FEMALE">checked</#if>>
		                                <span>女</span>
		                            </label>
		                        </div>
		                        <div class="ui-block-c">&nbsp;</div>
		                    </div>
               			</div>
                    </#list>
                 </#if>
                <div class="row-edt-til clearfix"><div class="travel-edt-btn"><span class="edt-cancel">取消</span><span class="edt-save">保存</span></div></div>
            </div>
            </div>
