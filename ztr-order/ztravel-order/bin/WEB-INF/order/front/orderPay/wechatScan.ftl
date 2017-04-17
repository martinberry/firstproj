 <#import "/common/front/htmlIndex.ftl" as html/>
 <@html.htmlIndex
 	title ="真旅行-微信扫码"
  	jsFiles=["order/front/orderPay/wechatScan.js","common/jquery.qrcode.min.js"]
>
<input type="hidden" id="codeUrl" value="${codeUrl!}"></input>
<input type="hidden" id="orderNo" value="${orderNo!}"></input>
<input type="hidden" id="payAmount" value="${payAmount!}"></input>
<input type="hidden" id="productType" value="${productType!}"></input>
<input type="hidden" id="countDown" value= ${countDown!}>
  <div class="main-wrapper" id="main-wrapper">
        <div class="main-box" id="main-box" style="width:1130px;">
            <div class="top-border"><span class="clip"></span></div>
            <div class="payBox-panel clearfix">
                <div class="qrCodePay" >
                    需支付<span class="orangeFonts">${payAmount!}</span>元，请开启微信扫描下面的二维码支付费用。
                    <div class="qrCode" id="qrCode" style="margin-top:56px;">
                    	<div style="display:inline-block;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@html.htmlIndex>

