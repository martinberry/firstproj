        <header class="main-header hoveringModel">
            <div class="stairMenuContent">
                <div class="wrap clearfix">
                    <div class="navLeft">
                        <a class="logo" href="/"></a>
                    </div>
                    <#include "/common/opera/header_right.ftl" />
                </div>
            </div>
            <div class="headSecondLevel headBtn clearfix">
                <span class="leftBtn">
                    <a href="${basepath}/localorder/travel/list" class="returnBtn">返回</a>
                </span>
                <ul class="topFlow clearfix">
                    <li <#if ((localDetail.statusEnum)!'') == "UNPAY">class="current"</#if>>
                        <label class="first">
                            <em>待支付</em>
                        </label>
                    </li>
                    <li <#if ((localDetail.statusEnum)!'') == "PAYED">class="current"</#if>>
                        <label class="middle">
                            <em>支付成功</em>
                        </label>
                    </li>
                    <li <#if ((localDetail.statusEnum)!'') == "CONFIRM">class="current"</#if>>
                        <label class="middle">
                            <em>Op已确认</em>
                        </label>
                    </li>
               
                </ul>
            </div>
        </header>