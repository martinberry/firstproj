<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/pagination.js","operator/financeMantain/trade.js"] cssFiles=["css/maintain/finance.css","css/bootstrap-datepicker.min.css"]
curModule="财务&供应商系统" title="交易明细">
  <@main_header.header currentMenu="财务&供应商系统" currentSubMenu="交易明细">
	</@main_header.header>

        <div class="main-container">
            <section class="whiteBg">
                <ul class="searchList clearfix">
                    <li>
                    <form id="search-form" data-role="validator" novalidate="novalidate">
                    		 <input type="hidden" name="pageNo" value="1" />
				<input type="hidden" name="pageSize" value="10" />
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>会员ID</th>
                                <td>
                                    <input type="text" id ="mid" name ="mid"/>
                                </td>
                            </tr>
                            <tr>
                                <th>业务类型</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                       <input type = "hidden" name ="productType" class="searchFormField">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id="productType" >全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);" data-val="">全部</a></li>
                                            <li><a href="javascript:void(0);" >自由行</a></li>
                                            <li><a href="javascript:void(0);" >代金券</a></li>
                                            <li><a href="javascript:void(0);" >OP确认差额单</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                               <tr>
                                <th>支付方式</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                     <input type = "hidden" name ="paymentType" class="searchFormField">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id ="paymentType" >全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">支付宝支付</a></li>
                                            <li><a href="javascript:void(0);">微信支付</a></li>
                                            <li><a href="javascript:void(0);">积分支付</a></li>
                                            <li><a href="javascript:void(0);">代金券支付</a></li>
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
                                <col width="272">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>交易日期</th>
                                <td>
                                    <input type="text" style="width: 113px;" class="datepicker startDate hasIcon" readonly="readonly" id="conditionTradeFrom" name ="conditionTradeFrom">&nbsp;至&nbsp;<input type="text" style="width: 112px;" class="datepicker endDate hasIcon" readonly="readonly" id="conditionTradeTo" name="conditionTradeTo">
                                </td>
                            </tr>
                            <tr>
                                <th>银行订单</th>
                                <td>
                                    <input style="width: 250px;" type="text" id ="traceNum" name="traceNum"/>
                                </td>
                            </tr>
                         <tr>
                                <th>支付状态</th>
                                <td>
                                    <div class="dropdown" style="width: 250px;">
                                     <input type = "hidden" name="tradeStatus" class="searchFormField">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id ="tradeStatus" >全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">请求</a></li>
                                            <li><a href="javascript:void(0);">成功</a></li>
                                            <li><a href="javascript:void(0);">失败</a></li>
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
                                <col width="225">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>订 单 号</th>
                                <td>
                                    <input type="text" id ="orderId" name="orderId"/>
                                </td>
                            </tr>
                            <tr>
                                <th>支付类型</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                      <input type = "hidden" name= "tradeType" class="searchFormField">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id ="tradeType" >全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">支付</a></li>
                                            <li><a href="javascript:void(0);">退款</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            
                            
                                <tr>
                                <th>订单类型</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                      <input type = "hidden" name= "orderType" class="searchFormField">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id ="orderType" >全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">支付单</a></li>
                                            <li><a href="javascript:void(0);">退款单</a></li>
                                            <li><a href="javascript:void(0);">取消单</a></li>
                                            <li><a href="javascript:void(0);">OP确认补款单</a></li>
                                            <li><a href="javascript:void(0);">OP确认退款单</a></li>
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
                                <col width="103">
                            </colgroup>
                            <tbody>
                            <tr>
                                <td style="height:45px;"></td>
                            </tr>
                            <tr>
                                <td style="height:45px;"></td>
                            </tr>
                            <tr>
                                <td style="height:46px;">
                                    <a href="javascript:void(0);" class="lightBlueBtn" ><i class="searchIcon"></i>查 询</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </form>
                    </li>
                </ul>
                <div class="outTab"><a href="javascript:void(0);" id="exportExcel">导出Excel</a></div>
                <section class="clearfix">
                    <table class="commonTab productTab">
                        <colgroup>
                            <col width="10%">
                            <col width="5%">
                            <col width="8%">
                            <col width="6%">
                            <col width="8%">
                            <col width="10%">
                            <col width="10%">
                            <col width="9%">
                            <col width="5%">
                            <col width="5%">
                            <col width="9%">
                            <col width="11%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>昵称</th>
                            <th>会员ID</th>
                            <th>订单号</th>
                            <th>业务类型</th>
                            <th>交易日期</th>
                            <th>订单金额</th>
                            <th>支付金额</th>
                            <th>支付方式</th>
                            <th>支付类型</th>
                            <th>支付状态</th>
                            <th>订单类型</th>
                            <th>银行订单号</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                          <div id="searchField">
                   </div>


                </section>
            </section>
        </div>
    </div>
    <div class="modal hide fade" id="ac-online">
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
                    <button type="button" class="commonButton red-fe6869Button">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton">取消</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal hide fade" id="ac-issue">
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
                        <span class="popupContainer-fonts">确认要发布产品吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton">取消</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(function(){
            $(".radioList .radio").click(function(){
                $(this).addClass("active");
                $(this).siblings().removeClass("active");
            });

            $(".allCheckbox").click(function(){
                $(this).toggleClass("active");
                if ($(this).hasClass("active")) {
                    $(this).parents("table").find(".checkbox").addClass("active");
                } else {
                    $(this).parents("table").find(".checkbox").removeClass("active");
                }
            });

            $(".ac-online").click(function(){
                $("#ac-online").modal();
            });

            $(".ac-issue").click(function(){
                $("#ac-issue").modal();
            });

        });
    </script>


</@html.htmlIndex>