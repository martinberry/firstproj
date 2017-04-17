<div class="moduleContent">
    <div class="titleContent clearfix">
        <h3 class="titleFonts blue2fb0c4">价格明细</h3>
    </div>
    <table class="leftGrayTh" style="text-align:center;">
        <colgroup>
            <col width="150">
            <col width="150">
            <col width="140">
            <col width="180">
            <col width="140">
            <col width="100">
        </colgroup>
        <tbody>
        <tr>
            <th rowspan="${priceRow!}">必选</th>
            <#if (localDetail.products) ??>
            <#list (localDetail.products) as product>
            <td rowspan="${priceRow!}">${(product.costPriceName)!''}</td>
            </#list>
            </#if>
            <td class="textAlignRight">成人价</td>
            <td>
                <div>
                    <span class="price">￥${((localDetail.feesDetail).adultPrice)!''} X ${((localDetail.feesDetail).adultNum)!''}</span>
                </div>
            </td>
            <td class="orangeFonts">￥${((localDetail.feesDetail).totalAdultPrice)!''}</td>
        </tr>
        <tr>
            <td class="textAlignRight">儿童价</td>
            <td>
                <div>
                    <span class="price">￥${((localDetail.feesDetail).childPrice)!''} X ${((localDetail.feesDetail).childNum)!''}</span>
                </div>
            </td>
            <td class="orangeFonts">￥${((localDetail.feesDetail).totalChildPrice)!''}</td>
        </tr>           
        
        
        
          <#if (localDetail.feesDetail).couponName??>
          <tr>
          <th>代金券优惠</th>
                    <td colspan="2">${((localDetail.feesDetail).couponName)!''}</td>
                    <td colspan="2" class="textAlignRight orangeFonts">-￥<span>${((localDetail.feesDetail).couponPrice)!''}</span></td>
           </tr>
         </#if>  
           
           
        <tr>
            <th>总价</th>
            <td colspan="3" style="border-right: 0;"></td>
            <td class="orangeFonts" style="border-left: 0;">
                <b class="fontsWeight16">￥${((localDetail.feesDetail).payPrice)!''}</b>
            </td>
        </tr>
        </tbody>
    </table>
</div>