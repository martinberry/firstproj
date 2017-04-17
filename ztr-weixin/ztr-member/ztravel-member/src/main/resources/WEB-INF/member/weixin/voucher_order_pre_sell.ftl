
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no, email=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm/jquery.mobile-1.4.5.min.css">
	<link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm.reset.css">
	<link rel="stylesheet" type="text/css" href="${host}/mstatic/css/common.css">
	<link rel="stylesheet" type="text/css" href="${host}/mstatic/css/myWallet.css">
	<link rel="stylesheet" type="text/css" href="${host}/mstatic/css/myCoupon.css">
	<script type="text/javascript" src="${host}/mstatic/js/base/jquery.min.js"></script>
	<script type="text/javascript" src="${host}/mstatic/js/jqm/jquery.mobile-1.4.5.min.js"></script>
	<script type="text/javascript" src="${host}/mstatic/js/iscroll/iscroll.js"></script>
	<script type="text/javascript" src="${host}/mstatic/js/iscroll/iscroll-probe.js"></script>
	<script type="text/javascript" src="${host}/mstatic/js/base/fastclick.js"></script>
    <script type="text/javascript" src="${host}/mstatic/js/common.js"></script>
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
                <div class="operProductsBlock clearfix">
                    <div class="voucherImg voucherImg-1-300"></div>
                    <div class="counter">
                        <span class="limitedOne">限购1份</span>
                    </div>
                    <a class="orange_soonSale ui-link disabled" href="javascript:;" data-role="none" data-transition="slidefade">即将开售</a>
                </div>
                <div class="operProductsBlock clearfix">
                    <div class="voucherImg voucherImg-500-1000"></div>
                    <div class="counter">
                        <span class="counter-item counter-minus"></span>
                        <span class="counter-item counter-num">1</span>
                        <span class="counter-item counter-add"></span>
                    </div>
                    <a class="orange_soonSale ui-link disabled" href="javascript:;" data-role="none" data-transition="slidefade">即将开售</a>
                </div>
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
        <div class="ui-grid-a">
            <div class="ui-block-a"><i class="person-icon"></i></div>
            <div class="ui-block-b"><input class="submit-dlg-val" type="text" placeholder="手机号" data-role="none"></div>
        </div>
        <div class="ui-grid-a hidden">
            <div class="ui-block-a"><i class="lock-icon"></i></div>
            <div class="ui-block-b"><input class="submit-dlg-val" type="password" placeholder="密码" data-role="none"></div>
        </div>
        <div class="verify-block clearfix">
            <i class="safe-icon fl"></i>
            <input class="input-txt" type="text" placeholder="验证码" data-role="none" style="width: 3rem;">
            <a class="btn-vld" data-role="none" href="javascript:void(0);">获取短信验证码</a>
        </div>
        <div class="dlg-foot">
            <a class="submit-dlg-confirm" data-role="none" href="javascript:void(0);">确 认</a>
        </div>
    </div>

<script type="text/javascript">
	$(function(){
		FastClick.attach(document.body);
        //用户操作
        $(".menu-pop").click(function(){
            var btn = $(this).next().find(".menu-list-container");
            if(btn.is(":visible")){
                btn.slideUp("fast");
            }
            $(this).find(".menu-pop-container").toggle();
            if($(".menu-pop-container").is(":visible")){
                $(document).click(function(){
                    $(".menu-pop-container").fadeOut("fast");
                });
            }
            event.stopPropagation();
        });
        //产品列表
        $(".menu-btn").click(function(event){
            var pop = $(this).prev().find(".menu-pop-container");
            if(pop.is(":visible")){
                pop.slideUp("fast");
            }
            $(this).find(".menu-list-container").toggle();
            if($(".menu-list-container").is(":visible")){
                $(document).click(function(){
                    $(".menu-list-container").fadeOut("fast");
                });
            }
            event.stopPropagation();
        });
        //文本框数量加减
        var least = 0;
        var most = 0;
        $(".counter > .counter-minus").click(function(){
            var minus_num = parseInt($(this).next().text());
            minus_num--;
            if(minus_num <= least){
                $(this).addClass("counter-disable");
                $(this).next().text(least);
                return false;
            }else{
                if($(this).hasClass("counter-disable")){
                    $(this).removeClass("counter-disable");
                }
                var obj = $(this).parent().find(".counter-add");
                if(obj.hasClass("counter-disable")){
                    obj.removeClass("counter-disable");
                }
            }
            $(this).next().text(minus_num);
        });
        $(".counter > .counter-add").click(function(){
            var add_num = parseInt($(this).prev().text());
            add_num++;
            if(add_num > least){
                var obj = $(this).parent().find(".counter-minus");
                if(obj.hasClass("counter-disable")){
                    obj.removeClass("counter-disable");
                }
            }
            $(this).prev().text(add_num);
        });
        //购买
        $(".orange_buy").click(function(){
            $("#submit-dlg").popup("open");
        });
	});
</script>
</body>
</html>