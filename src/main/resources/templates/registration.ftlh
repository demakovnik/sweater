<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<@l.login "/registration" "Add User" true/>
    <#if user??>
        <div class="alert alert-danger" role="alert">
            ${message?if_exists}
        </div>
    </#if>

</@c.page>