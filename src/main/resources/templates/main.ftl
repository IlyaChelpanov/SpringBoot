<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <@l.logout/>
    <div>
        <form method="post" action="/add">
            <input type="text" name="text" placeholder="Insert Message">
            <input type="text" name="tag" placeholder="tag">
            <button type="submit">Add</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
    <div>
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter}" placeholder="Filter messages">
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
    <#else>
        No messages
    </#list>


    <form method="post" action="delete">
        <button type="submit">Delete all messages</button>
        <input type="hidden" name="_csrf" value="{{_csrf.token}}">
    </form>
</@c.page>