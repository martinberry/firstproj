<#macro priceTab>
	<script type="text/javascript">
		var productId = "${(product.id)!}";
		var productPid = "${(product.pid)!}";
		var pName = "${(product.pName)!}";
		var de_SelectedDay = "${(selectedDay)!}";
		var de_packageId = "${(packageId)!}";
	</script>
	<div class="prd-order">
		<input id="initMarketPrice" type="hidden"/>
		<input id="initAdultPrice" type="hidden"/>
	    <div class="ui-grid-b">
	    	<div class="popup-toggle">
		        <div class="ui-block-a">
		            <p>出行日期：<span class="prd-order-span outDate">请选择出行时间</span></p>
		            <p>出行套餐：<span class="setMealVal">请选择出行套餐</span></p>
		        </div>
		        <div class="ui-block-b">
		            <a href="javascript:void(0);" class="order-btn-link ui-link"><span class="arr-up"></span></a>
		        </div>
	        </div>
	        <div class="ui-block-c">
	        	<a class="order-link ui-link bookBtn" href="javascript:void(0);">选择出行日期</a>
	        	<a class="wantgo-link ui-link" style="display:none;" data-rel="popup" href="javascript:void(0);">想去</a>
	        </div>
	    </div>
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
	<!--底部弹窗-->
    <div class="menu-dlg" id="popupMenu" data-role="popup" data-transition="slideup" data-history="false" data-position-to="window" data-theme="a" data-overlay-theme="b" data-corners="false">
        <div class="bottomWindowModule">
            <div class="windowContent current">
                    <div class="moduleTitle">①&nbsp;请选择出行日期</div>
                    <div class="changeModule">
                        <div class="menu-list changemenu">
                            <span class="left-month-btn disable"></span>
                            <div class="monthtab">
                                <ul class="clearfix"  id="tab_month">
                                </ul>
                            </div>
                            <span class="right-month-btn"></span>
                        </div>
                        <div id="menu-row-box">
                        </div>
                    </div>
            </div>
            <div class="windowContent">
                    <div class="moduleTitle">②&nbsp;请选择出行套餐</div>
                    <ul class="setMeal02List changeModule">
                    </ul>
            </div>
        </div>
        <div class="menu-order">
            <div class="ui-grid-b">
                <div class="ui-block-a">
                	<input type="hidden" id="selectDay" />
	                <p>出行日期：<span class="prd-order-span outDate">请选择出行时间</span></p>
	                <input type="hidden" id="pkgId"/>
                    <p>出行套餐：<span class="setMealVal">请选择出行套餐</span></p>
                </div>
                <div class="ui-block-b arr-click">
                    <a class="order-btn-link" href="javascript:void(0);" data-rel="back"><span class="arr-down"></span></a>
                </div>
                <div class="ui-block-c">
                    <a class="order-link ui-link bookBtn" href="javascript:void(0);">选择出行日期</a>
	            	<a class="wantgo-link ui-link" style="display:none;" data-rel="popup" href="javascript:void(0);" aria-haspopup="true" aria-owns="wantgoWindow" aria-expanded="false">想去</a>
                </div>
            </div>
        </div>
    </div>
</#macro>