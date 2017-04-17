<!-- 乘客信息开始 -->
<script id="wxPassengerTemplate" type="text/x-jquery-tmpl">
    <div class="order-traveler-til clearfix">
        <div class="borderB">
            <div class="fl"><i class="travelerIcon"></i>旅客信息</div>
            <div class="fr order-traveler-right"><label class="notice"></label>距出行日15天不可更改</div>
        </div>
    </div>
    <div class="order-traveler-box">
        <div class="ui-grid-b traveler-row">
            <div class="ui-block-a traveler-til">中文名字</div>
            <div class="ui-block-b"><input class="traveler-val firstName" maxlength="20" type="text" data-cv="required,chOrEnName" data-ct="旅客姓" placeholder="请输入姓" data-role="none"></div>
            <div class="ui-block-c">
            {{if login == 'true'}}
            	<span class="travel-lab" data-index="{{= indexNum-1}}">常旅客</span>
            {{else}}
            	<span class="travel-lab" style="display:none;">常旅客</span>
            {{/if}}
            </div>
        </div>
        <div class="ui-grid-a">
            <div class="ui-block-a">&nbsp;</div>
            <div class="ui-block-b traveler-row01"><input class="traveler-val lastName" maxlength="20" data-cv="required,chOrEnName"  data-ct="旅客名" type="text" placeholder="请输入名" data-role="none"></div>
        </div>
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">英文名字</div>
            <div class="ui-block-b"><input class="traveler-val firstNameEn" type="text" maxlength="30" data-cv="required,enName" data-ct="旅客英文姓" placeholder="first name" data-role="none"></div>
        </div>
        <input type="hidden" id="firstAndLastName{{= indexNum-1}}" class="wholeName" value="" data-ct="旅客姓名" data-cv="wholeName">
        <div class="ui-grid-a">
            <div class="ui-block-a">&nbsp;</div>
            <div class="ui-block-b traveler-row01"><input class="traveler-val lastNameEn" maxlength="30" data-cv="required,enName" data-ct="旅客英文名" type="text" placeholder="last name" data-role="none"></div>
        </div>
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">旅客类型</div>
            {{if passengerType == 'adult'}}
            	<div class="ui-block-b traveler-val passengerType" passengerType="ADULT">成人</div>
            {{else}}
            	<div class="ui-block-b traveler-val passengerType" passengerType="CHILD">儿童</div>
            {{/if}}
        </div>
        <div class="ui-grid-b traveler-row credential" data-index="{{= indexNum-1}}">
            <div class="ui-block-a traveler-til">证件类型</div>
            <input type="hidden" id="IDCARDHIDDEN{{= indexNum-1}}" data-type="IDCARD" data-name="身份证" data-num="" data-validate="" class="idCardHidden" >
            <input type="hidden" id="PASSPORTHIDDEN{{= indexNum-1}}" data-type="PASSPORT" data-name="护照" data-num="" data-validate="" class="passPortHidden" >
            <input type="hidden" id="HKPASSHIDDEN{{= indexNum-1}}" data-type="GANGAOPASS" data-name="港澳通行证" data-num="" data-validate="" class="hkPassHidden" >
            {{if productType == 'domestic'}}
            	<div class="ui-block-b traveler-val credentialType" credentialType="IDCARD">身份证</div>
            {{else}}
            	<div class="ui-block-b traveler-val credentialType" credentialType="PASSPORT">护照</div>
            {{/if}}
            <div class="ui-block-c traveler-icon"><label class="travel-arrow"></label></div>
        </div>
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">证件号码</div>
            {{if productType == 'domestic'}}
	            <div class="ui-block-b"><input class="traveler-val credentialNum" type="text" placeholder="请输入证件号码" data-ct="证件号" data-cv="required,idCard" data-role="none"></div>
            {{else}}
            	<div class="ui-block-b"><input class="traveler-val credentialNum" type="text" placeholder="请输入证件号码" data-ct="证件号" data-cv="required,passPort" data-role="none"></div>
            {{/if}}
        </div>

	    {{if productType != 'domestic'}}
		     <div class="ui-grid-b traveler-row">
	            <div class="ui-block-a traveler-til">证件有效期</div>
	            <div class="ui-block-b traveler-val credentialsDeadLine" id="deadLine{{= indexNum-1}}"><input class="traveler-val credentialsDeadLine datepicker"  data-ct="证件有效期" data-cv="required" date-type="deadTime" type="text" data-time="2020-01-01" placeholder="年  月  日"  value="2020年1月1日" data-role="none" readonly="readonly"></div>
	            <div class="ui-block-c traveler-icon"><label class="travel-arrow"></label></div>
	        </div>
	        <div class="ui-grid-a traveler-row">
	            <div class="ui-block-a traveler-til">旅客国籍</div>
	            <div class="ui-block-b">
		        	<input type="hidden" id="nationalityDropList_{{= indexNum-1}}" class="nationalityDropList">
	            	<input class="traveler-val country" id="nationality_{{= indexNum-1}}" type="text" placeholder="中国" data-cv="required" data-ct="国籍" value="中国" data-role="none">
            		<div class="verifyStyle" style="display:none">
                    	<i class="commonIcon errorIcon"></i><span id='nationality_errorHintAdd_{{= indexNum-1}}' class="errorHint"><span>
                	</div>
            	</div>
	        </div>
	    {{/if}}
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">旅客性别</div>
            <div class="ui-block-b traveler-val">
               <label class="gender">
                    <input type="radio" class="male" name="sex{{= indexNum-1}}" value="MALE" data-role="none" checked="checked">
                    <span>男</span>
                </label>
                <label class="gender">
                    <input type="radio" class="female" name="sex{{= indexNum-1}}" value="FEMALE" data-role="none">
                    <span>女</span>
                </label>
            </div>
        </div>
        <div class="ui-grid-b traveler-row">
            <div class="ui-block-a traveler-til">出生日期</div>
            <div class="ui-block-b traveler-val brithday" id="birthdate{{= indexNum-1}}"><input class="traveler-val birthday datepicker" type="text" data-cv="required" data-ct="出生日期" date-type="brithDay" placeholder="年  月  日" value="1980年1月1日" data-time="1980-01-01" data-role="none" value="" readonly="readonly"></div>
            <div class="ui-block-c traveler-icon"><label class="travel-arrow"></label></div>
        </div>
    </div>
</script>
<!-- 乘客信息结束 -->