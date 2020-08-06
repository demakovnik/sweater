<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<@l.login "/registration" "Add User" true/>
    <#if user??>
        <div class="alert alert-danger" role="alert">
            User <strong>"${user.getUsername()}"</strong> already exists!
        </div>
    </#if>

</@c.page>