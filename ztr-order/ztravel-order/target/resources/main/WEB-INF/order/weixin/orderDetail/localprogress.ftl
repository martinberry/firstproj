 <!-- 订单进度信息开始 -->
 <#macro orderProgress>
 		<div class="order-state">
            <div class="state-title">订单状态</div>
            <div class="state-detail">
                <ul>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['CREATE'])??><#if (orderVo.orderOperate['PAY'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i>填写订单</span>
                        <span class="list-time">${(orderVo.orderOperate['CREATE'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['PAY'])??><#if (orderVo.orderOperate['PAYSUCCESS'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i>支付订单</span>
                        <span class="list-time">${(orderVo.orderOperate['PAY'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['PAYSUCCESS'])??><#if (orderVo.orderOperate['OPCONFIRM'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i>支付成功</span>
                        <span class="list-time">${(orderVo.orderOperate['PAYSUCCESS'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['OPCONFIRM'])??>done-state<#else>undo-state</#if>"></i>产品确认</span>
                    	<span class="list-time">${(orderVo.orderOperate['OPCONFIRM'])!''}</span>
                    </li>             
                </ul>
            </div>
            <a class="detailBtn" href="javascript:void(0);" data-role="none">详情</a>
        </div>
   </#macro>