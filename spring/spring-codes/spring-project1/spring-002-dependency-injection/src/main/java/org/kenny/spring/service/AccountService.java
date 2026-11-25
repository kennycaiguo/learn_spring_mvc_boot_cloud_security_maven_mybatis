package org.kenny.spring.service;

import org.kenny.spring.dao.AccountDao;

public class AccountService {
    private AccountDao accountDao;
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    public void saveAccount(){
        accountDao.insert();
    }

    @Override
    public String toString() {
        return "AccountService{" +
                "accountDao=" + accountDao +
                '}';
    }
}
