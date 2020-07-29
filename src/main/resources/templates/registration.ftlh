<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    Add new User
<@l.login "/registration" "Add User"/>
    <#if user??>
        <span class="colortext">User <strong>${user.getUsername()}</strong> already exists!</span>
    </#if>

</@c.page>