<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
		<@html.htmlIndex
			title="私信列表"
		  	remoteCssFiles=["mstatic/css/privateLetterList.css"]
		  	localJsFiles=["member/weixin/letter/letter.js"]
		  				  remoteJsFiles=["mstatic/js/iscroll/iscroll-probe.js"]
		>
	<div data-role="page">
		<@head.headerBar title="私信列表"></@head.headerBar>
		<div class="wrapper">
			<div id="wrapper">
				<div id="scroller">
					<div id="pullDown">
						<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
					</div>
                    <div class="privateLetterTitle">
                        <span class="titleFonts">私信</span>
                        <span class="numberFonts">(0)</span>
                    </div>
                    <ul class="listContent" id="thelist">

                    </ul>
					<div id="pullUp">
						<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
					</div>
				</div>
			</div>
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