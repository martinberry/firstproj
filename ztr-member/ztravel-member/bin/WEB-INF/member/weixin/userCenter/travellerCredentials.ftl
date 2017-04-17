    <div class="menu-til">
        <div class="menu-rl">证件信息</div>
        <div class="menu-rr"><a class="btn-add" data-role="none" href="javascript:void(0);" id="addCredentialBtn">添加证件</a></div>
    </div>
    <div id="credentialList">
        <#if (traveller.credentials)??>
        <#list traveller.credentials as credential>
        <div class="revise-contain01">
            <div class="revise-tab">
                <div class="revTab-row">
                 <div class="revTab-row-til" >证件类型</div>
                 <div class="revTab-row-val">
                        <input type="text" id="cardType-${credential_index}" name="credentialType"  readonly="readonly" <#if credential.type == 'IDCARD'>value="身份证"</#if>  <#if credential.type == 'GANGAOPASS'>value="港澳通行证"</#if><#if credential.type == 'PASSPORT'>value="护照"</#if> data-role="none">
                 </div>
                 </div>
                <div class="revTab-row">
                    <div class="revTab-row-til">证件号</div>
                    <div class="revTab-row-val">
                        <input type="text" name="credentialNumber" value="${(credential.number)!}" data-role="none">
                    </div>
                </div>
                <div name="deadLineDateDiv">
                    <div class="revTab-row-til" >有效期</div>
                    <div class="revTab-row-val">
                        <span class="rev-term" name="deadLineDate" id="deadLineDay-${credential_index}">${(credential.deadLineDay)!}</span>
                        	<span class="arr-lr"><label class="u-lr"></label></span>
                    </div>
                </div>
            </div>
            <div class="rev-btn-box">
                <a class="rev-del" data-role="none" href="javascript:void(0);">删除</a>
            </div>
        </div>
        </#list>
        </#if>

    </div>
       <#include "/member/weixin/userCenter/blankCredential.ftl" />
