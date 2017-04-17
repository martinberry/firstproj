<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-我的订单"
currentMenu="我的订单"
remoteJsFiles=[]
remoteCssFiles=["css/client/orderInfo.css"]
localJsFiles=[]
localCssFiles=[]>


    <div class="main-wrapper box-space" id="main-wrapper">
        <section class="orderList">
            <span class="pin"></span>
            <div class="top-border"></div>
            <div class="orderListBox">
                <div class="orderListBox-cnt">
                    <div class="orderListBox-head clearfix">
                        <span class="boxHead-title boxHead-title-color">所有订单</span><span class="boxHead-title
                         boxHead-title-border">待支付<i class="boxHead-title-num" id="pay-num">0</i></span><span
                            class="boxHead-title">待评价<i class="boxHead-title-num" id="judge-num">0</i></span>
                        <span class="orderListStatus pull-right"><span class="orderStatusTip"></span><i class="orderTooltip green-border-tooltip">订单状态</i></span>
                    </div>
                    <i class="left-semicircle left-semicircle-bg01"></i>
                    <i class="right-semicircle left-semicircle-bg01"></i>
                </div>
                <#list orderProducts! as orderProduct>
                <#assign order = (orderProduct.order)!/>
                <#assign product = (orderProduct.product)!/>
                <div class="orderListBox-cnt">
                    <div class="boxTwo-cnt">
                    <#if order.productNature ??>
                    <#if order.productNature=='VISA'>
                        <span class="orderListModel" onClick="window.location.href='${basepath}/visaorder/front/detail/${order.orderId!}'">订单号:${order.orderNo!} </span>
                       <span onClick="window.location.href='${basepath}/visaorder/front/detail/${order.orderId!}'">${product.productTitle!}</span>  <#if product.packageName ??>  --${product.packageName!}</#if>
                    <#elseif order.productNature=='PACKAGE'||order.productNature=='COMBINATION'>                     
                      <span class="orderListModel" onClick="window.location.href='${basepath}/order/front/detail/${order.orderId!}'">订单号:${order.orderNo!} </span>
                     <span onClick="window.location.href='${basepath}/order/front/detail/${order.orderId!}'">${product.productTitle!}</span>  <#if product.packageName ??>  --${product.packageName!}</#if>                   
                    <#else>
                    <span class="orderListModel" onClick="window.location.href='${basepath}/localorder/front/detail/${order.orderId!}'">订单号:${order.orderNo!} </span>                        
                      <span onClick="window.location.href='${basepath}/localorder/front/detail/${order.orderId!}'">${product.productTitle!}</span>  <#if product.packageName ??>  --${product.packageName!}</#if>
                    </#if> 
                    <#else>
                     <span class="orderListModel" onClick="window.location.href='${basepath}/order/front/detail/${order.orderId!}'">订单号:${order.orderNo!} </span>
                      <span onClick="window.location.href='${basepath}/order/front/detail/${order.orderId!}'">${product.productTitle!}</span>  <#if product.packageName ??>  --${product.packageName!}</#if>
                    </#if>
                   
                    <input type="hidden" id= "packageId" value="${product.packageId!}">                 
                    </div>
                    <i class="left-semicircle left-semicircle-bg03"></i>
                    <i class="right-semicircle left-semicircle-bg03"></i>
                </div>
                <div>
                    <div class="boxCnt-item clearfix">
                        <div class="pull-left">
                            <img class="boxCnt-item-img" alt="缩略图" src="${mediaserver}imageservice?mediaImageId=${product.imageId!}">
                        </div>
                        <div class="boxCnt-item-type pull-left">
                            <div class="boxCnt-item-txt">下单日期</div>
                            <div class="boxCnt-item-txt boxCnt-txt-color">${order.createDate!}</div>
                        </div>
                        <div class="boxCnt-item-type boxCnt-item-border pull-left">
                            <div class="boxCnt-item-txt">出游日期</div>
                            <div class="boxCnt-item-txt boxCnt-txt-color">${product.bookDate!}</div>
                        </div>
                        <div class="boxCnt-item-type boxCnt-item-border pull-left">
                            <div class="boxCnt-item-txt">订单金额</div>
                            <div class="boxCnt-item-txt boxCnt-txt-num">￥${order.totalPrice!}</div>
                        </div>
                        <div class="boxCnt-item-type boxCnt-item-border pull-left">
                            <div class="boxCnt-item-txt">应付金额</div>
                            <div class="boxCnt-item-txt boxCnt-txt-num">￥${order.payAmount!}
                            <#if order.discountTotalSub?? && order.discountTotalSub!=".00">
                            <div class="boxCnt-txt-off">已优惠 ￥${order.discountTotalSub}</div>
                            </#if>
                             <#if order.commonOrderPrice ?? && order.commonOrderStatus?? && order.commonOrderStatus=="UNPAY">
                            	 <div class="for-fill-money">待补款：${order.commonOrderPrice!}</div>
                             </#if>
                            </div>
                        </div>
                        <div class="boxCnt-item-type boxCnt-item-border pull-left">
                            <div class="boxCnt-item-txt">状态</div>
                            <div class="boxCnt-item-txt boxCnt-txt-color03">${order.frontState!}</div>

                        </div>
                        <div class="boxCnt-item-type boxCnt-item-border pull-left">
                            <#if order.nextStep?? && order.nextStep=="去支付">
                            <div class="boxCnt-item-txt view-order" onClick="toDetail(${order.orderId!},'${order.productNature!}');">查看</div>
                            <div class="boxCnt-txt-color">
                            <button type="button" onClick="toPay(${order.orderId!});" class="btn btn-look btn-pay pay ">去支付</button>
                            </div>
                            <#elseif order.nextStep?? && order.nextStep=="评价" && order.canComment==true>
                            <div class="boxCnt-item-txt view-order" onClick="toDetail(${order.orderId!},'${order.productNature!}');">查看</div>
                            <div class="boxCnt-txt-color">
                            <button type="button" onClick="window.location.href='${basepath}/order/comment/edit/${(order.orderId)!}'" class="btn btn-look btn-judge judge">评价</button>
                            </div>
                            <#elseif order.frontState?? && order.frontState=="已取消">
                            <div class="boxCnt-item-txt view-order" onClick="toDetail(${order.orderId!},'${order.productNature!}');">查看</div>
                            <div class="boxCnt-txt-color">
                            <button type="button" onClick="window.location.href='${basepath}/home'"  class="btn btn-look btn-pay">查看其他</button>
                            </div>
                            <#elseif order.commonOrderStatus?? && order.commonOrderStatus=="UNPAY">
                             <div class="boxCnt-item-txt view-order" onClick="toDetail(${order.orderId!},'${order.productNature!}');">查看</div>
                            <div class="boxCnt-txt-color">
                            	<button type="button" onClick="toPay('${order.commonOrderId!}');" class="btn btn-look btn-pay pay ">去补款</button>
                            </div>
                            <#else>
                            <div class="boxCnt-txt-color boxCnt-look">
                            <button type="button" onClick="toDetail(${order.orderId!},'${order.productNature!}');" class="btn btn-look btn-see">查看</button>
                            </div>
                            </#if>
                        </div>
                    </div>
                </div>
                </#list>
            </div>
        </section>
    </div>

<script type="text/javascript">
    $(function () {

        $("#pay-num").html($(".pay").size());
        $("#judge-num").html($(".judge").size());

        setWrapperHeight();

        $(".main-nav span").click(function () {
            var $ele = $(this);
            $(".box-relative-location").css({
                "top": $ele.position().top + "px"
            }).show();
        });

        $(".orderTooltip").attr("title", $("#orderStatus-tooltip").html());

        $(".green-border-tooltip").tooltip({
            html: true,
            placement: 'bottom',
            template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
        });

    });

    function setWrapperHeight() {
        if ($(".main-wrapper").length !== 0) {
            $(".main-wrapper").eq(0).css({
                "min-height": (document.documentElement.clientHeight - 142) + "px"
            });
        }
    }

    function toPay(orderId){
    	_paq.push(['trackEvent', 'orderlistpage', 'ztrselectpayorder']);
    	window.location.href=basepath+'/orderPay/selectPayType/'+orderId;
    }
    function toDetail(orderId,productNature){
        if(productNature=='VISA'){
         window.location.href=basepath+'/visaorder/front/detail/'+orderId;
        }else if(productNature=='PACKAGE'||productNature=='COMBINATION'||productNature==''){
          window.location.href=basepath+'/order/front/detail/'+orderId;
        }else{
          window.location.href=basepath+'/localorder/front/detail/'+orderId;
        }
    	
    }
</script>
<!--订单状态的提示框-->
<script type="text/html" id="orderStatus-tooltip">
    <table class="order-table">
        <tr>
            <td class="td-first-line boxHead-title-color" width="80">状态说明</td>
            <td class="text-right order-td-right td-first-line">
                <!-- <button type="button" class="close" aria-hidden="true">&times;</button> -->
            </td>
        </tr>
        <tr class="order-tr">
            <td class="boxHead-title-color">待支付</td>
            <td class="order-td-right">订单已提交，等待出行人支付</td>
        </tr>
        <tr class="order-tr">
            <td class="boxHead-title-color">支付成功</td>
            <td class="order-td-right">真旅行收到您的支付信息，状态即可更新为支付成功</td>
        </tr>
        <tr class="order-tr">
            <td class="boxHead-title-color">已确认</td>
            <td class="order-td-right">您支付成功后，我们工作人员将及时为您确认产品，一旦产品确认成功，订单状态更改为“已确认”</td>
        </tr>
        <tr class="order-tr">
            <td class="boxHead-title-color">出行中</td>
            <td class="order-td-right">开开心心旅行中！</td>
        </tr>
        <tr class="order-tr">
            <td class="boxHead-title-color">已完成</td>
            <td class="order-td-right">游玩归来当日24:00，订单状态更新为已完成</td>
        </tr>
        <tr class="order-tr">
            <td class="boxHead-title-color">已取消</td>
            <td class="order-td-right">提交订单后，半小时内不支付，订单自动取消</td>
        </tr>

    </table>
    
</script>
</@html.navHeader>