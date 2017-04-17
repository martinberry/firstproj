<#import "/common/weixin/htmlIndex.ftl" as html />
<@html.htmlIndex remoteJsFiles=["mstatic/js/iscroll/iscroll-probe.js"] localCssFiles=[]
                                  localJsFiles=["member/weixin/activity/couponList.js","common/password_verify.js","common/caculate.js","common/weixin/common_utils.js"] title="代金券购买">

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/myWallet.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/myCoupon.css">
    <title>预售大作战</title>
    <script type="text/javascript">
        // 设置页面根字大小
        var root = document.getElementsByTagName("html")[0],
            w = window.innerWidth >= 640 ? 640 : window.innerWidth;

        root.style.fontSize = (w / 320) * 20 + "px";
        root.style.minHeight = window.innerHeight + "px";
    </script>
</head>
<body>
    <div class="viewport" data-role="page">
        <div class="wrapper">
            <div class="forWardSaleImgBlock">
                <img src="${host}/mstatic/images/forwardSaleImg.jpg" class="">
            </div>
            <div class="productsDesc">
                <h3>限量抢购</h3>
                <p>1元即可团购300元邀请券</p>
                <p>预售礼遇大放送：仅需500元即可获得1000元邀请券</p>
            </div>
            <div class="couponBuyCon">

            <#if couponList??>
                <#list couponList as coupon>
                <div class="operProductsBlock clearfix" id="${coupon.couponId!}">
                    <div class="voucherImg voucherImg-${coupon.price/100!}-${coupon.amount/100!}"></div>
                    <input type="hidden" id="activityId" value="${coupon.activityId!}"/>
                    <div class="counter">
                        <#if (coupon.unit) == 1>
                        <span class="limitedOne">限购1份</span>
                        <span style=" visibility:hidden;" class="counter-item counter-num" name="couponItemId">1</span>
                        <#else>
                        <span class="counter-item counter-minus"></span>
                        <span class="counter-item counter-num" name="couponItemId">1</span>
                        <span class="counter-item counter-add"></span>
                        </#if>
                    </div>
                    <#if (coupon.status) == 'READY'>
                    <a class="orange_soonSale ui-link disabled" href="javascript:;" data-role="none" data-transition="slidefade">即将开售</a>
                    <#else>
                    <a class="orange_buy ui-link" href="javascript:;" data-role="none" data-transition="slidefade">抢 购</a>
                    </#if>
                </div>
                </#list>
            </#if>
            </div>
            <div class="buyerReading">
                <h4>购买须知</h4>
                <div>有效期：2015.12.12 至  2016.06.30</div>
                <div>
                    <p>使用规则：</p>
                    <p><i class="smallDot"></i>真旅行网站全场适用</p>
                    <p><i class="smallDot"></i>每笔消费仅限使用1张邀请券</p>
                    <p><i class="smallDot"></i>1元抵300元，满3000以上使用</p>
                    <p><i class="smallDot"></i>500元抵1000元，满5000以上使用</p>
                </div>
                <div>
                    <p>温馨提示：</p>
                    <p><i class="smallDot"></i>1元抵300元每个用户限购1份</p>
                    <p><i class="smallDot"></i>本邀请券不兑现，不找零</p>
                    <p><i class="smallDot"></i>可随时退款，过期退款</p>
                    <p><i class="smallDot"></i>退款流程：前往<a href="http://zhenlx.com" target="_blank" data-role="none">zhenlx.com</a>“我的代金券”发起退款申请</p>
                </div>
            </div>
            <div class="forWardSaleBottom">
                <p>服务热线：400-620-6266 转6(9:00-18:00)</p>
                <p>Copyright  2015 真旅行All Rights Reserved.</p>
                <p>沪ICP备08004120号-5</p>
            </div>
        </div>
    </div>

    <!-- 购买弹窗 -->
    <div class="submit-dlg" id="submit-dlg" data-role="popup" data-transition="pop" data-position-to="window" data-theme="a" data-overlay-theme="b">
        <div class="check-error-tip"></div>
        <div class="ui-grid-a">
            <div class="ui-block-a"><i class="person-icon"></i></div>
            <div class="ui-block-b">
                <input class="submit-dlg-val"  id="mobileInputer" type="text" placeholder="手机号" data-role="none"  maxLength="11" onblur="validateMobile();">
                <input type = "hidden" id="chooseCouponId" name ="chooseCouponId" value = "">
                <input type = "hidden" id="chooseNum"  name ="chooseNum" value = "">
                <input type = "hidden" id="chooseActivityId"  name ="chooseActivityId" value = "">
            </div>
        </div>
        <div class="verify-block clearfix">
            <i class="safe-icon fl"></i>
            <input class="input-txt" type="text" id="verifyCodeInputer" placeholder="验证码" data-role="none" style="width: 3rem;" maxLength="6" onblur="validateVerifyCode();">
            <a class="btn-vld" data-role="none" href="javascript:void(0);">获取验证码</a>
        </div>
        <div class="dlg-foot">
            <a class="submit-dlg-confirm" data-role="none" href="javascript:void(0);">确 认</a>
        </div>
    </div>

    <!-- 提示弹窗 -->
    <div class="ui-content" data-role="popup" id="alert-buy-msg-dialog" data-transition="none" data-history="false" data-position-to="window" data-theme="a" data-overlay-theme="b" data-dismissible="true">
        <p class="dlg-cnt tip-win-cnt" id="errHint"></p>
        <div class="dlg-foot tip-foot">
            <a class="submit-dlg-confirm" data-role="none" href="javascript:;" data-rel="back">好的</a>
        </div>
    </div>

<script type="text/javascript">
    $(function(){
        FastClick.attach(document.body);
    });
</script>
</body>
</@html.htmlIndex>
