<#macro orderStatus>

<div class="orderFlowModel">
      <div class="dashedLine"></div>
            <ul class="orderStatus clearfix">
                <li class="first green-border-tooltip whiteOrder <#if (orderDetail.orderOperate['CREATE'])??>passed</#if>">
                    <span class="whiteOrderIcon"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">填写订单</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['CREATE'])!}</div>
                    </div>
                </li>
                <li class="payOrder green-border-tooltip <#if (orderDetail.orderOperate['PAY'])??>passed</#if>">
                    <span class="payOrderIcon"></span>
                    <span class="line"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">支付订单</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['PAY'])!}</div>
                    </div>
                </li>
                <li class="paySuccess green-border-tooltip <#if (orderDetail.orderOperate['PAYSUCCESS'])??>passed</#if>">
                    <span class="paySuccessIcon" style="margin-left:44px;"></span>
                    <span class="line"></span>
                    <div class="statusPay">
                        <div class="grayFonts"><#if (orderDetail.order.backState)?? && (orderDetail.order.backState != 'UNPAY' && orderDetail.order.backState != 'PAYED' && orderDetail.order.backState != 'CANCELED'  && orderDetail.order.backState != 'REFUNDING')>支付成功,产品已确认<#elseif (orderDetail.order.backState)?? && (orderDetail.order.backState == 'PAYED' || orderDetail.order.backState == 'UNPAY' || orderDetail.order.backState == 'CANCELED' || orderDetail.order.backState == 'REFUNDING')>支付成功</#if></div>
                        <div class="dateFonts">${(orderDetail.orderOperate['PAYSUCCESS'])!}</div>
                    </div>
                </li>
                <li class="box green-border-tooltip <#if (orderDetail.orderOperate['GIFTBOX'])??>passed</#if>">
                    <span class="boxIcon"></span>
                    <span class="line"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">寄送行程手册</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['GIFTBOX'])!}</div>
                    </div>
                </li>
                <li class="travelInform green-border-tooltip <#if (orderDetail.orderOperate['OUTNOTICE'])??>passed</#if>">
                    <span class="travelInformIcon"></span>
                    <span class="line"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">出行通知</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['OUTNOTICE'])!}</div>
                    </div>
                </li>
                <li class="steward green-border-tooltip <#if (orderDetail.orderOperate['ZTMANAGER'])??>passed</#if>">
                    <span class="stewardIcon"></span>
                    <span class="line"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">出行中</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['ZTMANAGER'])!}</div>
                    </div>
                </li>
                <li class="last evaluate green-border-tooltip <#if (orderDetail.orderOperate['COMPLETED'])??>passed</#if>">
                    <span class="evaluateIcon"></span>
                    <span class="line"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">评价</div>
                        <div class="dateFonts"><#if (orderDetail.orderOperate['EVALATE'])??>${orderDetail.orderOperate['EVALATE']}<#else>${(orderDetail.orderOperate['COMPLETED'])!}</#if></div>
                    </div>
                </li>
                <li style="display:none;" class="<#if (orderDetail.orderOperate['EVALATE'])??>passed</#if>">
                </li>
            </ul>
        </div>

</#macro>