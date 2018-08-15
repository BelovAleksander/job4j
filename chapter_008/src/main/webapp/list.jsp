<%@ page import="ru.job4j.models.User" %>
<%@ page import="ru.job4j.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Users table:</h2>
        <form action='<%=request.getContextPath()%>/create' method='GET'>
            <input type='submit' value="add"/>
        </form>
    <table>
        <% for (User user : ValidateService.getInstance().findAll()) {%>
        <tr><td>
            <%=user.toString()%>
            <form action="<%=request.getContextPath()%>/edit" method="GET">
                <input type='hidden' name='id' value='<%=user.getId()%>'/>
                <input type='hidden' name='name' value='<%=user.getName()%>'/>
                <input type='hidden' name='login' value='<%=user.getLogin()%>'/>
                <input type='hidden' name='email' value='<%=user.getEmail()%>'/>
                <input type="submit" value="edit">
            </form>
            <form action='<%=request.getContextPath()%>/user' method='POST'>
                <input type='hidden' name='id' value='<%=user.getId()%>'/>
                <input type='hidden' name='action' value='delete'/>
                <input type='submit' value='delete'/>
            </form>
        </td></tr>
        <%}%>
    </table>
</body>
</html>
