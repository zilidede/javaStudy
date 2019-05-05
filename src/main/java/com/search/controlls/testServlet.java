package com.search.controlls;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @Description: 测试Servlet
 * @Param:
 * @Author:
 * @Date:
 */
@WebServlet(
        name = "testServlet",
        urlPatterns={"/test"},
        initParams={
        @WebInitParam(name = "default_market", value = "NASDAQ"),
        @WebInitParam(name = "user_market", value = "userNASDAQ")
        }

)
public class testServlet extends HttpServlet {
    private  static final  String DEFAULT_USER ="ZILI";
    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{


        PrintWriter writer = response.getWriter();
        ServletConfig c = this.getServletConfig();
        String default_market=	getInitParameter("default_market");
        String user_market=	getInitParameter("user_market");
        writer.append("setting1: ").append(c.getInitParameter("default_market"))
                .append(", setting2: ").append(c.getInitParameter("user_market"));

    }
    @Override
    public  void doPost(HttpServletRequest request,HttpServletResponse response)throws  ServletException ,IOException{
        //this.doGet(request,response);
        String[] fruits = request.getParameterValues("fruit");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>Hello User Application</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n")
                .append("        <h2>Your Selections</h2>\r\n");

        if(fruits == null)
            writer.append("        You did not select any fruits.\r\n");
        else
        {
            writer.append("        <ul>\r\n");
            for(String fruit : fruits)
            {
                writer.append("        <li>").append(fruit).append("</li>\r\n");
            }
            writer.append("        </ul>\r\n");
        }

        writer.append("    </body>\r\n")
                .append("</html>\r\n");

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
