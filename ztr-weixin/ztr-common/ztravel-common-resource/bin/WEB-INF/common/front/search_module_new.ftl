<div class="indexSearchBar">
     <div class="searchModel index_searchModel">
     <div class="searchContent">
     	<table class="searchTab">
             <colgroup>
                 <col width="98">
                  <col width="142">
                  <col width="100">
                  <col width="424">
                  <col width="">
             </colgroup>
             <tbody>
             <tr>
                 <th class="addIcon"><span class="commonIcon startOffIcon"></span><span class="startPlace-title">出发地:</span></th>
                 <td>
                     <div class="startOffPlaceModel">
                         <div class="startOffPlace">
                             <input type="text" class="startOffInput" id="departure" value="${(smv.departPlace)!''}" style="width: 141px;" readonly="readonly">
                             <span class="caret"></span>
                         </div>
                         <ul class="selectStartOffPlace">
                             <#if smv.departurePlaceList??>
                             <#list smv.departurePlaceList as place>
                             <li <#if ((smv.departPlace)!'')==(place!'')>class="active"</#if>>${place!''}</li>
                             </#list>
                             </#if>
                         </ul>
                     </div>
                 </td>
                 <th class="addIcon"><span class="commonIcon destinationIcon"></span><span class="destination-title">目的地:</span></th>
                 <td>
                     <div class="destinationModel">
                         <div class="destination">
                             <!-- <span class="commonIcon destinationIcon"></span> -->
                             <input type="text" class="destinationInput" id="destination" value="${(smv.defaultDestination)!''}" style="width:466px;" placeholder="世界" readonly="readonly">
                             <input id="selectedDestLevel" type="hidden" value="${(smv.destinationLevel)!'1'}" />
                             <span class="caret"></span>
                             <!-- <input id="caret selectedDestLevel" type="hidden"/> -->
                         </div>
                         <div class="selectDestination">
                             <ul class="cityList ul-dest-first-level">
                                 <#if smv.destinations??>
                                 <#list smv.destinations as dest>
                                 <li class="destSelect-first-level">
                                     <span class="fl-destName">${(dest.destName)!''}</span>
                                     <span class="nav-tree-arrow"></span>
                                     <ul class="second-level">
                                     <#if (dest.subDestinations)??>
                                     <#list dest.subDestinations as secLevelDest>
                                         <li class="sl-destName">${secLevelDest!''}</li>
                                     </#list>
                                     </#if>
                                     </ul>
                                 </li>
                                 </#list>
                                 </#if>
                                 <li class="empty"></li>
                             </ul>
                         </div>
                     </div>
                 </td>
                 <td class="searchbtn">
                     <a href="javascript:void(0);" id="searchBtn"></a>
                 </td>
             </tr>
             </tbody>
         </table>
     </div>
  </div>
</div>
