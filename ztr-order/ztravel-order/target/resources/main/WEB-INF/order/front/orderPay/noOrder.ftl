 <#import "/common/front/htmlIndex.ftl" as html/>
 <@html.htmlIndex
 	title ="真旅行-没有订单"
  	jsFiles=[]
>
 <div class="main-wrapper" id="main-wrapper">
            <div class="orderFlowBar">
                <ul class="clearfix">
                    <li><i class="pastTenseIcon"></i><span class="pastTenseFonts">选择产品</span></li>
                    <li><i class="pastTenseIcon"></i><span class="pastTenseFonts">填写订单</span></li>
                    <li class="currentStatus"><i class="nowTenseIcon"><em>3</em></i><span class="nowTenseFonts">在线支付</span></li>
                    <li><i class="futureTenseIcon"><em>4</em></i><span class="futureTenseFonts">支付完成</span></li>
                </ul>
            </div>
            <section class="onLinePayContent">
                <div class="top-border"></div>
                <div class="withoutOrderModel">
                    <div class="withoutDivision clearfix">
                        <p class="text-center"><label class="noPosition"></label></p>
                        <h2 class="text-center withoutInfo">Oh no!</h2>
                        <p class="text-center withoutTip">实在抱歉，有人刚刚把剩余的几个位置抢光了！</p>
                    </div>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="withoutOrderModel01">
                    <div class="titleModel padding20">
                        <p class="text-center withoutTip">你可以</p>
                    </div>
                    <div class="text-center payWayContent clearfix">
                        <div class="withoutLeft">
                            <ul>
                                <li class="text-center" style="padding-bottom: 5px;"><a href="javascript:void(0);"><label class="clickme"></label></a></li>
                                <li class="text-center withoutText">点我</li>
                                <li class="text-center withoutText">预定其他日期</li>
                            </ul>
                        </div>
                        <div class="withoutRight">
                            <li class="text-center" style="padding-bottom: 5px;"><label class="weixin"></label></li>
                            <li class="text-center withoutText">扫一扫微信</li>
                            <li class="text-center withoutText">联系小真真客服咨询</li>
                        </div>
                        <div class="chkUser-center"></div>
                    </div>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
            </section>

</@html.htmlIndex>

 <script type="text/javascript">
        $(".clickme").hover(function(){
            $(".clickme").css("background-image","url(${host}/images/client/withImg_03.jpg)");
        }, function () {
            $(".clickme").css("background-image","url(${host}/images/client/withImg_01.jpg)");
        });
    </script>