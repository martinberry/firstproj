<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
		<@html.htmlIndex
			title="私信列表"
		  	remoteCssFiles=["mstatic/css/privateLetterList.css"]
		  	localJsFiles=[]
		  				  remoteJsFiles=[]
		>
	<div data-role="page">
		<@head.headerBar title="私信列表"></@head.headerBar>
		<div class="without-order-cnt">
        <img class="without-img" src="${host}/mstatic/images/without-letter.png">
        <div class="without-txt">您现在还没有私信</div>
    </div>
	</div>

<script type="text/javascript">
	$(function(){

        $(".rightDetails .secondEllipsis").each(function(){
            var divH = $(this).height();
            var $p = $("p", $(this)).eq(0);
            while ($p.outerHeight() > divH) {
                $p.text($p.text().replace(/(\s)*([a-zA-Z0-9]+|\W)(\.\.\.)?$/, "..."));
            }
        });

	});

</script>
</@html.htmlIndex>