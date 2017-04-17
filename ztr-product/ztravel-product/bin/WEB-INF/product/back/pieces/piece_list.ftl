<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/ZtrDropDown.js",
    "common/pagination.js","product/back/common.js","product/back/pieces/piece_list.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","/css/maintain/productManagement.css"]
    curModule="碎片化产品"
    title="真旅行碎片化产品列表">
    <@main_header.header currentMenu="产品管理" currentSubMenu="碎片化产品">
    <div class="operationDiv"><i class="plusIcon"></i>添加碎片产品</div>
    </@main_header.header>
        <script type="text/javascript">
            var ADDRESS = eval("(${(ADDRESS)!})");
        </script>
        <div class="main-container">
            <section class="whiteBg">
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>ID</th>
                                <td>
                                    <input id='pid_input' type="text"/>
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">产品ID格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>产品名称</th>
                                <td>
                                    <input id='pname_input' type="text"/>
                                    <div class="verifyStyle" style="width: 200px; display: none" maxLength="60'>
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">产品名称格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="140">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>产品种类</th>
                                <td>
                                    <div class="dropdown" style="width: 110px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id='pieceType_dropdown'>
                                            <li class="active" value="ALL"><a href="javascript:void(0);">全部</a></li>
                                            <li value="TICKET"><a href="javascript:void(0);">门票</a></li>
                                            <li value="LOCAL"><a href="javascript:void(0);">当地酷玩</a></li>
                                            <li value="TRAFFIC"><a href="javascript:void(0);">交通接驳</a></li>
                                            <li value="WIFI"><a href="javascript:void(0);">wifi通讯</a></li>
                                            <li value="HOTELUP"><a href="javascript:void(0);">酒店升级</a></li>
                                            <li value="CHARTER"><a href="javascript:void(0);">包车服务</a></li>
                                            <li value="INTELTAXI"><a href="javascript:void(0);">国际租车</a></li>
                                            <li value="VISA"><a href="javascript:void(0);">旅游签证</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="400">
                                <col width="110">
                            </colgroup>
                            <tbody>
                            <tr class="fitToTr">
                                <th class="verticalTop" style="padding-top: 15px;">目的地</th>
                                <td class="clearfix">
                                    <div class="pull-left">
                                        <div class="dropdown" style="width: 110px;" id='address_continent'>
                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                                <span class="activeFonts">全部</span>
                                                <span class="caret"></span>
                                            </a>
                                            <ul class="dropdown-menu">
                                            </ul>
                                        </div>
                                        <div class="dropdown" style="width: 110px;" id='address_country'>
                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                                <span class="activeFonts">全部</span>
                                                <span class="caret"></span>
                                            </a>
                                            <ul class="dropdown-menu">
                                            </ul>
                                        </div>
                                        <div class="dropdown" style="width: 110px;" id='address_region'>
                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                                <span class="activeFonts">全部</span>
                                                <span class="caret"></span>
                                            </a>
                                            <ul class="dropdown-menu">
                                            </ul>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <a href="javascript:searchByParams();" class="lightBlueBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr class="fitToTr">
                                <th>状  态</th>
                                <td class="radioList" id="status">
                                    <label class="radio active" value="ALL">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">全部</span>
                                    </label>
                                    <label class="radio" value="NEW">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">草稿</span>
                                    </label>
                                    <label class="radio" value="RELEASED">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">上线</span>
                                    </label>
                                    <label class="radio" value="OFFLINE">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">关闭</span>
                                    </label>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
                <section class="clearfix">
                    <table class="commonTab pieceProductTab">
                        <colgroup>
                            <col width="8%">
                            <col width="24%">
                            <col width="8%">
                            <col width="12%">
                            <col width="10%">
                            <col width="10%">
                            <col width="7%">
                            <col width="9%">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>产品名称</th>
                            <th>产品类属</th>
                            <th>目的地</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <div id="searchField">
                   </div>

                    <input type="hidden" name="pageNo" value="1" />
                    <input type="hidden" name="pageSize" value="10" />
                </section>
            </section>
        </div>
    </div>


    <div class="modal fade" id="ac-online">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认将产品上线吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button"onClick="onlineProduct();">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton">取消</button>
                    <input type="hidden" id="onlineObjectId">
                    <input type="hidden" id="onlineNature">
                </div>
            </div>
        </div>
    </div>

     <div class="modal fade" id="ac-offline">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认将产品下线吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" onClick="offlineProduct();">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" >取消</button>
                </div>
                <input type="hidden" id="offlineObjectId">
                <input type="hidden" id="offlineNature">
            </div>
        </div>
    </div>

    <div class="modal fade" id="ac-del">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认要删除产品吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" onClick="delProduct();">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton">取消</button>
                </div>
                <input type="hidden" id="delObjectId">
                <input type="hidden" id="delNature">
            </div>
        </div>
    </div>

       <div class="modal fade" id="ac-close">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认要关闭产品吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" onClick="closeProduct();">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton">取消</button>
                </div>
                <input type="hidden" id="closeObjectId">
                <input type="hidden" id="closeNature">
            </div>
        </div>
    </div>

</@html.htmlIndex>