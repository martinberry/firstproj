<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","member/opera/common.js","common/pagination.js","member/opera/mainList.js"]
                                 cssFiles=["css/bootstrap-datepicker.min.css","css/maintain/memberManagement.css"] curModule="会员管理" title="真旅行会员列表">
    <@main_header.header currentMenu="会员管理" currentSubMenu="会员维护">
	</@main_header.header>
	<div class="main-container">
            <section class="whiteBg">
                <form>
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>真实姓名</th>
                                <td>
                                    <input type="text" id="realNameInput" name="realName">
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">真实姓名格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>手 机 号</th>
                                <td>
                                    <input type="text" id="mobileInput" name="mobile">
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">手机号格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>会员 ID</th>
                                <td>
                                    <input type="text" id="memberIdInput" name="memberId">
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">会员ID格式错误</span>
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
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>昵  称</th>
                                <td>
                                    <input type="text" id="nickNameInput" name="nickName">
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">昵称格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>邮  箱</th>
                                <td>
                                    <input type="text" id="emailInput" name="email">
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">邮箱格式错误</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>状  态</th>
                                <td class="radioList" id="status">
                                    <label class="radio active" id="all">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">全部</span>
                                    </label>
                                    <label class="radio" id="normal">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">正常</span>
                                    </label>
                                    <label class="radio" id="suspend">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">挂起</span>
                                    </label>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="280">
                                <col width="110">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>注册日期</th>
                                <td>
                                    <input type="text" style="width: 110px;" class="datepicker startDate hasIcon" id="registerFromDate" name="registerFromDate">
                                    <em> 至 </em>
                                    <input type="text" style="width: 110px;" class="datepicker endDate hasIcon" id="registerToDate" name="registerToDate">
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr>
                                <th>消费金额</th>
                                <td>
                                    <input type="text" style="width: 110px;" class="purchaseAmount" id="purAmountFrom" name="amountFrom">
                                    <div class="verifyStyle" style="width: 220px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts">消费金额错误</span>
                                    </div>
                                    <em> 至 </em>
                                    <input type="text" style="width: 110px;" class="purchaseAmount" id="purAmountTo" name="amountTo">
                                </td>
                            </tr>
                            <tr>
                                <th>地  区</th>
                                <td>
                                    <div class="dropdown" style="width: 110px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id="province">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <#if provList??>
                                            <#list provList as prov>
                                            <li name="${prov.no}"><a href="javascript:void(0);"><#if prov.areaName??>${prov.areaName}</#if></a></li>
                                            </#list>
                                            </#if>
                                        </ul>
                                    </div>
                                    <div class="dropdown" style="width: 110px;" id="city">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
                <input type="hidden" name="pageNo" value="1" />
				<input type="hidden" name="pageSize" value="10" />
                </form>
                <section class="clearfix" id="searchField">
                    <div class="operationBtn">
                        <a href="javascript:void(0);" class="ac-hangUp">挂起</a>
                        <a href="javascript:void(0);" class="ac-unlock">解挂</a>
                    </div>
                    <table class="commonTab">
                        <colgroup>
                            <col width="45px">
                            <col width="9%">
                            <col width="9%">
                            <col width="10%">
                            <col width="10%">
                            <col width="16%">
                            <col width="10%">
                            <col width="7%">
                            <col width="7%">
                            <col width="11%">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>
                                <span class="checkbox allCheckbox"></span>
                            </th>
                            <th>会员ID</th>
                            <th>昵称</th>
                            <th>真实姓名</th>
                            <th>手机号</th>
                            <th>地区</th>
                            <th>消费金额
                                <i class="sortArrow upGray"></i>
                                <!--箭头有以下几种情况-->
                                <!--<i class="sortArrow downGray"></i>
                                <i class="sortArrow allGray"></i>
                                <i class="sortArrow allOrange"></i>-->
                            </th>
                            <th>心愿清单</th>
                            <th>是否绑定微信</th>
                            <th>
                                <div class="dropdown" style="width: 123px;">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts">最近登录时间</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu" id="latestLoginPeriod">
                                        <li class="active"><a href="javascript:void(0);">最近登录时间</a></li>
                                        <li name="ONE_WEEK"><a href="javascript:void(0);">1个星期</a></li>
                                        <li name="ONE_MONTH"><a href="javascript:void(0);">1个月</a></li>
                                        <li name="THREE_MONTH"><a href="javascript:void(0);">3个月</a></li>
                                        <li name="SIX_MONTH"><a href="javascript:void(0);">6个月</a></li>
                                        <li name="ONE_YEAR"><a href="javascript:void(0);">1年</a></li>
                                    </ul>
                                </div>
                            </th>
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

    <div class="modal fade" id="ac-hangUp">
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
                        <span class="popupContainer-fonts">确认要挂起选中的会员吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="suspendBatch" data-dismiss="modal">确认挂起</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">点错了</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="ac-unlock">
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
                        <span class="popupContainer-fonts">确认要解挂选中的会员吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="unlockBatch" data-dismiss="modal">确认解挂</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">点错了</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="noSelect">
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
                        <span class="popupContainer-fonts">请先选中要进行操作的会员</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">知道了</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="suspendError">
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