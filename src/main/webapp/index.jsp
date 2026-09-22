<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attendance Management System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="center-page">

<div class="card login-card">
    <h1>Attendance Management System</h1>
    <h2>Login</h2>

    <% if (request.getParameter("error") != null) { %>
        <p class="error"><%= request.getParameter("error") %></p>
    <% } %>

    <% if (request.getParameter("message") != null) { %>
        <p class="success"><%= request.getParameter("message") %></p>
    <% } %>

    <form action="login" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>

    <p class="hint">Admin: admin/admin123</p>
    <p class="hint">Staff: staff1/staff123</p>
    <p class="hint">Student: student1/student123</p>
</div>

</body>
</html>
