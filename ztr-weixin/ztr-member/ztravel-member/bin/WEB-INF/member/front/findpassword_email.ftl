<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="找回密码-邮箱" jsFiles=["member/front/common_utils.js", "member/front/find_password_email.js"] cssFiles=["css/client/register.css"]>
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:500px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="findPsw-icon"></span>
						<span class="title">找回密码</span>
					</div>
					<div class="cont-block">
                        <div class="findPswBox">
                        	<div type="text" class="registerErrorRow errorBoxContent"
                        		<#if !errorHint?? || errorHint == ''>
                        		style="display:none;"
                        		</#if>
                        		>
                                <i class="commonIcon errorIcon"></i><span class="errorHint">${errorHint!''}<span>
                            </div>
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon smallEmailIcon"></i>
                                <input id="emailInputer" maxlength="50" type="text" placeholder="请输入你的邮箱" style="width: 304px;"/>
                            </div>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigBlue11b9b7Btn" style="width:345px;">确   认</a>
                            </div>
                            <div class="goBackRow">
                                <i class="goBackIcon"></i><a href="javascript:history.go(-1);">返回</a>
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
</@html.htmlIndex>