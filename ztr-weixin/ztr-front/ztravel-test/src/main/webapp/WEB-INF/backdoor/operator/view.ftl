
<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="后台管理" title="管理员">


	<div class="main-container">
	
		<form id="addOpeartor" method="POST" action="../opera/add">
            <table class="detailsTab">
                <colgroup>
                    <col width="12%">
                    <col width="88%">
                </colgroup>
                <tbody>
                <tr>
                    <td>用户名：</td>
                    <td><input type="text" name="userName" /></td>
                </tr>
                <tr>
                    <td><span class="adjaceney">密码</span>：</td>
                    <td><input type="text" name="password" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="点击新增" /></td>
                </tr>                
                </tbody>
            </table>
		</form>
	    
	</div>
    
</@html.htmlIndex>