<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/bootstrap-datepicker.min.css", "css/maintain/orderManagement.css"]
                                  jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "order/back/visa/visaList.js" ,"common/pagination.js"]
                                  curModule="订单管理" title="真旅行签证订单列表">

    <@main_header.header currentMenu="订单管理" currentSubMenu="签证订单" >
    </@main_header.header>
    <div class="main-container">
            <section class="whiteBg">
            <form id="searchConditionForm">
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="100">
                                <col width="280">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>签证订单号</th>
                                <td>
                                    <input id="visaOrderNo" name="orderNo" type="text" data-cv="required" style="width: 255px;">
                                    <span class="orderNoTips" style="display:none;">订单号码应为1-12位数字</span>
                                  
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>联系人姓名</th>
                                <td>
                           <input id="contactorName" name="contactor" type="text" data-cv="required" style="width: 255px;">
                   
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
                                            <li data-val="" class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li data-val="UNPAY"><a href="javascript:void(0);" name="unpay">待支付</a></li>
                                            <li data-val="PAYED"><a href="javascript:void(0);" name="payed">支付成功</a></li>
                                              <li data-val="OPCONFIRM"><a href="javascript:void(0);" name="opconfirm">制作中</a></li>
                                                <li data-val="MAKED"><a href="javascript:void(0);" name="maked">制作完成</a></li>
                                                  <li data-val="MATERIALSEND"><a href="javascript:void(0);" name="materialsend">材料送回</a></li>
                                                    <li data-val="CANCELED"><a href="javascript:void(0);" name="canceled">已取消</a></li>
                                                      <li data-val="REFUNDING"><a href="javascript:void(0);" name="refunding">退款中</a></li>
                                                        <li data-val="REFUNDFAILED"><a href="javascript:void(0);" name="refundfailed">退款失败</a></li>
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
                                <col width="290">
                                <col width="110">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>产品标题</th>
                                <td>
                                    <input id="productTitle" placeholder="关键字" name="productTitle" type="text" style="width: 255px;">
                                </td>
                                <td>
                                        <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                              
                            </tr>
                             <tr>
                                <th>建单日期</th>
                                <td>
                                    <input id="purchaseTimeFrom" name="purchaseTimeFrom" type="text" style="width: 115px;" class="datepicker startDate hasIcon">
                                    <em> 至 </em>
                                    <input id="purchaseTimeTo" name="purchaseTimeTo" type="text" style="width: 115px;" class="datepicker endDate hasIcon">
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </li>

       
				    <input type="hidden" name="pageNo" id="pageNo" value="1" />
				    <input type="hidden" name="pageSize" id="pageSize" value="10" />
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
                        </colgroup>
                        <thead>
                        <tr>
                            <th>订单状态</th>
                            <th>订单号</th>
                            <th>产品标题</th>
                            <th>产品类型</th>
                            <th>联系人姓名</th>
                            <th>建单日期</th>
                            <th class="text-center">操作</th>
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
