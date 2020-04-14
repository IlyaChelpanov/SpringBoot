<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <@l.logout/>
    <span><a href="/user">User List</a> </span>
    <div>
        <form method="post" action="/add" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Insert Message">
            <input type="text" name="tag" placeholder="tag">
            <input type="file" name="file">
            <button type="submit">Add</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
    <div>
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter?if_exists}" placeholder="Filter messages">
            <button type="submit">Find</button>
        </form>
    </div>

    <div>Список сообщений</div>
    <#list messages as message>
        </div>
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i></ul>
        <strong>${message.authorName}</strong>
        <div>
            <#if message.filename??>
              <img src="/img/${message.filename}">
            </#if>
        </div>
        <div>
    <#else>
        No messages
    </#list>


    <form method="post" action="/delete">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Delete all messages</button>
    </form>
</@c.page>