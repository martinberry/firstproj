<!-- 旅客信息 S -->
	    <div class="order-conBlock" style="border-bottom: none;" id="order-conBlock">
			<#if (productBookInfo.nature)?? && (productBookInfo.nature)!='VISA' >
		        <div class="order-conBlockTitle">
		            <i class="guestIcon"></i><span>旅客信息</span>
		            	<span class="unlogin-tip"  <#if (login)?? && (login)?c == 'false'> style="display;" <#else>style="display:none;"</#if> >（<a class="login-oper-link" href="javascript:;">登录</a>后您可以选择常旅客）</span>
		        </div>
    		</#if>
	    </div>
<!-- 旅客信息 E -->
<script id="passengerTemplate" type="text/x-jquery-tmpl">
	<div class="guestInfo-item">
	<table class="order-conBlockTab order-guestTable">
	    <colgroup>
	        <col width="70">
	        <col width="110">
	        <col width="645">
	    </colgroup>
	    <tbody>
	    <tr>
	        <td rowspan="9" class="leftTd">
	            <div>{{= indexNum}}</div>
	        </td>
	        <th class="adjustpadding">旅客姓名：</th>
	        <td class="adjustpadding">
	         <div class="guestContainer">
                <input type="text" class="firstName" data-cv="required,chOrEnName" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" data-cp="top" style="width: 140px;" placeholder="姓" maxlength="20">
	            <input type="text" class="lastName" data-cv="required,chOrEnName" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" data-cp="top" style="width: 140px;" placeholder="名" maxlength="20">
	            {{if login == 'true'}}
	            	<span class="guestNameIcon" style="display;"></span>
	            {{else}}
	             	<span class="guestNameIcon" style="display:none;"></span>
	            {{/if}}
                <ul class="guestNameList" style="width:284px;">
                </ul>
            </div>
	        </td>
	    </tr>

	     <tr class="trans">
	        <th>英文/拼音：</th>
	        <td>
	            <div class="guestContainer">
		            <input type="text" class="transName firstNameEn" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" data-cv="required,enName" data-cp="bottom" style="width: 140px;" placeholder="姓(拼音或英文)" maxlength="30">
		            <input type="text" class="transName lastNameEn" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" data-cv="required,enName" data-cp="bottom" style="width: 140px;" placeholder="名(拼音或英文)" maxlength="30">
		            <span class="inputTip">为确保出行，请仔细核对拼音名</span>
	            </div>
	        </td>
	    </tr>

	    <tr>
	        <th>旅客类型：</th>
	        <td>
	        	{{if passengerType == 'adult'}}
	        		<span class="passengerType" data-val="ADULT">成人</span>
	        	{{else}}
	        		<span class="passengerType" data-val="CHILD">儿童</span>
	        	{{/if}}
	        </td>
	    </tr>
	    <tr>
	        <th>证件类型：</th>
	        <td>
	            <div class="dropdown credentialType" style="width: 105px;">
	                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
	                  {{if productType != 'domestic'}}
	                    <span class="activeFonts">护照</span>
	                  {{else}}
	                  	<span class="activeFonts">身份证</span>
	                  {{/if}}
	                    <span class="caret"></span>
	                </a>
	                <ul class="dropdown-menu">
	                {{if productType == 'domestic'}}
	                	<li class="active idcard" data-val="IDCARD" data-number="" name="IDCARD" data-dead=""><a href="javascript:void(0);">身份证</a></li>
	                	<li name="PASSPORT" class="passport" data-val="PASSPORT" data-number="" data-dead=""><a href="javascript:void(0);">护照</a></li>
	                 {{else}}
	                    <li class="active passport" name="PASSPORT" data-val="PASSPORT" data-number="" data-dead=""><a href="javascript:void(0);">护照</a></li>
	                 {{/if}}
	                    <li data-val="GANGAOPASS" class="gangaopass" name="GANGAOPASS" data-number="" data-dead=""><a href="javascript:void(0);">港澳通行证</a></li>
	                </ul>
	            </div>
	            {{if productType == 'domestic'}}
	            	<input type="text"data-cv="required,idCard" class="credentialNum" style="width: 270px;" data-cp="right" placeholder="证件号">
	            {{else}}
	            	<input type="text"data-cv="required,passPort" class="credentialNum" style="width: 270px;" data-cp="right" placeholder="证件号">
	            {{/if}}
	        </td>
	    </tr>
	    {{if productType != 'domestic'}}
	    <tr>
	        <th>证件有效期：</th>
	        <td>
	            <input type="text" class="datepicker hasIcon default credentialsDeadLine" readonly="readonly" placeholder="yy-mm-dd" data-cv="required" style="width: 280px;" >
	        </td>
	    </tr>
	    <tr>
	        <th>旅客国籍：</th>
	        <td>
	            <input type="text" id="nationality_{{= indexNum-1}}" class="country" placeholder="中国" value="中国" data-cv="required" style="width: 280px;" data-cp="right"/>
	        	<input type="hidden" id="nationalityDropList_{{= indexNum-1}}" class="nationalityDropList" value="中国">
	        	<div class="verifyStyle" style="display:none">
                    <i class="commonIcon errorIcon"></i><span id='nationality_errorHintAdd_{{= indexNum-1}}' class="errorHint"><span>
                </div>
	        </td>
	    </tr>
	    {{/if}}
	    <tr>
	        <th>出生日期：</th>
	        <td>
	            <input type="text" class="datepicker hasIcon default birthday" readonly="readonly" placeholder="yy-mm-dd" data-cv="required" style="width: 280px;">
	        </td>
	    </tr>
	    <tr>
	        <th>旅客性别：</th>
	        <td>
	            <div class="radioContent gender">
	                <label class="radio active" data-val="MALE">
	                    <span class="commonIcon radioIcon"></span>男
	                </label>
	                <label class="radio" data-val="FEMALE">
	                    <span class="commonIcon radioIcon"></span>女
	                </label>
	            </div>
	        </td>
	    </tr>
	    <tr>
	        <td></td>
	        <td>
	            <div class="checkboxContent savePassenger">
	                <label class="checkbox active">
	                    <span class="commonIcon checkboxIcon"></span>保存为常用旅客
	                </label>
	            </div>
	        </td>
	    </tr>
	    </tbody>
	</table>
	</div>
</script>