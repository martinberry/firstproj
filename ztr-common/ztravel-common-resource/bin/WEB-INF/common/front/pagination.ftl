    <div class="pagination">
    <span class="countNum">共 <span class="totalItemCount"><#if totalItemCount??>${totalItemCount}</#if></span> 条记录，</span>
    <span class="pageNum">
        <span class="pageNumClick">每页 <span class="pageSize"><#if pageSize??>${pageSize}</#if></span> 条 <i class="pageNumIcon"></i></span>
        <ul class="paginationNum" style="display: none;" >
            <li <#if pageSize==5>class="active"</#if>>
                <a href="javascript:void(0);">5</a>
            </li>
            <li <#if pageSize==10>class="active"</#if>>
                <a href="javascript:void(0);">10</a>
            </li>
            <li <#if pageSize==20>class="active"</#if>>
                <a href="javascript:void(0);">20</a>
            </li>
            <li <#if pageSize==30>class="active"</#if>>
                <a href="javascript:void(0);">30</a>
            </li>
            <li <#if pageSize==40>class="active"</#if>>
                <a href="javascript:void(0);">40</a>
            </li>
            <li <#if pageSize==50>class="active"</#if>>
                <a href="javascript:void(0);">50</a>
            </li>
        </ul>
    </span>
    <span class="pageSize"><span class="pageNo"><#if pageNo??>${pageNo}</#if></span>/<span class="totalPageCount"><#if totalPageCount??>${totalPageCount}</#if></span></span>
    <span class="pageArrow">
        <a href="javascript:void(0);" class="firstPage <#if pageNo==1|| pageNo==0>disabled</#if>">
            <i></i>
        </a>
        <a href="javascript:void(0);" class="prevPage <#if pageNo==1|| pageNo==0>disabled</#if>">
            <i></i>
        </a>
        <a href="javascript:void(0);" class="nextPage <#if pageNo==totalPageCount>disabled</#if>">
            <i></i>
        </a>
        <a href="javascript:void(0);" class="lastPage <#if pageNo==totalPageCount>disabled</#if>">
            <i></i>
        </a>
    </span>
    <span>
        <span>跳转到</span>
        <input type="text" style="width: 45px;" class="pageToNum">
        <a href="javascript:void(0);" class="pageTo">
            <i></i>
        </a>
    </span>
    </div>