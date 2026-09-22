package com.attendance.servlet;

import com.attendance.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");

        String sql =
            "SELECT s.roll_no, u.full_name, " +
            "SUM(a.status='PRESENT') AS present_days, " +
            "COUNT(a.id) AS total_days " +
            "FROM students s " +
            "JOIN users u ON s.user_id=u.id " +
            "LEFT JOIN attendance a ON s.id=a.student_id " +
            "GROUP BY s.id, s.roll_no, u.full_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            StringBuilder html = new StringBuilder();

            while (rs.next()) {
                int total = rs.getInt("total_days");
                int present = rs.getInt("present_days");
                double percentage = total == 0 ? 0 : present * 100.0 / total;

                html.append("<tr>")
                    .append("<td>").append(rs.getString("roll_no")).append("</td>")
                    .append("<td>").append(rs.getString("full_name")).append("</td>")
                    .append("<td>").append(present).append("</td>")
                    .append("<td>").append(total).append("</td>")
                    .append("<td>").append(String.format("%.2f", percentage)).append("%</td>")
                    .append("</tr>");
            }

            request.setAttribute("reportRows", html.toString());
            request.getRequestDispatcher("/report.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
