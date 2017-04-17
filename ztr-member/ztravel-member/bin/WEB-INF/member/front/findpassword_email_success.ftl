<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="找回密码-邮箱发送成功" cssFiles=["css/client/register.css"]>
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
                            <div class="tipsIconRow">
                                <i class="bigSuccess"></i>
                            </div>
                            <div class="tipsFonts">
                                <p class="fs18 fc484848">成功发送到邮箱</p>
                                <p class="fs14 fc818181">请至邮箱获取链接重设密码</p>
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
</@html.htmlIndex>