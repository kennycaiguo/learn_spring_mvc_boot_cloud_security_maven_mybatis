package org.kenny.bank.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kenny.bank.dto.AccountDTO;
import org.kenny.bank.entity.Tran;
import org.kenny.bank.service.AccountService;
import org.kenny.bank.service.impl.AccountServiceImpl;
import org.kenny.bank.vo.AccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/transfer")
public class AccountController extends HttpServlet {

    private AccountService accountService = new AccountServiceImpl();
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //显示标准输出流和标准错误流的编码方式
        System.out.println(System.out.charset());
        System.out.println(System.err.charset());
        //接收表单提交的数据，封装DTO
        String fromAcc = request.getParameter("fromAccount");
        String toAcc = request.getParameter("toAccount");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        AccountDTO accountDTO = new AccountDTO(fromAcc,toAcc,amount);
        //记录一下日志
        logger.info("转账请求参数：转出账号{}，转入账号{}，转账金额{}",fromAcc,toAcc,amount);
        //调用service完成转账
        Tran tran = accountService.transfer(accountDTO);
        //封装VO
        AccountVO accountVO = new AccountVO();
        accountVO.setAmount(amount);
        //账号需要脱敏处理，就是只显示某几位
        accountVO.setFromActNo("****"+fromAcc.substring(fromAcc.length()-4));
        accountVO.setToActNo("****"+toAcc.substring(fromAcc.length()-4));
        accountVO.setTranNo(tran.getTranNo());
        LocalDateTime localDateTime = tran.getTranTime();
        String tranTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        accountVO.setTranTime(tranTime);
        //将VO存储到请求域对象中
        request.setAttribute("accountVO",accountVO);
        request.setAttribute("template","success");
        //转发到成功页面
        request.getRequestDispatcher("/view").forward(request,response);
    }
}
