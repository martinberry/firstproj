<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="真旅行-密码找回" jsFiles=["member/front/find_password.js"] cssFiles=["css/client/register.css"]>
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
                            <div class="findByMobile current">
                                <i class="byMobileIcon"></i><span style="cursor:pointer;">手机获取动态码登录</span>
                            </div>
                            <div class="findByEmail">
                                <i class="byEmailIcon"></i><span style="cursor:pointer;">通过邮箱找回密码</span>
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
</@html.htmlIndex>