<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "product_menu.ftl" as productMenu/>
<@html.htmlIndex jsFiles=["common/pagination.js","product/back/cost_manage_common.js","product/back/cost_manage_combination.js","product/back/common.js","js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js"]
	cssFiles=["css/bootstrap-datepicker.min.css","css/maintain/productManagement.css"]
	curModule="产品管理"
	title="底价维护">
	<@productMenu.productMenu curProductModule="costManage">
    <div class="main-container changeMainContent">
		<span id="hoteldata" name="hoteldata" style="display:none ;">${cost.hotelCosts!'{}'}</span>
		<span id="flightdata" name="flightdata" style="display:none ;">${cost.flightCosts!'{}'}</span>
		<span id="id" name="id" style="display:none ;">${id!''}</span>
		<span id="tripNights" name="tripNights" style="display:none ;">${cost.tripNights!'0'}</span>
		<input type='hidden' id='mode' value="${mode!'view'}">
		<input type='hidden' id='flightSingleDay' value="">
		<input type='hidden' id='hotelSingleDay' value="">
		<input type='hidden' id='producttype' value="combination">
		<input type='hidden' id='isContainFlight' value="${cost.isContainFlight?c}">
		<input type='hidden' id='isContainHotel' value="${cost.isContainHotel?c}">
            <section class="whiteBg">
                <div class="add-product-oper">
                	<div class="add-btn add-flight-btn" <#if !cost.isContainFlight || cost.isFlightAlreadyHave> style="display:none" </#if>>
	                    <span class="add-icon"></span>
	                    <span>添加机票</span>
	                </div>
	                <div class="add-btn add-hotel-btn" <#if !cost.isContainHotel || cost.isHotelAlreadyHave> style="display:none" </#if>>
	                    <span class="add-icon"></span>
	                    <span>添加酒店</span>
	                </div>
                </div>
                <#if cost.isContainFlight>
                <section class="pm-info-module flight-info-module" style="width:1300px">
                    <div class="sub-title">机票信息</div>
                    <div class="flight-info view">
                    <#if cost.flight??>
                        <div class="table-top-block clearfix">
                            <ul class="table-tab">
                                <li class="">
                                    <span>${cost.from!''}出发</span>
                                </li>
                            </ul>
                            <span class="oper-link">
                                <a href="javascript:editFlight() ;">编辑</a>
                                <a href="javascript:deleteFlight() ;">删除</a>
                            </span>
                        </div>
                        <table class="flight-info-table">
                            <colgroup>
                                <col width="80"/>
                                <col width="79"/>
                                <col width="80"/>
                                <col width="79"/>
                                <col width="80"/>
                                <col width="79"/>
                                <col width="80"/>
                                <col width="85"/>
                                <col width="80"/>
                                <col width="85"/>
                                <col width="90"/>
                                <col width="85"/>
                                <col width="90"/>
                                <col width="85"/>
                            </colgroup>
                            <tbody>
                            		<#if cost.flight.go??>
                            			<#list cost.flight.go as go>
                            				<tr>
                            					<#if go_index == 0>
			                                    <td rowspan="${(cost.flight.goNum!1) * 2}" class="go-air-range">
			                                        <span class="go-flight-icon"></span><br/>
			                                        <span>去程</span>
			                                    </td>
			                                    </#if>
			                                    <th>第几天</th>
			                                    <td>${go.offsetDays!''}</td>
			                                    <th>航司名称</th>
			                                    <td>${go.airLine!''}</td>
			                                    <th>航班号</th>
			                                    <td>${go.flightNo!''}</td>
			                                    <th>舱位</th>
			                                    <td>${go.cabin!''}</td>
			                                    <th>机型</th>
			                                    <td>${go.flightModel!''}</td>
			                                    <th>经停信息</th>
			                                    <td colspan="3">${go.stop!''}</td>
			                                </tr>
			                                <tr>
			                                    <th>出发机场</th>
			                                    <td>${go.fromAirPort!''}</td>
			                                    <th>起飞时间</th>
			                                    <td>${go.departureTime!''}</td>
			                                    <th>到达机场</th>
			                                    <td>${go.toAirPort!''}</td>
			                                    <th>到达时间</th>
			                                    <td>${go.arrivalTime!''}</td>
			                                    <th>到达偏移时间</th>
			                                    <td>${go.addDays!''}</td>
			                                    <th>出发城市</th>
			                                    <td>${go.fromCity!''}</td>
			                                    <th>到达城市</th>
			                                    <td>${go.toCity!''}</td>
			                                </tr>
                            			</#list>
                            		</#if>
                            		<#if cost.flight.middle??>
                            			<#list cost.flight.middle?keys as range>
	                    					<#assign middles=cost.flight.middle[range] >
                            				<#list middles as middle>
	                            				<tr>
	                            					<#if middle_index == 0>
				                                    <td rowspan="${middles?size * 2}" class="middle-air-range">
				                                        <span class="middle-flight-icon"></span><br/>
				                                        <span>第${range}程</span>
				                                    </td>
				                                    </#if>
				                                    <th>第几天</th>
				                                    <td>${middle.offsetDays!''}</td>
				                                    <th>航司名称</th>
				                                    <td>${middle.airLine!''}</td>
				                                    <th>航班号</th>
				                                    <td>${middle.flightNo!''}</td>
				                                    <th>舱位</th>
				                                    <td>${middle.cabin!''}</td>
				                                    <th>机型</th>
				                                    <td>${middle.flightModel!''}</td>
				                                    <th>经停信息</th>
			                                    	<td colspan="3">${middle.stop!''}</td>
				                                </tr>
				                                <tr>
				                                    <th>出发机场</th>
				                                    <td>${middle.fromAirPort!''}</td>
				                                    <th>起飞时间</th>
				                                    <td>${middle.departureTime!''}</td>
				                                    <th>到达机场</th>
				                                    <td>${middle.toAirPort!''}</td>
				                                    <th>到达时间</th>
				                                    <td>${middle.arrivalTime!''}</td>
				                                    <th>到达偏移时间</th>
				                                    <td>${middle.addDays!''}</td>
				                                    <th>出发城市</th>
			                                    	<td>${middle.fromCity!''}</td>
				                                    <th>到达城市</th>
				                                    <td>${middle.toCity!''}</td>
				                                </tr>
			                                </#list>
                            			</#list>
                            		</#if>

                            		<#if cost.flight.back??>
                            			<#list cost.flight.back as back>
                            				<tr>
                            					<#if back_index == 0>
			                                    <td rowspan="${(cost.flight.backNum!1) * 2}" class="back-air-range">
			                                        <span class="back-flight-icon"></span><br/>
			                                        <span>返程</span>
			                                    </td>
			                                    </#if>
			                                    <th>第几天</th>
			                                    <td>${back.offsetDays!''}</td>
			                                    <th>航司名称</th>
			                                    <td>${back.airLine!''}</td>
			                                    <th>航班号</th>
			                                    <td>${back.flightNo!''}</td>
			                                    <th>舱位</th>
			                                    <td>${back.cabin!''}</td>
			                                    <th>机型</th>
			                                    <td>${back.flightModel!''}</td>
			                                    <th>经停信息</th>
			                                    <td colspan="3">${back.stop!''}</td>
			                                </tr>
			                                <tr>
			                                    <th>出发机场</th>
			                                    <td>${back.fromAirPort!''}</td>
			                                    <th>起飞时间</th>
			                                    <td>${back.departureTime!''}</td>
			                                    <th>到达机场</th>
			                                    <td>${back.toAirPort!''}</td>
			                                    <th>到达时间</th>
			                                    <td>${back.arrivalTime!''}</td>
			                                    <th>到达偏移时间</th>
			                                    <td>${back.addDays!''}</td>
			                                    <th>出发城市</th>
		                                    	<td>${back.fromCity!''}</td>
			                                    <th>到达城市</th>
			                                    <td>${back.toCity!''}</td>
			                                </tr>
                            			</#list>
                            		</#if>
                                <tr>
                                    <th class="boldTh">机票备注</th>
                                    <td colspan="15">${cost.flight.airRangeRemark!''}</td>
                                </tr>
                                <tr>
                                    <th class="boldTh">内部备注</th>
                                    <td colspan="15">${cost.flight.innerRemark!''}</td>
                                </tr>
                            </tbody>
                        </table>
                        </#if>
                    </div>

                </section>
                <section class="pm-info-module price-info-module flight" style="width:1300px">
                    <div class="sub-title">成本维护</div>
                    <section class="pack-price-block" style="width:1300px">
                    	<div class="radioContent priceOperType flight">
                            <label class="radio active" name="1">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">批量操作</span>
                            </label>
                            <label class="radio" name="2">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">单日操作(点击日历选取日期)</span>
                            </label>
                        </div>
                        <div class="price-maint-block">
                        	<!--  批量修改  -->
                            <div class="batch-oper-block flight">
	                            <div class="date-oper-line">
	                                <span>机票成本</span>
	                                <input type="text" class="datepicker hasIcon startDate flight" style="width:150px;" /> 至
	                                <input type="text" class="datepicker hasIcon endDate flight" style="width:150px;"/>
	                                <span class="checkboxContent week-date flight">
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>一
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>二
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>三
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>四
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>五
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>六
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>日
	                                    </label>
	                                </span>
	                                <span class="oper-price-btn">
	                                    <button class="commonButton blue-45c8dcButton flight cost add batch">添加成本</button>
	                                    <button class="commonButton blue-45c8dcButton flight cost remove batch">清除成本</button>
	                                </span>
	                            </div>
	                            <div class="price-line">
	                                <span class="adult-price-text">成人票价</span>
	                                <input class='adultCost batch' type="text" style="width:88px;" />
	                                <span class="child-price-text">儿童票价</span>
	                                <input class='childCost batch' type="text" style="width:88px;" />
	                            </div>
	                        </div>
                            <!--  单日修改  -->
                            <div class="single-oper-block flight" style="display:none;">
                                <span>机票成本</span>
                                <span class="adult-price-text">成人票价</span>
                                <input class='adultCost single' type="text" style="width:88px;" />
                                <span class="child-price-text">儿童票价</span>
                                <input class='childCost single' type="text" style="width:88px;" />
                                <span class="oper-price-btn">
                                    <button class="commonButton blue-45c8dcButton flight cost add single">添加成本</button>
                                    <button class="commonButton blue-45c8dcButton flight cost remove single">清除成本</button>
                                </span>
                            </div>
                        </div>
                        <div class="double-price-calendar clearfix flight" style="margin-left:60px;">
                            <div class="calendar-title">
                                <span class="prev"></span>
                                <span class="cal-month left-calendar-title"></span>
                                <span class="cal-month right-calendar-title"></span>
                                <span class="next"></span>
                            </div>
                            <div class="left-calendar"></div>
                            <div class="right-calendar"></div>
                        </div>
                    </section>
                    <section class="vendor-info-block flight">
                        <table>
                            <colgroup>
                                <col width="72" />
                                <col width="400" />
                                <col width="104" />
                                <col width="" />
                            </colgroup>
                            <tbody>
                            	<span>机票供应商</span>
                                <div class="dropdown" style="width: 320px;top:1px;">
	                                <a href="javascript:void(0);" class="dropdownBtn" data-toggle="dropdown" aria-expanded="true">
	                                    <span class="activeFonts">${(cost.flightSupplier.supplierName)!""}</span>
	                                    <span class="caret"></span>
	                                </a>
	                                <ul class="dropdown-menu">
	                                    <#list cost.allSuppliers as supplier>
											<li onclick=setDropDownMenu(this,'${supplier.supplierId}','${supplier.supplierNameTransfer}','#flightSupplierId');><a href="javascript:void(0);">${supplier.supplierName}</a></li>
										</#list>
	                                </ul>
	                                <input type="hidden" id='flightSupplierId' value='${(cost.flightSupplier.supplierId)!""}'>
                            	</div>
                            </tbody>
                        </table>
                    </section>
                </section>
                </#if>
				<#if cost.isContainHotel>
                <section class="pm-info-module hotel-info-module" style="width:1300px">
                    <div class="sub-title">酒店信息</div>
                    <div class="hotel-info view">
                    <#if cost.hotels??>
                        <div class="table-top-block clearfix">
                            <ul class="table-tab-switch hotel view">
                            	<#if cost.hotels??>
                            		<#list cost.hotels as hotel>
                            			<li <#if hotel_index == 0>class="current"</#if> onclick="swapHotelViewTab(this,'${hotel.id}')"><span>第${hotel.checkinDaysStr!''}晚</span></li>
                                	</#list>
                                </#if>
                            </ul>
                            <span class="oper-link">
                                 <a href="javascript:editHotel() ;">编辑</a>
                                <a href="javascript:deleteHotel() ;">删除</a>
                            </span>
                        </div>
                        <#list cost.hotels as hotel>
	                        <table class="hotel-info-table ${hotel.id}" <#if hotel_index != 0>style="display:none"</#if>>
	                            <colgroup>
	                                <col width="79"/>
	                                <col width="330"/>
	                                <col width="80"/>
	                                <col width="100"/>
	                                <col width="80"/>
	                                <col width="115"/>
	                                <col width="80"/>
	                                <col width="110"/>
	                            </colgroup>
                    				<tbody>
		                                <tr>
		                                    <th>酒店名称</th>
		                                    <td>${hotel.name!''}</td>
		                                    <th class="normal">星级</th>
		                                    <td>${hotel.rate!''}</td>
		                                    <th class="normal">目的地</th>
		                                    <td>${hotel.dest!''}</td>
		                                    <th class="normal">房型</th>
		                                    <td>${hotel.roomType!''}</td>
		                                </tr>
		                                <tr>
		                                    <th style="height:80px;">酒店特色</th>
		                                    <td colspan="7" style="height:80px;">${hotel.highLights!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>酒店备注</th>
		                                    <td colspan="7">${hotel.hotelRemark!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>内部备注</th>
		                                    <td colspan="7">${hotel.innerRemark!''}</td>
		                                </tr>
		                            </tbody>
                            	</#list>
	                        </table>
	                        </#if>
	                    </div>
                </section>
                <section class="pm-info-module price-info-module hotel" style="width:1300px">
                    <div class="sub-title">成本维护</div>
                    <section class="pack-price-block" style="width:1300px">
                    	<div class="radioContent priceOperType hotel">
                            <label class="radio active" name="1">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">批量操作</span>
                            </label>
                            <label class="radio" name="2">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">单日操作(点击日历选取日期)</span>
                            </label>
                        </div>
                        <div class="price-maint-block">
                        	<!--  批量修改  -->
                            <div class="batch-oper-block hotel">
	                            <div class="date-oper-line">
	                                <span>酒店成本</span>
	                                <input type="text" class="datepicker hasIcon startDate hotel" style="width:150px;" /> 至
	                                <input type="text" class="datepicker hasIcon endDate hotel" style="width:150px;"/>
	                                <span class="checkboxContent week-date hotel">
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>一
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>二
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>三
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>四
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>五
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>六
	                                    </label>
	                                    <label class="checkboxInfo">
	                                        <span class="checkboxIcon"></span>日
	                                    </label>
	                                </span>
	                                <span class="oper-price-btn">
	                                    <button class="commonButton blue-45c8dcButton hotel cost add batch">添加成本</button>
	                                    <button class="commonButton blue-45c8dcButton hotel cost remove batch">清除成本</button>
	                                </span>
	                            </div>
	                            <div class="price-line">
	                                <span class="room-price-text">房价(单人成本，等同单房差成本)</span>
	                                <#if cost?? && cost.tripNights?? && cost.tripNights gt 0>
		                                <#list 1..cost.tripNights as t>
										第${t}晚&nbsp;<input class='roomCost batch' type="text" style="width:88px;" />&nbsp;&nbsp;
										</#list>
									</#if>
	                            </div>
	                        </div>
	                        <!--  单日修改  -->
                            <div class="single-oper-block hotel" style="display:none;">
                            	<span>酒店成本</span>
                                <span class="room-price-text">房价(单人成本，等同单房差成本)</span>
                                <#if cost?? && cost.tripNights?? && cost.tripNights gt 0>
	                                <#list 1..cost.tripNights as t>
									第${t}晚&nbsp;<input class='roomCost single' type="text" style="width:88px;" />&nbsp;&nbsp;
									</#list>
								</#if>
                                <span class="oper-price-btn">
                                    <button class="commonButton blue-45c8dcButton hotel cost add single">添加成本</button>
                                    <button class="commonButton blue-45c8dcButton hotel cost remove single">清除成本</button>
                                </span>
                            </div>
                        </div>
                        <div class="double-price-calendar clearfix hotel" style="margin-left:60px;">
                            <div class="calendar-title">
                                <span class="prev"></span>
                                <span class="cal-month left-calendar-title"></span>
                                <span class="cal-month right-calendar-title"></span>
                                <span class="next"></span>
                            </div>
                            <div class="left-calendar"></div>
                            <div class="right-calendar"></div>
                        </div>
                    </section>
                </section>
                </#if>
            <section class="pm-info-module additional-costs">
                    	<#if cost.isContainShuttle>
	                    <div class="hotel-pickup addi-info-block">
	                        <div class="top clearfix">
	                            <div class="sub-title">接送机</div>
	                            <div class="oper-block shuttle set">
	                                <button class="commonButton blue-45c8dcButton shuttle set">设置</button>
	                            </div>
	                            <div class="oper-block shuttle save" style="display: none;">
	                                <button class="commonButton red-fe6869Button shuttle save">保存</button>
	                                <button class="commonButton gray-bbbButton shuttle cancel">取消</button>
	                            </div>
	                        </div>
	                        <div class="form-module">
	                            <div class="cospro">
	                                <span class="costs">
	                                    <span>成本</span>
	                                    <input type="text" class="shuttle cost" style="width:110px;" value="${(cost.shuttleSupplier.cost)!""}"> 元
	                                </span>
	                                <span class="provider">
	                                    <span>供应商</span>
			                            <div class="dropdown shuttle" style="width: 320px;top:1px;">
			                                <a href="javascript:void(0);" class="dropdownBtn" aria-expanded="true">
			                                    <span class="activeFonts">${(cost.shuttleSupplier.supplierName)!""}</span>
			                                    <span class="caret"></span>
			                                </a>
			                                <ul class="dropdown-menu">
			                                    <#list cost.allSuppliers as supplier>
													<li onclick=setDropDownMenu(this,'${supplier.supplierId}','${supplier.supplierNameTransfer}','#shuttleSupplierId');><a href="javascript:void(0);">${supplier.supplierName}</a></li>
												</#list>
			                                </ul>
			                                <input type="hidden" id='shuttleSupplierId' value='${(cost.shuttleSupplier.supplierId)!""}'>
			                            </div>
	                                </span>
	                            </div>
	                        </div>
	                    </div>
	                    </#if>
	                    <#if cost.isContainZenbook>
	                    <div class="travel-book addi-info-block">
	                        <div class="top clearfix">
	                            <div class="sub-title">真旅本子</div>
	                            <div class="oper-block zenbook set">
	                                <button class="commonButton blue-45c8dcButton zenbook set">设置</button>
	                            </div>
	                            <div class="oper-block zenbook save" style="display: none;">
	                                <button class="commonButton red-fe6869Button  zenbook save">保存</button>
	                                <button class="commonButton gray-bbbButton  zenbook cancel">取消</button>
	                            </div>
	                        </div>
	                        <div class="form-module">
	                            <div class="cospro">
	                                <span class="costs">
	                                    <span>成本</span>
	                                    <input type="text" class="zenbook cost" style="width:110px;" value="${(cost.zenbookSupplier.cost)!""}"> 元
	                                </span>
	                                <span class="provider">
	                                    <span>供应商</span>
			                            <div class="dropdown zenbook" style="width: 320px;top:1px;">
			                                <a href="javascript:void(0);" class="dropdownBtn" aria-expanded="true">
			                                    <span class="activeFonts">${(cost.zenbookSupplier.supplierName)!""}</span>
			                                    <span class="caret"></span>
			                                </a>
			                                <ul class="dropdown-menu">
			                                    <#list cost.allSuppliers as supplier>
													<li onclick=setDropDownMenu(this,'${supplier.supplierId}','${supplier.supplierNameTransfer}','#zenbookSupplierId');><a href="javascript:void(0);">${supplier.supplierName}</a></li>
												</#list>
			                                </ul>
			                                <input type="hidden" id='zenbookSupplierId' value='${(cost.zenbookSupplier.supplierId)!""}'>
			                            </div>
	                                </span>
	                            </div>
	                        </div>
	                    </div>
	                    </#if>
	                    <#if cost.isContainWifi>
	                    <div class="wifi-service addi-info-block">
	                        <div class="top clearfix">
	                            <div class="sub-title">WIFI</div>
	                            <div class="oper-block wifi set">
	                                <button class="commonButton blue-45c8dcButton wifi set">设置</button>
	                            </div>
	                            <div class="oper-block wifi save" style="display: none;">
	                                <button class="commonButton red-fe6869Button wifi save">保存</button>
	                                <button class="commonButton gray-bbbButton wifi cancel">取消</button>
	                            </div>
	                        </div>
	                        <div class="form-module">
	                            <div class="cospro">
	                                <span class="costs">
	                                    <span>成本</span>
	                                    <input type="text" class="wifi cost" style="width:110px;" value="${(cost.wifiSupplier.cost)!""}"> 元
	                                </span>
	                                <span class="provider">
	                                    <span>供应商</span>
			                            <div class="dropdown wifi" style="width: 320px;top:1px;">
			                                <a href="javascript:void(0);" class="dropdownBtn" aria-expanded="true">
			                                    <span class="activeFonts">${(cost.wifiSupplier.supplierName)!""}</span>
			                                    <span class="caret"></span>
			                                </a>
			                                <ul class="dropdown-menu">
			                                    <#list cost.allSuppliers as supplier>
													<li onclick=setDropDownMenu(this,'${supplier.supplierId}','${supplier.supplierNameTransfer}','#wifiSupplierId');><a href="javascript:void(0);">${supplier.supplierName}</a></li>
												</#list>
			                                </ul>
			                                <input type="hidden" id='wifiSupplierId' value='${(cost.wifiSupplier.supplierId)!""}'>
			                            </div>
	                                </span>
	                            </div>
	                        </div>
	                    </div>
	                    </#if>
	                    <#if cost.isContainOther>
	                    <div class="other-service addi-info-block">
	                        <div class="top clearfix">
	                            <div class="sub-title">其他</div>
	                            <div class="oper-block other set">
	                                <button class="commonButton blue-45c8dcButton other set">设置</button>
	                            </div>
	                            <div class="oper-block other save" style="display: none;">
	                                <button class="commonButton red-fe6869Button other save">保存</button>
	                                <button class="commonButton gray-bbbButton other cancel">取消</button>
	                            </div>
	                        </div>
	                        <div class="form-module">
	                            <div class="cospro">
	                                <span class="costs">
	                                    <span>成本</span>
	                                    <input type="text" class="other cost" style="width:110px;" value="${(cost.otherSupplier.cost)!""}"> 元
	                                </span>
	                                <span class="provider">
	                                    <span>供应商</span>
			                            <div class="dropdown other" style="width: 320px;top:1px;">
			                                <a href="javascript:void(0);" class="dropdownBtn" aria-expanded="true">
			                                    <span class="activeFonts">${(cost.otherSupplier.supplierName)!""}</span>
			                                    <span class="caret"></span>
			                                </a>
			                                <ul class="dropdown-menu">
			                                    <#list cost.allSuppliers as supplier>
													<li onclick=setDropDownMenu(this,'${supplier.supplierId}','${supplier.supplierNameTransfer}','#otherSupplierId');><a href="javascript:void(0);">${supplier.supplierName}</a></li>
												</#list>
			                                </ul>
			                                <input type="hidden" id='otherSupplierId' value='${(cost.otherSupplier.supplierId)!""}'>
			                            </div>
	                                </span>
	                            </div>
	                        </div>
	                    </div>
	                    </#if>
	                </section>
                </section>
            </section>
        </div>
    <!--  添加机票弹窗内容  -->

    <div class="modal hasHeaderBtn" id="add-flight-popup">
        <div class="modal-dialog" style="width: 1040px;height:687px;">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-oper">
                        <button class="commonButton red-fe6869Button flight save">保存</button>
                        <button class="commonButton gray-bbbButton flight cancel">取消</button>
                    </div>
                    <h4 class="modal-title">机票信息</h4>
                </div>
                <div class="modal-body" style="height:635px;overflow-y: auto;">
                    <div class="section-cont-wrapper">
                        <section class="flight-form-module">
                            <div class="flight-info edit">
                                <div class="table-top-block clearfix">
                                    <ul class="table-tab">
                                        <li class="">
                                            <span>${cost.from!''}出发</span>
                                        </li>
                                    </ul>
                                </div>
                                <section class="go-air-range-block">
                                    <div class="table-top-title">
                                        <span class="go-flight title-flag">
                                            <span class="go-flight-icon"></span>
                                            <span>去程</span>
                                        </span>
                                        <span class="add-oper add-transfer go">
                                            <span class="plus-icon"></span>
                                            <span>添加中转</span>
                                        </span>
                                    </div>
                                </section>
                                <section class="middle-air-range-container">
                                    <div class="long-split-line"></div>
                                    <section class="middle-air-range-block clearfix">
                                        <div class="table-top-title">
                                            <span class="middle-flight title-flag">
                                                <span class="middle-flight-icon"></span>
                                                <span class="flight-num">中间程</span>
                                            </span>
                                            <span class="add-oper add-middle-flight">
                                                <span class="plus-icon"></span>
                                                <span>添加航程</span>
                                            </span>
                                            <span class="add-oper add-transfer" style="display: none;">
                                                <span class="plus-icon"></span>
                                                <span>添加中转</span>
                                            </span>
                                        </div>
                                    </section>
                                </section>
                                <section class="back-air-range-block">
                                    <div class="long-split-line"></div>
                                    <div class="table-top-title">
                                        <span class="back-flight title-flag">
                                            <span class="back-flight-icon"></span>
                                            <span>返程</span>
                                        </span>
                                        <span class="add-oper add-back-flight">
                                            <span class="plus-icon"></span>
                                            <span>添加航程</span>
                                        </span>
                                        <span class="add-oper add-transfer" style="display: none;">
                                            <span class="plus-icon"></span>
                                            <span>添加中转</span>
                                        </span>
                                    </div>
                                </section>
                                <div class="long-split-line"></div>
                            </div>
                            <div class="remark-info">
                                <span class="title">航程备注</span>
                                <span class="cont">
                                    <textarea id="airRange-remark"></textarea>
                                </span>
                            </div>
                            <div class="remark-info">
                                <span class="title">内部备注</span>
                                <span class="cont">
                                    <textarea id="inner-remark"></textarea>
                                </span>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--  航程table template  -->
    <script type="text/html" id="flight_form_table_template">
    	<table class="flight-form-table">
            <colgroup>
                <col width="80"/>
                <col width="79"/>
                <col width="80"/>
                <col width="79"/>
                <col width="80"/>
                <col width="79"/>
                <col width="80"/>
                <col width="85"/>
                <col width="80"/>
                <col width="85"/>
                <col width="90"/>
                <col width="85"/>
                <col width="90"/>
                <col width="85"/>
            </colgroup>
            <tbody>
                <tr>
                    <th><span class="red-star">*</span>第几天</th>
                    <td><input type="text" class="offsetDays" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>航司名称</th>
                    <td><input type="text" class="airLine" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>航班号</th>
                    <td><input type="text" class="flightNo" style="width:100px;" data-cv="required"/></td>
                    <th>舱位</th>
                    <td><input type="text" class="cabin" style="width:80px;" /></td>
                    <th><span class="red-star">*</span>机型</th>
                    <td><input type="text" class="flightModel" style="width:80px;" /></td>
                    <th>经停信息</th>
                    <td colspan="3"><input type="text" class="stop" style="width:200px;" placeholder="经停:北京三水机场/停留时间:1小时"/></td>
                </tr>
                <tr>
                    <th><span class="red-star">*</span>起飞机场</th>
                    <td><input type="text" class="fromAirPort" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>起飞时间</th>
                    <td><input type="text" class="departureTime" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>到达机场</th>
                    <td><input type="text" class="toAirPort" style="width:100px;" data-cv="required"/></td>
                    <th><span class="red-star">*</span>到达时间</th>
                    <td colspan="3">
                        <input type="text" class="arrivalTime" style="width:100px;" data-cv="required"/>
                        <div class="dropdown" style="width: 67px;top:1px;">
                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="true">
                                <span class="activeFonts">+0日</span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li onclick=setDropDownMenu(this,0,'+0日','.addDays')><a href="javascript:void(0);">+0日</a></li>
                                <li onclick=setDropDownMenu(this,1,'+1日','.addDays')><a href="javascript:void(0);">+1日</a></li>
                                <li onclick=setDropDownMenu(this,2,'+2日','.addDays')><a href="javascript:void(0);">+2日</a></li>
                                <li onclick=setDropDownMenu(this,3,'+3日','.addDays')><a href="javascript:void(0);">+3日</a></li>
                            </ul>
                            <input type="hidden" class="addDays" value='0'>
                        </div>
                    </td>
                    <th><span class="red-star">*</span>出发城市</th>
                    <td><input type="text" class="fromCity" style="width:80px;" /></td>
                    <th><span class="red-star">*</span>到达城市</th>
                    <td><input type="text" class="toCity" style="width:80px;" /></td>
                </tr>
            </tbody>
        </table>
    </script>

    <!--  第一个中间程内容模板  -->
    <script type="text/html" id="first_middle_flight_template_front">
        <section class="clearfix">
    </script>

    <script type="text/html" id="first_middle_flight_template_back">
        <div class="del-oper del-middle-flight">
                <span class="del-icon"></span>
                <span>删除中间程信息</span>
            </div>
        </section>
    </script>

    <!--  normal中间程内容模板  -->
    <script type="text/html" id="normal_middle_flight_template_front">
        <section class="middle-air-range-block clearfix">
            <div class="long-split-line"></div>
            <div class="table-top-title">
                <span class="middle-flight title-flag">
                    <span class="middle-flight-icon"></span>
                    <span class="flight-num"></span>
                </span>
                <span class="add-oper add-middle-flight" style="display:none;">
                    <span class="plus-icon"></span>
                    <span>添加航程</span>
                </span>
                <span class="add-oper add-transfer">
                    <span class="plus-icon"></span>
                    <span>添加中转</span>
                </span>
            </div>
            <section class="clearfix">
    </script>

    <script type="text/html" id="normal_middle_flight_template_back">
        <div class="del-oper del-middle-flight">
                    <span class="del-icon"></span>
                    <span>删除中间程信息</span>
                </div>
            </section>
        </section>
    </script>

    <!--  第一个返程内容模板  -->
    <script type="text/html" id="first_back_flight_template_front">
        <section class="clearfix">
    </script>

    <script type="text/html" id="first_back_flight_template_back">
        <div class="del-oper del-back-flight">
            <span class="del-icon"></span>
            <span>删除返程信息</span>
        </div>
    </script>

    <!--  中转信息内容模板  -->
    <script type="text/html" id="transfer_flight_template_front">
        <section class="transfer-flight-block clearfix">
            <div class="short-split-line"></div>
    </script>

    <script type="text/html" id="transfer_flight_template_back">
        <div class="del-oper del-transfer">
                <span class="del-icon"></span>
                <span>删除中转信息</span>
            </div>
        </section>
    </script>
    
     <script type="text/html" id="all_suppliers">
     	<ul class="dropdown-menu">
        <#list cost.allSuppliers as supplier>
			<li onclick=setDropDownMenu(this,'${supplier.supplierId}','${supplier.supplierNameTransfer}','.hotelSupplierId');><a href="javascript:void(0);">${supplier.supplierName}</a></li>
		</#list>
		</ul>
    </script>
    
    <!--  添加酒店弹窗   酒店列表  -->
    <div class="modal hasHeaderBtn" id="hotel-list-popup" style="position: absolute;">
        <div class="modal-dialog" style="width: 1040px;height:687px;">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-oper">
                        <button class="commonButton gray-bbbButton hotellist cancel">取消</button>
                    </div>
                    <h4 class="modal-title">选择酒店</h4>
                </div>
                <div class="modal-body" style="height:635px;overflow-y: auto;">
                    <div class="section-cont-wrapper">
                        <section class="clearfix" id="searchField">
                            <div class="search-block">
                                <span>酒店名称</span>
                                <input id="hotelNameInputer" type="text" style="width:395px;" />
                                <button class="commonButton blue-45c8dcButton haveIconButton"><i class="searchIcon"></i>搜索</button>
                            	<input type="hidden" name="pageNo" value="1" />
				    			<input type="hidden" name="pageSize" value="10" />
                            </div>
                            <table class="commonTab hotel-list-table">
                                <colgroup>
                                    <col width="227">
                                    <col width="112">
                                    <col width="116">
                                    <col width="108">
                                    <col width="95">
                                    <col width="216">
                                    <col width="">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>酒店名称</th>
                                    <th>国家</th>
                                    <th>城市/景点</th>
                                    <th>酒店类型</th>
                                    <th>星级</th>
                                    <th>特色</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            <div id="pagination">
                    		</div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--  添加酒店弹窗   酒店表单  -->
    <div class="modal hasHeaderBtn" id="add-hotel-popup">
        <div class="modal-dialog" style="width: 1040px;height:687px;">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-oper">
                        <button class="commonButton red-fe6869Button hotel save">保存</button>
                        <button class="commonButton gray-bbbButton hotel cancel">取消</button>
                    </div>
                    <h4 class="modal-title">添加酒店</h4>
                </div>
                <div class="modal-body">
                    <div class="section-cont-wrapper">
                        <section class="hotel-form-module">
                            <div class="table-top-block clearfix">
                                <ul class="table-tab-switch hotel edit">
                                	<li onclick='hotelSearchListPage();'><span>+ 添加酒店</span></li>
                                </ul>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
    	$(function(){
			//  data替换为 hotel_data,则显示酒店房价日历
			geneDoublePriceCalendar(".double-price-calendar.hotel", strToJson($("#hoteldata").html()));
			geneDoublePriceCalendar(".double-price-calendar.flight", strToJson($("#flightdata").html()));
    	}) ;
    </script>
</@productMenu.productMenu>
</@html.htmlIndex>

