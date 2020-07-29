<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <@l.logout />
    <#if author.roles?seq_contains(roleAdmin)>
        <form method="get" action="/user">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <#--<input type="hidden" name="id" value="${message.getId()}">-->
            <button type="submit">User List</button>
        </form>
    </#if>

    <div>
        <form method="post" action="add" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="text" name="text" placeholder="введите сообщение">
            <input type="text" name="tag" placeholder="тэг">
            <input type="file" name="file">
            <button type="submit">Добавить сообщение</button>
        </form>
    </div>

    <div>
        Поиск по тэгу:
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter?if_exists}">
            <button type="submit">Найти</button>
        </form>
    </div>
    <div>Список сообщений:</div>
    <#list messages as message>
        <div>
        <span>
        <span>${message.getId()}</span>
        <b>${message.getText()}</b>
        <i>${message.getTag()}</i>
        <strong>${message.getAuthorName()}</strong>
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}">
                </#if>
            </div>
            <form method="get" action="delete">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="hidden" name="messageId" value="${message.getId()}">
                <button type="submit">Удалить</button>
            </form>
        </span>
        </div>
    <#else>
        No messages
    </#list>
</@c.page>
