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
                    <a href="${basepath}/order/freetravel/list" class="returnBtn">< 返回</a>
                </span>
                <ul class="topFlow clearfix">
                    <li <#if ((orderDetail.statusEnum)!'') == "UNPAY">class="current"</#if>>
                        <label class="first">
                            <em>未支付</em>
                        </label>
                    </li>
                    <li <#if ((orderDetail.statusEnum)!'') == "PAYED">class="current"</#if>>
                        <label class="middle">
                            <em>支付成功，待OP确认</em>
                        </label>
                    </li>
                    <li <#if ((orderDetail.statusEnum)!'') == "CONFIRM">class="current"</#if>>
                        <label class="middle">
                            <em>OP确认，待发放礼盒</em>
                        </label>
                    </li>
                    <li <#if ((orderDetail.statusEnum)!'') == "GIFTSEND">class="current"</#if>>
                        <label class="middle">
                            <em>礼盒发放，待出行通知</em>
                        </label>
                    </li>
                    <#assign completeStatuses = ["OUTNOTICE","OUTTING","COMPLETED"]>
                    <li <#if completeStatuses?seq_contains((orderDetail.statusEnum)!'')>class="current"</#if>>
                        <label class="last">
                            <em>已完成</em>
                        </label>
                    </li>
                </ul>
            </div>
        </header>