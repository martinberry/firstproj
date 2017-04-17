<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/pagination.js","operator/electronicWallet/dncouponUserDetail.js"] cssFiles=["css/maintain/operatorManagement.css","css/bootstrap-datepicker.min.css"] curModule="" title="运营管理 大宁活动代金券使用明细">
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
                    <div class="voucherContainer">
                    <#if couponItemVo??>
                    <div class="voucherContainer">
                        <div class="voucherItem voucher-bg-0">
                            <div class="flag"></div>
                            <div class="rightBorder"></div>
                            <div class="top">
                                <span class="title">${couponItemVo.name!}</span>
                                 <#if price ?? >
			                        <#if price gt 0 >
			                	      <span class="star-icon"></span>
			                      </#if>
              				    </#if>
                            </div>
                            <input type="hidden" id="activityId" value=${activityId!}>
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
                <div class="ticketStatusTab">
                    <ul class="clearfix">
                        <li class="current">初始</li>
                        <li>已领取</li>
                        <li>待退款</li>
                        <li>已退款</li>
                    </ul>
                </div>
                <div class="ticketStatusList">
                    <div class="ticketStatusItem">
                        <table class="searchTab" style="margin:25px 0;">
                            <colgroup>
                                <col width="115">
                                <col width="280">
                                <col width="50">
                                <col width="335">
                                <col width="">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>代金券兑换码</th>
                                <td>
                                    <input type="text" id="systemLock_voucherCode" maxlength="20" autocomplete="off">
                                </td>
                                <th>类型</th>
                                <td>
                                    <div class="dropdown" style="width: 120px;"  autocomplete="off">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">均含</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id="systemLock_status">
                                            <li class="active"><a href="javascript:void(0);">均含</a></li>
                                            <li name="11001"><a href="javascript:void(0);">非占用</a></li>
                                            <li name="11002"><a href="javascript:void(0);">占用中</a></li>
                                        </ul>
                                    </div>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="lightBlueBtn" style="width:100px;" id="systemLockSearch"><i class="searchIcon" ></i>搜 索</a>
                                    <button type="button" class="commonButton red-fe6869Button  <#if isActivityExpired || (activityStatus=='TERMINATED' || activityStatus == 'EDN')>disabled</#if>" id="lockAll"><#if isAllSystem??><#if isAllSystem=='no' >全部占用<#else>全部取消占用</#if></#if></button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <section class="clearfix" style="width:950px;">
                            <table class="commonTab">
                                <colgroup>
                                    <col width="320"/>
                                    <col width="200"/>
                                    <col width="340"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>代金券兑换码</th>
                                    <th>类型</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                       <tbody id="systemLock_tbody">

                                </tbody>

                            </table>

		    			 <input type="hidden" name="pageNo" value="1" />
						<input type="hidden" name="pageSize" value="10" />
                        </section>
                    </div>
                    <div class="ticketStatusItem" style="display: none;">
                        <table class="searchTab" style="margin:25px 0;">
                            <colgroup>
                                <col width="115">
                                <col width="220">
                                <col width="95">

                                <col width="220">
                                <col width="95">
                                <col width="335">
                                <col width="">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>代金券兑换码</th>
                                <td>
                                    <input type="text" id="recieved_voucherCode" maxlength="20"  autocomplete="off">
                                </td>
                                <th>领券会员ID</th>
                                <td>
                                    <input type="text" id="recieved_reciever_mid" maxlength="20"  autocomplete="off">
                                </td>
                                <th>领券日期</th>
                                <td>
                                    <input type="text" style="width: 130px;" class="datepicker startDate hasIcon" id="recieved_payTimeFrom"  autocomplete="off"><em> 至 </em><input type="text" style="width: 130px;" class="datepicker endDate hasIcon" id="recieved_payTimeTo"  autocomplete="off">
                                </td>
                                <td rowspan="3">
                                    <a href="javascript:void(0);" class="lightBlueBtn" style="width:100px;" id="recievedSearch"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr>
                                <th>消费状况</th>
                                <td>
                                    <div class="dropdown" style="width: 180px;" id="recieved_consumption"  autocomplete="off">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">均含</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">均含</a></li>
                                            <li name="11001"><a href="javascript:void(0);">已消费</a></li>
                                            <li name="11002"><a href="javascript:void(0);">未消费</a></li>
                                        </ul>
                                    </div>
                                </td>
                                <th>消费订单号</th>
                                <td>
                                    <input type="text" id="recieved_consumOrderId" maxlength="20"  autocomplete="off">
                                </td>
                                <th>消费会员ID</th>
                                <td>
                                    <input type="text" style="width: 285px;" id="recieved_consum_mid" maxlength="20"  autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th>系统占用</th>
                                <td>
                                    <div class="dropdown" style="width: 180px;" id="recieved_systemLock_condition"  autocomplete="off">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">均含</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">均含</a></li>
                                            <li name="11001"><a href="javascript:void(0);">是</a></li>
                                            <li name="11002"><a href="javascript:void(0);">否</a></li>
                                        </ul>
                                    </div>
                                </td>
                                <th>领券订单号</th>
                                <td><input type="text" id="recieved_recieveOrderId" maxlength="20"  autocomplete="off"></td>
                                <td></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                        <section class="clearfix" style="width:1600px;">
                            <table class="commonTab">
                                <colgroup>
                                    <col width="200"/>
                                    <col width="200"/>
                                    <col width="120"/>
                                    <col width="120"/>
                                    <col width="110"/>
                                    <col width="90"/>
                                    <col width="220"/>
                                    <col width="90"/>
                                    <col width="150"/>
                                    <col width="120"/>
                                    <col width=""/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>代金券兑换码</th>
                                    <th>领券订单号</th>
                                    <th>领券会员ID</th>
                                    <th>通知手机号</th>
                                    <th>支付类型</th>
                                    <th>支付金额</th>
                                    <th>领券日期</th>
                                    <th>是否消费</th>
                                    <th>消费订单号</th>
                                    <th>消费会员ID</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody id="recieved_tbody">

                                </tbody>

                            </table>

                        </section>
                    </div>
                    <div class="ticketStatusItem" style="display: none;">
                        <table class="searchTab" style="margin:25px 0;">
                            <colgroup>
                                <col width="115">
                                <col width="220">
                                <col width="115">
                                <col width="220">
                                <col width="95">
                                <col width="335">
                                <col width="">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>代金券兑换码</th>
                                <td>
                                    <input type="text" id="unrefunded_voucherCode" maxlength="20"  autocomplete="off">
                                </td>
                                <th>领券会员ID</th>
                                <td>
                                    <input type="text" id="unrefunded_recieve_mid" maxlength="20"  autocomplete="off">
                                </td>
                                <th>领券日期</th>
                                <td>
                                    <input type="text" style="width: 130px;" class="datepicker startDate hasIcon" id="unrefunded_payTime_from"  autocomplete="off"><em> 至 </em><input type="text" style="width: 130px;" class="datepicker endDate hasIcon" id="unrefunded_payTime_to"  autocomplete="off">
                                </td>
                                <td rowspan="2">
                                    <a href="javascript:void(0);" class="lightBlueBtn" style="width:100px;" id="unrefundedSearch"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr>
                                <th>领券订单号</th>
                                <td><input type="text" id="unrefunded_recieve_orderId" maxlength="20"  autocomplete="off"></td>
                                <th>申请退款日期</th>
                                <td colspan="3">
                                    <input type="text" style="width: 130px;" class="datepicker startDate hasIcon" id="unrefunded_applyRefundTime_from"  autocomplete="off"><em> 至 </em><input type="text" style="width: 130px;" class="datepicker endDate hasIcon" id="unrefunded_applyRefundTime_to"  autocomplete="off">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <section class="clearfix" style="width:1300px;">
                            <table class="commonTab">
                                <colgroup>
                                    <col width="200"/>
                                    <col width="200"/>
                                    <col width="140"/>
                                    <col width="100"/>
                                    <col width="90"/>
                                    <col width="130"/>
                                    <col width="100"/>
                                    <col width="90"/>
                                    <col width="130"/>
                                    <col width="110"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>代金券兑换码</th>
                                    <th>领券订单号</th>
                                    <th>领券会员ID</th>
                                    <th>支付类型</th>
                                    <th>支付金额</th>
                                    <th>领券日期</th>
                                    <th>退款类型</th>
                                    <th>退款金额</th>
                                    <th>申请退款时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="unrefunded_tbody">
                                </tbody>
                            </table>
                        </section>
                    </div>
                    <div class="ticketStatusItem" style="display: none;">
                        <table class="searchTab" style="margin:25px 0;">
                            <colgroup>
                                <col width="115">
                                <col width="220">
                                <col width="95">
                                <col width="220">
                                <col width="95">
                                <col width="335">
                                <col width="">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>代金券兑换码</th>
                                <td>
                                    <input type="text" id="refunded_voucherCode" maxlength="20"  autocomplete="off">
                                </td>
                                <th>领券会员ID</th>
                                <td>
                                    <input type="text" id="refunded_recieve_mid" maxlength="20"  autocomplete="off">
                                </td>
                                <th>领券日期</th>
                                <td>
                                    <input type="text" style="width: 130px;" class="datepicker startDate hasIcon" id="refunded_payTime_from"  autocomplete="off"><em> 至 </em><input type="text" style="width: 130px;" class="datepicker endDate hasIcon" id="refunded_payTime_to"  autocomplete="off">
                                </td>
                                <td rowspan="2">
                                    <a href="javascript:void(0);" class="lightBlueBtn" style="width:100px;" id="refundedSearch"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr>
                                <th>领券订单号</th>
                                <td><input type="text" id="refunded_recieve_orderId" maxlength="20"  autocomplete="off"></td>
                                <th>退款日期</th>
                                <td colspan="3">
                                    <input type="text" style="width: 130px;" class="datepicker startDate hasIcon" id="refunded_refundTime_from"  autocomplete="off"><em> 至 </em><input type="text" style="width: 130px;" class="datepicker endDate hasIcon" id="refunded_refundTime_to"  autocomplete="off">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <section class="clearfix" style="width:1300px;">
                            <table class="commonTab">
                                <colgroup>
                                    <col width="200"/>
                                    <col width="200"/>
                                    <col width="140"/>
                                    <col width="100"/>
                                    <col width="90"/>
                                    <col width="200"/>
                                    <col width="100"/>
                                    <col width="90"/>
                                    <col width=""/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>代金券兑换码</th>
                                    <th>领券订单号</th>
                                    <th>领券会员ID</th>
                                    <th>支付类型</th>
                                    <th>支付金额</th>
                                    <th>领券日期</th>
                                    <th>退款类型</th>
                                    <th>退款金额</th>
                                    <th>退款时间</th>
                                </tr>
                                </thead>
                                <tbody id="refunded_tbody">

                                </tbody>
                                </table>
                        </section>
                    </div>
                </div>

     					<tbody>
                        </tbody>
                    </table>
					<div id="searchField">
       				</div>
            </section>

        </div>

    </div>

    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="confirmUpdateVoucherTypeWindow">
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
                        <span class="popupContainer-fonts" id="confirmUpdateTip">确认系统占用操作?</span>
                    </div>
                </div>
                <input type="hidden" id="updateVoucherCode">
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="confirmUpdateVoucherTypeBtn">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" id="cancelUpdateVoucherTypeBtn">取消</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="updateVoucherTypeSuccessWindow">
        <div class="modal-dialog" style="width:400px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts" id="updatedVoucherTypeTip">该代金券已被系统占用成功</span>
                    </div>
                </div>
                <input type="hidden" id="updateVoucherCode">
                <input type ="hidden" id="voucherTypeHide">
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="updatedVoucherTypeOkBtn">ok</button>
                </div>
            </div>
        </div>
    </div>

     <div class="modal fade" data-backdrop="static" data-keyboard="false" id="updateVoucherTypeErrorWindow">
        <div class="modal-dialog" style="width:400px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts" id="updatedVoucherTypeTip">该代金券已被领取，操作失效</span>
                    </div>
                </div>
                <input type="hidden" id="updateVoucherCode">
                <input type ="hidden" id="voucherTypeHide">
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="updatedVoucherTypeErrorBtn">确定</button>
                </div>
            </div>
        </div>
    </div>

       <div class="modal fade" data-backdrop="static" data-keyboard="false" id="confirmUpdateVoucherTypeAllWindow">
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
                        <span class="popupContainer-fonts">确认系统全部占用操作?</span>
                    </div>
                </div>
                <input type="hidden" id="updateVoucherCode">
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="confirmUpdateVoucherTypeAllBtn">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" id="cancelUpdateVoucherTypeAllBtn">取消</button>
                </div>
            </div>
        </div>
    </div>

        <div class="modal fade" data-backdrop="static" data-keyboard="false" id="confirmAuditWindow">
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
                        <span class="popupContainer-fonts">确认通过审核?</span>
                    </div>
                </div>
                <input type="hidden" id="voucherOrderIdHide">
                <input type="hidden" id="paymentTypeHide">
                  <input type="hidden" id="voucherCodeAuditHide">
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="confirmPassAuditBtn">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

     <div class="modal fade" data-backdrop="static" data-keyboard="false" id="updateVoucherTypeAllSuccessWindow">
        <div class="modal-dialog" style="width:400px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts" id="updatedVoucherTypeTip">全部代金券已被系统占用成功</span>
                    </div>
                </div>
                <input type="hidden" id="updateVoucherCode">
                <input type ="hidden" id="voucherTypeHide">
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="UpdateVoucherTypeAllOkBtn">ok</button>
                </div>
            </div>
        </div>
    </div>



       <div class="modal fade" data-backdrop="static" data-keyboard="false" id="refundingConfirmWindow">
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
                        <span class="popupContainer-fonts">该代金券已进入退款流程</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="passAuditOkBtn">ok</button>
                </div>
            </div>
        </div>
    </div>

<script>
    $(function(){

        $(".ticketStatusTab li").each(function(index){
            $(this).click(function(){
                $(".ticketStatusTab li").removeClass("current");
                $(this).addClass("current");
                $(".ticketStatusList .ticketStatusItem").hide();
                $(".ticketStatusList").find(".ticketStatusItem").eq(index).show();
            });
        });

        $(".blueFontsLink").click(function(){
            $("#confirmWindow").modal("show");
        });
    });
</script>



</@html.htmlIndex>
