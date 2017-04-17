<#if success == "true" && coupons?? && coupons?size gt 0>
<#list coupons as coupon>
<tr>
    <td class="blueFontsNum">${(coupon.couponCode)!}</td>
    <td class="blueFonts">${(coupon.name)!}</td>
    <td>
        <input class="voucherPerNum" type="text" value="${(coupon.unit)!}" maxlength="2" data-cv="required,positiveNum" readonly="readonly" >张/人
    </td>
    <td><input class="voucherTotalNum" type="text" value="${(coupon.totalNum)!'N'}" data-cv="required,positiveNum" readonly="readonly" maxlength="7" >张</td>
    <td>${(coupon.statusDesc)!}</td>
    <td>
        <a class="greenFontsLink viewVoucherInfo" data-val="${(coupon.couponId)!}" href="javascript:;">查看</a>
    	<#if coupon.status == 'NOSENDDING'>
        	<a class="greenFontsLink edit" href="javascript:;">编辑</a>
        	<a class="greenFontsLink save"  href="javascript:;" data-val="${(coupon.couponId)!}" style="display: none;">保存</a>
        </#if>
        <#if (coupon.price)??>
        <#else>
             <a class="greenFontsLink delVoucher" data-val="${(coupon.couponId)!}" href="javascript:;">删除</a>
        </#if>
        <#if coupon.status != 'NOSENDDING'>
         <a class="greenFontsLink couponUserDetail" data-val="${(coupon.couponId)!}" href="javascript:;">使用明细</a>
        </#if>
        <#if coupon.status == 'SENDDING'>
        <a class="greenFontsLink terminateCoupon" data-val="${(coupon.couponId)!}" href="javascript:;">停止发放</a>
        </#if>
    </td>
</tr>
</#list>
</#if>