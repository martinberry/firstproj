<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />
<@html.htmlIndex remoteJsFiles=["mstatic/js/vendor/datepicker-scroll.js","mstatic/js/vendor/addresspicker-scroll.js","mstatic/js/iscroll/iscroll.js","mstatic/js/common.js","mstatic/js/vendor/cus-list-pop.js","mstatic/js/vendor/underscore.js"] remoteCssFiles=["mstatic/css/userCenter.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/editTraveller.js", "member/weixin/travellerValidate.js", "common/ChinesePY.js", "common/typeahead.js","common/weixin/cityArea.js"] title="修改旅客信息">


<div class="viewport" data-role="page">
    <div data-role="content">
        <@header.headerBar title="修改旅客信息">
        </@header.headerBar>
        <!--<form id="travellerInfoForm">-->
          <div class="menu-til">

        </div>
        <div class="revise-contain">
            <div class="revise-row-box">
                <div class="revise-til">姓</div>
                <div class="revise-val">
                    <input type="text" id="firstName" name="firstName" value="${(traveller.firstNameCn)!}" data-role="none">
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">名</div>
                <div class="revise-val">
                    <input type="text" id="lastName" name="lastName" value="${(traveller.lastNameCn)!}" data-role="none">
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">拼音/英文名</div>
                <div class="revise-val">
                    <input type="text" id="firstNameEn" name="firstNameEn" value="${(traveller.firstEnName)!}" placeholder="First Name" data-role="none">
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">&nbsp;</div>
                <div class="revise-val">
                    <input type="text" id="lastNameEn" name="lastNameEn" value="${(traveller.lastEnName)!}" placeholder="Last Name" data-role="none">
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">邮箱</div>
                <div class="revise-val">
                    <input type="text" id="email" name="email" value="${(traveller.email)!}" data-role="none" maxlength="50">
                </div>
            </div>
            <div>
                <div class="revise-til">手机号</div>
                <div class="revise-val">
                    <input type="text" id="mobile" name="mobile" value="${(traveller.phoneNum)!}" data-role="none">
                </div>
            </div>
        </div>
        <#include "/member/weixin/userCenter/travellerCredentials.ftl" />
        <div class="menu-til">
            <div class="menu-rl">详细信息</div>
        </div>
        <div class="revise-contain02">
            <div class="revise-row-box">
                <div class="revise-til">性别</div>
                <div class="revise-val" id="gender">
                    <label>
                        <input type="radio" name="gender" value="MALE" data-role="none" <#if (traveller.gender)! == "MALE">checked</#if>>
                        <label class="cus_radio"></label>
                    </label>男
                    <label>
                        <input type="radio" name="gender" value="FEMALE" data-role="none" <#if (traveller.gender)! == "FEMALE">checked</#if>>
                        <label class="cus_radio"></label>
                    </label>女
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">旅客类型</div>
                <div class="revise-val" id="travellerType">
                    <label>
                        <input type="radio" name="travellerType" value="ADULT" data-role="none" <#if (traveller.travelType)! == "ADULT">checked</#if>>
                        <label class="cus_radio"></label>
                    </label>成人
                    <label>
                        <input type="radio" name="travellerType" value="CHILD" data-role="none" <#if (traveller.travelType)! == "CHILD">checked</#if>>
                        <label class="cus_radio"></label>
                    </label>儿童
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">旅客国籍</div>
                <div class="revise-val">
                    <input type="text" id="nationality" name="nationality" value="${(traveller.nationality)!}" data-role="none">
                    <input type="hidden" id="nationalityDropList">
                    <!-- <span class="rev-term">中国</span><span class="arr-lr"><label class="u-lr"></label></span> -->
                </div>
            </div>
            <div class="revise-row-box">
                <div class="revise-til">出生日期</div>
                <div class="revise-val">
                    <span class="rev-term" id="birthdate">${(traveller.birthday)!}</span><span class="arr-lr"><label class="u-lr"></label></span>
                </div>
            </div>
            <div id="address">
                <div class="revise-til" >通讯地址</div>
	           	<input type="hidden" id="hideProvince" value="${(traveller.province)!''}">
	        	<input type="hidden" id="hideCity" value="${(traveller.city)!''}">
	        	<input type="hidden" id="hideCounty" value="${(traveller.district)!''}">
                <div class="revise-val">
                    <span class="rev-term" >
                    	<span id="province">${(traveller.province)!}</span> <span id="city">${(traveller.city)!}</span> <span id="country">${(traveller.district)!}</span>
                    </span>
                    <span class="arr-lr"><label class="u-lr"></label></span>
                </div>
            </div>
            <textarea class="edt-textEara" data-role="none" id="detailAddress" name="detailAddress">${(traveller.detailAddress)!}</textarea>
            <div class="save-box">
                <a class="edt-save" href="javascript:void(0);" id="saveBtn">保 存</a>
            </div>
        </div>
        <div id="travellerId" style="display:none">${(traveller.id)!}</div>


        <!-- </form> -->
           <div class="ui-content" data-role="popup" id="alertErrHint-dialog" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt" id="errHint"></p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	      </div>
    </div>
</div>

<#include "addressTemplate.ftl" />
</@html.htmlIndex>