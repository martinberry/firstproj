 <#macro activityMenu curStatus="" operation="" activityType="">
 <header class="main-header hoveringModel">
            <div class="stairMenuContent">
                <div class="wrap clearfix">
                    <div class="navLeft">
                        <a class="logo" href="/"></a>
                        <ul class="stairMenu">
	                        <#if activityType == "SYSTEMSEND">
	                        	<li class="editFonts">新增送券活动</li>
	                        </#if>
                        	<#if activityType == "CUSTOMERGET">
                            	<li class="editFonts">新增领券活动</li>
                            </#if>
                        </ul>
                    </div>
                    <#include "/common/opera/header_right.ftl" />
                </div>
            </div>
            <div class="headSecondLevel headBtn clearfix">
                <span class="leftBtn">
                    <a href="${basepath!''}/activity/list" class="returnBtn">< 返回</a>
                </span>
                <span class="rightBtn">
                	<#if curStatus == "DRAFT">
                    	<button class="commonButton red-fe6869Button effect" id="effect">活动生效</button>
	                    <button class="commonButton orange-f79767Btn draft" id="saveDraft">保存草稿</button>
                    </#if>
                    <#if curStatus == "EFFECTIVE">
                    	<button class="commonButton red-fe6869Button termination" id="terminate">强制终止</button>
                    	<#if activityType == "SYSTEMSEND">
                    	<button class="commonButton orange-f79767Btn deliver" id="grant">送券</button>
                    	</#if>
                    	<#if activityType == "CUSTOMERGET">
                    	<button class="commonButton orange-f79767Btn deliver submitCus" id="submitCus">提交</button>
                    	</#if>
                    </#if>
                </span>
            </div>
        </header>
        <#nested/>
</#macro>