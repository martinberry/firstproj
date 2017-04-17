<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex remoteJsFiles=["mstatic/js/common.js"] remoteCssFiles=["mstatic/css/myWallet.css","mstatic/css/myCoupon.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/electronicwallet/coupon/coupon.js"] title="我的代金券">

	<div data-role="page">
 	<@head.headerBar title="我的代金券"></@head.headerBar>
		<div class="wrapper" style="margin-bottom: 1.4rem;">
			<div class="myWalletTab">
				<div class="tab">
					<span class="accountBtn">账户余额</span><span class="cashCouponBtn current">代金券</span>
				</div>

				<ul class="voucher-switch clearfix">
					<li class="current">未使用(<em id="availableCount"></em>)</li>
					<li class="nopadding">已使用(<em  id="usedCount"></em>)</li>
					<li class="single-border">已失效(<em id="expiredCount"></em>)</li>
					<li>已赠送(<em id="donateCount"></em>)</li>
				</ul>

			</div>
			<input type="hidden" id="selfMobile" value=${selfMobile!}>
			<input type="hidden" id="currentMemberId" value=${memberId!}>
			<div class="voucherCon">
				<div class="voucherContainer" id="theDataList">


			</div>



				<div class="ui-content" data-role="popup" data-history="false" id="alert-dialog" data-overlay-theme="b">
					<span class="dlg-title">分享代金券</span>
					<div class="dlg-cont">
					    <input type="hidden" id="couponItemId" >
					    <input type="hidden" id="destMemberId">
					    <input type="hidden" id="shareAmount">
						<input type="tel" id="sharer_phone" placeholder="请输入对方手机号" data-role="none">
						<#--<span id="sharedTip" class="dlg-cont">代金券已至对方账户</span>-->
						<span class="dlg-notice hidden"  id="shared-tip" >代金券已至对方账户</span>
					</div>
					<div class="dlg-foot">
						<a class="btn-com btn-share" id="btn-share" data-role="none" href="javascript:void(0);" >分 享</a>
						<a class="btn-com btn-cancel" id="btn-cancel" data-role="none" href="javascript:void(0);" >取 消</a>
						<a class="btn-com btn-notice hiddenBtn" id="btn-know" data-role="none" href="javascript:void(0);" >知道了</a>
					</div>
				</div>

				<div class="ui-content" data-role="popup" id="alert-errorhint-dialog" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
				    <p class="dlg-cnt" id="errorHintMsg"></p>
				    <div class="dlg-foot">
				        <a class="btn-confirm" data-role="none" href="javascript:void(0);" >好的</a>
				    </div>
			   </div>

			   	<div class="ui-content" data-role="popup" id="alert-refundSuccess-dialog" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
				    <p class="dlg-cnt" id="errorHintMsg">退款申请已提交，退款金额将于一周时间内退到您指定账户。请注意查收。</p>
				    <div class="dlg-foot">
				        <a class="btn-confirm" data-role="none" href="javascript:void(0);" id="refundSuccessOk" >好的</a>
				    </div>
			   </div>

			    <!--点击退款弹窗-->
	            <div class="ui-content confirm-ui-content" data-role="popup" id="alert-dialog-refund"  data-history="false" data-theme="a" data-overlay-theme="b" data-dismissible="false">
	                <span class="dlg-title">代金券退款</span>
	                <div class="dlg-cont">
	                    本代金券申请退款后即不可再使用。您，尊的要退款么？
	                </div>
	                <div class="dlg-foot">
	                	<input type="hidden" id="refund-couponItemId">
	                    <a class="btn-com btn-share" data-role="none" href="javascript:void(0);"  id="confirm-refund-btn">确 认</a>
	                    <a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);" data-rel="back" id="cancel-refund-btn">取 消</a>
	                    <a class="btn-com btn-notice hiddenBtn" data-role="none" href="javascript:void(0);" data-rel="back" id="refund-ok">知道了</a>
	                </div>
	            </div>

              <!--点击代金券兑换弹窗-->
		        <div class="ui-content confirm-ui-content" data-role="popup" id="alert-dialog-exchange" data-history="false" data-theme="a" data-overlay-theme="b" data-dismissible="false" >
		            <span class="dlg-title">代金券兑换</span>
		            <div class="dlg-cont">
		                <input type="text" placeholder="请输入6位代金券兑换码" data-role="none" id="exchangeCode"  onblur="validateExchangeCode();" maxlength="6">
	                	<span class="dlg-notice hidden"  id="exchanged-tip" >兑换成功</span>
		            </div>
		            <div class="dlg-foot">
		                <a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);"  id="cancelExchangeBtn">取 消</a>
		                <a class="btn-com btn-share" data-role="none" href="javascript:void(0);"  id="confirmExchangeBtn">兑 换</a>
	                	<a class="btn-com btn-notice hiddenBtn" id="btn-exchanged-know" data-role="none" href="javascript:void(0);" >知道了</a>
		            </div>
		        </div>
			</div>

		</div>
		 <div class="payment-foot">
	            <a href="#alert-dialog-exchange" data-rel="popup" data-transition="pop" data-position-to="window" id="foot-exchangeCodeBtn">代金券兑换</a>
	         </div>
	</div>
	</div>




</@html.htmlIndex>