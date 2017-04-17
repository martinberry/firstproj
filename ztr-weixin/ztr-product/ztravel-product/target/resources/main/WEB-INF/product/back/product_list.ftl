<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","product/back/product_list.js","common/pagination.js","product/back/common.js"]
                                 cssFiles=["css/bootstrap-datepicker.min.css","/css/maintain/productManagement.css"] curModule="产品维护"
                                 title="真旅行产品列表"
                                 >
    <@main_header.header currentMenu="产品管理" currentSubMenu="产品维护">
    <div class="operationDiv"><i class="plusIcon"></i>添加自由行</div>
	</@main_header.header>
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
                                <th>产品ID</th>
                                <td>
                                    <input type="text" id="searchId"/>
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">产品ID格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>产品标题</th>
                                <td>
                                    <input type="text" id="title"/>
                                    <div class="verifyStyle" style="width: 200px; display: none" maxLength="60'>
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">产品名称格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>产品类型</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id="style">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
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
                                <col width="140">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>出 发 地</th>
                                <td>
                                    <div class="dropdown" style="width: 110px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id="from">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <#if departurePlaces??>
                                            <#list departurePlaces as departPlace>
                                            <li><a href="javascript:void(0);">${departPlace!''}</a></li>
                                            </#list>
                                            </#if>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>产品主题</th>
                                <td>
                                    <div class="dropdown" style="width: 110px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id="subject">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <#if themes??>
                                            <#list themes as theme>
                                            <li><a href="javascript:void(0);">${theme!''}</a></li>
                                            </#list>
                                            </#if>
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
                                <col width="360">
                                <col width="110">
                            </colgroup>
                            <tbody>

                             <tr>
                                <th>目 的 地</th>
                                <td>
                                    <div class="dropdown" style="width: 110px;" id="continent">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">不限</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">不限</a></li>
                                            <#if continentList??>
                                            <#list continentList as continent>
                                            <li><a href="javascript:void(0);">${continent}</a></li>
                                            </#list>
                                            </#if>
                                        </ul>
                                    </div>
                                    <div class="dropdown" style="width: 110px;" id="country">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">不限</span>
                                            <span class="caret"></span>
                                        </a>
                                    </div>
                                    <div class="verifyStyle" style="width: 225px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">请选择国家</span>
                                    </div>
                                    <div class="dropdown" style="width: 110px;" id="city">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">不限</span>
                                            <span class="caret"></span>
                                        </a>
                                    </div>
                                </td>
                                <td>
                                    <a href="javascript:searchByParams();" class="lightBlueBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr class="fitToTr">
                                <th>状  态</th>
                                <td class="radioList" id="status">
                                  <label class="radio active">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">全部</span>
                                    </label>
                                    <label class="radio">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">草稿</span>
                                    </label>
                                    <label class="radio">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">上线</span>
                                    </label>
                                    <label class="radio">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">关闭</span>
                                    </label>
                                      <label class="radio">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">过期</span>
                                    </label>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
                <section class="clearfix">
                <table class="commonTab productTab">
                        <colgroup>
                            <col width="9%">
                            <col width="24%">
                            <col width="8%">
                            <col width="8%">
                            <col width="6%">
                            <col width="14%">
                            <col width="9%">
                            <col width="9%">
                            <col width="5%">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>产品ID</th>
                            <th>产品标题</th>
                            <th>产品主题</th>
                            <th>产品类型</th>
                            <th>出发地</th>
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
            </div>
        </div>
    </div>

</@html.htmlIndex>