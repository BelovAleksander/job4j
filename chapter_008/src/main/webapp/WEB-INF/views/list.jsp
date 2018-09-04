<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .quitBt, .editUsBt, .addBt{
            float: right;
            margin-right: 2px;
        }
        .editBt, .deleteBt{
            float: right;
            margin-right: 2px;
        }

    </style>
</head>
<body>
    <form action="${pageContext.servletContext.contextPath}/signout" method="GET">
        <input type="submit" value="quit" class="quitBt"/>
    </form>
    <form action="${pageContext.servletContext.contextPath}/edit" method="POST">
        <input type='hidden' name='id' value='<c:out value="${sessionScope.user.id}"></c:out>'/>
        <input type='hidden' name='name' value='<c:out value="${sessionScope.user.personality.name}"></c:out>'/>
        <input type='hidden' name='login' value='<c:out value="${sessionScope.user.personality.login}"></c:out>'/>
        <input type='hidden' name='email' value='<c:out value="${sessionScope.user.personality.email}"></c:out>'/>
        <input type="hidden" name="password" value='<c:out value="${sessionScope.user.personality.password}"></c:out>'>
        <input type="hidden" name="role" value="<c:out value="$${sessionScope.user.role}"></c:out>">
        <input type="submit" value="edit" class="editUsBt">
    </form>
    <c:if test="${sessionScope.user.role.equals('admin')}">
    <form action='${pageContext.servletContext.contextPath}/create' method='POST'>
        <input type='submit' value="add" class="addBt"/>
    </form>
    </c:if>
    <h2>Users table:</h2>
    <div class="container">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>login</th>
            <th>email</th>
            <th>password</th>
            <th>create date</th>
            <th>role</th>
            <th>city</th>
            <th>country</th>
            <th>actions</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
        <tbody>
            <tr>
            <th><c:out value="${user.id}"></c:out></th>
            <th><c:out value="${user.personality.name}"></c:out></th>
            <th><c:out value="${user.personality.login}"></c:out></th>
            <th><c:out value="${user.personality.email}"></c:out></th>
            <th><c:out value="${user.personality.password}"></c:out></th>
            <th><c:out value="${user.createDate}"></c:out></th>
            <th><c:out value="${user.role}"></c:out></th>
            <th><c:out value="${user.city}"></c:out></th>
            <th><c:out value="${user.country}"></c:out></th>
            <c:if test="${sessionScope.user.role.equals('admin')}">
            <th>
                <form action="${pageContext.servletContext.contextPath}/edit" method="POST">
                <input type='hidden' name='id' value='<c:out value="${user.id}"></c:out>'/>
                <input type='hidden' name='name' value='<c:out value="${user.personality.name}"></c:out>'/>
                <input type='hidden' name='login' value='<c:out value="${user.personality.login}"></c:out>'/>
                <input type='hidden' name='email' value='<c:out value="${user.personality.email}"></c:out>'/>
                <input type="hidden" name="password" value='<c:out value="${user.personality.password}"></c:out>'/>
                <input type="hidden" name="role" value="<c:out value="${user.role}"></c:out>"/>
                <input type="hidden" name="city" value="<c:out value="${user.city}"></c:out>"/>
                <input type="hidden" name="country" value="<c:out value="${user.country}"></c:out>"/>
                <input type="submit" value="edit" class="editBt"/>
            </form>
            <form action='${pageContext.servletContext.contextPath}/' method='POST'>
                <input type='hidden' name='id' value='<c:out value="${user.id}"></c:out>'/>
                <input type='hidden' name='action' value='delete'/>
                <input type='submit' value='delete' class="deleteBt"/>
            </form>
            </th>
            </c:if>
            </tr>
        </c:forEach>>
        </tbody>
    </table>
    </div>
</body>
</html>
