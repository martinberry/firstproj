<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=["mstatic/js/vendor/datepicker-scroll.js","mstatic/js/vendor/addresspicker-scroll.js","mstatic/js/iscroll/iscroll.js","mstatic/js/common.js","mstatic/js/vendor/cus-list-pop.js","mstatic/js/vendor/underscore.js"] remoteCssFiles=["mstatic/css/userCenter.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/editMember.js","common/weixin/cityArea.js","common/password_verify.js"] title="真旅行">
<div class="viewport" data-role="page">
    <div data-role="content">
        <@header.headerBar title="编辑信息">
        </@header.headerBar>
        <div class="edt-contain">
            <form id="memberInfoForm">
            <div class="edt-row-box">
                <div class="edt-til">头像</div>
                <img class="edt-popimg" src="${mediaserver}imageservice?mediaImageId=${(member.headImgId)!}" alt="">
            </div>
            <div class="edt-row-box">
                <div class="edt-box-left">昵称</div>
                <div class="edt-box-right"><input type="text" id="nickName" name="nickName" value="${(member.nickName)!}" data-role="none"></div>
            </div>
            <div class="edt-row-box">
                <div class="edt-box-left">性别</div>
                <div class="edt-box-right">
                    <label>
                        <input type="radio" name="gender" value="M" data-role="none" <#if (member.gender)! == "M">checked</#if>>
                        <label class="cus_radio"></label>
                    </label>男
                    <label>
                        <input type="radio" name="gender" value="F" data-role="none" <#if (member.gender)! == "F">checked</#if>>
                        <label class="cus_radio"></label>
                    </label>女
                </div>
            </div>
            <#if (member.mobile)??>
             <div class="edt-row-box">
	            <div class="edt-box-left">登录密码</div>
	            <div class="edt-box-right">
	                <input type="password" placeholder="请输入密码" id="password" data-role="none">
	            </div>
        	</div>
        	</#if>
            <div class="edt-row-box">
                <div class="edt-box-left">邮箱</div>
                <div class="edt-box-right">
                    <input type="text" value="${(member.email)!}" data-role="none" id="email" readonly="readonly" name="email" maxlength="50">
                </div>
            </div>
             <div class="edt-row-box">
	            <div class="edt-box-left">&nbsp;</div>
	            <div class="edt-box-right">
	                <input type="email" placeholder="新邮箱地址" name="newEmail" maxlength="50" id="newEmail" data-role="none">
	            </div>
        	</div>
        	<#if (member.mobile)??>
            <div class="edt-row-box">
                <div class="edt-box-left">手机号</div>
                <div class="edt-box-right">
                    <input type="text" id="mobile" value="${(member.mobile)!}" data-role="none" readonly="readonly">
                </div>
            </div>
            <div class="edt-row-box">
                <div class="edt-box-left">&nbsp;</div>
                <div class="edt-box-right">
                    <input type="text" id="newMobile" name="newMobile" placeholder="新手机号" data-role="none">
                </div>
            </div>
            <div class="edt-row-box">
         <#--      <div class="edt-box-left">&nbsp;</div>
                <div class="edt-box-right">
                    <input class="input-txt" type="text" id="verificationCode" name="verificationCode" placeholder="验证码" data-role="none">
                    <a class="btn-vld" data-role="none" href="javascript:void(0);" id="getVerificationCodeBtn">获取验证码</a>
                </div>
                -->
                <div class="ui-grid-a">
				 <div class="ui-block-a">&nbsp;</div>
				 <div class="ui-block-b clearfix">
					 <input class="input-txt" type="text" placeholder="验证码" data-role="none" id="verificationCode" name="verificationCode">
					 <a class="btn-vld" data-role="none" href="javascript:void(0);" id="getVerificationCodeBtn">获取短信验证码</a>
				 </div>
			</div>
            </div>
            <#else>
            <div class="edt-row-box">
                <div class="edt-box-left">手机号</div>
                <div class="edt-box-right">
                    <input type="text" value="无" data-role="none" readonly="readonly">
                </div>
            </div>
            <div class="edt-row-box">
                <div class="edt-box-left">&nbsp;</div>
                <div class="edt-box-right">
                    <input type="text" id="bindMobile" name="bindMobile" placeholder="请输入手机号" data-role="none">
                </div>
            </div>
             <div class="edt-row-box">
                <div class="edt-box-left"></div>
                <div class="edt-box-right">
                    <input type="password" placeholder="请输入密码" id="password" data-role="none">
                </div>
            </div>
            <div class="edt-row-box">
                <div class="ui-grid-a">
                 <div class="ui-block-a">&nbsp;</div>
                 <div class="ui-block-b clearfix">
                     <input class="input-txt" type="text" placeholder="验证码" data-role="none" id="verificationCode" name="verificationCode">
                     <a class="btn-vld" data-role="none" href="javascript:void(0);" id="getVerificationCodeBtn">获取短信验证码</a>
                 </div>
            </div>
            </div>
            </#if>
            <div class="edt-row-box">
                <div class="edt-box-left">真实姓名</div>
                <div class="edt-box-right">
                    <input type="text" name="realName" value="${(member.realName)!}" data-role="none">
                </div>
            </div>
            <div class="edt-row-box">
                <div class="edt-box-left">微信昵称</div>
                <div class="edt-box-right">
                    <input type="text" id="wxNickName" value="${wxNickName!}" data-role="none" readonly="readonly">
                </div>
            </div>
            <div id="address">
                <div class="revise-til" >通讯地址</div>
	           	<input type="hidden" id="hideProvince" value="${(member.province)!''}">
	        	<input type="hidden" id="hideCity" value="${(member.city)!''}">
	        	<input type="hidden" id="hideCounty" value="${(member.area)!''}">
                <div class="revise-val">
                    <span class="rev-term" >
                    	<span id="province" name="province">${(member.province)!}</span> <span id="city" name="city">${(member.city)!}</span> <span id="country" name="area">${(member.area)!}</span>
                    </span>
                    <span class="arr-lr"><label class="u-lr"></label></span>
                </div>
            </div>
            <textarea class="edt-textEara" name="detailAddress" data-role="none" id="detailAddress">${(member.detailAddress)!}</textarea>
            <div class="save-box">
                <a class="edt-save" href="javascript:void(0);" id="saveBtn">保存信息</a>
            </div>
            </form>
        </div>
        <!--用户信息修改成功提示框-->
        <div class="ui-content" data-role="popup" id="successDlg" data-history="false" data-theme="a" data-overlay-theme="b">
            <p class="dlg-cnt">信息修改成功！</p>
            <div class="dlg-foot">
                <a class="btn-com btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">确 定</a>
            </div>
        </div>

    </div>
</div>

     <div class="ui-content" data-role="popup" id="alert-dialog-member" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt" id="errHintMember"></p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	      </div>
    </div>

    <#include "addressTemplate.ftl" />
</@html.htmlIndex>