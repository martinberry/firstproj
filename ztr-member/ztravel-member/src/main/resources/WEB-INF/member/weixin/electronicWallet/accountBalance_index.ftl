<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as head/>

<@html.htmlIndex remoteJsFiles=["mstatic/js/iscroll/iscroll-probe.js"] remoteCssFiles=["mstatic/css/myWallet.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/electronicwallet/accountBalance/list.js","common/caculate.js"] title="我的钱包">
      <!--pageNum,总页数初始化-->
	<input type="hidden" id="totalPage" name="totalPage" value=${totalPage!'0'}>
	<input type="hidden" id="pageNum" name="pageNum" value='0'>
<body>
	<div data-role="page">
	    <@head.headerBar title="我的钱包"></@head.headerBar>
		<div class="wrapper">
			<div class="myWalletTab">
				<div class="tab">
					<span class="accountBtn current">账户余额</span><span class="cashCouponBtn">代金券</span>
				</div>
				<input type="hidden" id="accountVo" value="${account!}">
				<div class="account">￥<em id="account"></em></div>
			</div>
			  <div class="accountCon">
				    <div class="makeMoney"  data-role="none">
					<a href="javascript:void(0);" class="HelpMakeMoney" data-role="none">好友帮赚钱</a><a  class="aboutMakeMoney" data-role="none" id="aboutEarnMoney">关于赚钱</a>
				</div>
				<div class="accountList" id="wrapper">
					<div id="scroller">
						<div id="pullDown" style="display:none;">
							<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
						</div>
						<ul id="thelist">

						</ul>
						<div id="pullUp">
							<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="payment-foot">
			<a href="javascript:void(0);" rel="external" data-transition="slidefade" id="convertBtn" data-role="none">余额兑换</a>
		</div>
	</div>
	<!--提示模板-->
	<div class="ui-content" data-role="popup" id="commonError" data-theme="a" data-history="false" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
    <p class="dlg-cnt" id="commonErrorCont"></p>
    <div class="dlg-foot">
        <a class="btn-confirm" data-role="none" href="javascript:void(0);" >好的</a>
    </div>
    </div>
</body>
</@html.htmlIndex>