
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action='<%=request.getContextPath()%>/user' method='POST'>
        name: <input type='text' name='name' value=''/>
        email: <input type='text' name='email' value=''/>
        login: <input type='text' name='login' value=''/>
        <input type='hidden' name='action' value='add'/>
        <input type='submit' value='create'/>
    </form>
</body>
</html>
