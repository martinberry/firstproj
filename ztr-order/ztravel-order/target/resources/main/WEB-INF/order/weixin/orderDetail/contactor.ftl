 <!-- 订单联系人信息开始 -->
    <div class="row-til clearfix">
		<div class="borderB">
			<i class="contactIcon"></i>联系人信息
			<div class="row-edt-pop">
				<#if canEdit?? && canEdit?c == "true"><div class="row-edt-pop">出行15天内不可修改<span class="edt-icon"></span></div></#if>
			</div>
		</div>
	</div>
    <#if contactorInfoVo??>
	    <div class="row-content">
	        <div><span class="row-txt">姓名：</span><span class="row-val">${(contactorInfoVo.name)!}</span></div>
	        <div><span class="row-txt">手机号：</span><span class="row-val">${(contactorInfoVo.mobile)!}</span></div>
	        <div><span class="row-txt">邮箱：</span><span class="row-val">${(contactorInfoVo.mail)!}</span></div>
	        <div><span class="row-txt">详细地址：</span><span class="row-val">${(contactorInfoVo.province)!}${(contactorInfoVo.city)!}${(contactorInfoVo.county)!}${(contactorInfoVo.street)!}</span></div>
	    </div>
	    
	    <!-- 订单联系人信息结束 -->
		<div class="order-pop-edt">
            <div class="order-edt-box">
                <div class="ui-grid-a">
    				<input id="contactorId" type="hidden" value="${(contactorInfoVo.comContactorId)!}">
                    <div class="ui-block-a">姓名</div>
                    <div class="ui-block-b width50">
                    	<input class="traveler-val contactorName" data-cv="required,contactor" data-ct="联系人姓名" type="text" value="${(contactorInfoVo.name)!}" data-role="none">
                    </div>
                </div>
                <div class="ui-grid-a">
                    <div class="ui-block-a">手机号</div>
                    <div class="ui-block-b"><input class="traveler-val phone" data-cv="required,cellphone" data-ct="手机号" type="text" value="${(contactorInfoVo.mobile)!}" data-role="none"></div>
                </div>
                <div class="ui-grid-a">
                    <div class="ui-block-a">邮箱</div>
                    <div class="ui-block-b"><input class="traveler-val email" type="text" data-cv="required,email" data-ct="邮箱" value="${(contactorInfoVo.mail)!}" data-role="none" maxlength="50"></div>
                </div>
                <div class="ui-grid-a" id="address">
                    <input type="hidden" id="hideProvince" value="${(contactorInfoVo.province)!}"/>
                    <input type="hidden" id="hideCity" value="${(contactorInfoVo.city)!}"/>
                    <input type="hidden" id="hideCounty" value="${(contactorInfoVo.county)!}"/>
                    <div class="ui-block-a">省、市、区</div>
                    <div class="ui-block-b"><span class="traveler-val01" id="wholeAddress">${(contactorInfoVo.province)!} ${(contactorInfoVo.city)!} ${(contactorInfoVo.county)!}</span><label class="travel-arrow" data-role="none"></label>
                    </div>
                </div>
                <div class="ui-grid-a">
                    <div class="ui-block-a">详细地址</div>
                    <div class="ui-block-b"><input class="traveler-val" type="text" data-cv="required,streetName" data-ct="详细地址" id="streetName" value="${(contactorInfoVo.street)!}" data-role="none"></div>
                </div>
            </div>
            <div class="row-edt-til noborderB clearfix"><div class="pop-edt-btn"><span class="edt-cancel">取消</span><span class="edt-save">保存</span></div></div>
        </div>
    <!--联系人-->
    </#if>
