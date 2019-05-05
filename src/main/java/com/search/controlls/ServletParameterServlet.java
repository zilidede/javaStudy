package com.search.controlls;

/*
 * @Description:
 * @Param:
 * @Author:
 * @Date:
 */
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
public class ServletParameterServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ServletConfig c = this.getServletConfig();
        PrintWriter writer = response.getWriter();

        writer.append("database: ").append(c.getInitParameter("database"))
                .append(", server: ").append(c.getInitParameter("server"));
    }
    @Override
    public  void init() throws  ServletException{
        System.out.println("Servlet"+this.getServletName()+"has stared。");
    }

    @Override
    public  void destroy()
    {
        System.out.println("Servlet " + this.getServletName() + " has stopped.");
    }

}
