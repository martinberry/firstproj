<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex jsFiles=["product/back/hotel/hotelList.js", "common/pagination.js"]
                                 cssFiles=["css/maintain/productManagement.css"] curModule="产品管理" title="真旅行酒店列表">
    <@main_header.header currentMenu="产品管理" currentSubMenu="酒店资源">
        <div class="operationDiv"><a href="${basepath}/product/back/hotel/add" class="greenFontsLink"><i class="plusIcon"></i>添加酒店</a></div>
	</@main_header.header>

	<div class="main-container">
            <section class="whiteBg">
            <!--     <form>  -->
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>酒店名称</th>
                                <td>
                                    <input type="text" id="hotelNameInput" />
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">仅可输入4-60位字符以内的中文、字母、数字及常用字符</span>
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
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="70">
                                <col width="250">
                                <col width="110">
                            </colgroup>
                            <tbody>
                            <tr class="fitToTr">
                                <th>状  态</th>
                                <td class="radioList radioContent" id="statusRadio">
                                    <label class="radio active" name="all">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">全部</span>
                                    </label>
                                    <label class="radio" name="complete">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">完成</span>
                                    </label>
                                    <label class="radio" name="draft">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">草稿</span>
                                    </label>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <input type="hidden" name="pageNo" value="1" />
				    <input type="hidden" name="pageSize" value="10" />
                </ul>
               <!-- </form> -->
                <section class="clearfix" id="searchField">
                    <table class="commonTab productTab">
                        <colgroup>
                            <col width="6%">
                            <col width="11%">
                            <col width="7%">
                            <col width="8%">
                            <col width="7%">
                            <col width="6%">
                            <col width="13%">
                            <col width="9%">
                            <col width="4%">
                            <col width="22%">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>酒店名称</th>
                            <th>国家</th>
                            <th>城市/景点</th>
                            <th>酒店类型</th>
                            <th>星级</th>
                            <th>卖点</th>
                            <th>电话</th>
                            <th>状态</th>
                            <th>地址</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <div id="pagination">
                    </div>
                </section>
            </section>
    </div>

    <div class="modal fade" id="dialog-deleteHotel">
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
                        <span class="popupContainer-fonts">确认要删除该酒店资源吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="deleteHotel">确认删除</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">点错了</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="deleteErrorDlg">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">错误</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</@html.htmlIndex>