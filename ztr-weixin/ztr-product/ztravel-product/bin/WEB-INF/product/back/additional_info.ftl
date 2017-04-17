<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "product_menu.ftl" as productMenu/>
<@html.htmlIndex jsFiles=["js/vendor/kindeditor.js","js/maintain/customValidator.js","common/KEditor_insertFile.js","product/back/additional_info.js","product/back/common_utils.js"]
	cssFiles=["css/maintain/productManagement.css"]
	curModule="产品管理"
	title="附加信息">
	<@productMenu.productMenu curProductModule="additionalInfo">
    <div class="main-container changeMainContent">
    	<input type='hidden' id='productId_hidden' value='${(data.id)!}'/>
    	<input type='hidden' id='productPid_hidden' value='${(data.pid)!}'/>
    	<input type='hidden' id='status_hidden' value='${(data.status)!}'/>
    	<input type='hidden' id='progress_hidden' value='${(data.progress)!}'/>
    	<input type='hidden' id='accessType' value="${(mode)!}"/>
        <section class="whiteBg">
            <div class="additionInfoDiv">
                <div class="editorCommonModel">
                    <h4 class="editorModelTitle" value="FEE_INCLUDE">费用包含</h4>
                    <form>
                        <textarea name="content" class="editorText">${(data.additionalInfos['FEE_INCLUDE'])!}</textarea>
                    </form>
                </div>
                <div class="editorCommonModel">
                    <h4 class="editorModelTitle" value="GIFT_ITEMS">真旅行赠送项目</h4>
                    <form>
                        <textarea name="content" class="editorText">${(data.additionalInfos['GIFT_ITEMS'])!}</textarea>
                    </form>
                </div>
                <div class="editorCommonModel">
                    <h4 class="editorModelTitle" value="FEE_NOT_INCLUDE">费用不含</h4>
                    <form>
                        <textarea name="content" class="editorText">${(data.additionalInfos['FEE_NOT_INCLUDE'])!}</textarea>
                    </form>
                </div>
                <div class="editorCommonModel">
                    <h4 class="editorModelTitle" value="VISA">签证须知</h4>
                    <form>
                        <textarea name="content" class="editorText">${(data.additionalInfos['VISA'])!}</textarea>
                    </form>
                </div>
                <div class="editorCommonModel">
                    <h4 class="editorModelTitle" value="BOOKING">预定须知</h4>
                    <form>
                        <textarea name="content" class="editorText">${(data.additionalInfos['BOOKING'])!}</textarea>
                    </form>
                </div>

                 <div class="editorCommonModel">
                    <h4 class="editorModelTitle" value="REFUND_POLICY">退改政策</h4>
                    <form>
                        <textarea name="content" class="editorText">${(data.additionalInfos['REFUND_POLICY'])!}</textarea>
                    </form>
                </div>

                <div class="localTips">
                    <h4 class="editorModelTitle">出行Tips</h4>
                    <form>
                        <ul class="facilityList clearfix" style="margin-bottom: 0;">
                            <li class="active">气候</li>
                            <li class="">消费</li>
                            <li class="">通信</li>
                            <li class="">当地风俗</li>
                            <li class="">其他</li>
                        </ul>
                        <div class="facilityContent">
                            <textarea name="WEATHER" class="editorText">${(data.travelTips['WEATHER'])!}</textarea>
                            <textarea name="EXPEND"  class="editorText" style="visibility: hidden;">${(data.travelTips['EXPEND'])!}</textarea>
                            <textarea name="COMMUNICATION" class="editorText" style="visibility: hidden;">${(data.travelTips['COMMUNICATION'])!}</textarea>
                            <textarea name="CUSTOM" class="editorText" style="visibility: hidden;">${(data.travelTips['CUSTOM'])!}</textarea>
                            <textarea name="OTHER" class="editorText" style="visibility: hidden;">${(data.travelTips['OTHER'])!}</textarea>
                        </div>
                    </form>
                </div>

            </div>
        </section>
    </div>
	</@productMenu.productMenu>
</@html.htmlIndex>