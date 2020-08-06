<#import "parts/common.ftlh" as c>
<@c.page>
    <div>
        UserList:
        <table>
            <thead>
            <tr>
                <td>User Name</td>
                <td>Roles</td>
                <td></td>
                <#if loggedUser.roles?seq_contains(adminRole)>
                    <td></td>
                    <#else>
                </#if>

            </tr>
            </thead>
            <tbody>
            <#list userList as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>
                        ${role}<#sep>,
                    </#list></td>
                <td><form method="get" action="/user/${user.id}">
                        <input type="hidden" name="userId" value="${user.id}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button type="submit">Edit</button>
                    </form>
                    <#--<a href="/user/${user.id}">edit</a>--></td>
                <td>
                    <form method="get" action="/user/delete">
                        <input type="hidden" name="userId" value="${user.id}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
            </tbody>
            </#list>
        </table>
    </div>
</@c.page>