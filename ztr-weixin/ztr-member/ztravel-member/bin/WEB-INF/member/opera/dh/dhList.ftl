<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/bootstrap-datepicker.min.css", "css/maintain/orderManagement.css"]
                                  jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "member/opera/dhList.js" ,"common/pagination.js"]
                                  curModule="订单管理" title="真旅行自由行订单列表">

    <@main_header.header currentMenu="订单管理" currentSubMenu="兑换订单" >
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
                                <th>兑换单ID</th>
                                <td>
                                    <input id="orderNo" name="dhId" type="text" data-cv="required" style="width: 255px;">
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
                                         <ul class="dropdown-menu"  id="status">
                                            <li data-val="" class="active"><a href="javascript:void(0);">所有状态</a></li>
                                            <li data-val="UNCONVERT"><a href="javascript:void(0);" name="unconvert">待兑换</a></li>
                                            <li data-val="CONVERTED"><a href="javascript:void(0);" name="converted">已兑换</a></li>
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
                                <col width="110">
                                <col width="280">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>会员ID</th>
                                <td>
                                    <input id="memberId" name="memberId" type="text" style="width: 255px;">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                             <tr>
                                <th>申请兑换时间</th>
                                <td>
                                    <input id="createDateFrom" name="pledhTimeFrom" type="text" style="width: 115px;" class="datepicker startDate hasIcon">
                                    <em> 至 </em>
                                    <input id="createDateTo" name="pledhTimeTo" type="text" style="width: 115px;" class="datepicker endDate hasIcon">
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </li>

                    <li>
                   <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="370">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th></th>
                                    <td style="text-align:right;">
                                        <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>查 询</a>
                                    </td>
                                </tr>
                                <tr>
                                <th>兑换金额</th>
                                <td>
                                    <input id="orderPriceLowerBound" name="dhPriceLowerBound" class="orderPrice" type="text" style="width: 115px;" value="">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                    <em> 至 </em>
                                    <input id="orderPriceUpperBound" name="dhPriceUpperBound" class="orderPrice" type="text" style="width: 115px;" value="">
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
                            <col width="10%">
                            <col width="8%">
                            <col width="12%">
                            <col width="10%">
                            <col width="8%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>兑换单ID</th>
                            <th>会员ID</th>
                            <th>兑换申请时间</th>
                            <th>兑换状态</th>
                            <th>本次兑换金额</th>
                            <th>兑换内容</th>
                            <th class="text-center">操作</th>
                            <th>最后操作人</th>
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
<#include "dhDetail.ftl" />
</@html.htmlIndex>
