    <#macro newTopic operation="">
    <header class="main-header hoveringModel">
            <div class="stairMenuContent">
                <div class="wrap clearfix">
                    <div class="navLeft">
                        <a class="logo" href="/"></a>
                        <ul class="stairMenu">
                            <li class="editFonts newtopic">新增话题</li>
                        </ul>
                    </div>
                 <#include "/common/opera/header_right.ftl" />
                </div>
            </div>
            <div class="headSecondLevel headBtn clearfix">
                <span class="leftBtn">
                    <a href="${basepath}/weixinTopic/index" class="returnBtn">< 返回</a>             
                </span>
                <span class="rightBtn">
                    <button class="commonButton red-fe6869Button save">保 存</button>
                    <button class="commonButton whiteBtn released">发 布</button>
                </span>
            </div>
        </header>
        <#nested/>
  </#macro>      
        
        