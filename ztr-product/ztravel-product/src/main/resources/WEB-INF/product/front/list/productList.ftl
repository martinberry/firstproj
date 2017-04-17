<#import "/common/front/userHtmlBase.ftl" as html />
<!-- <#import "/common/front/header/mainHeader.ftl" as mainHeader /> -->
<@html.htmlIndex remoteJsFiles=["js/base/jquery-1.11.2.min.js", "js/bootdist/bootstrap.min.js", "js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "js/vendor/jquery.color-2.1.2.min.js", "js/global/global.js", "js/client/common.js", "js/vendor/jquery.lazyload.js", "js/vendor/skrollr.min.js", "js/client/workplatform.js", "js/client/searchheader.js"]
                                  remoteCssFiles=["css/bootstrap.min.css", "css/client/bootstrap.reset.css", "css/bootstrap-datepicker.min.css", "css/common.css", "css/client/common.css", "css/client/productInfo.css", "css/client/workplatform.css","css/client/searchheader.css"]
                                  localCssFiles=["member/front/round_image.css"]
                                  localJsFiles=["product/front/productList.js", "member/front/login.js", "common/isOuterTrigger.js", "common/login_header.js"] title="${title!}" keywords="${keywords!}" description="${description!}">

<script type="text/javascript">
	var isLogin = '${(wpfv.isLogin)?c}';
</script>

<#include "/common/front/header/search_common_header.ftl"/>

<div class="product-container">
    <#include "/common/front/work_platform.ftl"/>
    <div id="productData">
    </div>
</div>
<#include "/common/front/right_side.ftl"/>
<#include "/common/front/header/footer.ftl"/>
</@html.htmlIndex>