package org.kenny.bank.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/view")
public class ThymeleafViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从request域中获取模板文件
        String tmpl =(String)req.getAttribute("template");
        if(tmpl==null){
            //如果没有,我们就从查询参数里面获取
            tmpl =req.getParameter("template");
        }
        ServletContext application = getServletContext();
        //获取适配器对象
        JakartaServletWebApplication jakartaServletWebApplication =
                (JakartaServletWebApplication) application.getAttribute("jakartaServletWebApplication");
        //获取模板引擎对象
        TemplateEngine templateEngine = (TemplateEngine) application.getAttribute("templateEngine");
        WebContext webContext =  new WebContext(jakartaServletWebApplication.buildExchange(req,resp),req.getLocale());
        //        webContext.setVariable("username",username); //使用重定向的时候需要打开
        //引擎渲染模板文件
        templateEngine.process(tmpl,webContext,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         doGet(req, resp);
    }
}
