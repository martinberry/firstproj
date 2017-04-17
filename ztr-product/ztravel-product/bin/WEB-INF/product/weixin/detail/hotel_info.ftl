<#macro hotelInfo>
<#if (product.hotels)??>
	<div id="hotelPoint"></div>
    <section class="hotelModule">
		<div class="hotelModule-head">
          <h2 class="commonTitleH2 haveIcon"><i class="hotelIcon"></i>酒店信息</h2>
        </div>
        <#list (product.hotels)! as hotel>
        <div class="hotel-item">
        	<h3 class="hotelTitleH3">${(hotel.name)!}</h3>
            <div class="starsContent">
                <label class="judge-stars">
                <#if (hotel.rate)??>
                    <#list 1..((hotel.rate)?substring(0, 1))?number as count>
                        <span class="yellow-star"></span>
                    </#list>
                    <#if (hotel.rate)?length gt 1>
                        <span class="half-yellow-star"></span>
                    </#if>
                </#if>
                </label>
                <#if (hotel.checkinDaysStr)?? && hotel.checkinDaysStr =="全程">
                    <span class="checkInFonts"><span>${(hotel.checkinDaysStr)!}</span>
                <#else>
                	<span class="checkInFonts">入住第<span>${(hotel.checkinDaysStr)!}</span>晚
                </#if>
                </span>
            </div>
            <div class="swiper-container swiper-container-horizontal" id="swiper-container0${(hotel_index + 1)!}">
                <div class="swiper-wrapper">
                    <#list (hotel.pictureIds)! as pictureId>
                        <div class="swiper-slide<#if pictureId_index == 0> swiper-slide-active<#elseif pictureId_index == 1> swiper-slide-next</#if>" style="margin-right: 5px;"><img class="slide-img lazyload-img" data-original="${mediaserver}imageservice?mediaImageId=s1${pictureId!}"></div>
                    </#list>
                </div>
                <!-- Add Pagination -->
                <div class="swiper-pagination swiper-pagination-clickable"><span class="swiper-pagination-bullet swiper-pagination-bullet-active"></span></div>
            </div>
            <script>
                 var imgWidth = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                 new Swiper('#swiper-container0${(hotel_index + 1)!}', {
                    pagination: '#swiper-container0${(hotel_index + 1)!} > .swiper-pagination',
                    paginationClickable: true,
                    spaceBetween: 5,
                    width: imgWidth
                });
            </script>
            <div class="hotelDetails">
                <pre>${(hotel.highLights)!}</pre>
            </div>
            <ul class="hotelClassify">
                <li class="clearfix">
                    <span class="a1a1a1Fonts">酒店类型: </span>
                    <span class="classifyContent">${(hotel.hoteltype)!}</span>
                </li>
                <li class="clearfix">
                    <span class="a1a1a1Fonts">房间类型: </span>
                    <span class="classifyContent">${(hotel.roomType)!}</span>
                </li>
                <li class="clearfix">
                    <span class="a1a1a1Fonts">酒店位置: </span>
                    <span class="classifyContent">${(hotel.address)!}</span>
                </li>
                <li class="clearfix">
                    <span class="a1a1a1Fonts">酒店电话: </span>
                    <span class="classifyContent">${(hotel.phone)!}</span>
                </li>
            </ul>
            <div class="hotelOp">
				<div class="seeDefault" style="display: none;">
                        <div class="costDetails separateLine">
                            <h3 class="costTitle">入住须知</h3>
                            <h3 class="costHead"><pre>${(hotel.highLights)!}</pre></h3>
                        </div>
                        <div class="costDetails separateLine">
                            <h3 class="costTitle">酒店设施</h3>
                            <div class="hotelTil">房间设施：</div>
                            <h2>${(hotel.compFacilities)!}</h2>
                            <div class="hotelTil">餐饮设施：</div>
                            <h2>${(hotel.cateringFacilities)!}</h2>
                            <div class="hotelTil">网络设施：</div>
                            <h2>${(hotel.networkFacilities)!}</h2>
                            <div class="hotelTil">服务项目：</div>
                            <h2>${(hotel.serviceFacilities)!}</h2>
                        </div>
                    </div>


	            <div class="commonBottomStyle hotelNotice">
	                <h3 class="commonBottomTil">
	                <i class="bAndTArrow btmAndTopArrow"></i>查看入住须知及酒店设施</h3>
	            </div>
				<div class="commonBottomStyle closeHotelNotice" style="display: none;">
                   <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
                </div>

	            
            </div>
        </div>
         <#if (((product.hotels)?size > 1) && (hotel_index + 1 < (product.hotels)?size))>
             <div class="hotel-split-line"></div>
         </#if>
        </#list>
    </section>
</#if>

</#macro>