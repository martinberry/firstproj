
    <div class="headRightOperate">
        <span class="userContent">Hi，${getCurrOp()!''}</span>
        <#if getMessage()??>
        <label class="emailContent" onClick="window.location.href='${basepath}${getMessage()}'">
            <i class="emailIcon"></i>
            <span class="emailFonts">0</span>
        </label>
        </#if>
        <a href="${basepath}/user/signOut?userName=${getCurrOp()!''}" class="exitFonts">退出</a>
    </div>
