<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Update</title>
</head>
<body>
    <form action='${pageContext.servletContext.contextPath}/' method=POST>
        name: <input type='text' name='name' value='${param.name}'/>
        login: <input type='text' name='login' value='${param.login}'/>
        email: <input type='email' name='email' value='${param.email}'/>
        new password: <input type='password' name='password1' value=''/>
        repeat password: <input type="password" name="password2" value=""/>
        <c:if test="${sessionScope.user.role.equals('admin')}">
            role: <select name="role">
            <option value="admin">admin</option>
            <option value="user">user</option>
        </select>
        </c:if>
        <input type='hidden' name='id' value='${param.id}'/>
        <input type='hidden' name='action' value='update'/>
        <input type='submit' value='edit'/>
        <c:if test="${error != ''}">
            <div style="background-color: red">
                <c:out value="${error}"></c:out>
            </div>
        </c:if>
    </form>
</body>
</html>
