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
                    <a href="${basepath}/visa/list" class="returnBtn">< 返回</a>
                </span>
                <ul class="topFlow clearfix">
                    <li <#if ((visaDetail.statusEnum)!'') == "UNPAY">class="current"</#if>>
                        <label class="first">
                            <em>待支付</em>
                        </label>
                    </li>
                    <li <#if ((visaDetail.statusEnum)!'') == "PAYED">class="current"</#if>>
                        <label class="middle">
                            <em>支付成功</em>
                        </label>
                    </li>
                    <li <#if ((visaDetail.statusEnum)!'') == "CONFIRM">class="current"</#if>>
                        <label class="middle">
                            <em>制作中</em>
                        </label>
                    </li>
                    <li <#if ((visaDetail.statusEnum)!'') == "MAKED">class="current"</#if>>
                        <label class="middle">
                            <em>制作完成</em>
                        </label>
                    </li>
                    <li <#if ((visaDetail.statusEnum)!'') == "MATERIALSEND">class="current"</#if>>
                        <label class="last">
                            <em>材料送回</em>
                        </label>
                    </li>
                </ul>
            </div>
        </header>