<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />

<@html.htmlIndex
jsFiles=["common/pagination.js","member/opera/operator_message.js"]
cssFiles=["css/maintain/notice.css"]
curModule=""
title="后台消息">
<@main_header.header currentMenu="" currentSubMenu=""></@main_header.header>

<div class="main-container">
    <div class="whiteBg" id="searchField">
        <h2>消息</h2>
        <table class="commonTab" style="margin-top:10px;">
            <colgroup>
                <col width="140">
                <col width="120">
                <col width="500">
                <col width="180">
                <col width="60">
            </colgroup>
            <thead>
            <tr>
                <th>业务类型</th>
                <th>消息主题</th>
                <th>消息内容</th>
                <th>时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div class="pagination" id="pagination" style="width:1000px;float:left;"></div>
        <input type="hidden" name="pageNo" value="1" />
	    <input type="hidden" name="pageSize" value="10" />
    </div>
</div>


</@html.htmlIndex>