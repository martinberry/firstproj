<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","js/vendor/kindeditor.js","common/ZtrDropDown.js",
    "product/back/pieces/unvisa_additional.js","product/back/common_utils.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","css/jquery.tagsinput.css","css/maintain/productManagement.css"]
    curModule="产品管理"
    title="附加信息">

    <@pieceMenu.pieceMenu curPieceModule="additionalInfo">
        <div class="main-container changeMainContent">
            <input id='productId_hidden' type='hidden' value='${(additionalInfo.id)!}'/>
            <input id='productPid_hidden' type='hidden' value='${(additionalInfo.pid)!}'/>
            <input id='progress_hidden' type='hidden' value='${(additionalInfo.progress)!}'/>
            <section class="whiteBg">
                <div class="additionInfoDiv">
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">产品介绍</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['FEATURES'])!}</textarea>
                        </form>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">使用说明</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['INTRODUCTIONS'])!}</textarea>
                        </form>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">预订须知</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['BOOKING'])!}</textarea>
                        </form>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">费用说明</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['FEE_DETAIL'])!}</textarea>
                        </form>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">退改政策</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['REFUND_POLICY'])!}</textarea>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    </@pieceMenu.pieceMenu>
</@html.htmlIndex>