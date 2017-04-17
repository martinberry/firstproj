<#macro orderAdditional>
<!-- 订单费用说明信息开始 -->
    <#if (orderVo.additionalInfos)??>
    <div class="row-til">
        <div class="borderB">
            <i class="costIcon"></i>费用说明
        </div>
    </div>
    <div class="row-box">
        	<div class="row-box-til">
        		费用包含
        		<div class="navWrap-val">
        		<#if (orderVo.additionalInfos['FEE_INCLUDE'])??>
        			<#noescape>${orderVo.additionalInfos['FEE_INCLUDE']}</#noescape>
        		</#if>
        		</div>
        	</div>
        	<div class="row-box-til">
        		费用不含
        		<div class="navWrap-val">
        		<#if (orderVo.additionalInfos['FEE_NOT_INCLUDE'])??>
        			<#noescape>${orderVo.additionalInfos['FEE_NOT_INCLUDE']}</#noescape>
        		</#if>
        		</div>
        	</div>
        	<div class="row-box-til">
        		赠送项目
        		<div class="navWrap-val">
        		<#if (orderVo.additionalInfos['GIFT_ITEMS'])??>
        			<#noescape>${orderVo.additionalInfos['GIFT_ITEMS']}</#noescape>
        		</#if>
        		</div>
        	</div>
        	<div class="row-box-til">
        		退改政策
        		<div class="navWrap-val"><#if orderVo.additionalInfos['REFUND_POLICY'] ?? ><#noescape>${(orderVo.additionalInfos['REFUND_POLICY'])!}</#noescape><#else>本产品为特惠产品，订单确认后不接受任何变更和退订</#if></div>
        	</div>
    </div>
    </#if>
    <!-- 订单费用说明信息结束 -->
</#macro>