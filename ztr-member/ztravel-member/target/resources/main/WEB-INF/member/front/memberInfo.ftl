<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-个人资料"
currentMenu="个人中心"
remoteJsFiles=[]
localJsFiles=["member/front/common_utils.js", "member/front/jquery-form.js", "common/ZtrDropDown.js",
"member/front/memberInfo.js", "member/front/change_head.js","common/password_verify.js"]
remoteCssFiles=["css/client/userInfo.css"]
localCssFiles=["member/front/round_image.css"]>

		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="top:30px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="cont-block clearfix">
                        <span class="modelLine"></span>
						<aside class="leftMenuContent">
                            <ul>
                                <li class="active">
                                    <a href="${basepath}/member/info">个人资料</a>
                                </li>
                                <li>
                                    <a href="${basepath}/travelerInfo/list">常用旅客信息</a>
                                </li>
                            </ul>
						</aside>
                        <section class="rightContent editHeadPortrait">
                            <table class="dataTab">
                                <colgroup>
                                    <col width="100">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th>头像:</th>
                                    <td>
                                        <span class="headPortraitImg">
                                            <img class="round_photo" src="${mediaserver}imageservice?mediaImageId=${(member.headImageId)!}" id='headIconImg'/>
                                            <span class="commonIcon smallEditIcon editHeadPortraitIcon"></span>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>昵称:</th>
                                    <td>
                                        <spn class="nicknameContent">
                                            <span class="nickname" id='nickName_content'>${(member.nickName)!}</span>
                                            <span class="commonIcon editIcon"></span>
                                        </spn>
                                        <span class="editContent nickName">
                                            <input type="text" id='nickName_input' value="${(member.nickName)!}" class="valContent" maxLength='11' style="width: 220px;">
                                            <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='nickName_errorHint' class="errorHint"><span>
                                            </div>
                                            <span class="sureBtn" id='nickName_sureBtn'>确定</span>
                                            <span class="cancelBtn" id='nickName_cancelBtn'>取消</span>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>性别:</th>
                                    <td>
                                        <span class="genderContent">
                                            <span class="genderFonts" id='gender_content'>
                                            	<#if (member.gender)! == 'M'>男</#if>
                                            	<#if (member.gender)! == 'F'>女</#if>
                                            </span>
                                            <span class="commonIcon editIcon"></span>
                                        </span>
                                        <span class="editContent gender">
                                            <span class="radioContent" id='gender_radio'>
                                                <label class="radio <#if (member.gender)! == 'M'>active</#if>">
                                                    <span class="commonIcon radioIcon"></span>
                                                    <span class="genderSelect" value='M'>男</span>
                                                </label>
                                                <label class="radio <#if (member.gender)! == 'F'>active</#if>">
                                                    <span class="commonIcon radioIcon"></span>
                                                    <span class="genderSelect" value='F'>女</span>
                                                </label>
                                            </span>
                                            <span class="sureBtn" id='gender_sureBtn'>确定</span>
                                            <span class="cancelBtn" id='gender_cancelBtn'>取消</span>
                                        </span>
                                    </td>
                                </tr>
                                <tr class="phoneNumberContent">
                                    <th>手机号:</th>
                                    <td>
                                        <#if (member.mobile)??>
                                        <span class='mobile' id='mobile_content'>${(member.mobile)!}</span>
                                        <span class="commonIcon editIcon"></span>
                                        <#else>
                                        <span>无</span>
                                        <a href="javascript:void(0);" class="bindphone">绑定手机</a>
                                        </#if>
                                    </td>
                                </tr>
                                <#if (member.mobile)??>
                                <tr class="editModelInfo editPhoneNumber" style="display: none;">
                                    <td colspan="2" class="noPadding">
                                        <table class="editModelTab">
                                            <colgroup>
                                                <col width="100">
                                                <col width="">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>原手机号:</th>
                                                <td>
                                                    <input id='oldMobile' type="text" maxLength='11' style="width: 220px;">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='oldMobile_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>登录密码:</th>
                                                <td>
                                                    <input type="password" id='mobilePassword' maxLength='28' class="verifyBox" style="width: 220px;">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='mobilePassword_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>新手机号:</th>
                                                <td>
                                                    <input id='newMobile' type="text" maxLength='11' style="width: 220px;">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='newMobile_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>验证码:</th>
                                                <td>
                                                    <div class="componentInput verificationCode">
                                                        <input type="text" id='verificationCode' maxLength='6' placeholder="请输入验证码" style="width: 108px;">
                                                        <span class="getVerificationCode" id='getVerificationCode'>获取短信验证码</span>
                                                    </div>
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='verificationCode_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th></th>
                                                <td>
                                                    <a href="javascript:void(0);" id='mobile_blueBtn' class="commonBtn blueBtn">提交</a>
                                                    <a href="javascript:void(0);" id='mobile_grayBtn' class="commonBtn grayBtn">取消</a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                <#else>
                                <tr class="editModelInfo bindPhoneNumber">
                                    <td colspan="2" class="noPadding">
                                        <div class="editModelTabContainer" style="display: none;">
                                            <table class="editModelTab">
                                            <colgroup>
                                                <col width="100">
                                                <col width="">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>手机号:</th>
                                                <td>
                                                    <input id='newMobile' type="text" maxLength='11' style="width: 220px;" onchange="clearErrorMsg(this);">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='newMobile_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>验证码:</th>
                                                <td>
                                                    <div class="componentInput verificationCode">
                                                        <input type="text" id='verificationCode' maxLength='6' placeholder="请输入验证码" style="width: 100px;" onchange="clearErrorMsg(this);">
                                                        <span class="getVerificationCode" id='getBindVerificationCode'>获取短信验证码</span>
                                                    </div>
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='verificationCode_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>密码:</th>
                                                <td>
                                                    <input type="password" id='bindPassword' maxLength='28' class="verifyBox" style="width: 220px;" onchange="clearErrorMsg(this);">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='bindPassword_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th></th>
                                                <td>
                                                    <a href="javascript:void(0);" id='bindmobile_blueBtn' class="commonBtn blueBtn">提交</a>
                                                    <a href="javascript:void(0);" class="cancelOper">取消</a>
                                                </td>
                                            </tr>
                                            </tbody>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                                </#if>
                                <tr class="mailContent">
                                    <th>邮箱:</th>
                                    <td>
                                        <span id='email_content'>${(member.email)!}</span>
                                        <span class="commonIcon editIcon"></span>
                                    </td>
                                </tr>
                                   <tr class="editModelInfo editMail">
                                    <td colspan="2" class="noPadding">
                                        <div class="editModelTabContainer" style="display: none;">
                                            <table class="editModelTab">
                                            <colgroup>
                                                <col width="100">
                                                <col width="">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>登录密码:</th>
                                                <td>
                                                    <input type="password" id="emailPassword" class="verifyBox" style="width: 220px;">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='emailPassword_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>新邮箱:</th>
                                                <td>
                                                    <input type="text" value="" id="newEmail" style="width: 220px;" placeholder="请输入新邮箱地址" maxlength="50">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='newEmail_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th></th>
                                                <td>
                                                    <a href="javascript:void(0);" id="email_blueBtn" class="commonBtn blueBtn">提交</a>
                                                    <a href="javascript:void(0);" id="email_grayBtn" class="cancelOper">取消</a>
                                                </td>
                                            </tr>
                                            </tbody>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                                <#if (member.password)??>
                                <tr class="PWContent">
                                    <th>密码:</th>
                                    <td>
                                        <span>●●●●●●</span>
                                        <span class="commonIcon editIcon"></span>
                                    </td>
                                </tr>
                                </#if>
                                <tr class="editModelInfo editPw" style="display: none;">
                                    <td colspan="2" class="noPadding">
                                        <table class="editModelTab">
                                            <colgroup>
                                                <col width="100">
                                                <col width="">
                                            </colgroup>
                                            <tbody>
                                            <tr>
                                                <th>原密码:</th>
                                                <td>
                                                    <input type="password" id='oldPassword' maxLength='28' style="width: 220px;">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='oldPassword_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>新密码:</th>
                                                <td>
                                                    <div class="componentInput PWStrong">
                                                        <input type="password" id='newPassword' maxLength='28' style="width: 177px;">
                                                        <span class="strength strong" style='display:none;'>强</span>
                                                        <span class="strength centre" style='display:none;'>中</span>
                                                        <span class="strength weak">弱</span>
                                                    </div>
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='newPassword_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>再次输入:</th>
                                                <td>
                                                    <input type="password" id='newPasswordRe' maxLength='28' style="width: 220px;">
                                                    <div class="verifyStyle" style='display:none;'>
                                                        <i class="commonIcon errorIcon"></i><span id='newPasswordRe_errorHint' class="errorHint"><span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th></th>
                                                <td>
                                                    <a href="javascript:void(0);" id='password_blueBtn' class="commonBtn blueBtn">提交</a>
                                                    <a href="javascript:void(0);" id='password_grayBtn' class="commonBtn grayBtn">取消</a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <th>真实姓名:</th>
                                    <td>
                                        <span class="realNameContent">
                                            <span class="realNameFonts" id='realName_content'>${(member.realName)!}</span>
                                            <span class="commonIcon editIcon"></span>
                                        </span>
                                        <span class="editContent realName">
                                            <input type="text" id='realName_input' value="${(member.realName)!}" maxLength='20' class="editRealName" style="width: 220px;">
                                            <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='realName_errorHint' class="errorHint"><span>
                                            </div>
                                            <span class="sureBtn" id='realName_sureBtn'>确定</span>
                                            <span class="cancelBtn" id='realName_cancelBtn'>取消</span>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>通信地址:</th>
                                    <td>
                                        <span class="addressContent">
                                            <span id='address_content'>
                                            ${(member.province)!}
                                            <#if member.city??>,${(member.city)!}</#if>
                                            <#if member.area??>,${(member.area)!}</#if>
                                            <#if member.detailAddress??>,${(member.detailAddress)!}</#if>
                                            </span>
                                            <input type='hidden' id='defaultProvince' value='${(member.province)!}'/>
                                            <input type='hidden' id='defaultCity' value='${(member.city)!}'/>
                                            <input type='hidden' id='defaultArea' value='${(member.area)!}'/>
                                            <input type='hidden' id='defaultDetailAddress' value='${(member.detailAddress)!}'/>
                                            <span class="commonIcon editIcon"></span>
                                        </span>
                                        <span class="editContent address">
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
                                            <input type="text" id='address_detailAddress' maxLength='50' value="${(member.detailAddress)!}" style="width: 300px;">
                                            <div class="verifyStyle" style='display:none;'>
                                                <i class="commonIcon errorIcon"></i><span id='detailAddress_errorHint' class="errorHint"><span>
                                            </div>
                                            <span class="sureBtn" id='address_sureBtn'>确定</span>
                                            <span class="cancelBtn" id='address_cancelBtn'>取消</span>
                                        </span>
                                    </td>
                                </tr>
                                <#if member.openId?? && member.mobile??>
                                <tr class="wechatNumberContent">
                                    <th>微信昵称:</th>
                                    <td>
                                        <span>${wxNickName!}</span>
                                        <!-- <span class="commonIcon editIcon"></span> -->
                                        <a href="javascript:void(0);" class="unbindWechat unbindWechat-tooltip">解除绑定</a>
                                    </td>
                                </tr>
                                <#elseif !member.openId?? && member.mobile??>
                                <tr class="wechatNumberContent">
                                    <th>微信昵称:</th>
                                    <td>
                                        <span>无</span>
                                        <!-- <span class="commonIcon editIcon"></span> -->
                                        <a href="javascript:void(0);" class="bindWechat bindWechat-tooltip">扫码绑定微信<span class="wechatQRIcon"></span></a>
                                    </td>
                                </tr>
                                <#elseif member.openId?? && !member.mobile??>
                                <tr class="wechatNumberContent">
                                    <th>微信昵称:</th>
                                    <td>
                                        <span>${wxNickName!}</span>
                                    </td>
                                </tr>
                                </#if>
                                </tbody>
                            </table>
                        </section>

                        <section class="rightContent addHeadPortrait">
                            <div class="sec-tab">
                                <span class="uploadBtn current">上传头像</span>
                                <span class="pickupBtn">选择头像</span>
                            </div>
                            <!--  上传头像  start  -->
							<form id="form1" action="${basepath}/member/changeHead" method="post" enctype="multipart/form-data">
	                            <section class="upload-avatar">
	                                <div class="upload-block clearfix">
	                                    <div class="upload-tip-text">( 请使用小于2M的 png,jpg,jpeg 格式图片 )</div>
	                                    <div class="upload-btn">
	                                    	<img id="imagePreviewer">
	                                        <input id="imageInputer" name="imageInputer" type="file" onchange="previewImage(this);"/>
	                                        <div class="verifyStyle" style='display:none;'>
				                                <i class="commonIcon errorIcon"></i><span id='img_errorHint' class="errorHint"><span>
				                            </div>
	                                    </div>
	                                </div>
	                            </section>
	                        </form>
                            <!--  上传头像  end  -->
                            <!--  选择默认头像 start -->
                            <section class="pickup-default-avatar" style="display: none;">
                                <div class="default-avatar-list"></div>
                                <span class="changeViewpoints">
                                    <span class="commonIcon changeIcon"></span>换一换
                                </span>
                            </section>
                            <!--  选择默认头像 end -->
                            <div class="bottom-block">
                                <a href="javascript:void(0);" class="commonBtn blueBtn img" id='headImg_blueBtn' flag='upload'>提交</a>
                                <a href="javascript:void(0);" class="commonBtn grayBtn img" id='headImg_grayBtn'>返回</a>
                            </div>
                        </section>

                        <div class="modal fade" id="ac-HintWindow">
						    <div class="modal-dialog" style="width: 450px;">
						        <div class="modal-content">
						            <div class="modal-header">
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						                    <span aria-hidden="true">&times;</span>
						                </button>
						                <h4 class="modal-title">提示</h4>
						            </div>
						            <div class="modal-body">
						                <p></p>
						            </div>
						            <div class="modal-footer">
						                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="$('#ac-HintWindow').modal('hide');">关闭</a>
						            </div>
						        </div>
						    </div>
						</div>

					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="ac-bindPhoneWindow" aria-hidden="true" style="display: none;">
            <div class="modal-dialog" style="width: 450px; height: 180px; margin-top: 406.5px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title">手机号绑定提示</h4>
                    </div>
                    <div class="modal-body">
                        <p class="bindtip">该手机号已是真旅行会员，请更换其他手机号绑定</p>
                    </div>
                </div>
            </div>
        </div>
		<script type="text/html" id="bindwechat_template">
		    <img src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${ticket!''}" style="width:170px;height:170px;"/>
		</script>

		<script type="text/html" id="unbindwechat_template">
		    <img src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${ticket!''}" style="width:170px;height:170px;"/>
		    <p>扫描二维码解除微信号绑定</p>
		</script>

		<script>
    $(function(){
    });
    </script>
</@html.navHeader>