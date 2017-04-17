<#if pieceProductVoList??>
    <#list pieceProductVoList as pieceProduct>
    <tr>
        <input type="hidden" name="objectId" value="${pieceProduct.id!}"/>
        <input type="hidden" name="nature" value="${pieceProduct.nature!}"/>
        <td>${pieceProduct.pid!}</td>
        <td class="pieceName">${(pieceProduct.pname)!''}</td>
        <td>${pieceProduct.type!}</td>
        <td>${pieceProduct.to!}</td>
        <td class="fs14">
            <div>${pieceProduct.createTime!}</div>
        </td>
        <td class="fs14">
            <div>${pieceProduct.updateTime!}</div>
        </td>
        <#if pieceProduct.status?? >
            <#if pieceProduct.status=="NEW">
                  <td><span> 草稿</span></td>
                   <td>
                         <a href="${basepath}/pieces/basicInfo/edit/${pieceProduct.nature!}/${pieceProduct.id!}" class="greenFontsLink">编辑</a>
                        <a href="javascript:void(0);" class="greenFontsLink ac-del del" >删除</a>
                    </td>
             <#elseif pieceProduct.status=="RELEASED">
                    <td><span> 上线</span></td>
                    <td>
                         <a href="${basepath}/pieces/basicInfo/edit/${pieceProduct.nature!}/${pieceProduct.id!}" class="greenFontsLink">编辑</a>
                         <a href="javascript:void(0);" class="greenFontsLink ac-offline offline">关闭</a>
                        <a href="javascript:void(0);" class="greenFontsLink ac-del del">删除</a>
                    </td>
             <#elseif pieceProduct.status=="OFFLINE">
                　<td><span> 关闭</span></td>
                    <td>
                        <a href="javascript:void(0);" class="greenFontsLink ac-online online">上线</a>
                        <a href="${basepath}/pieces/basicInfo/edit/${pieceProduct.nature!}/${pieceProduct.id!}" class="greenFontsLink">编辑</a>
                        <a href="javascript:void(0);" class="greenFontsLink ac-del del">删除</a>
                    </td>
             <#elseif pieceProduct.status=="EXPIRED">
                     <td><span> 过期</span></td>
                    <td>
                        <a href="${basepath}/pieces/basicInfo/edit/${pieceProduct.nature!}/${pieceProduct.id!}" class="greenFontsLink">编辑</a>
                        <a href="javascript:void(0);" class="greenFontsLink ac-del del">删除</a>
                    </td>
            </#if>
        <#else>
             <td><span></span></td>
             <td></td>
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
        var nature=$(this).parents("tr").find("[name='nature']").val();
        $("#onlineObjectId").val(objectId);
        $("#onlineNature").val(nature);
        $("#ac-online").modal();
    });

    //del
    $("tr").find(".del").click(function(){
        var objectId=$(this).parents("tr").find("[name='objectId']").val();
        var nature=$(this).parents("tr").find("[name='nature']").val();
        $("#delObjectId").val(objectId);
        $("#delNature").val(nature);
        $("#ac-del").modal();
    });

    //close
    $("tr").find(".offline").click(function(){
        var objectId=$(this).parents("tr").find("[name='objectId']").val();
        var nature=$(this).parents("tr").find("[name='nature']").val();
        $("#closeObjectId").val(objectId);
        $("#closeNature").val(nature);
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