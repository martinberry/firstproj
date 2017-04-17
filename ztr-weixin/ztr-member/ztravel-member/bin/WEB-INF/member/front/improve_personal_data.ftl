<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="完善个人资料" localCssFiles=["member/front/round_image.css"] jsFiles=["member/front/jquery-form.js", "member/front/common_utils.js", "member/front/improve_personal_data.js"] cssFiles=["css/client/register.css"]>
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box info" id="main-box" style="width:835px;height: 643px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="title-flight-icon" style="margin-left:65px;"></span>
						<span class="title">欢迎开启真旅行</span>
						<span class="skip-container">
							<span class="skip-icon"></span>
							<span class="skip-text info">跳过</span>
						</span>
					</div>
					<div class="cont-block">
						<div class="sub-title">完善个人资料</div>
						<input id="selectedImageId" type="hidden" value="${randomDefaultImageId!''}">
						<div class="avatar-container">
							<img style="width:95px;height:95px;" class="round_photo" src="${mediaserver}imageservice?mediaImageId=${randomDefaultImageId!''}" alt="默认头像">
							<!--<img src="${host}/images/client/avatar-example.jpg" alt="默认头像">-->
							<span class="commonIcon smallEditIcon"></span>
						</div>
						<div class="refine-info-block" style="padding-bottom:60px;">
							<div type="text" style="width: 340px;display:none;" class="errorBoxContent">
							    <i class="commonIcon errorIcon"></i><span class="errorHint"><span>
							</div>
							<br/>
							<div class="componentInput nickname">
							    <i class="commonIcon personHeaderIcon"></i>
							    <input id="nickNameInputer" value="${randomNickName!''}" maxlength="20" type="text" placeholder="请输入昵称" style="width: 266px;">
							    <span class="commonIcon refreshIcon"></span>
							    <span class="input-tip-pic"></span>
							</div>
							<br/>
							<div class="componentInput">
							    <i class="commonIcon emailIcon"></i>
							    <input id="emailInputer" maxlength="50" type="text" placeholder="确保账户安全,请输入准确邮箱地址" style="width: 300px;">
							</div>
							<br/>
							<p class="rewardPlan a"><em id="recommender">注册推荐人</em>将获得现金奖励，详情请查看<a href="${memberServer}/member/recommenderRewardPlan" target="_blank">《推荐人奖励计划》</a></p>
							<p class="rewardPlan b" style='display:none;'><em>该手机号码未注册</em></p>
							<div class="componentInput recommendPerson"">
							    <i class="commonIcon phoneNumberIcon"></i>
							    <input id="recommenderInputer" type="text" placeholder="请填写推荐人手机号(非必填)" style="width: 300px;">
							    <input id="recommenderInputerId" type="hidden">
							</div>
							<br/>
							<a href="javascript:void(0);" class="bigOrangeBtn active">完  成</a>
						</div>
					</div>
				</div>
			</div>

			<div class="main-box avatar" id="main-box" style="width:835px;display:none;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="title-flight-icon" style="margin-left:65px;"></span>
						<span class="title">欢迎开启真旅行</span>
						<span class="skip-container">
							<span class="skip-icon"></span>
							<span class="skip-text avatar">跳过</span>
						</span>
					</div>
					<div class="cont-block">
						<div class="sec-tab">
							<span class="uploadBtn current">上传头像</span>
							<em class="decoraline"></em>
							<span class="pickupBtn">选择头像</span>
							<div class="slidline"></div>
						</div>
						<form id="form1" action="${ssoServer}/sso/saveHead" method="post" enctype="multipart/form-data">
							<!--  上传头像  start  -->
							<section class="upload-avatar">
								<div class="upload-block clearfix">
									<div class="upload-btn">
										<img id="imagePreviewer">
										<input id="imageInputer" name="imageInputer" type="file" onchange="previewImage(this);"/>
										<div type="text" style="width: 340px;display:none;" class="verifyStyle">
										    <i class="commonIcon errorIcon"></i><span class="img_errorHint"><span>
										</div>
									</div>
								</div>
								<div class="upload-tip-text">( 请使用小于2M的 png,jpg,jpeg 格式图片 )</div>
							</section>
							<!--  上传头像  end  -->
						</form>

						<!--  选择默认头像 start -->

						<section class="pickup-default-avatar" style="display: none;">
							<div class="default-avatar-list">
							</div>
							<span class="changeViewpoints">
							    <span class="commonIcon changeIcon"></span>换一换
							</span>
						</section>

						<!--  选择默认头像 end -->
					</div>
					<div class="bottom-block">
						<a href="javascript:void(0);" class="commonBtn blueBtn upload" id="headImg_blueBtn" flag="upload">提交</a>
						<a href="javascript:void(0);" class="commonBtn grayBtn">取消</a>
					</div>
				</div>
			</div>

		</div>
</@html.htmlIndex>