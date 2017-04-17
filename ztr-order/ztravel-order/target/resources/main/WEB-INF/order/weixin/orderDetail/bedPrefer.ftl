 <div class="hotel-bed">偏好的酒店床型：<label class="hotel-bed-type"><span id="bed-type"><#if (bedPrefer)?? &&  (bedPrefer) == "big">大床房<#else>双床房</#if></span><#if canEdit?? && canEdit?c == "true"><span class="edt-icon"></span></#if></label></div>
				<div class="hotel-bed-op">
					 <div class="ui-grid-b">
					 <div class="ui-block-a">
					 <label class="choice-bed">
					 <input type="radio" name="bed" value="big" class="bigbed" data-role="none" <#if (bedPrefer)?? &&  (bedPrefer) == "big">checked</#if>>
					 <span>大床房</span>
					 </label>
				 </div>
				 <div class="ui-block-b">
					 <label class="choice-bed">
					 <input type="radio" name="bed" class="doublebed" value="double" data-role="none" <#if (bedPrefer)?? &&  (bedPrefer) == "double">checked</#if> >
					 <span>双床房</span>
					 </label>
				 </div>
				 <div class="ui-block-c">
					 <span class="span-confirm">确定</span><span class="span-cancel">取消</span>
				 </div>
				 <#if isBedTips?? && isBedTips?c == "true">
				 	<div class="hotelbed-tip" id="bedTips" style="display:none;">
                            由于日本酒店的特殊政策，双床房价格需补差价。下单后客服人员会联系您补拍差价。
                    </div>
				 </#if>
			 </div>
	</div>