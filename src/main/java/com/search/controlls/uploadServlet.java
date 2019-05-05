package com.search.controlls;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Description: servlet模板
 * @Param:
 * @Author:
 * @Date:
 */
@WebServlet(
        name="uploadServlet",
        urlPatterns={"/upload"},
        loadOnStartup = 1
)

@MultipartConfig(
        fileSizeThreshold = 5_242_880,
        maxFileSize  =20_971_250L,
        maxRequestSize = 41_943_040L

)

public class uploadServlet extends HttpServlet{
    public static void main(String[] args) {

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException,ServletException{

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException,ServletException{

    }
    @Override
    public  void init(){

    }
    @Override
    public  void destroy(){

    }


}