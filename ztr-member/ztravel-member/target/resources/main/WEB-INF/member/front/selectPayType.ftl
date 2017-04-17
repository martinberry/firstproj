<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex
	curModule="用户"
	title="常旅客管理"
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
                <div class="commonOrderModel">
                    <div class="aboutDivision clearfix">
                        <div class="titleLeft">
                            <div class="orderNumberFonts">订单号：1231325400315121</div>
                            <h3>上海至巴黎五晚13天话色彩国度</h3>
                        </div>
                        <div class="titleRight">
                            <div class="rental">
                                <b class="fonts18">应付总额</b>
                                <b class="orangeFonts33">￥12639</b>
                            </div>
                            <div class="rentalDetail">
                                <label>产品总额: <span class="orangeFonts">￥13520</span></label>
                                <label>代金券: <span class="orangeFonts">-￥50</span></label>
                                <label>积分: <span class="orangeFonts">-￥12.5</span></label>
                            </div>
                        </div>
                    </div>
                    <div class="payHintExplain">
                        <i class="borderRadius left"></i>
                        <span class="payHintFonts">请于30分钟内完成付款，否则订单将被取消</span>
                        <i class="borderRadius right"></i>
                    </div>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonOrderModel">
                    <div class="titleModel padding30">
                        <i class="payTitleIcon"></i>选择支付方式
                    </div>
                    <div class="payWayContent">
                        <div class="radioContent">
                            <label class="radio active">
                                <span class="commonIcon radioIcon"></span>
                                <span class="commonPayWayIcon payWayIcon01"></span>
                            </label>
                            <label class="radio">
                                <span class="commonIcon radioIcon"></span>
                                <span class="commonPayWayIcon payWayIcon02"></span>
                            </label>
                            <label class="radio">
                                <span class="commonIcon radioIcon"></span>
                                <span class="commonPayWayIcon payWayIcon03"></span>
                            </label>
                        </div>
                    </div>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="btnCenter">
                    <a href="javascript:void(0);" class="commonBtn orangeBtn width170 ac-payHintWindow">去支付</a>
                </div>
            </section>
		</div>

	</div>


    <div class="modal fade" id="ac-payHintWindow">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">支付提示</h4>
                </div>
                <div class="modal-body">
                    <p>请在新打开的页面中完成支付，支付完成前请不要关闭本窗口</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn width170">支付成功</a>
                    <a href="javascript:void(0);" class="commonBtn orangeBtn width170">支付失败,继续支付</a>
                </div>
            </div>
        </div>
    </div>


  <script>
        $(function(){
            $(".modal").modal({
                backdrop:"static",
                keyboard: false,
                show: false
            });
            $(".ac-payHintWindow").click(function(){
                $("#ac-payHintWindow").modal("show");
            });

        });
    </script>


</@html.htmlIndex>

