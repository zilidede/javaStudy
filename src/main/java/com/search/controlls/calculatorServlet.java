package com.search.controlls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Description: 计算器servlet
 * @Param:
 * @Author: zl
 * @Date: 2019/5/25 17:39
 */
@WebServlet(
        name = "toolServlet",
        urlPatterns = "/toolView"
)
public class calculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/tool/calculator.jsp").forward(req,resp); ;
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

    }
}
