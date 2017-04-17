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
        		费用说明
        		<div class="navWrap-val">
        		<#if (orderVo.additionalInfos['FEE_DETAIL'])??>
        			<#noescape>${orderVo.additionalInfos['FEE_DETAIL']}</#noescape>
        		</#if>
        		</div>
        	</div>
        	<div class="row-box-til">
        		退改政策
        		<div class="navWrap-val">
        		<#if (orderVo.additionalInfos['REFUND_POLICY'])??>
        			<#noescape>${orderVo.additionalInfos['REFUND_POLICY']}</#noescape>
        		</#if>
        		</div>
        	</div>
    </div>
    </#if>
    <!-- 订单费用说明信息结束 -->
</#macro>