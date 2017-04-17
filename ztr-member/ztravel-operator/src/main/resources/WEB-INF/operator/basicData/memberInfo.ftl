<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />
<#import "/operator/basicData/leftMenu.ftl" as left />

<@html.htmlIndex jsFiles=["operator/basicData/memberInfo.js", "common/jquery-form.js"] cssFiles=["css/maintain/operatorManagement.css"] curModule="" title="真旅行基础数据">
    <@main_header.header currentMenu="运营管理" currentSubMenu="基础数据">
    </@main_header.header>
    <div class="main-container clearfix">
            <@left.leftMenu curLeftModule="MEMBER_INFO">
            </@left.leftMenu>
            <section class="rightModelContent">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">名称AB库</h3>
                </div>
                <div class="uploadData">
                    <button class="commonButton blue-45c8dcButton">上传数据</button>
                    <!-- <span class="fileFonts">未上传文件</span> -->
                    <form id="abLibForm" method="POST" enctype="multipart/form-data" action="${basepath}/operation/basicData/uploadNicknameABLib">
                        <input type="file" id="abLibInputer" name="nickNameABLibFile" />
                    </form>
                </div>
                <div class="titleContent clearfix" style="margin-top:100px;">
                    <h3 class="titleFonts18">用户国籍</h3>
                </div>
                <div class="uploadData">
                    <button class="commonButton blue-45c8dcButton">上传数据</button>
                    <!-- <span class="fileFonts">未上传文件</span> -->
                    <form id="countryForm" method="POST" enctype="multipart/form-data" action="${basepath}/operation/basicData/uploadCountryFile">
                        <input type="file" id="countryInputer" name="countryFile" />
                    </form>
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