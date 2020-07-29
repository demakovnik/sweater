<#import "parts/common.ftlh" as c>
<@c.page>
    <form method="post" action="/user">
        <input name="userId" type="hidden" value="${user.id}">
        <input name="_csrf" type="hidden" value="${_csrf.token}">
        <label>User Name:<input value="${user.username}" name="username" type="text"></label>
        <#list roles as role>
        <div>
            <label><input name="${role}" type="checkbox"
                    ${user.roles?seq_contains(role)?string("checked","")}>
                ${role}
            </label>
        </div>
        </#list>
        <button type="submit">Save</button>
    </form>
</@c.page>