<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Update</title>
</head>
<body>
    <form action='<%=request.getContextPath()%>/' method=POST>
        name: <input type='text' name='name' value='<%=request.getParameter("name")%>'/>
        login: <input type='text' name='login' value='<%=request.getParameter("login")%>'/>
        email: <input type='email' name='email' value='<%=request.getParameter("email")%>'/>
        <input type='hidden' name='id' value='<%=request.getParameter("id")%>'/>
        <input type='hidden' name='action' value='update'/>
        <input type='submit' value='edit'/>
    </form>
</body>
</html>
