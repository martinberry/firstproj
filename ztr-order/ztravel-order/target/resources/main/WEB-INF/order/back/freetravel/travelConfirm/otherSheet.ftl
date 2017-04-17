<#assign attachments = (orderDetail.attachments)!/>
<div class="commonOPOrderModel paddingBottom20" <#if (readOnly)!false > style="display: none;" </#if>>
    <div class="commonSmallTitleModel blueFonts">
        <span class="leftTitleFonts">其他</span>
    </div>
    <div class="commonStyle">
        <div class="upLoadFile">
            <button class="commonButton gray-bbbButton">+ 添加附件</button>
            <form action="${basepath}/upload/uploadAttachment" method="POST" enctype="multipart/form-data">
                <input type="file" name="addAttachment" class="inputFile" id="addAttachment" onchange="uploadFile(this);">
            </form>
        </div>

        <div class="fileNameList">
            <#list attachments as attachment>
            <label class="fileName" value="${(attachment.mediaId)!}">
                <span class="fileNameFonts">${(attachment.name)!}</span>
                <i class="deleteFileName"></i>
            </label>
            </#list>
        </div>
    </div>
</div>