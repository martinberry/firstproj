<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/maintain/backtain.css"] curModule="" title="真旅行首页">
    <@main_header.header>
	</@main_header.header>

 <div class="main-container">
        <div class="whiteBg">
            <div class="cntxt">欢迎，<span>${getCurrOp()!''}</span>登录真旅行后台！</div>
        </div>
    </div>


</@html.htmlIndex>