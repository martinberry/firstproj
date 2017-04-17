<#macro pieceMenu curPieceModule="">
    <header class="main-header hoveringModel">
	    <div class="stairMenuContent topMenuFonts">
	        <div class="wrap clearfix">
	            <div class="navLeft">
	                <a class="logo" href="/"></a>
	                <ul class="stairMenu haveTopFonts">
	                    <li class="editFonts">&lt;${(pieceMenuVo.name)!''}&gt;</li>
	                    <li class="routeTipsFonts">编号:${(pieceMenuVo.code)!''}</li>
	                    <li class="routeTipsFonts">类型：${(pieceMenuVo.type)!''}</li>
	                </ul>
	            </div>
	            <#include "/common/opera/header_right.ftl" />
	        </div>
	    </div>
	    <div class="headSecondLevel headBtn clearfix">
	        <span class="leftBtn">
	            <a href="${basepath!''}/pieces/maintain/index" class="returnBtn">< 返回</a>
	        </span>
	        <ul class="middleContent">
	            <li <#if curPieceModule=="basicInfo">class="active"</#if>>
	            	<#if (pieceMenuVo.pid)! != '' >
	                	<a href="${basepath!''}/pieces/basicInfo/${mode!''}/${(pieceMenuVo.nature)!''}/${(pieceMenuVo.pid)!''}">基本信息</a>
	                <#else>
	            		<a href="javascript:void(0);">基本信息</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	            <li <#if curPieceModule=="detailInfo">class="active"</#if>>
	            	<#if ((pieceMenuVo.progress)! > 0) >
	            		<a href="${basepath!''}/pieces/detailInfo/${mode!''}/${(pieceMenuVo.nature)!''}/${(pieceMenuVo.pid)!''}">详情信息</a>
	            	<#else>
	            		<a href="javascript:void(0);">详情信息</a>
	            	</#if>
	            	<i class="downArrowIcon"></i>
	            </li>
	            <li <#if curPieceModule=="priceCostInfo">class="active"</#if>>
	            	<#if ((pieceMenuVo.progress)! > 1) >
	                	<a href="${basepath!''}/pieces/priceInfo/${mode!''}/cost/${(pieceMenuVo.nature)!''}/${(pieceMenuVo.pid)!''}">成本配置</a>
	                <#else>
	            		<a href="javascript:void(0);">成本配置</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	            <li <#if curPieceModule=="priceSaleInfo">class="active"</#if>>
	            	<#if ((pieceMenuVo.progress)! > 2) >
	                	<a href="${basepath!''}/pieces/priceInfo/${mode!''}/sale/${(pieceMenuVo.nature)!''}/${(pieceMenuVo.pid)!''}">价格维护</a>
	                <#else>
	            		<a href="javascript:void(0);">价格维护</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	            <li <#if curPieceModule=="additionalInfo">class="active"</#if>>
	            	<#if ((pieceMenuVo.progress)! > 3) >
	                	<a href="${basepath!''}/pieces/additionalInfo/${mode!''}/${(pieceMenuVo.nature)!''}/${(pieceMenuVo.pid)!''}">附加信息</a>
	                <#else>
	            		<a href="javascript:void(0);">附加信息</a>
	            	</#if>
	                <i class="downArrowIcon"></i>
	            </li>
	        </ul>
	        <#if mode?? && mode == 'edit'>
	        	<span class="rightBtn">
		            <button class="commonButton red-fe6869Button top">保 存</button>

		            <#if curPieceModule=="additionalInfo">
		            	<button class="commonButton orange-f79767Btn top">发布</button>
		            	<button class="commonButton blue-45c8dcButton" id="prevBtn">预览</button>
		            <#else>
			            <button class="commonButton whiteBtn top">保存&下一步</button>
		            </#if>
		        </span>
		    <#else>
			    <span class="rightBtn">
			    	<button class="commonButton blue-45c8dcButton top">编辑</button>
			    	<#if curPieceModule=="additionalInfo">
			    	<button class="commonButton orange-f79767Btn top">发布</button>
		            </#if>
			    </span>
	        </#if>
	    </div>
	</header>

    <#nested/>
</#macro>