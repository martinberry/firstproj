<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/pagination.js","common/typeahead.js","operator/financeMantain/supplier.js"] cssFiles=["css/maintain/finance.css","css/bootstrap-datepicker.min.css"]
curModule="财务&供应商系统" title="供应商信息">
  <@main_header.header currentMenu="财务&供应商系统" currentSubMenu="供应商信息">
            <div class="operationDiv"><i class="plusIcon"></i>新增供应商</div>
	</@main_header.header>
    <div class="main-container">
        <section class="whiteBg">
          <form id="search-form">
            <ul class="searchList clearfix">

                  <input type="hidden" name="pageNo" value="1" />
				<input type="hidden" name="pageSize" value="10" />
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="95">
                            <col width="330">
                        </colgroup>
                        <tbody>
                        <tr >
                            <th>供应商名称</th>
                            <td>
                                <input type="text" id="supplierName" name="supplierName" autocomplete="off" />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="103">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td style="height:46px;">
                                <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>查 询</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </li>
            </ul>
             </form>
            <div class="outTab"><a href="javascript:void(0);" id="exportExcel">导出Excel</a></div>
            <section class="clearfix">
                <table class="commonTab productTab">
                    <colgroup>
                        <col width="30%">
                        <col width="15%">
                        <col width="12%">
                        <col width="12%">
                        <col width="18%">
                        <col width="12%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>供应商名称</th>
                        <th>业务联系人</th>
                        <th>电话</th>
                        <th>传真</th>
                        <th>电邮</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                    <div id="searchField">
                   </div>
                </section>
        </section>
    </div>
</div>
<!--新增供应商-->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="addSupply">
    <div class="modal-dialog" style="width:812px;height:480px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增供应商信息</h4>
            </div>
            <div class="modal-body">
               <form id="addForm" method="POST" action= "${basepath}/financeMantain/opera/supplier/add">
                <div class="popupContainer">
                    <table class="newVoucherTable">
                        <colgroup>
                            <col width="100">
                            <col width="130">
                            <col width="150">
                            <col width="130">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><em class="redStar">*</em>供应商名称</th>
                            <td><input type="text" id="supplierNameAdd" name = "supplierName" maxlength="100">
                                  <div class="verifyStyle" style="display:none" id="supplierNameVerify">
		                                <i class="commonIcon errorIcon"></i><span id='supplierName_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                            <th><em class="redStar">*</em>业务联系人</th>
                            <td><input type="text" id="bussinessContacts" name="bussinessContacts" maxlength="50">
                            	<div class="verifyStyle" style="display:none" id="bussinessContactsVerify">
		                                <i class="commonIcon errorIcon"></i><span id='bussinessContacts_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                        </tr>
                        <tr>
                            <th><em class="redStar">*</em>开户银行</th>
                            <td><input type="text" id="accountBank" name="accountBank" maxlength="100">
                            	<div class="verifyStyle" style="display:none" id="accountBankVerify">
		                                <i class="commonIcon errorIcon"></i><span id='accountBank_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                            <th><em class="redStar">*</em>电&nbsp;&nbsp;&nbsp;&nbsp;话</th>
                            <td><input type="text" id="phone" name="phone" maxlength="30">
                            <div class="verifyStyle" style="display:none" id="phoneVerify">
		                                <i class="commonIcon errorIcon"></i><span id='phone_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                        </tr>

                        <tr>
                            <th><em class="redStar">*</em>开&nbsp;&nbsp;户&nbsp;&nbsp;名</th>
                            <td><input type="text" id="accountName" name="accountName" maxlength="100">
                             <div class="verifyStyle" style="display:none" id="accountNameVerify">
		                                <i class="commonIcon errorIcon"></i><span id='accountName_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                            <th>传&nbsp;&nbsp;&nbsp;&nbsp;真</th>
                            <td><input type="text" id="fax" name="fax">
                                <div class="verifyStyle" style="display:none" id="faxVerify" maxlength="50">
		                                <i class="commonIcon errorIcon"></i><span id='fax_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                        </tr>
                        <tr>
                            <th><em class="redStar">*</em>账&nbsp;&nbsp;&nbsp;&nbsp;户</th>
                            <td><input type="text" id="account" name="account" maxlength="50">
                             <div class="verifyStyle" style="display:none" id="accountVerify">
		                                <i class="commonIcon errorIcon"></i><span id='account_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                            <th>电&nbsp;&nbsp;&nbsp;&nbsp;邮</th>
                            <td><input type="text" id= "email" name="email" maxlength="50">
                             <div class="verifyStyle" style="display:none" id="emailVerify">
		                                <i class="commonIcon errorIcon"></i><span id='email_errorHintAdd' class="errorHint"><span>
		                          </div>
                            </td>
                        </tr>
                        <tr>
                            <th>结算周期</th>
                            <td>
                                <span class="dropdown" style="width: 230px;">
                                 <input type = "hidden" name ="settlementPeriod" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id="settlementPeriod" name="settlementPeriod">周期选择</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu" id="secMenuAdd">
                                    	<li><a href="javascript:void(0);">周期选择</a></li>
                                        <li class="active"><a href="javascript:void(0);">一单一结</a></li>
                                        <li><a href="javascript:void(0);">周结</a></li>
                                        <li><a href="javascript:void(0);">半月结</a></li>
                                        <li><a href="javascript:void(0);">月结</a></li>
                                    </ul>
                                </span>
                            </td>
                            <th class="verticalAlign">内部联系人</th>
                            <td><input type="text" id="innerContacts" name="innerContacts" maxlength="100"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="commonButton red-fe6869Button" style="margin-right:10px;" id ="addConfirm">确认</button>
                <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal" id="addCancel">取消</button>
            </div>
            </form>
        </div>
    </div>
</div>

<!--编辑供应商信息-->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="edtSupply">
    <div class="modal-dialog" style="width:812px;height:480px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑供应商信息</h4>
            </div>
            <div class="modal-body">
            <form id="editForm" method="POST" action= "${basepath}/financeMantain/opera/supplier/update">
                <div class="popupContainer">
                    <table class="newVoucherTable">
                        <colgroup>
                            <col width="100">
                            <col width="130">
                            <col width="150">
                            <col width="130">
                        </colgroup>
                        <tbody id="edtTable">
                            <tr>
                             <input type="hidden" id="supplierIdEdit" name="supplierId" >
                                <th><em class="redStar">*</em>供应商名称</th>
                                <input type ="hidden" id="supplierNameOrigin">
                                <td><input type="text" id="supplierNameEdit" name="supplierName" maxlength="100">
                                <div class="verifyStyle" style="display:none" id="supplierNameEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='supplierName_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                                <th><em class="redStar">*</em>业务联系人</th>
                                <td><input type="text" id="bussinessContactsEdit" name="bussinessContacts" maxlength="50">
                                <div class="verifyStyle" style="display:none" id="bussinessContactsEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='bussinessContacts_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                            </tr>
                            <tr>
                                <th><em class="redStar">*</em>开户银行</th>
                                <td><input type="text" id="accountBankEdit" name="accountBank" maxlength="100">
                                <div class="verifyStyle" style="display:none" id="accountBankEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='accountBank_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                                <th><em class="redStar">*</em>电&nbsp;&nbsp;&nbsp;&nbsp;话</th>
                                <td><input type="text" id="phoneEdit" name = "phone" maxlength="30">
                                <div class="verifyStyle" style="display:none" id="phoneEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='phone_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                            </tr>
                            <tr>
                                <th><em class="redStar">*</em>开&nbsp;&nbsp;户&nbsp;&nbsp;名</th>
                                <td><input type="text" id="accountNameEdit" name ="accountName" maxlength="100">
                                  <div class="verifyStyle" style="display:none" id="accountNameEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='accountName_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                                <th>传&nbsp;&nbsp;&nbsp;&nbsp;真</th>
                                <td><input type="text" id="faxEdit" name="fax" maxlength="50">
                                  <div class="verifyStyle" style="display:none" id="faxEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='fax_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                            </tr>
                            <tr>
                                <th><em class="redStar">*</em>账&nbsp;&nbsp;&nbsp;&nbsp;户</th>
                                <td><input type="text" id="accountEdit" name="account" maxlength="50">
                                  <div class="verifyStyle" style="display:none" id="accountEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='account_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                                <th>电&nbsp;&nbsp;&nbsp;&nbsp;邮</th>
                                <td><input type="text" id="emailEdit" name ="email" maxlength="50">
                                <div class="verifyStyle" style="display:none" id="emailEditVerify">
		                                <i class="commonIcon errorIcon"></i><span id='email_errorHintEdit' class="errorHint"><span>
		                          </div>
                                </td>
                            </tr>
                            <tr>
                                <th>结算周期</th>
                                <td>
                                    <span class="dropdown" style="width: 230px;">
                                     <input type = "hidden" name ="settlementPeriod" class="searchFormField">
                                        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                            <span class="activeFonts" id="settlementPeriodEdit" name ="settlementPeriod">周期选择</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" id="secMenu">
                                            <li class="active"><a href="javascript:void(0);">周期选择</a></li>
                                            <li><a href="javascript:void(0);">一单一结</a></li>
                                            <li><a href="javascript:void(0);">周结</a></li>
                                            <li><a href="javascript:void(0);">半月结</a></li>
                                            <li><a href="javascript:void(0);">月结</a></li>
                                        </ul>
                                    </span>
                                </td>
                                <th class="verticalAlign">内部联系人</th>
                                <td><input type="text" id="innerContactsEdit" name="innerContacts"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="commonButton red-fe6869Button" style="margin-right:10px;" id="editConfirm">确认</button>
                <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
            </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(function(){

        $(".modal").modal({
            backdrop: "static",
            keyboard: false,
            show: false
        });


        $(".greenFontsLink").click(function(){
            $("#edtTable tr td input").filter("[type='text']").val("");
            $("#secMenu li").filter(".active").removeClass("active");
            $("#secMenu li").first().addClass("active");
            var defau = $("#secMenu li").first("a").text();
            $("#period").text(defau)

            var supplyName = $(this).parent().siblings("td").first().text();
            var contacter = $(this).parent().siblings("td").eq(1).text();
            var telephone = $(this).parent().siblings("td").eq(2).text();
            var fax = $(this).parent().siblings("td").eq(3).text();
            var email = $(this).parent().siblings("td").last().text();

            $("#supplyName").val(supplyName);
            $("#contacter").val(contacter);
            $("#telephone").val(telephone);
            $("#fax").val(fax);
            $("#email").val(email);

            $("#edtSupply").modal("show");
        });
    });


</script>
</body>
</@html.htmlIndex>