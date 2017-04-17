<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex
			title="余额兑换"
		  	remoteCssFiles=["mstatic/css/myCoupon.css",
		  					"mstatic/css/myWallet.css"]
		  	localJsFiles=[
		  					"member/weixin/electronicwallet/accountBalance/convert.js",
		  					"member/weixin/electronicwallet/accountBalance/convertInit.js"
		  				 ]
		    remoteJsFiles=[]>
	<div data-role="page">
	<@head.headerBar title="余额兑换"></@head.headerBar>

	    <div class="convert-tip clearfix">
	        <span class="convert-tip-til">您当前账户余额为：</span><span class="convert-tip-val" id="amountLeft" data-val="${amount!}">${amount?string("0.00")}元</span>
	    </div>
	    <div class="convert-wrap">
	        <div class="convert-til">本期活动为兑换电话充值卡,类型如下:</div>
	        <div class="convert-cnt clearfix">
	            <ul class="clearfix">
	            	<#if amount?? && amount gte 10>
		                <li value="10">10元话费</li>
	                </#if>
	                <#if amount?? && amount gte 30>
		                <li value="30">30元话费</li>
		            </#if>
		            <#if amount?? && amount gte 50>
		                <li value="50">50元话费</li>
		            </#if>
		            <#if amount?? && amount gte 100>
		                <li value="100">100元话费</li>
		            </#if>
	            </ul>
	            <div class="convert-input">
	                <input type="text" placeholder="请输入手机号" data-role="none" id="mobile" value="${mobile!''}">
	            </div>
	            <div class="convert-notice clearfix">
	                <span class="notice-icon"></span>
	                <div class="notice-val">提交后手机号不可更改，请仔细核对，充值将在24小时内完成</div>
	            </div>
	            <div class="convert-link">
	                <a href="javascript:void(0);" id="confirmConvert">确认兑换</a>
	            </div>
	        </div>
	    </div>
	</div>

	<!--提示模板-->
	<div class="ui-content" data-role="popup" id="commonError" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
        <p class="dlg-cnt" id="commonErrorCont"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" >好的</a>
        </div>
    </div>
</@html.htmlIndex>

