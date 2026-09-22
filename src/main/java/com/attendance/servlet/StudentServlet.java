package com.attendance.servlet;

import com.attendance.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private int getStudentId(Connection con, int userId) throws SQLException {
        String sql = "SELECT id FROM students WHERE user_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String action = request.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {

            int studentId = getStudentId(con, userId);

            if ("leave".equals(action)) {
                String sql =
                    "INSERT INTO leaves(student_id,from_date,to_date,reason) VALUES(?,?,?,?)";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, studentId);
                    ps.setDate(2, Date.valueOf(request.getParameter("fromDate")));
                    ps.setDate(3, Date.valueOf(request.getParameter("toDate")));
                    ps.setString(4, request.getParameter("reason"));
                    ps.executeUpdate();
                }
            }

            if ("complaint".equals(action)) {
                String sql =
                    "INSERT INTO complaints(student_id,message) VALUES(?,?)";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, studentId);
                    ps.setString(2, request.getParameter("message"));
                    ps.executeUpdate();
                }
            }

            response.sendRedirect("dashboard?message=Student operation completed");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
