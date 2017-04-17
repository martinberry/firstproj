<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="登录">


		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:500px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="earch-icon"></span>
						<span class="title">欢迎加入真旅行</span>
					</div>
					<div class="cont-block">
                        <div class="registerForm">
                            <div type="text" class="registerErrorRow errorBoxContent">
                                <i class="commonIcon errorIcon"></i>请输入手机验证码
                            </div><br/>
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon phoneNumberIcon"></i>
                                <input type="text" placeholder="请输入手机号" style="width: 309px;"/>
                            </div><br/>
                            <div class="componentInput verificationCode messageCodeRow">
                                <i class="commonIcon verificationCodeIcon"></i>
                                <input type="text" placeholder="请输入验证码" style="width: 174px;">
                                <span class="getVerificationCode">获取短信验证码</span>
                            </div><br/>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigOrangeBtn" style="width:345px;">注   册</a>
                                <!--<a href="javascript:void(0);" class="bigOrangeBtn active" style="width:345px;">注   册</a>-->
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>

<script type="text/javascript">  

$(document).ready(function () {
		$(".registerBtn").click(function() {
		var loginfo = {};
		
		loginfo.userName = $(".mobileFile").val();
		loginfo.password = $(".messageCodeFile").val();		
		
		$.ajax({
				type : "POST",
				url : "../login/signIn",
				data : JSON.stringify(loginfo),
				
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "json",
				success : function(result) {
					if(result.res_code = '001') {
						window.location.href = memberServer + basepath + "login/success";
					}
				}
			});
		});
		
   });
   
</script>   


</@html.htmlIndex>

