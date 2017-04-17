<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

<@html.htmlIndex title="真旅行"
		  	remoteCssFiles=["mstatic/css/book.css", "mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js","mstatic/js/vendor/jquery.lazyload.js","mstatic/js/vendor/underscore-min.js","mstatic/js/vendor/hammer.min.js"]
		  	localJsFiles=["product/weixin/chooseTrip.js"]>
	<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" tabindex="0">
	    <@head.headerBar title="选择日期和人数"></@head.headerBar>

	    <section class="main-container">
            <div class="calendar-wrapper"></div>

            <input type="hidden" id="pid" value="${product.pid!}">
            <input type="hidden" id="productId" value="${product.id!}">
            <!--  出行时间  -->
            <input type="hidden" id="selectedDay" value="">
            <input type="hidden" id="selectedPackageId" value="">
            <input type="hidden" id="singleRoomPrice" value="">
            <input id="nature" type="hidden" value="${(product.nature)!''}">

            <!--  出行人数及套餐  -->
            <div class="trip-num-wrapper">
                <div class="head clearfix">
                    <div class="title fl">出行人数</div>
                    <div class="menu-select fr">
                        套餐<label class="switch-checkbox"><i></i></label>
                    </div>
                </div>
                <div class="trip-num-block">
                    <div class="passenger-type adult clearfix">
                        <div class="fl">
                            <span>成人</span><Br>
                            <span class="price">-- --</span>
                        </div>
                        <div class="fr">
                            <div class="plus-minus-comp clearfix">
                                <span class="minus-mark">-</span>
                                <span class="result">2</span>
                                <span class="plus-mark">+</span>
                            </div>
                        </div>
                    </div>
                    <div class="passenger-type child clearfix">
                        <div class="fl">
                            <span>儿童<i>（2-12周岁）</i></span><Br>
                            <span class="price">-- --</span>
                        </div>
                        <div class="fr">
                            <div class="plus-minus-comp clearfix">
                                <span class="minus-mark disabled">-</span>
                                <span class="result">0</span>
                                <span class="plus-mark">+</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="menu-block" style="display: none;">
                    <ul class="menu-list" id="packageInfoDiv">
                    </ul>
                </div>
            </div>
            <!--  下一步  -->
            <div class="total-price-block clearfix">
                <span class="total-price fl">
                    总价<em id="totalPrice">-- --</em>
                </span>
                <span class="next fl submit">下一步</span>
                <span class="next fl wantgo" style="display:none;" data-rel="popup" href="javascript:void(0);">想去</span>
            </div>
        </section>
    </div>

    <div class="ui-content" data-role="popup" id="wantgoWindow" data-transition="none" data-history="false" data-position-to="window" data-theme="a" data-overlay-theme="b" data-dismissible="false">
        <div class="dlg-cnt pre-order-cnt">
            <p>该日期暂未开放购买</p>
            <p>当真想去？预留手机号等开卖通知吧！</p>
            <p class="p-pre-order">手机号：<input type="tel" id="want-go-tel" placeholder="请输入手机号" data-role="none">
            </p>
        </div>
        <div class="dlg-foot">
            <a class="btn-confirm pre-order-btn" data-role="none" href="javascript:void(0);">OK</a>
        </div>
    </div>

    <div class="ui-content" data-role="popup" id="alert-errorhint-dialog" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
        <p class="dlg-cnt" id="errorHintMsg"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" >好的</a>
        </div>
    </div>
    <!--价格说明弹窗-->
    <div class="inst-dialog" id="inst-dialog" data-role="popup" data-position-to="window" data-theme="a" data-overlay-theme="b" data-history="false">
        <div class="inst-dialog-content">
            <p>1、本起价是按2位成人出行所核算的单人价格。</p>
            <p>2、您最终提交的价格会根据您所选择的出发日期、出行人数以及所选附加服务的不同而有所差别。</p>
        </div>
        <div class="inst-dialog-foot">
            <a class="btn-com btn-close" data-role="none" href="javascript:void(0);" data-rel="back">关 闭</a>
        </div>
    </div>

    <!--  日历模板 -->
    <script id="calendarTemplate" type="text/template">
        <div class="calendar-header">
            <nav class="calendar-nav clearfix">
                <%
                var dataList = data;

                var yearMonth = 0;
                var day = 0;
                _.each(dataList, function(item, index){
                    var currentYearMonth = item.date * 1;
                    if(yearMonth == 0 || currentYearMonth < yearMonth){
                        yearMonth = currentYearMonth;
                        //for(var wxDayWo in item.wxDayWoList){
                        //    if(day == 0 || wxDayWo.day < day){
                        //        day = wxDayWo.day;
                        //     }
                        //}
                    }
                });
                var dateY = yearMonth.toString().substr(0,4);
                var dateM = parseInt(yearMonth.toString().substr(4)) - 1;
                //var dateD = day.toString();
                var curDate = new Date(dateY, dateM, 1);

                var hasPrev = false;
                var hasNext = false;
                _.each(_.pluck(dataList, 'date'), function(ele, index){
	                var year = ele.substr(0,4) ;
	                var month = parseInt(ele.substr(4))-1 ;
	                var day = '1' ;
	                var eleDate = new Date(year, month, day) ;
                    if (eleDate < curDate) {
                        hasPrev = true;
                    } else if (eleDate > curDate) {
                        hasNext = true;
                    }
                });
                %>
                <div class="tab-wrapper">
                    <ul class="tablist clearfix">
                        <% _.each(_.pluck(dataList, 'date'), function(ele, index){

                        %>
                        <li class="<%= (ele.substr(0,4) == dateY && (parseInt(ele.substr(4)) == (dateM + 1))) ? 'current' : '' %>"><%= ele.substr(0,4) %>年<%= ele.substr(4) %>月</li>
                        <% }); %>
                    </ul>
                </div>
                <div class="nav-btn">
                    <span class="prev <%= hasPrev ? '' : 'disabled' %>"></span>
                    <span class="next <%= hasNext ? '' : 'disabled' %>"></span>
                </div>
                <a href="#inst-dialog" data-rel="popup" data-role="none" class="price-tip"><i></i> 价格说明</a>
            </nav>
        </div>
        <div class="calendar">
            <ul class="calendar-title clearfix">
                <li>日</li>
                <li>一</li>
                <li>二</li>
                <li>三</li>
                <li>四</li>
                <li>五</li>
                <li>六</li>
            </ul>
            <ul class="calendar-body clearfix">
            <%_.each(dataList, function(item, index) {%>
                <%
                var monthWrapper = item;
                var month = item.date;
                var curMonthDate = new Date(month[0], month[1]-1, 1); // 当月1号
                var nextMonthDate = new Date(month[0], month[1], 1);    // 下月1号
                var curMonthDays = (nextMonthDate - curMonthDate)/(1000*60*60*24);  // 当月总天数
                var firstWeekDay = curMonthDate.getDay();  // 当前月1号是周几, 范围0-6
                var lastWeekDay = nextMonthDate.getDay() - 1 < 0 ? 6 : nextMonthDate.getDay() - 1;  // 计算当月最后一天是周几，填补日历最后的空格
                %>
                <li class="calendar-item <%= (index == 0) ? 'current' : '' %>">
                    <table class="calTable ct-<%=month[0]%>-<%=month[1]%>">
                        <tbody>
                            <tr>
                            <%
                                for (var i = 0; i < firstWeekDay; i++) {
                            %>
                                <td></td>
                            <% } %>
                            <%
                                var w1 = firstWeekDay == 0 ? 7 : firstWeekDay;
                                for (var currentDay = 1; currentDay <= curMonthDays; currentDay++) {
                                    if (currentDay % 7 == (7 - w1) && currentDay != curMonthDays) {
                            %>
                                <td><%= geneTdCont(item, currentDay) %></td>
                            </tr>
                            <tr>
                            <%
                                } else if (currentDay != curMonthDays) {
                            %>
                                <td><%= geneTdCont(item, currentDay) %></td>
                            <%
                                } else if (currentDay == curMonthDays) {
                            %>
                                <td><%= geneTdCont(item, currentDay) %></td>
                            <%  for (var t = 0; t < 6 - lastWeekDay; t++) { %>
                                <td></td>
                            <%  }}} %>
                            </tr>
                        </tbody>
                    </table>
                </li>
            <%});%>
            </ul>
        </div>
    </script>

    <script>

        function geneTdCont(item, day) {
            var result = "";
            var dayCont = _.findWhere(item.wxDayWoList, {day: day});
            if (dayCont === undefined) {
                result += "<div class='tdContWrapper disabled'>";
                result += "<p class='day'>" + day + "</p>";
            } else {

                if (dayCont.availableStock <= 0) {    //  售罄
                    result += "<div class='tdContWrapper disabled'>";
                    result += "<p class='day'>" + dayCont.day + "</p>";
                    result += "<div class='priceWrapper sold-out'>";
                    result += "<p class='sold-out'>售罄</p>";
                } else {
                    result += "<div class='tdContWrapper'>";
                    result += "<p class='day'>" + dayCont.day + "</p>";
                    result += "<input name='dateWithYear' type='hidden' value='" + dayCont.dateWithYear + "'>";
                    result += "<input name='availableStock' type='hidden' value='" + dayCont.availableStock + "'>";
                    result += "<input name='adultPrice' type='hidden' value='" + dayCont.adultPrice + "'>";
                    result += "<input name='childPrice' type='hidden' value='" + dayCont.childPrice + "'>";
                    result += "<input name='singleRoomPrice' type='hidden' value='" + dayCont.singleRoomPrice + "'>";
                    result += "<input name='containsPackage' type='hidden' value='" + dayCont.containsPackage + "'>";
                    result += "<input name='saleStatus' type='hidden' value='" + dayCont.saleStatus + "'>";

                    if (dayCont.saleStatus =='NOT_SCHEDULED') {
                        result += "<div class='priceWrapper menu'>";
                        result += "<p class='menu'>未开放</p>";
                    } else if (dayCont.containsPackage) {
                        result += "<div class='priceWrapper menu'>";
                        result += "<p class='menu'>套餐</p>";
                    } else {
                        result += "<div class='priceWrapper'>";
                    }
                }
                result += "<p class='price'>¥" + dayCont.adultPrice + "</p>";
            }
            result += "</div></div>";
            return result;
        }
    </script>

</@html.htmlIndex>
