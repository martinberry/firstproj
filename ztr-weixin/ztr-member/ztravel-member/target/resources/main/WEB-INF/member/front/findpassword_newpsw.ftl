<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="找回密码-新密码" jsFiles=["member/front/common_utils.js", "member/front/reset_password.js","common/password_verify.js"] cssFiles=["css/client/register.css"]>
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
                        	<div type="text" class="registerErrorRow errorBoxContent" style="display:none;">
                                <i class="commonIcon errorIcon"></i><span class="errorHint"><span>
                            </div>
                            <div class="componentInput  mobileRow">
                                <i class="fonts-innerInput">新密码</i>
                                <input type="password" id="newPasswordInputer" placeholder="请输入8-28位密码" style="width: 269px;"/>
                            </div>
                            <div class="componentInput  messageCodeRow  PWStrong">
                                <i class="fonts-innerInput">再次输入</i>
                                <input type="password" id="againPasswordInputer" placeholder="请再次输入密码" style="width: 229px;"/>
                            	<span class="strength weak">弱</span>
                            </div>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigBlue11b9b7Btn" style="width:345px;">提   交</a>
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
		<div class="modal fade" id="ac-reset-ok">
		    <div class="modal-dialog" style="width: 450px;">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		                <h4 class="modal-title">提示</h4>
		            </div>
		            <div class="modal-body">
		                <p>密码修改成功</p>
		            </div>
		            <div class="modal-footer">
		                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="javascript:popCheck();">确认</a>
		            </div>
		        </div>
		    </div>
		</div>
</@html.htmlIndex>