<#macro priceCalendar>
<script type="text/javascript" src="${basepath}/resources/javascripts/product/back/prevDetail_calendar.js"></script>
<script type="text/javascript" src="${host}/js/vendor/underscore.js"></script>
<script type="text/javascript">
var productId = "${(product.id)!}";
var productPid = "${(product.pid)!}";
var pName = "${(product.pName)!}";
var de_SelectedDay = "${(selectedDay)!}";
var de_AdultNum = "${(adultNum)!}";
var de_ChildNum = "${(childNum)!}";
</script>
<div class="price-order-block clearfix">
	<input id="initMarketPrice" type="hidden"/>
	<input id="initAdultPrice" type="hidden"/>
    <div class="custom-price-calendar-container left-price-info" id="calendar-container">
        <ul class="week-day-title top clearfix">
            <li>日</li>
            <li>一</li>
            <li>二</li>
            <li>三</li>
            <li>四</li>
            <li>五</li>
            <li>六</li>
        </ul>
        <div class="wrap-cover-bg"></div>
        <div class="common-tab-bar">
			<div class="tab-top"></div>
			<div class="cal-mon-tab-container">
	        	<ul class="common-tab calendar-month-tab clearfix"></ul>
	        </div>
            <div class="mon-nav-btns">
                <span class="pre-mon-btn"></span>
                <span class="next-mon-btn"></span>
            </div>
        </div>

        <div class="calendarTables">
            <ul class="week-day-title clearfix">
                <li>日</li>
                <li>一</li>
                <li>二</li>
                <li>三</li>
                <li>四</li>
                <li>五</li>
                <li>六</li>
            </ul>
        </div>
    </div>

    <div class="right-order-info">
        <div class="tag-block clearfix">
            <span class="tag-icon"></span>
             <div class="trip-tag-list">
            <#if (product.tags)??>
            <#list product.tags as tag>
            	<span class="trip-tag tag-bg-${(tag_index + 1)!}">${(tag)!}</span>
            </#list>
            </#if>
            </div>
        </div>
        <div class="trip-info-block">
            <table class="trip-info-table">
            	<colgroup>
                    <col width="22">
                    <col width="82">
                    <col width="">
                </colgroup>
                <tbody>
                    <tr>
                        <td><span class="date-icon"></span></td>
                        <th>出行时间：</th>
                        <td class="grayFont" id='selectedDay'>请选择出行日期</td>
                        <input type="hidden" id="isBefore">
                        <input type="hidden" id="inAdvanceDays">
                    </tr>
                    <tr>
                        <td><span class="departure-icon"></span></td>
                        <th>出发地：</th>
                        <td>${(product.from)!}</td>
                    </tr>
                    <tr>
                        <td><span class="destination-icon"></span></td>
                        <th>目的地：</th>
                        <td>
                        	<#assign is_diff=false>
                        	<#list (product.to)! as toCity>
                        		<#if toCity_index != 0>
                        			,
                        		<#else>
                        			<#if toCity?? && toCity == "冲绳">
                        				<#assign is_diff=true>
                        			</#if>
                        		</#if>
                        		${(toCity)!}
                        	</#list>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="prod-type-block">
            <div class="prod-type-item" id="normalBlock">
                <div class="prod-by-person">
                    <span class="person-icon"></span>
                    <span class="title" style="margin-left:8px;">成人:</span>
                    <div class="dropdown" style="width: 56px;" id="adultNumDD">
                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                            <span class="activeFonts">2</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0);">1</a></li>
                            <li class="active"><a href="javascript:void(0);">2</a></li>
                        </ul>
                    </div>
                    <span class="title" style="margin-left:10px;">儿童(2-12周岁):</span>
                    <div class="dropdown" style="width: 56px;" id="childNumDD">
                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                            <span class="activeFonts">0</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="active"><a href="javascript:void(0);">0</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="prod-type-item" style="display: none;" id="packageBlock">
            </div>
        </div>
        <div class="order-block">
            <div class="cur-price">
                <span class="buy-icon"></span>
                <span class="title">价格: </span>
                <span class="price-text">￥<span id="currentPrice"></span><em>起</em></span>
                <span class="price-tooltip">价格说明</span>
            </div>
            <div class="discard-price" id="marketPriceBlock">
                <span class="title">市场价: </span>
                <span class="price-text">￥<span id="marketPrice"></span><em>起</em></span>
            </div>
            <div class="order-btn" id="bookBtn">立即预订</div>
            <div class="want-order-btn" id="wantBookBtn" style="display:none">想去</div>
            <div class="share-info">
                <span class="share-icon"></span>
                <span class="title">分享：</span>
                <span class="qrCode-icon qrCode-tooltip"></span>
                <span class="wishlist actived"></span>
                <span class="wishedTip">已加入心愿清单</span>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="ac-payHintWindow">
    <div class="modal-dialog" style="width: 450px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">验证提示</h4>
            </div>
            <div class="modal-body">
                <p>人数太多</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="$('#ac-payHintWindow').modal('hide');">关闭</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" data-backdrop="static" data-keyboard="false" id="ac-payHintWindow2">
    <div class="modal-dialog" style="width: 450px;height:195px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="javascript:cpg(false);">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body interestedTipText">
                <p>该日期暂未开放购买</p>
                <p>当真想去？预留手机号等开卖通知吧！</p>
                <p style="margin: 20px 0 0 0;">
                    手机号：<input id='interestedPhone' type="text" placeholder="请输入手机号" style="width:207px;"/>
                </p>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="javascript:cpg(true);">OK</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" data-backdrop="static" data-keyboard="false" id="ac-payHintWindow3">
    <div class="modal-dialog" style="width: 450px;height:195px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="javascript:cpg2();">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body interestedTipText">
                <p>该产品需提前N天预订，请重新选择日期哦</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="javascript:cpg2();">明白了</a>
            </div>
        </div>
    </div>
</div>

<!-- 价格说明 -->
<script type="text/html" id="priceTip_template">
    <div class="price-tip-info">
        <p class="title">1.起价说明</p>
        <p class="cnt">本起价是价格类型中按2位成人出行所核算的单人价格，您最终提交的价格会根据您选择的套餐及出发日期、出行人数以及附加服务的不同而有所差别。</p>
        <p class="title">2.儿童价说明</p>
        <p class="cnt">12周岁以上儿童与成人同价，购买时需以成人计算;<br>12周岁以下儿童价均不占床。</p>
        <p class="title">3.婴儿价说明</p>
        <p class="cnt">两岁以下婴儿，需与客服联系相应操作。</p>
    </div>
</script>

</#macro>