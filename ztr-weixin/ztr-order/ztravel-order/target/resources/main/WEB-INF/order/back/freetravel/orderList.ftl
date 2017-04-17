<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/bootstrap-datepicker.min.css", "css/maintain/orderManagement.css"]
                                  jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "order/back/freetravel/orderList.js", "common/pagination.js"]
                                  curModule="订单管理" title="真旅行自由行订单列表">

    <@main_header.header currentMenu="订单管理" currentSubMenu="自由行订单">
    </@main_header.header>
    <div class="main-container">
            <section class="whiteBg">
            <form id="searchConditionForm">
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="280">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>订 单 号</th>
                                <td>
                                    <input id="orderNo" name="orderNo" type="text" data-cv="required" style="width: 255px;">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>产品标题</th>
                                <td>
                                    <input id="productTitle" name="productTitle" type="text" placeholder="关键字" style="width: 255px;">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>建单日期</th>
                                <td>
                                    <input id="createDateFrom" name="createDateFrom" type="text" style="width: 115px;" class="datepicker startDate hasIcon">
                                    <em> 至 </em>
                                    <input id="createDateTo" name="createDateTo" type="text" style="width: 115px;" class="datepicker endDate hasIcon">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="290">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>产品ID</th>
                                <td>
                                    <input id="productId" name="productId" type="text" style="width: 255px;">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>订单状态</th>
                                <td>
                                    <div class="dropdown" style="width: 255px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id="status">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);" name="UNPAY">待支付</a></li>
                                            <li><a href="javascript:void(0);" name="PAYED">支付成功</a></li>
                                            <li><a href="javascript:void(0);" name="CONFIRM">OP确认</a></li>
                                            <li><a href="javascript:void(0);" name="GIFTSEND">礼盒发放</a></li>
                                            <li><a href="javascript:void(0);" name="OUTNOTICE">出行通知</a></li>
                                            <li><a href="javascript:void(0);" name="OUTTING">出行中</a></li>
                                            <li><a href="javascript:void(0);" name="COMPLETED">已完成</a></li>
                                            <li><a href="javascript:void(0);" name="REFUNDING">退款中</a></li>
                                            <li><a href="javascript:void(0);" name="REFUNDFAILED">退款失败</a></li>
                                            <li><a href="javascript:void(0);" name="CANCELED">已取消</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>订单金额</th>
                                <td>
                                    <input id="orderPriceLowerBound" name="orderPriceLowerBound" class="orderPrice" type="text" style="width: 115px;" value="">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                    <em> 至 </em>
                                    <input id="orderPriceUpperBound" name="orderPriceUpperBound" class="orderPrice" type="text" style="width: 115px;" value="">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="100">
                                <col width="290">
                                <col width="110">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>游客姓名</th>
                                <td>
                                    <input id="travellerNames" name="travellerNames" type="text" data-cv="required" style="width: 255px;" maxlength="30">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr>
                                <th>订单来源</th>
                                <td>
                                    <div class="dropdown" style="width: 255px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id="source">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);" name="WEIXIN">微信</a></li>
                                            <li><a href="javascript:void(0);" name="WEB">web</a></li>
                                        </ul>
                                    </div>
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
                            <col width="8%">
                            <col width="12%">
                            <col width="8%">
                            <col width="20%">
                            <col width="10%">
                            <col width="10%">
                            <col width="8%">
                            <col width="5%">
                            <col width="5%">
                            <col width="7%">
                            <col width="7%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>订单状态</th>
                            <th>订单号</th>
                            <th>产品ID</th>
                            <th>产品标题</th>
                            <th>套餐名称</th>
                            <th>建单时间</th>
                            <th>游客姓名</th>
                            <th>订单金额</th>
                            <th>订单来源</th>
                            <th class="text-center">订单操作</th>
                            <th>子订单状态</th>
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