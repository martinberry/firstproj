<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["product/back/hotel/editHotel.js", "product/back/hotel/editHotelValidate.js", "product/back/hotel/upload.js", "product/back/hotel/jquery-form.js"]
cssFiles=["css/maintain/productManagement.css"] curModule="产品管理" title="真旅行编辑酒店">

<header class="main-header hoveringModel">
            <div class="stairMenuContent">
                <div class="wrap clearfix">
                    <div class="navLeft">
                        <a class="logo" href="/"></a>
                        <ul class="stairMenu">
                            <li class="editFonts">酒店编辑</li>
                        </ul>
                    </div>
                    <#include "/common/opera/header_right.ftl" />
                </div>
            </div>
            <div class="headSecondLevel headBtn clearfix">
                <span class="leftBtn">
                    <a href="javascript:void(0);" class="returnBtn">< 返回</a>
                </span>
                <span class="rightBtn">
                    <button <#if hotel.isComplete?? && hotel.isComplete==true>class="commonButton red-fe6869Button disabled" disabled="true"
                    <#else>class="commonButton red-fe6869Button"</#if>>保 存</button>
                    <button class="commonButton blue-45c8dcButton complete">完 成</button>
                    <button class="commonButton lightBlue-b6d7e7Btn">取 消</button>
                </span>
            </div>
        </header>

        <div id="ac-hangUp" class="modal fade" style="display: none;" aria-hidden="true">
                    <div style="width: 500px; height: 185px; margin-top: 249.5px;" class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button aria-label="Close" data-dismiss="modal" class="close" type="button">
                                    <span aria-hidden="true">×</span>
                                </button>
                                <h4 class="modal-title">操作提示</h4>
                            </div>
                            <div class="modal-body">
                                <div class="popupContainer">
                                    <i class="warnIcon"></i>
                                        <#if hotel.isComplete?? && hotel.isComplete==true><span class="popupContainer-fonts">是否完成酒店信息？</span>
                                        <#else><span class="popupContainer-fonts">是否保存酒店信息？</span></#if>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <#if hotel.isComplete?? && hotel.isComplete==true><button type="button" class="commonButton blue-45c8dcButton complete">完 成</button>
                                <#else><button type="button" class="commonButton red-fe6869Button">保存</button></#if>
                                <button type="button" id="no" class="commonButton lightBlue-b6d7e7Btn">取消</button>
                            </div>
                        </div>
                    </div>
                </div>

        <div class="main-container changeMainContent">
            <section class="whiteBg">
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="110">
                        <col width="350">
                        <col width="100">
                        <col width="">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th></th>
                        <td>
                        <div style="width: 500px; color: #ff4e00; display: none;" class="error_edit_hotel"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>中文名称</th>
                        <td colspan="3">
                            <input type="text" id="hotelNameCn" style="width: 417px;" maxlength="60" value="${hotel.hotelNameCn!}">
                            <span class="replaceTh">酒店电话</span>
                            <input type="text" id="phone" style="width: 180px;" value="${hotel.phone!""}" placeholder="区号-电话">
                        </td>
                    </tr>
                    <tr>
                        <th>英文名称</th>
                        <td colspan="3">
                            <input type="text" id="hotelNameEn" style="width: 717px;" maxlength="60" value="${hotel.hotelNameEn!}">
                        </td>
                    </tr>
                    <tr>
                        <th>目 的 地</th>
                        <td>
                            <div class="dropdown" style="width: 110px;" id="continent-menu">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                    <span id="continent" class="activeFonts">${hotel.continent!""}</span>
                                    <span class="caret"></span>
                                </a>
                                    <#if continentList??>
                                    <ul class="dropdown-menu">
                                            <#list continentList as continent>
                                            <#if continent??><li><a href="javascript:void(0);">${continent}</a></li></#if>
                                            </#list>
                                            </#if>
                            </div>
                            <div class="dropdown" style="width: 110px;" id="nation-menu">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                    <span id="nation" class="activeFonts">${hotel.nation!""}</span>
                                    <span class="caret"></span>
                                </a>
                                <#if nationList??>
                                <ul class="dropdown-menu">
                                     <#list nationList as nation>
                                     <#if nation??><li><a href="javascript:void(0);">${nation}</a></li></#if>
                                     </#list>
                                </ul>
                                </#if>
                            </div>
                            <div class="dropdown" style="width: 110px;" id="city-menu">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                    <span id="city" class="activeFonts">${hotel.city!""}</span>
                                    <span class="caret"></span>
                                </a>
                                <#if cityList??>
                                <ul class="dropdown-menu">
                                          <#list cityList as city>
                                          <#if city??><li><a href="javascript:void(0);">${city}</a></li></#if>
                                          </#list>
                                </ul>
                                </#if>
                            </div>
                        </td>
                        <th>酒店坐标</th>
                        <td>
                            <input type="text" id="lon" style="width: 130px;" maxlength="7" value="${hotel.lon!""}" placeholder="经度">
                            <input type="text" id="lat" style="width: 132px;" maxlength="7" value="${hotel.lat!""}" placeholder="纬度">
                            <a href="javascript:void(0);" class="mapLook">地图查看</a>
                        </td>
                    </tr>
                    <tr>
                        <th>酒店地址</th>
                        <td colspan="3">
                            <input type="text" id="address" style="width: 717px;" maxlength="60" value="${hotel.address!}">
                        </td>
                    </tr>
                    <tr>
                        <th>酒店类型</th>
                        <td>
                            <div class="dropdown" style="width: 110px;">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                    <span class="type activeFonts"></span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu" id="type">
                                <#if typeMap??>
                                <#list typeMap?keys as typekey>
                                <li <#if typekey==hotel.type!"TYPE_1"> class="active"</#if> typeenum="${typekey}">
                                <a href="javascript:void(0);">${typeMap[typekey]}</a></li>
                                </#list>
                                </#if>
                                </ul>
                            </div>
                            <span class="replaceTh">酒店星级</span>
                            <div class="dropdown" style="width: 110px;">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                    <span class="rating activeFonts"></span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu" id="rating">
                                <#if rateMap??>
                                <#list rateMap?keys as ratekey>
                                    <li <#if ratekey==hotel.rating!"SUPER_FIVE_STAR"> class="active"</#if> ratingenum="${ratekey}">
                                    <a href="javascript:void(0);">${rateMap[ratekey]}</a></li>
                                </#list>
                                </#if>
                                </ul>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>卖  点</th>
                        <td colspan="3">
                            <input type="text" id="advantage" style="width: 717px;" maxlength="100" value="${hotel.advantage!}">
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop">酒店描述</th>
                        <td colspan="3">
                        <textarea id="describe" maxlength="500" class="hotelDescribe">${hotel.describe!}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop paddingTop10">酒店图片</th>
                        <td colspan="3">
                            <div>至少5张高清（XXX*XXX）JPG/PNG图</div>
                            <ul class="hotelImgList clearfix">
                            <#if hotel.pictureIds??>
                            <#list hotel.pictureIds as pictureId>
                            <li>
                                    <img src="${mediaserver}imageservice?mediaImageId=${pictureId!}" draggable="true">
                                    <input name="pictureId" value="${pictureId!}" type="hidden">
                                    <div class="move moveLeft">左移</div>
                                    <div class="move moveRight">右移</div>
                                    <div class="move hoverDelete"><i class="delIcon"></i>删除</div>
                            </li>
                            </#list>
                            </#if>
                                <li>
                                <form id="form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                    <div><input type="file" id="file" name="picture" class="upLoading"></div>
                                    </form>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop">酒店设施</th>
                        <td colspan="3">
                            <ul class="facilityList clearfix">
                                <li class="active">房间设施</li>
                                <li>餐饮设施</li>
                                <li>网络设施</li>
                                <li>酒店设施</li>
                                <li>服务项目</li>
                            </ul>
                            <div class="facilityContent">
                                <textarea id="compFacilities" maxlength="500" class="facilityTextarea">${hotel.compFacilities!}</textarea>
                                <textarea id="cateringFacilities" maxlength="500" class="facilityTextarea" style="display: none;">${hotel.cateringFacilities!}</textarea>
                                <textarea id="networkFacilities" maxlength="500" class="facilityTextarea" style="display: none;">${hotel.networkFacilities!}</textarea>
                                <textarea id="activityFacilities" maxlength="500" class="facilityTextarea" style="display: none;">${hotel.activityFacilities!}</textarea>
                                <textarea id="serviceFacilities" maxlength="500" class="facilityTextarea" style="display: none;">${hotel.serviceFacilities!}</textarea>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop">入住须知</th>
                        <td colspan="3">
                        <textarea id="notes" maxlength="500" class="noticeTextarea">${hotel.notes!}</textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </section>

        </div>

	<input name="id" type="hidden" value="${hotel.id!''}"/>


    <script>
        $(function(){

            $(".facilityList li").each(function(index){
                $(this).click(function(){
                    $(".facilityContent .facilityTextarea").hide();
                    $(".facilityContent .facilityTextarea:eq(" + index + ")").show();
                    $(".facilityList li").removeClass("active");
                    $(this).addClass("active");
                });

            });
        });
    </script>

</@html.htmlIndex>