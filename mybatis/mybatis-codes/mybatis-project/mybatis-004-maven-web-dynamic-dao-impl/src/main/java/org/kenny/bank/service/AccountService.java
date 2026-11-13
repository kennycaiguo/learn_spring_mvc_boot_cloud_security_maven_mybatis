package org.kenny.bank.service;

import org.kenny.bank.dto.AccountDTO;
import org.kenny.bank.entity.Account;
import org.kenny.bank.entity.Tran;

import java.math.BigDecimal;

public interface AccountService {
    /**
     * 转账业务方法
     * @param accountDTO ： 里面封装了相关的信息，转出账号：fromAccount, 转让账号： toAccount, 转账金额 amount
     * @return 转账对象
     */
    Tran transfer(AccountDTO accountDTO);
}
