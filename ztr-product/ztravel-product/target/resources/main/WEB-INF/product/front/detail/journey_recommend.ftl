<#macro journeyRecommend>

               <div class="commonModel journeyRecommendModel">
                    <div id="journeyRecommend" class="anchor"></div>
                    <div class="commonTitle"><i class="commonIcon journeyIcon"></i>行程推荐</div>
                    <div class="clearfix asideContContainer">
                        <ul class="asideFixed">
                            <li class="active">
                                <a href="#overview">概览</a>
                            </li>
                            <#list 1..(product.tripDays)! as index>
                    	         <li><a href="#D${(index)!}">D${(index)!}</a></li>
                            </#list>
                        </ul>
                        <div style="float: right;">
                        <!-- <div class="link" id="overview"></div> -->
                        <img data-original="${mediaserver}imageservice?mediaImageId=${(product.recommendTrips[0].desItems[0].images[0])!}" class="overviewImg lazyload-img" />
                        <ul class="journeyRecommendList">
                        <li class="active">
                            <div class="link" id="overview"></div>
                            <span class="commonIcon leftDot"></span>
                            <span class="leftLine"></span>
                            <ul class="wrapperList overviewBlock">
                                <li class="row twoRow overviewLeft">
	                                <h3 class="listTitle">目的地概览</h3>
									<pre><#noescape><#if (product.recommendTrips[0].content)??>${(product.recommendTrips[0].content)?replace("\n","<br>")}</#if></#noescape></pre>
                                </li>
                                <li class="row twoRow overviewRight">
                                    <div class="tipsTopBlock clearfix">
                                        <span class="title">当地Tips</span>
                                        <ul class="rightIcon tripTipsIconList clearfix">
                                            <li>
                                                <span class="tripTipsIcon weatherIcon current" title="天气"></span>
                                            </li>
                                            <li>
                                                <span class="tripTipsIcon shoppingIcon" title="消费"></span>
                                            </li>
                                            <li>
                                                <span class="tripTipsIcon communicationIcon" title="通信"></span>
                                            </li>
                                            <li>
                                                <span class="tripTipsIcon customIcon" title="当地风俗"></span>
                                            </li>
                                            <li>
                                                <span class="tripTipsIcon otherIcon" title="其他"></span>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="tipsContentBlock">
                                        <div style="display: none;"><ul class="listDetails"><#noescape>${(product.travelTips['WEATHER'])!}</#noescape></ul></div>
                                        <div style="display: none;"><ul class="listDetails"><#noescape>${(product.travelTips['EXPEND'])!}</#noescape></ul></div>
                                        <div style="display: none;"><ul class="listDetails"><#noescape>${(product.travelTips['COMMUNICATION'])!}</ul></#noescape></div>
                                        <div style="display: none;"><ul class="listDetails"><#noescape>${(product.travelTips['CUSTOM'])!}</#noescape></ul></div>
                                        <div style="display: none;"><ul class="listDetails"><#noescape>${(product.travelTips['OTHER'])!}</#noescape></ul></div>
                                    </div>

                                </li>
                            </ul>
                        </li>
                        <#list 1..(product.tripDays)! as index>
                        <#assign trip = (product.recommendTrips[index])!/>
                    	<li<#if product.tripDays==index> class="last"</#if>>
                            <div class="link" id="D${(index)!}"></div>
                            <span class="commonIcon leftDot"></span>
                            <#if product.tripDays==index><span class="commonIcon lastDot"></span></#if>
                            <span class="leftLine"></span>
                            <h3 class="listTitle">第${(index)!}天<em class="fontSize12">●</em>${(trip.title)!}</h3>
                            <ul class="daysInfo">
                            <#if trip.hotelInfo??>
                                <li class="clearfix">
                                    <div class="daysInfoLeft">
                                        <span class="commonIcon hotelIcon"></span>
                                    </div>
                                    <div class="daysFontsDetails">
                                        <span class="marginRight10">${(trip.hotelInfo.name)!}</span>
                                        <div class="commonStarLevelIcon starLevelIcon starLevel-${(trip.hotelInfo.rate)!}  allStar5">
                                            <span class="commonStarLevelIcon"></span>
                                        </div>
                                    </div>
                                </li>
                                </#if>
                                <li class="clearfix">
                                    <div class="daysInfoLeft">
                                        <span class="commonIcon breakfastIcon"></span>
                                    </div>
                                    <div class="daysFontsDetails">
                                        <span class="marginRight10">早餐: ${(trip.breakfest)!}</span>
                                        <span class="marginRight10">午餐: ${(trip.lunch)!}</span>
                                        <span class="marginRight10">晚餐: ${(trip.dinner)!}</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="daysInfoLeft">
                                        <span class="commonIcon shoesIcon"></span>
                                    </div>
                                    <div class="daysFontsDetails">
                                    	<#noescape><#if trip.content??>${(trip.content)?replace("\n","<br>")}</#if></#noescape>
                                    </div>
                                </li>
                            </ul>
                                <#if trip.desItems??>
                                <#list trip.desItems as item>
	                            <div class="daysImg">
	                            	<#list (item.images)! as img>
		                            <img data-original= "${mediaserver}imageservice?mediaImageId=${img!}" class="lazyload-img">
		                            </#list>
	                            </div>
	                            <p class="daysImgDescCont">
	                            	<#noescape><#if item.describe??>${(item.describe)?replace("\n","<br>")}</#if></#noescape>
	                            </p>
	                            </#list>
	                            </#if>
                        </li>
                        </#list>

                        </ul>
                    </div>
                </div>
            </div>

</#macro>