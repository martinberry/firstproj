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
            <th rowspan="${priceRow!''}">必选</th>
            <#if (visaDetail.products) ??>
            <#list (visaDetail.products) as product>
            <td rowspan="${priceRow!''}">${(product.costPriceName)!''}</td>
            </#list>
            </#if>
            <td class="textAlignRight">成人价</td>
            <td>
                <div>
                    <span class="price">￥${((visaDetail.feesDetail).adultPrice)!''} X ${((visaDetail.feesDetail).adultNum)!''}</span>
                </div>
            </td>
            <td class="orangeFonts">￥${((visaDetail.feesDetail).totalAdultPrice)!''}</td>
        </tr>
        <tr>
            <td class="textAlignRight">儿童价</td>
            <td>
                <div>
                    <span class="price">￥${((visaDetail.feesDetail).childPrice)!''} X ${((visaDetail.feesDetail).childNum)!''}</span>
                </div>
            </td>
            <td class="orangeFonts">￥${((visaDetail.feesDetail).totalChildPrice)!''}</td>
        </tr>
        
        
        
        
        <#if (visaDetail.feesDetail).couponName ??>
          <tr>
          <th>代金券优惠</th>
                    <td colspan="2">${((visaDetail.feesDetail).couponName)!''}</td>
                    <td colspan="2" class="textAlignRight orangeFonts">-￥<span>${((visaDetail.feesDetail).couponPrice)!''}</span></td>
           </tr>
        </#if>
        <tr>
            <th>总价</th>
            <td colspan="3" style="border-right: 0;"></td>
            <td class="orangeFonts" style="border-left: 0;">
                <b class="fontsWeight16">￥${((visaDetail.feesDetail).payPrice)!''}</b>
            </td>
        </tr>
        </tbody>
    </table>
</div>