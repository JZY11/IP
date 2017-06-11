package demo.servlet;

import demo.util.Db;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhenya.1291813139.com
 * on 2017/6/11.
 * IP.
 */
@WebServlet(urlPatterns = "/index")
public class AddressServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String ip = req.getParameter("ip");
        Connection connection = Db.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM db_1712.ip WHERE inet_aton(?) BETWEEN inet_aton(min) AND inet_aton(max)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ip);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                req.setAttribute("message", resultSet.getString("geo"));
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }else {
                req.setAttribute("message", resultSet.getString("您要查询的IP不存在"));
                req.getRequestDispatcher("second.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(resultSet, statement, connection);
        }
    }
}
