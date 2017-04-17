<#import "/common/ftl/htmlIndex.ftl" as html>
<#macro htmlBase jsFiles=[] cssFiles=[] title="">
<@html.htmlIndex jsFiles cssFiles >
	<div id="container" style="margin-top:60px">
		<div class="actions block">
            <div class="col col-l">
                <span class="page-title">${title}</span>
            </div>
        </div>
		<div id="mainContent">
			<#nested/>
		</div>
	</div>
</@html.htmlIndex>
</#macro>
