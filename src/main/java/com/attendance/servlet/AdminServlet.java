package com.attendance.servlet;

import com.attendance.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {

            if ("addClass".equals(action)) {
                String sql = "INSERT INTO classes(class_name,department) VALUES(?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, request.getParameter("className"));
                    ps.setString(2, request.getParameter("department"));
                    ps.executeUpdate();
                }
            }

            if ("addStaff".equals(action)) {
                String sql = "INSERT INTO users(username,password,role,full_name) VALUES(?,?, 'STAFF',?)";
                try (PreparedStatement ps = con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, request.getParameter("username"));
                    ps.setString(2, request.getParameter("password"));
                    ps.setString(3, request.getParameter("fullName"));
                    ps.executeUpdate();

                    ResultSet keys = ps.getGeneratedKeys();
                    if (keys.next()) {
                        int userId = keys.getInt(1);
                        String staffSql =
                                "INSERT INTO staff(user_id,staff_code,department) VALUES(?,?,?)";
                        try (PreparedStatement sp = con.prepareStatement(staffSql)) {
                            sp.setInt(1, userId);
                            sp.setString(2, request.getParameter("staffCode"));
                            sp.setString(3, request.getParameter("department"));
                            sp.executeUpdate();
                        }
                    }
                }
            }

            response.sendRedirect("dashboard?message=Admin operation completed");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
