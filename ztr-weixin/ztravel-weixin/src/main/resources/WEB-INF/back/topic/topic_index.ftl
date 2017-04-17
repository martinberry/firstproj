 <#import "/common/opera/htmlIndex.ftl" as html/>
<#import "/common/opera/main_header.ftl" as main_header/>
<@html.htmlIndex jsFiles=["back/topic/weixinTopic.js","common/pagination.js"] cssFiles=["css/maintain/wechat.css","css/common.css","css/maintain/common.css","css/maintain/operatorManagement.css","css/bootstrap.min.css","css/bootstrap.reset.css"]
curModule="微信管理" title="微信管理-微信话题">
    <@main_header.header currentMenu="运营管理" currentSubMenu="微信管理">
    <div class="operationDiv"><i class="plusIcon"></i><a href="${basepath}/weixinTopic/newTopic">新话题</a></div>   
	</@main_header.header>
 
 <div class="main-container clearfix">
            <aside class="leftMenu">
                <ul class="menuChange">
                    <li>
                        <a href="${basepath}/weixinMenu/index" class="menu">自定义菜单管理</a>
                        <i class="rightArrow"></i>
                    </li>
                    <li class="current">
                        <a href="${basepath}/weixinTopic/index" class="topic">微信话题</a>
                        <i class="rightArrow"></i>
                    </li>
                </ul>
            </aside> 
            <section class="rightModelContent">
             <form id="searchConditionForm">
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>话题标题</th>
                                <td>
                                    <input type="text" id="topicTitle" name="topicTitle" placeholder="关键字"/>
                                     <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>关联产品</th>
                                <td>
                                    <input type="text" id="productTitle" name="productTitle" placeholder="关键字"/>
                                     <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="160">
                                <col width="310">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>状  态</th>
                                <td>
                                    <div class="dropdown" style="width: 110px;">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts">全部</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="active"><a href="javascript:void(0);">全部</a></li>
                                            <li><a href="javascript:void(0);">上线</a></li>
                                            <li><a href="javascript:void(0);">关闭</a></li>
                                            <li><a href="javascript:void(0);">草稿</a></li>
                                        </ul>
                                    </div>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            <tr class="fitToTr">
                            </tbody>
                        </table>
                    </li>
                </ul>
               <input type="hidden" name="pageNo" value="1" />
			   <input type="hidden" name="pageSize" value="10" />                               
            </form>
             <input type="hidden" name="sortType" value="time" />   
              <section class="clearfix" id="searchField">
                <table class="commonTab productTab">
                    <colgroup>
                        <col width="16%">
                        <col width="28%">
                        <col width="14%">
                        <col width="14%">
                        <col width="9%">
                        <col width="6%">
                        <col width="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>话题标题</th>
                        <th>关联产品</th>
                        <th>礼品标题</th>
                        <th>上线时间</th>
                        <th>
                            讨论条数
                            <i class="sortArrow upGray discuss"></i>
        
                        </th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                   </tbody>
                </table>
                  <div id="pagination">
                  </div>
                 </section>  
            </section>
        </div>
    </div>
    
    
      <div class="modal fade" id="listdelete">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认要删除该话题吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button confirmDelete">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    
     <div class="modal fade" id="listreleased">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认要将该话题上线吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button confirmReleased">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    
     <div class="modal fade" id="listoffline">
        <div class="modal-dialog" style="width: 500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认要将该话题关闭吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button confirmOffline">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

</@html.htmlIndex>
