<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />
<#import "/operator/basicData/leftMenu.ftl" as left />

<@html.htmlIndex jsFiles=["operator/basicData/productProperty.js"] cssFiles=["css/maintain/operatorManagement.css"] curModule="" title="真旅行基础数据">
    <@main_header.header currentMenu="运营管理" currentSubMenu="基础数据">
    </@main_header.header>
    <div class="main-container clearfix">
            <@left.leftMenu curLeftModule="PRODUCT_PROPERTY">
            </@left.leftMenu>
            <section class="rightModelContent">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">产品属性信息</h3>
                </div>
                <div class="selectClassify clearfix">
                    <div class="leftClassifyBtn">
                        <div class="modifiedTheme">
                            <button class="commonButton blue-45c8dcButton">修改产品主题</button>
                        </div>
                        <div class="saveCancelBtn">
                            <button class="commonButton orange-f79767Btn" id="saveThemesBtn">确 认</button>
                            <button class="commonButton gray-bbbButton">取 消</button>
                        </div>
                    </div>
                    <div class="rightClassifyInfo">
                        <textarea class="commonTextarea themeText" readonly="readonly" id="themeTextarea">${themes!''}</textarea>
                    </div>
                </div>
                <div class="selectClassify clearfix">
                    <div class="leftClassifyBtn">
                        <div class="modifiedLabel">
                            <button class="commonButton blue-45c8dcButton">修改产品标签</button>
                        </div>
                        <div class="saveCancelBtn">
                            <button class="commonButton orange-f79767Btn" id="saveTagsBtn">确 认</button>
                            <button class="commonButton gray-bbbButton">取 消</button>
                        </div>
                    </div>
                    <div class="rightClassifyInfo">
                        <textarea class="commonTextarea labelText" readonly="readonly" id="tagTextarea">${tags!''}</textarea>
                    </div>
                </div>
            </section>
    </div>

    <div class="modal fade commonInitialize" id="messageDlg">
        <div class="modal-dialog" style="width: 350px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i></i>
                        <span class="popupContainer-fonts"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">确 认</button>
                </div>
            </div>
        </div>
    </div>
</@html.htmlIndex>