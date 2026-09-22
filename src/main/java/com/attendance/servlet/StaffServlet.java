package com.attendance.servlet;

import com.attendance.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/staff")
public class StaffServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {

            if ("attendance".equals(action)) {
                String sql =
                    "INSERT INTO attendance(student_id,attendance_date,status) " +
                    "VALUES(?,?,?) ON DUPLICATE KEY UPDATE status=VALUES(status)";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, Integer.parseInt(request.getParameter("studentId")));
                    ps.setDate(2, Date.valueOf(request.getParameter("date")));
                    ps.setString(3, request.getParameter("status"));
                    ps.executeUpdate();
                }
            }

            if ("addStudent".equals(action)) {
                String userSql =
                    "INSERT INTO users(username,password,role,full_name) VALUES(?,?, 'STUDENT',?)";

                try (PreparedStatement ps = con.prepareStatement(userSql,
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, request.getParameter("username"));
                    ps.setString(2, request.getParameter("password"));
                    ps.setString(3, request.getParameter("fullName"));
                    ps.executeUpdate();

                    ResultSet keys = ps.getGeneratedKeys();

                    if (keys.next()) {
                        int userId = keys.getInt(1);

                        String studentSql =
                            "INSERT INTO students(user_id,roll_no,department,class_name) VALUES(?,?,?,?)";

                        try (PreparedStatement sp = con.prepareStatement(studentSql)) {
                            sp.setInt(1, userId);
                            sp.setString(2, request.getParameter("rollNo"));
                            sp.setString(3, request.getParameter("department"));
                            sp.setString(4, request.getParameter("className"));
                            sp.executeUpdate();
                        }
                    }
                }
            }

            response.sendRedirect("dashboard?message=Staff operation completed");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
