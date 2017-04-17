
                           <#if productVoList??>
	                            <#list productVoList as product>
					   			 <tr>

					   			 <input type="hidden" name="objectId" value="${product.id!}"/>
					                            <td>  ${product.pid!}</td>
					                            <td class="fs14">${product.pName!}</td>
					                            <td>${product.theme!}</td>
					                            <td>自由行</td>
					                            <td>${product.from!}</td>
					                            <td>${product.to!}</td>
					                            <td class="fs14">
					                               ${product.createdTime!}
					                            </td>
					                            <td class="fs14">
					                                 ${product.updatedTime!}
					                            </td>

					                            <#if product.status?? >
					                            	<#if product.status=="NEW">
					                            		  <td><span> 草稿</span></td>
				                            			   <td>
								                                 <a href="${basepath}/product/basicInfo/edit/${product.id!}" class="greenFontsLink">编辑</a>
								                                <a href="javascript:void(0);" class="greenFontsLink ac-del del" >删除</a>
								                            </td>
								                     <#elseif product.status=="RELEASED">
								                     		<td><span> 上线</span></td>
								                     		<td>
								                                 <a href="${basepath}/product/basicInfo/edit/${product.id!}" class="greenFontsLink">编辑</a>
								                                 <a href="javascript:void(0);" class="greenFontsLink ac-offline offline">关闭</a>
								                                <a href="javascript:void(0);" class="greenFontsLink ac-del del">删除</a>
								                            </td>
								                     <#elseif product.status=="OFFLINE">
								                   		　<td><span> 关闭</span></td>
								                     		<td>
								                                <a href="javascript:void(0);" class="greenFontsLink ac-online online">上线</a>
								                                <a href="${basepath}/product/basicInfo/edit/${product.id!}" class="greenFontsLink">编辑</a>
								                                <a href="javascript:void(0);" class="greenFontsLink ac-del del">删除</a>
								                            </td>
								                     <#elseif product.status=="EXPIRED">
								                    		 <td><span> 过期</span></td>
								                     		<td>
								                                <a href="${basepath}/product/basicInfo/edit/${product.id!}" class="greenFontsLink">编辑</a>
								                                <a href="javascript:void(0);" class="greenFontsLink ac-del del">删除</a>
								                            </td>
													</#if>
													<#else>
													 <td><span> </span></td>
													 <td>
									                            </td>

					                            </#if>


					                        </tr>
								</#list>
                            </#if>
                    <-split->


                        </tbody>

                    </table>
                      <#include "/product/common/pagination.ftl"/>

                         <script>


    $(function(){
        $(".radioList .radio").click(function(){
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
        });

        $(".allCheckbox").click(function(){
            $(this).toggleClass("active");
            if ($(this).hasClass("active")) {
                $(this).parents("table").find(".checkbox").addClass("active");
            } else {
                $(this).parents("table").find(".checkbox").removeClass("active");
            }
        });


// online
        $("tr").find(".online").click(function(){
			var objectId=$(this).parents("tr").find("[name='objectId']").val();
			$("#onlineObjectId").val(objectId);
            $("#ac-online").modal();
        });
//del
         $("tr").find(".del").click(function(){
         	var objectId=$(this).parents("tr").find("[name='objectId']").val();
         	$("#delObjectId").val(objectId);
            $("#ac-del").modal();
        });
//close
 		 $("tr").find(".offline").click(function(){
         	var objectId=$(this).parents("tr").find("[name='objectId']").val();
         	$("#closeObjectId").val(objectId);
            $("#ac-close").modal();
        });



//cancel
          $("#ac-online").find(".blue-45c8dcButton").click(function(){
        	$("#ac-online").modal("hide");
        });

        $("#ac-close").find(".blue-45c8dcButton").click(function(){
        	$("#ac-close").modal("hide");
        });
           $("#ac-del").find(".blue-45c8dcButton").click(function(){
        	$("#ac-del").modal("hide");
        });


    });


</script>
