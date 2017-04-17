<#macro productMenu curProductModule="">
    <header class="main-header hoveringModel">
	    <div class="stairMenuContent topMenuFonts">
	        <div class="wrap clearfix">
	            <div class="navLeft">
	                <a class="logo" href="/"></a>
	                <ul class="stairMenu haveTopFonts">
	                    <li class="editFonts">&lt;${(productMenuVo.name)!''}&gt;</li>
	                    <li class="routeTipsFonts">编号:${(productMenuVo.code)!''}</li>
	                    <li class="routeTipsFonts">类型：${(productMenuVo.type)!''}</li>
	                </ul>
	            </div>
	            <#include "/common/opera/header_right.ftl" />
	        </div>
	    </div>
	    <div class="headSecondLevel headBtn clearfix">
	        <span class="leftBtn">
	            <a href="${basepath!''}/product/productMaintain/index" class="returnBtn">< 返回</a>
	        </span>
	        <ul class="middleContent">
	            <li <#if curProductModule=="basicInfo">class="active"</#if>>
	            	<#if (productMenuVo.pid)! != ''  >
	                	<a href="${basepath!''}/product/basicInfo/${mode!''}/${(productMenuVo.pid)!''}">基本信息</a>
	                <#else>
	            		<a href="javascript:void(0);">基本信息</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	            <li <#if curProductModule=="costManage">class="active"</#if>>
	            	<#if (productMenuVo.nature)! == 'PACKAGE' && ((productMenuVo.progress)! > 0) >
	            		<a href="${basepath!''}/product/cost/${mode!''}/${(productMenuVo.pid)!''}">底价维护</a>
	            	<#elseif (productMenuVo.nature)! == 'COMBINATION' && ((productMenuVo.progress)! > 0) >
	            		<a href="${basepath!''}/product/cost2/${mode!''}/${(productMenuVo.pid)!''}">底价维护</a>
	            	<#else>
	            		<a href="javascript:void(0);">底价维护</a>
	            	</#if>
	            	<i class="downArrowIcon"></i>
	            </li>
	            <li <#if curProductModule=="priceCalendar">class="active"</#if>>
	            	<#if ((productMenuVo.progress)! > 1) >
	                	<a href="${basepath!''}/product/calendar/${mode!''}/${(productMenuVo.pid)!''}">价格日历</a>
	                <#else>
	            		<a href="javascript:void(0);">价格日历</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	            <li <#if curProductModule=="recommendTrip">class="active"</#if>>
	            	<#if ((productMenuVo.progress)! > 2) >
	                	<a href="${basepath!''}/product/recommendTrip/${mode!''}/${(productMenuVo.pid)!''}">推荐行程</a>
	                <#else>
	            		<a href="javascript:void(0);">推荐行程</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	            <li <#if curProductModule=="additionalInfo">class="active"</#if>>
	            	<#if ((productMenuVo.progress)! > 3) >
	                	<a href="${basepath!''}/product/additionalInfo/${mode!''}/${(productMenuVo.pid)!''}">附加信息</a>
	                <#else>
	            		<a href="javascript:void(0);">附加信息</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	        </ul>
	        <#if mode?? && mode == 'edit'>
	        	<span class="rightBtn">
		            <button class="commonButton red-fe6869Button top">保 存</button>

		            <#if curProductModule=="additionalInfo">
		            	<button class="commonButton orange-f79767Btn top">发布</button>
		            	<button class="commonButton blue-45c8dcButton" id="prevBtn">预览</button>
		            <#else>
			            <button class="commonButton whiteBtn top">保存&下一步</button>
		            </#if>
		        </span>
		    <#else>
			    <span class="rightBtn">
			    	<button class="commonButton blue-45c8dcButton top">编辑</button>
			    	<#if curProductModule=="additionalInfo">
			    	<button class="commonButton orange-f79767Btn top">发布</button>
		            </#if>
			    </span>
	        </#if>
	    </div>
	</header>

    <#nested/>
</#macro>