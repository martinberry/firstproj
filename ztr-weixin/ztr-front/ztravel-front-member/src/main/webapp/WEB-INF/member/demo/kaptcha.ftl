<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "/common/opera/main_header.ftl" as main_header/>

<@html.htmlIndex curModule="用户">


	<@main_header.header>
	</@main_header.header>

	<div class="main-container" style="background: #0b8395;overflow: hidden;">

	     <div class="chknumber">
	           <label>验证码：
	           <input name="kaptcha" type="text" id="kaptcha" maxlength="4" class="chknumber_input" />
	           </label>
	            <img src= "${memberServer}${basepath}/captcha-image/10" width="55" height="20" id="kaptchaImage"  style="margin-bottom: -3px"/>
	           <script type="text/javascript">
	            $(function(){
	                $('#kaptchaImage').click(function () {//生成验证码
	                 $(this).hide().attr('src', memberServer + basepath + '/captcha-image/' + Math.floor(Math.random()*100) ).fadeIn(); })
	                      });

	           </script>
	     </div>
	 </div>


</@html.htmlIndex>
