<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/member/opera/memberManage/memberMaintain/leftMenu.ftl" as left/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/pagination.js","operator/financeMantain/accountHistory.js"] cssFiles=["css/maintain/finance.css","css/bootstrap-datepicker.min.css"]
curModule="财务&供应商系统" title="票券收支明细">
<@main_header.header currentMenu="财务&供应商系统" currentSubMenu="票券收支明细">
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
                                <input type="text" id="mid" name ="mid"/>
                            </td>
                        </tr>
                        <tr>
                            <th>订 单 号</th>
                            <td>
                                <input type="text" id="orderId" name ="orderId"/>
                            </td>

                        </tr>
                        </tbody>
                    </table>
                </li>
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="80">
                            <col width="220">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>业务类型</th>
                            <td>
                                <div class="dropdown" style="width: 200px;">
                                 	<input type = "hidden" name= "productType" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id="productType">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="active"><a href="javascript:void(0);">全部</a></li>
                                        <li><a href="javascript:void(0);">自由行</a></li>
                                        <li><a href="javascript:void(0);">代金券</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>账户类型</th>
                            <td>
                                <div class="dropdown" style="width: 200px;">
                                	 <input type = "hidden" name= "accountType" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id ="accountType">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="active"><a href="javascript:void(0);">全部</a></li>
                                        <li><a href="javascript:void(0);">积分</a></li>
                                        <li><a href="javascript:void(0);">代金券</a></li>
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
                            <col width="453">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>操作日期</th>
                            <td>
                                <input type="text" style="width: 200px;" class="datepicker startDate hasIcon" readonly="readonly" name="conditionOperateFrom" id="conditionOperateFrom">&nbsp;至&nbsp;<input type="text"
                                                   style="width: 200px;" class="datepicker endDate hasIcon" readonly="readonly" name="conditionOperateTo" id="conditionOperateTo">
                            </td>
                        </tr>
                        <tr>
                            <th>票务流向</th>
                            <td>
                                <div class="dropdown" style="width: 170px;" id="inoutDropdown">
                                	<input type = "hidden" name= "inoutType" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id="inoutType">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu" role="menu" id="parentMenu">
                                        <li class="active" role="presentation"><a href="javascript:void(0);">全部</a></li>
                                        <li role="presentation"><a href="javascript:void(0);">收入</a></li>
                                        <li role="presentation"><a href="javascript:void(0);">支出</a></li>
                                    </ul>
                                </div>&nbsp;-&nbsp;收支分类
                                <div class="dropdown" style="width: 170px;">
                                	<input type = "hidden" name= "inoutDetailType" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id="inoutDetailType">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu" id="sonMenu" role="menu">
                                        <li class="active" role="presentation"><a href="javascript:void(0);">全部</a></li>
                                        <li class="income-subType"><a href="javascript:void(0);">获得代金券</a></li>
                                        <li class="income-subType"><a href="javascript:void(0);">获得积分</a></li>
                                        <li class="outcome-subType"><a href="javascript:void(0);">支付票款</a></li>
                                        <li class="outcome-subType"><a href="javascript:void(0);">代金券过期</a></li>
                                        <li class="outcome-subType"><a href="javascript:void(0);">退款票款</a></li>
                                        <li class="outcome-subType"><a href="javascript:void(0);">分享代金券</a></li>
                                        <li class="outcome-subType"><a href="javascript:void(0);">代金券退款</a></li>
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
                            <td height="45">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>查 询</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    </form>
                </li>
            </ul>
            <div class="outTab"><a href="javascript:void(0);"  id="exportExcel">导出Excel</a></div>
            <section class="clearfix">
                <table class="commonTab productTab">
                    <colgroup>
                        <col width="10%">
                         <col width="8%">
                        <col width="8%">
                        <col width="8%">
                        <col width="8%">
                        <col width="6%">
                        <col width="6%">
                        <col width="9%">
                        <col width="10%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>昵称</th>
                        <th>会员ID</th>
                        <th>订单号</th>
                        <th>业务类型</th>
                        <th>账户类型</th>
                        <th>收入</th>
                        <th>支出</th>
                        <th>收支分类</th>
                        <th>操作日期</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                     </table>
                          <div id="searchField">
                   </div>
                </div>
            </section>
        </section>
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