

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'inventoryItem.label', default: 'InventoryItem')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'inventoryItem.id.label', default: 'Id')}" />
                        
                            <th><g:message code="inventoryItem.inventoryLocation.label" default="Inventory Location" /></th>
                        
                            <g:sortableColumn property="name" title="${message(code: 'inventoryItem.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${inventoryItemInstanceList}" status="i" var="inventoryItemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${inventoryItemInstance.id}">${fieldValue(bean: inventoryItemInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: inventoryItemInstance, field: "inventoryLocation")}</td>
                        
                            <td>${fieldValue(bean: inventoryItemInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${inventoryItemInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
