<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "/common/opera/main_header.ftl" as main_header/>
<@html.htmlIndex jsFiles=["weixinMenu/menu.js","weixinMenu/material.js", "common/pagination.js"] cssFiles=["css/maintain/wechat.css","css/common.css","css/maintain/common.css","css/maintain/operatorManagement.css","css/bootstrap.min.css","css/bootstrap.reset.css"]
curModule="微信管理" title="微信管理-自定义菜单管理">
    <@main_header.header currentMenu="运营管理" currentSubMenu="微信管理">
	</@main_header.header>

  <div class="main-container">
  
         <aside class="leftMenu">
                <ul class="menuChange">
                    <li class="current">
                        <a href="${basepath}/weixinMenu/index" class="menu">自定义菜单管理</a>
                        <i class="rightArrow"></i>
                    </li>
                    <li>
                        <a href="${basepath}/weixinTopic/index" class="topic">微信话题</a>
                        <i class="rightArrow"></i>
                    </li>
                </ul>
            </aside>
            
         <section class="rightModelContent">           
        <div class="whiteBg">
            <div class="container-til">自定义菜单管理</div>
            <div class="wechat-container">
                <section>
                    <div class="notice-til">注意：</div>
                    <p>1级菜单最多只能开启3个，2级子菜单最多开启5个!</p>
                    <p>生成自定义菜单,必须在已经保存的基础上进行,临时勾选启用点击生成是无效的! 第一步必须先修改保存状态！第二步点击生成!</p>
                    <p>当您为自定义菜单填写链接地址时请填写以"http://"开头，这样可以保证用户手机浏览的兼容性更好。</p>
                    <p>撤销自定义菜单：撤销后，您的微信公众帐号上的自定义菜单将不存在；如果您想继续在微信公众帐号上使用自定义菜单，请点击“生成自定义菜单”按钮，将重新启用！</p>
                </section>
                <div class="container-menu">
                    <a class="wechat-addMainBtn" href="javascript:void(0);"><i class="addBtn"></i>添加主菜单</a>
                    <table class="wechat-menuTab">
                        <colgroup>
                            <col width="169" />
                            <col width="469" />
                            <col width="165" />
                            <col width="366" />
                            <col width="110" />
                            <col width="100" />
                        </colgroup>
                        <thead>
                            <th>显示顺序</th>
                            <th>主菜单名称</th>
                            <th>触发动作</th>
                            <th>响应动作</th>
                            <th>启用</th>
                            <th>操作</th>
                        </thead>
                        <tbody class="body-menu">
                        <#if menuVo?? >
                        	<#if menuVo.firstLevelButtons ?? >
                        		<#list menuVo.firstLevelButtons as firstLevelButton>
                        			<tr class="first-menu" del-value="del_${firstLevelButton_index!}">
                        			    <td><input class="first-menu-num" type="text" value="${firstLevelButton.priority!}" ></td>
                        			    <td><input class="first-menu-name" type="text" value="${firstLevelButton.name!}"><a class="addBtn01" href="javascript:void(0);"></a></td>
										<#if firstLevelButton.subMenuButtonVos ?? >
												<td></td>
												<td></td>
											     <td>
						                                <div class="checkboxAction">
						                                    <label class="checkboxInfo <#if firstLevelButton.state == 1>active </#if>">
						                                        <span class="checkboxIcon " ></span>
						                                    </label>
						                                </div>
						                            </td>
						                            <td style="text-align: center;">
						                                <a class="first-menu-op" href="javascript:void(0);">删除</a>
						                            </td>
										        </tr>
											<#list firstLevelButton.subMenuButtonVos as secondLevelButton>
												   <tr class="second-menu"  del-value="del_${firstLevelButton_index!}_${secondLevelButton_index!}">
	                            					 <td><input class="second-menu-num" type="text" value="${secondLevelButton.priority!}"></td>
	                            					 <td>
						                                <i class="second-item-line"></i><input class="second-menu-name" type="text" value="${secondLevelButton.name!}">
						                            </td>
						                             <td>
						                                <div class="dropdown first-menu-action">
						                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
						                                        <span class="activeFonts"> <#if secondLevelButton.type??><#if secondLevelButton.type == "view">跳转链接<#elseif secondLevelButton.type == "click">发送消息</#if></#if></span>
						                                        <span class="caret"></span>
						                                    </a>
						                                    <ul class="dropdown-menu">
						                                        <li><a href="javascript:void(0);">跳转链接</a></li>
						                                        <li class="active"><a href="javascript:void(0);">发送消息</a></li>
						                                    </ul>
						                                </div>
						                            </td>
					                            	<#if secondLevelButton.type == 'view'>
						                            	 <td>
							                                <input type="text" class="first-menu-response" value="${secondLevelButton.url!}">
							                            </td>
					                            	<#elseif secondLevelButton.type == 'click'>
					                            	<td>
					                            		<#if secondLevelButton.key ??>
					                            		<input type="hidden" name= "mediaId" value=${secondLevelButton.mediaId}>
					                            		<a href="javascript:void(0);" class="teleText hidden">选择图文信息</a>
					                            		<div class="cuRadioTxt clearfix"><span><#if secondLevelButton.key?length gt 15>${secondLevelButton.key?substring(0,15)}...<#else>${secondLevelButton.key!}</#if></span><a href="javascript:void(0);" class="wechat-close"></a></div>
														<#else>
														<a href="javascript:void(0);" class="teleText">选择图文信息</a>
														</#if>
															</td>
					                            	</#if>
					                            	   <td>
						                                <div class="checkboxAction">
						                                    <label class="checkboxInfo <#if secondLevelButton.state == 1>active </#if>">
						                                        <span class="checkboxIcon" ></span>
						                                    </label>
						                                </div>
						                            </td>
						                            <td style="text-align: center;">
						                                <a class="first-menu-op" href="javascript:void(0);">删除</a>
						                            </td>
					                            </tr>
											</#list>
										<#else>
											<td>
				                                <div class="dropdown first-menu-action">
				                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
				                                        <span class="activeFonts"><#if firstLevelButton.type == 'view'>跳转链接<#elseif firstLevelButton.type == 'click'>发送消息</#if></span>
				                                        <span class="caret"></span>
				                                    </a>
				                                    <ul class="dropdown-menu">
				                                        <li class="active"><a href="javascript:void(0);">跳转链接</a></li>
				                                        <li><a href="javascript:void(0);">发送消息</a></li>
				                                    </ul>
				                                </div>
				                            </td>
				                            <#if firstLevelButton.type == 'view'>
			                            	     <td>
					                                <input type="text" class="first-menu-response" value="${firstLevelButton.url}" >
					                            </td>
					                        <#elseif firstLevelButton.type == 'click'>
					                         <td>
												<#if firstLevelButton.key ??>

													<input type="hidden" name= "mediaId" value=${firstLevelButton.mediaId}>
													<a href="javascript:void(0);" class="teleText hidden">选择图文信息</a>
													<div class="cuRadioTxt clearfix"><span><#if firstLevelButton.key?length gt 15>${firstLevelButton.key?substring(0,15)}...<#else>${firstLevelButton.key!}</#if></span><a href="javascript:void(0);" class="wechat-close"></a></div>
												<#else>
															<a href="javascript:void(0);" class="teleText">选择图文信息</a>

												</#if>
											</td>
				                            </#if>
				                              <td>
					                                <div class="checkboxAction">
					                                    <label class="checkboxInfo <#if firstLevelButton.state == 1>active </#if>">
					                                        <span class="checkboxIcon"></span>
					                                    </label>
					                                </div>
					                            </td>
					                            <td style="text-align: center;">
					                                <a class="first-menu-op" href="javascript:void(0);" >删除</a>
					                            </td>
			                            	</tr>
										</#if>

                        		</#list>
                        	</#if>
                        </#if>
                        </tbody>
                    </table>
                    <a href="javascript:void(0);" class="wechat-saveBtn" id="saveBtn">保 存</a><a href="javascript:void(0);" class="wechat-customMenu" id="customMenu">生成自定义菜单</a>
                    <a href="javascript:void(0);" class="wechat-revokeMenu" id="revokeMenu">撤销自定义菜单</a>
                </div>
            </div>
            </section>
        </div>
    </div>


    <!--删除操作-->
    <div class="modal fade" id="deleModal">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <p style="text-align: center"><span class="notice-icon"></span>确定删除所选消息？</p>
                </div>
                <input type="hidden" id="delLineId">
                <input type="hidden" id="delLineLevel">
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonNoticeBtn confirmBtn" id="confirm_del">确认</a>
                    <a href="javascript:void(0);" class="commonNoticeBtn cancelBtn" data-dismiss="modal">取消</a>
                </div>
            </div>
        </div>
    </div>

      <!--删除操作-->
    <div class="modal fade" id="cancelConfirm">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <p style="text-align: center"><span class="notice-icon"></span>确定撤销自定义菜单？</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonNoticeBtn confirmBtn" id="cancelMenu_confirm">确认</a>
                    <a href="javascript:void(0);" class="commonNoticeBtn cancelBtn" data-dismiss="modal">取消</a>
                </div>
            </div>
        </div>
    </div>

<!--为空校验框-->
 <div class="popup-saveBox" id="firstLevelTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>一级菜单名称请输入1-5个中文！</span>
            </div>
        </div>
</div>

 <div class="popup-saveBox" id="secondLevelTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>二级菜单名称请输入1-13个汉字！</span>
            </div>
        </div>
</div>

<div class="popup-saveBox" id="jumpUrlTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>请输入正确的url！</span>
            </div>
        </div>
</div>
<div class="popup-saveBox" id="firstLevelPriorityNumRepeatTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>一级菜单中序号不能相同！</span>
            </div>
        </div>
</div>

<div class="popup-saveBox" id="priorityNumTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>请输入正整数1~9！</span>
            </div>
        </div>
</div>

<div class="popup-saveBox" id="secondLevelPriorityNumRepeatTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>一级菜单内二级菜单的序号不能相同！</span>
            </div>
        </div>
</div>

 <div class="popup-saveBox" id="chooseNewsMessageTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>请选择图文信息！</span>
            </div>
        </div>
</div>

        <!--保存成功-->
    <div class="popup-saveBox" id="dlg-saveMenu">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>保存成功！</span>
            </div>
        </div>
    </div>
     <div class="popup-saveBox" id="dlg-saveMenu-failed">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>保存失败！</span>
            </div>
        </div>
    </div>
    <!--生成成功-->
    <div class="popup-saveBox" id="dlg-bliudMenu">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>生成菜单成功！</span>
            </div>
        </div>
    </div>
     <div class="popup-saveBox" id="dlg-bliudMenu-failed">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>生成菜单失败！</span>
            </div>
        </div>
    </div>

    <!--自定义菜单已撤销-->
    <div class="popup-saveBox" id="dlg-cancelMenu">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>自定义菜单已撤销！</span>
            </div>
        </div>
    </div>
    <div class="popup-saveBox" id="dlg-cancelMenu-failed">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>自定义菜单撤销失败！</span>
            </div>
        </div>
    </div>

    <div class="popup-saveBox" id="choose_material_tip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>请选择一个素材！</span>
            </div>
        </div>
    </div>

     <div class="popup-saveBox" id="firstLevelLengthTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>1级菜单最多只能开启3个！</span>
            </div>
        </div>
    </div>
      <div class="popup-saveBox" id="secondLevelLengthTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>2级子菜单最多开启5个！</span>
            </div>
        </div>
    </div>

</div>


<!--选择图文信息弹窗-->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="dlg-teleText">
        <div class="modal-dialog" style="width:808px; height:732px;">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">选择图文信息</h4>
                </div>
                <div class="modal-body wechat-dlg-content clearfix">
                    <div class="wechat-toolbars">
                    <input type="hidden" id="selectMenuButtonId" >
                    <form id="searchForm" onsubmit="return false;">
                      <span>产品标题</span><input class="toolbars-til" type="text" name="title" value="${title!}"><a href="javascript:void(0);" class="toolbars-search"><i class="toolbars-search-btn"></i>搜 索</a>
                     <input type="hidden" name="pageNo" value="1" />
				    <input type="hidden" name="pageSize" value="20" />
				    <input type="hidden" name="type" value="normal" />
				    </form>
                    </div>
                      <section class="clearfix" id="searchField">
                    <table class="commonTab">
                        <tbody>
                        </tbody>
                    </table>
                    <div id="pagination">
                    </div>
                </section>

                    <div class="wechat-dlg-foot">
                        <a href="javascript:void(0);" class="wechat-dlg-confirm">确 定</a>
                        <a href="javascript:void(0);" class="wechat-dlg-cancle"  data-dismiss="modal">取 消</a>
                    </div>
                </div>
            </div>
        </div>
     </div>

</@html.htmlIndex>


