package org.kenny.bank.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kenny.bank.common.exception.BusinessException;
import org.kenny.bank.common.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/error-handler")
public class GlobalExceptionHandler extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (throwable instanceof BusinessException e) {
            // 业务异常
            logger.warn("转账业务异常: {}", e.getMessage(), e);
            request.setAttribute("errorCode", e.getErrorCode());
            request.setAttribute("errorMessage", e.getMessage());
        } else if (throwable instanceof DataAccessException e) {
            // 数据库访问异常
            logger.error("数据库访问异常", e);
            request.setAttribute("errorCode", "DATABASE_ERROR");
            request.setAttribute("errorMessage", "系统繁忙，请稍后再试");
        } else {
            // 根据状态码定制错误信息
            if (statusCode != null && statusCode == 404) {
                logger.warn("404错误: {}", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                request.setAttribute("errorCode", "NOT_FOUND");
                request.setAttribute("errorMessage", "请求的资源不存在");
            } else {
                logger.error("系统未知异常", throwable);
                request.setAttribute("errorCode", "SYSTEM_ERROR");
                request.setAttribute("errorMessage", "系统异常，请联系管理员");
            }
        }
        request.setAttribute("template", "error");
        request.getRequestDispatcher("/view").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
