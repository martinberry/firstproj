				<!-- 用户评价start -->
	                <div class="commonModel">
	                    <div id="userJudge"></div>
	                    <div class="commonTitle"><i class="visaIcon userIcon"></i>用户评价<span class="judgeNum">(共<span>${commentNum!0}</span>人点评)</span></div>
	                    <#if commentList?? && commentList?size gt 0>
		                    <div class="userModel">
		                    	<#list commentList as comment>
			                        <div class="userJudge clearfix">
			                            <div class="userLeft">
			                                <img class="user-img" <#if comment.headImgId??>src="${mediaserver}imageservice?mediaImageId=${(comment.headImgId)!}"</#if> alt="用户图像">
			                                <div class="user-name">${(comment.memNickName)!''}</div>
			                                <div class="user-time">${(comment.date)!''}</div>
			                            </div>
			                            <div class="userRight">
			                                <ul class="list-inline">
			                                    <li class="yellowStar"></li>
			                                    <li class="yellowStar"></li>
			                                    <li class="yellowStar"></li>
			                                    <li class="yellowStar"></li>
			                                    <li class="yellowStar"></li>
			                                </ul>
			                                <div class="user-judge">
			                                   ${(comment.comment)!''}
			                                </div>
			                            </div>
			                        </div>
		                        </#list>
		                    </div>
	                    </#if>
	                </div>
                 <!-- 用户评价end -->