<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-我的订单"
currentMenu="我的订单"
remoteJsFiles=[]
remoteCssFiles=["css/client/orderInfo.css"]
localJsFiles=[]
localCssFiles=[]>
    <div class="main-wrapper" id="main-wrapper">
        <section class="withoutOrder main-box">
            <span class="pin"></span>
            <div class="top-border"></div>
            <div class="panel panel-default withoutOrderBox">
                <div class="panel-body">
                    <div class="withoutOrder-row1 text-center">
                        Hello,<span class="txtColor">${nickName!}</span>,你还没有订单哦~
                    </div>
                    <div class="withoutOrder-row2"></div>
                    <a class="withoutOrder-row3 text-center" href="${basepath}/home">踏上真旅，一起去看世界</a>

                </div>
            </div>
        </section>
    </div>

</@html.navHeader>