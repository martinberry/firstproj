<#macro headerBar title="" menuType="">
		<div class="header homeHeader clearfix" style="background-color: rgba(70, 209, 190, 0.00784314);">
		        <a class="header-logo" data-role="none" href="javascript:jump2List();">
		            <span class="logo"></span>
		        </a>
		        <#include "navigate.ftl"/>
		        <div class="title hideTitle">${title!'真旅行'}</div>
		</div>
		<div class="localtrip-banner">
	        <div class="swiper-container">
	            <div class="swiper-wrapper">
                    <a class="swiper-slide" href="javascript:void(0);"><img class="introduce" src="${host}/mstatic/images/localImg04.jpg"/></a>
	                <a class="swiper-slide" href="javascript:void(0);"><img class="localImg" src="${host}/mstatic/images/localImg01.jpg"/></a>
	                <a class="swiper-slide" href="javascript:void(0);"><img class="introduce" src="${host}/mstatic/images/localImg02.jpg"/></a>
	                <a class="swiper-slide" href="javascript:void(0);"><img class="introduce" src="${host}/mstatic/images/localImg03.jpg"/></a>
	            </div>
	            <div class="swiper-pagination"></div>
	        </div>
	    </div>
		<div class="trip-navMenu">
		    <div class="ui-grid-b">
		        <div class="ui-block-a">
		            <a class="travelEle travelEll <#if menuType=="TRAVEL">active</#if> ui-link" href="javascript:jump2List();">
		                <span class="travelEle-walk"></span>自由行
		            </a>
		        </div>
		        <div class="ui-block-b">
		            <a class="travelEle <#if menuType=="VISA">active</#if> ui-link" href="javascript:jump2VisaList();">
		                <span class="travelEle-visa"></span>签证
		            </a>
		        </div>
		        <div class="ui-block-c">
		            <a class="travelEle travelElr <#if menuType=="LOCAL">active</#if> ui-link" href="javascript:jump2LocalList();">
		                <span class="travelEle-trip"></span>当地游
                        <i class="disneyIcon"></i>
		            </a>
		        </div>
		    </div>
		</div>
		<script>
			var mySwiper = new Swiper('.swiper-container', {
	            autoplay:1500,//可选选项，自动滑动
	            autoplayDisableOnInteraction : false, //用户操作后不会停止自动切换
	            speed:1000,
	            loop : true,
	            pagination: '.swiper-pagination'
	        });
		    function jump2List(){
		        window.location.href = wxServer + "/weixin/product/list" ;
		    }
		    function jump2LocalList(){
		        window.location.href = wxServer + "/weixin/product/local/list" ;
		    }
		    function jump2VisaList(){
		        window.location.href = wxServer + "/weixin/product/visa/list" ;
		    }
		</script>
</#macro>