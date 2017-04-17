<#macro priceExplain>
   <div class="commonModel">
        <div id="priceExplain" class="anchor"></div>
        <div class="commonTitle"><i class="commonIcon costIcon"></i>费用说明</div>
        <ul class="wrapperList clearfix">
            <li class="row twoRow">
                <h3 class="titleFonts">费用包含</h3>
                <ul class="listDetails">
                    <#noescape>${(product.additionalInfos['FEE_INCLUDE'])!}</#noescape>
                </ul>
            </li>
            <li class="row twoRow">
                <h3 class="titleFonts">真旅行赠送项目</h3>
                <ul class="listDetails">
                    <#noescape>${(product.additionalInfos['GIFT_ITEMS'])!}</#noescape>
                </ul>
            </li>
        </ul>
        <ul class="wrapperList clearfix">
            <li class="row twoRow">
                <h3 class="titleFonts">费用不含</h3>
                <ul class="listDetails">
                    <#noescape>${(product.additionalInfos['FEE_NOT_INCLUDE'])!}</#noescape>
                </ul>
            </li>
            <li class="row twoRow">
                <h3 class="titleFonts">退改政策</h3>
                <ul class="listDetails">
                    <ol>
                        <li>本产品为即时确认，订单确认后原则上不能更改出行日期。如有特殊需求，请与客服沟通，我们将努力协调</li>
                        <li>订单一经确认，不可取消</li>
                    </ol>
                </ul>
            </li>
        </ul>
    </div>
</#macro>