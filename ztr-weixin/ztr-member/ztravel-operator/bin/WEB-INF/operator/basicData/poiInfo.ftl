<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "/operator/basicData/leftMenu.ftl" as left/>

<@html.htmlIndex jsFiles=["operator/basicData/poiInfo.js", "common/jquery-form.js"] cssFiles=["css/maintain/operatorManagement.css"] curModule="" title="真旅行基础数据">
    <@main_header.header currentMenu="运营管理" currentSubMenu="基础数据">
    </@main_header.header>
    <div class="main-container clearfix">
            <@left.leftMenu curLeftModule="POI_INFO">
            </@left.leftMenu>
            <section class="rightModelContent">
                <#include "/operator/basicData/departurePlace.ftl" />
                <div class="lightGrayLine"></div>
                <#include "/operator/basicData/destination.ftl" />
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