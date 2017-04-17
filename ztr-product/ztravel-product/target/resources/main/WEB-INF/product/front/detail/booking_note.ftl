<#macro bookingNote>

                <div class="commonModel">
                    <div id="bookingNote" class="anchor"></div>
                    <div class="commonTitle"><i class="commonIcon bookingIcon"></i>预定须知</div>
                    <ul class="wrapperList clearfix noBlueBorder">
                        <li class="row oneRow">
                            <h3 class="titleFonts">预定须知</h3>
                            <ul class="listDetails">
                               <#noescape>${(product.additionalInfos['BOOKING'])!}</#noescape>
                            </ul>
                        </li>
                        <li class="row oneRow">
                            <h3 class="titleFonts">签证须知</h3>
                            <ul class="listDetails">
                               <#noescape>${(product.additionalInfos['VISA'])!}</#noescape>
                            </ul>
                        </li>
                    </ul>
                </div>

</#macro>