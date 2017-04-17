<header class="main-header hoveringModel">
    <div class="stairMenuContent topMenuFonts">
        <div class="wrap clearfix">
            <div class="navLeft">
                <a class="logo" href="/"></a>
            </div>
           <#include "/common/opera/header_right.ftl" />
        </div>
    </div>
    <div class="headSecondLevel headBtn clearfix" style="bottom: -48px;">
        <span class="rightBtn">
         <#if (readOnly)!false >
            <#if (orderNotCancelled)!false>
            <button class="commonButton blue-45c8dcButton" id="sendEmailHeaderBtn">发送邮件</button>
            </#if>
            <button class="commonButton whiteBtn ac-generateOrderWindowBtn" id="cancelHeaderBtn">返回</button>
         <#else>
            <button class="commonButton orange-f79767Btn" id="confirmHeaderBtn">完成确认</button>
            <button class="commonButton whiteBtn ac-generateOrderWindowBtn" id="cancelHeaderBtn">取 消</button>
         </#if>
        </span>
    </div>
</header>