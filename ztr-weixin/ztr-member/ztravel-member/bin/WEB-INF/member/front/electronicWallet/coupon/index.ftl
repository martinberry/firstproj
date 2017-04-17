<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-代金券"
currentMenu="我的钱包"
remoteJsFiles=[]
remoteCssFiles=["/css/client/wallet.css"]
localJsFiles=["/member/front/electronicWallet/coupon/index.js","common/pagination.js","/member/front/common_utils.js"]
localCssFiles=[]>

		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="top:30px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="cont-block clearfix">
                        <span class="modelLine"></span>
						<aside class="leftMenuContent">
                            <ul>
                                <li class="active">
                                    <a href= "${basepath}/electronicWallet/coupon/index">代金券</a>
                                </li>
                                <li>
                                    <a href="${basepath}/electronicWallet/accountBalance/index">账户余额</a>
                                </li>
                            </ul>
						</aside>
                        <section class="rightContent">
                            <ul class="voucher-switch clearfix">
                                <li class="current">未使用</li>
                                <li>已使用</li>
                                <li>已失效</li>
                                <li class="voucher-last">已赠送</li>
                            </ul>
                             <div class="voucher-top-right">
                                <span class="voucher-instruction green-border-tooltip"><span class="orderStatusTip"></span>代金券使用说明</span>
                                <a class="voucher-exchange" href="javascript:;">代金券兑换</a>
                            </div>
                            <div class="voucherListContainer"></div>
      						<div id="searchField"></div>
							<input type="hidden" id="selfMobile" value=${selfMobile!}>
							 <input type="hidden" name="pageNo" value="1" />
							<input type="hidden" name="pageSize" value="10" />
                        </section>
					</div>
				</div>
			</div>


			<div class="modal fade" data-backdrop="static" data-keyboard="false" id="share-voucher">
		        <div class="modal-dialog" style="width: 510px;height:405px;">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		                    <h4 class="modal-title">分享代金券</h4>
		                </div>
		                <div class="modal-body">
		                    <div class="appendContainer"></div><!--此处追加代金券内容-->
		                    <div class="share-person-block">
		                        <span class="title">分享给：</span>
		                        <span class="share-person">
		                            <img  src=" " id="sharer_head_img" >
		                            <input type="text" style="width:245px;" id="sharer_phone" placeholder="请输入手机号" maxLength="11"/>
		                            <div class="verifyStyle" style="display:none;text-align:center;" id="phoneVerify" >
			                                <i class="commonIcon errorIcon"></i><span id='phone_errorHint' class="errorHint"><span>
			                            </div>
			                             <div class="share-notice hidden" id="shared-notice">代金券已至对方账户</div>
		                        </span>
		                        <input type="hidden" id="destMembeId">
		                        <input type="hidden" id="share_couponItemId" >
		                        <input type = "hidden" id ="shareAmount">
		                    </div>
		                </div>
		                <div class="modal-footer">
		                    <a href="javascript:void(0);" class="commonBtn blueBtn width170 waitShare" id="confirmShare">确认分享</a>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

  <div class="modal fade" data-backdrop="static" data-keyboard="false" id="exchange-voucher">
        <div class="modal-dialog" style="width: 600px;height:250px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">兑换代金券</h4>
                </div>
                <div class="modal-body">
                    <div class="input-wrapper">请输入6位代金券兑换码：<input type="text" style="width:300px;" id="exchangeCode"  maxLength="6" onblur="validateExchangeCode();"/></div>
                       <div class="verifyStyle" style="display:none;text-align:center;" >
                            <i class="commonIcon errorIcon"></i><span id='exchange_errorHint' class="errorHint"><span>
                        </div>
                </div>
                <div class="modal-footer exchange-voucher-footer">
                    <a href="javascript:void(0);" class="commonBtn grayBtn"  id="cancelExchange">算了，下次再说</a>
                    <a href="javascript:void(0);" class="commonBtn blueBtn"  id="confirmExchange">好了，去兑换</a>
                </div>
            </div>
        </div>
    </div>

	    <!-- 代金券退款弹窗 -->
	    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="voucher-refund">
	        <div class="modal-dialog" style="width: 600px;height:220px;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                    <h4 class="modal-title">代金券退款</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="modal-tip-wrap">本代金券申请退款后即不可再使用。您，尊的要退款么？</div>
	                </div>
	                <div class="modal-footer voucher-refund-footer">
	                    <a href="javascript:void(0);" class="commonBtn grayBtn" id="confirm-refund" style="width:140px;" data-dismiss="modal">嗯呢，狠心退！</a>
	                    <a href="javascript:void(0);" class="commonBtn blueBtn" data-dismiss="modal">算了，那么划算我怎么能退！</a>
	                </div>
                      <input type="hidden" id="refund-couponItemId" >
	            </div>
	        </div>
	    </div>

	    <!-- 退款成功提示 -->
	    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="voucher-refund-success">
	        <div class="modal-dialog" style="width: 600px;height:220px;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                    <h4 class="modal-title">代金券退款</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="modal-tip-wrap">退款申请已提交，退款金额将于一周时间内退到您指定账户。请注意查收。</div>
	                </div>
	                <div class="modal-footer exchange-voucher-footer">
	                    <a href="javascript:void(0);" class="commonBtn blueBtn" data-dismiss="modal" id="refund-success-ok">OK</a>
	                </div>
	            </div>
	        </div>
	    </div>

	       <div class="modal fade" data-backdrop="static" data-keyboard="false" id="voucher-refund-failed">
	        <div class="modal-dialog" style="width: 600px;height:220px;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                    <h4 class="modal-title">代金券退款</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="modal-tip-wrap">退款申请操作失败,请重新申请</div>
	                </div>
	                <div class="modal-footer exchange-voucher-footer">
	                    <a href="javascript:void(0);" class="commonBtn blueBtn" data-dismiss="modal" >好的</a>
	                </div>
	            </div>
	        </div>
	    </div>


	       <div class="modal fade" data-backdrop="static" data-keyboard="false" id="voucher-exchange-success">
	        <div class="modal-dialog" style="width: 600px;height:220px;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                    <h4 class="modal-title">兑换代金券</h4>
	                </div>
	                <div class="modal-body">
	                    <div class="modal-tip-wrap">兑换代金券成功</div>
	                </div>
	                <div class="modal-footer exchange-voucher-footer">
	                    <a href="javascript:void(0);" class="commonBtn blueBtn" data-dismiss="modal" id="exchange-success-ok">OK</a>
	                </div>
	            </div>
	        </div>
	    </div>

		<script type="text/html" id="voucherIns-tooltip">
        <table class="inst-table">
            <tr>
                <td colspan="2">使用说明：</td>
            </tr>
            <tr>
                <td>1、</td>
                <td>用户在购买产品时满足优惠券使用门槛，可在付款时选择使用代金券直接享受减免； </td>
            </tr>
            <tr>
                <td>2、</td>
                <td>每张代金券，用户仅可使用一次，每个订单仅可使用一张代金券；</td>
            </tr>
            <tr>
                <td>3、</td>
                <td>该代金券仅限在真旅行下单使用；</td>
            </tr>
            <tr>
                <td>4、</td>
                <td>代金券只能用于抵扣订单金额，不能用于兑换现金；</td>
            </tr>
            <tr>
                <td>5、</td>
                <td>代金券分享等效于赠送给他人；已分享的优惠券不能在购买产品时抵扣现金；</td>
            </tr>
            <tr>
                <td>6、</td>
                <td>已使用和已过期的代金券不能分享；</td>
            </tr>
            <tr>
                <td>7、</td>
                <td>活动最终解释权归真旅行所有。</td>
            </tr>
        </table>
    </script>
</@html.navHeader>
