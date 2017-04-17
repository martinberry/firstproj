
<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="后台管理" title="注册白名单">


	<div class="main-container">
	
		<form id="addOpeartor" method="POST" action="../regWhiteList/edit">
            <table class="detailsTab">
                <colgroup>
                    <col width="12%">
                    <col width="88%">
                </colgroup>
                <tbody>
                <tr>
                    <td>手机号(逗号隔开)</td>
                    <td><input type="text" name="mobiles" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="点击新增" /></td>
                </tr>                
                </tbody>
            </table>
		</form>
	    
	</div>
    
</@html.htmlIndex>