<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />

<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/pagination.js","operator/electronicWallet/list.js"] cssFiles=["css/maintain/operatorManagement.css",""] curModule="" title="运营管理 代金券使用明细">
    <@main_header.header currentMenu="运营管理" currentSubMenu="代金券使用明细">
       <div class="headSecondLevel headBtn clearfix">
                <span class="leftBtn">
                    <a href="${basepath}/activity/edit/${activityId!}" class="returnBtn">< 返回</a>
                </span>
            </div>
    </@main_header.header>
        <div class="main-container changeMainContent">

            <section class="whiteBg">
                <div class="voucherBlock clearfix">
                  <#if couponItemVo??>
                    <div class="voucherContainer">
                        <div class="voucherItem voucher-bg-0">
                            <div class="flag"></div>
                            <div class="rightBorder"></div>
                            <div class="top">
                                <span class="title">${couponItemVo.name!}</span>
                            </div>
                            <div class="horizontal-white-split-line"></div>
                            <div class="middle">
                                <input type="hidden" id ="couponCode" value="${couponCode!}">
                                <input type = "hidden" id = "orderLeast" value="${couponItemVo.orderLeast!}">
                                <div class="privilege"><#if couponItemVo.orderLeast ??>满${couponItemVo.orderLeast/100!}元减</#if></div>
                                <div class="validity">${couponItemVo.validTimeFrom?substring(0,16)} - ${couponItemVo.validTimeTo?substring(0,16)}</div>
                                <div class="applicability">适用范围：${couponItemVo.productRange!}</div>
                                <span class="price"><em><#if couponItemVo.amount ??>${couponItemVo.amount!/100}元</#if></em></span>
                            </div>
                        </div>
                        </#if>
                    </div>
                    <div class="voucherNumCondition">
                        <div class="left">
                            <div class="title">系统发送</div>
                            <div class="num"><#if systemSendCount??>${systemSendCount}<#else>N</#if></div>
                        </div>
                        <div class="left" style="margin-left: 35px;">
                            <div class="title">用户已领取</div>
                            <div class="num"><#if couponCount ??>${couponCount.grantedCount!}</#if></div>
                        </div>
                        <div class="right">
                            <div class="title">用户已消费</div>
                            <div class="num"><#if couponCount ??>${couponCount.usedCount!}</#if></div>
                        </div>
                    </div>
                </div>
                <table class="searchTab" style="margin:25px 0;">
                    <colgroup>
                        <col width="60">
                        <col width="280">
                        <col width="75">
                        <col width="435">
                        <col width="">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>领券者</th>
                        <td>
                            <input type="text" id = "couponUser">
                           <div class="verifyStyle" style="width: 200px; display: none" id ="couponUserError">
                                <i class="verifyIcon"></i>
                                <span class="verifyFonts">请输入正确会员ID</span>
                            </div>
                        </td>
                        <th>消费时间</th>
                        <td>
                            <input type="text" style="width: 130px;" class="datepicker startDate hasIcon" id="useTimeFrom"><em> 至 </em><input type="text" style="width: 130px;" class="datepicker endDate hasIcon" id="useTimeTo">
                        </td>
                        <td>
                            <a href="javascript:void(0);" class="lightBlueBtn" style="width:100px;"><i class="searchIcon"></i>搜 索</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <section class="clearfix" style="width:950px;">
                    <table class="commonTab">
                        <colgroup>
                            <col width="280"/>
                            <col width="340"/>
                            <col width="340"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>领券者</th>
                            <th>发放时间</th>
                            <th>消费时间</th>
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



</body>
</@html.htmlIndex>