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
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['PAYSUCCESS'])??><#if (orderVo.orderOperate['GIFTBOX'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i><#if (orderVo.orderOperate['confirmed'])??>支付成功，产品已确认<#else>支付成功</#if></span>
                        <span class="list-time">${(orderVo.orderOperate['PAYSUCCESS'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['GIFTBOX'])??><#if (orderVo.orderOperate['OUTNOTICE'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i>心意盒子</span>
                    	<span class="list-time">${(orderVo.orderOperate['GIFTBOX'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['OUTNOTICE'])??><#if (orderVo.orderOperate['ZTMANAGER'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i>出行通知</span>
                        <span class="list-time">${(orderVo.orderOperate['OUTNOTICE'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['ZTMANAGER'])??><#if (orderVo.orderOperate['EVALATE'])??>done-state<#else>current-state</#if><#else>undo-state</#if>"></i>出行中</span>
                        <span class="list-time">${(orderVo.orderOperate['ZTMANAGER'])!''}</span>
                    </li>
                    <li class="state-list">
                        <span class="list-name"><i class="<#if (orderVo.orderOperate['EVALATE'])??>done-state<#else><#if (orderVo.orderOperate['COMPLETED'])??>current-state<#else>undo-state</#if></#if>"></i>旅游评价</span>
                        <span class="list-time">${(orderVo.orderOperate['EVALATE'])!''}</span>
                    </li>
                </ul>
            </div>
            <a class="detailBtn" href="javascript:void(0);" data-role="none">详情</a>
        </div>
   </#macro>