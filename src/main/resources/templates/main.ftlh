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

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new Message
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group">
            <form method="post" action="add" enctype="multipart/form-data">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="введите сообщение">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="тэг">
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="customFile" name="files" multiple>
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить сообщение</button>
                </div>
            </form>
        </div>
    </div>


    <div class="card-columns">
        <#list messages as message>
            <div>
                <div class="card my-3">
                    <div>
                        <#if message.filenames??>
                            <#list message.filenames as filename>
                                <a href="/img/${message.authorName}/${filename}"><img class="card-img-top"
                                                                                      src="/img/${message.authorName}/${filename}"
                                                                                      height="30%" width="30%"></a>
                            </#list>
                        </#if>
                    </div>
                    <div class="m-2">
                        ${message.getText()}
                        <i>${message.getTag()}</i>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.getAuthorName()}
                    </div>

                    <form method="post" action="delete">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="hidden" name="messageId" value="${message.getId()}">
                        <button type="submit">Удалить</button>
                    </form>
                </div>
            </div>
        <#else>
            No messages
        </#list>
    </div>
</@c.page>
