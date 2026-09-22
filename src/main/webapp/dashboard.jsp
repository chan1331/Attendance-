<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String role = (String) session.getAttribute("role");
    String name = (String) session.getAttribute("name");

    if (role == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Attendance Management System</h1>
    <div>
        Welcome, <b><%= name %></b>
        <a class="logout" href="logout">Logout</a>
    </div>
</header>

<main>
    <% if (request.getParameter("message") != null) { %>
        <p class="success"><%= request.getParameter("message") %></p>
    <% } %>

    <% if ("ADMIN".equals(role)) { %>

    <h2>Admin Dashboard</h2>

    <div class="grid">

        <div class="card">
            <h3>Add Class</h3>
            <form action="admin" method="post">
                <input type="hidden" name="action" value="addClass">
                <input name="className" placeholder="Class Name" required>
                <input name="department" placeholder="Department" required>
                <button>Add Class</button>
            </form>
        </div>

        <div class="card">
            <h3>Add Staff</h3>
            <form action="admin" method="post">
                <input type="hidden" name="action" value="addStaff">
                <input name="fullName" placeholder="Full Name" required>
                <input name="username" placeholder="Username" required>
                <input name="password" placeholder="Password" required>
                <input name="staffCode" placeholder="Staff Code" required>
                <input name="department" placeholder="Department" required>
                <button>Add Staff</button>
            </form>
        </div>

        <div class="card">
            <h3>View Reports</h3>
            <a class="button" href="report?type=attendance">Attendance Report</a>
        </div>

    </div>

    <% } else if ("STAFF".equals(role)) { %>

    <h2>Staff Dashboard</h2>

    <div class="grid">

        <div class="card">
            <h3>Add Student</h3>
            <form action="staff" method="post">
                <input type="hidden" name="action" value="addStudent">
                <input name="fullName" placeholder="Full Name" required>
                <input name="username" placeholder="Username" required>
                <input name="password" placeholder="Password" required>
                <input name="rollNo" placeholder="Roll Number" required>
                <input name="department" placeholder="Department" required>
                <input name="className" placeholder="Class" required>
                <button>Add Student</button>
            </form>
        </div>

        <div class="card">
            <h3>Take Attendance</h3>
            <form action="staff" method="post">
                <input type="hidden" name="action" value="attendance">
                <input name="studentId" placeholder="Student ID" type="number" required>
                <input name="date" type="date" required>
                <select name="status">
                    <option value="PRESENT">Present</option>
                    <option value="ABSENT">Absent</option>
                </select>
                <button>Save Attendance</button>
            </form>
        </div>

        <div class="card">
            <h3>Generate Report</h3>
            <a class="button" href="report?type=attendance">Attendance Report</a>
        </div>

    </div>

    <% } else if ("STUDENT".equals(role)) { %>

    <h2>Student Dashboard</h2>

    <div class="grid">

        <div class="card">
            <h3>View Attendance</h3>
            <a class="button" href="report?type=attendance">View Attendance Report</a>
        </div>

        <div class="card">
            <h3>Apply Leave</h3>
            <form action="student" method="post">
                <input type="hidden" name="action" value="leave">
                <input name="fromDate" type="date" required>
                <input name="toDate" type="date" required>
                <textarea name="reason" placeholder="Reason" required></textarea>
                <button>Apply Leave</button>
            </form>
        </div>

        <div class="card">
            <h3>Complaint</h3>
            <form action="student" method="post">
                <input type="hidden" name="action" value="complaint">
                <textarea name="message" placeholder="Enter complaint" required></textarea>
                <button>Submit Complaint</button>
            </form>
        </div>

    </div>

    <% } %>
</main>

</body>
</html>
