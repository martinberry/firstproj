<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "order/back/orderComment/commentList.js", "common/pagination.js"]
                                 cssFiles=["css/maintain/judgeManagement.css", "css/bootstrap-datepicker.min.css"] title="真旅行评价列表">
    <@main_header.header currentMenu="运营管理" currentSubMenu="评价管理">
    </@main_header.header>
    <div class="main-container">
        <section class="whiteBg">
            <form id="searchConditionForm">
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="120">
                                <col width="260">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>评论产品标题</th>
                                <td>
                                    <input type="text" data-cv="" id="productTitle" name="productTitle" style="width: 255px;">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>评论时间</th>
                                <td>
                                    <input type="text" style="width: 110px;" class="datepicker startDate hasIcon" name="dateBegin">
                                    <em>至</em>
                                    <input type="text" style="width: 110px;" class="datepicker endDate hasIcon" name="dateEnd">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="120">
                                <col width="260">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>评论用户ID</th>
                                <td>
                                    <input type="text" data-cv="email" data-ct="" id="memberId" name="mid" style="width: 200px;">
                                    <div class="verifyStyle" style="width: 200px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>评论状态</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id="status">
                                            <li class="active"><a href="javascript:void(0);" name="ALL">全部</a></li>
                                            <li><a href="javascript:void(0);" name="COMMITED">待审核</a></li>
                                            <li><a href="javascript:void(0);" name="PUBLISHED">已通过</a></li>
                                            <li><a href="javascript:void(0);" name="RETURN">未通过</a></li>
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
                                <col width="120">
                            </colgroup>
                            <tbody>
                            <tr>
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
            </form>
            <section class="clearfix" id="searchField">
                <table class="commonTab">
                    <colgroup>
                        <col width="45px">
                        <col width="9%">
                        <col width="9%">
                        <col width="10%">
                        <col width="11%">
                        <col width="16%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>评论内容</th>
                        <th>产品标题</th>
                        <th>用户ID</th>
                        <th>评论时间</th>
                        <th>评论状态</th>
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
</@html.htmlIndex>