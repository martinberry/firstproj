  <#if searchList??>
    <#list searchList as item>
	    <tr>
	        <td>易谦</td>
	        <td>代金券</td>
	        <td>2500.00</td>
	        <td><span class="fcf78c68">2000</span></td>
	        <td>500</td>
	        <td>否</td>
	        <td><a href="javascript:void(0);" class="greenFontsLink">冻结</a></td>
	    </tr>
    </#list>
  </#if>
   <#if size==0>
<p style="color: #F71A3D;" class="no_data">暂无数据</p>
</#if>

