<!--兑换模态框-->
 <div class="modal fade" data-backdrop="static" data-keyboard="false" id="converting">
		        <div class="modal-dialog" style="width: 812px;height:480px;">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		                    <h4 class="modal-title"/>
		                    <div>${(dhDetail.dhId)!}</div>
                    </div>
                <div class="modal-body">
                    <div class="popupContainer">
               <table class="exchangeTable">
                            <colgroup>
                                <col width="85">
                                <col width="140">
                                <col width="105">
                                <col width="150">
                                <col width="125">
                                <col width="80">
                                <col width="85">
                                <col width="130">
                            </colgroup>
                            <tbody>
                            <tr>
		                <th>会员昵称</th>
		                 <td class="nickName">${(dhDetail.membername)!}</td>
		                 <th>兑换手机号</th>
		                <td class="mobile">${(dhDetail.dhphonenumber)!}</td>
		                 <th>本次兑换金额</th>
		                   <td class="orangefa7f1f">${(dhDetail.dhmoney)!}</td>
		                   <th>兑换内容</th>
		               <td class="content"> ${(dhDetail.contentDesc)!}</td>
		                   </tr>
                            </tbody>
                        </table>
                        <table class="exchangeTime">
                            <colgroup>
                                <col width="110">
                                <col width="220">
                                <col width="110">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                               <th>兑换申请时间</th>
                           <td class="pleDate">${(dhDetail.pledhtime)!}</td>
                                <th>确认兑换时间</th>
                             <td class="confirmDate"> ${(dhDetail.confdhtime)!}</td>
                            </tr>
                            </tbody>
                        </table>
                </div>
                </div>
		                <div class="modal-footer">
		                    <button type="button" class="commonButton red-fe6869Button" style="margin-right:10px;" id="confirmConvert" data-id="">确认兑换</button>
							<button type="button" class="commonButton blue-45c8dcButton"data-dismiss="modal" id="donotConvert">忽略请求</button>
		                </div>
		            </div>
		        </div>
		    </div>

	  <input type="hidden" name="detail" value="${dhDetail!}" />
<!--查看模态框-->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="haveconverted">
		        <div class="modal-dialog" style="width: 812px;height:480px;">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		          	 	<h4 class="modal-title"/>
               		<div>${(dhDetail.dhId)!}</div>
              </div>
              <div class="modal-body">
                    <div class="popupContainer">
                        <table class="exchangeTable">
                            <colgroup>
                                <col width="85">
                                <col width="140">
                                <col width="105">
                                <col width="150">
                                <col width="125">
                                <col width="80">
                                <col width="85">
                                <col width="130">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>会员昵称</th>
                                <td class="nickName">${(dhDetail.membername)!}</td>
                                <th>兑换手机号</th>
                                <td class="mobile">${(dhDetail.dhphonenumber)!}</td>
                                <th>本次兑换金额</th>
                                <td class="orangefa7f1f">${(dhDetail.dhmoney)!}</td>
                                <th >兑换内容</th>
                                 <td class="content"> ${(dhDetail.contentDesc)!}</td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="exchangeTime">
                            <colgroup>
                                <col width="110">
                                <col width="220">
                                <col width="110">
                                <col width="220">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>兑换申请时间</th>
                           <td class="pleDate">  ${(dhDetail.pledhtime)!}</td>
                                <th>确认兑换时间</th>
                             <td class="confirmDate"> ${(dhDetail.confdhtime)!}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            <div class="modal-footer">
		                    <button type="button"  class="commonButton blue-45c8dcButton"  data-dismiss="modal" id="cancelConvert">关闭</button>
		                </div>
		            </div>
		        </div>
		    </div>

