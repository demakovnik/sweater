<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?if_exists}" placeholder="Search">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>

    <div>
        <form method="post" action="add" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="text" name="text" placeholder="введите сообщение">
            <input type="text" name="tag" placeholder="тэг">
            <input type="file" name="files" multiple>
            <button type="submit">Добавить сообщение</button>
        </form>
    </div>

    <#list messages as message>
        <div>
        <span>
        <span>${message.getId()}</span>
        <b>${message.getText()}</b>
        <i>${message.getTag()}</i>
        <strong>${message.getAuthorName()}</strong>
            <div>
                <#if message.filename??>
                    <a href="/img/${message.authorName}/${message.filename}"><img src="/img/${message.authorName}/${message.filename}" height="30%" width="30%"></a>
                </#if>
            </div>
            <form method="post" action="delete">
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
