<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/member/opera/memberManage/memberMaintain/leftMenu.ftl" as left/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","member/opera/finance/trade.js"] cssFiles=["css/maintain/finance.css","css/bootstrap-datepicker.min.css"]
curModule="常旅客" title="B2C 真旅行后台 常旅客信息">
<body>
    <div class="wrapper">
        <header class="main-header">
            <div class="stairMenuContent">
                <div class="wrap clearfix">
                    <div class="navLeft">
                        <a class="logo" href="/"></a>
                        <ul class="stairMenu">
                            <li class="first-level-nav">
                                <a href="javascript:void(0);">产品管理</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">产品维护</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">酒店资源</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="first-level-nav">
                                <a href="javascript:void(0);">景点管理</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">国内机票</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">国际机票</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">酒店</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">文档下载</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="first-level-nav">
                                <a href="javascript:void(0);">订单管理</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">二级导航11</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">二级导航22</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">二级导航33</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">二级导航44</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="first-level-nav">
                                <a href="javascript:void(0);">运营管理</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">二级导航111</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">二级导航222</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">二级导航333</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">二级导航444</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="first-level-nav">
                                <a href="javascript:void(0);">会员管理</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">会员维护</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">心愿清单</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">常用旅客</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="first-level-nav currentStairMenu">
                                <a href="javascript:void(0);">财务&供应商系统</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">交易明细</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">票券收支明细</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">票券账户明细</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="first-level-nav">
                                <a href="javascript:void(0);">权限管理</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                        <li class="currentSecondLevelMenu">
                                            <a href="javascript:void(0);">会员维护</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">心愿清单</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);">常用旅客</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <#include "/common/opera/header_right.ftl" />
                </div>
            </div>
            <div class="headSecondLevel"></div>
        </header>
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
                                <th>客户名称</th>
                                <td>
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <th>业务类型</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">自由行</a></li>
                                            <li><a href="javascript:void(0);">周边游</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>支付机构</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">自有账号</a></li>
                                            <li><a href="javascript:void(0);">支付宝</a></li>
                                            <li><a href="javascript:void(0);">微信</a></li>
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
                                    <input type="text" style="width: 113px;" class="datepicker startDate hasIcon" readonly="readonly">&nbsp;至&nbsp;<input type="text" style="width: 112px;" class="datepicker endDate hasIcon" readonly="readonly">
                                </td>
                            </tr>
                            <tr>
                                <th>银行订单</th>
                                <td>
                                    <input style="width: 250px;" type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <th>支付方式</th>
                                <td>
                                    <div class="dropdown" style="width: 250px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">网上支付</a></li>
                                            <li><a href="javascript:void(0);">扫码支付</a></li>
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
                                <col width="225">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>订 单 号</th>
                                <td>
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <th>支付类型</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
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
                                <th>支付状态</th>
                                <td>
                                    <div class="dropdown" style="width: 200px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
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
                                    <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>查 询</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
                <div class="outTab"><a href="javascript:void(0);">导出Excel</a></div>
                <section class="clearfix">
                    <table class="commonTab productTab">
                        <colgroup>
                            <col width="15%">
                            <col width="12%">
                            <col width="8%">
                            <col width="8%">
                            <col width="6%">
                            <col width="6%">
                            <col width="9%">
                            <col width="9%">
                            <col width="5%">
                            <col width="5%">
                            <col width="11%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>客户名称</th>
                            <th>订单号</th>
                            <th>业务类型</th>
                            <th>交易日期</th>
                            <th>订单金额</th>
                            <th>支付金额</th>
                            <th>支付机构</th>
                            <th>支付方式</th>
                            <th>支付类型</th>
                            <th>支付状态</th>
                            <th>银行订单号</th>
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

</body>

</@html.htmlIndex>