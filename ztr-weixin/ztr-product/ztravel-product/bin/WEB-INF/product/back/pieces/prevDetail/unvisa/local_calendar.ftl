<#macro priceCalendar>
<script type="text/javascript" src="${basepath}/resources/javascripts/product/back/pieces/prevDetail/unvisa_calendar.js"></script>
<div class="panel-wrap">
<input type="hidden" name="productId" id="productId" value="${(product.id)!''}">
<input type="hidden" name="productNature" id="productNature" value="${(product.productNature)!''}">
            <div class="panelWrap-title">
                <i class="visaIcon travelDate"></i>出行日期
            </div>
            <div class="calendarWrap" id="visaCalendar"></div>
            <div class="panelWrap-title">
                <i class="visaIcon priceType"></i>价格类型
            </div>
            <ul class="priceGroup clearfix">
            <#list product.costPrice as costPrice>
                <li class="costPrice">
                    <a href="javascript:void(0);">${costPrice!''}</a>
                </li>
            </#list>
            </ul>

            <#list product.costPriceId as costPriceId>
             <input type="hidden" name="costPriceId" value="${costPriceId!''}">
            </#list>

            <div class="panelWrap-head">
                <div class="panelWrap-box">
                    <i class="visaIcon persionIcon"></i>成人：
                    <div class="dropdown" style="width: 56px;">
                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                            <span class="activeFonts">2</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu adultnum">
                            <li><a href="javascript:void(0);">1</a></li>
                            <li class="active"><a href="javascript:void(0);">2</a></li>
                            <li><a href="javascript:void(0);">3</a></li>
                            <li><a href="javascript:void(0);">4</a></li>
                        </ul>
                    </div>
                    <#list product.adultPrice as adultPrice>
                    <#if adultPrice??>
                    <span class="priceNum" style="display:none;">￥${(adultPrice)?string('0.00')}<span>/人</span></span>
                    </#if>
                    </#list>

                </div>

                <div class="panelWrap-box-child" style="display:block;">
                    <span class="panelWrap-empty"></span>儿童：
                    <div class="dropdown" style="width: 56px;">
                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                            <span class="activeFonts">0</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu childnum">
                            <li class="active"><a href="javascript:void(0);">0</a></li>
                            <li><a href="javascript:void(0);">1</a></li>
                            <li><a href="javascript:void(0);">2</a></li>
                            <li><a href="javascript:void(0);">3</a></li>
                            <li><a href="javascript:void(0);">4</a></li>
                        </ul>
                    </div>
                    <#list product.childPrice as childPrice>
                    <#if childPrice??>
                    <span class="priceNum" style="display:none;">￥${(childPrice)?string('0.00')}<span>/人</span></span>
                    <#else>
                     <span class="priceNum" style="display:none;">''<span>/人</span></span>
                    </#if>
                    </#list>
                </div>

            </div>

            <div class="panelWrap-price">
                <i class="visaIcon paymentIcon"></i>价格：<span id="totalPrice"></span>
            </div>
            <div class="visaSubmit">
                <a href="javascript:void(0);" class="bigOrangeBtn active" id="orderinstance">立即预订</a>
            </div>
            <div id="leftFlag"></div>
          </div>
</#macro>

