<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

<@html.htmlIndex title="真旅行-用户评论"
		  	remoteCssFiles=["mstatic/css/homePage.css"]
		  	remoteJsFiles=[]
		  	localJsFiles=[]>

<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" data-url="${wxServer}/product/weixin/detail/${productPid!}" tabindex="0">
    <@head.headerBar title="用户评论"></@head.headerBar>
    <div class="prd-space"></div>
    <div class="judge-content">
        <div class="path-til">用户评价<span class="judge-num">(${commentNum!0})</span></div>
        <#list commentList as comment>
			<div class="judge-cnt clearfix">
	            <div class="judge-top"><img class="pop-img" src="${mediaserver}imageservice?mediaImageId=${(comment.headImgId)!}"><span class="pop-name">${(comment.memNickName)!}</span><span class="fr judge-date">${(comment.date)!}</span></div>
	            <div class="judge-stars">
	            	<#list 1..(comment.stars)?number as count>
	            		<span class="yellow-star"></span>
	            	</#list>
	            </div>
	            <p class="judge-txt">${(comment.comment)!}</p>
	        </div>
		</#list>
    </div>
</div>

<div class="ui-loader ui-corner-all ui-body-a ui-loader-default"><span class="ui-icon-loading"></span><h1>loading</h1></div>
</@html.htmlIndex>