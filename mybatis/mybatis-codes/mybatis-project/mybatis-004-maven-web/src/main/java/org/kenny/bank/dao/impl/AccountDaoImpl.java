package org.kenny.bank.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.kenny.bank.common.exception.DataAccessException;
import org.kenny.bank.common.util.SqlSessionUtil;
import org.kenny.bank.dao.AccountDao;
import org.kenny.bank.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccountDaoImpl implements AccountDao {

    @Override
    public Account selectByAcctNo(String accNo) {
        //SqlSession对象在这里不要关闭
        return SqlSessionUtil.openSession().selectOne("selectByActNo", accNo);
    }

    @Override
    public int updateBalance(Account newAccount) {
        return SqlSessionUtil.openSession().update("updateByActNo", newAccount);
        //这里不需要提交因为我们现在在service里面的seqlSession对象和这里的sqlSession对象是同一个对象，我们在service里面提交和不好异常
        //所以这里也不需要捕获异常
    }
}
