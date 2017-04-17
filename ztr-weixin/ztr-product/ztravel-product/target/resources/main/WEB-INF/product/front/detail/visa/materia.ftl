			<div class="commonModel">
                 <div id="visaMaterial"></div>
                 <div class="commonTitle"><i class="visaIcon materialIcon"></i>签证材料</div>
                 <div class="materialModel">
                 	<#if (product.materias)??>
	                 	<#assign materies = product.materias>
	             		<ul class="nav jobGroup clearfix">
	                        <li class="active">
	                            <a class="" href="javascript:void(0);">在职</a>
	                        </li>
	                        <li>
	                            <a class="" href="javascript:void(0);">自由职业</a>
	                        </li>
	                        <li>
	                            <a class="" href="javascript:void(0);">在校学生</a>
	                        </li>
	                        <li>
	                            <a class="" href="javascript:void(0);">退休</a>
	                        </li>
	                        <li>
	                            <a class="" href="javascript:void(0);">学龄前儿童</a>
	                        </li>
	                    </ul>
	                    
	                    <div class="jobElement">
		                    <#if materies['EMPLOYED']??>
		            			<#assign mases=materies['EMPLOYED']>
		            			<#if mases?? && mases?size gt 0>
				                     <#list mases as mas>
				                         <div class="jobElement-head">
				                             <strong>
				                             	${(mas.title)!''}
				                             </strong>
				                             <#if mas.images?? && mas.images?size gt 0>
				                             	<#list mas.images as image>	
					                             <a class="lookImg-link post-link" href="javascript:void(0);" data-image="${image!}">查看样图</a>
					                             </#list>
				                             </#if>
				                         </div>
				                         <div class="jobElement-content">
				                             	<#noescape>${(mas.content)!''}</#noescape>
				                         </div>
				                     </#list>
			                     </#if>
	                    	</#if>
                        </div>
                    	
                    	<div class="jobElement hidden">
	                    	<#if materies['FREE']??>
		            			<#assign mases=materies['FREE']>
		            			<#if mases?? && mases?size gt 0>
				                     <#list mases as mas>
				                         <div class="jobElement-head">
				                             <strong>
				                             	${(mas.title)!''}
				                             </strong>
				                             <#if mas.images?? && mas.images?size gt 0>
				                             	<#list mas.images as image>	
					                             <a class="lookImg-link post-link" href="javascript:void(0);" data-image="${image!}">查看样图</a>
					                             </#list>
				                             </#if>
				                         </div>
				                         <div class="jobElement-content">
				                             	<#noescape>${(mas.content)!''}</#noescape>
				                         </div>
				                     </#list>
			                     </#if>
	                    	</#if>
                        </div>
                    	
                    	<div class="jobElement hidden">
	                    	<#if materies['STUDENT']??>
		            			<#assign mases=materies['STUDENT']>
		            			<#if mases?? && mases?size gt 0>
				                     <#list mases as mas>
				                         <div class="jobElement-head">
				                             <strong>
				                             	${(mas.title)!''}
				                             </strong>
				                             <#if mas.images?? && mas.images?size gt 0>
				                             	<#list mas.images as image>	
					                             <a class="lookImg-link post-link" href="javascript:void(0);" data-image="${image!}">查看样图</a>
					                             </#list>
				                             </#if>
				                         </div>
				                         <div class="jobElement-content">
				                             	<#noescape>${(mas.content)!''}</#noescape>
				                         </div>
				                     </#list>
			                     </#if>
	                    	</#if>
                        </div>
                    	
                    	<div class="jobElement hidden">
	                    	<#if materies['RETIRE']??>
		            			<#assign mases=materies['RETIRE']>
		            			<#if mases?? && mases?size gt 0>
				                     <#list mases as mas>
				                         <div class="jobElement-head">
				                             <strong>
				                             	${(mas.title)!''}
				                             </strong>
				                             <#if mas.images?? && mas.images?size gt 0>
				                             	<#list mas.images as image>	
					                             <a class="lookImg-link post-link" href="javascript:void(0);" data-image="${image!}">查看样图</a>
					                             </#list>
				                             </#if>
				                         </div>
				                         <div class="jobElement-content">
				                             	<#noescape>${(mas.content)!''}</#noescape>
				                         </div>
				                     </#list>
			                     </#if>
	                    	</#if>
                        </div>
                    	
                    	<div class="jobElement hidden">
	                    	<#if materies['CHILD']??>
		            			<#assign mases=materies['CHILD']>
		            			<#if mases?? && mases?size gt 0>
				                     <#list mases as mas>
				                         <div class="jobElement-head">
				                             <strong>
				                             	${(mas.title)!''}
				                             </strong>
				                             <#if mas.images?? && mas.images?size gt 0>
				                             	<#list mas.images as image>	
					                             <a class="lookImg-link post-link" href="javascript:void(0);" data-image="${image!}">查看样图</a>
					                             </#list>
				                             </#if>
				                         </div>
				                         <div class="jobElement-content">
				                             	<#noescape>${(mas.content)!''}</#noescape>
				                         </div>
				                     </#list>
			                     </#if>
	                    	</#if>
                    	</div>
                    	
                    </#if>
                 </div>
             </div>