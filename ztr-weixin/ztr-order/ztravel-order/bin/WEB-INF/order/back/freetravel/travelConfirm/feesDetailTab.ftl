<#assign price = (orderDetail.price)!/>
<#assign addProducts = (orderDetail.additionalProducts)!/>
<#if (price.packageId)??>
<tr class="defaultRowSpan">
    <th rowspan="1">必选产品</th>
    <td>
        <span id="packageName">${price.packageName!}</span>
    </td>
    <td>
        <input type="hidden" id="packageId" value="${price.packageId!}">
        <input type="hidden" id="packagePrice" value="${(price.totalPackagePrice?number)/1}">
        <input type="hidden" id="totalPackagePrice" value="${price.totalPackagePrice!}">
        <input type="hidden" id="sumNum" value="${price.sumNum!}">
        <span class="requiredContent">¥ ${price.totalPackagePrice!} * 1份</span>
        <span class="requiredEditContent">
            <span>¥</span>
            <input type="text" style="width: 100px;" name="packagePriceInputer"  value="${(price.totalPackagePrice?number)/1}" maxlength="5" onclick="focus();select()">
            <span> x </span>
            <span id="packageNum">1</span>
            <span>份</span>
            <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>价格必须是1到99999之间的整数</div>
        </span>
    </td>
    <td>
        <div class="editable">
            <span class="orangeWeight16">¥ </span><span class="orangeWeight16" id="totalPackagePriceInputer">${price.totalPackagePrice!}</span>
        </div>
    </td>
</tr>
<#else>
<#if (price.adultPrice)?? && (price.adultNum)?? && price.adultNum != 0>
<tr class="defaultRowSpan">
    <th rowspan="3">必选产品</th>
    <td>
        <span>成人价</span>
    </td>
    <td>
        <input type="hidden" id="adultPrice" value="${((price.adultPrice)?number)/1}">
        <input type="hidden" id="totalAdultPrice" value="${price.totalAdultPrice!}">
        <span class="requiredContent">¥ ${(price.adultPrice)!''} * ${price.adultNum!}人</span>
        <span class="requiredEditContent">
            <span>¥</span>
            <input type="text" style="width: 100px;" name="adultPriceInputer"  value="${((price.adultPrice)?number)/1}" maxlength="5" onclick="focus();select()">
            <span> x </span>
            <span id="adultNum">${price.adultNum!}</span>
            <span>人</span>
            <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>价格必须是1到99999之间的整数</div>
        </span>
    </td>
    <td>
        <div class="editable">
            <span class="orangeWeight16">¥ </span><span class="orangeWeight16" id="totalAdultPriceInputer">${price.totalAdultPrice!}</span>
        </div>
    </td>
</tr>
</#if>
<#if (price.childrenPrice)?? && (price.childNum)?? && price.childNum != 0>
<tr class="defaultRowSpan">
    <td>
        <span>儿童价</span>
    </td>
    <td>
        <input type="hidden" id="childrenPrice" value="${((price.childrenPrice)?number)/1}">
        <input type="hidden" id="totalChildPrice" value="${price.totalChildPrice!}">
        <span class="requiredContent">¥ ${(price.childrenPrice)!''} * ${price.childNum!}人</span>
        <span class="requiredEditContent">
            <span>¥</span>
            <input type="text" style="width: 100px;" name="childrenPriceInputer"  value="${((price.childrenPrice)?number)/1}" maxlength="5" onclick="focus();select()">
            <span> x </span>
            <span id="childNum">${price.childNum!}</span>
            <span>人</span>
            <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>价格必须是1到99999之间的整数</div>
        </span>
    </td>
    <td>
        <div class="editable">
            <span class="orangeWeight16">¥ </span><span class="orangeWeight16" id="totalChildPriceInputer">${price.totalChildPrice!}</span>
        </div>
    </td>
</tr>
</#if>
<#if (price.singleRoomDiff)?? && (price.singleNum)?? && price.singleNum != 0>
<tr class="defaultRowSpan">
    <td>
        <span>单房差</span>
    </td>
    <td>
        <input type="hidden" id="singleRoomDiff" value="${((price.singleRoomDiff)?number)/1}">
        <input type="hidden" id="totalSingleDiff" value="${price.totalSingleDiff!}">
        <span class="requiredContent">¥ ${(price.singleRoomDiff)!''} * ${price.singleNum!}人</span>
        <span class="requiredEditContent">
            <span>¥</span>
            <input type="text" style="width: 100px;" name="singleRoomDiffInputer"  value="${((price.singleRoomDiff)?number)/1}" maxlength="5" onclick="focus();select()">
            <span> x </span>
            <span id="singleNum">${price.singleNum!}</span>
            <span>人</span>
            <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>价格必须是1到99999之间的整数</div>
        </span>
    </td>
    <td>
        <div class="editable">
            <span class="orangeWeight16">¥ </span><span class="orangeWeight16" id="totalSingleDiffInputer">${price.totalSingleDiff!}</span>
        </div>
    </td>
</tr>
</#if>
</#if>
<#list addProducts as addProduct>
<tr>
    <#if addProduct_index ==  0 >
    <th rowspan="${addProducts?size}">附加产品</th>
    </#if>
    <td>${addProduct.name!}</td>
    <td>¥ ${addProduct.price!} * ${addProduct.num!}人</td>
    <td class="orangeWeight16">¥ ${addProduct.totalPrice!}</td>
</tr>
</#list>
<#if (price.couponName)?? && (orderDetail.order.discountTotalSub)?? && orderDetail.order.discountTotalSub != ".00">
<tr>
    <th>网站优惠</th>
    <td>优惠折扣券</td>
    <input type="hidden" id="couponId" value="${(price.couponId)!''}">
    <td id="couponName">${(price.couponName)!''}</td>
    <td class="orangeWeight16">-¥ ${(orderDetail.order.discountTotalSub)!''}</td>
</tr>
</#if>
<tr>
    <th>更后单价</th>
    <td colspan="3" class="textAlignRight">
        <span>（原订单价: ¥${(originalPayAmount)!''}）</span>
        <span class="orangeWeight22">¥ ${(orderDetail.order.payAmount)!''}</span>
    </td>
</tr>