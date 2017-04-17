<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/bootstrap-datepicker.min.css", "css/maintain/orderManagement.css"]
                                  jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "order/back/commonorder/commonorderList.js", "common/pagination.js"]
                                  curModule="订单管理" title="退补款申请单列表">

    <@main_header.header currentMenu="订单管理" currentSubMenu="退补款申请单">
    </@main_header.header>
    <div class="main-container">
            <section class="whiteBg">
            <form id="searchConditionForm">

                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="120">
                                <col width="280">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>关联主订单号</th>
                                <td>
                                    <input id="orderNo" name="orderNo" type="text" data-cv="required" style="width: 255px;">
                                      <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>订单号</th>
                                <td>
                                    <input id="orderId" name="orderId" type="text" data-cv="required" style="width: 255px;">
                                      <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>


                            </tbody>
                            </table>
                            </li>

                            <li>
                            <table class="searchTab">
                            <colgroup>
                                <col width="90">
                                <col width="290">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>建单日期</th>
                                <td>
                                    <input id="createDateFrom" name="createDateFrom" type="text" style="width: 115px;" class="datepicker startDate hasIcon">
                                    <em> 至 </em>
                                    <input id="createDateTo" name="createDateTo" type="text" style="width: 115px;" class="datepicker endDate hasIcon">
                                </td>
                            </tr>



                            <tr>
                                <th>游客姓名</th>
                                <td>
                                    <input id="travellerNames" name="travellerNames" type="text" maxlength="30" data-cv="required" style="width: 255px;">
                                    <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>


                            </tbody>
                        </table>
                    </li>

                    <li>
                      <table class="searchTab">
                            <colgroup>
                                 <col width="90">
                                <col width="290">
                                <col width="110">
                            </colgroup>
                            <tbody>
                          <tr>
                                <th>操作状态</th>
                                <td>
                                    <div class="dropdown" style="width: 255px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);" name="REFUNDED">已退款</a></li>
                                            <li><a href="javascript:void(0);" name="REFUNDING">待退款</a></li>
                                            <li><a href="javascript:void(0);" name="UNPASS">审核不通过</a></li>
                                            <li><a href="javascript:void(0);" name="AUDIT">待审核</a></li>
                                            <li><a href="javascript:void(0);" name="CANCELED">已取消</a></li>
                                            <li><a href="javascript:void(0);" name="UNPAY" style="display: none;">待支付</a></li>
                                            <li><a href="javascript:void(0);" name="PAYED" style="display: none;">已支付</a></li>

                                        </ul>
                                    </div>
                                </td>
                                <td><a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a></td>
                            </tr>

                          </tbody>
                        </table>
                    </li>
                </ul>
               <input type="hidden" name="pageNo" value="1" />
			   <input type="hidden" name="pageSize" value="10" />
            </form>

            <ul class="table-tab clearfix">
                <li class="back current">退款单</li>
                <li class="fill">补款单</li>
            </ul>


            <section class="clearfix" id="searchField">
            <table class="commonTab">
            <colgroup>
             <col width="9%">
                <col width="9%">
                <col width="9%">
                <col width="9%">
                <col width="10%">
                <col width="9%">
                <col width="9%">
                <col width="10%">
                <col width="14%">
                <col width="12%">
            </colgroup>
            <thead>
            <tr>
                <th>订单号</th>
                <th>关联主订单号</th>
                <th>主订单状态</th>
                <th>建单时间</th>
                <th>游客姓名</th>
                <th>退款金额</th>
                <th>子订单状态</th>
                <th class="text-center">操作</th>
                <th>备注</th>
                <th>状态变更历史</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            </table>
            <div id="pagination">
            </div>
            </section>


               <section class="clearfix back-money-section" style="display: none;">
                    <table class="back">
                        <colgroup>
                         <col width="9%">
		                <col width="9%">
		                <col width="9%">
		                <col width="9%">
		                <col width="10%">
		                <col width="9%">
		                <col width="9%">
		                <col width="10%">
		                <col width="14%">
		                <col width="12%">
                        </colgroup>
                        <thead>

                        <tr>
                            <th>订单号</th>
                            <th>关联主订单号</th>
                            <th>主订单状态</th>
                            <th>建单时间</th>
                            <th>游客姓名</th>
                            <th>退款金额</th>
                            <th>子订单状态</th>
                            <th class="text-center">操作</th>
                            <th>备注</th>
                            <th>状态变更历史</th>
                        </tr>
                        </thead>

                    </table>

                </section>

  <section class="clearfix fill-money-section" style="display: none;">
                    <table class="fill">
                        <colgroup>
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="12%">
                            <col width="10%">
                            <col width="15%">
                            <col width="23%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>关联主订单号</th>
                            <th>主订单状态</th>
                            <th>建单时间</th>
                            <th>游客姓名</th>
                            <th>补款金额</th>
                            <th>补款状态</th>
                            <th>状态变更历史</th>
                        </tr>
                        </thead>
                    </table>
             </section>





            </section>
    </div>
</@html.htmlIndex>

   <div class="modal fade" data-backdrop="static" data-keyboard="false" id="passWindow">
        <div class="modal-dialog" style="width:400px;height:215px;">
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
                        <span class="popupContainer-fonts">确认通过？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button confirm">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>




    <div class="modal fade commonInitialize" id="ac-notPassWindow">
        <div class="modal-dialog" style="width: 500px; height:290px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <table class="thRight notPassTab">
                        <colgroup>
                            <col width="">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>标注未通过原因</td>
                        </tr>
                        <tr>
                            <td>
                                <textarea>

                                </textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" id="unpassconfirm" class="commonButton red-fe6869Button">确 定</button>
                    <button class="commonButton gray-bbbButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>



