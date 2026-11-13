package org.kenny.bank.mapper;

import org.kenny.bank.entity.Account;

public interface AccountMapper {
    /**
     * 根据账号查询账户信息
     * @param accNo： 账号
     * @return ：账户对象
     */
   Account selectByAcctNo(String accNo);

    /**
     * 更新账户余额
     * @param newAccount 新的账户信息
     * @return 返回1表示更新成功
     */
   int updateBalance(Account newAccount);
}
