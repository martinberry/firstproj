<#import "/common/front/mainIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="首页"
	jsFiles=["member/front/login.js","common/productSearchBlock.js"]
	localCssFiles=["member/front/round_image.css"]>
	<#include "/common/front/login.ftl" />
</@html.htmlIndex>