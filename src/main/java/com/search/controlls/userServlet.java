package com.search.controlls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * @Description: 登陆servlet
 * @Param:
 * @Author: zl
 * @Date: 2019-01-06
 */
@WebServlet(
        name ="userServlet",
        urlPatterns = {"/login"}
)
public class userServlet  extends HttpServlet{
    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException,IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        /**
         * 接收json
         */
        BufferedReader reader = request.getReader();
        String json = reader.readLine();
        System.out.println(json);
        reader.close();

        /**
         * 返回json
         */
        PrintWriter out = response.getWriter();
        out.write("{\"retcode\":\"0\"}");
        out.close();

    }
    @Override
    public void init() throws  ServletException{
        System.out.println("Servlet"+this.getServletName()+"has stared。");
    }
    @Override
    public void destroy() {
        System.out.println("Servlet " + this.getServletName() + " has stopped.");
    }
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        String user="root";
        String pass="123456";
        String url = "jdbc:mysql://localhost:3306/bookdb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
        System.out.println("正在连接数据库......");
        try {
            Connection conn = DriverManager.getConnection(url,user,pass);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
