package org.kenny.bank.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获取ServletContext对象
        ServletContext application = sce.getServletContext();
//        System.out.println(application);//ok
        //创建Thymeleaf和servlet的适配器，处理web请求
        JakartaServletWebApplication jakartaServletWebApplication = JakartaServletWebApplication.buildApplication(application);
//        System.out.println(jakartaServletWebApplication); //ok
        //用适配器作为参数创建模板解析器
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(jakartaServletWebApplication);
        //设置模板文件的基础存放路径
        templateResolver.setPrefix("/WEB-INF/templates/");
        //设置模板文件的后缀名
        templateResolver.setSuffix(".html");
        //启动html语法支持
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //禁用模板缓存，目的是可以快速看到效果
        templateResolver.setCacheable(false);
        //创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        //关联模板解析器
        templateEngine.setTemplateResolver(templateResolver);
        // 把适配器保存到ServletContext，供后续请求使用
        application.setAttribute("jakartaServletWebApplication", jakartaServletWebApplication);
        // 模板引擎保存到ServletContext，供后续请求使用
        application.setAttribute("templateEngine", templateEngine);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
