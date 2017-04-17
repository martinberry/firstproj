<#macro contactor>
<!-- 联系人信息开始 -->

    <div class="order-traveler-til">
        <div class="borderB">
            <i class="contactIcon"></i>联系人信息
        </div>
    </div>
    <div class="order-traveler-box">
        <div class="ui-grid-a ui-grid-b traveler-row">
            <div class="ui-block-a traveler-til">姓名</div>
            <div class="ui-block-b traveler-val"><input class="traveler-val name"  data-cv="required,contactor" data-ct="联系人姓名" type="text" placeholder="旅客姓名" value="" data-role="none"></div>
            <#if isLogin>
                <span class="contactor-lab">常用联系人</span>
            </#if>
        </div>
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">手机号码</div>
            <div class="ui-block-b traveler-val"><input class="traveler-val mobile" data-cv="required,cellphone" data-ct="手机号" type="text" placeholder="手机号码" value="" data-role="none"></div>
        </div>
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">邮箱地址</div>
            <div class="ui-block-b traveler-val"><input class="traveler-val email" type="text" data-cv="required,email" data-ct="邮箱" placeholder="邮箱地址" value="" data-role="none" maxlength="50"></div>
        </div>
        <div class="ui-grid-b traveler-row row-link" id="address">
            <input type="hidden" id="hideProvince" value="">
            <input type="hidden" id="hideCity" value="">
            <input type="hidden" id="hideCounty" value="">
            <div class="ui-block-a traveler-til">省/市/区</div>
            <div class="ui-block-b traveler-val"><input class="traveler-val province" type="text"  data-cv="required" data-ct="省市区" id="wholeAddress" value="" data-role="none" readonly="readonly"></div>
            <div class="ui-block-c traveler-icon"><label class="travel-arrow"></label></div>
        </div>
        <div class="ui-grid-a traveler-row">
            <div class="ui-block-a traveler-til">详细地址</div>
            <div class="ui-block-b traveler-val"><input class="traveler-val address" data-cv="required,streetName" data-ct="详细地址" id="detail" type="text" placeholder="请填写你的常住地址"  value="" data-role="none"></div>
        </div>
    </div>
    <!-- 联系人信息结束 -->
</#macro>
