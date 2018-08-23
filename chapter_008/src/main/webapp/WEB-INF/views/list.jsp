<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Users table:</h2>
        <form action='${pageContext.servletContext.contextPath}/create' method='GET'>
            <input type='submit' value="add"/>
        </form>
    <table>
        <c:forEach items="${users}" var="user">
        <tr><td>
            <c:out value="${user}"></c:out>
            <form action="${pageContext.servletContext.contextPath}/edit" method="GET">
                <input type='hidden' name='id' value='<c:out value="${user.id}"></c:out>'/>
                <input type='hidden' name='name' value='<c:out value="${user.name}"></c:out>'/>
                <input type='hidden' name='login' value='<c:out value="${user.login}"></c:out>'/>
                <input type='hidden' name='email' value='<c:out value="${user.email}"></c:out>'/>
                <input type="submit" value="edit">
            </form>
            <form action='${pageContext.servletContext.contextPath}/' method='POST'>
                <input type='hidden' name='id' value='<c:out value="${user.id}"></c:out>'/>
                <input type='hidden' name='action' value='delete'/>
                <input type='submit' value='delete'/>
            </form>
        </td></tr>
        </c:forEach>>
    </table>
</body>
</html>
