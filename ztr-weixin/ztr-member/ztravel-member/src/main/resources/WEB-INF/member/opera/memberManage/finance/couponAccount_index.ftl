<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/member/opera/memberManage/memberMaintain/leftMenu.ftl" as left/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js"] cssFiles=["css/maintain/finance.css","css/bootstrap-datepicker.min.css"]
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
                                    <li>
                                        <a href="javascript:void(0);">交易明细</a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">票券收支明细</a>
                                    </li>
                                    <li class="currentSecondLevelMenu">
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
                            <th>账户类型</th>
                            <td>
                                <div class="dropdown" style="width: 200px;">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts">全部</span>
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
                            <col width="220">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>冻结状态</th>
                            <td>
                                <div class="dropdown" style="width: 200px;">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="active"><a href="javascript:void(0);">全部</a></li>
                                        <li><a href="javascript:void(0);">已冻结</a></li>
                                        <li><a href="javascript:void(0);">未冻结</a></li>
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
                            <th>截止时间</th>
                            <td>
                                <input type="text" style="width: 200px;" class="datepicker default hasIcon" readonly="readonly">
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
                        <tr style="height:46px;">
                            <td>
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
                    </colgroup>
                    <thead>
                    <tr>
                        <th>客户名称</th>
                        <th>账户类型</th>
                        <th>账户余额</th>
                        <th>可用余额</th>
                        <th>冻结余额</th>
                        <th>账户冻结</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>易谦</td>
                        <td>代金券</td>
                        <td>2500.00</td>
                        <td><span class="fcf78c68">2000</span></td>
                        <td>500</td>
                        <td>否</td>
                        <td><a href="javascript:void(0);" class="greenFontsLink">冻结</a></td>
                    </tr>
                    <tr>
                        <td>易谦</td>
                        <td>代金券</td>
                        <td>2500.00</td>
                        <td><span class="fcf78c68">2000</span></td>
                        <td>500</td>
                        <td>否</td>
                        <td><a href="javascript:void(0);" class="greenFontsLink">冻结</a></td>
                    </tr>
                    <tr>
                        <td>易谦</td>
                        <td>代金券</td>
                        <td>2500.00</td>
                        <td><span class="fcf78c68">2000</span></td>
                        <td>500</td>
                        <td>否</td>
                        <td><a href="javascript:void(0);" class="greenFontsLink">冻结</a></td>
                    </tr>
                    </tbody>
                </table>
                <div class="pagination">
                    <span class="countNum">共 331 条记录，</span>
                        <span class="pageNum">
                            <span class="pageNumClick">每页 5 条 <i class="pageNumIcon"></i></span>
                            <ul class="paginationNum" style="display: none;">
                                <li class="active">
                                    <a href="javascript:void(0);">5</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);">10</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);">20</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);">30</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);">40</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);">50</a>
                                </li>
                            </ul>
                        </span>
                    <span class="pageSize">1/10</span>
                        <span class="pageArrow">
                            <a href="javascript:void(0);" class="firstPage disabled">
                                <i></i>
                            </a>
                            <a href="javascript:void(0);" class="prevPage disabled">
                                <i></i>
                            </a>
                            <a href="javascript:void(0);" class="nextPage">
                                <i></i>
                            </a>
                            <a href="javascript:void(0);" class="lastPage">
                                <i></i>
                            </a>
                        </span>
                        <span>
                            <span>跳转到</span>
                            <input type="text" style="width: 45px;" class="pageToNum">
                            <a href="javascript:void(0);" class="pageTo">
                                <i></i>
                            </a>
                        </span>
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

</body>
</@html.htmlIndex>