<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attendance Report</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Attendance Report</h1>
    <a class="logout" href="dashboard">Dashboard</a>
</header>

<main>
<table>
    <thead>
        <tr>
            <th>Roll No</th>
            <th>Student Name</th>
            <th>Present</th>
            <th>Total Days</th>
            <th>Percentage</th>
        </tr>
    </thead>
    <tbody>
        <%= request.getAttribute("reportRows") == null
                ? "<tr><td colspan='5'>No data</td></tr>"
                : request.getAttribute("reportRows") %>
    </tbody>
</table>
</main>

</body>
</html>
