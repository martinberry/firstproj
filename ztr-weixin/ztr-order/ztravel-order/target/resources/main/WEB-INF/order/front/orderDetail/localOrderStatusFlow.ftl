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
                        <div class="grayFonts">支付成功</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['PAYSUCCESS'])!}</div>
                    </div>
                </li>
                <li class="box green-border-tooltip <#if (orderDetail.orderOperate['LOCALCONFIRM'])??>passed</#if>">
                    <span class="boxIcon"></span>
                    <span class="line"></span>
                    <div class="statusFonts">
                        <div class="grayFonts">产品确认</div>
                        <div class="dateFonts">${(orderDetail.orderOperate['LOCALCONFIRM'])!}</div>
                    </div>
                </li>               
             
            </ul>
        </div>

</#macro>