<#-- taglib -->
<#global spring=JspTaglibs["http://www.springframework.org/tags"] />
<#global form=JspTaglibs["http://www.springframework.org/tags/form"] />

<#-- properties -->
<#global basepath = "${getEnv(req.contextPath)}"/>
<#global host="${getEnv('server.host.static')}" />
<#global mediaserver="${getEnv('server.path.media')}" />
<#global memberServer="${getEnv('server.path.memberServer')}" />
<#global operaServer="${getEnv('server.path.operaServer')}" />
<#global panguServer="${getEnv('server.host.panguWeb')}" />
<#global panguStaticServer="${getEnv('server.host.panguStatic')}" />
<#global ssoServer="${getEnv('server.host.ssoServer')}" />
<#global wxServer="${getEnv('server.path.wxServer')}" />

