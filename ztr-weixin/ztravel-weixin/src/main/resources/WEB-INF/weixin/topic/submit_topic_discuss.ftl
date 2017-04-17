<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

<@html.htmlIndex title="真旅行"
		  	remoteCssFiles=["mstatic/css/topic/topic_discuss.css"]
		  	localJsFiles=["front/topic/topic_discuss.js"]>

	<div class="viewport" data-role="page">
		<@head.headerBar title="我想说"></@head.headerBar>
	
		<div class="submit-tip tips-fail" id="errTips">评论失败！</div>
		<div class="submit-tip tips-cuccess" id="sucTips">评论成功！</div>
	    <input type="hidden" id="openId" value="${(wxUser.openid)!''}">
	    <input type="hidden" id="nickName" value="${(wxUser.nickname)!''}">
	    <input type="hidden" id="headImageUrl" value="${(wxUser.headimgurl)!''}">
	    <input type="hidden" id="topicId" value="${(topic.topicId)!''}">
	    <input type="hidden" id="appId" value="${appId!''}">
	    <div class="detail-content">
	        <div class="topicModel">
	            <h1><i class="discussIcon"></i>${(topic.topicTitle)!''}</h1>
	            <div class="publish_discuss">
	                <textarea class="disCont" placeholder="我想说" data-role="none"></textarea>
	                <div class="publish">
	                    匿名<label class="switch-checkbox">
	                    	<i></i>
	                    </label>
	                    <a class="publishBtn" href="javascript:void(0);" data-role="none">发 表</a>
	                </div>
	            </div>
	        </div>
	    </div>
	
	</div>
</@html.htmlIndex>