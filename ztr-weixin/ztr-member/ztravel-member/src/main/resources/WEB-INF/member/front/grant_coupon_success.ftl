<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="领券成功" jsFiles=["member/front/common_utils.js"] cssFiles=["css/client/register.css"]>
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:500px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="earch-icon"></span>
						<span class="title">领券成功</span>
					</div>
					<div class="cont-block">
                        <div class="registerForm">
							<div type="text" class="registerErrorRow errorBoxContent">
                                <i class="commonIcon errorIcon"></i>领取代金券成功
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
</@html.htmlIndex>