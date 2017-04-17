<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "product_menu.ftl" as productMenu/>
<@html.htmlIndex jsFiles=["product/back/price_calendar.js","product/back/common.js","js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js"]
	cssFiles=["css/bootstrap-datepicker.min.css","css/maintain/productManagement.css","css/maintain/priceCalendar.css"]
	curModule="产品管理"
	title="价格日历">
	<@productMenu.productMenu curProductModule="priceCalendar">
    <div class="main-container changeMainContent">
    	<span id="data" name="data" style="display:none ;">${data!''}</span>
    	<input type="hidden" id="id" name="id" value="${id!''}"/>
    	<input type="hidden" id="saveType" name="saveType" value=""/>
    	<input type="hidden" id="selectedDay" name="selectedDay" value="${now!''}"/>
    	<input type="hidden" id="mode" name="mode" value="${mode!''}"/>
		<section class="whiteBg">
            <div class="price-calendar-container clearfix">
                <div class="left-cal-block">
                    <div class="price-calendar">
                        <div class="calendar-operation-bar clearfix">
                            <div class="cal-year-title">
                                <span class="change-calendar-bigIcon-left change-calendar prev-year"></span>
                                <span class="cal-year-text"><em class="num current">2015</em>年</span>
                                <span class="change-calendar-bigIcon-right change-calendar next-year"></span>
                            </div>
                            <div class="cal-month-title">
                                <span class="change-calendar-bigIcon-left change-calendar prev-month"></span>
                                <span class="cal-month-text"><em class="num">1</em>月</span>
                                <span class="change-calendar-bigIcon-right change-calendar next-month"></span>
                            </div>
                            <button class="commonButton blue-45c8dcButton batch-set-btn">批量设置</button>
                        </div>
                    </div>
                </div>
                <div class="right-cont-block">
                    <div class="default-tip">
                        <i class="left-arrow"></i>
                        <span>点击日历<br/>设定价格</span>
                    </div>
                    <div class="set-price-container" style="display:none;">
                         <div class="show-date"></div>
                        <div class="date-range" style="display:none;">
                            <div class="input-date-range">
                                <input type="text" class="datepicker startDate hasIcon" style="width:130px;" data-cv="required" data-ct="开始时间"/> 至 <input type="text" class="datepicker endDate hasIcon" style="width:130px;" data-cv="required" data-ct="结束时间"/>
                            </div>
                            <div class="checkboxContent week-days">
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>一
                                </label>
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>二
                                </label>
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>三
                                </label>
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>四
                                </label>
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>五
                                </label>
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>六
                                </label>
                                <label class="checkboxInfo active">
                                    <span class="checkboxIcon"></span>日
                                </label>
                            </div>
                        </div>
                        <div class="sub-title teamnum">团号</div>
                        <table>
                            <colgroup>
                                <col width="40">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>团号</th>
                                    <td id="teamNum"></td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="sub-title flight-cost">机票成本</div>
                        <table>
                            <colgroup>
                                <col width="40"/>
                                <col width="70"/>
                                <col width="40"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>成人</th>
                                    <td id="flightAdultCost"></td>
                                    <th>儿童</th>
                                    <td id="flightChildCost"></td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="sub-title hotel-cost">酒店成本</div>
                        <table>
                            <colgroup>
                                <col width="40"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>房价</th>
                                    <td id="hotelRoomCost"></td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="sub-title package-cost">打包成本</div>
                        <table>
                            <colgroup>
                                <col width="40"/>
                                <col width="70"/>
                                <col width="40"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>成人</th>
                                    <td id="packageAdultCost"></td>
                                    <th>儿童</th>
                                    <td id="packageChildCost"></td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="sub-title addition-cost">附加成本</div>
                        <table>
                            <colgroup>
                                <col width="60"/>
                                <col width="85"/>
                                <col width="70"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th id="shuttleCostTh">接送机</th>
                                    <td id="shuttleCost"></td>
                                    <th id="zenbookCostTh">真旅本子</th>
                                    <td id="zenbookCost"></td>                                        
                                </tr>
                                <tr>
                                    <th id="wifiCostTh">WIFI</th>
                                    <td id="wifiCost"></td>
                                    <th id="otherCostTh">其他</th>
                                    <td id="otherCost"></td>                                 
                                </tr>
                            </tbody>
                        </table>
                        
                        <div class="sub-title">售价与库存</div> 
                        <div class="checkboxContent" style="display:none;">
                            <label id="batchUpdPrice" class="checkboxInfo active">
                                <span class="checkboxIcon"></span>是否批量修改售价与库存
                            </label>
                        </div>   
                        <div class="pro-type-radio">
                        	<span class="pro-type-title">类型</span>
                        	<label class="radio active">
                        		<span class="radioIcon"></span>
                        		<span class="labelFonts" id="saleUnitInputer">单人份</span>
                        	</label>
                        </div>
                        <div class="menu-block">
                        	<table class="stock-table">
                                <colgroup>
                                    <col width="65"/>
                                    <col width="110"/>
                                    <col width=""/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <td><em class="red-star">*</em>成人</td>
                                        <td><input id="adultPriceInputer" type="text" style="width:100px;"/></td>
                                        <td>
                                            <div class="checkboxContent">
                                                <label id="adultTaxChecker" class="checkboxInfo active">
                                                    <span class="checkboxIcon"></span>含税
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="checkboxContent">
                                                <label id="childChecker" class="checkboxInfo active">
                                                    <span class="checkboxIcon"></span>儿童
                                                </label>
                                            </div>
                                        </td>
                                        <td><input id="childPriceInputer" type="text" style="width:100px;"/></td>
                                        <td>
                                            <div class="checkboxContent">
                                                <label id="childTaxChecker" class="checkboxInfo active">
                                                    <span class="checkboxIcon"></span>含税
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><em class="red-star">*</em>单房差</td>
                                        <td><input id="singleRoomPriceInputer" type="text" style="width:100px;" data-cv="required" data-ct="单房差"/></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td><em class="red-star">*</em>可用库存</td>
                                        <td colspan="2">
                                            <input id="stockInputer" type="text" style="width:100px;" data-cv="required" data-ct="可用库存"/>人
                                            <span style="margin-left:10px;">已用<em id="usedStockInputer">0</em>人</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><em class="red-star">*</em>市场价</td>
                                        <td><input id="marketPriceInputer" type="text" style="width:100px;" data-cv="required" data-ct="市场价"/></td>
                                        <td>
                                            <span class="gray-font">仅展示用</span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="sub-title">套餐设置</div>
                        <div class="stock-block">
	                        <div class="stock-num clearfix">
	                            <button class="commonButton blue-45c8dcButton addPriceType">新增套餐类型</button>
	                        </div>
	                        <div class="stock-cont">
                            </div>
                        </div>
                        <div class="sub-title">预订条件</div>
                        <div class="order-condition">
                            <span><em class="red-star">*</em>提前</span>
                            <input id="inAdvanceDaysInputer" type="text" style="width:55px;" data-cv="required" data-ct="时间"/>天
                            <input id="inAdvanceHoursInputer" type="text" style="width:55px;" data-cv="required" />点前预订
                        </div>
                        <div class="oper-btn">
                            <button class="commonButton orange-f79767Btn save">保 存</button>
                            <button class="commonButton gray-bbbButton cancel">取 消</button>
                        </div>
                        <div class="oper-btn">
                            <button class="commonButton blue-45c8dcButton closeBtn">关 闭</button>
                            <button class="commonButton red-fe6869Button deleteBtn">删 除</button>
                            <button class="commonButton gray-bbbButton canOrderSet">设为未开放</button>
                        </div>

                    </div>
                </div>
            </div>
        </section>
    </div>
    
    <!-- 新增价格类型弹窗 -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="priceTypePopup">
        <div class="modal-dialog" style="width:630px;height:420px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">新增套餐类型</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="prodType-table">
                            <colgroup>
                                <col width="95"/>
                                <col width="110"/>
                                <col width="95"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>套餐名称：</th>
                                    <td colspan="3">
                                        <input type="text" id="pkgName" name="" value="" placeholder="套餐名称" style="width: 400px;">
                                        <input type="hidden" id='pkgId'>
                                    </td>
                                </tr>
                                <tr>
                                    <th style="vertical-align: top;">套餐介绍：</th>
                                    <td colspan="3">
                                        <textarea id='pkgDesc' placeholder="套餐介绍" style="height:80px;width:400px;"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <th>成人：</th>
                                    <td>
                                        <input type="text" id="pkgAdultNum" name="" value="2" placeholder="" style="width:100px;">
                                    </td>
                                    <th>儿童：</th>
                                    <td>
                                        <input type="text" id="pkgChildrenNum" name="" value="0" placeholder="" style="width:100px;">
                                    </td>
                                </tr>
                                <tr>
                                    <th>套餐售价：</th>
                                    <td colspan="3">
                                        <input type="text" id="pkgPrice" style="width:100px;" data-cv="required" data-ct="市场价" data-cp="top"/>
                                        <span class="checkboxContent">       
                                            <label class="checkboxInfo active" id="pkgHasTax"><span class="checkboxIcon"></span>含税</label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>人均单价：</th>
                                    <td colspan="3" id="pkgPerPrice"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton blue-45c8dcButton cancelPkg">取消</button>
                    <button type="button" class="commonButton red-fe6869Button savePkg">新增</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="tipWin">
        <div class="modal-dialog" style="width:430px;height:220px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="prodType-table">
                            <colgroup>
                                <col width="95"/>
                                <col width="110"/>
                                <col width="95"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td colspan="3" id="tipMessage">
                                        
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton blue-45c8dcButton ok" data-dismiss="modal">OK</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="pkgDoubleCheck">
        <div class="modal-dialog" style="width:430px;height:220px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">二次确认</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="prodType-table">
                            <colgroup>
                                <col width="95"/>
                                <col width="110"/>
                                <col width="95"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td colspan="3">
                                    	确认取消添加套餐操作？
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">否</button>
                    <button type="button" class="commonButton red-fe6869Button" onclick='javascript:closePkgEditWin() ;'>是</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="pkgDeleteConfirm">
        <div class="modal-dialog" style="width:430px;height:220px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">二次确认</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="prodType-table">
                            <colgroup>
                                <col width="95"/>
                                <col width="110"/>
                                <col width="95"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td colspan="3">
                                    	确定删除此套餐信息？
                                    	<input type='hidden' id='currentDelPkgId'/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                    <button type="button" class="commonButton red-fe6869Button" onclick='javascript:deletePkgConfirmed() ;'>确认</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="saveConfirm">
        <div class="modal-dialog" style="width:430px;height:220px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">二次确认</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="prodType-table">
                            <colgroup>
                                <col width="95"/>
                                <col width="110"/>
                                <col width="95"/>
                                <col width=""/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td colspan="3">
                                    	是否确认修改？
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                    <button type="button" class="commonButton red-fe6869Button" onclick='javascript:saveConfirmed() ;'>确认</button>
                </div>
            </div>
        </div>
    </div>
    <script>
    	$(function(){
		    //  调用方法
		    //  param1: 包裹日历的外层容器元素;  param2: 填充日历所需要数据
		    //  以下独立出的 funntion 可自行封装在js文件中，引入js文件
		    genePriceCalendar(".price-calendar", strToJson($("#data").html()));
		});
    </script>
</@productMenu.productMenu>
</@html.htmlIndex>

